package org.eclipse.emf.henshin.statespace.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.HenshinEqualityUtil;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * Abstract base implementation of StateSpaceManager.
 * @author Christian Krause
 * @generated NOT
 */
public abstract class AbstractStateSpaceManager implements StateSpaceManager {

	// State space:
	private StateSpace stateSpace;
	
	// Change flag:
	private boolean change = false;
	
	// State space adapter:
	private Adapter adapter = new AdapterImpl() {
		@Override
		public void notifyChanged(Notification event) {
			if (!change) {
				throw new RuntimeException("Illegal state space access");
			}
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
		stateSpace.eAdapters().add(adapter);
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
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getState(org.eclipse.emf.ecore.resource.Resource)
	 */
	public final State getState(Resource model) {
		return getState(model, hashCode(model));
	}

	/**
	 * Get the state for a given model and its hash code.
	 * @param model State model.
	 * @param hash Its hash code.
	 * @return The corresponding state if it exists.
	 */
	protected abstract State getState(Resource model, int hash);
	
	/**
	 * Create a new state in the state space. Warning: this does not 
	 * check if an equivalent state exists already or whether the 
	 * hash code is incorrect.
	 * @param model Its model.
	 * @param hash The model's hash code.
	 * @return The newly created state.
	 */
	protected State createState(Resource model, int hash) {
		
		// Create a new state instance:
		State state = StateSpaceFactory.eINSTANCE.createState();
		state.setName("s" + getStateSpace().getStates().size());
		state.setHashCode(hash);
		state.setModel(model);				
		
		// Add the state to the state space:
		synchronized (this) {
			change = true;
			getStateSpace().getStates().add(state);
			change = false;
		}
		return state;

	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#createInitialState(org.eclipse.emf.ecore.resource.Resource)
	 */
	public final State createInitialState(Resource model) {
		
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
		return createState(model, hash);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<State> removeState(State state) {
		
		// Remove state and all unreachable states:
		synchronized (this) {
			change = true;
			if (stateSpace.removeState(state)) {
				// Remove unreachable states as well:
				List<State> removed = StateSpaceSearch.removeUnreachableStates(stateSpace);
				removed.add(state);
			}
			change = false;
		}
		// Nothing changed:
		return Collections.emptyList();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#resetStateSpace()
	 */
	public final void resetStateSpace() {		
		
		synchronized (this) {
			change = true;

			// Remove states and transitions:
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
			
			// Refresh the manager:
			try {
				refresh(new NullProgressMonitor());
			} catch (ExecutionException e) {
				throw new RuntimeException(e);
			}
			
			change = false;
		}
		
	}
	
	/**
	 * Find an outgoing transition.
	 * @param state State that should contain the transition.
	 * @param rule Rule of the transition.
	 * @param match Index of the match.
	 * @return The transition or <code>null</code>.
	 */
	protected Transition getTransition(State state, String rule, int match) {
		for (Transition transition : state.getOutgoing()) {
			if (rule.equals(transition.getRule()) && match==transition.getMatch()) {
				return transition;
			}
		}
		return null;
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
		state.getOutgoing().add(transition);
		return transition;
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

}
