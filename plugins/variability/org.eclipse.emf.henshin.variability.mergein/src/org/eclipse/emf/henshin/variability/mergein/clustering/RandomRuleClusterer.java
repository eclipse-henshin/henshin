package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

public class RandomRuleClusterer implements RuleClusterer {
	private int clusterNumber;
	public RandomRuleClusterer(int clusterNumber) {
		this.clusterNumber = clusterNumber;
	}
	@Override
	public List<List<Rule>> clusterRules(List<CloneGroup> cloneGroups) {
		List<List<Rule>> result = new ArrayList<List<Rule>>();
		for (int i=0; i<clusterNumber; i++) {
			ArrayList<Rule> entry = new ArrayList<Rule>();
			result.add(entry);
		}
		
		for (Rule r : getRules(cloneGroups)) {
			int targetCluster = (int) Math.floor(Math.random() * clusterNumber);
			result.get(targetCluster).add(r);
		}
		
		return result;
	}
	
	public Set<Rule> getRules(List<CloneGroup> cloneGroups) {
		Set<Rule> result = new HashSet<Rule>();
		for (CloneGroup cg : cloneGroups) {
			result.addAll(cg.getRules());
		}
		return result;
	}
	
	
	@Override
	public List<List<Rule>> clusterRules(List<CloneGroup> cloneGroups,
			double clusteringThreshold, boolean includeRhs) {
		// Ignore the second and third input parameter.
		return clusterRules(cloneGroups);
	}
	
}
