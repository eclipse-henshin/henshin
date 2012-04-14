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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.matching.util.TransformationOptions;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.ObjectKeyHelper;
import org.eclipse.emf.henshin.statespace.util.ParameterUtil;
import org.eclipse.emf.henshin.statespace.util.StateSpaceMonitor;

/**
 * Default state space manager implementation.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManagerImpl extends AbstractStateSpaceManager {
	
	// State model cache:
	private final Map<State,Model> stateModelCache = 
			Collections.synchronizedMap(new Cache<State,Model>());
		
	// Transformation engines:
	private final Stack<EmfEngine> engines = new Stack<EmfEngine>();
	
	// A lock used when exploring states:
	private final Object explorerLock = new Object();
	
	// JavaScript post-processor:
	private PostProcessor postProcessor;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceManagerImpl(StateSpace stateSpace) {
		super(stateSpace);
		postProcessor = new PostProcessor();
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
			Transition transition = findTransition(state, target, current.getRule(), current.getMatch(), current.getParameterKeys());
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
		
		// Model cached?
		Model model = getCachedModel(state);
		
		// Otherwise derive and store the model:
		if (model==null) {	
			model = deriveModel(state, false);
			storeModel(state, model);
		}
		else if (StateSpaceDebug.VALIDATE_STATES) {
			storeModel(state, model);
		}
		
		// Done.
		return model;
		
	}
	
	/*
	 * Try to get a cached model for a state.
	 */
	private Model getCachedModel(State state) {
		Model model = state.getModel();
		if (model!=null) {
			return model;
		}
		return stateModelCache.get(state);
	}
	
	/*
	 * Store or discard a state model probabilistically.
	 */
	private void storeModel(State state, Model model) throws StateSpaceException {
		
		// Never lose initial state models!!!
		if (state.isInitial()) return;
		
		// Debug:
		if (StateSpaceDebug.VALIDATE_STATES) {
			
			// Compare real hash code with state hash code: 
			Model derived = deriveModel(state, true);
			int hashCode = getStateSpace().getEqualityHelper().hashCode(model);
			int hashCode2 = getStateSpace().getEqualityHelper().hashCode(derived);
			
			if (hashCode!=state.getHashCode() || hashCode!=hashCode2) {
				throw new StateSpaceException("Attempted to store an invalid model for state " + state.getIndex());
			}
			
		}
		
		// Decide whether to cache the model:
		if (StateSpaceDebug.NORMAL_CACHING) {
			
			// Add the model to the cache:
			stateModelCache.put(state, model);

			// Number of states and index of the current state:
			int states = getStateSpace().getStateCount();
			int index = state.getIndex() + 1;	// always greater or equal 1
			
			//      States:  Stored:
			//     < 10,000     100%
			//    >= 10,000      50%
			//   >= 100,000      33%
			// >= 1,000,000      25%
			int threshold = 10000;
			int stored = 1;
			while (states>=threshold) {
				threshold *= 10;
				stored++;
			}
			
			// Unset the model by chance:
			if (index % stored != 0) {
				model = null;
			}
			
		} else if (StateSpaceDebug.NO_CACHING) {
			model = null;
		}
		
		// Update the state:
		state.setModel(model);
		
	}
	
	/*
	 * Derive a model. The path is assumed to be non-empty.
	 */
	private Model deriveModel(State state, boolean startFromInitial) throws StateSpaceException {
		
		//System.out.println("Deriving model for state " + state.getIndex() + " (fromInital: " + startFromInitial + ")");
		
		// Find a path from one of the states predecessors:
		Trace trace = new Trace();
		State source = state;
		State target;
		Model start = null;
		List<State> states = getStateSpace().getStates();
		try {
			while (start==null) {
				target = source;
				source = states.get(target.getDerivedFrom());
				trace.addFirst(findTransition(source, target, null, -1, null));
				start = getCachedModel(source);
				if (startFromInitial && !source.isInitial()) {
					start = null;
				}
			}
		} catch (Throwable t) {
			throw new StateSpaceException("Error deriving model for " + state, t);
		}

		// We copy the start model and create an engine for it:
		Model model = start.getCopy(null);
		EmfEngine engine = acquireEngine();
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
			
			// Validate model if necessary:
			if (StateSpaceDebug.VALIDATE_STATES) {
				int hashCode = getStateSpace().getEqualityHelper().hashCode(model);
				if (transition.getTarget().getHashCode()!=hashCode) {
					throw new StateSpaceException("Error constructing model for state " +
						transition.getTarget().getIndex() + " in path " + trace);
				}
			}
			
		}

		// Set the object keys if necessary:
		if (!getStateSpace().getEqualityHelper().getIdentityTypes().isEmpty()) {
			model.setObjectKeys(trace.getTarget().getObjectKeys());
		}

		// We can release the engine again:
		releaseEngine(engine);
		
		// Done.
		return model;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#exploreState(org.eclipse.emf.henshin.statespace.State, boolean)
	 */
	@Override
	protected List<State> exploreState(State state, boolean generateLocation) throws StateSpaceException {
		
		// Check if we exceeded the maximum state distance:
		int maxStateDistance = getStateSpace().getMaxStateDistance();
		if (maxStateDistance>=0 && getStateDistance(state)>=maxStateDistance) {
			return Collections.emptyList();
		}
		
		// For debugging purposes:
		if (StateSpaceDebug.CHECK_ENGINE_DETERMINISM) {
			checkEngineDeterminism(state);
		}
		
		// Explore the state without changing the state space.
		// This can take some time. So no lock here.
		List<Transition> transitions = doExplore(state);
		
		// Initialize the result.
		int newStates = 0;
		List<State> result = new ArrayList<State>(transitions.size());

		// For performance we use a monitor to detect concurrently made changes.
		StateSpaceMonitor monitor = new StateSpaceMonitor(getStateSpace());
		
		// START OF EXPLORER LOCK
		synchronized (explorerLock) {
			monitor.setActive(true);	// Activate the monitor.
		}
		// END OF EXPLORER LOCK
		
		// No check which states / transitions need to be added.
		for (Transition transition : transitions) {
			
			// Transition label details:
			Rule rule = transition.getRule();
			int match = transition.getMatch();
			int[] parameters = transition.getParameterKeys();
			
			// Get the hash and model of the new target state:
			int hashCode = transition.getTarget().getHashCode();
			Model transformed = transition.getTarget().getModel();
					
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
					target = createOpenState(transformed, hashCode, state, location);
					monitor.getAddedStates().remove(target);
					newState = true;
				}
	
				// Find or create the transition.
				if (newState || findTransition(state, target, rule, match, parameters)==null) {
					createTransition(state, target, rule, match, parameters);
				}

			}
			// END OF EXPLORER LOCK
			
			// Now that the transition is there, we can decide whether to store the model.
			if (newState) {
				storeModel(target, transformed);
				result.add(target);
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
		
		// Get the state model and create an engine for it:
		Model model = getModel(state);
		
		// We need a couple of engines and rule applications:
		EmfEngine matchEngine = acquireEngine();
		EmfEngine transformEngine = acquireEngine();
		RuleApplication matchApp, transformApp;

		// Initialize the match engine:
		matchEngine.setEmfGraph(model.getEmfGraph());

		// Get some important state space parameters:
		boolean useObjectKeys = !getStateSpace().getEqualityHelper().getIdentityTypes().isEmpty();
		
		// List of explored transitions.
		List<Transition> transitions = new ArrayList<Transition>();
		
		// Apply all rules:
		for (Rule rule : getStateSpace().getRules()) {
			
			// Compute matches:
			matchApp = new RuleApplication(matchEngine, rule);
			List<Match> matches = matchApp.findAllMatches();
//			System.out.println("Found " + matches.size() + " matches for rule " + rule.getName());
			
			// Get the parameters of the rule:
			List<Node> parameters = useObjectKeys ? 
					ParameterUtil.getParameters(getStateSpace(), rule) : null;
			
			// Iterate over all matches:
			for (int matchIndex=0; matchIndex<matches.size(); matchIndex++) {
				
				// Transform the model:
				Match match = matches.get(matchIndex);
				Model transformed = model.getCopy(match);
				transformEngine.setEmfGraph(transformed.getEmfGraph());
				transformApp = new RuleApplication(transformEngine, rule);
				transformApp.setMatch(match);
				transformApp.apply();
				postProcessor.process(transformed);
				transformed.collectMissingRootObjects();
				
				// Create a new state:
				State newState = StateSpaceFactory.eINSTANCE.createState();
				newState.setModel(transformed);
				
				// Update object keys if necessary:
				if (useObjectKeys) {
					transformed.updateObjectKeys(getStateSpace().getEqualityHelper().getIdentityTypes());
					int[] objectKeys = transformed.getObjectKeys();
					newState.setObjectKeys(objectKeys);
					newState.setObjectCount(objectKeys.length);
				}
				
				// Now compute and set the hash code (after the node IDs have been updated!):
				int newHashCode = getStateSpace().getEqualityHelper().hashCode(transformed);
				newState.setHashCode(newHashCode);
				newState.setDerivedFrom(state.getIndex());
				
				// Create a new transition:
				Transition newTransition = StateSpaceFactory.eINSTANCE.createTransition();
				newTransition.setRule(rule);
				newTransition.setMatch(matchIndex);
				newTransition.setTarget(newState);
				
				// Set the parameters if necessary:
				if (useObjectKeys) {
					int[] params = new int[parameters.size()];
					for (int p=0; p<params.length; p++) {
						Node node = parameters.get(p);
						EObject matched = match.getNodeMapping().get(node);
						if (matched==null) {
							matched = matchApp.getComatch().getNodeMapping().get(node);
						}
						int objectKey = transformed.getObjectKeysMap().get(matched);
						params[p] = ObjectKeyHelper.createObjectKey(
								matched.eClass(), 
								objectKey, 
								getStateSpace().getEqualityHelper().getIdentityTypes());
					}
					newTransition.setParameterKeys(params);
					newTransition.setParameterCount(params.length);
				}
				
				// Remember the transition:
				transitions.add(newTransition);
								
			}
		}
		
		// Now we can release the engines again:
		releaseEngine(matchEngine);
		releaseEngine(transformEngine);
		
		// And we are done:
		return transitions;

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
	public void clearCache() {
		for (State state : getStateSpace().getStates()) {
			if (!state.isInitial()) {
				state.setModel(null);
			}
		}
		stateModelCache.clear();
		getStateSpace().getEqualityHelper().clearCache();
		postProcessor = new PostProcessor();
		System.gc();
	}

	/*
	 * Perform a sanity check for the exploration. For testing only.
	 * This check if doExplore() really yields equal results when invoked
	 * more than once on the same state.
	 */
	protected void checkEngineDeterminism(State state) throws StateSpaceException {
		
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
				State s1 = t1.getTarget();
				State s2 = t2.getTarget();
				if (s1.getHashCode()!=s2.getHashCode()) {
					markTainted(); throw new StateSpaceException("Sanity check 3 failed!");
				}
				if (!getStateSpace().getEqualityHelper().equals(s1.getModel(),s2.getModel())) {
					markTainted(); throw new StateSpaceException("Sanity check 4 failed!");
				}
			}
		}
		
	}
	
	/**
	 * Post processor helper.
	 * @author ckrause
	 *
	 */
	class PostProcessor {
		
		private ScriptEngine engine;
		private String script;
		
		PostProcessor() {
			ScriptEngineManager manager = new ScriptEngineManager();
		    engine = manager.getEngineByName("JavaScript");
		    script = getStateSpace().getProperties().get("postProcessor");
		    if (script!=null && script.trim().length()==0) {
		    	script = null;
		    }
		    if (script!=null) {
		    	String imports = "importPackage(java.lang);\n" +
		    					 "importPackage(java.util);\n" +
		    					 "importPackage(org.eclipse.emf.ecore);\n" ;
			    script = imports + script;
		    }
		}
		
		public void process(Model model) throws StateSpaceException {
			if (script!=null) {
				EObject root = model.getResource().getContents().get(0);
				synchronized (engine) {
					engine.put("model", root);
					try {
						engine.eval(script);
					} catch (ScriptException e) {
						throw new StateSpaceException(e);
					}
				}
			}
		}
		
	}
	
	/**
	 * A general-purpose cache.
	 * @author Christian Krause
	 */
	static class Cache<K,V> extends LinkedHashMap<K,V> {

		// Cache size, estimated with maximum number of MB of free memory:
		public static final int CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
		
		// Serial id:
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
		 */
		@Override
		protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
			return size() > CACHE_SIZE;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getNumThreads()
	 */
	@Override
	public int getNumThreads() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#shutdown()
	 */
	@Override
	public void shutdown() {
		super.shutdown();
		clearCache();
	}

}
