package org.eclipse.emf.henshin.variability.mergein.clustering;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

public class GreedySubCloneClustererStaticThreshold extends
		GreedySubCloneClusterer {
	
	private int minimumSubCloneSize;

//	private GreedySubCloneClustererStaticThreshold(
//			CloneGroupDetectionResult cloneGroupDetectionResult) {
//		super(cloneGroupDetectionResult);
//		minimumSubCloneSize = DEFAULT_MINIMUM_SUBCLONE_SIZE;
//	}

	public GreedySubCloneClustererStaticThreshold(
			CloneGroupDetectionResult cloneGroupDetectionResult,
			int minimumSubCloneSize) {
		super(cloneGroupDetectionResult);
		this.minimumSubCloneSize = minimumSubCloneSize;
	}

	public GreedySubCloneClustererStaticThreshold(
			CloneGroupDetectionResult cloneGroupDetectionResult,
			int minimumSubCloneSize, double clusteringThreshold, boolean includeRhs) {
		super(cloneGroupDetectionResult, clusteringThreshold, includeRhs);
		this.minimumSubCloneSize = minimumSubCloneSize;
	}

	@Override
	boolean isSubCloneCandidate(CloneGroup superCloneGroup,
			CloneGroup subCloneGroup) {
		return (subCloneGroup.getSize() >= minimumSubCloneSize);
	}

}
