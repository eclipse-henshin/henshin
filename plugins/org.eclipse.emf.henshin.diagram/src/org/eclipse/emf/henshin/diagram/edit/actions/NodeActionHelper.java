package org.eclipse.emf.henshin.diagram.edit.actions;

import java.util.List;

import org.eclipse.emf.henshin.diagram.edit.maps.MapEditor;
import org.eclipse.emf.henshin.diagram.edit.maps.NodeMapEditor;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class NodeActionHelper extends AbstractActionHelper<Node,Rule> {
	
	/**
	 * Static instance.
	 */
	public static final NodeActionHelper INSTANCE = new NodeActionHelper();
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.ActionHelper#getActionElements(java.lang.Object, org.eclipse.emf.henshin.diagram.edit.actions.Action)
	 */
	public List<Node> getActionElements(Rule rule, Action action) {
		List<Node> candidates = ActionElementFinder.getRuleElementCandidates(rule, action, HenshinPackage.eINSTANCE.getGraph_Nodes());
		return filterElementsByAction(candidates, action);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.edit.actions.AbstractActionHelper#getMapEditor(org.eclipse.emf.henshin.model.Graph)
	 */
	@Override
	protected MapEditor<Node> getMapEditor(Graph target) {
		return new NodeMapEditor(target);
	}
	
	/**
	 * For an arbitrary node in a rule graph, find the corresponding action node.
	 * @param node Some node.
	 * @return The corresponding action node.
	 */
	public Node getActionNode(Node node) {
		return ActionElementFinder.getActionElement(node, INSTANCE);
	}

	/**
	 * For an arbitrary node in a rule graph, find the corresponding Lhs node.
	 * @param node Some node.
	 * @return The corresponding Lhs node.
	 */
	public Node getLhsNode(Node node) {
		Graph lhs = node.getGraph().getContainerRule().getLhs();
		if (node.getGraph()==lhs) {
			return node;
		}
		Node opposite = getActionNode(node);
		if (opposite.getGraph()==lhs) {
			return opposite;
		}
		// No corresponding Lhs node:
		return null;
	}
	
}