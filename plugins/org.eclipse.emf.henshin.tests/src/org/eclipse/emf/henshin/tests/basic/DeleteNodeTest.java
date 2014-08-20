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
package org.eclipse.emf.henshin.tests.basic;

import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.eclipse.emf.henshin.tests.framework.Tools;
import org.junit.Before;
import org.junit.Test;

/**
 * tests deleting nodes
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 *
 */
public class DeleteNodeTest extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		init("basic/rules/basicTests.henshin");
		setModelGraphProperties("basic/models/deleteNodesModels/", "testmodel");
	}
	
/*
 * ======================================
 * 				DELETING NODES
 * ======================================
 */
	
	@Test
	public void testDeleteRootNode() {
		/**
		 * Delete a root node in a graph
		 */
		loadGraph("graphWithRootNode");
		loadRule("deleteRootNode");
		Rules.assertRuleCanBeApplied(htRuleApp);
		if (Tools.getGraphRoot(htEGraph) != null) {
			throw new AssertionError("expected: Root node deleted, but a root node still exists.");
		}
	}
}
