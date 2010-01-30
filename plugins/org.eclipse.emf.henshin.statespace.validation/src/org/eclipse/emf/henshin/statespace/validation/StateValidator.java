package org.eclipse.emf.henshin.statespace.validation;

import org.eclipse.emf.henshin.statespace.State;

/**
 * Interface for state validators.
 * @author Christian Krause
 */
public interface StateValidator {
	
	/**
	 * Validates a given state.
	 * @param state State to be validated.
	 * @return Validation result.
	 */
	ValidationResult validate(State state);
	
}
