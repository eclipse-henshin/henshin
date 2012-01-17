package org.eclipse.emf.henshin.model.actions;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.internal.AttributeActionHelper;
import org.eclipse.emf.henshin.model.actions.internal.EdgeActionHelper;
import org.eclipse.emf.henshin.model.actions.internal.NodeActionHelper;

/**
 * Helper class for editing actions.
 * @author Christian Krause
 */
public class HenshinActionHelper {
	
	/**
	 * Get the action of a node.
	 * @param node The Node.
	 * @return The node's action or <code>null</code>.
	 */
	public static Action getAction(Node node) {
		return NodeActionHelper.INSTANCE.getAction(node);
	}

	/**
	 * Get the action of an edge.
	 * @param edge The edge.
	 * @return The edge's action or <code>null</code>.
	 */
	public static Action getAction(Edge edge) {
		return EdgeActionHelper.INSTANCE.getAction(edge);
	}

	/**
	 * Get the action of an attribute.
	 * @param attribute The attribute.
	 * @return The attributes's action or <code>null</code>.
	 */
	public static Action getAction(Attribute attribute) {
		return AttributeActionHelper.INSTANCE.getAction(attribute);
	}

	/**
	 * Set the action of a node.
	 * @param node The node.
	 * @param action The action.
	 */
	public static void setAction(Node node, Action action) {
		NodeActionHelper.INSTANCE.setAction(node, action);
	}

	/**
	 * Set the action of an edge.
	 * @param edge The edge.
	 * @param action The action.
	 */
	public static void setAction(Edge edge, Action action) {
		EdgeActionHelper.INSTANCE.setAction(edge, action);
	}
	/**
	 * Set the action of an attribute.
	 * @param attribute The attribute.
	 * @param action The action.
	 */
	public static void setAction(Attribute attribute, Action action) {
		AttributeActionHelper.INSTANCE.setAction(attribute, action);
	}

	/**
	 * Get all nodes in a rule that are associated with the given action.
	 * @param rule The container rule.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of nodes.
	 */
	public static List<Node> getActionNodes(Rule rule, Action action) {
		return NodeActionHelper.INSTANCE.getActionElements(rule, action);
	}

	/**
	 * Get all edges in a rule that are associated with the given action.
	 * @param rule The container rule.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of edges.
	 */
	public static List<Edge> getActionEdges(Rule rule, Action action) {
		return EdgeActionHelper.INSTANCE.getActionElements(rule, action);
	}

	/**
	 * Get all attributes in a node that are associated with the given action.
	 * @param node The container node.
	 * @param action Action or <code>null</code> for any action.
	 * @return List of attributes.
	 */
	public static List<Attribute> getActionAttributes(Node node, Action action) {
		return AttributeActionHelper.INSTANCE.getActionElements(node, action);
	}

	/**
	 * For an arbitrary node in a rule graph, find the corresponding action node.
	 * @param node Some node.
	 * @return The corresponding action node.
	 */
	public static Node getActionNode(Node node) {
		return NodeActionHelper.INSTANCE.getActionNode(node);
	}

	/**
	 * For an arbitrary node in a rule graph, find the corresponding Lhs node.
	 * @param node Some node.
	 * @return The corresponding Lhs node.
	 */
	public static Node getLhsNode(Node node) {
		return NodeActionHelper.INSTANCE.getLhsNode(node);
	}

	/**
	 * Check if an edge can be created.
	 */
	public static boolean canCreateEdge(Node source, Node target, EReference type) {
		return EdgeActionHelper.INSTANCE.canCreateEdge(source, target, type);
	}

	/**
	 * Create an edge between two action nodes.
	 */
	public static Edge createEdge(Node source, Node target, EReference type) {
		return EdgeActionHelper.INSTANCE.createEdge(source, target, type);
	}

}
