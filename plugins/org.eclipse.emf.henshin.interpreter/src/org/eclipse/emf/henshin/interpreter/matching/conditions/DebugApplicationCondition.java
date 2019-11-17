package org.eclipse.emf.henshin.interpreter.matching.conditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.StringJoiner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.HenshinModelPlugin;
import org.eclipse.emf.henshin.diagram.providers.HenshinMarkerNavigationProvider;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.debug.DebugValueEObject;
import org.eclipse.emf.henshin.interpreter.debug.DebugValueList;
import org.eclipse.emf.henshin.interpreter.debug.DebugValueObject;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugThread;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugValue;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugVariable;
import org.eclipse.emf.henshin.interpreter.debug.HenshinStackFrame;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ContainmentConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DanglingConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.PathConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Solution;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Node;

public class DebugApplicationCondition extends ApplicationCondition {  
	/**
	 * The Debug Levels from high to low (Variable > Value > Constraint Type > Constraint)
	 * ("step into" tries to go down a level, "step return" tries to go up a level, 
	 * "step over" tries to stay on the same level)
	 */
	public enum DebugLevel {
		NONE, VARIABLE, VALUE, CONSTRAINT_TYPE, CONSTRAINT;
		
		@Override
		public String toString() {
			return name();
		}
	}
	
	/**
	 * A debug code to signal different results in the stepping methods.<br>
	 * TERMINATED_TRUE - match found successfully.<br>
	 * TERMINATED_FALSE - no match found.<br>
	 * 
	 */
	public enum DebugState {
		RUNNING, STEPPING, SUSPENDED, TERMINATED_TRUE, TERMINATED_FALSE
	}
	
	/**
	 * The Constraint Types that are checked by the DomainSlot 
	 * (e.g. TypeConstraint, DanglingConstraint, AttributeConstraint, ...). <br>
	 * IMPORTANT: the order has to be the same as they are checked in 
	 * {@link DomainSlot#instantiate(Variable, Map, EGraph)}.
	 */
	public enum ConstraintType {
		NONE, TYPE, DANGLING, ATTRIBUTE, CONTAINMENT, REFERENCE, PATH, USER;
		
		public ConstraintType next() {
			if (isLast()) {
				return this;
			}
			return values()[(ordinal()+1) % values().length];
		}
		
		public boolean isLast() {
			return ordinal()+1 == values().length;
		}
		
		@Override
		public String toString() {
			// first letter uppercase
			String nameString = name().substring(0, 1);
			// rest lowercase, replace underscores with spaces
			nameString += name().substring(1, name().length()).toLowerCase().replace("_", " ");
			return nameString;
		}
	}
	
	
	/**
	 * the associated debug target
	 */
	private HenshinDebugTarget debugTarget;
	
    /**
     * The current debug state
     */
    private DebugState currentDebugState;
	
	private DebugLevel currentDebugLevel;
	private int currentVariableIndex;
	private ConstraintType currentConstraintType = ConstraintType.NONE;
	private int currentConstraintIndex;
	
	private Variable currentVariable;
	private DomainSlot currentSlot;
		
	private IStackFrame[] stackFrames;

	private Observer matchObserver;
	
	// used to compare currentVariable to HenshinBreakpoints
	private RuleInfo ruleInfo;
	
	private static DebugApplicationCondition instance;
	// needed for value breakpoints
	private EGraph graph;
	public EGraph getGraph() {
		return this.graph;
	}

	public DebugApplicationCondition(HenshinDebugTarget debugTarget, List<Variable> variables,
			Map<Variable, DomainSlot> domainMap, EGraph graph, IFormula formula, Observer matchObserver, RuleInfo ruleInfo) {
		super(graph, domainMap);
		this.debugTarget = debugTarget;
		this.variables = variables;
		this.formula = formula;
		this.matchObserver = matchObserver;
		this.ruleInfo = ruleInfo;
		
		currentDebugLevel = DebugLevel.NONE;
		currentVariableIndex = -1;
		currentConstraintType = ConstraintType.NONE;
		currentConstraintIndex = -1;
		currentDebugState = DebugState.SUSPENDED;
		
		// for value breakpoints
		instance = this;
		this.graph = graph;
	}
	
	// TODO: refactor, DebugApplicationCondition should not be static
	public static DebugApplicationCondition getInstance() {
		return instance;
	}

	
	public void initNextVariable() {

		// for the first call (maybe extract to a initFirstVariable() method)
		if (currentVariableIndex == -1) {
			updateDebugState(DebugLevel.VARIABLE, 0, ConstraintType.NONE, -1);

			if (debugTarget != null) {
				debugTarget.fireCreationEvent();				
			}
		}
		
		// Matched all variables?
		if (currentVariableIndex == variables.size()) {
			
			// Final variable re-checks:
			for (Variable variable : variables) {
				if (variable.requiresFinalCheck) {
					DomainSlot slot = domainMap.get(variable);
					if (!slot.recheck(variable, domainMap)) {
						// recheck turned out invalid --> terminate (no match found)
						updateDebugState(DebugLevel.NONE, -1, ConstraintType.NONE, -1);
						setCurrentDebugState(DebugState.TERMINATED_FALSE);
						if (debugTarget != null) {
							debugTarget.fireTerminateEvent();							
						}
						return;
					}
				}
			}
			
			// Evaluate formula:
			if (formula.eval()) {
				// final evaluation was successful --> terminate (match found)
				updateDebugState(DebugLevel.VARIABLE, currentVariableIndex-1, ConstraintType.NONE, -1);
				setCurrentDebugState(DebugState.TERMINATED_TRUE);
				
				// create a solution
				Solution solution = new Solution(variables, domainMap, currentSlot.getConditionHandler());
				
				// notify the observer that we have a solution
				if (matchObserver != null) {					
					matchObserver.update(null, solution);
				}
				
				if (debugTarget != null) {
					debugTarget.fireTerminateEvent();							
				}
				return;
			}
			
			// else: final evaluation was not successful --> terminate (no match found)
			updateDebugState(DebugLevel.NONE, -1, ConstraintType.NONE, -1);
			setCurrentDebugState(DebugState.TERMINATED_FALSE);
			if (debugTarget != null) {
				debugTarget.fireTerminateEvent();			
			}
			return;
			
		}
		
		// Otherwise select the last variable:
		currentVariable = variables.get(currentVariableIndex);
		currentSlot = domainMap.get(currentVariable);
		
		// initialize the domain slot
		if (!currentSlot.initialize(currentVariable, graph)) {
			//this can happen and is not a problem
		}
		
		if (debugTarget != null) {
			debugTarget.fireChangeEvent(DebugEvent.CONTENT);
			debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
		}				
	}

	/**
	 * uses the current constraint type & index to check the current constraint
	 * @return <code>true</code> if the current constraint is valid, <code>false</code> otherwise
	 */
	private boolean checkCurrentConstraint() {
		switch (currentConstraintType) {
		case TYPE:
			return currentSlot.checkTypeConstraint(currentVariable);
		case DANGLING:
			DanglingConstraint danglingConstraint = currentVariable.danglingConstraints.get(currentConstraintIndex);
			return currentSlot.checkDanglingConstraint(danglingConstraint, graph);
		case ATTRIBUTE:
			AttributeConstraint attributeConstraint = currentVariable.attributeConstraints.get(currentConstraintIndex);
			UnaryConstraint unaryUserConstraint = currentVariable.attributeUserConstraints.get(attributeConstraint);
			return currentSlot.checkAttributeConstraint(attributeConstraint, unaryUserConstraint);
		case CONTAINMENT:
			ContainmentConstraint containmentConstraint = currentVariable.containmentConstraints.get(currentConstraintIndex);
			return currentSlot.checkContainmentConstraint(containmentConstraint, domainMap);
		case REFERENCE:
			ReferenceConstraint referenceConstraint = currentVariable.referenceConstraints.get(currentConstraintIndex);
			BinaryConstraint binaryUserConstraint = currentVariable.binaryUserConstraints.get(referenceConstraint);
			return currentSlot.checkReferenceConstraint(referenceConstraint, binaryUserConstraint, domainMap);
		case PATH:
			PathConstraint pathConstraint = currentVariable.pathConstraints.get(currentConstraintIndex);
			return currentSlot.checkPathConstraint(pathConstraint, domainMap);
		case USER:
			return currentSlot.checkUserConstraint(currentVariable.userConstraints.get(currentConstraintIndex));
		case NONE:
			throw new IllegalStateException("trying to check the current constraint with currentConstraintType NONE");
		default:
			throw new IllegalStateException("currentConstraintType is \"" + currentConstraintType 
					+ "\", but has to be of the enum type ConstraintType");
		}
	}
	
	/**
	 * Tries to find another constraint type that has to be checked.
	 * If not, tries to continue to the next variable because all constraints for this variable are valid
	 * @return
	 */
	private synchronized void tryNextConstraintType() {
		//are there any constraint types (with constraints) left that we have to check?
		do {
			currentConstraintType = currentConstraintType.next();
			if (currentTypeHasConstraints()) {
				// continue with that constraint type
				updateDebugState(DebugLevel.CONSTRAINT_TYPE, currentVariableIndex, currentConstraintType, -1);
				return;
			}
		} while (!currentConstraintType.isLast());
		
		// there are no other constraint types left => all constraints are valid => go to the next variable
		updateDebugState(DebugLevel.VARIABLE, currentVariableIndex + 1, ConstraintType.NONE, -1);
		initNextVariable();
	}
	
	/**
	 * Tries to go to the next value. Calls {@link #tryLowerIndexVariable()} if no value is left
	 * @return
	 */
	private void tryNextValue() {
		// are any other values left for this variable?
		currentSlot.unlock(currentVariable);
		if (currentSlot.instantiationPossible()) {
			// go to next value
			currentSlot.setValueAndLock();
			updateDebugState(DebugLevel.VALUE, currentVariableIndex, ConstraintType.NONE, -1);
			if (debugTarget != null) {			
				debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
			}
		} else {
			tryLowerIndexVariable();
		}
	}
	
	/*
	 * Checks if the current variable has a next value.
	 */
	private boolean hasNextValue() {
		currentSlot.unlock(currentVariable);
		return currentSlot.instantiationPossible();
	}
	
	/**
	 * Tries to go to the next Variable with a lower index (shallower recursion depth)
	 * to continue the search for a match.
	 * @return
	 */
	private void tryLowerIndexVariable() {
		// clear (+ unlock) the current domain slot
		currentSlot.clear(currentVariable);

		// is there a lower index where a match might be found?
		if (currentVariableIndex > 0) {
			updateDebugState(DebugLevel.VARIABLE, currentVariableIndex - 1, ConstraintType.NONE, -1);
			initNextVariable();
			return;
		}
		
		// this is the "shallowest" recursion level (index 0)
		// --> terminate (no match found)
		updateDebugState(DebugLevel.NONE, -1, ConstraintType.NONE, -1);
		setCurrentDebugState(DebugState.TERMINATED_FALSE);
		if (debugTarget != null) {
			debugTarget.fireTerminateEvent();
		}
	}
	
	/**
	 * Checks if the current variable has constraints of the currently active constraint type
	 * @return the maximum index for the current constraint type (i.e # of constraints -1), 
	 * or -1 if there are no constraints of this type
	 */
	private int maxCurrentConstraintIndex() {
		switch (currentConstraintType) {
		case TYPE:
			// a variable always has one type constraint
			return 0;
		case DANGLING:
			return currentVariable.danglingConstraints.size()-1;
		case ATTRIBUTE:
			return currentVariable.attributeConstraints.size()-1;
		case CONTAINMENT:
			return currentVariable.containmentConstraints.size()-1;
		case REFERENCE:
			return currentVariable.referenceConstraints.size()-1;
		case PATH:
			return currentVariable.pathConstraints.size()-1;
		case USER:
			return currentVariable.userConstraints.size()-1;
		case NONE:
			throw new IllegalStateException("trying to get number of constraints with currentConstraintType NONE");
		default:
			throw new IllegalStateException("currentConstraintType is \"" + currentConstraintType 
					+ "\", but has to be of the enum type ConstraintType");
		}
	}
	
	private boolean currentTypeHasConstraints() {
		return maxCurrentConstraintIndex() >= 0;
	}

	/**
	 * updates the internal debug state
	 * 
	 * @param newDebugLevel the new {@link DebugLevel}
	 * @param newVariableIndex the new variable index (see {@link ApplicationCondition#findMatch(int)}
	 * @param newConstraintType the new constraint type (see {@link constraint#getClass()} or null if no constraint is active)
	 * @param newConstraintIndex the new iteration index for multiple constraints of the same type 
	 * (for example: {@link Variable#attributeConstraints}), or 0 if there is only one constraint or no constraint is active
	 */
	private synchronized void updateDebugState(
			DebugLevel newDebugLevel, 
			int newVariableIndex, 
			ConstraintType newConstraintType, 
			int newConstraintIndex) {
		
		currentDebugLevel = newDebugLevel;
		currentVariableIndex = newVariableIndex;
		currentConstraintType = newConstraintType;
		currentConstraintIndex = newConstraintIndex;
	}
	
	private void setCurrentDebugState(DebugState debugState) {
		if (!isTerminated()) {
			this.currentDebugState = debugState;
		}
	}

	public boolean canResume() {
		return isSuspended();
	}

	public boolean canSuspend() {
        return !isTerminated() && !isSuspended();
	}

	public boolean isSuspended() {
		return getCurrentDebugState() == DebugState.SUSPENDED;
	}

	public void resume() throws DebugException {
		synchronized (this) {
			if (getCurrentDebugState() == DebugState.SUSPENDED) {
				setCurrentDebugState(DebugState.RUNNING);
			}
						
			// if the currentDebugState is not terminated oder suspended we continue stepping
			while(!isTerminated() && getCurrentDebugState() != DebugState.SUSPENDED) {
				step();
			}
        }
	}

	public void suspend() throws DebugException {
		 synchronized (this) {
            setCurrentDebugState(DebugState.SUSPENDED);
        }
	}
	
	public boolean canStep() {
		return isSuspended();
	}

	public boolean canStepInto() {
		return isSuspended();
	}

	public boolean canStepOver() {
		return isSuspended();
	}

	public boolean canStepReturn() {
		return isSuspended() && currentDebugLevel != DebugLevel.VARIABLE;
	}

	public boolean isStepping() {
        return getCurrentDebugState() == DebugState.STEPPING;
	}

	/*
	 * Copy of the stepInto() method but without the static suspends after every step.
	 * We use this method to dive into the deeper logic without suspending the application while we do so.
	 */
	public void step() throws DebugException {
		if (currentVariable == null) {
			throw new IllegalStateException(
					"currentVariable is null - variableInitNext() has to be called before");
		}
		
		if (debugTarget != null) {
			debugTarget.fireResumeEvent(DebugEvent.STEP_INTO);
		}
		
		switch (currentDebugLevel) {
			case VARIABLE:
				// first unlock the variable because we might come from a higher index and have the slot still locked.
				// This was added because otherwise, when coming from a higher index while backtracking,
				// currentSlot.instantiate() would select the old slot value again instead of the next one.
				if (currentSlot.getValue() != null) {
					currentSlot.unlock(currentVariable);
				}
				
				// set the value
				if (!currentSlot.setValueAndLock()) {
					throw new IllegalStateException("step called on Variable level, but no values left: "
						+ toString());
				}

				// update the debug state (current index does not change)
				updateDebugState(DebugLevel.VALUE, currentVariableIndex, ConstraintType.NONE, -1);
												
				break;
			case VALUE:
				// go to the "type" constraint type as it is always the first constraint. 
				updateDebugState(DebugLevel.CONSTRAINT_TYPE, currentVariableIndex, ConstraintType.TYPE, -1);
				break;

			case CONSTRAINT_TYPE:
				// in this state there should be a constraint of the current type left
				// (this has to be ensured by the steps that lead to the "constraint type" level)
				if (!currentTypeHasConstraints()) {
					throw new IllegalStateException("called stepInto for constraint type \"" + currentConstraintType + "\", but this type has no constraint to step into");
				}

				// if there is a constraint, step into it
				updateDebugState(DebugLevel.CONSTRAINT, currentVariableIndex, currentConstraintType, 0);
				break;
	
			case CONSTRAINT:
				if (currentConstraintIndex > maxCurrentConstraintIndex()) {
					throw new IllegalStateException("called stepOver for constraint type \"" + currentConstraintType + "\", but this type has no unchecked constraint left and should have been skipped");
				}
				boolean constraintIsValid = checkCurrentConstraint();
				if (constraintIsValid) {
					// are there any constraints of the current type left that we have to check?
					if (currentConstraintIndex < maxCurrentConstraintIndex()) {
						// go to the next constraint of the current type
						updateDebugState(DebugLevel.CONSTRAINT, currentVariableIndex, currentConstraintType, currentConstraintIndex + 1);
						break;
					}
					// else: see if there is a constraint type left that we have to check
					tryNextConstraintType();
					break;
					
				}
				if (!constraintIsValid) {
					// try to go to the next value
					tryNextValue();
				}
				break;
			default:
				throwExceptionInvalidDebugLevel();
		}

		// initially get all henshin breakpoints
		ArrayList<HenshinBreakpoint> henshinBreakpoints = getHenshinBreakpoints();
		
		// This is the node representation for the variable we're currently at.
		// Compare this node with the node we received (when the user sets a HenshinBreakpoint with right click) when setting HenshinBreakpoints 
		Node currentNode = ruleInfo.getVariableInfo().getVariableForNode(currentVariable);
		// Get relative path to node - unique representation of the node. Looks something like this: '@units.1/@lhs/@nodes.0'
		String currentNodePath = EcoreUtil.getRelativeURIFragmentPath(null, currentNode);
		// get the current type constraint to compare in shouldStopAtVariableBreakpoint later
		String currentTypeName = currentVariable.typeConstraint.type.getName();
				
		// handle variable breakpoints
		ArrayList<VariableBreakpoint> variableBreakpoints = filterVariableBreakpoints(henshinBreakpoints);
		if (variableBreakpoints.size() > 0 && currentDebugLevel == DebugLevel.VARIABLE) {
			for (VariableBreakpoint variableBreakpoint : variableBreakpoints) {
				// pass all three parameters necessary to clearly determine whether to suspend the application or not
				if (shouldStopAtVariableBreakpoint(variableBreakpoint, currentTypeName, currentNodePath)) {
					suspendApplication();
				}
			}
		}
		
		// handle value breakpoints
		if (currentSlot.getValue() != null) {
			// get complete domain (e.g. domain for :Client could be 'charles', 'bob', 'alice')
			List<EObject> domain = graph.getDomain(currentSlot.getValue().eClass(), false);
			//List<EObject> domain = currentSlot.getDomain();
			EObject currentValue = currentSlot.getValue();
			int index = domain.indexOf(currentValue);
			
			// get the current domain
			// List<EObject> currentDomain = currentSlot.getDomain();
			// get all value breakpoints
			ArrayList<ValueBreakpoint> valueBreakpoints = filterValueBreakpoints(henshinBreakpoints);
			if (valueBreakpoints.size() > 0 && currentDebugLevel == DebugLevel.VALUE) {
				for (ValueBreakpoint valueBreakpoint : valueBreakpoints) {
					if (shouldStopAtValueBreakpoint(valueBreakpoint, currentValue, index)) {
						suspendApplication();
					}
				}
			}
		}
		
		// handle constraint type breakpoints
		ArrayList<ConstraintTypeBreakpoint> constraintTypeBreakpoints = filterConstraintTypeBreakpoints(henshinBreakpoints);
		if (constraintTypeBreakpoints.size() > 0 && currentDebugLevel == DebugLevel.CONSTRAINT_TYPE) {
			for (ConstraintTypeBreakpoint constraintTypeBreakpoint : constraintTypeBreakpoints) {
				if (shouldStopAtConstraintTypeBreakpoint(constraintTypeBreakpoint)) {
					suspendApplication();
				}
			}
		}
		
		// handle constraint instance breakpoints
		ArrayList<ConstraintInstanceBreakpoint> constraintInstanceBreakpoints = filterConstraintInstanceBreakpoints(henshinBreakpoints);
		if (constraintInstanceBreakpoints.size() > 0 && currentDebugLevel == DebugLevel.CONSTRAINT) {
			for (ConstraintInstanceBreakpoint constraintInstanceBreakpoint : constraintInstanceBreakpoints) {
				if (shouldStopAtConstraintInstanceBreakpoint(constraintInstanceBreakpoint)) {
					suspendApplication();
				}
			}
		}
		
		if (debugTarget != null) {			
			debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
		}
	}
	
	/*
	 * Step into
	 */
	public void stepInto() throws DebugException {
		if (currentVariable == null) {
			throw new IllegalStateException(
					"currentVariable is null - variableInitNext() has to be called before");
		}
		
		if (debugTarget != null) {
			debugTarget.fireResumeEvent(DebugEvent.STEP_INTO);
		}
		
		switch (currentDebugLevel) {
			case VARIABLE:
				
				// first unlock the variable because we might come from a higher index and have the slot still locked.
				// This was added because otherwise, when coming from a higher index while backtracking,
				// currentSlot.instantiate() would select the old slot value again instead of the next one.
				if (currentSlot.getValue() != null) {
					currentSlot.unlock(currentVariable);
				}
				
				// set the value
				if (!currentSlot.setValueAndLock()) {
					tryLowerIndexVariable();
				}
				else {
					
					// update the debug state (current index does not change)
					updateDebugState(DebugLevel.VALUE, currentVariableIndex, ConstraintType.NONE, -1);
					setCurrentDebugState(DebugState.SUSPENDED);
				}
				break;
			case VALUE:
				// go to the "type" constraint type as it is always the first constraint.
				updateDebugState(DebugLevel.CONSTRAINT_TYPE, currentVariableIndex, ConstraintType.TYPE, -1);
				setCurrentDebugState(DebugState.SUSPENDED);
				break;

			case CONSTRAINT_TYPE:
				// in this state there should be a constraint of the current type left
				// (this has to be ensured by the steps that lead to the "constraint type" level)
				if (!currentTypeHasConstraints()) {
					throw new IllegalStateException("called stepInto for constraint type \"" 
							+ currentConstraintType + "\", but this type has no constraint to step into");
				}

				// if there is a constraint, step into it
				updateDebugState(DebugLevel.CONSTRAINT, currentVariableIndex, currentConstraintType, 0);
				setCurrentDebugState(DebugState.SUSPENDED);
				break;
	
			case CONSTRAINT:
				// identical to stepOver
				step();
				setCurrentDebugState(DebugState.SUSPENDED);
				break;
			default:
				throwExceptionInvalidDebugLevel();
		}
		
		if (debugTarget != null) {			
			debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
		}
		
	}
	
	/*
	 * New step over
	 */
public void stepOver() throws DebugException {
		
		if (currentVariable == null) {
			throw new IllegalStateException(
					"currentVariable is null - variableInitNext() has to be called before");
		}
		
		if (debugTarget != null) {
			debugTarget.fireResumeEvent(DebugEvent.STEP_OVER);
		}
		
		switch (currentDebugLevel) {
			case VARIABLE:
				
				// first unlock the variable because we might come from a higher index and have the slot still locked.
				// This was added because otherwise, when coming from a higher index (i.e. going up in the "recursion"),
				// currentSlot.instantiate() would select the old slot value again instead of the next one.
				if (currentSlot.getValue() != null) {
					currentSlot.unlock(currentVariable);
				}
				
				// keep track of the variable we are currently at
				Variable tempVariable = currentVariable;
				// simulates an ordinary step over but with "looking" at all intermediate steps in the debugger as well
				while(checkForChange(tempVariable, currentVariable)) {
					step();
				}
				break;
			
			case VALUE:
				// stores the current value as we enter the VALUE case
				EObject tempValue = currentSlot.getValue();
				while (checkForChange(tempValue, currentSlot.getValue())) {
					step();
				}
				break;

			case CONSTRAINT_TYPE:
				// keeps track of the constraint type we started from
				ConstraintType tempConstraintType = currentConstraintType;
				while(checkForChange(tempConstraintType, currentConstraintType)) {
					step();
				}
				break;
				
			case CONSTRAINT:
				step();
				setCurrentDebugState(DebugState.SUSPENDED);
				break;
			
			default:
				throwExceptionInvalidDebugLevel();
		}
		
		if (debugTarget != null) {	
			debugTarget.fireChangeEvent(DebugEvent.CONTENT);
			debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
		}		
	}


public void stepReturn() throws DebugException {
	if (currentVariable == null) {
		throw new IllegalStateException(
				"currentVariable is null - variableInitNext() has to be called before");
	}
	
	if (debugTarget != null) {
		debugTarget.fireResumeEvent(DebugEvent.STEP_RETURN);
	}
	
	switch (currentDebugLevel) {
		case VARIABLE:
			// identical to stepOver
			stepOver();
			break;
		case VALUE:
			// keep track of the variable we are currently at
			Variable tempVariable = currentVariable;
			// boolean flag to determine whether we should keep stepping or not
			while(checkForChange(tempVariable, currentVariable)) {
				step();
			}
			break;
			
		case CONSTRAINT_TYPE:
			// stores the current value as we enter the VALUE case
			EObject tempValue = currentSlot.getValue();
			while (checkForChange(tempValue, currentSlot.getValue())) {
				step();
			}
			break;

		case CONSTRAINT:
			// keeps track of the constraint type we started from
			ConstraintType tempConstraintType = currentConstraintType;
			while(checkForChange(tempConstraintType, currentConstraintType)) {
				step();
			}
			break;

		default:
			throwExceptionInvalidDebugLevel();
	}
	
	if (debugTarget != null) {	
		debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
	}
}

	public boolean canTerminate() {
		return !isTerminated();
	}

	public boolean isTerminated() {
		return getCurrentDebugState() == DebugState.TERMINATED_FALSE
				|| getCurrentDebugState() == DebugState.TERMINATED_TRUE;
	}

	public void terminate() throws DebugException {
		updateDebugState(DebugLevel.NONE, -1, ConstraintType.NONE, -1);
		setCurrentDebugState(DebugState.TERMINATED_FALSE);
		if (debugTarget != null) {			
			debugTarget.fireTerminateEvent();
		}		
	}

	public String getName() throws DebugException {
		// if the matching has terminated successfully, display the match to the user
		if (getCurrentDebugState() == DebugState.TERMINATED_TRUE) {
			return "["+variables.size() + "/" + variables.size() + " variables matched]";
		} else if (getCurrentDebugState() == DebugState.TERMINATED_FALSE) {
			return "[no match found]";
		}
		
		// if the matching is still in process, display the number of matched variables
		return currentVariableIndex + "/" + variables.size() + " variables matched";
	}

	/*
	 * Custom methods start here
	 */
	
	public IBreakpointManager getManager() {
		return DebugPlugin.getDefault().getBreakpointManager(); 
	} 
	
	public IBreakpoint[] getBreakpoints() {
		// get manager and its breakpoints
		IBreakpointManager manager = getManager();
		IBreakpoint[] breakpoints = manager.getBreakpoints();
		
		if (breakpoints.length > 0) {
			manager.getTypeName(breakpoints[0]);
		}
		return breakpoints;
	}
	
	/**
	 * Returns all henshin breakpoints we currently have
	 */
	public ArrayList<HenshinBreakpoint> getHenshinBreakpoints() {
		IBreakpoint[] breakpoints = getBreakpoints();
		ArrayList<HenshinBreakpoint> henshinBreakpoints = filterHenshinBreakpoints(breakpoints);
		return henshinBreakpoints;
	}
	
	/**
	 * Sets an example variable breakpoint.
	 */
	public void setVariableBreakpoint(Variable variable) {
		// get all breakpoints
		IBreakpointManager manager = getManager();
		// create breakpoint
		IFile moduleFile = debugTarget.getModuleFile();
		IMarker marker = HenshinMarkerNavigationProvider.addMarker(moduleFile, HenshinModelPlugin.PLUGIN_ID, "/variable", "Sample VariableBreakpoint", IStatus.OK);
		VariableBreakpoint breakpoint = new VariableBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			// This is the node representation for the variable we're currently at.
			// Compare this node with the node we received (when the user sets a HenshinBreakpoint with right click) when setting HenshinBreakpoints 
			Node currentNode = ruleInfo.getVariableInfo().getVariableForNode(variable);
			// Get relative path to node - unique representation of the node. Looks something like this: '@units.1/@lhs/@nodes.0'
			String nodePath = EcoreUtil.getRelativeURIFragmentPath(null, currentNode);
			// get the current type constraint to compare in shouldStopAtVariableBreakpoint later
			String typeName = variable.typeConstraint.type.getName();
			// uncomment the lines below to stop at from:Account in transferMoney rule or at constraintType 'Manager' in createAccount
			breakpoint.setPath(nodePath); // equals node of 'from: Account' in transferMoney rule
			breakpoint.setTypeName(typeName); // the exact string representation of the current constraint type (in this case 'Manager' from createAccount rule) - set this via right click by using constraintType.type.getName()
			breakpoint.setEnabled(true);
			// configure breakpoint
			breakpoint.setDebugLevel(DebugLevel.VARIABLE);
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom VariableBreakpoint.");
			e1.printStackTrace();
		}
	}
	
	/**
	 * Sets breakpoint for a given value.
	 * This is determined by the value itself and its corresponding index within the domain.
	 */
	public void setValueBreakpoint(HenshinDebugValue value, int index) {
		// get all breakpoints
		IBreakpointManager manager = getManager();
		// create breakpoint
		IFile moduleFile = debugTarget.getModuleFile();
		IMarker marker = HenshinMarkerNavigationProvider.addMarker(moduleFile, HenshinModelPlugin.PLUGIN_ID, "/value", "Sample ValueBreakpoint", IStatus.OK);
		ValueBreakpoint breakpoint = new ValueBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			breakpoint.setEnabled(true);
			// configure breakpoint
			breakpoint.setType(value.getReferenceTypeName());
			breakpoint.setValueString(value.getValueString());
			breakpoint.setIndex(index);
			breakpoint.setDebugLevel(DebugLevel.VALUE);
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom ValueBreakpoint.");
			e1.printStackTrace();
		}
	}
	
	public void setConstraintTypeBreakpoint(String constraintType) {
		// get all breakpoints
		IBreakpointManager manager = getManager();
		// create breakpoint
		IFile moduleFile = debugTarget.getModuleFile();
		IMarker marker = HenshinMarkerNavigationProvider.addMarker(moduleFile, HenshinModelPlugin.PLUGIN_ID, "/constraintType", "Sample ConstraintTypeBreakpoint", IStatus.OK);
		ConstraintTypeBreakpoint breakpoint = new ConstraintTypeBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			breakpoint.setEnabled(true);
			// configure breakpoint
			breakpoint.setType(ConstraintType.valueOf(constraintType));
			breakpoint.setDebugLevel(DebugLevel.CONSTRAINT_TYPE);
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom ConstraintTypeBreakpoint.");
			e1.printStackTrace();
		}
	}
	
	public void setConstraintInstanceBreakpoint(String constraintInstance) {
		// get all breakpoints
		IBreakpointManager manager = getManager();
		// create breakpoint
		IFile moduleFile = debugTarget.getModuleFile();
		IMarker marker = HenshinMarkerNavigationProvider.addMarker(moduleFile, HenshinModelPlugin.PLUGIN_ID, "/constraintInstance", "Sample ConstraintInstanceBreakpoint", IStatus.OK);
		ConstraintInstanceBreakpoint breakpoint = new ConstraintInstanceBreakpoint();
		try {
			// set marker for breakpoint
			breakpoint.setMarker(marker);
			breakpoint.setEnabled(true);
			// configure breakpoint
			
			breakpoint.setConstraintInstance(removeRuntimeValuesFromConstraintInstance(constraintInstance));
			breakpoint.setDebugLevel(DebugLevel.CONSTRAINT);
			// add breakpoint to manager to keep track
			manager.addBreakpoint(breakpoint);
		} catch (CoreException e1) {
			System.out.println("Unable to create custom ConstraintInstanceBreakpoint.");
			e1.printStackTrace();
		}
	}
	
	protected String removeRuntimeValuesFromConstraintInstance(String constraintInstance) {
		// Remove runtime values from the constraint instance string
		final int indexOfParenthesis = constraintInstance.indexOf('(');
		if (indexOfParenthesis > 0) {
			constraintInstance = constraintInstance.substring(0, indexOfParenthesis - 1);
		}
		return constraintInstance;
	}
	
	
	/**
	 * Returns an array list containing all HenshinBreakpoints currently available
	 */
	public ArrayList<HenshinBreakpoint> filterHenshinBreakpoints(IBreakpoint[] breakpoints) {
		// array list of HenshinBreakpoints to be returned
		ArrayList<HenshinBreakpoint> henshinBreakpoints = new ArrayList<HenshinBreakpoint>();
		
		// loop through all breakpoints and return the ones which are of type henshin breakpoints
		for (IBreakpoint breakpoint : breakpoints) {
			// local variable
			HenshinBreakpoint henshinBreakpoint;
			if (isHenshinBreakpoint(breakpoint)) {
				// cast to henshin breakpoint
				henshinBreakpoint = (HenshinBreakpoint) breakpoint;
				henshinBreakpoints.add(henshinBreakpoint);
			}
		}
		
		return henshinBreakpoints;
	}
	
	/**
	 * Checks whether a given breakpoint is a subclass of HenshinBreakpoint
	 */
	public boolean isHenshinBreakpoint(IBreakpoint breakpoint) {
		return HenshinBreakpoint.class.isAssignableFrom(breakpoint.getClass());
	}
	
	/**
	 * Returns an array list containing all VariableBreakpoints currently available
	 */
	public ArrayList<VariableBreakpoint> filterVariableBreakpoints(ArrayList<HenshinBreakpoint> henshinBreakpoints) {
		ArrayList<VariableBreakpoint> variableBreakpoints = new ArrayList<VariableBreakpoint>();
		
		// loop through all breakpoints and return the ones which are of type VariableBreakpoint
		for (HenshinBreakpoint henshinBreakpoint : henshinBreakpoints) {
			// local variable
			if (isVariableBreakpoint(henshinBreakpoint)) {
				// cast to VariableBreakpoint
				VariableBreakpoint variableBreakpoint = (VariableBreakpoint) henshinBreakpoint;
				variableBreakpoints.add(variableBreakpoint);
			}
		}
		
		return variableBreakpoints;
	}
	
	/**
	 * Checks whether a given breakpoint is of type VariableBreakpoint
	 */
	public boolean isVariableBreakpoint(HenshinBreakpoint breakpoint) {
		return breakpoint instanceof VariableBreakpoint;
	}
	
	/**
	 * Filter all constraint type breakpoints and returns an array list.
	 * @param henshinBreakpoints
	 * @return
	 */
	public ArrayList<ConstraintTypeBreakpoint> filterConstraintTypeBreakpoints(ArrayList<HenshinBreakpoint> henshinBreakpoints) {
		ArrayList<ConstraintTypeBreakpoint> constraintTypeBreakpoints = new ArrayList<ConstraintTypeBreakpoint>();
		
		// loop through all breakpoints and return the ones which are of type ConstraintTypeBreakpoint
		for (HenshinBreakpoint henshinBreakpoint : henshinBreakpoints) {
			// local variable
			if (isConstraintTypeBreakpoint(henshinBreakpoint)) {
				// cast to VariableBreakpoint
				ConstraintTypeBreakpoint constraintTypeBreakpoint = (ConstraintTypeBreakpoint) henshinBreakpoint;
				constraintTypeBreakpoints.add(constraintTypeBreakpoint);
			}
		}
		
		return constraintTypeBreakpoints;
	}
	
	/**
	 * Checks whether a given breakpoint is of type ConstraintTypeBreakpoint
	 */
	public boolean isConstraintTypeBreakpoint(HenshinBreakpoint breakpoint) {
		return breakpoint instanceof ConstraintTypeBreakpoint;
	}
	
	/**
	 * Filter all constraint instance breakpoints and returns an array list.
	 * @param henshinBreakpoints
	 * @return
	 */
	public ArrayList<ConstraintInstanceBreakpoint> filterConstraintInstanceBreakpoints(ArrayList<HenshinBreakpoint> henshinBreakpoints) {
		ArrayList<ConstraintInstanceBreakpoint> constraintInstanceBreakpoints = new ArrayList<ConstraintInstanceBreakpoint>();
		
		// loop through all breakpoints and return the ones which are of type ConstraintTypeBreakpoint
		for (HenshinBreakpoint henshinBreakpoint : henshinBreakpoints) {
			// local variable
			if (isConstraintInstanceBreakpoint(henshinBreakpoint)) {
				// cast to VariableBreakpoint
				ConstraintInstanceBreakpoint constraintInstanceBreakpoint = (ConstraintInstanceBreakpoint) henshinBreakpoint;
				constraintInstanceBreakpoints.add(constraintInstanceBreakpoint);
			}
		}
		
		return constraintInstanceBreakpoints;
	}	
	
	/**
	 * Checks whether a given breakpoint is of type ConstraintTypeBreakpoint
	 */
	public boolean isConstraintInstanceBreakpoint(HenshinBreakpoint breakpoint) {
		return breakpoint instanceof ConstraintInstanceBreakpoint;
	}
	
	
	/**
	 * Checks all the necessary parameters to determine whether to stop at this variable or not.
	 * @param variableBreakpoint
	 * @param currentTypeName
	 * @param currentNodePath
	 * @return true if any of the criteria of the breakpoint match the current variable 
	 */
	public boolean shouldStopAtVariableBreakpoint(VariableBreakpoint variableBreakpoint, String currentTypeName, String currentNodePath) {		
		try {
			// check if enabled
			if (variableBreakpoint.isEnabled()) {
				// check if we want to stop at every breakpoint anyway
				if (variableBreakpoint.isGeneric()) {
					return true;
				}
				
				// check if we want to stop at a specific variable
				if (variableBreakpoint.isSpecificVariable()) {
					if (currentNodePath.equals(variableBreakpoint.getPath())) {
						return true;
					}
				}
				
				// check if we want to stop at a specific variable type
				if (variableBreakpoint.isSpecificType()) {
					if (currentTypeName.equals(variableBreakpoint.getTypeName())) {
						return true;
					}
				}
			}

			// if none of the above match we just return false to keep stepping
			return false;
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Checks if the application should suspend at the given ValueBreakpoint.
	 * Therefore we check if the value we saved in the ValueBreakpoint is a member of the domain
	 * and if so we additionally check if the saved index matches the index of the value within the domain.
	 * 
	 * @param variableBreakpoint
	 * @param domain
	 * @return boolean
	 */
	public boolean shouldStopAtValueBreakpoint(ValueBreakpoint valueBreakpoint, EObject value, int index) {
		try {
			if (valueBreakpoint.isEnabled()) {
				// breakpoint data
				String breakpointType = valueBreakpoint.getType();
				int breakpointIndex = valueBreakpoint.getIndex();
				
				// current data
				String currentType = value.eClass().getName();
				int currentIndex = index; 
				
				// check
				if (breakpointType.equals(currentType) && breakpointIndex == currentIndex) {
					return true;
				}
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return false;
	}
	
	/**
	 * 
	 * @param constraintTypeBreakpoint
	 * @return
	 */
	public boolean shouldStopAtConstraintTypeBreakpoint(ConstraintTypeBreakpoint constraintTypeBreakpoint) {
		return constraintTypeBreakpoint.getType() == currentConstraintType;
	}
	
	/**
	 * 
	 * @param constraintTypeBreakpoint
	 * @return
	 */
	public boolean shouldStopAtConstraintInstanceBreakpoint(ConstraintInstanceBreakpoint constraintInstanceBreakpoint) {
		return removeRuntimeValuesFromConstraintInstance(retrieveConstraintLabel()).equals(constraintInstanceBreakpoint.getConstraintInstance());
	}
	
	/**
	 * Checks if there are any variables left.
	 * 
	 * @return true if current variable is last variable
	 */
	public boolean isLastVariable() {
		return currentVariableIndex == variables.size() - 1;
	}
	
	/**
	 * Returns an array list containing all ValueBreakpoints currently available
	 */
	public ArrayList<ValueBreakpoint> filterValueBreakpoints(ArrayList<HenshinBreakpoint> henshinBreakpoints) {
		ArrayList<ValueBreakpoint> valueBreakpoints = new ArrayList<ValueBreakpoint>();
		
		// loop through all breakpoints and return the ones which are of type VariableBreakpoint
		for (HenshinBreakpoint henshinBreakpoint : henshinBreakpoints) {
			// local variable
			if (isValueBreakpoint(henshinBreakpoint)) {
				// cast to VariableBreakpoint
				ValueBreakpoint valueBreakpoint = (ValueBreakpoint) henshinBreakpoint;
				valueBreakpoints.add(valueBreakpoint);
			}
		}
		
		return valueBreakpoints;
	}
	
	/**
	 * Checks whether a given breakpoint is of type ValueBreakpoint
	 */
	public boolean isValueBreakpoint(IBreakpoint breakpoint) {
		return breakpoint instanceof ValueBreakpoint;
	}
		
	/*
	 * Suspends the application if certain criteria for the breakpoint are met.
	 */
	public void handleDebugState(HenshinBreakpoint henshinBreakpoint) {
		
		// TODO:
		
		// get debug level of current henshinBreakpoint
		DebugLevel henshinBreakpointDebugLevel = henshinBreakpoint.getDebugLevel();
		
		if (currentDebugLevel.equals(henshinBreakpointDebugLevel)) {
			// TODO: Suspend when criteria met
			// we want to suspend here
			try {
				debugTarget.suspend();
			} catch (DebugException e) {
				System.out.println("Application could not be suspended.");
				e.printStackTrace();
			}
		} else {
			// resume the application in case we are suspended
			try {
				debugTarget.resume();
			} catch (DebugException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Suspend the application
	 */
	public void setSuspended() {
		try {
			debugTarget.suspend();
		} catch (DebugException e) {
			System.out.println("Application could not be suspended.");
			e.printStackTrace();
		}
	}
	
	/*
	 * Resume the application
	 */
	public void setResumed() {
		// resume the application in case we are suspended
		try {
			debugTarget.resume();
		} catch (DebugException e) {
			System.out.println("Application could not be resumed.");
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks for deeper and debug levels and returns a boolean to indicate
	 * whether the application should be suspended or not. 
	 */
	public boolean shouldSuspendApplication(HenshinBreakpoint henshinBreakpoint) {		
		switch (currentDebugLevel) {
		case VARIABLE:
			if (henshinBreakpoint instanceof VariableBreakpoint) {
				// TODO: Implement - Check for VariableBreakpoint attributes
				System.out.println("Matched VariableBreakpoint");
			}
			break;
		case VALUE:
			if (henshinBreakpoint instanceof ValueBreakpoint) {
				// TODO: Implement
				System.out.println("Matched ValueBreakpoint");
			}
			break;
		default:
			break;
		}
		
		
		return false;
	}
			
	/*
	 * Custom methods end here
	 */
	
	/**
	 * returns the current array of stackFrames, each containing variables.
	 * NOTE: maybe we could store the stackframes that do not change
	 * @return
	 */
	public synchronized IStackFrame[] getStackFrames(HenshinDebugThread debugThread) {
		
		// calculate the number of stackframes we need:
		// we need one stackframe for every matched variable as well as one for the current one.
		int lines = currentVariableIndex + 1;
		
		// each debuglevel, if present, gets an extra stackframe
		// (current value, constraint type, constraint instance)
		if (currentDebugLevel.ordinal() > 1) {
			lines += (currentDebugLevel.ordinal() - 1);
		}
		
		stackFrames = new IStackFrame[lines];
		
		StringBuilder labelBuilder = new StringBuilder();
		
		// first loop over all matched variables + the current one.
		for (int i = 0; i <= currentVariableIndex; i++) {
			Variable var = variables.get(i);
			DomainSlot slot = domainMap.get(var);
			
			labelBuilder.append("Variable ").append(retrieveVariableLabel(var));
			
			if (!isTerminated() && i != currentVariableIndex || isTerminated()) {
				labelBuilder.append(" (Match: ")
				.append(retrieveValueLabel(slot.getValue(), graph))
				.append(")");
			}

			HenshinDebugVariable debugVariable =
					new HenshinDebugVariable(
							debugTarget,
							"Variable",
							retrieveVariableLabel(var),
							var);			
			
			String declaredType = var.typeConstraint.type.getName();
			
			// when the domainSlot is null, it is necessary to add the "[]" postfix to the "declared type" string beforehand.
			// in all other cases, the postfix is added inside the HenshinDebugValue constructor
			if (slot.getDomain() == null) {
				declaredType += "[]";
			}
												
			HenshinDebugVariable debugDomain =
					new HenshinDebugVariable(
							debugTarget,
							"Domain",
							new DebugValueList(
									debugTarget,
									graph,
									declaredType,
									(slot.getDomain() != null ? slot.getDomain() : new ArrayList<EObject>()) // if the domain is empty we pass an empty array list, else we pass the domain
									)
							);
			
			IVariable[] stackVariables = new IVariable[]{debugVariable, debugDomain};
			
			stackFrames[i] = new HenshinStackFrame(debugThread, stackVariables, labelBuilder.toString(), i);
			
			// clear the string builder
			labelBuilder.setLength(0);
		}

		// then add the extra stackframes for...
		// ...value
		if (currentDebugLevel.ordinal() > 1) {
			
			/*
			 * Get whole domain (e.g. domain for :Client could be 'charles', 'bob', 'alice').
			 * Domain may also be null, then we use an empty array list.
			 */
			List<EObject> domain;
			if (currentSlot.getValue() != null) {
				domain = graph.getDomain(currentSlot.getValue().eClass(), false);
			} else {
				domain = new ArrayList<EObject>();
			}
			int index = domain.indexOf(currentSlot.getValue());
			
			HenshinDebugVariable debugValue =
					new HenshinDebugVariable(
							debugTarget,
							"Value",
							new DebugValueEObject(
									debugTarget,
									graph,
									currentVariable.typeConstraint.type.getName(),
									currentSlot.getValue(),
									index)
							);
						
			IVariable[] stackFrameVariables = new IVariable[]{debugValue};
			String label = "Value: '" + retrieveValueLabel(currentSlot.getValue(), graph) + "'";
			
			stackFrames[currentVariableIndex+1] = new HenshinStackFrame(debugThread, stackFrameVariables, label, currentVariableIndex+1);
			
			// ... constraint type
			if (currentDebugLevel.ordinal() > 2) {
				HenshinDebugVariable debugConstrType =
						new HenshinDebugVariable(
								debugTarget,
								"Constraint Type",
								currentConstraintType.toString(),
								ConstraintType.class.getSimpleName());

				label = "Constraint Type: '" + currentConstraintType + "'";
				stackFrames[currentVariableIndex+2] = new HenshinStackFrame(debugThread, new IVariable[]{debugConstrType}, label, currentVariableIndex+2);
				
				// ...constraint instance
				if (currentDebugLevel.ordinal() > 3) {
					
					HenshinDebugVariable debugConstrIndex = 
							new HenshinDebugVariable(
									debugTarget,
									"Constraint Index", 
									new DebugValueObject(
											debugTarget,
											graph,
											int.class.getName(),
											currentConstraintIndex,
											0)
									);
					
					label = retrieveConstraintLabel();

					HenshinDebugVariable debugConstraint = 
							new HenshinDebugVariable(debugTarget, "Constraint", label, currentConstraintType.toString() + " Constraint");
					
//					if (stackFrames.length >= currentVariableIndex + 4) {
						stackFrames[currentVariableIndex+3] = new HenshinStackFrame(debugThread, new IVariable[]{debugConstrIndex, debugConstraint}, label, currentVariableIndex+3);
//					}
				}
			}
		}
			
		return stackFrames;
	}

	private String retrieveVariableLabel(Variable var) {
		// include variable name from the rule's LHS
		String varNamePrefix = var.name != null ? var.name + ":" : "";
		return variables.indexOf(var) + ": '" + varNamePrefix + var.typeConstraint.type.getName() + "'";
	}

	/**
	 * Attempts to find an appropriate String representation for the given value EObject.
	 * This is done by looking for a "name" or an "id" attribute.
	 * Tries to create an index from the graph if no such attributes can be found.
	 * Uses the EObjects hashCode, if everything else fails.
	 * @param value the {@link EObject} that a label should be found for
	 * @param graph the {@link EGraph} that contains the value, or <code>null</code>
	 * @return the label String.
	 */
	public static String retrieveValueLabel(EObject value, EGraph graph) {
		if (value == null)
			return "null";
		// the label should start with the eClass name
		String label = value.eClass().getName() + " ";
		
		// look for a "name" or an "id" attribute to use for the label	
		for (EStructuralFeature feature : value.eClass().getEAllStructuralFeatures()) {
			if (feature.getName().equalsIgnoreCase("name")
					|| feature.getName().equalsIgnoreCase("id")) {
				label += value.eGet(feature);
				return label;
			}
		}
		
		// no useful feature found
		if (graph != null) {
			List<EObject> allValues = graph.getDomain(value.eClass(), false);
			int valIndex = allValues.indexOf(value);
			
			if (valIndex != -1) {
				label += "(index=" + valIndex + ")";
			} else {
				// value is not part of the graph -> use hashCode
				label += "(hashCode=" + value.hashCode() + ")";
			}
		} else {
			// graph is not available -> use hashCode
			label += "(hashCode=" + value.hashCode() + ")";
		}
	
		
		return label;
	}
		
	/**
	 * Tries to find an appropriate label for the stackFrame to visualize the current constraint.
	 * This label depends on the current constraint type.
	 * @return a label string describing the current constraint instance
	 */
	@SuppressWarnings("unchecked")
	private synchronized String retrieveConstraintLabel() {
		String label = "";
		
		if (currentDebugLevel != DebugLevel.CONSTRAINT) {
			return "Constraint " + currentConstraintIndex;
		}
		
		switch (currentConstraintType) {
		case TYPE:
			label += ("Type = " + currentVariable.typeConstraint.type.getName());
			break;
		case DANGLING:
			label += retrieveValueLabel(currentSlot.getValue(), graph);
			break;
		case ATTRIBUTE:
			AttributeConstraint attributeConstraint = currentVariable.attributeConstraints.get(currentConstraintIndex);
			String paramName = String.valueOf(attributeConstraint.getValue());
			String paramValue = String.valueOf(currentSlot.getConditionHandler().getParameter(paramName));
			// paramValue should be the param name for unset parameters
			if (!currentSlot.getConditionHandler().isSet(paramName)) {
				paramValue = paramName;
			}
			String attName = attributeConstraint.getAttribute().getName();
			/*
			 * This usually won't be null but if the user steps through 
			 * the debugging process too fast this may result in a null pointer exception.
			 * Therefore we check for a value first. 
			 */
			if (currentSlot.getValue() != null) {
				String attValue = String.valueOf(currentSlot.getValue().eGet(currentSlot.getValue().eClass().getEStructuralFeature(attName)));
				label += attName + " = " + paramName + " (" + attValue + " = " + paramValue + ")";				
			}
			break;
		case CONTAINMENT:
			ContainmentConstraint containmentConstraint = currentVariable.containmentConstraints.get(currentConstraintIndex);
			Variable targetVariable = containmentConstraint.getTargetVariable();

			EObject targetValue = domainMap.get(targetVariable).getValue();
			
			// when the container has no locked value yet, targetValue is null.
			// In this case, we want to display the containing variable to the user			
			if (targetValue == null) {
				label += retrieveVariableLabel(targetVariable);
			} else {
				label += targetValue.eClass().getName() + " " + retrieveValueLabel(targetValue, graph);				
			}
			break;
		case REFERENCE:
			ReferenceConstraint referenceConstraint = currentVariable.referenceConstraints.get(currentConstraintIndex);
			EReference reference = referenceConstraint.getReference();
			
			label += reference.getName() + ": ";
			
			List<EObject> targetObjects;
			if (reference.isMany()) {
				targetObjects = (List<EObject>) currentSlot.getValue().eGet(reference);
			} else {
				EObject obj = (EObject) currentSlot.getValue().eGet(reference);
				targetObjects = Collections.singletonList(obj);
			}
			
			StringJoiner listJoiner = new StringJoiner(", ", "[", "]");
			for (EObject obj : targetObjects) {
				listJoiner.add(retrieveValueLabel(obj, graph));	
			}
			label += listJoiner.toString();
			
			break;
		case PATH:
			PathConstraint pathConstraint = currentVariable.pathConstraints.get(currentConstraintIndex);
			List<EReference> references = pathConstraint.getReferences();
			listJoiner = new StringJoiner(", ", "[", "]");
			for (EReference ref : references) {
				listJoiner.add(ref.getName());
			}
			label += "Path to variable <" + retrieveVariableLabel(pathConstraint.getTargetVariable()) 
				+ "> via references " + listJoiner.toString();
			break;
		case USER:
			label += "User";
			break;
		case NONE:
			throw new IllegalStateException("trying to find label for constraint with currentConstraintType NONE");
		default:
			throw new IllegalStateException("missing ConstraintType in switch statement?");
		}
		
		return label;
	}

	/*
	 * Get the current DebugState.
	 */
	public DebugState getCurrentDebugState() {
		return currentDebugState;
	}

	/*
	 * Determines the value of the shouldStep variable used in stepOver().
	 */
	public <U> boolean checkForChange(Object a, Object b) {
		// TODO: Check that a and b are of same type in method declaration
		// if we are at DebugLevel.NONE or the suspendCondition is met we stop stepping
		if (currentDebugLevel == DebugLevel.NONE || a != b || currentDebugState == DebugState.TERMINATED_TRUE || currentDebugState == DebugState.TERMINATED_FALSE) {
			// suspend application when one ore more of the two conditions are met
			suspendApplication();
			// gets assigned to the shouldStep variable in stepOver()
			return false;
		}
		
		// gets assigned to the shouldStep variable in stepOver()		
		return true;
	}
	
	/*
	 * Suspends the application by setting currentDebugState SUSPENDED.
	 */
	public void suspendApplication() {
		if (!isTerminated())
			setCurrentDebugState(DebugState.SUSPENDED);
	}

	/**
	 * checks the current debug state with the state provided by the parameters
	 * 
	 * @param expectedDebugLevel the expected {@link DebugLevel}
	 * @param expectedVariableIndex the expected variable index (see {@link ApplicationCondition#findMatch(int)}
	 * @param expectedConstraintType the expected constraint type (see {@link constraint#getClass()})
	 * @param expectedConstraintIndex the expected iteration index for multiple constraints of the same type (for example: {@link Variable#attributeConstraints}).
	 * 
	 * @return <code>true</code> if the state is the same, <code>false</code> otherwise
	 */
	public boolean checkDebugState(DebugLevel expectedDebugLevel, int expectedVariableIndex,
			EObject expectedValue, ConstraintType expectedConstraintType, int expectedConstraintIndex) {	
		
		return (expectedDebugLevel == currentDebugLevel
				&& expectedVariableIndex == currentVariableIndex
				&& (currentSlot == null? expectedValue == null : expectedValue == currentSlot.getValue())
				&& expectedConstraintType == currentConstraintType
				&& expectedConstraintIndex == currentConstraintIndex);

	}
	
	/**
	 * Checks the values of the match if one was found.
	 * @param expectedValues The values in the same order as the corresponding variables
	 * @return <code>true</code> if the values of the match are the same as the given values
	 */
	public boolean checkMatch(List<EObject> expectedValues) {
		for (int i = 0; i < variables.size(); i++) {
			DomainSlot slot = domainMap.get(variables.get(i));
			EObject actualValue = slot.getValue();
			try {
				if (expectedValues.get(i) != actualValue) {
					return false;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks the values of the match if one was found.
	 * @param expectedMatch The expected match
	 * @return <code>true</code> if the values of the match are the same as the given values
	 */
	public boolean checkMatch(Map<Variable, EObject> expectedValues) {
		for (Variable variable : expectedValues.keySet()) {
			EObject expectedValue = expectedValues.get(variable);
			EObject actualValue = domainMap.get(variable).getValue();
			if (expectedValue != actualValue) {
				return false;
			}
		}
		return true;
	}
	
	private void throwExceptionInvalidDebugLevel() {
		throw new IllegalStateException("currentDebugLevel is \"" + currentDebugLevel 
				+ "\", but has to be of the enum type DebugLevel");
	}
	
	@Override
	public String toString() {
		return "[debugLevel: " + currentDebugLevel
				+ ", variableIndex: " + currentVariableIndex
				+ ", value: " + retrieveValueLabel(currentSlot.getValue(), graph)
				+ ", constraintType: " + currentConstraintType
				+ ", " + retrieveConstraintLabel()
				+ "]";
	}
}
