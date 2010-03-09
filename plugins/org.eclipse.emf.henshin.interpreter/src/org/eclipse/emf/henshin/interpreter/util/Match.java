/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.internal.matching.Solution;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Encapsulates information about a single match from a Henshin rule into an EMF
 * instance.
 */
public class Match {
	// the rule which will be matched
	private Rule rule;

	// mapping of LHS or RHS nodes
	private Map<Node, EObject> nodeMapping;

	// variable assignments
	private Map<String, Object> parameterMapping;

	public Match(Rule rule, Map<String, Object> parameterMapping,
			Map<Node, EObject> nodeMapping) {
		this.parameterMapping = parameterMapping;
		this.nodeMapping = nodeMapping;
		this.rule = rule;
	}

	public Match(Rule rule, Solution solution, Map<Node, Variable> node2variable) {
		this.parameterMapping = solution.getParameterMatches();
		this.nodeMapping = new HashMap<Node, EObject>();

		if (solution != null) {
			Map<Variable, EObject> objectMatch = solution.getObjectMatches();
			for (Node node : rule.getLhs().getNodes()) {
				Variable var = node2variable.get(node);
				EObject eObject = objectMatch.get(var);
				nodeMapping.put(node, eObject);
			}
		}

		this.rule = rule;
	}

	/**
	 * @return the parameterMapping
	 */
	public Map<String, Object> getParameterMapping() {
		return parameterMapping;
	}

	/**
	 * @return the nodeMapping
	 */
	public Map<Node, EObject> getNodeMapping() {
		return nodeMapping;
	}

	/**
	 * Checks whether this match overlaps with another. The second match can be
	 * from a different rule.
	 * 
	 * @param match
	 *            A second match to check against.
	 * @return true, if both matches have common targets
	 */
	public boolean overlapsWith(Match match) {
		List<EObject> thistargets = new ArrayList<EObject>(nodeMapping.values());
		List<EObject> matchtargets = new ArrayList<EObject>(match
				.getNodeMapping().values());
		thistargets.retainAll(matchtargets);

		if (thistargets.size() == 0)
			return false;

		if (thistargets.size() == 0)
			return false;

		return true;
	}

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * Checks if all LHS nodes have a target.
	 * 
	 * @return true, if all LHS nodes are matched
	 */
	public boolean isComplete() {
		if (nodeMapping == null && rule.getLhs().getNodes().size() > 0)
			return false;
		
		for (Node node : rule.getLhs().getNodes()) {
			
			
			if (nodeMapping.get(node) == null)
				return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isValid() {
		// completeness
		if (!isComplete())
			return false;

		// properly typed
		for (Node node : rule.getLhs().getNodes()) {
			if (node.getType().isSuperTypeOf(nodeMapping.get(node).eClass()))
				return false;
		}

		// all edges are present
		for (Node node : rule.getLhs().getNodes()) {
			EObject source = nodeMapping.get(node);

			for (Edge edge : node.getOutgoing()) {
				EReference edgeType = edge.getType();
				EObject target = nodeMapping.get(edge.getTarget());

				if (edgeType.isMany()) {
					List<EObject> targetObjects = (List<EObject>) source.eGet(edgeType); 
					if (!targetObjects.contains(target))
						return false;
				} else {
					if (source.eGet(edgeType) != target)
						return false;
				}
			}
		}

		return true;
	}
}
