package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class RuleParameters {
	
	private Rule rule;
	private EList<Parameter> parameters;

	public RuleParameters(Rule rule) {
		this.rule = rule;
		this.parameters =  new BasicEList<Parameter>();
	}
	
	public void addParameter(Parameter param) {
		this.parameters.add(param);
	}

	public Rule getRule() {
		return rule;
	}
	
	public EList<Parameter> getParameters() {
		return parameters;
	}
}