package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingCore;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.ContextGenerator;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
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

public class GeneralContextFilter extends FilterSkeleton<GraphVerificationData, GraphVerificationData> {
	
	private ContextGenerator prover;
	
	private NACTranslator nacT;
	
	private int i = 0;
	
	private boolean printDebug;
	
	@ResultDictEntry(entryName="discarded")
	private int discarded = 0;
	
	@ResultDictEntry(entryName="passed")
	private int passed = 0;
	
	private Set<RuleGraph> constraints;
	private Set<Graph> resConstraints;
	
	public void produce() {
		try {
			RuleGraph sgp;
			if (SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(this.currentInput.sourceGraph.eClass()) && ((RuleGraph) this.currentInput.sourceGraph).getCondition() != null) {
				sgp = (RuleGraph) this.currentInput.sourceGraph;
			} else {
				this.defaultOutputPipe.queue(this.currentInput);
				passed++;
				return;
			}
			Match newSGPMatch = InvariantCheckingUtil.copyAsRuleGraph(sgp);
			RuleGraph newSGP = null;
			for (Map.Entry<Node, Node> entry : newSGPMatch.getNodeMatching()) {
				if (entry.getValue() != null) {
					newSGP = (RuleGraph) entry.getValue().eContainer();
				}
			}
			Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();			
			boolean partial = true;			
			for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(sgp).getNacs()) {
				if (nac.getAnnotations().isEmpty()) {
					partial = false;
				}
				for (Annotation ann : nac.getAnnotations()) {
					if (ann.getSource().equals(InvariantCheckingCore.NAC_BOUND_ITEM)) {
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
			}
			RuleGraph finalSGP = null;
			nacT.setPattern(sgp);
			nacT.setMergedGraph(newSGP);
			nacT.setInitialMatching(initialMatching);
			nacT.reset();
			//partial = false;
			if (partial) {
				finalSGP = nacT.translate();
				if (finalSGP == null) {
					throw new RuntimeException("problem in context generation");
				}
			} else {
				finalSGP = sgp;
			}
			prover.setProofGoal(finalSGP);
				
						
			if (prover.proof()) {
				//System.out.println("proven");
				discarded++;
				return;
			} else {
				//System.out.println("not proven");
				passed++;
				this.defaultOutputPipe.queue(this.currentInput);
			}
			
		} catch (InterruptedException ie) {
			this.running = false;
		}
		
	}
	
	
	@Override
	protected void initData() {
		this.prover = new ContextGenerator();
		
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
					if (/*!nc.getName().equals("noFire-noSend") && */SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(graph.eClass()) && ((RuleGraph) graph).getCondition() != null) {
						this.constraints.add((RuleGraph) graph);
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
							if (/*!neg.getName().equals("noFire-noSend") && */SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(graph.eClass()) && ((RuleGraph) graph).getCondition() != null) {
								this.constraints.add((RuleGraph) graph);
							} else {
								this.resConstraints.add(graph);
							}							
						}
					}
				}
			}
		}
		prover.setGenerationConstraints(this.constraints);
		prover.setRestrictingConstraints(this.resConstraints);
		
	}
	
	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "GeneralContexetFilter";	
		this.nacT = new NACTranslator();
		this.printDebug = this.getOption("printDebug");
	}	
}
