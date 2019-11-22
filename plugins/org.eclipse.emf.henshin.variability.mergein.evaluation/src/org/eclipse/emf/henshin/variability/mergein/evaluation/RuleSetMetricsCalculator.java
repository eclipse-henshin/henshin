package org.eclipse.emf.henshin.variability.mergein.evaluation;

import java.util.Collection;
import java.util.HashSet;

import metrics.RuleMetrics;
import metrics.RuleSetMetrics;
import metrics.impl.MetricsFactoryImpl;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityGraphElement;

public class RuleSetMetricsCalculator {
	public RuleSetMetrics calculcate(Collection<Rule> ruleSet) {
		RuleSetMetrics result = MetricsFactoryImpl.eINSTANCE
				.createRuleSetMetrics();
		result.getRuleSet().addAll(ruleSet);

		computeRuleMetrics(ruleSet, result);
		computeTotalMetrics(result);

		return result;
	}

	private void computeTotalMetrics(RuleSetMetrics result) {
		for (RuleMetrics m : result.getRuleMetrics()) {
			result.setTotalNumberOfLhsNodes(result.getTotalNumberOfLhsNodes()
					+ m.getNumberOfLhsNodes());
			result.setTotalNumberOfLhsEdges(result.getTotalNumberOfLhsEdges()
					+ m.getNumberOfLhsEdges());
			result.setTotalNumberOfLhsAttributes(result
					.getTotalNumberOfLhsAttributes()
					+ m.getNumberOfLhsAttributes());
			result.setTotalNumberOfNodes(result.getTotalNumberOfNodes()
					+ m.getNumberOfNodes());
			result.setTotalNumberOfEdges(result.getTotalNumberOfEdges()
					+ m.getNumberOfEdges());
			result.setTotalNumberOfAttributes(result
					.getTotalNumberOfAttributes() + m.getNumberOfAttributes());
			result.setTotalNumberOfAnnotatedNodes(result
					.getTotalNumberOfAnnotatedNodes()
					+ m.getNumberOfAnnotatedNodes());
			result.setTotalNumberOfAnnotatedEdges(result
					.getTotalNumberOfAnnotatedEdges()
					+ m.getNumberOfAnnotatedEdges());
			result.setTotalNumberOfAnnotatedAttributes(result
					.getTotalNumberOfAnnotatedAttributes()
					+ m.getNumberOfAnnotatedAttributes());
			result.setTotalNumberOfNACs((result
					.getTotalNumberOfNACs()
					+ m.getNumberOfNACs()));
			result.setTotalNumberOfPACs((result
					.getTotalNumberOfPACs()
					+ m.getNumberOfPACs()));
		}
		result.setNumberOfRules(result.getRuleSet().size());
	}

	private void computeRuleMetrics(Collection<Rule> ruleSet,
			RuleSetMetrics result) {
		for (Rule rule : ruleSet) {
			RuleMetrics metrics = MetricsFactoryImpl.eINSTANCE
					.createRuleMetrics();
			metrics.setNumberOfLhsNodes(rule.getLhs().getNodes().size());
			metrics.setNumberOfLhsEdges(rule.getLhs().getEdges().size());
			metrics.setNumberOfLhsAttributes(getAttributes(rule.getLhs())
					.size());
			metrics.setNumberOfNodes(countNodes(rule));
			metrics.setNumberOfEdges(countEdges(rule));
			metrics.setNumberOfAttributes(countAttributes(rule));
			metrics.setNumberOfAnnotatedNodes(countAnnotatedNodes(rule));
			metrics.setNumberOfAnnotatedEdges(countAnnotatedEdges(rule));
			metrics.setNumberOfAnnotatedAttributes(countAnnotatedAttributes(rule));
			metrics.setNumberOfNACs(rule.getLhs().getNACs().size());
			metrics.setNumberOfEdges(rule.getLhs().getPACs().size());
			result.getRuleMetrics().add(metrics);
		}
	}

	private int countNodes(Rule rule) {
		int i = 0;
		Collection<GraphElement> containedElements = getContainedElements(rule);
		for (GraphElement e : containedElements)
			if (e instanceof Node)
				i++;
		return i;
	}

	private int countEdges(Rule rule) {
		int i = 0;
		Collection<GraphElement> containedElements = getContainedElements(rule);
		for (GraphElement e : containedElements)
			if (e instanceof Edge)
				i++;
		return i;
	}

	private int countAttributes(Rule rule) {
		int i = 0;
		Collection<GraphElement> containedElements = getContainedElements(rule);
		for (GraphElement e : containedElements)
			if (e instanceof Attribute)
				i++;
		return i;
	}

	private int countAnnotatedNodes(Rule rule) {
		int i = 0;
		Collection<GraphElement> containedElements = getContainedElements(rule);
		for (GraphElement e : containedElements)
			if (isAnnotated(e) && e instanceof Node)
				i++;
		return i;
	}

	private int countAnnotatedEdges(Rule rule) {
		int i = 0;
		Collection<GraphElement> containedElements = getContainedElements(rule);
		for (GraphElement e : containedElements)
			if (isAnnotated(e) && e instanceof Edge)
				i++;
		return i;
	}

	private int countAnnotatedAttributes(Rule rule) {
		int i = 0;
		Collection<GraphElement> containedElements = getContainedElements(rule);
		for (GraphElement e : containedElements)
			if (isAnnotated(e) && e instanceof Attribute)
				i++;
		return i;
	}

	public Collection<Attribute> getAttributes(Graph graph) {
		Collection<Attribute> result = new HashSet<Attribute>();
		for (Node n : graph.getNodes()) {
			result.addAll(n.getAttributes());
		}
		return result;
	}

	public Collection<GraphElement> getContainedElements(Rule rule) {
		Collection<GraphElement> result = new HashSet<GraphElement>();
		TreeIterator<EObject> it = rule.eAllContents();
		while (it.hasNext()) {
			EObject o = it.next();
			if (o instanceof GraphElement) {
				result.add((GraphElement) o);
			}
		}
		return result;
	}

	private boolean isAnnotated(GraphElement graphElement) {
		VariabilityGraphElement ge = VariabilityFactory.createVariabilityGraphElement(graphElement);
		if (ge.getPresenceCondition() != null
				&& !ge.getPresenceCondition().isEmpty()
				&& !ge.getPresenceCondition().equals("true"))
			return true;
		else
			return false;
	}
}
