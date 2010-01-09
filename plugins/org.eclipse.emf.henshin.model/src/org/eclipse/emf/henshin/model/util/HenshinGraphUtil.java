package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Utility methods for Henshin graphs.
 * @generated NOT
 * @author Christian Krause
 */
public class HenshinGraphUtil {
	
	/**
	 * Create a new node and add it to a graph.
	 * @param graph Graph to be added to.
	 * @param type Type of the node.
	 * @return The created node.
	 */
	public static Node createNode(Graph graph, EClass type) {
		Node node = HenshinFactory.eINSTANCE.createNode();
		node.setType(type);
		graph.getNodes().add(node);
		return node;
	}
	
	/**
	 * Create a new edge between two nodes. The edge is automatically
	 * added to the graph of the source node, if it is not <code>null</code>.
	 * @param source Source node.
	 * @param target Target node.
	 * @param type Edge type.
	 * @return The created edge.
	 */
	public static Edge createEdge(Node source, Node target, EReference type) {
		Edge edge = HenshinFactory.eINSTANCE.createEdge();
		edge.setSource(source);
		edge.setTarget(target);
		edge.setType(type);
		edge.setGraph(source.getGraph());
		return edge;
	}

	/**
	 * Find an edge.
	 * @param source Source of the edge.
	 * @param target Target of the edge.
	 * @param type Type of the edge.
	 * @return The edge.
	 */
	public static Edge findEdge(Node source, Node target, EReference type) {
		for (Edge edge : source.getOutgoing()) {
			if (type==edge.getType() && target==edge.getTarget()) return edge;
		}
		return null;
	}

	/**
	 * Find an outgoing edge.
	 * @param source Source of the edge.
	 * @param type Type of the edge.
	 * @return The edge.
	 */
	public static Edge findOutgoingEdge(Node source, EReference type) {
		for (Edge edge : source.getOutgoing()) {
			if (type==edge.getType()) return edge;
		}
		return null;
	}

	/**
	 * Find an incoming edge.
	 * @param target Target of the edge.
	 * @param type Type of the edge.
	 * @return The edge.
	 */
	public static Edge findIncomingEdge(Node target, EReference type) {
		for (Edge edge : target.getIncoming()) {
			if (type==edge.getType()) return edge;
		}
		return null;
	}

	/**
	 * Get a list of all coinciding edges of a node.
	 * @param node Node.
	 * @return List of edges.
	 */
	public static List<Edge> getAllEdges(Node node) {
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(node.getIncoming());
		edges.addAll(node.getOutgoing());
		return Collections.unmodifiableList(edges);
	}

	/**
	 * Get the rule a graph is contained in.
	 * @param graph Graph.
	 * @return Rule or <code>null</code>.
	 */
	public static Rule getRule(Graph graph) {
		EObject container = graph.eContainer();
		while (container!=null) {
			if (container instanceof Rule) return (Rule) container;
			container = container.eContainer();
		}
		return null;
	}

	/**
	 * Delete an edge. This detaches the edge from its source and target node
	 * and removes it from the graph it is contained in.
	 * @param edge Edge to be deleted.
	 */
	public static void deleteEdge(Edge edge) {
		edge.setSource(null);
		edge.setTarget(null);
		edge.setGraph(null);
	}

	/**
	 * Delete a node. This removes the node and all attached edges
	 * from its container graph.
	 * @param node Node to be deleted.
	 */
	public static void deleteNode(Node node) {
		// Delete the edges first:
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(node.getIncoming());
		edges.addAll(node.getOutgoing());
		for (Edge edge : edges) {
			deleteEdge(edge);
		}
		// Check if it is contained in a graph:
		if (node.getGraph()!=null) {
			node.setGraph(null);
		}
	}
	
	/**
	 * Find an attribute in a node, given its type.
	 * @param node Node.
	 * @param type Attribute type.
	 * @return The attribute if found, <code>null</code> otherwise.
	 */
	public static Attribute findAttribute(Node node, EAttribute type) {
		for (Attribute attribute : node.getAttributes()) {
			if (attribute.getType()==type) return attribute;
		}
		return null;
	}
	
}
