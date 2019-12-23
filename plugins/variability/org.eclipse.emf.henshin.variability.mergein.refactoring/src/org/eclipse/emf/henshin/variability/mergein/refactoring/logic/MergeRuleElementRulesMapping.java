package org.eclipse.emf.henshin.variability.mergein.refactoring.logic;

import org.eclipse.emf.henshin.model.Rule;

import mergeSuggestion.MergeRuleElement;

public class MergeRuleElementRulesMapping extends RulesMapping {
	
	private MergeRuleElement mergeRuleElement;

	public MergeRuleElementRulesMapping(NewMerger newMerger, MergeRuleElement mre) {
		super(newMerger);
		mergeRuleElement = mre;
	}

	public MergeRuleElement getMergeRuleElement() {
		return mergeRuleElement;
	}

	public boolean hasSameRuleList(MergeRuleElementRulesMapping mapping) {
		if (getRules().size() != mapping.getRules().size()) {
			return false;
		}
		for (Rule rule : getRules()) {
			if (! mapping.getRules().contains(rule)) {
				return false;
			}
		}
		return true;
	}

}
