package org.eclipse.emf.henshin.variability.mergein.logic.similarity;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.variability.mergein.logic.EObjectPair;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;

/**
 * Base class for merge-in based on rule similarity. Will maybe not be
 * implemented due to conceptual issues: rule similarity might not be the suited
 * concept for merge-in.
 * 
 * @author strueber
 *
 */
public class MergeinComparer {

	private List<HenshinGraph> rules;

	public MergeinComparer(List<HenshinGraph> rules) {
		this.rules = rules;
	}

	class BinaryComparison {
		private HenshinGraph rule1;
		private HenshinGraph rule2;
		Map<String, Double> weights;
		Map<EObjectPair, Double> scores;

		public BinaryComparison(HenshinGraph rule1, HenshinGraph rule2) {
			this.rule1 = rule1;
			this.rule2 = rule2;
		}

		public void calculateSimilarityScores() {
			// identifySimilarEdges();
		}

		public void calculateSimilarityScore(EObject o1, EObject o2) {

		}

		public void getSimilarityScore(EObject o1, EObject o2) {
		}

		public void getOverallSimilarityScore() {

		}
	}

	MergeinComparisonResult createMergeInComparisonResult() {
		MergeinComparisonResult result = new MergeinComparisonResult();

		for (int i = 0; i < rules.size(); i++) {
			for (int j = i + 1; i < rules.size(); i++) {
				BinaryComparison comparison = new BinaryComparison(
						rules.get(i), rules.get(j));
			}
		}

		return result;
	}

}

class MergeinComparisonResult {

}