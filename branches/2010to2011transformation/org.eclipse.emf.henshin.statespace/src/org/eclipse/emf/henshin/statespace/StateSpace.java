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
import org.eclipse.emf.ecore.EClass;
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
	 * For fast add and remove operations, this
	 * collection is implemented as a set, not a list.
	 * @model
	 * @generated NOT
	 */
	Set<State> getOpenStates();

	/**
	 * Get the total number of transitions in this state space.
	 * @see #setTransitionCount(int)
	 * @model
	 * @generated
	 */
	int getTransitionCount();

	/**
	 * Update the object types for this state space.
	 * @model
	 * @generated
	 */
	void updateObjectTypes();

	/**
	 * Get the supported object types of this state space.
	 * @return the value of the '<em>Object Types</em>' attribute.
	 * @see #setObjectTypes(EClass[])
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_ObjectTypes()
	 * @model dataType="org.eclipse.emf.henshin.statespace.EClassArray" transient="true"
	 * @generated
	 */
	EClass[] getObjectTypes();

	/**
	 * Get the object type prefixes of this state space.
	 * @return the value of the '<em>Object Type Prefixes</em>' attribute.
	 * @see #setObjectTypePrefixes(String[])
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_ObjectTypePrefixes()
	 * @model dataType="org.eclipse.emf.henshin.statespace.StringArray" transient="true"
	 * @generated
	 */
	String[] getObjectTypePrefixes();

	/**
	 * Get all parameter identities used in transitions in this state space.
	 * @return the value of the '<em>All Parameter Identities</em>' attribute.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_AllParameterIdentities()
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int[] getAllParameterIdentities();

	/**
	 * Set the total number of transitions in this state space.
	 * @see #getTransitionCount()
	 * @generated
	 */
	void setTransitionCount(int value);

	/**
	 * Get the equality helper for this state space.
	 * @see #setEqualityHelper(StateEqualityHelper)
	 * @model containment="true"
	 * @generated
	 */
	StateEqualityHelper getEqualityHelper();

	/**
	 * Set the equality helper for this state space.
	 * @param value the new equality helper.
	 * @see #getEqualityHelper()
	 * @generated
	 */
	void setEqualityHelper(StateEqualityHelper value);

	/**
	 * Get the zoom level to be used when this state space is displayed.
	 * @return the value of the '<em>Zoom Level</em>' attribute.
	 * @see #setZoomLevel(int)
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	int getZoomLevel();

	/**
	 * Set the zoom level for this state space.
	 * @param value the new value of the '<em>Zoom Level</em>' attribute.
	 * @see #getZoomLevel()
	 * @generated
	 */
	void setZoomLevel(int value);

	/**
	 * Get the state repulsion to be used when layouting this state space.
	 * The value is between 0..100.
	 * @return the value of the '<em>State Repulsion</em>' attribute.
	 * @see #setStateRepulsion(int)
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	int getStateRepulsion();

	/**
	 * Set the state repulsion to be used when layouting this state space.
	 * @param value the new value of the '<em>State Repulsion</em>' attribute.
	 * @see #getStateRepulsion()
	 * @generated
	 */
	void setStateRepulsion(int value);

	/**
	 * Get the transition attraction to be used when layouting this state space.
	 * The value is between 0..100.
	 * @return the value of the '<em>Transition Attraction</em>' attribute.
	 * @see #setTransitionAttraction(int)
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	int getTransitionAttraction();

	/**
	 * Set the transition attraction to be used when layouting this state space.
	 * The value is between 0..100.
	 * @param value the new value of the '<em>Transition Attraction</em>' attribute.
	 * @see #getTransitionAttraction()
	 * @generated
	 */
	void setTransitionAttraction(int value);

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
	 * Check whether labels should be hidden.
	 * @return the value of the '<em>Hide Labels</em>' attribute.
	 * @see #setHideLabels(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_HideLabels()
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	boolean isHideLabels();

	/**
	 * Set the hide-labels flag.
	 * @param value the new value of the '<em>Hide Labels</em>' attribute.
	 * @see #isHideLabels()
	 * @generated
	 */
	void setHideLabels(boolean value);

	/**
	 * Get the properties for this state space.
	 * @return the value of the '<em>Properties</em>' map.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateSpace_Properties()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getProperties();

}
