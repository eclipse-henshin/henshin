//package org.eclipse.emf.henshin.variability.mergein.clustering.extension;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.eclipse.emf.henshin.model.Rule;
//import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
//import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;
//import org.eclipse.emf.henshin.variability.mergein.clustering.CloneClustering;
//import org.eclipse.emf.henshin.variability.mergein.clustering.CloneGroupCopier;
//import org.eclipse.emf.henshin.variability.mergein.clustering.SubCloneRelation;
//
///**
// * Takes a clustering that was performed for a basis clone group result, and
// * another clone group result to incorporate (the base clone result). Creates an
// * extended hierarchy.
// * 
// * @author strueber
// *
// */
//public class CloneClusteringExtender {
//	private CloneClustering clustering;
//	private CloneGroupDetectionResult extendedCloneDetectionResult;
//	private int minimumSubCloneSize;
//
//	public CloneClusteringExtender(CloneClustering clustering,
//			CloneGroupDetectionResult extendedCloneDetectionResult,
//			int minimumSubCloneSize) {
//		this.clustering = clustering;
//		this.extendedCloneDetectionResult = extendedCloneDetectionResult;
//	}
//
//	public ClusteringExtension createClusteringExtension() {
//		ClusteringExtension result = new ClusteringExtension();
//		List<CloneGroup> extended = extendedCloneDetectionResult
//				.getCloneGroups();
//		for (CloneGroup basisCloneGroup : clustering
//				.getBasicCloneGroups2Rules().keySet()) {
//			List<Rule> rules = clustering.getBasicCloneGroups2Rules().get(
//					basisCloneGroup);
//			List<CloneGroup> restricted = CloneGroupCopier
//					.createRestrictedCopies(extended, rules);
//			addExtensionClones(restricted, result, basisCloneGroup);
//			for (CloneGroup subClone : clustering.getTransitiveSubClones(basisCloneGroup)) {
//				addExtensionClones(restricted, result, subClone);
//			}
//		}
//		return result;
//	}
//
//	private void addExtensionClones(List<CloneGroup> cloneGroups,
//			ClusteringExtension extension, CloneGroup basis) {
//		Set<CloneGroup> subClones = new HashSet<CloneGroup>();
//		for (CloneGroup cg : cloneGroups) {
//			if (isExtensionCloneCandidate(basis, cg)) {
//				if (ExtensionCloneRelation.isExtensionClone(basis, cg)) {
//					subClones.add(cg);
//				}
//			}
//		}
//
//		for (CloneGroup cg : subClones) {
//			extension.addExtension(basis, cg);
//		}
//
//	}
//
//	private boolean isExtensionCloneCandidate(CloneGroup basis,
//			CloneGroup extension) {
//		return basis.getRules().size() >= extension.getRules().size();
//	}
//}
