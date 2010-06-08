package org.eclipse.emf.henshin.interpreter.henshin;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.VariableInfo;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class HenshinRuleInfo extends RuleInfo<EClass, Node> {

	public HenshinRuleInfo(Rule rule, ScriptEngine scriptEngine) {
		super(rule, scriptEngine);
	}

	@Override
	protected VariableInfo<EClass, Node> createVariableInfo(Rule rule,
			ScriptEngine scriptEngine) {
		return new HenshinVariableInfo(rule, scriptEngine);
	}

}
