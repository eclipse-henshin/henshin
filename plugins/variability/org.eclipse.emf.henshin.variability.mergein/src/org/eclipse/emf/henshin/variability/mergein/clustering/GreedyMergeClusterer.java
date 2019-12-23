package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

import mergeSuggestion.MergeSuggestion;

public class GreedyMergeClusterer extends MergeClusterer {

	private static final double WEIGHT_CARDINALITY = 0.0;
	private static final double WEIGHT_CLONE_SIZE = 1.0;
	
	private RuleClusterer clusterer = new DefaultAgglomerativeRuleClusterer();

	public GreedyMergeClusterer(
			CloneGroupDetectionResult cloneGroupDetectionResult) {
		super(cloneGroupDetectionResult);
	}
	

	public GreedyMergeClusterer(
			CloneGroupDetectionResult cloneGroupDetectionResult, RuleClusterer clusterer) {
		super(cloneGroupDetectionResult);
		this.clusterer = clusterer;
	}


	public GreedyMergeClusterer() {
		super();
	}

	@Override
	public MergeSuggestion createMergeSuggestion() {
		List<CloneGroup> cloneGroups = cloneGroupDetectionResult
				.getCloneGroups();
		List<List<Rule>> clusters = clusterer
				.clusterRules(cloneGroups);
		
		List<CloneGroup> basisClones = new ArrayList<CloneGroup>();
		for (List<Rule> cluster : clusters) {

			List<CloneGroup> restrictedCloneGroups = getRestrictedCopy(cloneGroups, cluster);
			while (!restrictedCloneGroups.isEmpty()) {
				CloneGroup topCloneGroup = getTopCloneGroup(restrictedCloneGroups);
				restrictedCloneGroups.remove(topCloneGroup);
				if (!topCloneGroup.getRules().isEmpty())
					basisClones.add(topCloneGroup);
				for (CloneGroup minorCloneGroup : restrictedCloneGroups)
					minorCloneGroup.removeRules(topCloneGroup.getRules());
				removeEmptyCloneGroups(restrictedCloneGroups);
			}
		}
		MergeSuggestion result = BasicMergeSuggestionBuilder.getInstance().createFromBasisClones(basisClones);

		return result;
	}

	protected List<CloneGroup> getRestrictedCopy(List<CloneGroup> cloneGroups, List<Rule> rules) {
		List<CloneGroup> result = new ArrayList<CloneGroup>();
		for (CloneGroup cg : cloneGroups) {
			if (concernsTwoRules(cg, rules)) {
//				result.add(CloneGroupCopier.createRestrictedCopy(cg, rules)); TODO
			}
		}
		return result;
	}

	private boolean concernsTwoRules(CloneGroup cg, List<Rule> rules) {
		int count = 0;
		for (Rule rule : cg.getRules()) {
			if (rules.contains(rule))
				count++;
			if (count == 2)
				return true;
		}
		return false;
	}

	private void removeEmptyCloneGroups(List<CloneGroup> cloneGroups) {
		List<CloneGroup> toRemove = new ArrayList<CloneGroup>();
		for (CloneGroup cg : cloneGroups) {
			if (cg.getRules().size() < 2)
				toRemove.add(cg);
		}
		cloneGroups.removeAll(toRemove);
	}

	protected CloneGroup getTopCloneGroup(List<CloneGroup> cloneGroups) {
		CloneGroup result = cloneGroups.get(0);
		double topScore = -1.0;
		for (CloneGroup cg : cloneGroups) {
			double cgScore = calculateScore(cg);
			if (cgScore > topScore) {
				result = cg;
				topScore = cgScore;
			}
		}
		return result;
	}

	private double calculateScore(CloneGroup cg) {
		return WEIGHT_CARDINALITY * cg.getRules().size() + WEIGHT_CLONE_SIZE
				* cg.getNumberOfCommonLhsEdges();
	}

}
