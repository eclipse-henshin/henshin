package org.eclipse.emf.henshin.interpreter.giraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class GiraphRuleData {

	public static class MatchingStep {

		public final Node node;

		public final Edge edge;

		public final Node verifyEdgeTo;

		public final Node sendBackTo;

		public MatchingStep(Node node, Edge edge, Node sendBackTo, Node verifyEdgeTo) {
			this.node = node;
			this.edge = edge;
			this.verifyEdgeTo = verifyEdgeTo;
			this.sendBackTo = sendBackTo;
		}

	}

	public final Rule rule;

	public List<MatchingStep> matchingSteps;

	public Map<ENamedElement,String> typeConstants;

	public List<Node> orderedLhsNodes;

	public GiraphRuleData(Rule rule) throws Exception {
		this.rule = rule;
		generateMatchingSteps();
		if (matchingSteps==null) {
			throw new RuntimeException("Cannot generate matching data for rule " + rule.getName());
		}
		generateTypeConstants();
		generateOrderedLhsNodes();
	}

	private void generateMatchingSteps() {
		List<Node> nodesToMatch = new ArrayList<Node>(rule.getLhs().getNodes());
		while (!nodesToMatch.isEmpty()) {
			List<MatchingStep> steps = getLongestMatchingSteps(nodesToMatch);
			if (this.matchingSteps==null) {
				this.matchingSteps = steps;
			} else {
				// Merge matching steps:
//				while (!steps.isEmpty()) {
					
//				}
			}
			// Remove matched nodes:
			for (MatchingStep step : steps) {
				nodesToMatch.remove(step.node);
				if (step.edge!=null) {
					nodesToMatch.remove(step.edge.getTarget());
				}
			}
		}
	}

	private List<MatchingStep> getLongestMatchingSteps(List<Node> nodes) {
		List<MatchingStep> result = null;
		for (Node start : nodes) {
			List<MatchingStep> matchingSteps = getMatchingSteps(rule, start);
			if (matchingSteps!=null) {
				if (result==null || matchingSteps.size()>result.size()) {
					result = matchingSteps;
				}
			}
		}
		return result;
	}

	private List<MatchingStep> getMatchingSteps(Rule rule, Node start) {
		List<MatchingStep> matchingSteps = new ArrayList<MatchingStep>();
		if (start.getOutgoing().isEmpty()) {
			return null;
		}
		Deque<Edge> edgeQueue = new ArrayDeque<Edge>();
		edgeQueue.addAll(start.getOutgoing());
		Set<Node> lockedNodes = new HashSet<Node>();
		Set<Edge> visitedEdges = new HashSet<Edge>();
		while (!edgeQueue.isEmpty()) {
			Edge edge = edgeQueue.pop();
			
			// Add the next matching step:
			matchingSteps.add(new MatchingStep(edge.getSource(), edge, null, 
					lockedNodes.contains(edge.getTarget()) ? edge.getTarget() : null));

			visitedEdges.add(edge);

			if (edge.getTarget().getOutgoing().isEmpty()) {

				// Leaf:
				if (!lockedNodes.contains(edge.getTarget())) {
					Node sendBackTo = null;
					if (!edgeQueue.isEmpty()) {
						sendBackTo = edgeQueue.getFirst().getSource();
					}
					matchingSteps.add(new MatchingStep(edge.getTarget(), null, sendBackTo, null));
				}

			} else {

				// Intermediate node:
				for (Edge succ : edge.getTarget().getOutgoing()) {
					if (!visitedEdges.contains(succ) && !edgeQueue.contains(succ)) {
						edgeQueue.push(succ);
					}
				}

			}

			lockedNodes.add(edge.getSource());
			lockedNodes.add(edge.getTarget());

		}
		if (rule.getLhs().getNodes().size()==lockedNodes.size()) {
			return matchingSteps;
		}
		return null;
	}

	private void generateTypeConstants() {
		typeConstants = new LinkedHashMap<ENamedElement, String>();
		for (EPackage pack : rule.getModule().getImports()) {
			for (EClassifier classifier : pack.getEClassifiers()) {
				if (!(classifier instanceof EClass)) {
					continue;
				}
				String baseName = camelCase2Upper(pack.getName()) + "_" + camelCase2Upper(classifier.getName());
				typeConstants.put(classifier, baseName);
				for (EReference ref : ((EClass) classifier).getEAllReferences()) {
					typeConstants.put(ref, baseName + "_" + camelCase2Upper(ref.getName()));
				}
			}
		}
	}

	private void generateOrderedLhsNodes() {
		orderedLhsNodes = new ArrayList<Node>();
		for (MatchingStep step : matchingSteps) {
			if (step.node!=null && !orderedLhsNodes.contains(step.node)) {
				orderedLhsNodes.add(step.node);
			}
		}
	}

	public String getNodeName(Node node) {
		return node.getName()!=null && node.getName().trim().length()>0 ? 
				"\""+node.getName()+"\"" : 
					"" + node.getGraph().getNodes().indexOf(node);
	}

	public String getTypeConstantName(ENamedElement type) {
		EPackage pack = (EPackage) type.eContainer();
		return camelCase2Upper(pack.getName()) + "_" + camelCase2Upper(type.getName());
	}

	private static String camelCase2Upper(String s) {
		String r = "";
		boolean u = false;
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			char C = Character.toUpperCase(c);
			if (Character.isUpperCase(c)) {
				r = r + (u ? ("_"+C) : C);
			} else {
				u = true;
				r = r + C;
			}
		}
		return r;
	}

}
