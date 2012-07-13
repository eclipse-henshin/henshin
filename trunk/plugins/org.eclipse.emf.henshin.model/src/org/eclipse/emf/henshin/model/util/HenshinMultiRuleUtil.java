/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Provides static helper methods for retrieving nodes and edges related via multiMappings.
 * 
 * @author Gregor Bonifer
 *
 */
public class HenshinMultiRuleUtil {
	
	public static Collection<Node> getDependentNodes(Node node) {
		System.out.print("Dependent Nodes for " + node + ": ");
		if (node.getGraph().isLhs() || node.getGraph().isRhs()) {
			Collection<Node> result = new ArrayList<Node>();
			Rule rule = node.getGraph().getContainerRule();
			for (Rule mRule : rule.getMultiRules()) {
				Node imgNode = getDependentNodeInRule(node, mRule);
				if (imgNode != null)
					result.add(imgNode);
			}
			System.out.println(result);
			return result;
		}
		System.out.println("none");
		return Collections.emptyList();
	}
	
	public static Node getDependentNodeInRule(Node node, Rule rule) {
		for (Mapping m : rule.getMultiMappings())
			if (m.getOrigin() == node)
				return m.getImage();
		return null;
	}
	
	public static Collection<Graph> getDependentGraphs(Graph graph) {
		Collection<Graph> result = new ArrayList<Graph>();
		Rule rule = graph.getContainerRule();
		boolean isLeft = graph.isLhs();
		if (rule == null)
			return result;
		for (Rule mRule : rule.getMultiRules())
			result.add(isLeft ? mRule.getLhs() : mRule.getRhs());
		return result;
	}
	
	public static Collection<Edge> getDependentEdges(Edge edge) {
		Collection<Edge> result = new ArrayList<Edge>();
		if (edge.getGraph() == null || edge.getGraph().getContainerRule() == null)
			return result;
		Rule rule = edge.getGraph().getContainerRule();
		for (Rule dependentRule : rule.getMultiRules()) {
			Edge dependentEdge = getDependentEdgeInRule(edge, dependentRule);
			if (dependentEdge != null)
				result.add(dependentEdge);
		}
		return result;
	}
	
	public static Edge getDependentEdgeInRule(Edge edge, Rule rule) {
		Node dependentSource = getDependentNodeInRule(edge.getSource(), rule);
		Node dependentTarget = getDependentNodeInRule(edge.getTarget(), rule);
		for (Edge dependentEdgeCandidate : dependentSource.getOutgoing()) {
			if (dependentEdgeCandidate.getTarget() == dependentTarget
					&& dependentEdgeCandidate.getType() == edge.getType()) {
				return dependentEdgeCandidate;
			}
		}
		return null;
	}
	
	/**
	 * Check whether a rule is a trivial multi-rule. A multi-rule
	 * is trivial if all elements in its LHS/RHS have a pre-image
	 * in the kernel rule's LHS/RHS.
	 * @param multi Rule to be checked.
	 * @return <code>true</code> if it is trivial.
	 */
	public static boolean isTrivialMultiRule(Rule multi) {
		
		// Create a list of all elements in the multi-rule:
		List<Node> nodes = new ArrayList<Node>();
		nodes.addAll(multi.getLhs().getNodes());
		nodes.addAll(multi.getRhs().getNodes());
		
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(multi.getLhs().getEdges());
		edges.addAll(multi.getRhs().getEdges());
		
		// Check if there have origins.
		for (Node node : nodes) {
			if (multi.getMultiMappings().getOrigin(node)==null) {
				return false;
			}
		}
		for (Edge edge : edges) {
			if (multi.getMultiMappings().getOrigin(edge)==null) {
				return false;
			}
		}
		
		// No reason why it shouldn't be trivial.
		return true;
	}

	public static void removeTrivialMultiRules(Rule kernel) {

		// Delete trivial multi-rules:
		for (int i=0; i<kernel.getMultiRules().size(); i++) {
			Rule multi = kernel.getMultiRules().get(i);
			if (isTrivialMultiRule(multi)) {
				kernel.getMultiRules().remove(i--);
			}
		}		
	}

}
