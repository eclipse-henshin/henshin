package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class StructuralPropertyTargetPatternFilter extends
		FilterSkeleton<Pair<Pair<Graph, GraphRule>, Pair<Match, RuleGraph>>, Pair<Pair<Graph, GraphRule>, Pair<Match, RuleGraph>>> {

	private ContainsPropertyHelper internalHelper;
	
	private boolean printDebug;
	
	@ResultDictEntry(entryName="checked")
	private int checked = 0;
	
	@ResultDictEntry(entryName="discarded")
	private int discarded = 0;
	
		
	@Override
	protected void initData() {
		Assert.isNotNull(this.getFilterDispatcher());
		Assert.isNotNull(this.getFilterDispatcher().getFilterInput());
		
		final EObject theEObject = this.getFilterDispatcher().getFilterInput();
		Assert.isTrue(theEObject.eClass() == SamrulesPackage.eINSTANCE.getGTS());
		final GTS theGTS = (GTS) theEObject;
		
		List<Graph> forbidden = new BasicEList<Graph>();
		for (TypeGraphCondition g : theGTS.getTypes().get(0).getConditions()) {
			if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
				NegatedCondition nc = (NegatedCondition) g;
				if (nc.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
					Quantification q = (Quantification) nc.getOperand();
					if (q.getContext().eClass() != SamrulesPackage.eINSTANCE.getRuleGraph() || ((RuleGraph) q.getContext()).getCondition() == null) {
						forbidden.add(q.getContext());
					}
					//forbidden.add(q.getContext());
					
				}
			} else if (g.eClass() == SamgraphconditionPackage.eINSTANCE.getLogicalGCCoupling()) {
				LogicalGCCoupling lgc = (LogicalGCCoupling) g;
				for (GraphCondition gc : lgc.getOperands()) {
					if (gc.eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {
						NegatedCondition neg = (NegatedCondition) gc;
						if (neg.getOperand().eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {
							Quantification q = (Quantification) neg.getOperand();
							if (q.getContext().eClass() != SamrulesPackage.eINSTANCE.getRuleGraph() || ((RuleGraph) q.getContext()).getCondition() == null) {
								forbidden.add(q.getContext());
							}
						}
					}
				}
			}
		}
		
		Graph[] props =  forbidden.toArray(new Graph[forbidden.size()]);
		//Graph[] guarantees = theVerificationTask.getNonexistent().toArray(new Graph[theVerificationTask.getNonexistent().size()]);
		//Graph[] combinedArray = new Graph[props.length + guarantees.length];
		Graph[] combinedArray = new Graph[props.length];
		System.arraycopy(props, 0, combinedArray, 0, props.length);
		//System.arraycopy(guarantees, 0, combinedArray, props.length, guarantees.length);
		this.internalHelper = new ContainsPropertyHelper(combinedArray);
	}

	public void produce() {
		checked++;
		//System.out.println("spf " + this.currentInput.pair.second.getName() + " " + ((NegatedCondition) this.currentInput.pair.first.eContainer().eContainer()).getName());
		//System.out.println(this.currentInput.second.second.getNodes().size() + "::" + this.currentInput.second.second.getEdges().size());
		if (this.internalHelper.findPreservedProperties(this.currentInput.second.second)) {
			discarded++;			
			return; 
		} else {			
			try {
				this.defaultOutputPipe.queue(this.currentInput);
			} catch (InterruptedException e) {
				this.running = false;
			}
		}
	}
	

	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "StructuralPropertyTargetPatternFilter";	
		this.printDebug = this.getOption("printDebug");
	}
}
