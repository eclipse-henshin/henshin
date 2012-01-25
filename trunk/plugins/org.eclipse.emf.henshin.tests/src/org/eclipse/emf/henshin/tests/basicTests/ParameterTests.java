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


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.testframework.GraphTransformations;
import org.eclipse.emf.henshin.testframework.Graphs;
import org.eclipse.emf.henshin.testframework.HenshinLoaders;
import org.eclipse.emf.henshin.testframework.HenshinTest;
import org.eclipse.emf.henshin.testframework.Matches;
import org.eclipse.emf.henshin.testframework.OclQueries;
import org.eclipse.emf.henshin.testframework.Parameters;
import org.eclipse.emf.henshin.testframework.Rules;
import org.eclipse.emf.henshin.testframework.Tools;
import org.eclipse.emf.henshin.testframework.TransformationUnits;
import org.eclipse.emf.henshin.tests.testmodel.Node;
import org.eclipse.emf.henshin.tests.testmodel.Val;
import org.junit.Before;
import org.junit.Test;

/**
 * tests handling of parameters
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 *
 */
public class ParameterTests extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		init("basicTestRules/parameterTests.henshin");
		setModelGraphProperties("basicTestModels/parameterTestsModels/", "testmodel");
	}
	
	@Test
	public void testParameterName() {
		/**
		 * Tests if passing a parameter and using it as an attribute in the LHS works
		 */
		loadGraph("paramTest");
		Node nd1 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename = 'nd1'", htEmfGraph);
		Node nd2 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename = 'nd2'", htEmfGraph);
		System.out.println(nd1 + "..." + nd2);
		loadRule("parameterAttribute", "param_Nodename", "nd1");
		System.out.println(htRuleApp);
		System.out.println("--->" + htRuleApp.getRule().getName());
		Tools.printMatches(htRuleApp.findAllMatches());
		Tools.printGraph(htEmfGraph);
		//loadRule("parameterAttribute", "param_Nodename", nd1.getNodename());	// pass the Nodename
		
		Rules.assertRuleCanBeApplied(htRuleApp);				// apply the rule
		Graphs.assertObjectNotInGraph(nd1, htEmfGraph);			// assert that nd1 has been removed
		Graphs.assertObjectInGraph(nd2, htEmfGraph);			// assert that nd2 is still there
	}
	
	@Test
	public void testParameterObject() {
		/**
		 * Tests if passing a parameter and using it as an object in the LHS works
		 */
		loadGraph("paramTest");
		Node nd1 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename = 'nd1'", htEmfGraph);
		Node nd2 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename = 'nd2'", htEmfGraph);
		loadRule("parameterObject", "param_nd", nd1);		// pass the Node directly
		
		System.out.println(htRuleApp);
		System.out.println("--->" + htRuleApp.getRule().getName());
		Tools.printMatches(htRuleApp.findAllMatches());
		Tools.printGraph(htEmfGraph);
		
		Rules.assertRuleCanBeApplied(htRuleApp);
		Graphs.assertObjectNotInGraph(nd1, htEmfGraph);
		Graphs.assertObjectInGraph(nd2, htEmfGraph);
	}

	@Test
	public void testParameterInOut1() {
		/**
		 * Input attribute name, output Node
		 */
		loadGraph("paramTest");
		
		Node nd1 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='nd1'", htEmfGraph);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param_ndname", "nd1");
		loadRule("parameterInOut", params);
		
		Rules.assertRuleCanBeApplied(htRuleApp);

		Tools.printParameterMappings(htRuleApp);
		
		Parameters.assertParameterMappingEquals(htRuleApp.getComatch(), "param_node", nd1);
	}
	
	@Test
	public void testParameterInOut2() {
		/**
		 * Input Node, output attribute name
		 */
		loadGraph("paramTest");
		loadRule("parameterInOut");
		
		Node nd1 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='nd1'", htEmfGraph);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param_node", nd1);
		//loadRule("parameterInOut", Tools.createParameterMapping(params, htRule));
		
		loadRule("parameterInOut", params);
		Rules.assertRuleCanBeApplied(htRuleApp);
		System.out.println("----");
		Tools.printParameterMappings(htRuleApp);
		
		Parameters.assertParameterMappingEquals(htRuleApp.getMatch(), "param_ndname", "nd1");
		Parameters.assertParameterMappingEquals(htRuleApp.getComatch(), "param_ndname", "nd1");
	}
	
	@Test
	public void testParameterInOut3() {
		/**
		 * Input nothing, output attribute name and node
		 */
		loadGraph("paramTest");
		loadRule("parameterInOut");
		
		Rules.assertRuleCanBeApplied(htRuleApp);

		String paramNdname = ((String) htRuleApp.getMatch().getParameterValues().values().toArray()[0]);
		Node matchedNode = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='" + paramNdname + "'", htEmfGraph);

		System.out.println("------");
		Tools.printParameterMappings(htRuleApp);
		
		Parameters.assertParameterMappingEquals(htRuleApp.getComatch(), "param_node", matchedNode);
		Parameters.assertParameterMappingEquals(htRuleApp.getComatch(), "param_ndname", matchedNode.getNodename());
		
	}
	
	@Test
	public void testParameterTu() {
		/**
		 * Tests if parameters are correctly handled in transformation units
		 */
		loadGraph("paramTest");
		loadTu("parameterUnit", "tu_param_in", "nd1");
		Node matchedNode = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='nd1'", htEmfGraph);
				
		TransformationUnits.assertTuCanBeExecuted(htUnitApp);
		Graphs.assertObjectNotInGraph(matchedNode, htEmfGraph);
	}
	
	
	@Test
	public void testParameterComposition2() {
		loadGraph("paramTest");
		loadRule("parameterComposition2");
		Val vl = (Val) Tools.getFirstElementFromOCLQueryResult("self.valname='vl1'", htEmfGraph);
		htRuleApp.setParameterValue("vl", vl);
		htRuleApp.setParameterValue("p1", 20);
		htRuleApp.setParameterValue("p2", 30);
		Rules.assertRuleCanBeApplied(htRuleApp);
		
		double compositionResult = Double.valueOf(vl.getValname());
		junit.framework.Assert.assertEquals(50.0, compositionResult);
		//junit.framework.Assert.assertEquals("50", vl.getValname());
	}
	
	@Test
	public void testParameterComposition3() {
		loadGraph("paramTest");
		loadRule("parameterComposition2");
		Val vl = (Val) Tools.getFirstElementFromOCLQueryResult("self.valname='vl1'", htEmfGraph);
		htRuleApp.setParameterValue("vl", vl);
		htRuleApp.setParameterValue("p1", "20");
		htRuleApp.setParameterValue("p2", "30");
		Rules.assertRuleCanBeApplied(htRuleApp);
		junit.framework.Assert.assertEquals("2030", vl.getValname());
	}
	
	@Test
	public void testParameterComposition1() {
		loadGraph("paramTest");
		loadRule("parameterComposition1");
		Val vl = (Val) Tools.getFirstElementFromOCLQueryResult("self.valname='vl1'", htEmfGraph);
		htRuleApp.setParameterValue("vl", vl);
		htRuleApp.setParameterValue("p1", 20);
		htRuleApp.setParameterValue("p2", 30);
		Rules.assertRuleCanBeApplied(htRuleApp);
		junit.framework.Assert.assertEquals(50, vl.getIntvl());
	}
	
	@Test
	public void testAttributeCondition1() {
		loadGraph("paramTest");
		loadRule("attribCond1");
		GraphTransformations.assertTransformsGraph(htRule, htEmfGraph, HenshinLoaders.loadGraph(getGraphURI("graphAfter_attribCond1")), 0.9);
	}
	
	// ----
	
	@Test
	public void testEmptyRuleWithAttributeCondition() {
		System.out.println("--------------------------- empty rule with attrib cond ---------------");
		loadGraph("paramTest");
		loadRule("emptyRuleWithAttribCond");
		htRuleApp.setParameterValue("p1", 10);
		htRuleApp.setParameterValue("p2", 20);
		htRuleApp.setParameterValue("p3", 30);
		Rules.assertRuleCanBeApplied(htRuleApp);
		Tools.printParameterMappings(htRuleApp);
	}
	
	@Test
	public void testMultipleAttributeConditions1() {
		loadGraph("paramTest");
		loadRule("multipleAttributeConditions");
		GraphTransformations.assertTransformsGraph(htRule, htEmfGraph, HenshinLoaders.loadGraph(getGraphURI("graphAfter_multipleAttribCond2")), 0.9);
		Tools.printGraph(htEmfGraph);
	}
	
	@Test
	public void testMultipleAttributeConditions2() {
		loadGraph("graphBefore_multipleAttribCond1");
		loadRule("multipleAttributeConditions");
		GraphTransformations.assertGraphIsNotChanged(htRule, htEmfGraph, 0.9);
		Tools.printGraph(htEmfGraph);
	}

	// tests a parameter in a nested condition (PAC). If the test succeeds, one Node should be matched
	@Test
	public void testParameterInNestedCondition1() {
		loadGraph("paramTest");
		loadRule("parameterInNestedCondition1");
		
		htRuleApp.setParameterValue("p1", "ndVal");
		
		//Collection<? extends EObject> nodesInGraph = Tools.getOCLQueryResults("self.oclIsKindOf(Node)", htEmfGraph);
		Node ndVal = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='ndVal'", htEmfGraph);
		
		Matches.assertObjectContainedInAllMatches(htRuleApp, ndVal);
		Tools.printMatches(htRuleApp.findAllMatches());
	}
	
	// tests a parameter in a nested condition (NAC). If the test succeeds, nd1 and nd2 should be matched
	@Test
	public void testParameterInNestedCondition2() {
		loadGraph("paramTest");
		loadRule("parameterInNestedCondition2");
		
		htRuleApp.setParameterValue("p1", "ndVal");
		
		//Collection<? extends EObject> nodesInGraph = Tools.getOCLQueryResults("self.oclIsKindOf(Node)", htEmfGraph);
		Node ndVal = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='ndVal'", htEmfGraph);
		
		Matches.assertObjectContainedInNoMatch(htRuleApp, ndVal);
		
		Node nd1 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='nd1'", htEmfGraph);
		Node nd2 = (Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='nd2'", htEmfGraph);
		
		Matches.assertObjectContainedInAtLeastOneMatch(htRuleApp, nd1);
		Matches.assertObjectContainedInAtLeastOneMatch(htRuleApp, nd2);
		
		Tools.printMatches(htRuleApp.findAllMatches());
	}
	
}