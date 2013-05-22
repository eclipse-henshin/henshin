package org.eclipse.emf.henshin.tests.basicTests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.EdgeImpl;
import org.eclipse.emf.henshin.model.impl.NodeImpl;
import org.eclipse.emf.henshin.model.impl.RuleImpl;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Rules;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.eclipse.emf.henshin.tests.testmodel.cont;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EdgeIndexTests extends HenshinTest {
	
	private TestmodelPackage tm;
	
	@Before
	public void setUp() throws Exception {
		setModelGraphProperties("basicTestModels/matchTestsModels/", "testmodel");
		htEngine = new EngineImpl();
		tm = TestmodelPackage.eINSTANCE;
	}
	
	@Test
	public void testConstantEdgeIndexMatch() {
		
		// Create the test rule:
		htRule = new RuleImpl("constantEdgeIndex");
		htRule.setCheckDangling(false);
		Node source = new NodeImpl("source", tm.getcont(), htRule.getLhs());
		Node target = new NodeImpl("target", tm.getNode(), htRule.getLhs());
		Edge edge = new EdgeImpl(source, target, tm.getcont_ContainsNode());
		
		// Load the test graph and get the number of child nodes in the graph:
		loadGraph("manyNodes");
		int nodeCount = htEGraph.getDomainSize(tm.getNode(), true);
		
		// If we do not specify the edge index, we should get all matches:
		Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, nodeCount);

		// Test constant edge indices:
		for (int i=0; i<nodeCount; i++) {
			edge.setIndex(""+i);
			Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, 1);
			Match match = htEngine.findMatches(htRule, htEGraph, null).iterator().next();
			cont srcMatch = (cont) match.getNodeTarget(source);
			EObject trgMatch = match.getNodeTarget(target);
			int index = srcMatch.getContainsNode().indexOf(trgMatch);
			Assert.assertTrue("Unexpected target node matched (edge index is " + index + 
					" but should be " + i, index==i);
		}
		
		// No matches if the index is out of range:
		edge.setIndex("-1");
		Rules.assertRuleHasNoMatch(htRule, htEGraph, null, htEngine);
		edge.setIndex("" + (nodeCount+1));
		Rules.assertRuleHasNoMatch(htRule, htEGraph, null, htEngine);
		
	}

}