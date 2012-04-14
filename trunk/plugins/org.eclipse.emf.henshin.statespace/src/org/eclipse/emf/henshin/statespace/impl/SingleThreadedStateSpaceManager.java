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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.matching.util.TransformationOptions;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.EqualityHelper;
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
import org.eclipse.emf.henshin.statespace.util.StateDistanceMonitor;
import org.eclipse.emf.henshin.statespace.util.StateSpaceMonitor;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * Single-threaded state space manager implementation.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class SingleThreadedStateSpaceManager extends StateSpaceIndexImpl implements StateSpaceManager {

	// State model cache:
	private final Map<State,Model> stateModelCache = 
			Collections.synchronizedMap(new UniversalCache<State,Model>());
		
	// Transformation engines:
	private final Stack<EmfEngine> engines = new Stack<EmfEngine>();
	
	// A lock used when accessing te state space:
	private final Object stateSpaceLock = new Object();

	// State distance monitor:
	private StateDistanceMonitor stateDistanceMonitor;
	
	// Model post-processor:
	private ModelPostProcessor postProcessor;

	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public SingleThreadedStateSpaceManager(StateSpace stateSpace) {
		super(stateSpace);
		refreshHelpers();
	}
	
	/*
	 * Refresh the helpers.
	 */
	protected void refreshHelpers() {
		getStateSpace().updateEqualityHelper();
		postProcessor = new ModelPostProcessor(getStateSpace());
		if (getStateSpace().getMaxStateDistance()>=0) {
			stateDistanceMonitor = new StateDistanceMonitor(getStateSpace());
		} else {
			stateDistanceMonitor = null;
		}
		clearCache();
	}
		
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#reload(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void reload(IProgressMonitor monitor) throws StateSpaceException {
		
		monitor.beginTask("Reload models", getStateSpace().getStates().size() + 3);
		
		// We need some info:
		StateSpace stateSpace = getStateSpace();
		EqualityHelper equalityHelper = stateSpace.getEqualityHelper();

		// Refresh helpers:
		refreshHelpers();	
		monitor.worked(1);
		
		try {
			
			// Reset state index:
			resetIndex();
			monitor.worked(1);
			
			// Reset all derived state models:
			for (State state : stateSpace.getStates()) {
				if (state.isInitial()) {
					state.setDerivedFrom(-1);
				} else {
					state.setModel(null);					
				}
			}
			monitor.worked(1);

			// Whether we need to compute keys:
			boolean useObjectKeys = !equalityHelper.getIdentityTypes().isEmpty();

			// Compute state models, update the hash code and the index:
			for (State state : stateSpace.getStates()) {
				
				// Get the model first:
				Model model = getModel(state);
				
				// Update the object keys if necessary:
				if (useObjectKeys) {
					model.updateObjectKeys(equalityHelper.getIdentityTypes());
					state.setObjectKeys(model.getObjectKeys());
				}
				
				// Update object count:
				state.setObjectCount(model.getEmfGraph().geteObjects().size());
				
				// Now compute the hash code:
				int hash = equalityHelper.hashCode(model);
				
				// Check if it exists already: 
				if (getState(model,hash)!=null) {
					throw new StateSpaceException("Duplicate state: " + state.getIndex());
				}
				
				// Update the hash code. Model is set by subclasses in getModel().
				state.setHashCode(hash);
				
				// Set the open-flag.
				setOpen(state,isOpen(state));
				
				// Add the state to the index:
				addToIndex(state);
				monitor.worked(1);
				
			}
		} catch (Throwable t) {
			throw new StateSpaceException(t);
		} finally {
			monitor.done();
		}
		
	}
	
	/*
	 * Helper method for finding a state in a list.
	 */
	protected State findState(Model model, int hashCode, Collection<State> states) throws StateSpaceException {
		for (State state : states) {
			if (hashCode==state.getHashCode() && 
				getStateSpace().getEqualityHelper().equals(model,getModel(state))) {
				return state;
			}
		}
		return null;
	}

	/*
	 * Mark a state as open or closed.
	 */
	protected void setOpen(State state, boolean open) {
		state.setOpen(open);
		if (open) {
			getStateSpace().getOpenStates().add(state);
		} else {
			getStateSpace().getOpenStates().remove(state);
		}
	}

	/*
	 * Decide whether a state is open.
	 */
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

	/**
	 * Create a new open state in the state space. Warning: this does not check if an 
	 * equivalent state exists already or whether the hash code is incorrect.
	 * @param model Its model.
	 * @param hash The model's hash code.
	 * @return The newly created state.
	 */
	protected final State createOpenState(Model model, int hash, State derivedFrom, int[] location) {
		
		// Create a new state instance:
		State state = StateSpaceFactory.eINSTANCE.createState();
		state.setIndex(getStateSpace().getStates().size());
		state.setHashCode(hash);
		state.setDerivedFrom(derivedFrom!=null ? derivedFrom.getIndex() : -1);
		state.setModel(model);
		state.setOpen(true);
		
		// Set the location, if set:
		if (location!=null) {
			state.setLocation(location);
		}
		
		// Set the object keys, if required:
		if (!getStateSpace().getEqualityHelper().getIdentityTypes().isEmpty()) {
			int[] objectKeys = model.getObjectKeys();
			state.setObjectKeys(objectKeys);
			state.setObjectCount(objectKeys.length);
		}
		
		// Add the state to the state space:
		getStateSpace().getStates().add(state);
		getStateSpace().getOpenStates().add(state);
		if (stateDistanceMonitor!=null) {
			stateDistanceMonitor.updateDistance(state);
		}
		
		// Add the state to the index:
		addToIndex(state);
		return state;

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#createInitialState(org.eclipse.emf.ecore.resource.Resource)
	 */
	public final State createInitialState(Model model) throws StateSpaceException {
		
		// Check if the resource is persisted:
		Resource resource = model.getResource();
		if (resource==null || resource.getURI()==null) {
			throw new IllegalArgumentException("Model is not persisted");
		}
		
		// Resolve all objects in the model:
		EcoreUtil.resolveAll(model);
		
		// Make sure the objects keys are set:
		if (!getStateSpace().getEqualityHelper().getIdentityTypes().isEmpty()) {
			model.updateObjectKeys(getStateSpace().getEqualityHelper().getIdentityTypes());
		}
		
		// Compute the hash code of the model:
		int hash = getStateSpace().getEqualityHelper().hashCode(model);
		
		// Look for an existing state:
		State state = getState(model,hash);
		if (state!=null) return state;
		
		// Otherwise create a new state:
		State initial = createOpenState(model, hash, null, null);
		synchronized (stateSpaceLock) {
			getStateSpace().getInitialStates().add(initial);
			if (stateDistanceMonitor!=null) {
				stateDistanceMonitor.updateDistance(state);
			}
		}
		return initial;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	public final List<State> removeState(State state) throws StateSpaceException {
		
		// List of removed states:
		List<State> removed = new ArrayList<State>();
		
		// Remove state and all unreachable states:
		synchronized (stateSpaceLock) {
			
			// Remove the state and all reachable states:
			if (getStateSpace().removeState(state)) {
				removed.addAll(StateSpaceSearch.removeUnreachableStates(getStateSpace()));
				removed.add(state);
			}
			
			// Update list of open and initial states:
			getStateSpace().getOpenStates().removeAll(removed);
			getStateSpace().getInitialStates().removeAll(removed);
			
			// Remove the states from the index and adjust the transition count:
			Set<Transition> transitions = new HashSet<Transition>();
			for (State current : removed) {
				
				// Remove from index:
				removeFromIndex(current);
				
				// Gather all transitions:
				transitions.addAll(current.getOutgoing());
				transitions.addAll(current.getIncoming());
				
			}
			
			// Update transition count:
			int number = getStateSpace().getTransitionCount() - transitions.size();
			getStateSpace().setTransitionCount(number);
			
		}
		
		// Done.
		return removed;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#resetStateSpace()
	 */
	public final void resetStateSpace() throws StateSpaceException {
		
		// Remove derived states and all transitions:
		synchronized (stateSpaceLock) {
			
			// Recompute the supported types:
			getStateSpace().updateEqualityHelper();
			
			// Remove all states except the initial ones:
			getStateSpace().getStates().clear();
			getStateSpace().getOpenStates().clear();
			getStateSpace().getStates().addAll(getStateSpace().getInitialStates());
			
			// Remove all transitions:
			for (State initial : getStateSpace().getStates()) {
				initial.getOutgoing().clear();
				initial.getIncoming().clear();
			}
			getStateSpace().setTransitionCount(0);

			// Reset the object keys for the initial states:
			for (State initial : getStateSpace().getStates()) {
				Model model = initial.getModel();
				model.setObjectKeys(StorageImpl.EMPTY_DATA);
				if (!getStateSpace().getEqualityHelper().getIdentityTypes().isEmpty()) {
					model.updateObjectKeys(getStateSpace().getEqualityHelper().getIdentityTypes());
				}
				initial.setObjectKeys(model.getObjectKeys());
			}

			// Reload the manager:
			reload(new NullProgressMonitor());

		}
		
	}
	
	/*
	 * Create a new outgoing transition. Note that the this does not check
	 * if the same transition exists already (use {@link #getTransition(State, String, int, int[])}
	 * for that). Moreover the created transition is dangling (the target is not set).
	 */
	protected Transition createTransition(State source, State target, Rule rule, int match, int[] paramIDs) {
		Transition transition = StateSpaceFactory.eINSTANCE.createTransition();
		transition.setRule(rule);
		transition.setMatch(match);
		transition.setParameterKeys(paramIDs);
		transition.setParameterCount(paramIDs.length);
		transition.setSource(source);
		transition.setTarget(target);
		getStateSpace().setTransitionCount(getStateSpace().getTransitionCount()+1);
		if (stateDistanceMonitor!=null) {
			stateDistanceMonitor.updateDistance(transition.getTarget());
		}
		return transition;
	}
	
	/*
	 * Find an outgoing transition.
	 */
	protected static Transition findTransition(State source, State target, Rule rule, int match, int[] paramIDs) {
		for (Transition transition : source.getOutgoing()) {
			if (target==transition.getTarget() && 
				(rule==null || rule==transition.getRule()) &&
				(paramIDs==null || Arrays.equals(paramIDs, transition.getParameterKeys())) &&
				(match<0 || transition.getMatch()==match)) {
				return transition;
			}
		}
		return null;
	}
	
	/**
	 * Get the distance of a state to an initial state.
	 * This returns the correct distance only if the
	 * maximum state distance of the state space is positive.
	 * Otherwise the distance is not relevant and therefore not stored.
	 * @param state The state.
	 * @return Its distance from an initial state, or -1 if not available.
	 */
	@Override
	public int getStateDistance(State state) {
		if (stateDistanceMonitor!=null) {
			return stateDistanceMonitor.getDistance(state);
		} else {
			return -1;
		}
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
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#exploreStates(java.util.List, boolean)
	 */
	public List<State> exploreStates(List<State> states, boolean generateLocation) throws StateSpaceException {
		List<State> result = new ArrayList<State>();
		try {
			for (State state : states) {
				result.addAll(exploreState(state, generateLocation));
			}
		} catch (Throwable t) {
			if (t instanceof StateSpaceException) {
				throw (StateSpaceException) t;
			} else {
				throw new StateSpaceException(t);
			}
		}
		return result;
	}

	/**
	 * Explore a given state.
	 * @param state State to be explored.
	 * @param generateLocation Whether to generate locations for the new state.
	 * @return List of newly created successor states.
	 * @throws StateSpaceException On errors.
	 */
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
		synchronized (stateSpaceLock) {
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
			synchronized (stateSpaceLock) {
				
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
		synchronized (stateSpaceLock) {
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
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#clearCache()
	 */
	@Override
	public void clearCache() {
		for (State state : getStateSpace().getStates()) {
			if (!state.isInitial()) {
				state.setModel(null);
			}
		}
		stateModelCache.clear();
		getStateSpace().getEqualityHelper().clearCache();
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
		clearCache();
	}

}
