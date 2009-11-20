package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * Light-weight state space model.
 * @generated
 */
public interface StateSpace extends EObject {

	/**
	 * Get the list of states in this state space. The list contents are of type {@link org.eclipse.emf.henshin.statespace.State}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.statespace.State#getStateSpace <em>StateSpace</em>}'.
	 * @return list of states in this state space.
	 * @see org.eclipse.emf.henshin.statespace.State#getStateSpace
	 * @model opposite="stateSpace" containment="true"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Get the number of explored states in this state space.
	 * @return number of explored  states.
	 * @see #setExploredCount(int)
	 * @model
	 * @generated
	 */
	int getExploredCount();
	
	/**
	 * Get the number of transitions in this state space.
	 * @return number of transitions.
	 * @model changeable="false"
	 * @generated
	 */
	int getTransitionCount();
	
}
