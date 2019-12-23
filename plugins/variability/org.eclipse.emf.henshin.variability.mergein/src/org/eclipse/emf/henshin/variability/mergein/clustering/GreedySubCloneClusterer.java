package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

import mergeSuggestion.MergeSuggestion;

public abstract class GreedySubCloneClusterer extends MergeClusterer {

	private static final double WEIGHT_CARDINALITY = 0.0;
	private static final double WEIGHT_CLONE_SIZE = 1.0;
	private double clusteringThreshold;
	private boolean includeRhs;

	
	protected GreedySubCloneClusterer(
			CloneGroupDetectionResult cloneGroupDetectionResult) {
		super(cloneGroupDetectionResult);
	}

	public GreedySubCloneClusterer() {
		super();
	}

	public GreedySubCloneClusterer(
			CloneGroupDetectionResult cloneGroupDetectionResult,
			double clusteringThreshold, boolean includeRhs) {

		super(cloneGroupDetectionResult);
		this.includeRhs = includeRhs;
		this.clusteringThreshold = clusteringThreshold;
	}

	@Override
	public MergeSuggestion createMergeSuggestion() {
		List<CloneGroup> cloneGroups = cloneGroupDetectionResult
				.getCloneGroups();

		List<List<Rule>> clusters = new DefaultAgglomerativeRuleClusterer()
				.clusterRules(cloneGroups, clusteringThreshold, includeRhs);

		CloneHierarchy cloneHierarchy = new CloneHierarchy();
		for (List<Rule> cluster : clusters) {
//			System.out.println();
//			System.out.println("Cluster ");
			List<CloneGroup> restrictedCloneGroups = getRestrictedCopy(
					cloneGroups, cluster);
			removeEmptyCloneGroups(restrictedCloneGroups);
			
			while (!restrictedCloneGroups.isEmpty()) {
				CloneGroup topCloneGroup = getTopCloneGroup(restrictedCloneGroups);
				restrictedCloneGroups.remove(topCloneGroup);
				Set<Rule> considered = new HashSet<Rule>();
				if (!topCloneGroup.getRules().isEmpty()) {
					cloneHierarchy.addBasisCloneGroup(topCloneGroup);
					considered.addAll(topCloneGroup.getRules());
					addSubClones(restrictedCloneGroups, cloneHierarchy,
							topCloneGroup);
					restrictedCloneGroups.removeAll(cloneHierarchy
							.getTransitiveSubClones(topCloneGroup));
					for (CloneGroup cg : cloneHierarchy
							.getTransitiveSubClones(topCloneGroup)) {
						considered.addAll(cg.getRules());
					}
//					System.out.println(considered);
				}
				for (CloneGroup minorCloneGroup : restrictedCloneGroups) {
					minorCloneGroup.removeRules(considered);
//					System.out.println(" * "+minorCloneGroup.getRules());
				}

				removeEmptyCloneGroups(restrictedCloneGroups);
			}
		}
		MergeSuggestion result = new HierarchicalMergeSuggestionBuilder()
				.createFromCloneHierarchy(cloneHierarchy);
		return result;
	}


	private void addSubClones(List<CloneGroup> cloneGroups,
			CloneHierarchy cloneHierarchy, CloneGroup topCloneGroup) {
		Set<CloneGroup> subClones = new HashSet<CloneGroup>();
		for (CloneGroup cg : cloneGroups) {
			if (isSubCloneCandidate(topCloneGroup, cg)
					&& SubCloneRelation.isSubClone(topCloneGroup, cg)) {
					subClones.add(cg);
			}
		}

		for (CloneGroup cg : subClones) {
			cloneHierarchy.addChild(topCloneGroup, cg);
		}
	}

	private List<CloneGroup> getRestrictedCopy(List<CloneGroup> cloneGroups,
			List<Rule> rules) {
		List<CloneGroup> result = new ArrayList<CloneGroup>();
		for (CloneGroup cg : cloneGroups) {
			if (concernsAtLeastTwoRules(cg, rules)) {
				result.add(CloneGroupCopier.createRestrictedCopy(cg, rules));
			}
		}
		return result;
	}

	private boolean concernsAtLeastTwoRules(CloneGroup cg, List<Rule> rules) {
		int count = 0;
		for (Rule rule : cg.getRules()) {
			if (rules.contains(rule))
				count++;
			if (count == 2)
				return true;
		}
		return false;
	}

	abstract boolean isSubCloneCandidate(CloneGroup superCloneGroup,
			CloneGroup subCloneGroup);

	//
	// private List<Set<CloneGroup>> getCloneGroupsPerLevel(
	// List<CloneGroup> cloneGroups) {
	// List<Set<CloneGroup>> result = new LinkedList<Set<CloneGroup>>();
	// int highestCardinality = -1;
	// for (CloneGroup cg : cloneGroups)
	// highestCardinality = Math.max(highestCardinality, cg.getRules()
	// .size());
	// for (int i = 0; i < highestCardinality + 1; i++)
	// result.add(new HashSet<CloneGroup>());
	// for (CloneGroup cg : cloneGroups)
	// result.get(cg.getRules().size()).add(cg);
	// return result;
	// }
	//
	// private void addTransitiveSubClonesRecursively(CloneGroup cloneGroup,
	// List<Set<CloneGroup>> cloneGroupsPerLevel,
	// CloneHierarchy cloneHierarchy, int level) {
	// for (CloneGroup candidate : cloneGroupsPerLevel) {
	// }
	// // else {
	// // // Only for the basis clone group we can decide if the
	// // // candidate is irrelvant:
	// // if (outerCall)
	// // cloneGroups.remove(candidate);
	// // // }
	// // }
	// }

//	private void overwriteRules(Map<CloneGroup, Set<Rule>> map) {
//		for (CloneGroup cg : map.keySet()) {
//			cg.getRules().clear();
//			cg.getRules().addAll(map.get(cg));
//		}
//	}

	private void removeEmptyCloneGroups(List<CloneGroup> cloneGroups) {
		Set<CloneGroup> cgs = new HashSet<CloneGroup>(cloneGroups);
		for (CloneGroup cg : cgs) {
			if (cg.getRules().size() < 2)
				cloneGroups.remove(cg);
		}
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
				* cg.getSize();
	}

}
