package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.OptimizedSubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

public class GenerateSubgraphFilter
		extends FilterSkeleton<Pair<Graph, GraphRule>, Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>>> {

	@ResultDictEntry(entryName = "number of generated subgraphs")
	private long graphs = 0l;
	private long graphsPair = 0l;

	private boolean printDebug;

	public void produce() {
		if (printDebug) {
			this.println(">>> DEBUG >>> GenerateSubgraphFilter >>> generating subgraphs for pair: ("
					+ this.currentInput.first + ";" + this.currentInput.second + ") ....\n\t currently generated: "
					+ this.graphs);
		}
		if (this.currentInput != null) {
			if (printDebug) {
				graphsPair = 0l;
			}
			Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>> outputSpecial = new Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>>(
					this.currentInput, new HashSet<AnnotatedElem>());
			graphs++;
			graphsPair++;
			try {
				this.defaultOutputPipe.queue(outputSpecial);
				// this.running =
				// this.getFilterDispatcher().isContinueComputation();
			} catch (InterruptedException e) {
				this.running = false;
			}
			// for (Iterator<Set<AnnotatedElem>> sgIter = new
			// SubgraphIterator(this.currentInput.second.getRight(),true);
			// this.running && sgIter.hasNext(); ) {
			for (Iterator<Set<AnnotatedElem>> sgIter = new OptimizedSubgraphIterator(
					this.currentInput.second.getRight(), true, this.currentInput.first); this.running
							&& sgIter.hasNext();) {
				Set<AnnotatedElem> nextSubGraph = sgIter.next();
				// this is new. we queue only node-only subgraphs, combinations
				// of edges are done individually
				// in the MatchSubgraphFilter to improve performance
				((OptimizedSubgraphIterator) sgIter).skip(); // this skips to
																// the next
																// subgraph
																// consisting of
																// nodes only
				if (nextSubGraph != null) {
					Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>> output = new Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>>(
							this.currentInput, nextSubGraph);
					graphs++;
					graphsPair++;
					if (printDebug) {
						this.println(">>> DEBUG >>> GenerateSubgraphFilter >>> generating " + graphsPair
								+ "th subgraph for pair: (" + this.currentInput.first + ";" + this.currentInput.second
								+ ")");
					}
					try {
						this.defaultOutputPipe.queue(output);
						this.running = this.getFilterDispatcher().isContinueComputation();
					} catch (InterruptedException e) {
						this.running = false;
					}
				}
			}
		}
	}

	@Override
	protected void shutDown() {
		super.shutDown();
		if (printDebug)
			this.println(">>> DEBUG >>> GenerateSubgraphFilter >>> number of generated subgraphs: " + graphs);
	}

	@Override
	protected void initFilter() {
		super.initFilter();
		// this.filterName = "GenerateSubgraphFilter"; //$NON-NLS-1$
		this.printDebug = this.getOption("printDebug");
	}
}
