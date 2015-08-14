package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartPartialMatcher;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;

/**
 * This helper class contains method that can be used to check if a given
 * <code>Graph</code> contains a <code>Graph</code> or not. <br/>
 * The class' visibilty is set to <code>package</code> because this class is
 * not intended to be used by other classes then those belonging to this
 * pacakge, too.
 * 
 * @author basilb
 */
public class ContainsPropertyHelper {

	/**
	 * log4j logging
	 */
	//private final static transient Logger log = Logger
	//        .getLogger(ContainsPropertyHelper.class);


	/**
	 * Creates a <code>ContainsPropertyHelper</code> that knows the
	 * <code>Graphs</code> given as parameter.
	 * 
	 * @param props
	 *            the <code>Graphs</code> for this
	 *            <code>ContainsPropertyHelper</code> instance.
	 */
	public ContainsPropertyHelper(Graph[] props) {
		StructuralPropertyFilter.resetNacDiscard();
		if ( props == null ) {
			throw new NullPointerException(
			        "null as constructor argument is not permitted");
		}
		this.properties = props;
	}

	/**
	 * An array of <code>Graphs</code> this
	 * <code>ContainsPropertyHelper</code> knows.
	 */
	private Graph[] properties;


	//private IsomorphicPartMatcher ipmInstance;
	private IsomorphicPartPartialMatcher ipmInstance;
	
	private void initIPM(Graph sourceGraph) {
		this.ipmInstance = new IsomorphicPartPartialMatcher();
		//this.ipmInstance = new IsomorphicPartMatcher();
		// new:		
		this.ipmInstance.setHostGraph(sourceGraph);
	}
	
	private Collection<Match> findAllOccurences(Graph p) {
		Set<AnnotatedElem> subGraph = SubgraphIterator.graphToSubGraph(p);
		this.ipmInstance.setCurrentSubGraph(subGraph);
		this.ipmInstance.setPattern(p);
		return this.ipmInstance.findAllMatchings();
	}
	
	public Collection<Pair<Graph, Collection<Match>>> allContainedProperties(
	        Graph sourceGraph) {
		this.initIPM(sourceGraph);
		Collection<Pair<Graph, Collection<Match>>> result =
		        new HashSet<Pair<Graph, Collection<Match>>>();
		for ( int i = 0; i < this.properties.length; i++ ) {
			Collection<Match> col =
			        this.findAllOccurences(this.properties[i]);
			if ( col != null && !col.isEmpty() ) {
				Pair<Graph, Collection<Match>> pair =
				        new Pair<Graph, Collection<Match>>(
				                this.properties[i], col);
				result.add(pair);
			}
		}
		return result;
	}
	
	public boolean findProperties(Graph sourceGraph) {
		this.initIPM(sourceGraph);
		for (Graph prop : this.properties) {
			//System.out.println("trying: " + ((NegatedCondition) prop.eContainer().eContainer()).getName());
			Set<AnnotatedElem> subGraph = SubgraphIterator.graphToSubGraph(prop);
			this.ipmInstance.reset();
			this.ipmInstance.setCurrentSubGraph(subGraph);
			this.ipmInstance.setPattern(prop);			
			if (this.ipmInstance.getNextMatching() != null) {
				if (SamrulesPackage.eINSTANCE.getRuleGraph().isSuperTypeOf(prop.eClass()) && ((RuleGraph) prop).getCondition() != null) {
					//StructuralPropertyFilter.incrementNacDiscard();
					//System.out.println("discarded by: " + ((NegatedCondition) prop.eContainer().eContainer()).getName());
				}
				return true;
			}
		}
		return false;
	}
	
	
	// new
	public boolean findPreservedProperties(Graph sourceGraph) {
		
		Graph preservedSubgraph = SamrulesFactory.eINSTANCE.createRuleGraph();
		Match m = SamtraceFactory.eINSTANCE.createMatch();
		for (Node n : sourceGraph.getNodes()) {
			if (((PatternNode) n).getSameInRule() == null || ((PatternNode) n).getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
				Node newNode = InvariantCheckingUtil.copyAsPattern(n);
				preservedSubgraph.getNodes().add(newNode);
				m.getNodeMatching().put(n, newNode);
			} else if (((PatternNode) n).getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getCreatedNode()) {
				for (Edge out : n.getOutgoing()) {
					PatternEdge e = (PatternEdge) out;
					if (e.getSameInRule() == null || e.getSameInRule().eClass() != SamrulesPackage.eINSTANCE.getCreatedEdge()) {
						return true;
					}
				}
				for (Edge in : n.getIncoming()) {
					PatternEdge e = (PatternEdge) in;
					if (e.getSameInRule() == null || e.getSameInRule().eClass() != SamrulesPackage.eINSTANCE.getCreatedEdge()) {
						return true;
					}
				}
			}
		}
		for (Edge e : sourceGraph.getEdges()) {
			if (((PatternEdge) e).getSameInRule() == null || ((PatternEdge) e).getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getPreservedEdge()) {
				Edge newEdge = InvariantCheckingUtil.copyAsPattern(e);
				newEdge.setSource(m.getNodeMatching().get(e.getSource()));
				newEdge.setTarget(m.getNodeMatching().get(e.getTarget()));
				preservedSubgraph.getEdges().add(newEdge);				
				m.getEdgeMatching().put(e, newEdge);
			}
		}
		
		this.initIPM(preservedSubgraph);
		for (Graph prop : this.properties) {
			Set<AnnotatedElem> subGraph = SubgraphIterator.graphToSubGraph(prop);
			this.ipmInstance.reset();
			this.ipmInstance.setCurrentSubGraph(subGraph);
			this.ipmInstance.setPattern(prop);			
			if (this.ipmInstance.getNextMatching() != null) {
				return true;
			}
		}
		return false;
	}
}
