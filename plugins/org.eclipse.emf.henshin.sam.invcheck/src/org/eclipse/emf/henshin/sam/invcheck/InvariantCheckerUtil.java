package org.eclipse.emf.henshin.sam.invcheck;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;

public class InvariantCheckerUtil {
	
	public static PatternNode copyAsPattern(Node n) {
		PatternNode result = NacFactory.eINSTANCE.createPatternNode();
		result.setInstanceOf(n.getInstanceOf());
		result.setName(n.getName());
		return result;
	}
	
	public static PatternEdge copyAsPattern(Edge e) {
		PatternEdge result = NacFactory.eINSTANCE.createPatternEdge();
		result.setInstanceOf(e.getInstanceOf());
		result.setName(e.getName());
		return result;
	}
	
	public static GraphCondition createNegatedCondition(NegativeApplicationCondition nac) {
		NegatedCondition result = SamgraphconditionFactory.eINSTANCE.createNegatedCondition();
		Quantification q = SamgraphconditionFactory.eINSTANCE.createQuantification();
		result.setOperand(q);
		q.setContext(nacToGraph(nac));
		TerminationCondition tC = SamgraphconditionFactory.eINSTANCE.createTerminationCondition();
		tC.setIsTrue(true);
		q.setNesting(tC);
		result.getAnnotations().addAll(nac.getAnnotations());
		return result;
	}
	
	public static GraphCondition createNegatedConditions(Set<NegativeApplicationCondition> nacs) {
		LogicalGCCoupling result = SamgraphconditionFactory.eINSTANCE.createLogicalGCCoupling();
		result.setOperator(LogicalOperator.CONJUNCTION);
		for (NegativeApplicationCondition nac : nacs) {
			result.getOperands().add(createNegatedCondition(nac));
		}
		return result;
	}

	public static GraphCondition addNegatedCondition(GraphCondition gc, NegativeApplicationCondition nac) {
		if (nac == null) {
			return null;
		}
		if (gc == null) {
			return createNegatedCondition(nac);
		}
		GraphCondition newGC = createNegatedCondition(nac);
		if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
			((LogicalGCCoupling) gc).getOperands().add(newGC);
			return gc;
		} else if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
			LogicalGCCoupling result = SamgraphconditionFactory.eINSTANCE.createLogicalGCCoupling();
			result.setOperator(LogicalOperator.CONJUNCTION);
			result.getOperands().add(gc);
			result.getOperands().add(newGC);
			return result;
		} else {
			return null;
		}
		
	}	
	
	public static GraphCondition addNegatedConditions(GraphCondition gc, Set<NegativeApplicationCondition> nacs) {
		if (nacs == null) {
			return null;
		}
		if (gc == null) {
			return createNegatedConditions(nacs);
		}
		if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
			for (NegativeApplicationCondition nac : nacs) {
				GraphCondition newGC = createNegatedCondition(nac);
				((LogicalGCCoupling) gc).getOperands().add(newGC);
			}
			return gc;
		} else if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
			LogicalGCCoupling result = (LogicalGCCoupling) createNegatedConditions(nacs);
			result.setOperator(LogicalOperator.CONJUNCTION);
			result.getOperands().add(gc);			
			return result;
		} else {
			return null;
		}
	}
	
	private static Graph nacToGraph(NegativeApplicationCondition nac) {
		Graph result = SamgraphFactory.eINSTANCE.createGraph();
		/*for (Node n : nac.getNodes()) {
			result.getNodes().add(n);
		}
		for (Edge e : nac.getEdges()) {
			result.getEdges().add(e);
		}*/
		result.getNodes().addAll(nac.getNodes());
		result.getEdges().addAll(nac.getEdges());
		EcoreUtil.remove(nac);
		return result;
	}
	
	public static GraphCondition getHighestCondition(AnnotatedElem e) {
		Graph g = (Graph) e.eContainer();
		EObject result = null;
		EObject cont = null;
		if (g.eContainer() != null && SamgraphconditionPackage.eINSTANCE.getGraphCondition().isSuperTypeOf(g.eContainer().eClass())) {
			cont = g.eContainer();
			result = cont;
			cont = cont.eContainer();
		} else {
			return null;
		}

		while (cont != null) {
			if (cont.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				return (GraphCondition) result;
			} else if (cont.eClass() == SamrulesPackage.eINSTANCE.getRuleGraph()) {
				return (GraphCondition) result;
			} else if (cont.eClass() == SamtypegraphPackage.eINSTANCE.getTypeGraph()) {
				return (GraphCondition) result;
			} else {
				result = cont;
				cont = cont.eContainer();
			}
		}
			
		return null;
	}
	
	public static boolean isNegated(Node n) {
		if (n.eContainer().eClass() == NacPackage.eINSTANCE.getNegativeApplicationCondition()) {
			return true;
		}
		GraphCondition gc = getHighestCondition(n);
		if (gc == null) {
			return false;
		}
		if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
			if (gc.eContainer().eClass() == SamtypegraphPackage.eINSTANCE.getTypeGraph()) {
				return false;
			} else if (gc.eContainer().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				if (gc.eContainer().eContainer().eClass() == SamtypegraphPackage.eINSTANCE.getTypeGraph()) {
					return false;
				}
			}
			return true;			
		} else {
			return false;
		}
	}
	
	public static boolean isNegated(Edge e) {
		if (e.eContainer().eClass() == NacPackage.eINSTANCE.getNegativeApplicationCondition()) {
			return true;
		}
		GraphCondition gc = getHighestCondition(e);
		if (gc == null) {
			return false;
		}
		if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
			if (gc.eContainer().eClass() == SamtypegraphPackage.eINSTANCE.getTypeGraph()) {
				return false;
			} else if (gc.eContainer().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				if (gc.eContainer().eContainer().eClass() == SamtypegraphPackage.eINSTANCE.getTypeGraph()) {
					return false;
				}
			}
			return true;				
		} else {
			return false;
		}
	}
	
	public static int positiveSize(Match matching) {
		int count = 0;
		for (Edge e : matching.getEdgeMatching().keySet()) {
			if (!isNegated(e)) {
				count = count + 1;
			}
		}
		for (Node n : matching.getNodeMatching().keySet()) {
			if (!isNegated(n)) {
				count = count + 1;
			}
		}
		return count;
	}
	
	public static Match copyAsRuleGraph(Graph g) {
		Match currentMatching = SamtraceFactory.eINSTANCE.createMatch(); 
		Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();		
		result.setTypedOver(g.getTypedOver());
		
		for (Node n : g.getNodes()) {
			Node newNode = copyAsPattern(n);
			if (n.eClass() == NacPackage.eINSTANCE.getPatternNode()) {
				((PatternNode) newNode).setSameInProp(((PatternNode) n).getSameInProp());
				((PatternNode) newNode).setSameInRule(((PatternNode) n).getSameInRule());
			}
			currentMatching.getNodeMatching().put(n, newNode);
			result.getNodes().add(newNode);
		}
		for (Edge e : g.getEdges()) {
			Edge newEdge = copyAsPattern(e);
			if (e.eClass() == NacPackage.eINSTANCE.getPatternEdge()) {
				((PatternEdge) newEdge).setSameInProp(((PatternEdge) e).getSameInProp());
				((PatternEdge) newEdge).setSameInRule(((PatternEdge) e).getSameInRule());
			}
			currentMatching.getEdgeMatching().put(e, newEdge);
			newEdge.setSource(currentMatching.getNodeMatching().get(e.getSource()));
			newEdge.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
			result.getEdges().add(newEdge);
		}
			
		return currentMatching;
	}
	
	public static Match createMinimalTranslation(Graph graph, Match matching) {
		Match resultMatching = SamtraceFactory.eINSTANCE.createMatch();
		Graph result = SamrulesFactory.eINSTANCE.createRuleGraph();
		
		for (Node src : matching.getNodeMatching().values()) {
			if (!graph.getNodes().contains(src)) {
				throw new RuntimeException("something strange happened");
			}
			Node n = src.copy();
			result.getNodes().add(n);
			resultMatching.getNodeMatching().put(src,  n);			
		}
		for (Edge src : matching.getEdgeMatching().values()) {
			if (!graph.getEdges().contains(src)) {
				throw new RuntimeException("something strange happened");
			}
			Edge e = src.copy();
			e.setSource(resultMatching.getNodeMatching().get(src.getSource()));
			e.setTarget(resultMatching.getNodeMatching().get(src.getTarget()));
			result.getEdges().add(e);
			resultMatching.getEdgeMatching().put(src,  e);			
		}
		for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(graph).getNacs()) {
			for (Annotation an : nac.getAnnotations()) {
				if (an.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM)) {
					if (!graph.getNodes().contains(an.getTarget()) && !graph.getEdges().contains(an.getTarget())) {
						throw new RuntimeException("something strange happened");
					}
					if (SamgraphPackage.eINSTANCE.getNode().isSuperTypeOf(an.getTarget().eClass())) {
						if (!resultMatching.getNodeMatching().containsKey(an.getTarget())) {
							Node n = ((Node) an.getTarget()).copy();
							result.getNodes().add(n);
							resultMatching.getNodeMatching().put((Node) an.getTarget(), n);
						}
					}					
				}
			}
			for (Annotation an : nac.getAnnotations()) {
				if (an.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM)) {
					if (!graph.getNodes().contains(an.getTarget()) && !graph.getEdges().contains(an.getTarget())) {
						throw new RuntimeException("something strange happened");
					}
					if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(an.getTarget().eClass())) {
						if (!resultMatching.getEdgeMatching().containsKey(an.getTarget())) {
							Edge e = ((Edge) an.getTarget()).copy();
							e.setSource(resultMatching.getNodeMatching().get(((Edge) an.getTarget()).getSource()));
							e.setTarget(resultMatching.getNodeMatching().get(((Edge) an.getTarget()).getTarget()));							
							result.getEdges().add(e);
							resultMatching.getEdgeMatching().put((Edge) an.getTarget(), e);
						}
					}					
				}
			}
		}
		
		return resultMatching;
	}
	
	public static Map<NodeType, Integer> calculateNodeTypeCount(Graph hostGraph) {		
		Map<NodeType, Integer> result = new HashMap<NodeType, Integer>();
		if (hostGraph == null) {
			return result;
		}
		for (Node n : hostGraph.getNodes()) {
			NodeType type = n.getInstanceOf();
			if (result.containsKey(type)) {
				result.put(type, result.get(type) + 1);
			} else {
				result.put(type, 1);
			}
		}
		return result;
	}
	
	public static Map<EdgeType, Integer> calculateEdgeTypeCount(Graph hostGraph) {		
		Map<EdgeType, Integer> result = new HashMap<EdgeType, Integer>();
		if (hostGraph == null) {
			return result;
		}
		for (Edge e : hostGraph.getEdges()) {
			EdgeType type = e.getInstanceOf();
			if (result.containsKey(type)) {
				result.put(type, result.get(type) + 1);
			} else {
				result.put(type, 1);
			}
		}
		return result;
	}

}
