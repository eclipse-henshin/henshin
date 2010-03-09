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
package org.eclipse.emf.henshin.statespace.validation.ocl;

import java.text.ParseException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;
import org.eclipse.emf.henshin.statespace.validation.impl.AbstractStateValidator;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * OCL state validator.
 * @author Christian Krause
 */
public class OCLStateValidator extends AbstractStateValidator {
	
	// OCL helper:
	private OCLHelper<EClassifier, ?, ?, Constraint> helper;
	
	// Context to be used:
	private EClassifier context;
	
	// Constraint to be checked.
	private Constraint constraint;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#setProperty(java.lang.String)
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
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#validate(org.eclipse.emf.henshin.statespace.State)
	 */
	public ValidationResult validate(State state) {
		Resource model = state.getModel();
		for (EObject root : model.getContents()) {
			// Check the constraint .
		}
		return ValidationResult.VALID;
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

}
