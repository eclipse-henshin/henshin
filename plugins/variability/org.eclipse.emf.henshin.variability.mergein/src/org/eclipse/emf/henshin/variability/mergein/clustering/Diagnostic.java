package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestion;

public class Diagnostic {
	public static void findDanglingObject(Rule rule) {
		TreeIterator<EObject> it = rule.eAllContents();

		while (it.hasNext()) {
			EObject next = it.next();

			if (next instanceof GraphElement) {
				GraphElement elem = (GraphElement) next;
				if (next instanceof Edge) {
					Edge edge = (Edge) next;
					if (edge.getSource().eResource() == null)
						alarm(elem, edge.getSource());
					if (edge.getTarget().eResource() == null)
						alarm(elem, edge.getTarget());
				}

				if (next instanceof Node) {
					Node node = (Node) next;
					for (Edge edge : node.getAllEdges()) {
						if (edge.eResource() == null)
							alarm(elem, edge);
					}
				}
			}
		}
	}

	private static void alarm(GraphElement elem, GraphElement dangling) {
		System.out.println("Found dangling element in rule "
				+ elem.getGraph().getRule());
		System.out.println(" * graph: " + elem.getGraph());
		System.out.println(" * contained element: " + elem + " "
				+ elem.getAction());
		System.out.println(" * dangling element: " + dangling + " "
				+ dangling.getAction());
		System.out.println();
	}

	public static void findInconsistencies(MergeSuggestion result) {
		Map<GraphElement, MergeRule> map = new HashMap<GraphElement, MergeRule>();
		for (MergeRule r1 : result.getMergeClusters()) {
			for (MergeRule r2 : result.getMergeClusters()) {
				if (r1 != r2) {
					for (Rule rr1 : r1.getRules()) {
						if (r2.getRules().contains(rr1))
							throw new RuntimeException(
									"Found overlapping merge rules: "+1 + ", "+r2);
					}
				}
			}
			
			
			for (MergeRuleElement elem : r1.getElements()) {				
				if (elem.getReferenceElements().isEmpty())
					throw new RuntimeException("Found empty element");

				Map<Rule, GraphElement>  rules = new HashMap<Rule, GraphElement>();	
				for (GraphElement ge : elem.getReferenceElements()) {
					if (!r1.getRules().contains(ge.getGraph().getRule()))
						throw new RuntimeException("Element assigned to wrong rule set: "+ge+" (belongs to "+ge.getGraph().getRule().getName()+", assigned to"+r1.getName()+")");
					if (map.containsKey(ge))
						throw new RuntimeException("Element considered twice ("+map.get(ge).getName()+","+r1.getName()+"): "+ge);
					map.put(ge, r1);
					
					Rule rootRule = ge.getGraph().getRule().getRootRule();
					if (rules.containsKey(rootRule))
						throw new RuntimeException("Tried to merge two elements from the same rule ("+rootRule.getName() +"): "+ge + " " + rules.get(rootRule));
					rules.put(rootRule, ge);
				}
			}
			
			for (Rule r :r1.getRules()) {
				TreeIterator<EObject> it = r.eAllContents();
				while (it.hasNext()) {
					EObject o = it.next();
					if (o instanceof GraphElement) {
						GraphElement ge = (GraphElement) o;
						if (r1.findMergeRuleElement(ge) == null) {
							String graphType = getGraphType(ge);
							if (!graphType.equals("AC"))
								throw new RuntimeException("Merge rule for rule "+r+" did not contain an element for "+graphType+" element "+ge+"! (MergeRule: "+r1.getName()+")");
						}
					}
				}
			}
		}
		
		
	}

	private static String getGraphType(GraphElement ge) {
		if (ge.getGraph().isLhs())
			return "LHS";
		else if (ge.getGraph().isRhs())
			return "RHS";
		else return "AC";
	}

	public static int getNoOfNodes(Graph graph) {
		int i = 0;
		for (Node node : graph.getNodes()) {
			i++;
//			for (Attribute a : node.getAttributes())
//				i++;
		}
		return i;
	}
	
	public static int getNoOfEdges(Graph graph) {
		int i = 0;
		for (Edge edge : graph.getEdges()) {
			i++;
		}
		return i;
	}

	public static int getNoOfLhsNodes(MergeRule mergeRule) {
		int i = 0;
		for (MergeRuleElement el : mergeRule.getElements()) {
			if (el.isLhsElement() && !el.isMultiRuleElement() && (el.getReferenceElements().get(0) instanceof Node))
				i++;
		}
		return i;
	}

	public static int getNoOfRhsNodes(MergeRule mergeRule) {
		int i = 0;
		for (MergeRuleElement el : mergeRule.getElements()) {
			if (el.isRhsElement()&& !el.isMultiRuleElement()  && (el.getReferenceElements().get(0) instanceof Node))
				i++;
		}
		return i;
	}

	public static int getNoOfLhsEdges(MergeRule mergeRule) {
		int i = 0;
		for (MergeRuleElement el : mergeRule.getElements()) {
			if (el.isLhsElement() && !el.isMultiRuleElement() && (el.getReferenceElements().get(0) instanceof Edge))
				i++;
		}
		return i;
	}

	public static int getNoOfRhsEdges(MergeRule mergeRule) {
		int i = 0;
		for (MergeRuleElement el : mergeRule.getElements()) {
			if (el.isRhsElement()&& !el.isMultiRuleElement() &&  (el.getReferenceElements().get(0) instanceof Edge))
				i++;
		}
		return i;
	}
	public static void printElementComparison(MergeRule mergeRule) {
		System.out.println("Merge Rule " +mergeRule.getName() + ":\n"
				+ Diagnostic.getNoOfLhsNodes(mergeRule) + " LHS nodes, "
				+ Diagnostic.getNoOfRhsNodes(mergeRule) + " RHS nodes, "
						+ Diagnostic.getNoOfLhsEdges(mergeRule) + " LHS edges, "
						+ Diagnostic.getNoOfRhsEdges(mergeRule) + " RHS edges.");
		System.out.println("Rule "+mergeRule.getMasterRule().getName()+ ":\n"+
				+ Diagnostic
						.getNoOfNodes(mergeRule.getMasterRule().getLhs())
				+ " LHS, "
				+ Diagnostic
						.getNoOfNodes(mergeRule.getMasterRule().getRhs())
				+ " RHS nodes,"+
				+ Diagnostic
						.getNoOfEdges(mergeRule.getMasterRule().getLhs())
				+ " LHS, "
				+ Diagnostic
						.getNoOfEdges(mergeRule.getMasterRule().getRhs())
				+ " RHS edges.");
		System.out.println();

	}
}
