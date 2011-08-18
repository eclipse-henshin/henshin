/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Utility methods for analyzing Henshin transformation rules.
 * 
 * @author Christian Krause
 */
public class HenshinRuleAnalysisUtil {
	
	/**
	 * Check whether a rule is adding nodes.
	 * 
	 * @param rule
	 *            Rule to be checked.
	 * @return <code>true</code> if the rule adds nodes.
	 */
	public static boolean isAddingNodes(Rule rule) {
		// Check if any of the nodes in the RHS is not the image of a mapping.
		for (Node node : rule.getRhs().getNodes()) {
			if (HenshinMappingUtil.getNodeOrigin(node, rule.getMappings()) == null) return true;
		}
		return false;
	}
	
	/**
	 * Check whether a rule is deleting nodes.
	 * 
	 * @param rule
	 *            Rule to be checked.
	 * @return <code>true</code> if the rule deletes nodes.
	 */
	public static boolean isDeletingNodes(Rule rule) {
		// Check if any of the nodes in the LHS is not mapped to the RHS.
		for (Node node : rule.getLhs().getNodes()) {
			if (HenshinMappingUtil.getNodeImage(node, rule.getRhs(), rule.getMappings()) == null)
				return true;
		}
		return false;
	}
	
	/**
	 * Check whether a rule is adding edges.
	 * 
	 * @param rule
	 *            Rule to be checked.
	 * @return <code>true</code> if the rule adds edges.
	 */
	public static boolean isAddingEdges(Rule rule) {
		// Check if any of the edges in the RHS is not the image of a mapping.
		for (Edge edge : rule.getRhs().getEdges()) {
			if (HenshinMappingUtil.getEdgeOrigin(edge, rule.getMappings()) == null) return true;
		}
		return false;
	}
	
	/**
	 * Check whether a rule is deleting edges.
	 * 
	 * @param rule
	 *            Rule to be checked.
	 * @return <code>true</code> if the rule deletes edges.
	 */
	public static boolean isDeletingEdges(Rule rule) {
		// Check if any of the nodes in the LHS is not mapped to the RHS.
		for (Edge edge : rule.getLhs().getEdges()) {
			if (HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings()) == null)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if the given edge represents a 'deletion' edge. This is the case,
	 * if it is contained in a LHS and if there is no corresponding image edge
	 * in the RHS.<br>
	 * 
	 * @param edge
	 * @return true if the edge could be identified to be a 'deletion' edge. In
	 *         every other case this method returns false.
	 */
	public static boolean isDeletionEdge(Edge edge) {
		if (edge.getSource() != null && edge.getTarget() != null && edge.getGraph() != null
				&& edge.getGraph().getContainerRule() != null) {
			Rule rule = edge.getGraph().getContainerRule();
			return isLHS(edge.getGraph())
					&& (HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings()) == null);
		} else
			return false;
	}// isDeletionEdge
	
	/**
	 * Checks if the given edge represents a 'creation' edge. This is the case,
	 * if it is contained in a RHS and if there is no corresponding origin edge
	 * in the LHS.
	 * 
	 * @param edge
	 * @return true if the edge could be identified to be a 'creation' edge. In
	 *         every other case this method returns false.
	 */
	public static boolean isCreationEdge(Edge edge) {
		if (edge.getSource() != null && edge.getTarget() != null && edge.getGraph() != null
				&& edge.getGraph().getContainerRule() != null) {
			Rule rule = edge.getGraph().getContainerRule();
			return isRHS(edge.getGraph())
					&& (HenshinMappingUtil.getEdgeOrigin(edge, rule.getMappings()) == null);
		} else
			return false;
	}// isCreationEdge
	
	/**
	 * Check whether a rule is changing any attributes.
	 * 
	 * @param rule
	 *            Rule to be checked.
	 * @return <code>true</code> if the rule changes attributes.
	 */
	public static boolean isChangingAttributes(Rule rule) {
		for (Node node : rule.getRhs().getNodes()) {
			for (Attribute attribute : node.getAttributes()) {
				Attribute origin = HenshinMappingUtil.getAttributeOrigin(attribute,
						rule.getMappings());
				if ((origin == null) || !valueEquals(attribute.getValue(), origin.getValue()))
					return true;
			}
		}
		return false;
	}
	
	/*
	 * Check if to attribute values are equal.
	 */
	static boolean valueEquals(String v1, String v2) {
		if (v1 == null) return (v2 == null);
		if (v2 == null) return false;
		return v1.trim().equals(v2.trim());
	}
	
	/**
	 * Checks whether a rule is making any changes when applied. A rule makes
	 * changes if it either adds or removes nodes or edges, or if it changes
	 * node attributes.
	 * 
	 * @param rule
	 *            Rule to be checked.
	 * @return <code>true</code> if it makes changes.
	 */
	public static boolean isChanging(Rule rule) {
		return isAddingNodes(rule) || isDeletingNodes(rule) || isAddingEdges(rule)
				|| isDeletingEdges(rule) || isChangingAttributes(rule);
	}
	
	/**
	 * @param graph
	 * @return whether the {@link Graph} is a LHS of a {@link Rule}
	 */
	public static boolean isLHS(Graph graph) {
		return (graph.eContainer() != null) && (graph.eContainer() instanceof Rule)
				&& (graph.getContainerRule().getLhs() == graph);
		
	}
	
	/**
	 * Checks whether the given graph is part of an LHS in the sense that it
	 * might be the lhs itself or a nested conditions contained in the lhs. a
	 * nested condition might be even part of a formula.
	 * 
	 * @param graph
	 * @return
	 */
	public static boolean isLHSPart(Graph graph) {
		boolean result = true;
		EObject eobject = graph;
		while ((eobject.eContainer() != null) && !(eobject.eContainer() instanceof Rule)) {
			eobject = eobject.eContainer();
		}// while
		if (eobject instanceof Graph) {
			result = isLHS((Graph) eobject);
		}// if
		return result;
	}
	
	/**
	 * @param graph
	 * @return whether the {@link Graph} is a RHS of a {@link Rule}
	 */
	public static boolean isRHS(Graph graph) {
		return (graph.eContainer() != null) && (graph.eContainer() instanceof Rule)
				&& (graph.getContainerRule().getRhs() == graph);
	}
	
	/**
	 * @param graph
	 * @return whether the {@link Graph} is the conclusion of a
	 *         {@link NestedCondition}
	 */
	public static boolean isConclusion(Graph graph) {
		return (graph.eContainer() != null) && (graph.eContainer() instanceof NestedCondition);
	}
	
}
