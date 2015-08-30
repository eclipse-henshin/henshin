package org.eclipse.emf.henshin.sam.invcheck.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.GCNACAdapter;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher.MatchingState.BackTrackReason;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher.MatchingState.MatchingOperation;
import org.eclipse.emf.henshin.sam.invcheck.filter.RuleApplicationFilter;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;


/**
 * Finds matchings between a set of graph items (current subgraph) and a host graph. <br>
 * <br>
 * Is used six times: <br><ul>
 * <li>To find intersections between the right part of a rule and a <a href="../../../../../doc/glossary.html">forbidden property</a>
 * in order to construct a counterexample. ({@link MatchSubgprahFilter}) </li>
 * <li>To translate NACs from a forbidden subgraph to a <a href="../../../../../doc/glossary.html">merged graph</a> when constructing a counterexample. ({@link NACTranslator}) </li>
 * <li>To ensure the applicability of a rule to a <a href="../../../../../doc/glossary.html">source graph pattern</a>. ({@link RuleApplicationFilter}) </li>
 * <li>To find a forbidden property in a source graph pattern. ({@link HybridPropertyFilter}) </li>
 * <li>To find the left part of a higher-priority rule in a source graph pattern. ({@link HybridGraphRuleFilter}) </li>
 * <li>To find the left part of an urgent rule in a <a href="../../../../../doc/glossary.html">target graph pattern</a>. ({@link HybridUrgentGraphRuleFilter}) </li>
 * </ul>
 *  <br>
 * The IPM matches only positive components. When matching NACs, separate IPM instances are launched and filled with positive representations of the NACs. <br>
 * <br>
 * The IPM can be executed in different modes: <br /><dl>
 * <dt>MATCH_CALLs</dt><dd> try to match the current subgraph to the host graph. NACs are matched with separate NAC_MATCH_CALLs. </dd>
 * <dt>NAC_MATCH_CALLs</dt><dd> try to match the current subgraph (being the positive representation of a NAC including its positive interface) to the positive representation of its NAC counterpart (host graph). </dd>
 * <dt>CHECK_NAC_CALLs</dt><dd> try to find a NAC (its positive representation, to be exact) as positive part in the host graph after the NAC has been matched to another NAC. Finding such a positive occurrence of a NAC would obviously result in failure of the current matching process. </dd>
 * <dt>NAC_TRANSLATION_CALLs</dt><dd>find all partial matchings of the positive representation of a NAC (current subgraph) in a merged graph (host graph). If the NAC can be matched completely, the result will be empty. </dd>
 * </dl>
 * @author    $Author: jfd $
 * @version   $Revision: 1328 $ $Date: 2014-04-07 19:34:14 +0200 (Mo, 07 Apr 2014) $
 */

public class IsomorphicPartMatcher implements AlgorithmComponent
{

	private static class MatchingStateFactory {
		
		/**
		 * The checkNodesStates array stores all available <code>MatchingStates</code> for checking nodes.
		 */
		private MatchingState[] checkNodeStates;
		
		/**
		 * the index of the next CHECK_NODE <code>MatchingState</code>.
		 */
		private int checkNodeStatesIndex = 0;
		
		/**
		 * The checkEdgeStates array stores all available <code>MatchingStates</code> for checking edges.
		 */
		private MatchingState[] checkEdgeStates;
		
		/**
		 * the index of the next CHECK_EDGE <code>MatchingState</code>.
		 */
		private int checkEdgeStatesIndex = 0;
		
		/**
		 * Initializes the factory with the specified number of <code>MatchingStates</code> for node and edge matching operations, respectively.
		 * @param non number of <code>MatchingStates</code> to create for matching <code>Nodes</code>.
		 * @param noe number of <code>MatchingStates</code> to create for matching <code>Edges</code>.
		 */
		public MatchingStateFactory(final int non, final int noe) {
			checkNodeStates = new MatchingState[non];
			checkEdgeStates = new MatchingState[noe];
			checkNodeStatesIndex = non - 1;
			checkEdgeStatesIndex = noe - 1;
			for (int i = 0; i < non; i++) {
				checkNodeStates[i] = new MatchingState(MatchingOperation.CHECK_NODE);
			}
			for (int i = 0; i < noe; i++) {
				checkEdgeStates[i] = new MatchingState(MatchingOperation.CHECK_EDGE);
			}
		}
		
		/**
		 * Same as <code>new MatchingStateFactory(n,n)</code>
		 * @see MatchingStateFactory#MatchingStateFactory(int, int)
		 * @param n
		 */
		public MatchingStateFactory(final int n) {
			this(n,n);
		}
		
		/**
		 * Same as <code>new MatchingStateFactory(20,20);</code>
		 * @see MatchingStateFactory#MatchingStateFactory(int, int)
		 */
		public MatchingStateFactory() {
			this(20,20);
		}
		
		/**
		 * Returns a new <code>MatchingState</code> for checking <code>Nodes</code>.<br />If the factory's internal array 
		 * still contains a <code>MathcingState</code> this one is returned. A new one will be created, otherwise.
		 * @return a <code>MatchingState</code> for matching <code>Nodes</code>.
		 */
		public MatchingState getCheckNodeState() {
			MatchingState result;
			if (checkNodeStatesIndex >= 0) {
				result = checkNodeStates[checkNodeStatesIndex];
				checkNodeStates[checkNodeStatesIndex] = null;
				checkNodeStatesIndex--;
				cleanMatchingState(result);
			} else {
				result = new MatchingState(MatchingOperation.CHECK_NODE);
			}
			return result;
		}
		
		/**
		 * Returns a new <code>MatchingState</code> for checking <code>Edges</code>.<br />If the factory's internal array 
		 * still contains a <code>MathcingState</code> this one is returned. A new one will be created, otherwise.
		 * @return a <code>MatchingState</code> for matching <code>Edges</code>.
		 */
		public MatchingState getCheckEdgeState() {
			MatchingState result;
			if (checkEdgeStatesIndex >= 0) {
				result = checkEdgeStates[checkEdgeStatesIndex];
				checkEdgeStates[checkEdgeStatesIndex] = null;
				checkEdgeStatesIndex--;
				cleanMatchingState(result);
			} else {
				result = new MatchingState(MatchingOperation.CHECK_EDGE);
			}
			return result;
		}
		
		
		public void releaseMatchingState(MatchingState ms) {
			switch(ms.matchOp) {
			case CHECK_NODE:
				if (checkNodeStatesIndex < checkNodeStates.length - 1) {
					checkNodeStatesIndex++;
					checkNodeStates[checkNodeStatesIndex] = ms;
				}
				break;
			case CHECK_EDGE:
				if (checkEdgeStatesIndex < checkEdgeStates.length - 1) {
					checkEdgeStatesIndex++;
					checkEdgeStates[checkEdgeStatesIndex] = ms;
				}
				break;
			}
		}
		
		private void cleanMatchingState(MatchingState ms) {
			ms.backtrack = BackTrackReason.NONE;
			ms.currentEdge = null;
			ms.currentNode = null;
			ms.edgeIterator = null;			
			ms.mappedEdgeIterator = null;
			ms.mappedToNode = null;
		}
	}

	/**
	 * The graph the current subgraph will be matched to. 
	 */
	private Graph hostGraph;

	/**
	 * A set of graph items representing the current subgraph to be matched to the host graph.
	 */
	private Set<AnnotatedElem> currentSubGraph;

	/**
	 * The data structure controlling the execution and order of the matching process.
	 */
	private Stack<MatchingState> stack = new Stack<MatchingState>();

	/**
	 * A data structure storing the node matching, edge matching and NAC matching the matcher has currenty built.
	 */
	private MatchWithNacs currentMatching;

	/**
	 * The order of the matching process for the current graphs.
	 */
	private LinkedList<MatchingState> matchingHistory = new LinkedList<MatchingState>();
	
	/**
	 * Stores the match mode that is currently active to control the way the matchers works.
	 */
	private MatchMode mode = MatchMode.MATCH_CALL;
	
	/**
	 * A list storing one matching for each matching step. Is used only within a NAC translation call.  
	 */
	private LinkedList<MatchWithNacs> iterativeMatchings = new LinkedList<MatchWithNacs>();
	
	/**
	 * A list preventing certain edges from being tried as "mapped" edges when executing a NAC translation call.
	 */
	private List<Edge> blacklist = new LinkedList<Edge>();
	
	/**
	 * A list preventing certain nodes from being tried as "mapped" edges when executing a NAC translation call.
	 */
	private List<Node> nodeBlacklist = new LinkedList<Node>();	

	/**
	 * Stores the node degrees of the current subgraph's nodes.
	 */
	private Map<Node, Integer> nodeDegrees;
	
	/**
	 * Stores the number of types in the host graph
	 */
	private Map<NodeType, Integer> typeCount;
	
	/**
	 * Saves the nacs in the subgraph
	 */
	private Set<NegativeApplicationCondition> nacs = null;
	
	/**
	 * Iterates over the nacs in the subgraph
	 */
	private Iterator<NegativeApplicationCondition> nacIterator = null;

	/**
	 * Only used in nac match call or nac translation call.
	 */
	private Match nacInterfaceMatch = null;	
	
	/**
	 * Gets the graph or pattern the current subgraph will be matched to.
	 *
	 * @return   The host graph
	 */
	public Graph getHostGraph()
	{		
		return this.hostGraph;
	}


	/**
	 * Sets the graph or pattern the current subgraph will be matched to and calculates the number of each node type.
	 *
	 * @param value  The new host graph
	 */
	public void setHostGraph (final Graph value)
	{	
		this.hostGraph = value;
		this.typeCount = calculateTypeCount();
	}
	
	/**
	 * Calculates the type count.
	 */
	private Map<NodeType, Integer> calculateTypeCount() {		
		Map<NodeType, Integer> result = new HashMap<NodeType, Integer>();
		if (this.hostGraph == null) {
			return result;
		}
		for (Node n : this.hostGraph.getNodes()) {
			NodeType type = n.getInstanceOf();
			if (result.containsKey(type)) {
				result.put(type, result.get(type) + 1);
			} else {
				result.put(type, 1);
			}
		}
		return result;
	}


	/**
	 * Indicates a failure to match NACs. New positive matching is required.
	 */
	boolean positiveFailure = false;
	
	/**
	 * This attribute doesn't represent a GraphRule, it only represents the
	 * right handed side of a GraphRule. More generally speaking, it represents the
	 * graph containing the current subgraph (which might be the same graph).
	 */
	private Graph pattern;


	/**
	 * Gets the graph containing the current subgraph.
	 *
	 * @return  The graph containing the current subgraph
	 */
	public Graph getPattern()
	{
		return this.pattern;
	}


	/**
	 * Sets the graph containing the current subgraph.
	 *
	 * @param value the graph containing the current subgraph
	 */
	public void setPattern (Graph value)
	{
		this.pattern = value;
	}	
	
	private boolean context = false;
	private Map<Match, RuleGraph> incompleteNacMatchings;
	private Set<Graph> restrictingConstraints;
	
	public Map<Match, RuleGraph> getIncompleteMatchings() {
		return this.incompleteNacMatchings;
	}	
	
	public void setRestrictingConstraint(Set<Graph> restrictingConstraints) {
		this.restrictingConstraints = restrictingConstraints;		
	}
	
	public void addRestrictingConstraint(Graph restrictingConstraint) {
		if (this.restrictingConstraints == null) {
			this.restrictingConstraints = new HashSet<Graph>();
		}
		this.restrictingConstraints.add(restrictingConstraint);
	}
	
	private Iterator<AnnotatedElem>	startNodeIter;
	
	
	private MatchingStateFactory theFactory;
	/**
	 * A <code>StartConfiguration</code> encapsulates all information that is required
	 * to start the matching of a connected component of the {@link IsomorphicPartMatcher#currentSubGraph}.
	 *  
	 * @author bb
	 *
	 */
	private static class StartConfiguration {
		/**
		 * the current connected component's start node
		 */
		public Node startNode = null;
		
		/**
		 * the {@link IsomorphicPartMatcher#matchingHistory matchingHistories} index at the time
		 * this <code>StartConfiguration</code> has been created.
		 */
		public int historyIndex = 0;
		
		/**
		 * The <code>Iterator</code> of <code>Nodes</code> contained in the hostgraph. The <code>Iterator</code>
		 * will be reset each time a match of a previous connected component has to be revoked. 
		 */
		public Iterator<Node> hostNodeIter = null;
	}

	private List<StartConfiguration> connectedComponents = null;
	
	private int connectedComponentIndex = 0;
	
	/**
	 * Resets this <code>IsomorphicPartMatcher</code> instance.<br />
	 * In particular this method clears the {@link #stack} or creates an empty
	 * one and sets the fields {@link #currentMatching}, {@link #startNode} and
	 * {@link #hostNodeIter} as well as certain flags to null/their default value.
	 */
	public void reset() {
		if (stack == null) {
			stack = new Stack<MatchingState>();
		} else
			stack.clear();
		
		if (theFactory == null) {
			theFactory = new MatchingStateFactory();
		}

		if (matchingHistory == null) {
			matchingHistory = new LinkedList<MatchingState>();
		} else {
			for (MatchingState ms : matchingHistory) {
				theFactory.releaseMatchingState(ms);
			}
			matchingHistory.clear();
		}
		
		if (connectedComponents == null) {
			this.connectedComponents = new ArrayList<StartConfiguration>();
		} else {
			this.connectedComponents.clear();
		}
		this.connectedComponents.add(new StartConfiguration());
		this.connectedComponentIndex = 0;

		this.iterativeMatchings.clear();
		this.currentMatching = null;
		this.nodeDegrees = null;
		this.startNodeIter = null;
		this.blacklist.clear();
		this.nodeBlacklist = null;
		this.nacIterator = null;
		this.nacs = null;
		this.positiveFailure = false;
		this.nacInterfaceMatch = null;
		this.context = false;
	}

	public void setContext(boolean context) {
		this.context = context;
	}
	
	private void addFirstCheckNACState() {		
		// if there is an check edge state with currentEdge == null, remove this entry
		if (stack.size() > 0) {
			if (stack.peek().matchOp == MatchingOperation.CHECK_EDGE && stack.peek().backtrack == BackTrackReason.NONE && stack.peek().currentEdge == null) {
				stack.pop();
			}
		}
		
		if (nacs == null) {
			nacs = new HashSet<NegativeApplicationCondition>();
			for (AnnotatedElem gI : currentSubGraph) {
				if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(gI.eClass())) {
					if (InvariantCheckerUtil.isNegated((Edge) gI)) {
						Edge e = (Edge) gI;					
						NegativeApplicationCondition nac = GCNACAdapter.getInstance(InvariantCheckerUtil.getHighestCondition(e));
						nacs.add(nac);
					}
				} else {
					if (InvariantCheckerUtil.isNegated((Node) gI)) {
						Node n = (Node) gI;
						NegativeApplicationCondition nac = GCNACAdapter.getInstance(InvariantCheckerUtil.getHighestCondition(n));
						nacs.add(nac);
					}
				}
			}
		}
		nacIterator = nacs.iterator();
		MatchingState ms = new MatchingState(MatchingOperation.CHECK_NAC);
		ms.currentNAC = nacIterator.next();
		stack.push(ms);		
	}
	
	/**
	 * Returns the next matching for the actual values of: {@link #pattern},{@link #hostGraph} and {@link #currentSubGraph}.<br />
	 * <p>The first invocation of this method after {@link #reset()} has been invoked, initializes the <code>IsomorphicPartMatcher</code>'s internal bookkeeping and returns the first matching of the {@link #currentSubGraph| current subgraph} in the {@link #hostGraph| host graph}. Following invocations invalidate the last matched element and start a backtracking procedure to find the next matching. If no more matching could be found the method returns <code>null</code> </p>
	 * The execution of this method might create additional instances of the matcher and execute them. 
	 * <p>elements from the {@link #pattern| graph pattern} are returned as the keys and elements from the {@link #hostGraph| host graph} are returned as the values.</p>
	 * <br>
	 * A matching for a MATCH_CALL or a CHECK_NAC_CALL must fulfill the following conditions:<br>
	 * <ul>
	 * <li>All nodes from the current subgraph have been matched.</li>
	 * <li>All edges from the current subgraph have been matched.</li>
	 * <li>All NACs from the current subgraph have been matched (by using separate NAC_MATCH_CALLs).</li>
	 * </ul>
	 * <br>
	 * A matching for a NAC_MATCH_CALL can be correct without having matched all nodes and edges from the subgraph. This requires
	 * that the host graph is already completely matched so that the subgraph contains more the same, but more elements and thus is stricter
	 * that the host graph.<br>
	 * <br>
	 * Matchings for a NAC_TRANSLATION_CALL are returned as a list of partial matchings, see {@link #getIterativeMatchings()}.<br>
	 * <br>
	 * If no matching could be found, null will be returned. 
	 * 
	 * @return A <code>Match</code> representing a matching of {@link #pattern} in {@link #hostGraph} w.r.t to the {@link #currentSubGraph} or <code>null</code>.
	 */
	public Match getNextMatching() {
		
		// When executing a NAC translation call, we only search for partial matches. Thus, the compareTypes method is not applicable in that case. 
		if (mode != MatchMode.NAC_TRANSLATION_CALL && !compareTypes(currentSubGraph, hostGraph)) {
			return null;
		}
		
		if (mode != MatchMode.NAC_TRANSLATION_CALL && posSizeOfSubgraph(currentSubGraph) > this.hostGraph.getEdges().size() + this.hostGraph.getNodes().size()) {
			return null;
		}
		
		if (mode == MatchMode.MATCH_CALL && numberOfNacs(currentSubGraph) > 0 && (hostGraph.eClass() != SamrulesPackage.eINSTANCE.getRuleGraph() || ((RuleGraph) hostGraph).getCondition() == null)) {
			return null;
		}
		
		MatchingState ms;
		//System.out.println("===");
		boolean reentrantCall = false;
		boolean triedAllHostNodes = false;
		if (this.currentMatching == null) {
			this.currentMatching = NacFactory.eINSTANCE.createMatchWithNacs();
		} 
		if (this.theFactory == null) {
			this.theFactory = new MatchingStateFactory();
		}
		if (this.connectedComponents == null) {
			this.connectedComponents = new ArrayList<StartConfiguration>();
			this.connectedComponents.add(new StartConfiguration());
		}
		if (this.currentSubGraph == null) {
			throw new IllegalStateException("initialize the object's current subgraph before invoking getNextMatching");
		} else if (this.nodeDegrees == null) {
			/*
			 * compute the node degrees of the current subgraph's nodes. We cannot use Node.getIncomingEdge().size() etc. 
			 * as the this returns a value that is only valid for the complete pattern. But our search is restricted to
			 * the currentSubgraph attribute and so have to be the node's degrees. 
			 */
			this.nodeDegrees = new HashMap<Node, Integer>(this.currentSubGraph.size());
			for (Iterator<AnnotatedElem> iter = this.currentSubGraph.iterator(); iter.hasNext(); ) {
				final AnnotatedElem gi = iter.next();
				if (SamgraphPackage.eINSTANCE.getNode().isSuperTypeOf(gi.eClass())) {			
				 if (!InvariantCheckerUtil.isNegated((Node) gi) && !this.nodeDegrees.containsKey(gi)) {
					 this.nodeDegrees.put((Node)gi, 0);
				 }
				} else if (!InvariantCheckerUtil.isNegated((Edge) gi)) {
					final Edge edge = (Edge) gi;
					if (this.nodeDegrees.containsKey(edge.getSource())){
						this.nodeDegrees.put(edge.getSource(), this.nodeDegrees.get(edge.getSource()) + 1);
					} else
						this.nodeDegrees.put(edge.getSource(), 1);
					if (this.nodeDegrees.containsKey(edge.getTarget())){
						this.nodeDegrees.put(edge.getTarget(), this.nodeDegrees.get(edge.getTarget()) + 1);
					} else
						this.nodeDegrees.put(edge.getTarget(), 1);
				}
			}
		}
		
		if ((mode == MatchMode.MATCH_CALL || mode == MatchMode.NAC_MATCH_CALL) && (this.stack != null && this.stack.size() > 0)) {
			/*
			 * The method is invoked after a previous match has been found.
			 * Thus, we invalidate the stack's topmost entry, to start the
			 * matcher's backtracking functionality. This is only applicable when
			 * executing a match call (as for example when executing the MatchSubgraphFilter).
			 */
			reentrantCall = true;
			handleReentrantCall();
		}
		
		/*
		 *  The matching process will continue while there are unmatched nodes, edges or NACs in the current subgraph.
		 *  The process may end prematurely in the case of a NAC match call when the host graph is found to be a stricter NAC.
		 */
		while ((reentrantCall || (((InvariantCheckerUtil.positiveSize(this.currentMatching) < posSizeOfSubgraph(currentSubGraph)) || (this.currentMatching.getNacMatching().size() < numberOfNacs(currentSubGraph))) && (triedAllHostNodes == false)))) {
			
			
			if (!reentrantCall && mode == MatchMode.MATCH_CALL && InvariantCheckerUtil.positiveSize(this.currentMatching) == posSizeOfSubgraph(currentSubGraph) && numberOfNacs(currentSubGraph) > 0) {
				if (nacIterator == null && !positiveFailure) {					
					// positive matching is complete, build first check nac state
					addFirstCheckNACState();
				}
				if (positiveFailure) {
					positiveFailure = false;
				}
			}
			reentrantCall = false;
			
			if (stack.size() > 0) {
				ms = stack.peek();
				//if (mode != MatchMode.NAC_TRANSLATION_CALL || mode == MatchMode.NAC_MATCH_CALL) {
					//System.out.println("now checking: " + ms.toString() + " " + ms.backtrack);
				//}
				
				switch (ms.matchOp) {
				case CHECK_NODE:
					checkNode(ms);
					break;
				case CHECK_EDGE:
					checkEdge(ms);
					break;
				case CHECK_NAC:
					checkNAC();
					break;
				}				
			}
			else {
				// Stack is empty, mode is MATCH_CALL, NAC_MATCH_CALL or NAC_TRANSLATION_CALL.
				// Have to find an initial node.
				// Will happen in two cases: 1) first call of gNM for a specific subgraph or 2) stack has been completely removed because of backtracking.  
				triedAllHostNodes = createInitialNodePair();
				if (triedAllHostNodes && connectedComponentIndex > 0) {
					for (int i = connectedComponents.get(connectedComponentIndex - 1).historyIndex; i < connectedComponents.get(connectedComponentIndex).historyIndex; i++) {
						if (i < matchingHistory.size())
							stack.push(matchingHistory.get(i));
					}
					stack.peek().backtrack = BackTrackReason.FAILURE;
					this.connectedComponentIndex--;
					triedAllHostNodes = false;
				}
				if (this.connectedComponents.get(connectedComponentIndex).startNode == null) {
					return null;
				}
			}
		}		
		return triedAllHostNodes ? null : this.currentMatching.copy();
	}
		
	/**
	 * This method determines the number of NACs contained in the given subgraph.
	 * 
	 * @param currentSubGraph the graph whose NACs will be counted
	 * @return the number of NACs
	 */
	private int numberOfNacs(Set<AnnotatedElem> currentSubGraph) {
		Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
		for (AnnotatedElem gI : currentSubGraph) {
			if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(gI.eClass())) {
				if (InvariantCheckerUtil.isNegated((Edge) gI)) {
					Edge e = (Edge) gI;					
					NegativeApplicationCondition nac = GCNACAdapter.getInstance(InvariantCheckerUtil.getHighestCondition(e));
					nacs.add(nac);
				}
			} else {
				if (InvariantCheckerUtil.isNegated((Node) gI)) {
					Node n = (Node) gI;
					NegativeApplicationCondition nac = GCNACAdapter.getInstance(InvariantCheckerUtil.getHighestCondition(n));
					nacs.add(nac);
				}
			}
		}
		return nacs.size();
	}

	/**
	 * This method determines the number of positive graph items in a given subgraph.
	 * 
	 * @param currentSubGraph the subgraph whose positive items will be counted
	 * @return the number of positive items
	 */
	private int posSizeOfSubgraph(Set<AnnotatedElem> currentSubGraph) {
		int count = 0;
		for (AnnotatedElem gI : currentSubGraph) {
			if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(gI.eClass())) {
				if (!InvariantCheckerUtil.isNegated((Edge) gI)) {
					count++;
				}
			} else {
				if (!InvariantCheckerUtil.isNegated((Node) gI)) {
					count++;
				}
			}
		}
		return count;
	}


	/**
	 * This method creates an arbitrary pair of two nodes and pushes an
	 * corresponding {@link MatchingState} instance onto the {@link #stack}.<br />
	 * If the {@link #startNode} field is empty the {@link #currentSubGraph}'s
	 * first node is selected. The {@link #startNode} is then tried to match to the
	 * {@link #hostGraph}'s next {@link Node}.
	 * 
	 * @return <code>false</code> if the {@link #hostGraph} has unvisited nodes.
	 *         <code>true></code> otherwise.
	 */
	private boolean createInitialNodePair() {
		//System.out.println("createInitialNodePair");
		boolean triedAllHostNodes;		
		if (this.currentMatching.getSize() > this.connectedComponents.get(connectedComponentIndex).historyIndex && this.currentMatching.getSize() < this.currentSubGraph.size()) {
			this.connectedComponentIndex++;
			if (this.connectedComponents.size() > this.connectedComponentIndex) {
				this.connectedComponents.get(this.connectedComponentIndex).hostNodeIter = null;
			} else {
			final StartConfiguration tmpConf = new StartConfiguration();
			tmpConf.historyIndex = this.currentMatching.getSize();
			tmpConf.startNode = null;
			tmpConf.hostNodeIter = null;
			this.connectedComponents.add(tmpConf);
			}
		}
		
		if (this.connectedComponents.get(connectedComponentIndex).startNode == null) {
			if (mode == MatchMode.NAC_TRANSLATION_CALL && connectedComponentIndex == 0) {
				return true;
			}
			findStartNode();
		}
		if (this.connectedComponents.get(connectedComponentIndex).hostNodeIter == null) {
			this.connectedComponents.get(connectedComponentIndex).hostNodeIter = this.hostGraph.getNodes().iterator();
		}
		Node next = null;
		triedAllHostNodes = false;
		// iterate through nodes of hostGraph
		// iterator is resetted for each subgraph (input)
		while (this.connectedComponents.get(connectedComponentIndex).hostNodeIter.hasNext() && next == null) {
			next = this.connectedComponents.get(connectedComponentIndex).hostNodeIter.next();
			if (currentMatching.getNodeMatching().containsValue(next) == false) {
				final MatchingState newms = theFactory.getCheckNodeState();
				newms.currentNode = this.connectedComponents
						.get(connectedComponentIndex).startNode;
				newms.mappedToNode = next;
				// types, negated are not checked, this is done in the checkNode method
				stack.push(newms);
				triedAllHostNodes = false;
			}
		} 
		if (next == null) {
			triedAllHostNodes = true;
		}
		return triedAllHostNodes;
	}


	/**
	 * Identifies the first not negative {@link Node} in the
	 * {@link #currentSubGraph}.<br />
	 * This node is not directly returned but the {@link #startNode} field is
	 * accordingly set.
	 */
	private void findStartNode() {
		if (startNodeIter == null) {
			startNodeIter = this.currentSubGraph.iterator();
		}
		while( this.connectedComponents.get(connectedComponentIndex).startNode == null && startNodeIter.hasNext()) {
			AnnotatedElem next = startNodeIter.next();
			if (next instanceof Node && !this.currentMatching.getNodeMatching().containsKey((Node)next)) {
				Node n = (Node) next;
				if (!InvariantCheckerUtil.isNegated(n)) {
					this.connectedComponents.get(connectedComponentIndex).startNode = n;
				}
			}
		}
	}


	/**
	 * This method is invoked if the {@link #getNextMatching()} method is not
	 * invoked the first time.<br />
	 * The method prepares the {@link #stack} to start the backtracking
	 * functionality to find a new matching.
	 * 
	 * Matching has been found and returned, then call of getNextMatching on same subgraph calls this
	 */
	private void handleReentrantCall() {		
		boolean stop = false;
		this.currentMatching.getNacMatching().clear();
		this.nacIterator = null;
		while (this.stack.size() > 0 && !stop) {
			// remove all check nac states.
			if (this.stack.peek().matchOp == MatchingOperation.CHECK_NAC) {
				this.stack.pop();
			} else {
				stop = true;
			}
		}
		if (this.stack.size() > 0 && this.stack.peek().matchOp == MatchingOperation.CHECK_EDGE && this.stack.peek().currentEdge == null) {
			/*
			 * If the last entry of the stack is a CHECK_EDGE entry, but it's
			 * currentEdge field is set to null, this entry has not lead to a
			 * matching and thus has to be removed from the stack.
			 */
			this.stack.pop();
		}
		if (this.stack.size() > 0) {			
			// Initiates the backtracking. 
			this.stack.peek().backtrack = BackTrackReason.FAILURE;
		}	
	}
	
	public void handleNACMatchCall(Match interfaceMatching, List<Node> interfaceBlacklist) {
		this.nodeBlacklist = interfaceBlacklist;
		mode = MatchMode.NAC_MATCH_CALL;
		this.nacInterfaceMatch = interfaceMatching;
	}

	/**
	 * This method prepares the IPM instance for a NAC translation call, starting from (negative) nodes that do not belong to the NAC interface.
	 * 
	 * @param startNode the start node
	 */
	public void handleNacTranslationCall(Node startNode, List<Edge> edgeBlacklist, List<Node> nodeBlacklist, Match nacInterfaceMatch) {
		
		mode = MatchMode.NAC_TRANSLATION_CALL;
		this.blacklist = edgeBlacklist;
		this.nodeBlacklist = nodeBlacklist;
		this.nacInterfaceMatch = nacInterfaceMatch;
		
		if (this.currentMatching == null) {
			this.currentMatching = NacFactory.eINSTANCE.createMatchWithNacs();
		}

		this.connectedComponents.get(0).startNode = startNode;
		
		createInitialNodePair();
		//System.out.println("inner nac translation call: " + startNode);
	}

	/**
	 * This method will handle a CHECK_NODE matching state. It will contain both the current node (from the current subgraph)
	 * and the corresponding counterpart (to be checked) from the host graph. The nature of the state might be: <br>
	 * <br>
	 * <dl>
	 * <dt>Failure</dt> <dd>Remove the current matching state from the stack.
	 * Remove both nodes from the current matching. Set the nature of the previous state to failure.</dd>
	 * <dt>Backtrack</dt> <dd>Remove the current matching state from the stack.
	 * Set the nature of the previous state to backtrack unless the IPM executes a NAC_MATCH_CALL or a CHECK_NAC_CALL. </dd>
	 * <dt>None</dt> <dd>Check {@link #degreePredicate(Node, Node)}, {@link #negatedPredicate(Node, Node)}
	 * and {@link #typePredicate(Node, Node)} for the current nodes. If successful, add the nodes to the current matching and
	 * add a new CHECK_EDGE matching state to the stack.
	 * Otherwise remove the current matching state from the stack and set the previous state to failure.
	 * </dd>
	 * </dl>
	 * <br>
	 * A CHECK_NODE state usually results from a successfully handled CHECK_EDGE state. It is also the first matching state on the stack when
	 * executing a MATCH_CALL.
	 *  
	 * @param ms the current matching state
	 */
	private void checkNode(final MatchingState ms) {
		if (ms.backtrack == BackTrackReason.FAILURE) {						
			// Remove wrong/invalid matchingState from stack.
			stack.pop();
			this.currentMatching.getNodeMatching().remove(ms.currentNode);
			this.matchingHistory.remove(ms);
			theFactory.releaseMatchingState(ms);
			
			if (stack.size() > 0) {
				// If the node matching is invalid, the edge matching leading to it has to be removed, too. 
				final MatchingState tmpMS = stack.peek();
				tmpMS.backtrack = BackTrackReason.FAILURE;
			}
		} else if (ms.backtrack == BackTrackReason.BACKTRACK) {
			// backtracking, remove stack until another chance can be found
			stack.pop();
			if (stack.size() > 0) {
				stack.peek().backtrack = BackTrackReason.BACKTRACK;
			}				
		} else {
			// check predicates, build new checkEdge state if applicable
			final Node expectedNode = ms.currentNode;
			final Node foundNode = ms.mappedToNode;
			if (nacInterfaceNodePredicate(expectedNode, foundNode) && degreePredicate(foundNode, expectedNode) && typePredicate(expectedNode, foundNode) && negatedPredicate(expectedNode, foundNode) && ((!currentMatching.getNodeMatching().containsKey(expectedNode) && !currentMatching.getNodeMatching().containsValue(foundNode)) || (foundNode == currentMatching.getNodeMatching().get(expectedNode)))) {
				this.currentMatching.getNodeMatching().put(expectedNode, foundNode);
				this.matchingHistory.add(ms);
				if (this.mode == MatchMode.NAC_TRANSLATION_CALL) {
					iterativeMatchings.add(currentMatching.copy());
				}
				MatchingState newms = theFactory.getCheckEdgeState();
				newms.currentNode = expectedNode;
				newms.mappedToNode = foundNode;
				newms.currentNAC = ms.currentNAC;
				newms.mappedToNAC = ms.mappedToNAC;
				stack.push(newms);
				// next call should be checkEdge
			} else {
				// cannot match nodes, remove from stack
				// next call should be checkEdge
				stack.pop();
				if (stack.size() > 0) {
					stack.peek().backtrack = BackTrackReason.FAILURE;
				}
			}
		}
	}

	/**
	 * Returns an unmatched edge in the current subgraph that leads to or comes from the current node in the matching state.
	 * This method will try to return positive edges first.
	 * 
	 * An edge will be returned if it fulfills all of the following conditions:
	 * <ul>
	 * <li>It is not contained in the current matching, thus being unmatched</li>
	 * <li>It is contained in the edgeIterator of the current matching state, which iterates over the edges from the currentNode of the current state</li>
	 * <li>It is contained in the current subgraph (and the pattern)</li>
	 * <li>It is contained in the current subgraph (and the pattern)</li>
	 * </ul>
	 * and additionally fulfills at least one of the following conditions:
	 * <ul>
	 * <li>It is positive (i.e. does not belong to a NAC) and there are unchecked edges left</li>
	 * <li>It is negative (i.e. belongs to a NAC), all positive edges have been checked and the <a href="../../../../../doc/glossary.html">positive NAC interface</a> has been matched</li>
	 * <li>The IPM executes a NAC_TRANSLATION_CALL</li>
	 * </ul>
	 *
	 * When executed the first time, this method will try to return a positive edge
	 * as the matching of NACs currently assumes that the positive NAC interfaces have already been matched.
	 * When all edges have been checked, the iterator will be reset and the ignoreNegativeEdges flag will be set to true.
	 * When iterating over the edges for the second time, negative edges will also be checked. 
	 * 
	 * @param ms the current matching state
	 * @return the unmatched edge
	 */
	private Edge getNextUnmatchedEdge(MatchingState ms) {
		if (ms != null) {
			if (ms.edgeIterator == null) {
				ms.edgeIterator = iteratorOfEdges(ms.currentNode);
			}
			while (ms.edgeIterator.hasNext()) {
				final Edge nextEdge = ms.edgeIterator.next();
				/* "unmatched", thus not contained in currentMatching but in the subgraph
				 * Only positive edges should be considered. 
				 */
				if (this.currentMatching != null && ! this.currentMatching.getEdgeMatching().containsKey(nextEdge) && this.currentSubGraph.contains(nextEdge) && !InvariantCheckerUtil.isNegated(nextEdge)) {
					return nextEdge;
				}
			}
		}
		return null;
	}

	/**
	 * This method will handle a CHECK_EDGE matching state. It might contain the current edge from the current subgraph
	 * and might also contain the corresponding counterpart (which might be checked) from the host graph. The nature of the state might be: <br>
	 * <br>
	 * <dl>
	 * <dt>Failure</dt> <dd>If the state is the last state in the matching history, remove it from the stack and
	 * remove both edges from the current matching. Set the state to NONE so that the next call will try to find a new matching edge.
	 * If not, rebuild the stack. Set the nature of the topmost state to FAILURE so that the next call will evaluate the states
	 * leading to the current failure.</dd>
	 * <dt>Backtrack</dt> <dd>If another unmatched edge going from or to the current node can be found, put it on the stack as a new CHECK_EDGE state.
	 * If not, remove the state from the stack and set the previous state to BACKTRACK in order to continue the backtracking process.</dd>
	 * <dt>None</dt> <dd> If the current edge is not set, find one by calling {@link #getNextUnmatchedEdge(MatchingState)}.
	 * Call {@link #findMatchingEdge(MatchingState)} to find a corresponding counterpart to the current edge in the host graph.
	 * This method will handle the predicates to be checked.
	 * </dd>
	 * </dl>
	 * <br>
	 * When executing a normal (NONE) CHECK_EDGE call, the following scenarios might occur: <br>
	 * <ul>
	 * <li>A matching edge will be found by {@link #findMatchingEdge(MatchingState)}. The implications of this will be explained in said method.</li>
	 * <li>There is no unmatched edge left to be returned by {@link #getNextUnmatchedEdge(MatchingState)}. The matching state will be removed
	 * from the stack and the previous state will be set to BACKTRACK.</li>
	 * <li>There was no matching edge to be found and the IPM is in NAC_MATCH mode. If the host graph NAC is already completely matched,
	 * it is stricter than the NAC in the current subgraph. In that case, the {@link #stricterNac} flag will be set and the matching process will terminate
	 * in the next iteration of the main loop.</li>
	 * <li>There was no matching edge to be found and the IPM is in NAC_TRANSLATION mode. As we look for incomplete matching, we cannot
	 * simply return with FAILURE but need to check other edges by using {@link #getNextUnmatchedEdge(MatchingState)}. If there is an
	 * unmatched edge, a new matching state will be put on the stack. If not, remove the state from the stack and set the previous state
	 * to FAILURE.</li>
	 * <li>There was no matching edge and the IPM is in standard MATCH mode. Remove the state from the stack and set the prevoius
	 * state to FAILURE.</li>
	 * </ul> 
	 * 
	 * <br>
	 * A CHECK_EDGE state is the first matching state on the stack when executing a MATCH_NAC_CALL, a CHECK_NAC_CALL or a NAC_TRANSLATION_CALL
	 * starting from the <a href="../../../../../doc/glossary.html">positive NAC interface</a> as these calls have predefined starting nodes
	 * from the current subgraph with corresponding counterparts from the host graph.
	 * 
	 * @param ms the current matching state
	 */
	private void checkEdge(final MatchingState ms) {
		if (ms.backtrack == BackTrackReason.FAILURE) {			
			if (this.matchingHistory != null && !this.matchingHistory.isEmpty() && this.matchingHistory.getLast() != null && ms != this.matchingHistory.getLast()) {
				// where is the current ms in the history?
				final int index = this.matchingHistory.indexOf(ms);
				if (index > -1) {
					/* current matching state is not the latest state in the history, return all matchingStates coming after
					 * the current state from the history to the stack
					 * Stack has to be rebuilt, because the wrong matching is probably not on the stack, but in the history.
					 * So we have to check previous matchings because otherwise we might pop everything from the stack
					 * without finding the error.
					 */
					for (Iterator<MatchingState> msIter = this.matchingHistory.listIterator(index + 1); msIter.hasNext(); this.stack.push(msIter.next()))
						;
					if (stack.size() > 0) {
						stack.peek().backtrack = BackTrackReason.FAILURE;
					}
				}
			} else {
				// matchingState was the latest state in the history or there was no history
				// find another edge that can be mapped to the current edge
				// next call should be checkEdge with the same matching state to find another				
				this.currentMatching.getEdgeMatching().remove(ms.currentEdge);
				this.matchingHistory.remove(ms);
			}
			ms.backtrack = BackTrackReason.NONE;
		} else if (ms.backtrack == BackTrackReason.BACKTRACK) {			
			//we are in backtracking mode. This means we have to find a new currentEdge for the current MatchingState to be matched
			// reset the iterator looking for unmatched edges
			ms.edgeIterator = null;			
			final Edge nue = this.getNextUnmatchedEdge(ms);
			if (nue == null) {
				// no unmatched edge from the node in question, go back
				stack.pop();
				if (stack.size() > 0) {
						stack.peek().backtrack = BackTrackReason.BACKTRACK;
				}
			} else {				
				// there is another edge, next call is checkEdge
				final MatchingState newMS = theFactory.getCheckEdgeState();
				newMS.currentEdge = nue;
				newMS.currentNode = ms.currentNode;
				newMS.mappedToNode = ms.mappedToNode;
				newMS.currentNAC = ms.currentNAC;
				newMS.mappedToNAC = ms.mappedToNAC;
				
				// NAC translation call
				if (mode == MatchMode.NAC_TRANSLATION_CALL) {
					newMS.edgeIterator = ms.edgeIterator;
				}				

				stack.push(newMS);
			}
		} else {
			// default case, try to find a matching edge
			if (ms.currentEdge == null) {
				ms.currentEdge = this.getNextUnmatchedEdge(ms);

				if (ms.currentEdge == null) {	
					 // We did not find any unmatched edge, following we track back to find further unmatched subgraph elements.
					stack.pop();
					if (stack.size() > 0) {
						// this is the only point where backtracking (not due to failure) is activated
						// assuming we tried to match an edge that did not exist, the next call is checkNode
						stack.peek().backtrack = BackTrackReason.BACKTRACK;						
					}
					return;
				}
			}
			
			if (ms.mappedEdgeIterator == null) {
				ms.mappedEdgeIterator = iteratorOfEdges(ms.mappedToNode);
			}

			boolean foundMatch = findMatchingEdge(ms);

			if (foundMatch == false) {
				// we did not find a match for at least one Edge				
				if (mode != MatchMode.NAC_TRANSLATION_CALL) {					
					stack.pop();
					if (stack.size() > 0) {
						stack.peek().backtrack = BackTrackReason.FAILURE;
					}					
				} else {
					/*
					 * When in NAC translation mode, we only search for incomplete matchings.
					 * So we can only "fail" when there are no unmatched edges left on a certain node in the current subgraph.
					 */
					Edge nue = getNextUnmatchedEdge(ms);
					if (nue == null) {
						stack.pop();
						if (stack.size() > 0) {
							stack.peek().backtrack = BackTrackReason.FAILURE;
						}
					} else {
						final MatchingState newMS = new MatchingState(MatchingOperation.CHECK_EDGE);
						newMS.currentEdge = nue;
						newMS.currentNode = ms.currentNode;
						newMS.mappedToNode = ms.mappedToNode;
						newMS.currentNAC = ms.currentNAC;
						newMS.mappedToNAC = ms.mappedToNAC;
						// nacTC
						if (mode == MatchMode.NAC_TRANSLATION_CALL) {
							newMS.edgeIterator = ms.edgeIterator;
						}
						stack.push(newMS);
					}
				}
			}
		}
	}
	
	/**
	 * This method finds an edge in the host graph for the current edge of the subgraph contained in the given matching state.
	 * 
	 * For an edge to be matched to the current edge, it must not be contained as a value in the current matching. Additionally, the predicates
	 * {@link #typePredicate(Edge, Edge), {@link #negatedPredicate(Edge, Edge)}} and {@link #directionPredicate(Edge, Edge, Node, Node)} need to
	 * be checked.<br>
	 * <br>
	 * If these conditions hold, the edges will be matched and added to the current matching. Depending on the nature of the edges, one of the
	 * following will happen:
	 * 
	 * <ul>
	 * <li>The edges are negated (i.e. belonging to NACs). A CHECK_NAC state will be put on the stack.
	 * This will result in the execution of the {@link #checkNAC()} method, which might result in the execution
	 * of a NAC_MATCH_CALL.</li>
	 * <li>The edges are positive and the node at the other end of the current edge is unmatched.
	 * A CHECK_NODE state with both nodes is put on the stack.</li>
	 * <li>The edges are positive and the node at the other end of the current edge has already been matched
	 * to the node at the other end of the mapped edge.
	 * This means that we do not need to match the nodes by creating a CHECK_NODE state. Instead, just search
	 * for unmatched edges going to or from the current node by putting a new CHECK_EDGE state on the stack.</li>
	 * </ul>
	 * <br>
	 * If no matching edge can be found, false will be returned. The calling method ({@link #checkNode(MatchingState)})
	 * will handle this result.
	 * 
	 * @param ms the current matching state
	 * @return true if there is a matching edge, false otherwise 
	 */
	private boolean findMatchingEdge(final MatchingState ms) {		
		final Iterator<Edge> theIterator = ms.mappedEdgeIterator;	
		while (theIterator.hasNext()) {
			final Edge foundEdge = theIterator.next();
			// the blacklist condition is only used in NAC translation calls
			if (!currentMatching.getEdgeMatching().containsValue(foundEdge) && !blacklist.contains(foundEdge)) {
				// predicates
				if (typePredicate(ms.currentEdge, foundEdge) && negatedPredicate(ms.currentEdge, foundEdge) && directionPredicate(ms.currentEdge, foundEdge, ms.currentNode, ms.mappedToNode)) {
					// get the nodes at the other side of the edge 
					final Node newCurrentNode = ms.currentEdge.getSource() == ms.currentNode ? ms.currentEdge.getTarget() : ms.currentEdge.getSource();
					final Node newFoundNode = foundEdge.getSource() == ms.mappedToNode ? foundEdge.getTarget() : foundEdge.getSource();
					
					if (!this.currentMatching.getNodeMatching().containsKey(newCurrentNode)) {							
						// the nodes on the other side of the edge are unmatched
						this.currentMatching.getEdgeMatching().put(ms.currentEdge, foundEdge);
						this.matchingHistory.add(ms);
												
						final MatchingState newMS = theFactory.getCheckNodeState();
						newMS.currentNode = newCurrentNode;
						newMS.mappedToNode = newFoundNode;
						newMS.currentNAC = ms.currentNAC;
						newMS.mappedToNAC = ms.mappedToNAC;
						newMS.backtrack = BackTrackReason.NONE;

						// next call is check node
						stack.push(newMS);						
						return true;						
					} else if (this.currentMatching.getNodeMatching().get(newCurrentNode) == newFoundNode) {
						/*
						 * The nodes on the other side of the edge are already matched, so we found some kind of cycle.
						 * Therefore we will try to find another unmatched edge for the current matching state with its current node. 
						 */
						this.currentMatching.getEdgeMatching().put(ms.currentEdge, foundEdge);						
						this.matchingHistory.add(ms);
						if (this.mode == MatchMode.NAC_TRANSLATION_CALL) {
							this.iterativeMatchings.add(this.currentMatching.copy());
						}
						
						final MatchingState newMS = theFactory.getCheckEdgeState();
						newMS.currentNode = ms.currentNode;
						newMS.mappedToNode = ms.mappedToNode;
						newMS.currentNAC = ms.currentNAC;
						newMS.mappedToNAC = ms.mappedToNAC;
						
						// next call is check edge
						stack.push(newMS);
						return true;
					}
				} //if (type ...
			} //if (!currentMatching
		} //while...
		return false;
	}
	
	private void checkNAC() {
		MatchingState ms = stack.peek();
		if (ms.backtrack == BackTrackReason.FAILURE) {
			// remove nac matching
			currentMatching.getNacMatching().remove(ms.currentNAC);
			nacIterator = null;
			positiveFailure = true;
			stack.pop();
			if (stack.size() > 0) {
				stack.peek().backtrack = BackTrackReason.FAILURE;
			}
		} else if (ms.backtrack == BackTrackReason.BACKTRACK) {
			stack.pop();
			if (stack.size() > 0) {
				stack.peek().backtrack = BackTrackReason.BACKTRACK;
			}
		} else {
			if (ms.translatedGraph == null) {				
				NACTranslator nacT =  new NACTranslator();
				Match translation = InvariantCheckerUtil.copyAsRuleGraph(hostGraph);
				Graph translated = null;
				if (translation.getNodeMatching().size() > 0) {
					translated = (Graph) translation.getNodeMatching().get(0).getValue().eContainer();
				}
				if (translated == null && translation.getEdgeMatching().size() > 0) {
					translated = (Graph) translation.getEdgeMatching().get(0).getValue().eContainer();
				}				
				nacT.setMergedGraph((RuleGraph) translated);
				nacT.setPattern(pattern);
				Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
				for (Map.Entry<Node, Node> entry : currentMatching.getNodeMatching()) {
					initialMatching.getNodeMatching().put(entry.getKey(), translation.getNodeMatching().get(entry.getValue()));
				}
				for (Map.Entry<Edge, Edge> entry : currentMatching.getEdgeMatching()) {
					initialMatching.getEdgeMatching().put(entry.getKey(), translation.getEdgeMatching().get(entry.getValue()));
				}			
				nacT.setInitialMatching(initialMatching);			
				ms.translatedGraph = nacT.translateSingleNac(ms.currentNAC);
				if (ms.translatedGraph == null) {
					// positive occurrence of nac from current subgraph/pattern in host graph
					// => abort, look for new positive matching 
					ms.backtrack = BackTrackReason.FAILURE;
					return;
				}
				ms.translation = translation;
			}
			GraphWithNacs tmp = SamGraphInvCheckGraphAdapter.getInstance(ms.translatedGraph);
			if (ms.currentNACIter == null) {
				ms.currentNACIter = tmp.getNacs().iterator(); //SamGraphInvCheckGraphAdapter.getInstance(ms.translatedGraph).getNacs().iterator();
			}
			if (ms.stack == null) {
				ms.stack = new Stack<NacMatchingState>();
			}
			if (ms.nacMatching == null) {
				ms.nacMatching = NacFactory.eINSTANCE.createMatchWithNacs();
			}
			int i = 0;
			while(ms.nacMatching.getNacMatching().size() < tmp.getNacs().size()) {
				if (ms.stack.size() > 0) {
					//System.out.println(ms.stack.peek());
					boolean triedAll = checkSingleNAC(ms);
					if (triedAll) {
						// analyse context
						if (this.restrictingConstraints != null && this.context) {
							boolean found = false;
							IsomorphicPartMatcher subIpm = new IsomorphicPartMatcher();
							Match m = SubgraphIterator.fullNacToGraph(ms.stack.peek().currentNAC, false);
							Graph newHost = null;
							for (Node n : m.getNodeMatching().values()) {
								newHost = (Graph) n.eContainer();
								break;
							}
							i++;
							subIpm.setHostGraph(newHost);
							System.out.println("context " + i);
							for (Graph constraint : this.restrictingConstraints) {
								System.out.print(((NegatedCondition) constraint.eContainer().eContainer()).getName() + ", ");
								subIpm.setPattern(constraint);
								subIpm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(constraint));
								subIpm.reset();
								if (subIpm.getNextMatching() != null) {
									System.out.println();
									System.out.println("found " + ((NegatedCondition) constraint.eContainer().eContainer()).getName());
									found = true;
									ms.nacMatching.getNacMatching().put(ms.stack.peek().currentNAC, null);
									break;
								}
							}
							System.out.println();
							if (!found) {
								ms.backtrack = BackTrackReason.FAILURE;
								return;
							} else {
								if (ms.currentNACIter.hasNext()) {
									NegativeApplicationCondition next = ms.currentNACIter.next();
									NacMatchingState newNMS = new NacMatchingState();
									newNMS.currentNAC = next;
									ms.stack.push(newNMS);
								}
							}
						} else {
						
							ms.backtrack = BackTrackReason.FAILURE;
							return;
						}
					}
				} else {
					if (ms.currentNACIter.hasNext()) {
						NegativeApplicationCondition next = ms.currentNACIter.next();
						NacMatchingState newNMS = new NacMatchingState();
						newNMS.currentNAC = next;
						ms.stack.push(newNMS);
					}
				}
			}
			// add main nac matching			
			currentMatching.getNacMatching().put(ms.currentNAC, ms.mappedToNAC);
			
			// successful matching: put next nac matching on the main stack			
			if (nacIterator.hasNext()) {
				NegativeApplicationCondition next = nacIterator.next();
				MatchingState newMS = new MatchingState(MatchingOperation.CHECK_NAC);
				newMS.currentNAC = next;
				stack.push(newMS);
				
				// cleanup, trying to save some memory
				ms.nacMatching = null;
				ms.stack.clear();
				ms.translatedGraph = null;
				ms.translation.clear();
				ms.translation = null;
			}
		}
	}

	private boolean checkSingleNAC(MatchingState ms) {
		NacMatchingState nacms = ms.stack.peek();
		if (nacms.backtrack == BackTrackReason.FAILURE) {
			ms.stack.pop();
			if (ms.stack.size() > 0) {
				ms.stack.peek().backtrack = BackTrackReason.FAILURE;
			}
			return false;
		} else if (nacms.backtrack == BackTrackReason.BACKTRACK) {
			ms.stack.pop();
			if (ms.stack.size() > 0) {
				ms.stack.peek().backtrack = BackTrackReason.BACKTRACK;
			}
			return false;
		} else {
			if (nacms.mappedNACIter == null) {
				nacms.mappedNACIter = SamGraphInvCheckGraphAdapter.getInstance(hostGraph).getNacs().iterator();
			}
			if (nacms.mappedNACIter.hasNext()) {
				NegativeApplicationCondition mappedToNAC = nacms.mappedNACIter.next();
				// match successful: next nacmatchingstate, add to currentMatching
				// match unsuccessful: leave on stack, try next turn other nac
				if (nacSizePredicate(nacms.currentNAC, mappedToNAC) && nacInterfacePredicate(nacms.currentNAC, mappedToNAC)) {
					IsomorphicPartMatcher subIPM = new IsomorphicPartMatcher();
					// translate the current NAC (subgraph) to a positive representation and save the mapping
					Match newHostNacMatch = SubgraphIterator.nacToGraph(nacms.currentNAC);
					Graph newHost = null;
					for (Edge e : newHostNacMatch.getEdgeMatching().keySet()) {
						if (newHostNacMatch.getEdgeMatching().get(e) != null) {
							// this is the new (positive) graph representing the NAC and its positive interface nodes
							newHost = (Graph) newHostNacMatch.getEdgeMatching().get(e).eContainer();
						}
					}
					if (newHost == null) {
						for (Node n : newHostNacMatch.getNodeMatching().keySet()) {
							if (newHostNacMatch.getNodeMatching().get(n) != null) {
								// this is the new (positive) graph representing the NAC and its positive interface nodes
								newHost = (Graph) newHostNacMatch.getNodeMatching().get(n).eContainer();
							}
						}
					}
										
					// translate the "mapped" NAC (host graph) to a positive representation and save the mapping
					Match newPatternNacMatch = SubgraphIterator.nacToGraph(mappedToNAC);
					Graph newPattern = null;
					for (Edge e : newPatternNacMatch.getEdgeMatching().keySet()) {
						if (newPatternNacMatch.getEdgeMatching().get(e) != null) {
							// this is the (positive) graph representing the NAC and its positive interface nodes
							newPattern = (Graph) newPatternNacMatch.getEdgeMatching().get(e).eContainer();
						}
					}
					if (newPattern == null) {
						for (Node n : newPatternNacMatch.getNodeMatching().keySet()) {
							if (newPatternNacMatch.getNodeMatching().get(n) != null) {
								// this is the (positive) graph representing the NAC and its positive interface nodes
								newPattern = (Graph) newPatternNacMatch.getNodeMatching().get(n).eContainer();
							}
						}
					}
					Set<AnnotatedElem> subgraph = SubgraphIterator.graphToSubGraph(newPattern);
					// the current NAC will be the current subgraph of the new IPM instance
					subIPM.setCurrentSubGraph(subgraph);
					subIPM.setPattern(newPattern);
					// the "mapped" NAC will be the host graph of the new IPM instance
					subIPM.setHostGraph(newHost);
					
					List<Node> interfaceBlacklist = new LinkedList<Node>();
					// build matching of the interface nodes
					Match interfaceMatching = SamtraceFactory.eINSTANCE.createMatch();
					/*
					for (Map.Entry<Node, Node> entry : currentMatching.getNodeMatching()) {
						Node nodeInTranslatedHost = ms.translation.getNodeMatching().get(currentMatching.getNodeMatching().get(entry.getKey()));
						Node nodeInPatternNac = newPatternNacMatch.getNodeMatching().get(currentMatching.getNodeMatching().get(entry.getKey()));
						Node nodeInHostNac = newHostNacMatch.getNodeMatching().get(nodeInTranslatedHost);
						if (nodeInPatternNac != null && nodeInHostNac != null) {
							interfaceMatching.getNodeMatching().put(nodeInPatternNac, nodeInHostNac);
						} else if (nodeInPatternNac != null && nodeInHostNac == null) {
							// interfaces do not comply with positive matching, try another nac
							return false;
						} else if (nodeInPatternNac == null && nodeInHostNac != null) {
							interfaceBlacklist.add(nodeInHostNac);
						}
					}*/
					
					for (Node n : hostGraph.getNodes()) {
						Node nodeInTranslatedHost = ms.translation.getNodeMatching().get(n);
						Node nodeInPatternNac = newPatternNacMatch.getNodeMatching().get(n);
						Node nodeInHostNac = newHostNacMatch.getNodeMatching().get(nodeInTranslatedHost);
						if (nodeInPatternNac != null && nodeInHostNac != null) {
							interfaceMatching.getNodeMatching().put(nodeInPatternNac, nodeInHostNac);
						} else if (nodeInPatternNac != null && nodeInHostNac == null) {
							// interfaces do not comply with positive matching, try another nac
							return false;
						} else if (nodeInPatternNac == null && nodeInHostNac != null) {
							interfaceBlacklist.add(nodeInHostNac);
						}
					}
					
					// initialize initial matching (positive interface nodes of the NACs)
					//subIPM.handleNACMatchCall(ms.translation, newPatternNacMatch, newHostNacMatch);
					subIPM.handleNACMatchCall(interfaceMatching, interfaceBlacklist);
					Match nacMatching = subIPM.getNextMatching();
					if (nacMatching != null) {
						// found matching. If we just matched our "main" nac, save the result.
						if (nacms.currentNAC.getNodes().size() == ms.currentNAC.getNodes().size()) {
							if (nacms.currentNAC.getEdges().size() == ms.currentNAC.getEdges().size()) {
								ms.mappedToNAC = mappedToNAC;
							}
						}
						
						// found matching, build next state on the stack
						if (ms.currentNACIter.hasNext()) {
							NegativeApplicationCondition next = ms.currentNACIter.next();
							NacMatchingState newNMS = new NacMatchingState();
							newNMS.currentNAC = next;
							ms.stack.push(newNMS);
						}
						//System.out.println("matched single nac");
						ms.nacMatching.getNacMatching().put(nacms.currentNAC, mappedToNAC);
						// todo: add matchings for nodes and edges.
						// Strictly speaking, this is unnecessary. For the moment, at least.
						return false;
					} else {
						// no match: leave state on stack, try another nac next turn
						return false;
					}
				} else {
					// predicates do not match; leave state on stack, try another nac next turn
					return false;
				}				
			} else {
				return true;
			}
		}
	}
	
	/**
	 * This comment is old, as the old checkNAC method does not exist any more. 
	 * 
	 * This method handles a CHECK_NAC matching state.
	 * Such a state is initialized when {@link #findMatchingEdge(MatchingState)} matches
	 * two negative <a href="../../../../../doc/glossary.html">NAC interface edges</a> to each other.
	 * The NACs containing the edges will then be matched by a separate IPM instance executing a NAC_MATCH_CALL. It is important to note
	 * that such a state will only be created from within the {@link #findMatchingEdge(MatchingState)} method after handling
	 * a CHECK_EDGE state. Furthermore, CHECK_NAC states will only be created in MATCH_CALLs, but not in NAC_TRANSLATION_CALLs,
	 * CHECK_NAC_CALLs or NAC_MATCH_CALLs as these calls will work with positive elements only (some of which will represent negative items).
	 * <br>
	 * <br>
	 * The nature of the state might be:
	 * <dl>
	 * <dt>Failure</dt> <dd>Remove the state from the stack. Remove both NACs from the matching. Remove all items in the NACs from the matching.
	 * Set the previous state (will be a CHECK_EDGE state with two NAC interface edges) to FAILURE.</dd>
	 * <dt>Backtrack</dt> <dd>Remove the state from the stack. Set the previous state to BACKTRACK, continuing the
	 * backtracking process.</dd>
	 * <dt>None</dt> <dd>Try to match the NACs by executing a separate IPM instance with a NAC_MATCH_CALL. To prepare this call,
	 * the following steps will be executed:
	 * </dd>
	 * </dl>
	 * <ol>
	 * <li>Check the {@link #nacSizePredicate(NegativeApplicationCondition, NegativeApplicationCondition)},
	 * the {@link #nacInterfacePredicate(NegativeApplicationCondition, NegativeApplicationCondition)}
	 * and the {@link #mappedNacInterface(NegativeApplicationCondition)} predicate. If any of these conditions do not hold,
	 * remove the state from the stack and set the previous state to FAILURE.</li>
	 * <li>Create a new IPM instance.
	 * Create a <a href="../../../../../doc/glossary.html">positive representation</a> for the current NAC (from the current subgraph)
	 * and set it as the new IPM's current subgraph and pattern.</li>
	 * <li>Create a positive representation for the mapped NAC (from the host graph) and set it as the new IPM's host graph.</li>
	 * <li>Call {@link #handleNACMatchCall(NegativeApplicationCondition, Match, Match, Match)},
	 * passing the current NAC, the match mapping it to its positive representation, the match mapping the mapped NAC to its
	 * positive representation and the current matching. This method will translate the current matching to the new subgraph and host graph
	 * so that the NAC_MATCH_CALL will have the positive NAC interface as starting points which will already be matched.</li>
	 * <li>Execute the new IPM instance by calling {@link #getNextMatching()}.</li>
	 * <li> If there is a result, call {@link #completeNACMatching(MatchingState, NegativeApplicationCondition, NegativeApplicationCondition)}.
	 * This method will issue a CHECK_NAC_CALL in another IPM instance, which will ensure that the NAC does not illegally exist as
	 * a positive part of the unmatched host graph. It it does exist or if the result of the NAC_MATCH_CALL is null,
	 * the NACs can not be matched.
	 * In that case remove the state from the stack and set the previous state (will be the CHECK_EDGE state containing two 
	 * NAC interface edges) to FAILURE.</li>
	 * <li>Add the two NACs to the current matching.</li>
	 * <li>The matching returned by the NAC_MATCH_CALL matches only the positive representations of the NACs to each other.
	 * Therefore we need to translate the matching to our original NACs in the current subgraph and host graph.</li>
	 * <li>Add the matching of the graph items in the NACs to the current matching. If a CHECK_NAC state with FAILURE is handled,
	 * all the items will be removed from the current matching. So basically, the matching of the graph items is tied to the CHECK_NAC state.
	 * <li>Create a new CHECK_EDGE state and put it on the stack. The nodes will be the same as the nodes of the CHECK_EDGE stack
	 * that originally lead to the matching of two negative edges and to this CHECK_NAC call. These nodes will usually be
	 * part of the <a href="../../../../../doc/glossary.html">positive NAC interface</a>
	 * As the NAC items were matched by the NAC_MATCH_CALL, the matching process will continue with the positive part of the current subgraph.</li> 
	 * </ol>
	 *  
	 */
	
	/**
	 * This predicate checks whether the interfaces of two NACs contain the same types, which is a necessary requirement for the NACs to be matched.
	 * 
	 * @param currentNAC the current NAC
	 * @param mappedToNAC the "mapped" NAC
	 * @return true if the the interfaces contain the same number of items for every type, false otherwise 
	 */
	private boolean nacInterfacePredicate(NegativeApplicationCondition currentNAC, NegativeApplicationCondition mappedToNAC) {
		HashMap<EdgeType, Integer> typeMapC = new HashMap<EdgeType, Integer>();
		HashMap<EdgeType, Integer> typeMapM = new HashMap<EdgeType, Integer>();
		for (Edge e1 : currentNAC.getEdges()) {
			if (e1.partOfNacInterface()) {
				if (typeMapC.containsKey(e1.getInstanceOf())) {
					typeMapC.put(e1.getInstanceOf(), typeMapC.get(e1.getInstanceOf()) + 1);
				} else {
					typeMapC.put(e1.getInstanceOf(), 1);
				}				
			}
		}
		for (Edge e1 : mappedToNAC.getEdges()) {
			if (e1.partOfNacInterface()) {
				if (typeMapM.containsKey(e1.getInstanceOf())) {
					typeMapM.put(e1.getInstanceOf(), typeMapM.get(e1.getInstanceOf()) + 1);
				} else {
					typeMapM.put(e1.getInstanceOf(), 1);
				}
			}
		}		
		for (EdgeType s : typeMapM.keySet()) {
			if (!typeMapC.containsKey(s)) {
				return false;
			} else if (typeMapM.get(s) > typeMapC.get(s)) {
				return false;				
			}
		}
		return true;
	}	

	/**
	 * This predicate compares the sizes of the NAC, ensuring a necessary requirement for the NACs to be matched.
	 * 
	 * @param currentNAC the current NAC
	 * @param mappedToNAC the "mapped" NAC
	 * @return false if the current NAC is smaller than the "mapped" NAC, true otherwise
	 */
	private boolean nacSizePredicate(NegativeApplicationCondition currentNAC, NegativeApplicationCondition mappedToNAC) {
		if (currentNAC.getNodes().size() < mappedToNAC.getNodes().size()) {
			return false;
		}
		return true;
	}

	/**
	 * Compare the types of two edges.
	 * 
	 * @param e1 one edge
	 * @param e2 another edge
	 * @return true if the edges have the same type, false otherwise
	 */
	private boolean typePredicate(final Edge e1, final Edge e2) {
		if (e1 != null && e2 != null) {
			return (e1.getInstanceOf() == null || e2.getInstanceOf() == null || e1.getInstanceOf().equals(e2.getInstanceOf()));
		} else if (e1 == e2) {
			return true;
		}
		return false;
	}

	/**
	 * Compare the types of two nodes.
	 * 
	 * @param n1 one node
	 * @param n2 another node
	 * @return true if the nodes have the same type, false otherwise
	 */	
	private boolean typePredicate(final Node n1, final Node n2) {
		if (n1 != null && n2 != null) {
			return n1.getInstanceOf() == null || n2.getInstanceOf() == null || n1.getInstanceOf().equals(n2.getInstanceOf());
		} else if (n1 == n2) {
			return true;
		}
		return false;
	}

	/**
	 * Compare the direction of two edges.
	 * 
	 * @param e1 one edge
	 * @param e2 another edge
	 * @param n1 a node adjacent to e1 (source or target)
	 * @param n2 a node adjacent to e2 (source or target)
	 * @return true if the edges have the same direction, false otherwise
	 */
	private boolean directionPredicate(final Edge e1, final Edge e2, final Node n1, final Node n2) {
		if (e1 != null && e2 != null) {
			return e1.getInstanceOf().getDirection() == e2.getInstanceOf().getDirection() && (e1.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED || ((e1
					.getSource() == n1 && e2
					.getSource() == n2) || (e1
							.getTarget() == n1 && e2
							.getTarget() == n2)));
		} else if (e1 == e2) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check whether the edges are both negative or both positive.
	 * 
	 * @param e1 one edge
	 * @param e2 another edge
	 * @return true if both edges are negative or positive, false otherwise
	 */
	private boolean negatedPredicate(final Edge e1, final Edge e2) {		
		if (e1 != null && e2 != null) {
			return (InvariantCheckerUtil.isNegated(e1) == InvariantCheckerUtil.isNegated(e2));
		} else if (e1 == e2) {
			return true;
		}
		
		return false;
	}

	/**
	 * Check whether the nodes are both negative or both positive.
	 * 
	 * @param n1 a node
	 * @param n2 another node
	 * @return true if both nodes are negative or positive, false otherwise
	 */
	private boolean negatedPredicate(final Node n1, final Node n2) {
		if (mode == MatchMode.NAC_TRANSLATION_CALL) {
			return true;
		}
		if (n1 != null && n2 != null) {
			return InvariantCheckerUtil.isNegated(n1) == InvariantCheckerUtil.isNegated(n2);
		} else if (n1 == n2) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method builds all incomplete matchings of the given graphs. It is used by the NACTranslator component.
	 * It is executed in the context of a NAC_TRANSLATION_CALL. If the subgraph can be found in the host graph, null is returned.
	 * 
	 * @return a list of matchings or null, if a complete matching can be found
	 */
	public LinkedList<Match> getIterativeMatchings() {
		if (this.getNextMatching() != null) {
			return null;
		} else {
			LinkedList<Match> result = new LinkedList<Match>();
			for (Match gm : iterativeMatchings) {
				result.add(gm.copy());
			}
			return result;
		}
	}
	
	private boolean nacInterfaceNodePredicate(Node expected, Node found) {
		if (mode == MatchMode.NAC_MATCH_CALL) {
			if (this.nacInterfaceMatch.getNodeMatching().containsKey(expected)) {
				return this.nacInterfaceMatch.getNodeMatching().get(expected) == found;
			} else {
				// the first part of the condition is probably obsolete
				return !this.nacInterfaceMatch.getNodeMatching().containsValue(found) && !nodeBlacklist.contains(found);
			}
		} else if (mode == MatchMode.NAC_TRANSLATION_CALL) {
			if (this.nacInterfaceMatch.getNodeMatching().containsKey(expected)) {
				return this.nacInterfaceMatch.getNodeMatching().get(expected) == found;
			} else {
				return !this.nodeBlacklist.contains(found);
			}
		} else {
			return true;
		}
	}

	/**
	 * This predicate returns true if the degree of the first node is greater or equal the second node's degree.<br />
	 * A node's degree is specified as the sum of the numbers of its in- and outgoing edges.
	 * @param n1 the first node
	 * @param n2 the second node
	 * @return true if the first node's degree is greater or equal the second node's degree.
	 */
	private boolean degreePredicate(final Node n1, final Node n2) {		
		if (n1 != null && n2 != null) {
			// addition to negative nodes
			if (mode == MatchMode.NAC_TRANSLATION_CALL || mode == MatchMode.NAC_MATCH_CALL) {
				return true;
			}
			if (InvariantCheckerUtil.isNegated(n1) && InvariantCheckerUtil.isNegated(n2)) {
				return true;
			}
			if (this.nodeDegrees == null || !this.nodeDegrees.containsKey(n2)) {
				return false;
			}
			final int degree = this.nodeDegrees.get(n2);
			return (n1.getIncoming().size() + n1.getOutgoing().size()) >= degree;
		} 
		return false;
	}
	/**
	 * This method returns a <code>Vector</code> containing all matchings between the
	 * currently set {@link IsomorphicPartMatcher#setCurrentSubGraph(Set) subgraph} and the
	 * set property attribute of this object.  Note, that you have to invoke this method for
	 * each subgraph of a rule Graph to compute all partial matchings between rule and property.
	 *
	 * @return   a <code>{@link Vector Vector}</code> containing all matchings between the property
	 *           and the rule restricted to the current subgraph of this object.
	 * @see      IsomorphicPartMatcher#setCurrentSubGraph(Set)
	 * @see      IsomorphicPartMatcher#setHostGraph(Graph)
	 * @see      IsomorphicPartMatcher#setPattern(Graph)
	 * @see      org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator
	 */
	public Vector<Match> findAllMatchings()
	{
		Vector<Match> results = new Vector<Match>();
		if (this.currentSubGraph != null && this.pattern != null && this.hostGraph != null) {
			this.reset();
			Match gm = this.getNextMatching();
			while (gm != null) {
				results.add(gm);
				gm = this.getNextMatching();
			}
		}
		return results;
	}



	/**
	 * Get the currentSubGraph attribute of the IsomorphicPartMatcher object
	 *
	 * @return   The currentSubGraph value
	 */
	public Set<AnnotatedElem> getCurrentSubGraph()
	{
		return currentSubGraph;
	}


	/**
	 * Sets the currentSubGraph attribute of the IsomorphicPartMatcher object.<br />
	 * Changing an <code>IsomorphicPartMatcher</code>'s subgraph attribute, invalidates the <code>IsomorphicPartMatcher</code>'s internal state. You have to invoke {@link #reset()} prior to invoking {@link #getNextMatching()} to receive valid matches.
	 *
	 * @param subGraph  The new currentSubGraph value
	 */
	public void setCurrentSubGraph (Set<AnnotatedElem> subGraph)
	{
		if (this.currentSubGraph != subGraph) {
			this.currentSubGraph = subGraph;
		}

	}
	
	/**
	 * Builds an iterator for all nodes coming from or going to the given node.
	 * 
	 * @param n a node
	 * @return an iterator of edges
	 */
	private Iterator<Edge> iteratorOfEdges(Node n) {		
		EList<Edge> all = new BasicEList<Edge>();
		for(Edge e : n.getIncoming()){
			all.add(e);
		}
		for(Edge e : n.getOutgoing()){
			if ((e.getSource() == e.getTarget()) == false)
				all.add(e);
		}
	 	return all.iterator();
	}
	
	/**
	 * In a first step this method computes a <code>Map</code> that contains the frequency of types in
	 * the <code>Set</code> s.  Afterwards each entry in this <code>Map</code> gets compared to the
	 * corresponding value of the given <code>Graph</code>. Contains the <code>Set</code> more elements
	 * of a type then the <code>Graph</code> the method returns false. In this case there is no need to compute
	 * all matchings, as none exists.
	 *
	 * @param s
	 * @param g
	 * @return
	 */
	private boolean compareTypes(Set<AnnotatedElem> s, Graph g) {
		// compute types in host graph
		
		if (s != null && g != null) {
			Map<NodeType, Integer> m = SubgraphIterator.computeTypesInSubgraph(s);
			for (Iterator<Map.Entry<NodeType, Integer>> iter = m.entrySet()
					.iterator(); iter.hasNext();) {
				Map.Entry<NodeType, Integer> entry = iter.next();
				Integer i = entry.getValue();
				//if (i.intValue() > g.getNumberOfType(entry.getKey())) {
				if (i.intValue() > 0 && !this.typeCount.containsKey(entry.getKey())) {
					return false;
				} else if (i.intValue() > this.typeCount.get(entry.getKey())) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public enum MatchMode {
		MATCH_CALL, NAC_MATCH_CALL, NAC_TRANSLATION_CALL;
	}

	public static class NacMatchingState {
		public NegativeApplicationCondition currentNAC;
		public Iterator<NegativeApplicationCondition> mappedNACIter;
		BackTrackReason backtrack;
		
		public NacMatchingState() {
			
		}
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer(20);
			sb.append("Single NAC matching");
			sb.append(": ");
			sb.append(this.currentNAC.getNodes().size());
			return sb.toString();
		}
		
	}
	
	public static class MatchingState {
		public Node currentNode;
		public Node mappedToNode;
		public Iterator<Edge> edgeIterator;
		public Iterator<Edge> mappedEdgeIterator;
		public Edge currentEdge;
		public NegativeApplicationCondition currentNAC;
		public NegativeApplicationCondition mappedToNAC;
		public RuleGraph translatedGraph; // only for nac matching
		public Iterator<NegativeApplicationCondition> currentNACIter; // only for nac matching
		public MatchWithNacs nacMatching; // only for nac matching
		public Stack<NacMatchingState> stack; // only for nac matching
		public Match translation; // only for nac matching
		BackTrackReason backtrack;
		final MatchingOperation matchOp;

		public MatchingState(MatchingOperation mo) {
			this.matchOp = mo;
		}

		enum MatchingOperation {
			CHECK_EDGE, CHECK_NODE, CHECK_NAC;
		}

		enum BackTrackReason {
			NONE, FAILURE, BACKTRACK;
		}

		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer(20);
			sb.append(this.matchOp);
			sb.append(" ").append(this.currentNode != null ? this.currentNode.getName() : "null");
			sb.append("->").append(this.mappedToNode != null ? this.mappedToNode.getName() : "null");
			if (matchOp == MatchingOperation.CHECK_EDGE) {
				sb.append(this.currentEdge != null ? this.currentEdge : "null");
			}
			if (matchOp == MatchingOperation.CHECK_NAC) {				
				/*for (Edge e : this.currentNAC.getEdges()) {
					sb.append(e);
					sb.append(",");
				}
				sb.append("--");
				for (Edge e : this.mappedToNAC.getEdges()) {
					sb.append(e);
					sb.append(",");
				}*/
			}
			return sb.toString();
		}
	}

	public class TestAdapter {
		
		public class TestAdapterMatchingStateFactory extends MatchingStateFactory {TestAdapterMatchingStateFactory(final int a, final int b) {super(a,b);}};

		public void setStartNode(Node n) {
			connectedComponents.get(0).startNode = n;
		}
		
		public void pushCheckNodeMatchingState(Node current, Node mappedTo) {
			if (stack != null) {
				MatchingState ms = new MatchingState(MatchingOperation.CHECK_NODE);
				ms.currentNode = current;
				ms.mappedToNode = mappedTo;
				stack.push(ms);
			}
		}
		
		public void setHostNodeIter(Iterator<Node> iter) {
			connectedComponents.get(0).hostNodeIter = iter;
		}

		public boolean testTypePredicate(Edge e1, Edge e2) {
			return typePredicate(e1, e2);
		}

		public boolean testDirectionPredicate(Edge e1, Edge e2, Node n1, Node n2) {
			return directionPredicate(e1, e2, n1, n2);
		}

		public boolean testNegatedPredicate(Edge e1, Edge e2) {
			return negatedPredicate(e1, e2);
		}
		
		public TestAdapterMatchingStateFactory getFactory() {
			return new TestAdapterMatchingStateFactory(5, 5);
		}
	}
}

/*
 * $Log$
 * Revision 1.4  2007/06/25 17:48:22  basilb
 * fixed a lot of these bugs testing does not find but evaluating ;)
 *
 * Revision 1.3  2007/01/11 14:44:23  basilb
 * even more usage of generics
 *
 * Revision 1.2  2007/01/03 09:27:46  basilb
 * removed compile errors caused by wrong import declarations; introduced empty plugin class to ensure correct loading
 *
 */
