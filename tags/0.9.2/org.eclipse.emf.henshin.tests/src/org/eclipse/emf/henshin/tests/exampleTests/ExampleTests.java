package org.eclipse.emf.henshin.tests.exampleTests;

import org.eclipse.emf.henshin.examples.combpattern.CombBenchmark;
import org.eclipse.emf.henshin.examples.diningphils.DiningPhilsBenchmark;
import org.eclipse.emf.henshin.examples.java2statemachine.Java2StateMachine;
import org.eclipse.emf.henshin.examples.sierpinski.SierpinskiBenchmark;
import org.junit.Test;

/**
 * Tests implemented in examples (see the examples plug-in).
 * @author Christian Krause
 *
 */
public class ExampleTests {
	
	/**
	 * Relative path to the examples plug-in.
	 */
	public static final String EXAMPLES_PATH = "../org.eclipse.emf.henshin.examples/";
	
	/**
	 * Sierpinski triangle example.
	 */
	@Test
	public void testSierpinski() {
		System.out.println();
		SierpinskiBenchmark.run(EXAMPLES_PATH + SierpinskiBenchmark.PATH, 10);
	}

	/**
	 * Java2StateMachine example from TTC 2011.
	 */
	@Test
	public void testJava2StateMachine() {
		System.out.println();
		Java2StateMachine.run(EXAMPLES_PATH + Java2StateMachine.PATH, 
				Java2StateMachine.JAVA_MODEL_SMALL, Java2StateMachine.REFERENCE_STATE_MACHINE, false);
	}

	/**
	 * Comb-pattern example.
	 */
	@Test
	public void testCombPattern() {
		System.out.println();
		CombBenchmark.run(EXAMPLES_PATH + CombBenchmark.PATH);
	}

	/**
	 * Dining philosophers example (state space generation).
	 */
	@Test
	public void testDiningPhils() {
		System.out.println();
		DiningPhilsBenchmark.run(EXAMPLES_PATH + DiningPhilsBenchmark.PATH, 8, 
				Runtime.getRuntime().availableProcessors());
	}

}
