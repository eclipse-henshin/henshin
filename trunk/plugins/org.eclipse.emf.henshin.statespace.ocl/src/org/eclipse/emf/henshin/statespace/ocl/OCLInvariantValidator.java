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
package org.eclipse.emf.henshin.statespace.ocl;

import java.text.ParseException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;

/**
 * OCL invariant state validator.
 * @author Christian Krause
 */
public class OCLInvariantValidator implements StateSpaceValidator {
		
	// Property to be checked.
	private String property;

	// State space index.
	private StateSpaceIndex index;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#setProperty(java.lang.String)
	 */
	public void setProperty(String property) throws ParseException {
		this.property = property;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public StateSpaceValidationResult validate(final IProgressMonitor monitor) throws Exception {
		
		// We use a helperfor this:
		OCLInvariantHelper helper = new OCLInvariantHelper(index, property);
		return helper.validate(monitor);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#getName()
	 */
	public String getName() {
		return "OCL Invariant";
	}

}
