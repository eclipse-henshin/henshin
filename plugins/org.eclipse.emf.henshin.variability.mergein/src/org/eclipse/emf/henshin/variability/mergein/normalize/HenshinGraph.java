package org.eclipse.emf.henshin.variability.mergein.normalize;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.jgrapht.graph.Multigraph;

public class HenshinGraph extends
		Multigraph<HenshinNode, HenshinEdge> {
	private static final long serialVersionUID = 1L;

	Map<Edge, HenshinEdge> edge2representation = new HashMap<Edge, HenshinEdge>();
	Map<Node, HenshinNode> node2representation = new HashMap<Node, HenshinNode>();
	Map<Attribute, HenshinEdge> attribute2representation = new HashMap<Attribute, HenshinEdge>();
	Map<HenshinEdge, Edge> representation2edge = new HashMap<HenshinEdge, Edge>();
	Map<HenshinNode, Node> representation2node = new HashMap<HenshinNode, Node>();
	Map<HenshinEdge, Attribute> representation2attribute = new HashMap<HenshinEdge, Attribute>();

	private String ruleName;

	public HenshinGraph() {
		super(HenshinEdge.class);
	}

	public boolean containsEdge(Edge edge) {
		return edge2representation.containsKey(edge);
	}

	public boolean containsAttribute(Attribute attribute) {
		return edge2representation.containsKey(attribute);
	}

	public boolean containsNode(Node node) {
		return node2representation.containsKey(node);
	}

	public HenshinNode getRepresentation(Node node) {
		return node2representation.get(node);
	}

	public HenshinEdge getRepresentation(Edge edge) {
		return edge2representation.get(edge);
	}

	public HenshinEdge getRepresentation(Attribute attribute) {
		return edge2representation.get(attribute);
	}

	public Node getRepresentedNode(HenshinNode node) {
		return representation2node.get(node);
	}

	public Edge getRepresentedEdge(HenshinEdge edge) {
		return representation2edge.get(edge);
	}

	public Attribute getRepresentedAttribute(HenshinEdge edge) {
		return representation2attribute.get(edge);
	}

	public void addVertex(Node node) {
		throw new UnsupportedOperationException(
				"Use addVertex(node, graphNode) instead.");
	}

	public void addVertex(Node node, HenshinNode graphNode) {
		node2representation.put(node, graphNode);
		representation2node.put(graphNode, node);
		super.addVertex(graphNode);
	}

	public boolean addEdge(Node source, Node target, Edge e, HenshinEdge edge) {
		HenshinNode sourceNode = node2representation.get(source);
		HenshinNode targetNode = node2representation.get(target);
		if (sourceNode != null && targetNode != null) {
			addEdge(sourceNode, targetNode, edge);
			edge2representation.put(e, edge);
			representation2edge.put(edge, e);
			return true;
		}
		return false;
	}

	public boolean addAttribute(Attribute attribute, Type actionType, String ruleName) {
		Node containerNode = attribute.getNode().getActionNode();
		HenshinNode sourceNode = node2representation.get(containerNode);
		if (sourceNode != null) {
			HenshinNode attributeNode = new HenshinAttributeNode(this,
					attribute.getType(), actionType, ruleName, attribute.getValue());
			super.addVertex(attributeNode);
			
			HenshinEdge integratedEdge = new HenshinEdge(this,
					AttributeEReference.instance, actionType, ruleName, true);
			addEdge(sourceNode, attributeNode, integratedEdge);
			attribute2representation.put(attribute, integratedEdge);
			representation2attribute.put(integratedEdge, attribute);
			return true;
		} else throw new RuntimeException("Error during Henshin graph creation");
//		return false;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String toStringRepresentation() {
		StringBuilder result = new StringBuilder();
		result.append("Graph " + ruleName + ": " + vertexSet().size()
				+ " nodes, " + edgeSet().size() + " edges\n");
		for (HenshinNode v : vertexSet()) {
			result.append(" * " + ((ENamedElement) v.getType()).getName() + " "
					+ v.getActionType().name() + " (incoming: "
					+ incomingEdgesOf(v).size() + ", outgoing: "
					+ outgoingEdgesOf(v).size() + ")\n");
		}
		return result.toString();
	}

}
