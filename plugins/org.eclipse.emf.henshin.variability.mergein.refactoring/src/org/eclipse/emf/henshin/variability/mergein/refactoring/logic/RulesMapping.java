package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;

public class RulesMapping {
	
	private EList<Rule> rules;
	private NewMerger merger;
	
	public RulesMapping(NewMerger newMerger) {
		merger = newMerger;
		rules = new BasicEList<Rule>();
	}
	
	public EList<Rule> getRules() {
		return rules;
	}
	
	public void addRule(Rule rule) {
		if (! rules.contains(rule)) {
			this.rules.add(rule);
		}
		sortRules();
	}

	private void sortRules() {
		EList<Rule> sortedRules = new BasicEList<Rule>();
		if (rules.contains(merger.getMasterRule())) {
			sortedRules.add(merger.getMasterRule());
		}
		for (Rule rule : merger.getFurtherRules()) {
			if (rules.contains(rule)) {
				sortedRules.add(rule);
			}
		}
		rules = sortedRules;
	}
}
