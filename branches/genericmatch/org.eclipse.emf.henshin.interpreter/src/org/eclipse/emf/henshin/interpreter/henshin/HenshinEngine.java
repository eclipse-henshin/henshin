package org.eclipse.emf.henshin.interpreter.henshin;

import java.util.Map;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.interpreter.GenericEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class HenshinEngine extends GenericEngine<EClass, Node> {

	@Override
	protected RuleInfo<EClass, Node> createRuleInfo(Rule rule,
			ScriptEngine scriptEngine) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Match executeModelChanges(RuleApplication ruleApplication) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RuleApplication generateAmalgamationRule(
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void redoChanges(RuleApplication ruleApplication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undoChanges(RuleApplication ruleApplication) {
		// TODO Auto-generated method stub
		
	}
}
