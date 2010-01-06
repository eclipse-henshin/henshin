package org.eclipse.emf.henshin.diagram.actions;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinGraphUtil;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Static methods for determining and changing action for nodes.
 * @generated NOT
 * @author Christian Krause
 */
public class NodeActionUtil {
	
	/**
	 * Determine the action for a node. If this returns <code>null</code>
	 * the node is not considered to be a proper action node and hence
	 * should not be displayed in an integrated rule view.
	 * @param node Node.
	 * @return Action or <code>null</code>.
	 */
	public static Action getNodeAction(Node node) {
		return InternalActionUtil.getAction(node);
	}
	
	/**
	 * Set the action for a node.
	 * @param node Node.
	 * @param action Action.
	 */
	public static void setNodeAction(Node node, Action action) {
		
		// Get the current action.
		Action current = getNodeAction(node);
		if (action.equals(current)) return;
		
		// Get the container graph and rule.
		Graph graph = node.getGraph();
		Rule rule = HenshinGraphUtil.getRule(graph);
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the node is contained in the LHS and that it is mapped to a node in the RHS.
			
			// We delete the mapping and the image:
			Mapping mapping = HenshinMappingUtil.getNodeImageMapping(node, rule.getRhs(), rule.getMappings());			
			HenshinGraphUtil.deleteNode(mapping.getImage());
			rule.getMappings().remove(mapping);
			
			// For CREATE actions, move the origin to the RHS:
			if (action.getType()==ActionType.CREATE) {
				node.setGraph(rule.getRhs());
			}
			
		}
		
		// Current action type = CREATE?
		if (current.getType()==ActionType.CREATE) {
			
			// We know that the node is contained in the RHS and that it is not an image of a mapping.
			
			// We move the node to the LHS:
			node.setGraph(rule.getLhs());
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				Node image = (Node) EcoreUtil.copy(node);
				Mapping mapping = HenshinMappingUtil.createMapping(node,image);
				rule.getMappings().add(mapping);
				rule.getRhs().getNodes().add(image);
			}
			
		}

		// Current action type = DELETE?
		if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS and that it has no image in the RHS.
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				Node image = (Node) EcoreUtil.copy(node);
				Mapping mapping = HenshinMappingUtil.createMapping(node, image);
				rule.getMappings().add(mapping);
				rule.getRhs().getNodes().add(image);
			}
			
			// For CREATE actions, move the node to the RHS:
			if (action.getType()==ActionType.CREATE) {
				node.setGraph(rule.getRhs());
			}
		}		
		
	}
	
	/*
	 * Private helper for moving a node to another graph.
	 */
	private static void moveNode(Node node, Graph graph, List<Mapping> mappings) {
		for (Edge incoming : node.getIncoming()) {
			Node newSource = HenshinMappingUtil.getNodeImage(incoming.getSource(), graph, mappings);
			incoming.setSource(newSource);
			incoming.setGraph(graph);
		}
		for (Edge outgoing : node.getOutgoing()) {
			Node newTarget = HenshinMappingUtil.getNodeImage(outgoing.getTarget(), graph, mappings);
			outgoing.setTarget(newTarget);
			outgoing.setGraph(graph);
		}
		node.setGraph(graph);
	}
	
	/**
	 * Get all nodes in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule, Action action) {
		return InternalActionUtil.getActionElements(rule, action, HenshinPackage.eINSTANCE.getGraph_Nodes());
	}
	
	/**
	 * Get all nodes in a rule that are associated with an arbitrary action.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule) {
		return getActionNodes(rule, null);
	}
	
}
