package org.eclipse.emf.henshin.statespace.util;

/**
 * State space validation error class.
 * @author Christian Krause
 */
public class StateSpaceValidationError extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @param message Error message.
	 */
	public StateSpaceValidationError(String message) {
		super(message);
	}
	
}
