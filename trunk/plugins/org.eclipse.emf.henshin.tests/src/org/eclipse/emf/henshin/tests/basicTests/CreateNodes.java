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

import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.testframework.GraphTransformations;
import org.eclipse.emf.henshin.testframework.HenshinLoaders;
import org.eclipse.emf.henshin.testframework.HenshinTest;
import org.eclipse.emf.henshin.testframework.Rules;
import org.eclipse.emf.henshin.testframework.Tools;
import org.eclipse.emf.henshin.tests.testmodel.Node;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests creating nodes
 * 
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class CreateNodes extends HenshinTest {
	
	@Before
	public void setUp() throws Exception {
		init("basicTestRules/basicTests.henshin");
		setModelGraphProperties("basicTestModels/createNodesModels/", "testmodel");
		loadGraph("graphWithOneNode");
	}
	
	/*
	 * ====================================== CREATING NODES
	 * ======================================
	 */

	@Test
	public void testCreateNodeWithContainmentEdge() {
		/**
		 * Create a node (type: Node) with just a containment edge
		 */
		loadGraph("graphWithCont");
		loadRule("createNodeWithContainmentEdge");
		GraphTransformations.assertTransformsGraph(htRule, htEmfGraph,
				HenshinLoaders.loadGraph(getGraphURI("graphWithOneNode")), 0.9);
	}
	
	@Test
	public void testCreateNodeWithContainmentEdgeAndUnidirectionalEdgeToNode() {
		/**
		 * Create a node (type: Vals) with a containment edge and a
		 * unidirectional edge to another node (type: Node)
		 * 
		 * Node --(hasVals)--> Vals
		 */
		loadRule("createNodeWithUnidirectionalEdge");
		GraphTransformations.assertTransformsGraph(htRule, htEmfGraph, HenshinLoaders
				.loadGraph(getGraphURI("graphAfter_createNodeWithUnidirectionalEdge")), 0.9);
	}
	
	// removed testCreateNodeWithContainmentEdgeAndBidirectionalEdge1
	// removed testCreateNodeWithContainmentEdgeAndBidirectionalEdge2
	
	@Test
	public void testCreateNodeWithContainmentEdgeAndBidirectionalEdge3() {
		loadRule("createNodeWithBidirectionalEdge3");
		GraphTransformations
				.assertTransformsGraph(htRule, htEngine, HenshinLoaders
						.loadGraph(getGraphURI("graphAfter_createNodeWithBidirectionalEdge")), 0.9);
	}
	
	@Test
	public void testCreateRootNode() {
		/**
		 * Create a root node in an empty graph
		 */
		loadGraph(new EmfGraph());
		loadRule("createRootNode");
		Rules.assertRuleCanBeApplied(htRuleApp);
		try {
			Node createdRootNode = (Node) Tools.getGraphRoot(htEmfGraph);
			junit.framework.Assert.assertNotNull(createdRootNode);
			System.out.println(createdRootNode);
		} catch (Exception e) {
			junit.framework.Assert.fail("root node couldn't be created.");
		}
	}
	
	// ----
	
	@Test
	public void edgeCTest() {
		loadGraph("graphWithCont");
		loadRule("edgeCtest");
		GraphTransformations.assertTransformsGraph(htRule, htEngine, HenshinLoaders.loadGraph(getGraphURI("graphAfter_edgeCtest")), 0.9);
	}
}
