/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch.Path;

/**
 * Default state space manager implementation.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManagerImpl extends AbstractStateSpaceManager {
	
	// State model cache:
	private StateModelCache cache;
	
	// Transformation engine:
	private EmfEngine engine;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceManagerImpl(StateSpace stateSpace) {
		super(stateSpace);
		this.cache = new StateModelCache();
		this.engine = new EmfEngine();
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
		
		for (Transition transition : transitions) {
			
			// Find the corresponding transition in the state space:
			Resource transformed = transition.getTarget().getModel();
			Transition existing = findTransition(state, transition.getRule(), transformed);
			if (existing==null) return true;
			matched.add(existing);
			
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
	public Resource getModel(State state) throws StateSpaceException {
		
		// Model already set?
		if (state.getModel()!=null) {
			return state.getModel();
		}
		
		// Cached?
		Resource cached = cache.get(state);
		if (cached!=null) {
			return cached;
		}
		
		// Find a predecessor state that has a model:
		StateSpaceSearch search = new StateSpaceSearch() {
			@Override
			protected boolean shouldStop(State current, Path path) {
				return current.getModel()!=null || cache.get(current)!=null;
			}
		};
		boolean found = search.depthFirst(state, true);
		if (!found) {
			throw new StateSpaceException("Unable to derive state model for state " + state.getIndex());
		}
		
		// Derive the current model:
		Resource start = search.getState().getModel();
		if (start==null) start = cache.get(search.getState());
		Resource model = deriveModel(start, search.getPath());
		
		// Always add it to the cache (is maintained automatically):
		cache.put(state, model);
		
		// Done.
		return model;
		
	}
	
	/*
	 * Store a model in a state.
	 */
	private void storeModel(State state, Resource model) {
		
		// Do not override initial state models!!!
		if (state.isInitial()) return;
		
		// Number of states: rounded up for more stability:
		int states = getStateSpace().getStates().size();
		states = states - (states % 1000) + 1000;
		
		// Decide whether the current model should be kept in memory:
		int stored = (int) Math.log10(states) - 1;
		int index = state.getIndex();
		
		//System.out.println(stored);
		
		if (stored>0 && index>0 && (index % stored)==0) {
			state.setModel(model);
		} else {
			state.setModel(null);
		}

	}
	
	/*
	 * Derive a model. The path is assumed to be non-empty.
	 */
	private Resource deriveModel(Resource start, Path path) throws StateSpaceException {
		
		// Copy the model first:
		Resource model = copyModel(start, null);
		
		// Derive the model for the current state:
		for (Transition transition : path) {
			
			Rule rule = transition.getRule();
			if (rule==null || !getStateSpace().getRules().contains(rule)) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());
			}
			
			RuleApplication application = createRuleApplication(model, rule);
			List<Match> matches = application.findAllMatches();
			if (matches.size()<=transition.getMatch()) {
				throw new StateSpaceException("Illegal transition in state " + transition.getSource());				
			}
			
			// Apply the rule with the found match:
			Match match = matches.get(transition.getMatch());
			application.setMatch(match);
			application.apply();
			
			// Store model:
			storeModel(transition.getTarget(),copyModel(model,null));
			
		}
		
		// Decide whether the model in the start state should be kept:
		storeModel(path.getSource(),start);

		return model;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#explore(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<Transition> exploreState(State state, boolean generateLocation) throws StateSpaceException {
		
		// Explore the state without changing the state space:
		List<Transition> transitions = doExplore(state);
		List<Transition> result = new ArrayList<Transition>(transitions.size());
		
		int newStates = 0;
		for (Transition transition : transitions) {
			
			// Get the hash and model of the new target state:
			int hashCode = transition.getTarget().getHashCode();
			Resource transformed = transition.getTarget().getModel();
			
			// Find existing state / transition:
			Transition existingTransition = findTransition(state, transition.getRule(), transformed);
			State targetState = getState(transformed, hashCode);
			
			if (existingTransition!=null) {
				
				// Check if the transition points to the correct state:
				Resource existingModel = getModel(existingTransition.getTarget());
				if (!equals(existingModel,transformed)) {
					markTainted();
					throw new StateSpaceException("Illegal transition in state " + state);
				}
				
			} else {
				
				// Create a new transition and state if required:
				if (targetState==null) {
					int[] location = generateLocation ? shiftedLocation(state, newStates++) : null;
					targetState = createOpenState(transformed, hashCode, location);
					storeModel(targetState, transformed);
				}
				Transition newTransition = createTransition(state, targetState, transition.getRule(), transition.getMatch());
				result.add(newTransition);
			}
		}
		
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
	private List<Transition> doExplore(State state) throws StateSpaceException {
		
		// Get the state model:
		Resource model = getModel(state);
		
		// List of explored transitions.
		List<Transition> transitions = new ArrayList<Transition>();
		
		// Apply all rules:
		for (Rule rule : getStateSpace().getRules()) {
			
			RuleApplication application = createRuleApplication(model, rule);
			List<Match> matches = application.findAllMatches();
			
			for (int i=0; i<matches.size(); i++) {
				
				// Transform the model:
				Match match = matches.get(i);
				Resource transformed = copyModel(model, match);
				application = createRuleApplication(transformed, rule);
				application.setMatch(match);
				application.apply();
				
				// Create a new transition and state:								
				State newState = StateSpaceFactory.eINSTANCE.createState();
				newState.setHashCode(hashCode(transformed));
				newState.setModel(transformed);
				
				Transition newTransition = StateSpaceFactory.eINSTANCE.createTransition();
				newTransition.setRule(rule);
				newTransition.setMatch(i);
				newTransition.setTarget(newState);
				
				transitions.add(newTransition);
				
			}
		}
		
		return transitions;

	}
	
	/*
	 * Find an outgoing transition.
	 */
	private Transition findTransition(State state, Rule rule, Resource targetModel) throws StateSpaceException {
		for (Transition transition : state.getOutgoing()) {
			if (rule==transition.getRule() && equals(getModel(transition.getTarget()),targetModel)) {
				return transition;
			}
		}
		return null;
	}
	
	/*
	 * Create a new RuleApplication
	 */
	private RuleApplication createRuleApplication(Resource model, Rule rule) {
		EmfGraph graph = new EmfGraph();
		for (EObject root : model.getContents()) {
			graph.addRoot(root);
		}
		engine.setEmfGraph(graph);
		return new RuleApplication(engine,rule);
	}
	
	/*
	 * Copy a state model.
	 */
	private Resource copyModel(Resource model, Match match) {
		Resource copy = new ResourceImpl();
		Copier copier = new Copier();
		copy.getContents().addAll(copier.copyAll(model.getContents()));
		copier.copyReferences();
		if (match!=null) {
			List<Node> nodes = new ArrayList<Node>(match.getNodeMapping().keySet());
			for (Node node : nodes) {
				EObject newImage = copier.get(match.getNodeMapping().get(node));
				match.getNodeMapping().put(node, newImage);
			}
		}
		return copy;
	}
	
	/*
	 * Create a shifted location.
	 */
	private int[] shiftedLocation(State base, int index) {
		int[] location = base.getLocation();
		double angle = (Math.PI * index * 0.17d);
		location[0] += 60 * Math.cos(angle);
		location[1] += 60 * Math.sin(angle);
		return location;
	}
	
	/**
	 * Set the memory usage for this state space manager. Must be between 0 and 1, 
	 * where 0 means that no model are kept in memory (except a cache of constant size)
	 * and 1 means that all models are kept in memory. For full effect, the manager has to
	 * be reloaded using {@link #reload(IProgressMonitor)}.
	 * @param memoryUsage Percentage for the memory usage.
	 */
	public void setMemoryUsage(double memoryUsage) {
		memoryUsage = Math.max(Math.min(memoryUsage,1),0);
	}

}
