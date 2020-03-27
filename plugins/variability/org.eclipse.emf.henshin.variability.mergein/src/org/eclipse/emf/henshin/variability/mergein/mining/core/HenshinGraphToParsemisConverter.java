package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.util.Map;

import org.eclipse.emf.henshin.variability.mergein.mining.MiningManagerFactory;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.DefaultAttributeNodeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.DefaultEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.DefaultNodeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinAttributeNode;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;
import de.parsemis.graph.GraphFactory;
import de.parsemis.graph.HPMutableGraph;
import de.parsemis.graph.ListGraph.Factory;

/**
 * Creates parsemis compatible graph representations of Henshin rule graphs.
 * 
 * @author str�ber
 *
 */
/**
 * @author strueber
 *
 */
public class HenshinGraphToParsemisConverter {

	public HenshinGraphToParsemisConverter() {
	}

	private HPMutableGraph<INodeLabel, IEdgeLabel> highPerformanceGraph;

	private Map<Graph<INodeLabel, IEdgeLabel>, HenshinGraphToParsemisGraphElementsMap> maps = MiningManagerFactory
			.getInstance().getManager().getHenshinToMinableGraphElementsMap();

	/**
	 * Creates the Parsemis graph representation of a Henshin Rrule, represented
	 * by the input HenshinGraph.
	 * 
	 * @param entityList
	 * @return
	 */
	public Graph<INodeLabel, IEdgeLabel> createParsemisGraph(
			HenshinGraph henshinGraph) {
		GraphFactory<INodeLabel, IEdgeLabel> factory = new Factory<>(
				new ParsemisParserSerializer(),
				new ParsemisParserSerializer());
		Graph<INodeLabel, IEdgeLabel> graph = factory.newGraph(henshinGraph
				.getRuleName());
		highPerformanceGraph = (HPMutableGraph<INodeLabel, IEdgeLabel>) graph
				.toHPGraph();
		HenshinGraphToParsemisGraphElementsMap map = new HenshinGraphToParsemisGraphElementsMap();
		maps.put(graph, map);

		for (HenshinNode node : henshinGraph.vertexSet()) {
			addNodeToGraph(node, map);
		}
		for (HenshinEdge edge : henshinGraph.edgeSet()) {
			HenshinNode source = henshinGraph.getEdgeSource(edge);
			HenshinNode target = henshinGraph.getEdgeTarget(edge);
			addEdgeToGraph(edge, source, target, map);
		}

		return highPerformanceGraph.toGraph();
	}

	/**
	 * Adds the given node to the graph.
	 * @param theMap 
	 */
	private boolean addNodeToGraph(HenshinNode node, HenshinGraphToParsemisGraphElementsMap theMap) {
		INodeLabel newNodeLabel;
		if (node instanceof HenshinAttributeNode)
			newNodeLabel = new DefaultAttributeNodeLabel((HenshinAttributeNode) node);
		else
			newNodeLabel = new DefaultNodeLabel(node);
		int index = highPerformanceGraph.addNodeIndex(newNodeLabel);
		theMap.put(node, index);
		return true;
	}

	/**
	 * Adds the given edge with given source and target nodes in the graph.
	 * @param edge
	 * @param source
	 * @param target
	 */
	private void addEdgeToGraph(HenshinEdge edge, HenshinNode source,
			HenshinNode target, HenshinGraphToParsemisGraphElementsMap theMap) {
		IEdgeLabel edgeLabel = new DefaultEdgeLabel(edge);
		int sourceIndex = theMap.get(source);
		int targetIndex = theMap.get(target);
		int edgeIndex = highPerformanceGraph.addEdgeIndex(sourceIndex,
				targetIndex, edgeLabel, Edge.OUTGOING);
		theMap.put(edge, edgeIndex);

	}
}
