package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.model.samrules.GTS;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;


public class StructuralGraphRuleFilter extends FilterSkeleton<GraphVerificationData,GraphVerificationData> {

	private ContainsGraphRuleHelper internalHelper;
	
	private boolean printDebug;
	
	@ResultDictEntry(entryName="discarded")
	private int discarded = 0;
	
	@ResultDictEntry(entryName="passed")
	private int passed = 0;
	
	@Override
	protected void initData() {
		Assert.isNotNull(this.getFilterDispatcher());
		Assert.isNotNull(this.getFilterDispatcher().getFilterInput());
		
		final EObject theEObject = this.getFilterDispatcher().getFilterInput();
		Assert.isTrue(theEObject.eClass() == SamrulesPackage.eINSTANCE.getGTS());
		final GTS theGTS = (GTS) theEObject;
		
		final GraphRule[] rules = theGTS.getRules().toArray(new GraphRule[theGTS.getRules().size()]);
		this.internalHelper = new ContainsGraphRuleHelper(rules);
	}
	
	public void produce() {
		GraphVerificationData result;
		//Map<GraphRule, Collection<Match>> map = this.internalHelper.findAllMatchingRules(this.currentInput.pair.second.getPriority(), this.currentInput.sourceGraph);
		if (this.internalHelper.findMatchingRule(this.currentInput.pair.second.getPriority(), this.currentInput.sourceGraph)) {
			discarded++;
			return;
		} else {
			result = this.currentInput; //new GraphVerificationData(this.currentInput.pair,this.currentInput.sourceGraph,this.currentInput.targetGraph);		
			try {
				//System.out.println("gr, try to " + this.currentInput.sourceGraph.getNodes());
				passed++;
				this.defaultOutputPipe.queue(result);
			//	System.out.println("gr, queueud " + this.currentInput.sourceGraph.getNodes());
			} catch (InterruptedException e) {
				//System.out.println("gr, interrupted " + this.currentInput.sourceGraph.getNodes());
				this.running = false;
			}
		}
	}
	
	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "StructuralGraphRuleFilter";	
		this.printDebug = this.getOption("printDebug");
	}

}
