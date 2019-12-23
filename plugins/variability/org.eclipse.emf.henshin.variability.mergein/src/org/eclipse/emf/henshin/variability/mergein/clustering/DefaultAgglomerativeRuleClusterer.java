package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

import com.apporiented.algorithm.clustering.AverageLinkageStrategy;
import com.apporiented.algorithm.clustering.Cluster;
import com.apporiented.algorithm.clustering.ClusteringAlgorithm;
import com.apporiented.algorithm.clustering.DefaultClusteringAlgorithm;

public class DefaultAgglomerativeRuleClusterer implements RuleClusterer {

	List<CloneGroup> cloneGroups;
	Map<Rule, Map<Rule, CloneGroup>> largestClones;
	double largestCloneTotalSize;
	double largestRuleSize;
	Map<String, Rule> nameToRule;
	Map<Rule, Integer> ruleToSize;

	List<Rule> rules;

	// static final double COEFF_RULE_SIZE_DIFFERENCE_FACTOR = 0.2;
	// static final double COEFF_CLONE_SIZE_FACTOR = 0.8;
	// static final double DISTANCE_THRESHOLD = 0.85;

	double distanceThreshold;
	boolean includeRhs;
	// final static double DISTANCT_THRESHOLD_DEFAULT = 0.66;

	// OCL
	final static double DISTANCT_THRESHOLD_DEFAULT = 0.55;
	final static boolean INCLUDE_RHS_DEFAULT = true;

	// 0.57 => 6

	// FM
	// final static double DISTANCT_THRESHOLD_DEFAULT = 0.90;
	// 0 = n rules, 1 = one big rule

	public List<List<Rule>> clusterRules(List<CloneGroup> theCloneGroups) {
		return clusterRules(theCloneGroups, DISTANCT_THRESHOLD_DEFAULT, INCLUDE_RHS_DEFAULT);
	}

	public List<List<Rule>> clusterRules(List<CloneGroup> theCloneGroups,
			boolean includeRhs) {
		this.includeRhs = includeRhs;
		return clusterRules(theCloneGroups, DISTANCT_THRESHOLD_DEFAULT, includeRhs);
	}

	public List<List<Rule>> clusterRules(List<CloneGroup> theCloneGroups,
			double distanceThreshold, boolean includeRhs) {
		this.distanceThreshold = distanceThreshold;
		this.includeRhs = includeRhs;
		this.cloneGroups = theCloneGroups;
		initialize();
		List<List<Rule>> result = new ArrayList<List<Rule>>();
		if (rules.isEmpty())
			 return result;
		
		String[] names = getRuleNames(rules).toArray(new String[rules.size()]);
		double[][] distances = getDistances();
		// printDistances(names, distances);
		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		Cluster cluster = alg.performClustering(distances, names,
				new AverageLinkageStrategy());

		addToResult(cluster, result);
		sortRules();
		System.out.println("[Info] Determined" + +result.size() + " clusters");
		// for (List<Rule> entry: result) {
		// System.out.println(entry.size() + " " + entry);
		// }

		return result;
	}

	private void printDistances(String[] names, double[][] distances) {
		for (int i = 0; i < names.length; i++) {
			System.out.print(i + " = " + names[i]);
			if (i + 1 < names.length)
				System.out.print(", ");
		}
		System.out.println();
		for (int i = 0; i < distances.length; i++) {
			for (int j = 0; j < distances[i].length; j++) {
				System.out.print(Math.round(distances[i][j] * 100.0) / 100.0
						+ " ");
			}
			System.out.println();
		}
	}

	private List<List<Rule>> addToResult(Cluster cluster,
			List<List<Rule>> result) {
		if (cluster.getChildren().isEmpty()) {
			List<Rule> list = Collections.singletonList(nameToRule.get(cluster
					.getName()));
			result.add(list);
		} else if (cluster.getDistance().getDistance() < distanceThreshold) {
			List<Rule> resultCluster = new ArrayList<Rule>();
			result.add(resultCluster);
			addChildrenToOneResultCluster(cluster.getChildren(), resultCluster);
		} else {
			for (Cluster child : cluster.getChildren()) {
				addToResult(child, result);
			}
		}
		return result;
	}

	private void addChildrenToOneResultCluster(List<Cluster> children,
			List<Rule> resultCluster) {
		for (Cluster child : children) {
			if (child.getChildren().isEmpty()) {
				resultCluster.add(nameToRule.get(child.getName()));
			} else {
				addChildrenToOneResultCluster(child.getChildren(),
						resultCluster);
			}
		}
	}

	private double[][] getDistances() {
		double[][] result = new double[rules.size()][rules.size()];
		for (int i = 0; i < rules.size(); i++) {
			for (int j = i + 1; j < rules.size(); j++) {
				result[i][j] = getDistance(rules.get(i), rules.get(j));
			}
		}
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 0; j < i; j++) {
				result[i][j] = result[j][i];
			}
		}
		for (int i = 0; i < rules.size(); i++) {
			result[i][i] = 0;
		}
		return result;
	}

	private double getDistance(Rule rule, Rule rule2) {
		return 1 / (calculcateSimilarity(rule, rule2) + 1);
	}

	private List<String> getRuleNames(List<Rule> rules) {
		ArrayList<String> result = new ArrayList<String>();
		for (Rule rule : rules) {
			result.add(rule.getName());
		}
		return result;
	}

	public double calculcateSimilarity(Rule rule, Rule rule2) {
		double clonesize = 0;
		if (largestClones.containsKey(rule)
				&& largestClones.get(rule).containsKey(rule2)) {
			clonesize = largestClones.get(rule).get(rule2).getSize();
		}
		double rulesize = (ruleToSize.get(rule) + ruleToSize.get(rule2)) / 2.0;
		return (clonesize / rulesize);
		// System.out.println(cloneFactor + " " + ruleSizeDifferenceFactor);
	}

	private void initialize() {
		largestClones = new HashMap<Rule, Map<Rule, CloneGroup>>();
		for (CloneGroup cg : cloneGroups) {
			initializeInnerMaps(largestClones, cg);
			for (Rule r1 : cg.getRules()) {
				for (Rule r2 : cg.getRules()) {
					CloneGroup existingEntry = largestClones.get(r1).get(r2);
					if (existingEntry == null
							|| cg.getSize() > existingEntry.getSize()) {
						largestClones.get(r1).put(r2, cg);
						largestClones.get(r2).put(r1, cg);
					}
				}
			}
			if (largestCloneTotalSize < cg.getSize()) {
				largestCloneTotalSize = cg.getSize();
			}
		}
		rules = new ArrayList<Rule>();
		rules.addAll(largestClones.keySet());

		nameToRule = new HashMap<String, Rule>();
		ruleToSize = new HashMap<Rule, Integer>();
		for (Rule rule : rules) {
			nameToRule.put(rule.getName(), rule);
			ruleToSize.put(rule, getSize(rule));
		}
		setLargestRule();
		sortRules();
	}

	private void setLargestRule() {
		largestRuleSize = 0;
		for (Rule rule : rules) {
			int currentRuleSize = ruleToSize.get(rule);
			if (largestRuleSize < currentRuleSize) {
				largestRuleSize = currentRuleSize;
			}
		}
	}

	private int getSize(Rule rule) {
		int size = 0;
		if (rule == null)
			return size;
		size =
//				rule.getActionEdges(new Action(Type.FORBID)).size() +
				rule.getActionEdges(new Action(Type.DELETE)).size()
				+ rule.getActionEdges(new Action(Type.PRESERVE)).size()
				+ (includeRhs ? rule.getActionEdges(new Action(Type.CREATE))
						.size() : 0);
		// + rule.getLhs().getNodes().size();
		// for (Rule subRule : rule.getMultiRules()) {
		// size += subRule.getLhs().getEdges().size()
		// + subRule.getLhs().getNodes().size();
		//
		// }
		// for (NestedCondition ac : rule.getLhs().getNACs()) {
		// size += ac.getConclusion().getEdges().size()
		// + ac.getConclusion().getNodes().size();
		// }
		return size;
	}

	private void initializeInnerMaps(Map<Rule, Map<Rule, CloneGroup>> outerMap,
			CloneGroup cg) {
		for (Rule r1 : cg.getRules()) {
			Map<Rule, CloneGroup> innerMap = largestClones.get(r1);
			if (innerMap == null) {
				innerMap = new HashMap<Rule, CloneGroup>();
				outerMap.put(r1, innerMap);
			}
		}
	}

	public void sortRules() {
		Collections.sort(rules, new Comparator<Rule>() {

			@Override
			public int compare(Rule arg0, Rule arg1) {
				return ruleToSize.get(arg0) - ruleToSize.get(arg1);
			}

		});
	}

	public void printRuleInfo() {

		for (Rule rule : rules) {
			System.out.println(" * " + rule.getName() + ": "
					+ ruleToSize.get(rule));
		}
	}

}
