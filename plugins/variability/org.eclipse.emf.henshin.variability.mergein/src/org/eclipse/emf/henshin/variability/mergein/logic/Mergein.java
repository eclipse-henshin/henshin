package org.eclipse.emf.henshin.variability.mergein.logic;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;

import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionFactory;

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
