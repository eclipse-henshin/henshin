/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ContainmentConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DanglingConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Attribute;
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
	private EngineImpl engine;
	
	public VariableInfo(RuleInfo ruleInfo, EngineImpl engine) {
		this.rule = ruleInfo.getRule();
		this.engine = engine;
		
		this.node2variable = new HashMap<Node, Variable>();
		this.variable2node = new HashMap<Variable, Node>();
		
		this.graph2variables = new HashMap<Graph, List<Variable>>();
		this.variable2mainVariable = new HashMap<Variable, Variable>();
		
		createVariables(rule.getLhs(), null);
		
		for (Node node : rule.getLhs().getNodes()) {
			if (rule.getMappings().getImage(node,rule.getRhs())==null)
				createDanglingConstraints(node);
		}
		
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
						mainVariable = variable2mainVariable.get(node2variable.get(mapping
								.getOrigin()));
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
			ReferenceConstraint constraint = new ReferenceConstraint(targetVariable, edge.getType());
			var.referenceConstraints.add(constraint);
		}
		
		for (Edge edge : node.getIncoming()) {
			if (edge.getType().isContainment()) {
				Variable targetVariable = node2variable.get(edge.getSource());
				ContainmentConstraint constraint = new ContainmentConstraint(targetVariable);
				var.containmentConstraints.add(constraint);
			}
		}
		
		for (Attribute attribute : node.getAttributes()) {
			if (attributeIsParameter(rule, attribute)) {
				ParameterConstraint constraint = new ParameterConstraint(attribute.getValue(),
						attribute.getType());
				var.parameterConstraints.add(constraint);
			} else {
				Object value = engine.evalAttributeExpression(attribute);
				AttributeConstraint constraint = new AttributeConstraint(attribute.getType(), value);
				var.attributeConstraints.add(constraint);
			}
		}
	}
	
	private void createDanglingConstraints(Node node) {
		Variable var = node2variable.get(node);
		DanglingConstraint constraint = new DanglingConstraint(getEdgeCounts(node, false),
				getEdgeCounts(node, true));
		var.danglingConstraints.add(constraint);
	}
	
	private Map<EReference, Integer> getEdgeCounts(Node node, boolean incoming) {
		Collection<Edge> edges = incoming ? node.getIncoming() : node.getOutgoing();
		Collection<Edge> oppositeEdges = incoming ? node.getOutgoing() : node.getIncoming();
		
		if (edges.size() == 0)
			return null;
		
		Map<EReference, Integer> edgeCount = new HashMap<EReference, Integer>();
		for (Edge edge : edges) {
			EReference type = edge.getType();
			Integer count = edgeCount.get(type);
			if (count == null) {
				count = ONE;
			} else {
				count = count + 1;
			}
			edgeCount.put(type, count);
		}
		
		for (Edge edge : oppositeEdges) {
			if (edge.getType().getEOpposite() == null)
				continue;
			
			EReference oppType = edge.getType().getEOpposite();
			
			if (incoming) {
				// opposite edges are outgoing from the PoV of the node
				Node remoteNode = edge.getTarget();
				if (remoteNode.getOutgoing(oppType, node) == null) {
					Integer count = edgeCount.get(oppType);
					if (count == null) {
						count = ONE;
					} else {
						count = count + 1;
					}
					edgeCount.put(oppType, count);
				}
			} else {
				// opposite edges are incoming from the PoV of the node
				Node remoteNode = edge.getSource();
				if (node.getOutgoing(oppType, remoteNode) == null) {
					Integer count = edgeCount.get(oppType);
					if (count == null) {
						count = ONE;
					} else {
						count = count + 1;
					}
					edgeCount.put(oppType, count);
				}
			}
		}
		
		return edgeCount;
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
	
	/**
	 * Checks whether the value of the given attribute corresponds to a
	 * parameter of the rule.
	 * 
	 * @param rule
	 *            The rule which contains the parameters.
	 * @param attribute
	 *            The attribute, which value should be checked.
	 * @return true, if the attribute value equals a parameter name.
	 */
	private static boolean attributeIsParameter(Rule rule, Attribute attribute) {
		String potentialParameter = attribute.getValue();
		boolean found = false;
		for (Parameter parameter : rule.getParameters()) {
			found = parameter.getName().equals(potentialParameter);
			if (found)
				break;
		}
		return found;
	}
	
	private static final Integer ONE = new Integer(1);
	
}
