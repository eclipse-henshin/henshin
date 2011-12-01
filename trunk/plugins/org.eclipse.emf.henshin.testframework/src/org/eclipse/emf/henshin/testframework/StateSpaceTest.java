package org.eclipse.emf.henshin.testframework;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;
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
	 * Recursively find state space files in a given path.
	 * @param path Path where to look for state space files.
	 * @return List of full paths of the found state space files.
	 */
	protected List<File> findStateSpaceFiles(String path) {
		List<File> files = new ArrayList<File>();
		addStateSpaceFiles(new File(path), files);
		Collections.sort(files);
		return files;
	}
	
	/*
	 * Recursively search for state space files.
	 */
	private void addStateSpaceFiles(File file, List<File> stateSpaceFiles) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				addStateSpaceFiles(child, stateSpaceFiles);
			}
		} else {
			if (file.getName().endsWith("." + StateSpaceResource.FILE_EXTENSION)) {
				stateSpaceFiles.add(file);
			}
		}
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
