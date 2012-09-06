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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.DifferenceKind;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchElement;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * Assertions for changes on graphs/graph transformations.
 * 
 * @see Graphs
 * @see Matches
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class GraphTransformations {
	// test number 30
	
	/**
	 * Assert that the number of objects in the given graph is identical before
	 * and after application of the {@link Rule}
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EGraph}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsIdentical(Rule r, EGraph graph, Engine engine) throws AssertionError {
		RuleApplication ra = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ra.setRule(r);
		ra.setEGraph(graph);
		assertNumberOfObjectsIdentical(ra);
	}
	
	
	/**
	 * Assert that the number of objects in the {@link UnitApplication}'s graph
	 * is identical before and after its execution.
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsIdentical(UnitApplication ua) throws AssertionError {
		int[] sizes = Tools.getGraphSizes(ua);
		if (sizes[0] != sizes[1]) {
			throw new AssertionError("expected: Number of elements before and after execution of "
					+ ua.getUnit().getName() + " identical. Values: <" + sizes[0]
					+ "> -> <" + sizes[1] + ">");
		}
	}
	
	// test number 31
	
	/**
	 * Assert that the number of objects in the graph is not identical before
	 * and after application of the specified {@link Rule}
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EGraph}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsChanged(Rule r, EGraph graph, Engine engine) throws AssertionError {
		RuleApplication ra = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ra.setRule(r);
		ra.setEGraph(graph);
		assertNumberOfObjectsChanged(ra);
	}
	
	/**
	 * Assert that the number of objects in the engine's graph is not identical
	 * before and after execution of the specified {@link Unit}
	 * 
	 * @param tu
	 *            {@link Unit}
	 * @param engine
	 *            {@link Engine}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsChanged(Unit tu, EGraph graph, Engine engine)
			throws AssertionError {
		UnitApplication ua = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		ua.setUnit(tu);
		ua.setEGraph(graph);
		assertNumberOfObjectsChanged(ua);
	}
	
	/**
	 * Assert that the number of objects in the {@link UnitApplication}'s graph
	 * is not identical before and after its execution
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsChanged(UnitApplication ua) throws AssertionError {
		int[] sizes = getGraphSizes(ua);
		if (sizes[0] == sizes[1]) {
			throw new AssertionError("expected: Number of elements before and after execution of "
					+ ua.getUnit().getName() + " different. Values: <" + sizes[0]
					+ "> -> <" + sizes[1] + ">");
		}
	}
	
	// test number 18
	
	/**
	 * Assert that applying the {@link Rule} to the specified graph doesn't
	 * change the graph.<br>
	 * <strong><div style="background-color: red">Graphs with multiple root
	 * objects are not supported yet.</div></strong>
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EGraph}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertGraphIsNotChanged(Rule r, EGraph graph, Engine engine,
			double matchSimilarityThreshold) throws AssertionError {
		RuleApplication ra = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ra.setRule(r);
		ra.setEGraph(graph);
		assertGraphIsNotChanged(ra, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that executing the {@link Unit} doesn't change the
	 * specified engine's graph.<br>
	 * <strong><div style="background-color: red">Graphs with multiple root
	 * objects are not supported yet.</div></strong>
	 * 
	 * @param tu
	 *            {@link Unit}
	 * @param engine
	 *            {@link Engine}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfComare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertGraphIsNotChanged(Unit tu, EGraph graph, Engine engine,
			double matchSimilarityThreshold) throws AssertionError {
		UnitApplication ua = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		ua.setUnit(tu);
		ua.setEGraph(graph);
		assertGraphIsNotChanged(ua, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that the {@link UnitApplication}'s graph is not changed by its
	 * execution.<br>
	 * <strong><div style="background-color: red">Graphs with multiple root
	 * objects are not supported yet.</div></strong>
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertGraphIsNotChanged(UnitApplication ua, double matchSimilarityThreshold)
			throws AssertionError {
		// TODO: support multiple root nodes
		
		if (ua.getEGraph().getRoots().size() != 1) {
			throw new AssertionError("!!!! graphs with multiple root objects not supported yet.");
		}
		
		EGraph graph = ua.getEGraph();
		Collection<EObject> rootObjects = graph.getRoots();
		EObject[] rootObjectArr = rootObjects.toArray(new EObject[1]);
		EObject rootCopy = EcoreUtil.copy(rootObjectArr[0]);
		EObject rootOrig = rootObjectArr[0];
		
		ua.execute(null);
		
		MatchModel matchM;
		try {
			matchM = MatchService.doMatch(rootCopy, rootOrig, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new AssertionError("!!!! execution interrupted.");
		}
		
		for (MatchElement ma : matchM.getMatchedElements()) {
			if (ma.getSimilarity() < matchSimilarityThreshold) {
				throw new AssertionError("Could not match graphs. Threshold exceeded: ("
						+ ma.getSimilarity() + "), threshold " + matchSimilarityThreshold
						+ ". Quite possibly, graph was changed.");
			}
		}
		
		DiffModel diffM = DiffService.doDiff(matchM);
		
		if (diffM.getDifferences().size() != 0) {
			// graph changed
			throw new AssertionError("expected: Graph is not changed, but "
					+ diffM.getDifferences().size() + " changes occured.");
		}
		
	}
	
	// 16, 17
	
	/**
	 * Assert that the engine's graph and specified graph are equal after
	 * applying the specified Rule
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link Engine}
	 * @param graph2
	 *            {@link EGraph} to compare the engine's graph with
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsGraph(Rule r, EGraph graph1, EGraph graph2, Engine engine,
			double matchSimilarityThreshold) throws AssertionError {
		RuleApplication ra = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ra.setRule(r);
		ra.setEGraph(graph1);
		assertTransformsGraph(ra, graph2, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that both graphs are equal after executing the specified
	 * {@link Unit} on graph
	 * 
	 * @param tu
	 *            {@link Unit}
	 * @param graph
	 *            {@link EGraph} to execute {@link Unit} on
	 * @param graph2
	 *            {@link EGraph} to compare graph with
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsGraph(Unit tu, EGraph graph1,
			EGraph graph2, Engine engine, double matchSimilarityThreshold) throws AssertionError {
		UnitApplication ra = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		ra.setUnit(tu);
		ra.setEGraph(graph1);
		assertTransformsGraph(ra, graph2, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that the {@link UnitApplication}'s graph and specified graph are
	 * equal after execution.
	 * 
	 * @param ua
	 *            {@link UnitApplication} to be executed
	 * @param graph2
	 *            {@link EGraph} to compare {@link UnitApplication}'s graph
	 *            with
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsGraph(UnitApplication ua, EGraph graph2,
			double matchSimilarityThreshold) throws AssertionError {
		
		ua.execute(null);
		
		if (!(graphsEqual(ua.getEGraph(), graph2,
				matchSimilarityThreshold))) {
			Tools.printGraph(ua.getEGraph());
			throw new AssertionError("expected: Execution of "
					+ ua.getUnit().getName()
					+ " transforms graph into specified graph, but doesn't.");
		}
		
	}
	
	public static void assertTransformsGraph(RuleApplication ra, EGraph graph2,
			double matchSimilarityThreshold) throws AssertionError {
		
		ra.execute(null);
		
		if (!(graphsEqual(ra.getEGraph(), graph2,
				matchSimilarityThreshold))) {
			Tools.printGraph(ra.getEGraph());
			throw new AssertionError("expected: Execution of " + ra.getRule().getName()
					+ " transforms graph into specified graph, but doesn't.");
		}
		
	}
	
	/**
	 * Assert that n objects are deleted after applying the specified
	 * {@link Rule} on the graph
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EGraph}
	 * @param n
	 *            number of elements that should be deleted after application
	 * @throws AssertionError
	 */
	public static void assertNObjectsDeleted(Rule r, EGraph graph, Engine engine, int n) throws AssertionError {
		RuleApplication ra = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ra.setRule(r);
		ra.setEGraph(graph);
		assertNObjectsDeleted(ra, n);
	}
	
	/**
	 * Assert that n objects are deleted after executing the specified
	 * {@link Unit} on the graph
	 * 
	 * @param tu
	 *            {@link Unit}
	 * @param graph
	 *            {@link EGraph}
	 * @param n
	 *            number of elements that should be deleted after execution
	 * @throws AssertionError
	 */
	public static void assertNObjectsDeleted(Unit tu, EGraph graph, Engine engine, int n)
			throws AssertionError {
		UnitApplication ra = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		ra.setUnit(tu);
		ra.setEGraph(graph);
		assertNObjectsDeleted(ra, n);
	}
	
	/**
	 * Asserts that n objects were deleted from the {@link UnitApplication}'s
	 * graph by its execution.
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @param n
	 *            number of objects that should have been deleted
	 * @throws AssertionError
	 */
	public static void assertNObjectsDeleted(UnitApplication ua, int n) throws AssertionError {
		if (ua.getEGraph().getRoots().size() != 1) {
			throw new AssertionError("!!!! graphs with multiple root objects not supported yet.");
		}
		
		// copy root object
		
		EGraph graph = ua.getEGraph();
		Collection<EObject> rootObjects = graph.getRoots();
		EObject[] rootObjectArr = rootObjects.toArray(new EObject[1]);
		EObject rootCopy = EcoreUtil.copy(rootObjectArr[0]);
		EObject rootOrig = rootObjectArr[0];
		
		ua.execute(null);
		
		MatchModel matchM;
		try {
			matchM = MatchService.doMatch(rootOrig, rootCopy, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new AssertionError("!!!! execution interrupted.");
		}
		
		int dcount = 0;
		
		DiffModel diffM = DiffService.doDiff(matchM);
		for (DiffElement di : diffM.getDifferences()) {
			if (di.getKind().equals(DifferenceKind.DELETION)) {
				dcount++;
			}
		}
		
		if (dcount != n) {
			throw new AssertionError("expected: <" + n + "> elements deleted, but <" + dcount
					+ "> elements were deleted.");
		}
	}
	
	/**
	 * Asserts that n objects will be created by applying the {@link Rule} on
	 * the graph
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EGraph}
	 * @param n
	 *            number of objects that should have been created
	 * @throws AssertionError
	 */
	public static void assertNObjectsCreated(Rule r, EGraph graph, Engine engine, int n) throws AssertionError {
		RuleApplication ra = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ra.setRule(r);
		ra.setEGraph(graph);
		assertNObjectsCreated(ra, n);
	}
	
	/**
	 * Asserts that n objects will be created by executing the specified
	 * {@link Unit} on the graph
	 * 
	 * @param tu
	 *            {@link Unit}
	 * @param graph
	 *            {@link EGraph}
	 * @param n
	 *            number of objects that should have been created
	 * @throws AssertionError
	 */
	public static void assertNObjectsCreated(Unit tu, EGraph graph, Engine engine, int n)
			throws AssertionError {
		UnitApplication ra = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		ra.setUnit(tu);
		ra.setEGraph(graph);
		assertNObjectsCreated(ra, n);
	}
		
	/**
	 * Asserts that n objects will be created by executing the specified
	 * {@link UnitApplication}
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @param n
	 *            number of objects that should have been created
	 * @throws AssertionError
	 */
	public static void assertNObjectsCreated(UnitApplication ua, int n) throws AssertionError {
		if (ua.getEGraph().getRoots().size() != 1) {
			throw new AssertionError("!!!! graphs with multiple root objects not supported yet.");
		}
		
		EGraph graph = ua.getEGraph();
		Collection<EObject> rootObjects = graph.getRoots();
		EObject[] rootObjectArr = rootObjects.toArray(new EObject[1]);
		EObject rootCopy = EcoreUtil.copy(rootObjectArr[0]);
		EObject rootOrig = rootObjectArr[0];
		
		ua.execute(null);
		
		MatchModel matchM;
		try {
			matchM = MatchService.doMatch(rootOrig, rootCopy, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new AssertionError("!!!! execution interrupted.");
		}
		
		int ccount = 0;
		
		DiffModel diffM = DiffService.doDiff(matchM);
		for (DiffElement di : diffM.getDifferences()) {
			if (di.getKind().equals(DifferenceKind.ADDITION)) {
				ccount++;
			}
		}
		
		if (ccount != n) {
			throw new AssertionError("expected: <" + n + "> elements created, but <" + ccount
					+ "> elements were created.");
		}
	}	
	
	/**
	 * Assert that the specified {@link Unit} is executed on the
	 * {@link EGraph} so that:
	 * <ul>
	 * <li>subgraphBefore is a subgraph of graph</li>
	 * <li>subgraphAfter is a subgraph of graph after the
	 * {@link Unit} has been executed on it</li>
	 * <li>the {@link Unit} is actually executed</li>
	 * </ul>
	 * 
	 * @param tu
	 *            {@link Unit} to be executed on the
	 *            {@link EGraph}
	 * @param graph
	 *            {@link EGraph} the {@link Unit} should be
	 *            executed on
	 * @param subgraphBefore
	 *            {@link EGraph} that should be contained in the
	 *            {@link EGraph} graph before executing the
	 *            {@link Unit}
	 * @param subgraphAfter
	 *            {@link EGraph} that should be contained in the
	 *            {@link EGraph} graph after executing the
	 *            {@link Unit}
	 * @param ignoreChanges
	 *            when deciding whether subgraphBefore and subgraphAfter are
	 *            subgraphs, ignore value changes. Set this if only the
	 *            structure of the subgraphs is of interest and not the actual
	 *            values.
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsSubgraph(Unit tu, EGraph graph,
			EGraph subgraphBefore, EGraph subgraphAfter, Engine engine, boolean ignoreChanges,
			double matchSimilarityThreshold) throws AssertionError {
		
		UnitApplication ra = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		ra.setUnit(tu);
		ra.setEGraph(graph);
		assertTransformsSubgraph(ra, subgraphBefore, subgraphAfter,
				ignoreChanges, matchSimilarityThreshold);
	}
	
	
	/**
	 * Assert that the specified {@link UnitApplication} is executed so that:
	 * <ul>
	 * <li>subgraphBefore is a subgraph of the {@link UnitApplication}'s graph
	 * before execution.</li>
	 * <li>subgraphAfter is a subgraph of the {@link UnitApplication}'s graph
	 * after execution.</li>
	 * <li>{@link UnitApplication} is actually executed</li>
	 * </ul>
	 * 
	 * @param ua
	 *            {@link UnitApplication} to be executed
	 * @param subgraphBefore
	 *            {@link EGraph} that should be contained in the
	 *            {@link UnitApplication}'s graph before its execution.
	 * @param subgraphAfter
	 *            {@link EGraph} that should be contained in the
	 *            {@link UnitApplication}'s graph after its execution.
	 * @param ignoreChanges
	 *            when deciding whether subgraphBefore and subgraphAfter are
	 *            subgraphs, ignore value changes. Set this if only the
	 *            structure of the subgraphs is of interest and not the actual
	 *            values.
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsSubgraph(UnitApplication ua, EGraph subgraphBefore,
			EGraph subgraphAfter, boolean ignoreChanges, double matchSimilarityThreshold)
			throws AssertionError {
		
		MatchModel matchM;
		EGraph mainGraph = ua.getEGraph();
		
		// first, check if subgraphBefore is actually a subgraph of the
		// UnitApplication's graph
		
		Map<String, Object> matchOptions = new HashMap<String, Object>();
		matchOptions.put("IGNORE_ID", true);
		matchOptions.put("IGNORE_XMI_ID", true);
		
		try {
			matchM = MatchService.doMatch(getGraphRoot(subgraphBefore), getGraphRoot(mainGraph),
					matchOptions);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AssertionError("interrupted.");
		}
		
		for (MatchElement ma : matchM.getMatchedElements()) {
			System.out.println(ma);
			if (ma.getSimilarity() < matchSimilarityThreshold) {
				// UnitApplication's graph can't be matched to subgraphBefore
				throw new AssertionError(
						"expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, but graph can't be matched to subgraphBefore");
			}
		}
		
		DiffModel diffM = DiffService.doDiff(matchM, true);
		
		for (DiffElement di : diffM.getDifferences()) {
			System.out.println(di);
			/*
			 * if (di.getKind().equals(DifferenceKind.ADDITION)) { //
			 * graphBefore contains elements not in mainGraph, which doesn't
			 * make it a subgraph throw new AssertionError(
			 * "expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, "
			 * +
			 * "but subgraphBefore contains elements not contained in UnitApplication's graph."
			 * ); } else if (!ignoreChanges &&
			 * (di.getKind().equals(DifferenceKind.CHANGE))) { // graphBefore
			 * contains different values, which doesn't make it a subgraph //
			 * this can be ignored by setting ignoreChanges throw new
			 * AssertionError(
			 * "expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, "
			 * +
			 * "but subgraphBefore contains elements with differing values from matched elements contained in UnitApplication's graph."
			 * ); }
			 */
		}
		
		// => graphBefore is a subgraph.
		// execute UnitApplication
		
		if (!(ua.execute(null))) {
			throw new AssertionError(
					"expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, "
							+ "but execution of UnitApplication failed.");
		}
		
		// now, check if subgraphAfter is a subgraph of the UnitApplication's
		// graph
		
		MatchModel match2M;
		
		try {
			match2M = MatchService.doMatch(getGraphRoot(subgraphAfter), getGraphRoot(mainGraph),
					null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AssertionError("interrupted.");
		}
		
		for (MatchElement me : match2M.getMatchedElements()) {
			if (me.getSimilarity() < matchSimilarityThreshold) {
				throw new AssertionError(
						"expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, "
								+ "but subgraphAfter could not be matched.");
			}
		}
		
		DiffModel diff2M = DiffService.doDiff(match2M);
		
		for (DiffElement di : diff2M.getDifferences()) {
			if (di.getKind().equals(DifferenceKind.ADDITION)) {
				// graphBefore contains elements not in mainGraph, which doesn't
				// make it a subgraph
				throw new AssertionError(
						"expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, "
								+ "but subgraphAfter contains elements not contained in UnitApplication's graph.");
			} else if (!ignoreChanges && (di.getKind().equals(DifferenceKind.CHANGE))) {
				// graphBefore contains different values, which doesn't make it
				// a subgraph
				// this can be ignored by setting ignoreChanges
				throw new AssertionError(
						"expected: UnitApplication transforms graph containing subgraphBefore to graph containing subgraphAfter, "
								+ "but subgraphAfter contains elements with differing values from matched elements contained in UnitApplication's graph.");
			}
		}
		
	}
	
	private static int[] getGraphSizes(UnitApplication ua) {
		return Tools.getGraphSizes(ua);
	}
	
	private static boolean graphsEqual(EGraph graph1, EGraph graph2,
			double matchSimilarityThreshold) {
		return Graphs.graphsEqual(graph1, graph2, matchSimilarityThreshold);
	}
	
	private static EObject getGraphRoot(EGraph graph) {
		return Tools.getGraphRoot(graph);
	}
}
