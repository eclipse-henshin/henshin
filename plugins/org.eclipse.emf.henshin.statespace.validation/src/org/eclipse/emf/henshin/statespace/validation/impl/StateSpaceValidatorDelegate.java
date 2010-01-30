package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationContext;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;

/**
 * State space validator that acts as a delegate for other validators.
 * @author Christian Krause
 */
public class StateSpaceValidatorDelegate implements StateSpaceValidator {

	// Validation context:
	private StateSpaceValidationContext context;
	
	/**
	 * Default constructor.
	 * @param validator The actual validator.
	 * @param stateSpace
	 * @param local
	 */
	public StateSpaceValidatorDelegate(StateSpaceValidator validator, StateSpace stateSpace, boolean local) {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.State, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ValidationResult validate(State state, IProgressMonitor monitor) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setContext(org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationContext)
	 */
	public void setContext(StateSpaceValidationContext context) {
		this.context = context;
	}

}
