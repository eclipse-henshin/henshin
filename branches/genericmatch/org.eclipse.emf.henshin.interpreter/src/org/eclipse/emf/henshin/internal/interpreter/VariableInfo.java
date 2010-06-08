package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.constraints.TypeConstraint;
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

public abstract class VariableInfo<TType, TNode> {

	// variables which represent nodes when they are first introduced, e.g. if a
	// mapped node occurs in the LHS and in one condition, only the variable
	// representing the LHS node will be in this collection
	protected Collection<Variable<TType, TNode>> mainVariables;

	// node-variable pair
	protected Map<Node, Variable<TType, TNode>> node2variable;

	// variable-node pair
	protected Map<Variable<TType, TNode>, Node> variable2node;

	// map between a graph and all variables corresponding to nodes of that
	// graph
	protected Map<Graph, List<Variable<TType, TNode>>> graph2variables;

	// map between a key variable and its main variable, e.g. there is a mapping
	// chain from the node belonging to the main variable to the key variable
	protected Map<Variable<TType, TNode>, Variable<TType, TNode>> variable2mainVariable;

	protected Rule rule;
	protected ScriptEngine scriptEngine;

	protected VariableInfo() {

	}

	public VariableInfo(Rule rule, ScriptEngine scriptEngine) {
		this.rule = rule;
		this.scriptEngine = scriptEngine;

		this.node2variable = new HashMap<Node, Variable<TType, TNode>>();
		this.variable2node = new HashMap<Variable<TType, TNode>, Node>();

		this.graph2variables = new HashMap<Graph, List<Variable<TType, TNode>>>();
		this.variable2mainVariable = new HashMap<Variable<TType, TNode>, Variable<TType, TNode>>();

		createVariables(rule.getLhs(), null);

		mainVariables = variable2mainVariable.values();
	}

	protected abstract TypeConstraint<TType, TNode> createTypeConstraint(
			Node node);

	protected abstract ReferenceConstraint<TNode> createReferenceConstraint(
			Variable<TType, TNode> targetVariable, Edge edge);

	protected abstract AttributeConstraint<TNode> createAttributeConstraint(
			Attribute attribute, Object value);

	protected abstract ParameterConstraint<TNode> createParameterConstraint(
			Attribute attribute);

	protected void createVariables(Graph g, Collection<Mapping> mappings) {
		List<Variable<TType, TNode>> variables = new ArrayList<Variable<TType, TNode>>();

		for (Node node : g.getNodes()) {
			Variable<TType, TNode> var = new Variable<TType, TNode>();
			var.setTypeConstraint(createTypeConstraint(node));
			variables.add(var);
			node2variable.put(node, var);
			variable2node.put(var, node);

			Variable<TType, TNode> mainVariable = var;
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
		Variable<TType, TNode> var = node2variable.get(node);

		for (Edge edge : node.getOutgoing()) {
			Variable<TType, TNode> targetVariable = node2variable.get(edge
					.getTarget());

			var.getReferenceConstraints().add(
					createReferenceConstraint(targetVariable, edge));
		}

		for (Attribute attribute : node.getAttributes()) {
			if (ModelHelper.attributeIsParameter(rule, attribute)) {
				var.getParameterConstraints().add(
						createParameterConstraint(attribute));
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

				var.getAttributeConstraints().add(
						createAttributeConstraint(attribute, attributeValue));
			}
		}
	}

	public Node getVariableForNode(Variable<TType, TNode> variable) {
		return variable2node.get(variable);
	}

	public Collection<Variable<TType, TNode>> getDependendVariables(
			Variable<TType, TNode> mainVariable) {
		Collection<Variable<TType, TNode>> dependendVariables = new HashSet<Variable<TType, TNode>>();
		for (Variable<TType, TNode> var : variable2mainVariable.keySet()) {
			if (variable2mainVariable.get(var) == mainVariable)
				dependendVariables.add(var);
		}

		return dependendVariables;
	}

	public Collection<Variable<TType, TNode>> getMainVariables() {
		return mainVariables;
	}

	/**
	 * @return the graph2variables
	 */
	public Map<Graph, List<Variable<TType, TNode>>> getGraph2variables() {
		return graph2variables;
	}

	/**
	 * @return the node2variable
	 */
	public Map<Node, Variable<TType, TNode>> getNode2variable() {
		return node2variable;
	}
}
