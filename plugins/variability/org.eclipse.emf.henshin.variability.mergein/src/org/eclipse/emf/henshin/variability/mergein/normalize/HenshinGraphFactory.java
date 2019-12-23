package org.eclipse.emf.henshin.variability.mergein.normalize;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class HenshinGraphFactory {

	private static HenshinGraphFactory instance = new HenshinGraphFactory();

	private HenshinGraphFactory() {
	}

	public static HenshinGraphFactory getInstance() {
		return instance;
	}

	public HenshinGraph createIntegratedGraph(Rule rule, boolean includeRhs)
			throws InputRuleNotSupportedException {
		checkPrecondition(rule);
		HenshinGraph result = new HenshinGraph();
		addGraphElementsToResult(rule, result, includeRhs);
		return result;
	}

	private void addGraphElementsToResult(Rule rule, HenshinGraph result, boolean includeRhs) {
		for (Node n : getNodes(rule)) {
			if (isRelevant(n, includeRhs)) {
				HenshinNode node = new HenshinNode(result, n.getType(), n.getAction().getType(), "");
				result.addVertex(n, node);
			}
			
			for (Attribute a : n.getAttributes()) {
				if (isRelevant(a, includeRhs)) 
					result.addAttribute(a, a.getAction().getType(), "");
			}
		}
		for (Edge e : getEdges(rule)) {
			if (isRelevant(e, includeRhs)) {
			Node source = e.getSource().getActionNode();
			Node target = e.getTarget().getActionNode();
			HenshinEdge integratedEdge = new HenshinEdge(result, e.getType(), e.getAction().getType(), "", false);
			result.addEdge(source, target, e, integratedEdge);
			}
		}
	}

	private List<Node> getNodes(Rule rule) {
		List<Node> result = new ArrayList<Node>();
		result.addAll(rule.getLhs().getNodes());
		result.addAll(rule.getRhs().getNodes());
		for (NestedCondition cond : rule.getLhs().getNestedConditions())
			result.addAll(cond.getConclusion().getNodes());
		return result;
	}

	private List<Edge> getEdges(Rule rule) {
		List<Edge> result = new ArrayList<Edge>();
		result.addAll(rule.getLhs().getEdges());
		result.addAll(rule.getRhs().getEdges());
		for (NestedCondition cond : rule.getLhs().getNestedConditions())
			result.addAll(cond.getConclusion().getEdges());
		return result;
	}

	private boolean isRelevant(GraphElement ge, boolean includeRhs) {
		if (ge.getAction() == null)
			return false;
		Type type = ge.getAction().getType();
		if (type == Type.DELETE || type == Type.PRESERVE)
			return true;
		if (type == Type.FORBID || type == Type.REQUIRE)
			return true;
		if (type == Type.CREATE)
			return includeRhs;
		return false;
	}

	private void checkPrecondition(Rule rule)
			throws InputRuleNotSupportedException {
		for (NestedCondition nac : rule.getLhs().getNACs())
			if (!nac.getConclusion().getNestedConditions().isEmpty())
				throw new InputRuleNotSupportedException(
						"Currently not supporting nesting of application conditions.");
		for (NestedCondition pac : rule.getLhs().getPACs())
			if (!pac.getConclusion().getNestedConditions().isEmpty())
				throw new InputRuleNotSupportedException(
						"Currently not supporting nesting of application conditions.");

	}


	public RuleToHenshinGraphMap createIntegratedGraphs(List<Rule> rules,
			boolean includeRhs) {
		RuleToHenshinGraphMap result = new RuleToHenshinGraphMap();
		for (Rule rule : rules) {
			try {
				result.put(rule, createIntegratedGraph(rule, includeRhs));
			} catch (InputRuleNotSupportedException e) {
				System.out.println("Skipping rule " + rule.getName() + ": "
						+ e.getMessage());
			}
		}
		return result;
	}
}
