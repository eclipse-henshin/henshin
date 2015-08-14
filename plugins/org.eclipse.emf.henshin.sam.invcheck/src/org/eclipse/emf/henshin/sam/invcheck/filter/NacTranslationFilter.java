package org.eclipse.emf.henshin.sam.invcheck.filter;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.NACTranslator;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class NacTranslationFilter extends FilterSkeleton<Pair<Pair<Graph, GraphRule>, Pair<Match, RuleGraph>>, Pair<Pair<Graph, GraphRule>, RuleGraph>> {
	
	@ResultDictEntry(entryName="checked")
	private int checked = 0;
	
	@ResultDictEntry(entryName="discarded")
	private int discarded = 0;
	
	@ResultDictEntry(entryName="noNacs")
	private int noNacs = 0;
	
	private NACTranslator nacT;
	
	private boolean printDebug;
	
	public void produce() {		
		try {/*
			if (true) {
				this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph, GraphRule>, RuleGraph>(this.currentInput.first, this.currentInput.second.second));
				return;
			}*/
			checked++;
			nacT.reset();
			if (checked % 200 == 0) {
			System.out.println("nt " + this.currentInput.first.second.getName() + " " + ((NegatedCondition) this.currentInput.first.first.eContainer().eContainer()).getName());
			}
			//System.out.println(i);
			//RuleGraph result = null;
			RuleGraph result = (RuleGraph) nacT.partialTranslate(this.currentInput.first.second);
			//RuleGraph result = this.currentInput.second.second;
			//RuleGraph result = (RuleGraph) nacT.translate();
			System.out.println("translated");
			//RuleGraph result = null;
			if (result != null) {
				/*if (result.getCondition() instanceof LogicalGCCoupling) {
					System.out.println("conditions: " + ((LogicalGCCoupling) result.getCondition()).getOperands().size());
					noNacs = noNacs + ((LogicalGCCoupling) result.getCondition()).getOperands().size();
				} else {
					System.out.println(1);
					noNacs++;
				}*/
				//if (!true) {
					this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph, GraphRule>, RuleGraph>(this.currentInput.first, result));
				//}
			} else {
				discarded++;
				return;
			}
		} catch (InterruptedException ie) {
			this.running = false;
		}
		
	}
	
	
	
	public void consume() {
		try {
			Pair<Pair<Graph, GraphRule>, Pair<Match, RuleGraph>> newPair = this.defaultInputPipe
				.dequeue();
			
			Graph pattern = newPair.first.first;
			RuleGraph mergedGraph = newPair.second.second;			
			
			Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();			
			
			for (Edge e : pattern.getEdges()) {
				if (!InvariantCheckingUtil.isNegated(e)) {
					for (Edge m : mergedGraph.getEdges()) {
						if (((PatternEdge) m).getSameInProp() == e) {
							initialMatching.getEdgeMatching().put(e, m);							
						}
					}										
				}
			}
			for (Node n : pattern.getNodes()) {
				if (!InvariantCheckingUtil.isNegated(n)) {
					for (Node m : mergedGraph.getNodes()) {
						if (((PatternNode) m).getSameInProp() == n) {
							initialMatching.getNodeMatching().put(n, m);
							break;
						}
					}					
				}
			}
			
			this.nacT.setMergedGraph(mergedGraph);
			this.nacT.setPattern(pattern);
			this.nacT.setInitialMatching(initialMatching);
			
			this.currentInput = newPair; 
		} catch (IllegalStateException ise) {
			this.running = false;
		} catch (InterruptedException ie) {
			this.running = false;
		}
	}
	
	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "NACTranslationFilter";
		this.nacT = new NACTranslator();
		this.printDebug = this.getOption("printDebug");
	}	
}
