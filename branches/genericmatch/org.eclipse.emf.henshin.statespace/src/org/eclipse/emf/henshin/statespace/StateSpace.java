/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
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
	 * Check whether this state space uses graph-equality instead of Ecore-equality.
	 * @see #setUseGraphEquality(boolean)
	 * @model default="false"
	 * @generated
	 */
	boolean isUseGraphEquality();

	/**
	 * Set whether this state space uses graph-equality instead of Ecore-equality.
	 * @see #isUseGraphEquality()
	 * @generated
	 */
	void setUseGraphEquality(boolean value);
	
}
