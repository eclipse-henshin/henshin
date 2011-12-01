package org.eclipse.emf.henshin.tests.stateSpaceTests;

import java.io.File;
import java.util.List;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.testframework.StateSpaceTest;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for state space generation testing.
 * @author Christian Krause
 */
public class Exploration extends StateSpaceTest {

	/**
	 * Single-threaded state space exploration test.
	 * The method is synchronized so that it does not
	 * run in parallel with the multi-threaded test.
	 */
	@Test
	public synchronized void singleThreadedExploration() {
		for (File file : getExplorationTestFiles()) {
			doExplorationTest(file, 1);
		}
	}

	/**
	 * Multi-threaded state space exploration test.
	 * The method is synchronized so that it does not
	 * run in parallel with the single-threaded test.
	 */
	@Test
	public synchronized void multiThreadedExploration() {
		for (File file : getExplorationTestFiles()) {
			doExplorationTest(file, 4);
		}
	}

	
	/*
	 * Do an exploration test.
	 */
	private void doExplorationTest(File stateSpaceFile, int numThreads) {
		
		System.out.println("Exploring state space in " + stateSpaceFile.getName() + "...");
		
		// Load the state space twice:
		StateSpace stateSpace = loadStateSpace(stateSpaceFile.getPath());
		StateSpaceManager manager = StateSpaceFactory.eINSTANCE.createStateSpaceManager(stateSpace, numThreads);
		
		System.gc();
	}
	
	/*
	 * Get the state space exploration test files.
	 */
	private List<File> getExplorationTestFiles() {
		List<File> files = findStateSpaceFiles("stateSpaceTests" + File.separator + "exploration");
		assertFalse("No state space exploration test files found", files.isEmpty());
		return files;
	}
	
}
