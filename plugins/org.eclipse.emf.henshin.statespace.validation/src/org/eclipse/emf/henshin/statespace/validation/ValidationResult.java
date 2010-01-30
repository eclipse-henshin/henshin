package org.eclipse.emf.henshin.statespace.validation;

/**
 * Validation result data class.
 * @author Christian Krause
 */
public final class ValidationResult {

	/**
	 * Static validation result for successful validations without messages and data.
	 */
	public static final ValidationResult VALID = new ValidationResult(true,null,null);

	/**
	 * Static validation result for invalid properties, without extra message or data.
	 */
	public static final ValidationResult INVALID = new ValidationResult(false,null,null);
	
	// Valid-flag.
	private boolean valid;
	
	// Message.
	private String message;
	
	// Data object.
	private Object data;

	/**
	 * Default constructor.
	 * @param valid Valid-flag.
	 * @param message Message.
	 * @param data Data object.
	 */
	public ValidationResult(boolean valid, String message, Object data) {
		this.valid = valid;
		this.message = message;
		this.data = data;
	}

	/**
	 * Returns <code>true</code> if a property was successfully validated.
	 * @return <code>true</code> if the property is valid.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Get the optional message of this validation result.
	 * @return message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Get the optional data associated with this result.
	 * @return Data object.
	 */
	public Object getData() {
		return data;
	}

}
