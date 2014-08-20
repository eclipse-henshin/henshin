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

import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.eclipse.emf.henshin.tests.framework.Tools;
import org.eclipse.emf.henshin.tests.testmodel.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests creating nodes
 * 
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class CreateNodeTest extends HenshinTest {
	
	@Before
	public void setUp() throws Exception {
		init("basic/rules/basicTests.henshin");
		setModelGraphProperties("basic/models/createNodesModels/", "testmodel");
		loadGraph("graphWithOneNode");
	}
	
	/*
	 * ====================================== CREATING NODES
	 * ======================================
	 */
	
	@Test
	public void testCreateRootNode() {
		/**
		 * Create a root node in an empty graph
		 */
		loadGraph(InterpreterFactory.INSTANCE.createEGraph());
		loadRule("createRootNode");
		Rules.assertRuleCanBeApplied(htRuleApp);
		try {
			Node createdRootNode = (Node) Tools.getGraphRoot(htEGraph);
			Assert.assertNotNull(createdRootNode);
			System.out.println(createdRootNode);
		} catch (Exception e) {
			Assert.fail("root node couldn't be created.");
		}
	}
	
}
