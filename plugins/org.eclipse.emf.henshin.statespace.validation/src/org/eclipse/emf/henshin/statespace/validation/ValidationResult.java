package org.eclipse.emf.henshin.statespace.validation;

/**
 * Interface for state space validation results.
 * @author Christian Krause
 */
public interface ValidationResult {
	
	/**
	 * Returns <code>true</code> if a property was successfully validated.
	 * @return <code>true</code> if the property is valid.
	 */
	boolean isValid();
	
	/**
	 * Get the optional message of this validation result.
	 * @return message.
	 */
	String getMessage();
	
	/**
	 * Get the optional data associated with this result.
	 * @return Data object.
	 */
	Object getData();
	
}
