package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinNACUtil;

/**
 * Static methods for determining and changing actions for attributes.
 * @generated NOT
 * @author Christian Krause
 */
public class AttributeActionUtil {
	
	/**
	 * Determine the action for an attribute. If this returns <code>null</code>
	 * the attribute is not considered to be a proper action attribute and hence
	 * should not be displayed in an integrated rule view.
	 * @param attribute Attribute
	 * @return Action or <code>null</code>.
	 */
	public static Action getAttributeAction(Attribute attribute) {

		// Compute the action:
		Action action = ElementActionHelper.getAction(attribute);
		if (action==null) return null;

		// Actions of the parent node overrule:
		Action nodeAction = NodeActionUtil.getNodeAction(attribute.getNode());
		if (nodeAction!=null && nodeAction.getType()!=ActionType.NONE) {
			return new Action(ActionType.NONE);
		}
		
		return action;
		
	}
	
	/**
	 * Set the action for an attribute.
	 * @param attribute Attribute.
	 * @param action Action.
	 */
	public static void setAttributeAction(Attribute attribute, Action action) {
		
		// Get the current action.
		Action current = getAttributeAction(attribute);
		if (action.equals(current)) return;
		
		// Get the container node, graph, rule.
		Node node = attribute.getNode();
		Graph graph = node.getGraph();
		Rule rule = graph.getContainerRule();
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the attribute is contained in the LHS and that it is mapped to an attribute in the RHS.
			
			// Find the the image in the RHS:
			Attribute image = HenshinMappingUtil.getAttributeImage(attribute, rule.getRhs(), rule.getMappings());
			Node nodeImage = image.getNode();
			
			// For CREATE actions, move the attribute to the RHS:
			if (action.getType()==ActionType.CREATE) {
				nodeImage.getAttributes().remove(image);
				nodeImage.getAttributes().add(attribute);
			}
			
			// For DELETE actions, delete the attribute image in the RHS:
			if (action.getType()==ActionType.DELETE) {
				node.getAttributes().remove(attribute);
			}
			
			// For FORBID actions, move the attribute to the NAC, and remove it from the RHS:
			if (action.getType()==ActionType.FORBID) {
				NestedCondition nac = ElementActionHelper.getOrCreateNAC(action, rule);
				Node target = HenshinMappingUtil.getNodeImage(nodeImage, nac.getConclusion(), nac.getMappings());
				if (target==null) {
					GraphMorphismEditor editor = new GraphMorphismEditor(rule.getLhs(), nac.getConclusion(), nac.getMappings());
					target = editor.copyNode(node);
				}
				attribute.setNode(target);
				nodeImage.getAttributes().remove(image);
			}
			
		}
		
		// Current action type = CREATE?
		else if (current.getType()==ActionType.CREATE) {
			
			// We know that the attribute is contained in the RHS and that it is not an image of a mapping.
			Node nodeOrigin = HenshinMappingUtil.getNodeOrigin(node, rule.getMappings());
			if (nodeOrigin==null) return;
			
			// Move the attribute to the node in the LHS:
			attribute.setNode(nodeOrigin);
			
			// For NONE actions, copy the attribute to the RHS again:
			if (action.getType()==ActionType.NONE) {
				Attribute copy = (Attribute) EcoreUtil.copy(attribute);
				copy.setNode(node);
			}
			
		}

		// Current action type = DELETE?
		else if (current.getType()==ActionType.DELETE) {
			
			// We know that the attribute is contained in the LHS and that it has no image in the RHS.
			Node nodeImage = HenshinMappingUtil.getNodeImage(node, rule.getRhs(), rule.getMappings());
			if (nodeImage==null) return;
			
			// For NONE actions, create a copy of the attribute in the RHS node:
			if (action.getType()==ActionType.NONE) {
				Attribute copy = (Attribute) EcoreUtil.copy(attribute);
				copy.setNode(nodeImage);
			}
			
			// For CREATE actions, move the attribute to the RHS:
			else if (action.getType()==ActionType.CREATE) {
				attribute.setNode(nodeImage);
			}
		}		
		
		// Current action type = FORBID?
		else if (current.getType()==ActionType.FORBID) {
			
			// We know that the attribute's node is contained in a NAC and that it has no origin in the LHS.
			NestedCondition nac = (NestedCondition) graph.eContainer();
			
			// For NONE actions, create a copy in the LHS as well:
			if (action.getType()==ActionType.NONE) {
				Node target = HenshinMappingUtil.getNodeOrigin(node, nac.getMappings());
				if (target==null) return;
				target.getAttributes().add((Attribute) EcoreUtil.copy(attribute));
			}
			
			// Delete the NAC is it became empty or trivial due to the current change.
			if (HenshinNACUtil.isTrivialNAC(nac)) {
				HenshinNACUtil.removeNAC(rule, nac);
			}
			
		}
		
	}
	
	/**
	 * Get all attributes in a node that are associated with the given argument action.
	 * @param node Node.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of attributes.
	 */
	public static List<Attribute> getActionAttributes(Node node, Action action) {
		
		// Find the proper node in the LHS:
		Graph graph = node.getGraph();
		Rule rule = graph.getContainerRule();
		Node lhsNode = findLHSNode(node);
		
		// Lets go...
		ActionType type = (action==null) ? null : action.getType();
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		// We always need an lhsNode.
		if (lhsNode==null) {
			return attributes;
		}
		
		if (type==null || type==ActionType.NONE) {
			attributes.addAll(lhsNode.getAttributes());
		}
		if (type==null || type==ActionType.CREATE) {
			Node image = HenshinMappingUtil.getNodeImage(lhsNode, rule.getRhs(), rule.getMappings());
			if (image!=null) {
				for (Attribute create : image.getAttributes()) {
					if (node.findAttributeByType(create.getType())==null) {
						attributes.add(create);
					}
				}
			}
		}
		if (type==null || type==ActionType.DELETE) {
			Node image = HenshinMappingUtil.getNodeImage(lhsNode, rule.getRhs(), rule.getMappings());
			if (image!=null) {
				for (Attribute delete : node.getAttributes()) {
					if (image.findAttributeByType(delete.getType())==null) {
						attributes.add(delete);
					}
				}
			}
		}
		if (type==null || type==ActionType.FORBID) {
			for (NestedCondition nac : HenshinNACUtil.getAllNACs(rule)) {
				Node image = HenshinMappingUtil.getNodeImage(lhsNode, nac.getConclusion(), nac.getMappings());
				if (image!=null) {
					for (Attribute forbid : image.getAttributes()) {
						if (node.findAttributeByType(forbid.getType())==null) {
							attributes.add(forbid);
						}
					}
				}				
			}
		}
		
		return attributes;
		
	}
	
	public static Node findLHSNode(Node node) {
		Graph graph = node.getGraph();
		Rule rule = graph.getContainerRule();
		if (graph==rule.getLhs()) {
			return node;
		}
		if (graph==rule.getRhs()) {
			return HenshinMappingUtil.getNodeOrigin(node, rule.getMappings());
		}
		if (graph.eContainer() instanceof NestedCondition) {
			NestedCondition nested = (NestedCondition) graph.eContainer();
			return HenshinMappingUtil.getNodeOrigin(node, nested.getMappings());
		}
		return null;
	}

}
