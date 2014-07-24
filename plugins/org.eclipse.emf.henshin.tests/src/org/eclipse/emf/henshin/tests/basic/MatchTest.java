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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Matches;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.eclipse.emf.henshin.tests.framework.Tools;
import org.eclipse.emf.henshin.tests.testmodel.Node;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.eclipse.emf.henshin.tests.testmodel.Val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * tests aspects of matching
 * 
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class MatchTest extends HenshinTest {
	
	@Before
	public void setUp() throws Exception {
		init("basic/rules/basicMatchingTests.henshin");
		setModelGraphProperties("basic/models/matchTestsModels/", "testmodel");
	}
	
	@Test
	public void testACMatchNoNodes() {
		/**
		 * test if no nodes are matched when using a NAC containing the Node to
		 * be matched in the Rule (match (Node) and (NOT Node))
		 */
		loadGraph("manyNodes");
		loadRule("acMatchNoNode");
		Rules.assertRuleHasNoMatch(htRule, htEGraph, null, htEngine);
	}
	
	@Test
	public void testMatchAllNodes() {
		// test if all Nodes are matched. First, get all Nodes via OCL, then
		// check if they are
		// contained in the match produced by the Rule 'matchAllNodes'
		loadGraph("manyNodes");
		loadRule("matchAllNodes");
		
		Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, 32);
		
		Collection<? extends EObject> nodesInGraph = Tools.getOCLQueryResults(
				"self.oclIsKindOf(Node)", htEGraph);
		
		Matches.assertGroupIsMatched(htRule, htEGraph, null, htEngine, nodesInGraph);
	}
	
	@Test
	public void testACMatchAllNodes() {
		/**
		 * test if all Nodes are matched when using a PAC containing the Node to
		 * be matched in the Rule (match (Node) and (Node))
		 */
		loadGraph("manyNodes");
		loadRule("acMatchAllNodes");
		Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, 32);
		
		Collection<? extends EObject> nodesInGraph = Tools.getOCLQueryResults(
				"self.oclIsKindOf(Node)", htEGraph);
		Matches.assertGroupIsMatched(htRule, htEGraph, null, htEngine, nodesInGraph);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMatchNodesWithVals() {
		/**
		 * test matching Nodes with unidirectional edges to Vals
		 */
		loadGraph("nodesAndVals");
		loadRule("matchNodesAndVals");
		
		Collection<Node> foundNodes = (Collection<Node>) Tools.getOCLQueryResults(
				"self.nodename = 'v1'", htEGraph);
		foundNodes.add((Node) Tools.getFirstElementFromOCLQueryResult("self.nodename = 'v2'",
				htEGraph));
		foundNodes.add((Node) Tools.getFirstElementFromOCLQueryResult("self.nodename = 'v3'",
				htEGraph));
		Collection<Val> foundVals = (Collection<Val>) Tools.getOCLQueryResults(
				"self.oclIsKindOf(Val)", htEGraph);
		
		Collection<EObject> matchContents = new ArrayList<EObject>();
		matchContents.addAll(foundNodes);
		matchContents.addAll(foundVals);
		
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, matchContents);
	}
	
	@Test
	public void testEmptyRule() {
		/**
		 * test matching the empty Rule (there should be exactly one match, but
		 * this match should be empty)
		 */
		loadGraph("nodesAndVals");
		loadRule("emptyRule");
		Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, 1); // there should be exactly 1
													// match
		Matches.assertNoObjectFromGroupContainedInAnyMatch(htRule, htEGraph, null, htEngine, htEGraph);       // match
																					    	// should
																									// be
																									// empty
	}
	
	@Test
	public void testOnlyNAC() {
		/**
		 * test if Rules with only a NAC are matched correctly (there should be
		 * no match)
		 */
		loadGraph("nodesAndVals");
		loadRule("onlyNAC");
		Rules.assertRuleHasNoMatch(htRule, htEGraph, null, htEngine); // there should be no match
	}
	
	@Test
	public void testOnlyPAC() {
		/**
		 * test if Rules with only a PAC are matched correctly (if the PAC can
		 * be matched, there should be exactly one match, but this match should
		 * be empty)
		 */
		loadGraph("nodesAndVals");
		loadRule("onlyPAC");
		Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, 1); // there should be exactly 1
													// match
		Matches.assertNoObjectFromGroupContainedInAnyMatch(htRule, htEGraph, null, htEngine, htEGraph); // match
																									// should
																									// be
																									// empty
	}
	
	@Test
	public void testNestedPacPac() {
		/**
		 * test if nested Application Conditions work as expected. Tests a PAC
		 * nested inside a PAC
		 * 
		 */
		loadGraph("nestedACTests");
		loadRule("nestedAC_pac-pac");
		// matches Nodes with a parent that has a Vals-reference
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_11'", htEGraph)); // parent is t_1, which
														// has a vals reference
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_12'", htEGraph)); // parent is t_1, which
														// has a vals reference
		//System.out.println("pac-pac:");
		//Tools.printMatches(htRuleApp.findAllMatches());
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	@Test
	public void testNestedPacNac() {
		/**
		 * Tests a NAC tested inside a PAC
		 */
		loadGraph("nestedACTests");
		// matches Nodes with a parent that doesn't have a Vals-reference
		loadRule("nestedAC_pac-nac");
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_1'", htEGraph)); // parent is top, which
														// doesn't have a vals
														// reference
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_111'", htEGraph)); // parent is t_11,
															// which doesn't
															// have a vals
															// reference
		//System.out.println("pac-nac:");
		//Tools.printMatches(htRuleApp.findAllMatches());
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	@Test
	public void testNestedNacPac() {
		/**
		 * Tests a PAC nested inside a NAC
		 */
		loadGraph("nestedACTests");
		loadRule("nestedAC_nac-pac");
		// matches Nodes without (a parent that has a Vals-reference)
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 'unconnected'", htEGraph)); // no parent
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 'top'", htEGraph)); // no parent
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_1'", htEGraph)); // parent is top, and top
														// doesn't have a vals
														// reference
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_111'", htEGraph)); // parent is t_11, and
															// t_11 doesn't have
															// a vals reference
		//System.out.println("nac-pac:");
		//Tools.printMatches(htRuleApp.findAllMatches());
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	@Test
	public void testNestedNacNac() {
		/**
		 * Tests a NAC nested inside a NAC
		 */
		loadGraph("nestedACTests");
		loadRule("nestedAC_nac-nac");
		// matches Nodes without (a parent that (doesn't have a Vals-reference))
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 'unconnected'", htEGraph)); // no parent
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 'top'", htEGraph)); // no parent
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_11'", htEGraph)); // no parent which
														// doesn't have a vals
														// reference (parent is
														// t1, and t1 has a vals
														// reference)
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename = 't_12'", htEGraph)); // no parent which
														// doesn't have a vals
														// reference (parent is
														// t1, and t1 has a vals
														// reference)
		//System.out.println("nac-nac:");
		//Tools.printMatches(htRuleApp.findAllMatches());
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	@Test
	public void testAndNestedCondition() {
		loadGraph("nestedACTests");
		loadRule("andNestedCondition");
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename='t_1'", htEGraph));
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename='t_11'", htEGraph));
		//Tools.printMatches(htRuleApp.findAllMatches());
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	@Test
	public void testOrNestedCondition() {
		loadGraph("nestedACTests");
		loadRule("orNestedCondition");
		Collection<? extends EObject> anticipatedMatches = Tools.getOCLQueryResults(
				"self.oclIsKindOf(Node)", htEGraph);
		anticipatedMatches.remove((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename='unconnected'", htEGraph));
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	@Test
	public void testNotNestedCondition() {
		loadGraph("nestedACTests");
		loadRule("notNestedCondition");
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename='unconnected'", htEGraph));
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult(
				"self.nodename='top'", htEGraph));
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);
	}
	
	// ----
	
	@Test
	public void testNestedConditionMultipleMappings() {
		/**
		 * Tests multiple mappings in nested conditions
		 * Will find a parent Node nd1 which has a Val
		 * and a child Node (of nd1) nd2 which doesn't have a Val
		 * 
		 * i.e. t_11 and t_1
		 */
		loadGraph("nestedACTests");
		loadRule("nestedCondition_multipleMappings");
		Collection<Node> anticipatedMatches = new ArrayList<Node>();
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='t_11'", htEGraph));
		anticipatedMatches.add((Node) Tools.getFirstElementFromOCLQueryResult("self.nodename='t_1'", htEGraph));
		Matches.assertOnlyGroupIsMatched(htRule, htEGraph, null, htEngine, anticipatedMatches);

	}
	
	@Test
	public void intListConstTest() {
		loadGraph("intList");
		loadRule("intListConstTest");
		Rules.assertRuleCanBeApplied(htRuleApp);
		Val val = (Val) htEGraph.getDomain(TestmodelPackage.eINSTANCE.getVal(), true).get(0);
		EList<Integer> result = new BasicEList<Integer>();
		result.add(1);
		result.add(2);
		result.add(3);
		result.add(4);
		Assert.assertEquals("Unexpected integer list result: " + val.getIntlist(), val.getIntlist(), result);
	}

	@Test
	public void intListVarTest() {
		loadGraph("intList");
		loadRule("intListVarTest");
		Rules.assertRuleCanBeApplied(htRuleApp);
		Val val = (Val) htEGraph.getDomain(TestmodelPackage.eINSTANCE.getVal(), true).get(0);
		EList<Integer> result = new BasicEList<Integer>();
		result.add(1);
		result.add(2);
		result.add(3);
		result.add(4);
		Assert.assertEquals("Unexpected integer list result: " + val.getIntlist(), val.getIntlist(), result);
	}

}