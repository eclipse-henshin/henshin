package org.eclipse.emf.henshin.interpreter.giraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.interpreter.info.RuleChangeInfo;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class GiraphRuleData {

	public static class MatchingStep {

		public Node node;

		public Edge edge;

		public Node verifyEdgeTo;

		public Node sendBackTo;
		
		public boolean isStart;

		public boolean isJoin;

		public MatchingStep(Node node, Edge edge, Node sendBackTo, Node verifyEdgeTo, boolean isStart, boolean isJoin) {
			this.node = node;
			this.edge = edge;
			this.verifyEdgeTo = verifyEdgeTo;
			this.sendBackTo = sendBackTo;
			this.isStart = isStart;
			this.isJoin = isJoin;
		}

	}

	public final Rule rule;

	public final RuleChangeInfo changeInfo;

	public List<MatchingStep> matchingSteps;

	public List<Node> orderedLhsNodes;

	public GiraphRuleData(Rule rule) throws Exception {
		this.rule = rule;
		this.changeInfo = new RuleChangeInfo(rule);
		this.matchingSteps = generateMatchingSteps();
		if (matchingSteps==null) {
			throw new RuntimeException("Cannot generate matching data for rule " + rule.getName());
		}
		generateOrderedLhsNodes();
	}

	private List<MatchingStep> generateMatchingSteps() {
		List<Node> nodesToMatch = new ArrayList<Node>(rule.getLhs().getNodes());
		List<MatchingStep> result = new ArrayList<GiraphRuleData.MatchingStep>();
		while (!nodesToMatch.isEmpty()) {
			
			// The next matching steps:
			List<MatchingStep> newSteps = getLongestMatchingSteps(nodesToMatch);
			
			// Find the join node:
			Node joinNode = null;
			for (int i=0; i<newSteps.size(); i++) {
				for (MatchingStep oldStep : result) {
					if (oldStep.node==newSteps.get(i).node) {
						joinNode = oldStep.node;
						break;
					}
				}
				if (joinNode!=null) {
					break;
				}
			}
			
			// In the last old matching step, send the matches to the join node:
			if (joinNode!=null && !result.isEmpty()) {
				result.get(result.size()-1).sendBackTo = joinNode;
			}
			
			// Add all new matching steps until the join node is reached:
			for (MatchingStep step : newSteps) {
				result.add(step);
				// Remember new matched nodes:
				nodesToMatch.remove(step.node);
				if (step.edge!=null) {
					nodesToMatch.remove(step.edge.getTarget());
				}
				// Stop if we reached the join node:
				if (step.node==joinNode) {
					step.edge = null;
					step.isJoin = true;
					break;
				}
			}
		}
		return result;
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
					lockedNodes.contains(edge.getTarget()) ? edge.getTarget() : null, 
					lockedNodes.isEmpty(), false));

			visitedEdges.add(edge);

			if (edge.getTarget().getOutgoing().isEmpty()) {

				// Leaf:
				if (!lockedNodes.contains(edge.getTarget())) {
					Node sendBackTo = null;
					if (!edgeQueue.isEmpty()) {
						sendBackTo = edgeQueue.getFirst().getSource();
					}
					matchingSteps.add(new MatchingStep(edge.getTarget(), null, sendBackTo, null, false, false));
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
		return matchingSteps;
	}

	private void generateOrderedLhsNodes() {
		orderedLhsNodes = new ArrayList<Node>();
		for (MatchingStep step : matchingSteps) {
			if (step.node!=null && !orderedLhsNodes.contains(step.node)) {
				orderedLhsNodes.add(step.node);
			}
		}
	}

}
