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
package org.eclipse.emf.henshin.internal.interpreter;

import javax.script.ScriptEngine;

import org.eclipse.emf.henshin.model.Rule;

public abstract class RuleInfo<TType, TNode> {
	private Rule rule;

	private VariableInfo<TType, TNode> variableInfo;
	private ChangeInfo changeInfo;
	private ConditionInfo conditionInfo;

	public RuleInfo(Rule rule, ScriptEngine scriptEngine) {
		this.rule = rule;

		this.conditionInfo = new ConditionInfo(rule, scriptEngine);
		this.changeInfo = new ChangeInfo(rule);
		this.variableInfo = createVariableInfo(rule, scriptEngine);
	}

	protected abstract VariableInfo<TType, TNode> createVariableInfo(Rule rule,
			ScriptEngine scriptEngine);

	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}

	/**
	 * @return the variableInfo
	 */
	public VariableInfo<TType, TNode> getVariableInfo() {
		return variableInfo;
	}

	/**
	 * @return the changeInfo
	 */
	public ChangeInfo getChangeInfo() {
		return changeInfo;
	}

	/**
	 * @return the conditionInfo
	 */
	public ConditionInfo getConditionInfo() {
		return conditionInfo;
	}
}