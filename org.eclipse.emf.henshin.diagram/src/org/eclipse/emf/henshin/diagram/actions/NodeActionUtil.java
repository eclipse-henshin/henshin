package org.eclipse.emf.henshin.diagram.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.MappingUtil;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class NodeActionUtil {
	
	public static List<Node> getAllNodes(Rule rule) {
		List<Node> nodes = new ArrayList<Node>();
		nodes.addAll(rule.getLhs().getNodes());
		nodes.addAll(rule.getRhs().getNodes());
//		for (Graph nac : rule.getNacs()) {
//		}
		return nodes;
	}
	
	public static List<Node> getActionNodes(Rule rule) {
		return getActionNodes(rule, null);
	}
	
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
		
	public static ElementAction getNodeAction(Node node) {
		
		// Must be contained in a graph and rule:
		Graph graph = node.getGraph();
		if (graph==null) return null;
		
		// Graph must be part of a rule:
		if (!(graph.eContainer() instanceof Rule)) return null;
		Rule rule = (Rule) graph.eContainer();
		
		// LHS element?
		if (graph==rule.getLhs()) {
			
			// Check if it is mapped to the RHS:
			for (Node image : MappingUtil.getImages(node)) {
				if (image.getGraph()==rule.getRhs()) {
					return new ElementAction(ElementActionType.NONE);
				}
			}
			
			// Not mapped to the RHS, so it will be deleted:
			return new ElementAction(ElementActionType.DELETE);
			
		}
		
		// Otherwise find the origin in the LHS:
		Node origin = MappingUtil.getOrigin(node);
		
		if (origin==null) {
			
			// CREATE-action?
			if (rule.getRhs()==node.getGraph()) {
				return new ElementAction(ElementActionType.CREATE);			
			}

			// FORBID-action?
			//if (rule.getNacs().contains(node.getGraph())) {
			//	return new ElementAction(ElementActionType.FORBID);
			//}
			
		}
		
		return null;
		
	}
	
}
