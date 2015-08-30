package org.eclipse.emf.henshin.invcheck;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartPartialMatcher;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.RuleApplication;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationFactory;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;


public class IsomorphicPartPartialMatcherTest extends junit.framework.TestCase {

	private Graph lhs1;	
	private Graph rhs1;
	private Node rhs1n2;	
	private Node rhs2n2;
	private Node tgp1n1;
	private Node tgp2n1;
	private Edge tgp1e1;
	private Node tgp1n2;
	private Match initialMatching1;
	private Match initialMatching2;
	
	
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
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("rear");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("rear", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("front");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("front", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("go");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("go", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("next");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("next", et);
		et = SamtypegraphFactory.eINSTANCE.createEdgeType();
		et.setName("on");
		et.setTypeGraph(tg);
		et.setDirection(EdgeDirection.DIRECTED);
		edgeTypes.put("on", et);
		return tg;
	}
		
	private Graph buildSpecialPattern1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC",  ItemType.PATTERN, nac);
		e2.setSource(n2); e2.setTarget(n3);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildSpecialPattern2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac1 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		NegativeApplicationCondition nac2 = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, nac1);
		Node n4 = buildNode("b", "B",  ItemType.PATTERN, nac2);
		Node n5 = buildNode("c", "C",  ItemType.PATTERN, nac2);		
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC",  ItemType.PATTERN, nac1);
		e2.setSource(n2); e2.setTarget(n3);
		Edge e3 = buildEdge("AB",  ItemType.PATTERN, nac2);
		e3.setSource(n1); e3.setTarget(n4);
		Edge e4 = buildEdge("BC",  ItemType.PATTERN, nac2);
		e4.setSource(n4); e4.setTarget(n5);
		
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		nacs.add(nac1);
		nacs.add(nac2);
		GraphCondition gc = InvariantCheckerUtil.createNegatedConditions(nacs);
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
		
		//Node shadow1 = SamgraphFactory.eINSTANCE.createNode();
		//((PatternNode) n1).setSameInProp(shadow1);
		
		Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(n1);		
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		gc.getAnnotations().add(an);
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildSpecialHost2() {
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
		
		/*Node shadow1 = SamgraphFactory.eINSTANCE.createNode();
		((PatternNode) n1).setSameInProp(shadow1);
		Node shadow2 = SamgraphFactory.eINSTANCE.createNode();
		((PatternNode) n2).setSameInProp(shadow2);*/
		
		Annotation an1 = SamannotationFactory.eINSTANCE.createAnnotation();
		an1.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an1.setTarget(n1);
		Annotation an2 = SamannotationFactory.eINSTANCE.createAnnotation();
		an2.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an2.setTarget(n2);
				
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);
		gc.getAnnotations().add(an1);
		gc.getAnnotations().add(an2);
		((RuleGraph) result).setCondition(gc);		
		
		return result;
	}
	
	private Graph buildTGP1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		tgp1n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		tgp1n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		//Node n3 = buildNode("b", "B",  ItemType.PATTERN, nac);
		//Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac);
		tgp1e1 = buildEdge("AB",  ItemType.PATTERN, result);
		tgp1e1.setSource(tgp1n1); tgp1e1.setTarget(tgp1n2);
		//Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac);
		//e2.setSource(tgp1n1); e2.setTarget(n3);
		//Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac);
		//e3.setSource(n3); e3.setTarget(n4);
		
		//GraphCondition gc = InvariantCheckingUtil.createNegatedCondition(nac);		
		//((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	private Graph buildPattern1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);		
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, nac);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, nac);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC",  ItemType.PATTERN, nac);
		e2.setSource(n2); e2.setTarget(n3);
				
		((PatternNode) tgp1n1).setSameInProp(n1);		
		initialMatching1.getNodeMatching().put(n1, tgp1n1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	private Graph buildSGP1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		
		Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, nac);
		e3.setSource(n2); e3.setTarget(n4);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
		
	private RuleGraph buildRHS1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.CREATED, result);
		rhs1n2 = buildNode("b", "B",  ItemType.PRESERVED, result);
		
		Edge e1 = buildEdge("AB",  ItemType.CREATED, result);		
		e1.setSource(n1); e1.setTarget(rhs1n2);
		
		((PatternNode) tgp1n1).setSameInRule(n1);
		((PatternNode) tgp1n2).setSameInRule(rhs1n2);
		((PatternEdge) tgp1e1).setSameInRule(e1);
		
		return result;
	}
	
	private RuleGraph buildLHS1() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		Node n2 = buildNode("b", "B",  ItemType.PRESERVED, result);
		((PreservedNode) n2).setRefInRule((PreservedNode) rhs1n2);
		
		return result;
	}
	
	private GraphRule buildRule1() {
		GraphRule result = SamrulesFactory.eINSTANCE.createGraphRule();
		result.setRight(buildRHS1());
		result.setLeft(buildLHS1());
		return result;
	}
	
	private Graph buildTGP2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		tgp2n1 = buildNode("a", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, result);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, result);
		//Node n4 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, result);
		e1.setSource(tgp2n1); e1.setTarget(n2);
		//Edge e2 = buildEdge("AB",  ItemType.PATTERN, nac);
		//e2.setSource(tgp1n1); e2.setTarget(n3);
		Edge e3 = buildEdge("BC",  ItemType.PATTERN, result);
		e3.setSource(n2); e3.setTarget(n3);
		
		//GraphCondition gc = InvariantCheckingUtil.createNegatedCondition(nac);		
		//((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	private Graph buildPattern2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.PATTERN, result);		
		Node n2 = buildNode("b", "B",  ItemType.PATTERN, nac);
		Node n3 = buildNode("c", "C",  ItemType.PATTERN, nac);
		Edge e1 = buildEdge("AB",  ItemType.PATTERN, nac);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("BC",  ItemType.PATTERN, nac);
		e2.setSource(n2); e2.setTarget(n3);
				
		((PatternNode) tgp2n1).setSameInProp(n1);		
		initialMatching2.getNodeMatching().put(n1, tgp2n1);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	
	
	private RuleGraph buildRHS2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("a", "A",  ItemType.CREATED, result);
		rhs2n2 = buildNode("b", "B",  ItemType.PRESERVED, result);
		
		Edge e1 = buildEdge("AB",  ItemType.CREATED, result);		
		e1.setSource(n1); e1.setTarget(rhs2n2);		
		
		return result;
	}
	
	private RuleGraph buildLHS2() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		
		Node n2 = buildNode("b", "B",  ItemType.PRESERVED, result);
		((PreservedNode) n2).setRefInRule((PreservedNode) rhs2n2);
		
		return result;
	}
	
	private GraphRule buildRule2() {
		GraphRule result = SamrulesFactory.eINSTANCE.createGraphRule();
		result.setRight(buildRHS2());
		result.setLeft(buildLHS2());
		return result;
	}
	
	public void testLLF1() {
		Graph pattern = buildSpecialPattern1();
		Graph host = buildSpecialHost1();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartPartialMatcher ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
	}

	public void testLLF2() {
		Graph pattern = buildSpecialPattern1();
		Graph host = buildSpecialHost2();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartPartialMatcher ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		assertNull(ipm.getNextMatching());
	}
	
	public void testLLF3() {
		Graph pattern = buildSpecialPattern2();
		Graph host = buildSpecialHost1();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartPartialMatcher ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		assertNull(ipm.getNextMatching());
	}
	
	
	public void testLLF4() {
		Graph pattern = buildSpecialPattern2();
		Graph host = buildSpecialHost2();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartPartialMatcher ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		assertNull(ipm.getNextMatching());
	}
	
	public void testLLF5() {
		initialMatching1 = SamtraceFactory.eINSTANCE.createMatch();
		
		Graph tgp = buildTGP1();
		Graph sgp = buildSGP1();
		GraphRule rule = buildRule1();
		Graph pattern = buildPattern1();
		
		
		NACTranslator nacT = new NACTranslator();
		nacT.setMergedGraph((RuleGraph) tgp);
		nacT.setPattern(pattern);
		nacT.setInitialMatching(initialMatching1);
		Graph translated = nacT.partialTranslate(rule);
		
		RuleApplication rA = new RuleApplication();
		Graph result = rA.reverseRuleApplication(translated, rule);
		
		IsomorphicPartPartialMatcher ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(result);
		ipm.setCurrentSubGraph(graphToSubgraph(sgp));
		ipm.setPattern(sgp);
		Match m = ipm.getNextMatching();
		assertCorrectMatch(m);
		
		ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(sgp);
		ipm.setCurrentSubGraph(graphToSubgraph(result));
		ipm.setPattern(result);
		m = ipm.getNextMatching();
		assertCorrectMatch(m);
		
	}
	
	private Graph buildPattern3() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("t1", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("t2", "A",  ItemType.PATTERN, result);
		Node n3 = buildNode("s1", "B",  ItemType.PATTERN, result);
		Node n4 = buildNode("s2", "B",  ItemType.PATTERN, result);
		Node n5 = buildNode("dc", "C",  ItemType.PATTERN, nac);
		
		Edge e1 = buildEdge("AA",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("on",  ItemType.PATTERN, result);
		e2.setSource(n3); e2.setTarget(n1);
		Edge e3 = buildEdge("on",  ItemType.PATTERN, result);
		e3.setSource(n4); e3.setTarget(n2);
		Edge e4 = buildEdge("go",  ItemType.PATTERN, result);
		e4.setSource(n3); e4.setTarget(n2);
		Edge e5 = buildEdge("rear",  ItemType.PATTERN, nac);
		e5.setSource(n3); e5.setTarget(n5);
		Edge e6 = buildEdge("front",  ItemType.PATTERN, nac);
		e6.setSource(n4); e6.setTarget(n5);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);
		
		return result;
	}
	
	private Graph buildHostGraph3() {
		RuleGraph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		NegativeApplicationCondition nac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
		Node n1 = buildNode("t1", "A",  ItemType.PATTERN, result);
		Node n2 = buildNode("t2", "A",  ItemType.PATTERN, result);
		Node n3 = buildNode("s1", "B",  ItemType.PATTERN, result);
		Node n4 = buildNode("s2", "B",  ItemType.PATTERN, result);
		Node n5 = buildNode("dc", "C",  ItemType.PATTERN, nac);
		
		Edge e1 = buildEdge("AA",  ItemType.PATTERN, result);
		e1.setSource(n1); e1.setTarget(n2);
		Edge e2 = buildEdge("on",  ItemType.PATTERN, result);
		e2.setSource(n3); e2.setTarget(n1);
		Edge e3 = buildEdge("on",  ItemType.PATTERN, result);
		e3.setSource(n4); e3.setTarget(n2);
		Edge e4 = buildEdge("go",  ItemType.PATTERN, result);
		e4.setSource(n3); e4.setTarget(n2);
		Edge e5 = buildEdge("next",  ItemType.PATTERN, result);
		e5.setSource(n4); e5.setTarget(n1);
		Edge e6 = buildEdge("rear",  ItemType.PATTERN, nac);
		e6.setSource(n3); e6.setTarget(n5);
		Edge e7 = buildEdge("front",  ItemType.PATTERN, nac);
		e7.setSource(n4); e7.setTarget(n5);
		
		GraphCondition gc = InvariantCheckerUtil.createNegatedCondition(nac);		
		((RuleGraph) result).setCondition(gc);
		
		Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(n1);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(n2);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(n3);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(n4);
		gc.getAnnotations().add(an);
		
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(e1);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(e2);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(e3);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(e4);
		gc.getAnnotations().add(an);
		an = SamannotationFactory.eINSTANCE.createAnnotation();
		an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
		an.setTarget(e5);
		gc.getAnnotations().add(an);
		
		return result;
	}
	
	public void testLLF6() {
		Graph pattern = buildPattern3();
		Graph host = buildHostGraph3();		
		Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(pattern);
		IsomorphicPartPartialMatcher ipm = new IsomorphicPartPartialMatcher();
		ipm.setHostGraph(host);
		ipm.setCurrentSubGraph(subgraph);
		ipm.setPattern(pattern);
		
		assertCorrectMatch(ipm.getNextMatching());
	}
	
	public void testTranslate1() {
		initialMatching2 = SamtraceFactory.eINSTANCE.createMatch();
		
		Graph tgp = buildTGP2();
		Graph pattern = buildPattern2();
		GraphRule rule = buildRule2();
		
		NACTranslator nacT = new NACTranslator();
		nacT.setMergedGraph((RuleGraph) tgp);
		nacT.setPattern(pattern);
		nacT.setInitialMatching(initialMatching2);
		Graph translated = nacT.partialTranslate(rule);
		
		assertNull(translated);
		
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
