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

import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void updateEqualityHelper();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	int incTransitionCount();

	/**
	 * Get the initial states in this state space.
	 * @model
	 * @generated
	 */
	EList<State> getInitialStates();

	/**
	 * Get the open states in this state space.
	 * For fast add and remove operations, this
	 * collection is implemented as a set, not a list.
	 * @model
	 * @generated NOT
	 */
	Set<State> getOpenStates();

	/**
	 * Get the number of states in this state space.
	 * @return the state count
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_StateCount()
	 * @model transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	int getStateCount();

	/**
	 * Get the total number of transitions in this state space.
	 * @see #setTransitionCount(int)
	 * @model
	 * @generated
	 */
	int getTransitionCount();

	/**
	 * Get all object keys used as parameters in transitions in this state space.
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int[] getAllParameterKeys();

	/**
	 * Set the total number of transitions in this state space.
	 * @see #getTransitionCount()
	 * @generated
	 */
	void setTransitionCount(int value);

	/**
	 * Get the equality helper for this state space.
	 * @see #setEqualityHelper(EqualityHelper)
	 * @model containment="true"
	 * @generated
	 */
	EqualityHelper getEqualityHelper();

	/**
	 * Set the equality helper for this state space.
	 * @param value the new equality helper.
	 * @see #getEqualityHelper()
	 * @generated
	 */
	void setEqualityHelper(EqualityHelper value);

	/**
	 * Get the zoom level to be used when this state space is displayed.
	 * @return the value of the '<em>Zoom Level</em>' attribute.
	 * @see #setLayoutZoomLevel(int)
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	int getLayoutZoomLevel();

	/**
	 * Set the zoom level for this state space.
	 * @param value the new value of the '<em>Zoom Level</em>' attribute.
	 * @see #getLayoutZoomLevel()
	 * @generated
	 */
	void setLayoutZoomLevel(int value);

	/**
	 * Get the state repulsion to be used when layouting this state space.
	 * The value is between 0..100.
	 * @return the value of the '<em>State Repulsion</em>' attribute.
	 * @see #setLayoutStateRepulsion(int)
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	int getLayoutStateRepulsion();

	/**
	 * Set the state repulsion to be used when layouting this state space.
	 * @param value the new value of the '<em>State Repulsion</em>' attribute.
	 * @see #getLayoutStateRepulsion()
	 * @generated
	 */
	void setLayoutStateRepulsion(int value);

	/**
	 * Get the transition attraction to be used when layouting this state space.
	 * The value is between 0..100.
	 * @return the value of the '<em>Transition Attraction</em>' attribute.
	 * @see #setLayoutTransitionAttraction(int)
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	int getLayoutTransitionAttraction();

	/**
	 * Set the transition attraction to be used when layouting this state space.
	 * The value is between 0..100.
	 * @param value the new value of the '<em>Transition Attraction</em>' attribute.
	 * @see #getLayoutTransitionAttraction()
	 * @generated
	 */
	void setLayoutTransitionAttraction(int value);

	/**
	 * Check whether labels should be hidden.
	 * @return the value of the '<em>Hide Labels</em>' attribute.
	 * @see #setLayoutHideLabels(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_HideLabels()
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	boolean isLayoutHideLabels();

	/**
	 * Set the hide-labels flag.
	 * @param value the new value of the '<em>Hide Labels</em>' attribute.
	 * @see #isLayoutHideLabels()
	 * @generated
	 */
	void setLayoutHideLabels(boolean value);

	/**
	 * Get the maximum distance of states from the initial states.
	 * @return the value of the '<em>Max State Distance</em>' attribute.
	 * @see #setMaxStateDistance(int)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_MaxStateDistance()
	 * @model default="-1" transient="true" volatile="true"
	 * @generated
	 */
	int getMaxStateDistance();

	/**
	 * Set the maximum distance of states from the initial states.
	 * @param value the new value of the '<em>Max State Distance</em>' attribute.
	 * @see #getMaxStateDistance()
	 * @generated
	 */
	void setMaxStateDistance(int value);

	/**
	 * Get the properties for this state space.
	 * @return the value of the '<em>Properties</em>' map.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_Properties()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getProperties();

}
