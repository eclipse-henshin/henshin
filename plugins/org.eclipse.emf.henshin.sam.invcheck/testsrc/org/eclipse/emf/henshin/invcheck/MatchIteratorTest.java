package org.eclipse.emf.henshin.invcheck;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.sam.invcheck.MatchIterator;
import org.eclipse.emf.henshin.sam.invcheck.OptimizedSubgraphIterator;
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
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

public class MatchIteratorTest extends TestCase {
	
	private Graph host;
	private Graph target;
	private Node h1n1;
	private Node h1n2;
	private Node h1n3;	
	private Node h1n4;
	private Node h1n5;
	private Node h1n6;
	private Node h1n7;
	private Node h1n8;
	private Node h1n9;
	private Node h1n10;
	private Node h1n11;
	private Node h1n12;
	private Node h1n13;
	private Node h1n14;
	private Node h1n15;
	private Node h1n16;
	private Node h1n17;
	private Node h1n18;
	private Node h1n19;
	private Node h1n20;
	private Node t1n1;
	private Node t1n2;
	private Node t1n3;
	private Node t1n4;
	private Node t1n5;
	private Node t1n6;
	private Node t1n7;
	private Node t1n8;
	private Node t1n9;
	private Node t1n10;
	private Node t1n11;
	private Node t1n12;
	private Node t1n13;
	private Node t1n14;
	private Node t1n15;
	private Node t1n16;
	private Node t1n17;
	private Node t1n18;
	private Node t1n19;
	private Node t1n20;	
	
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
		return tg;
	}

	
	private void buildHost1() {
		host = SamgraphFactory.eINSTANCE.createGraph();
		h1n1 = buildNode("a1", "A", host);		
		h1n3 = buildNode("a3", "A", host);		
		h1n6 = buildNode("c1", "C", host);
		h1n7 = buildNode("c2", "C", host);
		h1n8 = buildNode("c3", "C", host);
		h1n2 = buildNode("c4", "C", host);
		h1n9 = buildNode("d1", "D", host);
		h1n10 = buildNode("e1", "E", host);		
		h1n12 = buildNode("f1", "F", host);
		h1n13 = buildNode("f2", "F", host);
		h1n11 = buildNode("f3", "F", host);
		h1n11 = buildNode("f4", "F", host);
		h1n11 = buildNode("g1", "G", host);
		h1n11 = buildNode("g2", "G", host);
		h1n11 = buildNode("g3", "G", host);		
	}
	
	private void buildPattern1() {
		target = SamgraphFactory.eINSTANCE.createGraph();
		t1n1 = buildNode("a1", "A", target);
		t1n2 = buildNode("a2", "A", target);
		t1n3 = buildNode("a3", "A", target);
		t1n4 = buildNode("b1", "B", target);
		t1n5 = buildNode("b2", "B", target);
		t1n6 = buildNode("c1", "C", target);
		t1n7 = buildNode("c2", "C", target);
		t1n8 = buildNode("c3", "C", target);
		t1n9 = buildNode("d1", "D", target);
		t1n10 = buildNode("e1", "E", target);
		t1n11 = buildNode("e2", "E", target);
		t1n12 = buildNode("f1", "F", target);
		t1n13 = buildNode("f2", "F", target);
		
	}
	
	private void buildHost2() {
		host = SamgraphFactory.eINSTANCE.createGraph();
		h1n1 = buildNode("a1", "A", host);
		h1n2 = buildNode("a2", "A", host);	
		
	}
	
	private void buildPattern2() {
		target = SamgraphFactory.eINSTANCE.createGraph();
		t1n1 = buildNode("a1", "A", target);
		t1n2 = buildNode("a2", "A", target);
	}
	
	private void buildHost3() {
		host = SamgraphFactory.eINSTANCE.createGraph();
		h1n1 = buildNode("a1", "A", host);
		h1n2 = buildNode("a2", "A", host);
		h1n3 = buildNode("b1", "B", host);
		h1n4 = buildNode("d", "D", host);
		
	}
	
	private void buildPattern3() {
		target = SamgraphFactory.eINSTANCE.createGraph();
		t1n1 = buildNode("a1", "A", target);
		t1n2 = buildNode("b1", "B", target);
		t1n3 = buildNode("b2", "B", target);
		t1n4 = buildNode("c1", "C", target);
	}

	
	private void buildHost4() {
		host = SamgraphFactory.eINSTANCE.createGraph();
		h1n1 = buildNode("a1", "A", host);
		h1n2 = buildNode("a2", "A", host);
		h1n3 = buildNode("b1", "B", host);
		h1n4 = buildNode("d1", "D", host);
		h1n5 = buildNode("d2", "D", host);
		h1n6 = buildNode("d3", "D", host);
		h1n7 = buildNode("e", "E", host);
		
		
	}
	
	private void buildPattern4() {
		target = SamgraphFactory.eINSTANCE.createGraph();
		t1n1 = buildNode("a1", "A", target);
		t1n2 = buildNode("b1", "B", target);
		t1n3 = buildNode("b2", "B", target);
		t1n4 = buildNode("c1", "C", target);
		t1n4 = buildNode("d1", "D", target);
		t1n4 = buildNode("e1", "E", target);
		t1n4 = buildNode("e2", "E", target);
	}
	
	private void buildHost5() {
		host = SamgraphFactory.eINSTANCE.createGraph();
		h1n1 = buildNode("a1", "A", host);		
		h1n3 = buildNode("a3", "A", host);		
		//h1n6 = buildNode("c1", "C", host);
		//h1n7 = buildNode("c2", "C", host);
		//h1n8 = buildNode("c3", "C", host);
		//h1n2 = buildNode("c4", "C", host);
		//h1n9 = buildNode("d1", "D", host);
		h1n10 = buildNode("e1", "E", host);		
		//h1n12 = buildNode("f1", "F", host);
		//h1n13 = buildNode("f2", "F", host);
		//h1n11 = buildNode("f3", "F", host);
		//h1n14 = buildNode("f4", "F", host);
		//h1n11 = buildNode("g1", "G", host);
		//h1n11 = buildNode("g2", "G", host);
		//h1n11 = buildNode("g3", "G", host);		
	}
	
	private void buildPattern5() {
		target = SamgraphFactory.eINSTANCE.createGraph();
		//t1n1 = buildNode("a1", "A", target);
		//t1n2 = buildNode("a2", "A", target);
		//t1n3 = buildNode("a3", "A", target);
		t1n4 = buildNode("b1", "B", target);
		t1n5 = buildNode("b2", "B", target);
		//t1n6 = buildNode("c1", "C", target);
		//t1n7 = buildNode("c2", "C", target);
		//t1n8 = buildNode("c3", "C", target);
		//t1n9 = buildNode("d1", "D", target);
		//t1n10 = buildNode("e1", "E", target);
		//t1n11 = buildNode("e2", "E", target);
		t1n12 = buildNode("f1", "F", target);
		//t1n13 = buildNode("f2", "F", target);
		
	}
	
	private void buildPattern6() {
		target = SamgraphFactory.eINSTANCE.createGraph();
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
		t1n1 = buildNode("a1", "A", target);
	
	}
	
	private void buildHost6() {
		host = SamgraphFactory.eINSTANCE.createGraph();
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
		h1n1 = buildNode("a1", "A", host);
	}
	
	public void testTestIterator1() {
		buildHost1();
		buildPattern1();
		
		for (Node n : host.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		for (Node n : target.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		MatchIterator iter = new MatchIterator(host, target);
		int matches = 0;
		while (iter.hasNext()) {
			Match current = iter.next();
			matches++;
			for (Map.Entry<Node, Node> entry : current.getNodeMatching()) {
				//System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
			}
			//System.out.println();
		}
		System.out.println("matches " + matches);
		
	}
	
	public void testTestIterator2() {
		buildHost2();
		buildPattern2();
		
		for (Node n : host.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		for (Node n : target.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		MatchIterator iter = new MatchIterator(host, target);
		int i = 0;
		while (iter.hasNext()) {
			Match current = iter.next();
			i++;
			for (Map.Entry<Node, Node> entry : current.getNodeMatching()) {
				System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
			}
			System.out.println();
		}
		System.out.println(i);
		
	}
	
	public void testTestIterator3() {
		buildHost3();
		buildPattern3();
		
		for (Node n : host.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		for (Node n : target.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		MatchIterator iter = new MatchIterator(host, target);
		int i = 0;
		while (iter.hasNext()) {
			Match current = iter.next();
			i++;
			for (Map.Entry<Node, Node> entry : current.getNodeMatching()) {
				System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
			}
			System.out.println();
		}
		System.out.println(i);
		
	}
	
	public void testOldIteratorComp() {
		buildHost1();
		buildPattern1();
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();		
		ipm.setHostGraph(target);
		ipm.setPattern(host);
		
		OptimizedSubgraphIterator iter = new OptimizedSubgraphIterator(host, false, target);
		//OptimizedSubgraphIterator iter = new OptimizedSubgraphIterator(host);
		int matches = 0;
		int subgraphs = 0;
		while (iter.hasNext()) {
			subgraphs++;
			Set<AnnotatedElem> current = iter.next();
			ipm.reset();
			ipm.setCurrentSubGraph(current);
			Match currentMatch = ipm.getNextMatching();			
			while (currentMatch != null) {
				matches++;
				currentMatch = ipm.getNextMatching();				
			}
		}
		System.out.println("matches: " + matches);
		System.out.println("subgraphs: " + subgraphs);
		
	}
	
	public void testDebugOldAndNew() {
		buildHost1();
		buildPattern1();
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();		
		ipm.setHostGraph(target);
		ipm.setPattern(host);
		
		OptimizedSubgraphIterator iter = new OptimizedSubgraphIterator(host, false, target);
		//OptimizedSubgraphIterator iter = new OptimizedSubgraphIterator(host);
		
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		
		int matches = 0;
		int subgraphs = 0;	
		while (iter.hasNext()) {
			subgraphs++;
			Set<AnnotatedElem> current = iter.next();
			//iter.skip();
			ipm.reset();
			ipm.setCurrentSubGraph(current);
			Match currentMatch = ipm.getNextMatching();			
			while (currentMatch != null) {
				matches++;
				if (currentMatch.getNodeMatching().size() == 2){
					for (Map.Entry<Node, Node> entry : currentMatch.getNodeMatching()) {
						//System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
					}
					//System.out.println();
				}
				int size = currentMatch.getNodeMatching().size();
				if (counts.get(size) == null) {
					counts.put(size, 1);
				} else {
					counts.put(size, counts.get(size) + 1);
				}
				//
				currentMatch = ipm.getNextMatching();				
			}
		}
		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			//System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		System.out.println("matches, old: " + matches);
		System.out.println("subgraphs, old: " + subgraphs);
		counts.clear();
		
		
		
		
		System.out.println();
		MatchIterator newIter = new MatchIterator(host, target);
		matches = 0;
		while (newIter.hasNext()) {
			Match current = newIter.next();
			matches++;
			if (current.getNodeMatching().size() == 2) {
				for (Map.Entry<Node, Node> entry : current.getNodeMatching()) {
					//System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
				}
				//System.out.println();
			}
			int size = current.getNodeMatching().size();
			if (counts.get(size) == null) {
				counts.put(size, 1);
			} else {
				counts.put(size, counts.get(size) + 1);
			}
		}
		for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
			//System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		System.out.println("matches, new " + matches);
		
	}
	
	public void testCompOldAndNew() {
		
		buildHost6();
		buildPattern6();
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();		
		ipm.setHostGraph(target);
		ipm.setPattern(host);
		
		OptimizedSubgraphIterator iter = new OptimizedSubgraphIterator(host, false, target);
		//OptimizedSubgraphIterator iter = new OptimizedSubgraphIterator(host);
	
		
		int matches = 0;
		int subgraphs = 0;	
		while (iter.hasNext()) {
			subgraphs++;
			Set<AnnotatedElem> current = iter.next();
			//iter.skip();
			ipm.reset();
			ipm.setCurrentSubGraph(current);
			Match currentMatch = ipm.getNextMatching();			
			while (currentMatch != null) {
				matches++;				
				currentMatch = ipm.getNextMatching();				
			}
		}
		
		System.out.println("matches, old: " + matches);
		System.out.println("subgraphs, old: " + subgraphs);
	
		
		
		
		System.out.println();
		MatchIterator newIter = new MatchIterator(host, target);
		matches = 0;
		while (newIter.hasNext()) {
			Match current = newIter.next();
			matches++;			
		}
		System.out.println("matches, new " + matches);
		
	}
	
	public void testTestIterator4() {
		buildHost5();
		buildPattern5();
		
		for (Node n : host.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		for (Node n : target.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		MatchIterator iter = new MatchIterator(host, target);
		int matches = 0;
		while (iter.hasNext()) {
			Match current = iter.next();
			matches++;
			for (Map.Entry<Node, Node> entry : current.getNodeMatching()) {
				//System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
			}
			//System.out.println();
		}
		System.out.println("matches " + matches);
		
	}
	
	public void testTestIteratorMatch() {
		buildHost1();
		buildPattern1();
		
		for (Node n : host.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		for (Node n : target.getNodes()) {
			System.out.print(n.getName() + ":" + n.getInstanceOf().getName() + ", ");
		}
		System.out.println();
		Set<Node> nodes = new HashSet<Node>();
		nodes.add(t1n1);
		nodes.add(t1n2);
		nodes.add(t1n3);
		nodes.add(t1n4);
		nodes.add(t1n5);
		nodes.add(t1n6);
		nodes.add(t1n7);
		nodes.add(t1n8);
		nodes.add(t1n9);
		nodes.add(t1n10);
		nodes.add(t1n11);
		MatchIterator iter = new MatchIterator(host, target, null, nodes);
		int matches = 0;
		while (iter.hasNext()) {
			Match current = iter.next();
			matches++;
			for (Map.Entry<Node, Node> entry : current.getNodeMatching()) {
				//System.out.println(entry.getKey().getName() + ":" + entry.getKey().getInstanceOf().getName() + " - " + entry.getValue().getName() + ":"  + entry.getValue().getInstanceOf().getName());				
			}
			//System.out.println();
		}
		System.out.println("matches " + matches);
		
	}
	
}
