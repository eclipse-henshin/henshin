package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * A helper class for editing graph morphisms, e.g.
 * for copying and mapping nodes from the source to
 * the target graph etc.
 * 
 * @author Christian Krause
 */
public class GraphMorphismEditor {
	
	// Source and target graph.
	private Graph source, target;
	
	// Mappings to be used.
	private List<Mapping> mappings;
	
	/**
	 * Default constructor.
	 * @param source Source graph.
	 * @param target Target graph.
	 * @param mappings Mappings from the source to the target graph.
	 */
	public GraphMorphismEditor(Graph source, Graph target, List<Mapping> mappings) {
		this.source = source;
		this.target = target;
		this.mappings = mappings;
	}
	
	/**
	 * Alternative constructor, which assumes that the source graph
	 * is the LHS of a rule and the target graph is either the RHS
	 * or a nested condition of the same rule.
	 * @param source Source graph.
	 * @param target Target graph.
	 */
	public GraphMorphismEditor(Graph source, Graph target) {
		
		// Check whether the graphs are null or the same.
		if (source==null || target==null || source==target) {
			throw new IllegalArgumentException("Source and target graph cannot be the same or null");
		}
		
		// Check whether the graphs are properly contained in a rule.
		if (source.getContainerRule()==null) {
			throw new IllegalArgumentException("Source graph not contained in a rule");
		}
		if (target.getContainerRule()==null) {
			throw new IllegalArgumentException("Target graph not contained in a rule");
		}
		if (source.getContainerRule()!=target.getContainerRule()) {
			throw new IllegalArgumentException("Source and target graph not contained in the same rule");
		}
		
		Rule rule = source.getContainerRule();
		if (source!=rule.getLhs()) {
			throw new IllegalArgumentException("Source graph is not the LHS of the rule");
		}
		
		// Determine the mappings to be used.
		if (target==rule.getRhs()) {
			this.mappings = rule.getMappings();
		}
		else if (target.eContainer() instanceof NestedCondition) {
			this.mappings = ((NestedCondition) target.eContainer()).getMappings();
		}
		else {
			throw new IllegalArgumentException("Target graph must be either the RHS or contained in a NestedCondition");
		}
		
		// Remember source and target:
		this.source = source;
		this.target = target;
		
	}
	
	/**
	 * Get the source graph of the morphism.
	 * @return Source graph.
	 */
	public Graph getSource() {
		return source;
	}

	/**
	 * Get the target graph of the morphism.
	 * @return Target graph.
	 */
	public Graph getTarget() {
		return target;
	}

	/**
	 * Get the mappings of the morphism.
	 * @return Mappings from the source to the target graph.
	 */
	public List<Mapping> getMappings() {
		return mappings;
	}
	
	/**
	 * If the argument node is contained in the source graph,
	 * the method returns the mapping image in the target.
	 * If it is contained in the target graph, it returns
	 * the mapping origin. In all other cases it throws an exception.
	 * @param node Node.
	 * @return The image or origin of the node.
	 */
	public Node getMappedNode(Node node) {
		Graph graph = node.getGraph();
		if (graph==null || (graph!=source && graph!=target)) {
			throw new IllegalArgumentException("Unknown node: " + node);
		}
		if (graph==source) {
			return HenshinMappingUtil.getNodeImage(node, target, mappings);
		} else {
			return HenshinMappingUtil.getNodeOrigin(node, mappings);			
		}
	}

	/**
	 * If the argument edge is contained in the source graph,
	 * the method returns the mapping image in the target.
	 * If it is contained in the target graph, it returns
	 * the mapping origin. In all other cases it throws an exception.
	 * @param edge Edge.
	 * @return The image or origin of the edge.
	 */
	public Edge getMappedEdge(Edge edge) {
		Graph graph = edge.getGraph();
		if (graph==null || (graph!=source && graph!=target)) {
			throw new IllegalArgumentException("Unknown edge: " + edge);
		}
		if (graph==source) {
			return HenshinMappingUtil.getEdgeImage(edge, target, mappings);
		} else {
			return HenshinMappingUtil.getEdgeOrigin(edge, mappings);			
		}
	}
	
	/**
	 * Remove a node from the source or target graph.
	 * @param node Node to be removed.
	 * @return <code>true</code> if it has been succesfully removed.
	 */
	public boolean removeNode(Node node) {
		Graph graph = node.getGraph();
		if (graph==source || graph==target) {
			Node mapped = getMappedNode(node);
			if (mapped!=null) {
				doRemoveMapping(node, mapped);
			}
			graph.removeNode(node);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the argument node is mapped to another one,
	 * according to {@link #getMappedNode(Node)}. If yes, it
	 * returns the mapped node. Otherwise it create a copy of
	 * it, add it to the corresponding graph and maps to it.
	 * @param node Node to be copied.
	 * @return The mapped copy.
	 */
	public Node copyNode(Node node) {
		
		// Create the copy:
		Node copy = doCopyNode(node);
		
		// Copy the incoming and outgoing edges:
		for (Edge incoming : node.getIncoming()) {
			doCreateEdge(doCopyNode(incoming.getSource()), copy, incoming.getType());
		}
		for (Edge outgoing : node.getOutgoing()) {
			doCreateEdge(copy, doCopyNode(outgoing.getTarget()), outgoing.getType());
		}
		
		// Done.
		return copy;
		
	}
	
	/**
	 * If the argument node is contained in the source graph,
	 * it is moved to the target graph, otherwise to the source graph. 
	 * If there exists already a mapped node, it is replaced.
	 * @param node Nde to be moved.
	 */
	public void moveNode(Node node) {
		
		// Check if there is already mapped:
		Node mapped = getMappedNode(node);
		if (mapped!=null) {
			replaceNode(node);
			return; // done already
		}
		
		// The graph where we move the node to:
		Graph newGraph = node.getGraph()==source ? target : source;
		
		// Move all incoming and outgoing edges:
		for (Edge incoming : node.getIncoming()) {
			incoming.setSource(doCopyNode(incoming.getSource()));
			incoming.setGraph(newGraph);
		}
		for (Edge outgoing : node.getOutgoing()) {
			outgoing.setTarget(doCopyNode(outgoing.getTarget()));
			outgoing.setGraph(newGraph);
		}
		
		// Move the node itself:
		node.setGraph(newGraph);
		
	}

	
	/**
	 * Replace a node by its image / origin.
	 */
	public Node replaceNode(Node node) {
		
		// Get the mapped node:
		Node mapped = getMappedNode(node);
		if (mapped==null) {
			throw new IllegalArgumentException("Cannot replace a node that is not mapped");
		}
		
		// The graph where we replace:
		Graph graph = node.getGraph();
				
		// Migrate the new edges:
		for (Edge incoming : mapped.getIncoming()) {
			
			// Remove old edges first:
			Edge oldEdge = getMappedEdge(incoming);
			if (oldEdge!=null) {
				graph.removeEdge(oldEdge);
			}
			
			// Now wire the new edge and move it to the other graph:
			incoming.setSource(doCopyNode(incoming.getSource()));
			incoming.setGraph(graph);
			
		}	
		for (Edge outgoing : mapped.getOutgoing()) {

			// Remove old edges first:
			Edge oldEdge = getMappedEdge(outgoing);
			if (oldEdge!=null) {
				graph.removeEdge(oldEdge);
			}
			
			// Now wire the new edge and move it to the other graph:
			outgoing.setTarget(doCopyNode(outgoing.getTarget()));
			outgoing.setGraph(graph);

		}

		// Take care of the old edges that are not mapped (the others are removed by now):
		
		for (Edge incoming : new ArrayList<Edge>(node.getIncoming())) {
			incoming.setTarget(mapped);
		}
		for (Edge outgoing : new ArrayList<Edge>(node.getOutgoing())) {
			outgoing.setSource(mapped);
		}		
		
		// Remove the mapping now (not earlier):
		doRemoveMapping(node, mapped);

		// Replace the old node by the new node:
		int index = graph.getNodes().indexOf(node);
		graph.getNodes().set(index, mapped);
		
		// Done.
		return mapped;
		
	}

	
	// -------------- //
	// Helper methods //
	// -------------- //
	
	/*
	 * Copy a node.
	 */
	private Node doCopyNode(Node node) {
		Node mapped = getMappedNode(node);
		if (mapped==null) {
			mapped = (Node) EcoreUtil.copy(node);
			if (node.getGraph()==source) {
				target.getNodes().add(mapped);
				HenshinMappingUtil.createMapping(node, mapped, mappings);
			} else {
				source.getNodes().add(mapped);
				HenshinMappingUtil.createMapping(mapped, node, mappings);
			}
		}
		return mapped;
	}
	
	/*
	 * Create an edge between two nodes.
	 */
	private Edge doCreateEdge(Node source, Node target, EReference type) {
		Edge edge = source.findOutgoingEdgeByType(target, type);
		if (edge==null) {
			edge = HenshinFactory.eINSTANCE.createEdge();
			edge.setSource(source);
			edge.setTarget(target);
			edge.setType(type);
			source.getGraph().getEdges().add(edge);
		}
		return edge;
	}
	
	/*
	 * Remove a mapping between two nodes.
	 */
	private Mapping doRemoveMapping(Node n1, Node n2) {
		Graph g1 = n1.getGraph();
		if (g1==null || (g1!=source && g1!=target)) {
			throw new IllegalArgumentException("Unknown node: " + n1);
		}
		if (g1==source) {
			return HenshinMappingUtil.removeMapping(n1, n2, mappings);
		} else {
			return HenshinMappingUtil.removeMapping(n2, n1, mappings);			
		}
	}
	
}
