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
package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.matching.constraints.Solution;
import org.eclipse.emf.henshin.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
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
	private Map<Parameter, Object> parameterValues;

	/**
	 * 
	 * @param rule
	 *            The rule this match is constructed for.
	 * @param parameterValues
	 *            Values for the parameter of the rule
	 * @param nodeMapping
	 *            Mapping between rule nodes and EObjects
	 */
	public Match(Rule rule, Map<Parameter, Object> parameterValues,
			Map<Node, EObject> nodeMapping) {
		this.parameterValues = parameterValues;
		this.nodeMapping = nodeMapping;
		this.rule = rule;
	}

	/**
	 * 
	 * @param rule
	 *            The rule this match is constructed for.
	 * @param solution
	 *            A solution of a matchfinder
	 * @param node2variable
	 *            Map of corresponding pairs of matchfinder variables and rule
	 *            nodes.
	 */
	public Match(Rule rule, Solution solution, Map<Node, Variable> node2variable) {
		if (solution != null) {
			this.parameterValues = new HashMap<Parameter, Object>();
			for (String parameterName : solution.getParameterValues().keySet()) {
				Parameter parameter = rule.getParameterByName(parameterName);
				if (parameter != null) {
					parameterValues.put(parameter, solution
							.getParameterValues().get(parameterName));
				}
			}

			this.nodeMapping = new HashMap<Node, EObject>();

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
	 * The parameter values of the rule corresponding to this match.
	 * 
	 * @return the parameterMapping
	 */
	public Map<Parameter, Object> getParameterValues() {
		return parameterValues;
	}

	/**
	 * The node mapping of the rule corresponding to this match.
	 * 
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
	 * The rule this match belongs to.
	 * 
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
	 * Checks whether this match is complete and typing between rule nodes and
	 * EObjects is valid.
	 * 
	 * @return true, if the match is complete and properly typed.
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
					List<EObject> targetObjects = (List<EObject>) source
							.eGet(edgeType);
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

	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Match)) {
			return false;
		}
		
		Match match = (Match) arg0;
		if (rule != match.getRule())
			return false;
		
		for (Parameter param: getParameterValues().keySet()) {
			if (getParameterValues().get(param) != match.getParameterValues().get(param))
				return false;
		}
		
		for (Node node: getNodeMapping().keySet()) {
			if (getNodeMapping().get(node) != match.getNodeMapping().get(node))
				return false;
		}
		
		return true;
	}
}
