package org.eclipse.emf.henshin.statespace.validation;

import java.text.ParseException;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

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
	
	/**
	 * Set the state space to be used.
	 * @param stateSpace State space.
	 */
	void setStateSpace(StateSpace stateSpace);
	
	/**
	 * Set the property to be validated. It can be assumed
	 * that the state space has been set already before this 
	 * method is called.
	 * @param property Property.
	 * @throws ParseException If the property cannot be parsed.
	 */
	void setProperty(String property) throws ParseException;
	
}
