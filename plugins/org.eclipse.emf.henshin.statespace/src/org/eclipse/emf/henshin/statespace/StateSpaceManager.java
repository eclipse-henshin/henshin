package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * State space manager interface. State managers are used to
 * index and explore a state space.
 * 
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
	 * Get the state that corresponds to the argument model. 
	 * If no corresponding state was found and the create-argument 
	 * is set to <code>true</code>, a new state is automatically added 
	 * to the state space.
	 * @param model State model.
	 * @param create create-flag.
	 * @return The corresponding state or <code>null</code>.
	 */
	State getState(Resource model, boolean create);

	/**
	 * Get the model that corresponds to a state.
	 * @param state State in the state space.
	 * @return The corresponding model.
	 */
	Resource getModel(State state);
	
	/**
	 * Explore a state. This computes all outgoing transitions
	 * and their target states and adds them to the state space
	 * if they do not exist yet.
	 * @param state State to be explored.
	 * @return List of newly created states.
	 */
	List<State> explore(State state);
	
}
