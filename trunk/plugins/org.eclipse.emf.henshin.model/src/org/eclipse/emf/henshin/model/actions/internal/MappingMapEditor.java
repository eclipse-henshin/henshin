package org.eclipse.emf.henshin.model.actions.internal;

import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * A map editor for mappings. Used for amalgamations.
 * This does not implement {@link MapEditor}.
 * @author Christian Krause
 */
public class MappingMapEditor {
	
	// Source and target rule.
	private Rule source, target;
	
	// Node map editors:
	private NodeMapEditor lhsNodeMapEditor, rhsNodeMapEditor;

	// Edge map editors:
	private EdgeMapEditor lhsEdgeMapEditor, rhsEdgeMapEditor;

	public MappingMapEditor(Rule source, Rule target, MappingList mappings) {
		
		// Source and target:
		this.source = source;
		this.target = target;

		// LHS and RHS map editors:
		lhsNodeMapEditor = new NodeMapEditor(source.getLhs(), target.getLhs(), mappings);
		rhsNodeMapEditor = new NodeMapEditor(source.getRhs(), target.getRhs(), mappings);
		lhsEdgeMapEditor = new EdgeMapEditor(source.getLhs(), target.getLhs(), mappings);
		rhsEdgeMapEditor = new EdgeMapEditor(source.getRhs(), target.getRhs(), mappings);
	}
	
	/**
	 * Move a mapped node.
	 * @param node Node to be moved.
	 */
	public void moveMappedNode(Node node) {
		
		// Get the opposite node and the mapping.
		Node opposite = getOppositeNode(node);
		Graph graph = node.getGraph();
		if (opposite==null || graph==null) return;
		
		// Move the mapping:
		if (graph.getContainerRule()==source) {
			target.getMappings().add( getLhsRhsMapping(node, opposite) );
		} else if (graph.getContainerRule()==target) {
			source.getMappings().add( getLhsRhsMapping(node, opposite) );
		}

		// Move the node and the opposite node:
		if (graph==source.getLhs() || graph==target.getLhs()) {
			rhsNodeMapEditor.move(opposite);				
			lhsNodeMapEditor.move(node);
		}
		else if (graph==source.getRhs() || graph==target.getRhs()) {
			rhsNodeMapEditor.move(node);				
			lhsNodeMapEditor.move(opposite);
		}
		
		// It could be that we left an old mapping instance in the multi-rule.
		removeInvalidMappings(target.getMappings(), target.getLhs(), target.getRhs());
		
	}

	private static void removeInvalidMappings(List<Mapping> mappings, Graph source, Graph target) {
		for (int i=0; i<mappings.size(); i++) {
			Mapping mapping = mappings.get(i);
			if (mapping.getOrigin().getGraph()!=source ||
				mapping.getImage().getGraph()!=target) {
				mappings.remove(i--);
			}
		}
	}

	/**
	 * Copy a mapped node.
	 * @param node Node to be copied.
	 */
	public void copyMappedNode(Node node) {
		
		// Get the opposite node and the mapping.
		Node opposite = getOppositeNode(node);
		Graph graph = node.getGraph();
		if (opposite==null || graph==null) return;

		// Copy the node and the opposite node:
		Node copiedNode, copiedOpposite;
		if (graph==source.getLhs() || graph==target.getLhs()) {
			copiedOpposite = rhsNodeMapEditor.copy(opposite);				
			copiedNode = lhsNodeMapEditor.copy(node);
		}
		else if (graph==source.getRhs() || graph==target.getRhs()) {
			copiedNode = rhsNodeMapEditor.copy(node);
			copiedOpposite = lhsNodeMapEditor.copy(opposite);
		}
		else {
			return;
		}
		
		// Create a copy of the mapping:
		Mapping mapping = getLhsRhsMapping(node, opposite);
		if (graph.getContainerRule()==source) {
			copyMapping(mapping, node, opposite, copiedNode, copiedOpposite, target);
		} else if (graph.getContainerRule()==target) {
			copyMapping(mapping, node, opposite, copiedNode, copiedOpposite, source);
		}
	
	}

	
	/**
	 * Move a mapped edge.
	 * @param edge Edge to be moved.
	 */
	public void moveMappedEdge(Edge edge) {
		
		// Get the opposite edge first:
		Edge opposite = getOppositeEdge(edge);
		Graph graph = edge.getGraph();
		if (opposite==null || graph==null) return;
		
		// Find out from where to where we move...
		Rule from;
		if (graph==source.getLhs() || graph==source.getRhs()) {
			from = source;
		} else if (graph==target.getLhs() || graph==target.getRhs()) {
			from = target;
		} else {
			return;
		}
		
		// If the edge is mapped, then so is its source and target
		copyMappedNode(edge.getSource());
		copyMappedNode(edge.getTarget());
		
		// Now we move the edge to the new graph
		if (graph==from.getLhs()) {
			lhsEdgeMapEditor.move(edge);
			rhsEdgeMapEditor.move(opposite);
		} else if (graph==from.getRhs()) {
			lhsEdgeMapEditor.move(opposite);
			rhsEdgeMapEditor.move(edge);
		}
		
	}

	/**
	 * Move a mapped element. This can be either a node or
	 * an edge. If it is neither, nothing hapens.
	 * @param element Element to be moved.
	 */
	public void moveMappedElement(Object element) {
		if (element instanceof Node) {
			moveMappedNode((Node) element);
		} else if (element instanceof Edge) {
			moveMappedEdge((Edge) element);
		}
	}
	
	public void ensureCompleteness() {
		for (Node node : source.getLhs().getNodes()) {
			copyNodeToTarget(node);
		}
		for (Edge edge : source.getLhs().getEdges()) {
			copyEdgeToTarget(edge);
		}
	}
	
	private void copyNodeToTarget(Node node) {
		if (getOppositeNode(node)!=null) {
			copyMappedNode(node);
		} else {
			if (node.getGraph()==source.getLhs()) {
				lhsNodeMapEditor.copy(node);
			} else if (node.getGraph()==source.getRhs()) {
				rhsNodeMapEditor.copy(node);
			}
		}
	}

	private void copyEdgeToTarget(Edge edge) {
		Edge opposite = getOppositeEdge(edge);
		if (edge.getGraph()==source.getLhs()) {
			lhsEdgeMapEditor.copy(edge);
			if (opposite!=null) {
				rhsEdgeMapEditor.copy(opposite);				
			}
		}
		else if (edge.getGraph()==source.getRhs()) {
			rhsEdgeMapEditor.copy(edge);
			if (opposite!=null) {
				lhsEdgeMapEditor.copy(opposite);				
			}
		}
	}

	
	/*
	 * Get the opposite node.
	 */
	private Node getOppositeNode(Node node) {
		if (node.getGraph().getContainerRule()==source) {
			return new NodeMapEditor(source.getRhs()).getOpposite(node);
		} else if (node.getGraph().getContainerRule()==target) {
			return new NodeMapEditor(target.getRhs()).getOpposite(node);
		} else {
			return null;
		}
	}

	/*
	 * Get the opposite edge.
	 */
	private Edge getOppositeEdge(Edge edge) {
		if (edge.getGraph().getContainerRule()==source) {
			return new EdgeMapEditor(source.getRhs()).getOpposite(edge);
		} else if (edge.getGraph().getContainerRule()==target) {
			return new EdgeMapEditor(target.getRhs()).getOpposite(edge);
		} else {
			return null;
		}
	}
	
	// Get the LHS-RHS mapping for two nodes.
	private Mapping getLhsRhsMapping(Node n1, Node n2) {
		Mapping mapping = source.getMappings().get(n1, n2);
		if (mapping==null) {
			mapping = target.getMappings().get(n1, n2);
		}
		return mapping;
	}
	
	private void copyMapping(Mapping mapping, Node old1, Node old2, Node new1, Node new2, Rule rule) {
		if (mapping.getOrigin()==old1 && mapping.getImage()==old2) {
			rule.getMappings().add(new1, new2);
		} else if (mapping.getOrigin()==old2 && mapping.getImage()==old1) {
			rule.getMappings().add(new2, new1);
		}
	}
	
}
