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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * Helper class for checking a state space for OCL invariants.
 * @author Christian Krause
 */
public class OCLInvariantHelper extends StateSpaceSearch {

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
	
	// Parsed constraint:
	private Constraint constraint = null;
	
	// Context classifier:
	private EClassifier classifier = null;

	// Progress monitor:
	private IProgressMonitor monitor;

	/**
	 * Default constructor.
	 */
	public OCLInvariantHelper(StateSpaceIndex index, String property) {
		super();
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		helper = ocl.createOCLHelper();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public StateSpaceValidationResult validate(IProgressMonitor monitor) throws Exception {
		
		// Save and update progress monitor:
		this.monitor = monitor;
		monitor.beginTask("Checking OCL constraint", index.getStateSpace().getStates().size());
		
		// Reset first:
		reset();
		
		// Search the state space:
		depthFirst(index.getStateSpace(), false);
		monitor.done();
		
		// Exception occurred?
		if (exception!=null) {
			throw exception;
		}
		
		// Otherwise create the result:
		if (valid) {
			return StateSpaceValidationResult.VALID;			
		} else {
			return new StateSpaceValidationResult(false,getTrace());
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.util.StateSpaceSearch#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		valid = true;
		exception = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.util.StateSpaceSearch#shouldStop(org.eclipse.emf.henshin.statespace.State, org.eclipse.emf.henshin.statespace.Trace)
	 */
	@Override
	protected boolean shouldStop(State current, Trace trace) {
		
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

}
