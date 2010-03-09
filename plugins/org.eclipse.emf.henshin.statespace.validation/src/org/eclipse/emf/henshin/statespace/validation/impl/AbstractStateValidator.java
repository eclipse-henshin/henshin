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
package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;

/**
 * Abstract implementation of {@link StateValidator} interface.
 * @author Christian Krause
 */
public abstract class AbstractStateValidator implements StateValidator {

	// State space index to be used.
	private StateSpaceIndex index;
	
	/**
	 * Get the state space index to be used.
	 * @return State space index.
	 */
	protected StateSpaceIndex getStateSpaceIndex() {
		return index;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}
	
}
