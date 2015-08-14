package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.invcheck.IsomorphicPartMatcherTest.ItemType;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.adapter.GCNACAdapter;
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
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;



public class NACAdapterTest extends TestCase {

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
		Graph pattern = buildPattern1();
				
		//NegativeApplicationCondition nac1 = (NegativeApplicationCondition) Platform.getAdapterManager().getAdapter(gc1, NegativeApplicationCondition.class);
		NegativeApplicationCondition nac1 = GCNACAdapter.getInstance(gc1);
		
		assertNotNull(nac1);
		assertEquals(4, nac1.getEdges().size());
		assertEquals(4, nac1.getNodes().size());
	
		NegatedCondition nc = (NegatedCondition) gc1;
		Quantification q = (Quantification) nc.getOperand();
		
		assertSame(nac1.getEdges(), q.getContext().getEdges());
		assertSame(nac1.getNodes(), q.getContext().getNodes());
	}
	
	public void testTestCase2() {
		Graph pattern = buildPattern1();
		
		assertSame(((RuleGraph) pattern).getCondition(), gc1);
				
		NegativeApplicationCondition nac1 = GCNACAdapter.getInstance(gc1);
		
		assertNotNull(nac1);
		assertEquals(4, nac1.getEdges().size());
		assertEquals(4, nac1.getNodes().size());
		
		NegatedCondition nc = (NegatedCondition) gc1;
		Quantification q = (Quantification) nc.getOperand();		
		assertSame(nac1.getEdges(), q.getContext().getEdges());
		assertSame(nac1.getNodes(), q.getContext().getNodes());
		
		GraphCondition gcOld = gc1;
		NegativeApplicationCondition nacOld = nac1;
		assertSame(gcOld, gc1);
		
		nac1 = GCNACAdapter.getInstance(gc1);
		assertEquals(nac1, nacOld);
		
		nc = (NegatedCondition) gc1;
		q = (Quantification) nc.getOperand();		
		assertSame(nac1.getEdges(), q.getContext().getEdges());
		assertSame(nac1.getNodes(), q.getContext().getNodes());
		
		pattern = buildPattern1();
		//assertNotEqual(gcOld, gc1);
		assertFalse(gcOld.equals(gc1));
		
		nc = (NegatedCondition) gc1;
		q = (Quantification) nc.getOperand();		
		assertNotSame(nac1.getEdges(), q.getContext().getEdges());
		assertNotSame(nac1.getNodes(), q.getContext().getNodes());		
				
		nac1 = GCNACAdapter.getInstance(gc1);		
		assertNotNull(nac1);
		assertEquals(4, nac1.getEdges().size());
		assertEquals(4, nac1.getNodes().size());
		
		nc = (NegatedCondition) gc1;
		q = (Quantification) nc.getOperand();		
		assertSame(nac1.getEdges(), q.getContext().getEdges());
		assertSame(nac1.getNodes(), q.getContext().getNodes());
		
	}
	
	public void testTestCase3() {
		Graph pattern = buildMultipleNacs1();
		
		assertSame(((RuleGraph) pattern).getCondition(), gc2);
				
		NegativeApplicationCondition nac1 = GCNACAdapter.getInstance(gc21);
		NegativeApplicationCondition nac2 = GCNACAdapter.getInstance(gc22);
		NegativeApplicationCondition nac3 = GCNACAdapter.getInstance(gc23);
		NegativeApplicationCondition nac4 = GCNACAdapter.getInstance(gc24);
		
		assertNotNull(nac1);
		assertEquals(3, nac1.getNodes().size());
		assertEquals(4, nac1.getEdges().size());
		
		assertNotNull(nac2);
		assertEquals(2, nac2.getNodes().size());
		assertEquals(3, nac2.getEdges().size());
		
		assertNotNull(nac3);
		assertEquals(2, nac3.getNodes().size());
		assertEquals(4, nac3.getEdges().size());
		
		assertNotNull(nac4);
		assertEquals(1, nac4.getNodes().size());
		assertEquals(3, nac4.getEdges().size());
		
		NegatedCondition nc1 = (NegatedCondition) gc21;
		Quantification q1 = (Quantification) nc1.getOperand();		
		assertSame(nac1.getEdges(), q1.getContext().getEdges());
		assertSame(nac1.getNodes(), q1.getContext().getNodes());
				
		NegatedCondition nc2 = (NegatedCondition) gc22;
		Quantification q2 = (Quantification) nc2.getOperand();		
		assertSame(nac2.getEdges(), q2.getContext().getEdges());
		assertSame(nac2.getNodes(), q2.getContext().getNodes());
		
		NegatedCondition nc3 = (NegatedCondition) gc23;
		Quantification q3 = (Quantification) nc3.getOperand();		
		assertSame(nac3.getEdges(), q3.getContext().getEdges());
		assertSame(nac3.getNodes(), q3.getContext().getNodes());
		
		NegatedCondition nc4 = (NegatedCondition) gc24;
		Quantification q4 = (Quantification) nc4.getOperand();		
		assertSame(nac4.getEdges(), q4.getContext().getEdges());
		assertSame(nac4.getNodes(), q4.getContext().getNodes());
				
	}
	
	private Graph buildPattern1() {
		Graph pattern7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition pattern7Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern7Nac.setName("nac1"); pattern7Nac.setGraph(pattern7);
		
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
		
		gc1 = InvariantCheckingUtil.createNegatedCondition(pattern7Nac);
		((RuleGraph) pattern7).setCondition(gc1);
		
		return pattern7;	
	}

	private Graph buildMultipleNacs1() {
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
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("cNac", "C", nac1);
		nn2 = buildNode("dNac", "D", nac1);
		nn3 = buildNode("eNac", "E", nac1);
		
		e1 = buildEdge("BC", nac1);
		e1.setSource(n2); e1.setTarget(nn1);
		e2 = buildEdge("CD", nac1);
		e2.setSource(nn1); e2.setTarget(nn2);
		e3 = buildEdge("DE", nac1);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac1);
		e4.setSource(nn3); e4.setTarget(n1);
		
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn2 = buildNode("dNac", "D", nac2);
		nn3 = buildNode("eNac", "E", nac2);
				
		e2 = buildEdge("CD", nac2);
		e2.setSource(n3); e2.setTarget(nn2);
		e3 = buildEdge("DE", nac2);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac2);
		e4.setSource(nn3); e4.setTarget(n1);
		
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn1 = buildNode("cNac", "C", nac3);
		nn3 = buildNode("eNac", "E", nac3);
		
		e1 = buildEdge("BC", nac3);
		e1.setSource(n2); e1.setTarget(nn1);
		e2 = buildEdge("CD", nac3);
		e2.setSource(nn1); e2.setTarget(n4);
		e3 = buildEdge("DE", nac3);
		e3.setSource(n4); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac3);
		e4.setSource(nn3); e4.setTarget(n1);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn3 = buildNode("eNac", "E", nac4);
				
		e2 = buildEdge("CD", nac4);
		e2.setSource(n3); e2.setTarget(n4);
		e3 = buildEdge("DE", nac4);
		e3.setSource(n4); e3.setTarget(nn3);
		e4 = buildEdge("EA", nac4);
		e4.setSource(nn3); e4.setTarget(n1);

		gc21 = InvariantCheckingUtil.createNegatedCondition(nac1);
		gc22 = InvariantCheckingUtil.createNegatedCondition(nac2);
		gc23 = InvariantCheckingUtil.createNegatedCondition(nac3);
		gc24 = InvariantCheckingUtil.createNegatedCondition(nac4);
		gc2 = SamgraphconditionFactory.eINSTANCE.createLogicalGCCoupling();
		((LogicalGCCoupling) gc2).setOperator(LogicalOperator.CONJUNCTION);
		((LogicalGCCoupling) gc2).getOperands().add(gc21);
		((LogicalGCCoupling) gc2).getOperands().add(gc22);
		((LogicalGCCoupling) gc2).getOperands().add(gc23);
		((LogicalGCCoupling) gc2).getOperands().add(gc24);				
		((RuleGraph) resultGraph).setCondition(gc2);
		
		return resultGraph;
		
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
