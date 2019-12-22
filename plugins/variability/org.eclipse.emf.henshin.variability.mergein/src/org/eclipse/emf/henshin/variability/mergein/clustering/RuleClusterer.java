package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

public interface RuleClusterer {

	List<List<Rule>> clusterRules(List<CloneGroup> cloneGroups);

	List<List<Rule>> clusterRules(List<CloneGroup> cloneGroups,
			double clusteringThreshold, boolean includeRhs);

}
