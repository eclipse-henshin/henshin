package org.eclipse.emf.henshin.statespace.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Compound properties manager.
 * @author Christian Krause
 */
public class CompoundPropertiesManager implements StateSpacePropertiesManager {
	
	// The managers:
	private List<StateSpacePropertiesManager> managers;
	
	/**
	 * Default constructor.
	 */
	public CompoundPropertiesManager() {
		managers = new ArrayList<StateSpacePropertiesManager>();
	}
	
	/**
	 * Get the properties managers.
	 * @return List of properties managers (modifiable).
	 */
	public List<StateSpacePropertiesManager> getManagers() {
		return managers;
	}
	
	/**
	 * Initialize state space. This delegates to all registered managers.
	 * @param stateSpace State space.
	 */
	@Override
	public void initialize(StateSpace stateSpace) {
		for (StateSpacePropertiesManager manager : managers) {
			manager.initialize(stateSpace);
		}
	}
	
	/**
	 * Computes the 'worst' validation result (highest severity).
	 * If no managers are registered, OK is returned.
	 * @param stateSpace State space.
	 * @return Validation result.
	 */
	@Override
	public IStatus validate(StateSpace stateSpace) {
		IStatus result = Status.OK_STATUS;
		for (StateSpacePropertiesManager manager : managers) {
			IStatus current = manager.validate(stateSpace);
			if (current!=null && current.getSeverity()>result.getSeverity()) {
				result = current;
			}
		}
		return result;
	}
	
}
