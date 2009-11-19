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
	 * Get the integer array for the explored states in this state space.
	 * Clients should not use this method directly. Instead, {@link State#isExplored()}
	 * can used to check whether a state is explored or not.
	 * @return the '<em>explored</em>'-array.
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray" changeable="false"
	 * @generated
	 */
	int[] getExplored();

	/**
	 * Get the number of explored states in this state space.
	 * @return number of explored  states.
	 * @see #setExploredCount(int)
	 * @model
	 * @generated
	 */
	int getExploredCount();
	
	/**
	 * Set the number of explored states in this state space.
	 * <b>WARNING: this method is part of the internal API and should not be invoked by clients.<b>
	 * @param number of explored states.
	 * @see #getExploredCount()
	 * @generated
	 */
	void setExploredCount(int value);
	
}
