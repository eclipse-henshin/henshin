package org.eclipse.emf.henshin.interpreter;

import java.util.List;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Parameter assignment interface for storing parameter values.
 * @author Christian Krause
 */
public interface Assignment {

	/**
	 * Get the unit that this assignment refers to.
	 * @return The transformation unit.
	 */
	TransformationUnit getUnit();

	/**
	 * Get the value assigned to a parameter.
	 * @param param The parameter.
	 * @return The assigned value or <code>null</code>.
	 */
	Object getParameterValue(Parameter param);
	
	/**
	 * Set the assigned value for a parameter.
	 * @param param The parameter.
	 * @param value The value to be assigned with the parameter.
	 */
	void setParameterValue(Parameter param, Object value);
	
	/**
	 * Get all parameter values.
	 * @return List of all parameter values.
	 */
	List<Object> getParameterValues();
	
	/**
	 * Clear all values stored in this assignment.
	 */
	void clear();
	
}
