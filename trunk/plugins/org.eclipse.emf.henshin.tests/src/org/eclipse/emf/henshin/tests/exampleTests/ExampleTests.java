package org.eclipse.emf.henshin.tests.exampleTests;

import org.eclipse.emf.henshin.examples.sierpinski.SierpinskiBenchmark;
import org.junit.Test;

/**
 * Tests implemented in examples (see the examples plug-in)
 * @author Christian Krause
 *
 */
public class ExampleTests {
	
	public static final String EXAMPLES_PATH = "../org.eclipse.emf.henshin.examples/";
	
	/**
	 * Sierpinski triangle example.
	 */
	@Test
	public void testSierpinski() {
		SierpinskiBenchmark.run(EXAMPLES_PATH + SierpinskiBenchmark.PATH, 10);
	}
	
}
