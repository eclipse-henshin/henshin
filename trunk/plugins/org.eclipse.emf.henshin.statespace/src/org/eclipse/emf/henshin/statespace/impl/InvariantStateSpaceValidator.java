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
package org.eclipse.emf.henshin.statespace.impl;

import java.text.ParseException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.StateValidator;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.ValidationResult;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSearch;

/**
 * An invariant state space validator, which is derived from a state validator.
 * This state space validator performs a depth-first search on
 * the state space and checks whether all states can be successfully
 * validated using the given state validator.
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class InvariantStateSpaceValidator extends StateSpaceSearch implements StateSpaceValidator {
	
	// State validator to be used:
	private StateValidator validator;
	
	// Is the property in the current state valid?
	private boolean valid;
	
	// Exception:
	private Exception exception;
	
	// Progress monitor:
	private IProgressMonitor monitor;

	/**
	 * Default and only constructor.
	 */
	public InvariantStateSpaceValidator(StateValidator validator) {
		super();
		if (validator==null) {
			throw new IllegalArgumentException();
		}
		this.validator = validator;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.StateSpace, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ValidationResult validate(StateSpace stateSpace, IProgressMonitor monitor) throws Exception {
		
		// Save and update progress monitor:
		this.monitor = monitor;
		monitor.beginTask("Validating invariant...", stateSpace.getStates().size());
		
		// Reset the search first:
		reset();
		
		// Search the state space:
		depthFirst(stateSpace, false);
		if (monitor.isCanceled()) return null;
		monitor.done();
		
		// Exception occurred?
		if (exception!=null) {
			throw exception;
		}
		
		// Otherwise create the result:
		if (valid) {
			return ValidationResult.VALID;			
		} else {
			return new ValidationResult(false, getTrace());	// Trace leads to the error state.
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
		
		// Canceled?
		if (monitor.isCanceled()) {
			return true;
		}
		
		// Validate the property using the state validator:
		try {
			ValidationResult result = validator.validate(current, new SubProgressMonitor(monitor,1));
			if (!result.isValid()) {
				valid = false;
				return true;	// Stop now.
			}
		} catch (Exception e) {
			exception = e;
			return true;		// Stop now.
		}

		monitor.worked(1);		
		return false;			// Don't stop.
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#getName()
	 */
	public String getName() {
		return validator.getName() + " (invariant)";
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setProperty(java.lang.String)
	 */
	public void setProperty(String property) throws ParseException {
		validator.setProperty(property);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	public void setStateSpaceIndex(StateSpaceIndex index) {
		validator.setStateSpaceIndex(index);
	}

}
