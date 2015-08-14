package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

import junit.framework.TestCase;

public class IncoherentNACTranslationTest extends TestCase {

	private Graph	pattern1;
	private NegativeApplicationCondition	pattern1Nac;
	private Node	pattern1N1;
	private Node	pattern1N2;
	private Node	pattern1N3;
	private Node	pattern1N4;
	private Node	pattern1N5;
	private Edge	pattern1E1;
	private Edge	pattern1E2;
	private Edge	pattern1E3;
	private Graph	merged1;	
	private Node	merged1N1;
	private Node	merged1N2;
	private Node	merged1N3;
	private Node	merged1N4;
	private Edge	merged1E1;
	private Edge	merged1E2;
	private Edge	merged1E3;
	private Map<AnnotatedElem, AnnotatedElem> refItems = new HashMap<AnnotatedElem, AnnotatedElem>();
	
	private String[] types = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	private Map<String, NodeType> nodeTypes;
	private Map<String, EdgeType> edgeTypes;
	private TypeGraph typeGraph = buildTypeGraph();
	
	private Node buildNode(final String name, final String type, final Graph g) {
		final Node result = basicBuildNode(name, type);
		g.getNodes().add(result);
		return result;
	}
	
	private Node buildNode(final String name, final String type, final NegativeApplicationCondition n) {
		final Node result = basicBuildNode(name, type);
		n.getNodes().add(result);
		return result;
	}

	private Node basicBuildNode(final String name, final String type) {
		final Node result = SamgraphFactory.eINSTANCE.createNode();
		result.setName(name);
		result.setInstanceOf(nodeTypes.get(type));
		return result;
	}
	
	private Edge buildEdge(final String type, final Graph g) {
		final Edge result = basicBuildEdge(type);
		g.getEdges().add(result);		
		return result;
	}
	
	private Edge buildEdge(final String type, final NegativeApplicationCondition n) {
		final Edge result = basicBuildEdge(type);
		n.getEdges().add(result);
		return result;
	}

	private Edge basicBuildEdge(final String type) {
		final Edge result = SamgraphFactory.eINSTANCE.createEdge();
		/*String s1 = type.substring(0, 1);
		String s2 = type.substring(1);
		String newType = s1 + s2;
		if (s1.compareTo(s2) < 0) {
			newType = s1 + s2;
		} else {
			newType = s2 + s1;
		}*/
		result.setInstanceOf(edgeTypes.get(type));
		return result;		
	}
	
	private TypeGraph buildTypeGraph() {
		nodeTypes = new HashMap<String, NodeType>();
		edgeTypes = new HashMap<String, EdgeType>();
		
		TypeGraph tg = SamtypegraphFactory.eINSTANCE.createTypeGraph();
		tg.setName("types");
		
		NodeType track = SamtypegraphFactory.eINSTANCE.createNodeType();
		track.setName("Track");
		track.setTypeGraph(tg);
		nodeTypes.put("Track", track);
		
		NodeType shuttle = SamtypegraphFactory.eINSTANCE.createNodeType();
		shuttle.setName("Shuttle");
		shuttle.setTypeGraph(tg);
		nodeTypes.put("Shuttle", shuttle);
		
		NodeType dc = SamtypegraphFactory.eINSTANCE.createNodeType();
		dc.setName("DC");
		dc.setTypeGraph(tg);
		nodeTypes.put("DC", dc);
		
		EdgeType isAt = SamtypegraphFactory.eINSTANCE.createEdgeType();
		isAt.setDirection(EdgeDirection.DIRECTED);
		isAt.setSource(shuttle);
		isAt.setTarget(track);
		isAt.setTypeGraph(tg);
		isAt.setName("isAt");
		edgeTypes.put("isAt", isAt);
		
		EdgeType go = SamtypegraphFactory.eINSTANCE.createEdgeType();
		go.setDirection(EdgeDirection.DIRECTED);
		go.setSource(shuttle);
		go.setTarget(track);
		go.setTypeGraph(tg);
		go.setName("go");
		edgeTypes.put("go", go);
		
		EdgeType next = SamtypegraphFactory.eINSTANCE.createEdgeType();
		next.setDirection(EdgeDirection.DIRECTED);
		next.setSource(shuttle);
		next.setTarget(track);
		next.setTypeGraph(tg);
		next.setName("next");
		edgeTypes.put("next", next);
		
		EdgeType succ = SamtypegraphFactory.eINSTANCE.createEdgeType();
		succ.setDirection(EdgeDirection.DIRECTED);
		succ.setSource(track);
		succ.setTarget(track);
		succ.setTypeGraph(tg);
		succ.setName("succ");
		edgeTypes.put("succ", succ);
			
		EdgeType front1 = SamtypegraphFactory.eINSTANCE.createEdgeType();
		front1.setDirection(EdgeDirection.DIRECTED);
		front1.setSource(dc);
		front1.setTarget(shuttle);
		front1.setTypeGraph(tg);
		front1.setName("front1");
		edgeTypes.put("front1", front1);
		
		EdgeType front = SamtypegraphFactory.eINSTANCE.createEdgeType();
		front.setDirection(EdgeDirection.DIRECTED);
		front.setSource(shuttle);
		front.setTarget(dc);
		front.setTypeGraph(tg);
		front.setName("front");
		edgeTypes.put("front", front);
		
		EdgeType rear = SamtypegraphFactory.eINSTANCE.createEdgeType();
		rear.setDirection(EdgeDirection.DIRECTED);
		rear.setSource(shuttle);
		rear.setTarget(dc);
		rear.setTypeGraph(tg);
		rear.setName("rear");
		edgeTypes.put("rear", rear);
		
		EdgeType rear1 = SamtypegraphFactory.eINSTANCE.createEdgeType();
		rear1.setDirection(EdgeDirection.DIRECTED);
		rear1.setSource(dc);
		rear1.setTarget(track);
		rear1.setTypeGraph(tg);
		rear1.setName("rear1");
		edgeTypes.put("rear1", rear1);
		
		EdgeType dcConn = SamtypegraphFactory.eINSTANCE.createEdgeType();
		dcConn.setDirection(EdgeDirection.UNDIRECTED);
		dcConn.setSource(dc);
		dcConn.setTarget(dc);
		dcConn.setTypeGraph(tg);
		dcConn.setName("dcConn");
		edgeTypes.put("dcConn", dcConn);
		
		return tg;		
	}
		
	private void buildPattern1() {
		pattern1 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern1Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern1Nac.setName("nac1"); pattern1Nac.setGraph(pattern1);
		pattern1N1 = buildNode("s", "Shuttle", pattern1);
		pattern1N2 = buildNode("t", "Track", pattern1);
		pattern1N3 = buildNode("dcS", "DC", pattern1Nac);
		pattern1N4 = buildNode("dcI1", "DC", pattern1Nac);
		pattern1N5 = buildNode("dcI2", "DC", pattern1Nac);
		pattern1E1 = buildEdge("isAt", pattern1);
		pattern1E1.setSource(pattern1N1); pattern1E1.setTarget(pattern1N2);
		pattern1E2 = buildEdge("front1", pattern1Nac);
		pattern1E2.setSource(pattern1N3); pattern1E2.setTarget(pattern1N1);
		pattern1E3 = buildEdge("dcConn", pattern1Nac);
		pattern1E3.setSource(pattern1N4); pattern1E3.setTarget(pattern1N5);
		
		GraphCondition gc = InvariantCheckingUtil.createNegatedCondition(pattern1Nac);
		((RuleGraph) pattern1).setCondition(gc);
	}
	
	private void buildMergedGraph1() {
		merged1 = SamrulesFactory.eINSTANCE.createRuleGraph();
		merged1N1 = buildNode("s", "Shuttle", merged1);
		merged1N2 = buildNode("t", "Track", merged1);
		merged1N3 = buildNode("dcS", "DC", merged1);
		merged1N4 = buildNode("dcT", "DC", merged1);
		merged1E1 = buildEdge("isAt", merged1);
		merged1E1.setSource(merged1N1); merged1E1.setTarget(merged1N2);
		merged1E2 = buildEdge("front1", merged1);
		merged1E2.setSource(merged1N3); merged1E2.setTarget(merged1N1);
		merged1E3 = buildEdge("rear1", merged1);
		merged1E3.setSource(merged1N4); merged1E3.setTarget(merged1N2);
		
		refItems.put(pattern1N1, merged1N1);
		refItems.put(pattern1N2, merged1N2);
		refItems.put(pattern1E1, merged1E1);		
	}

	private Graph buildPattern2() {
		Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("ta", "Track", result);
		Node n2 = buildNode("tb", "Track", result);
		Node n3 = buildNode("sa", "Shuttle", result);
		Node n4 = buildNode("sb", "Shuttle", result);
		Node n5 = buildNode("dc", "DC", nac);
		
		Edge e1 = buildEdge("isAt", result); e1.setName("isAta");
		e1.setSource(n3); e1.setTarget(n1);
		Edge e2 = buildEdge("isAt", result); e2.setName("isAtb");
		e2.setSource(n4); e2.setTarget(n2);
		Edge e3 = buildEdge("go", result); e3.setName("go");
		e3.setSource(n3); e3.setTarget(n2);
		Edge e4 = buildEdge("succ", result);
		e4.setSource(n1); e4.setTarget(n2); e4.setName("succ");
		Edge e5 = buildEdge("front", nac);
		e5.setSource(n4); e5.setTarget(n5); e5.setName("front");
		Edge e6 = buildEdge("rear", nac);
		e6.setSource(n3); e6.setTarget(n5); e6.setName("rear");
		
		GraphCondition gc = InvariantCheckingUtil.createNegatedCondition(nac);
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	private Graph buildHost2() {
		Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("ta", "Track", result);
		Node n2 = buildNode("tb", "Track", result);
		Node n3 = buildNode("sa", "Shuttle", result);
		Node n4 = buildNode("sb", "Shuttle", result);
		Node n5 = buildNode("dc", "DC", nac1);
				
		Edge e1 = buildEdge("isAt", result); e1.setName("isAta");
		e1.setSource(n3); e1.setTarget(n1);
		Edge e2 = buildEdge("isAt", result); e2.setName("isAtb");
		e2.setSource(n4); e2.setTarget(n2);
		Edge e3 = buildEdge("go", result); e3.setName("go");
		e3.setSource(n3); e3.setTarget(n2);
		Edge e4 = buildEdge("succ", result);
		e4.setSource(n1); e4.setTarget(n2); e4.setName("succ");
		Edge e5 = buildEdge("front", nac1);
		e5.setSource(n4); e5.setTarget(n5); e5.setName("front");
		Edge e6 = buildEdge("rear", nac1);
		e6.setSource(n3); e6.setTarget(n5); e6.setName("rear");
		
		Node n6 = buildNode("t3", "Track", result);
		Node n7 = buildNode("s2", "Shuttle", result);
		
		Edge e7 = buildEdge("succ", result); e7.setName("succ");
		e7.setSource(n1); e7.setTarget(n6);
		e7 = buildEdge("isAt", result); e7.setName("isAt");
		e7.setSource(n7); e7.setTarget(n1);
		e7 = buildEdge("go", nac2); e7.setName("go");
		e7.setSource(n7); e7.setTarget(n6);
		e7 = buildEdge("next", result); e7.setName("next");
		e7.setSource(n7); e7.setTarget(n6);
		
		e7 = buildEdge("succ", result); e7.setName("succ");
		e7.setSource(n2); e7.setTarget(n1);
		e7 = buildEdge("go", result); e7.setName("go");
		e7.setSource(n3); e7.setTarget(n1);
		e7 = buildEdge("isAt", result); e7.setName("isAt");
		e7.setSource(n3); e7.setTarget(n2);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckingUtil.createNegatedConditions(nacs);
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	public void testTestCase1() {		
		buildPattern1();
		buildMergedGraph1();
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		
		for (Edge e : pattern1.getEdges()) {
			if (!InvariantCheckingUtil.isNegated(e)) {		
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern1.getNodes()) {
			if (!InvariantCheckingUtil.isNegated(n)) {		
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) merged1);
		nacT.setPattern(pattern1);
		Graph res = nacT.translate();
		
		GraphWithNacs gr = (GraphWithNacs) SamGraphInvCheckGraphAdapter.getInstance(res);
		for (NegativeApplicationCondition nac : gr.getNacs()) {
			System.out.println("========");
			for (Node n : nac.getNodes()) {
				System.out.println(n);
			}
			for (Edge e : nac.getEdges()) {
				System.out.println(e);
			}
		}		
	}
	
	public void testTestCase2() {
		for (int i = 0; i < 50; i++){
		Graph pattern = buildPattern2();
		Graph host = buildHost2();
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(pattern));
		System.out.println("========================================");
		assertNotNull(ipm.getNextMatching());}
				
	}
		
	private void assertContainments(Graph sgp) {		
		for (Node n : sgp.getNodes()) {
			assertNotNull(n.eContainer());
			for (Edge out : n.getOutgoing()) {
				assertNotNull(out.eContainer());
				assertNotNull(out.getSource());				
				assertNotNull(out.getTarget());
			}
			for (Edge in : n.getIncoming()) {
				assertNotNull(in.eContainer());
				assertNotNull(in.getSource());
				assertNotNull(in.getTarget());
			}
		}
		
		for (Edge e : sgp.getEdges()) {
			assertNotNull(e.eContainer());
			assertNotNull(e.getSource());
			assertNotNull(e.getTarget());
			assertNotNull(e.getSource().eContainer());
			assertNotNull(e.getTarget().eContainer());
			assertEquals(e.getSource().eContainer(), e.getTarget().eContainer());
		}
		
		GraphWithNacs gr = (GraphWithNacs) SamGraphInvCheckGraphAdapter.getInstance(sgp);
		if (gr != null) {
			for (NegativeApplicationCondition nac : gr.getNacs()) {
				for (Node n : nac.getNodes()) {
					assertNotNull(n.eContainer());
					for (Edge out : n.getOutgoing()) {
						assertEquals(n.eContainer(), out.eContainer());
						assertNotNull(out.eContainer());
						assertNotNull(out.getSource());
						assertNotNull(out.getTarget());
					}
					for (Edge in : n.getIncoming()) {
						assertEquals(n.eContainer(), in.eContainer());
						assertNotNull(in.eContainer());
						assertNotNull(in.getSource());
						assertNotNull(in.getTarget());
					}
				}
			
				for (Edge e : nac.getEdges()) {
					assertNotNull(e.eContainer());
					assertNotNull(e.getSource());
					assertNotNull(e.getTarget());
					assertNotNull(e.getSource().eContainer());
					assertNotNull(e.getTarget().eContainer());
				}
			}
		}
	}
	
}
