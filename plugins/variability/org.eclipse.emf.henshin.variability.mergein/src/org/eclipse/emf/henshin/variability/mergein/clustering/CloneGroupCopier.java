package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

public class CloneGroupCopier {


	public static List<CloneGroup> createRestrictedCopies(List<CloneGroup> cloneGroups,
			List<Rule> rules) {
		List<CloneGroup> result = new ArrayList<CloneGroup>();
		for (CloneGroup cg : cloneGroups) {
			if (concernsAtLeastTwoRules(cg, rules)) {
				result.add(CloneGroupCopier.createRestrictedCopy(cg, rules));
			}
		}
		return result;
	}
	
	public static CloneGroup createRestrictedCopy(CloneGroup cg,
			List<Rule> ruleSet) {
		List<Rule> rules = new ArrayList<Rule>();
		rules.addAll(cg.getRules());
		rules.retainAll(ruleSet);

		Map<Attribute, Map<Rule, Attribute>> attrMappings = new HashMap<Attribute, Map<Rule, Attribute>>();
		for (Attribute outerEntry : cg.getAttributeMappings().keySet()) {
			if (outerEntry.getGraph().getRule() != null
					&& rules.contains(outerEntry.getGraph().getRule())
					&& !attrMappings.containsKey(outerEntry)) {
				HashMap<Rule, Attribute> innerMap = new HashMap<Rule, Attribute>();
				for (Rule r : rules) {
					Attribute theAttribute = cg.getAttributeMappings().get(outerEntry)
							.get(r);
					innerMap.put(r, theAttribute);
					attrMappings.put(theAttribute, innerMap);
				}
			}
		}

		Map<Edge, Map<Rule, Edge>> edgeMappings = new HashMap<Edge, Map<Rule, Edge>>();
		for (Edge outerEntry : cg.getEdgeMappings().keySet()) {
			if (outerEntry.getGraph().getRule() != null
					&& rules.contains(outerEntry.getGraph().getRule())
					&& !edgeMappings.containsKey(outerEntry)) {
				HashMap<Rule, Edge> innerMap = new HashMap<Rule, Edge>();
				for (Rule r : rules) {
					Edge theEdge = cg.getEdgeMappings().get(outerEntry)
							.get(r);
					innerMap.put(r, theEdge);
					edgeMappings.put(theEdge, innerMap);
				}
			}
		}

		CloneGroup result = new CloneGroup(rules, edgeMappings, attrMappings);
		return result;
	}
	
	private static boolean concernsAtLeastTwoRules(CloneGroup cg, List<Rule> rules) {
		int count = 0;
		for (Rule rule : cg.getRules()) {
			if (rules.contains(rule))
				count++;
			if (count == 2)
				return true;
		}
		return false;
	}

}
