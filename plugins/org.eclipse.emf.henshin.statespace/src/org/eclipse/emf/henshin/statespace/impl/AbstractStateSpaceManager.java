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
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.TaintedStateSpaceException;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.HenshinEqualityUtil;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * Abstract base implementation of StateSpaceManager.
 * @author Christian Krause
 * @generated NOT
 */
public abstract class AbstractStateSpaceManager implements StateSpaceManager {

	// State space meta data feature. This is ignored when changed from outside.
	private static final EAttribute METADATA_FEATURE = StateSpacePackage.eINSTANCE.getStorage_Data(); 
	
	// State space:
	private StateSpace stateSpace;
	
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
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public AbstractStateSpaceManager(StateSpace stateSpace) {
		if (stateSpace==null) {
			throw new IllegalArgumentException();
		}	
		this.stateSpace = stateSpace;
		this.tainted = false;
		stateSpace.eAdapters().add(adapter);
	}
	
	/**
	 * Reload this state space manager.
	 * @param monitor Progress monitor.
	 * @throws TaintedStateSpaceException If the state space turns out to be tainted.
	 */
	public final void reload(IProgressMonitor monitor) throws TaintedStateSpaceException {
		
		monitor.beginTask("Load state space", getStateSpace().getStates().size() + 2);
		try {
			
			// Reset the state registry:
			resetRegistry();			
			monitor.worked(1);

			// Reset all derived state models:
			for (State state : getStateSpace().getStates()) {
				if (!state.isInitial()) {
					state.setModel(null);
				}
			}
			monitor.worked(1);

			// Compute state models, update the hash code and the index:
			int transitionCount = 0;
			for (State state : getStateSpace().getStates()) {
				
				// Compute the model and its hash:
				Resource model = getModel(state);
				int hash = hashCode(model);
				
				// Check if it exists already: 
				if (getState(model, hash)!=null) {
					markTainted();
					throw new TaintedStateSpaceException("Duplicate state " + state.getName());
				}
				
				// Update the state properties:
				state.setHashCode(hash);
				state.setOpen(isOpen(state));
				
				// Register the state:
				registerState(state);
				
				// Update the transition count:
				transitionCount += state.getOutgoing().size();
				monitor.worked(1);
				
			}
			
			// Update the transition count:
			synchronized (this) {
				change = true;
				stateSpace.setTransitionCount(transitionCount);
				change = false;
			}
			
		} catch (Throwable t) {
			markTainted();
			throw new TaintedStateSpaceException(t.getMessage());
		} finally {
			monitor.done();	
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getState(org.eclipse.emf.ecore.resource.Resource)
	 */
	public final State getState(Resource model) throws TaintedStateSpaceException {
		return getState(model, hashCode(model));
	}

	/**
	 * Get the state for a given model and its hash code.
	 * @param model State model.
	 * @param hash Its hash code.
	 * @return The corresponding state if it exists.
	 */
	protected abstract State getState(Resource model, int hash) throws TaintedStateSpaceException;
	
	/**
	 * Decide whether a state is open.
	 * @param state State state.
	 * @return <code>true</code> if it is open.
	 */
	protected boolean isOpen(State state) throws TaintedStateSpaceException {
		// By default we just assume that all states are open.
		return true;
	}
	
	/**
	 * Create a new open state in the state space. Warning: this does not check if an 
	 * equivalent state exists already or whether the hash code is incorrect.
	 * @param model Its model.
	 * @param hash The model's hash code.
	 * @return The newly created state.
	 */
	protected State createOpenState(Resource model, int hash) {
		
		// Create a new state instance:
		State state = StateSpaceFactory.eINSTANCE.createState();
		state.setName("s" + getStateSpace().getStates().size());
		state.setHashCode(hash);
		state.setModel(model);
		state.setOpen(true);
		
		// Add the state to the state space:
		synchronized (this) {
			change = true;
			getStateSpace().getStates().add(state);
			change = false;
		}
		
		// Register the state:
		registerState(state);
		return state;

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#createInitialState(org.eclipse.emf.ecore.resource.Resource)
	 */
	public final State createInitialState(Resource model) throws TaintedStateSpaceException {
		
		// Check if the resource is persisted:
		if (model.getURI()==null) {
			throw new IllegalArgumentException("Model is not persisted");
		}
		
		// Compute the hash code of the model:
		int hash = hashCode(model);
		
		// Look for an existing state:
		State state = getState(model,hash);
		if (state!=null) return state;
		
		// Otherwise create the new state:
		return createOpenState(model, hash);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	public final List<State> removeState(State state) throws TaintedStateSpaceException {
		
		// Check if the state space is tainted:
		if (tainted) throw new TaintedStateSpaceException();
		
		// List of removed states:
		List<State> removed = new ArrayList<State>();
			
		// Remove state and all unreachable states:
		synchronized (this) {
			change = true;
			
			// Remove the state and all reachable states:
			if (stateSpace.removeState(state)) {
				removed.addAll(StateSpaceSearch.removeUnreachableStates(stateSpace));
				removed.add(state);
			}
			
			// Unregister states and adjust the transition count:
			Set<Transition> transitions = new HashSet<Transition>();
			for (State current : removed) {
				unregisterState(current);
				transitions.addAll(current.getOutgoing());
				transitions.addAll(current.getIncoming());
			}
			
			// Update transition count:
			int number = stateSpace.getTransitionCount() - transitions.size();
			stateSpace.setTransitionCount(number);
			
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
		synchronized (this) {
			change = true;
			List<State> states = stateSpace.getStates();			
			for (int i=0; i<states.size(); i++) {
				State state = states.get(i);
				if (state.isInitial()) {
					state.getIncoming().clear();
					state.getOutgoing().clear();					
				} else {
					states.remove(i--);
				}
			}
			change = false;
		}
		
		// Reload the manager:
		try {
			reload(new NullProgressMonitor());
			tainted = false;
		}
		catch (TaintedStateSpaceException e) {
			// This should not happen because the state space is reset.
			e.printStackTrace();
			tainted = true;
		}
	}
	
	/**
	 * Create a new outgoing transition. Note that the this does not check
	 * if the same transition exists already (use {@link #getTransition(State, String, int)}
	 * for that). Moreover the created transition is dangling (the target is not set).
	 * @param state State that will contain the new transition.
	 * @param rule Rule to be used.
	 * @param match Match to be used.
	 * @return The newly created transition.
	 */
	protected Transition createTransition(State state, Rule rule, int match) {
		Transition transition = StateSpaceFactory.eINSTANCE.createTransition();
		transition.setRule(rule);
		transition.setMatch(match);
		synchronized (this) {
			change = true;
			state.getOutgoing().add(transition);
			stateSpace.setTransitionCount(stateSpace.getTransitionCount()+1);
			change = false;
		}
		return transition;
	}
		
	/**
	 * Register an existing state in an index so that it
	 * can be found efficiently.
	 * @param state State to be registered.
	 */
	protected void registerState(State state) {
		// Do nothing by default.
	}
		
	/**
	 * Remove a state from the registry again.
	 * @param state State to be unregistered.
	 */
	protected void unregisterState(State state) {
		// Do nothing by default.
	}
	
	/**
	 * Reset the state registry.
	 */
	protected void resetRegistry() {
		// Do nothing by default.
	}
	
	/*
	 * Compute the hash code of a state model.
	 * This delegates to HenshinEqualityUtil#hashCode
	 */
	protected static int hashCode(Resource model) {
		return HenshinEqualityUtil.hashCode(model);
	}
	
	/*
	 * Check if two state models are equal.
	 * This delegates to HenshinEqualityUtil#equals
	 */
	protected static boolean equals(Resource model1, Resource model2) {
		return HenshinEqualityUtil.equals(model1, model2);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getStateSpace()
	 */
	public StateSpace getStateSpace() {
		return stateSpace;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#isTainted()
	 */
	public boolean isTainted() {
		return tainted;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#markTainted()
	 */
	public void markTainted() {
		tainted = true;
	}

}
