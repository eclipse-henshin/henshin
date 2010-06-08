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

import org.eclipse.emf.henshin.internal.matching.Solution;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Encapsulates information about a single match from a Henshin rule into an EMF
 * instance.
 */
public class Match<TType, TNode> {
	// the rule which will be matched
	private Rule rule;

	// mapping of LHS or RHS nodes
	private Map<Node, TNode> nodeMapping;

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
			Map<Node, TNode> nodeMapping) {
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
	public Match(Rule rule, Solution<TType, TNode> solution, Map<Node, Variable<TType, TNode>> node2variable) {
		if (solution != null) {
			this.parameterValues = new HashMap<Parameter, Object>();
			for (String parameterName : solution.getParameterValues().keySet()) {
				Parameter parameter = rule.getParameterByName(parameterName);
				if (parameter != null) {
					parameterValues.put(parameter, solution
							.getParameterValues().get(parameterName));
				}
			}

			this.nodeMapping = new HashMap<Node, TNode>();

			Map<Variable<TType, TNode>, TNode> objectMatch = solution.getObjectMatches();
			for (Node node : rule.getLhs().getNodes()) {
				Variable<TType, TNode> var = node2variable.get(node);
				TNode eObject = objectMatch.get(var);
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
	public Map<Node, TNode> getNodeMapping() {
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
	public boolean overlapsWith(Match<TType, TNode> match) {
		List<TNode> thistargets = new ArrayList<TNode>(nodeMapping.values());
		List<TNode> matchtargets = new ArrayList<TNode>(match
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
}
