package org.eclipse.emf.henshin.interpreter.matching.conditions;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.StringJoiner;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugThread;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugValue;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugVariable;
import org.eclipse.emf.henshin.interpreter.debug.HenshinStackFrame;
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
	private ConstraintType currentConstraintType;
	private int currentConstraintIndex;
	
	private Variable currentVariable;
	private DomainSlot currentSlot;
		
	private IStackFrame[] stackFrames;

	private Observer matchObserver;

	public DebugApplicationCondition(HenshinDebugTarget debugTarget, List<Variable> variables,
			Map<Variable, DomainSlot> domainMap, EGraph graph, IFormula formula, Observer matchObserver) {
		super(graph, domainMap);
		this.debugTarget = debugTarget;
		this.variables = variables;
		this.formula = formula;
		this.matchObserver = matchObserver;
		
		currentDebugLevel = DebugLevel.NONE;
		currentVariableIndex = -1;
		currentConstraintType = ConstraintType.NONE;
		currentConstraintIndex = -1;
		currentDebugState = DebugState.SUSPENDED;
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
		if (currentVariableIndex==variables.size()) {
			
			// Final variable re-checks:
			for (Variable variable : variables) {
				if (variable.requiresFinalCheck) {
					DomainSlot slot = domainMap.get(variable);
					if (!slot.recheck(variable, domainMap)) {
						// recheck turned out invalid --> terminate (no match found)
						updateDebugState(DebugLevel.NONE, -1, ConstraintType.NONE, -1);
						currentDebugState = DebugState.TERMINATED_FALSE;
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
				currentDebugState = DebugState.TERMINATED_TRUE;
				
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
			currentDebugState = DebugState.TERMINATED_FALSE;
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
			throw new IllegalStateException(
					"tried to initialize slot with empty domain" + toString());
		}
		
		currentDebugState = DebugState.SUSPENDED;
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
	private void tryNextConstraintType() {
		//are there any constraint types (with constraints) left that we have to check?
		do {
			currentConstraintType = currentConstraintType.next();
			if (currentTypeHasConstraints()) {
				// continue with that constraint type
				updateDebugState(DebugLevel.CONSTRAINT_TYPE, currentVariableIndex, currentConstraintType, -1);
				currentDebugState = DebugState.SUSPENDED;
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
			currentDebugState = DebugState.SUSPENDED;
			if (debugTarget != null) {			
				debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
			}
		} else {
			tryLowerIndexVariable();
		}
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
		currentDebugState = DebugState.TERMINATED_FALSE;
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
	private void updateDebugState(
			DebugLevel newDebugLevel, 
			int newVariableIndex, 
			ConstraintType newConstraintType, 
			int newConstraintIndex) {
		
		currentDebugLevel = newDebugLevel;
		currentVariableIndex = newVariableIndex;
		currentConstraintType = newConstraintType;
		currentConstraintIndex = newConstraintIndex;
	}

	public boolean canResume() {
		return isSuspended();
	}

	public boolean canSuspend() {
        return !isTerminated() && !isSuspended();
	}

	public boolean isSuspended() {
		return currentDebugState.equals(DebugState.SUSPENDED);
	}

	public void resume() throws DebugException {
		synchronized (this) {
//            requestedDebugState = DebugState.RUNNING;
        }
	}

	public void suspend() throws DebugException {
		 synchronized (this) {
//	            requestedDebugState = DebugState.SUSPENDED;
	        }		
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
        return currentDebugState.equals(DebugState.STEPPING);
	}

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
					throw new IllegalStateException("stepInto called on Variable level, but no values left: "
						+ toString());
				}
				
				// update the debug state (current index does not change)
				updateDebugState(DebugLevel.VALUE, currentVariableIndex, ConstraintType.NONE, -1);
				currentDebugState = DebugState.SUSPENDED;
				break;
			case VALUE:
				// go to the "type" constraint type as it is always the first constraint. 
				updateDebugState(DebugLevel.CONSTRAINT_TYPE, currentVariableIndex, ConstraintType.TYPE, -1);
				currentDebugState = DebugState.SUSPENDED;
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
				currentDebugState = DebugState.SUSPENDED;
				break;
	
			case CONSTRAINT:
				// identical to stepOver
				stepOver();
				break;

			default:
				throwExceptionInvalidDebugLevel();
		}
		
		if (debugTarget != null) {			
			debugTarget.fireSuspendEvent(DebugEvent.STEP_END);
		}
		
	}

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
				
				boolean valid = false;
				while (!valid) {
					valid = currentSlot.instantiate(currentVariable, domainMap, graph);
					if (valid) {
						
						// no recursive call, we just want to update the state
						updateDebugState(DebugLevel.VARIABLE, currentVariableIndex + 1, ConstraintType.NONE, -1);
						initNextVariable();
						return;
					}
					if (!valid) {
						currentSlot.unlock(currentVariable);
						if (!currentSlot.instantiationPossible()) {
							tryLowerIndexVariable();
							return;
						}
					}
				}
				
				// Found a match.
				currentDebugState = DebugState.SUSPENDED;
				break;

			case VALUE:
				// try to match the current value
				valid = currentSlot.instantiate(currentVariable, domainMap, graph);
				if (valid) {
					// go to next variable
					updateDebugState(DebugLevel.VARIABLE, currentVariableIndex + 1, ConstraintType.NONE, -1);
					initNextVariable();
					return;
				} 
				if (!valid) {
					// try to go to the next value
					tryNextValue();
					return;
				}
				break;

			case CONSTRAINT_TYPE:
				// in this state there should always be a constraint left
				if (!currentTypeHasConstraints()) {
					throw new IllegalStateException("called stepOver for constraint type \"" 
							+ currentConstraintType + "\", but this type has no constraint and should have been skipped");
				}
				
				int maxCurrConstrIndex = maxCurrentConstraintIndex();
				
				// the current constraint type has constraints => check them
				for (currentConstraintIndex = 0; currentConstraintIndex <= maxCurrConstrIndex; currentConstraintIndex++) {
					// if one is invalid, we know this value will not match => try to go to the next value
					if (!checkCurrentConstraint()) {
						tryNextValue();
						return;
					}
				}
				
				// if all constraints of this type are valid, see if there is a constraint type left that we have to check
				tryNextConstraintType();
				break;

			case CONSTRAINT:
				if (currentConstraintIndex > maxCurrentConstraintIndex()) {
					throw new IllegalStateException("called stepOver for constraint type \"" + currentConstraintType 
							+ "\", but this type has no unchecked constraint left and should have been skipped");
				}
				boolean constraintIsValid = checkCurrentConstraint();
				if (constraintIsValid) {
					// are there any constraints of the current type left that we have to check?
					if (currentConstraintIndex < maxCurrentConstraintIndex()) {
						// go to the next constraint of the current type
						updateDebugState(DebugLevel.CONSTRAINT, currentVariableIndex, currentConstraintType, currentConstraintIndex + 1);
						currentDebugState = DebugState.SUSPENDED;
						break;
					}
					// else: see if there is a constraint type left that we have to check
					tryNextConstraintType();
					break;
					
				} 
				if (!constraintIsValid) {
					// try to go to the next value
					tryNextValue();
					return;
				}
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
				// Do the same as the variable step over, but without unlocking the slot. This way, DomainSlot.initialize() and
				// DomainSlot.setValueAndLock() get called a second time, but the calls have no effect for a locked and initialized slot
				boolean valid = false;
				while (!valid) {
					valid = currentSlot.instantiate(currentVariable, domainMap, graph);
					if (valid) {
						
						// no recursive call, we just want to update the state
						updateDebugState(DebugLevel.VARIABLE, currentVariableIndex + 1, ConstraintType.NONE, -1);
						initNextVariable();
						return;
					}
					if (!valid) {
						currentSlot.unlock(currentVariable);
						if (!currentSlot.instantiationPossible()) {
							tryLowerIndexVariable();
							return;
						}
					}
				}
				
				// Found a match.
				currentDebugState = DebugState.SUSPENDED;
				break;
				
			case CONSTRAINT_TYPE:
				// in this state there should always be a constraint left
				if (!currentTypeHasConstraints()) {
					throw new IllegalStateException("called stepOver for constraint type \"" 
							+ currentConstraintType + "\", but this type has no constraint and should have been skipped");
				}
				
				while (! currentConstraintType.isLast()) {
					// check all the constraints of this type
					for (currentConstraintIndex = 0; currentConstraintIndex <= maxCurrentConstraintIndex(); currentConstraintIndex++) {
						if (!checkCurrentConstraint()) {
							// constraint is invalid, so we know this value will not match => try to go to the next value
							tryNextValue();
							return;
						}
					}
					
					// next iteration with the next type
					currentConstraintType = currentConstraintType.next();
	
				}
				// we checked all constraints of all types, and no constraint was invalid
				// => go to next variable (index++)
				updateDebugState(DebugLevel.VARIABLE, currentVariableIndex + 1, ConstraintType.NONE, -1);
				initNextVariable();
				return;

			case CONSTRAINT:
				// check for illegal state
				if (currentConstraintIndex > maxCurrentConstraintIndex()) {
					throw new IllegalStateException("called stepOver for constraint type \"" + currentConstraintType 
							+ "\", but this type has no unchecked constraint left and should have been skipped");
				}
				
				// check the remaining constraints of this type
				while (currentConstraintIndex <= maxCurrentConstraintIndex()) {
					// if one is invalid, we know this value will not match => try to go to the next value
					if (!checkCurrentConstraint()) {
						tryNextValue();
						return;
					}
					currentConstraintIndex++;
				}
				
				// every constraint of this type is valid => go to next constraint type / variable
				tryNextConstraintType();
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
		return currentDebugState.equals(DebugState.TERMINATED_FALSE)
				|| currentDebugState.equals(DebugState.TERMINATED_TRUE);
	}

	public void terminate() throws DebugException {
		updateDebugState(DebugLevel.NONE, -1, ConstraintType.NONE, -1);
		currentDebugState = DebugState.TERMINATED_FALSE;
		if (debugTarget != null) {			
			debugTarget.fireTerminateEvent();
		}		
	}

	public String getName() throws DebugException {
		// if the matching has terminated successfully, display the match to the user
		if (currentDebugState == DebugState.TERMINATED_TRUE) {
			return "["+variables.size() + "/" + variables.size() + " variables matched]";
		} else if (currentDebugState == DebugState.TERMINATED_FALSE) {
			return "[no match found]";
		}
		
		// if the matching is still in process, display the number of matched variables
		return currentVariableIndex + "/" + variables.size() + " variables matched";
	}

	public IBreakpoint[] getBreakpoints() {
		return new IBreakpoint[0];
	}
	
	/**
	 * returns the current array of stackFrames, each containing variables.
	 * NOTE: maybe we could store the stackframes that do not change
	 * @return
	 */
	public IStackFrame[] getStackFrames(HenshinDebugThread debugThread) {
		
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
							var.typeConstraint.type.getName());			
			
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
							new HenshinDebugValue(
									debugTarget,
									graph,
									slot.getDomain(),
									declaredType));
			
			IVariable[] stackVariables = new IVariable[]{debugVariable, debugDomain};
			
			stackFrames[i] = new HenshinStackFrame(debugThread, stackVariables, labelBuilder.toString(), i);
			
			// clear the string builder
			labelBuilder.setLength(0);
		}

		// then add the extra stackframes for...
		// ...value
		if (currentDebugLevel.ordinal() > 1) {
			
			HenshinDebugVariable debugValue =
					new HenshinDebugVariable(
							debugTarget,
							"Value",
							new HenshinDebugValue(
									debugTarget,
									graph,
									currentSlot.getValue(),
									currentVariable.typeConstraint.type.getName()));
			
			HenshinDebugVariable debugDomain = 
					new HenshinDebugVariable(
							debugTarget,
							"Domain",
							new HenshinDebugValue(
									debugTarget,
									graph,
									currentSlot.getDomain(),
									currentVariable.typeConstraint.type.getName()));
			
			IVariable[] stackVariables = new IVariable[]{debugValue, debugDomain};
			String label = "Value: '" + retrieveValueLabel(currentSlot.getValue(), graph) + "'";
			
			stackFrames[currentVariableIndex+1] = new HenshinStackFrame(debugThread, stackVariables, label, currentVariableIndex+1);
			
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
									new HenshinDebugValue(
											debugTarget,
											graph,
											Integer.valueOf(currentConstraintIndex),
											int.class.getName()));
					
					label = retrieveConstraintLabel();

					HenshinDebugVariable debugConstraint = 
							new HenshinDebugVariable(debugTarget, "Constraint", label, currentConstraintType.toString() + " Constraint");
								
					stackFrames[currentVariableIndex+3] = new HenshinStackFrame(debugThread, new IVariable[]{debugConstrIndex, debugConstraint}, label, currentVariableIndex+3);
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
	private String retrieveConstraintLabel() {
		String label = "Constraint " + currentConstraintIndex;
		
		if (currentDebugLevel != DebugLevel.CONSTRAINT) {
			return label;
		}
		
		label += ": '";
		
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
			String attValue = String.valueOf(currentSlot.getValue().eGet(currentSlot.getValue().eClass().getEStructuralFeature(attName)));
			label += attValue + " = " + paramValue + " (" + attName + " = " + paramName + ")";
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
		case NONE:
			throw new IllegalStateException("trying to find label for constraint with currentConstraintType NONE");
		default:
			throw new IllegalStateException("missing ConstraintType in switch statement?");
		}
		
		label += "'";
		return label;
	}


	public DebugState getCurrentDebugState() {
		return currentDebugState;
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
