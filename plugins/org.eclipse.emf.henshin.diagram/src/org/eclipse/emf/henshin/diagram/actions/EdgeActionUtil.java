package org.eclipse.emf.henshin.diagram.actions;

import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;

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
	public static Action getEdgeActionType(Edge edge) {
		return InternalActionUtil.getAction(edge);
	}
	
	
	/**
	 * Get all edges in a rule that are associated with the given argument action.
	 * @param rule Rule.
	 * @return List of edges.
	 */
	public static List<Edge> getActionEdges(Rule rule, Action action) {
		return InternalActionUtil.getActionElements(rule, action, HenshinPackage.eINSTANCE.getGraph_Edges());
	}
	
	/**
	 * Get all edges in a rule that are associated with an arbitrary action.
	 * @param rule Rule.
	 * @return List of edges.
	 */
	public static List<Edge> getActionEdges(Rule rule) {
		return getActionEdges(rule, null);
	}
	
}
