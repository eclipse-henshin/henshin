package org.eclipse.emf.henshin.sam.invcheck.filter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckerUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.algorithm.IsomorphicPartMatcher;
import org.eclipse.emf.henshin.sam.invcheck.filter.CombinationProducer.Pair;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;

public class ContainsGraphRuleHelper {

	//@SuppressWarnings("restriction")
	//private static transient final Logger log = Logger.getLogger(ContainsGraphRuleHelper.class);

	private GraphRule[] allRules;

	private boolean printDebug;
	
	public ContainsGraphRuleHelper(GraphRule[] rules) {
		if ( rules == null ) {
			throw new NullPointerException(
			        "null is not an allowed value for constructor parameter!");
		}
		this.allRules = rules;
	}


	/**
	 * Looks in the given <code>Graph</code> which of the
	 * <code>GraphRules</code> known by this
	 * <code>ContainsGraphRuleHelper</code> instance are contained within it.
	 * <br/> This method returns for each urgent <code>GraphRule</code> (i.e.
	 * <code>GraphRule.isUrgent() == true</code>) all mappings of the
	 * <code>GraphRule</code> within the given <code>Graph</code>.
	 * 
	 * @param graph
	 *            the graph to check for contained urgen transitions
	 * @return a collection of <code>GraphRules</code> and mappings to the
	 *         given <code>Graph</code>
	 */
	public Collection<Pair<GraphRule, Collection<Match>>> getUrgentTransitions(
	        Graph graph) {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(graph);
		Collection<Pair<GraphRule, Collection<Match>>> result =
		        new HashSet<Pair<GraphRule, Collection<Match>>>();
		for ( int index = 0; index < this.allRules.length; index++ ) {
			GraphRule currentRule = this.allRules[index];
			if ( currentRule.isUrgent() ) {
				Graph tmpGraph = currentRule.getLeft();
				
				Set<AnnotatedElem> subGraph = SubgraphIterator.graphToSubGraph(tmpGraph);
				        
				ipm.setPattern(tmpGraph);
				ipm.setCurrentSubGraph(subGraph);
				
				Collection<Match> tmp = ipm.findAllMatchings();
				Vector<Match> col = new Vector<Match>();
				for (Match m : tmp) {
					if (checkRuleMatchingApplicability(m)) {
						col.add(m);
					}
				}				
				
				if ( col != null && !col.isEmpty() ) {
					Pair<GraphRule, Collection<Match>> pair =
					        new Pair<GraphRule, Collection<Match>>(
					                currentRule, col);
					result.add(pair);
				}
			}
		}
		return result;
	}
	
	public Map<GraphRule,Collection<Match>> findAllMatchingRules(final int priority, Graph graph) {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(graph);
		Map<GraphRule,Collection<Match>> result = new HashMap<GraphRule, Collection<Match>>();
		for (int index = 0; index < this.allRules.length; index++) {
			ipm.reset();
			GraphRule currentRule = allRules[index];
			if (currentRule.getPriority() > priority) {
				Graph tmpGraph = currentRule.getLeft();
				ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(tmpGraph));
				ipm.setPattern(tmpGraph);
				
				Collection<Match> tmp = ipm.findAllMatchings();
				Vector<Match> col = new Vector<Match>();
				for (Match m : tmp) {
					if (checkRuleMatchingApplicability(m)) {
						col.add(m);
					}
				}				
				
				if (col != null && col.size() > 0) {
					result.put(currentRule, col);
				}
			}
		}
		return result;
	}
	
	/**
	 * Unfortunately, the gluing condition needs to hold for the pattern, i.e. all graphs satisfying the pattern.
	 * Therefore we cannot just check the condition for nodes from the pattern.
	 * At the moment, rules are only considered applicable if they do not contain deleted nodes.
	 * In the future, this check can be extended by searching for nacs enforcing the gluing condition
	 * and by including information from the type graph.
	 * 
	 * @param matching
	 * @return
	 */
	public boolean checkRuleMatchingApplicability(Match matching) {
		for (Node ruleN : matching.getNodeMatching().keySet()) {
			if (ruleN.eClass() == SamrulesPackage.eINSTANCE.getDeletedNode()) {
				return false;
				/*
				Node hostGraphNode = matching.getNodeMatching().get(ruleN);
				for (Edge e : hostGraphNode.getIncoming()) {
					if (!InvariantCheckingUtil.isNegated(e) && !matching.getEdgeMatching().containsValue(e)) {
						return false;
					}
				}
				for (Edge e : hostGraphNode.getOutgoing()) {
					if (!InvariantCheckingUtil.isNegated(e) && !matching.getEdgeMatching().containsValue(e)) {
						return false;
					}
				}*/
			}
		}
		return true;
	}
	
	public boolean findMatchingRule(final int priority, Graph graph) {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(graph);		
		for (int index = 0; index < this.allRules.length; index++) {
			ipm.reset();
			GraphRule currentRule = allRules[index];
			if (currentRule.getPriority() > priority) {
				Graph tmpGraph = currentRule.getLeft();
				ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(tmpGraph));
				ipm.setPattern(tmpGraph);
				Match currentMatch = ipm.getNextMatching();
				while (currentMatch != null) {
					if (checkRuleMatchingApplicability(currentMatch)) {
						return true;
					} else {
						currentMatch = ipm.getNextMatching();
					}
				}				
			}
		}
		return false;
	}

}
