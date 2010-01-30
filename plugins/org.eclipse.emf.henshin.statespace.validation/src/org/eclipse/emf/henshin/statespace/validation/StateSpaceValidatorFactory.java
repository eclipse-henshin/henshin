package org.eclipse.emf.henshin.statespace.validation;

import java.text.ParseException;

import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Factory for state space validators.
 * @author Christian Krause
 */
public interface StateSpaceValidatorFactory {
	
	/**
	 * Create a new validator instance.
	 * @param property Property to be verified.
	 * @param stateSpace State space to be used.
	 * @throws ParseException If the property contains errors.
	 */
	StateSpaceValidator createValidator(String property, StateSpace stateSpace) throws ParseException;
	
}
