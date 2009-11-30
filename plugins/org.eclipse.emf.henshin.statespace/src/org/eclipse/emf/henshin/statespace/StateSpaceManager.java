package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * State space manager interface.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public interface StateSpaceManager {
	
	/**
	 * Get the indexed state space.
	 * @return The indexed state space.
	 */
	StateSpace getStateSpace();
	
	/**
	 * Get the state in the state space that corresponds to a
	 * given model. If no corresponding state was found and the 
	 * create-flag is set to <code>true</code>, a new state is
	 * automatically added and returned.
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
	 * Explore a state.
	 * @param state State to be explored.
	 * @return List of newly created states.
	 */
	List<State> explore(State state);
	
}
