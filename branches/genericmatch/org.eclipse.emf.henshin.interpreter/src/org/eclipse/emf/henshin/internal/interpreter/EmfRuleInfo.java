package org.eclipse.emf.henshin.internal.interpreter;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;

public class EmfRuleInfo extends RuleInfo<EClass, EObject> {

	public EmfRuleInfo(Rule rule, ScriptEngine scriptEngine) {
		super(rule, scriptEngine);
	}

	@Override
	protected VariableInfo<EClass, EObject> createVariableInfo(Rule rule,
			ScriptEngine scriptEngine) {
		return new EmfVariableInfo(rule, scriptEngine);
	}

}
