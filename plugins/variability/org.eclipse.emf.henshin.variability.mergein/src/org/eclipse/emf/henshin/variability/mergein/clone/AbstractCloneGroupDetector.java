package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.RuleToHenshinGraphMap;

public abstract class AbstractCloneGroupDetector {
	protected List<Rule> rules;
	protected Set<CloneGroup> result; 
	protected boolean includeRhs;
	
	@SuppressWarnings("unused")
	private AbstractCloneGroupDetector() {}
	
	public AbstractCloneGroupDetector(List<Rule> rules) {
		this.rules = rules;
		this.includeRhs = false;
	}
	
	public AbstractCloneGroupDetector(List<Rule> rules, boolean includeRhs) {
		this.rules = rules;
		this.includeRhs = includeRhs;
	}
	
	public CloneGroupDetectionResult getResultOrderedByNumberOfCommonElements() {
		List<CloneGroup> orderedResult = new ArrayList<CloneGroup>();
		orderedResult.addAll(result);
		Comparator<CloneGroup> comp = new Comparator<CloneGroup>() {
			@Override
			public int compare(CloneGroup arg0, CloneGroup arg1) {
				return arg1.getNumberOfCommonEdges() - arg0.getNumberOfCommonEdges();
			}
		};
		Collections.sort(orderedResult, comp);
		return new CloneGroupDetectionResult(orderedResult);
	}
	
	public abstract void detectCloneGroups();

	protected Map<Attribute, Map<Rule, Attribute>> convertAttributeMappings(
			Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> henshinAttributeMappings,
			RuleToHenshinGraphMap ruleGraphs) {
		Map<Attribute, Map<Rule, Attribute>> result = new HashMap<Attribute, Map<Rule, Attribute>>();
		for (HenshinEdge edge : henshinAttributeMappings.keySet()) {
			HenshinGraph graph = edge.getHenshinGraph();
			Attribute a = graph.getRepresentedAttribute(edge);
			if (a != null) {
				Map<HenshinGraph, HenshinEdge> oldInnerMap = henshinAttributeMappings
						.get(edge);
				Map<Rule, Attribute> newInnerMap = new HashMap<Rule, Attribute>();
				for (HenshinGraph oldGraph : oldInnerMap.keySet()) {
					Rule newRule = ruleGraphs.get(oldGraph);
					HenshinEdge oldEdge = oldInnerMap.get(oldGraph);
					Attribute newAttribute = oldEdge.getHenshinGraph()
							.getRepresentedAttribute(oldEdge);
					newInnerMap.put(newRule, newAttribute);
				}
				result.put(a, newInnerMap);
			}
		}

		return result;
	}

	protected Map<Edge, Map<Rule, Edge>> convertEdgeMappings(
			Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> henshinEdgeMappings,
			RuleToHenshinGraphMap ruleGraphs) {
		Map<Edge, Map<Rule, Edge>> result = new HashMap<Edge, Map<Rule, Edge>>();
		for (HenshinEdge edge : henshinEdgeMappings.keySet()) {
			HenshinGraph graph = edge.getHenshinGraph();
			Edge e = graph.getRepresentedEdge(edge);
			if (e != null) {
				Map<HenshinGraph, HenshinEdge> oldInnerMap = henshinEdgeMappings
						.get(edge);
				Map<Rule, Edge> newInnerMap = new HashMap<Rule, Edge>();
				for (HenshinGraph oldGraph : oldInnerMap.keySet()) {
					Rule newRule = ruleGraphs.get(oldGraph);
					HenshinEdge oldEdge = oldInnerMap.get(oldGraph);
					Edge newEdge = oldEdge.getHenshinGraph()
							.getRepresentedEdge(oldEdge);
					newInnerMap.put(newRule, newEdge);
				}
				result.put(e, newInnerMap);
			}
		}

		return result;
	}
	
	protected List<Rule> convertInvolvedRules(List<HenshinGraph> involvedRuleGraphs, RuleToHenshinGraphMap ruleGraphs) {
		List<Rule> theResult = new ArrayList<Rule>();
		for (HenshinGraph graph : involvedRuleGraphs) {
			if (!theResult.contains(ruleGraphs.get(graph))) {
				theResult.add(ruleGraphs.get(graph));
			}
		}
		return theResult;
	}

}
