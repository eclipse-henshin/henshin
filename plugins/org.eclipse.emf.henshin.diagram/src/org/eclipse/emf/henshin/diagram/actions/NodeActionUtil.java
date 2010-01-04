package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinUtil;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class NodeActionUtil {
	
	/**
	 * Get the action associated with a node.
	 * @param node Node.
	 * @return Action.
	 */
	public static ElementAction getNodeAction(Node node) {
		
		// Must be contained in a graph and rule:
		Graph graph = node.getGraph();
		if (graph==null) return null;
		
		// Graph must be part of a rule:
		Rule rule = HenshinUtil.getRule(graph);
		if (rule==null) return null;
		
		// LHS element?
		if (graph==rule.getLhs()) {
			
			// Check if it is mapped to the RHS:
			Mapping mapping = HenshinUtil.getOriginMapping(node, rule.getRhs(), rule.getMappings());
			if (mapping!=null) {
				return new ElementAction(ActionType.NONE);
			} else {
				return new ElementAction(ActionType.DELETE);
			}
			
		}
		
		// Otherwise find the origin in the LHS:
		Mapping mapping = HenshinUtil.getImageMapping(node, rule.getMappings());
		if (mapping==null) {
			
			// CREATE-action?
			if (rule.getRhs()==graph) {
				return new ElementAction(ActionType.CREATE);			
			}
			
			// FORBID-action?
			//if (rule.getNacs().contains(node.getGraph())) {
			//	return new ElementAction(ElementActionType.FORBID);
			//}
			
		}
		
		// This point is not considered as an action-node.
		return null;
		
	}

	/**
	 * Set the action for a node.
	 * @param node Node.
	 * @param action Action.
	 */
	public static void setNodeAction(Node node, ElementAction action) {
		
		// Get the current action.
		ElementAction current = getNodeAction(node);
		if (action.equals(current)) return;

		// Get the container graph and rule.
		Graph graph = node.getGraph();
		Rule rule = HenshinUtil.getRule(graph);
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the node is contained in the LHS
			// and that it is mapped to a node in the RHS.
			Mapping mapping = HenshinUtil.getOriginMapping(node, rule.getRhs(), rule.getMappings());
			
			// For CREATE actions, delete the node in the LHS:
			if (action.getType()==ActionType.CREATE) {
				HenshinUtil.deleteNode(mapping.getImage());
				HenshinUtil.deleteNode(node);
				rule.getRhs().getNodes().add(node);
			}
			
			// For DELETE actions, delete the node in the RHS:
			if (action.getType()==ActionType.DELETE) {
				HenshinUtil.deleteNode(mapping.getImage());
			}
		}
		
		// Current action type = CREATE?
		if (current.getType()==ActionType.CREATE) {
			
			// We know that the node is contained in the RHS 
			// and that it is not an image of a mapping.
			
			// For NONE actions, create a node in the LHS and map it:
			if (action.getType()==ActionType.NONE) {
				Node origin = (Node) EcoreUtil.copy(node);
				rule.getLhs().getNodes().add(origin);
				HenshinUtil.createMapping(origin, node);
			}
			
			// For DELETE actions, move the node to the LHS:
			if (action.getType()==ActionType.DELETE) {
				HenshinUtil.deleteNode(node);
				rule.getLhs().getNodes().add(node);
			}
		}

		// Current action type = DELETE?
		if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS 
			// and that it has no image in the RHS.
			
			// For NONE actions, create a node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				Node image = (Node) EcoreUtil.copy(node);
				rule.getRhs().getNodes().add(image);
				HenshinUtil.createMapping(node, image);
			}
			
			// For CREATE actions, move the node to the RHS:
			if (action.getType()==ActionType.CREATE) {
				HenshinUtil.deleteNode(node);
				rule.getRhs().getNodes().add(node);
			}
		}		
		
	}

	/**
	 * Get all nodes contained in a rule. It collects all nodes
	 * from the LHS, RHS and the NACs.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getAllNodes(Rule rule) {
		List<Node> nodes = new ArrayList<Node>();
		nodes.addAll(rule.getLhs().getNodes());
		nodes.addAll(rule.getRhs().getNodes());
//		for (Graph nac : rule.getNacs()) {
//		}
		return nodes;
	}
	
	/**
	 * Get all nodes in a rule that are associated with an arbitrary action.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule) {
		return getActionNodes(rule, null);
	}
		
	/**
	 * Get all nodes in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule, ElementAction action) {
		List<Node> nodes;
		if (action!=null) {
			switch (action.getType()) {
			case NONE:
			case DELETE:
				nodes = rule.getLhs().getNodes();
				break;
			case CREATE:
				nodes = rule.getRhs().getNodes();
				break;
			case FORBID:
				
			default:
				nodes = getAllNodes(rule);
			}
		} else {
			nodes = getAllNodes(rule);
		}
		return getActionNodes(nodes, action);
	}
	
	/*
	 * Get nodes associated with an action.
	 */
	private static List<Node> getActionNodes(List<Node> nodes, ElementAction action) {
		List<Node> result = new ArrayList<Node>();
		for (Node node : nodes) {
			ElementAction current = getNodeAction(node);
			if (current!=null && (action==null || action.equals(current))) {
				result.add(node);
			}
		}
		return result;
	}
	
}
