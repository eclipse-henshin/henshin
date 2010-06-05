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
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * OCL invariant state validator.
 * @author Christian Krause
 */
public class OCLInvariantValidator implements StateSpaceValidator {
	
	// OCL:
	private OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
	
	// OCL helper:
	private OCLHelper<EClassifier, ?, ?, Constraint> helper;
	
	// Property to be checked.
	private String property;

	// State space index.
	private StateSpaceIndex index;
	
	// Is the property in the current state valid?
	private boolean valid;
	
	// Exception:
	private Exception exception;
	
	/**
	 * Default constructor.
	 */
	public OCLInvariantValidator() {
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		helper = ocl.createOCLHelper();
	}
	
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
		
		monitor.beginTask("Checking OCL constraint", index.getStateSpace().getStates().size());
		
		// Reset:
		valid = true;
		exception = null;
		
		// Create a state space search:
		StateSpaceSearch search = new StateSpaceSearch() {
			
			Constraint constraint = null;
			EClassifier classifier = null;

			@Override
			protected boolean shouldStop(Trace trace) {
				
				// Get the model for the current state:
				Resource model;
				try {
					model = index.getModel(getCurrentState());
				} catch (StateSpaceException e) {
					e.printStackTrace();
					return true;
				}
								
				// Check the constraint for all root elements:
				for (EObject root : model.getContents()) {
					
					if (classifier!=root.eClass()) {
						classifier = root.eClass();
					  	helper.setContext(classifier);
						try {
							constraint = helper.createInvariant(property);
						} catch (ParserException e) {
							exception = e;
							return true;
						}
					}
					
					// Check the constraint:
					if (!ocl.check(root, constraint)) {
						valid = false;
						return true;
					}
					
				}
				
				monitor.worked(1);
				
				// Don't stop:
				return false;
				
			}
		};
		
		// Search the state space:
		search.depthFirst(index.getStateSpace(), false);
		monitor.done();
		
		// Exception occurred?
		if (exception!=null) {
			throw exception;
		}
		
		if (valid) {
			return StateSpaceValidationResult.VALID;			
		} else {
			return new StateSpaceValidationResult(false, "Property not satisfied.", search.getTrace());
		}
		
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
