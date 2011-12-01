package org.eclipse.emf.henshin.tests.stateSpaceTests;

import java.io.File;
import java.util.List;

import org.eclipse.emf.henshin.testframework.StateSpaceTest;
import org.junit.Test;

/**
 * Test class for state space generation testing.
 * @author Christian Krause
 */
public class StateSpaceGeneration extends StateSpaceTest {

	@Test
	public void stateSpaceGeneration() {
		List<File> files = findStateSpaceFiles("stateSpaceTests" + File.separator + "generation");
		//assert "ff" !files.isEmpty() ;
		for (File file : files) {
			System.out.println("Regenerating " + file.getName() + "...");
		}
	}
	
}
