package org.eclipse.emf.henshin.diagram.actions;

import java.util.List;

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
		return ElementActionHelper.getAction(node);
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
			Mapping mapping = HenshinMappingUtil.getNodeImageMapping(node, rule.getRhs(), rule.getMappings());			
			
			// For delete actions, delete the image in the RHS:
			if (action.getType()==ActionType.DELETE) {			
				HenshinGraphUtil.deleteNode(mapping.getImage());
				rule.getMappings().remove(mapping);
			}
			
			// For CREATE actions, replace the image in the RHS by the origin:
			if (action.getType()==ActionType.CREATE) {
				GraphEdititingHelper.replaceNode(mapping.getImage(), node, rule.getMappings());
				rule.getMappings().remove(mapping);
			}
			
		}
		
		// Current action type = CREATE?
		if (current.getType()==ActionType.CREATE) {
			
			// We know that the node is contained in the RHS and that it is not an image of a mapping.
			
			// We move the node to the LHS:
			GraphEdititingHelper.moveNode(node, rule.getLhs(), rule.getMappings());
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				GraphEdititingHelper.copyNode(node, rule.getRhs(), rule.getMappings());
			}
			
		}

		// Current action type = DELETE?
		if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS and that it has no image in the RHS.
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				GraphEdititingHelper.copyNode(node, rule.getRhs(), rule.getMappings());
			}
			
			// For CREATE actions, move the node to the RHS:
			if (action.getType()==ActionType.CREATE) {
				GraphEdititingHelper.moveNode(node, rule.getRhs(), rule.getMappings());
			}
		}		
		
	}

	/**
	 * Get all nodes in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule, Action action) {
		return ElementActionHelper.getActionElements(rule, action, HenshinPackage.eINSTANCE.getGraph_Nodes());
	}

	/**
	 * For an arbitrary node in a rule graph, find the corresponding action node.
	 * @param node Some node.
	 * @return The corresponding action node.
	 */
	public static Node getActionNode(Node node) {
		
		// Is the node itself already an action node?
		if (getNodeAction(node)!=null) {
			return node;
		} else {
			
			// Get the graph and rule:
			Graph graph = node.getGraph();
			Rule rule = HenshinGraphUtil.getRule(graph);
			
			// Find the origin in the LHS:
			return HenshinMappingUtil.getNodeOrigin(node, rule.getMappings());	
			
		}
		
	}

}
