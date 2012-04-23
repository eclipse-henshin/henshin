package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.matching.util.TransformationOptions;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.ObjectKeyHelper;
import org.eclipse.emf.henshin.statespace.util.ParameterUtil;

/**
 * Helper class for exploring states. This forms the bridge between a {@link StateSpaceManager}
 * and an {@link InterpreterEngine}.
 * 
 * @author Christian Krause
 *
 */
public class StateExplorer {
	
	// State space manager:
	private StateSpaceManager manager;
	
	// Cached state space:
	private StateSpace stateSpace;
	
	// Cached engine:
	private EmfEngine engine;
	
	// Cached result:
	private List<Transition> result;
	
	// Whether to use object keys:
	private boolean useObjectKeys;
	
	// Cached rules:
	private Rule[] rules;
	
	// Cached identity types:
	private EList<EClass> identityTypes;
	
	// Cached post-processor:
	private ModelPostProcessor postProcessor;

	/**
	 * Default constructor.
	 */
	public StateExplorer(StateSpaceManager manager) {
		
		this.manager = manager;
		this.stateSpace = manager.getStateSpace();
		this.result = new ArrayList<Transition>();
		
		// Set-up the engine:
		engine = new EmfEngine();
		TransformationOptions options = new TransformationOptions();
		options.setDeterministic(true);		// really make sure it is deterministic
		engine.setOptions(options);
		
		postProcessor = new ModelPostProcessor(stateSpace);
		
		// Use object keys?
		identityTypes = stateSpace.getEqualityHelper().getIdentityTypes();
		useObjectKeys = !identityTypes.isEmpty();
		
		// Cache rules:
		rules = manager.getStateSpace().getRules().toArray(new Rule[0]);
		
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
		
		// Apply all rules:
		for (int ruleIndex=0; ruleIndex<rules.length; ruleIndex++) {

			// Initialize the match engine:
			engine.setEmfGraph(model.getEmfGraph());

			// Compute matches:
			RuleApplication app = new RuleApplication(engine, rules[ruleIndex]);
			List<Match> matches = app.findAllMatches();
			
			// Get the parameters of the rule:
			List<Node> parameters = useObjectKeys ? 
					ParameterUtil.getParameters(stateSpace, rules[ruleIndex]) : null;
			
			// Iterate over all matches:
			int matchCount = matches.size();
			for (int matchIndex=0; matchIndex<matchCount; matchIndex++) {
				
				// Transform the model:
				Match match = matches.get(matchIndex);
				Model transformed = model.getCopy(match);
				engine.setEmfGraph(transformed.getEmfGraph());
				app = new RuleApplication(engine, rules[ruleIndex]);
				app.setMatch(match);
				app.apply();
				postProcessor.process(transformed);
				transformed.collectMissingRootObjects();
				
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
				int newHashCode = stateSpace.getEqualityHelper().hashCode(transformed);
				newState.setHashCode(newHashCode);
				newState.setDerivedFrom(state.getIndex());
				
				// Create a new transition:
				Transition newTransition = StateSpaceFactory.eINSTANCE.createTransition();
				newTransition.setRule(rules[ruleIndex]);
				newTransition.setMatch(matchIndex);
				newTransition.setTarget(newState);
				
				// Set the parameters if necessary:
				if (useObjectKeys) {
					int[] params = new int[parameters.size()];
					for (int p=0; p<params.length; p++) {
						Node node = parameters.get(p);
						EObject matched = match.getNodeMapping().get(node);
						if (matched==null) {
							matched = app.getComatch().getNodeMapping().get(node);
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
								
			}
		}
		
		return result;
		
	}

	/**
	 * Derive a model using a trace and a given start model.
	 * @param trace Trace.
	 * @param start Start model.
	 * @return The derived model.
	 * @throws StateSpaceException On errors.
	 */
	public Model deriveModel(Trace trace, Model start) throws StateSpaceException {
		
		// We copy the start model:
		Model model = start.getCopy(null);
		engine.setEmfGraph(model.getEmfGraph());
		
		// Derive the model for the current state:
		for (Transition transition : trace) {
			
			RuleApplication application = new RuleApplication(engine, transition.getRule());
			List<Match> matches = application.findAllMatches();
			if (matches.size()<=transition.getMatch()) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());
			}
			
			// Apply the rule with the found match:
			Match match = matches.get(transition.getMatch());
			application.setMatch(match);
			application.apply();
			postProcessor.process(model);
			model.collectMissingRootObjects();
			
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
