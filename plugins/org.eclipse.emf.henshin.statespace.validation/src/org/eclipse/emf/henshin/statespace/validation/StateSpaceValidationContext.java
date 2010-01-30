package org.eclipse.emf.henshin.statespace.validation;

/**
 * Interface for state space validation contexts.
 * @author Christian Krause
 */
public interface StateSpaceValidationContext {
	
	/**
	 * Get the validator for a given property name.
	 * @param property Name of a property.
	 * @return The validator for the property.
	 */
	StateSpaceValidator getValidator(String property);
	
}
