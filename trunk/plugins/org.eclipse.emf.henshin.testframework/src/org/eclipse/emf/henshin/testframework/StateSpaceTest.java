package org.eclipse.emf.henshin.testframework;

import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResourceFactory;
import org.eclipse.emf.henshin.statespace.util.StateSpaceExplorationHelper;

/**
 * Abstract state space test class.
 * @author Christian Krause
 */
public abstract class StateSpaceTest {
	
	/*
	 * Register the state space resource factory.
	 */
	static {
		StateSpaceResourceFactory.registerInRuntime();
	}
	
	/**
	 * Load a state space from a given file.
	 * @param path Path of the state space file.
	 * @return The loaded state space.
	 */
	protected StateSpace loadStateSpace(String path) {
		return (StateSpace) ModelHelper.loadFile(path);
	}

	/**
	 * Do a full exploration of a state space.
	 * @param manager State space manager.
	 */
	protected void doFullExploration(StateSpaceManager manager) {
		StateSpaceExplorationHelper helper = new StateSpaceExplorationHelper(manager);
		try {
			while (helper.doExplorationStep()) {}
		} catch (StateSpaceException e) {
			throw new RuntimeException(e);
		}
	}
	
}
