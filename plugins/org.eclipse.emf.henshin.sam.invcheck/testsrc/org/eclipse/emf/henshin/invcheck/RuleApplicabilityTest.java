package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.adapter.GCNACAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.RuleApplication;
import org.eclipse.emf.henshin.sam.invcheck.filter.ContainsGraphRuleHelper;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;


public class RuleApplicabilityTest extends TestCase {

	private Graph		tgp1;
	private Graph		sgp1;
	private RuleGraph	lhs1;
	private RuleGraph	rhs1;
	private Graph		res1;
	private GraphRule	rule1;
	private Node		lhs1n1;
	private Node		lhs1n2;
	private Node		lhs1n3;
	private Edge		lhs1e1;
	private Edge		lhs1e2;
	private Edge		lhs1e3;
	private NegativeApplicationCondition	lhs1nac1;
	private Node		rhs1n1;
	private Node		rhs1n2;
	private Node		rhs1n3;
	private Edge		rhs1e1;
	private Edge		rhs1e2;
	private Node		tgp1n1;
	private Node		tgp1n2;
	private Node		tgp1n3;
	private Node		tgp1n4;
	private Edge		tgp1e1;
	private Edge		tgp1e2;
	private Edge		tgp1e3;
	private NegativeApplicationCondition	tgp1nac1;
	
	private Graph		tgp2;
	private Graph		sgp2;
	private RuleGraph	lhs2;
	private RuleGraph	rhs2;
	private Graph		res2;
	private GraphRule	rule2;
	private Node		lhs2n1;
	private Node		lhs2n2;
	private Edge		lhs2e1;	
	private Node		rhs2n1;
	private Node		rhs2n2;
	private Edge		rhs2e1;
	private Node		tgp2n1;
	private Node		tgp2n2;
	private Node		tgp2n3;
	private Node		tgp2n4;
	private Edge		tgp2e1;
	private Edge		tgp2e2;
	private Edge		tgp2e3;
	private Node		res2n1;
	private Node		res2n2;
	private Node		res2n3;
	private Node		res2n4;
	private Edge		res2e1;
	private Edge		res2e2;
	private Edge		res2e3;
	private Edge		res2e4;
	private NegativeApplicationCondition res2nac1;
	
	private Graph		tgp3;
	private Graph		sgp3;
	private RuleGraph	lhs3;
	private RuleGraph	rhs3;
	private Graph		res3;
	private GraphRule	rule3;
	private Node		lhs3n1;
	private Node		lhs3n2;
	private Node		lhs3n3;
	private Node		lhs3n4;
	private Node		lhs3n5;
	private Edge		lhs3e1;
	private Edge		lhs3e2;
	private Edge		lhs3e3;
	private Edge		lhs3e4;
	private NegativeApplicationCondition	lhs3nac1;
	private NegativeApplicationCondition	lhs3nac2;
	private Node		rhs3n1;
	private Node		rhs3n2;
	private Node		rhs3n3;
	private Edge		rhs3e1;
	private Edge		rhs3e2;
	private Node		tgp3n1;
	private Node		tgp3n2;
	private Node		tgp3n3;
	private Node		tgp3n4;
	private Node		tgp3n5;
	private Edge		tgp3e1;
	private Edge		tgp3e2;
	private Edge		tgp3e3;
	private Edge		tgp3e4;
	private NegativeApplicationCondition	tgp3nac1;
	private Node		res3n1;
	private Node		res3n2;
	private Node		res3n3;
	private Node		res3n4;
	private Node		res3n5;
	private Node		res3n6;
	private Node		res3n7;
	private Edge		res3e1;
	private Edge		res3e2;
	private Edge		res3e3;
	private Edge		res3e4;
	private Edge		res3e5;
	private Edge		res3e6;
	private Edge		res3e7;
	private NegativeApplicationCondition	res3nac1;
	private NegativeApplicationCondition	res3nac2;
	private NegativeApplicationCondition	res3nac3;
	private NegativeApplicationCondition	res3nac4;
	
	private Graph		tgp4;
	private Graph		sgp4;
	private RuleGraph	lhs4;
	private RuleGraph	rhs4;
	private Graph		res4;
	private GraphRule	rule4;
	private Node		lhs4n1;
	private Node		lhs4n2;
	private Node		lhs4n3;
	private Node		lhs4n4;
	private Node		lhs4n5;
	private Node		lhs4n6;
	private Node		lhs4n7;
	private Node		lhs4n8;
	private Edge		lhs4e1;
	private Edge		lhs4e2;
	private Edge		lhs4e3;
	private Edge		lhs4e4;
	private Edge		lhs4e5;
	private Edge		lhs4e6;
	private NegativeApplicationCondition	lhs4nac1;
	private NegativeApplicationCondition	lhs4nac2;
	private NegativeApplicationCondition	lhs4nac3;
	private Node		rhs4n1;
	private Node		rhs4n2;
	private Node		rhs4n3;
	private Node		rhs4n4;
	private Node		rhs4n5;
	private Edge		rhs4e1;
	private Edge		rhs4e2;
	private Edge		rhs4e3;
	private Edge		rhs4e4;
	private Node		tgp4n1;
	private Node		tgp4n2;
	private Node		tgp4n3;
	private Node		tgp4n4;
	private Node		tgp4n5;
	private Node		tgp4n6;
	private Node		tgp4n7;
	private Edge		tgp4e1;
	private Edge		tgp4e2;
	private Edge		tgp4e3;
	private Edge		tgp4e4;
	private Edge		tgp4e5;
	private Edge		tgp4e6;
	private Edge		tgp4e7;
	private NegativeApplicationCondition	tgp4nac1;
	private NegativeApplicationCondition	tgp4nac2;
	private Node		res4n1;
	private Node		res4n2;
	private Node		res4n3;
	private Node		res4n4;
	private Node		res4n5;
	private Node		res4n6;
	private Node		res4n7;
	private Node		res4n8;
	private Node		res4n9;
	private Node		res4n10;
	private Edge		res4e1;
	private Edge		res4e2;
	private Edge		res4e3;
	private Edge		res4e4;
	private Edge		res4e5;
	private Edge		res4e6;
	private Edge		res4e7;
	private Edge		res4e8;
	private Edge		res4e9;
	private NegativeApplicationCondition	res4nac1;
	private NegativeApplicationCondition	res4nac2;
	private NegativeApplicationCondition	res4nac3;
	private NegativeApplicationCondition	res4nac4;
	private NegativeApplicationCondition	res4nac5;

	private String[] types = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
	private Map<String, NodeType> nodeTypes;
	private Map<String, EdgeType> edgeTypes;
	private TypeGraph typeGraph = buildTypeGraph();
	
	public enum ItemType {
		CREATED, DELETED, PRESERVED, PATTERN, NONE
	}
	
	private Map<AnnotatedElem, AnnotatedElem> refItems = new HashMap<AnnotatedElem, AnnotatedElem>();
	

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
	
	private void buildLhs1() {
		lhs1 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		lhs1nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		lhs1nac1.setGraph(lhs1);
		lhs1n1 = buildNode("a", "A", ItemType.PRESERVED, lhs1);
		lhs1n2 = buildNode("dNac", "D", ItemType.NONE, lhs1nac1);
		lhs1n3 = buildNode("eNac", "E", ItemType.NONE, lhs1nac1);
		lhs1e1 = buildEdge("AD", ItemType.NONE, lhs1nac1);
		lhs1e1.setSource(lhs1n1); lhs1e1.setTarget(lhs1n2);
		lhs1e2 = buildEdge("DE", ItemType.NONE, lhs1nac1);
		lhs1e2.setSource(lhs1n2); lhs1e2.setTarget(lhs1n3);
		lhs1e3 = buildEdge("EA", ItemType.NONE, lhs1nac1);
		lhs1e3.setSource(lhs1n3); lhs1e3.setTarget(lhs1n1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(lhs1nac1);
		lhs1.setCondition(gc);
	}
	
	private void buildRhs1() {
		rhs1 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		rhs1n1 = buildNode("a", "A", ItemType.PRESERVED, rhs1);
		rhs1n2 = buildNode("b", "B", ItemType.CREATED, rhs1);
		rhs1n3 = buildNode("c", "C", ItemType.CREATED, rhs1);
		rhs1e1 = buildEdge("AB", ItemType.CREATED, rhs1);
		rhs1e1.setSource(rhs1n1); rhs1e1.setTarget(rhs1n2);
		rhs1e2 = buildEdge("BC", ItemType.CREATED, rhs1);
		rhs1e2.setSource(rhs1n2); rhs1e2.setTarget(rhs1n3);
		
		((PreservedNode) rhs1n1).setRefInRule((PreservedNode) lhs1n1);
	}
	
	private void buildTgp1() {
		tgp1 = SamrulesFactory.eINSTANCE.createRuleGraph();
		tgp1nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		tgp1nac1.setGraph(tgp1);
		tgp1n1 = buildNode("a", "A", ItemType.PATTERN, tgp1);
		tgp1n2 = buildNode("b", "B", ItemType.PATTERN, tgp1);
		tgp1n3 = buildNode("c", "C", ItemType.PATTERN, tgp1);
		tgp1n4 = buildNode("dNac", "D", ItemType.NONE, tgp1nac1);		
		tgp1e1 = buildEdge("AB", ItemType.PATTERN, tgp1);
		tgp1e1.setSource(tgp1n1); tgp1e1.setTarget(tgp1n2);
		tgp1e2 = buildEdge("BC", ItemType.PATTERN, tgp1);
		tgp1e2.setSource(tgp1n2); tgp1e2.setTarget(tgp1n3);
		tgp1e3 = buildEdge("CD", ItemType.NONE, tgp1nac1);
		tgp1e3.setSource(tgp1n3); tgp1e3.setTarget(tgp1n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(tgp1nac1);
		((RuleGraph) tgp1).setCondition(gc);
		
		((PatternNode) tgp1n1).setSameInRule(rhs1n1);
		((PatternNode) tgp1n2).setSameInRule(rhs1n2);
		((PatternNode) tgp1n3).setSameInRule(rhs1n3);
		((PatternEdge) tgp1e1).setSameInRule(rhs1e1);
		((PatternEdge) tgp1e2).setSameInRule(rhs1e2);		
	}
	
	private void buildRes1 () {
		res1 = lhs1;
	}

	private void buildLhs2() {
		lhs2 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		lhs2n1 = buildNode("a", "A",  ItemType.DELETED, lhs2);
		lhs2n2 = buildNode("b", "B",  ItemType.PRESERVED, lhs2);		
		lhs2e1 = buildEdge("AB",  ItemType.DELETED, lhs2);
		lhs2e1.setSource(lhs2n1); lhs2e1.setTarget(lhs2n2);
	}
	
	private void buildRhs2() {
		rhs2 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		rhs2n1 = buildNode("b", "B",  ItemType.PRESERVED, rhs2);
		rhs2n2 = buildNode("c", "C",  ItemType.CREATED, rhs2);		
		rhs2e1 = buildEdge("BC",  ItemType.CREATED, rhs2);
		rhs2e1.setSource(rhs2n1); rhs2e1.setTarget(rhs2n2);		
		
		((PreservedNode) rhs2n1).setRefInRule((PreservedNode) lhs2n2);
	}
	
	private void buildTgp2() {
		tgp2 = SamgraphFactory.eINSTANCE.createGraph();
		tgp2n1 = buildNode("a", "A", ItemType.PATTERN, tgp2);
		tgp2n2 = buildNode("b", "B", ItemType.PATTERN, tgp2);
		tgp2n3 = buildNode("c", "C", ItemType.PATTERN, tgp2);
		tgp2n4 = buildNode("d", "D", ItemType.PATTERN, tgp2);		
		tgp2e1 = buildEdge("AB", ItemType.PATTERN, tgp2);
		tgp2e1.setSource(tgp2n1); tgp2e1.setTarget(tgp2n2);
		tgp2e2 = buildEdge("BC", ItemType.PATTERN, tgp2);
		tgp2e2.setSource(tgp2n2); tgp2e2.setTarget(tgp2n3);
		tgp2e3 = buildEdge("CD", ItemType.PATTERN, tgp2);
		tgp2e3.setSource(tgp2n3); tgp2e3.setTarget(tgp2n4);
				
		((PatternNode) tgp2n2).setSameInRule(rhs2n1);
		((PatternNode) tgp2n3).setSameInRule(rhs2n2);
		((PatternEdge) tgp2e2).setSameInRule(rhs2e1);				
	}
	
	private void buildRes2() {
		res2 = SamgraphFactory.eINSTANCE.createGraph();
		res2n1 = buildNode("a", "A", ItemType.PATTERN, res2);
		res2n2 = buildNode("b", "B", ItemType.PATTERN, res2);
		res2n3 = buildNode("c", "C", ItemType.PATTERN, res2);
		res2n4 = buildNode("d", "D", ItemType.PATTERN, res2);		
		res2e1 = buildEdge("AB", ItemType.PATTERN, res2);
		res2e1.setSource(res2n1); res2e1.setTarget(res2n2);
		res2e2 = buildEdge("BC", ItemType.PATTERN, res2);
		res2e2.setSource(res2n2); res2e2.setTarget(res2n3);
		res2e3 = buildEdge("AD", ItemType.PATTERN, res2);
		res2e3.setSource(res2n1); res2e3.setTarget(res2n4);
				
		((PatternNode) res2n2).setSameInRule(res2n1);
		((PatternNode) res2n3).setSameInRule(res2n2);
		((PatternEdge) res2e2).setSameInRule(res2e1);				
	}
	
	private void buildAlternativeRes2() {
		res2 = SamrulesFactory.eINSTANCE.createRuleGraph();
		res2n1 = buildNode("a", "A", ItemType.PATTERN, res2);
		res2n2 = buildNode("b", "B", ItemType.PATTERN, res2);
		res2n3 = buildNode("c", "C", ItemType.PATTERN, res2);
		res2n4 = buildNode("d", "D", ItemType.PATTERN, res2);		
		res2e1 = buildEdge("AB", ItemType.PATTERN, res2);
		res2e1.setSource(res2n1); res2e1.setTarget(res2n2);
		res2e2 = buildEdge("BC", ItemType.PATTERN, res2);
		res2e2.setSource(res2n2); res2e2.setTarget(res2n3);
		res2e3 = buildEdge("AD", ItemType.PATTERN, res2);
		res2e3.setSource(res2n1); res2e3.setTarget(res2n4);
		
		res2nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res2e4 = buildEdge("AC", ItemType.PATTERN, res2nac1);
		res2e4.setSource(res2n1); res2e4.setTarget(res2n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(res2nac1);
		((RuleGraph) res2).setCondition(gc);
		
		((PatternNode) res2n2).setSameInRule(res2n1);
		((PatternNode) res2n3).setSameInRule(res2n2);
		((PatternEdge) res2e2).setSameInRule(res2e1);				
	}
	
	private void buildLhs3() {
		lhs3 = SamrulesFactory.eINSTANCE.createRuleGraph();
		lhs3nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		lhs3nac1.setGraph(lhs3);
		lhs3nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		lhs3nac2.setGraph(lhs3);
		lhs3n1 = buildNode("a", "A", ItemType.PRESERVED, lhs3);
		lhs3n2 = buildNode("b", "B", ItemType.DELETED, lhs3);
		lhs3n3 = buildNode("eNac", "E", ItemType.NONE, lhs3nac1);
		lhs3n4 = buildNode("fNac", "F", ItemType.NONE, lhs3nac1);
		lhs3n5 = buildNode("gNac", "G", ItemType.NONE, lhs3nac2);
		lhs3e1 = buildEdge("AB", ItemType.DELETED, lhs3);
		lhs3e1.setSource(lhs3n1); lhs3e1.setTarget(lhs3n2);
		lhs3e2 = buildEdge("AE", ItemType.NONE, lhs3nac1);
		lhs3e2.setSource(lhs3n1); lhs3e2.setTarget(lhs3n3);
		lhs3e3 = buildEdge("EF", ItemType.NONE, lhs3nac1);
		lhs3e3.setSource(lhs3n3); lhs3e3.setTarget(lhs3n4);
		lhs3e4 = buildEdge("BG", ItemType.NONE, lhs3nac2);
		lhs3e4.setSource(lhs3n2); lhs3e4.setTarget(lhs3n5);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(lhs3nac1);
		nacs.add(lhs3nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) lhs3).setCondition(gc);
	}
	
	private void buildRhs3() {
		rhs3 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		rhs3n1 = buildNode("a", "A", ItemType.PRESERVED, rhs3);
		rhs3n2 = buildNode("c", "C", ItemType.CREATED, rhs3);
		rhs3n3 = buildNode("d", "D", ItemType.CREATED, rhs3);
		rhs3e1 = buildEdge("AC", ItemType.CREATED, rhs3);
		rhs3e1.setSource(rhs3n1); rhs3e1.setTarget(rhs3n2);
		rhs3e2 = buildEdge("CD", ItemType.CREATED, rhs3);
		rhs3e2.setSource(rhs3n2); rhs3e2.setTarget(rhs3n3);
		
		((PreservedNode) rhs3n1).setRefInRule((PreservedNode) lhs3n1);
	}
	
	private void buildTgp3() {
		tgp3 = SamrulesFactory.eINSTANCE.createRuleGraph();
		tgp3nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		tgp3nac1.setGraph(tgp3);
		tgp3n1 = buildNode("a", "A", ItemType.PATTERN, tgp3);		
		tgp3n2 = buildNode("c", "C", ItemType.PATTERN, tgp3);
		tgp3n3 = buildNode("d", "D", ItemType.PATTERN, tgp3);
		tgp3n4 = buildNode("i", "I", ItemType.PATTERN, tgp3);
		tgp3n5 = buildNode("hNac", "H", ItemType.PATTERN, tgp3nac1);		
		tgp3e1 = buildEdge("AC", ItemType.PATTERN, tgp3);
		tgp3e1.setSource(tgp3n1); tgp3e1.setTarget(tgp3n2);
		tgp3e2 = buildEdge("CD", ItemType.PATTERN, tgp3);
		tgp3e2.setSource(tgp3n2); tgp3e2.setTarget(tgp3n3);
		tgp3e3 = buildEdge("IA", ItemType.PATTERN, tgp3);
		tgp3e3.setSource(tgp3n4); tgp3e3.setTarget(tgp3n1);
		tgp3e4 = buildEdge("AH", ItemType.PATTERN, tgp3nac1);
		tgp3e4.setSource(tgp3n1); tgp3e4.setTarget(tgp3n5);
				
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(tgp3nac1);
		((RuleGraph) tgp3).setCondition(gc);
		
		((PatternNode) tgp3n1).setSameInRule(rhs3n1);
		((PatternNode) tgp3n2).setSameInRule(rhs3n2);
		((PatternNode) tgp3n3).setSameInRule(rhs3n3);
		((PatternEdge) tgp3e1).setSameInRule(rhs3e1);
		((PatternEdge) tgp3e2).setSameInRule(rhs3e2);		
	}
	
	private void buildRes3 () {
		res3 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		res3nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res3nac1.setGraph(res3);
		res3nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res3nac2.setGraph(res3);
		res3nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res3nac3.setGraph(res3);
		res3nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res3nac4.setGraph(res3);
		res3n1 = buildNode("a", "A", ItemType.PATTERN, res3);
		res3n2 = buildNode("b", "B", ItemType.PATTERN, res3);
		res3n3 = buildNode("eNac", "E", ItemType.PATTERN, res3nac1);
		res3n4 = buildNode("fNac", "F", ItemType.PATTERN, res3nac1);
		res3n5 = buildNode("gNac", "G", ItemType.PATTERN, res3nac2);
		res3n6 = buildNode("hNac", "H", ItemType.PATTERN, res3nac3);
		res3n7 = buildNode("i", "I", ItemType.PATTERN, res3);
		res3e1 = buildEdge("AB", ItemType.PATTERN, res3);
		res3e1.setSource(res3n1); res3e1.setTarget(res3n2);
		res3e2 = buildEdge("AE", ItemType.PATTERN, res3nac1);
		res3e2.setSource(res3n1); res3e2.setTarget(res3n3);
		res3e3 = buildEdge("EF", ItemType.PATTERN, res3nac1);
		res3e3.setSource(res3n3); res3e3.setTarget(res3n4);
		res3e4 = buildEdge("BG", ItemType.PATTERN, res3nac2);
		res3e4.setSource(res3n2); res3e4.setTarget(res3n5);
		res3e5 = buildEdge("AH", ItemType.PATTERN, res3nac3);
		res3e5.setSource(res3n1); res3e5.setTarget(res3n6);
		res3e6 = buildEdge("IA", ItemType.PATTERN, res3);
		res3e6.setSource(res3n7); res3e6.setTarget(res3n1);
		res3e7 = buildEdge("AA", ItemType.PATTERN, res3nac4);
		res3e7.setSource(res3n1); res3e7.setTarget(res3n1);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(res3nac1);
		nacs.add(res3nac2);
		nacs.add(res3nac3);
		nacs.add(res3nac4);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) res3).setCondition(gc);
	}

	private void buildLhs4() {
		lhs4 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		lhs4nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		lhs4nac1.setGraph(lhs4);
		lhs4nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		lhs4nac2.setGraph(lhs4);
		lhs4nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		lhs4nac3.setGraph(lhs4);
		lhs4n1 = buildNode("a", "A", ItemType.PRESERVED, lhs4);
		lhs4n2 = buildNode("b", "B", ItemType.DELETED, lhs4);
		lhs4n3 = buildNode("eNac", "E", ItemType.NONE, lhs4nac1);
		lhs4n4 = buildNode("fNac", "F", ItemType.NONE, lhs4nac1);
		lhs4n5 = buildNode("gNac", "G", ItemType.NONE, lhs4nac2);
		lhs4n6 = buildNode("e", "E", ItemType.PRESERVED, lhs4);
		lhs4n7 = buildNode("f", "F", ItemType.PRESERVED, lhs4);
		lhs4n8 = buildNode("gNac2", "G", ItemType.NONE, lhs4nac3);
		lhs4e1 = buildEdge("AB", ItemType.DELETED, lhs4);
		lhs4e1.setSource(lhs4n1); lhs4e1.setTarget(lhs4n2);
		lhs4e2 = buildEdge("AE", ItemType.NONE, lhs4nac1);
		lhs4e2.setSource(lhs4n1); lhs4e2.setTarget(lhs4n3);
		lhs4e3 = buildEdge("EF", ItemType.NONE, lhs4nac1);
		lhs4e3.setSource(lhs4n3); lhs4e3.setTarget(lhs4n4);
		lhs4e4 = buildEdge("BG", ItemType.NONE, lhs4nac2);
		lhs4e4.setSource(lhs4n2); lhs4e4.setTarget(lhs4n5);
		lhs4e5 = buildEdge("EF", ItemType.PRESERVED, lhs4);
		lhs4e5.setSource(lhs4n6); lhs4e5.setTarget(lhs4n7);
		lhs4e6 = buildEdge("EG", ItemType.NONE, lhs4nac3);
		lhs4e6.setSource(lhs4n6); lhs4e6.setTarget(lhs4n8);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(lhs4nac1);
		nacs.add(lhs4nac2);
		nacs.add(lhs4nac3);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) lhs4).setCondition(gc);		
		
	}
	
	private void buildRhs4() {
		rhs4 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		rhs4n1 = buildNode("a", "A", ItemType.PRESERVED, rhs4);
		rhs4n2 = buildNode("c", "C", ItemType.CREATED, rhs4);
		rhs4n3 = buildNode("d", "D", ItemType.CREATED, rhs4);
		rhs4n4 = buildNode("e", "E", ItemType.PRESERVED, rhs4);
		rhs4n5 = buildNode("f", "F", ItemType.PRESERVED, rhs4);
		rhs4e1 = buildEdge("AC", ItemType.CREATED, rhs4);
		rhs4e1.setSource(rhs4n1); rhs4e1.setTarget(rhs4n2);
		rhs4e2 = buildEdge("CD", ItemType.CREATED, rhs4);
		rhs4e2.setSource(rhs4n2); rhs4e2.setTarget(rhs4n3);
		rhs4e3 = buildEdge("DE", ItemType.CREATED, rhs4);
		rhs4e3.setSource(rhs4n3); rhs4e3.setTarget(rhs4n4);
		rhs4e4 = buildEdge("EF", ItemType.PRESERVED, rhs4);
		rhs4e4.setSource(rhs4n4); rhs4e4.setTarget(rhs4n5);
		
		((PreservedNode) rhs4n1).setRefInRule((PreservedNode) lhs4n1);
		((PreservedNode) rhs4n4).setRefInRule((PreservedNode) lhs4n6);
		((PreservedNode) rhs4n5).setRefInRule((PreservedNode) lhs4n7);
		((PreservedEdge) rhs4e4).setRefInRule((PreservedEdge) lhs4e5);
	}
	
	private void buildTgp4() {
		tgp4 = SamrulesFactory.eINSTANCE.createRuleGraph();
		tgp4nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		tgp4nac1.setGraph(tgp4);
		tgp4nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		tgp4nac2.setGraph(tgp4);
		tgp4n1 = buildNode("a", "A", ItemType.PATTERN, tgp4);		
		tgp4n2 = buildNode("c", "C", ItemType.PATTERN, tgp4);
		tgp4n3 = buildNode("d", "D", ItemType.PATTERN, tgp4);
		tgp4n4 = buildNode("i", "I", ItemType.PATTERN, tgp4);
		tgp4n5 = buildNode("hNac", "H", ItemType.PATTERN, tgp4nac1);
		tgp4n6 = buildNode("e", "E", ItemType.PATTERN, tgp4);
		tgp4n7 = buildNode("f", "F", ItemType.PATTERN, tgp4);
		tgp4e1 = buildEdge("AC", ItemType.PATTERN, tgp4);
		tgp4e1.setSource(tgp4n1); tgp4e1.setTarget(tgp4n2);
		tgp4e2 = buildEdge("CD", ItemType.PATTERN, tgp4);
		tgp4e2.setSource(tgp4n2); tgp4e2.setTarget(tgp4n3);
		tgp4e3 = buildEdge("IA", ItemType.PATTERN, tgp4);
		tgp4e3.setSource(tgp4n4); tgp4e3.setTarget(tgp4n1);
		tgp4e4 = buildEdge("AH", ItemType.PATTERN, tgp4nac1);
		tgp4e4.setSource(tgp4n1); tgp4e4.setTarget(tgp4n5);
		tgp4e5 = buildEdge("DE", ItemType.PATTERN, tgp4);
		tgp4e5.setSource(tgp4n3); tgp4e5.setTarget(tgp4n6);
		tgp4e6 = buildEdge("EF", ItemType.PATTERN, tgp4);
		tgp4e6.setSource(tgp4n6); tgp4e6.setTarget(tgp4n7);
		tgp4e7 = buildEdge("AE", ItemType.PATTERN, tgp4nac2);
		tgp4e7.setSource(tgp4n1); tgp4e7.setTarget(tgp4n6);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(tgp4nac1);
		nacs.add(tgp4nac2);		
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) tgp4).setCondition(gc);
		
		((PatternNode) tgp4n1).setSameInRule(rhs4n1);
		((PatternNode) tgp4n2).setSameInRule(rhs4n2);
		((PatternNode) tgp4n3).setSameInRule(rhs4n3);
		((PatternEdge) tgp4e1).setSameInRule(rhs4e1);
		((PatternEdge) tgp4e2).setSameInRule(rhs4e2);		
		
		((PatternNode) tgp4n6).setSameInRule(rhs4n4);
		((PatternNode) tgp4n7).setSameInRule(rhs4n5);
		((PatternEdge) tgp4e5).setSameInRule(rhs4e3);
		((PatternEdge) tgp4e6).setSameInRule(rhs4e4);
	}
	
	private void buildRes4 () {
		res4 = SamrulesFactory.eINSTANCE.createRuleGraph();;
		res4nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res4nac1.setGraph(res4);
		res4nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res4nac2.setGraph(res4);
		res4nac3 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res4nac3.setGraph(res4);
		res4nac4 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res4nac4.setGraph(res4);
		res4nac5 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		res4nac5.setGraph(res4);
		res4n1 = buildNode("a", "A", ItemType.PATTERN, res4);
		res4n2 = buildNode("b", "B", ItemType.PATTERN, res4);
		res4n3 = buildNode("eNac", "E", ItemType.PATTERN, res4nac1);
		res4n4 = buildNode("fNac", "F", ItemType.PATTERN, res4nac1);
		res4n5 = buildNode("gNac", "G", ItemType.PATTERN, res4nac2);
		res4n6 = buildNode("hNac", "H", ItemType.PATTERN, res4nac3);
		res4n7 = buildNode("i", "I", ItemType.PATTERN, res4);
		res4n8 = buildNode("e", "E", ItemType.PATTERN, res4);
		res4n9 = buildNode("f", "F", ItemType.PATTERN, res4);
		res4n10 = buildNode("gNac", "G", ItemType.PATTERN, res4nac4);
		res4e1 = buildEdge("AB", ItemType.PATTERN, res4);
		res4e1.setSource(res4n1); res4e1.setTarget(res4n2);
		res4e2 = buildEdge("AE", ItemType.PATTERN, res4nac1);
		res4e2.setSource(res4n1); res4e2.setTarget(res4n3);
		res4e3 = buildEdge("EF", ItemType.PATTERN, res4nac1);
		res4e3.setSource(res4n3); res4e3.setTarget(res4n4);
		res4e4 = buildEdge("BG", ItemType.PATTERN, res4nac2);
		res4e4.setSource(res4n2); res4e4.setTarget(res4n5);
		res4e5 = buildEdge("AH", ItemType.PATTERN, res4nac3);
		res4e5.setSource(res4n1); res4e5.setTarget(res4n6);
		res4e6 = buildEdge("IA", ItemType.PATTERN, res4);
		res4e6.setSource(res4n7); res4e6.setTarget(res4n1);
		res4e7 = buildEdge("EF", ItemType.PATTERN, res4);
		res4e7.setSource(res4n8); res4e7.setTarget(res4n9);
		res4e8 = buildEdge("EG", ItemType.PATTERN, res4nac4);
		res4e8.setSource(res4n8); res4e8.setTarget(res4n10);
		res4e9 = buildEdge("AE", ItemType.PATTERN, res4nac5);
		res4e9.setSource(res4n1); res4e9.setTarget(res4n8);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(res4nac1);
		nacs.add(res4nac2);
		nacs.add(res4nac3);
		nacs.add(res4nac4);
		nacs.add(res4nac5);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
		((RuleGraph) res4).setCondition(gc);
	}
	
	public void testTestCase1() {
		buildLhs1();
		buildRhs1();		
		buildRes1();
		rule1 = SamrulesFactory.eINSTANCE.createGraphRule();
		rule1.setLeft(lhs1);
		rule1.setRight(rhs1);
		
		GraphRule[] rules = {rule1};
		
		ContainsGraphRuleHelper helper = new ContainsGraphRuleHelper(rules);
		
		assertTrue(helper.findMatchingRule(-1, res1));
			
		assertContainments(lhs1);
		assertContainments(rhs1);
		assertContainments(res1);
	}
	
	public void testTestCase2() {
		buildLhs2();
		buildRhs2();
		buildRes2();
		
		rule2 = SamrulesFactory.eINSTANCE.createGraphRule();
		rule2.setLeft(lhs2);
		rule2.setRight(rhs2);		
		
		GraphRule[] rules = {rule2};
		
		ContainsGraphRuleHelper helper = new ContainsGraphRuleHelper(rules);
		
		assertFalse(helper.findMatchingRule(-1, res2));
		assertContainments(lhs2);
		assertContainments(rhs2);
		assertContainments(res2);
	}
	
	public void testTestCase2Alternative() {
		buildLhs2();
		buildRhs2();
		buildAlternativeRes2();
		
		rule2 = SamrulesFactory.eINSTANCE.createGraphRule();
		rule2.setLeft(lhs2);
		rule2.setRight(rhs2);		
		
		GraphRule[] rules = {rule2};
		
		ContainsGraphRuleHelper helper = new ContainsGraphRuleHelper(rules);
		
		assertFalse(helper.findMatchingRule(-1, res2));
		assertContainments(lhs2);
		assertContainments(rhs2);
		assertContainments(res2);
	}
	
	public void testTestCase3() {
		buildLhs3();
		buildRhs3();
		buildRes3();
		rule3 = SamrulesFactory.eINSTANCE.createGraphRule();
		rule3.setLeft(lhs3);
		rule3.setRight(rhs3);		
		
		GraphRule[] rules = {rule3};
		
		ContainsGraphRuleHelper helper = new ContainsGraphRuleHelper(rules);
		
		// at the moment, there is no matching rule due to the gluing condition.
		// see ContainsGraphRuleHelper.checkRuleMatchingApplicability()
		assertFalse(helper.findMatchingRule(-1, res3));
				
		assertContainments(lhs3);
		assertContainments(rhs3);
		assertContainments(res3);
	}
	
	public void testTestCase4() {
		buildLhs4();
		buildRhs4();
		buildRes4();
		rule4 = SamrulesFactory.eINSTANCE.createGraphRule();
		rule4.setLeft(lhs4);
		rule4.setRight(rhs4);		
	
		GraphRule[] rules = {rule4};
		
		ContainsGraphRuleHelper helper = new ContainsGraphRuleHelper(rules);
		
		// at the moment, there is no matching rule due to the gluing condition.
		// see ContainsGraphRuleHelper.checkRuleMatchingApplicability()
		assertFalse(helper.findMatchingRule(-1, res4));
		
		assertContainments(res4);
		
		assertContainments(lhs4);
		assertContainments(rhs4);
		assertContainments(res4);		
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
}