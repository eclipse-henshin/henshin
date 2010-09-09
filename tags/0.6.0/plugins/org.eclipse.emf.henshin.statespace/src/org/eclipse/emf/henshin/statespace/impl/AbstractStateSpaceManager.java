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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * Abstract base implementation of StateSpaceManager.
 * @author Christian Krause
 * @generated NOT
 */
public abstract class AbstractStateSpaceManager extends StateSpaceIndexImpl implements StateSpaceManager {

	// State space meta data feature. This is ignored when changed from outside.
	private static final EAttribute METADATA_FEATURE = StateSpacePackage.eINSTANCE.getStorage_Data(); 
	
	// Tainted flag;
	private boolean tainted;
	
	// Change flag:
	private boolean change = false;
	
	// State space adapter:
	private Adapter adapter = new AdapterImpl() {		
		@Override
		public void notifyChanged(Notification event) {
			// Check if the change is illegal:
			if (!change && event.getFeature()==METADATA_FEATURE) tainted = true;
		}
	};
	
	// A lock for the state space:
	private final Object stateSpaceLock = new Object();
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public AbstractStateSpaceManager(StateSpace stateSpace) {
		super(stateSpace);
		this.tainted = false;
		stateSpace.eAdapters().add(adapter);
	}
	
	/**
	 * Reload all models and update hash codes.
	 * @param monitor Progress monitor.
	 * @exception StateSpaceException If the state space contains errors.
	 */
	public final void reload(IProgressMonitor monitor) throws StateSpaceException {
		
		monitor.beginTask("Reload models", getStateSpace().getStates().size());
		
		try {			
			// Reset state index:
			resetIndex();
			
			// Reset all derived state models:
			for (State state : getStateSpace().getStates()) {
				if (!state.isInitial()) {
					state.setModel(null);
				}
			}
			monitor.worked(1);
						
			// Compute state models, update the hash code and the index:
			for (State state : getStateSpace().getStates()) {
				
				// Compute the model and its hash:
				Resource model = getModel(state);
				int hash = hashCode(model);
				
				// Check if it exists already: 
				if (getState(model, hash)!=null) {
					markTainted();
					throw new StateSpaceException("Duplicate state: " + state.getIndex());
				}
				
				// Set the hash code. Model is set by subclasses in getModel().
				state.setHashCode(hash);
				
				// Set the open-flag.
				setOpen(state,isOpen(state));
				
				// Add the state to the index:
				addToIndex(state);
				
				monitor.worked(1);
				
			}
		} catch (Throwable t) {
			markTainted();
			throw new StateSpaceException(t);
		} finally {
			monitor.done();	
		}
		
	}
	
	/**
	 * Decide whether a state is open.
	 * @param state State state.
	 * @return <code>true</code> if it is open.
	 */
	protected boolean isOpen(State state) throws StateSpaceException {
		// By default we just assume that all states are open.
		return true;
	}
	
	/**
	 * Mark a state as open or closed.
	 * @param state State.
	 * @param open Open-flag.
	 */
	protected void setOpen(State state, boolean open) {
		synchronized (stateSpaceLock) {
			change = true;
			state.setOpen(open);
			if (open) {
				if (!getStateSpace().getOpenStates().contains(state)) {
					getStateSpace().getOpenStates().add(state);
				}
			} else {
				getStateSpace().getOpenStates().remove(state);
			}			
			change = false;
		}
	}
	
	protected State createOpenState(Resource model, int hash) {
		return createOpenState(model, hash, null);
	}

	/**
	 * Create a new open state in the state space. Warning: this does not check if an 
	 * equivalent state exists already or whether the hash code is incorrect.
	 * @param model Its model.
	 * @param hash The model's hash code.
	 * @return The newly created state.
	 */
	protected State createOpenState(Resource model, int hash, int[] location) {
		
		// Create a new state instance:
		State state = StateSpaceFactory.eINSTANCE.createState();
		state.setIndex(getStateSpace().getStates().size());
		state.setHashCode(hash);
		state.setModel(model);
		state.setOpen(true);
		if (location!=null) state.setLocation(location);
		
		// Add the state to the state space:
		synchronized (stateSpaceLock) {
			change = true;
			getStateSpace().getStates().add(state);
			getStateSpace().getOpenStates().add(state);
			change = false;
		}
		
		// Add the state to the index:
		addToIndex(state);
		return state;

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#createInitialState(org.eclipse.emf.ecore.resource.Resource)
	 */
	public final State createInitialState(Resource model) throws StateSpaceException {
		
		// Check if the resource is persisted:
		if (model.getURI()==null) {
			throw new IllegalArgumentException("Model is not persisted");
		}
		
		// Compute the hash code of the model:
		int hash = hashCode(model);
		
		// Look for an existing state:
		State state = getState(model,hash);
		if (state!=null) return state;
		
		// Otherwise create a new state:
		State initial = createOpenState(model, hash);
		synchronized (stateSpaceLock) {
			change = true;
			getStateSpace().getInitialStates().add(initial);
			change = false;
		}
		return initial;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	public final List<State> removeState(State state) throws StateSpaceException {
		
		// Check if the state space is tainted:
		if (tainted) throw new StateSpaceException();
		
		// List of removed states:
		List<State> removed = new ArrayList<State>();
		
		// Remove state and all unreachable states:
		synchronized (stateSpaceLock) {
			change = true;
			
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
			
			change = false;
		}
		
		// Done.
		return removed;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#resetStateSpace()
	 */
	public final void resetStateSpace() {
		
		// Remove derived states and all transitions:
		synchronized (stateSpaceLock) {
			change = true;
			
			getStateSpace().getStates().clear();
			getStateSpace().getOpenStates().clear();
			getStateSpace().getStates().addAll(getStateSpace().getInitialStates());
			
			for (State initial : getStateSpace().getStates()) {
				initial.getOutgoing().clear();
				initial.getIncoming().clear();
			}
			
			getStateSpace().setTransitionCount(0);
			
			change = false;
		}
		
		// Reload the manager:
		try {
			reload(new NullProgressMonitor());
			tainted = false;
		}
		catch (StateSpaceException e) {
			// This should not happen because the state space is reset.
			e.printStackTrace();
			tainted = true;
		}
	}
	
	/**
	 * Create a new outgoing transition. Note that the this does not check
	 * if the same transition exists already (use {@link #getTransition(State, String, int)}
	 * for that). Moreover the created transition is dangling (the target is not set).
	 * @param source Source state.
	 * @param target Target state.
	 * @param rule Rule to be used.
	 * @param match Match to be used.
	 * @return The newly created transition.
	 */
	protected Transition createTransition(State source, State target, Rule rule, int match) {
		Transition transition = StateSpaceFactory.eINSTANCE.createTransition();
		transition.setRule(rule);
		transition.setMatch(match);
		synchronized (stateSpaceLock) {
			change = true;
			transition.setSource(source);
			transition.setTarget(target);
			getStateSpace().setTransitionCount(getStateSpace().getTransitionCount()+1);
			change = false;
		}
		return transition;
	}
	
	/*
	 * Check if the state space is tainted.
	 */
	protected boolean isTainted() {
		return tainted;
	}
	
	/*
	 * Mark this state space as tainted.
	 */
	protected void markTainted() {
		tainted = true;
	}

}
