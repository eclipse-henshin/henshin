package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.model.samgraph.Attribute;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;

/**
 * This is a DataType only. During verification it is used after the
 * GraphMerging succeeded. This object stores the <code>Pair</code> containig
 * the originating {@link Graph} and {@link GraphRule} and additional
 * two {@link Graph}s for the source- and the targetGraph. If used during
 * verification of a hybrid graph transformation system there could be
 * additional information put into this class' public fields
 * {@link #GraphMappings} and {@link #urgentTransitionMappings}.
 * 
 * @author basilb
 */
public class GraphVerificationData {

	public boolean bla = false;
	
	public void setBla() {
		bla = true;
	}
	
	/**
	 * The pair of <code>Graph</code> and <code>GraphRule</code>
	 * that is the source for {@link #targetGraph}.
	 */
	public Pair<Graph, GraphRule> pair;

	/**
	 * a <code>Graph</code> that is the result of merging a
	 * <code>Graph</code> and a <code>GraphRule</code>
	 */
	public Graph targetGraph;

	/**
	 * the sourceGraph is the result of performing a reverse rule application of
	 * the {@linkplain #pair GraphRule} to the {@link #targetGraph}
	 */
	public Graph sourceGraph;

	/**
	 * this <code>Map</code> stores for a <code>Graph</code> its
	 * partial isomorphisms to the {@link #sourceGraph}
	 */
	public Map<Graph, Collection<Match>> propertyGraphMappings;

	/**
	 * this <code>Map</code> stores for a <code>GraphRule</code> that has to
	 * be urgent its partial isomorphism to the {@link #targetGraph};
	 * 
	 * @see GraphRule#isUrgent()
	 */
	public Map<GraphRule, Collection<Match>> urgentTransitionMappings;

	/**
	 * this <code>Map</code> contains these <code>GraphRules</code> that
	 * could be applicable in the {@link #sourceGraph}. It is important that
	 * the priorities of the <code>GraphRules</code> stored in this map are
	 * greater than the <code>GraphRule</code>'s pirority stored in the
	 * {@link #pair}. During verification of a discrete {@link #pair} this
	 * field won't be used.
	 * 
	 * @see GraphRule#getPriority()
	 */
	public Map<GraphRule, Collection<Match>> applicableGraphRules;

	/**
	 * In case of a hybrid verification run, the values of each
	 * <code>AttributeInstance</code> involved in the Mixed Integer Program
	 * will be stored in this <code>Map</code>
	 */
	public Map<Attribute, Double> attributeValues;

	/**
	 * this constructor is a shorthand for creating a
	 * <code>GraphVerificationData</code> instance with {@link #targetGraph},
	 * {@link #sourceGraph} and {@link #pair} set, already.
	 * 
	 * @param cPair
	 *            this <code>GraphVerificationData</code>'s <code>Pair</code>
	 * @param source
	 *            this <code>GraphVerificationData</code>'s
	 *            <code>sourceGraph</code>
	 * @param target
	 *            this <code>GraphVerificationData</code>'s </code>targetGraph</code>
	 */
	public GraphVerificationData(Pair<Graph, GraphRule> cPair,
			Graph source, Graph target) {
		this.pair = cPair;
		this.sourceGraph = source;
		this.targetGraph = target;
	}

}
