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
	
}
