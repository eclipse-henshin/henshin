package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;

/**
 * Light-weight state space model.
 * @generated
 */
public interface StateSpace extends Storage {

	/**
	 * Get the list of states in this state space. The list contents are of type 
	 * {@link org.eclipse.emf.henshin.statespace.State}. It is bidirectional and its 
	 * opposite is '{@link org.eclipse.emf.henshin.statespace.State#getStateSpace <em>StateSpace</em>}'.
	 * 
	 * @return list of states in this state space.
	 * @see org.eclipse.emf.henshin.statespace.State#getStateSpace
	 * @model opposite="stateSpace" containment="true"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Remove a state from the state space. This automatically removes
	 * all transitions coinciding in that state.
	 * @model
	 * @generated
	 */
	boolean removeState(State state);
	
}
