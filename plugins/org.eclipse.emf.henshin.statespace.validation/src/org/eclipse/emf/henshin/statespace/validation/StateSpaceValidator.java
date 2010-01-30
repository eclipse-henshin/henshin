package org.eclipse.emf.henshin.statespace.validation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;

/**
 * Interface for state space validators.
 * @author Christian Krause
 */
public interface StateSpaceValidator {
	
	/**
	 * Perform the validation for a given state.
	 * @param state State to be validated.
	 * @param monitor Progress monitor.
	 * @return Validation result.
	 */
	StateSpaceValidationResult validate(State state, IProgressMonitor monitor);
	
	/**
	 * Set the validation context to be used.
	 * @param context Validation context.
	 */
	void setContext(StateSpaceValidationContext context);
	
}
