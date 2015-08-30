package org.eclipse.emf.henshin.sam.invcheck.filter;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.GraphMerger;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.RuleApplication;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

/**
 * merges right hand side of GraphRule and a PropertyGraph, performs a reverse
 * rule application on the merged Graph and last checks if the transition from
 * source- to targetgraph is applicable
 * 
 * @author bb
 * 
 */
public class GraphMergeFilter
		extends
		//FilterSkeleton<Pair<Pair<PropertyGraph, GraphRule>, Match>, GraphVerificationData> {
		FilterSkeleton<Pair<Pair<Graph, GraphRule>, Match>, Pair<Pair<Graph, GraphRule>, Pair<Match, RuleGraph>>> {
			
	private GraphMerger graphMerger = new GraphMerger();

	private boolean printDebug; 
	
	@ResultDictEntry(entryName="Number of merged graphs")
	private long graphs = 0l;
	// private SimpleGraphMatcher graphMatcher = new SimpleGraphMatcher();

	private RuleApplication ruleApplication = new RuleApplication();

	@ResultDictEntry(entryName="Number of correct merged graphs")
	private int wroteItems = 0;

	public void produce() {
		graphMerger.reset();
		// graphMatcher.setCheckRuleApplication(true);
		// graphMatcher.setRule(this.currentPair.first.second.getLeftRuleSide());
		
		if (printDebug) {
			this.println(">>> DEBUG >>> GraphMergeFilter >>> merging together pair: ("+this.currentInput.first.first+";"+this.currentInput.first.second.getName()+")\n\t number of matchings to merge: "+this.currentInput.second.getEdgeMatching().size() + this.currentInput.second.getNodeMatching().size());
		}
		boolean continueLoop;
		try {			
			RuleGraph targetGraph = graphMerger.merge(this.currentInput.first.first, this.currentInput.first.second.getRight(),	this.currentInput.second);
			
			this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph,GraphRule>, Pair<Match,RuleGraph>>(this.currentInput.first, new CombinationProducer.Pair<Match, RuleGraph>(this.currentInput.second, targetGraph)));
			wroteItems++;
			return;
			

		} catch (InterruptedException ie) {
			this.running = false;
		}
	}

	@Override
	protected void shutDown() {
		super.shutDown();
		//this.println("number of struct checks: " + graphs);
	}
	
	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "GraphMergeFilter"; //$NON-NLS-1$
		this.printDebug = this.getOption("printDebug");		
	}
}
