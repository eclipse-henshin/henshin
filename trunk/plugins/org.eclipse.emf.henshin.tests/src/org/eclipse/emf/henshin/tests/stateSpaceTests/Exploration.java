package org.eclipse.emf.henshin.tests.stateSpaceTests;

import java.io.File;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
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
	 * State space exploration test.
	 */
	@Test
	public synchronized void multiThreadedExploration() {
		testAllFiles(Runtime.getRuntime().availableProcessors());
		System.out.println("stateSpaceExploration[success]");
	}
	
	/*
	 * Test all files state space files.
	 */
	private void testAllFiles(int numThreads) {
		System.out.println("-----------");
		for (File file : getExplorationTestFiles()) {
			doExplorationTest(file, numThreads);
		}
	}
	
	/*
	 * Do an exploration test.
	 */
	private void doExplorationTest(File stateSpaceFile, int numThreads) {
		
		// Some debug output:
		System.out.print("Exploring " + stateSpaceFile.getName());
		
		// Load the state space:
		StateSpace stateSpace = loadStateSpace(stateSpaceFile);
		StateSpaceManager manager = StateSpaceFactory.eINSTANCE.createStateSpaceManager(stateSpace, numThreads);
		
		// Reset the state space and explore it:
		doStateSpaceReset(manager);
		doFullExploration(manager, true);
		
		// Free some memory:
		manager.clearCache();
		manager = null;
		System.gc();
		
		// Now load the state space again and compare it with the explored version:
		StateSpace original = loadStateSpace(stateSpaceFile);
		assertIsomorphic(stateSpace, original);
		
		// Finished. Clean up:
		stateSpace = null;
		original = null;
		System.gc();
		
	}
	
	/*
	 * Check whether two state spaces are isomorphic. That means that they
	 * should be structurally equal. Properties that are allowed to differ
	 * are locations of states and the ordering of states and transitions. 
	 */
	private void assertIsomorphic(StateSpace s1, StateSpace s2) {
		
		// Make sure the equality helpers are equal:
		assertTrue("Incompatible equality helpers", EcoreUtil.equals(s1.getEqualityHelper(), s2.getEqualityHelper()));

		// Compare the number of states:
		assertTrue("Different number of states: " + s1.getStates().size() + "!=" + s2.getStates().size(),
				s1.getStates().size()==s2.getStates().size());

		// Compare the number of initial states:
		assertTrue("Different number of initial states: " + s1.getInitialStates().size() + "!=" + s2.getInitialStates().size(),
				s1.getInitialStates().size()==s2.getInitialStates().size());

		// Compare the number of open states:
		assertTrue("Different number of open states: " + s1.getOpenStates().size() + "!=" + s2.getOpenStates().size(),
				s1.getOpenStates().size()==s2.getOpenStates().size());

		// Compare the number of transitions:
		assertTrue("Different number of transitions: " + s1.getTransitionCount() + "!=" + s2.getTransitionCount(),
				s1.getTransitionCount()==s2.getTransitionCount());
		
		// do more tests here...
		
	}
	
	/*
	 * Get the state space exploration test files.
	 */
	private List<File> getExplorationTestFiles() {
		File dir = new File("stateSpaceTests");
		List<File> files = findStateSpaceFiles(dir);
		assertFalse("No state space exploration test files found", files.isEmpty());
		return files;
	}
	
}
