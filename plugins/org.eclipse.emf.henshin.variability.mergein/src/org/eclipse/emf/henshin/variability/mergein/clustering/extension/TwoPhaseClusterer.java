//package org.eclipse.emf.henshin.variability.mergein.clustering.extension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;
//import org.eclipse.emf.henshin.variability.mergein.clustering.CloneClustering;
//import org.eclipse.emf.henshin.variability.mergein.clustering.GreedySubCloneClustererStaticThreshold;
//
///**
// * Creates a clustering with an extension: The clustering is based on a clone
// * detection step that ignores the right-hand sides of rules. The extension
// * provides additional clone dependencies by incorporating clones that cover the
// * complete rules.
// */
//public class TwoPhaseClusterer {
//
//	private CloneGroupDetectionResult cloneGroupDetectionResult;
//	private CloneGroupDetectionResult extendedGroupDetectionResult;
//	private int minimumSubCloneSize;
//	private double clusteringThreshold;
//
//	/**
//	 * 
//	 * @param cloneGroupDetectionResult
//	 * @param extendedGroupDetectionResult
//	 * @param minimumSubCloneSize
//	 * @param clusteringThreshold
//	 */
//	public TwoPhaseClusterer(
//			CloneGroupDetectionResult cloneGroupDetectionResult,
//			CloneGroupDetectionResult extendedGroupDetectionResult,
//			int minimumSubCloneSize, double clusteringThreshold) {
//		this.cloneGroupDetectionResult = cloneGroupDetectionResult;
//		this.extendedGroupDetectionResult = extendedGroupDetectionResult;
//		this.minimumSubCloneSize = minimumSubCloneSize;
//		this.clusteringThreshold = clusteringThreshold;
//	}
//
//	public CloneClustering createClusterings() {
//		GreedySubCloneClustererStaticThreshold clusterer = new GreedySubCloneClustererStaticThreshold(
//				cloneGroupDetectionResult, minimumSubCloneSize,
//				clusteringThreshold, false);
//		CloneClustering result = clusterer.createClustering();
//		CloneClusteringExtender extender = new CloneClusteringExtender(result,
//				extendedGroupDetectionResult, minimumSubCloneSize);
//		ClusteringExtension extension = extender.createClusteringExtension();
//		result.setClusteringExtension(extension);
//		return result;
//	}
//
//}
