package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;

public class RuleSpecifics {
	
	private Rule rule;
	private EList<GraphElement> specificElements;

	public RuleSpecifics(Rule rule) {
		this.rule = rule;
		this.specificElements =  new BasicEList<GraphElement>();
	}
	
	public void addGraphElement(GraphElement elem) {
		this.specificElements.add(elem);
	}

	public Rule getRule() {
		return rule;
	}
	
	public EList<GraphElement> getSpecificElements() {
		return specificElements;
	}
}