package org.eclipse.emf.henshin.variability.ui;

import java.util.HashSet;

import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroupDetectionResult;

public class CloneDetectionResultDiscarder {

	public void discard(CloneGroupDetectionResult cloneGroups, double discardedPortion) {
		HashSet<CloneGroup> discarded = new HashSet<CloneGroup>();
		for (CloneGroup cg : cloneGroups.getCloneGroups()) {
			if (Math.random() < discardedPortion) {
				discarded.add(cg);
			}
		}
		cloneGroups.getCloneGroups().removeAll(discarded);
	}

}
