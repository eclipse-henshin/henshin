package org.eclipse.emf.henshin.statespace.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * An abstract state space manager that can be refreshed.
 * @generated NOT
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceManagerWithRefresh extends AbstractStateSpaceManager {

	// Number of transitions:
	private int transitionCount = 0;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public AbstractStateSpaceManagerWithRefresh(StateSpace stateSpace) {
		super(stateSpace);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#refresh(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public final void refresh(IProgressMonitor monitor) throws ExecutionException {
		
		monitor.beginTask("Refresh state space", getStateSpace().getStates().size() + 2);
		
		try {

			// Reset the state registry and the number of transitions:
			resetRegistry();
			transitionCount = 0;
			monitor.worked(1);

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
					throw new RuntimeException("Duplicate state " + state.getName());
				}
				
				// Update the state and meta-data:
				state.setHashCode(hash);
				registerState(state);
				transitionCount += state.getOutgoing().size();
				
				monitor.worked(1);
				
			}

		} catch (Throwable t) {
			throw new ExecutionException(t);
		} finally {
			monitor.done();			
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#createState(org.eclipse.emf.ecore.resource.Resource, int)
	 */
	@Override
	protected State createState(Resource model, int hash) {
		State state = super.createState(model, hash);
		registerState(state);
		return state;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#createTransition(org.eclipse.emf.henshin.statespace.State, org.eclipse.emf.henshin.model.Rule, int)
	 */
	@Override
	protected Transition createTransition(State state, Rule rule, int match) {
		Transition transition = super.createTransition(state, rule, match);
		transitionCount++;
		return transition;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	public final List<State> removeState(State state) {
		List<State> removed = super.removeState(state);
		Set<Transition> transitions = new HashSet<Transition>();
		for (State current : removed) {
			transitions.addAll(current.getOutgoing());
			transitions.addAll(current.getIncoming());
			unregisterState(current);	
		}
		transitionCount = transitionCount - transitions.size();
		return removed;
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
	 * Remove a state from the registry again.
	 * @param state State to be unregistered.
	 */
	protected void unregisterState(State state) {
		// Do nothing by default.
	}
	
	/**
	 * Reset the state registry. Subclasses can override
	 * this with a more efficient implementation.
	 */
	protected void resetRegistry() {
		// Do nothing by default.
	}

}
