package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerPlugin;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.paf.FilterSkeleton;
import org.eclipse.emf.henshin.sam.paf.annotation.ResultDictEntry;

/**
 * This Filter tests whether the subgraph (modelled as a {@link Set} containing <code>Node</code> and
 * <code>Edge</code> objects) contained in the inputPair could be matched on the inputPairs's {@link Graph}. 
 * Therefore the <code>NotMatchableSubgraphFilter</code> does not perform a complete graphmatching but it compares the 
 * <code>Node.getType()</code> quantities in the subgraph and the <code>Graph</code>.
 * 
 *   At the moment this <code>IFilter</code> implementation only makes use of exactly one input and one output <code>IPipe</code>.
 *   These were identitfied by the static fields <code>DEFAULT_INPUT_KEY</code> and <code>DEFAULT_OUTPUT_KEY</code>. Any other
 *   connected pipe won't be read from or written to!
 * @author basilb
 * @deprecated
 *
 */

public class NotMatchableSubgraphFilter
		extends
		FilterSkeleton<Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>>, Pair<Pair<Graph, GraphRule>, Set<AnnotatedElem>>> {

	@ResultDictEntry(entryName="Number of matchable subgraphs")
	private long wroteItems = 0;

	@ResultDictEntry(entryName="Number of not matchable subgraphs")
	private long droppedItems = 0;
	
	static {
		String debugOption = Platform.getDebugOption("de.uni_paderborn.invariantchecking.core/filter/NotMatchableSubgraphFilter/printDebug"); //$NON-NLS-1$
		printDebug = debugOption != null ? debugOption.equals("true") : false; //$NON-NLS-1$
	}
	
	private final static boolean printDebug;
	
	public void produce() {
		if (this.compareTypes(this.currentInput.second,
				this.currentInput.first.first)) {
			this.wroteItems++;
			try {
				this.defaultOutputPipe.queue(this.currentInput);
			} catch (InterruptedException e) {
				this.running = false;
			}
		} else {
			this.currentInput.second.clear();
			this.currentInput.second = null;
			this.currentInput.first = null;
			this.currentInput = null;
			this.droppedItems++;
		}
	}
	
	@Override
	protected void shutDown() {
		super.shutDown();
		if (printDebug) {
			this.println(">>> DEBUG >>> NotMatchableSubgraphFilter >>> wrote "+wroteItems+" subgraphs and dropped "+droppedItems+" subgraphs. Sum="+(wroteItems+droppedItems));
		}
	}

	/**
	 * counts occurences of types in the given <code>Set</code>.</br>
	 * This method returns a <code>Map</code> with the nodes' types as keys and an <code>Integer</code>
	 * object storing their frequency of occurence
	 *
	 * @param subgraph  a <code>Set</code> representing a valid subgraph
	 * @return          <code>Map</code> with the frequency of occurence for each type
	 * @deprecated
	 */
	private Map<String, Integer> computeTypesInSubgraph(Set<AnnotatedElem> subgraph) {
		Map<String, Integer> m = new HashMap<String, Integer>();
		if (subgraph != null) {
			for (Iterator<AnnotatedElem> iter = subgraph.iterator(); iter.hasNext();) {
				AnnotatedElem next = iter.next();
				if (next instanceof Node) {
					Node node = (Node) next;
					/*Integer i = m.get(node.getType());
					if (i != null) {
						m.put(node.getType(), new Integer(i.intValue() + 1));
					} else {
						m.put(node.getType(), new Integer(1));
					}*/
				}
			}
		}
		return m;
	}

	/**
	 * In a first step this method computes a <code>Map</code> that contains the frequency of types in
	 * the <code>Set</code> s.  Afterwards each entry in this <code>Map</code> gets compared to the
	 * corresponding value of the given <code>Graph</code>. Contains the <code>Set</code> more elements
	 * of a type then the <code>Graph</code> the method returns false. In this case there is no need to compute
	 * all matchings, as none exists.
	 *
	 * @param s
	 * @param g
	 * @return
	 * @deprecated
	 */
	private boolean compareTypes(Set<AnnotatedElem> s, Graph g) {
		if (s != null && g != null) {
			Map<String, Integer> m = computeTypesInSubgraph(s);
			for (Iterator<Map.Entry<String, Integer>> iter = m.entrySet()
					.iterator(); iter.hasNext();) {
				Map.Entry<String, Integer> entry = iter.next();
				Integer i = entry.getValue();
				/*if (i.intValue() > g.getNumberOfType(entry.getKey())) {
					return false;
				}*/
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected void initFilter() {
		super.initFilter();
		this.filterName = "NotMatchableSubgraphfilter"; //$NON-NLS-1$
	}
}
