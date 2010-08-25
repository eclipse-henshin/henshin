/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.validation;

import org.eclipse.emf.henshin.statespace.Trace;

/**
 * Validation result data class.
 * @author Christian Krause
 * @generated NOT
 */
public final class ValidationResult {

	/**
	 * Static validation result for successful validations without messages.
	 */
	public static final ValidationResult VALID = new ValidationResult(true,null);

	/**
	 * Static validation result for invalid properties, without extra message or data.
	 */
	public static final ValidationResult INVALID = new ValidationResult(false,null);
	
	// Valid-flag.
	private boolean valid;
	
	// Trace.
	private Trace trace;
	
	/**
	 * Default constructor.
	 * @param valid Valid-flag.
	 * @param message Message.
	 * @param trace Trace.
	 */
	public ValidationResult(boolean valid, Trace trace) {
		this.valid = valid;
		this.trace = trace;
	}

	/**
	 * Default constructor.
	 * @param valid Valid-flag.
	 * @param message Message.
	 */
	public ValidationResult(boolean valid) {
		this(valid,null);
	}

	/**
	 * Returns <code>true</code> if a property was successfully validated.
	 * @return <code>true</code> if the property is valid.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Get the optional trace associated with this result.
	 * @return Trace trace.
	 */
	public Trace getTrace() {
		return trace;
	}

}
