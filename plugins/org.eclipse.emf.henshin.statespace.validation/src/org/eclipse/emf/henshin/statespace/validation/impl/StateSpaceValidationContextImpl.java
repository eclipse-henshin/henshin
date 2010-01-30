package org.eclipse.emf.henshin.statespace.validation.impl;

import java.util.HashMap;

import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationContext;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;

/**
 * Default implementation of {@link StateSpaceValidationContext}.
 * @author Christian Krause
 */
public class StateSpaceValidationContextImpl extends HashMap<String,StateSpaceValidator>
	implements StateSpaceValidationContext {
	
	// Serial-id:
	private static final long serialVersionUID = 1L;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationContext#getValidator(java.lang.String)
	 */
	public StateSpaceValidator getValidator(String property) {
		return get(property);
	}

}
