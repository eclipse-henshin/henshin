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
package org.eclipse.emf.henshin.internal.interpreter;

import javax.script.ScriptEngine;

import org.eclipse.emf.henshin.model.Rule;

public class RuleInfo {
	private Rule rule;
	
	private VariableInfo variableInfo;
	private ChangeInfo changeInfo;
	private ConditionInfo conditionInfo;

	public RuleInfo(Rule rule, ScriptEngine scriptEngine) {
		this.rule = rule;
		
		this.conditionInfo = new ConditionInfo(rule, scriptEngine);
		this.variableInfo = new VariableInfo(this, scriptEngine);
		this.changeInfo = new ChangeInfo(rule);
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