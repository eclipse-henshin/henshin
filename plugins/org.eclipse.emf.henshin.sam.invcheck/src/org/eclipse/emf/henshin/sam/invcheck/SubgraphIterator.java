package org.eclipse.emf.henshin.sam.invcheck;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;

/**
 * This class implements the <code>Iterator</code> interface. It is used to
 * iterate over all subgraphs of a given <code>Graph</code>. Simply use it as
 * a normal <code>Iterator</code>. The returned subgraphs are all Objects
 * from type <code>Set</code>, containing the <code>Nodes</code> and
 * <code>Edges</code> of the subgraph.
 */
public class SubgraphIterator implements Iterator<Set<AnnotatedElem>> {
	
	private boolean cacheValid = false;
	
	private boolean cachedHasNext = true;

	/**
	 * this field contains the maximum for the number of bits, which currently
	 * could be set to <code>true</code> in the
	 * {@link #edgeBitSet edge bitset}.
	 */
	private transient int maxNumberEdges = 0;
	
	/**
	 * This field stores the number of edges of the <code>Graph</code>  {@link #graph}.
	 */
	private transient final int sizeOfEdges;
	
	private transient final int sizeOfNodes;

	/**
	 * This <code>Iterator</code> has no meaningful implementation for this
	 * method. If you invoke it, an <code>IllegalAccessError</code> will be
	 * thrown.
	 * 
	 * @throws IllegalAccessError
	 *             always
	 */
	public void remove() {
		throw new IllegalAccessError(
				"you may not call remove() on a SubgraphIterator.");
	}

	/**
	 * Use this method to test whether this <code>Iterator</code> has more
	 * elements or not.<br />
	 * An <code>SubgraphIterator</code> contains more elements if the
	 * iterator's local {@link #edgeBitSet} is <code>null</code> or if the
	 * number of bits, actually set in the <code>BitSet</code>, is less than
	 * the number of <code>Edge</code>s contained in the <code>Graph</code>
	 * the iterator is based on.
	 * 
	 * @return <code>true</code> if there ary some more elements
	 * @see Iterator#hasNext()
	 */
	public boolean hasNext() {
		if (!cacheValid) {
			//if (this.nodeBitSet.cardinality() < this.graph.getNodes().size() || this.edgeBitSet == null	|| this.edgeBitSet.cardinality() < this.sizeOfEdges) {
			if (this.nodeBitSet.cardinality() < this.sizeOfNodes || this.edgeBitSet == null	|| this.edgeBitSet.cardinality() < this.sizeOfEdges) {	
				cachedHasNext = true;
			} else	
				cachedHasNext = false;
			cacheValid = true;
		}
		return cachedHasNext;
	}

	/**
	 * Returns the next subgraph of the <code>Graph</code> passed to the
	 * constructor.
	 * 
	 * @return a <code>Set</code> containing the elements of the subgraphs
	 * @throws NoSuchElementException
	 *             If there isn't any remaining subgraph
	 * @see Iterator#next()
	 */
	public Set<AnnotatedElem> next() {
		if (!this.hasNext()) {
			throw new NoSuchElementException(
					"end of iterator has been reached. no more subgraphs available");
		}
		this.computeNextState();/*
		while (this.hasNext() && !isNecessarySubgraph()) {
			//System.out.println("wasn't necessary");
			this.computeNextState();
		}
		if (!isNecessarySubgraph()) {
			return null;
		}*/
		Set<AnnotatedElem> tmpSet = this.buildReturnValue();
		/*while (tmpSet != null && ! isValidReturnValue(tmpSet)) {
			if (this.hasNext()) {
				this.computeNextState();
				tmpSet = this.buildReturnValue();
			} else {
				tmpSet = null;
			}
		}*/
		return tmpSet;
	}
	
	private boolean isNecessarySubgraph() {
		if (this.bitMask == null) {
			return true;
		} else {
			return bitMask.intersects(nodeBitSet);
		}
		
	}
	
	private boolean isValidReturnValue(Set<AnnotatedElem> returnValue) {
		boolean result = true;
		if (this.returnAllSubgraphs == false) {
			result = false;
			for (Iterator<AnnotatedElem> iter = returnValue.iterator(); !result && iter.hasNext(); ) {
				AnnotatedElem tmpItem = iter.next();
				if (tmpItem.eClass() != SamrulesPackage.eINSTANCE.getPreservedEdge() && tmpItem.eClass() != SamrulesPackage.eINSTANCE.getPreservedNode()) {
					result = true;
				} else if (tmpItem.eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
					PreservedNode tmpNode = (PreservedNode) tmpItem;
					//System.out.println(tmpNode);
					//System.out.println(tmpNode.getRefInRule());
					if (adjacentToModifiedEdges(tmpNode) || adjacentToModifiedEdges(tmpNode.getRefInRule())) {
						result = true;
					}
				}
			}
		}
		return result;
	}
	
	private boolean adjacentToModifiedEdges(final Node n) {
		boolean result = false;
		for (Iterator<Edge> edgeIter = n.getIncoming().iterator(); !result && edgeIter.hasNext(); ) {
			Edge e = edgeIter.next();
			if (e.eClass() != SamrulesPackage.eINSTANCE.getPreservedEdge()) {
				result = true;
			}
			/*if (e.getRefInRule() == null) {
				result = true;
			}*/
		}
		for (Iterator<Edge> edgeIter = n.getOutgoing().iterator(); !result && edgeIter.hasNext(); ) {
			Edge e = edgeIter.next();
			if (e.eClass() != SamrulesPackage.eINSTANCE.getPreservedEdge()) {
				result = true;
			}
			/*if (e.getRefInRule() == null) {
				result = true;
			}*/
		}
		return result;
	}
	
	private Set<AnnotatedElem> buildReturnValue() {
		final Set<AnnotatedElem> tmpSet = new HashSet<AnnotatedElem>();
		for (int i = this.nodeBitSet.nextSetBit(0); i != -1; i = this.nodeBitSet
				.nextSetBit(i+1)) {
			// we had to add this somehow weird condition, because it could
			// happen that i gets bigger than nodeArray actually is.
			if (i >= 0 && i < this.nodeArray.length && this.nodeBitSet.get(i)) {
				tmpSet.add(this.nodeArray[i]);
			}
		}/*
		for (int i = 0; i < this.nodeBitSet.size(); i++) {
			if (i < this.nodeArray.length && this.nodeBitSet.get(i)) {
					tmpSet.add(this.nodeArray[i]);
			}
		}
		for (int i = 0; i < this.edgeBitSet.size(); i++) {
			if (i < this.edgeArray.length && this.edgeBitSet.get(i)) {
					tmpSet.add(this.edgeArray[i]);
			}
		}*/
		if (this.edgeArray != null && this.edgeBitSet != null) {
			for (int i = this.edgeBitSet.nextSetBit(0); i != -1; i = this.edgeBitSet.nextSetBit(i+1)) {
					tmpSet.add(this.edgeArray[i]);
			}
		}		
		return tmpSet;
	}

	/**
	 * No comment provided by developer, please add a comment to improve
	 * documentation.
	 */
	private transient final Graph graph; // NOPMD by bbecker on 04.03.09 11:08

	/**
	 * this array contains all <code>Nodes</code> of <code>Graph</code>
	 * attribute of this <code>SubgraphIterator</code>
	 */
	private final transient Node nodeArray[];

	/**
	 * This array stores the edges which were inducted by the
	 * <code>nodeArray</code> and the <code>nodeBitSet</code>
	 */
	private transient Edge edgeArray[];
	
	private transient final Edge origEdgeArray[];

	/**
	 * This BitSet stores the <code>Nodes</code> which were contained in the
	 * last generated subgraph.
	 */
	private transient final BitSet nodeBitSet;

	/**
	 * the <code>edgeBitSet</code> describes a subset of the
	 * <code>Edges</code> contained in the <code>edgeArray</code>
	 */
	private transient BitSet edgeBitSet;

	private Set<Edge> nextStatetmpEdgeSet = null; // NOPMD by bbecker on 04.03.09 11:06
	
	private BitSet bitMask;
	
	public BitSet getBitMask() {
		return bitMask;
	}
	
	private final boolean returnAllSubgraphs;
	
	/**
	 * Constructor for class SubgraphIterator.
	 * Calling this constructor is equivalent to:<code>new SubgraphIterator(g,true)</code>
	 * @param g the <code>Graph</code> that is used as base for 
	 *            the generated subgraphs
	 * @see #SubgraphIterator(Graph, boolean)
	 */
	public SubgraphIterator(final Graph g) {
		this(g, true, null);
	}

	public SubgraphIterator(final Graph g, final boolean all) {
		this(g, all, null);
	}
	
	public SubgraphIterator(final Graph g, final Graph forbidden) {
		this(g, true, forbidden);
	}

	/**
	 * Constructor for class SubgraphIterator
	 * 
	 * @param g
	 *            the <code>Graph</code> that is used as base for 
	 *            the generated subgraphs
	 * @param all Whether or not all subgraphs of <code>g</code> should 
	 * 			  be returned.<br />If set to <code>false</code> only such 
	 * 			  subgraphs are returned that contain at least one element
	 * 			  which is modified by the rule.
	 * 			  NOTE: <code>all</code> can only be used if <code>g</code>
	 * 			  is a {@link RuleGraph} and if <code>g</code> is properly
	 * 			  contained in a {@link GraphRule}.
	 */
	public SubgraphIterator(final Graph g, final boolean all, final Graph forbidden) { // NOPMD by bbecker on 04.03.09 11:05
		assert g != null : "you passed null as paramter to the constructor";
		this.graph = g;
		//bitMask = this.calculateBitMask();
		
		Map<NodeType, Integer> nodeTypes = null;
		Map<EdgeType, Integer> edgeTypes = null;
		if (forbidden != null) {
			nodeTypes = InvariantCheckerUtil.calculateNodeTypeCount(forbidden);
		}
		if (forbidden != null) {
			edgeTypes = InvariantCheckerUtil.calculateEdgeTypeCount(forbidden);
		}
		
		if (forbidden == null) {
			this.sizeOfEdges = g == null ? 0 : g.getEdges().size();
			this.sizeOfNodes = this.graph.getNodes().size();
			this.nodeArray = new Node[this.graph.getNodes().size()];
		} else {
			int edgeSize = 0;
			int nodeSize = 0;			
			for (final Iterator<Node> i = this.graph.getNodes().iterator(); i.hasNext();) {
				Node n = i.next();
				if (forbidden == null || nodeTypes.containsKey(n.getInstanceOf())) {
					nodeSize++;
				}
			}			
			for (final Iterator<Edge> i = this.graph.getEdges().iterator(); i.hasNext();) {
				Edge e = i.next();
				if (forbidden == null || edgeTypes.containsKey(e.getInstanceOf())) {
					edgeSize++;
				}			
			}
			//System.out.println("nodeSize: " + nodeSize);
			//System.out.println("edgeSize: " + edgeSize);
			this.nodeArray = new Node[nodeSize];
			this.sizeOfNodes = nodeSize;
			this.sizeOfEdges = edgeSize;
		}
		
		int index = 0;
		for (final Iterator<Node> i = this.graph.getNodes().iterator(); i.hasNext();) {
			Node n = i.next();
			if (forbidden == null || nodeTypes.containsKey(n.getInstanceOf())) {
				nodeArray[index] = n;
				index++;
			}
			//nodeArray[index] = i.next();
		}
		this.edgeArray = new Edge[this.sizeOfEdges];
		this.origEdgeArray = new Edge[this.sizeOfEdges];
		index = 0;
		for (final Iterator<Edge> i = this.graph.getEdges().iterator(); i.hasNext();) {
			Edge e = i.next();
			if (forbidden == null || edgeTypes.containsKey(e.getInstanceOf())) {
				origEdgeArray[index] = e;
				index++;
			}
			//this.origEdgeArray[index] = i.next();
		}
		this.edgeBitSet = new BitSet(this.sizeOfEdges);

		this.nodeBitSet = new BitSet(this.nodeArray.length);
		
		boolean tmpReturnAllSubgraphs = true;
		if (all == false) {
			if (g.eClass() == SamrulesPackage.eINSTANCE.getRuleGraph()) {
				GraphRule graphRule = (GraphRule) g.eContainer();
				if (graphRule.getLeft() != null && graphRule.getRight() != null && graphRule.getLeft() != graphRule.getRight()) {
					tmpReturnAllSubgraphs = false;
				}
				/*if (g.eGet(SamrulesPackage.eINSTANCE.getRuleGraph_IsLeft()) != null) {
					GraphRule graphRule = (GraphRule)(g.eGet(SamrulesPackage.eINSTANCE.getRuleGraph_IsLeft()) != null ? g.eGet(SamrulesPackage.eINSTANCE.getRuleGraph_IsLeft()) : g.eGet(MetamodelPackage.eINSTANCE.getRuleGraph_IsRight()));
					if (graphRule.getLeft() != null && graphRule.getRight() != null && graphRule.getLeft() != graphRule.getRight()) {
						tmpReturnAllSubgraphs = false;
					} 
				} */
			} 
		}
		this.returnAllSubgraphs = tmpReturnAllSubgraphs;
	}

	private BitSet calculateBitMask() {
		if (this.graph.eContainer() == null || this.graph.eContainer().eClass() != SamrulesPackage.eINSTANCE.getGraphRule()) {
			return null;
		}
		RuleGraph leftSide = ((GraphRule) this.graph.eContainer()).getLeft();
		BitSet result = new BitSet(this.graph.getNodes().size());
		int index = 0;
		for (final Iterator<Node> i = this.graph.getNodes().iterator(); i.hasNext(); index++) {
			Node n = i.next();
			if (n.eClass() == SamrulesPackage.eINSTANCE.getCreatedNode()) {
				result.set(index);
			}			
		}
		for (Edge e : this.graph.getEdges()) {
			if (e.eClass() == SamrulesPackage.eINSTANCE.getCreatedEdge()) {
				result.set(this.graph.getNodes().indexOf(e.getSource()));
				result.set(this.graph.getNodes().indexOf(e.getTarget()));
			}
		}		
		
		for (Edge e : leftSide.getEdges()) {
			if (e.eClass() == SamrulesPackage.eINSTANCE.getDeletedEdge()) {
				if (e.getSource().eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
					PreservedNode presN = (PreservedNode) e.getSource();
					result.set(this.graph.getNodes().indexOf(presN.getRefInRule()));
				}
				if (e.getTarget().eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
					PreservedNode presN = (PreservedNode) e.getTarget();
					result.set(this.graph.getNodes().indexOf(presN.getRefInRule()));
				}				
			}
		}
		
		GraphWithNacs left = SamGraphInvCheckGraphAdapter.getInstance(leftSide);
		for (NegativeApplicationCondition nac : left.getNacs()) {
			for (Edge e : nac.getEdges()) {
				if (e.getSource().eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
					PreservedNode presN = (PreservedNode) e.getSource();
					result.set(this.graph.getNodes().indexOf(presN.getRefInRule()));
				}
				if (e.getTarget().eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
					PreservedNode presN = (PreservedNode) e.getTarget();
					result.set(this.graph.getNodes().indexOf(presN.getRefInRule()));
				}
			}
		}
		//System.out.println(result);
		return result;
	}
	
	public boolean isReturnAllSubgraphs() {
		return this.returnAllSubgraphs;
	}

	/**
	 * No comment provided by developer, please add a comment to improve
	 * documentation.
	 */
	private void computeNextState()
	{
		if (this.maxNumberEdges > 0 && this.edgeBitSet.cardinality() < maxNumberEdges)
		{
			incrementBitSet (this.edgeBitSet);
		}
		else
		{
			incrementBitSet (this.nodeBitSet);
			//this.edgeArray = null;
			
			if (nextStatetmpEdgeSet == null) {
				this.nextStatetmpEdgeSet = new HashSet<Edge>();
			} else {
				this.nextStatetmpEdgeSet.clear();
			}
			
			for (int i = 0; i < this.sizeOfEdges; i++)
			{
				final int srcIndex = indexInArray ( this.origEdgeArray[i].getSource());
				if (srcIndex >=0 && this.nodeBitSet.get (srcIndex))
				{
					final int tgtIndex = indexInArray ( this.origEdgeArray[i].getTarget());
					if (this.nodeBitSet.get (tgtIndex))
					{
						nextStatetmpEdgeSet.add (this.origEdgeArray[i]);
					}
				}
			}
			this.maxNumberEdges = this.nextStatetmpEdgeSet.size();
			if (!this.nextStatetmpEdgeSet.isEmpty()) {
				this.edgeArray = nextStatetmpEdgeSet.toArray (this.edgeArray);
			}
			if (this.edgeBitSet == null)
			{
				this.edgeBitSet = new BitSet();
			}
			else
			{
				this.edgeBitSet.clear();
			}
		}
	}

	/**
	 * No comment provided by developer, please add a comment to improve
	 * documentation.
	 * 
	 * @param edge
	 *            No description provided
	 * @param node
	 *            No description provided
	 * @return No description provided
	 */
	private int indexInArray(final Node node) {
		int result = -1;
		for (int i = 0; result == -1 && i < this.nodeArray.length; i++) {
			if (this.nodeArray[i] == node) {
				result = i;
			}
		}
		return result;
	}

	/**
	 * Increment the binary value represented by the <code>BitSet</code>
	 * <code>bs</code>
	 * 
	 * @param bs
	 *            the <code>BitSet</code> that gets incremented
	 */
	private void incrementBitSet(final BitSet bs) {
		this.cacheValid = false;
		final int firstClear = bs.nextClearBit(0);
		bs.set(firstClear);
		for (int i = firstClear - 1; i >= 0; i--) {
			bs.clear(i);
		}
	}
	
	/**
	 * Translates a given <code>Graph</code> into a subgraph containing all the graph's elements.
	 * @param g the <code>Graph</code> to translate.
	 * @return a set containing all of the graph's elements in an arbitrary order.
	 */
	public static Set<AnnotatedElem> graphToSubGraph(final Graph g) {
		Set<AnnotatedElem> result = null;
		if (g != null) {
			result = new HashSet<AnnotatedElem>(g.getEdges().size() + g.getNodes().size());
			result.addAll(g.getEdges());
			result.addAll(g.getNodes());
			//for (NegativeApplicationCondition nac : ((GraphWithNacs) Platform.getAdapterManager().getAdapter(g, GraphWithNacs.class)).getNacs()) {
			for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(g).getNacs()) {
				result.addAll(nac.getEdges());
				result.addAll(nac.getNodes());
			}
		}
		return result;
	}
	
	
	
	public static Set<AnnotatedElem> graphToPosSubGraph(final Graph g) {
		Set<AnnotatedElem> result = null;
		if (g != null) {
			result = new HashSet<AnnotatedElem>(g.getEdges().size() + g.getNodes().size());
			result.addAll(g.getEdges());
			result.addAll(g.getNodes());
		}
		return result;
	}
	
	
	public static Match partialNacToGraph(NegativeApplicationCondition nac1, Set<EdgeType> edgeTypes, Set<NodeType> nodeTypes) {
		Graph g = SamgraphFactory.eINSTANCE.createGraph();
		Match matching = SamtraceFactory.eINSTANCE.createMatch();
		for (Node src : nac1.getNodes()) {
			if (nodeTypes.contains(src.getInstanceOf())) {
				Node n = SamgraphFactory.eINSTANCE.createNode();
				n = src.copy();
				//n.setGraph(g);
				g.getNodes().add(n);
				matching.getNodeMatching().put(src, n);
			}
		}
		for (Edge src : nac1.getEdges()) {
			if (edgeTypes.contains(src.getInstanceOf())) {
				Edge e = SamgraphFactory.eINSTANCE.createEdge();
				e = src.copy();
				//e.setGraph(g);
				g.getEdges().add(e);
				//if (src.partOfNacInterface()) {				
					if (!matching.getNodeMatching().containsKey(src.getSource())) {
						Node n = SamgraphFactory.eINSTANCE.createNode();
						n = src.getSource().copy();
						//n.setGraph(g);
						g.getNodes().add(n);
						matching.getNodeMatching().put(src.getSource(), n);
					}
					if (!matching.getNodeMatching().containsKey(src.getTarget())) {
						Node n = SamgraphFactory.eINSTANCE.createNode();
						n = src.getTarget().copy();
						//n.setGraph(g);
						g.getNodes().add(n);
						matching.getNodeMatching().put(src.getTarget(), n);
					}
				//}
				e.setSource(matching.getNodeMatching().get(src.getSource()));
				e.setTarget(matching.getNodeMatching().get(src.getTarget()));
				matching.getEdgeMatching().put(src, e);
			}
		}
		
		return matching;
	}	

	public static Match nacToGraph(NegativeApplicationCondition nac1) {
		Graph g = SamgraphFactory.eINSTANCE.createGraph();
		Match matching = SamtraceFactory.eINSTANCE.createMatch();
		for (Node src : nac1.getNodes()) {
			Node n = SamgraphFactory.eINSTANCE.createNode();
			n = src.copy();
			//n.setGraph(g);
			g.getNodes().add(n);
			matching.getNodeMatching().put(src, n);
		}
		for (Edge src : nac1.getEdges()) {
			Edge e = SamgraphFactory.eINSTANCE.createEdge();
			e = src.copy();
			//e.setGraph(g);
			g.getEdges().add(e);
			if (src.partOfNacInterface()) {				
				if (!nac1.getNodes().contains(src.getSource()) && !matching.getNodeMatching().containsKey(src.getSource())) {
					Node n = SamgraphFactory.eINSTANCE.createNode();
					n = src.getSource().copy();
					//n.setGraph(g);
					g.getNodes().add(n);
					matching.getNodeMatching().put(src.getSource(), n);
				}
				if (!nac1.getNodes().contains(src.getTarget()) && !matching.getNodeMatching().containsKey(src.getTarget())) {
					Node n = SamgraphFactory.eINSTANCE.createNode();
					n = src.getTarget().copy();
					//n.setGraph(g);
					g.getNodes().add(n);
					matching.getNodeMatching().put(src.getTarget(), n);
				}
			}
			e.setSource(matching.getNodeMatching().get(src.getSource()));
			e.setTarget(matching.getNodeMatching().get(src.getTarget()));
			matching.getEdgeMatching().put(src, e);
		}
		
		return matching;
	}
	
	/**
	 * This method is used to translate a NAC to a graph while also keeping all positive elements the formal version of the NAC
	 * includes.
	 * 
	 * The flag isPartial indicates whether the NAC is a partial negative application condition, in which case the positive parts to
	 * be included in the NAC are determined by checking the annotations in the NAC. (This is the special case of partial NACs
	 * created by merging a forbidden subgraph and a right rule side. We do not need to check left application conditions which would
	 * also be partially integrated into the source pattern, because in our special example, rules do not have NACs.)
	 * 
	 * If it is not a partial NAC, we can simply copy all positive items from the graph.
	 *  
	 * @param nac1
	 * @return
	 */
	public static Match fullNacToGraph(NegativeApplicationCondition nac1, boolean isPartial) {
		boolean partial = isPartial;
		if (nac1.getAnnotations().isEmpty()) {
			partial = false;
		} else {
			partial = true;
		}
		Graph g = SamgraphFactory.eINSTANCE.createGraph();
		Match matching = SamtraceFactory.eINSTANCE.createMatch();
		for (Node src : nac1.getNodes()) {
			Node n = SamgraphFactory.eINSTANCE.createNode();
			n = src.copy();
			//n.setGraph(g);
			g.getNodes().add(n);
			matching.getNodeMatching().put(src, n);
		}
		for (Edge src : nac1.getEdges()) {
			Edge e = SamgraphFactory.eINSTANCE.createEdge();
			e = src.copy();
			//e.setGraph(g);
			g.getEdges().add(e);
			if (src.partOfNacInterface()) {				
				if (!nac1.getNodes().contains(src.getSource()) && !matching.getNodeMatching().containsKey(src.getSource())) {
					Node n = SamgraphFactory.eINSTANCE.createNode();
					n = src.getSource().copy();
					//n.setGraph(g);
					g.getNodes().add(n);
					matching.getNodeMatching().put(src.getSource(), n);
				}
				if (!nac1.getNodes().contains(src.getTarget()) && !matching.getNodeMatching().containsKey(src.getTarget())) {
					Node n = SamgraphFactory.eINSTANCE.createNode();
					n = src.getTarget().copy();
					//n.setGraph(g);
					g.getNodes().add(n);
					matching.getNodeMatching().put(src.getTarget(), n);
				}
			}
			e.setSource(matching.getNodeMatching().get(src.getSource()));
			e.setTarget(matching.getNodeMatching().get(src.getTarget()));
			matching.getEdgeMatching().put(src, e);
		}
		
		for (Node src : nac1.getGraph().getNodes()) {
			if (!isPartial) {
				if (!matching.getNodeMatching().containsKey(src)) {
					Node n = src.copy();
					g.getNodes().add(n);
					matching.getNodeMatching().put(src, n);
				}
			} else {
				if (!matching.getNodeMatching().containsKey(src)) {
					for (Annotation an : nac1.getAnnotations()) {
						if (an.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM) && an.getTarget() == src) {
							Node n = src.copy();
							g.getNodes().add(n);
							matching.getNodeMatching().put(src, n);
							break;
						}
					}										
				}
			}
		}
		for (Edge src : nac1.getGraph().getEdges()) {
			if (!isPartial) {
				if (!matching.getEdgeMatching().containsKey(src)) {
					Edge e = src.copy();
					g.getEdges().add(e);
					e.setSource(matching.getNodeMatching().get(src.getSource()));
					e.setTarget(matching.getNodeMatching().get(src.getTarget()));
					matching.getEdgeMatching().put(src, e);
				}
			} else {
				if (!matching.getEdgeMatching().containsKey(src)) {
					for (Annotation an : nac1.getAnnotations()) {
						if (an.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM) && an.getTarget() == src) {
							Edge e = src.copy();
							g.getEdges().add(e);
							e.setSource(matching.getNodeMatching().get(src.getSource()));
							e.setTarget(matching.getNodeMatching().get(src.getTarget()));
							matching.getEdgeMatching().put(src, e);
							break;
						}
					}		
				}
			}
		}
		
		return matching;
	}
	
	
	public static Match fullNacToRuleGraph(NegativeApplicationCondition nac1, boolean isPartial) {
		boolean partial = isPartial;
		if (nac1.getAnnotations().isEmpty()) {
			partial = false;
		} else {
			partial = true;
		}
		RuleGraph g = SamrulesFactory.eINSTANCE.createRuleGraph();
		Match matching = SamtraceFactory.eINSTANCE.createMatch();
		for (Node src : nac1.getNodes()) {
			Node n = SamgraphFactory.eINSTANCE.createNode();
			n = src.copy();
			//n.setGraph(g);
			g.getNodes().add(n);
			matching.getNodeMatching().put(src, n);
		}
		for (Edge src : nac1.getEdges()) {
			Edge e = SamgraphFactory.eINSTANCE.createEdge();
			e = src.copy();
			//e.setGraph(g);
			g.getEdges().add(e);
			if (src.partOfNacInterface()) {				
				if (!nac1.getNodes().contains(src.getSource()) && !matching.getNodeMatching().containsKey(src.getSource())) {
					Node n = SamgraphFactory.eINSTANCE.createNode();
					n = src.getSource().copy();
					//n.setGraph(g);
					g.getNodes().add(n);
					matching.getNodeMatching().put(src.getSource(), n);
				}
				if (!nac1.getNodes().contains(src.getTarget()) && !matching.getNodeMatching().containsKey(src.getTarget())) {
					Node n = SamgraphFactory.eINSTANCE.createNode();
					n = src.getTarget().copy();
					//n.setGraph(g);
					g.getNodes().add(n);
					matching.getNodeMatching().put(src.getTarget(), n);
				}
			}
			e.setSource(matching.getNodeMatching().get(src.getSource()));
			e.setTarget(matching.getNodeMatching().get(src.getTarget()));
			matching.getEdgeMatching().put(src, e);
		}
		
		for (Node src : nac1.getGraph().getNodes()) {
			if (!isPartial) {
				if (!matching.getNodeMatching().containsKey(src)) {
					Node n = src.copy();
					g.getNodes().add(n);
					matching.getNodeMatching().put(src, n);
				}
			} else {
				if (!matching.getNodeMatching().containsKey(src)) {
					for (Annotation an : nac1.getAnnotations()) {
						if (an.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM) && an.getTarget() == src) {
							Node n = src.copy();
							g.getNodes().add(n);
							matching.getNodeMatching().put(src, n);
							break;
						}
					}										
				}
			}
		}
		for (Edge src : nac1.getGraph().getEdges()) {
			if (!isPartial) {
				if (!matching.getEdgeMatching().containsKey(src)) {
					Edge e = src.copy();
					g.getEdges().add(e);
					e.setSource(matching.getNodeMatching().get(src.getSource()));
					e.setTarget(matching.getNodeMatching().get(src.getTarget()));
					matching.getEdgeMatching().put(src, e);
				}
			} else {
				if (!matching.getEdgeMatching().containsKey(src)) {
					for (Annotation an : nac1.getAnnotations()) {
						if (an.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM) && an.getTarget() == src) {
							Edge e = src.copy();
							g.getEdges().add(e);
							e.setSource(matching.getNodeMatching().get(src.getSource()));
							e.setTarget(matching.getNodeMatching().get(src.getTarget()));
							matching.getEdgeMatching().put(src, e);
							break;
						}
					}		
				}
			}
		}
		
		return matching;
	}
	
	
	
	/**
	 * This method extracts a subgraph given by the parameters from a graph by copying the passed elements to a new graph and returning
	 * the match.
	 * 
	 * @param nodes
	 * @param edges
	 * @return
	 */
	public static Match extractSubgraph(Set<Node> nodes, Set<Edge> edges) {
		Match result = SamtraceFactory.eINSTANCE.createMatch();
		Graph subgraph = SamgraphFactory.eINSTANCE.createGraph();
		for (Node src : nodes) {
			Node n = src.copy();
			subgraph.getNodes().add(n);
			result.getNodeMatching().put(src, n);
		}
		for (Edge src : edges) {
			Edge e = src.copy();
			subgraph.getEdges().add(e);
			e.setSource(result.getNodeMatching().get(src.getSource()));
			e.setTarget(result.getNodeMatching().get(src.getTarget()));
			result.getEdgeMatching().put(src, e);
		}		
		return result;
	}
	
	public static Match getUnmatchedPositivePart(Graph hostGraph, Match matching, NegativeApplicationCondition nac) {
		Graph result = SamgraphFactory.eINSTANCE.createGraph();
		Match tmp = SamtraceFactory.eINSTANCE.createMatch();
		
		for (Node src : hostGraph.getNodes()) {
			if (!matching.getNodeMatching().containsValue(src)) {
				Node n = src.copy();
				//n.setGraph(result);
				result.getNodes().add(n);
				tmp.getNodeMatching().put(src, n);
			}
		}
		for (Edge src : nac.getEdges()) {
			if (src.partOfNacInterface()) {
				//if (!nac.getNodes().contains(src.getSource()) && !tmp.getNodeMatching().containsKey(src.getSource())) {
				if (!InvariantCheckerUtil.isNegated(src.getSource()) && !tmp.getNodeMatching().containsKey(src.getSource())) {
					Node n = src.getSource().copy();
					//n.setGraph(result);
					result.getNodes().add(n);
					tmp.getNodeMatching().put(src.getSource(), n);
				}				
				if (!InvariantCheckerUtil.isNegated(src.getTarget()) && !tmp.getNodeMatching().containsKey(src.getTarget())) {
					Node n = src.getTarget().copy();
					//n.setGraph(result);
					result.getNodes().add(n);
					tmp.getNodeMatching().put(src.getTarget(), n);
				}
			}
		}
		for (Edge src : hostGraph.getEdges()) {
			if (!matching.getEdgeMatching().containsValue(src)) {
				Edge e = src.copy();
				//e.setGraph(result);
				result.getEdges().add(e);
				
				// In some cases there might be unmatched edges with matched source
				// or target nodes (might for example occur when there are two edges between two nodes).
				// The following conditions are included to prevent dangling edges due to this.
				if (tmp.getNodeMatching().get(src.getSource()) == null) {
					Node newSrc = src.getSource().copy();
					//newSrc.setGraph(result);
					result.getNodes().add(newSrc);
					tmp.getNodeMatching().put(src.getSource(), newSrc);
				}
				if (tmp.getNodeMatching().get(src.getTarget()) == null) {
					Node newSrc = src.getTarget().copy();
					//newSrc.setGraph(result);
					result.getNodes().add(newSrc);
					tmp.getNodeMatching().put(src.getTarget(), newSrc);
				}
				
				e.setSource(tmp.getNodeMatching().get(src.getSource()));
				e.setTarget(tmp.getNodeMatching().get(src.getTarget()));
				tmp.getEdgeMatching().put(src, e);				
			}
		}		
		
		return tmp;
	}

	public static Set<AnnotatedElem> removeNACsFromGraph(Graph g) {
		Set<AnnotatedElem> result = null;
		if (g != null) {
			result = new HashSet<AnnotatedElem>(g.getEdges().size() + g.getNodes().size());
			for (Iterator<Node> iter = g.getNodes().iterator(); iter.hasNext(); result.add(iter.next()));
			for (Iterator<Edge> iter = g.getEdges().iterator(); iter.hasNext(); result.add(iter.next()));
			for (NegativeApplicationCondition nac : ((GraphWithNacs) (Platform.getAdapterManager().getAdapter(g, GraphWithNacs.class))).getNacs()) {
				for (Iterator<Node> iter = nac.getNodes().iterator(); iter.hasNext(); result.add(iter.next()));
				for (Iterator<Edge> iter = nac.getEdges().iterator(); iter.hasNext(); result.add(iter.next()));
			}
		}
		
		return result;
	}
	

	/**
	 * counts occurences of types in the given <code>Set</code>.</br>
	 * This method returns a <code>Map</code> with the nodes' types as keys and an <code>Integer</code>
	 * object storing their frequency of occurence
	 *
	 * @param subgraph  a <code>Set</code> representing a valid subgraph
	 * @return          <code>Map</code> with the frequency of occurence for each type
	 */
	public static Map<NodeType, Integer> computeTypesInSubgraph(Set<AnnotatedElem> subgraph) {
		Map<NodeType, Integer> m = new HashMap<NodeType, Integer>();
		if (subgraph != null) {
			for (Iterator<AnnotatedElem> iter = subgraph.iterator(); iter.hasNext();) {
				AnnotatedElem next = iter.next();
				if (next instanceof Node && !(InvariantCheckerUtil.isNegated((Node) next))) {
					Node node = (Node) next;
					Integer i = m.get(node.getInstanceOf());
					if (i != null) {
						m.put(node.getInstanceOf(), new Integer(i.intValue() + 1));
					} else {
						m.put(node.getInstanceOf(), new Integer(1));
					}
				}
			}
		}
		return m;
	}

}

/*
 * $Log$ Revision 1.2 2007/01/03 09:27:47 basilb removed compile errors caused
 * by wrong import declarations; introduced empty plugin class to ensure correct
 * loading
 * 
 */
