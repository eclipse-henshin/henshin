package org.eclipse.emf.henshin.variability.mergein.logic;

import java.util.List;

import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionFactory;

import org.eclipse.emf.henshin.model.Rule;

public class Mergein {
	
	private List<Rule> rules;

	public Mergein(List<Rule> rules) {
		this.rules = rules;
	}

	public MergeSuggestion createMergeSuggestion() {
		MergeSuggestion result = MergeSuggestionFactory.eINSTANCE.createMergeSuggestion();
//		MergeinComparer MergeinComparer = new MergeinComparer(rules);
		
		
		return result;
	}
}
