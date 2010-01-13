package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.util.HenshinEqualityUtil;

/**
 * Abstract base implementation of StateSpaceManager.
 * @author Christian Krause
 * @generated NOT
 */
public abstract class AbstractStateSpaceManager implements StateSpaceManager {

	// State space:
	private StateSpace stateSpace;
	
	// Number of transitions:
	private int transitionCount = 0;
	
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
	
	/**
	 * Reload this state space manager. This resets all
	 * state models and recomputes them.
	 */
	public void reload(IProgressMonitor monitor) {
		
		monitor.beginTask("Loading state space", stateSpace.getStates().size() + 2);
		
		// Reset the state registry and the number of transitions:
		resetRegistry();
		transitionCount = 0;
		monitor.worked(1);
		
		// Reset all derived state models:
		for (State state : stateSpace.getStates()) {
			if (!state.isInitial()) {
				state.setModel(null);
			}
		}
		monitor.worked(1);
		
		// Compute state models, update the hash code and the index:
		for (State state : stateSpace.getStates()) {
			Resource model = getModel(state);
			int hash = hashCode(model);
			if (getState(model, hash)!=null) {
				throw new RuntimeException("Duplicate state " + state.getName());
			}			
			state.setHashCode(hash);
			registerState(state);
			transitionCount += state.getOutgoing().size();
			monitor.worked(1);
		}
		
		monitor.done();
		
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
	protected final State createState(Resource model, int hash) {
		
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
		
		// Register the state and return it:
		registerState(state);
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
		if (getState(model,hash)!=null) {
			throw new RuntimeException("State exists already");
		}
		
		// Ok: create the new state:
		return createState(model, hash);
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	public void removeState(State state) {
		
		synchronized (this) {
			change = true;
			boolean removed = getStateSpace().getStates().remove(state);
			if (removed) {
				for (Transition outgoing : state.getOutgoing()) {
					outgoing.setTarget(null);
				}
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
	protected Transition createTransition(State state, String rule, int match) {		
		Transition transition = StateSpaceFactory.eINSTANCE.createTransition();
		transition.setRule(rule);
		transition.setMatch(match);
		state.getOutgoing().add(transition);
		transitionCount++;
		return transition;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getTransitionCount()
	 */
	public int getTransitionCount() {
		return transitionCount;
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

}
