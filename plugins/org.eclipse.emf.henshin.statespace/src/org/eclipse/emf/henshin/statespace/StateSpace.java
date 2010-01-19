package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;

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
	 * Returns the value of the '<em><b>Rules</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' reference list.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_Rules()
	 * @model
	 * @generated
	 */
	EList<Rule> getRules();

	/**
	 * Remove a state from the state space. This automatically removes
	 * all transitions coinciding in that state.
	 * @model
	 * @generated
	 */
	boolean removeState(State state);

	/**
	 * Get the initial states in this state space.
	 * Returns an unmodifiable list.
	 * @model kind="operation"
	 * @generated
	 */
	EList<State> getInitialStates();
	
}
