package org.eclipse.emf.henshin.statespace.validation;

import java.text.ParseException;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Interface for state space validators.
 * @author Christian Krause
 */
public interface StateSpaceValidator {
	
	/**
	 * Perform the validation for a given state.
	 * @param monitor Progress monitor.
	 * @return Validation result.
	 */
	ValidationResult validate(IProgressMonitor monitor);
	
	/**
	 * Set the state space index to be used.
	 * @param index State space index.
	 */
	void setStateSpaceIndex(StateSpaceIndex index);
	
	/**
	 * Set the property to be validated. It can be assumed
	 * that the state space has been set already before this 
	 * method is called.
	 * @param property Property.
	 * @throws ParseException If the property cannot be parsed.
	 */
	void setProperty(String property) throws ParseException;
	
	/**
	 * Set the state validators to be used.
	 * @param validators State validators.
	 */
	void setStateValidators(Map<String,StateValidator> validators);
}
