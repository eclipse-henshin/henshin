package org.eclipse.emf.henshin.statespace.validation;

import java.text.ParseException;

import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Abstract state space validator implementation.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceValidator implements StateSpaceValidator {

	// Property to be checked:
	protected String property;
	
	// State space index:
	protected StateSpaceIndex index;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setProperty(java.lang.String)
	 */
	@Override
	public void setProperty(String property) throws ParseException {
		this.property = property;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.Validator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	@Override
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}

}
