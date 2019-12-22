package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;

import de.parsemis.graph.Graph;

public class HenshinGraphToParsemisGraphMap {
	private Map<HenshinGraph, Graph<INodeLabel, IEdgeLabel>> henshinToParsemisGraphMap = new LinkedHashMap<HenshinGraph, Graph<INodeLabel, IEdgeLabel>>();
	private Map<Graph<INodeLabel, IEdgeLabel>, HenshinGraph> parsemisToHenshinGraphMap = new LinkedHashMap<Graph<INodeLabel, IEdgeLabel>, HenshinGraph>();

	public void put(HenshinGraph henshinGraph, Graph<INodeLabel, IEdgeLabel> graph) {
		henshinToParsemisGraphMap.put(henshinGraph, graph);
		parsemisToHenshinGraphMap.put(graph, henshinGraph);
	}

	public void put(Graph<INodeLabel, IEdgeLabel> graph, HenshinGraph henshinGraph) {
		henshinToParsemisGraphMap.put(henshinGraph, graph);
		parsemisToHenshinGraphMap.put(graph, henshinGraph);
	}

	public HenshinGraph get(Graph<INodeLabel, IEdgeLabel> graph) {
		return parsemisToHenshinGraphMap.get(graph);
	}

	public Graph<INodeLabel, IEdgeLabel> get(HenshinGraph henshinGraph) {
		return henshinToParsemisGraphMap.get(henshinGraph);
	}

	public boolean contains(HenshinGraph henshinGraph) {
		return henshinToParsemisGraphMap.containsKey(henshinGraph);
	}

	public boolean contains(Graph<INodeLabel, IEdgeLabel> graph) {
		return parsemisToHenshinGraphMap.containsKey(graph);
	}

	public List<HenshinGraph> getHenshinGraphs() {
		List<HenshinGraph> result = new ArrayList<HenshinGraph>();
		result.addAll(henshinToParsemisGraphMap.keySet());
		return result;
	}
	
	public List<Graph<INodeLabel, IEdgeLabel>> getMinableGraphs() {
		List<Graph<INodeLabel, IEdgeLabel>> result = new ArrayList<Graph<INodeLabel, IEdgeLabel>> ();
		result.addAll(parsemisToHenshinGraphMap.keySet());
		return result;
	}


}
