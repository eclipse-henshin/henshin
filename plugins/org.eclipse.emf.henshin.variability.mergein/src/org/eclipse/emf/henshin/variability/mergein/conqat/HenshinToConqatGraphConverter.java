package org.eclipse.emf.henshin.variability.mergein.conqat;

import java.util.List;

import org.conqat.engine.model_clones.model.IDirectedEdge;
import org.conqat.engine.model_clones.model.IModelGraph;
import org.conqat.engine.model_clones.model.INode;
import org.conqat.engine.model_clones.model.ModelGraphMock;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinAttributeNode;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

/**
 * Creates Conquat compatible graph representations of Henshin rule graphs.
 * 
 * @author strüber
 *
 */
/**
 * @author strueber
 *
 */
public class HenshinToConqatGraphConverter {
	HenshinToConqatGraphElementMap map;
	List<HenshinGraph> henshinGraphs;

	public HenshinToConqatGraphConverter(List<HenshinGraph> henshinGraphs,
			HenshinToConqatGraphElementMap map) {
		this.henshinGraphs = henshinGraphs;
		this.map = map;
	}

	/**
	 * Creates the Conqat graph representation of a set of Henshin Rules, represented by
	 * the input HenshinGraph.
	 * 
	 * @param henshinGraph
	 * @return
	 */
	public IModelGraph createConqatGraph() {
		IModelGraph resultGraph = new ModelGraphMock();
		for (HenshinGraph henshinGraph : henshinGraphs) {
			IModelGraph tempGraph = createConqatGraph(henshinGraph);
			resultGraph.getNodes().addAll(tempGraph.getNodes());
			resultGraph.getEdges().addAll(tempGraph.getEdges());
		}
		return resultGraph;
	}
	
	/**
	 * Creates the Conqat graph representation of a Henshin Rule, represented by
	 * the input HenshinGraph.
	 * 
	 * @param henshinGraph
	 * @return
	 */
	public IModelGraph createConqatGraph(HenshinGraph henshinGraph) {
		IModelGraph graph = new ModelGraphMock();

		for (HenshinNode node : henshinGraph.vertexSet()) {
			addNodeToGraph(node, graph);
		}
		for (HenshinEdge edge : henshinGraph.edgeSet()) {
			HenshinNode source = henshinGraph.getEdgeSource(edge);
			HenshinNode target = henshinGraph.getEdgeTarget(edge);
			addEdgeToGraph(edge, source, target, graph);
		}

		return graph;
	}

	/**
	 * Adds the given node to the graph.
	 * 
	 * @param theMap
	 */
	private boolean addNodeToGraph(HenshinNode node, IModelGraph graph) {
		INode newNode;
		if (node instanceof HenshinAttributeNode) {
			newNode = new ConqatAttributeNode((HenshinAttributeNode) node);
		} else {
			newNode = new ConqatNode(node);
		}
		graph.getNodes().add(newNode);
		map.put(node, newNode);
		return true;
	}

	/**
	 * Adds the given edge with given source and target nodes in the graph.
	 * 
	 * @param edge
	 * @param source
	 * @param target
	 * @param graph
	 */
	private boolean addEdgeToGraph(HenshinEdge edge, HenshinNode source,
			HenshinNode target, IModelGraph graph) {
		INode sourceNode = map.get(source);
		INode targetNode = map.get(target);
		if (sourceNode != null && targetNode != null) {
			IDirectedEdge newEdge = new ConqatEdge(edge, sourceNode, targetNode);
			graph.getEdges().add(newEdge);
			map.put(edge, newEdge);
			return true;
		} else {
			return false;
		}
	}
}
