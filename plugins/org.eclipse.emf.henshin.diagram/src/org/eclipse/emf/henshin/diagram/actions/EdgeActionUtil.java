package org.eclipse.emf.henshin.diagram.actions;

import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * Static methods for determining and changing actions for edges.
 * @generated NOT
 * @author Christian Krause
 */
public class EdgeActionUtil {
	
	/**
	 * Determine the action for an edge. If this returns <code>null</code>
	 * the node is not considered to be a proper action edge and hence
	 * should not be displayed in an integrated rule view.
	 * @param edge Edge.
	 * @return Action or <code>null</code>.
	 */
	public static Action getEdgeAction(Edge edge) {
		return ElementActionHelper.getAction(edge);
	}
	
	/**
	 * Set the action for an edge.
	 * @param edge Edge.
	 * @param action Action.
	 */
	public static void setEdgeAction(Edge edge, Action action) {
		
		// Get the current action.
		Action current = getEdgeAction(edge);
		if (action.equals(current)) return;
		
		// Get the container graph and rule.
		Graph graph = edge.getGraph();
		Rule rule = graph.getContainerRule();
		
		// Current action type = NONE?
		if (current.getType()==ActionType.NONE) {
			
			// We know that the edge is contained in the LHS and that it is mapped to an edge in the RHS.
			
			// Find the the image in the RHS:
			Edge image = HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings());
			Node source = image.getSource();
			Node target = image.getTarget();
			
			// We delete the edge image:
			graph.removeEdge(image);
			
			// For CREATE actions, move the edge to the RHS:
			if (action.getType()==ActionType.CREATE) {
				edge.setSource(source);
				edge.setTarget(target);
				edge.setGraph(rule.getRhs());
			}
			
		}
		
		// Current action type = CREATE?
		if (current.getType()==ActionType.CREATE) {
			
			// We know that the node is contained in the RHS and that it is not an image of a mapping.
			
			// For DELETE actions, move the edge to the LHS:
			if (action.getType()==ActionType.DELETE) {
				
			}
			
			// For NONE actions, move the edge to the LHS and create a copy in the RHS:
			else if (action.getType()==ActionType.NONE) {
				
			}
			
		}

		// Current action type = DELETE?
		if (current.getType()==ActionType.DELETE) {
			
			// We know that the node is contained in the LHS and that it has no image in the RHS.
			
			// For NONE actions, create a copy of the edge in the RHS:
			if (action.getType()==ActionType.NONE) {
			}
			
			// For CREATE actions, move the edge to the RHS:
			if (action.getType()==ActionType.CREATE) {
			}
		}		
		
	}
	
	/**
	 * Get all edges in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of edges.
	 */
	public static List<Edge> getActionEdges(Rule rule, Action action) {
		return ElementActionHelper.getActionElements(rule, action, HenshinPackage.eINSTANCE.getGraph_Edges());
	}
	
}
