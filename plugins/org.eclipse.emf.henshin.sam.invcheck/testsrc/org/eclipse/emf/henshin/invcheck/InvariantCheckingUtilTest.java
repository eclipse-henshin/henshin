package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;



public class InvariantCheckingUtilTest extends TestCase {

	private GraphCondition gc1;
	private GraphCondition gc2;
	private GraphCondition gc21;
	private GraphCondition gc22;
	private GraphCondition gc23;
	private GraphCondition gc24;
	
	private NegativeApplicationCondition nac1;
	private NegativeApplicationCondition nac21;
	private NegativeApplicationCondition nac22;
	private NegativeApplicationCondition nac23;
	private NegativeApplicationCondition nac24;
	
	private Graph rhs;
	private Graph forb;
	private Graph result;
	private Match matching;
	private int maxItems = 10;
	private int addItems = 4;
	private String[] types = {"A", "B", "C", "D", "E"};
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
		String s1 = type.substring(0, 1);
		String s2 = type.substring(1);
		String newType = s1 + s2;
		if (s1.compareTo(s2) < 0) {
			newType = s1 + s2;
		} else {
			newType = s2 + s1;
		}
		result.setInstanceOf(edgeTypes.get(newType));
		return result;		
	}

	private TypeGraph buildTypeGraph() {
		nodeTypes = new HashMap<String, NodeType>();
		edgeTypes = new HashMap<String, EdgeType>();
		
		TypeGraph tg = SamtypegraphFactory.eINSTANCE.createTypeGraph();
		tg.setName("types");
		NodeType a = SamtypegraphFactory.eINSTANCE.createNodeType();
		a.setName("A");
		a.setTypeGraph(tg);
		nodeTypes.put("A", a);
		NodeType b = SamtypegraphFactory.eINSTANCE.createNodeType();
		b.setName("B");
		b.setTypeGraph(tg);
		nodeTypes.put("B", b);
		NodeType c = SamtypegraphFactory.eINSTANCE.createNodeType();
		c.setName("C");
		c.setTypeGraph(tg);
		nodeTypes.put("C", c);
		NodeType d = SamtypegraphFactory.eINSTANCE.createNodeType();
		d.setName("D");
		d.setTypeGraph(tg);
		nodeTypes.put("D", d);
		NodeType e = SamtypegraphFactory.eINSTANCE.createNodeType();
		e.setName("E");
		e.setTypeGraph(tg);
		nodeTypes.put("E", e);

		for (int i = 1; i <= 15; i++) {
			EdgeType curr = SamtypegraphFactory.eINSTANCE.createEdgeType();
			curr.setDirection(EdgeDirection.UNDIRECTED);
			curr.setTypeGraph(tg);
			int tar;			
			if (i <= 5) {
				curr.setSource(a);				
				tar = i - 1;
			} else if (i <= 9) {
				curr.setSource(b);
				tar = i - 6;
			} else if (i <= 12) {
				curr.setSource(c);
				tar = i - 10;
			} else if (i <= 14) {
				curr.setSource(d);
				tar = i - 13;
			} else {
				curr.setSource(e);
				tar = 0;
			}
			curr.setTarget(nodeTypes.get(types[4 - tar]));
			String name = curr.getSource().getName() + curr.getTarget().getName();
			curr.setName(name);
			curr.setTypeGraph(tg);
			edgeTypes.put(name, curr);
		}
		return tg;
	}
	
	public void testTestCase1() {
		nac1 = buildNac1();
		assertNotNull(nac1);
	
		gc1 = InvariantCheckingUtil.createNegatedCondition(nac1);
		assertNotNull(gc1);
		assertTrue(gc1 instanceof NegatedCondition);
		
		assertTrue(nac1.getEdges().isEmpty());
		assertTrue(nac1.getNodes().isEmpty());
		
		NegatedCondition nc1 = (NegatedCondition) gc1;
		Quantification q1 = (Quantification) nc1.getOperand();
		TerminationCondition tc1 = (TerminationCondition) q1.getNesting();
		assertTrue(tc1.isIsTrue());
		assertEquals(4, q1.getContext().getNodes().size());
		assertEquals(4, q1.getContext().getEdges().size());
		
	}

	public void testTestCase2() {
		buildMultipleNacs1();
		assertNotNull(nac21);
		assertNotNull(nac22);
		assertNotNull(nac23);
		assertNotNull(nac24);
	
		gc21 = InvariantCheckingUtil.createNegatedCondition(nac21);
		gc22 = InvariantCheckingUtil.createNegatedCondition(nac22);
		gc23 = InvariantCheckingUtil.createNegatedCondition(nac23);
		gc24 = InvariantCheckingUtil.createNegatedCondition(nac24);
		
		assertNotNull(gc21);
		assertTrue(gc21 instanceof NegatedCondition);
		
		assertTrue(nac21.getEdges().isEmpty());
		assertTrue(nac21.getNodes().isEmpty());
		
		NegatedCondition nc1 = (NegatedCondition) gc21;
		Quantification q1 = (Quantification) nc1.getOperand();
		TerminationCondition tc1 = (TerminationCondition) q1.getNesting();
		assertTrue(tc1.isIsTrue());
		assertEquals(3, q1.getContext().getNodes().size());
		assertEquals(4, q1.getContext().getEdges().size());
		
		
		assertNotNull(gc22);
		assertTrue(gc22 instanceof NegatedCondition);
		
		assertTrue(nac22.getEdges().isEmpty());
		assertTrue(nac22.getNodes().isEmpty());
		
		NegatedCondition nc2 = (NegatedCondition) gc22;
		Quantification q2 = (Quantification) nc2.getOperand();
		TerminationCondition tc2 = (TerminationCondition) q2.getNesting();
		assertTrue(tc2.isIsTrue());
		assertEquals(2, q2.getContext().getNodes().size());
		assertEquals(3, q2.getContext().getEdges().size());
		
		assertNotNull(gc23);
		assertTrue(gc23 instanceof NegatedCondition);
		
		assertTrue(nac23.getEdges().isEmpty());
		assertTrue(nac23.getNodes().isEmpty());
		
		NegatedCondition nc3 = (NegatedCondition) gc23;
		Quantification q3 = (Quantification) nc3.getOperand();
		TerminationCondition tc3 = (TerminationCondition) q3.getNesting();
		assertTrue(tc3.isIsTrue());
		assertEquals(2, q3.getContext().getNodes().size());
		assertEquals(4, q3.getContext().getEdges().size());
		
		assertNotNull(gc24);
		assertTrue(gc24 instanceof NegatedCondition);
		
		assertTrue(nac24.getEdges().isEmpty());
		assertTrue(nac24.getNodes().isEmpty());
		
		NegatedCondition nc4 = (NegatedCondition) gc24;
		Quantification q4 = (Quantification) nc4.getOperand();
		TerminationCondition tc4 = (TerminationCondition) q4.getNesting();
		assertTrue(tc4.isIsTrue());
		assertEquals(1, q4.getContext().getNodes().size());
		assertEquals(3, q4.getContext().getEdges().size());
		
	}
	
	private NegativeApplicationCondition buildNac1() {
		NegativeApplicationCondition pattern7Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Graph pattern7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node pattern7N1 = buildNode("a1", "A", pattern7);
		Node pattern7N2 = buildNode("aNac", "A", pattern7Nac);
		Node pattern7N3 = buildNode("b1", "B", pattern7);
		Node pattern7N4 = buildNode("bNac", "B", pattern7Nac);
		Node pattern7N5 = buildNode("c1Nac", "C", pattern7Nac);
		Node pattern7N6 = buildNode("c2Nac", "C", pattern7Nac);
		
		Edge pattern7E1 = buildEdge("AB", pattern7);
		Edge pattern7E2 = buildEdge("AB", pattern7Nac);
		Edge pattern7E3 = buildEdge("AB", pattern7Nac);
		Edge pattern7E4 = buildEdge("BC", pattern7Nac);
		Edge pattern7E5 = buildEdge("BC", pattern7Nac);
		
		pattern7E1.setSource(pattern7N1); pattern7E1.setTarget(pattern7N3);
		pattern7E2.setSource(pattern7N1); pattern7E2.setTarget(pattern7N4);
		pattern7E3.setSource(pattern7N2); pattern7E3.setTarget(pattern7N4);
		pattern7E4.setSource(pattern7N3); pattern7E4.setTarget(pattern7N6);
		pattern7E5.setSource(pattern7N4); pattern7E5.setTarget(pattern7N5);
		
		return pattern7Nac;	
	
		
	}
	
	private void buildMultipleNacs1() {
		Graph resultGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", resultGraph);
		Node n2 = buildNode("b1", "B", resultGraph);
		Edge e1 = buildEdge("AB", resultGraph);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", resultGraph);
		Edge e2 = buildEdge("BC", resultGraph);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("d", "D", resultGraph);
		Edge e3 = buildEdge("AD", resultGraph);
		e3.setSource(n1); e3.setTarget(n4);
		
		Node nn1;
		Node nn2;
		Node nn3;
		Edge e4;
		
		nac21 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("cNac", "C", nac21);
		nn2 = buildNode("dNac", "D", nac21);
		nn3 = buildNode("eNac", "E", nac21);
		
		e1 = buildEdge("BC", nac21);
		e1.setSource(n2); e1.setTarget(nn1);
		e2 = buildEdge("CD", nac21);
		e2.setSource(nn1); e2.setTarget(nn2);
		e3 = buildEdge("DE", nac21);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac21);
		e4.setSource(nn3); e4.setTarget(n1);
		
		nac22 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn2 = buildNode("dNac", "D", nac22);
		nn3 = buildNode("eNac", "E", nac22);
				
		e2 = buildEdge("CD", nac22);
		e2.setSource(n3); e2.setTarget(nn2);
		e3 = buildEdge("DE", nac22);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac22);
		e4.setSource(nn3); e4.setTarget(n1);
		
		nac23 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn1 = buildNode("cNac", "C", nac23);
		nn3 = buildNode("eNac", "E", nac23);
		
		e1 = buildEdge("BC", nac23);
		e1.setSource(n2); e1.setTarget(nn1);
		e2 = buildEdge("CD", nac23);
		e2.setSource(nn1); e2.setTarget(n4);
		e3 = buildEdge("DE", nac23);
		e3.setSource(n4); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac23);
		e4.setSource(nn3); e4.setTarget(n1);
		
		nac24 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn3 = buildNode("eNac", "E", nac24);
				
		e2 = buildEdge("CD", nac24);
		e2.setSource(n3); e2.setTarget(n4);
		e3 = buildEdge("DE", nac24);
		e3.setSource(n4); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac24);
		e4.setSource(nn3); e4.setTarget(n1);		
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
		
		GraphWithNacs gr = (GraphWithNacs) Platform.getAdapterManager().getAdapter(sgp, GraphWithNacs.class);
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
	
	private Set<AnnotatedElem> graphToSubgraph(final Graph g) {
		if (g != null) {
			final Set<AnnotatedElem> result = new HashSet<AnnotatedElem>();
			for (Iterator<Node> iter = g.getNodes().iterator(); iter.hasNext(); result.add(iter.next()));
			for (Iterator<Edge> iter = g.getEdges().iterator(); iter.hasNext(); result.add(iter.next()));
			GraphWithNacs gr = (GraphWithNacs) Platform.getAdapterManager().getAdapter(g, GraphWithNacs.class);
			if (gr != null) {
				for(NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(g).getNacs()){
					result.addAll(nac.getEdges());
					result.addAll(nac.getNodes());
				} 
			}
			return result;
		}
		return null;
	}
	
}
