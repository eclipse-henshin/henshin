package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * Default implementation for state space validation results-
 * @author Christian Krause
 */
public class StateSpaceValidationResultImpl implements ValidationResult {
	
	/**
	 * Static validation result for successful validations without messages and data.
	 */
	public static final ValidationResult VALID = new StateSpaceValidationResultImpl(true);

	/**
	 * Static validation result for invalid properties, without extra message or data.
	 */
	public static final ValidationResult INVALID = new StateSpaceValidationResultImpl(false);
	
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
	public StateSpaceValidationResultImpl(boolean valid, String message, Object data) {
		this.valid = valid;
		this.message = message;
		this.data = data;
	}

	/**
	 * Convenience constructor.
	 * @param valid Valid-flag.
	 */
	public StateSpaceValidationResultImpl(boolean valid) {
		this(valid,null,null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationResult#getData()
	 */
	public Object getData() {
		return data;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationResult#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationResult#isValid()
	 */
	public boolean isValid() {
		return valid;
	}

}
