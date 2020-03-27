package org.eclipse.emf.henshin.variability.mergein.mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.variability.mergein.mining.core.HenshinGraphToParsemisGraphElementsMap;
import org.eclipse.emf.henshin.variability.mergein.mining.core.HenshinGraphToParsemisGraphMap;
import org.eclipse.emf.henshin.variability.mergein.mining.core.ParsemisParserSerializer;
import org.eclipse.emf.henshin.variability.mergein.mining.core.TransformationSystemMiner;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.IEdgeLabel;
import org.eclipse.emf.henshin.variability.mergein.mining.labels.INodeLabel;
import org.eclipse.emf.henshin.variability.mergein.normalize.AttributeEReference;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraph;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinGraphElement;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;
import de.parsemis.graph.HPGraph;
import de.parsemis.graph.HPListGraph;
import de.parsemis.graph.Node;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.general.Embedding;
import de.parsemis.miner.general.Fragment;
import de.parsemis.miner.general.HPEmbedding;
import de.parsemis.miner.general.IntFrequency;
import de.parsemis.strategy.ThreadedDFSStrategy;

/**
 * This class manages one run of graph mining for a set of Henshin rules
 * provided in a normalized HenshinGraph representation. Retrieves the result
 * graphs as Fragments. Additionally, it can return string representations of
 * frequent subgraphs.
 * 
 * Maintains a TransformationSystemMiner ({@link TransformationSystemMiner}),
 * and stores input model elements, Parsemis settings, input graphs for the
 * mining, mapping between both graph representation and the result fragments.
 *
 */
public class MiningManager {
	private TransformationSystemMiner miner = new TransformationSystemMiner(
			this);
	private Settings<INodeLabel, IEdgeLabel> settings = new Settings<>();
	private ParsemisParserSerializer serializer = new ParsemisParserSerializer();

	private List<HenshinGraph> ruleGraphs;
	private HenshinGraphToParsemisGraphMap henshinToParsemisGraphMap = new HenshinGraphToParsemisGraphMap();
	private Map<Graph<INodeLabel, IEdgeLabel>, HenshinGraphToParsemisGraphElementsMap> henshinToMinableGraphElementMap = new HashMap<>();

	private List<Fragment<INodeLabel, IEdgeLabel>> minedFragments;

	public MiningManager(List<HenshinGraph> ruleGraphs) {
		this.ruleGraphs = ruleGraphs;

		settings.algorithm = new de.parsemis.algorithms.gSpan.Algorithm<INodeLabel, IEdgeLabel>();
		settings.strategy = new ThreadedDFSStrategy<INodeLabel, IEdgeLabel>(1,
				null);
		settings.connectedFragments = true;
		settings.embeddingBased = true;
		settings.storeEmbeddings = true;
		settings.closeGraph = true;
		settings.singleRooted = false;
		settings.minFreq = new IntFrequency(2);
		settings.minNodes = 3;
		settings.minEdges = 2;
		settings.factory = new HPListGraph.Factory<INodeLabel, IEdgeLabel>(
				new ParsemisParserSerializer(), new ParsemisParserSerializer());

	}

	public List<Fragment<INodeLabel, IEdgeLabel>> getMinedFragments() {
		return minedFragments;
	}

	/**
	 * Returns the list of HenshinGraph nodes corresponding to the given node in
	 * the given fragment.
	 * 
	 * @param node
	 * @param fragment
	 * @return
	 */
	public List<HenshinNode> getNodesCorrespondingToFragmentNode(
			Node<INodeLabel, IEdgeLabel> node,
			Fragment<INodeLabel, IEdgeLabel> fragment) {
		List<HenshinNode> nodeList = new ArrayList<>();

		int nodeIndex = node.getIndex();
		for (Embedding<INodeLabel, IEdgeLabel> embedding : fragment
				.getMaximalNonOverlappingSubSet()) {
			HPEmbedding<INodeLabel, IEdgeLabel> highPerformantEmbedding = embedding
					.toHPEmbedding();
			HPGraph<INodeLabel, IEdgeLabel> highPerformantSuperGraph = highPerformantEmbedding
					.getSuperGraph();

			Node<INodeLabel, IEdgeLabel> superGraphNode = highPerformantSuperGraph
					.toGraph().getNode(
							highPerformantEmbedding
									.getSuperGraphNode(nodeIndex));

			HenshinNode correspondingNode = henshinToMinableGraphElementMap
					.get(superGraphNode.getGraph()).get(superGraphNode);
			nodeList.add(correspondingNode);
		}

		return nodeList;
	}

	/**
	 * Returns the list of HenshinGraph edge corresponding to the given edge in
	 * the given fragment.
	 * 
	 * @param node
	 * @param fragment
	 * @return
	 */
	public List<HenshinEdge> getEdgesCorrespondingToFragmentEdge(
			Edge<INodeLabel, IEdgeLabel> edge,
			Fragment<INodeLabel, IEdgeLabel> fragment) {
		List<HenshinEdge> result = new ArrayList<>();

		Set<HenshinGraph> considered = new HashSet<>();
		int edgeIndex = edge.getIndex();
		for (Embedding<INodeLabel, IEdgeLabel> embedding : fragment
				.getMaximalNonOverlappingSubSet()) {
			HPEmbedding<INodeLabel, IEdgeLabel> highPerformantEmbedding = embedding
					.toHPEmbedding();
			HPGraph<INodeLabel, IEdgeLabel> highPerformantSupergraph = highPerformantEmbedding
					.getSuperGraph();
			Edge<INodeLabel, IEdgeLabel> superGraphEdge = highPerformantSupergraph
					.toGraph().getEdge(
							highPerformantEmbedding
									.getSuperGraphEdge(edgeIndex));

			HenshinEdge correspondingEdge = getHenshinEdge(superGraphEdge);
			if (!considered.contains(correspondingEdge.getHenshinGraph())) {
				result.add(correspondingEdge);
				considered.add(correspondingEdge.getHenshinGraph());
			}
		}

		return result;
	}

	/**
	 * This method returns a String describing the graph represented by the
	 * fragment.
	 * 
	 * @param f
	 * @return
	 */
	public String printFragment(Fragment<INodeLabel, IEdgeLabel> f) {
		return serializer.serialize(f.toGraph());
	}

	/**
	 * Returns the list of graph element groups instantiating the frequent
	 * subgraph pattern represented by the given fragment.
	 * 
	 * @param fragment
	 * @return
	 */
	public List<Set<HenshinGraphElement>> getGraphElementsOfEmbeddings(
			Fragment<INodeLabel, IEdgeLabel> fragment) {
		List<Set<HenshinGraphElement>> embeddings = new ArrayList<>();

		for (Embedding<INodeLabel, IEdgeLabel> emb : fragment
				.getMaximalNonOverlappingSubSet()) {
			Set<HenshinGraphElement> embeddedElements = new HashSet<>();
			HPEmbedding<INodeLabel, IEdgeLabel> highPerformantEmbedding = emb
					.toHPEmbedding();
			HPGraph<INodeLabel, IEdgeLabel> highPerformantSupergraph = highPerformantEmbedding
					.getSuperGraph();

			int nodeCount = fragment.toGraph().getNodeCount();
			for (int i = 0; i < nodeCount; i++) {
				Node<INodeLabel, IEdgeLabel> superGraphNode = highPerformantSupergraph
						.toGraph().getNode(
								highPerformantEmbedding.getSuperGraphNode(i));
				HenshinGraphToParsemisGraphElementsMap map = henshinToMinableGraphElementMap
						.get(superGraphNode.getGraph());
				HenshinNode node = map.get(superGraphNode);

				embeddedElements.add(node);
			}

			int edgeCount = fragment.toGraph().getEdgeCount();
			for (int i = 0; i < edgeCount; i++) {
				Edge<INodeLabel, IEdgeLabel> superGraphEdge = highPerformantSupergraph
						.toGraph().getEdge(
								highPerformantEmbedding.getSuperGraphEdge(i));

				HenshinGraphToParsemisGraphElementsMap map = henshinToMinableGraphElementMap
						.get(superGraphEdge.getGraph());
				HenshinEdge edge = map.get(superGraphEdge);

				embeddedElements.add(edge);
			}
			embeddings.add(embeddedElements);
		}
		return embeddings;
	}

	/*
	 * public void setSettings(Settings<VPMNodeLabel, VPMEdgeLabel> settings) {
	 * this.settings = settings; }
	 */

	public List<HenshinGraph> getRuleGraphs() {
		return ruleGraphs;
	}

	public String getFrequency(Fragment<INodeLabel, IEdgeLabel> frag) {
		return frag.frequency().toString();
	}

	public void doMining() {
		miner.doGraphMining();
	}

	public TransformationSystemMiner getTransformationSystemMiner() {
		return miner;
	}

	public Settings<INodeLabel, IEdgeLabel> getSettings() {
		return settings;
	}

	public void setMinedFragments(
			List<Fragment<INodeLabel, IEdgeLabel>> minedFragments) {
		this.minedFragments = minedFragments;
	}

	public void setMaxNodeCount(int maxNodeCount) {
		settings.maxNodes = maxNodeCount;
	}

	public void setMaxFreq(int maxFreq) {
		settings.maxFreq = new IntFrequency(maxFreq);
	}

	public List<Graph<INodeLabel, IEdgeLabel>> getParsemisGraphs() {
		return henshinToParsemisGraphMap.getMinableGraphs();
	}

	public void addParsemisGraph(HenshinGraph henshinGraph,
			Graph<INodeLabel, IEdgeLabel> graph) {
		henshinToParsemisGraphMap.put(henshinGraph, graph);
	}

	public Map<Graph<INodeLabel, IEdgeLabel>, HenshinGraphToParsemisGraphElementsMap> getHenshinToMinableGraphElementsMap() {
		return henshinToMinableGraphElementMap;
	}

	/**
	 * Gets all HenshinEdges in a HenshinGraph found corresponding in the given
	 * embedding.
	 * 
	 * @param e1
	 * @return
	 */
	public Set<HenshinEdge> getSuperGraphEdges(
			Embedding<INodeLabel, IEdgeLabel> e1) {
		Set<HenshinEdge> result = new HashSet<>();
		Iterator<Edge<INodeLabel, IEdgeLabel>> it = e1.getSuperGraph()
				.edgeIterator();
		while (it.hasNext()) {
			Edge<INodeLabel, IEdgeLabel> e = it.next();
			result.add(getHenshinEdge(e));
		}
		return result;
	}

	/**
	 * Gets the HenshinEdge corresponding to the given parsemis graph Edge.
	 * 
	 * @param edge
	 * @return
	 */
	private HenshinEdge getHenshinEdge(Edge<INodeLabel, IEdgeLabel> edge) {
		return henshinToMinableGraphElementMap.get(edge.getGraph()).get(edge);
	}

	/**
	 * Creates a mapping from HenshinEdges to all HenshinEdges found to be
	 * corresponding in the given fragment. The corresponding edges are indexed
	 * by their containing HenshinGraph.
	 * 
	 * @param fragment
	 * @return
	 */
	public Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> createHenshinEdgeMappings(
			Fragment<INodeLabel, IEdgeLabel> fragment) {
		HashMap<HenshinEdge, Map<HenshinGraph, HenshinEdge>> outerMap = new HashMap<>();
		for (Embedding<INodeLabel, IEdgeLabel> e1 : fragment.getEmbeddings()) {
			Iterator<Edge<INodeLabel, IEdgeLabel>> it = e1.getSubGraph()
					.edgeIterator();
			while (it.hasNext()) {
				Set<HenshinGraph> considered = new HashSet<>();
				Edge<INodeLabel, IEdgeLabel> subGraphEdge = it.next();
				List<HenshinEdge> correspondingEdges = getEdgesCorrespondingToFragmentEdge(
						subGraphEdge, fragment);
				Map<HenshinGraph, HenshinEdge> innerMap = new HashMap<>();
				HenshinGraph graph = correspondingEdges.iterator().next()
						.getHenshinGraph();
				if (!considered.contains(graph)) {

					for (HenshinEdge edge : correspondingEdges) {
						innerMap.put(edge.getHenshinGraph(), edge);
						outerMap.put(edge, innerMap);
					}
					considered.add(graph);
				}
			}
		}
		return outerMap;
	}

	/**
	 * Creates a mapping from attribute HenshinEdges to all attribute
	 * HenshinEdges found to be corresponding in the given fragment. The
	 * corresponding attribute edges are indexed by their containing
	 * HenshinGraph.
	 * 
	 * @param fragment
	 * @return
	 */
	public Map<HenshinEdge, Map<HenshinGraph, HenshinEdge>> createHenshinAttributeMappings(
			Fragment<INodeLabel, IEdgeLabel> fragment) {
		HashMap<HenshinEdge, Map<HenshinGraph, HenshinEdge>> outerMap = new HashMap<>();
		for (Embedding<INodeLabel, IEdgeLabel> e1 : fragment.getEmbeddings()) {
			Iterator<Edge<INodeLabel, IEdgeLabel>> it = e1.getSubGraph()
					.edgeIterator();
			while (it.hasNext()) {
				Edge<INodeLabel, IEdgeLabel> subGraphEdge = it.next();
				List<HenshinEdge> correspondingEdges = getEdgesCorrespondingToFragmentEdge(
						subGraphEdge, fragment);
				if (correspondingEdges.get(0).getType() == AttributeEReference.instance) {
					Map<HenshinGraph, HenshinEdge> innerMap = new HashMap<>();
					for (HenshinEdge attributeEdge : correspondingEdges) {
						innerMap.put(attributeEdge.getHenshinGraph(),
								attributeEdge);
						outerMap.put(attributeEdge, innerMap);
					}
				}
			}
		}
		return outerMap;
	}

}
