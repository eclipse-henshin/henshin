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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * OCL invariant state validator.
 * @author Christian Krause
 */
public class OCLStateValidator implements StateSpaceValidator {
	
	// OCL helper:
	private OCLHelper<EClassifier, ?, ?, Constraint> helper;
	
	// Context to be used:
	private EClassifier context;
	
	// Constraint to be checked.
	private Constraint constraint;

	private StateSpaceIndex index;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#setProperty(java.lang.String)
	 */
	public void setProperty(String property) throws ParseException {
		try {
			constraint = getHelper().createInvariant(property);
		} catch (ParserException e) {
			throw new ParseException(e.getMessage(),0);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public StateSpaceValidationResult validate(IProgressMonitor monitor) {
/*		Resource model = state.getModel();
		for (EObject root : model.getContents()) {
			// Check the constraint .
		}
		*/
		return StateSpaceValidationResult.VALID;
	}

	/*
	 * Get the OCL helper.
	 */
	private OCLHelper<EClassifier, ?, ?, Constraint> getHelper() {
		if (helper==null) {
			OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
			ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
			helper = ocl.createOCLHelper();
		}
		
		// Set the OCL context classifier
	    helper.setContext(context);

		return helper;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}

}
