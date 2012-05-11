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
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

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
	 * Assert that the number of objects in the engine's graph is identical
	 * before and after application of the {@link Rule}
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link Engine}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsIdentical(Rule r, Engine engine)
			throws AssertionError {
		TransformationUnit tu = createTUFromRule(r);
		
		assertNumberOfObjectsIdentical(tu, engine);
	}
	
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
	public static void assertNumberOfObjectsIdentical(Rule r, EGraph graph) throws AssertionError {
		assertNumberOfObjectsIdentical(r, new Engine(graph));
	}
	
	/**
	 * Assert that the number of objects in the engine's graph is identical
	 * before and after execution of the {@link TransformationUnit}
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param engine
	 *            {@link Engine}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsIdentical(TransformationUnit tu, Engine engine)
			throws AssertionError {
		assertNumberOfObjectsIdentical(new UnitApplication(engine, tu));
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
	public static void assertNumberOfObjectsChanged(Rule r, EGraph graph) throws AssertionError {
		assertNumberOfObjectsChanged(r, new Engine(graph));
	}
	
	/**
	 * Assert that the number of objects in the engine's graph is not identical
	 * before and after application of the specified {@link Rule}
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link Engine}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsChanged(Rule r, Engine engine) throws AssertionError {
		assertNumberOfObjectsChanged(createTUFromRule(r), engine);
	}
	
	/**
	 * Assert that the number of objects in the engine's graph is not identical
	 * before and after execution of the specified {@link TransformationUnit}
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param engine
	 *            {@link Engine}
	 * @throws AssertionError
	 */
	public static void assertNumberOfObjectsChanged(TransformationUnit tu, Engine engine)
			throws AssertionError {
		assertNumberOfObjectsChanged(new UnitApplication(engine, tu));
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
	public static void assertGraphIsNotChanged(Rule r, EGraph graph,
			double matchSimilarityThreshold) throws AssertionError {
		assertGraphIsNotChanged(createTUFromRule(r), new Engine(graph), matchSimilarityThreshold);
	}
	
	/**
	 * Assert that applying the {@link Rule} doesn't change the specified
	 * engine's graph<br>
	 * <strong><div style="background-color: red">Graphs with multiple root
	 * objects are not supported yet.</div></strong>
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link Engine}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertGraphIsNotChanged(Rule r, Engine engine,
			double matchSimilarityThreshold) throws AssertionError {
		assertGraphIsNotChanged(createTUFromRule(r), engine, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that executing the {@link TransformationUnit} doesn't change the
	 * specified engine's graph.<br>
	 * <strong><div style="background-color: red">Graphs with multiple root
	 * objects are not supported yet.</div></strong>
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param engine
	 *            {@link Engine}
	 * @param matchSimilarityThreshold
	 *            similarity for EmfComare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertGraphIsNotChanged(TransformationUnit tu, Engine engine,
			double matchSimilarityThreshold) throws AssertionError {
		assertGraphIsNotChanged(new UnitApplication(engine, tu), matchSimilarityThreshold);
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
		
		if (((Engine) (ua.getInterpreterEngine())).getEmfGraph().getRootObjects().size() != 1) {
			throw new AssertionError("!!!! graphs with multiple root objects not supported yet.");
		}
		
		EGraph graph = ((Engine) ua.getInterpreterEngine()).getEmfGraph();
		Collection<EObject> rootObjects = graph.getRootObjects();
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
	public static void assertTransformsGraph(Rule r, Engine engine, EGraph graph2,
			double matchSimilarityThreshold) throws AssertionError {
		assertTransformsGraph(createTUFromRule(r), engine, graph2, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that both graphs are equal after applying the specified Rule on
	 * graph
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EGraph}
	 * @param graph2
	 *            {@link EGraph} to compare graph with
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsGraph(Rule r, EGraph graph, EGraph graph2,
			double matchSimilarityThreshold) throws AssertionError {
		assertTransformsGraph(createTUFromRule(r), graph, graph2, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that both graphs are equal after executing the specified
	 * {@link TransformationUnit} on graph
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param graph
	 *            {@link EGraph} to execute {@link TransformationUnit} on
	 * @param graph2
	 *            {@link EGraph} to compare graph with
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsGraph(TransformationUnit tu, EGraph graph,
			EGraph graph2, double matchSimilarityThreshold) throws AssertionError {
		assertTransformsGraph(tu, new Engine(graph), graph2, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that the engine's graph and specified graph are equal after
	 * executing the {@link TransformationUnit}
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed by the engine
	 * @param engine
	 *            {@link Engine}
	 * @param graph2
	 *            {@link EGraph} to compare engine's graph with
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsGraph(TransformationUnit tu, Engine engine,
			EGraph graph2, double matchSimilarityThreshold) throws AssertionError {
		assertTransformsGraph(new UnitApplication(engine, tu), graph2, matchSimilarityThreshold);
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
		
		if (!(graphsEqual(((Engine) ua.getInterpreterEngine()).getEmfGraph(), graph2,
				matchSimilarityThreshold))) {
			Tools.printGraph(((Engine) ua.getInterpreterEngine()).getEmfGraph());
			throw new AssertionError("expected: Execution of "
					+ ua.getTransformationUnit().getName()
					+ " transforms graph into specified graph, but doesn't.");
		}
		
	}
	
	public static void assertTransformsGraph(RuleApplication ra, EGraph graph2,
			double matchSimilarityThreshold) throws AssertionError {
		
		ra.execute(null);
		
		if (!(graphsEqual(((Engine) ra.getInterpreterEngine()).getEmfGraph(), graph2,
				matchSimilarityThreshold))) {
			Tools.printGraph(((Engine) ra.getInterpreterEngine()).getEmfGraph());
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
	public static void assertNObjectsDeleted(Rule r, EGraph graph, int n) throws AssertionError {
		assertNObjectsDeleted(r, new Engine(graph), n);
	}
	
	/**
	 * Assert that n objects are deleted after applying the specified
	 * {@link Rule} on the engine's graph
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link Engine}
	 * @param n
	 *            number of elements that should be deleted after application
	 * @throws AssertionError
	 */
	public static void assertNObjectsDeleted(Rule r, Engine engine, int n) throws AssertionError {
		assertNObjectsDeleted(createTUFromRule(r), engine, n);
	}
	
	/**
	 * Assert that n objects are deleted after executing the specified
	 * {@link TransformationUnit} on the graph
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param graph
	 *            {@link EGraph}
	 * @param n
	 *            number of elements that should be deleted after execution
	 * @throws AssertionError
	 */
	public static void assertNObjectsDeleted(TransformationUnit tu, EGraph graph, int n)
			throws AssertionError {
		assertNObjectsDeleted(tu, new Engine(graph), n);
	}
	
	/**
	 * Assert that n objects are deleted after executing the specified
	 * {@link TransformationUnit} on the engine's graph
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param engine
	 *            {@link Engine}
	 * @param n
	 *            number of elements that should be deleted after execution
	 * @throws AssertionError
	 */
	public static void assertNObjectsDeleted(TransformationUnit tu, Engine engine, int n)
			throws AssertionError {
		assertNObjectsDeleted(new UnitApplication(engine, tu), n);
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
		if (((Engine) (ua.getInterpreterEngine())).getEmfGraph().getRootObjects().size() != 1) {
			throw new AssertionError("!!!! graphs with multiple root objects not supported yet.");
		}
		
		// copy root object
		
		EGraph graph = ((Engine) ua.getInterpreterEngine()).getEmfGraph();
		Collection<EObject> rootObjects = graph.getRoots();
		EObject[] rootObjectArr = rootObjects.toArray(new EObject[1]);
		EObject rootCopy = EcoreUtil.copy(rootObjectArr[0]);
		EObject rootOrig = rootObjectArr[0];
		
		ua.execute();
		
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
	public static void assertNObjectsCreated(Rule r, EGraph graph, int n) throws AssertionError {
		assertNObjectsCreated(r, new Engine(graph), n);
	}
	
	/**
	 * Asserts that n objects will be created by applying the {@link Rule} on
	 * the engine's graph
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link Engine}
	 * @param n
	 *            number of objects that should have been created
	 * @throws AssertionError
	 */
	public static void assertNObjectsCreated(Rule r, Engine engine, int n) throws AssertionError {
		assertNObjectsCreated(createTUFromRule(r), engine, n);
	}
	
	/**
	 * Asserts that n objects will be created by executing the specified
	 * {@link TransformationUnit} on the graph
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param graph
	 *            {@link EGraph}
	 * @param n
	 *            number of objects that should have been created
	 * @throws AssertionError
	 */
	public static void assertNObjectsCreated(TransformationUnit tu, EGraph graph, int n)
			throws AssertionError {
		assertNObjectsCreated(tu, new Engine(graph), n);
	}
	
	/**
	 * Asserts that n objects will be created by executing the specified
	 * {@link TransformationUnit} on the engine's graph
	 * 
	 * @param tu
	 *            {@link TransformationUnit}
	 * @param engine
	 *            {@link Engine}
	 * @param n
	 *            number of objects that should have been created
	 * @throws AssertionError
	 */
	public static void assertNObjectsCreated(TransformationUnit tu, Engine engine, int n)
			throws AssertionError {
		assertNObjectsCreated(new UnitApplication(engine, tu), n);
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
		if (((Engine) (ua.getInterpreterEngine())).getEmfGraph().getRootObjects().size() != 1) {
			throw new AssertionError("!!!! graphs with multiple root objects not supported yet.");
		}
		
		EGraph graph = ((Engine) ua.getInterpreterEngine()).getEmfGraph();
		Collection<EObject> rootObjects = graph.getRootObjects();
		EObject[] rootObjectArr = rootObjects.toArray(new EObject[1]);
		EObject rootCopy = EcoreUtil.copy(rootObjectArr[0]);
		EObject rootOrig = rootObjectArr[0];
		
		ua.execute();
		
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
	
	// -----
	// TODO: test
	/**
	 * Assert that the specified {@link Rule} is applied to the {@link EGraph}
	 * so that:
	 * <ul>
	 * <li>subgraphBefore is a subgraph of the {@link EGraph} graph</li>
	 * <li>subgraphAfter is a subgraph of the {@link EGraph} graph after the
	 * {@link Rule} has been applied to it.</li>
	 * <li>the {@link Rule} is actually applied.</li>
	 * </ul>
	 * 
	 * @param r
	 *            {@link Rule} to be applied to the {@link EGraph}
	 * @param graph
	 *            {@link EGraph} the {@link Rule} should be applied to
	 * @param subgraphBefore
	 *            {@link EGraph} that should be contained in the
	 *            {@link EGraph} graph before applying the {@link Rule}
	 * @param subgraphAfter
	 *            {@link EGraph} that should be contianed in the
	 *            {@link EGraph} graph after applying the {@link Rule}
	 * @param ignoreChanges
	 *            when deciding whether subgraphBefore and subgraphAfter are
	 *            subgraphs, ignore value changes. Set this if only the
	 *            structure of the subgraphs is ofinterest and not the actual
	 *            values.
	 * @param matchSimilarityThreshold
	 *            similarity for EmfCompare's mapping. Values above (and
	 *            including) this are considered as mapped. Range [0..1]. 0.9 is
	 *            a good value to start with, adjust if problems occur.
	 * @throws AssertionError
	 */
	public static void assertTransformsSubgraph(Rule r, EGraph graph, EGraph subgraphBefore,
			EGraph subgraphAfter, boolean ignoreChanges, double matchSimilarityThreshold)
			throws AssertionError {
		assertTransformsSubgraph(createTUFromRule(r), graph, subgraphBefore, subgraphAfter,
				ignoreChanges, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that the specified {@link Rule} is applied by the
	 * {@link Engine} so that:
	 * <ul>
	 * <li>subgraphBefore is a subgraph of the {@link Engine}'s graph</li>
	 * <li>subgraphAfter is a subgraph of the {@link Engine}'s graph after
	 * the {@link Rule} has been applied.</li>
	 * <li>the {@link Rule} is actually applied.</li>
	 * </ul>
	 * 
	 * @param r
	 *            {@link Rule} to be applied by the {@link Engine}.
	 * @param engine
	 *            {@link Engine} that should apply the {@link Rule}
	 * @param subgraphBefore
	 *            {@link EGraph} that should be contained in the
	 *            {@link Engine}'s graph before applying the {@link Rule}
	 * @param subgraphAfter
	 *            {@link EGraph} that should be contained in the
	 *            {@link Engine}'s graph after applying the {@link Rule}
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
	public static void assertTransformsSubgraph(Rule r, Engine engine, EGraph subgraphBefore,
			EGraph subgraphAfter, boolean ignoreChanges, double matchSimilarityThreshold)
			throws AssertionError {
		assertTransformsSubgraph(createTUFromRule(r), engine, subgraphBefore, subgraphAfter,
				ignoreChanges, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that the specified {@link TransformationUnit} is executed on the
	 * {@link EGraph} so that:
	 * <ul>
	 * <li>subgraphBefore is a subgraph of graph</li>
	 * <li>subgraphAfter is a subgraph of graph after the
	 * {@link TransformationUnit} has been executed on it</li>
	 * <li>the {@link TransformationUnit} is actually executed</li>
	 * </ul>
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed on the
	 *            {@link EGraph}
	 * @param graph
	 *            {@link EGraph} the {@link TransformationUnit} should be
	 *            executed on
	 * @param subgraphBefore
	 *            {@link EGraph} that should be contained in the
	 *            {@link EGraph} graph before executing the
	 *            {@link TransformationUnit}
	 * @param subgraphAfter
	 *            {@link EGraph} that should be contained in the
	 *            {@link EGraph} graph after executing the
	 *            {@link TransformationUnit}
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
	public static void assertTransformsSubgraph(TransformationUnit tu, EGraph graph,
			EGraph subgraphBefore, EGraph subgraphAfter, boolean ignoreChanges,
			double matchSimilarityThreshold) throws AssertionError {
		assertTransformsSubgraph(tu, new Engine(graph), subgraphBefore, subgraphAfter,
				ignoreChanges, matchSimilarityThreshold);
	}
	
	/**
	 * Assert that the specified {@TransformationUnit} is
	 * executed so that:
	 * <ul>
	 * <li>subgraphBefore is a subgraph of the {@link Engine}'s graph</li>
	 * <li>subgraphAfter is a subgraph of the {@link Engine}'s graph after
	 * executing the {@link TransformationUnit}</lI>
	 * <li>the {@link TransformationUnit} is actually executed</li>
	 * </ul>
	 * 
	 * @param tu
	 *            {@link TransformationUnit} to be executed
	 * @param engine
	 *            {@link Engine} the {@link TransformationUnit} should be
	 *            executed by
	 * @param subgraphBefore
	 *            {@link EGraph} that should be contained in the
	 *            {@link Engine}'s graph before executing the
	 *            {@link TransformationUnit}
	 * @param subgraphAfter
	 *            {@link EGraph} that should be contained in the
	 *            {@link Engine}'s graph after executing the
	 *            {@link TransformationUnit}
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
	public static void assertTransformsSubgraph(TransformationUnit tu, Engine engine,
			EGraph subgraphBefore, EGraph subgraphAfter, boolean ignoreChanges,
			double matchSimilarityThreshold) throws AssertionError {
		assertTransformsSubgraph(new UnitApplication(engine, tu), subgraphBefore, subgraphAfter,
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
		EGraph mainGraph = ((Engine) ua.getInterpreterEngine()).getEmfGraph();
		
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
		
		if (!(ua.execute())) {
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
	
	private static TransformationUnit createTUFromRule(Rule r) {
		return Tools.createTUFromRule(r);
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
