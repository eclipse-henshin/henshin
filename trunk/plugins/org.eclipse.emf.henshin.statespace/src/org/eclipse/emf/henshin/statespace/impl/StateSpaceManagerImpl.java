/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.common.util.TransformationOptions;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.equality.HashCodeMap;
import org.eclipse.emf.henshin.statespace.properties.ParametersPropertiesManager;
import org.eclipse.emf.henshin.statespace.util.StateSpaceMonitor;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;
import org.eclipse.emf.henshin.statespace.util.UniversalCache;

/**
 * Default state space manager implementation.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManagerImpl extends AbstractStateSpaceManagerWithStateDistance {
	
	// State model cache:
	private final Map<State,Model> modelCache = 
			Collections.synchronizedMap(new UniversalCache<State,Model>());
	
	// Hash code tree maps:
	private final Map<Model,HashCodeMap> codesCache = 
			Collections.synchronizedMap(new UniversalCache<Model,HashCodeMap>());
	
	// Transformation engines:
	private final Stack<EmfEngine> engines = new Stack<EmfEngine>();
	
	// A lock used when exploring states:
	private final Object explorerLock = new Object();
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceManagerImpl(StateSpace stateSpace) {
		super(stateSpace);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#isOpen(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	protected boolean isOpen(State state) throws StateSpaceException {
		
		// Do a dry exploration of the state:
		List<Transition> transitions = doExplore(state);
		Set<Transition> matched = new HashSet<Transition>();
		
		for (Transition current : transitions) {
			
			// Find the corresponding target state in the state space.
			State generated = current.getTarget();
			State target = getState(generated.getModel(), generated.getHashCode());
			if (target==null) {
				return true;
			}
			
			// Find the corresponding outgoing transition:
			Transition transition = findTransition(state, target, current.getRule(), current.getParameterIDs());
			if (transition==null) {
				return true;
			}
			matched.add(transition);
			
		}
		
		// Check if there are extra transitions (illegal):
		if (!matched.containsAll(state.getOutgoing())) {
			throw new StateSpaceException("Illegal transition in state " + state.getIndex());
		}
		
		// State is not open:
		return false;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getModel(org.eclipse.emf.henshin.statespace.State)
	 */
	public Model getModel(State state) throws StateSpaceException {
		
		// Model already set?
		if (state.getModel()!=null) {
			return state.getModel();
		}
		
		// Cached?
		Model cached = modelCache.get(state);
		if (cached!=null) {
			return cached;
		}
		
		// Find a predecessor state that has a model:
		StateSpaceSearch search = new StateSpaceSearch() {
			@Override
			protected boolean shouldStop(State current, Trace trace) {
				return current.getModel()!=null || modelCache.get(current)!=null;
			}
		};
		boolean found = search.depthFirst(state, true);
		if (!found) {
			throw new StateSpaceException("Unable to derive state model for state " + state.getIndex());
		}
		
		// Derive the current model:
		Model start = search.getCurrentState().getModel();
		if (start==null) start = modelCache.get(search.getCurrentState());
		Model model = deriveModel(start, search.getTrace());
			
		// Update the cached hash code maps if necessary:
		if (getStateSpace().getEqualityHelper().isGraphEquality()) {
			hashCode(model);
		}
		
		// Always add it to the cache (is maintained automatically):
		modelCache.put(state, model);

		// Done.
		return model;
		
	}
	
	/*
	 * Store a model in a state.
	 */
	private void storeModel(State state, Model model) {
		
		// Do not override initial state models!!!
		if (state.isInitial()) return;
		
		// Number of states: rounded up for more stability:
		int states = getStateSpace().getStates().size();
		states = states - (states % 1000) + 1000;			// always greater than 1000
		
		// Decide whether the current model should be kept in memory.
		int stored = (int) (Math.log10(states) * 1.5);		// ranges between 4 and 9-10
		int index = state.getIndex() + 1;					// always greater or equal 1
		
		//System.out.println(stored);
		
		if (index % stored != 0) {
			model = null;
		}
		state.setModel(model);
		
	}
	
	/*
	 * Derive a model. The path is assumed to be non-empty.
	 */
	private Model deriveModel(Model start, Trace path) throws StateSpaceException {

		// We need a transformation engine first:
		EmfEngine engine = acquireEngine();

		// We copy the model:
		Model model = start.getCopy(null);
		
		// Derive the model for the current state:
		for (Transition transition : path) {
			
			Rule rule = transition.getRule();
			if (rule==null || !getStateSpace().getRules().contains(rule)) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());
			}
			
			RuleApplication application = createRuleApplication(model, rule, engine);
			List<Match> matches = application.findAllMatches();
			if (matches.size()<=transition.getMatch()) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());				
			}
			
			// Apply the rule with the found match:
			Match match = matches.get(transition.getMatch());
			application.setMatch(match);
			application.apply();
			
			// Store model:
			storeModel(transition.getTarget(), model.getCopy(null));
			
		}
		
		// We can release the engine already:
		releaseEngine(engine);
		
		// Decide whether the model in the start state should be kept:
		storeModel(path.getSource(),start);
		
		return model;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#explore(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<Transition> exploreState(State state, boolean generateLocation) throws StateSpaceException {
		
		// Check if we exceeded the maximum state distance:
		int maxStateDistance = getStateSpace().getMaxStateDistance();
		if (maxStateDistance>=0 && getStateDistance(state)>=maxStateDistance) {
			return Collections.emptyList();
		}
		
		// For testing only:
		// performExplorationSanityCheck(state);
		
		// Explore the state without changing the state space.
		// This can take some time. So no lock here.
		List<Transition> transitions = doExplore(state);
		
		// Initialize the result.
		int newStates = 0;
		List<Transition> result = new ArrayList<Transition>(transitions.size());

		// For performance we use a monitor to detect concurrently made changes.
		StateSpaceMonitor monitor = new StateSpaceMonitor(getStateSpace());
		
		// START OF EXPLORER LOCK
		synchronized (explorerLock) {
			monitor.setActive(true);	// Activate the monitor.
		}
		// END OF EXPLORER LOCK
		
		// No check which states / transitions need to be added.
		for (Transition current : transitions) {
			
			// Transition label details:
			Rule rule = current.getRule();
			int match = current.getMatch();
			int[] parameters = current.getParameterIDs();
			
			// Get the hash and model of the new target state:
			int hashCode = current.getTarget().getHashCode();
			Model transformed = current.getTarget().getModel();
			
			// The target state and some of its properties:
			State target;
			boolean newState = false;
			int[] location = generateLocation ? shiftedLocation(state, newStates++) : null;
			
			// Try to find an equivalent state. This can take some time. Hence no lock here.
			target = getState(transformed, hashCode);
			
			// START OF EXPLORER LOCK
			synchronized (explorerLock) {
				
				if (target==null) {
					// Check if an equivalent state has been added in the meantime.
					target = findState(transformed, hashCode, monitor.getAddedStates());
				} else {
					// Check if the found state has been removed in the meantime.
					if (monitor.getRemovedStates().contains(target)) {
						target = null;
					}
				}
				
				// Ok, now we can create a new state if necessary.
				if (target==null) {
					target = createOpenState(transformed, hashCode, location);
					newState = true;
				}
				
			}
			// END OF EXPLORER LOCK

			// Find or create the transition.
			Transition transition = findTransition(state, target, rule, parameters);
			if (transition==null) {
				transition = createTransition(state, target, rule, match, parameters);
				result.add(transition);
			}
			
			// Now that the transition is there, we can decide whether to store the model.
			if (newState) {
				storeModel(target, transformed);
			}
			
		}
		
		// START OF EXPLORER LOCK
		synchronized (explorerLock) {
			// Deactivate the monitor so that it can be garbage collected.
			monitor.setActive(false);
		}
		// END OF EXPLORER LOCK

			
		// Mark the state as closed:
		setOpen(state, false);
				
		// Done: return the new transitions.
		return result;
		
	}
	
	/**
	 * Explore a state without actually changing the state space.
	 * This method does not check if the state is explored already
	 * or whether any of the transitions or states exists already.
	 * @param state State to be explored.
	 * @return List of outgoing transition.
	 * @throws StateSpaceException On explore errors.
	 */
	protected List<Transition> doExplore(State state) throws StateSpaceException {
		
		// We need a transformation engine first:
		EmfEngine engine = acquireEngine();
		
		// Get the state model:
		Model model = getModel(state);
		
		// Get some important state space parameters:
		boolean ignoreNodeIDs = getStateSpace().getEqualityHelper().isIgnoreNodeIDs();
		
		// List of explored transitions.
		List<Transition> transitions = new ArrayList<Transition>();
		
		// Apply all rules:
		for (Rule rule : getStateSpace().getRules()) {
			
			// Compute matches:
			RuleApplication application = createRuleApplication(model, rule, engine);
			List<Match> matches = application.findAllMatches();
			
			// Get the parameters of the rule:
			List<Node> parameters = ignoreNodeIDs ? 
					null : ParametersPropertiesManager.getParameters(getStateSpace(), rule);
			
			// Iterate over all matches:
			for (int i=0; i<matches.size(); i++) {
				
				// Transform the model:
				Match match = matches.get(i);
				Model transformed = model.getCopy(match);
				EmfGraph graph = createEmfGraph(transformed);
				application = createRuleApplication(graph, rule, engine);
				application.setMatch(match);
				application.apply();
				
				// Collect newly created root objects:
				Resource resource = transformed.getResource();
				for (EObject root : graph.getRootObjects()) {
					if (!resource.getContents().contains(root)) {
						resource.getContents().add(root);
					}
				}
				
				// Create a new state:
				State newState = StateSpaceFactory.eINSTANCE.createState();
				newState.setModel(transformed);
				
				// Update node IDs if necessary:
				if (!ignoreNodeIDs) {
					transformed.updateNodeIDs();
					int[] nodeIDs = transformed.getNodeIDs();
					newState.setNodeIDs(nodeIDs);
					newState.setNodeCount(nodeIDs.length);
				}
				
				// Now compute and set the hash code (after the node IDs have been updated!):
				newState.setHashCode(hashCode(transformed));
				
				// Create a new transition:
				Transition newTransition = StateSpaceFactory.eINSTANCE.createTransition();
				newTransition.setRule(rule);
				newTransition.setMatch(i);
				newTransition.setTarget(newState);
				if (!ignoreNodeIDs) {
					int[] paramIDs = new int[parameters.size()];
					for (int p=0; p<paramIDs.length; p++) {
						Node node = parameters.get(p);
						EObject matched = match.getNodeMapping().get(node);
						if (matched==null) {
							matched = application.getComatch().getNodeMapping().get(node);
						}
						paramIDs[p] = transformed.getNodeIDsMap().get(matched);
					}
					newTransition.setParameterIDs(paramIDs);
					newTransition.setParameterCount(paramIDs.length);
				}
				
				// Remember the transition:
				transitions.add(newTransition);
				
			}
		}
		
		// Now we can release the engine again:
		releaseEngine(engine);
		
		// And we are done:
		return transitions;

	}
	
	/*
	 * Helper method for finding a state in a list.
	 */
	private State findState(Model model, int hashCode, List<State> states) throws StateSpaceException {
		for (State state : states) {
			if (hashCode==state.getHashCode() && equals(model,getModel(state))) {
				return state;
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpaceIndexImpl#hashCode(org.eclipse.emf.henshin.statespace.Model)
	 */
	@Override
	protected int hashCode(Model model) {
		if (getStateSpace().getEqualityHelper().isGraphEquality()) {
			HashCodeMap map = new HashCodeMap();
			int hashcode = getStateSpace().getEqualityHelper().hashCode(model, map);
			codesCache.put(model, map);
			return hashcode;			
		} else {
			return getStateSpace().getEqualityHelper().hashCode(model, null);
		}
	}	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpaceIndexImpl#equals(org.eclipse.emf.henshin.statespace.Model, org.eclipse.emf.henshin.statespace.Model)
	 */
	@Override
	protected boolean equals(Model model1, Model model2) {
		// We definitely need the hash code maps if we use graph equality:
		if (getStateSpace().getEqualityHelper().isGraphEquality()) {
			HashCodeMap map1 = codesCache.get(model1);
			if (map1==null) {
				map1 = new HashCodeMap();
				getStateSpace().getEqualityHelper().hashCode(model1, map1);
				codesCache.put(model1, map1);
			}
			HashCodeMap map2 = codesCache.get(model2);
			if (map2==null) {
				map2 = new HashCodeMap();
				getStateSpace().getEqualityHelper().hashCode(model2, map2);
				codesCache.put(model2, map2);
			}
			return getStateSpace().getEqualityHelper().equals(model1, map1, model2, map2);
		} else {
			return super.equals(model1, model2);
		}
	}

	/*
	 * Create a new EmfGraph.
	 */
	private static EmfGraph createEmfGraph(Model model) {
		EmfGraph graph = new EmfGraph();
		for (EObject root : model.getResource().getContents()) {
			graph.addRoot(root);
		}
		return graph;
	}

	/*
	 * Create a new RuleApplication.
	 */
	private static RuleApplication createRuleApplication(EmfGraph graph, Rule rule, EmfEngine engine) {
		engine.setEmfGraph(graph);
		return new RuleApplication(engine, rule);
	}

	/*
	 * Create a new RuleApplication.
	 */
	private static RuleApplication createRuleApplication(Model model, Rule rule, EmfEngine engine) {
		EmfGraph graph = createEmfGraph(model);
		return createRuleApplication(graph, rule, engine);
	}

	/*
	 * Acquire a transformation engine.
	 */
	private EmfEngine acquireEngine() {
		synchronized (engines) {
			if (!engines.isEmpty()) {
				return engines.pop();
			} else {
				EmfEngine engine = new EmfEngine();
				TransformationOptions options = new TransformationOptions();
				options.setDeterministic(true);		// really make sure it is deterministic
				engine.setOptions(options);
				return engine;
			}
		}
	}

	/*
	 * Release a transformation engine again.
	 */
	private void releaseEngine(EmfEngine engine) {
		synchronized (engines) {
			engines.push(engine);
		}
	}
	
	/*
	 * Create a shifted location.
	 */
	private static int[] shiftedLocation(State base, int index) {
		int[] location = base.getLocation();
		double angle = (Math.PI * index * 0.17d);
		location[0] += 60 * Math.cos(angle);
		location[1] += 60 * Math.sin(angle);
		return location;
	}
	
	/**
	 * Clear the state model cache. Should be done every now and then.
	 */
	public void clearStateModelCache() {
		int index = 0;
		for (State state : getStateSpace().getStates()) {
			// We leave a small rest amount of the state models.
			if ((++index % 10 != 0) && !state.isInitial()) {
				state.setModel(null);
			}
		}
		modelCache.clear();
		codesCache.clear();
		System.gc();
	}

	/*
	 * Perform a sanity check for the exploration. For testing only.
	 * This check if doExplore() really yields equal results when invoked
	 * more than once on the same state.
	 */
	protected void performExplorationSanityCheck(State state) throws StateSpaceException {
		
		// Explore the state without changing the state space:
		List<Transition> transitions = doExplore(state);
		
		// Do it again and compare the results.
		for (int i=0; i<25; i++) {
			List<Transition> transitions2 = doExplore(state);
			if (transitions.size()!=transitions2.size()) {
				markTainted(); throw new StateSpaceException("Sanity check 1 failed!");
			}
			for (int j=0; j<transitions.size(); j++) {
				Transition t1 = transitions.get(j);
				Transition t2 = transitions2.get(j);
				if (t1.getRule()!=t2.getRule() || t1.getMatch()!=t2.getMatch()) {
					markTainted(); throw new StateSpaceException("Sanity check 2 failed!");
				}
				if (!equals(t1.getTarget().getModel(),t2.getTarget().getModel())) {
					markTainted(); throw new StateSpaceException("Sanity check 3 failed!");
				}
				if (t1.getTarget().getHashCode()!=t2.getTarget().getHashCode()) {
					markTainted(); throw new StateSpaceException("Sanity check 4 failed!");
				}
			}
		}
		
	}
	
}
