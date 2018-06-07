package org.eclipse.emf.henshin.multicda.cda;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.units.Span;
import org.eclipse.emf.henshin.multicda.cda.units.SpanMappings;

public class Pushout {
	HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

	/**
	 * @return the mappingsOfRule1
	 */
	public List<Mapping> getRule1Mappings() {
		return toMappingList(rule1toPOmap);
	}

	/**
	 * @return the mappingsOfRule2
	 */
	public List<Mapping> getRule2Mappings() {
		return toMappingList(rule2toPOmap);
	}

	private List<Mapping> toMappingList(HashMap<Node, Node> map) {
		List<Mapping> result = new LinkedList<Mapping>();
		for (Node node : map.keySet()) {
			result.add(henshinFactory.createMapping(node, map.get(node)));
		}
		return result;
	}

	/**
	 * @return the resultGraph
	 */
	public Graph getResultGraph() {
		return graph;
	}

	Graph graph;
	Graph graphNoneDeletion;

	private HashMap<Node, Node> rule1toPOmap = new HashMap<Node, Node>();
	private HashMap<Node, Node> rule2toPOmap = new HashMap<Node, Node>();

	private Graph shadowGraph;

	/**
	 * Creates Pushout of rule1.Lhs() <-- S --> rule2.Lhs() and rule1.Lhs() --> G <-- rule2.Lhs()
	 * Pushout validation is on
	 * 
	 * @param rule1
	 * @param l1Sl2
	 * @param rule2
	 */
	public Pushout(Rule rule1, Span l1Sl2, Rule rule2) {
		this(rule1.getLhs(), l1Sl2, rule2.getLhs(), true);
	}

	/**
	 * Creates Pushout of rule1.Lhs() <-- S --> rule2.Lhs() and rule1.Lhs() --> G <-- rule2.Lhs()
	 * 
	 * @param rule1
	 * @param s1span
	 * @param rule2
	 * @param validate turns the pushout validation on or of
	 */

	public Pushout(Graph l1, Span s1span, Graph l2, boolean validate) {
		Utils.checkNull(l1);
		Utils.checkNull(s1span);
		Utils.checkNull(l2);
		if (!s1span.validate(l1, l2))
			throw new IllegalArgumentException("Span is in invalide state.");

		graph = preparePushoutGraph(l1);
		Map<EObject, EObject> shadow2Rule2 = prepareShadowPushoutGraph(l2);

		Graph s1 = s1span.getGraph();
		for (Node node : s1.getNodes()) {
			glue(s1span, new SpanMappings(s1span), node, shadow2Rule2, validate);
		}

		moveShadowContentsToPushout(graph, shadowGraph);

		if (validate)
			validatePushout(l1, l2, s1);
		graph.setName("Pushout");

	}

	@SuppressWarnings("unused")
	private void glue(Span s1span, SpanMappings spanMappings, Node node, Map<EObject, EObject> shadow2Rule2,
			boolean validate) {
		Node l1node = s1span.getMappingIntoRule1(node).getImage();
		Node l2node = s1span.getMappingIntoRule2(node).getImage();

		if (l1node == null || l2node == null) {
			throw new RuntimeException("Did not find a L1 or L2 counterpart for one of the nodes in S1!");
		} else {
			Node glueNode = rule1toPOmap.get(l1node);
			Node discardNode = rule2toPOmap.get(l2node);
			glueNode.setName(glueNode.getName() + "," + discardNode.getName());

			List<Edge> l2nodesIncoming = new LinkedList<Edge>(discardNode.getIncoming());
			for (Edge eIn : l2nodesIncoming) {
				if (spanMappings.getEdgeMappingsRule2S1().get(shadow2Rule2.get(eIn)) == null) {
					eIn.setTarget(glueNode);
				} else {
					eIn.getGraph().removeEdge(eIn);
				}
			}

			List<Edge> l2nodesOutgoing = new LinkedList<Edge>(discardNode.getOutgoing());
			for (Edge eOut : l2nodesOutgoing) {
				if (spanMappings.getEdgeMappingsRule2S1().get(shadow2Rule2.get(eOut)) == null) {
					eOut.setSource(glueNode);
				} else {
					eOut.getGraph().removeEdge(eOut);
				}
			}

			rule2toPOmap.put(l2node, glueNode);

			if (validate && discardNode.getAllEdges().size() > 0) {
				System.err.println("All Edges of should have been removed, but still " + l2node.getAllEdges().size()
						+ " are remaining!");
			}
			Graph graphOfNodeL2 = discardNode.getGraph();
			if (graphOfNodeL2 != null)
				graphOfNodeL2.removeNode(discardNode);
			else if (validate)
				System.err.println(discardNode + " has no Graph");
		}
	}

	private void moveShadowContentsToPushout(Graph pushout, Graph shadowpushout) {
		List<Node> nodesInCopyOfLhsOfRule2 = new LinkedList<Node>(shadowpushout.getNodes());
		for (Node nodeInCopyOfLhsOfRule2 : nodesInCopyOfLhsOfRule2) {
			nodeInCopyOfLhsOfRule2.setGraph(pushout);
		}
		List<Edge> edgesInCopyOfLhsOfRule2 = new LinkedList<Edge>(shadowpushout.getEdges());
		for (Edge edgeInCopyOfLhsOfRule2 : edgesInCopyOfLhsOfRule2) {
			edgeInCopyOfLhsOfRule2.setGraph(pushout);
		}

		if (shadowpushout.getEdges().size() > 0) {
			System.err.println(
					shadowpushout.getEdges().size() + " edges remaining in " + shadowpushout + ", but should be 0");
		}
		if (shadowpushout.getNodes().size() > 0) {
			System.err.println(
					shadowpushout.getNodes().size() + " nodes remaining in " + shadowpushout + ", but should be 0");
		}
	}

	private Map<EObject, EObject> prepareShadowPushoutGraph(Graph l2) {
		Copier copierForRule2 = new Copier();
		shadowGraph = (Graph) copierForRule2.copy(l2);
		copierForRule2.copyReferences();
		for (Node node : l2.getNodes()) {
			Node copyResultNode = (Node) copierForRule2.get(node);
			rule2toPOmap.put(node, copyResultNode);
		}
		Map<EObject, EObject> shadow2Rule2 = new HashMap<>();
		for (EObject o : copierForRule2.keySet()) {
			shadow2Rule2.put(copierForRule2.get(o), o);
		}
		return shadow2Rule2;
	}

	private Graph preparePushoutGraph(Graph l1) {
		Copier copierForRule1Map = new Copier();
		Graph pushout = (Graph) copierForRule1Map.copy(l1);
		copierForRule1Map.copyReferences();
		for (Node node : l1.getNodes()) {
			Node copyResultNode = (Node) copierForRule1Map.get(node);
			rule1toPOmap.put(node, copyResultNode);
		}
		return pushout;
	}

	private void validatePushout(Graph l1, Graph l2, Graph s1) {
		int numberOfExpectedNodes = (l1.getNodes().size() + l2.getNodes().size() - s1.getNodes().size());
		if (graph.getNodes().size() != numberOfExpectedNodes) {
			System.err.println("Number of nodes in created result graph (" + graph.getNodes().size()
					+ ") not as expected (" + numberOfExpectedNodes + "). Difference: "
					+ (graph.getNodes().size() - numberOfExpectedNodes));
		}
		int numberOfExpectedEdges = (l1.getEdges().size() + l2.getEdges().size() - s1.getEdges().size());
		if (graph.getEdges().size() != numberOfExpectedEdges) {
			System.err.println("Number of edges in created result graph (" + graph.getEdges().size()
					+ ") not as expected (" + numberOfExpectedEdges + "). Difference: "
					+ (graph.getEdges().size() - numberOfExpectedEdges));
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pushout: " + graph + "\nShadow graph: " + shadowGraph;
	}
}