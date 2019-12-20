package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import org.eclipse.emf.henshin.model.Parameter;

public class ParameterRulesMapping extends RulesMapping {

	private Parameter parameter;
	
	public ParameterRulesMapping(Parameter param, NewMerger newMerger) {
		super(newMerger);
		parameter = param;
	}
	
	public Parameter getParameter() {
		return parameter;
	}	
}
