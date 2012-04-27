/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.testframework;

import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.interpreter.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.impl.EmfEngine;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * Assertions for Transformation Units. For assertions on specific objects
 * matched, see {@link Matches}.
 * 
 * @see Matches
 * @see Rules
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class TransformationUnits {
	/**
	 * Assert that the specified {@link TransformationUnit} can be executed
	 * multiple times.
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed
	 * @param graph
	 *            {@link EmfGraph} the {@link TransformationUnit} should be
	 *            executed on
	 * @throws AssertionError
	 */
	public static void assertTransformationUnitCanBeExecutedMultipleTimes(TransformationUnit tu,
			EmfGraph graph) throws AssertionError {
		assertTransformationUnitCanBeExecutedMultipleTimes(tu, new EmfEngine(graph));
	}
	
	/**
	 * Assert that the specified {@link TransformationUnit} can be executed
	 * multiple times.
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed
	 * @param engine
	 *            {@link EmfEngine} the {@link TransformationUnit} should be
	 *            executed by
	 * @throws AssertionError
	 */
	public static void assertTransformationUnitCanBeExecutedMultipleTimes(TransformationUnit tu,
			EmfEngine engine) throws AssertionError {
		assertUnitApplicationCanBeExecutedMultipleTimes(new UnitApplicationImpl(engine, tu));
	}
	
	/**
	 * Assert that the specified {@link UnitApplicationImpl} can be executed
	 * multiple times.
	 * 
	 * @param ua
	 *            {@link UnitApplicationImpl} to be executed
	 * @throws AssertionError
	 */
	public static void assertUnitApplicationCanBeExecutedMultipleTimes(UnitApplicationImpl ua)
			throws AssertionError {
		TransformationUnit tu = ua.getTransformationUnit();
		EmfEngine engine = (EmfEngine) ua.getInterpreterEngine();
		
		ua.execute();
		if (ua.getAppliedRules().size() > 0) {
			// first execution successful, now execute a second time
			
			UnitApplicationImpl ua2 = new UnitApplicationImpl(engine, tu);
			ua2.execute();
			if (ua2.getAppliedRules().size() > 0) {
				// success!
				return;
			}
			throw new AssertionError(
					"expected: UnitApplication can be executed multiple times, but could just be executed once.");
		}
		throw new AssertionError(
				"expected: UnitApplication can be executed multiple times, but couldn't even be executed once.");
		
	}
	
	/**
	 * Assert that the specified {@link TransformationUnit} can be executed at
	 * least n times.
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed
	 * @param graph
	 *            {@link EmfGraph} the {@link TransformationUnit} should be
	 *            executed on
	 * @param n
	 *            minimum number of times the {@link TransformationUnit} should
	 *            be able to be executed
	 * @throws AssertionError
	 */
	public static void assertTransformationUnitCanBeExecutedNTimes(TransformationUnit tu,
			EmfGraph graph, int n) throws AssertionError {
		assertTransformationUnitCanBeExecutedNTimes(tu, new EmfEngine(graph), n);
	}
	
	/**
	 * Assert that the specified {@link TransformationUnit} can be executed at
	 * least n times.
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed
	 * @param engine
	 *            {@link EmfEngine} the {@link TransformationUnit} should be
	 *            executed by
	 * @param n
	 *            minimum number of times the {@link TransformationUnit} should
	 *            be able to be executed
	 * @throws AssertionError
	 */
	public static void assertTransformationUnitCanBeExecutedNTimes(TransformationUnit tu,
			EmfEngine engine, int n) throws AssertionError {
		assertUnitApplicationCanBeExecutedNTimes(new UnitApplicationImpl(engine, tu), n);
	}
	
	/**
	 * Assert that the specified {@link UnitApplicationImpl} can be executed at
	 * least n times.
	 * 
	 * @param ua
	 *            {@link UnitApplicationImpl} to be executed
	 * @param n
	 *            minimum number of times the {@link UnitApplicationImpl} should be
	 *            able to be executed
	 * @throws AssertionError
	 */
	public static void assertUnitApplicationCanBeExecutedNTimes(UnitApplicationImpl ua, int n)
			throws AssertionError {
		TransformationUnit tu = ua.getTransformationUnit();
		EmfEngine engine = (EmfEngine) ua.getInterpreterEngine();
		
		for (int i = 0; i < n; i++) {
			UnitApplicationImpl ua2 = new UnitApplicationImpl(engine, tu);
			ua2.execute();
			if (ua2.getAppliedRules().size() > 0) {
				// do nothing
			} else {
				throw new AssertionError("expected: UnitApplication can be executed at least " + n
						+ " times, but could only be executed " + i + " times");
			}
		}
		
	}
	
	public static void assertTuCanBeExecuted(UnitApplicationImpl ua) throws AssertionError {
		if (!(ua.execute())) {
			throw new AssertionError("expected: UnitApplication can be executed.");
		}
	}
	
	public static void assertTuCanNotBeExecuted(UnitApplicationImpl ua) throws AssertionError {
		if (ua.execute()) {
			throw new AssertionError("expected: UnitApplication can not be executed.");
		}
	}
	
}
