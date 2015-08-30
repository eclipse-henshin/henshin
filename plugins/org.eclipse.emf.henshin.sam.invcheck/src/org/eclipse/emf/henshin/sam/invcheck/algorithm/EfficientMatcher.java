package org.eclipse.emf.henshin.sam.invcheck.algorithm;

import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;

public class EfficientMatcher {

	private Node[][] hostNodes;
	private Node[][] targetNodes;

	private BitSet[] targetNodesBS;

	private int[][] combinations;
	private int[] maximalCardinality;

	// private boolean cachedHasNext;
	// private boolean cacheValid = false;
	//
	// private boolean innerCachedHasNext;
	// private boolean innerCacheValid = false;
	//
	// private boolean innerValid = false;

	private Match currentResult = null;

	private Graph host;

	private Map preMatch;

	private boolean noMatch = false;

	private boolean init = false;

	private Graph pattern;

	public Match getNextMatching() {
		if (this.noMatch) {
			return null;
		}
		if (!this.init) {
			// initialize
			combinations = new int[hostNodes.length][];
			for (int i = 0; i <= hostNodes.length - 1; i++) {
				if (this.hostNodes[i].length > this.maximalCardinality[i] + 1) {
					return null;
				}
				int[] crossProduct = new int[this.hostNodes[i].length];
				combinations[i] = crossProduct;
			}
			init = true;
		} else {
			if (!incrementCrossProducts(this.combinations)) {
				return null;
			}
		}
		buildCurrentNodeResult();
		boolean result = buildCurrentEdgeResult();
		while (!result) {
			if (!incrementCrossProducts(this.combinations)) {
				return null;
			}
			buildCurrentNodeResult();
			result = buildCurrentEdgeResult();
		}
		if (result) {
			return this.currentResult;
		}
		return null;
	}

	private boolean buildCurrentEdgeResult() {
		for (Edge e : this.host.getEdges()) {
			Node srcInHost = this.currentResult.getNodeMatching().get(e.getSource());
			Node tarInHost = this.currentResult.getNodeMatching().get(e.getTarget());
			if (srcInHost != null && tarInHost != null) {
				// source and target is contained in current subgraph, edge is
				// valid and should be matched
				Edge matchingEdge = null;
				for (Edge currentEdge : srcInHost.getOutgoing()) {
					if (this.pattern.getEdges().contains(currentEdge)
							&& !this.currentResult.getEdgeMatching().containsValue(matchingEdge)) {
						// need to check this because an outgoing or incoming
						// edge might belong to a nac in the host
						if (currentEdge.getTarget() == tarInHost && currentEdge.getInstanceOf() == e.getInstanceOf()) {
							// the edges match. However, there might be another
							// possible matching edge
							// if there are multiple edges of the same type
							// between the nodes.
							// Since such a match would be isomorphic to the
							// current match
							// we do not need to consider it. This would be
							// relevant if there were
							// attributes or identifying names attached to the
							// edges.
							matchingEdge = currentEdge;
							break;
						}
					}
				}
				if (matchingEdge == null) {
					for (Edge currentEdge : srcInHost.getIncoming()) {
						if (this.pattern.getEdges().contains(currentEdge)
								&& !this.currentResult.getEdgeMatching().containsValue(matchingEdge)) {
							// need to check this because an outgoing or
							// incoming edge might belong to a nac in the host
							if (currentEdge.getTarget() == srcInHost && currentEdge.getInstanceOf() == e.getInstanceOf()
									&& e.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED) {
								// undirected edges may have src/tar node
								// reversed
								// not all isomorphic combinations - see above
								matchingEdge = currentEdge;
								break;
							}
						}
					}
				}
				if (matchingEdge != null) {
					this.currentResult.getEdgeMatching().put(e, matchingEdge);
				} else {
					this.currentResult.getEdgeMatching().clear();
					this.currentResult.getNodeMatching().clear();
					return false;
				}
			}
		}
		return true;
	}

	private void buildCurrentNodeResult() {
		Match match = SamtraceFactory.eINSTANCE.createMatch();
		for (int i = 0; i <= hostNodes.length - 1; i++) {
			int k = 0;
			for (int j = 0; j <= hostNodes[i].length - 1; j++) {
				Node key = hostNodes[i][j];
				int originalValue = this.combinations[i][k];
				int valueIndex = this.combinations[i][k];
				// Node value = targetNodes[i][valueIndex];
				// iterate over array until current index, increment for each
				// value equal or lower
				// note: this step could be optimized by saving for each integer
				// the number of indexes
				// below that value in a map. Then, instead of iterating over
				// the left side of the array,
				// the number by which the index must be incremented can be
				// fetched from the map.
				for (int n = 0; n < k; n++) {
					if (originalValue >= this.combinations[i][n]) {
						valueIndex++;
					}
				}
				Node value = targetNodes[i][valueIndex];
				while (match.getNodeMatching().containsValue(value)) {
					valueIndex = valueIndex + 1;
					value = targetNodes[i][valueIndex];
				}
				match.getNodeMatching().put(key, value);
				k = k + 1;
			}
		}
		match.getNodeMatching().putAll(this.preMatch);
		this.currentResult = match;
	}

	private boolean incrementCrossProducts(int[][] c) {
		for (int i = 0; i <= c.length - 1; i++) {
			if (!hasMaxCardinality(c[i], i)) {
				incrementCrossProduct(c[i], i);
				return true;
			} else {
				incrementCrossProduct(c[i], i);
			}
		}
		return false;
	}

	private boolean hasMaxCardinality(int[] a, int index) {
		for (int i = 0; i <= a.length - 1; i++) {
			if (a[i] < this.maximalCardinality[index] - i) {
				return false;
			}
		}
		return true;
	}

	private void incrementCrossProduct(int[] p, int index) {
		for (int i = 0; i <= p.length - 1; i++) {
			if (p[i] == this.maximalCardinality[index] - i) {
				p[i] = 0;
			} else {
				p[i] = p[i] + 1;
				return;
			}
		}
	}

	public EfficientMatcher(Graph host, Graph pattern, Map<Node, Node> preMatch) {
		if (preMatch == null) {
			this.preMatch = new HashMap<Node, Node>();
		} else {
			this.preMatch = preMatch;
		}

		this.host = host;
		this.pattern = pattern;

		// restrict to node types present in both graphs
		// Map<NodeType, Integer> typeCount = new HashMap<NodeType, Integer>();
		Map<NodeType, List<Node>> typedNodesHost = new HashMap<NodeType, List<Node>>();
		Map<NodeType, List<Node>> typedNodesPattern = new HashMap<NodeType, List<Node>>();

		Map<EdgeType, List<Edge>> typedEdgesHost = new HashMap<EdgeType, List<Edge>>();
		Map<EdgeType, List<Edge>> typedEdgesPattern = new HashMap<EdgeType, List<Edge>>();

		for (Node node : host.getNodes()) {
			if (!this.preMatch.containsKey(node)) {
				if (typedNodesHost.containsKey(node.getInstanceOf())) {
					typedNodesHost.get(node.getInstanceOf()).add(node);
				} else {
					typedNodesHost.put(node.getInstanceOf(), new LinkedList<Node>());
					typedNodesHost.get(node.getInstanceOf()).add(node);
				}
			}
		}

		for (Node node : pattern.getNodes()) {
			// check whether this type is relevant for match
			if (!this.preMatch.containsValue(node) && typedNodesHost.containsKey(node.getInstanceOf())) {
				if (typedNodesPattern.containsKey(node.getInstanceOf())) {
					typedNodesPattern.get(node.getInstanceOf()).add(node);
				} else {
					typedNodesPattern.put(node.getInstanceOf(), new LinkedList<Node>());
					typedNodesPattern.get(node.getInstanceOf()).add(node);
				}
			}
		}
		List<NodeType> toBeRemoved = new LinkedList<NodeType>();
		for (NodeType nt : typedNodesHost.keySet()) {
			if (!typedNodesPattern.containsKey(nt)) {
				toBeRemoved.add(nt);
			}
		}
		for (NodeType nt : toBeRemoved) {
			typedNodesHost.remove(nt);
		}

		// edges
		for (Edge edge : host.getEdges()) {
			if (!this.preMatch.containsKey(edge)) {
				if (typedEdgesHost.containsKey(edge.getInstanceOf())) {
					typedEdgesHost.get(edge.getInstanceOf()).add(edge);
				} else {
					typedEdgesHost.put(edge.getInstanceOf(), new LinkedList<Edge>());
					typedEdgesHost.get(edge.getInstanceOf()).add(edge);
				}
			}
		}

		for (Edge edge : pattern.getEdges()) {
			// check whether this type is relevant for match
			if (typedEdgesHost.containsKey(edge.getInstanceOf())) {
				if (typedEdgesPattern.containsKey(edge.getInstanceOf())) {
					typedEdgesPattern.get(edge.getInstanceOf()).add(edge);
				} else {
					typedEdgesPattern.put(edge.getInstanceOf(), new LinkedList<Edge>());
					typedEdgesPattern.get(edge.getInstanceOf()).add(edge);
				}
			}
		}
		List<EdgeType> toBeRemovedE = new LinkedList<EdgeType>();
		for (EdgeType et : typedEdgesHost.keySet()) {
			if (!typedEdgesPattern.containsKey(et)) {
				toBeRemovedE.add(et);
			}
		}
		for (EdgeType et : toBeRemovedE) {
			typedEdgesHost.remove(et);
		}

		for (Map.Entry<NodeType, List<Node>> entry : typedNodesHost.entrySet()) {
			if (entry.getValue().size() > typedNodesPattern.get(entry.getKey()).size()) {
				this.noMatch = true;
				return;
			}
		}
		for (Map.Entry<EdgeType, List<Edge>> entry : typedEdgesHost.entrySet()) {
			if (entry.getValue().size() > typedEdgesPattern.get(entry.getKey()).size()) {
				this.noMatch = true;
				return;
			}
		}

		this.hostNodes = new Node[typedNodesHost.keySet().size()][];

		Map<NodeType, Integer> typeMap = new HashMap<NodeType, Integer>();

		int typeIndex = 0;
		for (NodeType nodeType : typedNodesHost.keySet()) {
			typeMap.put(nodeType, typeIndex);
			this.hostNodes[typeIndex] = new Node[typedNodesHost.get(nodeType).size()];

			int nodeIndex = 0;
			for (Node n : typedNodesHost.get(nodeType)) {
				this.hostNodes[typeIndex][nodeIndex] = n;
				nodeIndex++;
			}
			typeIndex++;
		}

		this.maximalCardinality = new int[typedNodesPattern.size()];

		this.targetNodes = new Node[typedNodesPattern.keySet().size()][];
		this.targetNodesBS = new BitSet[typedNodesPattern.keySet().size()];
		Map<NodeType, Integer> typeMapPattern = new HashMap<NodeType, Integer>();

		int typeIndexP = 0;
		for (NodeType nodeType : typedNodesPattern.keySet()) {
			typeMapPattern.put(nodeType, typeIndexP);
			this.targetNodes[typeIndexP] = new Node[typedNodesPattern.get(nodeType).size()];

			int nodeIndex = 0;
			for (Node n : typedNodesPattern.get(nodeType)) {
				this.targetNodes[typeIndexP][nodeIndex] = n;
				nodeIndex++;
			}
			this.targetNodesBS[typeIndexP] = new BitSet(typedNodesPattern.get(nodeType).size());
			this.maximalCardinality[typeIndexP] = typedNodesPattern.get(nodeType).size() - 1;
			typeIndexP++;
		}
	}

	public void reset() {

	}

	public Graph getHost() {
		return host;
	}

	public void setHost(Graph host) {
		this.host = host;
	}

	public Graph getPattern() {
		return pattern;
	}

	public void setPattern(Graph pattern) {
		this.pattern = pattern;
	}

}
