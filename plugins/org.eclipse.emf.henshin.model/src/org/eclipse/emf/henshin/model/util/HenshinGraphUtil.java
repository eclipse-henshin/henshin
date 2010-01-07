package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
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
	 * Move a node to another graph.
	 * @param node Node to be moved.
	 * @param graph Target graph
	 * @param mappings Mappings to be used.
	 */
	public static void moveNode(Node node, Graph graph, List<Mapping> mappings) {
		
		// Move all incoming edges:
		for (Edge incoming : node.getIncoming()) {
			Node newSource = HenshinMappingUtil.getNodeImage(incoming.getSource(), graph, mappings);
			incoming.setSource(newSource);
			incoming.setGraph(graph);
		}
		
		// Move all outgoing edges:
		for (Edge outgoing : node.getOutgoing()) {
			Node newTarget = HenshinMappingUtil.getNodeImage(outgoing.getTarget(), graph, mappings);
			outgoing.setTarget(newTarget);
			outgoing.setGraph(graph);
		}
		
		// Move the node itself:
		node.setGraph(graph);
		
	}
	
	/**
	 * Copy a node to another graph.
	 * @param node Node to be copied.
	 * @param graph Target graph
	 * @param mappings Mappings to be used.
	 */
	public static Node copyNode(Node node, Graph graph, List<Mapping> mappings, boolean map) {
		
		// Copy the node:
		Node newNode = (Node) EcoreUtil.copy(node);
		
		// Create the mapping if required:
		if (map) {
			Mapping mapping = HenshinMappingUtil.createMapping(node, newNode);
			mappings.add(mapping);
		}
		
		// Copy the incoming edges:
		for (Edge incoming : node.getIncoming()) {
			Node newSource = HenshinMappingUtil.getNodeImage(incoming.getSource(), graph, mappings);
			Edge copy = HenshinFactory.eINSTANCE.createEdge();
			copy.setSource(newSource);
			copy.setTarget(node);
		}
		
		// Copy the outgoing edges:
		for (Edge outgoing : node.getOutgoing()) {
			Node newTarget = HenshinMappingUtil.getNodeImage(outgoing.getTarget(), graph, mappings);
			Edge copy = HenshinFactory.eINSTANCE.createEdge();
			copy.setSource(node);
			copy.setTarget(newTarget);
		}
		
		// Add the node and the edges to the target graph:
		graph.getNodes().add(newNode);
		for (Edge incoming : newNode.getIncoming()) {
			if (!graph.getEdges().contains(incoming)) graph.getEdges().add(incoming);
		}
		for (Edge outgoing : newNode.getOutgoing()) {
			if (!graph.getEdges().contains(outgoing)) graph.getEdges().add(outgoing);
		}
		
		// Done.
		return newNode;
		
	}
	
}
