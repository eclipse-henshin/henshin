/**
 * <copyright>
 * Copyright (c) 2010-2018 Henshin developers. All rights reserved.
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.tests.framework.Graphs;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Parameters;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.eclipse.emf.henshin.tests.framework.Tools;
import org.eclipse.emf.henshin.tests.framework.Units;
import org.eclipse.emf.henshin.tests.testmodel.Node;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.eclipse.emf.henshin.tests.testmodel.Val;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests handling of parameter kinds.
 *
 * @author Benjamin Heidelmeier
 */
public class ParameterKindTest extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		TestmodelPackage.eINSTANCE.eClass();
		initFactory("testmodel", new XMIResourceFactoryImpl());
		init("basic/rules/parameterKindTests.henshin");
		setEGraphPath("basic/models/parameterTestsModels/", "testmodel");
		loadEGraph("paramTest");
	}

	@Test
	/*
	 * Parameter of kind IN should be settable before rule application,
	 * should not change during rule application
	 * and should not be readable after rule application.
	 */
	public void testParameterKindInRule() {
		Node nd1 = (Node) Tools.getFirstOCLResult("self.nodename = 'nd1'", htEGraph);
		Node nd2 = (Node) Tools.getFirstOCLResult("self.nodename = 'nd2'", htEGraph);
		loadRule("parameterKindInRule", "param_Nodename", "nd1"); // settable before application

		Rules.assertRuleCanBeApplied(htRuleApp);
		// correct usage of the parameter
		Graphs.assertObjectNotInGraph(nd1, htEGraph); // assert that nd1 has been removed
		Graphs.assertObjectInGraph(nd2, htEGraph); // assert that nd2 is still there

		// assert that nd1 is not readable after application
		Parameters.assertParameterNotReadableAfterApplication(htRuleApp, "param_Nodename");
	}

	@Test
	/*
	 * Parameter of kind IN should be settable before unit execution,
	 * should not change during unit execution
	 * and should not be readable after unit execution.
	 */
	public void testParameterKindInUnit() {
		Node nd1 = (Node) Tools.getFirstOCLResult("self.nodename = 'nd1'", htEGraph);
		Node nd2 = (Node) Tools.getFirstOCLResult("self.nodename = 'nd2'", htEGraph);
		loadUnit("parameterKindInUnit");
		htUnitApp.setParameterValue("param_Nodename", "nd1"); // settable before execution
		htUnitApp.setParameterValue("param_dummy", "dummy");

		Units.assertUnitCanBeExecuted(htUnitApp);
		// correct usage of the parameter
		Graphs.assertObjectNotInGraph(nd1, htEGraph); // assert that nd1 has been removed
		Graphs.assertObjectInGraph(nd2, htEGraph); // assert that nd2 is still there
	}

	@Test
	/*
	 * Rule should not be applicable if IN parameter is not set.
	 */
	public void testParameterKindInRuleMustBeSet() {
		loadRule("parameterKindInRule");
		Rules.assertRuleCannotBeApplied(htRuleApp);
	}

	@Test
	/*
	 * Unit should not be executable if IN parameter is not set.
	 */
	public void testParameterKindInUnitMustBeSet() {
		loadUnit("parameterKindInUnit", "param_Nodename", "nd1");
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	/*
	 * Parameter of kind OUT should not be settable before rule application,
	 * should be automatically set during rule application
	 * and should be readable after rule application.
	 */
	public void testParameterKindOutRule() {
		loadRule("parameterKindOutRule");

		Parameters.assertParameterNotSettable(htRuleApp, "param_node_out");	//not settable before application
		Parameters.assertParameterNotSettable(htRuleApp, "param_ndname_out");

		Rules.assertRuleCanBeApplied(htRuleApp);

		//read after application
		String paramNdname = (String) htRuleApp.getResultParameterValue("param_ndname_out");
		Node param_node = (Node) htRuleApp.getResultParameterValue("param_node_out");
		Node matchedNode = (Node) Tools.getFirstOCLResult("self.nodename='" + paramNdname + "'", htEGraph);

		//check whether parameters were correctly set during rule application
		assertEquals(paramNdname, param_node.getNodename());
		assertEquals(param_node, matchedNode);
	}

	@Test
	/*
	 * Parameter of kind OUT should not be settable before unit execution,
	 * should be automatically set during unit execution
	 * and should be readable after unit execution.
	 */
	public void testParameterKindOutUnit() {
		loadUnit("parameterKindOutUnit");

		Parameters.assertParameterNotSettable(htUnitApp, "param_node_out");	//not settable before execution
		Parameters.assertParameterNotSettable(htUnitApp, "param_ndname_out");

		Units.assertUnitCanBeExecuted(htUnitApp);

		//read after execution
		String paramNdname = (String) htUnitApp.getResultParameterValue("param_ndname_out");
		Node param_node = (Node) htUnitApp.getResultParameterValue("param_node_out");
		Node matchedNode = (Node) Tools.getFirstOCLResult("self.nodename='" + paramNdname + "'", htEGraph);

		//check whether parameters were correctly set during unit execution
		assertEquals(paramNdname, param_node.getNodename());
		assertEquals(param_node, matchedNode);
	}

	@Test
	/*
	 * Parameter of kind INOUT should be settable before rule application,
	 * should not change during rule application
	 * and should be readable after rule application.
	 */
	public void testParameterKindInoutRule() {
		loadRule("parameterKindInOutRule", "param_ndname_inout", "nd1");	//set before application

		Parameters.assertParameterNotSettable(htRuleApp, "param_node_inout");
		Rules.assertRuleCanBeApplied(htRuleApp);

		//out parameter is read after application
		Node paramNode = (Node) htRuleApp.getResultParameterValue("param_node_inout");
		assertEquals("nd1", paramNode.getNodename());

		//inout parameter is still readable and has not changed its value
		Parameters.assertParameterMappingEquals(htRuleApp.getResultMatch(), "param_ndname_inout", "nd1");
	}

	@Test
	/*
	 * Parameter of kind INOUT should be settable before unit execution,
	 * should not change during unit execution
	 * and should be readable after unit execution.
	 */
	public void testParameterKindInoutUnit() {
		loadUnit("parameterKindInOutUnit");
		htUnitApp.setParameterValue("param_ndname_inout", "nd1"); //set before execution
		htUnitApp.setParameterValue("param_dummy", "dummy");

		Parameters.assertParameterNotSettable(htUnitApp, "param_node_inout");
		Units.assertUnitCanBeExecuted(htUnitApp);

		//out parameter is read after execution
		Node paramNode = (Node) htUnitApp.getResultParameterValue("param_node_inout");
		assertEquals("nd1", paramNode.getNodename());

		//inout parameter is still readable and has not changed its value
		String paramNdname = (String) htUnitApp.getResultParameterValue("param_ndname_inout");
		assertEquals("nd1", paramNdname);
	}

	@Test
	/*
	 * Rule should not be applicable if INOUT parameter is not set.
	 */
	public void testParameterKindInoutRuleMustBeSet() {
		loadRule("parameterKindInOutRule");
		Rules.assertRuleCannotBeApplied(htRuleApp);
	}

	@Test
	/*
	 * Unit should not be executable if IN parameter is not set.
	 */
	public void testParameterKindInoutUnitMustBeSet() {
		loadUnit("parameterKindInOutUnit", "param_ndname_inout", "nd1");
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	/*
	 * Parameter of kind VAR should not be settable before rule application,
	 * should be settable during rule application
	 * and should not be readable after rule application.
	 */
	public void testParameterKindVarRule() {
		loadRule("parameterKindVarRule", "value_name", "vl2");

		Parameters.assertParameterNotSettable(htRuleApp, "temp");	//not settable before application
		Rules.assertRuleCanBeApplied(htRuleApp);

		Parameters.assertParameterNotReadableAfterApplication(htRuleApp, "temp");	//not readable after application
		Parameters.assertParameterNotReadableAfterApplication(htRuleApp, "value_name");

		//rule correctly applied?
		Val matchedVal = (Val) Tools.getFirstOCLResult("self.valname='" + "vl2" + "'", htEGraph);
		assertEquals(324, matchedVal.getIntvl());
	}

	@Test
	/*
	 * Parameter of kind VAR should not be settable before unit execution,
	 * should be settable during unit execution
	 * and should not be readable after unit execution.
	 */
	public void testParameterKindVarUnit() {
		loadUnit("parameterKindVarUnit");

		Parameters.assertParameterNotSettable(htUnitApp, "nodename_var");	//not settable before execution
		Units.assertUnitCanBeExecuted(htUnitApp);

		//rule correctly applied?
		Node node_out = (Node) htUnitApp.getResultParameterValue("node_out");
		Graphs.assertObjectNotInGraph(node_out, htEGraph);
	}

	@Test
	/*
	 * Parameters of kind IN should not be readable after IndependentUnit execution.
	 * Parameters of kind VAR, INOUT and OUT should be readable after IndependentUnit execution.
	 */
	public void testParameterKindsReadableAfterIndependentUnit() {
		loadUnit("parameterKindsReadableAfterIndependentUnit");
		htUnitApp.setParameterValue("param_in", "nd1");
		htUnitApp.setParameterValue("param_inout", "nd1");

		Units.assertUnitCanBeExecuted(htUnitApp);

		Parameters.assertParameterNotReadableAfterApplication(htUnitApp, "param_in");
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_inout"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_out"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_var"));
	}

	@Test
	/*
	 * Parameters of kind IN should not be readable after SequentialUnit execution.
	 * Parameters of kind VAR, INOUT and OUT should be readable after SequentialUnit execution.
	 */
	public void testParameterKindsReadableAfterSequentialUnit() {
		loadUnit("parameterKindsReadableAfterSequentialUnit");
		htUnitApp.setParameterValue("param_in", "nd1");
		htUnitApp.setParameterValue("param_inout", "nd1");

		Units.assertUnitCanBeExecuted(htUnitApp);

		Parameters.assertParameterNotReadableAfterApplication(htUnitApp, "param_in");
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_inout"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_out"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_var"));
	}

	@Test
	/*
	 * Parameters of kind IN should not be readable after LoopUnit execution.
	 * Parameters of kind VAR, INOUT and OUT should be readable after LoopUnit execution.
	 */
	public void testParameterKindsReadableAfterLoopUnit() {
		loadUnit("parameterKindsReadableAfterLoopUnit");
		htUnitApp.setParameterValue("param_in", "nd1");
		htUnitApp.setParameterValue("param_inout", "nd1");

		Units.assertUnitCanBeExecuted(htUnitApp);

		Parameters.assertParameterNotReadableAfterApplication(htUnitApp, "param_in");
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_inout"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_out"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_var"));
	}

	@Test
	/*
	 * Parameters of kind IN should not be readable after IteratedUnit execution.
	 * Parameters of kind VAR, INOUT and OUT should be readable after IteratedUnit execution.
	 */
	public void testParameterKindsReadableAfterIteratedUnit() {
		loadUnit("parameterKindsReadableAfterIteratedUnit");
		htUnitApp.setParameterValue("param_in", "nd1");
		htUnitApp.setParameterValue("param_inout", "nd1");

		Units.assertUnitCanBeExecuted(htUnitApp);

		Parameters.assertParameterNotReadableAfterApplication(htUnitApp, "param_in");
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_inout"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_out"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_var"));
	}

	@Test
	/*
	 * Parameters of kind IN should not be readable after PriorityUnit execution.
	 * Parameters of kind VAR, INOUT and OUT should be readable after PriorityUnit execution.
	 */
	public void testParameterKindsReadableAfterPriorityUnit() {
		loadUnit("parameterKindsReadableAfterPriorityUnit");
		htUnitApp.setParameterValue("param_in", "nd1");
		htUnitApp.setParameterValue("param_inout", "nd1");

		Units.assertUnitCanBeExecuted(htUnitApp);

		Parameters.assertParameterNotReadableAfterApplication(htUnitApp, "param_in");
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_inout"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_out"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_var"));
	}

	@Test
	/*
	 * Parameters of kind IN should not be readable after ConditionalUnit execution.
	 * Parameters of kind VAR, INOUT and OUT should be readable after ConditionalUnit execution.
	 */
	public void testParameterKindsReadableAfterConditionalUnit() {
		loadUnit("parameterKindsReadableAfterConditionalUnit");
		htUnitApp.setParameterValue("param_in", "nd1");
		htUnitApp.setParameterValue("param_inout", "nd1");

		Units.assertUnitCanBeExecuted(htUnitApp);

		Parameters.assertParameterNotReadableAfterApplication(htUnitApp, "param_in");
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_inout"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_out"));
		assertEquals("nd1", htUnitApp.getResultParameterValue("param_var"));
	}
}
