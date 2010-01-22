package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * State space manager interface. State managers are used to
 * index and explore state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public interface StateSpaceManager {
	
	/**
	 * Get the managed state space.
	 * @return The managed state space.
	 */
	StateSpace getStateSpace();
	
	/**
	 * Check whether the managed state space is tainted
	 * and needs to be reset using {@link #resetStateSpace()}.
	 * @return <code>true</code> if it needs to be reset.
	 */
	boolean isTainted();
	
	/**
	 * Mark the state space manager as tainted.
	 * @see #isTainted()
	 */
	void markTainted();
	
	/**
	 * Get the state that corresponds to the argument model. 
	 * @param model State model.
	 * @return The corresponding state or <code>null</code> if none was found.
	 * @exception TaintedStateSpaceException If the state space turns out to be tainted.
	 */
	State getState(Resource model) throws TaintedStateSpaceException;
	
	/**
	 * Get the model that corresponds to a state. The model is either cached
	 * or if not cached, it has to be derived on-the-fly.
	 * @param state State in the state space.
	 * @return The corresponding model.
	 * @exception TaintedStateSpaceException If the state space turns out to be tainted.
	 */
	Resource getModel(State state) throws TaintedStateSpaceException;
	
	/**
	 * Create a new initial state to the state space. This throws a 
	 * runtime exception if the state is not contained in a resource.
	 * If there is already a state for it, it is returned instead.
	 * @param model Model of the initial state.
	 * @return The newly created state.
	 * @exception TaintedStateSpaceException If the state space turns out to be tainted.
	 */
	State createInitialState(Resource model) throws TaintedStateSpaceException;
	
	/**
	 * Remove a state from the state space. Unreachable states are automatically
	 * removed afterwards and the open-attributes are updated.
	 * @param state State to be removed.
	 * @return List of removed states.
	 * @exception TaintedStateSpaceException If the state space turns out to be tainted.
	 */
	List<State> removeState(State state) throws TaintedStateSpaceException;
	
	/**
	 * Explore a state. This computes all outgoing transitions
	 * and their target states and adds them to the state space
	 * if they do not exist yet.
	 * @param state State to be explored.
	 * @return List of newly created outgoing transitions.
	 * @exception TaintedStateSpaceException If the state space turns out to be tainted.
	 */
	List<Transition> exploreState(State state) throws TaintedStateSpaceException;
	
	/**
	 * Reset the state space managed by this instance.
	 * This removes all derived states and all transitions.
	 */
	void resetStateSpace();
	
}
