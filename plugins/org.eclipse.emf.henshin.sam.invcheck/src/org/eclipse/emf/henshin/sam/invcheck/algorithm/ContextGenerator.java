package org.eclipse.emf.henshin.sam.invcheck.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.GCNACAdapter;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;

/**
 * 
 * 
 * 
 * Create all possible context for one match, disjunctively combined.
 * Try to eliminate disjunctive combinations to reduce NACs to one possibility.
 * If there is more than one possibility, discard match and generated context.
 * Take the next match, repeat.
 * Take the next pattern, repeat.
 *
 * Problem: Translation of big NACs takes much time. Could order constraints by NAC size to alleviate this.
 * 
 * When creating subgraphs from NACs to be matched to the target graph, we could check whether the rest of the
 * NAC (connected to the positive part) contains a forbidden pattern. Thus, we could discard subgraphs 
 * without checking each and every match.
 * Unfortunately, this has to be implemented directly in the NACTranslator.
 * 
 * 
 * 
 * @author Pro
 *
 */
public class ContextGenerator {

	private Set<RuleGraph> generationConstraints;
	
	private Set<Graph> restrictingConstraints;
	
	private RuleGraph proofGoal;
	
	private RuleGraph currentConstraint;

	private NACTranslator nacT;
	
	private IsomorphicPartMatcher ipm;
	
	private Map<RuleGraph, Set<Match>> discardedMatches;
	
	private Map<RuleGraph, Set<Match>> discardedMatchesRevertible;
	
	public Set<RuleGraph> getGenerationConstraints() {
		return this.generationConstraints;
	}

	public void setGenerationConstraints(Set<RuleGraph> generationConstraints) {
		this.generationConstraints = generationConstraints;
	}

	public Set<Graph> getRestrictingConstraints() {
		return this.restrictingConstraints;
	}

	public void setRestrictingConstraints(Set<Graph> restrictingConstraints) {
		this.restrictingConstraints = restrictingConstraints;
	}

	public RuleGraph getProofGoal() {
		return this.proofGoal;
	}

	public void setProofGoal(RuleGraph proofGoal) {
		this.proofGoal = proofGoal;
	}

	/*
	public Set<RuleGraph> getCurrentConstraints() {
		return this.currentConstraints;
	}

	public void setCurrentConstraints(Set<RuleGraph> currentConstraints) {
		this.currentConstraints = currentConstraints;
	}*/
	
	public ContextGenerator() {
		this.ipm = new IsomorphicPartMatcher();
		this.nacT = new NACTranslator();
		this.discardedMatches = new HashMap<RuleGraph, Set<Match>>();
		this.discardedMatchesRevertible = new HashMap<RuleGraph, Set<Match>>();
	}
	
	public boolean proof() {
		Match match = InvariantCheckerUtil.copyAsRuleGraph(this.proofGoal);
		RuleGraph precondition = null;
		for (Node n : match.getNodeMatching().values()) {
			precondition = (RuleGraph) n.eContainer();
			break;
		}
		nacT.setRestrictingConstraints(this.restrictingConstraints);
				
		int cnt = 0;
		this.currentConstraint = precondition;		
		Iterator<RuleGraph> iter = this.generationConstraints.iterator();
		while (iter.hasNext()) {
			RuleGraph constraint = iter.next();
			if (generateContext(constraint)) {
				cnt++;
				if (checkProofGoal()) {
					return true;
				} else {
					// reset iterator
					iter = this.generationConstraints.iterator();
					// delete revertible discarded matches
					this.discardedMatchesRevertible.clear();
				}
			} else {
				// do nothing, continue loop with next pattern
			}				
		}
		//System.out.println(cnt);
		// iterated through all patterns without being able to generate new context
		return false;
	}
	
	public boolean generateContext(RuleGraph pattern) {
		//System.out.println("gc: " + ((NegatedCondition) pattern.eContainer().eContainer()).getName());
		// so actually we may allow multiple NACs. Should then change this check.
		if (pattern.getCondition() == null) {
			throw new UnsupportedOperationException("Patterns that are used to generate context must have one or more NACs.");
		}
		if (pattern.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling() && ((LogicalGCCoupling) pattern.getCondition()).getOperands().size() != 1) {
			// have to see whether this will work
			//throw new UnsupportedOperationException("Patterns that are used to generate context must have exactly one NAC.");
		}
		
		if (this.discardedMatches.get(pattern) == null) {
			this.discardedMatches.put(pattern, new HashSet<Match>());
		}
		if (this.discardedMatchesRevertible.get(pattern) == null) {
			this.discardedMatchesRevertible.put(pattern, new HashSet<Match>());
		}
		
		boolean generatedContext = false;
		
		RuleGraph g = null;
		Match tmpMatch = null;
		ipm.reset();
		
		// old version
		/*if (this.currentConstraint.getCondition() != null) {
			
			// at this point we take one and only one NAC from the current constraint to match the current generating constraint in it
			// in order to also generate context for other NACs 
			NegativeApplicationCondition nac = SamGraphInvCheckGraphAdapter.getInstance(this.currentConstraint).getNacs().get(0);
			tmpMatch = SubgraphIterator.fullNacToRuleGraph(nac, false);			
			for (Node n : tmpMatch.getNodeMatching().values()) {
				g = (RuleGraph) n.eContainer();
				break;
			}
		} else {
			tmpMatch = InvariantCheckingUtil.copyAsRuleGraph(this.currentConstraint);
			for (Node n : tmpMatch.getNodeMatching().values()) {
				g = (RuleGraph) n.eContainer();
				break;
			}
		}*/
		
		Iterator<NegativeApplicationCondition> nacIter = null;		
		boolean first = false;
		if (this.currentConstraint.getCondition() == null) {			
			first = true;			
		} else {
			// remember to delete condition if we reach a point where we remove all conditions from current constraint. Cannot happen right now
			nacIter = SamGraphInvCheckGraphAdapter.getInstance(this.currentConstraint).getNacs().iterator();
		}
		while (first || (nacIter != null && nacIter.hasNext())) {
			NegativeApplicationCondition targetNac = null;
			if (first) {
				tmpMatch = InvariantCheckerUtil.copyAsRuleGraph(this.currentConstraint);
				for (Node n : tmpMatch.getNodeMatching().values()) {
					g = (RuleGraph) n.eContainer();
					break;
				}
			} else {
				targetNac = nacIter.next();
				tmpMatch = SubgraphIterator.fullNacToRuleGraph(targetNac, false);			
				for (Node n : tmpMatch.getNodeMatching().values()) {
					g = (RuleGraph) n.eContainer();
					break;
				}
			}
			first = false;
				
		
			// old
		
			ipm.setHostGraph(g);
			ipm.setPattern(pattern);
			ipm.setCurrentSubGraph(SubgraphIterator.graphToPosSubGraph(pattern));
			ipm.reset();
			
			Match nextMatch = null;
			
			// here we could integrate a check for not only a match with the graph and one NAC
			// but for matches with other NACs if there is more than one.
			// however, we need to take care, that a match targets only the graph and exactly one NAC
			// could just iterate over NACs and then over matches for the current NAC. Would just need
			// to ensure that the same match in the graph will not be considered multiple times.
			while (nextMatch == null) {
				nextMatch = ipm.getNextMatching();			
				if (nextMatch == null) {
					//System.out.println("no match: " + ((NegatedCondition) pattern.eContainer().eContainer()).getName());
					//return false;
					break;
				} else {
					if (!checkMatch(nextMatch, tmpMatch, pattern) && false) {
						//System.out.println("match was discarded: " + ((NegatedCondition) pattern.eContainer().eContainer()).getName());
						nextMatch = null;
					} else {
						// match is valid, use it to generate context
						nacT.reset();
						nacT.setPattern(pattern);
						nacT.setMergedGraph(g);
						nacT.setInitialMatching(nextMatch);
						
						RuleGraph result = null;
						if (nacT.checkFullNacExistence()) {
							result = null; 
						} else {
							result = nacT.translateContext();
						}
						if (result == null) {
							//System.out.println("translate null: " + ((NegatedCondition) pattern.eContainer().eContainer()).getName());
							Match match = SamtraceFactory.eINSTANCE.createMatch();
							for (Map.Entry<Node, Node> entry : nextMatch.getNodeMatching()) {
								Node newValue = null;
								for (Map.Entry<Node, Node> e : tmpMatch.getNodeMatching()){
									if (entry.getValue() == e.getValue()) {
										newValue = e.getKey();
										break;
									}
								}
								match.getNodeMatching().put(entry.getKey(), newValue);
							}
							for (Map.Entry<Edge, Edge> entry : nextMatch.getEdgeMatching()) {
								Edge newValue = null;
								for (Map.Entry<Edge, Edge> e : tmpMatch.getEdgeMatching()){
									if (entry.getValue() == e.getValue()) {
										newValue = e.getKey();
										break;
									}
								}
								match.getEdgeMatching().put(entry.getKey(), newValue);
							}
							this.discardedMatches.get(pattern).add(match);
							nextMatch = null;
						} else {
							//System.out.println("translate not null: " + ((NegatedCondition) pattern.eContainer().eContainer()).getName());
							if (removeInvalidNacs(result)) {
								generatedContext = true;
								// probably a good idea to exclude match here. If not, it will result in an empty
								// translation the next time it is chosen and will then be discarded
								
								// now we have to transform the generated NACs to the current NAC
								if (this.currentConstraint.getCondition() == null) {								
									/*NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
									Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
									nacs.add(newNac);
									newNac.setGraph(this.currentConstraint);
									this.currentConstraint.setCondition(InvariantCheckingUtil.createNegatedConditions(nacs));*/
									targetNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
									//Set<NegativeApplicationCondition> nacs = new HashSet<NegativeApplicationCondition>();
									//nacs.add(targetNac);
									targetNac.setGraph(this.currentConstraint);
									//this.currentConstraint.setCondition(InvariantCheckingUtil.createNegatedConditions(nacs));
								}
								// in prior versions, we translated the result to the only nac in the current constraint
								// now, we translate it to the nac that was used as a target to generate context
								// NegativeApplicationCondition nac = GCNACAdapter.getInstance(((LogicalGCCoupling) this.currentConstraint.getCondition()).getOperands().get(0));
								// iterate over all generated nacs
								Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();								
								for (NegativeApplicationCondition generatedNac : SamGraphInvCheckGraphAdapter.getInstance(result).getNacs()) {
									// have to copy existing nac. Problem: cannot insert new nacs just yet because we are currently iterating over them -
									// or maybe we can, because the adapter will not be updated when we add nacs?
									NegativeApplicationCondition copyOfTargetNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
									copyOfTargetNac.setGraph(targetNac.getGraph());
									Match copyOfTargetNacMatch = copyNacWithoutInterface(targetNac, copyOfTargetNac);
									
									copyOfTargetNac.getNodes().addAll(generatedNac.getNodes());
									copyOfTargetNac.getEdges().addAll(generatedNac.getEdges());
																		
									for (Edge e : copyOfTargetNac.getEdges()) {
										if (!copyOfTargetNac.getNodes().contains(e.getSource())) {
											for (Map.Entry<Node, Node> entry : tmpMatch.getNodeMatching()) {
												if (entry.getValue() == e.getSource()) {
													if (copyOfTargetNacMatch.getNodeMatching().containsKey(entry.getKey())) {
														e.setSource(copyOfTargetNacMatch.getNodeMatching().get(entry.getKey()));
													} else {
														e.setSource(entry.getKey());
													}
													break;
												}
											}									
										}
										if (!copyOfTargetNac.getNodes().contains(e.getTarget())) {
											for (Map.Entry<Node, Node> entry : tmpMatch.getNodeMatching()) {
												if (entry.getValue() == e.getTarget()) {
													if (copyOfTargetNacMatch.getNodeMatching().containsKey(entry.getKey())) {
														e.setTarget(copyOfTargetNacMatch.getNodeMatching().get(entry.getKey()));
													} else {
														e.setTarget(entry.getKey());
													}
													break;
												}
											}
										}
										/*
										if (e.getSource().eContainer() != this.currentConstraint && !copyOfTargetNac.getNodes().contains(e.getSource())) {
											//System.out.println("nac error src/cr " + e.getName() + ":" + e.getInstanceOf().getName());
										}										
										if (e.getTarget().eContainer() != this.currentConstraint && !copyOfTargetNac.getNodes().contains(e.getTarget())) {
											//System.out.println("nac error tar/cr " + e.getName() + ":" + e.getInstanceOf().getName());
										}*/
									}
									newNacs.add(copyOfTargetNac);
								}
								
								
								
								//nac.getNodes().addAll(SamGraphInvCheckGraphAdapter.getInstance(result).getNacs().get(0).getNodes());
								//nac.getEdges().addAll(SamGraphInvCheckGraphAdapter.getInstance(result).getNacs().get(0).getEdges());
								/* old
								for (Edge e : nac.getEdges()) {
									if (!nac.getNodes().contains(e.getSource())) {
										for (Map.Entry<Node, Node> entry : tmpMatch.getNodeMatching()) {
											if (entry.getValue() == e.getSource()) {
												e.setSource(entry.getKey());
												break;
											}
										}									
									}
									if (!nac.getNodes().contains(e.getTarget())) {
										for (Map.Entry<Node, Node> entry : tmpMatch.getNodeMatching()) {
											if (entry.getValue() == e.getTarget()) {
												e.setTarget(entry.getKey());
												break;
											}
										}
									}
								}*/
								
								// exclude match
								Match match = SamtraceFactory.eINSTANCE.createMatch();
								for (Map.Entry<Node, Node> entry : nextMatch.getNodeMatching()) {
									Node newValue = null;
									for (Map.Entry<Node, Node> e : tmpMatch.getNodeMatching()){
										if (entry.getValue() == e.getValue()) {
											newValue = e.getKey();
											break;
										}
									}
									match.getNodeMatching().put(entry.getKey(), newValue);
								}								
								for (Map.Entry<Edge, Edge> entry : nextMatch.getEdgeMatching()) {
									Edge newValue = null;
									for (Map.Entry<Edge, Edge> e : tmpMatch.getEdgeMatching()){
										if (entry.getValue() == e.getValue()) {
											newValue = e.getKey();
											break;
										}
									}
									match.getEdgeMatching().put(entry.getKey(), newValue);
								}
								this.discardedMatches.get(pattern).add(match);
								
								// remove old NACs, add expanded NACs
								EObject gcToRemove = null;
								for (Edge e : targetNac.getEdges()) {
									if (gcToRemove == null) {
										gcToRemove = e.eContainer();
										while (SamgraphconditionPackage.eINSTANCE.getNegatedCondition() != gcToRemove.eClass()) {
											gcToRemove = gcToRemove.eContainer();
										}
									}
									e.setSource(null);
									e.setTarget(null);
								}
								if (gcToRemove == null) {
									for (Node n : targetNac.getNodes()) {
										gcToRemove = n.eContainer();
										while (SamgraphconditionPackage.eINSTANCE.getNegatedCondition() != gcToRemove.eClass()) {
											gcToRemove = gcToRemove.eContainer();
										}
										break;
									}
								}
								this.currentConstraint.setCondition(InvariantCheckerUtil.addNegatedConditions(this.currentConstraint.getCondition(), newNacs));
								((LogicalGCCoupling) this.currentConstraint.getCondition()).getOperands().remove(gcToRemove);
								/*
								for (Edge e : this.currentConstraint.getEdges()) {
									if (e.eContainer() != this.currentConstraint) {
										//System.out.println("cont error");
									}
									if (e.getSource().eContainer() != this.currentConstraint) {
										//System.out.println("cc error src");
									}
									if (e.getTarget().eContainer() != this.currentConstraint) {
										//System.out.println("cc error tar");
									}
								}
								for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(this.currentConstraint).getNacs()) {
									for (Edge e : nac.getEdges()) {										
										if (e.getSource().eContainer() != this.currentConstraint && !nac.getNodes().contains(e.getSource())) {
											System.out.println("nac error src");
										}										
										if (e.getTarget().eContainer() != this.currentConstraint && !nac.getNodes().contains(e.getTarget())) {
											System.out.println("nac error tar");
										}
									}
								}*/
								
								return true;
							} else {
								generatedContext = true;
								// there is no condition left. This means we have to discard the current NAC from which context was generated
								// if there is no nac left after that, the positive part of the proof goal is a forbidden pattern
								// this also means that we can prove forbidden patterns without nacs - however, the chance of failure to proof that is quite high
								
								// we must not add this match to the discarded matches
								/*
								Match match = SamtraceFactory.eINSTANCE.createMatch();
								for (Map.Entry<Node, Node> entry : nextMatch.getNodeMatching()) {
									Node newValue = null;
									for (Map.Entry<Node, Node> e : tmpMatch.getNodeMatching()){
										if (entry.getValue() == e.getValue()) {
											newValue = e.getKey();
											break;
										}
									}
									match.getNodeMatching().put(entry.getKey(), newValue);
								}
								for (Map.Entry<Edge, Edge> entry : nextMatch.getEdgeMatching()) {
									Edge newValue = null;
									for (Map.Entry<Edge, Edge> e : tmpMatch.getEdgeMatching()){
										if (entry.getValue() == e.getValue()) {
											newValue = e.getKey();
											break;
										}
									}
									match.getEdgeMatching().put(entry.getKey(), newValue);
								}
								this.discardedMatchesRevertible.get(pattern).add(match);*/
								
								// need to remove the nac from which this context was generated
								if (targetNac != null) {
									for (Edge e : targetNac.getEdges()) {
										e.setSource(null);
										e.setTarget(null);
									}
								}
								if (this.currentConstraint.getCondition() == null) {
									// do not add a condition
								} else if (SamgraphconditionPackage.eINSTANCE.getNegatedCondition() == this.currentConstraint.getCondition().eClass()) {
									// single condition, remove that. If this happens, we should be done with the proof
									this.currentConstraint.setCondition(null);
								} else if (SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling() == this.currentConstraint.getCondition().eClass()) {
									if (((LogicalGCCoupling) this.currentConstraint.getCondition()).getOperands().size() <= 1) {
										// no or just the one condition in coupling. If this happens, we should be done with the proof
										this.currentConstraint.setCondition(null);
									} else {
										// more than one condition in coupling of current constraint. have to continue context generation
										GraphCondition gc = null;
										for (GraphCondition cond : ((LogicalGCCoupling) this.currentConstraint.getCondition()).getOperands()) {
											if (GCNACAdapter.getInstance(cond).equals(targetNac)) {
												gc = cond;
												break;
											}
										}
										if (gc != null) {
											((LogicalGCCoupling) this.currentConstraint.getCondition()).getOperands().remove(gc);
										} else {
											throw new RuntimeException("strange error");	
										}
									}
								} else {
									throw new RuntimeException("strange error");
								}								
								return true;
							}					
						}					
					}
				}
			}
		}
		return generatedContext;
	}

	private boolean removeInvalidNacs(RuleGraph result) {
		Set<NegatedCondition> toRemove = new HashSet<NegatedCondition>();
		// translate returns a coupling - if there is just one condition and translate ever
		// returns just a negated condition, this should be changed
		for (GraphCondition gc : ((LogicalGCCoupling) result.getCondition()).getOperands()) {
			NegatedCondition nac = (NegatedCondition) gc;
			IsomorphicPartMatcher subIpm = new IsomorphicPartMatcher();
			Match match = SubgraphIterator.fullNacToGraph(GCNACAdapter.getInstance(nac), false);
			Graph host = null;
			for (Node n : match.getNodeMatching().values()) {
				host = (Graph) n.eContainer();
				break;
			}
			subIpm.setHostGraph(host);
			for (Graph rg : this.restrictingConstraints) {
				subIpm.reset();
				subIpm.setPattern(rg);
				subIpm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(rg));
				if (subIpm.getNextMatching() != null) {
					toRemove.add(nac);
					break;
				}
			}
		}
		for (NegatedCondition nac : toRemove) {
			Quantification quant = (Quantification) nac.getOperand();
			Graph graph = quant.getContext();
			for (Edge e : graph.getEdges()) {
				e.setSource(null);
				e.setTarget(null);
			}							
		}
		((LogicalGCCoupling) result.getCondition()).getOperands().removeAll(toRemove);
		if (((LogicalGCCoupling) result.getCondition()).getOperands().size() < 1) {
			//System.out.println("0 nacs left");
			return false;
		} else if (((LogicalGCCoupling) result.getCondition()).getOperands().size() > 1) {			
			// we will still return true here. Note that the generator should still give the correct result, as 
			// there will just be more nacs in the current constraint (the one trying to reach the proof goal.
			// However, generating new nacs with generating constraints will just happen in the first nac in the condition.
			// This is not wrong, but it is not complete either, as the generating constraint should be matches to all possible nacs, 
			// not just the first.
			//System.out.println("not one nac left: " + ((LogicalGCCoupling) result.getCondition()).getOperands().size());
			//return false;
			return true;
		} else {
			//System.out.println("one nac left: ");
			return true;
		}
	}
	
	private boolean checkMatch(Match chMatch, Match tmpMatch, RuleGraph pattern) {
		for (Match m : this.discardedMatches.get(pattern)) {
			boolean equal = true;
			for (Map.Entry<Node, Node> entry : chMatch.getNodeMatching()) {				
				if (tmpMatch.getNodeMatching().get(m.getNodeMatching().get(entry.getKey())) != entry.getValue()) {
					equal = false;
					break;
				}
			}
			if (equal) {
				for (Map.Entry<Edge, Edge> entry : chMatch.getEdgeMatching()) {				
					if (tmpMatch.getEdgeMatching().get(m.getEdgeMatching().get(entry.getKey())) != entry.getValue()) {
						equal = false;
						break;
					}
				}
			}
			if (equal) {
				return false;
			}
			// m: pattern -> host
			// chMatch: pattern ->chost
			// tmpMatch: host -> chost
		}
		for (Match m : this.discardedMatchesRevertible.get(pattern)) {
			boolean equal = true;
			for (Map.Entry<Node, Node> entry : chMatch.getNodeMatching()) {				
				if (tmpMatch.getNodeMatching().get(m.getNodeMatching().get(entry.getKey())) != entry.getValue()) {
					equal = false;
					break;
				}
			}
			if (equal) {
				for (Map.Entry<Edge, Edge> entry : chMatch.getEdgeMatching()) {				
					if (tmpMatch.getEdgeMatching().get(m.getEdgeMatching().get(entry.getKey())) != entry.getValue()) {
						equal = false;
						break;
					}
				}
			}
			if (equal) {
				return false;
			}
			// m: pattern -> host
			// chMatch: pattern ->chost
			// tmpMatch: host -> chost
		}
		return true;
	}
	
	public Match copyNacWithoutInterface(NegativeApplicationCondition nac, NegativeApplicationCondition newNac) {
		Match match = SamtraceFactory.eINSTANCE.createMatch();		
		
		for (Node n : nac.getNodes()) {
			Node newNode = InvariantCheckerUtil.copyAsPattern(n);
			match.getNodeMatching().put(n, newNode);
			newNac.getNodes().add(newNode);
		}
		for (Edge e : nac.getEdges()) {
			Edge newEdge = InvariantCheckerUtil.copyAsPattern(e);
			match.getEdgeMatching().put(e, newEdge);
			newNac.getEdges().add(newEdge);
			if (match.getNodeMatching().get(e.getSource()) != null) {
				newEdge.setSource(match.getNodeMatching().get(e.getSource()));
			} else {
				newEdge.setSource(e.getSource());
			}
			if (match.getNodeMatching().get(e.getTarget()) != null) {
				newEdge.setTarget(match.getNodeMatching().get(e.getTarget()));
			} else {
				newEdge.setTarget(e.getTarget());
			}
			
		}
		return match;
	}
	
	public boolean checkProofGoal() {
		// alle Graphen, die proof goal erf�llen, m�ssen auch current constraint erf�llen
		this.ipm.reset();
		this.ipm.setHostGraph(this.proofGoal);
		this.ipm.setPattern(this.currentConstraint);
		this.ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(this.currentConstraint));
		return this.ipm.getNextMatching() != null;
	}
	
	/**
	 * @deprecated 
	 */
	public void discardPatterns() {
		Set<RuleGraph> toDiscard = new HashSet<RuleGraph>();
		
		for (Graph rg : restrictingConstraints) {
			ipm.reset();
		
		}
		
	}
	
	public Set<RuleGraph> separateContext(RuleGraph targetGraph) {
		Set<RuleGraph> result = new HashSet<RuleGraph>();
		for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(targetGraph).getNacs()) {
			RuleGraph newGraph = null;
			Match match = InvariantCheckerUtil.copyAsRuleGraph(targetGraph);
			for (Node n : match.getNodeMatching().values()) {
				if (n != null) {
					newGraph = (RuleGraph) n.eContainer();
					break;
				}
			}
			for (Node n : nac.getNodes()) {
				newGraph.getNodes().add(n);
			}
			for (Edge e : nac.getEdges()) {
				newGraph.getEdges().add(e);
				if (!newGraph.getNodes().contains(e.getSource())) {
					e.setSource(match.getNodeMatching().get(e.getSource()));
				}
				if (!newGraph.getNodes().contains(e.getTarget())) {
					e.setTarget(match.getNodeMatching().get(e.getTarget()));
				}
			}
			result.add(newGraph);
		}
		return result;
	}
	
	/**
	 * @deprecated
	 * @param targetGraph
	 * @return
	 */
	public Set<RuleGraph> separateContextPositive(RuleGraph targetGraph) {
		Set<RuleGraph> result = new HashSet<RuleGraph>();
		for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(targetGraph).getNacs()) {
			RuleGraph newGraph = null;
			Match match = InvariantCheckerUtil.copyAsRuleGraph(targetGraph);
			for (Node n : match.getNodeMatching().values()) {
				if (n != null) {
					newGraph = (RuleGraph) n.eContainer();
					break;
				}
			}
			for (Node n : nac.getNodes()) {
				newGraph.getNodes().add(n);
			}
			for (Edge e : nac.getEdges()) {
				newGraph.getEdges().add(e);
				if (!newGraph.getNodes().contains(e.getSource())) {
					e.setSource(match.getNodeMatching().get(e.getSource()));
				}
				if (!newGraph.getNodes().contains(e.getTarget())) {
					e.setTarget(match.getNodeMatching().get(e.getTarget()));
				}
			}
			result.add(newGraph);
		}
		return result;
	}
	
	
	public RuleGraph addContext(RuleGraph pattern, RuleGraph targetGraph) {
		if (pattern.getCondition() == null) {
			return null;
		}
		if (pattern.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling() && ((LogicalGCCoupling) pattern.getCondition()).getOperands().size() != 1) {
			throw new UnsupportedOperationException();
		}
		
		Graph g = null;
		Match tmpMatch = null;
		ipm.reset();
		if (targetGraph.getCondition() != null) {		
			NegativeApplicationCondition nac = SamGraphInvCheckGraphAdapter.getInstance(targetGraph).getNacs().get(0);
			tmpMatch = SubgraphIterator.fullNacToGraph(nac, false);			
			for (Node n : tmpMatch.getNodeMatching().values()) {
				g = (Graph) n.eContainer();
				break;
			}
		} else {
			tmpMatch = InvariantCheckerUtil.copyAsRuleGraph(targetGraph);
			for (Node n : tmpMatch.getNodeMatching().values()) {
				g = (Graph) n.eContainer();
				break;
			}
		}
		ipm.setHostGraph(g);				
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToPosSubGraph(pattern));
		
		Match nextMatch = ipm.getNextMatching();
		if (nextMatch == null) {
			return null;
		}		
		nacT.reset();
		nacT.setPattern(pattern);
		nacT.setMergedGraph(targetGraph);
		nacT.setInitialMatching(nextMatch);
		RuleGraph result = nacT.translate();
		
		// reform NACs in result
		//for (NegativeApplicationCondition nac : )
		
		return result;
	}
	
	
	/**
	 * @deprecated
	 * @param pattern
	 * @param targetGraph
	 * @return
	 */
	public RuleGraph addContextPositive(RuleGraph pattern, RuleGraph targetGraph) {
		if (pattern.getCondition() == null) {
			return null;
		}
		if (pattern.getCondition().eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling() && ((LogicalGCCoupling) pattern.getCondition()).getOperands().size() != 1) {
			throw new UnsupportedOperationException();
		}
				
		ipm.setHostGraph(targetGraph);
			
		// have to transfer nacs to original graph
				
		ipm.setPattern(pattern);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToPosSubGraph(pattern));
		
		Match nextMatch = ipm.getNextMatching();
		if (nextMatch == null) {
			return null;
		}		
		nacT.reset();
		nacT.setPattern(pattern);
		nacT.setMergedGraph(targetGraph);
		nacT.setInitialMatching(nextMatch);
		RuleGraph result = nacT.translate();
		
		return result;
	}
	
}
