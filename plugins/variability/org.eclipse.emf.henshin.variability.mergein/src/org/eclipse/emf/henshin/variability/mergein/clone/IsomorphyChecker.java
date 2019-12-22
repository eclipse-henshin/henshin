package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

public class IsomorphyChecker {

	public static boolean cloneIsIsomorphic(Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> edgeMappings,
			Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> attributeMappings) {
		Map<HenshinGraph, HenshinGraph> rules2clones = getRules2clones(edgeMappings,attributeMappings);

		if (!edgeMappings.isEmpty()) {
			treatEdges(edgeMappings, rules2clones);
		}

		if (!attributeMappings.isEmpty()) {
			treatEdges(attributeMappings, rules2clones);
		}

		Collection<HenshinGraph> clones = rules2clones.values();
		JGraphTIsomorphyChecker checker = new JGraphTIsomorphyChecker();
		boolean result = checker.checkIsomorphyWithMultiEdges(clones);
		return result;
	}

	private static HashMap<HenshinGraph, HenshinGraph> getRules2clones(Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> edgeMappings,
			Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> attributeMappings) {
		HashMap<HenshinGraph, HenshinGraph> rules2clones = new HashMap<HenshinGraph, HenshinGraph>();
		if (!edgeMappings.isEmpty()) {
			HenshinEdge first = edgeMappings.keySet().iterator().next();
				Map<HenshinGraph, HenshinEdge> rule2edges = edgeMappings.get(first);
			for (HenshinGraph ruleGraph : rule2edges.keySet())
				rules2clones.put(ruleGraph, new HenshinGraph());
			
		} else if (!attributeMappings.isEmpty())  {
			HenshinEdge first = attributeMappings.keySet().iterator().next();
			Map<HenshinGraph, HenshinEdge> rule2edges = attributeMappings.get(first);
			for (HenshinGraph ruleGraph : rule2edges.keySet())
				rules2clones.put(ruleGraph, new HenshinGraph());
		}
		return rules2clones;
	}
	
	private static void treatEdges(Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> edgeMappings,
			Map<HenshinGraph, HenshinGraph> rules2clones) {

		Set<Map> considered = new HashSet<Map>();
		for (HenshinEdge e : edgeMappings.keySet()) {
			Map<HenshinGraph, HenshinEdge> map = edgeMappings.get(e);
			if (!considered.contains(map)) {
				for (HenshinGraph ruleGraph : map.keySet()) {
					HenshinGraph clone = rules2clones.get(ruleGraph);
					HenshinEdge edge = map.get(ruleGraph);
					HenshinNode source = ruleGraph.getEdgeSource(edge);
					HenshinNode target = ruleGraph.getEdgeTarget(edge);
					clone.addVertex(source);
					clone.addVertex(target);
					clone.addEdge(source, target, edge);
				}
				considered.add(edgeMappings.get(e));
			}
		}
	}
}
