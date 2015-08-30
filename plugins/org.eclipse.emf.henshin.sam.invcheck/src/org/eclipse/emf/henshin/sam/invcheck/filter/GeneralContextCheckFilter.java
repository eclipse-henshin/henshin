package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

/**
 * This can probably be optimized. not very fast atm?
 * 
 * Problem: nac translation tries to set sameInProp and sameInRule fields. this
 * leads to items not in a ressource. not that bad, but inconvenient
 * 
 * @author jfd
 */
public class GeneralContextCheckFilter extends FilterSkeleton<GraphVerificationData, GraphVerificationData> {

	// private ContextGenerator prover;

	private NACTranslator nacT;
	private IsomorphicPartMatcher ipm;

	@ResultDictEntry(entryName = "discarded")
	private int discarded = 0;

	@ResultDictEntry(entryName = "passed")
	private int passed = 0;

	@ResultDictEntry(entryName = "translations")
	private int trans = 0;

	private Set<RuleGraph> constraints;
	private Set<Graph> resConstraints;

	/**
	 * this filter will use counterexamples that could not be discarded before.
	 * It will either a) use forbidden patterns without NACs to generate new
	 * NACs in the source graph patterns and then check again the existence of
	 * forbidden patterns in the extended sgp b) or check for the existence of
	 * forbidden patterns, generating not-matchable NACs as positive elements in
	 * the process that can then be analysed for the existence of forbidden
	 * patterns
	 * 
	 * First, we will focus on a). However, this is a problem because we
	 * generate lots of unnecessary nacs ... So, b) seems to be the better idea.
	 * 
	 * Just use the same procedure as in StructuralPropertyFilter but save NAC
	 * translations in the IPM that do not have an equivalent NAC in the host
	 * graph. For a first implementation, might be easier to just copy the IPM
	 * code and change it, where required ...
	 * 
	 * Translate NACs, discard those that can be found and add positive elements
	 * for all others. Then check for new forbidden patterns. Could also be used
	 * to subsequently check rule application, etc. again.
	 * 
	 */
	/*
	 * public void produce() { try { Graph sgp = this.currentInput.sourceGraph;
	 * // might contain partial NACs. translate NACs to complete graph.
	 * 
	 * Match newSGPMatch = InvariantCheckingUtil.copyAsRuleGraph(sgp); RuleGraph
	 * newSGP = null; for (Map.Entry<Node, Node> entry :
	 * newSGPMatch.getNodeMatching()) { if (entry.getValue() != null) { newSGP =
	 * (RuleGraph) entry.getValue().eContainer(); break; } } for
	 * (NegativeApplicationCondition nac :
	 * SamGraphInvCheckGraphAdapter.getInstance(sgp).getNacs()) { boolean
	 * partial = false; for (Annotation ann : nac.getAnnotations()) { if
	 * (ann.getSource().equals(InvariantCheckingCore.NAC_BOUND_ITEM)) { partial
	 * = true; } } if (!partial) { // copy whole NAC Match nacCopy =
	 * newSGPMatch.copy(); NegativeApplicationCondition newNac =
	 * NacFactory.eINSTANCE.createNegativeApplicationCondition(); for (Node n :
	 * nac.getNodes()) { PatternNode newNode =
	 * InvariantCheckingUtil.copyAsPattern(n);
	 * newNode.setSameInProp(((PatternNode) n).getSameInProp());
	 * newNode.setSameInRule(((PatternNode) n).getSameInRule());
	 * nacCopy.getNodeMatching().put(n, newNode);
	 * newNac.getNodes().add(newNode); } for (Edge e : nac.getEdges()) {
	 * PatternEdge newEdge = InvariantCheckingUtil.copyAsPattern(e);
	 * newEdge.setSameInProp(((PatternEdge) e).getSameInProp());
	 * newEdge.setSameInRule(((PatternEdge) e).getSameInRule());
	 * nacCopy.getEdgeMatching().put(e, newEdge);
	 * newEdge.setSource(nacCopy.getNodeMatching().get(e.getSource()));
	 * newEdge.setTarget(nacCopy.getNodeMatching().get(e.getTarget()));
	 * newNac.getEdges().add(newEdge); }
	 * newSGP.setCondition(InvariantCheckingUtil.addNegatedCondition(newSGP.
	 * getCondition(), newNac)); } else { // translate NAC Match initialMatching
	 * = SamtraceFactory.eINSTANCE.createMatch(); for (Annotation ann :
	 * nac.getAnnotations()) { if
	 * (ann.getSource().equals(InvariantCheckingCore.NAC_BOUND_ITEM)) { if
	 * (SamgraphPackage.eINSTANCE.getNode().isSuperTypeOf(ann.getTarget().eClass
	 * ())) { Node n = (Node) ann.getTarget(); if
	 * (!initialMatching.getNodeMatching().containsKey(n)) {
	 * initialMatching.getNodeMatching().put(n,
	 * newSGPMatch.getNodeMatching().get(n)); } } else if
	 * (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(ann.getTarget().eClass
	 * ())) { Edge e = (Edge) ann.getTarget(); if
	 * (!initialMatching.getEdgeMatching().containsKey(e)) {
	 * initialMatching.getEdgeMatching().put(e,
	 * newSGPMatch.getEdgeMatching().get(e)); } } } } nacT.reset();
	 * nacT.setInitialMatching(initialMatching); nacT.setMergedGraph(newSGP);
	 * nacT.setPattern(sgp); this.trans++; newSGP =
	 * nacT.translateSingleNac(nac); if (newSGP == null) { throw new
	 * RuntimeException("something strange happened"); } } }
	 * 
	 * // now we have expanded all partial nacs (and copied nonpartial NACs) //
	 * so we can start to generate context from forbidden patterns without NACs.
	 * // it is assured that no forbidden graphs can be bound because then the
	 * counterexample would not // appear here
	 * 
	 * for (Graph constraint : this.resConstraints) { // generate all subgraphs
	 * OptimizedSubgraphIterator sgIter = new
	 * OptimizedSubgraphIterator(constraint); while (sgIter.hasNext()) {
	 * Set<AnnotatedElem> subgraph = sgIter.next(); // create new cond.
	 * forbidden pattern RuleGraph newConstraint =
	 * SamrulesFactory.eINSTANCE.createRuleGraph(); Match tmpMatch =
	 * SamtraceFactory.eINSTANCE.createMatch(); NegativeApplicationCondition
	 * newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition(); for
	 * (Node n : constraint.getNodes()) { Node newNode = n.copy();
	 * tmpMatch.getNodeMatching().put(n, newNode); if (subgraph.contains(n)) {
	 * newConstraint.getNodes().add(newNode); } else {
	 * newNac.getNodes().add(newNode); } } for (Edge e : constraint.getEdges())
	 * { Edge newEdge = e.copy(); tmpMatch.getEdgeMatching().put(e, newEdge);
	 * newEdge.setSource(tmpMatch.getNodeMatching().get(e.getSource()));
	 * newEdge.setTarget(tmpMatch.getNodeMatching().get(e.getTarget())); if
	 * (subgraph.contains(e)) { newConstraint.getEdges().add(newEdge); } else {
	 * newNac.getEdges().add(newEdge); } }
	 * newConstraint.setCondition(InvariantCheckingUtil.createNegatedCondition(
	 * newNac)); ipm.reset(); ipm.setHostGraph(newSGP);
	 * ipm.setPattern(newConstraint);
	 * ipm.setCurrentSubGraph(SubgraphIterator.graphToPosSubGraph(newConstraint)
	 * ); Match matching = ipm.getNextMatching(); while (matching != null) {
	 * //System.out.println("matched new constraint"); nacT.reset();
	 * nacT.setInitialMatching(matching); nacT.setMergedGraph(newSGP);
	 * nacT.setPattern(newConstraint); newSGP = nacT.translate(); matching =
	 * ipm.getNextMatching(); // System.out.println("translated new constraint"
	 * ); } if (newSGP.getCondition() != null) { if
	 * (newSGP.getCondition().eClass() ==
	 * SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
	 * //System.out.println("tr, size of nacs: " + ((LogicalGCCoupling)
	 * newSGP.getCondition()).getOperands().size()); } else {
	 * //System.out.println("tr, size of nacs: " + 1); } } } } for (Graph
	 * constraint : this.constraints) { ipm.reset(); ipm.setHostGraph(newSGP);
	 * ipm.setPattern(constraint);
	 * ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(constraint)); if
	 * (ipm.getNextMatching() != null) { System.out.println("discarded by " +
	 * ((NegatedCondition) constraint.eContainer().eContainer()).getName());
	 * this.discarded++; return; } } this.passed++;
	 * this.defaultOutputPipe.queue(new
	 * GraphVerificationData(this.currentInput.pair, newSGP,
	 * this.currentInput.targetGraph)); } catch (InterruptedException ie) {
	 * this.running = false; }
	 * 
	 * }
	 */

	// new method appears to work. However, since we employ full nac
	// translation, checking big conditions in the counterexamples (namely
	// noSend-noFire) ist stil a problem.
	// so, just use partial nac stuff again. should not be that hard.
	// we can probably
	public void produce() {
		try {
			Graph sgp = this.currentInput.sourceGraph;
			// might contain partial NACs. translate NACs to complete graph.

			Match newSGPMatch = InvariantCheckerUtil.copyAsRuleGraph(sgp);
			RuleGraph newSGP = null;
			for (Map.Entry<Node, Node> entry : newSGPMatch.getNodeMatching()) {
				if (entry.getValue() != null) {
					newSGP = (RuleGraph) entry.getValue().eContainer();
					break;
				}
			}
			for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(sgp).getNacs()) {
				boolean partial = false;
				for (Annotation ann : nac.getAnnotations()) {
					if (ann.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM)) {
						partial = true;
					}
				}
				if (!partial) {
					// copy whole NAC
					Match nacCopy = newSGPMatch.copy();
					NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
					for (Node n : nac.getNodes()) {
						PatternNode newNode = InvariantCheckerUtil.copyAsPattern(n);
						newNode.setSameInProp(((PatternNode) n).getSameInProp());
						newNode.setSameInRule(((PatternNode) n).getSameInRule());
						nacCopy.getNodeMatching().put(n, newNode);
						newNac.getNodes().add(newNode);
					}
					for (Edge e : nac.getEdges()) {
						PatternEdge newEdge = InvariantCheckerUtil.copyAsPattern(e);
						newEdge.setSameInProp(((PatternEdge) e).getSameInProp());
						newEdge.setSameInRule(((PatternEdge) e).getSameInRule());
						nacCopy.getEdgeMatching().put(e, newEdge);
						newEdge.setSource(nacCopy.getNodeMatching().get(e.getSource()));
						newEdge.setTarget(nacCopy.getNodeMatching().get(e.getTarget()));
						newNac.getEdges().add(newEdge);
					}
					newSGP.setCondition(InvariantCheckerUtil.addNegatedCondition(newSGP.getCondition(), newNac));
				} else {
					// translate NAC
					Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
					for (Annotation ann : nac.getAnnotations()) {
						if (ann.getSource().equals(InvariantCheckerPlugin.NAC_BOUND_ITEM)) {
							if (SamgraphPackage.eINSTANCE.getNode().isSuperTypeOf(ann.getTarget().eClass())) {
								Node n = (Node) ann.getTarget();
								if (!initialMatching.getNodeMatching().containsKey(n)) {
									initialMatching.getNodeMatching().put(n, newSGPMatch.getNodeMatching().get(n));
								}
							} else if (SamgraphPackage.eINSTANCE.getEdge().isSuperTypeOf(ann.getTarget().eClass())) {
								Edge e = (Edge) ann.getTarget();
								if (!initialMatching.getEdgeMatching().containsKey(e)) {
									initialMatching.getEdgeMatching().put(e, newSGPMatch.getEdgeMatching().get(e));
								}
							}
						}
					}
					nacT.reset();
					nacT.setInitialMatching(initialMatching);
					nacT.setMergedGraph(newSGP);
					nacT.setPattern(sgp);
					this.trans++;
					newSGP = nacT.translateSingleNac(nac);
					if (newSGP == null) {
						throw new RuntimeException("something strange happened");
					}
				}
			}
			for (Graph constraint : constraints) {
				ipm.reset();
				ipm.setHostGraph(newSGP);
				ipm.setPattern(constraint);
				// match only positive part
				ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(constraint));
				ipm.setContext(true);
				if (ipm.getNextMatching() != null) {
					this.discarded++;
					System.out.println(
							"discarded by " + ((NegatedCondition) constraint.eContainer().eContainer()).getName());
					return;
				}
			}
			this.passed++;
			this.defaultOutputPipe
					.queue(new GraphVerificationData(this.currentInput.pair, newSGP, this.currentInput.targetGraph));

			// now we have expanded all partial nacs (and copied nonpartial
			// NACs)
			// so we can start to check for the existence of forbidden patterns

			/*
			 * for (Graph constraint : constraints) { ipm.setHostGraph(newSGP);
			 * ipm.setPattern(constraint); // match only positive part
			 * ipm.setCurrentSubGraph(SubgraphIterator.graphToPosSubGraph(
			 * constraint)); Match matching = ipm.getNextMatching(); // found a
			 * matching, match NACs and generate context where NACs are // not
			 * matchable
			 * 
			 * Match translation =
			 * InvariantCheckingUtil.copyAsRuleGraph(newSGP); Graph translated =
			 * null; if (translation.getNodeMatching().size() > 0) { translated
			 * = (Graph)
			 * translation.getNodeMatching().get(0).getValue().eContainer(); }
			 * if (translated == null && translation.getEdgeMatching().size() >
			 * 0) { translated = (Graph)
			 * translation.getEdgeMatching().get(0).getValue().eContainer(); }
			 * nacT.setMergedGraph((RuleGraph) translated);
			 * nacT.setPattern(constraint); Match initialMatching =
			 * SamtraceFactory.eINSTANCE.createMatch(); for (Map.Entry<Node,
			 * Node> entry : matching.getNodeMatching()) {
			 * initialMatching.getNodeMatching().put(entry.getKey(),
			 * translation.getNodeMatching().get(entry.getValue())); } for
			 * (Map.Entry<Edge, Edge> entry : matching.getEdgeMatching()) {
			 * initialMatching.getEdgeMatching().put(entry.getKey(),
			 * translation.getEdgeMatching().get(entry.getValue())); }
			 * nacT.setInitialMatching(initialMatching); Graph translatedGraph =
			 * nacT.translateSingleNac(ms.currentNAC); if (translatedGraph ==
			 * null) {
			 * 
			 * } } this.passed++; this.defaultOutputPipe.queue(new
			 * GraphVerificationData(this.currentInput.pair, newSGP,
			 * this.currentInput.targetGraph));
			 */
		} catch (InterruptedException ie) {
			this.running = false;
		}
	}

	@Override
	protected void initData() {

		Assert.isNotNull(this.getFilterDispatcher());
		Assert.isNotNull(this.getFilterDispatcher().getFilterInput());

		final EObject theEObject = this.getFilterDispatcher().getFilterInput();
		Assert.isTrue(theEObject.eClass() == SamrulesPackage.eINSTANCE.getGTS());
		final GTS theGTS = (GTS) theEObject;
		this.constraints = new HashSet<RuleGraph>();
		this.resConstraints = new HashSet<Graph>();

		for (TypeGraphCondition g : theGTS.getTypes().get(0).getConditions()) {
			if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
				NegatedCondition nc = (NegatedCondition) g;
				if (nc.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
					Quantification q = (Quantification) nc.getOperand();
					Graph graph = q.getContext();
					if (SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(graph.eClass())
							&& ((RuleGraph) graph).getCondition() != null) {
						if (!nc.getName().equals("noSend-noFire")) {
							this.constraints.add((RuleGraph) graph);
						}
					} else {
						this.resConstraints.add(graph);
					}
				}
			} else if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				LogicalGCCoupling lgc = (LogicalGCCoupling) g;
				for (GraphCondition gc : lgc.getOperands()) {
					if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
						NegatedCondition neg = (NegatedCondition) gc;
						if (neg.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
							Quantification q = (Quantification) neg.getOperand();
							Graph graph = q.getContext();
							if (SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(graph.eClass())
									&& ((RuleGraph) graph).getCondition() != null) {
								this.constraints.add((RuleGraph) graph);
							} else {
								this.resConstraints.add(graph);
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "GeneralContextCheckFilter";
		this.nacT = new NACTranslator();
		this.ipm = new IsomorphicPartMatcher();
		for (Graph constraint : resConstraints) {
			ipm.addRestrictingConstraint(constraint);
		}
	}
}
