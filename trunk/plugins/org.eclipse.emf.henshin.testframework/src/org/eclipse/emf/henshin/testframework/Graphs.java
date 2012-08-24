/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.testframework;

import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchElement;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;

/**
 * Assertions for (static) {@link EGraph}s. For everything related to changes
 * (caused by Rules/TransformationUnits) on graphs, see
 * {@link GraphTransformations}.
 * 
 * @see GraphTransformations
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class Graphs {
	/**
	 * Assert that the specified object is contained in the graph
	 * 
	 * @param obj
	 *            {@link EObject}
	 * @param graph
	 *            {@link EGraphImpl}
	 * @throws AssertionError
	 */
	public static void assertObjectInGraph(EObject obj, EGraph graph) throws AssertionError {
		if (!(graph.contains(obj))) {
			throw new AssertionError("expected: Object " + obj.toString() + " contained in graph");
		}
	}
		
	/**
	 * Assert that the specified object is not contained in the graph
	 * 
	 * @param obj
	 *            {@link EObject}
	 * @param graph
	 *            {@link EGraphImpl}
	 * @throws AssertionError
	 */
	public static void assertObjectNotInGraph(EObject obj, EGraph graph) throws AssertionError {
		if (graph.contains(obj)) {
			throw new AssertionError("expected: Object " + obj.toString()
					+ " not contained in graph");
		}
	}
	
	/**
	 * Assert that both graphs are equal, i.e.
	 * <ul>
	 * <li>all matched elements have a similarity above the
	 * matchSimilarityThreshold</li>
	 * <li>there are no unmatched elements or changes</li>
	 * </ul>
	 * 
	 * @param graph1
	 *            {@link EGraphImpl}
	 * @param graph2
	 *            {@link EGraphImpl}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertGraphsEqual(EGraph graph1, EGraph graph2,
			double matchSimilarityThreshold) throws AssertionError {
		MatchModel matchM;
		try {
			matchM = MatchService.doMatch(graph1.getRoots().toArray(new EObject[1])[0],
					graph2.getRoots().toArray(new EObject[1])[0], null);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new AssertionError("!!!! execution interrupted.");
		}
		
		for (MatchElement ma : matchM.getMatchedElements()) {
			if (ma.getSimilarity() < matchSimilarityThreshold) {
				throw new AssertionError(
						"expected: Graphs equal, but graphs not equal. Reason: Could not match graphs. Threshold exceeded: ("
								+ ma.getSimilarity()
								+ "), threshold "
								+ matchSimilarityThreshold
								+ ".");
			}
		}
		
		DiffModel diffM = DiffService.doDiff(matchM);
		
		if (diffM.getDifferences().size() != 0) {
			throw new AssertionError("expected: Graphs equal, but graphs not equal ("
					+ diffM.getDifferences().size() + " changes occured.)");
		}
	}
	
	/**
	 * Check if two graphs are equal
	 * 
	 * @param graph1
	 *            {@link EGraph}
	 * @param graph2
	 *            {@link EGraph}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @return true - graphs equal; false - graphs unequal
	 */
	public static boolean graphsEqual(EGraph graph1, EGraph graph2,
			double matchSimilarityThreshold) {
		MatchModel matchM;
		
		//System.out.println("-----------");
		try {
			matchM = MatchService.doMatch(graph1.getRoots().toArray(new EObject[1])[0],
					graph2.getRoots().toArray(new EObject[1])[0], null);
		} catch (InterruptedException e) {
			//System.err.println("interrupted");
			return false;
		}
		
		for (MatchElement ma : matchM.getMatchedElements()) {
			//System.out.println("-> " + ma);
			//for (MatchElement sma : ma.getSubMatchElements()) {
			//	System.out.println("   -> " + sma);
			//}
			if (ma.getSimilarity() < matchSimilarityThreshold) {
				System.out.println("graphs not equal->similarity threshold too high");
				return false;
			}
		}
		
		DiffModel diffM = DiffService.doDiff(matchM);
		
		if (diffM.getDifferences().size() == 0) {
			return true;
		} else {
			System.out.println("graphs not equal->differences found");
			for (DiffElement de : diffM.getDifferences()) {
				System.out.println("\t" + de);
			}
		}
		
		return false;
	}
	
}
