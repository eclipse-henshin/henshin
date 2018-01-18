package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Minimizes a rule by cutting all context which is not necessary to perform the transformation.
 * 
 * @author Timo Kehrer, Manuel Ohrndorf
 */
public class RuleMinimizer {

	/**
	 * Removes all non-context (preserve) nodes from a rule.
	 * 
	 * @param module The Henshin Module.
	 */
	public static void reduceToMinimalRule(Rule rule) {

		// We identify all non-context nodes and <<preserve>> edges
		List<Node> nonContextNodes = new ArrayList<>();
		List<Edge> preserveEdges = new ArrayList<>();

		for (Iterator<EObject> iterator = rule.eAllContents(); iterator.hasNext();) {
			EObject element = iterator.next();

			// Nodes
			if (element instanceof Node) {
				Node node = (Node) element;

				if (node.getActionNode().getAction().getType().equals(Type.PRESERVE)) {
					if (node.getActionNode() != node) {
						Node lhsNode = node.getActionNode();
						Node rhsNode = node;

						if (!isNodeWithActionEdges(rhsNode, Type.CREATE) && !isNodeWithActionEdges(lhsNode, Type.DELETE)
								&& !isNodeWithChangingAttributes(lhsNode, rhsNode)) {

							nonContextNodes.add(lhsNode);
							nonContextNodes.add(rhsNode);
						}
					}
				}
			}

			// Edges
			if (element instanceof Edge) {
				Edge edge = (Edge) element;

				if (edge.getActionEdge().getAction().getType().equals(Type.PRESERVE)) {
					if (edge.getActionEdge() != edge) {
						Edge lhsEdge = edge.getActionEdge();
						Edge rhsEdge = edge;

						preserveEdges.add(lhsEdge);
						preserveEdges.add(rhsEdge);
					}
				}
			}

		}

		// Then, we delete all the identified elements
		for (Edge edge : preserveEdges) {
			deleteEdge(edge);
		}

		for (Node node : nonContextNodes) {
			deleteNode(node);
		}
	}

	/**
	 * @param node The node which will be deleted from its graph.
	 */
	private static void deleteNode(Node node) {
		// Clean-up mappings:
		node.getGraph().getRule().getMappings().stream()
				.filter(m -> ((m.getOrigin() == node) || (m.getImage() == node))).findFirst()
				.ifPresent(m -> EcoreUtil.remove(m));

		// Remove node from graph:
		EcoreUtil.remove(node);
	}

	/**
	 * @param edge The edge which will be deleted from its graph.
	 */
	private static void deleteEdge(Edge edge) {
		EcoreUtil.remove(edge);
		edge.getSource().getOutgoing().remove(edge);
		edge.getTarget().getIncoming().remove(edge);
	}

	/**
	 * Searches for the first incoming or outgoing edge of the given node having the given actionType. Returns
	 * <code> true </code> if there is such an edge; <code> false </code> otherwise.
	 * 
	 * @param node the node to test.
	 * @return <code> true </code> if there is an edge with the given actionType; <code> false </code> otherwise.
	 */
	private static boolean isNodeWithActionEdges(Node node, Type actionType) {

		for (Edge edge : node.getIncoming()) {
			if (edge.getActionEdge().getAction().getType().equals(actionType)) {
				return true;
			}
		}

		for (Edge edge : node.getOutgoing()) {
			if (edge.getActionEdge().getAction().getType().equals(actionType)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Searches for (X -> Y) attributes in a << preserve >> node (lhsNode/rhsNode pair).
	 * 
	 * Returns <code> true </code> if there is a (X -> Y) attribute, false otherwise
	 * 
	 */
	private static boolean isNodeWithChangingAttributes(Node lhsNode, Node rhsNode) {

		for (Attribute lhsAttribute : lhsNode.getAttributes()) {
			Attribute rhsAttribute = rhsNode.getAttribute(lhsAttribute.getType());
			if (rhsAttribute == null || !rhsAttribute.getValue().equals(lhsAttribute.getValue())) {
				return true;
			}
		}

		for (Attribute rhsAttribute : rhsNode.getAttributes()) {
			Attribute lhsAttribute = lhsNode.getAttribute(rhsAttribute.getType());
			if (lhsAttribute == null || !lhsAttribute.getValue().equals(rhsAttribute.getValue())) {
				return true;
			}
		}

		return false;
	}
}
