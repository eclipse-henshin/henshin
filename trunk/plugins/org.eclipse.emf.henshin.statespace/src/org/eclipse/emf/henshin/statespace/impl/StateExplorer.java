package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.EqualityHelper;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpaceProperties;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.ObjectKeyHelper;
import org.eclipse.emf.henshin.statespace.util.ParameterUtil;

/**
 * Helper class for exploring states. This forms the bridge between a {@link StateSpaceManager}
 * and an {@link Engine}. This class tries to minimize the number of created 
 * short living objects to improve the performance. Instances of this class must not be 
 * used concurrently!
 * 
 * @author Christian Krause
 *
 */
public class StateExplorer {
	
	// State space manager:
	private final StateSpaceManager manager;
	
	// Cached state space:
	private final StateSpace stateSpace;
	
	// Cached equality helper:
	private final EqualityHelper equalityHelper;
	
	// Cached engine:
	private Engine engine;
	
	// Cached result:
	private final List<Transition> result;
	
	// Whether to use object keys:
	private final boolean useObjectKeys;
	
	// Cached rules:
	private final Rule[] rules;
	
	// Cached rule parameters:
	private final Map<Rule,List<Node>> ruleParameters;
	
	// Cached rule application:
	private final RuleApplication application;
	
	// Cached identity types:
	private final EList<EClass> identityTypes;
	
	// Cached post-processor:
	private final ModelPostProcessor postProcessor;
	
	// Cached flag determining whether to collect miossing roots:
	private final boolean collectMissingRoots;
	
	/**
	 * Default constructor.
	 */
	public StateExplorer(StateSpaceManager manager) {
		
		this.manager = manager;
		
		// Cache data:
		stateSpace = manager.getStateSpace();
		equalityHelper = stateSpace.getEqualityHelper();
		result = new ArrayList<Transition>();
		identityTypes = equalityHelper.getIdentityTypes();
		useObjectKeys = !identityTypes.isEmpty();
		rules = stateSpace.getRules().toArray(new Rule[0]);
		ruleParameters = new HashMap<Rule,List<Node>>();
		if (useObjectKeys) {
			for (Rule rule : rules) {
				try {
					ruleParameters.put(rule, ParameterUtil.getParameters(stateSpace, rule));
				} catch (StateSpaceException e) {
					throw new RuntimeException(e);
				}
			}
		}
		String collect = stateSpace.getProperties().get(StateSpaceProperties.COLLECT_MISSING_ROOTS);
		collectMissingRoots = (collect!=null) && ("true".equalsIgnoreCase(collect) || "yes".equalsIgnoreCase(collect));
		
		// Set-up the engine:
		engine = InterpreterFactory.INSTANCE.createEngine();
		engine.getOptions().put(Engine.OPTION_DETERMINISTIC, Boolean.TRUE); // really make sure it is deterministic

		// Create a rule application:
		application = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		
		// Post-processor:
		postProcessor = new ModelPostProcessor(stateSpace);
		
	}
	
	
	/**
	 * Explore a state without actually changing the state space.
	 * This method does not check if the state is explored already
	 * or whether any of the transitions or states exists already.
	 * @param state State to be explored.
	 * @throws StateSpaceException On explore errors.
	 */
	public List<Transition> doExplore(State state) throws StateSpaceException {
		
		// Clear the result:
		result.clear();
		
		// Get the state model and create an engine for it:
		Model model = manager.getModel(state);
		EGraph graph = model.getEGraph();
		
		// Apply all rules:
		for (int i=0; i<rules.length; i++) {

			// Get the parameters of the rule:
			List<Node> parameters = ruleParameters.get(rules[i]);

			// Iterate over all matches:
			int matchIndex = 0;
			for (Match match : engine.findMatches(rules[i], graph, null)) {
				
				// Transform the model:
				Model transformed = model.getCopy(match);
				application.setRule(rules[i]);
				application.setEGraph(transformed.getEGraph());
				application.setCompleteMatch(match);
				if (!application.execute(null)) {
					throw new StateSpaceException("Error applying rule \"" + rules[i].getName() + "\" to found match in state " + state.getIndex());
				}
				postProcessor.process(transformed);
				if (collectMissingRoots) {
					transformed.collectMissingRootObjects();
				}
				
				// Create a new state:
				State newState = StateSpaceFactory.eINSTANCE.createState();
				newState.setModel(transformed);
				
				// Update object keys if necessary:
				if (useObjectKeys) {
					transformed.updateObjectKeys(identityTypes);
					int[] objectKeys = transformed.getObjectKeys();
					newState.setObjectKeys(objectKeys);
					newState.setObjectCount(objectKeys.length);
				}
				
				// Now compute and set the hash code (after the node IDs have been updated!):
				int newHashCode = equalityHelper.hashCode(transformed);
				newState.setHashCode(newHashCode);
				newState.setDerivedFrom(state.getIndex());
				
				// Create a new transition:
				Transition newTransition = StateSpaceFactory.eINSTANCE.createTransition();
				newTransition.setRule(rules[i]);
				newTransition.setMatch(matchIndex);
				newTransition.setTarget(newState);
				
				// Set the parameters if necessary:
				if (useObjectKeys) {
					int[] params = new int[parameters.size()];
					for (int p=0; p<params.length; p++) {
						Node node = parameters.get(p);
						EObject matched = match.getNodeTarget(node);
						if (matched==null) {
							matched = application.getResultMatch().getNodeTarget(node);
						}
						int objectKey = transformed.getObjectKeysMap().get(matched);
						params[p] = ObjectKeyHelper.createObjectKey(
								matched.eClass(), 
								objectKey, 
								identityTypes);
					}
					newTransition.setParameterKeys(params);
					newTransition.setParameterCount(params.length);
				}
				
				// Remember the transition:
				result.add(newTransition);
				
				// Increase the match index:
				matchIndex++;
								
			}
		}
		
		return result;
		
	}

	/**
	 * Derive a model using a trace and a given start model.
	 * @param trace Trace.
	 * @param model Start model.
	 * @return The derived model.
	 * @throws StateSpaceException On errors.
	 */
	public Model deriveModel(Trace trace, Model model) throws StateSpaceException {
		
		// We need to copy the start model!!!
		model = model.getCopy(null);
		EGraph graph = model.getEGraph();
		application.setEGraph(graph);
		
		// Derive the model for the current state:
		for (Transition transition : trace) {
			
			// Find the right match:
			application.setRule(transition.getRule());
			int targetMatch = transition.getMatch();
			int currentMatch = 0;
			Match match = null;
			for (Match foundMatch : engine.findMatches(transition.getRule(), graph, null)) {
				if (currentMatch++ == targetMatch) {
					match = foundMatch;
				}
			}
			if (match==null) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());
			}
			
			// Apply the rule with the found match:
			application.setCompleteMatch(match);
			if (!application.execute(null)) {
				throw new StateSpaceException("Error deriving model");				
			}
			postProcessor.process(model);
			if (collectMissingRoots) {
				model.collectMissingRootObjects();
			}
			
			// Debug: Validate model if necessary:
			//int hashCode = getStateSpace().getEqualityHelper().hashCode(model);
			//if (transition.getTarget().getHashCode()!=hashCode) {
			//	throw new StateSpaceException("Error constructing model for state " +
			//			transition.getTarget().getIndex() + " in path " + trace);
			//}
			
		}

		// Set the object keys if necessary:
		if (useObjectKeys) {
			model.setObjectKeys(trace.getTarget().getObjectKeys());
		}

		// Done.
		return model;
		
	}
	
	/*
	 * Perform a sanity check for the exploration. For testing only.
	 * This check if doExplore() really yields equal results when invoked
	 * more than once on the same state.
	 */
/*	protected void checkEngineDeterminism(State state) throws StateSpaceException {
		
		// Explore the state without changing the state space:
		List<Transition> transitions = acquireTransitionList();
		doExplore(state, transitions);
		
		// Do it again and compare the results.
		for (int i=0; i<25; i++) {
			List<Transition> transitions2 = acquireTransitionList();
			doExplore(state, transitions2);
			if (transitions.size()!=transitions2.size()) {
				throw new StateSpaceException("Sanity check 1 failed!");
			}
			for (int j=0; j<transitions.size(); j++) {
				Transition t1 = transitions.get(j);
				Transition t2 = transitions2.get(j);
				if (t1.getRule()!=t2.getRule() || t1.getMatch()!=t2.getMatch()) {
					throw new StateSpaceException("Sanity check 2 failed!");
				}
				State s1 = t1.getTarget();
				State s2 = t2.getTarget();
				if (s1.getHashCode()!=s2.getHashCode()) {
					throw new StateSpaceException("Sanity check 3 failed!");
				}
				if (!getStateSpace().getEqualityHelper().equals(s1.getModel(),s2.getModel())) {
					throw new StateSpaceException("Sanity check 4 failed!");
				}
			}
		}
		
	}
*/	

}
