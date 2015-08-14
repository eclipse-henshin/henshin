package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.BitSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class MatchSubgraphFilter
		extends
		FilterSkeleton<Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>>, Pair<Pair<Graph, GraphRule>, Match>> {

	private IsomorphicPartMatcher ipm;

	private boolean printDebug;
	
	@ResultDictEntry(entryName="Number of read items")
	private int readItems = 0;
	
	@ResultDictEntry(entryName="Number of generated subgraphs")
	private int wroteItems = 0;

	
	public void produce() {		
		if (this.currentInput.second.size() == 0) {
			if (this.currentInput.first.first.eClass() == SamrulesPackage.eINSTANCE.getRuleGraph() && ((RuleGraph) this.currentInput.first.first).getCondition() != null) {
				// disjoint case
				try {
					this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph, GraphRule>, Match>(this.currentInput.first, SamtraceFactory.eINSTANCE.createMatch()));					
				} catch (InterruptedException e) {
					this.running = false;					
				}
			}
		} else {
			this.ipm.setCurrentSubGraph(this.currentInput.second);
			this.ipm.reset();
			Match gm = this.ipm.getNextMatching();
			while (running && gm != null) {
				// assuming that the node-only optimization is used:
				// this loops over matches of the current node-only subgraph
				// and for each match should generate all possible combinations
				// of matches including edges.
				// Thus, we find matching edges for as much edges as possible and then build the Cartesian product.
				
				// Another thing. In the future, we should think about generating not all combinations
				// but only those leading to a possible counterexample. Not sure yet how this can be determined in advance.
				
				// Even more general idea: Do not investigate subgraphs but match all nodes individually.
				// Then create all subgraphs with all possible matchings. Repetition of matches would not be
				// necessary - investigate whether this is a performance/complexity gain.
				// Then do the same with all edges. Right now, edges are matched multiple times
				// as they occur in multiple subgraphs. With the new approach, each edge would only be matched once.
				// The validity of the edge for a specific subgraph then depends on the nodes in the subgraph
				// and their matches.
				
				// added
				try {
					this.wroteItems++;
					this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph, GraphRule>, Match>(this.currentInput.first, gm.copy()));													
				} catch (InterruptedException e) {
					this.running = false;
				}
				Match edgeMatching = SamtraceFactory.eINSTANCE.createMatch();
				for (Edge e : this.currentInput.first.second.getRight().getEdges()) {
					Node srcInHost = gm.getNodeMatching().get(e.getSource());
					Node tarInHost = gm.getNodeMatching().get(e.getTarget());
					if (srcInHost != null && tarInHost != null) {
						// source and target is contained in current subgraph, edge is valid and should be matched
						Edge matchingEdge = null; 
						for (Edge currentEdge : srcInHost.getOutgoing()) {
							if (this.currentInput.first.first.getEdges().contains(currentEdge) && !edgeMatching.getEdgeMatching().containsValue(matchingEdge)) {
								// need to check this because an outgoing or incoming edge might belong to a nac in the host
								if (currentEdge.getTarget() == tarInHost && currentEdge.getInstanceOf() == e.getInstanceOf()) {
									// the edges match. However, there might be another possible matching edge
									// if there are multiple edges of the same type between the nodes.
									// Since such a match would be isomorphic to the current match
									// we do not need to consider it. This would be relevant if there were
									// attributes or identifying names attached to the edges.
									matchingEdge = currentEdge;
									break;
								}
							}
						}
						if (matchingEdge == null) {
							for (Edge currentEdge : srcInHost.getIncoming()) {
								if (this.currentInput.first.first.getEdges().contains(currentEdge) && !edgeMatching.getEdgeMatching().containsValue(matchingEdge)) {
									// need to check this because an outgoing or incoming edge might belong to a nac in the host
									if (currentEdge.getTarget() == srcInHost && currentEdge.getInstanceOf() == e.getInstanceOf() && e.getInstanceOf().getDirection() == EdgeDirection.UNDIRECTED) {
										// undirected edges may have src/tar node reversed
										// not all isomorphic combinations - see above
										matchingEdge = currentEdge;
										break;
									}
								}
							}
						}
						if (matchingEdge != null) {
							edgeMatching.getEdgeMatching().put(e,  matchingEdge);
						}
					}
				}
				
				if (edgeMatching.getEdgeMatching().size() > 0) {
					Edge[] edges = new Edge[edgeMatching.getEdgeMatching().size()];
					int i = 0;
					for (Edge e : edgeMatching.getEdgeMatching().keySet()) {
						edges[i] = e;
						i++;
					}					
					BitSet bs = new BitSet(edges.length);
					boolean finished = false;
					while (bs.cardinality() < edges.length && running) {
						incrementBitSet(bs);
						Match completedMatch = gm.copy();
						for (int j = bs.nextSetBit(0); j >= 0; j = bs.nextSetBit(j+1)) {
							completedMatch.getEdgeMatching().put(edges[j], edgeMatching.getEdgeMatching().get(edges[j]));
						}
						this.wroteItems++;
						try {
							this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph, GraphRule>, Match>(this.currentInput.first, completedMatch));
							running = this.getFilterDispatcher().isContinueComputation();								
						} catch (InterruptedException e) {
							this.running = false;
						}
					}
				}
				gm = this.ipm.getNextMatching();
				// normal part 
				/*
				this.wroteItems++;
				try {
					this.defaultOutputPipe.queue(new CombinationProducer.Pair<Pair<Graph, GraphRule>, Match>(this.currentInput.first, gm));
					running = this.getFilterDispatcher().isContinueComputation();
					if (running) {
						gm = this.ipm.getNextMatching();
					}
				} catch (InterruptedException e) {
					this.running = false;
				}*/
			}
		}
	}

	
	private void incrementBitSet(final BitSet bs) {		
		final int firstClear = bs.nextClearBit(0);
		bs.set(firstClear);
		for (int i = firstClear - 1; i >= 0; i--) {
			bs.clear(i);
		}
	}
	
	
	
	
	
	public void consume() {
		try {
			final Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>> newPair = this.defaultInputPipe
				.dequeue();
			this.readItems++;
			if (this.currentInput == null || newPair.first != this.currentInput.first) {
				this.ipm.setHostGraph(newPair.first.first);
				this.ipm.setPattern(newPair.first.second.getRight());
			}
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
		//this.filterName = "MatchSubgraphFilter"; //$NON-NLS-1$
		this.ipm = new IsomorphicPartMatcher();
		this.printDebug = this.getOption("printDebug");
	}
}
