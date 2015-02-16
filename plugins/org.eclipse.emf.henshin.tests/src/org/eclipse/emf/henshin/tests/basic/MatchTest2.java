package org.eclipse.emf.henshin.tests.basic;

import java.util.List;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.junit.Assert;
import org.junit.Test;

public class MatchTest2 {

	@Test
	public void bug453287() {

		Rule rule = HenshinFactory.eINSTANCE.createRule();
		Node t = rule.createNode(EcorePackage.eINSTANCE.getEDataType());
		Node a1 = rule.createNode(EcorePackage.eINSTANCE.getEAttribute());
		Node a2 = rule.createNode(EcorePackage.eINSTANCE.getEAttribute());
		rule.createEdge(a1, t, EcorePackage.eINSTANCE.getEAttribute_EAttributeType());
		rule.createEdge(a2, t, EcorePackage.eINSTANCE.getEAttribute_EAttributeType());

		t.setAction(new Action(Action.Type.REQUIRE));
		Assert.assertEquals(2, rule.getLhs().getNodes().size());
		Assert.assertEquals(0, rule.getLhs().getEdges().size());

		EGraph graph = new EGraphImpl();
		graph.add(EcoreFactory.eINSTANCE.createEAttribute());
		graph.add(EcoreFactory.eINSTANCE.createEAttribute());

		Engine engine = new EngineImpl();
		List<Match> matches = InterpreterUtil.findAllMatches(engine, rule, graph, null);
		Assert.assertEquals(0, matches.size());

	}

	@Test
	public void bug453287_2() {

		Rule rule = HenshinFactory.eINSTANCE.createRule();
		Node p = rule.createNode(EcorePackage.eINSTANCE.getEPackage());
		Node c = rule.createNode(EcorePackage.eINSTANCE.getEClass());
		Edge e = rule.createEdge(p, c, EcorePackage.eINSTANCE.getEPackage_EClassifiers());

		e.setAction(new Action(Action.Type.REQUIRE));
		Assert.assertEquals(2, rule.getLhs().getNodes().size());
		Assert.assertEquals(0, rule.getLhs().getEdges().size());

		EGraph graph = new EGraphImpl();
		graph.add(EcoreFactory.eINSTANCE.createEPackage());
		graph.add(EcoreFactory.eINSTANCE.createEClass());

		Engine engine = new EngineImpl();
		List<Match> matches = InterpreterUtil.findAllMatches(engine, rule, graph, null);
		Assert.assertEquals(0, matches.size());

	}

}
