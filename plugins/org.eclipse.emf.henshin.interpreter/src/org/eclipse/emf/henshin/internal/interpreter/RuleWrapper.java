/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;

public class RuleWrapper {
	private Rule rule;

	private Map<Node, Variable> node2variable;
	private Map<Variable, Node> variable2node;

	private Map<Graph, List<Variable>> graph2variables;
	private Map<Variable, Variable> variable2mainVariable;
	private List<Variable> mainVariables;

	private AttributeConditionHandler conditionHandler;
	private ScriptEngine scriptEngine;

	public RuleWrapper(Rule rule, ScriptEngine scriptEngine) {
		this.rule = rule;
		this.scriptEngine = scriptEngine;

		this.node2variable = new HashMap<Node, Variable>();
		this.variable2node = new HashMap<Variable, Node>();
		this.graph2variables = new HashMap<Graph, List<Variable>>();
		this.variable2mainVariable = new HashMap<Variable, Variable>();

		createVariables(rule.getLhs());
		translateFormula(rule.getLhs().getFormula());

		createVariableDependencies(rule.getMappings());
		createVariableDependencies(rule.getLhs().getFormula());

		mainVariables = new ArrayList<Variable>(variable2node.keySet());
		mainVariables.removeAll(variable2mainVariable.keySet());

		List<String> ruleParameters = new ArrayList<String>();
		for (Parameter parameter : rule.getParameters()) {
			ruleParameters.add(parameter.getName());
		}

		List<String> conditionStrings = new ArrayList<String>();
		for (AttributeCondition condition : rule.getAttributeConditions()) {
			conditionStrings.add(condition.getConditionText());
		}

		conditionHandler = new AttributeConditionHandler(
				scriptEngine, ruleParameters, conditionStrings);
	}

	private void translateNestedCondition(NestedCondition condition) {
		createVariables(condition.getConclusion());
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

	private void createVariables(Graph g) {
		List<Variable> variables = new ArrayList<Variable>();

		for (Node node : g.getNodes()) {
			EClass type = node.getType();
			Variable var = new Variable(type);
			variables.add(var);
			node2variable.put(node, var);
			variable2node.put(var, node);
		}

		for (Node node : g.getNodes()) {
			createConstraints(node);
		}

		graph2variables.put(g, variables);
	}

	private Variable getMainVariable(Variable var) {
		Variable predecessor = variable2mainVariable.get(var);

		return (predecessor == null) ? var : getMainVariable(predecessor);
	}

	private void createVariableDependencies(List<Mapping> mappings) {
		if (mappings != null) {
			for (Mapping mapping : mappings) {
				Variable sourceVar = node2variable.get(mapping.getOrigin());
				Variable targetVar = node2variable.get(mapping.getImage());

				variable2mainVariable
						.put(targetVar, getMainVariable(sourceVar));
			}
		}
	}

	private void createVariableDependencies(Formula formula) {
		if (formula instanceof BinaryFormula) {
			createVariableDependencies(((BinaryFormula) formula).getLeft());
			createVariableDependencies(((BinaryFormula) formula).getRight());
		} else if (formula instanceof UnaryFormula)
			createVariableDependencies(((UnaryFormula) formula).getChild());
		else if (formula instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) formula;
			createVariableDependencies(nc.getMappings());
			createVariableDependencies(nc.getConclusion().getFormula());
		}
	}

	private void createConstraints(Node node) {
		Variable var = node2variable.get(node);

		for (Edge edge : node.getOutgoing()) {
			Variable targetVariable = node2variable.get(edge.getTarget());
			ReferenceConstraint constraint = new ReferenceConstraint(
					targetVariable, edge.getType());
			var.getReferenceConstraints().add(constraint);
		}

		for (Attribute attribute : node.getAttributes()) {
			if (ModelHelper.attributeIsParameter(rule, attribute)) {
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

	public List<Variable> getMainVariables() {
		return mainVariables;
	}

	public Rule getRule() {
		return rule;
	}

	public AttributeConditionHandler getConditionHandler() {
		return conditionHandler;
	}
}