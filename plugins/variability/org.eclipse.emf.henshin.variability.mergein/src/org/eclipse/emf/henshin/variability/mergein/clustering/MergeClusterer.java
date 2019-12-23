package org.eclipse.emf.henshin.variability.mergein.clustering;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

import mergeSuggestion.MergeSuggestion;

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
