package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.common.util.ModelHelper;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class RuleInfo {
	private List<Node> createdNodes;
	private List<Node> deletedNodes;
	private List<Node> preservedNodes;
	private List<Edge> createdEdges;
	private List<Edge> deletedEdges;
	private List<Attribute> attributeChanges;

	public RuleInfo(Rule rule) {
		createdNodes = new ArrayList<Node>();
		createdEdges = new ArrayList<Edge>();
		deletedEdges = new ArrayList<Edge>();
		deletedNodes = new ArrayList<Node>();
		attributeChanges = new ArrayList<Attribute>();
		preservedNodes = new ArrayList<Node>();

		for (Node node : rule.getLhs().getNodes()) {
			if (!ModelHelper.isNodeMapped(rule.getMappings(), node)) {
				deletedNodes.add(node);
			}
		}

		for (Node node : rule.getRhs().getNodes()) {
			if (!ModelHelper.isNodeMapped(rule.getMappings(), node)) {
				createdNodes.add(node);
			} else {
				preservedNodes.add(node);
			}

			for (Attribute attribute : node.getAttributes()) {
				attributeChanges.add(attribute);
			}
		}

		for (Edge edge : rule.getLhs().getEdges()) {
			if (!ModelHelper.isEdgeMapped(rule.getMappings(), edge)) {
				deletedEdges.add(edge);
			}
		}

		for (Edge edge : rule.getRhs().getEdges()) {
			if (!ModelHelper.isEdgeMapped(rule.getMappings(), edge)) {
				createdEdges.add(edge);
			}
		}

	}

	/**
	 * @return the createdNodes
	 */
	public List<Node> getCreatedNodes() {
		return createdNodes;
	}

	/**
	 * @return the preservedNodes
	 */
	public List<Node> getPreservedNodes() {
		return preservedNodes;
	}

	/**
	 * @return the createdEdges
	 */
	public List<Edge> getCreatedEdges() {
		return createdEdges;
	}

	/**
	 * @return the deletedEdges
	 */
	public List<Edge> getDeletedEdges() {
		return deletedEdges;
	}

	/**
	 * @return the attributeChanges
	 */
	public List<Attribute> getAttributeChanges() {
		return attributeChanges;
	}

	/**
	 * @return the deletedNodes
	 */
	public List<Node> getDeletedNodes() {
		return deletedNodes;
	}
}
