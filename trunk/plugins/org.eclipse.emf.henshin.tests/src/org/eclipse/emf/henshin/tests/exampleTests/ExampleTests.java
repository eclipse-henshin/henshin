package org.eclipse.emf.henshin.tests.exampleTests;

import org.eclipse.emf.henshin.examples.diningphils.DiningPhilsBenchmark;
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

	/**
	 * Dining philosophers example (state space generation).
	 */
	@Test
	public void testDiningPhils() {
		DiningPhilsBenchmark.run(EXAMPLES_PATH + DiningPhilsBenchmark.PATH, 8, 
				Runtime.getRuntime().availableProcessors());
	}

}
