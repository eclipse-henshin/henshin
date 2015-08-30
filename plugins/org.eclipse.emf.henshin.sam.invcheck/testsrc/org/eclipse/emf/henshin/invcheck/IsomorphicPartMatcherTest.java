package org.eclipse.emf.henshin.invcheck;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher.MatchingState;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher.TestAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher.TestAdapter.TestAdapterMatchingStateFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;


public class IsomorphicPartMatcherTest extends junit.framework.TestCase {

	private String[] types = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	private Map<String, NodeType> nodeTypes;
	private Map<String, EdgeType> edgeTypes;
	private TypeGraph typeGraph = buildTypeGraph();
	
	public enum ItemType {
		CREATED, DELETED, PRESERVED, PATTERN, NONE
	}
	
	private Node buildNode(final String name, final String type, ItemType t, final Graph g) {
		final Node result = basicBuildNode(name, type, t);
		g.getNodes().add(result);
		return result;
	}
	
	private Node buildNode(final String name, final String type, ItemType t, final NegativeApplicationCondition n) {
		final Node result = basicBuildNode(name, type, t);
		n.getNodes().add(result);
		return result;
	}

	private Node basicBuildNode(final String name, final String type, ItemType t) {
		final Node result;
		switch (t) {
		case CREATED: result = SamrulesFactory.eINSTANCE.createCreatedNode(); break;
		case DELETED: result = SamrulesFactory.eINSTANCE.createDeletedNode(); break;
		case PRESERVED: result = SamrulesFactory.eINSTANCE.createPreservedNode(); break;
		case PATTERN: result = NacFactory.eINSTANCE.createPatternNode(); break;
		case NONE: result = SamgraphFactory.eINSTANCE.createNode(); break;
		default: result = SamgraphFactory.eINSTANCE.createNode(); break;
		}
		
		result.setName(name);
		result.setInstanceOf(nodeTypes.get(type));
		return result;
	}
	
	private Edge buildEdge(final String type, ItemType t, final Graph g) {
		final Edge result = basicBuildEdge(type, t);
		g.getEdges().add(result);		
		return result;
	}
	
	private Edge buildEdge(final String type, ItemType t, final NegativeApplicationCondition n) {
		final Edge result = basicBuildEdge(type, t);
		n.getEdges().add(result);
		return result;
	}

	private Edge basicBuildEdge(final String type, ItemType t) {
		final Edge result;
		switch (t) {
		case CREATED: result = SamrulesFactory.eINSTANCE.createCreatedEdge(); break;
		case DELETED: result = SamrulesFactory.eINSTANCE.createDeletedEdge(); break;
		case PRESERVED: result = SamrulesFactory.eINSTANCE.createPreservedEdge(); break;
		case PATTERN: result = NacFactory.eINSTANCE.createPatternEdge(); break;
		case NONE: result = SamgraphFactory.eINSTANCE.createEdge(); break;
		default: result = SamgraphFactory.eINSTANCE.createEdge(); break;
		}
		
		if (edgeTypes.containsKey(type)) {
			result.setInstanceOf(edgeTypes.get(type));
		} else {
		
			String s1 = type.substring(0, 1);
			String s2 = type.substring(1);
			String newType = s1 + s2;
			if (s1.compareTo(s2) < 0) {
				newType = s1 + s2;
			} else {
				newType = s2 + s1;
			}
			result.setInstanceOf(edgeTypes.get(newType));
		}
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
		NodeType f = SamtypegraphFactory.eINSTANCE.createNodeType();
		f.setName("F");
		f.setTypeGraph(tg);
		nodeTypes.put("F", f);
		NodeType g = SamtypegraphFactory.eINSTANCE.createNodeType();
		g.setName("G");
		g.setTypeGraph(tg);
		nodeTypes.put("G", g);
		NodeType h = SamtypegraphFactory.eINSTANCE.createNodeType();
		h.setName("H");
		h.setTypeGraph(tg);
		nodeTypes.put("H", h);
		NodeType i = SamtypegraphFactory.eINSTANCE.createNodeType();
		i.setName("I");
		i.setTypeGraph(tg);
		nodeTypes.put("I", i);

		NodeType floor = SamtypegraphFactory.eINSTANCE.createNodeType();
		floor.setName("Floor");
		floor.setTypeGraph(tg);
		nodeTypes.put("Floor", floor);
		NodeType client = SamtypegraphFactory.eINSTANCE.createNodeType();
		client.setName("Client");
		client.setTypeGraph(tg);
		nodeTypes.put("Client", client);
		NodeType account = SamtypegraphFactory.eINSTANCE.createNodeType();
		account.setName("Account");
		account.setTypeGraph(tg);
		nodeTypes.put("Account", account);
		NodeType bill = SamtypegraphFactory.eINSTANCE.createNodeType();
		bill.setName("Bill");
		bill.setTypeGraph(tg);
		nodeTypes.put("Bill", bill);
		
		for (int j = 1; j <= 45; j++) {
			EdgeType curr = SamtypegraphFactory.eINSTANCE.createEdgeType();
			curr.setDirection(EdgeDirection.UNDIRECTED);
			curr.setTypeGraph(tg);
			int tar;			
			if (j <= 9) {
				curr.setSource(a);				
				tar = j - 1;
			} else if (j <= 17) {
				curr.setSource(b);
				tar = j - 10;
			} else if (j <= 24) {
				curr.setSource(c);
				tar = j - 18;
			} else if (j <= 30) {
				curr.setSource(d);
				tar = j - 25;
			} else if (j <= 35) {
				curr.setSource(e);
				tar = j - 31;
			} else if (j <= 39) {
				curr.setSource(f);
				tar = j - 36;
			} else if (j <= 42) {
				curr.setSource(g);
				tar = j - 40;
			} else if (j <= 44) {
				curr.setSource(h);
				tar = j - 43;
			} else {
				curr.setSource(i);
				tar = 0;
			}
			curr.setTarget(nodeTypes.get(types[8 - tar]));
			String name = curr.getSource().getName() + curr.getTarget().getName();
			curr.setName(name);
			curr.setTypeGraph(tg);
			edgeTypes.put(name, curr);
		}
		EdgeType et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("E");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("E", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("A");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("A", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("B");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("B", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("C");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("C", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("higher_than");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("higher_than", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("next_up");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("next_up", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("hasAccount");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("hasAccount", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("pays");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("pays", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("YY");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("YY", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("ZZ");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.UNDIRECTED);
		edgeTypes.put("ZZ", et);
		return tg;
	}
	
	public void testGetNextMatching() {
		Graph host = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node nodeA = buildNode("nodeA", "A", ItemType.NONE, host);
		Node nodeB = buildNode("nodeB", "B", ItemType.NONE, host);		
		Node nodeC = buildNode("nodeC", "C", ItemType.NONE, host);
		Node nodeD = buildNode("nodeD", "A", ItemType.NONE, host);
		Edge e1 = buildEdge("A", ItemType.NONE, host);
		e1.setSource(nodeA); e1.setTarget(nodeB);		
		Edge e2  = buildEdge("A", ItemType.NONE, host);
		e2.setSource(nodeA);
		e2.setTarget(nodeC);		
		Edge e4 = buildEdge("A", ItemType.NONE, host);
		e4.setSource(nodeD);
		e4.setTarget(nodeB);
		
		Graph pattern = SamrulesFactory.eINSTANCE.createRuleGraph(); //new Graph();
		Node nodepA = buildNode("nodeA", "A", ItemType.NONE, pattern);
		Node nodepB = buildNode("nodeB", "B", ItemType.NONE, pattern);
		Edge e3 = buildEdge("A", ItemType.NONE, pattern);
		e3.setSource(nodepA);
		e3.setTarget(nodepB);		
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(pattern);
		ipm.setHostGraph(host);
		
		Set<AnnotatedElem> subgraph = graphToSubgraph(pattern);
		
		ipm.setCurrentSubGraph(subgraph);
		
		try {
			final Match gm1 = ipm.getNextMatching();
			assertNotNull(gm1);
			final Match gm2 = ipm.getNextMatching();
			assertNotNull(gm2);
			final Match gm3 = ipm.getNextMatching();
			assertNull(gm3);
			assertFalse(gm1.equals(gm2));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getLocalizedMessage());
		}
		
	}
	
	public void testGetNextMatching2() {
		final Graph hostGraph = buildHostGraph();
		final Graph pattern = buildPattern();
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		TestAdapter ta = ipm.new TestAdapter();
		for (Iterator<Node> nIter = pattern.getNodes().iterator(); nIter.hasNext(); ) {
			final Node startNode = nIter.next();
			System.out.println("StartNode = " + startNode.getName() + ":" + startNode.getInstanceOf());
			ipm.reset();
			ta.setStartNode(startNode);
			assertCorrectMatch(ipm.getNextMatching());
			assertCorrectMatch(ipm.getNextMatching());
			assertNull(ipm.getNextMatching());
		}
	}
	
	public void testCircleNextMatching() {
		final Graph hostGraph = buildHostGraph();
		final Graph pattern = buildPattern2();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		TestAdapter ta = ipm.new TestAdapter();
		for (Iterator<Node> nIter = pattern.getNodes().iterator(); nIter.hasNext(); ) {
			final Node startNode = nIter.next();
			//System.out.println("StartNode = " + startNode.getName() + ":" + startNode.getType());
			ipm.reset();
			ta.setStartNode(startNode);
			//assertCorrectMatch(ipm.getNextMatching());
			//assertCorrectMatch(ipm.getNextMatching());
			assertNull(ipm.getNextMatching());
		}
	}
	
	public void testEdgeLoop() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("nodeA1", "A", ItemType.NONE, hostGraph);
		Node n2 = buildNode("nodeA2", "A", ItemType.NONE, hostGraph);
		Edge e1 = buildEdge("AA", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);		
		Edge e2 = buildEdge("AA", ItemType.NONE, hostGraph);
		e2.setSource(n1); e2.setTarget(n1);		
		
		final Graph pattern = SamrulesFactory.eINSTANCE.createRuleGraph();
		n1 = buildNode("nodeA", "A", ItemType.NONE, pattern);
		e2 = buildEdge("AA", ItemType.NONE, pattern);
		e2.setSource(n1); e2.setTarget(n1);		
		
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(this.graphToSubgraph(pattern));
		
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
	}
	
	public void testMatchingStateFactory() {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		TestAdapter ta = ipm.new TestAdapter();
		TestAdapterMatchingStateFactory msf = ta.getFactory();
		Set<MatchingState> tmp = new HashSet<MatchingState>(10);
		try {
			for (int i = 0; i < 9; i++) {
				tmp.add(msf.getCheckNodeState());
			}
			for (MatchingState ms : tmp) {
				msf.releaseMatchingState(ms);
			}
			tmp.clear();
			for (int i = 0; i < 9; i++) {
				tmp.add(msf.getCheckEdgeState());
			}
			for (MatchingState ms : tmp) {
				msf.releaseMatchingState(ms);
			}
		} catch (Exception e) {
			fail();
		}
	}

	public void testCircleNextMatching2() {
		final Graph hostGraph = buildHostGraph2();
		final Graph pattern = buildPattern2();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		TestAdapter ta = ipm.new TestAdapter();
		for (Iterator<Node> nIter = pattern.getNodes().iterator(); nIter.hasNext(); ) {
			final Node startNode = nIter.next();
			//System.out.println("StartNode = " + startNode.getName() + ":" + startNode.getType());
			ipm.reset();
			ta.setStartNode(startNode);
			assertCorrectMatch(ipm.getNextMatching());
			assertCorrectMatch(ipm.getNextMatching());
			assertNull(ipm.getNextMatching());
		}
	}
	
	public void testNegativeNode() {
		Graph hostGraph = this.buildHostGraph();
		final Graph pattern = this.buildPattern3();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		final TestAdapter ta = ipm.new TestAdapter();
		for (Iterator<Node> niter = pattern.getNodes().iterator(); niter.hasNext(); ) {
			final Node startNode = niter.next();
			if (InvariantCheckerUtil.isNegated(startNode) == false) {
				ipm.reset();
				ta.setStartNode(startNode);
				//assertNull(ipm.getNextMatching());
			}
		}
		
		hostGraph = this.buildHostGraph3();
		ipm.setHostGraph(hostGraph);
		for (Iterator<Node> niter = pattern.getNodes().iterator(); niter.hasNext(); ) {
			final Node startNode = niter.next();
			if (InvariantCheckerUtil.isNegated(startNode) == false) {
				ipm.reset();
				ta.setStartNode(startNode);
				//assertCorrectMatch(ipm.getNextMatching()); //the matchers semantic has changed and thus we don't have any match anymore
				//assertCorrectMatch(ipm.getNextMatching());
				assertNull(ipm.getNextMatching());
			}
		}
	}
	
	public void testNacNodeMatch() {
		final Graph hostGraph = buildPatternNacMatch();
		final Graph pattern = buildHostGraphNacMatch();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipm.getNextMatching());
	}
	
	public void testNacNodeMatchVV() {
		final Graph hostGraph = buildHostGraphNacMatch();
		final Graph pattern = buildPatternNacMatch();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertNull(ipm.getNextMatching());
	}
	
	public void testGetNextMatchingException() {
		final Graph hostGraph = buildPatternNacMatch();
		final Graph pattern = buildPattern();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		try {
			ipm.getNextMatching();
		} catch (IllegalStateException e) {
			assertNotNull(e);
		} catch (Exception e) {
			fail("received an unexcepted exception");
		}
	}
	
	public void testStartNodeIsNull() {
		//we do not really care, which hostGraph will be used
		final Graph hostGraph = buildHostGraph();
		final Graph pattern = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();		
		Node n;
		for (int i = 0; i < 3; i++) {
			n = buildNode("a", "A", ItemType.NONE, nac);
		}
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph)pattern).setCondition(gc);
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertNull(ipm.getNextMatching());
	}
	
	public void testNacMatch2() {
		final Graph pattern = buildPatternNac2();
		final Graph hostGraph = buildHostGraphNac2();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertNull(ipm.getNextMatching());
	}
	
	public void testNacMatch3() {
		final Graph pattern = buildPatternNac3();
		final Graph hostGraph = buildHostGraphNac3();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
	}
	
	public void testNacMatch4() {
		final Graph pattern = buildPatternNac4();
		final Graph hostGraph = buildHostGraphNac4();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
	}
	
	public void testNacMatch5() {
		final Graph pattern = buildPatternNac5();
		final Graph hostGraph = buildHostGraphNac5();
		final IsomorphicPartMatcher ipmn = new IsomorphicPartMatcher();
		ipmn.setHostGraph(hostGraph);
		ipmn.setPattern(pattern);
		ipmn.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipmn.getNextMatching());
		assertNull(ipmn.getNextMatching());
	}
	
	public void testNacMatch6() {
		final Graph pattern = buildPatternNac6();
		final Graph hostGraph = buildHostGraphNac6();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
	}
	
	public void testNacMatch7() {
		final Graph pattern = buildPatternNac7();
		final Graph hostGraph = buildHostGraphNac7();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
		
	}

	public void testNacMatch8() {
		final Graph pattern = buildPatternNac8();
		final Graph hostGraph = buildHostGraphNac8();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertCorrectMatch(ipm.getNextMatching());
		System.out.println("-");
		assertNull(ipm.getNextMatching());
		
	}
	
	/**
	 * This test sketches a situation where the <code>IsomorphicPartMatcher</code> must not find a match of the pattern in the given hostGraph.<br />
	 * <p>Both, pattern and hostgraph are more or less triangles of the nodes A,B and C where the nodes B and C are connected by a negative Edge (i.e. 
	 * an <code>Edge</code> that is part of a <code>NegativeApplicationCondition</code>). The hostgraph contains an additional <code>Edge</code> from 
	 * B to C that is not part of a NAC. Thus the meaning of pattern and hostgraph are different. The hostgraph specifies that there exists exactly
	 * one <code>Edge</code> whereas the pattern looks for situations where no <code>Edge</code> exists.</p>
	 * <p>Of course the scenario depicted above is a general one and not only restricted to NACs containing only one <code>Edge</code>. In general it
	 * must be assured that the NAC does not exists as unmatched positive part in the hostgraph!</p>
	 */
	public void testNegativeAndPositive1() {
		final Graph pattern = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Node pn1 = buildNode("a", "A", ItemType.NONE, pattern);
		final Node pn2 = buildNode("b", "B", ItemType.NONE, pattern);
		final Node pn3 = buildNode("c", "C", ItemType.NONE, pattern);
		final Edge pe1 = buildEdge("E", ItemType.NONE, pattern);
		pe1.setSource(pn1); pe1.setTarget(pn2);
		final Edge pe2 = buildEdge("E", ItemType.NONE, pattern);
		pe2.setSource(pn1);pe2.setTarget(pn3);
		final NegativeApplicationCondition pnac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pnac.setGraph(pattern);
		final Edge pe3 = buildEdge("E", ItemType.NONE, pnac);
		pe3.setSource(pn2);pe3.setTarget(pn3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pnac);
		((RuleGraph) pattern).setCondition(gc);
		
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b ", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Edge e1 = buildEdge("E", ItemType.NONE, hostGraph);		
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("E", ItemType.NONE, hostGraph);
		e2.setSource(n1);e2.setTarget(n3);
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac.setGraph(hostGraph);
		final Edge e3 = buildEdge("E", ItemType.NONE, nac);
		e3.setSource(n2);e3.setTarget(n3);		
		final Edge e4 = buildEdge("E", ItemType.NONE, hostGraph);
		e4.setSource(n2);e4.setTarget(n3);
		
		GraphCondition gc2 = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc2);
		
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertNull(ipm.getNextMatching());
	}
	
	public void testNoTwoHigherThan() {
		final Graph sourceGraphPattern = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition sourceNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		sourceNac.setGraph(sourceGraphPattern);
		Node n1 = buildNode("f", "Floor", ItemType.NONE, sourceGraphPattern);
		Node n2 = buildNode("f", "Floor", ItemType.NONE, sourceGraphPattern);
		Edge nextUp = buildEdge("next_up", ItemType.NONE, sourceGraphPattern);
		nextUp.setSource(n1); nextUp.setTarget(n2);
		Edge higherThan = buildEdge("higher_than", ItemType.NONE, sourceNac);
		higherThan.setSource(n2); higherThan.setTarget(n1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(sourceNac);
		((RuleGraph) sourceGraphPattern).setCondition(gc);
		
		final Graph ruleLHS = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition ruleNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		ruleNac.setGraph(ruleLHS);
		Node n3 = buildNode("f", "Floor", ItemType.NONE, ruleLHS);
		Node n4 = buildNode("f", "Floor", ItemType.NONE, ruleLHS);
		nextUp = buildEdge("next_up", ItemType.NONE, ruleLHS);
		nextUp.setSource(n3); nextUp.setTarget(n4);
		higherThan = buildEdge("higher_than", ItemType.NONE, ruleNac);
		higherThan.setSource(n4); higherThan.setTarget(n3);
		
		GraphCondition gc2 = InvariantCheckerUtil.createNegatedCondition(ruleNac);
		((RuleGraph) ruleLHS).setCondition(gc2);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(sourceGraphPattern);
		ipm.setPattern(ruleLHS);
		ipm.setCurrentSubGraph(graphToSubgraph(ruleLHS));
		
		assertCorrectMatch(ipm.getNextMatching());
		
		higherThan = buildEdge("higher_than", ItemType.NONE, sourceGraphPattern);
		higherThan.setSource(n2); higherThan.setTarget(n1);
		
		ipm.reset();
		
		assertNull(ipm.getNextMatching());
	}
	
	public void testNegativeAndPositive2() {
		final Graph g = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a", "A", ItemType.NONE, g);
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		Node n2 = buildNode("b", "B", ItemType.NONE, nac);
		Node n3 = buildNode("c", "C", ItemType.NONE, nac);
		Edge e1 = buildEdge("AB", ItemType.NONE, nac);		
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC", ItemType.NONE, nac);
		e2.setSource(n2); e2.setTarget(n3);
		Edge e3 = buildEdge("CA", ItemType.NONE, nac);
		e3.setSource(n3); e3.setTarget(n1);
		
		final Graph complexHost = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		//build the graph's positive part
		n1 = buildNode("a", "A", ItemType.NONE, complexHost);
		n2 = buildNode("b", "B", ItemType.NONE, complexHost);
		n3 = buildNode("c", "C", ItemType.NONE, complexHost);
		Node n4 = buildNode("c", "C", ItemType.NONE, complexHost);
		Node n5 = buildNode("wichtig", "A", ItemType.NONE, complexHost);
		e1 = buildEdge("AB", ItemType.NONE, complexHost);
		e1.setSource(n1); e1.setTarget(n2);
		e1 = buildEdge("CA", ItemType.NONE, complexHost);
		e1.setSource(n1); e1.setTarget(n3);
		e1 = buildEdge("BC", ItemType.NONE, complexHost);
		e1.setSource(n2); e1.setTarget(n3);
		e1 = buildEdge("BC", ItemType.NONE, complexHost);
		e1.setSource(n3); e1.setTarget(n4);
		e1 = buildEdge("CA", ItemType.NONE, complexHost);
		e1.setSource(n4); e1.setTarget(n5);
		n2 = buildNode("b", "B", ItemType.NONE, nac2);
		n3 = buildNode("c", "C", ItemType.NONE, nac2);
		e1 = buildEdge("AB", ItemType.NONE, nac2);
		e1.setSource(n1); e1.setTarget(n2);
		e1 = buildEdge("BC", ItemType.NONE, nac2);
		e1.setSource(n2); e1.setTarget(n3);
		e1 = buildEdge("CA", ItemType.NONE, nac2);
		e1.setSource(n3); e1.setTarget(n1);
		n2 = buildNode("b", "B", ItemType.NONE, nac3);
		n3 = buildNode("c", "C", ItemType.NONE, nac3);
		e1 = buildEdge("AB", ItemType.NONE, nac3);
		e1.setSource(n5); e1.setTarget(n2);
		e1 = buildEdge("BC", ItemType.NONE, nac3);
		e1.setSource(n2); e1.setTarget(n3);
		e1 = buildEdge("CA", ItemType.NONE, nac3);
		e1.setSource(n3); e1.setTarget(n5);

		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) g).setCondition(gc);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac2);
		nacs.add(nac3);
		
		GraphCondition gc2 = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) complexHost).setCondition(gc2);		
		
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(complexHost);
		ipm.setPattern(g);
		ipm.setCurrentSubGraph(graphToSubgraph(g));
		
		// this was a mistake in the old test. Actually, there is no match.		
		//assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
	}
	
	public void testNegativeAndPositive3() {
		final Graph pattern = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		final NegativeApplicationCondition nacPattern = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nacPattern.setGraph(pattern);
		final NegativeApplicationCondition nacHost = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nacHost.setGraph(hostGraph);
		
		Node n1 = buildNode("a", "A", ItemType.NONE, pattern);
		Node n2 = buildNode("b", "B", ItemType.NONE, pattern);
		Node n3 = buildNode("c", "C", ItemType.NONE, pattern);
		Edge e1 = buildEdge("AB", ItemType.NONE, pattern);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC", ItemType.NONE, pattern);
		e2.setSource(n2); e2.setTarget(n3);
		Edge e3 = buildEdge("CA", ItemType.NONE, pattern);
		e3.setSource(n3); e3.setTarget(n1);
		Node n4 = buildNode("b", "B", ItemType.NONE, nacPattern);
		e1 = buildEdge("AB", ItemType.NONE, nacPattern);
		e1.setSource(n1); e1.setTarget(n4);
		n1 = buildNode("c", "C", ItemType.NONE, nacPattern);
		e1 = buildEdge("BC", ItemType.NONE, nacPattern);
		e1.setSource(n4); e1.setTarget(n1);
		e1 = buildEdge("CB", ItemType.NONE, nacPattern);
		e1.setSource(n1); e1.setTarget(n2);
		n1 = buildNode("c", "C", ItemType.NONE, nacPattern);
		e1 = buildEdge("CC", ItemType.NONE, nacPattern);
		e1.setSource(n1); e1.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nacPattern);
		((RuleGraph) pattern).setCondition(gc);
		
		n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		e3 = buildEdge("CA", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n1);
		Node n5 = buildNode("b", "B", ItemType.NONE, hostGraph);
		e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n5);
		Node n6 = buildNode("c", "C", ItemType.NONE, hostGraph);
		e1 = buildEdge("BC", ItemType.NONE, hostGraph);
		e1.setSource(n5); e1.setTarget(n6);
		e1 = buildEdge("CB", ItemType.NONE, hostGraph);
		e1.setSource(n6); e1.setTarget(n2);
		n4 = buildNode("b", "B", ItemType.NONE, nacHost);
		e1 = buildEdge("AB", ItemType.NONE, nacHost);
		e1.setSource(n1); e1.setTarget(n4);
		n1 = buildNode("c", "C", ItemType.NONE, nacHost);
		e1 = buildEdge("BC", ItemType.NONE, nacHost);
		e1.setSource(n4); e1.setTarget(n1);
		e1 = buildEdge("CB", ItemType.NONE, nacHost);
		e1.setSource(n1); e1.setTarget(n2);
		n1 = buildNode("c", "C", ItemType.NONE, nacHost);
		e1 = buildEdge("CC", ItemType.NONE, nacHost);
		e1.setSource(n1); e1.setTarget(n3);
		
		GraphCondition gc2 = InvariantCheckerUtil.createNegatedCondition(nacHost);
		((RuleGraph) hostGraph).setCondition(gc2);
		
		assertContainments(hostGraph);
		assertContainments(pattern);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		//mistake. There is no match.
		// assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
		
		//we now add the NAC's third node as a positive node to the hostgraph
		//the next invocation of the matcher should return null.
		n6 = buildNode("c", "C", ItemType.NONE, hostGraph);
		e1 = buildEdge("CC", ItemType.NONE, hostGraph);
		e1.setSource(n6); e1.setTarget(n3);
		
		ipm.reset();
		assertNull(ipm.getNextMatching());
	}
	
	private Graph buildHostTwoNegative1(int i) {
		Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		Node n2 = buildNode("a", "A", ItemType.NONE, hostGraph);
		Edge e1 = buildEdge("A", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		
		switch(i) {
		case 0: break;
		case 1: {
			final NegativeApplicationCondition hostNAC = NacFactory.eINSTANCE.createNegativeApplicationCondition();
			
			e1 = buildEdge("B", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			
			GraphCondition gc2 = InvariantCheckerUtil.createNegatedCondition(hostNAC);
			((RuleGraph) hostGraph).setCondition(gc2);
			break;
		}
		case 2: {
			final NegativeApplicationCondition hostNAC = NacFactory.eINSTANCE.createNegativeApplicationCondition();
			e1 = buildEdge("B", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			e1 = buildEdge("C", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			
			GraphCondition gc3 = InvariantCheckerUtil.createNegatedCondition(hostNAC);
			((RuleGraph) hostGraph).setCondition(gc3);
			break;
		}
		case 3: {
			e1 = buildEdge("C", ItemType.NONE, hostGraph);
			e1.setSource(n1); e1.setTarget(n2);
			
			final NegativeApplicationCondition hostNAC = NacFactory.eINSTANCE.createNegativeApplicationCondition();
			e1 = buildEdge("B", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			e1 = buildEdge("C", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			
			GraphCondition gc3 = InvariantCheckerUtil.createNegatedCondition(hostNAC);
			((RuleGraph) hostGraph).setCondition(gc3);
			break;
		}
		case 4: {
			e1 = buildEdge("B", ItemType.NONE, hostGraph);
			e1.setSource(n1); e1.setTarget(n2);
			final NegativeApplicationCondition hostNAC = NacFactory.eINSTANCE.createNegativeApplicationCondition();
			e1 = buildEdge("B", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			e1 = buildEdge("C", ItemType.NONE, hostNAC);
			e1.setSource(n1); e1.setTarget(n2);
			
			GraphCondition gc3 = InvariantCheckerUtil.createNegatedCondition(hostNAC);
			((RuleGraph) hostGraph).setCondition(gc3);
			break;
		}
		}
		
		return hostGraph;
	}
	
	public void testTwoNegativeEdges() {
		final Graph pattern = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition patternNAC = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		patternNAC.setGraph(pattern);
		Node n1 = buildNode("a", "A", ItemType.NONE, pattern);
		Node n2 = buildNode("a", "A", ItemType.NONE, pattern);
		Edge e1 = buildEdge("A", ItemType.NONE, pattern);
		e1.setSource(n1); e1.setTarget(n2);
		e1 = buildEdge("B", ItemType.NONE, patternNAC);
		e1.setSource(n1); e1.setTarget(n2);
		e1 = buildEdge("C", ItemType.NONE, patternNAC);
		e1.setSource(n1); e1.setTarget(n2);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(patternNAC);
		((RuleGraph) pattern).setCondition(gc);
		
		Graph hostGraph = buildHostTwoNegative1(0);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		
		assertNull(ipm.getNextMatching());
		
		hostGraph = buildHostTwoNegative1(1);
		
		ipm.reset();
		ipm.setHostGraph(hostGraph);
		assertCorrectMatch(ipm.getNextMatching());
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
		
		hostGraph = buildHostTwoNegative1(2);
			
		ipm.reset();
		ipm.setHostGraph(hostGraph);
		assertCorrectMatch(ipm.getNextMatching());
		assertCorrectMatch(ipm.getNextMatching());
		assertNull(ipm.getNextMatching());
				
		hostGraph = buildHostTwoNegative1(3);
		ipm.setHostGraph(hostGraph);
		ipm.reset();
		
		assertNull(ipm.getNextMatching());
						
		hostGraph = buildHostTwoNegative1(4);
		ipm.setHostGraph(hostGraph);
		ipm.reset();

		assertNull(ipm.getNextMatching());
		
	}
	
	public void testTypePredicate() {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		TestAdapter ta = ipm.new TestAdapter();
		final Edge e1 = SamgraphFactory.eINSTANCE.createEdge();
		final Edge e2 = SamgraphFactory.eINSTANCE.createEdge();
		//e1.setType(null); e2.setType(null);
		assertTrue(ta.testTypePredicate(e1, e2));
		e1.setInstanceOf(edgeTypes.get("A")); e2.setInstanceOf(edgeTypes.get("B"));
		assertFalse(ta.testTypePredicate(e1, e2));
		e2.setInstanceOf(edgeTypes.get("A"));
		assertTrue(ta.testTypePredicate(e1, e2));
		e1.setInstanceOf(null);
		assertTrue(ta.testTypePredicate(e1, e2));
		assertFalse(ta.testTypePredicate(null, e2));
		assertFalse(ta.testTypePredicate(e1, null));
		assertTrue(ta.testTypePredicate(null, null));
	}
	
	
	public void testNegativeNodeNoMatch() {
		final Graph hostGraph = buildHostGraph4();
		final Graph pattern = buildPattern4();
		final IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		assertNull(ipm.getNextMatching());
	}
	
	public void testNegatedPredicate() {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		TestAdapter ta = ipm.new TestAdapter();
		final Graph g = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition n = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		n.setGraph(g);
		
		final Edge e1 = SamgraphFactory.eINSTANCE.createEdge();
		final Edge e2 = SamgraphFactory.eINSTANCE.createEdge();
		g.getEdges().add(e1); g.getEdges().add(e2);		
		assertTrue(ta.testNegatedPredicate(e1, e2));
		
		g.getEdges().remove(e2);
		n.getEdges().add(e2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(n);
		((RuleGraph) g).setCondition(gc);
		assertFalse(ta.testNegatedPredicate(e1, e2));
		
		n.getEdges().remove(e2);
		g.getEdges().remove(e1);
		n.getEdges().add(e1);
		g.getEdges().add(e2);
		gc = InvariantCheckerUtil.createNegatedCondition(n);
		((RuleGraph) g).setCondition(gc);		
		assertFalse(ta.testNegatedPredicate(e1, e2));
		
		g.getEdges().remove(e2);
		n.getEdges().add(e2);
		n.getEdges().add(e1);
		gc = InvariantCheckerUtil.createNegatedCondition(n);
		((RuleGraph) g).setCondition(gc);

		assertTrue(ta.testNegatedPredicate(e1, e2));
	}
	
	public void testDirectedPredicate() {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		TestAdapter ta = ipm.new TestAdapter();
		Edge e1 = SamgraphFactory.eINSTANCE.createEdge();
		Edge e2 = SamgraphFactory.eINSTANCE.createEdge();
		Node n1 = SamgraphFactory.eINSTANCE.createNode();
		Node n2 = SamgraphFactory.eINSTANCE.createNode();
		e1.setSource(n1);
		e2.setTarget(n2);
		//e1.setDirected(true); hasAccount is undirected, next_up is directed
		//e2.setDirected(true);
		e1.setInstanceOf(edgeTypes.get("next_up"));
		e2.setInstanceOf(edgeTypes.get("next_up"));
		assertFalse(ta.testDirectionPredicate(e1, e2, n1, n2));
		//e1.setDirected(false); e2.setDirected(false);
		e1.setInstanceOf(edgeTypes.get("hasAccount"));
		e2.setInstanceOf(edgeTypes.get("hasAccount"));
		assertTrue(ta.testDirectionPredicate(e1, e2, n1, n2));
		//e1.setDirected(true); e2.setDirected(true); e2.setSource(n2);
		e1.setInstanceOf(edgeTypes.get("next_up"));
		e2.setInstanceOf(edgeTypes.get("next_up"));
		e2.setSource(n2);
		assertTrue(ta.testDirectionPredicate(e1, e2, n1, n2));
		e2.setTarget(null);
		assertTrue(ta.testDirectionPredicate(e1, e2, n1, n2));
	}
	
	public void testIncoherentNac1() {
		for (int i = 0; i < 50; i++) {
			Graph pattern = buildPatternIncoherent1();
			Graph hostGraph = buildPatternIncoherent1();
		
			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			ipm.setHostGraph(hostGraph);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(graphToSubgraph(pattern));
			assertCorrectMatch(ipm.getNextMatching());
		}
	}
	
	public void testIncoherentNac2() {
		for (int i = 0; i < 50; i++) {
			Graph pattern = buildPatternIncoherent2();
			Graph hostGraph = buildPatternIncoherent2();
		
			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			ipm.setHostGraph(hostGraph);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM1 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM2 ===");
			assertFalse(ipm.getNextMatching() != null);
		}
	}
	
	public void testMultipleMatchings() {		
		for (int i = 0; i < 50; i++) {
			Graph pattern = buildMultipleMatchings();
			Graph hostGraph = buildMultipleMatchings();
			
			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			ipm.setHostGraph(hostGraph);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(graphToSubgraph(pattern));
			/*TestAdapter ta = ipm.new TestAdapter();
			for(Node n : pattern.getNodes()) {
				if (n.getName().equals("d1")) {
					ipm.reset();
//					ta.setStartNode(n);
					break;
				}
			}*/
			
			
			System.out.println("============ nextM1 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM2 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM3 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM4 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM5 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM6 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM7 ===");
			assertFalse(ipm.getNextMatching() != null);
		}		
	}
	
	public void testMultipleMatchings2() {		
		for (int i = 0; i < 10; i++) {
			Graph pattern = buildMultipleMatchings2();
			Graph hostGraph = buildMultipleMatchings2();
			
			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			ipm.setHostGraph(hostGraph);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(graphToSubgraph(pattern));
			
			System.out.println("============ nextM1 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM2 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM3 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM4 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM5 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM6 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM7 ===");
			assertFalse(ipm.getNextMatching() != null);
		}		
	}
	
	public void testMultipleEdges() {		
		//for (int i = 0; i < 100; i++) {
			Graph pattern = buildMultipleEdges();
			Graph hostGraph = buildMultipleEdges();
			
			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			ipm.setHostGraph(hostGraph);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(graphToSubgraph(pattern));
			
			System.out.println("============ nextM1 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM2 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM3 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM4 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM5 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM6 ===");
			assertCorrectMatch(ipm.getNextMatching());
			System.out.println("============ nextM7 ===");
			assertFalse(ipm.getNextMatching() != null);
		//}
		
	}
	
	public void testMultipleNacs1() {
		//for (int i = 0; i < 50; i++) {
			Graph pattern = buildMultipleNacs1();
			Graph hostGraph = buildMultipleNacs1();
		
			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			ipm.setHostGraph(hostGraph);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		
			assertCorrectMatch(ipm.getNextMatching());
			assertFalse(ipm.getNextMatching() != null);
		//}
	}
	

	/**
	 * A simple test that is used to check whether the matching algorithm will find all
	 * possible matchings in a regular structure.<br />
	 * <p>The test generates square grids of edge length 2, 3 and 4 and tries to find all 
	 * matchings for a sequence of 3,5 and 7 nodes. Obviously this is the question about 
	 * the number of ways from top left corner to bottom right corner.</p>
	 * <p>For correctness reasons it is important that no match is missed and for performance
	 * reasons it is required not to find any match more than once.</p>
	 */
	public void testNumberOfMatchings() {
		//hostgraph is a square of four nodes with directed edges that point either left or down.
		Graph hostGraph = this.generateGrid(2, true);
		Graph pattern = this.generateSequence(3, true);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		int counter = 0;
		Match gm = ipm.getNextMatching();
		while (gm != null) { 
			counter++;
			gm = ipm.getNextMatching();
		}
		assertEquals(2, counter);
		
		ipm.reset();
		
		hostGraph = this.generateGrid(3, true);
		pattern = this.generateSequence(5, true);
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		counter = 0;
		while (ipm.getNextMatching() != null) 
			counter++;
		assertEquals(6, counter);
		
		ipm.reset();
		counter = 0;
		while (ipm.getNextMatching() != null) 
			counter++;
		assertEquals(6, counter);
		
		ipm.reset();
		
		hostGraph = this.generateGrid(4, true);
		pattern = this.generateSequence(7, true);
		ipm.setHostGraph(hostGraph);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		counter = 0;
		while (ipm.getNextMatching() != null)
			counter++;
		assertEquals(20, counter);
	}

	private Graph buildHostGraph4() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("c", "Client", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("a", "Account", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("b", "Bill", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("bNac", "Bill", ItemType.NONE, nac);
		final Edge e1 = buildEdge("hasAccount", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("pays", ItemType.NONE, hostGraph);
		e2.setSource(n1); e2.setTarget(n3);
		final Edge e3 = buildEdge("pays", ItemType.NONE, nac);
		e3.setSource(n1); e3.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPattern4() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("c", "Client", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("a", "Account", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("bNac", "Bill", ItemType.NONE, nac);
		final Edge e1 = buildEdge("hasAccount", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e3 = buildEdge("pays", ItemType.NONE, nac);
		e3.setSource(n1); e3.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	
	private Graph buildHostGraph3() {
		final Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("b", "B", ItemType.NONE, result);
		final Node n2 = buildNode("a", "A", ItemType.NONE, result);
		final Node n3 = buildNode("a2", "A", ItemType.NONE, result);
		final Node n4 = buildNode("c1", "C", ItemType.NONE, result);
		final Node n5 = buildNode("c2", "C", ItemType.NONE, nac);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, result);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("AB", ItemType.NONE, result);
		e2.setSource(n1); e2.setTarget(n3);
		final Edge e3 = buildEdge("BC", ItemType.NONE, result);
		e3.setSource(n1); e3.setTarget(n4);
		final Edge e4 = buildEdge("BC", ItemType.NONE, nac);
		e4.setSource(n5); e4.setTarget(n1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	
	private Graph buildPattern3() {
		final Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac.setGraph(result);
		final Node n1 = buildNode("a", "A", ItemType.NONE, result);
		final Node n2 = buildNode("b", "B", ItemType.NONE, result);
		final Node n3 = buildNode("c", "C", ItemType.NONE, nac);
	
		final Edge e1 = buildEdge("AB", ItemType.NONE, result);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, nac);
		e2.setSource(n2); e2.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	/**
	 * @return
	 */
	private Graph buildHostGraph() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e1", "E", ItemType.NONE, hostGraph);
		final Node n6 = buildNode("e2", "E", ItemType.NONE, hostGraph);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("BE", ItemType.NONE, hostGraph);
		e4.setSource(n2); e4.setTarget(n5);
		final Edge e5 = buildEdge("BE", ItemType.NONE, hostGraph);
		e5.setSource(n2); e5.setTarget(n6);
		return hostGraph;
	}
	
	private Graph buildPattern() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e1", "E", ItemType.NONE, hostGraph);
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e4 = buildEdge("BE", ItemType.NONE, hostGraph);
		e4.setSource(n2); e4.setTarget(n5);
		return hostGraph;

	}
	
	private Graph buildHostGraph2() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e1", "E", ItemType.NONE, hostGraph);
		final Node n6 = buildNode("e2", "E", ItemType.NONE, hostGraph);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("BE", ItemType.NONE, hostGraph);
		e4.setSource(n2); e4.setTarget(n5);
		final Edge e5 = buildEdge("BE", ItemType.NONE, hostGraph);
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("AC", ItemType.NONE, hostGraph);
		e6.setSource(n1); e6.setTarget(n3);
		return hostGraph;
	}
	
	private Graph buildPattern2() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e", "E", ItemType.NONE, hostGraph);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e4 = buildEdge("BE", ItemType.NONE, hostGraph);
		e4.setSource(n2); e4.setTarget(n5);
		final Edge e5 = buildEdge("AC", ItemType.NONE, hostGraph);
		e5.setSource(n1); e5.setTarget(n3);
		return hostGraph;
	}
	
	private Graph buildHostGraphNacMatch() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, nac);
		final Node n4 = buildNode("d", "D", ItemType.NONE, nac);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, nac);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, nac);
		e3.setSource(n3); e3.setTarget(n4);		
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNacMatch() {
		final Graph subGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, subGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, subGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, nac);		
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, subGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, nac);
		e2.setSource(n2); e2.setTarget(n3);		
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) subGraph).setCondition(gc);
		
		return subGraph;
	}
	
	private Graph buildHostGraphNac2() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, nac);
		final Node n3 = buildNode("c", "C", ItemType.NONE, nac);
			
		final Edge e1 = buildEdge("AB", ItemType.NONE, nac);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("AC", ItemType.NONE, nac);
		e2.setSource(n1); e2.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNac2() {
		final Graph subGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, subGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, nac);				
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, nac);
		e1.setSource(n1); e1.setTarget(n2);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) subGraph).setCondition(gc);
		
		return subGraph;
	}

	private Graph buildHostGraphNac3() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, nac1);
		final Node n3 = buildNode("c", "C", ItemType.NONE, nac2);
			
		final Edge e1 = buildEdge("AB", ItemType.NONE, nac1);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, nac2);
		e2.setSource(n1); e2.setTarget(n3);		
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) hostGraph).setCondition(gc);
		
		//nac2.getNodes().add(n3);
		//nac2.getEdges().add(e2);
		return hostGraph;
	}
	
	private Graph buildPatternNac3() {
		final Graph subGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, subGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, nac);				
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, nac);
		e1.setSource(n1); e1.setTarget(n2);				
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) subGraph).setCondition(gc);
		
		return subGraph;
	}
	
	private Graph buildHostGraphNac4() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, nac1);
			
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, nac1);
		e2.setSource(n2); e2.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac1);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNac4() {
		final Graph subGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Node n1 = buildNode("a", "A", ItemType.NONE, subGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, subGraph);				
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, subGraph);
		e1.setSource(n1); e1.setTarget(n2);				
		
				
		return subGraph;
	}
	
	private Graph buildHostGraphNac5() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e", "E", ItemType.NONE, hostGraph);
		
		final Node n6 = buildNode("f", "F", ItemType.NONE, nac);
		final Node n7 = buildNode("g", "G", ItemType.NONE, nac);
		final Node n8 = buildNode("h", "H", ItemType.NONE, nac);
		
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("DE", ItemType.NONE, hostGraph);
		e4.setSource(n4); e4.setTarget(n5);
		
		final Edge e5 = buildEdge("BF", ItemType.NONE, nac);		
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("FG", ItemType.NONE, nac);
		e6.setSource(n6); e6.setTarget(n7);
		final Edge e7 = buildEdge("GH", ItemType.NONE, nac);
		e7.setSource(n7); e7.setTarget(n8);
		final Edge e8 = buildEdge("HD", ItemType.NONE, nac);
		e8.setSource(n8); e8.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNac5() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
				
		final Node n6 = buildNode("f", "F", ItemType.NONE, nac);
		final Node n7 = buildNode("g", "G", ItemType.NONE, nac);
		final Node n8 = buildNode("h", "H", ItemType.NONE, nac);		
		
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
				
		final Edge e5 = buildEdge("BF", ItemType.NONE, nac);		
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("FG", ItemType.NONE, nac);
		e6.setSource(n6); e6.setTarget(n7);
		final Edge e7 = buildEdge("GH", ItemType.NONE, nac);
		e7.setSource(n7); e7.setTarget(n8);
		final Edge e8 = buildEdge("HD", ItemType.NONE, nac);
		e8.setSource(n8); e8.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildHostGraphNac6() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e", "E", ItemType.NONE, hostGraph);
		
		final Node n6 = buildNode("f", "F", ItemType.NONE, nac);
		final Node n7 = buildNode("g", "G", ItemType.NONE, nac);
		final Node n8 = buildNode("h", "H", ItemType.NONE, nac);
		
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("DE", ItemType.NONE, hostGraph);
		e4.setSource(n4); e4.setTarget(n5);
		
		final Edge e5 = buildEdge("BF", ItemType.NONE, nac);		
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("FG", ItemType.NONE, nac);
		e6.setSource(n6); e6.setTarget(n7);
		final Edge e7 = buildEdge("GH", ItemType.NONE, nac);
		e7.setSource(n7); e7.setTarget(n8);
		final Edge e8 = buildEdge("HD", ItemType.NONE, nac);
		e8.setSource(n8); e8.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNac6() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e", "E", ItemType.NONE, hostGraph);
				
		final Node n6 = buildNode("f", "F", ItemType.NONE, nac);
		final Node n7 = buildNode("g", "G", ItemType.NONE, nac);
		final Node n8 = buildNode("h", "H", ItemType.NONE, nac);		
		
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("DE", ItemType.NONE, hostGraph);
		e4.setSource(n4); e4.setTarget(n5);
				
		final Edge e5 = buildEdge("BF", ItemType.NONE, nac);		
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("FG", ItemType.NONE, nac);
		e6.setSource(n6); e6.setTarget(n7);
		final Edge e7 = buildEdge("GH", ItemType.NONE, nac);
		e7.setSource(n7); e7.setTarget(n8);
		final Edge e8 = buildEdge("HD", ItemType.NONE, nac);
		e8.setSource(n8); e8.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}

	private Graph buildHostGraphNac7() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e", "E", ItemType.NONE, hostGraph);
		
		final Node n6 = buildNode("f", "F", ItemType.NONE, nac);
		final Node n7 = buildNode("g", "G", ItemType.NONE, nac);
		final Node n8 = buildNode("h", "H", ItemType.NONE, nac);
		final Node n9 = buildNode("i", "I", ItemType.NONE, nac);
		
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("DE", ItemType.NONE, hostGraph);
		e4.setSource(n4); e4.setTarget(n5);
		
		final Edge e5 = buildEdge("BF", ItemType.NONE, nac);		
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("FG", ItemType.NONE, nac);
		e6.setSource(n6); e6.setTarget(n7);
		final Edge e7 = buildEdge("GH", ItemType.NONE, nac);
		e7.setSource(n7); e7.setTarget(n8);
		final Edge e8 = buildEdge("HD", ItemType.NONE, nac);
		e8.setSource(n8); e8.setTarget(n4);
		final Edge e9 = buildEdge("HI", ItemType.NONE, nac);
		e9.setSource(n8); e9.setTarget(n9);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNac7() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		final Node n4 = buildNode("d", "D", ItemType.NONE, hostGraph);
		final Node n5 = buildNode("e", "E", ItemType.NONE, hostGraph);
				
		final Node n6 = buildNode("f", "F", ItemType.NONE, nac);
		final Node n7 = buildNode("g", "G", ItemType.NONE, nac);
		final Node n8 = buildNode("h", "H", ItemType.NONE, nac);		
		final Node n9 = buildNode("i", "I", ItemType.NONE, nac);
		
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", ItemType.NONE, hostGraph);
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("DE", ItemType.NONE, hostGraph);
		e4.setSource(n4); e4.setTarget(n5);
				
		final Edge e5 = buildEdge("BF", ItemType.NONE, nac);		
		e5.setSource(n2); e5.setTarget(n6);
		final Edge e6 = buildEdge("FG", ItemType.NONE, nac);
		e6.setSource(n6); e6.setTarget(n7);
		final Edge e7 = buildEdge("GH", ItemType.NONE, nac);
		e7.setSource(n7); e7.setTarget(n8);
		final Edge e8 = buildEdge("HD", ItemType.NONE, nac);
		e8.setSource(n8); e8.setTarget(n4);
		final Edge e9 = buildEdge("HI", ItemType.NONE, nac);
		e9.setSource(n8); e9.setTarget(n9);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildHostGraphNac8() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		
		final Edge e3 = buildEdge("AC", ItemType.NONE, nac);		
		e3.setSource(n1); e3.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}
	
	private Graph buildPatternNac8() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		final Node n1 = buildNode("a", "A", ItemType.NONE, hostGraph);
		final Node n2 = buildNode("b", "B", ItemType.NONE, hostGraph);
		final Node n3 = buildNode("c", "C", ItemType.NONE, hostGraph);
		
		final Edge e1 = buildEdge("AB", ItemType.NONE, hostGraph);
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", ItemType.NONE, hostGraph);
		e2.setSource(n2); e2.setTarget(n3);
		
		final Edge e3 = buildEdge("AC", ItemType.NONE, nac);		
		e3.setSource(n1); e3.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		((RuleGraph) hostGraph).setCondition(gc);
		
		return hostGraph;
	}

	private Graph buildPatternIncoherent1() {
		Graph pattern7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition pattern7Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern7Nac.setName("nac1"); pattern7Nac.setGraph(pattern7);
		
		Node pattern7N1 = buildNode("a1", "A", ItemType.NONE, pattern7);
		Node pattern7N2 = buildNode("aNac", "A", ItemType.NONE, pattern7Nac);
		Node pattern7N3 = buildNode("b1", "B", ItemType.NONE, pattern7);
		Node pattern7N4 = buildNode("bNac", "B", ItemType.NONE, pattern7Nac);
		Node pattern7N5 = buildNode("c1Nac", "C", ItemType.NONE, pattern7Nac);
		Node pattern7N6 = buildNode("c2Nac", "C", ItemType.NONE, pattern7Nac);
		
		Edge pattern7E1 = buildEdge("AB", ItemType.NONE, pattern7);
		Edge pattern7E2 = buildEdge("AB", ItemType.NONE, pattern7Nac);
		Edge pattern7E3 = buildEdge("AB", ItemType.NONE, pattern7Nac);
		Edge pattern7E4 = buildEdge("BC", ItemType.NONE, pattern7Nac);
		Edge pattern7E5 = buildEdge("BC", ItemType.NONE, pattern7Nac);
		
		pattern7E1.setSource(pattern7N1); pattern7E1.setTarget(pattern7N3);
		pattern7E2.setSource(pattern7N1); pattern7E2.setTarget(pattern7N4);
		pattern7E3.setSource(pattern7N2); pattern7E3.setTarget(pattern7N4);
		pattern7E4.setSource(pattern7N3); pattern7E4.setTarget(pattern7N6);
		pattern7E5.setSource(pattern7N4); pattern7E5.setTarget(pattern7N5);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern7Nac);
		((RuleGraph) pattern7).setCondition(gc);
		
		return pattern7;	
	}
	
	private Graph buildPatternIncoherent2() {
		Graph resultGraph7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", ItemType.NONE, resultGraph7);
		Node n2 = buildNode("b1", "B", ItemType.NONE, resultGraph7);
		Node n3 = buildNode("b2", "B", ItemType.NONE, resultGraph7);
		Node n4 = buildNode("b3", "B", ItemType.NONE, resultGraph7);
		Node n5 = buildNode("c1", "C", ItemType.NONE, resultGraph7);
		Node n6 = buildNode("c2", "C", ItemType.NONE, resultGraph7);
		Node n7 = buildNode("c3", "C", ItemType.NONE, resultGraph7);
		
		Edge e1 = buildEdge("AB", ItemType.NONE, resultGraph7);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB", ItemType.NONE, resultGraph7);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("AB", ItemType.NONE, resultGraph7);
		e3.setSource(n1); e3.setTarget(n4);
		Edge e4 = buildEdge("BC", ItemType.NONE, resultGraph7);
		e4.setSource(n2); e4.setTarget(n5);
		Edge e5 = buildEdge("BC", ItemType.NONE, resultGraph7);
		e5.setSource(n2); e5.setTarget(n7);
		Edge e6 = buildEdge("BC", ItemType.NONE, resultGraph7);
		e6.setSource(n4); e6.setTarget(n6);
		
		Node nn1;
		Node nn2;
		Node nn3;
		Node nn4;
		
		/*
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac1);
		nn2 = buildNode("bNac", "B", ItemType.NONE, nac1);
		nn3 = buildNode("c2Nac", "C", ItemType.NONE, nac1);
		nn4 = buildNode("c1Nac", "C", ItemType.NONE, nac1);
		
		e1 = buildEdge("AB", ItemType.NONE, nac1);
		e1.setSource(n1); e1.setTarget(nn2);
		e2 = buildEdge("BC", ItemType.NONE, nac1);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", ItemType.NONE, nac1);
		e3.setSource(nn1); e3.setTarget(nn2);
		e4 = buildEdge("BC", ItemType.NONE, nac1);
		e4.setSource(nn2); e4.setTarget(nn4);
		*/
		/*
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac2);
		nn2 = buildNode("bNac", "B", ItemType.NONE, nac2);		
		nn4 = buildNode("c1Nac", "C", ItemType.NONE, nac2);
		
		e1 = buildEdge("AB", ItemType.NONE, nac2);
		e1.setSource(n1); e1.setTarget(nn2);		
		e3 = buildEdge("AB", ItemType.NONE, nac2);
		e3.setSource(nn1); e3.setTarget(nn2);
		e4 = buildEdge("BC", ItemType.NONE, nac2);
		e4.setSource(nn2); e4.setTarget(nn4);
		*/
		/*
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac3);				
		nn4 = buildNode("c1Nac", "C", ItemType.NONE, nac3);		
				
		e3 = buildEdge("AB", ItemType.NONE, nac3);
		e3.setSource(nn1); e3.setTarget(n3);
		e4 = buildEdge("BC", ItemType.NONE, nac3);
		e4.setSource(n3); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac4);				
		nn4 = buildNode("c1Nac", "C", ItemType.NONE, nac4);		
				
		e3 = buildEdge("AB", ItemType.NONE, nac4);
		e3.setSource(nn1); e3.setTarget(n4);
		e4 = buildEdge("BC", ItemType.NONE, nac4);
		e4.setSource(n4); e4.setTarget(nn4);
		*/
		NegativeApplicationCondition nac5 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac5);
				
		e3 = buildEdge("AB", ItemType.NONE, nac5);
		e3.setSource(nn1); e3.setTarget(n4);
		/*
		NegativeApplicationCondition nac6 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac6);		
		nn3 = buildNode("c2Nac", "C", ItemType.NONE, nac6);
		nn4 = buildNode("c1Nac", "C", ItemType.NONE, nac6);
				
		e2 = buildEdge("BC", ItemType.NONE, nac6);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", ItemType.NONE, nac6);
		e3.setSource(nn1); e3.setTarget(n3);
		e4 = buildEdge("BC", ItemType.NONE, nac6);
		e4.setSource(n3); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac7 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac7);		
		nn3 = buildNode("c2Nac", "C", ItemType.NONE, nac7);
		nn4 = buildNode("c1Nac", "C", ItemType.NONE, nac7);
				
		e2 = buildEdge("BC", ItemType.NONE, nac7);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", ItemType.NONE, nac7);
		e3.setSource(nn1); e3.setTarget(n4);
		e4 = buildEdge("BC", ItemType.NONE, nac7);
		e4.setSource(n4); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac8 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", ItemType.NONE, nac8);		
		nn3 = buildNode("c2Nac", "C", ItemType.NONE, nac8);
						
		e2 = buildEdge("BC", ItemType.NONE, nac8);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", ItemType.NONE, nac8);
		e3.setSource(nn1); e3.setTarget(n4);*/		
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		//nacs.add(nac1);
		//nacs.add(nac2);
		//nacs.add(nac3);
		//nacs.add(nac4);
		nacs.add(nac5);
		//nacs.add(nac6);
		//nacs.add(nac7);
		//nacs.add(nac8);		
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph7).setCondition(gc);
		
		return resultGraph7;

	}

	private Graph buildMultipleMatchings() {
		Graph resultGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", ItemType.NONE, resultGraph);
		Node n2 = buildNode("b1", "B", ItemType.NONE, resultGraph);
		Node nc1 = buildNode("c1", "C", ItemType.NONE, resultGraph);
		Node nc2 = buildNode("c2", "C", ItemType.NONE, resultGraph);
		Node nc3 = buildNode("c3", "C", ItemType.NONE, resultGraph);
		Node n3 = buildNode("d1", "D", ItemType.NONE, resultGraph);
		Node n4 = buildNode("e1", "E", ItemType.NONE, resultGraph);
		
		Edge e1 = buildEdge("AB", ItemType.NONE, resultGraph);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC", ItemType.NONE, resultGraph);
		e2.setSource(n2); e2.setTarget(nc1);
		Edge e3 = buildEdge("BC", ItemType.NONE, resultGraph);
		e3.setSource(n2); e3.setTarget(nc2);
		Edge e4 = buildEdge("BC", ItemType.NONE, resultGraph);
		e4.setSource(n2); e4.setTarget(nc3);		
		Edge e5 = buildEdge("BD", ItemType.NONE, resultGraph);
		e5.setSource(n2); e5.setTarget(n3);
		Edge e6 = buildEdge("DE", ItemType.NONE, resultGraph);
		e6.setSource(n3); e6.setTarget(n4);
		
		return resultGraph;
	}

	private Graph buildMultipleMatchings2() {
		Graph resultGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		//Node n1 = buildNode("a1", "A", ItemType.NONE, resultGraph);
		Node n2 = buildNode("b1", "B", ItemType.NONE, resultGraph);
		Node nc1 = buildNode("c1", "C", ItemType.NONE, resultGraph);
		Node nc2 = buildNode("c2", "C", ItemType.NONE, resultGraph);
		Node nc3 = buildNode("c3", "C", ItemType.NONE, resultGraph);
		//Node n3 = buildNode("d1", "D", ItemType.NONE, resultGraph);
		//Node n4 = buildNode("e1", "E", ItemType.NONE, resultGraph);
		
		//Edge e1 = buildEdge("AB", ItemType.NONE, resultGraph);
		//e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC", ItemType.NONE, resultGraph);
		e2.setSource(n2); e2.setTarget(nc1);
		Edge e3 = buildEdge("BC", ItemType.NONE, resultGraph);
		e3.setSource(n2); e3.setTarget(nc2);
		Edge e4 = buildEdge("BC", ItemType.NONE, resultGraph);
		e4.setSource(n2); e4.setTarget(nc3);		
		/*Edge e5 = buildEdge("BD", ItemType.NONE, resultGraph);
		e5.setSource(n2); e5.setTarget(n3);
		Edge e6 = buildEdge("DE", ItemType.NONE, resultGraph);
		e6.setSource(n3); e6.setTarget(n4);*/
		
		return resultGraph;
	}
	
	private Graph buildMultipleEdges() {
		Graph resultGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		//Node n1 = buildNode("a1", "A", ItemType.NONE, resultGraph);
		Node n2 = buildNode("b1", "B", ItemType.NONE, resultGraph);
		Node nc1 = buildNode("c1", "C", ItemType.NONE, resultGraph);
		//Node n3 = buildNode("d1", "D", ItemType.NONE, resultGraph);
		//Node n4 = buildNode("e1", "E", ItemType.NONE, resultGraph);
		
		//Edge e1 = buildEdge("AB", ItemType.NONE, resultGraph);
		//e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC", ItemType.NONE, resultGraph);
		e2.setSource(n2); e2.setTarget(nc1);
		Edge e3 = buildEdge("BC", ItemType.NONE, resultGraph);
		e3.setSource(n2); e3.setTarget(nc1);
		Edge e4 = buildEdge("BC", ItemType.NONE, resultGraph);
		e4.setSource(n2); e4.setTarget(nc1);		
		//Edge e5 = buildEdge("BD", ItemType.NONE, resultGraph);
		//e5.setSource(n2); e5.setTarget(n3);
		//Edge e6 = buildEdge("DE", ItemType.NONE, resultGraph);
		//e6.setSource(n3); e6.setTarget(n4);
		
		return resultGraph;
	}	
	
	private Graph buildMultipleNacs1() {
		Graph resultGraph8 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", ItemType.NONE, resultGraph8);
		Node n2 = buildNode("b1", "B", ItemType.NONE, resultGraph8);
		Edge e1 = buildEdge("AB", ItemType.NONE, resultGraph8);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", ItemType.NONE, resultGraph8);
		Edge e2 = buildEdge("BC", ItemType.NONE, resultGraph8);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("d", "D", ItemType.NONE, resultGraph8);
		Edge e3 = buildEdge("AD", ItemType.NONE, resultGraph8);
		e3.setSource(n1); e3.setTarget(n4);
		
		Node nn1;
		Node nn2;
		Node nn3;
		Edge e4;
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("cNac", "C", ItemType.NONE, nac1);
		nn2 = buildNode("dNac", "D", ItemType.NONE, nac1);
		nn3 = buildNode("eNac", "E", ItemType.NONE, nac1);
		
		e1 = buildEdge("BC", ItemType.NONE, nac1);
		e1.setSource(n2); e1.setTarget(nn1);
		e2 = buildEdge("CD", ItemType.NONE, nac1);
		e2.setSource(nn1); e2.setTarget(nn2);
		e3 = buildEdge("DE", ItemType.NONE, nac1);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("EA", ItemType.NONE, nac1);
		e4.setSource(nn3); e4.setTarget(n1);
		
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn2 = buildNode("dNac", "D", ItemType.NONE, nac2);
		nn3 = buildNode("eNac", "E", ItemType.NONE, nac2);
				
		e2 = buildEdge("CD", ItemType.NONE, nac2);
		e2.setSource(n3); e2.setTarget(nn2);
		e3 = buildEdge("DE", ItemType.NONE, nac2);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("EA", ItemType.NONE, nac2);
		e4.setSource(nn3); e4.setTarget(n1);
		
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn1 = buildNode("cNac", "C", ItemType.NONE, nac3);
		nn3 = buildNode("eNac", "E", ItemType.NONE, nac3);
		
		e1 = buildEdge("BC", ItemType.NONE, nac3);
		e1.setSource(n2); e1.setTarget(nn1);
		e2 = buildEdge("CD", ItemType.NONE, nac3);
		e2.setSource(nn1); e2.setTarget(n4);
		e3 = buildEdge("DE", ItemType.NONE, nac3);
		e3.setSource(n4); e3.setTarget(nn3);
		e4 = buildEdge("EA", ItemType.NONE, nac3);
		e4.setSource(nn3); e4.setTarget(n1);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn3 = buildNode("eNac", "E", ItemType.NONE, nac4);
				
		e2 = buildEdge("CD", ItemType.NONE, nac4);
		e2.setSource(n3); e2.setTarget(n4);
		e3 = buildEdge("DE", ItemType.NONE, nac4);
		e3.setSource(n4); e3.setTarget(nn3);
		e4 = buildEdge("EA", ItemType.NONE, nac4);
		e4.setSource(nn3); e4.setTarget(n1);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		nacs.add(nac3);
		nacs.add(nac4);				
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph8).setCondition(gc);
		
		return resultGraph8;
		
	}
	
	
	
	private GraphRule buildRule1() {
		GraphRule rule = SamrulesFactory.eINSTANCE.createGraphRule();
		
		RuleGraph lhs2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node lhs2n1 = buildNode("a", "A",  ItemType.DELETED, lhs2);
		Node lhs2n2 = buildNode("b", "B",  ItemType.PRESERVED, lhs2);		
		Edge lhs2e1 = buildEdge("AB",  ItemType.DELETED, lhs2);
		lhs2e1.setSource(lhs2n1); lhs2e1.setTarget(lhs2n2);
		
		RuleGraph rhs2 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		Node rhs2n1 = buildNode("b", "B",  ItemType.PRESERVED, rhs2);
		Node rhs2n2 = buildNode("c", "C",  ItemType.CREATED, rhs2);		
		Edge rhs2e1 = buildEdge("BC",  ItemType.CREATED, rhs2);
		rhs2e1.setSource(rhs2n1); rhs2e1.setTarget(rhs2n2);		
		
		rule.setLeft(lhs2);
		rule.setRight(rhs2);
		
		((PreservedNode) rhs2n1).setRefInRule((PreservedNode) lhs2n2);
		
		return rule;
	}
	
	private GraphRule buildRule2() {
		GraphRule rule = SamrulesFactory.eINSTANCE.createGraphRule();
		
		RuleGraph lhs2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node lhs2n1 = buildNode("a", "A",  ItemType.PRESERVED, lhs2);
		Node lhs2n2 = buildNode("b", "B",  ItemType.PRESERVED, lhs2);		
		Edge lhs2e1 = buildEdge("AB",  ItemType.PRESERVED, lhs2);
		lhs2e1.setSource(lhs2n1); lhs2e1.setTarget(lhs2n2);
		
		RuleGraph rhs2 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		Node rhs2n1 = buildNode("a", "A",  ItemType.PRESERVED, rhs2);
		Node rhs2n2 = buildNode("b", "B",  ItemType.PRESERVED, rhs2);		
		Edge rhs2e1 = buildEdge("AB",  ItemType.PRESERVED, rhs2);
		rhs2e1.setSource(rhs2n1); rhs2e1.setTarget(rhs2n2);
		Node rhs2n3 = buildNode("c", "C",  ItemType.CREATED, rhs2);		
		Edge rhs2e2 = buildEdge("BC",  ItemType.CREATED, rhs2);
		rhs2e2.setSource(rhs2n2); rhs2e2.setTarget(rhs2n3);		
		
		rule.setLeft(lhs2);
		rule.setRight(rhs2);
		
		((PreservedNode) rhs2n1).setRefInRule((PreservedNode) lhs2n1);
		((PreservedNode) rhs2n2).setRefInRule((PreservedNode) lhs2n2);
		((PreservedEdge) rhs2e1).setRefInRule((PreservedEdge) lhs2e1);
		
		return rule;
	}
		
	public void testTestCaseMatchSubgraph2() {
		RuleGraph right = (RuleGraph) buildRightSide2();
		RuleGraph forb = (RuleGraph) buildForbiddenSubgraph();
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(right);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(forb);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(right);
		
		Match m = ipm.getNextMatching();		
		while (m != null) {
			assertCorrectMatch(m);
			boolean inc = false;
			//System.out.println("new Match----------");
			for (Map.Entry<Node, Node> me : m.getNodeMatching().entrySet()) {
				Node key = me.getKey();
				Node value = me.getValue();
				for (Map.Entry<Node, Node> mec : m.getNodeMatching().entrySet()) {
					if (mec.getValue() == value) {
						if (mec.getKey() != key) {
							inc = true;
						}
					}
				}
			}
			if (inc) {
				System.out.println();
				System.out.println("error: subgraph: ");
				for (AnnotatedElem elem : subgraph) {
					if (elem instanceof Node) {
						System.out.print(((Node) elem).getName() + ", ");
					} else {
						System.out.print(elem + ", ");
					}
				}
				System.out.println();
				System.out.println("Match:");
				for (Map.Entry<Node, Node> me : m.getNodeMatching().entrySet()) {
					System.out.print(me.getKey().getName());
					System.out.print("-");
					System.out.print(me.getValue().getName());
					System.out.print(", ");
				}
			}
			assertFalse(inc);
			m = ipm.getNextMatching();
		}		
	}
	
	// new nac match algorithm test
	private Graph buildSpecialPattern1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, nac);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, nac);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC",  ItemType.PATTERN, nac);
		e2.setSource(n2); e2.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildSpecialHost1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("b", "B",  ItemType.PATTERN, nac);
		Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac);
		e3.setSource(n3); e3.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
		
	private Graph buildSpecialHost2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("b", "B",  ItemType.PATTERN, nac1);
		Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac1);
		Node n5 = buildNode("c", "C",  ItemType.PATTERN, nac2);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac1);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac1);
		e3.setSource(n3); e3.setTarget(n4);
		Edge e4 = buildEdge("BC",  ItemType.PATTERN, nac2);
		e4.setSource(n2); e4.setTarget(n5);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildSpecialHost3() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("b", "B",  ItemType.PATTERN, nac1);
		Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac1);
		Node n5 = buildNode("c", "C",  ItemType.PATTERN, nac2);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac1);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac1);
		e3.setSource(n3); e3.setTarget(n4);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildSpecialPattern2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, nac);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e2 = buildEdge("BC",  ItemType.PATTERN, nac);
		e2.setSource(n2); e2.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildSpecialHost4() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("b", "B",  ItemType.PATTERN, nac1);
		Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac1);
		Node n5 = buildNode("c", "C",  ItemType.PATTERN, nac2);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac1);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac1);
		e3.setSource(n3); e3.setTarget(n4);
		Edge e4 = buildEdge("BC",  ItemType.PATTERN, nac2);
		e4.setSource(n2); e4.setTarget(n5);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}

	private Graph buildSpecialHost5() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n2a = buildNode("c", "C",  ItemType.PATTERN, result);
		Node n3 = buildNode("b", "B",  ItemType.PATTERN, nac1);
		Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac1);
		Node n5 = buildNode("c", "C",  ItemType.PATTERN, nac2);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac1);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac1);
		e3.setSource(n3); e3.setTarget(n4);
		Edge e4 = buildEdge("BC",  ItemType.PATTERN, nac2);
		e4.setSource(n2); e4.setTarget(n5);
		Edge e5 = buildEdge("BC",  ItemType.PATTERN, result);
		e5.setSource(n2); e5.setTarget(n2a);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}

	
	private Graph buildIncoherentPattern1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);		
		Node n3 = buildNode("bNac", "B",  ItemType.PATTERN, result);
		//Node n4 = buildNode("cNac1", "C",  ItemType.PATTERN, result);
		Node n5 = buildNode("cNac2", "C",  ItemType.PATTERN, result);
		
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n3); e1.setName("abNac");
		//Edge e2 = buildEdge("BC",  ItemType.PATTERN, result);
		//e2.setSource(n3); e2.setTarget(n4); e2.setName("bc1Nac");
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, result);
		e3.setSource(n2); e3.setTarget(n5); e3.setName("bc2Nac");
		
		return result;
	}
	
	private Graph buildCoherentHost1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();		
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b1", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("b2", "B",  ItemType.PATTERN, result);
		Node n4 = buildNode("c1", "C",  ItemType.PATTERN, result);
		Node n5 = buildNode("c3", "C",  ItemType.PATTERN, result);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2); e1.setName("ab1");
		Edge e2 = buildEdge("AB",  ItemType.PATTERN, result);
		e2.setSource(n1); e2.setTarget(n3); e2.setName("ab2");
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, result);
		e3.setSource(n2); e3.setTarget(n4); e3.setName("bc1");
		Edge e4 = buildEdge("BC",  ItemType.PATTERN, result);
		e4.setSource(n2); e4.setTarget(n5); e4.setName("bc3");
		
		return result;
	}
	
	private Graph buildDoublyIsomorphicGraph() {
		Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node merged7N1 = buildNode("a1", "A", ItemType.PATTERN, result);
		Node merged7N2 = buildNode("b1", "B", ItemType.PATTERN, result);
		Node merged7N3 = buildNode("b2", "B", ItemType.PATTERN, result);
		Node merged7N4 = buildNode("b3", "B", ItemType.PATTERN, result);
		Node merged7N5 = buildNode("c1", "C", ItemType.PATTERN, result);
		Node merged7N6 = buildNode("c2", "C", ItemType.PATTERN, result);
		Node merged7N7 = buildNode("c3", "C", ItemType.PATTERN, result);
		
		Edge merged7E1 = buildEdge("AB", ItemType.PATTERN, result);
		merged7E1.setSource(merged7N1); merged7E1.setTarget(merged7N2);
		Edge merged7E2 = buildEdge("AB", ItemType.PATTERN, result);
		merged7E2.setSource(merged7N1); merged7E2.setTarget(merged7N3);
		Edge merged7E3 = buildEdge("AB", ItemType.PATTERN, result);
		merged7E3.setSource(merged7N1); merged7E3.setTarget(merged7N4);
		Edge merged7E4 = buildEdge("BC", ItemType.PATTERN, result);
		merged7E4.setSource(merged7N2); merged7E4.setTarget(merged7N5);
		Edge merged7E5 = buildEdge("BC", ItemType.PATTERN, result);
		merged7E5.setSource(merged7N2); merged7E5.setTarget(merged7N7);
		Edge merged7E6 = buildEdge("BC", ItemType.PATTERN, result);
		merged7E6.setSource(merged7N4); merged7E6.setTarget(merged7N6);
		
		return result;
	}
	
	public void testIncoherentCoherentCase() {
		Graph pattern = buildIncoherentPattern1();
		Graph host = buildCoherentHost1();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		System.out.println("===");
		m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
	}
	
	public void testTestNewAlgorithm1() {
		Graph pattern = buildSpecialPattern1();
		Graph host = buildSpecialHost1();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertNull(m);		
	}
	
	public void testTestNewAlgorithm2() {
		Graph pattern = buildSpecialPattern1();
		Graph host = buildSpecialHost2();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
	}
	
	public void testTestNewAlgorithm3() {
		Graph pattern = buildSpecialHost1();
		Graph host = buildSpecialHost1();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
		
		ipm.reset();
		subgraph = SubgraphIterator.graphToSubGraph(host);		
		ipm.setHostGraph(pattern);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(host);
		
		m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
		
	}
	
	public void testTestNewAlgorithm4() {
		Graph pattern = buildDoublyIsomorphicGraph();
		Graph host = buildDoublyIsomorphicGraph();
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
	}
	
	public void testTestNewAlgorithm5() {
		Graph pattern = buildSpecialPattern1();
		Graph host = buildSpecialHost5();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		assertNull(ipm.getNextMatching());
	}
	
	public void testTestNewAlgorithm6() {
		Graph pattern = buildSpecialPattern2();
		Graph host = buildSpecialHost5();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		assertNull(ipm.getNextMatching());
	}
	
	public void testLLF1() {
		Graph pattern = buildSpecialPattern1();
		Graph host = buildSpecialHost3();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
	}
	
	public void testLLF2() {
		Graph pattern = buildSpecialPattern2();
		Graph host = buildSpecialHost4();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertNull(m);
	}
	
	public void testTestCaseMatchSubgraph() {
		RuleGraph right = (RuleGraph) buildRightSide();
		RuleGraph forb = (RuleGraph) buildForbiddenSubgraph();
		SubgraphIterator iter = new SubgraphIterator(right);
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(forb);
		while (iter.hasNext()) {
			Set<AnnotatedElem> subgraph = iter.next();
			if (subgraph.size() != 4) continue;
			ipm.reset();			
			ipm.setCurrentSubGraph(subgraph);
			ipm.setPattern(right);
			//System.out.println("new Subgraph=======");
			for (AnnotatedElem elem : subgraph) {
				if (elem instanceof Node) {
					//System.out.print(((Node) elem).getName() + ", ");
				} else {
					//System.out.print(elem + ", ");
				}
			}
			//System.out.println();
			Vector<Match> matches = ipm.findAllMatchings();
			for (Match m : matches) {
				boolean inc = false;
				//System.out.println("new Match----------");
				for (Map.Entry<Node, Node> me : m.getNodeMatching().entrySet()) {
					Node key = me.getKey();
					Node value = me.getValue();
					for (Map.Entry<Node, Node> mec : m.getNodeMatching().entrySet()) {
						if (mec.getValue() == value) {
							if (mec.getKey() != key) {
								inc = true;
							}
						}
					}
				}
				if (inc) {
					System.out.println();
					System.out.println("error: subgraph: ");
					for (AnnotatedElem elem : subgraph) {
						if (elem instanceof Node) {
							System.out.print(((Node) elem).getName() + ", ");
						} else {
							System.out.print(elem + ", ");
						}
					}
					System.out.println();
					System.out.println("Match:");
					for (Map.Entry<Node, Node> me : m.getNodeMatching().entrySet()) {
						System.out.print(me.getKey().getName());
						System.out.print("-");
						System.out.print(me.getValue().getName());
						System.out.print(", ");
					}
				}
			}
		}
	}
	
	private Graph buildRightSide2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node t1 = buildNode("b1", "B", ItemType.PRESERVED, result);
		Node t2 = buildNode("b2", "B", ItemType.PRESERVED, result);
		Node t3 = buildNode("b3", "B", ItemType.PRESERVED, result);
		
		Edge e2 = buildEdge("BB", ItemType.NONE, result);
		e2.setSource(t2); e2.setTarget(t3);
				
		return result;
	}
	
	private Graph buildRightSide() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node s1 = buildNode("a1", "A", ItemType.PRESERVED, result);
		Node s2 = buildNode("a2", "A", ItemType.PRESERVED, result);
		Node t1 = buildNode("b1", "B", ItemType.PRESERVED, result);
		Node t2 = buildNode("b2", "B", ItemType.PRESERVED, result);
		Node t3 = buildNode("b3", "B", ItemType.PRESERVED, result);
		
		Edge e1 = buildEdge("BB", ItemType.NONE, result);
		e1.setSource(t1); e1.setTarget(t2);
		Edge e2 = buildEdge("BB", ItemType.NONE, result);
		e2.setSource(t2); e2.setTarget(t3);
		Edge e3 = buildEdge("AB", ItemType.NONE, result);
		e3.setSource(s1); e3.setTarget(t2);
		Edge e4 = buildEdge("AB", ItemType.NONE, result);
		e4.setSource(s2); e4.setTarget(t2);
		Edge e5 = buildEdge("next_up", ItemType.NONE, result);
		e5.setSource(s2); e5.setTarget(t3);
		
		return result;
	}

	private RuleGraph buildForbiddenSubgraph() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node s1 = buildNode("a1", "A", ItemType.PRESERVED, result);
		Node s2 = buildNode("a2", "A", ItemType.PRESERVED, result);
		Node t1 = buildNode("b1", "B", ItemType.PRESERVED, result);
		Node t2 = buildNode("b2", "B", ItemType.PRESERVED, result);		
		
		Edge e1 = buildEdge("BB", ItemType.NONE, result);
		e1.setSource(t1); e1.setTarget(t2);
		Edge e2 = buildEdge("AB", ItemType.NONE, result);
		e2.setSource(s1); e2.setTarget(t1);
		Edge e3 = buildEdge("AB", ItemType.NONE, result);
		e3.setSource(s2); e3.setTarget(t2);
		Edge e4 = buildEdge("higher_than", ItemType.NONE, result);
		e4.setSource(s1); e4.setTarget(t2);		
		
		return result;
	}
	
	private Graph generateGrid(final int gridSize, boolean directed) {
		final Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		if (gridSize > 0) {
			Node[][] nodes = new Node[gridSize][gridSize];
			for (int i = 0; i < gridSize; i++)
				for (int j = 0; j < gridSize; j++) {
					nodes[i][j] = SamgraphFactory.eINSTANCE.createNode();
					nodes[i][j].setInstanceOf(nodeTypes.get("A"));
					result.getNodes().add(nodes[i][j]);
					if (i > 0) {
						final Edge e = SamgraphFactory.eINSTANCE.createEdge();
						result.getEdges().add(e);
						e.setSource(nodes[i - 1][j]);
						e.setTarget(nodes[i][j]);
						if (directed) {
							e.setInstanceOf(edgeTypes.get("YY"));
						} else {
							e.setInstanceOf(edgeTypes.get("ZZ"));
						}
						
					}
					if (j > 0) {
						final Edge e = SamgraphFactory.eINSTANCE.createEdge();
						result.getEdges().add(e);
						e.setSource(nodes[i][j - 1]);
						e.setTarget(nodes[i][j]);
						if (directed) {
							e.setInstanceOf(edgeTypes.get("YY"));
						} else {
							e.setInstanceOf(edgeTypes.get("ZZ"));
						}
					}
				}
		}
		return result;
	}
	
	private Graph generateSequence(final int length, boolean directed) {
		final Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node lastNode = null;
		Node currentNode = null;
		Edge e = null;
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				currentNode = SamgraphFactory.eINSTANCE.createNode();
				currentNode.setInstanceOf(nodeTypes.get("A"));
				result.getNodes().add(currentNode);
				if (lastNode != null) {
					e = SamgraphFactory.eINSTANCE.createEdge();
					result.getEdges().add(e);					
					e.setSource(lastNode);
					e.setTarget(currentNode);					
					if (directed) {
						e.setInstanceOf(edgeTypes.get("YY"));
					} else {
						e.setInstanceOf(edgeTypes.get("ZZ"));
					}
				}
				lastNode = currentNode;
			}
		}
		return result;
	}

	private Set<AnnotatedElem> graphToSubgraph(final Graph g) {
		if (g != null) {
			final Set<AnnotatedElem> result = new HashSet<AnnotatedElem>();
			for (Iterator<Node> iter = g.getNodes().iterator(); iter.hasNext(); result.add(iter.next()));
			for (Iterator<Edge> iter = g.getEdges().iterator(); iter.hasNext(); result.add(iter.next()));
			for(NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(g).getNacs()){
				result.addAll(nac.getEdges());
				result.addAll(nac.getNodes());
			}
			return result;
		}
		return null;
	}

	private void assertCorrectMatch(Match m) {
		assertNotNull(m);
		for (Map.Entry<Node, Node> e : m.getNodeMatching()) {
			Node key = e.getKey();
			Node value = e.getValue();
			for (Map.Entry<Node, Node> me : m.getNodeMatching()) {
				if (me != e) {
					assertTrue(key != me.getKey());
					assertTrue(value != me.getValue());
				}
			}
		}
		for (Map.Entry<Edge, Edge> e : m.getEdgeMatching()) {
			Edge key = e.getKey();
			Edge value = e.getValue();
			for (Map.Entry<Edge, Edge> me : m.getEdgeMatching()) {
				if (me != e) {
					assertTrue(key != me.getKey());
					assertTrue(value != me.getValue());
				}
			}
		}
		/*if (m instanceof MatchWithNacs) {
			MatchWithNacs match = (MatchWithNacs) m;
			for (Map.Entry<NegativeApplicationCondition, NegativeApplicationCondition> e : match.getNacMatching()) {
				NegativeApplicationCondition key = e.getKey();
				NegativeApplicationCondition value = e.getValue();
				for (Map.Entry<NegativeApplicationCondition, NegativeApplicationCondition> me : match.getNacMatching()) {
					if (me != e) {
						assertTrue(key != me.getKey());
						assertTrue(value != me.getValue());
					}
				}
			}
		}*/
	}
	
	private void assertContainments(Graph sgp) {		
		for (Node n : sgp.getNodes()) {
			assertNotNull(n.eContainer());
			for (Edge out : n.getOutgoing()) {
				assertNotNull(out.eContainer());
				assertNotNull(out.getSource());
				System.out.println(out.getInstanceOf() + " " + out + " " + out.getSource().getInstanceOf());
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
