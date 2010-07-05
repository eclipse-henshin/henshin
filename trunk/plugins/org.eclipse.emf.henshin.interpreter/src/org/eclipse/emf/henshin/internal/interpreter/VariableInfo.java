package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
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

public class VariableInfo {

	// variables which represent nodes when they are first introduced, e.g. if a
	// mapped node occurs in the LHS and in one condition, only the variable
	// representing the LHS node will be in this collection
	private Collection<Variable> mainVariables;

	// node-variable pair
	private Map<Node, Variable> node2variable;

	// variable-node pair
	private Map<Variable, Node> variable2node;

	// map between a graph and all variables corresponding to nodes of that
	// graph
	private Map<Graph, List<Variable>> graph2variables;

	// map between a key variable and its main variable, e.g. there is a mapping
	// chain from the node belonging to the main variable to the key variable
	private Map<Variable, Variable> variable2mainVariable;

	private Rule rule;
	private ScriptEngine scriptEngine;

	public VariableInfo(RuleInfo ruleInfo, ScriptEngine scriptEngine) {
		this.rule = ruleInfo.getRule();
		this.scriptEngine = scriptEngine;

		this.node2variable = new HashMap<Node, Variable>();
		this.variable2node = new HashMap<Variable, Node>();

		this.graph2variables = new HashMap<Graph, List<Variable>>();
		this.variable2mainVariable = new HashMap<Variable, Variable>();

		createVariables(rule.getLhs(), null);
		
		mainVariables = variable2mainVariable.values();
	}

	private void createVariables(Graph g, Collection<Mapping> mappings) {
		List<Variable> variables = new ArrayList<Variable>();

		for (Node node : g.getNodes()) {
			EClass type = node.getType();
			Variable var = new Variable(type);
			variables.add(var);
			node2variable.put(node, var);
			variable2node.put(var, node);

			Variable mainVariable = var;
			if (mappings != null) {
				for (Mapping mapping : mappings) {
					if (node == mapping.getImage()) {
						mainVariable = variable2mainVariable.get(node2variable
								.get(mapping.getOrigin()));
					}
				}
			}
			
			variable2mainVariable.put(var, mainVariable);
		}

		for (Node node : g.getNodes()) {
			createConstraints(node);
		}

		graph2variables.put(g, variables);

		createVariables(g.getFormula());
	}

	private void createVariables(Formula formula) {
		if (formula instanceof BinaryFormula) {
			createVariables(((BinaryFormula) formula).getLeft());
			createVariables(((BinaryFormula) formula).getRight());
		} else if (formula instanceof UnaryFormula)
			createVariables(((UnaryFormula) formula).getChild());
		else if (formula instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) formula;
			createVariables(nc.getConclusion(), nc.getMappings());
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

	public Node getVariableForNode(Variable variable) {
		return variable2node.get(variable);
	}

	public Collection<Variable> getDependendVariables(Variable mainVariable) {
		Collection<Variable> dependendVariables = new HashSet<Variable>();
		for (Variable var : variable2mainVariable.keySet()) {
			if (variable2mainVariable.get(var) == mainVariable)
				dependendVariables.add(var);
		}

		return dependendVariables;
	}

	public Collection<Variable> getMainVariables() {
		return mainVariables;
	}

	/**
	 * @return the graph2variables
	 */
	public Map<Graph, List<Variable>> getGraph2variables() {
		return graph2variables;
	}

	/**
	 * @return the node2variable
	 */
	public Map<Node, Variable> getNode2variable() {
		// TODO: change Solution to get rid of this method
		return node2variable;
	}
}
