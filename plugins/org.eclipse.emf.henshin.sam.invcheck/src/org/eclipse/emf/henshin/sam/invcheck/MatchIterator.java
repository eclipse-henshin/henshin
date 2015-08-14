package org.eclipse.emf.henshin.sam.invcheck;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;

public class MatchIterator implements Iterator<Match> {

	//not threadsafe 
	
	private Node[][] hostNodes;
	private Node[][] targetNodes;	
	
	private BitSet[] hostNodesBS;
	BitSet[] targetNodesBS;
	
	private int[][] combinations;
	private int[] maximalCardinality;
	
	private boolean cachedHasNext;
	private boolean cacheValid = false;
	
	private boolean innerCachedHasNext;
	private boolean innerCacheValid = false;
	
	private boolean innerValid = false;
	
	private Map<Node, Node> preMatch;
	
	private Match currentResult = null;	
	
	public boolean hasNext() {
		if (this.cacheValid) {
			return this.cachedHasNext;
		}
		
		while(!innerHasNext()) {
			this.innerValid = false;
			this.innerCacheValid = false;
			if (!incrementBitSets(hostNodesBS)) {
				this.cacheValid = true;
				this.cachedHasNext = false;
				this.currentResult = null;
				return false;
			}
		}
		
		innerNext();
		buildCurrentResult();
		this.cachedHasNext = true;
		this.cacheValid = true;
		return true;		
	}
	
	private boolean innerHasNext() {
		if (innerCacheValid) {
			return this.innerCachedHasNext;
		}
		if (!innerValid) {
			return initializeInner();
		} else {
			if (incrementCrossProducts(combinations)) {
				this.innerCachedHasNext = true;
				this.innerCacheValid = true;
				//buildInnerResult();
				return true;
			}
		}
		this.innerCacheValid = true; 
		return false;		
	}
	
	private void buildCurrentResult() {		
		Match match = SamtraceFactory.eINSTANCE.createMatch();
		for (int i = 0; i <= hostNodesBS.length - 1; i++) {
			int k = 0;			
			for (int j = hostNodesBS[i].nextSetBit(0); j != -1; j = hostNodesBS[i].nextSetBit(j+1)) {
				Node key = hostNodes[i][j];
				int originalValue = this.combinations[i][k];				
				int valueIndex = this.combinations[i][k];
				//Node value = targetNodes[i][valueIndex];
				// iterate over array until current index, increment for each value equal or lower
				// note: this step could be optimized by saving for each integer the number of indexes
				// below that value in a map. Then, instead of iterating over the left side of the array,
				// the number by which the index must be incremented can be fetched from the map.
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
	
	private void innerNext() {
		if (!this.innerHasNext()) {
			throw new NoSuchElementException("end of iterator has been reached. no more matches available");
		}
		this.innerCacheValid = false;
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
	
	/**
	 * initialize inner iterator such that first result is valid
	 * 
	 * @return whether match is possible
	 */
	private boolean initializeInner() {
		combinations = new int[hostNodesBS.length][];
		for (int i = 0; i <= hostNodesBS.length - 1; i++) {
			int currentCardinality = hostNodesBS[i].cardinality(); // how many bits are set of this type?			
			if (currentCardinality > this.maximalCardinality[i] + 1) {
				return false;
			}
			int[] crossProduct = new int[currentCardinality];			
			combinations[i] = crossProduct;			
		}
		this.innerCacheValid = true;
		this.innerCachedHasNext = true;
		this.innerValid = true;
		return true;
	}

	public Match next() {		
		if (!this.hasNext()) {
			throw new NoSuchElementException("end of iterator has been reached. no more matches available");
		}
		this.cacheValid = false;
		return currentResult;
	}
	
	public MatchIterator(Graph host, Graph pattern) {
		this(host, pattern, null, null);
	}
	
	public MatchIterator(Graph host, Graph pattern, Map<Node, Node> preMatch, Collection<Node> blacklist) {
		if (preMatch == null) {
			this.preMatch = new HashMap<Node, Node>();
		} else {
			this.preMatch = preMatch;
		}
		
		if (blacklist == null) {
			blacklist = new LinkedList<Node>();
		}
		
		// restrict to node types present in both graphs
		//Map<NodeType, Integer> typeCount = new HashMap<NodeType, Integer>();
		Map<NodeType, List<Node>> typedNodesHost = new HashMap<NodeType, List<Node>>();
		Map<NodeType, List<Node>> typedNodesPattern = new HashMap<NodeType, List<Node>>();
		
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
			if (!blacklist.contains(node) && !this.preMatch.containsValue(node) && typedNodesHost.containsKey(node.getInstanceOf())) {
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
		
		if (typedNodesHost.size() == 0 || typedNodesPattern.size() == 0) {
			this.cachedHasNext = false;
			this.cacheValid = true;
			return;
		}
		
		this.hostNodes = new Node[typedNodesHost.keySet().size()][];
		this.hostNodesBS = new BitSet[typedNodesHost.keySet().size()];
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
			this.hostNodesBS[typeIndex] = new BitSet(typedNodesHost.get(nodeType).size());
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
		
		incrementBitSets(hostNodesBS);
		// repeat for edges and edge types?		
	}	

	private void incrementBitSet(final BitSet bs) {		
		final int firstClear = bs.nextClearBit(0);
		bs.set(firstClear);
		for (int i = firstClear - 1; i >= 0; i--) {
			bs.clear(i);
		}
	}
	
	private boolean incrementBitSets(final BitSet[] bss) {		
		for (int i = 0; i <= bss.length - 1; i++) {		
			if (bss[i].cardinality() == hostNodes[i].length) {
				bss[i].clear();
			} else {
				incrementBitSet(bss[i]);
				return true;
			}
		}
		return false;
	}
}

/*
 * $Log$ Revision 1.2 2007/01/03 09:27:47 basilb removed compile errors caused
 * by wrong import declarations; introduced empty plugin class to ensure correct
 * loading
 * 
 */
