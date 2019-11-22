package org.eclipse.emf.henshin.variability.mergein.normalize;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Rule;

public class RuleToHenshinGraphMap {
	private Map<Rule, HenshinGraph> ruleToHenshinGraphMap = new LinkedHashMap<Rule, HenshinGraph>();
	private Map<HenshinGraph, Rule> henshinGraphToRuleMap = new LinkedHashMap<HenshinGraph, Rule>();

	public void put(Rule rule, HenshinGraph henshinGraph) {
		ruleToHenshinGraphMap.put(rule, henshinGraph);
		henshinGraphToRuleMap.put(henshinGraph, rule);
	}

	public void put(HenshinGraph henshinGraph, Rule rule) {
		ruleToHenshinGraphMap.put(rule, henshinGraph);
		henshinGraphToRuleMap.put(henshinGraph, rule);
	}

	public Rule get(HenshinGraph henshinGraph) {
		return henshinGraphToRuleMap.get(henshinGraph);
	}

	public HenshinGraph get(Rule rule) {
		return ruleToHenshinGraphMap.get(rule);
	}

	public boolean contains(Rule rule) {
		return ruleToHenshinGraphMap.containsKey(rule);
	}

	public boolean contains(HenshinGraph henshinGraph) {
		return henshinGraphToRuleMap.containsKey(henshinGraph);
	}

	public List<HenshinGraph> getHenshinGraphs() {
		List<HenshinGraph> result = new ArrayList<HenshinGraph>();
		result.addAll(henshinGraphToRuleMap.keySet());
		return result;
	}

}
