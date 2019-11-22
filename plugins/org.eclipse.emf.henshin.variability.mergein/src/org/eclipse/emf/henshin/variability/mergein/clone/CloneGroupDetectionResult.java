package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.List;

public class CloneGroupDetectionResult {
	List<CloneGroup> cloneGroups;

	public CloneGroupDetectionResult(List<CloneGroup> cloneGroups) {
		this.cloneGroups = cloneGroups;
	}

	public List<CloneGroup> getCloneGroups() {
		return cloneGroups;
	}
}
