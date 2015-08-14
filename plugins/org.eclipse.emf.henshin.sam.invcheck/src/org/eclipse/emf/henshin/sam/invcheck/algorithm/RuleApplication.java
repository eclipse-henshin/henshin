package org.eclipse.emf.henshin.sam.invcheck.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingCore;
import org.eclipse.emf.henshin.sam.invcheck.InvariantCheckingUtil;
import org.eclipse.emf.henshin.sam.invcheck.SubgraphIterator;
import org.eclipse.emf.henshin.sam.invcheck.adapter.SamGraphInvCheckGraphAdapter;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationFactory;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;

/**
 * Reversely applies a graph rule to a graph (typically a target graph pattern) to build the source graph (typically a source graph pattern).
 * When (reversely) applying the rule, NACs from the left side of the rule and NACs from the target (typically a target graph pattern) are copied to the result.  
 *
 * The following diagram shows the behaviour of the reverseRuleApplication method.
 * 
 * <p><img src="../../../../../doc/diagrams/ruleApplication.png" alt="Rule application"></p> 
 * 
 */
public class RuleApplication implements AlgorithmComponent
{

	// note: rhs map is sgp -> rhs, lhs map is lhs -> sgp, tgp map is tgp -> sgp
	// or the reverse for forwardRuleApplication
	private Map<Graph, Map<AnnotatedElem, AnnotatedElem>> refItems = new HashMap<Graph, Map<AnnotatedElem, AnnotatedElem>>();

   /**
    * Applies a graphRule or GraphTransformation to a Graph. The rule is applied reversly, this means from the
    * right hand to the left hand side. But you can use this method also to apply a rule in the "correct" way:
    * just switch right and left hand side of the graphrule.
    *
    * @param r         the graphrule to apply
    * @param srcGraph  No description provided
    * @return          g after apllying rule or null if at least one parameter is null
    * 			or the rule is not applicable using the restricted spo
    */
   public Graph reverseRuleApplication (final Graph srcGraph, final GraphRule r)
   {
	  refItems.put(srcGraph, new HashMap<AnnotatedElem, AnnotatedElem>());
      if (srcGraph != null && r != null)
      {         
         Graph rhs = r.getRight();
         Graph lhs = r.getLeft();         

         refItems.put(lhs, new HashMap<AnnotatedElem, AnnotatedElem>());
         refItems.put(rhs, new HashMap<AnnotatedElem, AnnotatedElem>());
         RuleGraph g = this.copyGraph (srcGraph, r, false);         
         //remove all nodes and edges from g, that are created by the rule
         for (Iterator<Node> iter = g.getNodes().iterator(); iter.hasNext(); )
         {
        	 
            Node node = iter.next();
            
            boolean isInRightRuleSide = refItems.get(rhs).get(node) != null;
            boolean isInBothRuleSides = isInRightRuleSide && refItems.get(rhs).get(node).eClass() == SamrulesPackage.eINSTANCE.getPreservedNode();
            if (isInRightRuleSide && !isInBothRuleSides)
            {

               boolean deleteIt = true;
               for (Iterator<Edge> i = node.getOutgoing().iterator(); i.hasNext() && deleteIt; )
               {
            	   
                  Edge e = i.next();
            
                 
                  if (refItems.get(rhs).get(e) == null)
                  {                	  
            
                     deleteIt = false;
                  }
               }
               for (Iterator<Edge> i = node.getIncoming().iterator(); i.hasNext() && deleteIt; )
               {
            	   
                  Edge e = i.next();
            
                  if (refItems.get(rhs).get(e) == null)
                  {                	  
            
                     deleteIt = false;
                  }
               }
               if (deleteIt)
               {
            	  // actually I think the items have to be removed from refItems as well,
            	  // especially from refItems.get(tgp); unfortunately we have to do an inverse
            	  // lookup
            	  Node key = null; // TODO find a better way to remove obsolete items
            	  for (Map.Entry<AnnotatedElem,AnnotatedElem> entry : refItems.get(srcGraph).entrySet()) {
            		  if (entry.getValue() == node) {
            			  key = (Node) entry.getKey();
            			  break;
            		  }
            	  }
            	  refItems.get(srcGraph).remove(key);
            	  refItems.get(lhs).remove(((PatternNode) node).getSameInRule());
            	  refItems.get(rhs).remove(node);
            	  node.getIncoming().clear();
            	  node.getOutgoing().clear();
            	  ((PatternNode) node).setSameInRule(null);
            	  ((PatternNode) node).setSameInProp(null); // manage origin of elements in sgp
            	  iter.remove();
               }
               else
               {            	
                  return null;
               }
            }
            else if (isInBothRuleSides)
            {            	
            	refItems.get(lhs).put(((PreservedNode) refItems.get(rhs).get(node)).getRefInRule(), node);
            }
         }
         for (Iterator<Edge> iter = g.getEdges().iterator(); iter.hasNext(); )
         {
            Edge edge = iter.next();
            
            boolean isInRightRuleSide = refItems.get(rhs).get(edge) != null;
            boolean isInBothRuleSides = isInRightRuleSide && refItems.get(rhs).get(edge).eClass() == SamrulesPackage.eINSTANCE.getPreservedEdge();
            if (isInRightRuleSide && !isInBothRuleSides)
            {
            	Edge key = null; // TODO find a better way to remove obsolete items
            	for (Map.Entry<AnnotatedElem,AnnotatedElem> entry : refItems.get(srcGraph).entrySet()) {
          		  if (entry.getValue() == edge) {
          			  key = (Edge) entry.getKey();
          			  break;
          		  }
          	  }
          	  refItems.get(srcGraph).remove(key);
          	  refItems.get(lhs).remove(((PatternEdge) edge).getSameInRule());
          	  refItems.get(rhs).remove(edge);
          	  edge.setSource(null);
          	  edge.setTarget(null);
          	  ((PatternEdge) edge).setSameInRule(null);
          	  ((PatternEdge) edge).setSameInProp(null); // manage origin of elements in dgp
            	iter.remove();
            }
            else if (isInBothRuleSides)
            {
            	refItems.get(lhs).put(((PreservedEdge) refItems.get(rhs).get(edge)).getRefInRule(), edge);
            }
         }
         //add the nodes and edges deleted by the rule to g
         for (Iterator<Node> iter = lhs.getNodes().iterator(); iter.hasNext(); )
         {
            Node node = iter.next();
            boolean isDeleted = node.eClass() != SamrulesPackage.eINSTANCE.getPreservedNode() || ((PreservedNode) node).getRefInRule() == null;
            if (isDeleted)
            {
               Node newNode = InvariantCheckingUtil.copyAsPattern(node);//node.copy();
               g.getNodes().add(newNode);
               //node.addToReferenceItems (newNode);
               ((PatternNode) newNode).setSameInRule(node);
               refItems.get(lhs).put(node, newNode);
            }
         }
         for (Iterator<Edge> iter = lhs.getEdges().iterator(); iter.hasNext(); )
         {
            Edge edge = iter.next();
            boolean isDeleted = edge.eClass() != SamrulesPackage.eINSTANCE.getPreservedEdge() || ((PreservedEdge) edge).getRefInRule() == null;
            if (isDeleted)
            {
               Edge newEdge = InvariantCheckingUtil.copyAsPattern(edge);//edge.copy();
               g.getEdges().add(newEdge);
               ((PatternEdge) newEdge).setSameInRule(edge);
               refItems.get(lhs).put(edge, newEdge);
            }
         }
         //ensure that assocs in g are isomorph to lhs
         createAssocs (lhs, g);
         
         // translate NACs from the left rule side to the source graph
         NACTranslator nacT = new NACTranslator();
         Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
    	 nacT.setMergedGraph(g);
    	 nacT.setPattern(lhs);
    	 for (Map.Entry<AnnotatedElem, AnnotatedElem> entry : refItems.get(lhs).entrySet()) {
    		 if (SamgraphPackage.eINSTANCE.getNode().isSuperTypeOf(entry.getKey().eClass())) {
    			 initialMatching.getNodeMatching().put((Node) entry.getKey(), (Node) entry.getValue());
    		 } else {
    			 initialMatching.getEdgeMatching().put((Edge) entry.getKey(), (Edge) entry.getValue());
    		 }
    	 }
    	 nacT.setInitialMatching(initialMatching);
    	 g = nacT.translate();
    	 if (g == null) {
    		 // one or more nacs could not be translated because they were found as positive elements
    		 // rule is not applicable
    		 return null;
    	 }
         
         Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();
         // copy NACs from the target graph pattern
         for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(srcGraph).getNacs()) {
        	 boolean consistent = true;
        	 Match currentMatching = SamtraceFactory.eINSTANCE.createMatch();
        	 for (Edge e : nac.getEdges()) {
        		 boolean found = false;
        		 //if (e.partOfNacInterface() && !e.getSource().isNegated()) {
        		 if (e.partOfNacInterface() && !InvariantCheckingUtil.isNegated(e.getSource())) {
        			 if (refItems.get(srcGraph).get(e.getSource()) == null) {
        				 consistent = false;
        			 } else {
        				 currentMatching.getNodeMatching().put(e.getSource(), (Node) refItems.get(srcGraph).get(e.getSource()));
        			 }
        		 }
        		 if (e.partOfNacInterface() && !InvariantCheckingUtil.isNegated(e.getTarget())){
        			 if (refItems.get(srcGraph).get(e.getTarget()) == null) {
        				 consistent = false;
        			 } else {
        				 currentMatching.getNodeMatching().put(e.getTarget(), (Node) refItems.get(srcGraph).get(e.getTarget()));
        			 }
        		 }
        	 }
        	 if (consistent) {
        		NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
        		
        		for (Node n : nac.getNodes()) {
    				//Node newNode = n.copy();
        			Node newNode = InvariantCheckingUtil.copyAsPattern(n);
        			((PatternNode) newNode).setSameInProp(((PatternNode) n).getSameInProp());
    				newNac.getNodes().add(newNode);
    				currentMatching.getNodeMatching().put(n, newNode);
    			}
    			for (Edge e : nac.getEdges()) {
    				//Edge newEdge = e.copy();
    				Edge newEdge = InvariantCheckingUtil.copyAsPattern(e);
        			((PatternEdge) newEdge).setSameInProp(((PatternEdge) e).getSameInProp());
    				newNac.getEdges().add(newEdge);
    				newEdge.setSource(currentMatching.getNodeMatching().get(e.getSource()));
    				newEdge.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
    			}
    			for (Annotation an : nac.getAnnotations()) {
    				if (refItems.get(srcGraph).get(an.getTarget()) != null) {
    					Annotation anCopy = SamannotationFactory.eINSTANCE.createAnnotation();
    					anCopy.setSource(an.getSource());    				
    					anCopy.setTarget(refItems.get(srcGraph).get(an.getTarget()));
    					newNac.getAnnotations().add(anCopy);
    				}    				
    			}
    			
    			for (Node n : lhs.getNodes()) {
    				if (SamrulesPackage.eINSTANCE.getDeletedNode() == n.eClass()) {
    					Node copyInSgp = (Node) refItems.get(lhs).get(n);
    					Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
    					an.setSource(InvariantCheckingCore.NAC_BOUND_ITEM);    				
    					an.setTarget(copyInSgp);
    					newNac.getAnnotations().add(an);
    				}
    			}
    			for (Edge e : lhs.getEdges()) {
    				if (SamrulesPackage.eINSTANCE.getDeletedEdge() == e.eClass()) {
    					Edge copyInSgp = (Edge) refItems.get(lhs).get(e);
    					Annotation an = SamannotationFactory.eINSTANCE.createAnnotation();
    					an.setSource(InvariantCheckingCore.NAC_BOUND_ITEM);    				
    					an.setTarget(copyInSgp);
    					newNac.getAnnotations().add(an);
    				}
    			}
    			
    			newNacs.add(newNac);
        	 }
         }
         if (!newNacs.isEmpty()) {
        	 g.setCondition(InvariantCheckingUtil.addNegatedConditions(g.getCondition(), newNacs)); 
         }                  
         
         return g;
      }
      return null;

   } //end of reverseRuleApplication()

   public Graph forwardRuleApplication(final Graph srcGraph, final GraphRule r) {
		  refItems.put(srcGraph, new HashMap<AnnotatedElem, AnnotatedElem>());
	      if (srcGraph != null && r != null)
	      {         
	         Graph rhs = r.getRight();
	         Graph lhs = r.getLeft();         

	         // first step: translate NACs from the left rule side to the source pattern
	         NACTranslator nacT = new NACTranslator();
	         Match initialMatching = SamtraceFactory.eINSTANCE.createMatch();
	    	 nacT.setMergedGraph((RuleGraph) srcGraph);
	    	 nacT.setPattern(lhs);
	    	 for (Node n : srcGraph.getNodes()) {
	    		 if (((PatternNode) n).getSameInRule() != null) {
	    			 initialMatching.getNodeMatching().put(((PatternNode) n).getSameInRule(), n);
	    		 } 
	    	 }
	    	 for (Edge e : srcGraph.getEdges()) {
	    		 if (((PatternEdge) e).getSameInRule() != null) {
	    			 initialMatching.getEdgeMatching().put(((PatternEdge) e).getSameInRule(), e);
	    		 } 
	    	 }
	    	 
	    	 nacT.setInitialMatching(initialMatching);
	    	 Graph res = nacT.translate();
	    	 if (res == null) {
	    		 // one or more nacs could not be translated because they were found as positive elements
	    		 // rule is not applicable
	    		 return null;
	    	 }
	         
	         
	         refItems.put(lhs, new HashMap<AnnotatedElem, AnnotatedElem>());
	         refItems.put(rhs, new HashMap<AnnotatedElem, AnnotatedElem>());
	         RuleGraph g = this.copyGraph (srcGraph, r, true);         
	         //remove all nodes and edges from g, that are deleted by the rule
	         for (Iterator<Node> iter = g.getNodes().iterator(); iter.hasNext(); )
	         {
	        	 
	            Node node = iter.next();
	            
	            boolean isInLeftRuleSide = refItems.get(lhs).get(node) != null;
	            boolean isInBothRuleSides = isInLeftRuleSide && refItems.get(lhs).get(node).eClass() == SamrulesPackage.eINSTANCE.getPreservedNode();
	            if (isInLeftRuleSide && !isInBothRuleSides)
	            {

	               boolean deleteIt = true;
	               for (Iterator<Edge> i = node.getOutgoing().iterator(); i.hasNext() && deleteIt; )
	               {
	            	   
	                  Edge e = i.next();
	            
	                 
	                  if (refItems.get(lhs).get(e) == null)
	                  {                	  
	            
	                     deleteIt = false;
	                  }
	               }
	               for (Iterator<Edge> i = node.getIncoming().iterator(); i.hasNext() && deleteIt; )
	               {
	            	   
	                  Edge e = i.next();
	            
	                  if (refItems.get(lhs).get(e) == null)
	                  {                	  
	            
	                     deleteIt = false;
	                  }
	               }
	               if (deleteIt)
	               {
	            	  // actually I think the items have to be removed from refItems as well,
	            	  // especially from refItems.get(tgp); unfortunately we have to do an inverse
	            	  // lookup
	            	  Node key = null; // TODO find a better way to remove obsolete items
	            	  for (Map.Entry<AnnotatedElem,AnnotatedElem> entry : refItems.get(srcGraph).entrySet()) {
	            		  if (entry.getValue() == node) {
	            			  key = (Node) entry.getKey();
	            			  break;
	            		  }
	            	  }
	            	  refItems.get(srcGraph).remove(key);
	            	  refItems.get(rhs).remove(((PatternNode) node).getSameInRule());
	            	  refItems.get(lhs).remove(node);
	            	  node.getIncoming().clear();
	            	  node.getOutgoing().clear();
	            	  ((PatternNode) node).setSameInRule(null);
	            	  ((PatternNode) node).setSameInProp(null); // manage origin of elements in tgp
	            	  iter.remove();
	               }
	               else
	               {
	            	   System.out.println(node);
	                  return null;
	               }
	            }
	            else if (isInBothRuleSides)
	            {            	
	            	refItems.get(rhs).put(((PreservedNode) refItems.get(lhs).get(node)).getRefInRule(), node);
	            }
	         }
	         for (Iterator<Edge> iter = g.getEdges().iterator(); iter.hasNext(); )
	         {
	            Edge edge = iter.next();
	            
	            boolean isInLeftRuleSide = refItems.get(lhs).get(edge) != null;
	            boolean isInBothRuleSides = isInLeftRuleSide && refItems.get(lhs).get(edge).eClass() == SamrulesPackage.eINSTANCE.getPreservedEdge();
	            if (isInLeftRuleSide && !isInBothRuleSides)
	            {
	            	Edge key = null; // TODO find a better way to remove obsolete items
	            	for (Map.Entry<AnnotatedElem,AnnotatedElem> entry : refItems.get(srcGraph).entrySet()) {
	          		  if (entry.getValue() == edge) {
	          			  key = (Edge) entry.getKey();
	          			  break;
	          		  }
	          	  }
	          	  refItems.get(srcGraph).remove(key);
	          	  refItems.get(rhs).remove(((PatternEdge) edge).getSameInRule());
	          	  refItems.get(lhs).remove(edge);
	          	  edge.setSource(null);
	          	  edge.setTarget(null);
	          	  ((PatternEdge) edge).setSameInRule(null);
	          	  ((PatternEdge) edge).setSameInProp(null); // manage origin of elements in tgp
	            	iter.remove();
	            }
	            else if (isInBothRuleSides)
	            {
	            	refItems.get(rhs).put(((PreservedEdge) refItems.get(lhs).get(edge)).getRefInRule(), edge);
	            }
	         }
	         //add the nodes and edges created by the rule to g
	         for (Iterator<Node> iter = rhs.getNodes().iterator(); iter.hasNext(); )
	         {
	            Node node = iter.next();
	            boolean isCreated = node.eClass() != SamrulesPackage.eINSTANCE.getPreservedNode() || ((PreservedNode) node).getRefInRule() == null;
	            if (isCreated)
	            {
	               Node newNode = InvariantCheckingUtil.copyAsPattern(node);//node.copy();
	               g.getNodes().add(newNode);
	               //node.addToReferenceItems (newNode);
	               ((PatternNode) newNode).setSameInRule(node);
	               refItems.get(rhs).put(node, newNode);
	            }
	         }
	         for (Iterator<Edge> iter = rhs.getEdges().iterator(); iter.hasNext(); )
	         {
	            Edge edge = iter.next();
	            boolean isCreated = edge.eClass() != SamrulesPackage.eINSTANCE.getPreservedEdge() || ((PreservedEdge) edge).getRefInRule() == null;
	            if (isCreated)
	            {
	               Edge newEdge = InvariantCheckingUtil.copyAsPattern(edge);//edge.copy();
	               g.getEdges().add(newEdge);
	               ((PatternEdge) newEdge).setSameInRule(edge);
	               refItems.get(rhs).put(edge, newEdge);
	            }
	         }
	         //ensure that assocs in g are isomorph to rhs
	         createAssocs (rhs, g);	         
	         
	         Set<NegativeApplicationCondition> newNacs = new HashSet<NegativeApplicationCondition>();	         	         
	         // copy all applicable NACs from the source graph pattern to the target graph pattern
	         for (NegativeApplicationCondition nac : SamGraphInvCheckGraphAdapter.getInstance(srcGraph).getNacs()) {
	        	 boolean consistent = true;
	        	 Match currentMatching = SamtraceFactory.eINSTANCE.createMatch();
	        	 for (Edge e : nac.getEdges()) {
	        		 boolean found = false;
	        		 //if (e.partOfNacInterface() && !e.getSource().isNegated()) {
	        		 if (e.partOfNacInterface() && !InvariantCheckingUtil.isNegated(e.getSource())) {
	        			 if (refItems.get(srcGraph).get(e.getSource()) == null) {
	        				 consistent = false;
	        			 } else {
	        				 currentMatching.getNodeMatching().put(e.getSource(), (Node) refItems.get(srcGraph).get(e.getSource()));
	        			 }
	        		 }
	        		 if (e.partOfNacInterface() && !InvariantCheckingUtil.isNegated(e.getTarget())){
	        			 if (refItems.get(srcGraph).get(e.getTarget()) == null) {
	        				 consistent = false;
	        			 } else {
	        				 currentMatching.getNodeMatching().put(e.getTarget(), (Node) refItems.get(srcGraph).get(e.getTarget()));
	        			 }
	        		 }
	        	 }
	        	 if (consistent) {
	        		NegativeApplicationCondition newNac = NacFactory.eINSTANCE.createNegativeApplicationCondition();
	        		
	        		for (Node n : nac.getNodes()) {
	    				//Node newNode = n.copy();
	        			Node newNode = InvariantCheckingUtil.copyAsPattern(n);
	        			((PatternNode) newNode).setSameInProp(((PatternNode) n).getSameInProp());
	        			((PatternNode) newNode).setSameInRule(((PatternNode) n).getSameInRule());
	    				newNac.getNodes().add(newNode);
	    				currentMatching.getNodeMatching().put(n, newNode);
	    			}
	    			for (Edge e : nac.getEdges()) {
	    				//Edge newEdge = e.copy();
	    				Edge newEdge = InvariantCheckingUtil.copyAsPattern(e);
	        			((PatternEdge) newEdge).setSameInProp(((PatternEdge) e).getSameInProp());
	        			((PatternEdge) newEdge).setSameInRule(((PatternEdge) e).getSameInRule());
	    				newNac.getEdges().add(newEdge);
	    				newEdge.setSource(currentMatching.getNodeMatching().get(e.getSource()));
	    				newEdge.setTarget(currentMatching.getNodeMatching().get(e.getTarget()));
	    			}
	    			newNacs.add(newNac);
	        	 }
	         }
	         if (!newNacs.isEmpty()) {
	        	 g.setCondition(InvariantCheckingUtil.createNegatedConditions(newNacs)); 
	         }
	                  
	         
	         return g;
	      }
	      return null;
   }   
   
   /**
    * creates the assocs between the nodes and edges of srcGraph between the elements of resultGraph.
    * to make this work, all elements have be copied first before calling this method
    *
    * @param srcGraph     the source Graph
    * @param resultGraph  the resulting graph containing then the assoc from srcGraph
    */
   private void createAssocs (final Graph srcGraph, final Graph resultGraph)
   {
      for (Iterator<Edge> iter = srcGraph.getEdges().iterator(); iter.hasNext(); )
      {
         Edge srcEdge = iter.next();
         Node srcTargetNode = srcEdge.getTarget();
         Node srcSourceNode = srcEdge.getSource();

         Edge resultEdge = (Edge) refItems.get(srcGraph).get(srcEdge);
         Node resultTargetNode = (Node) refItems.get(srcGraph).get(srcTargetNode);
         Node resultSourceNode = (Node) refItems.get(srcGraph).get(srcSourceNode);
         
         if (resultEdge != null)
         {
            resultEdge.setSource (resultSourceNode);
            resultEdge.setTarget (resultTargetNode);
         }
      }
   }


   /**
    * No comment provided by developer, please add a comment to improve documentation.
    *
    * @param src  No description provided
    * @param r    No description provided
    * @return     No description provided
    */
   private RuleGraph copyGraph (final Graph src, final GraphRule r, boolean forward)
   {
      RuleGraph result = null;
      if (src != null && r != null)
      {
         result = SamrulesFactory.eINSTANCE.createRuleGraph();
         result.setTypedOver(src.getTypedOver());
         Graph lhs = r.getLeft();
         Graph rhs = r.getRight();
         for (Iterator<Node> iter = src.getNodes().iterator(); iter.hasNext(); )
         {
            PatternNode nextNode = (PatternNode) iter.next();
            PatternNode newNode = InvariantCheckingUtil.copyAsPattern(nextNode);
            
            result.getNodes().add (newNode);
            refItems.get(src).put(nextNode, newNode);
            if (!forward) {
            	refItems.get(rhs).put(newNode, nextNode.getSameInRule());
            } else {
            	refItems.get(lhs).put(newNode, nextNode.getSameInRule());
            }
            if (nextNode.getSameInRule() != null) {
            	if (nextNode.getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getPreservedNode()) {
            		Node refInRule = ((PreservedNode) nextNode.getSameInRule()).getRefInRule();
            		if (!forward) {
            			refItems.get(lhs).put(refInRule, newNode);
            		} else {
            			refItems.get(rhs).put(refInRule, newNode);
            		}
            		newNode.setSameInRule(refInRule);            		
            	}
            }
            if (nextNode.getSameInProp() != null) {
    			newNode.setSameInProp(nextNode.getSameInProp()); // manage origin of new elements
    		}
         }
         for (Iterator<Edge> iter = src.getEdges().iterator(); iter.hasNext(); )
         {
        	PatternEdge nextEdge = (PatternEdge) iter.next();
            PatternEdge newEdge = InvariantCheckingUtil.copyAsPattern(nextEdge);
            
            result.getEdges().add (newEdge);
            refItems.get(src).put(nextEdge, newEdge);
            if (!forward) {
            	refItems.get(rhs).put(newEdge, nextEdge.getSameInRule());
            } else {
            	refItems.get(lhs).put(newEdge, nextEdge.getSameInRule());
            }
            if (nextEdge.getSameInRule() != null) {
            	if (nextEdge.getSameInRule().eClass() == SamrulesPackage.eINSTANCE.getPreservedEdge()) {
            		Edge refInRule = ((PreservedEdge) nextEdge.getSameInRule()).getRefInRule();
            		if (!forward) {
            			refItems.get(lhs).put(refInRule, newEdge);
            		} else {
            			refItems.get(rhs).put(refInRule, newEdge);
            		}
            		newEdge.setSameInRule(refInRule);
            	}
            }
            if (nextEdge.getSameInProp() != null) {
    			newEdge.setSameInProp(nextEdge.getSameInProp()); // manage origin of new elements
    		}
         }
         this.createAssocs (src, result);
      }
      return result;
   }

   public boolean checkCorrectRuleApplication(RuleGraph lhs, Graph sourceGraph) {
		IsomorphicPartMatcher ipm = new IsomorphicPartMatcher();
		ipm.setHostGraph(sourceGraph);
		ipm.setCurrentSubGraph(SubgraphIterator.graphToSubGraph(lhs));
		ipm.setPattern(lhs);
		Match currentMatching = ipm.getNextMatching();
		while (currentMatching != null) {
			boolean consistent = true;
			for (Map.Entry<Edge, Edge> e : currentMatching.getEdgeMatching().entrySet()) {
				if (e.getKey() != ((PatternEdge) e.getValue()).getSameInRule()) {
					consistent = false;
					break;
				}
			}
			for (Map.Entry<Node, Node> e : currentMatching.getNodeMatching().entrySet()) {
				if (e.getKey() != ((PatternNode) e.getValue()).getSameInRule()) {
					consistent = false;
					break;
				}
			}
			if (consistent) {
				return true;
			} else {
				currentMatching = ipm.getNextMatching();
			}
		}		
		return false;
	}
   
   public void reset(){
	   refItems = new HashMap<Graph, Map<AnnotatedElem, AnnotatedElem>>();
   }

}

/*
 * $Log$
 * Revision 1.2  2007/01/03 09:27:48  basilb
 * removed compile errors caused by wrong import declarations; introduced empty plugin class to ensure correct loading
 *
 */
