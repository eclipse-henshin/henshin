package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.emf.henshin.statespace.StateSpaceIndex;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;

/**
 * Abstract implementation of {@link StateValidator} interface.
 * @author Christian Krause
 */
public abstract class AbstractStateValidator implements StateValidator {

	// State space index to be used.
	private StateSpaceIndex index;
	
	/**
	 * Get the state space index to be used.
	 * @return State space index.
	 */
	protected StateSpaceIndex getStateSpaceIndex() {
		return index;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}
	
}
