package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.GraphMerger;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
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



public class GraphMergerTest extends TestCase {
	
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
	
	private Graph buildHostGraph() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		final Node n1 = buildNode("a", "A", hostGraph);		
		final Node n2 = buildNode("b", "B", hostGraph);		
		final Node n3 = buildNode("c", "C", hostGraph);
		final Node n4 = buildNode("d", "D", hostGraph);
		final Node n5 = buildNode("e1", "E", hostGraph);		
		final Node n6 = buildNode("e2", "E", hostGraph);
		
		final Edge e1 = buildEdge("AB", hostGraph);		
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", hostGraph);		
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e3 = buildEdge("CD", hostGraph);		
		e3.setSource(n3); e3.setTarget(n4);
		final Edge e4 = buildEdge("BE", hostGraph);		
		e4.setSource(n2); e4.setTarget(n5);
		final Edge e5 = buildEdge("BE", hostGraph);		
		e5.setSource(n2); e5.setTarget(n6);
		return hostGraph;
	}
	
	private Graph buildPattern() {
		final Graph hostGraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		final Node n1 = buildNode("a", "A", hostGraph);		
		final Node n2 = buildNode("b", "B", hostGraph);		
		final Node n3 = buildNode("c", "C", hostGraph);		
		final Node n5 = buildNode("e", "E", hostGraph); 
		
		final Edge e1 = buildEdge("AB", hostGraph);		
		e1.setSource(n1); e1.setTarget(n2);
		final Edge e2 = buildEdge("BC", hostGraph);		
		e2.setSource(n2); e2.setTarget(n3);
		final Edge e4 = buildEdge("BE", hostGraph);		
		e4.setSource(n2); e4.setTarget(n5);
		return hostGraph;

	}
	
	public void testTestCase1() {
		for (int i = 0; i < 100; i++) {
		this.rhs = generateRandomGraph();		
		this.matching = generatePGraphFromSubGraph(this.rhs);
		for (Node n : matching.getNodeMatching().values()) {
			this.forb = (Graph) n.eContainer();
			break;
		}
		assertContainments(rhs);
		//Diagnostic diag = Diagnostician.INSTANCE.validate(rhs);
		/*if (diag.getSeverity() == Diagnostic.ERROR) {
			assertTrue(false);
		}*/
		
		
		GraphMerger merger = new GraphMerger();
		Graph result = merger.merge(forb, rhs, matching);
		System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
		System.out.println(forb.getNodes().size() + " " + forb.getNodes());		
		System.out.println(result.getNodes().size() + " " + result.getNodes());
		System.out.println("matching: " + matching.getNodeMatching().size());
		for (Map.Entry<Node, Node> e : matching.getNodeMatching().entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		for (Map.Entry<Edge, Edge> e : matching.getEdgeMatching().entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		checkItemCount(rhs, forb, matching, result);
		checkMatching(matching);
		checkTypeCount(rhs, forb, matching, result);
		assertContainments(rhs);
		assertContainments(forb);
		assertContainments(result);
		assertTrue(((RuleGraph) result).getCondition() == null);
		checkRefItems(rhs, forb, matching, result);
		}
	}

	
	public void testTestCase2() {
		Graph rhs = buildPattern();
		Graph pattern = buildHostGraph();
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(pattern);
		ipm.setPattern(rhs);
		ipm.setCurrentSubGraph(graphToSubgraph(rhs));
		Match match = ipm.getNextMatching();
		assertNotNull(match);
		
		this.rhs = rhs;
		this.forb = pattern;
		this.matching = match;
		
		assertContainments(rhs);
		assertContainments(forb);
		
		GraphMerger merger = new GraphMerger();
		Graph result = merger.merge(forb, this.rhs, matching);
		
		System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
		System.out.println(forb.getNodes().size() + " " + forb.getNodes());
		System.out.println(matching.getNodeMatching().size());
		System.out.println(result.getNodes().size() + " " + result.getNodes());
		checkItemCount(rhs, forb, matching, result);
		checkMatching(matching);
		checkTypeCount(rhs, forb, matching, result);
		assertContainments(rhs);
		assertContainments(forb);
		assertContainments(result);
		assertTrue(((RuleGraph)result).getCondition() == null);
		checkRefItems(rhs, forb, matching, result);
	}
	
	public void testTestCase3() {
		for (int i = 0; i < 100; i++) {
		Graph part1 = generateRandomGraph();
		this.matching = generatePGraphFromSubGraph(part1);
		for (Node n : matching.getNodeMatching().values()) {
			this.forb = (Graph) n.eContainer();
			break;
		}
		
		Graph part2 = generateRandomGraph();
		Match add = generatePGraphFromSubGraph(part2);
		Graph tmp = null;
		for (Node n : add.getNodeMatching().values()) {
			tmp = (Graph) n.eContainer();
			break;
		}
		part1.getNodes().addAll(part2.getNodes());
		part1.getEdges().addAll(part2.getEdges());
		this.forb.getNodes().addAll(tmp.getNodes());
		this.forb.getEdges().addAll(tmp.getEdges());
		this.matching.getNodeMatching().addAll(add.getNodeMatching());
		this.matching.getEdgeMatching().addAll(add.getEdgeMatching());
		this.rhs = part1;
		
		GraphMerger merger = new GraphMerger();
		Graph result = merger.merge(forb, rhs, matching);
		System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
		System.out.println(forb.getNodes().size() + " " + forb.getNodes());		
		System.out.println(result.getNodes().size() + " " + result.getNodes());
		System.out.println("matching: " + matching.getNodeMatching().size());
		for (Map.Entry<Node, Node> e : matching.getNodeMatching().entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		for (Map.Entry<Edge, Edge> e : matching.getEdgeMatching().entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		checkItemCount(rhs, forb, matching, result);
		checkMatching(matching);
		checkTypeCount(rhs, forb, matching, result);
		assertContainments(rhs);
		assertContainments(forb);
		assertContainments(result);
		assertTrue(((RuleGraph)result).getCondition() == null);
		checkRefItems(rhs, forb, matching, result);
		}
	}
	
	public void testTestCase4() {
		for (int i = 0; i < 100; i++) {
		this.rhs = generateRandomGraph();
		this.matching = generatePGraphFromSubGraph(rhs);
		for (Node n : matching.getNodeMatching().values()) {
			this.forb = (Graph) n.eContainer();
			break;
		}
		
		for (Edge e : this.rhs.getEdges()) {
			e.setSource(null);
			e.setTarget(null);
		}
		this.rhs.getEdges().clear();
		
		for (Edge e : this.forb.getEdges()) {
			e.setSource(null);
			e.setTarget(null);
		}
		this.forb.getEdges().clear();
		this.matching.getEdgeMatching().clear();
		
		GraphMerger merger = new GraphMerger();
		Graph result = merger.merge(forb, rhs, matching);
		System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
		System.out.println(forb.getNodes().size() + " " + forb.getNodes());		
		System.out.println(result.getNodes().size() + " " + result.getNodes());
		System.out.println("matching: " + matching.getNodeMatching().size());
		for (Map.Entry<Node, Node> e : matching.getNodeMatching().entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		for (Map.Entry<Edge, Edge> e : matching.getEdgeMatching().entrySet()) {
			System.out.println(e.getKey() + " " + e.getValue());
		}
		checkItemCount(rhs, forb, matching, result);
		checkMatching(matching);
		checkTypeCount(rhs, forb, matching, result);
		assertContainments(rhs);
		assertContainments(forb);
		assertContainments(result);
		assertTrue(((RuleGraph)result).getCondition() == null);
		checkRefItems(rhs, forb, matching, result);
		}
	}
	
	
	public void testTestCase5() {
		for (int i = 0; i < 100; i++) {
			Graph part1 = generateRandomGraph();
			this.matching = generatePGraphFromSubGraph(part1);
			for (Node n : matching.getNodeMatching().values()) {
				this.forb = (Graph) n.eContainer();
				break;
			}
			
			Graph part2 = generateRandomGraph();
			Match add = generatePGraphFromSubGraph(part2);
			Graph tmp = null;
			for (Node n : add.getNodeMatching().values()) {
				tmp = (Graph) n.eContainer();
				break;
			}
			part1.getNodes().addAll(part2.getNodes());
			part1.getEdges().addAll(part2.getEdges());
			
			/*
			Edge connectingEdge = buildEdge("CCC", this.forb);
			connectingEdge.setSource(this.forb.getNodes().get(0));
			connectingEdge.setTarget(tmp.getNodes().get(0));
			*/
			
			String et = this.forb.getNodes().get(0).getInstanceOf().getName() + tmp.getNodes().get(0).getInstanceOf().getName();
			Edge connectingEdge = buildEdge(et, this.forb);
			connectingEdge.setSource(this.forb.getNodes().get(0));
			connectingEdge.setTarget(tmp.getNodes().get(0));
			
			this.forb.getEdges().add(connectingEdge);
			this.forb.getNodes().addAll(tmp.getNodes());
			this.forb.getEdges().addAll(tmp.getEdges());
			this.matching.getNodeMatching().addAll(add.getNodeMatching());
			this.matching.getEdgeMatching().addAll(add.getEdgeMatching());
			this.rhs = part1;
			
			GraphMerger merger = new GraphMerger();
			Graph result = merger.merge(forb, rhs, matching);
			System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
			System.out.println(forb.getNodes().size() + " " + forb.getNodes());		
			System.out.println(result.getNodes().size() + " " + result.getNodes());
			System.out.println("matching: " + matching.getNodeMatching().size());
			for (Map.Entry<Node, Node> e : matching.getNodeMatching().entrySet()) {
				System.out.println(e.getKey() + " " + e.getValue());
			}
			for (Map.Entry<Edge, Edge> e : matching.getEdgeMatching().entrySet()) {
				System.out.println(e.getKey() + " " + e.getValue());
			}
			checkItemCount(rhs, forb, matching, result);
			checkMatching(matching);
			checkTypeCount(rhs, forb, matching, result);
			assertContainments(rhs);
			assertContainments(forb);
			assertContainments(result);
			assertTrue(((RuleGraph)result).getCondition() == null);
			checkRefItems(rhs, forb, matching, result);
			}
	}
	
	public void testTestCase6() {
		for (int i = 0; i < 100; i++) {
			Graph part1 = generateRandomGraph();
			this.matching = generatePGraphFromSubGraph(part1);
			for (Node n : matching.getNodeMatching().values()) {
				this.forb = (Graph) n.eContainer();
				break;
			}
			
			Graph part2 = generateRandomGraph();
			Match add = generatePGraphFromSubGraph(part2);
			Graph tmp = null;
			for (Node n : add.getNodeMatching().values()) {
				tmp = (Graph) n.eContainer();
				break;
			}
			
			
			NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
			Node nn1 = buildNode("nn1", "A", nac);			
			Node nn2 = buildNode("nn2", "B", nac);			
			Node nn3 = buildNode("nn3", "C", nac);
						
			Edge ne1 = buildEdge("AB", nac);
			ne1.setSource(nn1);
			ne1.setTarget(nn2);			
			Edge ne2 = buildEdge("AC", nac);
			ne2.setSource(nn1);
			ne2.setTarget(nn3);
			
			String edgeType = nn1.getInstanceOf().getName() + part1.getNodes().get(0).getInstanceOf().getName();			
			Edge ne3 = buildEdge(edgeType, nac);
			ne3.setSource(nn1);
			ne3.setTarget(part1.getNodes().get(0));
			
			edgeType = nn3.getInstanceOf().getName() + part2.getNodes().get(0).getInstanceOf().getName();
			Edge ne4 = buildEdge(edgeType, nac);
			ne4.setSource(nn3);
			ne4.setTarget(part2.getNodes().get(0));			
			GraphCondition cond = InvariantCheckerUtil.createNegatedCondition(nac);
			((RuleGraph) part1).setCondition(cond);
			
			part1.getNodes().addAll(part2.getNodes());
			part1.getEdges().addAll(part2.getEdges());
									
			this.forb.getNodes().addAll(tmp.getNodes());
			this.forb.getEdges().addAll(tmp.getEdges());
			this.matching.getNodeMatching().addAll(add.getNodeMatching());
			this.matching.getEdgeMatching().addAll(add.getEdgeMatching());
			this.rhs = part1;
			
			GraphMerger merger = new GraphMerger();
			Graph result = merger.merge(forb, rhs, matching);
			System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
			System.out.println(forb.getNodes().size() + " " + forb.getNodes());		
			System.out.println(result.getNodes().size() + " " + result.getNodes());
			System.out.println("matching: " + matching.getNodeMatching().size());
			for (Map.Entry<Node, Node> e : matching.getNodeMatching().entrySet()) {
				System.out.println(e.getKey() + " " + e.getValue());
			}
			for (Map.Entry<Edge, Edge> e : matching.getEdgeMatching().entrySet()) {
				System.out.println(e.getKey() + " " + e.getValue());
			}
			checkItemCount(rhs, forb, matching, result);
			checkMatching(matching);
			checkTypeCount(rhs, forb, matching, result);
			assertContainments(rhs);
			assertContainments(forb);
			assertContainments(result);
			assertTrue(((RuleGraph)result).getCondition() == null);
			checkRefItems(rhs, forb, matching, result);
			}
	}
	
	/*
	public void testTestCase3() {
		Graph rhs = buildHostGraph3();
		Graph pattern = buildPattern3();
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(rhs);
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(graphToSubgraph(pattern));
		Match match = ipm.getNextMatching();
		assertNotNull(match);
		
		this.rhs = rhs;
		this.forb = pattern;
		this.matching = match;
		
		assertContainments(rhs);
		assertContainments(forb);
		
		GraphMerger merger = new GraphMerger();
		Graph result = merger.merge(forb, this.rhs, matching);
		
		System.out.println(rhs.getNodes().size() + " " + rhs.getNodes());
		System.out.println(forb.getNodes().size() + " " + forb.getNodes());
		System.out.println(matching.getNodeMatching().size());
		System.out.println(result.getNodes().size() + " " + result.getNodes());
		checkItemCount(rhs, forb, matching, result);
		checkMatching(matching);
		checkTypeCount(rhs, forb, matching, result);
		assertContainments(rhs);
		assertContainments(forb);
		assertContainments(result);
	}*/
	
	
	private Match generateRandomMatching() {
		Match result = SamtraceFactory.eINSTANCE.createMatch();
		Random r = new Random();
		int maxNodeCount = (rhs.getNodes().size() > forb.getNodes().size()) ? forb.getNodes().size() : rhs.getNodes().size();
		int maxEdgeCount = (rhs.getEdges().size() > forb.getEdges().size()) ? forb.getEdges().size() : rhs.getEdges().size();
		int nodeCount = r.nextInt(maxNodeCount);
		int edgeCount = r.nextInt(maxEdgeCount);
		for (int i = 1; i <= nodeCount; i++) {
			boolean found = false;
			Node source = null;
			Node target = null;
			while(!found) {
				Random ra = new Random();
				int sourceId = ra.nextInt(rhs.getNodes().size());
				source = rhs.getNodes().get(sourceId);
				if (!result.getNodeMatching().containsKey(source)){
					found = true;
				}
			}
			found = false;
			while(!found) {
				Random ra = new Random();
				int targetId = ra.nextInt(forb.getNodes().size());
				target = forb.getNodes().get(targetId);
				if (!result.getNodeMatching().containsValue(target)){
					found = true;
				}
			}
			result.getNodeMatching().put(source, target);			
		}
		for (int i = 1; i <= edgeCount; i++) {
			boolean found = false;
			Edge source = null;
			Edge target = null;
			while(!found) {
				Random ra = new Random();
				int sourceId = ra.nextInt(rhs.getEdges().size());
				source = rhs.getEdges().get(sourceId);
				if (!result.getEdgeMatching().containsKey(source)){
					found = true;
				}
			}
			found = false;
			while(!found) {
				Random ra = new Random();
				int targetId = ra.nextInt(forb.getEdges().size());
				target = forb.getEdges().get(targetId);
				if (!result.getEdgeMatching().containsValue(target)){
					found = true;
				}
			}
			result.getEdgeMatching().put(source, target);
		}
		return result;
	}
	
	private Graph generateRandomGraph() {
		Random r = new Random();
		Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		int nodeCount = r.nextInt(maxItems) + 2;
		int edgeCount = r.nextInt(maxItems) + 2;
		for (int i = 1; i <= nodeCount; i++) {
			Random t = new Random();
			int ind = t.nextInt(types.length);
			Node n = buildNode(new Integer(i).toString(), types[ind], result);
		}
		for (int i = 1; i <= edgeCount; i++) {
			Random ra = new Random();
			int sourceId = ra.nextInt(nodeCount - 1);
			int targetId = ra.nextInt(nodeCount - 1);
			String edgeType = result.getNodes().get(sourceId).getInstanceOf().getName() + result.getNodes().get(targetId).getInstanceOf().getName();
			Edge e = buildEdge(edgeType, result);
			e.setSource(result.getNodes().get(sourceId));
			e.setTarget(result.getNodes().get(targetId));
		}
		return result;
	}

	private Match generatePGraphFromSubGraph(Graph src) {
		Graph g = SamgraphFactory.eINSTANCE.createGraph();
		Match result = SamtraceFactory.eINSTANCE.createMatch();
		Random r = new Random();
		int initEdgeCount = r.nextInt(src.getEdges().size() + 1);
		if (initEdgeCount == 0) {
			initEdgeCount = 1;
		}
		for (int i = 1; i <= initEdgeCount; i++) {			
			boolean found = false;
			Edge srcEdge = null;
			while(!found) {
				Random ra = new Random();
				int id = ra.nextInt(src.getEdges().size());
				srcEdge = src.getEdges().get(id);
				if (!result.getEdgeMatching().containsKey(srcEdge)){
					found = true;
				}
			}
			Node source = srcEdge.getSource();
			Node target = srcEdge.getTarget();
			Edge newEdge = (Edge) srcEdge.copy();
			
			g.getEdges().add(newEdge);
			result.getEdgeMatching().put(srcEdge, newEdge);
			
			if (result.getNodeMatching().containsKey(source)) {
				newEdge.setSource(result.getNodeMatching().get(source));
			} else {
				Node newSource = (Node) source.copy();
				newEdge.setSource(newSource);
				g.getNodes().add(newSource);
				result.getNodeMatching().put(source, newSource);
			}
			
			if (result.getEdgeMatching().containsKey(target)) {
				newEdge.setTarget(result.getNodeMatching().get(target));
			} else {
				Node newTarget = (Node) target.copy();
				newEdge.setTarget(newTarget);
				g.getNodes().add(newTarget);
				result.getNodeMatching().put(target, newTarget);
			}
		}
		
		int nodeCount = r.nextInt(addItems) + 2;
		int edgeCount = r.nextInt(addItems) + 2;
		for (int i = 1; i <= nodeCount; i++) {
			Random t = new Random();
			int ind = t.nextInt(types.length);
			Node n = buildNode(new Integer(i).toString(), types[ind], g);			
		}
		for (int i = 1; i <= edgeCount; i++) {
			Random ra = new Random();
			int sourceId = ra.nextInt(g.getNodes().size());
			int targetId = ra.nextInt(g.getNodes().size());
			String edgeType = g.getNodes().get(sourceId).getInstanceOf().getName() + g.getNodes().get(targetId).getInstanceOf().getName(); 
			Edge e = buildEdge(edgeType, g);			
			
			e.setSource(g.getNodes().get(sourceId));
			e.setTarget(g.getNodes().get(targetId));			
		}
		
		return result;
	}
	
	// obsolete
	/*
	private Graph generateRandomPGraph() {
		Random r = new Random();
		Graph result = SamgraphFactory.eINSTANCE.createGraph();
		int nodeCount = r.nextInt(maxItems) + 2;
		int edgeCount = r.nextInt(maxItems) + 2;
		for (int i = 1; i <= nodeCount; i++) {
			Node n = buildNode(new Integer(i).toString(), "A", result);			
			n.setType("A");			
		}
		for (int i = 1; i <= edgeCount; i++) {
			Edge e = buildEdge("AA", result);
			e.setType("AA");			
			Random ra = new Random();
			int sourceId = ra.nextInt(nodeCount - 1);
			int targetId = ra.nextInt(nodeCount - 1);
			e.setSource(result.getNodes().get(sourceId));
			e.setTarget(result.getNodes().get(targetId));						
		}
		return result;
	}*/
	
	private void checkItemCount(Graph rhs, Graph forb, Match match, Graph result) {
		assertEquals(rhs.getNodes().size() + forb.getNodes().size() - match.getNodeMatching().size(), result.getNodes().size());
		assertEquals(rhs.getEdges().size() + forb.getEdges().size() - match.getEdgeMatching().size(), result.getEdges().size());
	}
	
	private void checkRefItems(Graph rhs, Graph forb, Match match, Graph result) {
		for (Node n_t : result.getNodes()) {
			PatternNode n = (PatternNode) n_t;
			boolean prop = false;
			boolean rule = false;
			if (n.getSameInProp() != null) {
				assertEquals(n.getInstanceOf(), ((Node) n.getSameInProp()).getInstanceOf());
				prop = true;
			}
			if (n.getSameInRule() != null) {
				assertEquals(n.getInstanceOf(), ((Node) n.getSameInRule()).getInstanceOf());
				rule = true;
			}
			assertTrue(prop || rule);
			if (prop && rule) {
				assertTrue(match.getNodeMatching().containsKey((Node) n.getSameInRule()));
				assertTrue(match.getNodeMatching().containsValue((Node) n.getSameInProp()));
				assertEquals(match.getNodeMatching().get((Node) n.getSameInRule()),(Node) n.getSameInProp());
			}
			if (rule && !prop) {
				assertTrue(!match.getNodeMatching().containsKey((Node) n.getSameInRule()));
			}
			if (!rule && prop) {
				assertTrue(!match.getNodeMatching().containsValue((Node) n.getSameInProp()));
			}
		}
	}
	
	private EMap<NodeType, Integer> getTypeCount(Graph g) {
		EMap<NodeType, Integer> result = new BasicEMap<NodeType, Integer>();
		for (Node n : g.getNodes()) {
			if (result.get(n.getInstanceOf()) != null) {
				result.put(n.getInstanceOf(), result.get(n.getInstanceOf()) + 1);
			} else {
				result.put(n.getInstanceOf(), 1);
			}
		}
	
		return result;
	}
	
	private EMap<EdgeType, Integer> getEdgeTypeCount(Graph g) {
		EMap<EdgeType, Integer> result = new BasicEMap<EdgeType, Integer>();
		for (Edge e : g.getEdges()) {
			if (result.get(e.getInstanceOf()) != null) {
				result.put(e.getInstanceOf(), result.get(e.getInstanceOf()) + 1);
			} else {
				result.put(e.getInstanceOf(), 1);
			}
		}
	
		return result;
	}
	
	private void checkEdgeTypeCount(Graph rhs, Graph forb, Match match, Graph result) {
		EMap<EdgeType, Integer> rhsTypes = getEdgeTypeCount(rhs);
		EMap<EdgeType, Integer> forbTypes = getEdgeTypeCount(forb);
		EMap<EdgeType, Integer> resultTypes = getEdgeTypeCount(result);
		EMap<EdgeType, Integer> matchTypes = new BasicEMap<EdgeType, Integer>();
		
		for (Edge e : match.getEdgeMatching().keySet()) {
			if (matchTypes.get(e.getInstanceOf()) != null) {
				matchTypes.put(e.getInstanceOf(), matchTypes.get(e.getInstanceOf()) + 1);
			} else {
				matchTypes.put(e.getInstanceOf(), 1);
			}
		}
		
		Set<EdgeType> types = new HashSet<EdgeType>();		
		types.addAll(rhsTypes.keySet());
		types.addAll(forbTypes.keySet());
		types.addAll(resultTypes.keySet());
		
		for (EdgeType s : types) {
			Integer rhsCount = rhsTypes.get(s) == null ? 0 : rhsTypes.get(s);
			Integer forbCount = forbTypes.get(s) == null ? 0 : forbTypes.get(s);
			Integer matchCount = matchTypes.get(s) == null ? 0 : matchTypes.get(s);;
			Integer resultCount = resultTypes.get(s) == null ? 0 : resultTypes.get(s);;
			assertEquals(rhsCount.intValue() + forbCount.intValue() - matchCount.intValue(), resultCount.intValue());
		}
	}
	
	private void checkTypeCount(Graph rhs, Graph forb, Match match, Graph result) {
		checkEdgeTypeCount(rhs, forb, match, result);
		EMap<NodeType, Integer> rhsTypes = getTypeCount(rhs);
		EMap<NodeType, Integer> forbTypes = getTypeCount(forb);
		EMap<NodeType, Integer> resultTypes = getTypeCount(result);
		EMap<NodeType, Integer> matchTypes = new BasicEMap<NodeType, Integer>();
		
		for (Node n : match.getNodeMatching().keySet()) {
			if (matchTypes.get(n.getInstanceOf()) != null) {
				matchTypes.put(n.getInstanceOf(), matchTypes.get(n.getInstanceOf()) + 1);
			} else {
				matchTypes.put(n.getInstanceOf(), 1);
			}
		}
		
		Set<NodeType> types = new HashSet<NodeType>();		
		types.addAll(rhsTypes.keySet());
		types.addAll(forbTypes.keySet());
		types.addAll(resultTypes.keySet());
		
		for (NodeType s : types) {
			Integer rhsCount = rhsTypes.get(s) == null ? 0 : rhsTypes.get(s);
			Integer forbCount = forbTypes.get(s) == null ? 0 : forbTypes.get(s);
			Integer matchCount = matchTypes.get(s) == null ? 0 : matchTypes.get(s);;
			Integer resultCount = resultTypes.get(s) == null ? 0 : resultTypes.get(s);;
			assertEquals(rhsCount.intValue() + forbCount.intValue() - matchCount.intValue(), resultCount.intValue());
		}
	}
	
	private void checkMatching(Match match) {
		for (Map.Entry<Node, Node> e : match.getNodeMatching()) {
			assertEquals(e.getKey().getInstanceOf(), e.getValue().getInstanceOf());
		}
		for (Map.Entry<Edge, Edge> e : match.getEdgeMatching()) {
			assertEquals(e.getKey().getInstanceOf(), e.getValue().getInstanceOf());
		}		
	}
	
	// Diagnostic diag = Diagnostician.INSTANCE.validate(vt);
	
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
