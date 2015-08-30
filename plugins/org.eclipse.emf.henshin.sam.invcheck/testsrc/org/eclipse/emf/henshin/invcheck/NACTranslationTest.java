package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
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

public class NACTranslationTest extends TestCase {

	private Graph	pattern1;
	private NegativeApplicationCondition	pattern1Nac;
	private Node	pattern1N1;
	private Node	pattern1N2;
	private Node	pattern1N3;
	private Edge	pattern1E1;
	private Edge	pattern1E2;
	private Graph	host1;
	private Node	host1N1;
	private Node	host1N2;
	private Edge	host1E;
	private Graph	mergedGraph1;
	private Graph	resultGraph1;
	private Graph	host2;
	private Node	host2N1;
	private Node	host2N2;
	private Edge	host2E;
	private Node	host2N3;
	private Edge	host2E2;
	private Node	host2N4;
	private Edge	host2E3;
	private Graph	pattern2;
	private NegativeApplicationCondition	pattern2Nac;
	private Node	pattern2N1;
	private Node	pattern2N2;
	private Node	pattern2N3;
	private Edge	pattern2E1;
	private Edge	pattern2E2;
	private Node	pattern2N4;
	private Edge	pattern2E3;
	private Graph	mergedGraph2;
	private Graph	resultGraph2;
	private Graph	host3;
	private Node	host3N1;
	private Node	host3N2;
	private Edge	host3E;
	private Node	host3N3;
	private Edge	host3E2;
	private Graph	pattern3;
	private NegativeApplicationCondition	pattern3Nac;
	private Node	pattern3N1;
	private Node	pattern3N2;
	private Node	pattern3N3;
	private Edge	pattern3E1;
	private Edge	pattern3E2;
	private Node	pattern3N4;
	private Edge	pattern3E3;
	private Graph	mergedGraph3;
	private Graph	host4;
	private Node	host4N1;
	private Node	host4N2;
	private Edge	host4E;
	private Node	host4N3;
	private Edge	host4E2;
	private Graph	pattern4;
	private NegativeApplicationCondition	pattern4Nac;
	private Node	pattern4N1;
	private Node	pattern4N2;
	private Node	pattern4N3;
	private Node	pattern4N4;
	private Node	pattern4N5;
	private Edge	pattern4E1;
	private Edge	pattern4E2;
	private Edge	pattern4E3;
	private Edge	pattern4E4;
	private Edge	pattern4E5;
	private Graph	mergedGraph4;
	private Graph	resultGraph4;
	private Graph	host5;
	private Node	host5N1;
	private Node	host5N2;
	private Edge	host5E;
	private Node	host5N3;
	private Edge	host5E2;
	private Node	host5N4;
	private Edge	host5E3;
	private Graph	pattern5;
	private NegativeApplicationCondition	pattern5Nac;
	private Node	pattern5N1;
	private Node	pattern5N2;
	private Node	pattern5N3;
	private Node	pattern5N4;
	private Node	pattern5N5;
	private Edge	pattern5E1;
	private Edge	pattern5E2;
	private Edge	pattern5E3;
	private Edge	pattern5E4;
	private Edge	pattern5E5;
	private Graph	mergedGraph5;
	private Graph	resultGraph5;
	private Graph	host6;
	private Node	host6N1;
	private Node	host6N2;
	private Edge	host6E;
	private Node	host6N3;
	private Edge	host6E2;
	private Node	host6N4;
	private Node	host6N5;
	private Node	host6N6;
	private Edge	host6E3;
	private Edge	host6E4;
	private Edge	host6E5;
	private Graph	pattern6;
	private NegativeApplicationCondition	pattern6Nac;
	private Node	pattern6N1;
	private Node	pattern6N2;
	private Node	pattern6N3;
	private Node	pattern6N4;
	private Node	pattern6N5;
	private Node	pattern6N6;
	private Edge	pattern6E1;
	private Edge	pattern6E2;
	private Edge	pattern6E3;
	private Edge	pattern6E4;
	private Edge	pattern6E5;
	private Edge	pattern6E6;
	private Graph	mergedGraph6;
	private Graph	resultGraph6;
	private Graph	host7;
	private Node	host7N1;
	private Node	host7N2;
	private Edge	host7E1;
	private Node	host7N3;
	private Edge	host7E2;
	private Node	host7N4;
	private Node	host7N5;
	private Node	host7N6;
	private Edge	host7E3;
	private Edge	host7E4;
	private Edge	host7E5;
	private Edge	host7E6;
	private Node	host7N7;
	private Graph	pattern7;
	private NegativeApplicationCondition	pattern7Nac;
	private Node	pattern7N1;
	private Node	pattern7N2;
	private Node	pattern7N3;
	private Node	pattern7N4;
	private Node	pattern7N5;
	private Node	pattern7N6;
	private Edge	pattern7E1;
	private Edge	pattern7E2;
	private Edge	pattern7E3;
	private Edge	pattern7E4;
	private Edge	pattern7E5;
	private Edge	pattern7E6;
	private Graph	mergedGraph7;
	private Node	merged7N1;
	private Node	merged7N2;
	private Edge	merged7E1;
	private Node	merged7N3;
	private Edge	merged7E2;
	private Node	merged7N4;
	private Node	merged7N5;
	private Node	merged7N6;
	private Edge	merged7E3;
	private Edge	merged7E4;
	private Edge	merged7E5;
	private Edge	merged7E6;
	private Node	merged7N7;
	private Graph	resultGraph7;
	private Node	result7N1;
	private Node	result7N2;
	private Edge	result7E1;
	private Node	result7N3;
	private Edge	result7E2;
	private Node	result7N4;
	private Node	result7N5;
	private Node	result7N6;
	private Edge	result7E3;
	private Edge	result7E4;
	private Edge	result7E5;
	private Edge	result7E6;
	private Node	result7N7;	
	private Graph	host8;
	private Node	host8N1;
	private Node	host8N2;
	private Node	host8N3;
	private Node	host8N4;
	private Edge	host8E1;	
	private Edge	host8E2;
	private Edge	host8E3;
	private Graph	pattern8;
	private NegativeApplicationCondition	pattern8Nac;
	private Node	pattern8N1;
	private Node	pattern8N2;
	private Node	pattern8N3;
	private Node	pattern8N4;
	private Node	pattern8N5;
	private Edge	pattern8E1;
	private Edge	pattern8E2;
	private Edge	pattern8E3;
	private Edge	pattern8E4;
	private Edge	pattern8E5;	
	private Graph	mergedGraph8;
	private Node	merged8N1;
	private Node	merged8N2;
	private Node	merged8N3;
	private Node	merged8N4;
	private Edge	merged8E1;	
	private Edge	merged8E2;		
	private Edge	merged8E3;
	private Graph	resultGraph8;
	private Graph	sPattern7;
	private NegativeApplicationCondition	sPattern7Nac;
	private Node	sPattern7N1;
	private Node	sPattern7N2;
	private Node	sPattern7N3;
	private Node	sPattern7N4;
	private Node	sPattern7N5;
	private Node	sPattern7N6;
	private Edge	sPattern7E1;
	private Edge	sPattern7E2;
	private Edge	sPattern7E3;
	private Edge	sPattern7E4;
	private Edge	sPattern7E5;	
	private Graph	sMergedGraph7;
	private Node	sMerged7N1;
	private Node	sMerged7N2;
	private Node	sMerged7N3;
	private Node	sMerged7N4;
	private Node	sMerged7N5;
	private Edge	sMerged7E1;	
	private Edge	sMerged7E2;		
	private Edge	sMerged7E3;
	private Edge	sMerged7E4;
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
		host1 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host1N1 = buildNode("a1", "A", host1);
		host1N2 = buildNode("b1", "B", host1);
		host1E = buildEdge("AB", host1);
		host1E.setSource(host1N1); host1E.setTarget(host1N2);
		
	}
	
	private void buildPattern1() {
		pattern1 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern1Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern1Nac.setName("nac1"); pattern1Nac.setGraph(pattern1);
		pattern1N1 = buildNode("a2", "A", pattern1);
		pattern1N2 = buildNode("b2", "B", pattern1Nac);
		pattern1N3 = buildNode("c1", "C", pattern1Nac);
		pattern1E1 = buildEdge("AB", pattern1Nac);
		pattern1E1.setSource(pattern1N1); pattern1E1.setTarget(pattern1N2);
		pattern1E2 = buildEdge("BC", pattern1Nac);
		pattern1E2.setSource(pattern1N2); pattern1E2.setTarget(pattern1N3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern1Nac);
		((RuleGraph) pattern1).setCondition(gc);
	}
	
	private void buildMergedGraph1() {
		mergedGraph1 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", mergedGraph1);
		Node n2 = buildNode("b1", "B", mergedGraph1);
		Edge e = buildEdge("AB", mergedGraph1);
		e.setSource(n1); e.setTarget(n2);
		refItems.clear();
		refItems.put(pattern1N1, n1);
		//n1.addToReferenceItems(host1N1);
		//n1.addToReferenceItems(pattern1N1);
		
	}
	
	private void buildResultGraph1() {
		resultGraph1 = SamrulesFactory.eINSTANCE.createRuleGraph();		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac1.setGraph(resultGraph1);
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac2.setGraph(resultGraph1);
		
		Node n1 = buildNode("a1", "A", resultGraph1);
		Node n2 = buildNode("b1", "B", resultGraph1);
		Edge e1 = buildEdge("AB", resultGraph1);
		e1.setSource(n1); e1.setTarget(n2);
		
		Node n3 = buildNode("b", "B", nac1);
		Node n4 = buildNode("c", "C", nac1);
		Edge e2 = buildEdge("AB", nac1);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC", nac1);
		e3.setSource(n3); e3.setTarget(n4);
		
		Node n5 = buildNode("c", "C", nac2);
		Edge e4 = buildEdge("BC", nac2);
		e4.setSource(n2); e4.setTarget(n5);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph1).setCondition(gc);
		
	}
	
	private void buildHost2() {
		host2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host2N1 = buildNode("a1", "A", host2);
		host2N2 = buildNode("b1", "B", host2);
		host2E = buildEdge("AB", host2);
		host2E.setSource(host2N1); host2E.setTarget(host2N2);
		host2N3 = buildNode("c1", "C", host2);
		host2E2 = buildEdge("BC", host2);
		host2E2.setSource(host2N2); host2E2.setTarget(host2N3);
		host2N4 = buildNode("b2", "B", host2);
		host2E3 = buildEdge("AB", host2);
		host2E3.setSource(host2N1); host2E3.setTarget(host2N4);
	}
	
	private void buildPattern2() {
		pattern2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern2Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern2Nac.setName("nac1"); pattern2Nac.setGraph(pattern2);
		pattern2N1 = buildNode("a2", "A", pattern2);
		pattern2N2 = buildNode("b2", "B", pattern2Nac);
		pattern2N3 = buildNode("c1", "C", pattern2Nac);
		pattern2E1 = buildEdge("AB", pattern2Nac);
		pattern2E1.setSource(pattern2N1); pattern2E1.setTarget(pattern2N2);
		pattern2E2 = buildEdge("BC", pattern2Nac);
		pattern2E2.setSource(pattern2N2); pattern2E2.setTarget(pattern2N3);
		pattern2N4 = buildNode("d1", "D", pattern2Nac);
		pattern2E3 = buildEdge("CD", pattern2Nac);
		pattern2E3.setSource(pattern2N3); pattern2E3.setTarget(pattern2N4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern2Nac);
		((RuleGraph) pattern2).setCondition(gc);
	}
	
	private void buildMergedGraph2() {
		mergedGraph2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", mergedGraph2);
		Node n2 = buildNode("b1", "B", mergedGraph2);
		Edge e1 = buildEdge("AB", mergedGraph2);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", mergedGraph2);
		Edge e2 = buildEdge("BC", mergedGraph2);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("b2", "B", mergedGraph2);
		Edge e3 = buildEdge("AB", mergedGraph2);
		e3.setSource(n1); e3.setTarget(n4);
		//n1.addToReferenceItems(host2N1);
		//n1.addToReferenceItems(pattern2N1);
		refItems.clear();
		refItems.put(pattern2N1, n1);
	}
	
	private void buildResultGraph2() {
		resultGraph2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", resultGraph2);
		Node n2 = buildNode("b1", "B", resultGraph2);
		Edge e1 = buildEdge("AB", resultGraph2);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", resultGraph2);
		Edge e2 = buildEdge("BC", resultGraph2);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("b2", "B", resultGraph2);
		Edge e3 = buildEdge("AB", resultGraph2);
		e3.setSource(n1); e3.setTarget(n4);
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();		
		nac1.setGraph(resultGraph2);		
		Node n5 = buildNode("b", "B", nac1);
		Node n6 = buildNode("c", "C", nac1);
		Node n7 = buildNode("d", "D", nac1);
		Edge e4 = buildEdge("AB", nac1);
		e4.setSource(n1); e4.setTarget(n5);
		Edge e5 = buildEdge("BC", nac1);
		e5.setSource(n5); e5.setTarget(n6);
		Edge e6 = buildEdge("CD", nac1);
		e6.setSource(n6); e6.setTarget(n7);
		
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac2.setGraph(resultGraph2);
		Node n8 = buildNode("d", "D", nac2);
		Edge e7 = buildEdge("CD", nac2);
		e7.setSource(n3); e7.setTarget(n8);
		
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac3.setGraph(resultGraph2);
		Node n9 = buildNode("c", "C", nac3);
		Node n10 = buildNode("d", "D", nac3);
		Edge e8 = buildEdge("BC", nac3);
		e8.setSource(n2); e8.setTarget(n9);
		Edge e9 = buildEdge("CD", nac3);
		e9.setSource(n9); e9.setTarget(n10);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();	
		nac4.setGraph(resultGraph2);
		Node n11 = buildNode("c", "C", nac4);
		Node n12 = buildNode("d", "D", nac4);
		Edge e10 = buildEdge("BC", nac4);
		e10.setSource(n4); e10.setTarget(n11);
		Edge e11 = buildEdge("CD", nac4);
		e11.setSource(n11); e11.setTarget(n12);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		nacs.add(nac3);
		nacs.add(nac4);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph2).setCondition(gc);
		
		
	}
	
	private void buildHost3() {
		host3 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host3N1 = buildNode("a1", "A", host3);
		host3N2 = buildNode("b1", "B", host3);
		host3E = buildEdge("AB", host3);
		host3E.setSource(host3N1); host3E.setTarget(host3N2);
		host3N3 = buildNode("c1", "C", host3);
		host3E2 = buildEdge("BC", host3);
		host3E2.setSource(host3N2); host3E2.setTarget(host3N3);
	}
	
	private void buildPattern3() {
		pattern3 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern3Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern3Nac.setName("nac1"); pattern3Nac.setGraph(pattern3);
		pattern3N1 = buildNode("a2", "A", pattern3);
		pattern3N2 = buildNode("b2", "B", pattern3Nac);
		pattern3N3 = buildNode("c1", "C", pattern3Nac);
		pattern3E1 = buildEdge("AB", pattern3Nac);
		pattern3E1.setSource(pattern3N1); pattern3E1.setTarget(pattern3N2);
		pattern3E2 = buildEdge("BC", pattern3Nac);
		pattern3E2.setSource(pattern3N2); pattern3E2.setTarget(pattern3N3);
		//pattern3N4 = buildNode("d1", "D", pattern3Nac);
		//pattern3E3 = buildEdge("cd", "CD", pattern3Nac);
		//pattern3E3.setSource(pattern3N3); pattern3E3.setTarget(pattern3N4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern3Nac);
		((RuleGraph) pattern3).setCondition(gc);
	}
	
	private void buildMergedGraph3() {
		mergedGraph3 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", mergedGraph3);
		Node n2 = buildNode("b1", "B", mergedGraph3);
		Edge e1 = buildEdge("AB", mergedGraph3);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", mergedGraph3);
		Edge e2 = buildEdge("BC", mergedGraph3);
		e2.setSource(n2); e2.setTarget(n3);
		//n1.addToReferenceItems(host3N1);
		//n1.addToReferenceItems(pattern3N1);
		refItems.clear();
		refItems.put(pattern3N1, n1);
	}
	
	private void buildHost4() {
		host4 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host4N1 = buildNode("a1", "A", host4);
		host4N2 = buildNode("b1", "B", host4);
		host4E = buildEdge("AB", host4);
		host4E.setSource(host4N1); host4E.setTarget(host4N2);
		host4N3 = buildNode("c1", "C", host4);
		host4E2 = buildEdge("BC", host4);
		host4E2.setSource(host4N2); host4E2.setTarget(host4N3);
	}
	
	private void buildPattern4() {
		pattern4 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern4Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern4Nac.setName("nac1"); pattern4Nac.setGraph(pattern4);
		pattern4N1 = buildNode("a1", "A", pattern4);
		pattern4N2 = buildNode("b2", "B", pattern4);
		pattern4N3 = buildNode("c2", "C", pattern4Nac);
		pattern4N4 = buildNode("d1", "D", pattern4Nac);
		pattern4N5 = buildNode("e1", "E", pattern4Nac);
		pattern4E1 = buildEdge("AB", pattern4);
		pattern4E2 = buildEdge("BC", pattern4Nac);
		pattern4E3 = buildEdge("CD", pattern4Nac);
		pattern4E4 = buildEdge("DE", pattern4Nac);
		pattern4E5 = buildEdge("EA", pattern4Nac);
		pattern4E1.setSource(pattern4N1); pattern4E1.setTarget(pattern4N2);
		pattern4E2.setSource(pattern4N2); pattern4E2.setTarget(pattern4N3);
		pattern4E3.setSource(pattern4N3); pattern4E3.setTarget(pattern4N4);
		pattern4E4.setSource(pattern4N4); pattern4E4.setTarget(pattern4N5);
		pattern4E5.setSource(pattern4N5); pattern4E5.setTarget(pattern4N1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern4Nac);
		((RuleGraph) pattern4).setCondition(gc);
	}
	
	private void buildMergedGraph4() {
		mergedGraph4 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", mergedGraph4);
		Node n2 = buildNode("b1", "B", mergedGraph4);
		Edge e1 = buildEdge("AB", mergedGraph4);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", mergedGraph4);
		Edge e2 = buildEdge("BC", mergedGraph4);
		e2.setSource(n2); e2.setTarget(n3);
		/*n1.addToReferenceItems(host4N1);
		n1.addToReferenceItems(pattern4N1);
		n2.addToReferenceItems(host4N2);
		n2.addToReferenceItems(pattern4N2);
		e1.addToReferenceItems(host4E);
		e1.addToReferenceItems(pattern4E1);
		e2.addToReferenceItems(host4E2);*/
		refItems.clear();
		refItems.put(pattern4N1, n1);
		refItems.put(pattern4N2, n2);
		refItems.put(pattern4E1, e1);
	}
	
	private void buildResultGraph4() {
		resultGraph4 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", resultGraph4);
		Node n2 = buildNode("b1", "B", resultGraph4);
		Edge e1 = buildEdge("AB", resultGraph4);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", resultGraph4);
		Edge e2 = buildEdge("BC", resultGraph4);
		e2.setSource(n2); e2.setTarget(n3);
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac1.setGraph(resultGraph4);
		Node n4 = buildNode("c", "C", nac1);
		Node n5 = buildNode("d", "D", nac1);
		Node n6 = buildNode("e", "E", nac1);
		Edge e3 = buildEdge("BC", nac1);
		e3.setSource(n2); e3.setTarget(n4);
		Edge e4 = buildEdge("CD", nac1);
		e4.setSource(n4); e4.setTarget(n5);
		Edge e5 = buildEdge("DE", nac1);
		e5.setSource(n5); e5.setTarget(n6);
		Edge e6 = buildEdge("EA", nac1);
		e6.setSource(n6); e6.setTarget(n1);
		
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac2.setGraph(resultGraph4);
		Node n7 = buildNode("d", "D", nac2);
		Node n8 = buildNode("e", "E", nac2);
		Edge e7 = buildEdge("CD", nac2);
		e7.setSource(n3); e7.setTarget(n7);
		Edge e8 = buildEdge("DE", nac2);
		e8.setSource(n7); e8.setTarget(n8);
		Edge e9 = buildEdge("EA", nac2);
		e9.setSource(n8); e9.setTarget(n1);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph4).setCondition(gc);
	}
	
	private void buildHost5() {
		host5 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host5N1 = buildNode("a1", "A", host5);
		host5N2 = buildNode("b1", "B", host5);
		host5E = buildEdge("AB", host5);
		host5E.setSource(host5N1); host5E.setTarget(host5N2);
		host5N3 = buildNode("c1", "C", host5);
		host5E2 = buildEdge("BC", host5);
		host5E2.setSource(host5N2); host5E2.setTarget(host5N3);
		host5N4 = buildNode("e1", "E", host5);
		host5E3 = buildEdge("EA", host5);
		host5E3.setSource(host5N4); host5E3.setTarget(host5N1);
	}
	
	private void buildPattern5() {
		pattern5 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern5Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern5Nac.setName("nac1"); pattern5Nac.setGraph(pattern5);
		pattern5N1 = buildNode("a1", "A", pattern5);
		pattern5N2 = buildNode("b2", "B", pattern5);
		pattern5N3 = buildNode("c2", "C", pattern5Nac);
		pattern5N4 = buildNode("d1", "D", pattern5Nac);
		pattern5N5 = buildNode("e1", "E", pattern5Nac);
		pattern5E1 = buildEdge("AB", pattern5);
		pattern5E2 = buildEdge("BC", pattern5Nac);
		pattern5E3 = buildEdge("CD", pattern5Nac);
		pattern5E4 = buildEdge("DE", pattern5Nac);
		pattern5E5 = buildEdge("EA", pattern5Nac);
		pattern5E1.setSource(pattern5N1); pattern5E1.setTarget(pattern5N2);
		pattern5E2.setSource(pattern5N2); pattern5E2.setTarget(pattern5N3);
		pattern5E3.setSource(pattern5N3); pattern5E3.setTarget(pattern5N4);
		pattern5E4.setSource(pattern5N4); pattern5E4.setTarget(pattern5N5);
		pattern5E5.setSource(pattern5N5); pattern5E5.setTarget(pattern5N1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern5Nac);
		((RuleGraph) pattern5).setCondition(gc);
	}
	
	private void buildMergedGraph5() {
		mergedGraph5 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", mergedGraph5);
		Node n2 = buildNode("b1", "B", mergedGraph5);
		Edge e1 = buildEdge("AB", mergedGraph5);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", mergedGraph5);
		Edge e2 = buildEdge("BC", mergedGraph5);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("e1", "E", mergedGraph5);
		Edge e3 = buildEdge("EA", mergedGraph5);
		e3.setSource(n4); e3.setTarget(n1);
		/*n1.addToReferenceItems(host5N1);
		n1.addToReferenceItems(pattern5N1);
		n2.addToReferenceItems(host5N2);
		n2.addToReferenceItems(pattern5N2);
		e1.addToReferenceItems(host5E);
		e1.addToReferenceItems(pattern5E1);*/
		refItems.clear();
		refItems.put(pattern5N1, n1);
		refItems.put(pattern5N2, n2);
		refItems.put(pattern5E1, e1);
	}
	
	private void buildResultGraph5() {
		resultGraph5 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", resultGraph5);
		Node n2 = buildNode("b1", "B", resultGraph5);
		Edge e1 = buildEdge("AB", resultGraph5);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", resultGraph5);
		Edge e2 = buildEdge("BC", resultGraph5);
		e2.setSource(n2); e2.setTarget(n3);
		Node n3e = buildNode("e", "E", resultGraph5);
		Edge e2e = buildEdge("EA", resultGraph5);
		e2e.setSource(n3e); e2e.setTarget(n1);
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac1.setGraph(resultGraph5);
		Node n4 = buildNode("c", "C", nac1);
		Node n5 = buildNode("d", "D", nac1);
		Node n6 = buildNode("e", "E", nac1);
		Edge e3 = buildEdge("BC", nac1);
		e3.setSource(n2); e3.setTarget(n4);
		Edge e4 = buildEdge("CD", nac1);
		e4.setSource(n4); e4.setTarget(n5);
		Edge e5 = buildEdge("DE", nac1);
		e5.setSource(n5); e5.setTarget(n6);
		Edge e6 = buildEdge("EA", nac1);
		e6.setSource(n6); e6.setTarget(n1);
		
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac2.setGraph(resultGraph5);
		Node n7 = buildNode("d", "D", nac2);
		Node n8 = buildNode("e", "E", nac2);
		Edge e7 = buildEdge("CD", nac2);
		e7.setSource(n3); e7.setTarget(n7);
		Edge e8 = buildEdge("DE", nac2);
		e8.setSource(n7); e8.setTarget(n8);
		Edge e9 = buildEdge("EA", nac2);
		e9.setSource(n8); e9.setTarget(n1);		
		
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac3.setGraph(resultGraph5);
		Node n9 = buildNode("c", "C", nac3);
		Node n10 = buildNode("d", "D", nac3);
		Edge e10 = buildEdge("BC", nac3);
		e10.setSource(n2); e10.setTarget(n9);
		Edge e11 = buildEdge("CD", nac3);
		e11.setSource(n9); e11.setTarget(n10);
		Edge e12 = buildEdge("DE", nac3);
		e12.setSource(n10); e12.setTarget(n3e);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		nac4.setGraph(resultGraph5);
		Node n11 = buildNode("d", "D", nac4);
		Edge e13 = buildEdge("CD", nac4);
		e13.setSource(n3); e13.setTarget(n11);
		Edge e14 = buildEdge("DE", nac4);
		e14.setSource(n11); e14.setTarget(n3e);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		nacs.add(nac3);
		nacs.add(nac4);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph5).setCondition(gc);
		
	}
	
	private void buildHost6() {
		host6 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host6N1 = buildNode("a", "A", host6);
		host6N2 = buildNode("b1", "B", host6);
		host6E = buildEdge("AB", host6);
		host6E.setSource(host6N1); host6E.setTarget(host6N2);
		host6N3 = buildNode("c1", "C", host6);
		host6E2 = buildEdge("BC", host6);
		host6E2.setSource(host6N2); host6E2.setTarget(host6N3);
		
		host6N4 = buildNode("f", "F", host6);
		host6E3 = buildEdge("FA", host6);
		host6E3.setSource(host6N4); host6E3.setTarget(host6N1);
		host6N5 = buildNode("e", "E", host6);
		host6E4 = buildEdge("EF", host6);
		host6E4.setSource(host6N5); host6E4.setTarget(host6N4);
		
		host6N6 = buildNode("b2", "B", host6);
		host6E5 = buildEdge("AB", host6);
		host6E5.setSource(host6N6); host6E5.setTarget(host6N1);
	}
	
	private void buildPattern6() {
		pattern6 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern6Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern6Nac.setName("nac1"); pattern6Nac.setGraph(pattern6);
		pattern6N1 = buildNode("a", "A", pattern6);
		pattern6N2 = buildNode("b", "B", pattern6Nac);
		pattern6N3 = buildNode("c", "C", pattern6Nac);
		pattern6N4 = buildNode("d", "D", pattern6Nac);
		pattern6N5 = buildNode("e", "E", pattern6Nac);
		pattern6N6 = buildNode("f", "F", pattern6);
		pattern6E1 = buildEdge("AB", pattern6Nac);
		pattern6E2 = buildEdge("BC", pattern6Nac);
		pattern6E3 = buildEdge("CD", pattern6Nac);
		pattern6E4 = buildEdge("DE", pattern6Nac);
		pattern6E5 = buildEdge("EF", pattern6Nac);
		pattern6E6 = buildEdge("FA", pattern6);
		pattern6E1.setSource(pattern6N1); pattern6E1.setTarget(pattern6N2);
		pattern6E2.setSource(pattern6N2); pattern6E2.setTarget(pattern6N3);
		pattern6E3.setSource(pattern6N3); pattern6E3.setTarget(pattern6N4);
		pattern6E4.setSource(pattern6N4); pattern6E4.setTarget(pattern6N5);
		pattern6E5.setSource(pattern6N5); pattern6E5.setTarget(pattern6N6);
		pattern6E6.setSource(pattern6N6); pattern6E6.setTarget(pattern6N1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern6Nac);
		((RuleGraph) pattern6).setCondition(gc);
	}
	
	private void buildMergedGraph6() {
		mergedGraph6 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a", "A", mergedGraph6);
		Node n2 = buildNode("b1", "B", mergedGraph6);
		Edge e1 = buildEdge("AB", mergedGraph6);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", mergedGraph6);
		Edge e2 = buildEdge("BC", mergedGraph6);
		e2.setSource(n2); e2.setTarget(n3);
		
		Node n6 = buildNode("b2", "B", mergedGraph6);
		Edge e5 = buildEdge("AB", mergedGraph6);
		e5.setSource(n1); e5.setTarget(n6);
		
		Node n4 = buildNode("f", "F", mergedGraph6);
		Edge e3 = buildEdge("FA", mergedGraph6);
		e3.setSource(n4); e3.setTarget(n1);
		
		Node n5 = buildNode("e", "E", mergedGraph6);
		Edge e4 = buildEdge("EF", mergedGraph6);
		e4.setSource(n4); e4.setTarget(n5);
		
		/*n1.addToReferenceItems(host6N1);
		n1.addToReferenceItems(pattern6N1);
		n4.addToReferenceItems(host6N4);
		n4.addToReferenceItems(pattern6N6);
		e3.addToReferenceItems(host6E3);*/
		refItems.clear();
		refItems.put(pattern6N1, n1);
		refItems.put(pattern6N6, n4);
		refItems.put(pattern6E3, e3);		
	}

	public void buildResultGraph6() {
		resultGraph6 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a", "A", resultGraph6);
		Node n2 = buildNode("b1", "B", resultGraph6);
		Node n3 = buildNode("c1", "C", resultGraph6);
		Node n4 = buildNode("f", "F", resultGraph6);
		Node n5 = buildNode("e", "E", resultGraph6);
		Node n6 = buildNode("b2", "B", resultGraph6);		
		Edge e1 = buildEdge("AB", resultGraph6);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC", resultGraph6);
		e2.setSource(n2); e2.setTarget(n3);
		Edge e3 = buildEdge("FA", resultGraph6);
		e3.setSource(n4); e3.setTarget(n1);
		Edge e4 = buildEdge("EF", resultGraph6);
		e4.setSource(n4); e4.setTarget(n5);
		Edge e5 = buildEdge("AB", resultGraph6);
		e5.setSource(n1); e5.setTarget(n6);
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		Node nn1 = buildNode("b", "B", nac1);
		Node nn2 = buildNode("c", "C", nac1);
		Node nn3 = buildNode("d", "D", nac1);
		Node nn4 = buildNode("e", "E", nac1);
		
		e1 = buildEdge("AB", nac1);
		e1.setSource(n1); e1.setTarget(nn1);
		e2 = buildEdge("BC", nac1);
		e2.setSource(nn1); e2.setTarget(nn2);
		e3 = buildEdge("CD", nac1);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac1);
		e4.setSource(nn3); e4.setTarget(nn4);		
		e5 = buildEdge("EF", nac1);
		e5.setSource(nn4); e5.setTarget(n4);
				
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn2 = buildNode("c", "C", nac2);
		nn3 = buildNode("d", "D", nac2);
		nn4 = buildNode("e", "E", nac2);
				
		e2 = buildEdge("BC", nac2);
		e2.setSource(n2); e2.setTarget(nn2);
		e3 = buildEdge("CD", nac2);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac2);
		e4.setSource(nn3); e4.setTarget(nn4);		
		e5 = buildEdge("EF", nac2);
		e5.setSource(nn4); e5.setTarget(n4);
		
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn2 = buildNode("c", "C", nac3);
		nn3 = buildNode("d", "D", nac3);
		nn4 = buildNode("e", "E", nac3);
				
		e2 = buildEdge("BC", nac3);
		e2.setSource(n6); e2.setTarget(nn2);
		e3 = buildEdge("CD", nac3);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac3);
		e4.setSource(nn3); e4.setTarget(nn4);		
		e5 = buildEdge("EF", nac3);
		e5.setSource(nn4); e5.setTarget(n4);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn3 = buildNode("d", "D", nac4);
		nn4 = buildNode("e", "E", nac4);
				
		e3 = buildEdge("CD", nac4);
		e3.setSource(n3); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac4);
		e4.setSource(nn3); e4.setTarget(nn4);		
		e5 = buildEdge("EF", nac4);
		e5.setSource(nn4); e5.setTarget(n4);		
		
		NegativeApplicationCondition nac5 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("b", "B", nac5);
		nn2 = buildNode("c", "C", nac5);
		nn3 = buildNode("d", "D", nac5);		
		
		e1 = buildEdge("AB", nac5);
		e1.setSource(n1); e1.setTarget(nn1);
		e2 = buildEdge("BC", nac5);
		e2.setSource(nn1); e2.setTarget(nn2);
		e3 = buildEdge("CD", nac5);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac5);
		e4.setSource(nn3); e4.setTarget(n5);		
		
		NegativeApplicationCondition nac6 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn2 = buildNode("c", "C", nac6);
		nn3 = buildNode("d", "D", nac6);		
				
		e2 = buildEdge("BC", nac6);
		e2.setSource(n2); e2.setTarget(nn2);
		e3 = buildEdge("CD", nac6);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac6);
		e4.setSource(nn3); e4.setTarget(n5);
		
		NegativeApplicationCondition nac7 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn2 = buildNode("c", "C", nac7);
		nn3 = buildNode("d", "D", nac7);		
				
		e2 = buildEdge("BC", nac7);
		e2.setSource(n6); e2.setTarget(nn2);
		e3 = buildEdge("CD", nac7);
		e3.setSource(nn2); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac7);
		e4.setSource(nn3); e4.setTarget(n5);		
		
		NegativeApplicationCondition nac8 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				
		nn3 = buildNode("d", "D", nac8);		
				
		e3 = buildEdge("CD", nac8);
		e3.setSource(n3); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac8);
		e4.setSource(nn3); e4.setTarget(n5);		
		/*
		NegativeApplicationCondition nac9 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("b", "B", nac9);
		nn3 = buildNode("d", "D", nac9);		
				
		e1 = buildEdge("AB", nac9);
		e1.setSource(n1); e1.setTarget(nn1);
		e2 = buildEdge("BC", nac9);
		e2.setSource(nn1); e2.setTarget(n3);
		e3 = buildEdge("CD", nac9);
		e3.setSource(n3); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac9);
		e4.setSource(nn3); e4.setTarget(n5);
		
		NegativeApplicationCondition nac10 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("b", "B", nac10);
		nn3 = buildNode("d", "D", nac10);
		nn4 = buildNode("e", "E", nac10);
				
		e1 = buildEdge("AB", nac10);
		e1.setSource(n1); e1.setTarget(nn1);
		e2 = buildEdge("BC", nac10);
		e2.setSource(nn1); e2.setTarget(n3);
		e3 = buildEdge("CD", nac10);
		e3.setSource(n3); e3.setTarget(nn3);
		e4 = buildEdge("DE", nac10);
		e4.setSource(nn3); e4.setTarget(nn4);
		e5 = buildEdge("EF", nac10);
		e5.setSource(nn4); e5.setTarget(n4);
		*/
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		nacs.add(nac3);
		nacs.add(nac4);
		nacs.add(nac5);
		nacs.add(nac6);
		nacs.add(nac7);
		nacs.add(nac8);
		//nacs.add(nac9);
		//nacs.add(nac10);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph6).setCondition(gc);


	}
	
	private void buildHost7() {
		host7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host7N1 = buildNode("a1", "A", host7);
		host7N2 = buildNode("b1", "B", host7);
		host7N3 = buildNode("b2", "B", host7);
		host7N4 = buildNode("b3", "B", host7);
		host7N5 = buildNode("c1", "C", host7);
		host7N6 = buildNode("c2", "C", host7);
		host7N7 = buildNode("c3", "C", host7);
		
		host7E1 = buildEdge("AB", host7);
		host7E1.setSource(host7N1); host7E1.setTarget(host7N2);
		host7E2 = buildEdge("AB", host7);
		host7E2.setSource(host7N1); host7E2.setTarget(host7N3);
		host7E3 = buildEdge("AB", host7);
		host7E3.setSource(host7N1); host7E3.setTarget(host7N4);
		host7E4 = buildEdge("BC", host7);
		host7E4.setSource(host7N2); host7E4.setTarget(host7N5);
		host7E5 = buildEdge("BC", host7);
		host7E5.setSource(host7N2); host7E5.setTarget(host7N7);
		host7E6 = buildEdge("BC", host7);
		host7E6.setSource(host7N4); host7E6.setTarget(host7N6);
	}
	
	private void buildPattern7() {
		pattern7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern7Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern7Nac.setName("nac1"); pattern7Nac.setGraph(pattern7);
		
		pattern7N1 = buildNode("a1", "A", pattern7);
		pattern7N2 = buildNode("aNac", "A", pattern7Nac);
		pattern7N3 = buildNode("b1", "B", pattern7);
		pattern7N4 = buildNode("bNac", "B", pattern7Nac);
		pattern7N5 = buildNode("c1Nac", "C", pattern7Nac);
		pattern7N6 = buildNode("c2Nac", "C", pattern7Nac);
		
		pattern7E1 = buildEdge("AB", pattern7);
		pattern7E2 = buildEdge("AB", pattern7Nac);
		pattern7E3 = buildEdge("AB", pattern7Nac);
		pattern7E4 = buildEdge("BC", pattern7Nac);
		pattern7E5 = buildEdge("BC", pattern7Nac);
		
		pattern7E1.setSource(pattern7N1); pattern7E1.setTarget(pattern7N3);
		pattern7E2.setSource(pattern7N1); pattern7E2.setTarget(pattern7N4);
		pattern7E3.setSource(pattern7N2); pattern7E3.setTarget(pattern7N4);
		pattern7E4.setSource(pattern7N3); pattern7E4.setTarget(pattern7N6);
		pattern7E5.setSource(pattern7N4); pattern7E5.setTarget(pattern7N5);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern7Nac);
		((RuleGraph) pattern7).setCondition(gc);
		
	}
	
	private void buildMergedGraph7() {
		mergedGraph7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		merged7N1 = buildNode("a1", "A", mergedGraph7);
		merged7N2 = buildNode("b1", "B", mergedGraph7);
		merged7N3 = buildNode("b2", "B", mergedGraph7);
		merged7N4 = buildNode("b3", "B", mergedGraph7);
		merged7N5 = buildNode("c1", "C", mergedGraph7);
		merged7N6 = buildNode("c2", "C", mergedGraph7);
		merged7N7 = buildNode("c3", "C", mergedGraph7);
		
		merged7E1 = buildEdge("AB", mergedGraph7);
		merged7E1.setSource(merged7N1); merged7E1.setTarget(merged7N2);
		merged7E2 = buildEdge("AB", mergedGraph7);
		merged7E2.setSource(merged7N1); merged7E2.setTarget(merged7N3);
		merged7E3 = buildEdge("AB", mergedGraph7);
		merged7E3.setSource(merged7N1); merged7E3.setTarget(merged7N4);
		merged7E4 = buildEdge("BC", mergedGraph7);
		merged7E4.setSource(merged7N2); merged7E4.setTarget(merged7N5);
		merged7E5 = buildEdge("BC", mergedGraph7);
		merged7E5.setSource(merged7N2); merged7E5.setTarget(merged7N7);
		merged7E6 = buildEdge("BC", mergedGraph7);
		merged7E6.setSource(merged7N4); merged7E6.setTarget(merged7N6);
		
		/*merged7N1.addToReferenceItems(host7N1);
		merged7N1.addToReferenceItems(pattern7N1);
		merged7N2.addToReferenceItems(host7N2);
		merged7N2.addToReferenceItems(pattern7N3);
		merged7E1.addToReferenceItems(host7E1);
		merged7E1.addToReferenceItems(pattern7E1);*/
		refItems.clear();
		refItems.put(pattern7N1, merged7N1);
		refItems.put(pattern7N3, merged7N2);
		refItems.put(pattern7E1, merged7E1);
	}
	
	private void buildResultGraph7() {
		resultGraph7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", resultGraph7);
		Node n2 = buildNode("b1", "B", resultGraph7);
		Node n3 = buildNode("b2", "B", resultGraph7);
		Node n4 = buildNode("b3", "B", resultGraph7);
		Node n5 = buildNode("c1", "C", resultGraph7);
		Node n6 = buildNode("c2", "C", resultGraph7);
		Node n7 = buildNode("c3", "C", resultGraph7);
		
		Edge e1 = buildEdge("AB", resultGraph7);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("AB", resultGraph7);
		e2.setSource(n1); e2.setTarget(n3);
		Edge e3 = buildEdge("AB", resultGraph7);
		e3.setSource(n1); e3.setTarget(n4);
		Edge e4 = buildEdge("BC", resultGraph7);
		e4.setSource(n2); e4.setTarget(n5);
		Edge e5 = buildEdge("BC", resultGraph7);
		e5.setSource(n2); e5.setTarget(n7);
		Edge e6 = buildEdge("BC", resultGraph7);
		e6.setSource(n4); e6.setTarget(n6);
		
		Node nn1;
		Node nn2;
		Node nn3;
		Node nn4;
		
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac1);
		nn2 = buildNode("bNac", "B", nac1);
		nn3 = buildNode("c2Nac", "C", nac1);
		nn4 = buildNode("c1Nac", "C", nac1);
		
		e1 = buildEdge("AB", nac1);
		e1.setSource(n1); e1.setTarget(nn2);
		e2 = buildEdge("BC", nac1);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", nac1);
		e3.setSource(nn1); e3.setTarget(nn2);
		e4 = buildEdge("BC", nac1);
		e4.setSource(nn2); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac2);
		nn2 = buildNode("bNac", "B", nac2);		
		nn4 = buildNode("c1Nac", "C", nac2);
		
		e1 = buildEdge("AB", nac2);
		e1.setSource(n1); e1.setTarget(nn2);		
		e3 = buildEdge("AB", nac2);
		e3.setSource(nn1); e3.setTarget(nn2);
		e4 = buildEdge("BC", nac2);
		e4.setSource(nn2); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac3);				
		nn4 = buildNode("c1Nac", "C", nac3);		
				
		e3 = buildEdge("AB", nac3);
		e3.setSource(nn1); e3.setTarget(n3);
		e4 = buildEdge("BC", nac3);
		e4.setSource(n3); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac4);				
		nn4 = buildNode("c1Nac", "C", nac4);		
				
		e3 = buildEdge("AB", nac4);
		e3.setSource(nn1); e3.setTarget(n4);
		e4 = buildEdge("BC", nac4);
		e4.setSource(n4); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac5 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac5);
				
		e3 = buildEdge("AB", nac5);
		e3.setSource(nn1); e3.setTarget(n4);
		
		NegativeApplicationCondition nac6 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac6);		
		nn3 = buildNode("c2Nac", "C", nac6);
		nn4 = buildNode("c1Nac", "C", nac6);
				
		e2 = buildEdge("BC", nac6);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", nac6);
		e3.setSource(nn1); e3.setTarget(n3);
		e4 = buildEdge("BC", nac6);
		e4.setSource(n3); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac7 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac7);		
		nn3 = buildNode("c2Nac", "C", nac7);
		nn4 = buildNode("c1Nac", "C", nac7);
				
		e2 = buildEdge("BC", nac7);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", nac7);
		e3.setSource(nn1); e3.setTarget(n4);
		e4 = buildEdge("BC", nac7);
		e4.setSource(n4); e4.setTarget(nn4);
		
		NegativeApplicationCondition nac8 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		nn1 = buildNode("aNac", "A", nac8);		
		nn3 = buildNode("c2Nac", "C", nac8);
						
		e2 = buildEdge("BC", nac8);
		e2.setSource(n2); e2.setTarget(nn3);
		e3 = buildEdge("AB", nac8);
		e3.setSource(nn1); e3.setTarget(n4);		
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		nacs.add(nac3);
		nacs.add(nac4);
		nacs.add(nac5);
		nacs.add(nac6);
		nacs.add(nac7);
		nacs.add(nac8);		
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph7).setCondition(gc);

	}
	
	private void buildHost8() {
		host8 = SamrulesFactory.eINSTANCE.createRuleGraph();
		host8N1 = buildNode("a1", "A", host8);
		host8N2 = buildNode("b1", "B", host8);
		host8E1 = buildEdge("AB", host8);
		host8E1.setSource(host4N1); host8E1.setTarget(host8N2);
		host8N3 = buildNode("c1", "C", host8);
		host8E2 = buildEdge("BC", host8);
		host8E2.setSource(host8N2); host8E2.setTarget(host8N3);
		host8N4 = buildNode("d", "D", host8);
		host8E3 = buildEdge("AD", host8);
		host8E3.setSource(host8N1); host8E3.setTarget(host8N4);
	}
	
	private void buildPattern8() {
		pattern8 = SamrulesFactory.eINSTANCE.createRuleGraph();
		pattern8Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		pattern8Nac.setName("nac1"); pattern8Nac.setGraph(pattern8);
		pattern8N1 = buildNode("a1", "A", pattern8);
		pattern8N2 = buildNode("b2", "B", pattern8);
		pattern8N3 = buildNode("c2", "C", pattern8Nac);
		pattern8N4 = buildNode("d1", "D", pattern8Nac);
		pattern8N5 = buildNode("e1", "E", pattern8Nac);
		pattern8E1 = buildEdge("AB", pattern8);
		pattern8E2 = buildEdge("BC", pattern8Nac);
		pattern8E3 = buildEdge("CD", pattern8Nac);
		pattern8E4 = buildEdge("DE", pattern8Nac);
		pattern8E5 = buildEdge("EA", pattern8Nac);
		pattern8E1.setSource(pattern8N1); pattern8E1.setTarget(pattern8N2);
		pattern8E2.setSource(pattern8N2); pattern8E2.setTarget(pattern8N3);
		pattern8E3.setSource(pattern8N3); pattern8E3.setTarget(pattern8N4);
		pattern8E4.setSource(pattern8N4); pattern8E4.setTarget(pattern8N5);
		pattern8E5.setSource(pattern8N5); pattern8E5.setTarget(pattern8N1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(pattern8Nac);
		((RuleGraph) pattern8).setCondition(gc);
	}
	
	private void buildMergedGraph8() {
		mergedGraph8 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", mergedGraph8);
		Node n2 = buildNode("b1", "B", mergedGraph8);
		Edge e1 = buildEdge("AB", mergedGraph8);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", mergedGraph8);
		Edge e2 = buildEdge("BC", mergedGraph8);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("d", "D", mergedGraph8);
		Edge e3 = buildEdge("AD", mergedGraph8);
		e3.setSource(n1); e3.setTarget(n4);
		/*n1.addToReferenceItems(host8N1);
		n1.addToReferenceItems(pattern8N1);
		n2.addToReferenceItems(host8N2);
		n2.addToReferenceItems(pattern8N2);
		e1.addToReferenceItems(host8E1);
		e1.addToReferenceItems(pattern8E1);
		e2.addToReferenceItems(host8E2);*/
		refItems.clear();
		refItems.put(pattern8N1, n1);
		refItems.put(pattern8N2, n2);
		refItems.put(pattern8E1, e1);
	}
	
	private void buildResultGraph8() {
		resultGraph8 = SamrulesFactory.eINSTANCE.createRuleGraph();
		Node n1 = buildNode("a1", "A", resultGraph8);
		Node n2 = buildNode("b1", "B", resultGraph8);
		Edge e1 = buildEdge("AB", resultGraph8);
		e1.setSource(n1); e1.setTarget(n2);
		Node n3 = buildNode("c1", "C", resultGraph8);
		Edge e2 = buildEdge("BC", resultGraph8);
		e2.setSource(n2); e2.setTarget(n3);
		Node n4 = buildNode("d", "D", resultGraph8);
		Edge e3 = buildEdge("AD", resultGraph8);
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
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		nacs.add(nac3);
		nacs.add(nac4);				
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);		
		((RuleGraph) resultGraph8).setCondition(gc);
		
	}

	private void buildSPattern7() {
		sPattern7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		sPattern7Nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		sPattern7Nac.setName("nac1"); sPattern7Nac.setGraph(sPattern7);
		
		sPattern7N1 = buildNode("a1", "A", sPattern7);
		//sPattern7N2 = buildNode("aNac", "A", sPattern7Nac);
		sPattern7N3 = buildNode("b1", "B", sPattern7);
		sPattern7N4 = buildNode("bNac", "B", sPattern7Nac);
		sPattern7N5 = buildNode("c1Nac", "C", sPattern7Nac);
		sPattern7N6 = buildNode("c2Nac", "C", sPattern7Nac);
		
		sPattern7E1 = buildEdge("AB", sPattern7); sPattern7E1.setName("ab1");
		sPattern7E2 = buildEdge("AB", sPattern7Nac); sPattern7E2.setName("abNac");
		//sPattern7E3 = buildEdge("AB", sPattern7Nac);
		sPattern7E4 = buildEdge("BC", sPattern7Nac); sPattern7E4.setName("bc2Nac");
		sPattern7E5 = buildEdge("BC", sPattern7Nac); sPattern7E5.setName("bc1Nac");
		
		sPattern7E1.setSource(sPattern7N1); sPattern7E1.setTarget(sPattern7N3);
		sPattern7E2.setSource(sPattern7N1); sPattern7E2.setTarget(sPattern7N4);
		//sPattern7E3.setSource(sPattern7N2); sPattern7E3.setTarget(sPattern7N4);
		sPattern7E4.setSource(sPattern7N3); sPattern7E4.setTarget(sPattern7N6);
		sPattern7E5.setSource(sPattern7N4); sPattern7E5.setTarget(sPattern7N5);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(sPattern7Nac);
		((RuleGraph) sPattern7).setCondition(gc);
		
	}
	
	private void buildSMergedGraph7() {
		sMergedGraph7 = SamrulesFactory.eINSTANCE.createRuleGraph();
		sMerged7N1 = buildNode("a1", "A", sMergedGraph7);
		sMerged7N2 = buildNode("b1", "B", sMergedGraph7);
		sMerged7N3 = buildNode("b2", "B", sMergedGraph7);
		
		sMerged7N4 = buildNode("c1", "C", sMergedGraph7);
		
		sMerged7N5 = buildNode("c3", "C", sMergedGraph7);
		
		sMerged7E1 = buildEdge("AB", sMergedGraph7); sMerged7E1.setName("ab1");
		sMerged7E1.setSource(sMerged7N1); sMerged7E1.setTarget(sMerged7N2);
		sMerged7E2 = buildEdge("AB", sMergedGraph7); sMerged7E2.setName("ab2");
		sMerged7E2.setSource(sMerged7N1); sMerged7E2.setTarget(sMerged7N3);
		
		sMerged7E3 = buildEdge("BC", sMergedGraph7); sMerged7E3.setName("bc1");
		sMerged7E3.setSource(sMerged7N2); sMerged7E3.setTarget(sMerged7N4);
		sMerged7E4 = buildEdge("BC", sMergedGraph7); sMerged7E4.setName("bc3");
		sMerged7E4.setSource(sMerged7N2); sMerged7E4.setTarget(sMerged7N5);
		
		
		refItems.clear();
		refItems.put(sPattern7N1, sMerged7N1);
		refItems.put(sPattern7N3, sMerged7N2);
		refItems.put(sPattern7E1, sMerged7E1);
	}
	
	public void testTestCase1() {
		buildHost1();
		buildPattern1();
		buildMergedGraph1();
		buildResultGraph1();
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		//initialMatching.put(pattern1N1, (Node)pattern1N1.getFromReferenceItems(mergedGraph1));
		for (Edge e : pattern1.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.put(e, (Edge)e.getFromReferenceItems(mergedGraph1));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern1.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph1));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph1);
		nacT.setPattern(pattern1);
		Graph res = nacT.translate();
		
		GraphWithNacs t = SamGraphInvCheckGraphAdapter.getInstance(res);
		System.out.println("nacs: " + t.getNacs().size());
		
		//Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		//MetamodelPackage.eINSTANCE.getGraph();		
		/*
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/res1.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		r.getContents().clear();
		r.getContents().add(res);
		try {
			r.save(null);
		} catch (IOException e) {
			
		}
		*/
		//System.out.println("Number of NACs: " + res.getNacs().size());
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(resultGraph1);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(resultGraph1));
		assertTrue(ipm.getNextMatching() != null);
		
		/*
		 * initialize matching algorithm
		 * receive and compare results depending on algorithm's implementation.
		 */
		//fail("test case is unimplemented, yet");
	}
	
	public void testTestCase2() {
		buildHost2();
		buildPattern2();
		buildMergedGraph2();
		buildResultGraph2();
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		//initialMatching.put(pattern2N1, (Node)pattern2N1.getFromReferenceItems(mergedGraph2));
		
		for (Edge e : pattern2.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.put(e, (Edge)e.getFromReferenceItems(mergedGraph2));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern2.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph2));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph2);
		nacT.setPattern(pattern2);
		Graph res = nacT.translate();
		assertContainments(res);
		//System.out.println("Number of NACs: " + res.getNacs().size());
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/res2.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		r.getContents().clear();
		r.getContents().add(res);
		try {
			r.save(null);
		} catch (IOException e) {
			
		}	
		
		*/
		//assertTrue(res.getNacs().size() == 7);
		
		for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(res).getNacs()) {
			System.out.println("===");
			for (Node n : nac.getNodes()) {
				System.out.println(n);
			}
			for (Edge n : nac.getEdges()) {
				System.out.println(n);
			}
		}
		
		
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(resultGraph2);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(resultGraph2));
		assertTrue(ipm.getNextMatching() != null);

		ipm = new IsomorphicPartMatcher();
		ipm.setPattern(res);
		ipm.setHostGraph(resultGraph2);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(res));
		assertTrue(ipm.getNextMatching() != null);

		/*
		 * initialize matching algorithm
		 * receive and compare results depending on algorithm's implementation.
		 */
		//fail("test case is unimplemented, yet");
	}
	
	public void testTestCase3() {
		buildHost3();
		buildPattern3();
		buildMergedGraph3();
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		//initialMatching.put(pattern3N1, (Node)pattern3N1.getFromReferenceItems(mergedGraph3));
		
		
		for (Edge e : pattern3.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.put(e, (Edge)e.getFromReferenceItems(mergedGraph3));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern3.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph3));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph3);
		nacT.setPattern(pattern3);
		assertNull(nacT.translate());
		/*
		 * initialize matching algorithm
		 * receive and compare results depending on algorithm's implementation.
		 */
		//fail("test case is unimplemented, yet");
	}
	
	public void testTestCase4() {
		buildHost4();
		buildPattern4();
		buildMergedGraph4();
		buildResultGraph4();
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		//initialMatching.put(host4N1,pattern4N1);
		//initialMatching.put(host4N2,pattern4N2);
		//initialMatching.put(host4E,pattern4E1);
		
		for (Edge e : pattern4.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.getEdgeMatching().put(e, (Edge)e.getFromReferenceItems(mergedGraph4));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern4.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph4));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph4);
		nacT.setPattern(pattern4);
		Graph res = nacT.translate();
		
		//System.out.println("Number of NACs: " + res.getNacs().size());
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/res4.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		r.getContents().clear();
		r.getContents().add(res);
		try {
			r.save(null);
		} catch (IOException e) {
			
		}
		*/
		//assertTrue(res.getNacs().size() == 6);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(resultGraph4);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(resultGraph4));
		assertTrue(ipm.getNextMatching() != null);
	}
	
	public void testTestCase5() {
		buildHost5();
		buildPattern5();
		buildMergedGraph5();
		buildResultGraph5();
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();

		for (Edge e : pattern5.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.getEdgeMatching().put(e, (Edge)e.getFromReferenceItems(mergedGraph5));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern5.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph5));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph5);
		nacT.setPattern(pattern5);
		Graph res = nacT.translate();	
		//System.out.println("Number of NACs: " + res.getNacs().size());
		
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/res5.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		r.getContents().clear();
		r.getContents().add(res);
		try {
			r.save(null);
		} catch (IOException e) {
			
		}*/
		
		
		//assertTrue(res.getNacs().size() == 35);
		//assertTrue(res.getNacs().get(0).getNodes().size() + res.getNacs().get(1).getNodes().size() == 4);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(resultGraph5);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(resultGraph5));
		assertTrue(ipm.getNextMatching() != null);

	}
	
	public void testTestCase6() {
		buildHost6();
		buildPattern6();
		buildMergedGraph6();
		buildResultGraph6();
		Graph result = resultGraph6;
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();

		for (Edge e : pattern6.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.getEdgeMatching().put(e, (Edge)e.getFromReferenceItems(mergedGraph6));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern6.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph6));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph6);
		nacT.setPattern(pattern6);
		Graph res = nacT.translate();

		
		//System.out.println("Number of NACs: " + res.getNacs().size());
		
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
/*		
		ResourceSet rs1 = new ResourceSetImpl();
		URI uri1 = URI.createURI("testsrc/res6.metamodel");
		Resource r1 = rs1.getResource(uri1, true);
		EObject o1 = r1.getContents().get(0);
		r1.getContents().clear();
		r1.getContents().add(res);
		try {
			r1.save(null);
		} catch (IOException e) {
			
		}*/
				
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();
				
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/result6.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
		       result = (Graph) o;
		} else {
			fail("loading graph model failed");
		}*/

		GraphWithNacs g = SamGraphInvCheckGraphAdapter.getInstance(res);
		for(NegativeApplicationCondition nac : g.getNacs()) {
			System.out.println("==");
			for (Node n : nac.getNodes()) {
				System.out.println(n);
			}			
			for (Edge e : nac.getEdges()) {
				System.out.println(e);
			}
			System.out.println("==");
		}
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(result);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(result));
		assertTrue(ipm.getNextMatching() != null);
	}
	
	public void testTestCase7() {
		//for (int i = 0; i <= 10; i++) { 
		buildHost7();
		buildPattern7();
		buildMergedGraph7();

		buildResultGraph7();
		Graph result = resultGraph7;
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		
		assertContainments(resultGraph7);
		assertContainments(mergedGraph7);
		

		for (Edge e : pattern7.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.getEdgeMatching().put(e, (Edge)e.getFromReferenceItems(mergedGraph7));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern7.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph7));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph7);
		nacT.setPattern(pattern7);
		Graph res = nacT.translate();
	

		assertContainments(res);
		assertContainments(result);
		
		//System.out.println("Number of NACs: " + res.getNacs().size());
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs1 = new ResourceSetImpl();
		URI uri1 = URI.createURI("testsrc/res7.metamodel");
		Resource r1 = rs1.getResource(uri1, true);
		EObject o1 = r1.getContents().get(0);
		r1.getContents().clear();
		r1.getContents().add(res);
		try {
			r1.save(null);
		} catch (IOException e) {
			
		}*/
		
		//System.out.println(res.getNacs().size());
		//assertTrue(res.getNacs().size() == 8);
		
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();
				
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("tests/result7.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
		       result = (Graph) o;
		} else {
			fail("loading graph model failed");
		}*/
		
		GraphWithNacs g = SamGraphInvCheckGraphAdapter.getInstance(res);
		for(NegativeApplicationCondition nac : g.getNacs()) {
			//System.out.println("==");
			for (Node n : nac.getNodes()) {
				//System.out.println(n.getName());
			}			
			for (Edge e : nac.getEdges()) {
				//System.out.println(e.getSource().getName() + ":" + e.getTarget().getName());
			}
			//System.out.println("==");
		}
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(result);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(result));
		assertTrue(ipm.getNextMatching() != null);
		
		IsomorphicPartMatcher ipm2 = new IsomorphicPartMatcher();
		ipm2.setPattern(res);
		ipm2.setHostGraph(res);
		ipm2.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(res));
		assertTrue(ipm2.getNextMatching() != null);
		System.out.println("=== next match ====");
		assertNotNull(ipm2.getNextMatching());
		System.out.println("=== next match ====");
		assertNull(ipm2.getNextMatching());
		
		IsomorphicPartMatcher ipm3 = new IsomorphicPartMatcher();
		ipm3.setPattern(pattern7);
		ipm3.setHostGraph(res);
		ipm3.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(pattern7));
		assertTrue(ipm3.getNextMatching() != null);		
		ipm3.reset();		
				
		ipm3.setPattern(mergedGraph7);
		ipm3.setHostGraph(res);
		ipm3.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(mergedGraph7));
		assertTrue(ipm3.getNextMatching() != null);
		assertNotNull(ipm3.getNextMatching());
		assertNull(ipm3.getNextMatching());
	}//}
	
	public void testTestCase8() {
		buildHost8();
		buildPattern8();
		buildMergedGraph8();
		buildResultGraph8();
		Graph result = resultGraph8;
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		//initialMatching.put(host4N1,pattern4N1);
		//initialMatching.put(host4N2,pattern4N2);
		//initialMatching.put(host4E,pattern4E1);
		
		for (Edge e : pattern8.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.getEdgeMatching().put(e, (Edge)e.getFromReferenceItems(mergedGraph8));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : pattern8.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph8));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) mergedGraph8);
		nacT.setPattern(pattern8);
		Graph res = nacT.translate();
		
		//System.out.println("Number of NACs: " + res.getNacs().size());
		
		/*
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/res8.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		r.getContents().clear();
		r.getContents().add(res);
		try {
			r.save(null);
		} catch (IOException e) {
			
		}*/
		
		
		//assertTrue(res.getNacs().size() == 4);
		
		GraphWithNacs g = SamGraphInvCheckGraphAdapter.getInstance(res);
		for(NegativeApplicationCondition nac : g.getNacs()) {
			System.out.println("==");
			for (Node n : nac.getNodes()) {
				System.out.println(n.getName());
			}			
			for (Edge e : nac.getEdges()) {
				System.out.println(e.getSource().getName() + ":" + e.getTarget().getName());
			}
			System.out.println("==");
		}

		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();

		ipm.setPattern(res);
		ipm.setHostGraph((RuleGraph) result);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(res));
		assertTrue(ipm.getNextMatching() != null);
		assertNull(ipm.getNextMatching());
		
		IsomorphicPartMatcher ipm2 = new IsomorphicPartMatcher();
		ipm2.setPattern(result);
		ipm2.setHostGraph((RuleGraph) res);
		ipm2.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(result));
		assertTrue(ipm2.getNextMatching() != null);
		assertNull(ipm2.getNextMatching());
	}
	
	public void testSmallerTestCase7() {		
		buildSPattern7();
		buildSMergedGraph7();
		//buildResultGraph8();
		//Graph result = sResultGraph8;
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		
		for (Edge e : sPattern7.getEdges()) {
			if (!InvariantCheckerUtil.isNegated(e)) {
				//initialMatching.getEdgeMatching().put(e, (Edge)e.getFromReferenceItems(mergedGraph8));
				initialMatching.getEdgeMatching().put(e, (Edge) refItems.get(e));
			}
		}
		for (Node n : sPattern7.getNodes()) {
			if (!InvariantCheckerUtil.isNegated(n)) {
				//initialMatching.getNodeMatching().put(n, (Node)n.getFromReferenceItems(mergedGraph8));
				initialMatching.getNodeMatching().put(n, (Node) refItems.get(n));
			}
		}
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph((RuleGraph) sMergedGraph7);
		nacT.setPattern(sPattern7);
		Graph res = nacT.translate();
		
		GraphWithNacs g = SamGraphInvCheckGraphAdapter.getInstance(res);
		//assertEquals(g.getNacs().size(), 33);
//		assertEquals(g.getNacs().size(), 21);
		System.out.println(g.getNacs().size());
		for(NegativeApplicationCondition nac : g.getNacs()) {
			//System.out.println("==");
			for (Node n : nac.getNodes()) {
				//System.out.println(n.getName());
			}			
			for (Edge e : nac.getEdges()) {
				//System.out.println(e.getSource().getName() + ":" + e.getTarget().getName());
			}
			//System.out.println("==");
		}

		System.out.println("iso");
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(res);
		ipm.setHostGraph((RuleGraph) res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(res));
		assertTrue(ipm.getNextMatching() != null);
		assertTrue(ipm.getNextMatching() != null);
		assertTrue(ipm.getNextMatching() == null);
		
		System.out.println("merged");
		ipm.reset();
		ipm.setPattern(sMergedGraph7);
		ipm.setHostGraph((RuleGraph) res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(sMergedGraph7));
		assertTrue(ipm.getNextMatching() != null);
		assertTrue(ipm.getNextMatching() != null);
		assertTrue(ipm.getNextMatching() == null);
		
		System.out.println("pattern");
		ipm.reset();
		ipm.setPattern(sPattern7);
		ipm.setHostGraph((RuleGraph) res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(sPattern7));
		assertTrue(ipm.getNextMatching() != null);
		assertTrue(ipm.getNextMatching() != null);
		assertTrue(ipm.getNextMatching() == null);
		assertEquals(g.getNacs().size(), 14);
	}
	
	/*
	public void testTestCaseFile() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();
		Graph pattern = null;
		Graph merged = null;
		Graph result = null;
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/pattern-test2.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
		       pattern = (Graph) o;
		} else {
			fail("loading graph model failed");
		}
		
		uri = URI.createURI("testsrc/merged-test2.metamodel");
		Resource rMerged = rs.getResource(uri, true);
		o = rMerged.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
			merged = (Graph) o;
		} else {
			fail("loading graph model failed");
		}
		
		Graph host = SamrulesFactory.eINSTANCE.createRuleGraph();
		for (Node n : merged.getNodes()) {
			Node n1 = (Node) n.copyMe();
			n1.setGraph(host);
		}
		for (Edge e : merged.getEdges()) {
			Edge e1 = (Edge) e.copyMe();
			e1.setGraph(host);
		}
		
		/*uri = URI.createURI("tests/result-test2.metamodel");
		Resource rResult = rs.getResource(uri, true);
		o = rResult.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
			result = (Graph) o;
		} else {
			fail("loading graph model failed");
		}
		*//*
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		initialMatching.put(pattern.getNodes().get(0), merged.getNodes().get(0));
		initialMatching.put(pattern.getNodes().get(1), merged.getNodes().get(1));
		initialMatching.put(pattern.getEdges().get(0), merged.getEdges().get(0));
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph(merged);
		nacT.setPattern(pattern);		
		
		Graph res = nacT.translate();
		
		System.out.println("Number of NACs: " + res.getNacs().size());
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs1 = new ResourceSetImpl();
		URI uri1 = URI.createURI("testsrc/resFile1.metamodel");
		Resource r1 = rs1.getResource(uri1, true);
		EObject o1 = r1.getContents().get(0);
		r1.getContents().clear();
		r1.getContents().add(res);
		try {
			r1.save(null);
		} catch (IOException e) {
			
		}
		
		System.out.println(res.getNacs().size());
		assertTrue(res.getNacs().size() == 6);
		
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setPattern(result);
		ipm.setHostGraph(res);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(result));
		assertTrue(ipm.getNextMatching() != null);
		
	}*/
	
	/*
	public void testTestCaseFile2() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();
		Graph pattern = null;
		Graph merged = null;
		Graph result = null;
		
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createURI("testsrc/pattern-test3.metamodel");
		Resource r = rs.getResource(uri, true);
		EObject o = r.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
		       pattern = (Graph) o;
		} else {
			fail("loading graph model failed");
		}
		
		uri = URI.createURI("testsrc/merged-test3.metamodel");
		Resource rMerged = rs.getResource(uri, true);
		o = rMerged.getContents().get(0);
		if (o.eClass() == MetamodelPackage.eINSTANCE.getGraph()) {
			merged = (Graph) o;
		} else {
			fail("loading graph model failed");
		}
		
		Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
		initialMatching.put(pattern.getNodes().get(0), merged.getNodes().get(0));
		initialMatching.put(pattern.getNodes().get(1), merged.getNodes().get(1));
		initialMatching.put(pattern.getEdges().get(0), merged.getEdges().get(0));
		
		NACTranslator nacT = new NACTranslator();
		nacT.setInitialMatching(initialMatching);
		nacT.setMergedGraph(merged);
		nacT.setPattern(pattern);
		
		result = nacT.translate();
		
		System.out.println("Number of NACs: " + result.getNacs().size());
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("metamodel",	new XMIResourceFactoryImpl());
		MetamodelPackage.eINSTANCE.getGraph();		
		
		ResourceSet rs1 = new ResourceSetImpl();
		URI uri1 = URI.createURI("testsrc/resFile2.metamodel");
		Resource r1 = rs1.getResource(uri1, true);
		EObject o1 = r1.getContents().get(0);
		r1.getContents().clear();
		r1.getContents().add(result);
		try {
			r1.save(null);
		} catch (IOException e) {
			
		}
		
	}*/

	public void testTestCaseProduct() {
		List<List<Integer>> listOfLists = new LinkedList<List<Integer>>();
		Map<Integer, List<Integer>> matchings = new HashMap<Integer, List<Integer>>();
		List<Integer> n1 = new LinkedList<Integer>();
		n1.add(11);
		List<Integer> n2 = new LinkedList<Integer>();
		n2.add(21);
		n2.add(22);
		List<Integer> n3 = new LinkedList<Integer>();
		n3.add(31);
		n3.add(32);
		n3.add(33);
		matchings.put(1, n1);
		matchings.put(2, n2);
		matchings.put(3, n3);
	
		List<Integer> start = new LinkedList<Integer>();
		listOfLists.add(start);
		for (List<Integer> l : matchings.values()) {
			if (l.size() == 1) {
				for (List<Integer> l1 : listOfLists) {
					l1.add(l.get(0));
				}
			} else if (l.size() > 1) {
				List<List<Integer>> toAdd = new LinkedList<List<Integer>>();
				for (Integer gm : l) {
					if (gm == l.get(l.size() - 1)) {
						for (List<Integer> l1 : listOfLists) {
							l1.add(gm);
						}
					} else {
						for (List<Integer> l1 : listOfLists) {
							List<Integer> sublist = new LinkedList<Integer>();
							for (Integer gm1 : l1) {
								sublist.add(new Integer(gm1));
							}
							sublist.add(gm);
							toAdd.add(sublist);
						}
					}
				}
				listOfLists.addAll(toAdd);
			}
		}
		for (List<Integer> l : listOfLists) {
			System.out.println(l);
		}
		System.out.println("end");
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
