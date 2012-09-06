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
package org.eclipse.emf.henshin.tests.basicTests;

import org.eclipse.emf.henshin.testframework.GraphTransformations;
import org.eclipse.emf.henshin.testframework.Graphs;
import org.eclipse.emf.henshin.testframework.HenshinLoaders;
import org.eclipse.emf.henshin.testframework.HenshinTest;
import org.eclipse.emf.henshin.testframework.Units;
import org.junit.Before;
import org.junit.Test;

/**
 * tests execution of transformationUnits
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 *
 */
public class TransformationUnitTests extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		init("basicTestRules/transformationUnitTests.henshin");
		setModelGraphProperties("basicTestModels/transformationUnitTestsModels/", "testmodel");
	}
	
	
	// -------- amalgamation unit ---------
	@Test
	public void amalgamationUnitTest() {
		loadGraph("graphBefore_amalgamationUnit");
		loadRule("amalgamationUnitTest_krl_findNode", "ndname", "R");		
		GraphTransformations.assertTransformsGraph(htRuleApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_amalgamationUnit")), 0.9);
		//GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_amalgamationUnit")), 0.9);
	}
	
	// -------- counted unit ---------
	@Test
	public void loopUnitTest() {
		/**
		 * loop unit runs until no match can be found
		 * (count = -1)
		 */
		System.out.println("loopUnitTest");
		loadGraph("graphBefore_countedUnit");
		loadTu("countedUnitTest");
		//((CountedUnit) htUnitApp.getTransformationUnit()).setCount(-1);
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_countedUnit")), 0.9);
	}
	
	
	//TODO: Figure out which tests are needed when using LoopUnits
//	@Test
//	public void countedUnitTest2() {
//		/**
//		 * counted unit runs 8 iterations, removing all child nodes in the graph
//		 * (count = 8)
//		 */
//		System.out.println("countedUnitTest2");
//		loadGraph("graphBefore_countedUnit");
//		loadTu("countedUnitTest");
//		Tools.printGraph(htEmfGraph);
//		((CountedUnit) htUnitApp.getTransformationUnit()).setCount(8);
//		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_countedUnit")), 0.9);
//	}
//	
//	@Test(expected=AssertionError.class)
//	public void countedUnitTest3() {
//		/**
//		 * counted unit runs 10 iterations, and should therefore fail to transform the graph
//		 * (count = 10)
//		 */
//		System.out.println("countedUnitTest3--should fail");
//		loadGraph("graphBefore_countedUnit");
//		loadTu("countedUnitTest");
//		((CountedUnit) htUnitApp.getTransformationUnit()).setCount(10);
//		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_countedUnit")), 0.9);
//	}
//	
//	@Test
//	public void countedUnitTest3ReturnValue() {
//		/**
//		 * counted unit runs 10 iterations, which is not possible with the loaded graph, and should therefore return "false"
//		 */
//		loadGraph("graphBefore_countedUnit");
//		loadTu("countedUnitTest");
//		((CountedUnit) htUnitApp.getTransformationUnit()).setCount(10);
//		Assert.assertFalse(htUnitApp.execute());
//	}
//	
//	@Test
//	public void countedUnitTest3Undo() {
//		/**
//		 * counted unit runs 10 iterations, which is not possible with the loaded graph,
//		 * so the RuleApplications are undone. This means the graph should be
//		 * identical before and after running the counted unit.
//		 */
//		System.out.println("countedUnitTest3Undo");
//		loadGraph("graphBefore_countedUnit");
//		loadTu("countedUnitTest");
//		((CountedUnit) htUnitApp.getTransformationUnit()).setCount(10);
//		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphBefore_countedUnit")), 0.9);
//	}
//	
//	@Test(expected=AssertionError.class)
//	public void countedUnitTest4() {
//		/**
//		 * counted unit runs -2 iterations, and should therefore fail
//		 * (count = -2)
//		 */
//		System.out.println("countedUnitTest4--should fail");
//		loadGraph("graphBefore_countedUnit");
//		loadTu("countedUnitTest");
//		((CountedUnit) htUnitApp.getTransformationUnit()).setCount(-2);
//		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_countedUnit")), 0.9);
//	}
//	
	// removed countedUnitTest4ReturnValue

	
	// -------- priority unit ---------
	@Test
	public void priorityUnitTest() {
		/**
		 * Graph:
		 * 
		 *           0      Q...vl3                0  Q...vl3          0   Q...vl3        0		Q
		 *	        / \                           / \                   \                  \
		 *	 vl1...L   R               -->  vl1..L   R          -->      R           -->    R
		 *	      /
		 *	vl2..LL
		 * 	
		 * 1) first run of priority unit should remove vl2, LL and edges	(deleteNodeWithVal)
		 * 2) second run of priority unit should remove vl1, L and edges	(deleteNodeWithVal)
		 * 3) third run of priority unit should remove vl3 					(deleteVal)
		 */
		
		loadGraph("graphBefore_priorityUnit");
		loadTu("priorityUnitTest");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_priorityUnit_1")), 0.9);
		loadTu("priorityUnitTest");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_priorityUnit_2")), 0.9);
		//Tools.printGraph(htEmfGraph);
		//loadTu("priorityUnitTest");
		//System.out.println(htUnitApp.execute());
		loadTu("priorityUnitTest");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_priorityUnit_3")), 0.9);
	}
	
	@Test
	public void sequentialUnitTest() {
		loadGraph("graphBefore_priorityUnit");
		loadTu("sequentialUnitTest");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_sequentialUnit")), 0.9);
	}
	
	@Test
	public void sequentialUnitTestUndo() {
		loadGraph("graphBefore_sequentialUnitUndo");
		loadTu("sequentialUnitTest");	// unit will fail
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphBefore_sequentialUnitUndo")), 0.9);
	}
	
	@Test
	public void sequentialUnitTestUndo2() {
		loadGraph("graphBefore_priorityUnit");
		loadTu("sequentialUnitTest");
		// execute tu and undo
		Units.assertTuCanBeExecuted(htUnitApp);
		htUnitApp.undo(null);
		Graphs.assertGraphsEqual(htEGraph, HenshinLoaders.loadGraph(getGraphURI("graphBefore_priorityUnit")), 0.9);
		// execute tu again
		loadTu("sequentialUnitTest");
		Units.assertTuCanBeExecuted(htUnitApp);
		Graphs.assertGraphsEqual(htEGraph, HenshinLoaders.loadGraph(getGraphURI("graphAfter_sequentialUnit")), 0.9);
	}
	
	// ---- conditional unit ----
	
	@Test
	public void conditionalUnitTest() {
		loadGraph("graphWithCont");
		loadTu("conditionalUnitTest");
		htUnitApp.setParameterValue("valname", "vl1");
		htUnitApp.setParameterValue("vlintvl", "15");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_conditionalUnit1")), 0.9);
		loadTu("conditionalUnitTest");
		htUnitApp.setParameterValue("valname", "vl1");
		htUnitApp.setParameterValue("vlintvl", "20");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_conditionalUnit2")), 0.9);
	}
	
	// ---- independent unit ----

	@Test
	public void independentUnitTest() {
		boolean rule1First = false, rule2First = false;

		for (int i = 0; i < 100; i++) {
			loadGraph("graphWithCont");
			loadTu("independentUnitTest");
			Units.assertTuCanBeExecuted(htUnitApp);
			if (Graphs.graphsEqual(htEGraph, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit1a")), 0.9)) {
				Graphs.assertGraphsEqual(htEGraph, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit1a")), 0.9);
				loadTu("independentUnitTest");
				GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit2a")), 0.9);
				rule1First = true;
			} else {
				Graphs.assertGraphsEqual(htEGraph, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit1b")), 0.9);
				loadTu("independentUnitTest");
				GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit2b")), 0.9);
				rule2First = true;
			}
			
			if (rule1First && rule2First) {
				System.out.println("terminated after " + i + " tries.");
				return;
			}
		}
		
		throw new AssertionError("Independent unit selected the same Rule 100 times in a row. Maybe there is something wrong.");
		
	}
	
	// -----
	
	@Test
	public void testAmalgamationUnitAttributeInKernelRuleNac() {
		loadGraph("graphBefore_amu_attributeInKernelNac");
		//loadRule("amu2_krl");
		//Tools.printMatches(htRuleApp.findAllMatches());
		loadTu("loop_amu_attributeInKernelNac");
		Units.assertTuCanBeExecuted(htUnitApp);
		//Tools.persist(Tools.getGraphRoot(htEmfGraph), "gt_amu2.testmodel");
	}
	
	@Test
	public void testEmptyIndependentUnit() {
		loadGraph("graphBefore_countedUnit");
		loadTu("emptyIndependentUnit");
		Units.assertTuCanNotBeExecuted(htUnitApp);
		//htUnitApp.undo(null);
		//GraphTransformations.assertGraphIsNotChanged(htUnitApp, 0.9);
	}
	
	@Test
	public void testEmptySequentialUnit() {
		loadGraph("graphBefore_countedUnit");
		loadTu("emptySequentialUnit");
		Units.assertTuCanBeExecuted(htUnitApp);
		htUnitApp.undo(null);
		GraphTransformations.assertGraphIsNotChanged(htUnitApp, 0.9);
	}

	@Test
	public void testEmptyPriorityUnit() {
		loadGraph("graphBefore_countedUnit");
		loadTu("emptyPriorityUnit");
		Units.assertTuCanNotBeExecuted(htUnitApp);
		//htUnitApp.undo(null);
		//GraphTransformations.assertGraphIsNotChanged(htUnitApp, 0.9);
	}
	
	// graphAfter_IndependentUnit1a
	@Test
	public void testIndependentUnitWithOneSubUnit() {
		loadGraph("graphWithCont");
		loadTu("independentUnitWithOneSubUnit");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit1a")), 0.9);
	}
	
	@Test
	public void testPriorityUnitWithOneSubUnit() {
		loadGraph("graphWithCont");
		loadTu("priorityUnitWithOneSubUnit");
		GraphTransformations.assertTransformsGraph(htUnitApp, HenshinLoaders.loadGraph(getGraphURI("graphAfter_independentUnit1a")), 0.9);
	}

	
}