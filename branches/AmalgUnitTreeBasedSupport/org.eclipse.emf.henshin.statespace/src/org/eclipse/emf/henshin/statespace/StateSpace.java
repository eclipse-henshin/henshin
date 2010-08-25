/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Light-weight state space model.
 * @generated
 */
public interface StateSpace extends Storage {

	/**
	 * Get the states stored in this state space.
	 * @model opposite="stateSpace" containment="true"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Get the rules used for generating this state space.
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
	 * @model
	 * @generated
	 */
	EList<State> getInitialStates();

	/**
	 * Get the open states in this state space.
	 * @model
	 * @generated
	 */
	EList<State> getOpenStates();

	/**
	 * Get the total number of transitions in this state space.
	 * @see #setTransitionCount(int)
	 * @model
	 * @generated
	 */
	int getTransitionCount();

	/**
	 * Set the total number of transitions in this state space.
	 * @see #getTransitionCount()
	 * @generated
	 */
	void setTransitionCount(int value);

	/**
	 * Returns the value of the '<em><b>Equality Helper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Equality Helper</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Equality Helper</em>' containment reference.
	 * @see #setEqualityHelper(StateEqualityHelper)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_EqualityHelper()
	 * @model containment="true"
	 * @generated
	 */
	StateEqualityHelper getEqualityHelper();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.StateSpace#getEqualityHelper <em>Equality Helper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Equality Helper</em>' containment reference.
	 * @see #getEqualityHelper()
	 * @generated
	 */
	void setEqualityHelper(StateEqualityHelper value);
	
}
