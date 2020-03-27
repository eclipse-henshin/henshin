package org.eclipse.emf.henshin.variability.mergein.mining.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinEdge;
import org.eclipse.emf.henshin.variability.mergein.normalize.HenshinNode;

import de.parsemis.graph.Edge;
import de.parsemis.graph.Node;

/**
 * This class connects a HenshinGraph to its corresponding mineable graph. 
 * @author leves
 *
 */
public class HenshinGraphToParsemisGraphElementsMap {
	
	private Map<HenshinNode,Integer> nodeToIndexMap = new HashMap<>();
	private Map<Integer,HenshinNode> indexToNodeMap = new HashMap<>();
	
	private Map<HenshinEdge,Integer> edgeToIndexMap = new HashMap<>();
	private Map<Integer,HenshinEdge> indexToEdgeMap = new HashMap<>();

	public void put(HenshinEdge relation,int index) {
		edgeToIndexMap.put(relation, index);
		indexToEdgeMap.put(index, relation);
	}
	
	public void put(HenshinNode node, int index) {
		nodeToIndexMap.put(node,index);
		indexToNodeMap.put(index, node);
	}
	
	
	public HenshinEdge get(Edge<?, ?> edge) {
		return indexToEdgeMap.get(edge.getIndex());
	}
	
	public HenshinNode get(Node<?, ?> node) {
		return indexToNodeMap.get(node.getIndex());		
	}

	public boolean contains(HenshinNode node) {
		return nodeToIndexMap.containsKey(node);
	}

	public int get(HenshinNode node) {
		return nodeToIndexMap.get(node);
	}

}
