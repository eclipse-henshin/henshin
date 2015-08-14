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



public class GraphAdapterTest extends TestCase {

	private GraphCondition gc11;
	private GraphCondition gc12;
	
	private NegativeApplicationCondition nac11;
	private NegativeApplicationCondition nac12;
	
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
				
		GraphWithNacs ng1 = (GraphWithNacs) SamGraphInvCheckGraphAdapter.getInstance(pattern);
		
		assertNotNull(ng1);
		assertEquals(0, ng1.getNacs().size());
		
		nac11 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node nn1 = buildNode("a", "A", nac11);
		Edge ne1 = buildEdge("AX", nac11);
		ne1.setSource(nn1); ne1.setTarget(pattern.getNodes().get(0));
		
		nac12 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node nn2 = buildNode("b", "B", nac12);
		Edge ne2 = buildEdge("AX", nac12);
		ne2.setSource(nn2); ne2.setTarget(pattern.getNodes().get(2));
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac11);
		nacs.add(nac12);
		gc12 = InvariantCheckingUtil.createNegatedConditions(nacs);
		((RuleGraph) pattern).setCondition(gc12);
		
		GraphWithNacs ng2 = (GraphWithNacs) SamGraphInvCheckGraphAdapter.getInstance(pattern);
		
		assertNotNull(ng2);
		assertEquals(2, ng2.getNacs().size());
	}
	
	private Graph buildPattern1() {
		Graph pattern7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		Node pattern7N1 = buildNode("a", "A", pattern7);
		Node pattern7N2 = buildNode("b", "B", pattern7);
		Node pattern7N3 = buildNode("c", "C", pattern7);
		Node pattern7N4 = buildNode("d", "D", pattern7);
				
		Edge pattern7E1 = buildEdge("AB", pattern7);
		Edge pattern7E2 = buildEdge("BC", pattern7);
		Edge pattern7E3 = buildEdge("CD", pattern7);
				
		pattern7E1.setSource(pattern7N1); pattern7E1.setTarget(pattern7N2);
		pattern7E2.setSource(pattern7N2); pattern7E2.setTarget(pattern7N3);
		pattern7E3.setSource(pattern7N3); pattern7E3.setTarget(pattern7N4);
		
		return pattern7;	
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
