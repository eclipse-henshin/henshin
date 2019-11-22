
package org.eclipse.emf.henshin.variability.mergein.logic;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;

public class RuleComparer {
	private Rule rule1; 
	private Rule rule2; 
	Map<String, Double> weights;
	Map<EObjectPair, Double> scores;
	List<EClass> types;
	
	public void calculateAllSimilarityScores() {
		
	}

	public void calculateSimilarityScore(EObject o1, EObject o2) {
		
	}
	
	public void getSimilarityScore(EObject o1, EObject o2) {
	}

}
