package org.eclipse.emf.henshin.tests.basicTests;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.EdgeImpl;
import org.eclipse.emf.henshin.model.impl.NodeImpl;
import org.eclipse.emf.henshin.model.impl.ParameterImpl;
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
	
	private Edge edge;

	private int nodeCount;

	private ParameterImpl param;
	
	@Before
	public void setUp() throws Exception {

		htEngine = new EngineImpl();
		tm = TestmodelPackage.eINSTANCE;

		// Load the test graph and get the number of child nodes in the graph:
		setModelGraphProperties("basicTestModels/matchTestsModels/", "testmodel");
		loadGraph("manyNodes");
		nodeCount = htEGraph.getDomainSize(tm.getNode(), true);
		
		// Create the test rule:
		htRule = new RuleImpl("edgeIndexTest");
		htRule.setCheckDangling(false);
		
		Node source = new NodeImpl("source", tm.getcont());
		Node target = new NodeImpl("target", tm.getNode());
		edge = new EdgeImpl(source, target, tm.getcont_ContainsNode());
		param = new ParameterImpl("x", EcorePackage.eINSTANCE.getEInt());
		
		htRule.getLhs().getNodes().add(source);
		htRule.getLhs().getNodes().add(target);
		htRule.getLhs().getEdges().add(edge);
		htRule.getParameters().add(param);
		
	}

	private void checkEdgeIndex(Edge edge, String indexExpr, int index, Match partialMatch) {
		edge.setIndex(indexExpr);
		((EngineImpl) htEngine).clearCache();
		if (index>=0) {
			Rules.assertRuleHasNMatches(htRule, htEGraph, partialMatch, htEngine, 1);
			Match match = htEngine.findMatches(htRule, htEGraph, partialMatch).iterator().next();
			cont srcMatch = (cont) match.getNodeTarget(edge.getSource());
			EObject trgMatch = match.getNodeTarget(edge.getTarget());
			int theIndex = srcMatch.getContainsNode().indexOf(trgMatch);
			Assert.assertTrue("Unexpected target node matched (edge index is " + theIndex + 
					" but should be " + index, theIndex==index);
		} else {
			Rules.assertRuleHasNoMatch(htRule, htEGraph, partialMatch, htEngine);
		}
	}
	
	@Test
	public void testConstantEdgeIndexMatch() {
		
		// If we do not specify the edge index, we should get all matches:
		edge.setIndex(null);
		((EngineImpl) htEngine).clearCache();
		Rules.assertRuleHasNMatches(htRule, htEGraph, null, htEngine, nodeCount);

		// Test constant edge indices (also with negative constants):
		for (int i=0; i<nodeCount; i++) {
			checkEdgeIndex(edge, ""+i, i, null);
			checkEdgeIndex(edge, ""+(i*13) + "/13", i, null);
			checkEdgeIndex(edge, ""+(-i-1), nodeCount-i-1, null);
		}
		
		// Test out of range indices:
		checkEdgeIndex(edge, ""+nodeCount, -1, null);
		checkEdgeIndex(edge, ""+(-nodeCount-1), -1, null);
		
	}

	@Test
	public void testSetParamEdgeIndexMatch() {
		
		// If we do not specify the edge index, we should get all matches:
		edge.setIndex(param.getName());
		((EngineImpl) htEngine).clearCache();

		// Test constant edge indices (also with negative constants):
		Match partialMatch = new MatchImpl(htRule);
		for (int i=0; i<nodeCount; i++) {
			partialMatch.setParameterValue(param, i);
			checkEdgeIndex(edge, param.getName(), i, partialMatch);
		}
		
	}

	@Test
	public void testGetParamEdgeIndexMatch() {
		
		// If we do not specify the edge index, we should get all matches:
		edge.setIndex(param.getName());
		((EngineImpl) htEngine).clearCache();

		// Test constant edge indices (also with negative constants):
		List<Match> matches = InterpreterUtil.findAllMatches(htEngine, htRule, htEGraph, null);
		Assert.assertTrue(matches.size()==nodeCount);
		
		// Check whether the calculated indices are correct:
		for (Match match : matches) {
			int paramIndex = (Integer) match.getParameterValue(param);
			cont srcMatch = (cont) match.getNodeTarget(edge.getSource());
			EObject trgMatch = match.getNodeTarget(edge.getTarget());
			Assert.assertTrue(paramIndex==srcMatch.getContainsNode().indexOf(trgMatch));
		}
				
	}


}