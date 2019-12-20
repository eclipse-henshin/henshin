package org.eclipse.emf.henshin.variability.mergein.clustering;

import mergeSuggestion.MergeSuggestion;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

public abstract class MergeClusterer {
	CloneGroupDetectionResult cloneGroupDetectionResult;

	public MergeClusterer(
			CloneGroupDetectionResult cloneGroupDetectionResult) {
		this.cloneGroupDetectionResult = cloneGroupDetectionResult;
	}

	public MergeClusterer() {
	}

	public abstract MergeSuggestion createMergeSuggestion();


}
