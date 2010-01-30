package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationResult;

/**
 * Default implementation for state space validation results-
 * @author Christian Krause
 */
public class StateSpaceValidationResultImpl implements StateSpaceValidationResult {
	
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
