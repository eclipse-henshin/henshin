package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Helper methods for graph editing.
 * @generated NOT
 * @author Christian Krause
 */
class GraphEditingHelper {

	/*
	 * Copy a node to another graph.
	 */
	static Node copyNode(Node node, Graph graph, List<Mapping> mappings, boolean reverseMap) {
		
		// Make a copy of the node and map to it:
		Node newNode = (Node) EcoreUtil.copy(node);
		
		// Create a mapping to the new node:
		Rule rule = graph.getContainerRule();
		Mapping mapping = HenshinFactoryImpl.eINSTANCE.createMapping(node, newNode);
		rule.getMappings().add(mapping);
		
		// Copy the incoming edges:
		for (Edge incoming : node.getIncoming()) {
			Node newSource = getOrCreateNodeImageOrOrigin(incoming.getSource(), graph, mappings, reverseMap);
			Edge copy = (Edge) EcoreUtil.copy(incoming);
			copy.setSource(newSource);
			copy.setTarget(newNode);
		}
		
		// Copy the outgoing edges:
		for (Edge outgoing : node.getOutgoing()) {
			Node newTarget = getOrCreateNodeImageOrOrigin(outgoing.getTarget(), graph, mappings, reverseMap);
			Edge copy = (Edge) EcoreUtil.copy(outgoing);
			copy.setSource(newNode);
			copy.setTarget(newTarget);
		}
		
		// Add the new node and the edges to the target graph:
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

	/*
	 * Move a node to another graph.
	 */
	static void moveNode(Node node, Graph graph, List<Mapping> mappings, boolean reverseMap) {
		
		// Check if there is already an image or origin in the target graph:
		Node mapped = getImageOrOrigin(node, graph, mappings, reverseMap);
		if (mapped!=null) {
			
			// Replace the node instead:
			replaceNode(mapped, node, mappings, reverseMap);

			// Remove the mapping:
			Mapping mapping = getSourceMapping(node, mappings, graph, reverseMap);
			mappings.remove(mapping);
			
			// Done already:
			return;
			
		}
		
		// Move all incoming edges:
		for (Edge incoming : new ArrayList<Edge>(node.getIncoming())) {
			Node newSource = getOrCreateNodeImageOrOrigin(incoming.getSource(), graph, mappings, reverseMap);
			incoming.setSource(newSource);
			incoming.setGraph(graph);				
		}
		
		// Move all outgoing edges:
		for (Edge outgoing : new ArrayList<Edge>(node.getOutgoing())) {
			Node newTarget = getOrCreateNodeImageOrOrigin(outgoing.getTarget(), graph, mappings, reverseMap);
			outgoing.setTarget(newTarget);
			outgoing.setGraph(graph);
		}
		
		// Move the node itself:
		node.setGraph(graph);
		
	}

	/*
	 * Replace a node by another node.
	 */
	static void replaceNode(Node oldNode, Node newNode, List<Mapping> mappings, boolean reverseMap) {
		
		Graph oldGraph = oldNode.getGraph();

		// Take care of the new edges:
		
		List<Edge> newIncoming = new ArrayList<Edge>(newNode.getIncoming());
		List<Edge> newOutgoing = new ArrayList<Edge>(newNode.getOutgoing());
		
		for (Edge incoming : newIncoming) {
			Edge mapped = getImageOrOrigin(incoming, oldGraph, mappings, reverseMap);
			
			// Remove mapped edges first:
			if (mapped!=null) {
				mapped.getGraph().removeEdge(mapped);
			}
			
			// Now wire the new edge and move it to the old graph:
			Node oldSource = getOrCreateNodeImageOrOrigin(incoming.getSource(), oldGraph, mappings, reverseMap);
			incoming.setSource(oldSource);
			incoming.setGraph(oldGraph);
			
		}	
		
		for (Edge outgoing : newOutgoing) {
			Edge mapped = getImageOrOrigin(outgoing, oldGraph, mappings, reverseMap);
			
			// Remove mapped edges first:
			if (mapped!=null) {
				mapped.getGraph().removeEdge(mapped);
			}
			
			// Now wire the new edge and move it to the old graph:
			Node oldTarget = getOrCreateNodeImageOrOrigin(outgoing.getTarget(), oldGraph, mappings, reverseMap);
			outgoing.setTarget(oldTarget);
			outgoing.setGraph(oldGraph);
			
		}

		
		// Take care of the old edges that are not mapped (the others are removed by now):
		
		for (Edge incoming : new ArrayList<Edge>(oldNode.getIncoming())) {
			incoming.setTarget(newNode);
		}
		for (Edge outgoing : new ArrayList<Edge>(oldNode.getOutgoing())) {
			outgoing.setSource(newNode);
		}		
		
		// Replace the old node by the new node:
		int index = oldGraph.getNodes().indexOf(oldNode);
		oldGraph.getNodes().set(index, newNode);
		
	}

	
	/*
	 * Find either an image or an origin of a node.
	 */
	private static <T> T getImageOrOrigin(T element, Graph target, List<Mapping> mappings, boolean reverseMap) {
		return reverseMap ? HenshinMappingUtil.getOrigin(element, mappings) :
							HenshinMappingUtil.getImage(element, target, mappings);
	}

	/*
	 * Find or create a mapped node.
	 */
	private static Node getOrCreateNodeImageOrOrigin(Node node, Graph target, List<Mapping> mappings, boolean reverseMap) {
		Node mapped = getImageOrOrigin(node, target, mappings, reverseMap);
		if (mapped==null) {
			mapped = (Node) EcoreUtil.copy(node);
			target.getNodes().add(mapped);
			Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
			if (reverseMap) {
				mapping.setOrigin(mapped);
				mapping.setImage(node);
			} else {
				mapping.setOrigin(node);
				mapping.setImage(mapped);				
			}
			mappings.add(mapping);
		}
		return mapped;
	}

	/*
	 * Find a mapping for a given node.
	 */
	private static Mapping getSourceMapping(Node node, List<Mapping> mappings, Graph target, boolean reverseMap) {
		return reverseMap ? HenshinMappingUtil.getNodeOriginMapping(node, mappings) :
							HenshinMappingUtil.getNodeImageMapping(node, target, mappings);
	}

}
