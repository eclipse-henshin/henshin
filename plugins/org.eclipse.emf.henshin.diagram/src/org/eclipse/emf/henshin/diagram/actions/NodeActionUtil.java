package org.eclipse.emf.henshin.diagram.actions;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

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
		Rule rule = graph.getContainerRule();
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the node is contained in the LHS and that it is mapped to a node in the RHS.
			Mapping mapping = HenshinMappingUtil.getNodeImageMapping(node, rule.getRhs(), rule.getMappings());			
			
			// For DELETE actions, delete the image in the RHS:
			if (action.getType()==ActionType.DELETE) {
				rule.getRhs().removeNode(mapping.getImage());
				rule.getMappings().remove(mapping);
			}
			
			// For CREATE actions, replace the image in the RHS by the origin:
			else if (action.getType()==ActionType.CREATE) {
				GraphEditingHelper.replaceNode(mapping.getImage(), node, rule.getMappings(), false);
				rule.getMappings().remove(mapping);
			}
			
			// For FORBID actions, delete the image in the RHS and move the node to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				
				// Get the NAC first:
				NestedCondition nac = ElementActionHelper.getOrCreateNAC(action, rule);
				
				// Remove the image from the RHS:
				rule.getRhs().removeNode(mapping.getImage());
				rule.getMappings().remove(mapping);
				
				// Move the node to the NAC:
				GraphEditingHelper.moveNode(node, nac.getConclusion(), nac.getMappings(), false);
				
			}
			
		}
		
		// Current action type = CREATE?
		else if (current.getType()==ActionType.CREATE) {
			
			// We know that the node is contained in the RHS and that it is not an image of a mapping.
			
			// We move the node to the LHS in any case:
			GraphEditingHelper.moveNode(node, rule.getLhs(), rule.getMappings(), true);
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				GraphEditingHelper.copyNode(node, rule.getRhs(), rule.getMappings(), false);
			}
			
			// For FORBID actions, move the node further to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ElementActionHelper.getOrCreateNAC(action, rule);
				GraphEditingHelper.moveNode(node, nac.getConclusion(), nac.getMappings(), false);
			}
			
		}

		// Current action type = DELETE?
		else if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS and that it has no image in the RHS.
			
			// For NONE actions, create a copy of the node in the RHS and map to it:
			if (action.getType()==ActionType.NONE) {
				GraphEditingHelper.copyNode(node, rule.getRhs(), rule.getMappings(), false);
			}
			
			// For CREATE actions, move the node to the RHS:
			else if (action.getType()==ActionType.CREATE) {
				GraphEditingHelper.moveNode(node, rule.getRhs(), rule.getMappings(), false);
			}
			
			// For FORBID actions, move the node to the NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ElementActionHelper.getOrCreateNAC(action, rule);
				GraphEditingHelper.moveNode(node, nac.getConclusion(), nac.getMappings(), false);
			}
			
		}		
		
		// Current action type = FORBID?
		else if (current.getType()==ActionType.FORBID) {
			
			// We know that the node is contained in a NAC and that it has no origin in the LHS.
			NestedCondition nac = (NestedCondition) graph.eContainer();
			
			// We move the node to the LHS in any case:
			GraphEditingHelper.moveNode(node, rule.getLhs(), nac.getMappings(), true);
			
			// For NONE actions, create a copy in the RHS as well:
			if (action.getType()==ActionType.NONE) {
				GraphEditingHelper.copyNode(node, rule.getRhs(), rule.getMappings(), false);
			}

			// For FORBID actions, move the node to the new NAC:
			else if (action.getType()==ActionType.FORBID) {
				NestedCondition target = ElementActionHelper.getOrCreateNAC(action, rule);
				GraphEditingHelper.moveNode(node, target.getConclusion(), target.getMappings(), false);
			}
			
			// Delete the NAC is it became empty or trivial due to the current change.
			if (HenshinNACUtil.isTrivialNAC(nac)) {
				HenshinNACUtil.removeNAC(rule, nac);
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
		}
		else {
			
			// Get the mappings:
			EObject container = node.getGraph().eContainer();
			EList<Mapping> mappings = null;
			
			if (container instanceof Rule) {
				mappings = ((Rule) container).getMappings();
			}
			else if (container instanceof NestedCondition) {
				mappings = ((NestedCondition) container).getMappings();
			}
			else {
				throw new RuntimeException("Graph neither contained in a Rule nor in a NestedCondition");
			}
			
			// Find the origin in the LHS:
			return HenshinMappingUtil.getNodeOrigin(node, mappings);	
			
		}
		
	}

}
