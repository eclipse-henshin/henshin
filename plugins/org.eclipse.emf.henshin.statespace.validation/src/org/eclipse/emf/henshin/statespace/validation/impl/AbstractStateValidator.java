package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;

/**
 * Abstract implementation of {@link StateValidator} interface.
 * @author Christian Krause
 */
public abstract class AbstractStateValidator implements StateValidator {

	// State space to be used.
	private StateSpace stateSpace;
	
	/**
	 * Get the state space to be used.
	 * @return State space.
	 */
	protected StateSpace getStateSpace() {
		return stateSpace;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setStateSpace(org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}
	
}
