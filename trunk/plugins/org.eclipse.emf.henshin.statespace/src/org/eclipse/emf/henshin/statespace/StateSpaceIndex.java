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

/**
 * An interface for state space indexes.
 * @author Christian Krause
 * @generated NOT
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
	State getState(Model model) throws StateSpaceException;
	
	/**
	 * Get the model that corresponds to a state. The model is either cached
	 * or if not cached, it has to be derived on-the-fly.
	 * @param state State in the state space.
	 * @return The corresponding model.
	 * @exception StateSpaceException If the state space contains errors.
	 */
	Model getModel(State state) throws StateSpaceException;

	/**
	 * Clear all caches of the state space index.
	 */
	void clearCache();
	
}
