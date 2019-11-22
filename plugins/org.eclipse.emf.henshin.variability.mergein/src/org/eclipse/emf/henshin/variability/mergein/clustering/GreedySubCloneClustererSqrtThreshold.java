package org.eclipse.emf.henshin.variability.mergein.clustering;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

public class GreedySubCloneClustererSqrtThreshold extends
		GreedySubCloneClusterer {
	

	public GreedySubCloneClustererSqrtThreshold(
			CloneGroupDetectionResult cloneGroupDetectionResult) {
		super(cloneGroupDetectionResult);
	}

	public GreedySubCloneClustererSqrtThreshold(
			CloneGroupDetectionResult cloneGroupDetectionResult,
			int minimumSubCloneSize) {
		super(cloneGroupDetectionResult);
	}

	@Override
	boolean isSubCloneCandidate(CloneGroup superCloneGroup,
			CloneGroup subCloneGroup) {
		return (superCloneGroup.getSize() - subCloneGroup.getSize()
		< Math.sqrt(superCloneGroup.getSize()));

//		return (superCloneGroup.getSize() - subCloneGroup.getSize()) < 3;
	}

}
