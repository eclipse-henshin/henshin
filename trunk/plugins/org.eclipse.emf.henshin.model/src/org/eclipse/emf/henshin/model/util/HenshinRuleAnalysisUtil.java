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
