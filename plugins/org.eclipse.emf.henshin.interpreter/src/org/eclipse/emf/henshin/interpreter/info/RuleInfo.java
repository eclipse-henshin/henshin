/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.info;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class RuleInfo {
	private Rule rule;
	
	private VariableInfo variableInfo;
	private RuleChangeInfo changeInfo;
	private ConditionInfo conditionInfo;
	
	// Nodes whose dangling condition check must be postponed,
	// due to multi-rules.
	private Set<Node> postponed;

	public RuleInfo(Rule rule, EngineImpl engine) {
		this.rule = rule;

		this.postponed = computePostponed();
		this.conditionInfo = new ConditionInfo(rule);
		this.variableInfo = new VariableInfo(this, engine);
		this.changeInfo = new RuleChangeInfo(rule);
	}

	private Set<Node> computePostponed() {
		Set<Node> result = new HashSet<Node>();
		for (Rule r : rule.getMultiRules()) {
			for (Mapping m : r.getMultiMappings()) {
				if (m.getOrigin().getGraph().isLhs())
					result.add(m.getOrigin());
			}
		}
		return result;
	}

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @return the variableInfo
	 */
	public VariableInfo getVariableInfo() {
		return variableInfo;
	}

	/**
	 * @return the changeInfo
	 */
	public RuleChangeInfo getChangeInfo() {
		return changeInfo;
	}

	/**
	 * @return the conditionInfo
	 */
	public ConditionInfo getConditionInfo() {
		return conditionInfo;
	}

	public Collection<Node> getPostponed() {
		return postponed;
	}

	public void updateCached() {
		variableInfo.updateCached();
	}
}