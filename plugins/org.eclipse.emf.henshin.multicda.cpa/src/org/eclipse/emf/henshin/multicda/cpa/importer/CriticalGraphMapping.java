/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.importer;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.henshin.model.Node;

import agg.xt_basis.GraphObject;

/**
 * A class for the central management of associated nodes between the AGG overlap graph, also called minimal model of
 * AGG and the nodes in the two Henshin rules forming the critical pair.
 * 
 * @author Kristopher Born
 *
 */
public class CriticalGraphMapping {

	/**
	 * A list containing the separate Mapping instances. One for each Node of the AGG overlap graph.
	 */
	private List<Mapping> mappings;

	/**
	 * Default constructor.
	 */
	public CriticalGraphMapping() {
		mappings = new LinkedList<CriticalGraphMapping.Mapping>();
	}

	/**
	 * Inner class for the mapping, one for each Node of the AGG overlap graph.
	 * 
	 * @author Kristopher Born
	 *
	 */
	private class Mapping {
		private GraphObject criticalGraphNode;
		private Node firstRuleNode;
		private Node secondRuleNode;
		private List<Node> firstRuleNodesOfNestedGraphs;
		private List<Node> secondRuleNodesOfNestedGraphs;

		public Mapping() {
			firstRuleNodesOfNestedGraphs = new LinkedList<Node>();
			secondRuleNodesOfNestedGraphs = new LinkedList<Node>();
		}
	}

	/**
	 * Add the mapping of the <code>Node</code> of the first Henshin rule to a common <code>GraphNode</code>.
	 * 
	 * @param criticalGraphNode The GraphNode of AGG to which the node of the first rule shall be mapped.
	 * @param firstRuleNode The <code>Node</code> of the first rule, which shall be mapped.
	 * @return <code>false</code> if there already exists a mapping for the <code>firstRuleNode</code> of this
	 *         <code>criticalGraphNode</code>.
	 */
	public boolean addFirstRuleMapping(GraphObject criticalGraphNode, Node firstRuleNode) {
		// in case of allreday existing mapping for the GraphObject
		for (Mapping mapping : mappings) {
			if (mapping.criticalGraphNode == criticalGraphNode) {
				if (mapping.firstRuleNode == null) {
					mapping.firstRuleNode = firstRuleNode;
					return true;
				}
				// in case of an already existing Henshin Node for the AGG GraphNode:
				if (mapping.firstRuleNode != null)
					return false;
			}
		}
		// in case of no mapping for the GraphObject yet:
		Mapping newMapping = new Mapping();
		newMapping.criticalGraphNode = criticalGraphNode;
		newMapping.firstRuleNode = firstRuleNode;
		mappings.add(newMapping);
		return true;
	}

	/**
	 * Add the mapping of the <code>Node</code> of the second Henshin rule to a common <code>GraphNode</code>.
	 * 
	 * @param criticalGraphNode The GraphNode of AGG to which the node of the second rule shall be mapped.
	 * @param secondRuleNode The <code>Node</code> of the second rule, which shall be mapped.
	 * @return <code>false</code> if there already exists a mapping for the <code>secondRuleNode</code> of this
	 *         <code>criticalGraphNode</code>.
	 */
	public boolean addSecondRuleMapping(GraphObject criticalGraphNode, Node secondRuleNode) {
		// in case of allreday existing mapping for the GraphObject
		for (Mapping mapping : mappings) {
			if (mapping.criticalGraphNode == criticalGraphNode) {
				if (mapping.secondRuleNode == null) {
					mapping.secondRuleNode = secondRuleNode;
					return true;
				}
				// in case of an already existing Henshin Node for the AGG GraphNode:
				if (mapping.secondRuleNode != null)
					return false;
			}
		}
		// in case of no mapping for the GraphObject yet:
		Mapping newMapping = new Mapping();
		newMapping.criticalGraphNode = criticalGraphNode;
		newMapping.secondRuleNode = secondRuleNode;
		mappings.add(newMapping);
		return true;
	}

	/**
	 * Returns the Henshin node of the first rule to the common associated AGG graph node.
	 * 
	 * @param criticalGraphNode The common AGG graph node.
	 * @return The Henshin node of the first rule to the associated common AGG graph node or <code>NULL</code> if the
	 *         mapping does not exist.
	 */
	public Node getFirstRuleNode(GraphObject criticalGraphNode) {
		for (Mapping mapping : mappings) {
			if (mapping.criticalGraphNode == criticalGraphNode)
				return mapping.firstRuleNode;
		}
		return null;
	}

	/**
	 * Returns the Henshin node of the second rule to the common associated AGG graph node.
	 * 
	 * @param criticalGraphNode The common AGG graph node.
	 * @return The Henshin node of the second rule to the associated common AGG graph node or <code>NULL</code> if the
	 *         mapping does not exist.
	 */
	public Node getSecondRuleNode(GraphObject criticalGraphNode) {
		for (Mapping mapping : mappings) {
			if (mapping.criticalGraphNode == criticalGraphNode)
				return mapping.secondRuleNode;
		}
		return null;
	}

	/**
	 * Assigns a <code>Node</code> of an nested <code>Graph</code> to an already processed <code>Node</code> of the main
	 * <code>Graph</code> of the first <code>Rule</code>.
	 * 
	 * @param nodeOfAllreadyProcessedGraph A Henshin <code>Node</code> that is already processed.
	 * @param nodeOfNestedGraph A Henshin <code>Node</code> of a nested <code>Graph</code>.
	 */
	public void addFirstRuleNodesOfNestedGraphs(Node nodeOfAllreadyProcessedGraph, Node nodeOfNestedGraph) {
		for (Mapping mapping : mappings) {
			if (mapping.firstRuleNode == nodeOfAllreadyProcessedGraph)
				mapping.firstRuleNodesOfNestedGraphs.add(nodeOfNestedGraph);
		}
	}

	/**
	 * Assigns a <code>Node</code> of an nested <code>Graph</code> to an already processed <code>Node</code> of the main
	 * <code>Graph</code> of the second <code>Rule</code>.
	 * 
	 * @param nodeOfAllreadyProcessedGraph A Henshin <code>Node</code> that is already processed.
	 * @param nodeOfNestedGraph A Henshin <code>Node</code> of a nested <code>Graph</code>.
	 */
	public void addSecondRuleNodesOfNestedGraphs(Node nodeOfAllreadyProcessedGraph, Node nodeOfNestedGraph) {
		for (Mapping mapping : mappings) {
			if (mapping.secondRuleNode == nodeOfAllreadyProcessedGraph)
				mapping.secondRuleNodesOfNestedGraphs.add(nodeOfNestedGraph);
		}
	}

	/**
	 * Returns a List of <code>Node</code>s of the first Henshin rule, which belong to the same AGG graph node.
	 * 
	 * @param criticalGraphNode The AGG graph node for which the belonging Henshin <code>Node</code>s are requested.
	 * @return A List of <code>Node</code>s of the first Henshin rule, which belong to the same AGG graph nodeor
	 *         <code>NULL</code> if the mapping does not exist.
	 */
	public List<Node> getFirstRuleNodesOfNestedGraphs(GraphObject criticalGraphNode) {
		for (Mapping mapping : mappings) {
			if (mapping.criticalGraphNode == criticalGraphNode)
				return mapping.firstRuleNodesOfNestedGraphs;
		}
		return null;
	}

	/**
	 * Returns a List of <code>Node</code>s of the second Henshin rule, which belong to the same AGG graph node.
	 * 
	 * @param criticalGraphNode The AGG graph node for which the belonging Henshin <code>Node</code>s are requested.
	 * @return A List of <code>Node</code>s of the second Henshin rule, which belong to the same AGG graph nodeor
	 *         <code>NULL</code> if the mapping does not exist.
	 */
	public List<Node> getSecondRuleNodesOfNestedGraphs(GraphObject criticalGraphNode) {
		for (Mapping mapping : mappings) {
			if (mapping.criticalGraphNode == criticalGraphNode)
				return mapping.secondRuleNodesOfNestedGraphs;
		}
		return null;
	}

}
