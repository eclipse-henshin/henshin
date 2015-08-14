
package org.eclipse.emf.henshin.sam.invcheck.filter;

import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.RuleApplication;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class RuleApplicationFilter extends FilterSkeleton<Pair<Pair<Graph, GraphRule>, Graph>, GraphVerificationData> {

	private RuleApplication ruleApplication = new RuleApplication();
	
	@ResultDictEntry(entryName="checked")
	private int checked = 0;
	
	@ResultDictEntry(entryName="discarded")
	private int discarded = 0;
	
	private boolean printDebug;
	
	private boolean skip = true;
	
	public void produce() {
		checked++;
		try {
			ruleApplication.reset();
			Graph targetGraph = this.currentInput.second;			
			
				Graph sourceGraph = ruleApplication.reverseRuleApplication(targetGraph, this.currentInput.first.second);
				if (sourceGraph != null) {
					//if (ruleApplication.checkCorrectRuleApplication(this.currentInput.first.second.getLeft(), sourceGraph)) {
					//this.wroteItems++;
					//this.currentInput.second.clear();
					
					// this removes all nacs from the right sidfe to save memory
					/*
					for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(targetGraph).getNacs()) {						
						for (Edge e : nac.getEdges()) {
							e.setSource(null);
							e.setTarget(null);
						}
					}
					if (SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(targetGraph.eClass())) {
						((RuleGraph) targetGraph).setCondition(null);
					}*/
					GraphVerificationData newGVD = new GraphVerificationData(this.currentInput.first, sourceGraph, targetGraph);
					//GraphVerificationData newGVD = new GraphVerificationData(this.currentInput.first, targetGraph, targetGraph);
						
						
						this.defaultOutputPipe.queue(newGVD);
					//}
				} else {
					discarded++;
				}
			//}
		} catch (InterruptedException ie) {
			this.running = false;
		}		
	}
	
	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "RuleApplicationFilter";		
		this.printDebug = this.getOption("printDebug");
	}
	
	
	
}
