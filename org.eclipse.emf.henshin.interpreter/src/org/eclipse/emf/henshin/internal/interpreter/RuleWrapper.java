package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.common.util.ModelHelper;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.conditions.nested.AndFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.ApplicationCondition;
import org.eclipse.emf.henshin.internal.conditions.nested.IFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.NotFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.OrFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.TrueFormula;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.InstanciationConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.matching.Match;
import org.eclipse.emf.henshin.internal.matching.Matchfinder;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.interpreter.util.RuleMatch;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;

//TODO(enrico): merge this class with RuleInfo
public class RuleWrapper {
	private Rule rule;
	private EmfGraph emfGraph;
	IFormula formula;

	private Map<Graph, ApplicationCondition> graph2ac;
	private Map<Node, Variable> node2variable;
	private Map<Variable, Node> variable2node;

	private AttributeConditionHandler conditionHandler;
	private ScriptEngine scriptEngine;

	private Matchfinder matchfinder;

	public RuleWrapper(Rule rule, EmfGraph emfGraph, ScriptEngine scriptEngine) {
		this.rule = rule;
		this.emfGraph = emfGraph;

		this.conditionHandler = new AttributeConditionHandler(rule,
				scriptEngine);
		this.node2variable = new HashMap<Node, Variable>();
		this.variable2node = new HashMap<Variable, Node>();
		this.graph2ac = new HashMap<Graph, ApplicationCondition>();
		this.scriptEngine = scriptEngine;

		createLhsVariables();
		ApplicationCondition ac = graph2ac.get(rule.getLhs());
		ac.setFormula(translateFormula(rule.getLhs().getFormula()));
	}

	private ApplicationCondition translateNestedCondition(
			NestedCondition condition) {
		createVariables(condition);
		ApplicationCondition ac = graph2ac.get(condition.getConclusion());
		ac.setNegated(condition.isNegated());
		ac.setFormula(translateFormula(condition.getConclusion().getFormula()));

		return ac;
	}

	private IFormula translateFormula(
			org.eclipse.emf.henshin.model.Formula formula) {
		IFormula result = null;

		if (formula instanceof And)
			result = new AndFormula(
					translateFormula(((And) formula).getLeft()),
					translateFormula(((And) formula).getRight()));
		else if (formula instanceof Or)
			result = new OrFormula(translateFormula(((Or) formula).getLeft()),
					translateFormula(((Or) formula).getRight()));
		else if (formula instanceof Not)
			result = new NotFormula(
					translateFormula(((Not) formula).getChild()));
		else if (formula instanceof NestedCondition) {
			result = translateNestedCondition((NestedCondition) formula);
		} else {
			result = new TrueFormula();
		}

		return result;
	}

	public void reset() {
		matchfinder.reset();
	}

	private void createLhsVariables() {
		List<Variable> variables = new ArrayList<Variable>();

		for (Node node : rule.getLhs().getNodes()) {
			EClass type = node.getType();
			Variable var = new Variable(emfGraph, type);
			variables.add(var);
			node2variable.put(node, var);
			variable2node.put(var, node);
		}

		for (Node node : rule.getLhs().getNodes()) {
			createQueries(node, null);
		}

		matchfinder = new Matchfinder(variables, formula, conditionHandler);
		graph2ac.put(rule.getLhs(), matchfinder);
	}

	private void createVariables(NestedCondition condition) {
		List<Variable> variables = new ArrayList<Variable>();
		Graph conclusion = condition.getConclusion();

		for (Node node : conclusion.getNodes()) {
			EClass type = node.getType();
			Variable var = new Variable(emfGraph, type);
			variables.add(var);
			node2variable.put(node, var);
			variable2node.put(var, node);
		}

		for (Node node : conclusion.getNodes()) {
			createQueries(node, condition.getMappings());
		}

		ApplicationCondition ac = new ApplicationCondition(variables,
				new TrueFormula(), true);
		graph2ac.put(conclusion, ac);
	}

	private void createQueries(Node node, List<Mapping> mapping) {
		Variable var = node2variable.get(node);

		for (Edge edge : node.getOutgoing()) {
			Variable targetVariable = node2variable.get(edge.getTarget());
			ReferenceConstraint refQuery = new ReferenceConstraint(var,
					targetVariable, edge.getType());
			var.addQuery(refQuery);
		}

		for (Attribute attribute : node.getAttributes()) {
			if (ModelHelper.isVariable(rule, attribute)) {
				ParameterConstraint parQuery = new ParameterConstraint(
						conditionHandler, var, attribute.getValue(), attribute
								.getType());
				var.addQuery(parQuery);
			} else {
				Object attributeValue = null;

				try {
					attributeValue = scriptEngine.eval(attribute.getValue());
					attributeValue = ModelHelper.castDown(attributeValue,
							attribute.getType().getEType()
									.getInstanceClassName());
				} catch (ScriptException ex) {
					ex.printStackTrace();
				}

				AttributeConstraint attrQuery = new AttributeConstraint(var,
						attribute.getType(), attributeValue);
				var.addQuery(attrQuery);
			}
		}

		if (mapping != null) {
			Node premiseNode = ModelHelper.getRemoteNode(mapping, node);
			if (premiseNode != null) {
				Variable premiseVariable = node2variable.get(premiseNode);
				InstanciationConstraint instanceQuery = new InstanciationConstraint(
						premiseVariable, var);
				premiseVariable.addACQuery(instanceQuery);
			}
		}
	}

	public void setAssignments(Map<String, Object> assignments) {
		if (assignments == null)
			return;

		for (org.eclipse.emf.henshin.model.Variable var : rule.getVariables()) {
			String name = var.getName();
			if (assignments.keySet().contains(name)) {
				conditionHandler.setParameter(name, assignments.get(name));
				conditionHandler.fixParameter(name);
			}
		}
	}

	public void setMatchObjects(Map<Node, EObject> prematch) {
		if (prematch == null)
			return;

		for (Node node : prematch.keySet()) {
			node2variable.get(node).enable(prematch.get(node));
		}
	}

	public RuleMatch getMatch() {
		Match match = matchfinder.getNextMatch();

		if (match != null)
			return new RuleMatch(rule, match, node2variable);

		return null;
	}

	public List<RuleMatch> getAllMatches() {
		List<Match> matches = matchfinder.getAllMatches();
		List<RuleMatch> ruleMatches = new ArrayList<RuleMatch>();
		for (Match match : matches) {
			ruleMatches.add(new RuleMatch(rule, match, node2variable));
		}

		return ruleMatches;
	}
}