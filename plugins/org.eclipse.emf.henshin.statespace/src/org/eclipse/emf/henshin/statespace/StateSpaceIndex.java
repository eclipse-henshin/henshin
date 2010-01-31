package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * An interface for state space indexes.
 * @author Christian Krause
 */
public interface StateSpaceIndex {

	/**
	 * Get the indexed state space.
	 * @return The state space.
	 */
	StateSpace getStateSpace();
	
	/**
	 * Get the state that corresponds to the argument model. 
	 * @param model State model.
	 * @return The corresponding state or <code>null</code> if none was found.
	 * @exception StateSpaceException If the state space contains errors.
	 */
	State getState(Resource model) throws StateSpaceException;
	
	/**
	 * Get the model that corresponds to a state. The model is either cached
	 * or if not cached, it has to be derived on-the-fly.
	 * @param state State in the state space.
	 * @return The corresponding model.
	 * @exception StateSpaceException If the state space contains errors.
	 */
	Resource getModel(State state) throws StateSpaceException;

}
