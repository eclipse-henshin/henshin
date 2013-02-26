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

import org.eclipse.emf.henshin.tests.framework.GraphTransformations;
import org.eclipse.emf.henshin.tests.framework.HenshinLoaders;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests creating and removing edges
 * 
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class CreateDeleteEdges extends HenshinTest {
	
	@Before
	public void setUp() throws Exception {
		init("basicTestRules/basicTests.henshin");
		setModelGraphProperties("basicTestModels/createDeleteEdgesModels/", "testmodel");
	}
	
	/*
	 * ====================================== CREATING/DELETING EDGES
	 * ======================================
	 */

	@Test
	public void testCreateUnidirectionalEdge() {
		loadGraph("graphBefore_edgesCreateUnidirectionalEdge");
		loadRule("edgesCreateUnidirectionalEdge");
		
		GraphTransformations.assertTransformsGraph(htRule, htEGraph,
				HenshinLoaders.loadGraph(getGraphURI("graphAfter_edgesCreateUnidirectionalEdge")),
				htEngine, 0.9);
	}
	
	// removed testCreateBidirectionalEdge1
	// removed testCreateBidirectionalEdge2
	
	@Test
	public void testCreateBidirectionalEdge3() {
		loadGraph("graphBefore_edgesCreateBidirectionalEdge");
		loadRule("edgesCreateBidirectionalEdge3");
		GraphTransformations.assertTransformsGraph(htRule, htEGraph,
				HenshinLoaders.loadGraph(getGraphURI("graphAfter_edgesCreateBidirectionalEdge")),
				htEngine, 0.7);
	}
	
	@Test
	public void testRemoveUnidirectionalEdge() {
		loadGraph("graphAfter_edgesCreateUnidirectionalEdge");
		loadRule("edgesRemoveUnidirectionalEdge");
		GraphTransformations.assertTransformsGraph(htRule, htEGraph,
				HenshinLoaders.loadGraph(getGraphURI("graphBefore_edgesCreateUnidirectionalEdge")),
				htEngine, 0.9);
	}
	
	// removed testRemoveBidirectionalEdge1
	// removed testRemoveBidirectionalEdge2
	
	@Test
	public void testRemoveBidirectonalEdge3() {
		loadGraph("graphAfter_edgesCreateBidirectionalEdge");
		loadRule("edgesRemoveBidirectionalEdge3");
		GraphTransformations.assertTransformsGraph(htRule, htEGraph,
				HenshinLoaders.loadGraph(getGraphURI("graphBefore_edgesCreateBidirectionalEdge")),
				htEngine, 0.7);
	}
	
}
