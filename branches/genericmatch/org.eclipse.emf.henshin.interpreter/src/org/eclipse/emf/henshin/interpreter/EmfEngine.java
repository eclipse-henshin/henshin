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
package org.eclipse.emf.henshin.interpreter;

import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.change.EmfModelChange;
import org.eclipse.emf.henshin.internal.interpreter.ChangeInfo;
import org.eclipse.emf.henshin.internal.interpreter.EmfRuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * The default implementation of an interpreter engine.
 */
public class EmfEngine extends GenericEngine<EClass, EObject> {

	public EmfEngine() {

	}

	public EmfEngine(EmfGraph graph) {
		super(graph);
	}

	@Override
	protected RuleInfo<EClass, EObject> createRuleInfo(Rule rule,
			ScriptEngine scriptEngine) {
		return new EmfRuleInfo(rule, scriptEngine);
	}

	@Override
	protected Match executeModelChanges(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleInfo<EClass, EObject> ruleInfo = ruleInformation.get(rule);
		ChangeInfo changeInfo = ruleInfo.getChangeInfo();

		Match match = ruleApplication.getMatch();

		if (!match.isComplete())
			return null;

		EmfModelChange modelChange = new EmfModelChange();

		Map<Node, EObject> matchNodeMapping = match.getNodeMapping();
		Map<Node, EObject> comatchNodeMapping = new HashMap<Node, EObject>();
		Map<Parameter, Object> comatchParameterValues = new HashMap<Parameter, Object>();
		comatchParameterValues.putAll(match.getParameterValues());

		// create new EObjects with their attributes
		for (Node node : changeInfo.getCreatedNodes()) {

			EClass type = node.getType();
			EPackage ePackage = type.getEPackage();

			EObject newObject = ePackage.getEFactoryInstance().create(type);
			modelChange.addCreatedObject(newObject);
			graph.addEObject(newObject);

			comatchNodeMapping.put(node, newObject);

			// add an assignment for parameters
			if (node.getName() != null && !node.getName().isEmpty()) {
				Parameter parameter = rule.getParameterByName(node.getName());
				if (parameter != null) {
					comatchParameterValues.put(parameter, newObject);
				}
			}
		}

		// delete EObjects
		for (Node node : changeInfo.getDeletedNodes()) {
			modelChange.addDeletedObject((EObject) match.getNodeMapping().get(
					node));
			graph.removeEObject((EObject) match.getNodeMapping().get(node));
		}

		for (Node node : changeInfo.getPreservedNodes()) {
			Node lhsNode = ModelHelper.getRemoteNode(rule.getMappings(), node);
			EObject targetObject = matchNodeMapping.get(lhsNode);
			comatchNodeMapping.put(node, targetObject);

			// add an assignment for node parameters which will be used to
			// update ports
			if (node.getName() != null && !node.getName().isEmpty()) {
				Parameter parameter = rule.getParameterByName(node.getName());
				if (parameter != null) {
					comatchParameterValues.put(parameter, targetObject);
				}
			}
		}

		// remove deleted edges
		for (Edge edge : changeInfo.getDeletedEdges()) {
			modelChange.addObjectChange(matchNodeMapping.get(edge.getSource()),
					edge.getType(), matchNodeMapping.get(edge.getTarget()));

		}

		// add new edges
		for (Edge edge : changeInfo.getCreatedEdges()) {
			modelChange.addObjectChange(comatchNodeMapping
					.get(edge.getSource()), edge.getType(), comatchNodeMapping
					.get(edge.getTarget()));
		}

		for (Attribute attribute : changeInfo.getAttributeChanges()) {
			EObject targetObject = comatchNodeMapping.get(attribute.getNode());
			Object value = evalExpression(match.getParameterValues(), attribute
					.getValue());

			String valueString = null;
			// workaround for Double conversion
			if (value != null) {
				valueString = value.toString();
				if (valueString.endsWith(".0"))
					valueString = valueString.substring(0,
							valueString.length() - 2);
			}

			value = EcoreUtil.createFromString(attribute.getType()
					.getEAttributeType(), valueString);

			modelChange.addObjectChange(targetObject, attribute.getType(),
					value);
		}

		modelChange.applyChanges();
		ruleApplication.setModelChange(modelChange);

		return new Match(rule, comatchParameterValues, comatchNodeMapping);
	}

	@Override
	public RuleApplication generateAmalgamationRule(
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {

		return null;
	}

	@Override
	public void redoChanges(RuleApplication ruleApplication) {
		EmfModelChange modelChange = ruleApplication.getModelChange();

		for (EObject createdObject : modelChange.getCreatedObjects()) {
			graph.addEObject(createdObject);
		}

		for (EObject deletedObject : modelChange.getDeletedObjects()) {
			graph.removeEObject(deletedObject);
		}

		modelChange.redoChanges();
	}

	@Override
	public void undoChanges(RuleApplication ruleApplication) {
		EmfModelChange modelChange = ruleApplication.getModelChange();

		modelChange.undoChanges();

		for (EObject deletedObject : modelChange.getDeletedObjects()) {
			graph.addEObject(deletedObject);
		}

		for (EObject createdObject : modelChange.getCreatedObjects()) {
			graph.removeEObject(createdObject);
		}
	}

	public void setEmfGraph(EmfGraph graph) {
		this.graph = graph;
	}
}