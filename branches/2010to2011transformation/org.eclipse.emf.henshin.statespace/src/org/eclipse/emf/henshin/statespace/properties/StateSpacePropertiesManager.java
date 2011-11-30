package org.eclipse.emf.henshin.statespace.properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Interface for state space properties managers.
 * @author Christian Krause
 */
public interface StateSpacePropertiesManager {
	
	/**
	 * Initialize default properties for a state space.
	 * @param stateSpace State space.
	 */
	void initialize(StateSpace stateSpace);
	
	/**
	 * Validate the properties of a state space.
	 * @param stateSpace State space.
	 * @return Validation result.
	 */
	IStatus validate(StateSpace stateSpace);
	
}
