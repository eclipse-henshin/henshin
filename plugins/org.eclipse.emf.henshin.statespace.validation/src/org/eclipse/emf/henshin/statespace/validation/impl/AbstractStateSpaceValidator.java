package org.eclipse.emf.henshin.statespace.validation.impl;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;

/**
 * Abstract implementation of {@link StateSpaceValidator}.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceValidator implements StateSpaceValidator {
	
	// State space index.
	private StateSpaceIndex index;
	
	// State validators:
	private Map<String, StateValidator> validators = Collections.emptyMap();
	
	/**
	 * Get the state space index to be used.
	 * @return State space index.
	 */
	protected StateSpaceIndex getStateSpaceIndex() {
		return index;
	}
	
	/**
	 * Get the state validators map.
	 * @return State validators map.
	 */
	protected Map<String, StateValidator> getStateValidators() {
		return validators;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setStateValidators(java.util.Map)
	 */
	public void setStateValidators(Map<String, StateValidator> validators) {
		this.validators = validators;
	}

}
