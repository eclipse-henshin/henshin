/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.*;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.eclipse.emf.henshin.tests.framework.Tools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests creating edges in an existing rule.
 * 
 * @author Daniel STrüber
 * 
 */
public class AddEdgeToRuleTest extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		init("basic/rules/ruleEditing.henshin");
	}

	@Test
	public void testCreateEdgeBetweenPreserveNodes() {
		loadRule("2preserveNodes");
		Node s = htRule.getLhs().getNode("s");
		Node t = htRule.getLhs().getNode("t");
		htRule.createEdge(s, t, EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		assertTrue(s.getOutgoing().size() == 1);
		assertTrue(isPreserveEdge(s.getOutgoing().get(0)));
	}

	@Test
	public void testCreateEdgeBetweenPreserveNodesWithForbidEdge() {
		loadRule("2preserveNodes1ForbidEdge");
		Node s = htRule.getLhs().getNode("s");
		Node t = htRule.getLhs().getNode("t");
		htRule.createEdge(s, t, EcorePackage.eINSTANCE.getEClass_ESuperTypes());
	
		Node sRhs = htRule.getRhs().getNode("s");
		assertTrue(sRhs.getOutgoing().size() == 1);
		assertTrue(isCreateEdge(sRhs.getOutgoing().get(0)));
	}

	@Test
	public void testCreateEdgeBetweenPreserveNodesWithMultipleNacs() {
		loadRule("2nacs1ofThemWithForbidEdge");
		Node s = htRule.getLhs().getNode("s");
		Node t = htRule.getLhs().getNode("t");
		htRule.createEdge(s, t, EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		
		Node sRhs = htRule.getRhs().getNode("s");
		assertTrue(sRhs.getOutgoing().size() == 1);
		assertTrue(isCreateEdge(sRhs.getOutgoing().get(0)));
	}
	

	@Test
	public void testCreateEdgeBetweenPreserveNodesWithNestedApplicationCondition() {
		loadRule("nestedApplicationCondition");
		Node s = htRule.getLhs().getNode("s");
		Node t = htRule.getLhs().getNode("t");
		htRule.createEdge(s, t, EcorePackage.eINSTANCE.getEClass_ESuperTypes());
		assertTrue(s.getOutgoing().size() == 1);
		assertTrue(isPreserveEdge(s.getOutgoing().get(0)));
	}
	
	private boolean isPreserveEdge(Edge edge) {
		return edge.getAction().getType() == Action.Type.PRESERVE;
	}
	private boolean isCreateEdge(Edge edge) {
		return edge.getAction().getType() == Action.Type.CREATE;
	}

}
