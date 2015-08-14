package org.eclipse.emf.henshin.sam.invcheck.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;

/**
 * Merges two graphs and a graph matching to a merged graph.
 * The typical inputs are a forbidden subgraph, a part of the right side of a graph rule and their matching. 
 *  
 * The following diagram shows the behaviour of the merge method.
 * 
 * <p><img src="../../../../../doc/diagrams/graphMerger.png" alt="Graph merger"></p>
 *
 */
public class GraphMerger implements AlgorithmComponent
{
	
	private Map<Graph, Map<AnnotatedElem, AnnotatedElem>> refItems = new HashMap<Graph, Map<AnnotatedElem, AnnotatedElem>>();
	//private Map<Graph, Map<AnnotatedElem, AnnotatedElem>> inverseSameInRuleProp = new HashMap<Graph, Map<AnnotatedElem, AnnotatedElem>>();

   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param forbiddenSubGraph  No description provided
    * @param rule       No description provided
    * @param matching   No description provided
    * @return           No description provided
    */
   public RuleGraph merge (Graph forbiddenSubGraph, Graph rule, Match matching)
   {
	  refItems.put(forbiddenSubGraph, new HashMap<AnnotatedElem, AnnotatedElem>());
	  refItems.put(rule, new HashMap<AnnotatedElem, AnnotatedElem>());
      RuleGraph result = null;
      if (forbiddenSubGraph != null && rule != null && matching != null)
      {
         result = SamrulesFactory.eINSTANCE.createRuleGraph();//new Graph();
         result.setTypedOver(rule.getTypedOver());
         copyItems (forbiddenSubGraph, result, true);
         createAssocs (forbiddenSubGraph, result);
         /*
          *  this for loop ensures that items mentioned by the given matching, won't be copied twice.
          */
         for (Iterator<Map.Entry<Node, Node>> iter = matching.getNodeMatching().iterator(); iter.hasNext(); )
         {
            Map.Entry<Node, Node> tmpEntry = iter.next();
            Node ruleNode = tmpEntry.getKey();
            Node resultNode = (Node) refItems.get(forbiddenSubGraph).get(tmpEntry.getValue());
            assert resultNode != null;
            refItems.get(rule).put(ruleNode, resultNode);
            ((PatternNode) resultNode).setSameInRule(ruleNode);
         }
         for (Iterator<Map.Entry<Edge, Edge>> iter = matching.getEdgeMatching().iterator(); iter.hasNext(); ) {
        	 Map.Entry<Edge, Edge> tmpEntry = iter.next();
        	 Edge ruleEdge = tmpEntry.getKey();
        	 Edge resultEdge = (Edge) refItems.get(forbiddenSubGraph).get(tmpEntry.getValue());
        	 assert resultEdge != null;
        	 refItems.get(rule).put(ruleEdge, resultEdge);
        	 ((PatternEdge) resultEdge).setSameInRule(ruleEdge);
         }
         copyItems (rule, result, false);
         createAssocs (rule, result);
      }
      return result;
   }

   /**
    * copies all graph items from srcGraph to resultGraph. thereby the qualified assocs for copies of
    * elements are set.
    *
    * @param srcGraph
    * @param resultGraph
    */
   private void copyItems (Graph srcGraph, Graph resultGraph, boolean isForbidden)
   {
      for (Iterator<Node> iter = srcGraph.getNodes().iterator(); iter.hasNext(); )
      {
         final Node srcNode = iter.next();
         if (!refItems.get(srcGraph).containsKey(srcNode))
         {        	 
        	PatternNode resultNode = InvariantCheckingUtil.copyAsPattern(srcNode);
        	 
            resultGraph.getNodes().add(resultNode);
            refItems.get(srcGraph).put(srcNode, resultNode);
            if (isForbidden) {
            	resultNode.setSameInProp(srcNode);
            } else {
            	resultNode.setSameInRule(srcNode);
            }            
         }
      }
      for (Iterator<Edge> iter = srcGraph.getEdges().iterator(); iter.hasNext(); )
      {
         final Edge srcEdge = iter.next();
         if (!refItems.get(srcGraph).containsKey(srcEdge))
         {
        	PatternEdge resultEdge = InvariantCheckingUtil.copyAsPattern(srcEdge);
            resultGraph.getEdges().add(resultEdge);
            refItems.get(srcGraph).put(srcEdge, resultEdge);
            if (isForbidden) {
            	resultEdge.setSameInProp(srcEdge);
            } else {
            	resultEdge.setSameInRule(srcEdge);
            }
         }
      }
   }


   /**
    * creates the assocs between the nodes and edges of srcGraph between the elements of resultGraph.
    * to make this work, all elements have be copied first before calling this method
    *
    * @param srcGraph     the source Graph
    * @param resultGraph  the resulting graph containing then the assoc from srcGraph
    */
   private void createAssocs (Graph srcGraph, Graph resultGraph)
   {
      for (Iterator<Edge> iter = srcGraph.getEdges().iterator(); iter.hasNext(); )
      {
         final Edge srcEdge = iter.next();
         Node srcTargetNode = srcEdge.getTarget();
         Node srcSourceNode = srcEdge.getSource();
         assert srcTargetNode != null : srcEdge + ": sourceNode equals null!";
         assert srcSourceNode != null : srcEdge + ": targetNode quals null!";

         Edge resultEdge = (Edge) refItems.get(srcGraph).get(srcEdge);
         Node resultTargetNode = (Node) refItems.get(srcGraph).get(srcTargetNode);
         Node resultSourceNode = (Node) refItems.get(srcGraph).get(srcSourceNode);
         
         resultEdge.setSource (resultSourceNode);
         resultEdge.setTarget (resultTargetNode);
      }
   }
   
   public void reset(){
	   refItems = new HashMap<Graph, Map<AnnotatedElem, AnnotatedElem>>();
   }

}

/*
 * $Log$
 * Revision 1.4  2007/06/25 17:48:20  basilb
 * fixed a lot of these bugs testing does not find but evaluating ;)
 *
 * Revision 1.3  2007/01/11 14:44:23  basilb
 * even more usage of generics
 *
 * Revision 1.2  2007/01/03 09:27:48  basilb
 * removed compile errors caused by wrong import declarations; introduced empty plugin class to ensure correct loading
 *
 */
