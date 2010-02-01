package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.common.util.ModelHelper;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;

public class RuleWrapper {
	private Rule rule;

	private Map<Node, Variable> node2variable;
	private Map<Variable, Node> variable2node;

	private Map<Graph, List<Variable>> graph2variables;
	private Map<Variable, Variable> variable2mainVariable;

	public RuleWrapper(Rule rule) {
		this.rule = rule;

		this.node2variable = new HashMap<Node, Variable>();
		this.variable2node = new HashMap<Variable, Node>();
		this.graph2variables = new HashMap<Graph, List<Variable>>();
		this.variable2mainVariable = new HashMap<Variable, Variable>();

		createVariables(rule.getLhs(), null);
		translateFormula(rule.getLhs().getFormula());
	}

	private void translateNestedCondition(NestedCondition condition) {
		createVariables(condition.getConclusion(), condition.getMappings());
		translateFormula(condition.getConclusion().getFormula());
	}

	private void translateFormula(Formula formula) {
		if (formula instanceof BinaryFormula) {
			translateFormula(((BinaryFormula) formula).getLeft());
			translateFormula(((BinaryFormula) formula).getRight());
		} else if (formula instanceof UnaryFormula)
			translateFormula(((UnaryFormula) formula).getChild());
		else if (formula instanceof NestedCondition) {
			translateNestedCondition((NestedCondition) formula);
		}
	}

	private void createVariables(Graph g, List<Mapping> mappings) {
		List<Variable> variables = new ArrayList<Variable>();

		for (Node node : g.getNodes()) {
			EClass type = node.getType();
			Variable var = new Variable(type);
			variables.add(var);
			node2variable.put(node, var);
			variable2node.put(var, node);
		}

		for (Node node : g.getNodes()) {
			Variable var = node2variable.get(node);

			boolean isMinor = false;
			createConstraints(node);
			if (mappings != null) {
				for (Mapping mapping : mappings) {
					if (mapping.getImage() == node) {
						Variable mainVariable = node2variable.get(mapping
								.getOrigin());
						variable2mainVariable.put(var, mainVariable);
						isMinor = true;
					}
				}
			}
			if (!isMinor) {
				variable2mainVariable.put(var, var);
			}
		}

		graph2variables.put(g, variables);
	}

	private void createConstraints(Node node) {
		Variable var = node2variable.get(node);

		for (Edge edge : node.getOutgoing()) {
			Variable targetVariable = node2variable.get(edge.getTarget());
			ReferenceConstraint constraint = new ReferenceConstraint(
					targetVariable, edge.getType());
			var.getReferenceConstraints().add(constraint);
		}

		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");

		for (Attribute attribute : node.getAttributes()) {
			if (attribute.containsVariableByRule(rule)) {
				ParameterConstraint constraint = new ParameterConstraint(
						attribute.getValue(), attribute.getType());
				var.getParameterConstraints().add(constraint);
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

				AttributeConstraint constraint = new AttributeConstraint(
						attribute.getType(), attributeValue);
				var.getAttributeConstraints().add(constraint);
			}
		}
	}

	public Map<Variable, Variable> getVariable2mainVariable() {
		return variable2mainVariable;
	}

	public Map<Graph, List<Variable>> getGraph2variables() {
		return graph2variables;
	}

	public Map<Node, Variable> getNode2variable() {
		return node2variable;
	}

	public Map<Variable, Node> getVariable2node() {
		return variable2node;
	}
}