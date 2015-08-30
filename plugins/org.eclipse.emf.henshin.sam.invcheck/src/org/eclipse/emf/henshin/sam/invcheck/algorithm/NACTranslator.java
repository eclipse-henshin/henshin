package org.eclipse.emf.henshin.sam.invcheck.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.OptimizedSubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationFactory;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphFactory;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;

/**
 * This class translates NACs from a forbidden subgraph to a merged graph which
 * may already contain parts of the NACs. It adds the new NACs to the
 * <a href="../../../../../doc/glossary.html">merged graph</a> ensuring the
 * absence of the NACs from the
 * <a href="../../../../../doc/glossary.html">forbidden subgraph</a> in the
 * result (which is typically a
 * <a href="../../../../../doc/glossary.html">target graph pattern</a>). Thus,
 * any NAC from the forbidden subgraph will not occur as a positive part of the
 * target graph pattern, which would invalidate the counterexample. <br>
 * <br>
 * To translate NACs from a forbidden subgraph to a merged graph, the following
 * steps have to be done: <br>
 * <ol>
 * <li>Create a new instance of the class NACTranslator or call {@link #reset()}
 * if an existing instance will be used.</li>
 * <li>Set the forbidden subgraph using the {@link #setPattern(Graph)} method.
 * </li>
 * <li>Set the merged graph using the {@link #setMergedGraph(RuleGraph)} method.
 * </li>
 * <li>Set the initial matching using the {@link #setInitialMatching(Match)}
 * method. It is a matching between the positive items of the forbidden subgraph
 * and the corresponding graph items in the merged graph. This relationship
 * between graph items is first set by the {@link GraphMerger} using the
 * sameInProp field of items in graph patterns.</li>
 * <li>Call the {@link #translate()} method.</li>
 * </ol>
 * 
 * The result is a RuleGraph, usually representing a target graph pattern. This
 * pattern now contains a number of NACs to ensure the absence of nay NACs from
 * the forbidden subgraph so that the target graph pattern is a valid
 * counterexample. <br>
 * <br>
 * 
 * The following diagram shows the behaviour of the translate method. For
 * further explanation, see {@link #translate()}.
 * 
 * <p>
 * <img src="../../../../../doc/diagrams/nacTranslator.png" alt=
 * "NAC translation">
 * </p>
 * 
 * @author jfd
 */
public class NACTranslator implements AlgorithmComponent {

	/**
	 * A map mapping a list of partial matchings (from NAC to merged graph) to
	 * the start node of the matching process. This map will be reset each time
	 * a new NAC is analyzed.
	 */
	private Map<Node, List<Match>> matchings = new HashMap<Node, List<Match>>();

	/**
	 * A list of matching lists built during the translation process. Each
	 * matching list contains one matching per start node. Start nodes include
	 * all nodes of the <a href="../../../../../doc/glossary.html">positive NAC
	 * interface</a> and all negative nodes within the NAC. This list will be
	 * reset each time a new NAC is analyzed.
	 */
	// private List<List<Match>> listOfLists = new LinkedList<List<Match>>();

	/**
	 * A matching between the positive items of a forbidden subgraph and their
	 * equivalent items in the merged graph. Needs to be set by calling
	 * {@link #setInitialMatching(Match)} before initiating the translation
	 * process. This relationship between the graph items is first set by the
	 * {@link GraphMerger} and can be retrieved by checking the sameInProp field
	 * of the graph items.
	 */
	private Match initialMatching;

	/**
	 * A matching between the forbidden subgraph and the merged graph. Starting
	 * as the initial matching, it is updated during the translation process. It
	 * is used to complete the partial matchings found in the merged graph and
	 * thus build the required NACs.
	 */
	private Match currentMatching = SamtraceFactory.eINSTANCE.createMatch();

	/**
	 * The forbidden subgraph whose NACs will be translated to the merged graph.
	 */
	private Graph pattern;

	/**
	 * The merged graph the translated NACs will be added to, generating a
	 * target graph pattern.
	 */
	private RuleGraph mergedGraph;

	/**
	 * This field saves the value of the previous pattern (probably a forbidden
	 * pattern) after having executed a partial translate. We do this in order
	 * to shorten further translations, if the possible matches of the NAC
	 * elements to be translated do not change from one merged graph to the
	 * next.
	 * 
	 * It might be possible to also integrate this optimization in the execution
	 * of a full translate (to be checked).
	 */
	private Graph previousPattern;

	/**
	 * see previousPattern
	 */
	// private GraphRule previousRule;

	/**
	 * see previousPattern
	 */
	private Graph previousMergedGraph;

	/**
	 * see previousPattern
	 */
	private Match previousInitialMatching;

	private Graph previousNacGraph = null;
	private GraphRule previousRule = null;
	private Set<Node> previousRuleNodes = new HashSet<Node>();
	private Set<Edge> previousRuleEdges = new HashSet<Edge>();
	private Set<Match> previousMatches = new HashSet<Match>();
	private Match previousNacConversion = null;
	private Match previousMirroredExtractedMatch = null;

	/**
	 * Sets the merged graph.
	 * 
	 * @param mergedGraph
	 *            the merged graph
	 */
	public void setMergedGraph(RuleGraph mergedGraph) {
		this.mergedGraph = mergedGraph;
	}

	/**
	 * Sets the initial matching between the positive items of the forbidden
	 * subgraph and the merged graph.
	 * 
	 * @param initialMatching
	 *            the initial matching
	 */
	public void setInitialMatching(Match initialMatching) {
		this.initialMatching = initialMatching;
	}

	/**
	 * Sets the forbidden subgraph.
	 * 
	 * @param pattern
	 *            the forbidden subgraph
	 */
	public void setPattern(Graph pattern) {
		this.pattern = pattern;
	}

	/**
	 * this is purely for experimental context generation purposes
	 */
	private Set<Graph> restrictingConstraints;

	public void setRestrictingConstraints(Set<Graph> restrictingConstraings) {
		this.restrictingConstraints = restrictingConstraings;
	}

	public Set<Graph> getRestrictingConstraints() {
		return this.restrictingConstraints;
	}

	public RuleGraph translateSingleNac(NegativeApplicationCondition nac) {
		return translate(nac, false);
	}

	public RuleGraph translate() {
		return translate(null, true);
	}

	/**
	 * Resets the current instance of the NAC translator by resetting its data
	 * structures.
	 */
	public void reset() {
		this.currentMatching.clear();
		this.matchings.clear();
	}

	public RuleGraph translate(NegativeApplicationCondition singleNac, boolean generateAll) {
		int number = 0;
		if (singleNac == null) {
			generateAll = true;
		}

		int remNo = 0;
		int tw = 0;
		int incon = 0;
		int matches = 0;

		// this determines whether we translate nacs from a rule side or a
		// property
		// the sameInProp or sameInRul values have to be set accordingly
		boolean fromProperty = true;
		if (pattern.eContainer() != null && pattern.eContainer().eClass() == SamrulesPackage.eINSTANCE.getGraphRule()) {
			fromProperty = false;
		}

		// this is checked below. not clear, how the final solution will look
		// like, e.g. what is best performance-wise
		/*
		 * if (checkFullNacExistence()) { return null; }
		 */

		// execute for each NAC
		for (NegativeApplicationCondition nac : ((GraphWithNacs) (SamGraphInvCheckGraphAdapter.getInstance(pattern)))
				.getNacs()) {
			if (!generateAll && !nac.equals(singleNac)) {
				continue;
			}
			currentMatching = initialMatching.copy();

			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			Match nacConversion = SubgraphIterator.nacToGraph(nac);

			// get the new (positive) graph built from the NAC
			// this will be the pattern and the current subgraph in the IPM
			Graph g = null;
			for (Edge e : nacConversion.getEdgeMatching().keySet()) {
				if (nacConversion.getEdgeMatching().get(e) != null) {
					g = (Graph) nacConversion.getEdgeMatching().get(e).eContainer();
					break;
				}
			}
			if (g == null) {
				for (Node n : nacConversion.getNodeMatching().keySet()) {
					if (nacConversion.getNodeMatching().get(n) != null) {
						g = (Graph) nacConversion.getNodeMatching().get(n).eContainer();
						break;
					}
				}
			}

			// optimization

			Map<EdgeType, Integer> edgeTypeMap = new HashMap<EdgeType, Integer>();
			Map<NodeType, Integer> nodeTypeMap = new HashMap<NodeType, Integer>();
			Set<Node> interfaceNodes = new HashSet<Node>();
			boolean incoherentNac = true;
			for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
				Node nodeInPattern = entry.getKey();
				boolean isInterfaceNode = false;
				for (Edge e : nodeInPattern.getIncoming()) {
					if (nac.getEdges().contains(e)) {
						isInterfaceNode = true;
						incoherentNac = false;
						interfaceNodes.add(nacConversion.getNodeMatching().get(entry.getKey()));
						break;
					}
				}
				if (!isInterfaceNode) {
					for (Edge e : nodeInPattern.getOutgoing()) {
						if (nac.getEdges().contains(e)) {
							isInterfaceNode = true;
							incoherentNac = false;
							interfaceNodes.add(nacConversion.getNodeMatching().get(entry.getKey()));
							break;
						}
					}
				}
				if (!isInterfaceNode) {
					if (nodeTypeMap.containsKey(nodeInPattern.getInstanceOf())) {
						nodeTypeMap.put(nodeInPattern.getInstanceOf(),
								nodeTypeMap.get(nodeInPattern.getInstanceOf()) + 1);
					} else {
						nodeTypeMap.put(nodeInPattern.getInstanceOf(), 1);
					}
				}
			}

			for (Map.Entry<Edge, Edge> entry : initialMatching.getEdgeMatching()) {
				Edge edgeInPattern = entry.getKey();
				if (edgeTypeMap.containsKey(edgeInPattern.getInstanceOf())) {
					edgeTypeMap.put(edgeInPattern.getInstanceOf(), edgeTypeMap.get(edgeInPattern.getInstanceOf()) + 1);
				} else {
					edgeTypeMap.put(edgeInPattern.getInstanceOf(), 1);
				}
			}

			// SubgraphIterator sgIter = new SubgraphIterator(g, mergedGraph);
			OptimizedSubgraphIterator sgIter = new OptimizedSubgraphIterator(g, mergedGraph, nodeTypeMap, edgeTypeMap,
					interfaceNodes);
			ipm.setPattern(g);
			ipm.setHostGraph(mergedGraph);
			Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();
			// Match emptyMatch = null;

			// create full nac
			if (incoherentNac) {
				NegativeApplicationCondition fullNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				for (Node n : nac.getNodes()) {
					Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
					if (fromProperty) {
						((PatternNode) newNodeInMerged).setSameInProp(n);
					} else {
						((PatternNode) newNodeInMerged).setSameInRule(n);
					}
					fullNac.getNodes().add(newNodeInMerged);
					currentMatching.getNodeMatching().put(n, newNodeInMerged);
				}
				for (Edge e : nac.getEdges()) {
					Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
					if (fromProperty) {
						((PatternEdge) newEdgeInMerged).setSameInProp(e);
					} else {
						((PatternEdge) newEdgeInMerged).setSameInRule(e);
					}
					fullNac.getEdges().add(newEdgeInMerged);
					currentMatching.getEdgeMatching().put(e, newEdgeInMerged);
				}
				for (Edge e : nac.getEdges()) {
					Edge inMerged = currentMatching.getEdgeMatching().get(e);
					if (inMerged.getSource() == null && inMerged.getTarget() == null) {
						inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
						inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
					}
				}
				newNacs.add(fullNac);
			}

			Map<NegativeApplicationCondition, Map<AnnotatedElem, AnnotatedElem>> srcItemsm = new HashMap<NegativeApplicationCondition, Map<AnnotatedElem, AnnotatedElem>>(); // new
			number = 0;
			int no2 = 1;
			int i = 0;

			while (sgIter.hasNext()) {
				// System.out.println("next sg");
				number++;
				if (singleNac != null && number % 200 == 0) {
					// System.out.println("sbg:" + number);
					// System.out.println("matches for " + number + ": " +
					// matches);
				}
				Set<AnnotatedElem> subgraph = sgIter.next();
				ipm.setCurrentSubGraph(subgraph);
				ipm.reset();
				sgIter.skip();

				List<Edge> blacklist = new LinkedList<Edge>();
				for (Edge e : initialMatching.getEdgeMatching().keySet()) {
					// ignore positive edges from the initial matching because
					// they already have matching edges
					blacklist.add(initialMatching.getEdgeMatching().get(e));
				}

				List<Node> nodeBlacklist = new LinkedList<Node>();
				for (Node nc : initialMatching.getNodeMatching().keySet()) {
					nodeBlacklist.add(initialMatching.getNodeMatching().get(nc));
				}

				Match nacInterfaceMatch = SamtraceFactory.eINSTANCE.createMatch();
				for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
					Node nodeInNac = nacConversion.getNodeMatching().get(entry.getKey());
					if (nodeInNac != null) {
						nacInterfaceMatch.getNodeMatching().put(nodeInNac, entry.getValue());
					}
				}

				ipm.handleNACMatchCall(nacInterfaceMatch, nodeBlacklist);

				Match currentMatch = ipm.getNextMatching();
				// System.out.print(no2 + ", ");
				/*
				 * if (matches >= 12000 && number >= 250) { for
				 * (NegativeApplicationCondition nacToRemove : newNacs) {
				 * //remNo++; for (Edge e : nacToRemove.getEdges()) {
				 * e.setSource(null); e.setTarget(null); }
				 * //newNacs.remove(nacToRemove); srcItemsm.remove(nacToRemove);
				 * } newNacs.clear(); return null; }
				 */
				no2 = 1;
				while (currentMatch != null/* || emptyMatch == null */) {
					i++;
					if (singleNac != null && no2 % 500 == 0) {
						// System.out.println("sgnumber::match:" + number + "::"
						// + no2);
					}
					no2++;
					matches++;
					if (singleNac == null && matches % 500 == 0) {
						System.out.println("matches: " + matches);
					}
					if (singleNac != null && matches % 500 == 0) {
						System.out.println("tr matches: " + matches);
					}
					// System.out.println("next match");
					// System.out.println(subgraph.size());
					Map<AnnotatedElem, AnnotatedElem> currentNewm = new HashMap<AnnotatedElem, AnnotatedElem>(); // new
					if (currentMatch != null && subgraph.size() == g.getNodes().size() + g.getEdges().size()) {
						return null;
					}
					/*
					 * if (currentMatch == null) { emptyMatch =
					 * SamtraceFactory.eINSTANCE.createMatch(); currentMatch =
					 * emptyMatch; }
					 */
					Match gm = currentMatch;
					currentMatch = ipm.getNextMatching();
					currentMatching = initialMatching.copy();

					// experimental - separate node/edge matching

					// our subgraph should only consist of nodes

					for (AnnotatedElem elem : subgraph) {
						if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(elem.eClass())) {
							throw new RuntimeException("found edge in node-only subgraph");
						}
					}

					// match as many edges as possible
					for (Edge e : g.getEdges()) {
						// equivalent nodes in merged graph
						Node src = null;
						Node tar = null;
						if (subgraph.contains(e.getSource())) {
							src = gm.getNodeMatching().get(e.getSource());
						} else if (interfaceNodes.contains(e.getSource())) {
							src = nacInterfaceMatch.getNodeMatching().get(e.getSource());
						} else {
							continue;
						}
						if (subgraph.contains(e.getTarget())) {
							tar = gm.getNodeMatching().get(e.getTarget());
						} else if (interfaceNodes.contains(e.getTarget())) {
							tar = nacInterfaceMatch.getNodeMatching().get(e.getTarget());
						} else {
							continue;
						}
						Edge resultEdge = null;
						for (Edge matchingEdge : this.mergedGraph.getEdges()) {
							if (!initialMatching.getEdgeMatching().containsValue(matchingEdge)
									&& !gm.getEdgeMatching().containsValue(matchingEdge)) {
								if (matchingEdge.getTarget() == tar && matchingEdge.getSource() == src) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()) {
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								} else if (matchingEdge.getTarget() == src && matchingEdge.getSource() == tar) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()
											&& e.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED) {
										// undirected edge - src and tar may be
										// reversed for the matching edge
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								}
							}
						}
						if (resultEdge != null) {
							gm.getEdgeMatching().put(e, resultEdge);
						}
					}
					// complicated, should be changed somehow
					// and should be checked!
					if (g.getNodes().size() - interfaceNodes.size() <= gm.getNodeMatching().size()
							&& g.getEdges().size() == gm.getEdgeMatching().size()) {
						return null;
					}

					NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
					boolean inconsistent = false;
					for (Node n : nac.getNodes()) {
						Node copyInNewGraph = nacConversion.getNodeMatching().get(n);
						Node copyInMergedGraph = null;
						copyInMergedGraph = gm.getNodeMatching().get(copyInNewGraph);
						if (copyInMergedGraph != null
								&& currentMatching.getNodeMatching().containsValue(copyInMergedGraph)) { // new
							inconsistent = true;
							incon++;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							// node matches another node in the merged Graph, so
							// add the match
							currentMatching.getNodeMatching().put(n, copyInMergedGraph);
							// existingNodesInMerged.add(copyInMergedGraph);
							currentNewm.put(copyInMergedGraph, n); // new
						} else {
							// node does not match another node, so create it
							// (copy the orignial node in the NAC)
							Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
							if (fromProperty) {
								((PatternNode) newNodeInMerged).setSameInProp(n);
							} else {
								((PatternNode) newNodeInMerged).setSameInRule(n);
							}
							newNac.getNodes().add(newNodeInMerged);
							currentMatching.getNodeMatching().put(n, newNodeInMerged);
						}
					}
					if (inconsistent) {
						// there were inconsistent matchings in this element of
						// the cartesian product, so skip the list
						// cnt++;
						newNac.setGraph(null);
						continue;
					}

					// edges
					for (Edge e : nac.getEdges()) {
						Edge copyInNewGraph = nacConversion.getEdgeMatching().get(e);
						Edge copyInMergedGraph = null;
						copyInMergedGraph = gm.getEdgeMatching().get(copyInNewGraph);
						if (copyInMergedGraph != null
								&& currentMatching.getEdgeMatching().containsValue(copyInMergedGraph)) {
							inconsistent = true;
							incon++;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							currentMatching.getEdgeMatching().put(e, copyInMergedGraph);
							currentNewm.put(copyInMergedGraph, e);
						} else {
							// No match was found for the current edge, so copy
							// it and get source
							// and target node from the currentMatching.
							// Since all the nodes have been processed before,
							// source and target will exist.
							Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
							if (fromProperty) {
								((PatternEdge) newEdgeInMerged).setSameInProp(e);
							} else {
								((PatternEdge) newEdgeInMerged).setSameInRule(e);
							}
							newNac.getEdges().add(newEdgeInMerged);
							currentMatching.getEdgeMatching().put(e, newEdgeInMerged);

							// This is an attempt to keep track of the new items
							// created in a NAC
							// so that redundant NACs can be skipped. Since it
							// did not work out as planned,
							// newItems and oldItems are currently not used.

						}
					}

					if (inconsistent) {
						newNac.setGraph(null);
						continue;
					}
					boolean twice = false;
					/*
					 * for (Map<AnnotatedElem, AnnotatedElem> curr :
					 * srcItemsm.values()) { //if (curr.size() ==
					 * currentNewm.size()) { if (curr.size() >=
					 * currentNewm.size()) { // existing NAC could be stronger
					 * (smaller negative part) than currentNac boolean unequal =
					 * false; for (Map.Entry<AnnotatedElem, AnnotatedElem> entry
					 * : currentNewm.entrySet()) { if (entry.getValue() !=
					 * curr.get(entry.getKey())) { //unequal = true; break; } }
					 * // Now we check whether there exists an item matched as
					 * positive in the existing NAC // that is matched to a
					 * different positive element or not at all in the current
					 * NAC. // If the existing NAC is smaller, meaning that the
					 * positive matching is larger, // this will obviously be
					 * the case. If they are of equal size and share the same
					 * elements, they // can only be (I think) from the same
					 * subgraph and the same match, so this will not happen.
					 * 
					 * // before changing the subgraph iterator to disregard
					 * permutations of the NAC interface in subgraphs, // it was
					 * possible to gain equivalent matches from those different
					 * subgraphs. After changing that, it // should not happen
					 * any more. for (Map.Entry<AnnotatedElem, AnnotatedElem>
					 * entry : curr.entrySet()) { if
					 * (currentNewm.get(entry.getKey()) != entry.getValue()) {
					 * unequal = true; break; } }
					 * 
					 * if (unequal) { continue; } else { twice = true; break; }
					 * } }
					 */

					if (twice) {
						tw++;
						// System.out.println("remove");
						currentNewm.clear();
						newNac.setGraph(null);
						// continue;
					} else {
						Set<NegativeApplicationCondition> toRemove = new HashSet<NegativeApplicationCondition>();
						// iterate over all positive matches for existing NACs
						// to be added
						for (NegativeApplicationCondition nacToReplace : srcItemsm.keySet()) {
							Map<AnnotatedElem, AnnotatedElem> currItems = srcItemsm.get(nacToReplace);
							boolean remove = false;
							// the current NAC is smaller than the existing NAC
							// -> check if existing NAC can be replaced
							if (currentNewm.size() >= currItems.size()) {
								remove = true;
								for (Map.Entry<AnnotatedElem, AnnotatedElem> entry : currentNewm.entrySet()) {
									// check whether there is an item matched to
									// a positive item in the current NAC
									// that is not matched in the existing NAC.
									// If there is such a node, we must keep
									// the existing NAC. If it is an edge, the
									// NAC might be removed.
									if (!currItems.containsKey(entry.getKey())) {
										if (!SamgraphPackage.eINSTANCE.getEdge()
												.isSuperTypeOf(entry.getKey().eClass())) {
											remove = false;
											break;
										}
										// if there is an item in the current
										// positive match that is matched
										// differently in the existing NAC,
										// we must keep the existing NAC
									} else if (currItems.get(entry.getKey()) != entry.getValue()) {
										remove = false;
										break;
									}
								}
							}
							if (remove) {
								toRemove.add(nacToReplace);
							}
						}
						for (Edge e : nac.getEdges()) {
							Edge inMerged = currentMatching.getEdgeMatching().get(e);
							if (inMerged.getSource() == null && inMerged.getTarget() == null) {
								inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
								inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
							}

						}
						newNacs.add(newNac);
						srcItemsm.put(newNac, currentNewm);
						for (NegativeApplicationCondition nacToRemove : toRemove) {
							remNo++;
							for (Edge e : nacToRemove.getEdges()) {
								e.setSource(null);
								e.setTarget(null);
							}
							newNacs.remove(nacToRemove);
							srcItemsm.remove(nacToRemove);
						}
						// GraphCondition nc =
						// InvariantCheckingUtil.createNegatedCondition(newNac);
						// reduceNacs(nc, mergedGraph);
						/*
						 * if (mergedGraph.getCondition() == null) {
						 * mergedGraph.setCondition(nc); } else if
						 * (mergedGraph.getCondition().eClass() ==
						 * SamgraphconditionPackage.eINSTANCE.
						 * getNegatedCondition()) { LogicalGCCoupling co =
						 * SamgraphconditionFactory.eINSTANCE.
						 * createLogicalGCCoupling();
						 * co.getOperands().add(mergedGraph.getCondition());
						 * co.getOperands().add(nc);
						 * co.setOperator(LogicalOperator.CONJUNCTION);
						 * mergedGraph.setCondition(co); } else if
						 * (mergedGraph.getCondition().eClass() ==
						 * SamgraphconditionPackage.eINSTANCE.
						 * getLogicalGCCoupling()) { ((LogicalGCCoupling)
						 * mergedGraph.getCondition()).getOperands().add(nc); }
						 */
						// ((LogicalGCCoupling)
						// mergedGraph.getCondition()).getOperands().add(nc);
						// mergedGraph.setCondition(InvariantCheckingUtil.addNegatedCondition(mergedGraph.getCondition(),
						// newNac));
						// reduceNacs(mergedGraph);

					}
				}
			}
			// System.out.println("number of subgraphs: " + number);
			mergedGraph.setCondition(InvariantCheckerUtil.addNegatedConditions(mergedGraph.getCondition(), newNacs));
			// System.out.println("np-removed: " + (remNo / (remNo +
			// newNacs.size())));
			this.reset();
			// System.out.println("single + number: " + generateAll + ": " + i);
		}
		/*
		 * if (mergedGraph.getCondition() != null) { if
		 * (mergedGraph.getCondition().eClass() ==
		 * SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
		 * System.out.println("size of nacs: " + ((LogicalGCCoupling)
		 * mergedGraph.getCondition()).getOperands().size()); } else {
		 * System.out.println("size of nacs: " + 1); } }
		 */

		int size = 0;
		/*
		 * if (singleNac != null) { System.out.println(); System.out.println(
		 * "twice: " + tw); System.out.println("removed: " + remNo);
		 * System.out.println("incon: " + incon); System.out.println("matches: "
		 * + matches); }
		 */
		if (mergedGraph.getCondition() != null) {
			if (mergedGraph.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				// System.out.println("nonpartial, size of nacs after reduce: "
				// + ((LogicalGCCoupling)
				// mergedGraph.getCondition()).getOperands().size() + ", size: "
				// + mergedGraph.getNodes().size());
				// size = ((LogicalGCCoupling)
				// mergedGraph.getCondition()).getOperands().size();

			} else {
				// System.out.println("nonpartial, size of nacs after reduce: "
				// + 1);
			}
		}
		// System.out.println("translated");
		return mergedGraph;
	}

	/**
	 * Use only for context generation.
	 * 
	 * This method checks whether we do not need to find matches for a specific
	 * subgraph of a nac because all possible translated NACs would result in a
	 * forbidden pattern.
	 * 
	 * Later, it may be possible to do this for subgraphs with all possible
	 * edges so that all subgraphs of that subgraph can also be discarded
	 * without having to be checked individually.
	 * 
	 * @param subgraph
	 * @param nac
	 * @return
	 */
	private boolean checkSubgraphValidity(Set<AnnotatedElem> subgraph, NegativeApplicationCondition nac,
			Match nacConversion) {
		// nac different from graph in iterator!
		Match tmpMatch = InvariantCheckerUtil.copyAsRuleGraph(this.mergedGraph);
		Graph host = null;
		for (Node n : tmpMatch.getNodeMatching().values()) {
			host = (Graph) n.eContainer();
			break;
		}
		Match nacToHost = SamtraceFactory.eINSTANCE.createMatch();
		for (Node n : nac.getNodes()) {
			if (!subgraph.contains(nacConversion.getNodeMatching().get(n))) {
				Node newNode = n.copy();
				host.getNodes().add(newNode);
				nacToHost.getNodeMatching().put(n, newNode);
			}
		}
		for (Edge e : nac.getEdges()) {
			if (!subgraph.contains(nacConversion.getEdgeMatching().get(e))
					&& !subgraph.contains(nacConversion.getNodeMatching().get(e.getSource()))
					&& !subgraph.contains(nacConversion.getNodeMatching().get(e.getTarget()))) {
				Edge newEdge = e.copy();
				host.getEdges().add(newEdge);
				nacToHost.getEdgeMatching().put(e, newEdge);
				if (nac.getNodes().contains(e.getSource())) {
					newEdge.setSource(nacToHost.getNodeMatching().get(e.getSource()));
				} else {
					newEdge.setSource(
							tmpMatch.getNodeMatching().get(initialMatching.getNodeMatching().get(e.getSource())));
				}
				if (nac.getNodes().contains(e.getTarget())) {
					newEdge.setTarget(nacToHost.getNodeMatching().get(e.getTarget()));
				} else {
					newEdge.setTarget(
							tmpMatch.getNodeMatching().get(initialMatching.getNodeMatching().get(e.getTarget())));
				}
			}
		}
		IsomorphicPartMatcher tmpIpm = new IsomorphicPartMatcher();
		tmpIpm.setHostGraph(host);
		for (Graph constraint : this.restrictingConstraints) {
			tmpIpm.reset();
			tmpIpm.setPattern(constraint);
			tmpIpm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(constraint));
			if (tmpIpm.getNextMatching() != null) {
				return false;
			}
		}
		return true;
	}

	public RuleGraph translateContext() {
		int number = 0;

		double remNo = 0;
		int tw = 0;
		int incon = 0;
		int matches = 0;

		// this determines whether we translate nacs from a rule side or a
		// property
		// the sameInProp or sameInRul values have to be set accordingly
		boolean fromProperty = true;
		if (pattern.eContainer() != null && pattern.eContainer().eClass() == SamrulesPackage.eINSTANCE.getGraphRule()) {
			fromProperty = false;
		}

		// execute for each NAC
		for (NegativeApplicationCondition nac : ((GraphWithNacs) (SamGraphInvCheckGraphAdapter.getInstance(pattern)))
				.getNacs()) {

			currentMatching = initialMatching.copy();

			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			Match nacConversion = SubgraphIterator.nacToGraph(nac);

			// get the new (positive) graph built from the NAC
			// this will be the pattern and the current subgraph in the IPM
			Graph g = null;
			for (Edge e : nacConversion.getEdgeMatching().keySet()) {
				if (nacConversion.getEdgeMatching().get(e) != null) {
					g = (Graph) nacConversion.getEdgeMatching().get(e).eContainer();
					break;
				}
			}
			if (g == null) {
				for (Node n : nacConversion.getNodeMatching().keySet()) {
					if (nacConversion.getNodeMatching().get(n) != null) {
						g = (Graph) nacConversion.getNodeMatching().get(n).eContainer();
						break;
					}
				}
			}

			// optimization

			Map<EdgeType, Integer> edgeTypeMap = new HashMap<EdgeType, Integer>();
			Map<NodeType, Integer> nodeTypeMap = new HashMap<NodeType, Integer>();
			Set<Node> interfaceNodes = new HashSet<Node>();
			boolean incoherentNac = true;
			for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
				Node nodeInPattern = entry.getKey();
				boolean isInterfaceNode = false;
				for (Edge e : nodeInPattern.getIncoming()) {
					if (nac.getEdges().contains(e)) {
						isInterfaceNode = true;
						incoherentNac = false;
						interfaceNodes.add(nacConversion.getNodeMatching().get(entry.getKey()));
						break;
					}
				}
				if (!isInterfaceNode) {
					for (Edge e : nodeInPattern.getOutgoing()) {
						if (nac.getEdges().contains(e)) {
							isInterfaceNode = true;
							incoherentNac = false;
							interfaceNodes.add(nacConversion.getNodeMatching().get(entry.getKey()));
							break;
						}
					}
				}
				if (!isInterfaceNode) {
					if (nodeTypeMap.containsKey(nodeInPattern.getInstanceOf())) {
						nodeTypeMap.put(nodeInPattern.getInstanceOf(),
								nodeTypeMap.get(nodeInPattern.getInstanceOf()) + 1);
					} else {
						nodeTypeMap.put(nodeInPattern.getInstanceOf(), 1);
					}
				}
			}

			for (Map.Entry<Edge, Edge> entry : initialMatching.getEdgeMatching()) {
				Edge edgeInPattern = entry.getKey();
				if (edgeTypeMap.containsKey(edgeInPattern.getInstanceOf())) {
					edgeTypeMap.put(edgeInPattern.getInstanceOf(), edgeTypeMap.get(edgeInPattern.getInstanceOf()) + 1);
				} else {
					edgeTypeMap.put(edgeInPattern.getInstanceOf(), 1);
				}
			}

			// SubgraphIterator sgIter = new SubgraphIterator(g, mergedGraph);
			OptimizedSubgraphIterator sgIter = new OptimizedSubgraphIterator(g, mergedGraph, nodeTypeMap, edgeTypeMap,
					interfaceNodes);
			ipm.setPattern(g);
			ipm.setHostGraph(mergedGraph);
			Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();
			// Match emptyMatch = null;

			// create full nac
			if (incoherentNac) {
				NegativeApplicationCondition fullNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
				for (Node n : nac.getNodes()) {
					Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
					if (fromProperty) {
						((PatternNode) newNodeInMerged).setSameInProp(n);
					} else {
						((PatternNode) newNodeInMerged).setSameInRule(n);
					}
					fullNac.getNodes().add(newNodeInMerged);
					currentMatching.getNodeMatching().put(n, newNodeInMerged);
				}
				for (Edge e : nac.getEdges()) {
					Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
					if (fromProperty) {
						((PatternEdge) newEdgeInMerged).setSameInProp(e);
					} else {
						((PatternEdge) newEdgeInMerged).setSameInRule(e);
					}
					fullNac.getEdges().add(newEdgeInMerged);
					currentMatching.getEdgeMatching().put(e, newEdgeInMerged);
				}
				for (Edge e : nac.getEdges()) {
					Edge inMerged = currentMatching.getEdgeMatching().get(e);
					if (inMerged.getSource() == null && inMerged.getTarget() == null) {
						inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
						inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
					}
				}
				newNacs.add(fullNac);
			}

			Map<NegativeApplicationCondition, Map<AnnotatedElem, AnnotatedElem>> srcItemsm = new HashMap<NegativeApplicationCondition, Map<AnnotatedElem, AnnotatedElem>>(); // new
			number = 0;
			int no2 = 1;
			int valid = 0;
			int invalid = 0;
			int cached = 0;
			Set<Node> currentDiscarded = new HashSet<Node>();
			while (sgIter.hasNext()) {

				// System.out.println("next sg");
				number++;

				Set<AnnotatedElem> subgraph = sgIter.next();

				if (!checkSubgraphValidity(subgraph, nac, nacConversion) && false) {

					invalid++;
					sgIter.skip();
					// System.out.println("not valid");
					continue;
				} else {
					sgIter.skip();
					// if we skip at this point, we consider only subgraphs
					// consisting of nodes.
					// edges are not considered. This might be a possible
					// optimization, though I
					// still need to find out in what way.

					// sgIter.skip();

					// this is more general, probably also applicable to normal
					// NAC translation:
					// generate only subgraphs consisting solely of nodes,
					// create all NACs.
					// Afterwards, iterate over all edges and try to map as many
					// of them as possible.
					// Their position is unambiguously defined by their source
					// and target nodes.
					// Thus, we avoid generating all combinations of edgse
					// absent or present for
					// a given subgraph consisting of nodes.

					// This approach should probably be formally justified. We
					// would need to prove
					// that a nac without a specific edge is more powerful than
					// the same NAC
					// containing the edge.

					// Using only nodes might lead to more time being spent in
					// the IPM algorithm
					// since nodes are not connected and thus need to be matched
					// in separate
					// connected components. Consequently, the algorithm to find
					// start nodes in
					// connected components should be improved, e.g. by
					// consideration of node types.

				}

				// System.out.println("valid");
				valid++;
				ipm.setCurrentSubGraph(subgraph);
				ipm.reset();

				List<Edge> blacklist = new LinkedList<Edge>();
				for (Edge e : initialMatching.getEdgeMatching().keySet()) {
					// ignore positive edges from the initial matching because
					// they already have matching edges
					blacklist.add(initialMatching.getEdgeMatching().get(e));
				}

				List<Node> nodeBlacklist = new LinkedList<Node>();
				for (Node nc : initialMatching.getNodeMatching().keySet()) {
					nodeBlacklist.add(initialMatching.getNodeMatching().get(nc));
				}

				Match nacInterfaceMatch = SamtraceFactory.eINSTANCE.createMatch();
				for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
					Node nodeInNac = nacConversion.getNodeMatching().get(entry.getKey());
					if (nodeInNac != null) {
						// nacConversionGraph/subgraph -> mergedGraph
						nacInterfaceMatch.getNodeMatching().put(nodeInNac, entry.getValue());
					}
				}

				ipm.handleNACMatchCall(nacInterfaceMatch, nodeBlacklist);

				Match currentMatch = ipm.getNextMatching();

				no2 = 1;
				while (currentMatch != null/* || emptyMatch == null */) {
					if (subgraph.size() == 6) {
						for (Map.Entry<Node, Node> entry : currentMatch.getNodeMatching()) {
							// System.out.println(entry.getKey().getName() +
							// "::" + entry.getValue().getName());
						}
					}
					no2++;
					matches++;
					// System.out.println("next match");
					// System.out.println(subgraph.size());
					Map<AnnotatedElem, AnnotatedElem> currentNewm = new HashMap<AnnotatedElem, AnnotatedElem>(); // new
					if (currentMatch != null && subgraph.size() == g.getNodes().size() + g.getEdges().size()) {
						// found full nac
						return null;
					}

					Match gm = currentMatch;
					currentMatch = ipm.getNextMatching();
					currentMatching = initialMatching.copy();

					// our subgraph should only consist of nodes

					for (AnnotatedElem elem : subgraph) {
						if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(elem.eClass())) {
							throw new RuntimeException("found edge in node-only subgraph");
						}
					}

					// match as many edges as possible
					for (Edge e : g.getEdges()) {
						// equivalent nodes in merged graph
						Node src = null;
						Node tar = null;
						if (subgraph.contains(e.getSource())) {
							src = gm.getNodeMatching().get(e.getSource());
						} else if (interfaceNodes.contains(e.getSource())) {
							src = nacInterfaceMatch.getNodeMatching().get(e.getSource());
						} else {
							continue;
						}
						if (subgraph.contains(e.getTarget())) {
							tar = gm.getNodeMatching().get(e.getTarget());
						} else if (interfaceNodes.contains(e.getTarget())) {
							tar = nacInterfaceMatch.getNodeMatching().get(e.getTarget());
						} else {
							continue;
						}
						Edge resultEdge = null;
						for (Edge matchingEdge : this.mergedGraph.getEdges()) {
							if (!initialMatching.getEdgeMatching().containsValue(matchingEdge)
									&& !gm.getEdgeMatching().containsValue(matchingEdge)) {
								if (matchingEdge.getTarget() == tar && matchingEdge.getSource() == src) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()) {
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								} else if (matchingEdge.getTarget() == src && matchingEdge.getSource() == tar) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()
											&& e.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED) {
										// undirected edge - src and tar may be
										// reversed for the matching edge
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								}
							}
						}
						if (resultEdge != null) {
							gm.getEdgeMatching().put(e, resultEdge);
						}
					}

					NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
					boolean inconsistent = false;
					for (Node n : nac.getNodes()) {
						Node copyInNewGraph = nacConversion.getNodeMatching().get(n);
						Node copyInMergedGraph = null;
						copyInMergedGraph = gm.getNodeMatching().get(copyInNewGraph);
						if (copyInMergedGraph != null
								&& currentMatching.getNodeMatching().containsValue(copyInMergedGraph)) { // new
							inconsistent = true;
							incon++;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							// node matches another node in the merged Graph, so
							// add the match
							currentMatching.getNodeMatching().put(n, copyInMergedGraph);
							// existingNodesInMerged.add(copyInMergedGraph);
							currentNewm.put(copyInMergedGraph, n); // new
						} else {
							// node does not match another node, so create it
							// (copy the orignial node in the NAC)
							Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
							if (fromProperty) {
								((PatternNode) newNodeInMerged).setSameInProp(n);
							} else {
								((PatternNode) newNodeInMerged).setSameInRule(n);
							}
							newNac.getNodes().add(newNodeInMerged);
							currentMatching.getNodeMatching().put(n, newNodeInMerged);
						}
					}
					if (inconsistent) {
						// there were inconsistent matchings in this element of
						// the cartesian product, so skip the list
						// cnt++;
						newNac.setGraph(null);
						continue;
					}

					// edges
					for (Edge e : nac.getEdges()) {
						Edge copyInNewGraph = nacConversion.getEdgeMatching().get(e);
						Edge copyInMergedGraph = null;
						copyInMergedGraph = gm.getEdgeMatching().get(copyInNewGraph);
						if (copyInMergedGraph != null
								&& currentMatching.getEdgeMatching().containsValue(copyInMergedGraph)) {
							inconsistent = true;
							incon++;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							currentMatching.getEdgeMatching().put(e, copyInMergedGraph);
							currentNewm.put(copyInMergedGraph, e);
						} else {
							// No match was found for the current edge, so copy
							// it and get source
							// and target node from the currentMatching.
							// Since all the nodes have been processed before,
							// source and target will exist.
							Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
							if (fromProperty) {
								((PatternEdge) newEdgeInMerged).setSameInProp(e);
							} else {
								((PatternEdge) newEdgeInMerged).setSameInRule(e);
							}
							newNac.getEdges().add(newEdgeInMerged);
							currentMatching.getEdgeMatching().put(e, newEdgeInMerged);

							// This is an attempt to keep track of the new items
							// created in a NAC
							// so that redundant NACs can be skipped. Since it
							// did not work out as planned,
							// newItems and oldItems are currently not used.

						}
					}

					if (inconsistent) {
						newNac.setGraph(null);
						continue;
					}
					boolean twice = false;

					if (twice) {
						tw++;
						// System.out.println("remove");
						currentNewm.clear();
						newNac.setGraph(null);
						// continue;
					} else {
						Set<NegativeApplicationCondition> toRemove = new HashSet<NegativeApplicationCondition>();
						// iterate over all positive matches for existing NACs
						// to be added
						for (NegativeApplicationCondition nacToReplace : srcItemsm.keySet()) {
							Map<AnnotatedElem, AnnotatedElem> currItems = srcItemsm.get(nacToReplace);
							boolean remove = false;
							// the current NAC is smaller than the existing NAC
							// -> check if existing NAC can be replaced
							if (currentNewm.size() >= currItems.size()) {
								remove = true;
								for (Map.Entry<AnnotatedElem, AnnotatedElem> entry : currentNewm.entrySet()) {
									// check whether there is an item matched to
									// a positive item in the current NAC
									// that is not matched in the existing NAC.
									// If there is such a node, we must keep
									// the existing NAC. If it is an edge, the
									// NAC might be removed.
									if (!currItems.containsKey(entry.getKey())) {
										if (!SamgraphPackage.eINSTANCE.getEdge()
												.isSuperTypeOf(entry.getKey().eClass())) {
											remove = false;
											break;
										}
										// if there is an item in the current
										// positive match that is matched
										// differently in the existing NAC,
										// we must keep the existing NAC
									} else if (currItems.get(entry.getKey()) != entry.getValue()) {
										remove = false;
										break;
									}
								}
							}
							if (remove) {
								toRemove.add(nacToReplace);
							}
						}
						for (Edge e : nac.getEdges()) {
							Edge inMerged = currentMatching.getEdgeMatching().get(e);
							if (inMerged.getSource() == null && inMerged.getTarget() == null) {
								inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
								inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
							}

						}
						newNacs.add(newNac);
						srcItemsm.put(newNac, currentNewm);
						for (NegativeApplicationCondition nacToRemove : toRemove) {
							remNo++;
							for (Edge e : nacToRemove.getEdges()) {
								e.setSource(null);
								e.setTarget(null);
							}
							newNacs.remove(nacToRemove);
							srcItemsm.remove(nacToRemove);
						}
					}
				}
			}
			// System.out.println("number of subgraphs: " + number);
			mergedGraph.setCondition(InvariantCheckerUtil.addNegatedConditions(mergedGraph.getCondition(), newNacs));
			// System.out.println("np-removed: " + (remNo / (remNo +
			// newNacs.size())));
			this.reset();
			// System.out.println("valid: " + valid);
			// System.out.println("invalid: " + invalid);
			// System.out.println("cached: " + cached);
		}
		/*
		 * if (mergedGraph.getCondition() != null) { if
		 * (mergedGraph.getCondition().eClass() ==
		 * SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
		 * System.out.println("size of nacs: " + ((LogicalGCCoupling)
		 * mergedGraph.getCondition()).getOperands().size()); } else {
		 * System.out.println("size of nacs: " + 1); } }
		 */
		int size = 0;
		/*
		 * if (singleNac != null) { System.out.println(); System.out.println(
		 * "twice: " + tw); System.out.println("removed: " + remNo);
		 * System.out.println("incon: " + incon); System.out.println("matches: "
		 * + matches); }
		 */

		if (mergedGraph.getCondition() != null) {
			if (mergedGraph.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				// System.out.println("nonpartial, size of nacs after reduce: "
				// + ((LogicalGCCoupling)
				// mergedGraph.getCondition()).getOperands().size() + ", size: "
				// + mergedGraph.getNodes().size());
				size = ((LogicalGCCoupling) mergedGraph.getCondition()).getOperands().size();
			} else {
				// System.out.println("nonpartial, size of nacs after reduce: "
				// + 1);
			}
		}
		return mergedGraph;
	}

	public boolean checkFullNacExistence() {
		for (NegativeApplicationCondition nac : ((GraphWithNacs) (SamGraphInvCheckGraphAdapter.getInstance(pattern)))
				.getNacs()) {

			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			Match nacConversion = SubgraphIterator.nacToGraph(nac);

			// get the new (positive) graph built from the NAC
			// this will be the pattern and the current subgraph in the IPM
			Graph g = null;
			for (Edge e : nacConversion.getEdgeMatching().keySet()) {
				if (nacConversion.getEdgeMatching().get(e) != null) {
					g = (Graph) nacConversion.getEdgeMatching().get(e).eContainer();
					break;
				}
			}
			if (g == null) {
				for (Node n : nacConversion.getNodeMatching().keySet()) {
					if (nacConversion.getNodeMatching().get(n) != null) {
						g = (Graph) nacConversion.getNodeMatching().get(n).eContainer();
						break;
					}
				}
			}
			ipm.setPattern(g);
			ipm.setHostGraph(mergedGraph);
			Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();
			// Match emptyMatch = null;

			// search for full nac to prevent target pattern that will trivially
			// evaluate to false
			Set<AnnotatedElem> completeNac = SubgraphIterator.graphToSubGraph(g);
			ipm.setCurrentSubGraph(completeNac);
			ipm.reset();

			List<Edge> blacklist = new LinkedList<Edge>();
			for (Edge e : initialMatching.getEdgeMatching().keySet()) {
				// ignore positive edges from the initial matching because they
				// already have matching edges
				blacklist.add(initialMatching.getEdgeMatching().get(e));
			}

			List<Node> nodeBlacklist = new LinkedList<Node>();
			for (Node nc : initialMatching.getNodeMatching().keySet()) {
				nodeBlacklist.add(initialMatching.getNodeMatching().get(nc));
			}

			Match nacInterfaceMatch = SamtraceFactory.eINSTANCE.createMatch();
			for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
				Node nodeInNac = nacConversion.getNodeMatching().get(entry.getKey());
				if (nodeInNac != null) {
					nacInterfaceMatch.getNodeMatching().put(nodeInNac, entry.getValue());
				}
			}

			ipm.handleNACMatchCall(nacInterfaceMatch, nodeBlacklist);

			Match currentMatch = ipm.getNextMatching();

			if (currentMatch != null) {
				return true;
			} else {
				// return false;
			}
		}
		return false;
	}

	/**
	 * In contrast to the nonpartial translate, partialTranslate does not use
	 * fixed nodes in the SubgraphIterator. This is mainly due to the fact that
	 * the NACTranslationFilter does not take that much time when using partial
	 * translates.
	 * 
	 * Skip or rewrite twice!
	 * 
	 * @param rule
	 * @return
	 */
	public Graph partialTranslate(GraphRule rule) {

		/*
		 * new approach for NAC translation: - during reverse rule application,
		 * add deleted items to (positive) items in the partial NAC - when
		 * partially translating, build the subgraph from the partial NAC with:
		 * - edges with a created type - nodes with a created type - nodes
		 * adjacent to edges with a created type - build the target graph to
		 * match to (usually the merged graph) with: - created nodes - created
		 * edges - nodes adjacent to created edges
		 * 
		 * It is probably necessary to extract the respective subgraph from the
		 * merged graph before executing the translation. As long as the
		 * forbidden pattern and the subgraph as constructed above are disjoint,
		 * the NACs should be the same.
		 * 
		 * When translating, the items marked as bound items are not necessarily
		 * only targets of node or edge mappings but also those that were
		 * included as potential target before starting the mapping/translation
		 * process. This is similar to the way the complete NAC is copied to the
		 * merged graph in the straight-forward full translation without
		 * matching a single element while still being represented by a total,
		 * not a partial morphism.
		 * 
		 * This means that the annotations regarding bound items are the same
		 * for each partially translated NAC, assuming that they were created as
		 * described above.
		 * 
		 */

		boolean fromProperty = true;
		if (pattern.eContainer() != null && pattern.eContainer().eClass() == SamrulesPackage.eINSTANCE.getGraphRule()) {
			fromProperty = false;
		}

		// execute for each NAC
		for (NegativeApplicationCondition nac : ((GraphWithNacs) (SamGraphInvCheckGraphAdapter.getInstance(pattern)))
				.getNacs()) {

			// save the graph containing the negative elements. This is for
			// caching purposes only.
			Graph nacGraph = null;
			for (Node n : nac.getNodes()) {
				nacGraph = (Graph) n.eContainer();
				break;
			}
			if (nacGraph == null) {
				for (Edge e : nac.getEdges()) {
					nacGraph = (Graph) e.eContainer();
					break;
				}
			}

			double remNo = 0;
			int tw = 0;
			currentMatching = initialMatching.copy();

			// before setting the hostGraph attribute of the IPM, we need to
			// calculate the graph to translate the NAC to.
			// Since this is a partial translation, we translate only to
			// nodes/edges that are created, nodes adjacent to created edges and
			// preserved nodes adjacent to deleted edges.
			// For caching purposes we also save the equivalent nodes/edges in
			// the rule.
			// actually, we could probably restrict the subgraphMergedGraph even
			// further since only items with types from the NAC will be relevant

			Set<Node> extractedNodes = new HashSet<Node>();
			Set<Edge> extractedEdges = new HashSet<Edge>();

			Set<Node> extractedNodesInRule = new HashSet<Node>();
			Set<Edge> extractedEdgesInRule = new HashSet<Edge>();

			Set<NodeType> createdNodeTypes = new HashSet<NodeType>();
			Set<EdgeType> createdEdgeTypes = new HashSet<EdgeType>();

			for (Node n : mergedGraph.getNodes()) {
				if (((PatternNode) n).getSameInRule() != null
						&& ((PatternNode) n).getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getCreatedNode()) {
					extractedNodes.add(n);
					createdNodeTypes.add(n.getInstanceOf());
					extractedNodesInRule.add(((PatternNode) n).getSameInRule());
				}

				// need to add nodes adjacent to edges that are deleted by the
				// rule, i.e. created by the reverse rule application
				Node sameInRule = ((PatternNode) n).getSameInRule();
				if (sameInRule != null && sameInRule.eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
					Node refInRule = ((PreservedNode) sameInRule).getRefInRule();
					for (Edge e : refInRule.getIncoming()) {
						if (e.eClass() == SamrulesPackage.eINSTANCE.getDeletedEdge()) {
							if (!extractedNodes.contains(n)) {
								extractedNodes.add(n);
								createdNodeTypes.add(n.getInstanceOf());
								extractedNodesInRule.add(sameInRule);
							}
							break;
						}
					}
					for (Edge e : refInRule.getOutgoing()) {
						if (e.eClass() == SamrulesPackage.eINSTANCE.getDeletedEdge()) {
							if (!extractedNodes.contains(n)) {
								extractedNodes.add(n);
								createdNodeTypes.add(n.getInstanceOf());
								extractedNodesInRule.add(sameInRule);
							}
							break;
						}
					}
				}
			}

			for (Edge e : mergedGraph.getEdges()) {
				if (((PatternEdge) e).getSameInRule() != null
						&& ((PatternEdge) e).getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getCreatedEdge()) {
					extractedEdges.add(e);
					createdEdgeTypes.add(e.getInstanceOf());
					extractedEdgesInRule.add(((PatternEdge) e).getSameInRule());
					if (!extractedNodes.contains(e.getSource())) {
						extractedNodes.add(e.getSource());
						createdNodeTypes.add(e.getSource().getInstanceOf());
						extractedNodesInRule.add(((PatternNode) e.getSource()).getSameInRule());
					}
					if (!extractedNodes.contains(e.getTarget())) {
						extractedNodes.add(e.getTarget());
						createdNodeTypes.add(e.getTarget().getInstanceOf());
						extractedNodesInRule.add(((PatternNode) e.getTarget()).getSameInRule());
					}
				}
			}

			IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
			Match nacConversion = SubgraphIterator.partialNacToGraph(nac, createdEdgeTypes, createdNodeTypes);

			Set<Node> interfaceNodes = new HashSet<Node>();
			for (Edge e : nac.getEdges()) {
				if (!nac.getNodes().contains(e.getSource())) {
					interfaceNodes.add(e.getSource());
				}
				if (!nac.getNodes().contains(e.getTarget())) {
					interfaceNodes.add(e.getTarget());
				}
			}

			// get the new (positive) graph built from the NAC
			// this will be the pattern and the current subgraph in the IPM
			Graph g = null;
			for (Edge e : nacConversion.getEdgeMatching().keySet()) {
				if (nacConversion.getEdgeMatching().get(e) != null) {
					g = (Graph) nacConversion.getEdgeMatching().get(e).eContainer();
					break;
				}
			}
			if (g == null) {
				for (Node n : nacConversion.getNodeMatching().keySet()) {
					if (nacConversion.getNodeMatching().get(n) != null) {
						g = (Graph) nacConversion.getNodeMatching().get(n).eContainer();
						break;
					}
				}
			}
			if (g == null) {
				// System.out.println("empty");
				g = SamgraphFactory.eINSTANCE.createGraph();
			}

			// problem here: nac interface not included in subgraphMErgedGraph
			// (extracted), therefore illegal matches on
			// interface elements are found
			// solution: first, choose elements of NAC that will be given to the
			// subgraph iterator.
			// Then, for any NAC interface elements contained, add the
			// respective elements in the subgraphMErgedGraph.

			Match extractedSGMatch = SubgraphIterator.extractSubgraph(extractedNodes, extractedEdges);
			Graph subgraphMergedGraph = null;
			for (Edge e : extractedSGMatch.getEdgeMatching().keySet()) {
				if (extractedSGMatch.getEdgeMatching().get(e) != null) {
					subgraphMergedGraph = (Graph) extractedSGMatch.getEdgeMatching().get(e).eContainer();
					break;
				}
			}
			if (subgraphMergedGraph == null) {
				for (Node n : extractedSGMatch.getNodeMatching().keySet()) {
					if (extractedSGMatch.getNodeMatching().get(n) != null) {
						subgraphMergedGraph = (Graph) extractedSGMatch.getNodeMatching().get(n).eContainer();
						break;
					}
				}
			}

			Match mirroredExtractedSGMatch = SamtraceFactory.eINSTANCE.createMatch();
			for (Map.Entry<Node, Node> entry : extractedSGMatch.getNodeMatching()) {
				mirroredExtractedSGMatch.getNodeMatching().put(entry.getValue(), entry.getKey());
			}
			for (Map.Entry<Edge, Edge> entry : extractedSGMatch.getEdgeMatching()) {
				mirroredExtractedSGMatch.getEdgeMatching().put(entry.getValue(), entry.getKey());
			}

			// OptimizedSubgraphIterator sgIter = new
			// OptimizedSubgraphIterator(g);
			// try to disregard interface nodes
			Set<Node> convertedInterfaceNodes = new HashSet<Node>();
			for (Node n : interfaceNodes) {
				Node newNode = nacConversion.getNodeMatching().get(n);
				if (newNode != null) {
					convertedInterfaceNodes.add(newNode);
				}
			}

			OptimizedSubgraphIterator sgIter = new OptimizedSubgraphIterator(g, false, subgraphMergedGraph, null, null,
					convertedInterfaceNodes);

			ipm.setPattern(g);
			// ipm.setHostGraph(mergedGraph); // this actually has to be the
			// modified graph
			ipm.setHostGraph(subgraphMergedGraph);
			Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();

			// ipm initialization
			List<Node> nodeBlacklist = new LinkedList<Node>();
			for (Node nc : initialMatching.getNodeMatching().keySet()) {
				nodeBlacklist.add(extractedSGMatch.getNodeMatching().get(initialMatching.getNodeMatching().get(nc)));
			}

			Match nacInterfaceMatch = SamtraceFactory.eINSTANCE.createMatch();
			for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
				Node nodeInNac = nacConversion.getNodeMatching().get(entry.getKey());
				if (nodeInNac != null && extractedSGMatch.getNodeMatching().containsKey(entry.getValue())) {
					nacInterfaceMatch.getNodeMatching().put(nodeInNac,
							extractedSGMatch.getNodeMatching().get(entry.getValue()));
				}
			}

			// Match emptyMatch = null;

			// search for full nac to prevent target pattern that will trivially
			// evaluate to false
			// will skip this for the moment. Need to evaluate whether it is
			// better to check it afterwards in case of a
			// counterexample not eliminated by the StructuralPropertyFilter.

			Match fullNacConversion = SubgraphIterator.nacToGraph(nac);

			// get the new (positive) graph built from the NAC
			// this will be the pattern and the current subgraph in the IPM
			Graph fullNacG = null;
			for (Edge e : fullNacConversion.getEdgeMatching().keySet()) {
				if (fullNacConversion.getEdgeMatching().get(e) != null) {
					fullNacG = (Graph) fullNacConversion.getEdgeMatching().get(e).eContainer();
					break;
				}
			}
			if (fullNacG == null) {
				for (Node n : fullNacConversion.getNodeMatching().keySet()) {
					if (fullNacConversion.getNodeMatching().get(n) != null) {
						fullNacG = (Graph) fullNacConversion.getNodeMatching().get(n).eContainer();
						break;
					}
				}
			}

			Set<AnnotatedElem> completeNac = SubgraphIterator.graphToSubGraph(fullNacG);
			ipm.setCurrentSubGraph(completeNac);
			ipm.setHostGraph(mergedGraph);
			ipm.setPattern(fullNacG);
			ipm.reset();

			List<Edge> blacklist = new LinkedList<Edge>();
			for (Edge e : initialMatching.getEdgeMatching().keySet()) {
				// ignore positive edges from the initial matching because they
				// already have matching edges
				blacklist.add(initialMatching.getEdgeMatching().get(e));
			}

			List<Node> nodeBlacklist1 = new LinkedList<Node>();
			for (Node nc : initialMatching.getNodeMatching().keySet()) {
				nodeBlacklist1.add(initialMatching.getNodeMatching().get(nc));
			}

			Match nacInterfaceMatch1 = SamtraceFactory.eINSTANCE.createMatch();
			for (Map.Entry<Node, Node> entry : initialMatching.getNodeMatching()) {
				Node nodeInNac = fullNacConversion.getNodeMatching().get(entry.getKey());
				if (nodeInNac != null) {
					nacInterfaceMatch1.getNodeMatching().put(nodeInNac, entry.getValue());
				}
			}

			ipm.handleNACMatchCall(nacInterfaceMatch1, nodeBlacklist1);

			Match currentMatch1 = ipm.getNextMatching();

			if (currentMatch1 != null) {
				// full nac found, return null, unset previous fields
				this.previousRule = null;
				this.previousNacGraph = null;
				this.previousRuleNodes = null;
				this.previousRuleEdges = null;
				this.previousMatches.clear();
				this.previousNacConversion = null;
				this.previousMirroredExtractedMatch = null;
				// System.out.println("full nac found: " + ((NegatedCondition)
				// pattern.eContainer().eContainer()).getName());
				return null;
			}
			ipm.reset();
			ipm.setPattern(g);
			ipm.setHostGraph(subgraphMergedGraph);

			Map<NegativeApplicationCondition, Map<AnnotatedElem, AnnotatedElem>> srcItemsm = new HashMap<NegativeApplicationCondition, Map<AnnotatedElem, AnnotatedElem>>(); // new

			// this is temporary - it creates the full NAC.

			// if (!sgIter.hasNext()) {
			NegativeApplicationCondition fullNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
			for (Node n : nac.getNodes()) {
				Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
				if (fromProperty) {
					((PatternNode) newNodeInMerged).setSameInProp(n);
				} else {
					((PatternNode) newNodeInMerged).setSameInRule(n);
				}
				fullNac.getNodes().add(newNodeInMerged);
				currentMatching.getNodeMatching().put(n, newNodeInMerged);
			}
			for (Edge e : nac.getEdges()) {
				Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
				if (fromProperty) {
					((PatternEdge) newEdgeInMerged).setSameInProp(e);
				} else {
					((PatternEdge) newEdgeInMerged).setSameInRule(e);
				}
				fullNac.getEdges().add(newEdgeInMerged);
				currentMatching.getEdgeMatching().put(e, newEdgeInMerged);
			}
			for (Edge e : nac.getEdges()) {
				Edge inMerged = currentMatching.getEdgeMatching().get(e);
				if (inMerged.getSource() == null && inMerged.getTarget() == null) {
					inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
					inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
				}
			}
			for (Node n : initialMatching.getNodeMatching().values()) {
				Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
				an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
				an.setTarget(n);
				fullNac.getAnnotations().add(an);
			}
			for (Edge e : initialMatching.getEdgeMatching().values()) {
				Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
				an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
				an.setTarget(e);
				fullNac.getAnnotations().add(an);
			}
			// ... and also all extracted elements from the merged graph the NAC
			// was translated to
			for (Node n : extractedNodes) {
				if (!initialMatching.getNodeMatching().containsValue(n)) {
					Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
					an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
					an.setTarget(n);
					fullNac.getAnnotations().add(an);
				}
			}
			for (Edge e : extractedEdges) {
				if (!initialMatching.getEdgeMatching().containsValue(e)) {
					Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
					an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
					an.setTarget(e);
					fullNac.getAnnotations().add(an);
				}
			}

			newNacs.add(fullNac);
			// }

			// check for repetition
			boolean repeat = false;
			if (rule == this.previousRule && this.previousNacGraph == nacGraph) {
				repeat = true;
				if (this.previousRuleEdges != null && this.previousRuleEdges.size() == extractedEdgesInRule.size()
						&& this.previousRuleNodes != null
						&& this.previousRuleNodes.size() == extractedNodesInRule.size()) {
					for (Node n : extractedNodesInRule) {
						if (!this.previousRuleNodes.contains(n)) {
							repeat = false;
						}
					}
					for (Edge e : extractedEdgesInRule) {
						if (!this.previousRuleEdges.contains(e)) {
							repeat = false;
						}
					}
				} else {
					repeat = false;
				}
			} else {
				repeat = false;
			}
			// repeat = false;
			if (!repeat) {
				this.previousRule = null;
				this.previousNacGraph = null;
				this.previousRuleEdges = null;
				this.previousRuleNodes = null;
				this.previousNacConversion = null;
				this.previousMirroredExtractedMatch = null;
				this.previousMatches.clear();
			} else {
				// System.out.println("repeat");
				for (Match m : this.previousMatches) {
					Match gm = SamtraceFactory.eINSTANCE.createMatch();
					currentMatching = initialMatching.copy();
					// recreate subgraph and match
					Set<Node> subgraph = new HashSet<Node>();

					for (Map.Entry<Node, Node> entry : m.getNodeMatching()) {
						Node nodeInOldSubgraph = entry.getKey();
						for (Map.Entry<Node, Node> nCEntry : this.previousNacConversion.getNodeMatching()) {
							if (nCEntry.getValue() == nodeInOldSubgraph) {
								Node nodeInCurrent = nacConversion.getNodeMatching().get(nCEntry.getKey());
								subgraph.add(nodeInCurrent);
								// we cannot use mappings involving the merged
								// graph because merged graphs change between
								// translations
								// so we have to look at the sameInRule item
								// in theory, every mapped item should have a
								// sameInRule equivalent because we cannot map
								// nac items on items from the property itself

								// this can probably be done easier if we save
								// our matches in a different form (e.g., direct
								// mappings to mergedGraph/rule instead of
								// mappings to intermediate graphs)
								Node nodeInOldMerged = this.previousMirroredExtractedMatch.getNodeMatching()
										.get(entry.getValue());
								Node nodeInRule = ((PatternNode) nodeInOldMerged).getSameInRule();
								if (nodeInRule == null) {
									throw new RuntimeException("strange");
								}
								Node newValue = null;

								for (Node n : this.mergedGraph.getNodes()) {
									if (((PatternNode) n).getSameInRule() == nodeInRule) {
										newValue = extractedSGMatch.getNodeMatching().get(n);
										break;
									}
								}
								gm.getNodeMatching().put(nodeInCurrent, newValue);
								// gm.getNodeMatching().put(nodeInCurrent,
								// extractedSGMatch.getNodeMatching().get(this.previousMirroredExtractedMatch.getNodeMatching().get(entry.getValue())));
								break;
							}
						}
					}

					// debug
					/*
					 * boolean stop = false; if (gm.getNodeMatching().size() ==
					 * 7 && mergedGraph.getNodes().size() == 16 &&
					 * mergedGraph.getEdges().size() == 27) { stop = true; for
					 * (Map.Entry<Node, Node> entry : gm.getNodeMatching()) { if
					 * (!entry.getKey().getName().substring(1).equals(entry.
					 * getValue().getName())) {
					 * System.out.println(entry.getKey().getName() + " :: " +
					 * entry.getValue().getName()); stop = false; break; } } }
					 */

					// match as many edges as possible
					for (Edge e : g.getEdges()) {
						// equivalent nodes in merged graph
						Node src = null;
						Node tar = null;
						if (subgraph.contains(e.getSource())) {
							src = gm.getNodeMatching().get(e.getSource());
						} else if (convertedInterfaceNodes.contains(e.getSource())) {
							src = nacInterfaceMatch.getNodeMatching().get(e.getSource());
						} else {
							continue;
						}
						if (subgraph.contains(e.getTarget())) {
							tar = gm.getNodeMatching().get(e.getTarget());
						} else if (convertedInterfaceNodes.contains(e.getTarget())) {
							tar = nacInterfaceMatch.getNodeMatching().get(e.getTarget());
						} else {
							continue;
						}
						Edge resultEdge = null;
						for (Edge matchingEdge : subgraphMergedGraph.getEdges()) {
							if (!initialMatching.getEdgeMatching()
									.containsValue(mirroredExtractedSGMatch.getEdgeMatching().get(matchingEdge))
									&& !gm.getEdgeMatching().containsValue(matchingEdge)) {
								if (matchingEdge.getTarget() == tar && matchingEdge.getSource() == src) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()) {
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								} else if (matchingEdge.getTarget() == src && matchingEdge.getSource() == tar) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()
											&& e.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED) {
										// undirected edge - src and tar may be
										// reversed for the matching edge
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								}
							}
						}
						if (resultEdge != null) {
							gm.getEdgeMatching().put(e, resultEdge);
						}
					}

					if (gm.getNodeMatching().size() >= 7) {
						// System.out.println("nodes: " +
						// gm.getNodeMatching().size() + ", edge: " +
						// gm.getEdgeMatching().size());
					}
					Map<AnnotatedElem, Annotation> annotationMapping = new HashMap<AnnotatedElem, Annotation>();

					NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
					boolean inconsistent = false;
					for (Node n : nac.getNodes()) {
						Node copyInNewGraph = nacConversion.getNodeMatching().get(n);
						Node copyInMergedGraph = null;
						copyInMergedGraph = mirroredExtractedSGMatch.getNodeMatching()
								.get(gm.getNodeMatching().get(copyInNewGraph));
						if (copyInMergedGraph != null
								&& currentMatching.getNodeMatching().containsValue(copyInMergedGraph)) { // new
							inconsistent = true;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							// node matches another node in the merged Graph, so
							// add the match
							currentMatching.getNodeMatching().put(n, copyInMergedGraph);
							// existingNodesInMerged.add(copyInMergedGraph);

							// create annotation that will indicate whether node
							// is "repeated" in the corresponding NAC
							// the annotations will be saved in the NAC itself,
							// not in the items. This is not an ideal solution,
							// but
							// will hopefully suffice until I come up with a
							// better idea
							/*
							 * Annotation an =
							 * SamannotationFactory.eINSTANCE.createAnnotation()
							 * ;
							 * an.setSource(InvariantCheckingCore.NAC_BOUND_ITEM
							 * ); an.setTarget(copyInMergedGraph);
							 * newNac.getAnnotations().add(an);
							 */
						} else {
							// node does not match another node, so create it
							// (copy the original node in the NAC)
							Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
							if (fromProperty) {
								((PatternNode) newNodeInMerged).setSameInProp(n);
							} else {
								((PatternNode) newNodeInMerged).setSameInRule(n);
							}
							newNac.getNodes().add(newNodeInMerged);
							currentMatching.getNodeMatching().put(n, newNodeInMerged);
						}
					}
					if (inconsistent) {
						// there were inconsistent matchings in this element of
						// the Cartesian product, so skip the list
						// cnt++;
						newNac.setGraph(null);
						continue;
					}

					// edges
					for (Edge e : nac.getEdges()) {
						Edge copyInNewGraph = nacConversion.getEdgeMatching().get(e);
						Edge copyInMergedGraph = null;
						copyInMergedGraph = mirroredExtractedSGMatch.getEdgeMatching()
								.get(gm.getEdgeMatching().get(copyInNewGraph));
						if (copyInMergedGraph != null
								&& currentMatching.getEdgeMatching().containsValue(copyInMergedGraph)) {
							inconsistent = true;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							currentMatching.getEdgeMatching().put(e, copyInMergedGraph);

							// see above
							/*
							 * Annotation an =
							 * SamannotationFactory.eINSTANCE.createAnnotation()
							 * ;
							 * an.setSource(InvariantCheckingCore.NAC_BOUND_ITEM
							 * ); an.setTarget(copyInMergedGraph);
							 * newNac.getAnnotations().add(an);
							 */
						} else {
							// No match was found for the current edge, so copy
							// it and get source
							// and target node from the currentMatching.
							// Since all the nodes have been processed before,
							// source and target will exist.
							Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
							if (fromProperty) {
								((PatternEdge) newEdgeInMerged).setSameInProp(e);
							} else {
								((PatternEdge) newEdgeInMerged).setSameInRule(e);
							}
							newNac.getEdges().add(newEdgeInMerged);
							currentMatching.getEdgeMatching().put(e, newEdgeInMerged);

							// This is an attempt to keep track of the new items
							// created in a NAC
							// so that redundant NACs can be skipped. Since it
							// did not work out as planned,
							// newItems and oldItems are currently not used.

						}
					}

					for (Edge e : nac.getEdges()) {
						Edge inMerged = currentMatching.getEdgeMatching().get(e);
						if (inMerged.getSource() == null && inMerged.getTarget() == null) {
							inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
							inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
						}
					}
					newNacs.add(newNac);

					// we need to put all items from the pattern as bound items
					// to the annotations of a potentially partial NAC ...
					for (Node n : initialMatching.getNodeMatching().values()) {
						Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
						an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
						an.setTarget(n);
						newNac.getAnnotations().add(an);
					}
					for (Edge e : initialMatching.getEdgeMatching().values()) {
						Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
						an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
						an.setTarget(e);
						newNac.getAnnotations().add(an);
					}
					// ... and also all extracted elements from the merged graph
					// the NAC was translated to
					for (Node n : extractedNodes) {
						if (!initialMatching.getNodeMatching().containsValue(n)) {
							Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
							an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
							an.setTarget(n);
							newNac.getAnnotations().add(an);
						}
					}
					for (Edge e : extractedEdges) {
						if (!initialMatching.getEdgeMatching().containsValue(e)) {
							Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
							an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
							an.setTarget(e);
							newNac.getAnnotations().add(an);
						}
					}
				}

				mergedGraph
						.setCondition(InvariantCheckerUtil.addNegatedConditions(mergedGraph.getCondition(), newNacs));
				if (mergedGraph.getCondition() != null) {
					if (mergedGraph.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE
							.getLogicalGCCoupling()) {
						// System.out.println("repeated: " +
						// ((LogicalGCCoupling)
						// mergedGraph.getCondition()).getOperands().size());
					} else {
						// System.out.println("repeated: " + 1);
					}
				}
				return mergedGraph;
			}

			int i = 0;
			int mat = 0;
			while (sgIter.hasNext()) {
				i++;
				if (i % 200 == 0) {
					// System.out.println("subgraphs: " + i + ", " + mat);
				}
				// System.out.println("next sg");

				Set<AnnotatedElem> subgraph = sgIter.next();
				// System.out.println(subgraph);
				ipm.setCurrentSubGraph(subgraph);
				ipm.reset();
				sgIter.skip();

				ipm.handleNACMatchCall(nacInterfaceMatch, nodeBlacklist);

				Match currentMatch = ipm.getNextMatching();

				while (currentMatch != null/* || emptyMatch == null */) {
					mat++;
					// System.out.println("next match");
					// System.out.println(subgraph.size());
					Map<AnnotatedElem, AnnotatedElem> currentNewm = new HashMap<AnnotatedElem, AnnotatedElem>(); // new

					Match gm = currentMatch;

					if (currentMatch != null) {
						this.previousMatches.add(currentMatch.copy());
					}

					currentMatch = ipm.getNextMatching();
					currentMatching = initialMatching.copy();

					// experimental - try to separate edge/node matching
					// our subgraph should only consist of nodes
					for (AnnotatedElem elem : subgraph) {
						if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(elem.eClass())) {
							throw new RuntimeException("found edge in node-only subgraph");
						}
					}

					// use caching! many nacs are translated multiple times
					// algo:
					// save all node-only subgraph matchings
					// save rule, pattern and extractetMergedSubgraph and
					// compare
					// note that we should not compare extractedMergedSubgraphs,
					// but the items used in creating them
					// if they are the same:
					// copy all node-only subgraph matchings
					// for each node-only matching, add edge matchings

					// can use extractedNodes and extractedEdges

					// match as many edges as possible
					// note that the partial translate function does not use
					// subgraph optimization,
					// meaning that there will also be subgraphs consisting of
					// interface nodes? probably?
					// so we do not need some complicated interface node check

					// actually, we do ignore interface nodes now
					for (Edge e : g.getEdges()) {
						// equivalent nodes in merged graph
						Node src = null;
						Node tar = null;
						if (subgraph.contains(e.getSource())) {
							src = gm.getNodeMatching().get(e.getSource());
						} else if (convertedInterfaceNodes.contains(e.getSource())) {
							src = nacInterfaceMatch.getNodeMatching().get(e.getSource());
						} else {
							continue;
						}
						if (subgraph.contains(e.getTarget())) {
							tar = gm.getNodeMatching().get(e.getTarget());
						} else if (convertedInterfaceNodes.contains(e.getTarget())) {
							tar = nacInterfaceMatch.getNodeMatching().get(e.getTarget());
						} else {
							continue;
						}
						Edge resultEdge = null;
						for (Edge matchingEdge : subgraphMergedGraph.getEdges()) {
							if (!initialMatching.getEdgeMatching()
									.containsValue(mirroredExtractedSGMatch.getEdgeMatching().get(matchingEdge))
									&& !gm.getEdgeMatching().containsValue(matchingEdge)) {
								if (matchingEdge.getTarget() == tar && matchingEdge.getSource() == src) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()) {
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								} else if (matchingEdge.getTarget() == src && matchingEdge.getSource() == tar) {
									if (matchingEdge.getInstanceOf() == e.getInstanceOf()
											&& e.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED) {
										// undirected edge - src and tar may be
										// reversed for the matching edge
										resultEdge = matchingEdge;
										break;
										// note that there may be more than one
										// matching edge which is not considered
										// here
										// however, all matching edges share the
										// same type and source/target,
										// so they are equivalent.
									}
								}
							}
						}
						if (resultEdge != null) {
							gm.getEdgeMatching().put(e, resultEdge);
						}
					}

					Map<AnnotatedElem, Annotation> annotationMapping = new HashMap<AnnotatedElem, Annotation>();

					NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
					boolean inconsistent = false;
					for (Node n : nac.getNodes()) {
						Node copyInNewGraph = nacConversion.getNodeMatching().get(n);
						Node copyInMergedGraph = null;
						copyInMergedGraph = mirroredExtractedSGMatch.getNodeMatching()
								.get(gm.getNodeMatching().get(copyInNewGraph));
						if (copyInMergedGraph != null
								&& currentMatching.getNodeMatching().containsValue(copyInMergedGraph)) { // new
							inconsistent = true;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							// node matches another node in the merged Graph, so
							// add the match
							currentMatching.getNodeMatching().put(n, copyInMergedGraph);
							// existingNodesInMerged.add(copyInMergedGraph);
							currentNewm.put(copyInMergedGraph, n); // new

							// create annotation that will indicate whether node
							// is "repeated" in the corresponding NAC
							// the annotations will be saved in the NAC itself,
							// not in the items. This is not an ideal solution,
							// but
							// will hopefully suffice until I come up with a
							// better idea
							/*
							 * Annotation an =
							 * SamannotationFactory.eINSTANCE.createAnnotation()
							 * ;
							 * an.setSource(InvariantCheckingCore.NAC_BOUND_ITEM
							 * ); an.setTarget(copyInMergedGraph);
							 * newNac.getAnnotations().add(an);
							 */
						} else {
							// node does not match another node, so create it
							// (copy the original node in the NAC)
							Node newNodeInMerged = InvariantCheckerUtil.copyAsPattern(n);
							if (fromProperty) {
								((PatternNode) newNodeInMerged).setSameInProp(n);
							} else {
								((PatternNode) newNodeInMerged).setSameInRule(n);
							}
							newNac.getNodes().add(newNodeInMerged);
							currentMatching.getNodeMatching().put(n, newNodeInMerged);
						}
					}
					if (inconsistent) {
						// there were inconsistent matchings in this element of
						// the Cartesian product, so skip the list
						// cnt++;
						newNac.setGraph(null);
						continue;
					}

					// edges
					for (Edge e : nac.getEdges()) {
						Edge copyInNewGraph = nacConversion.getEdgeMatching().get(e);
						Edge copyInMergedGraph = null;
						copyInMergedGraph = mirroredExtractedSGMatch.getEdgeMatching()
								.get(gm.getEdgeMatching().get(copyInNewGraph));
						if (copyInMergedGraph != null
								&& currentMatching.getEdgeMatching().containsValue(copyInMergedGraph)) {
							inconsistent = true;
							break;
						}

						if (inconsistent) {
							newNac.setGraph(null);
							break;
						}

						if (copyInMergedGraph != null) {
							currentMatching.getEdgeMatching().put(e, copyInMergedGraph);
							currentNewm.put(copyInMergedGraph, e);

							// see above
							/*
							 * Annotation an =
							 * SamannotationFactory.eINSTANCE.createAnnotation()
							 * ;
							 * an.setSource(InvariantCheckingCore.NAC_BOUND_ITEM
							 * ); an.setTarget(copyInMergedGraph);
							 * newNac.getAnnotations().add(an);
							 */
						} else {
							// No match was found for the current edge, so copy
							// it and get source
							// and target node from the currentMatching.
							// Since all the nodes have been processed before,
							// source and target will exist.
							Edge newEdgeInMerged = InvariantCheckerUtil.copyAsPattern(e);
							if (fromProperty) {
								((PatternEdge) newEdgeInMerged).setSameInProp(e);
							} else {
								((PatternEdge) newEdgeInMerged).setSameInRule(e);
							}
							newNac.getEdges().add(newEdgeInMerged);
							currentMatching.getEdgeMatching().put(e, newEdgeInMerged);

							// This is an attempt to keep track of the new items
							// created in a NAC
							// so that redundant NACs can be skipped. Since it
							// did not work out as planned,
							// newItems and oldItems are currently not used.

						}
					}

					if (inconsistent) {
						newNac.setGraph(null);
						continue;
					}
					boolean twice = false;
					for (Map<AnnotatedElem, AnnotatedElem> curr : srcItemsm.values()) {
						// if (curr.size() == currentNewm.size()) {
						if (curr.size() >= currentNewm.size()) {
							boolean unequal = false;
							for (Map.Entry<AnnotatedElem, AnnotatedElem> entry : currentNewm.entrySet()) {
								if (entry.getValue() != curr.get(entry.getKey())) {
									unequal = true;
									break;
								}
							}
							for (Map.Entry<AnnotatedElem, AnnotatedElem> entry : curr.entrySet()) {
								if (currentNewm.get(entry.getKey()) != entry.getValue()) {
									unequal = true;
									break;
								}
							}

							if (unequal) {
								continue;
							} else {
								twice = true; // reverse
								break;
							}
						}
					}

					if (twice) {
						tw++;
						currentNewm.clear();
						newNac.setGraph(null);
						newNac.getAnnotations().clear();
						// continue;
					} else {
						Set<NegativeApplicationCondition> toRemove = new HashSet<NegativeApplicationCondition>();
						/*
						 * for (NegativeApplicationCondition nacToReplace :
						 * srcItemsm.keySet()) { Map<AnnotatedElem,
						 * AnnotatedElem> currItems =
						 * srcItemsm.get(nacToReplace); boolean remove = false;
						 * if (currentNewm.size() >= currItems.size()) { remove
						 * = true; for (Map.Entry<AnnotatedElem, AnnotatedElem>
						 * entry : currentNewm.entrySet()) { if
						 * (!currItems.containsKey(entry.getKey())) { if
						 * (!SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(
						 * entry.getKey().eClass())) { remove = false; break; }
						 * } else if (currItems.get(entry.getKey()) !=
						 * entry.getValue()) { remove = false; break; } } } if
						 * (remove) { toRemove.add(nacToReplace); } }
						 */
						for (Edge e : nac.getEdges()) {
							Edge inMerged = currentMatching.getEdgeMatching().get(e);
							if (inMerged.getSource() == null && inMerged.getTarget() == null) {
								inMerged.setSource(currentMatching.getNodeMatching().get(e.getSource()));
								inMerged.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
							}

						}
						newNacs.add(newNac);

						// we need to put all items from the pattern as bound
						// items to the annotations of a potentially partial NAC
						// ...
						for (Node n : initialMatching.getNodeMatching().values()) {
							Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
							an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
							an.setTarget(n);
							newNac.getAnnotations().add(an);
						}
						for (Edge e : initialMatching.getEdgeMatching().values()) {
							Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
							an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
							an.setTarget(e);
							newNac.getAnnotations().add(an);
						}
						// ... and also all extracted elements from the merged
						// graph the NAC was translated to
						for (Node n : extractedNodes) {
							if (!initialMatching.getNodeMatching().containsValue(n)) {
								Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
								an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
								an.setTarget(n);
								newNac.getAnnotations().add(an);
							}
						}
						for (Edge e : extractedEdges) {
							if (!initialMatching.getEdgeMatching().containsValue(e)) {
								Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
								an.setSource(InvariantCheckerPlugin.NAC_BOUND_ITEM);
								an.setTarget(e);
								newNac.getAnnotations().add(an);
							}
						}

						srcItemsm.put(newNac, currentNewm);
						for (NegativeApplicationCondition nacToRemove : toRemove) {
							remNo++;
							for (Edge e : nacToRemove.getEdges()) {
								e.setSource(null);
								e.setTarget(null);
							}
							nacToRemove.getAnnotations().clear();
							newNacs.remove(nacToRemove);
							srcItemsm.remove(nacToRemove);
						}

					}
				}
			}
			// cache results
			this.previousRule = rule;
			this.previousNacGraph = nacGraph;
			this.previousRuleNodes = extractedNodesInRule;
			this.previousRuleEdges = extractedEdgesInRule;
			this.previousNacConversion = nacConversion;
			this.previousMirroredExtractedMatch = mirroredExtractedSGMatch.copy();

			// System.out.println("number of subgraphs: " + number);
			mergedGraph.setCondition(InvariantCheckerUtil.addNegatedConditions(mergedGraph.getCondition(), newNacs));
			this.reset();
			// System.out.println("removed: " + (remNo / (remNo +
			// newNacs.size())));
			// System.out.println("twice: " + tw);
			// System.out.println("np-removed: " + (remNo / (remNo +
			// newNacs.size())));
			// System.out.println("removed: " + remNo);
		}
		if (mergedGraph.getCondition() != null) {
			if (mergedGraph.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				// System.out.println("partial, size of nacs: " +
				// ((LogicalGCCoupling)
				// mergedGraph.getCondition()).getOperands().size() + ", node
				// size: " + mergedGraph.getNodes().size());
			} else {
				// System.out.println("partial, size of nacs: " + 1);
			}
		}
		int size = 0;

		if (mergedGraph.getCondition() != null) {
			if (mergedGraph.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				// System.out.println("partial, size of nacs: " +
				// ((LogicalGCCoupling)
				// mergedGraph.getCondition()).getOperands().size() + ", " +
				// ((NegatedCondition)
				// pattern.eContainer().eContainer()).getName());
				size = ((LogicalGCCoupling) mergedGraph.getCondition()).getOperands().size();
			} else {
				// System.out.println("partial, size of nacs: " + 1);
			}
		}

		return mergedGraph;

	}

	public RuleGraph eTranslate() {

		return null;
	}

}
