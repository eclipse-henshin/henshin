package org.eclipse.emf.henshin.statespace.validation.impl;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;

/**
 * Abstract implementation of {@link StateSpaceValidator}.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceValidator implements StateSpaceValidator {
	
	// State space.
	private StateSpace stateSpace;
	
	// State validators:
	private Map<String, StateValidator> validators = Collections.emptyMap();
	
	/**
	 * Get the state space to be used.
	 * @return State space.
	 */
	protected StateSpace getStateSpace() {
		return stateSpace;
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
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setStateSpace(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setStateValidators(java.util.Map)
	 */
	public void setStateValidators(Map<String, StateValidator> validators) {
		this.validators = validators;
	}

}
