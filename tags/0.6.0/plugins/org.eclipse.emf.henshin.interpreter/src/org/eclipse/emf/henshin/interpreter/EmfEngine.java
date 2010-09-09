/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.common.util.TransformationOptions;
import org.eclipse.emf.henshin.internal.change.ModelChange;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.conditions.nested.AndFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.ApplicationCondition;
import org.eclipse.emf.henshin.internal.conditions.nested.IFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.NotFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.OrFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.TrueFormula;
import org.eclipse.emf.henshin.internal.interpreter.AmalgamationInfo;
import org.eclipse.emf.henshin.internal.interpreter.ChangeInfo;
import org.eclipse.emf.henshin.internal.interpreter.ConditionInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.VariableInfo;
import org.eclipse.emf.henshin.internal.matching.DomainSlot;
import org.eclipse.emf.henshin.internal.matching.Matchfinder;
import org.eclipse.emf.henshin.internal.matching.Solution;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * The default implementation of an interpreter engine.
 */
public class EmfEngine implements InterpreterEngine {
	EmfGraph emfGraph;
	ScriptEngine scriptEngine;

	Map<Rule, RuleInfo> ruleInformation;
	Map<AmalgamationUnit, AmalgamationInfo> amalgamationInformation;
	TransformationOptions options;

	public EmfEngine() {
		ruleInformation = new HashMap<Rule, RuleInfo>();

		ScriptEngineManager mgr = new ScriptEngineManager();
		scriptEngine = mgr.getEngineByName("JavaScript");

		options = new TransformationOptions();
	}

	public EmfEngine(EmfGraph emfGraph) {
		this();

		this.emfGraph = emfGraph;
	}

	private Matchfinder prepareMatchfinder(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleInfo ruleInfo = ruleInformation.get(rule);
		ConditionInfo conditionInfo = ruleInfo.getConditionInfo();
		VariableInfo variableInfo = ruleInfo.getVariableInfo();

		Map<Parameter, Object> parameterValues = ruleApplication.getMatch()
				.getParameterValues();

		Map<Node, EObject> prematch = ModelHelper.createPrematch(rule, parameterValues);
		Map<Node, EObject> rulePrematch = ruleApplication.getMatch().getNodeMapping();
		
		for (Node node: rulePrematch.keySet()) {
			prematch.put(node, rulePrematch.get(node));
		}

		// evaluates attribute conditions of the rule
		AttributeConditionHandler conditionHandler = new AttributeConditionHandler(
				conditionInfo.getConditionParameters(), scriptEngine);

		// usedObjects ensures injective matching by removing already
		// matched objects from other DomainSlots
		Collection<EObject> usedObjects = new HashSet<EObject>();

		// Creates a domain map where all variables are mapped to slots.
		// Different variables may share one domain slot, if there is a mapping
		// between the nodes of the variables.
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();
		for (Variable mainVariable : variableInfo.getMainVariables()) {
			Node node = variableInfo.getVariableForNode(mainVariable);

			// use injective, deterministic matching for nested conditions
			TransformationOptions options = getOptions();
			if (node.getGraph() != ruleInfo.getRule().getLhs()) {
				options = new TransformationOptions();
				options.setDeterministic(true);
				options.setInjective(true);
				options.setDangling(false);
			}

			DomainSlot domainSlot = new DomainSlot(conditionHandler,
					usedObjects, options);

			if (prematch.get(node) != null)
				domainSlot.fixInstanciation(prematch.get(node));

			for (Variable dependendVariable : variableInfo
					.getDependendVariables(mainVariable)) {
				domainMap.put(dependendVariable, domainSlot);
			}
		}

		if (parameterValues != null) {
			for (Parameter parameter : parameterValues.keySet()) {
				conditionHandler.setParameter(parameter.getName(),
						parameterValues.get(parameter));
			}
		}

		Map<Graph, List<Variable>> graphMap = variableInfo.getGraph2variables();
		Matchfinder matchfinder = new Matchfinder(emfGraph, domainMap,
				conditionHandler);
		matchfinder.setVariables(graphMap.get(rule.getLhs()));
		matchfinder.setFormula(initFormula(rule.getLhs().getFormula(),
				domainMap, graphMap, conditionHandler));

		return matchfinder;
	}

	private IFormula initFormula(Formula formula,
			Map<Variable, DomainSlot> domainMap,
			Map<Graph, List<Variable>> graphMap,
			AttributeConditionHandler conditionHandler) {
		if (formula instanceof And) {
			And and = (And) formula;
			IFormula left = initFormula(and.getLeft(), domainMap, graphMap,
					conditionHandler);
			IFormula right = initFormula(and.getRight(), domainMap, graphMap,
					conditionHandler);
			AndFormula andFormula = new AndFormula(left, right);

			return andFormula;
		} else if (formula instanceof Or) {
			Or or = (Or) formula;
			IFormula left = initFormula(or.getLeft(), domainMap, graphMap,
					conditionHandler);
			IFormula right = initFormula(or.getRight(), domainMap, graphMap,
					conditionHandler);
			OrFormula orFormula = new OrFormula(left, right);

			return orFormula;
		} else if (formula instanceof Not) {
			Not not = (Not) formula;
			IFormula child = initFormula(not.getChild(), domainMap, graphMap,
					conditionHandler);
			NotFormula notFormula = new NotFormula(child);

			return notFormula;
		} else if (formula instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) formula;
			IFormula ac = initApplicationCondition(nc, domainMap, graphMap,
					conditionHandler);

			return ac;
		}

		return new TrueFormula();
	}

	private ApplicationCondition initApplicationCondition(NestedCondition nc,
			Map<Variable, DomainSlot> domainMap,
			Map<Graph, List<Variable>> graphMap,
			AttributeConditionHandler conditionHandler) {
		ApplicationCondition ac = new ApplicationCondition(emfGraph, domainMap,
				nc.isNegated());
		ac.setVariables(graphMap.get(nc.getConclusion()));
		ac.setFormula(initFormula(nc.getConclusion().getFormula(), domainMap,
				graphMap, conditionHandler));

		return ac;
	}

	@Override
	public List<Match> findAllMatches(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();

		RuleInfo wrapper = ruleInformation.get(rule);

		if (wrapper == null) {
			wrapper = new RuleInfo(rule, scriptEngine);
			ruleInformation.put(rule, wrapper);
		}

		Matchfinder matchfinder = prepareMatchfinder(ruleApplication);

		List<Solution> solutions = matchfinder.getAllMatches();
		List<Match> matches = new ArrayList<Match>();
		for (Solution solution : solutions) {
			Match match = new Match(rule, solution, wrapper.getVariableInfo()
					.getNode2variable());
			matches.add(match);
		}

		return matches;
	}

	public Match findMatch(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleInfo wrapper = ruleInformation.get(rule);

		if (wrapper == null) {
			wrapper = new RuleInfo(rule, scriptEngine);
			ruleInformation.put(rule, wrapper);
		}

		if (!ruleApplication.getMatch().isComplete()
				|| rule.getLhs().getNodes().size() == 0) {
			Matchfinder matchfinder = prepareMatchfinder(ruleApplication);
			Solution solution = matchfinder.getNextMatch();

			if (solution != null) {
				Match match = new Match(rule, solution, wrapper
						.getVariableInfo().getNode2variable());
				return match;
			} else
				return null;
		} else {
			return ruleApplication.getMatch();
		}
	}

	public RuleApplication generateAmalgamationRule(
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {

		AmalgamationInfo amalgamationWrapper = new AmalgamationInfo(this,
				amalgamationUnit, parameterValues);

		return amalgamationWrapper.getAmalgamationRule();
	}

	@Override
	public boolean applyRule(RuleApplication ruleApplication) {
		Match match = findMatch(ruleApplication);
		if (match != null) {
			ruleApplication.setMatch(match);
			Match comatch = executeModelChanges(ruleApplication);
			ruleApplication.setComatch(comatch);

			return true;
		}

		return false;
	}

	public void purgeCache() {
		ruleInformation.clear();
		amalgamationInformation.clear();
	}

	/**
	 * @return the emfGraph
	 */
	public EmfGraph getEmfGraph() {
		return emfGraph;
	}

	public void setEmfGraph(EmfGraph emfGraph) {
		this.emfGraph = emfGraph;
	}

	@Override
	public void setOptions(TransformationOptions options) {
		this.options = options;
	}

	public TransformationOptions getOptions() {
		return options;
	}

	/**
	 * Computes all necessary model changes resulting from the current match.
	 * Note that this method doesn't actually change the model.
	 * 
	 * @return the comatch from the RHS into the instance
	 */
	private Match executeModelChanges(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleInfo ruleInfo = ruleInformation.get(rule);
		ChangeInfo changeInfo = ruleInfo.getChangeInfo();

		Match match = ruleApplication.getMatch();

		if (!match.isComplete())
			return null;

		ModelChange modelChange = new ModelChange();

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
			emfGraph.addEObject(newObject);

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
			modelChange.addDeletedObject(match.getNodeMapping().get(node));
			emfGraph.removeEObject(match.getNodeMapping().get(node));
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
					edge.getType(), matchNodeMapping.get(edge.getTarget()),
					true);
		}

		// add new edges
		for (Edge edge : changeInfo.getCreatedEdges()) {
			modelChange.addObjectChange(comatchNodeMapping
					.get(edge.getSource()), edge.getType(), comatchNodeMapping
					.get(edge.getTarget()), false);
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
					value, false);
		}

		modelChange.applyChanges();
		ruleApplication.setModelChange(modelChange);

		return new Match(rule, comatchParameterValues, comatchNodeMapping);
	}

	/**
	 * Evaluates the given Java-Expression.
	 * 
	 * @param expr
	 *            An expression string.
	 * @return The result of the computation.
	 */
	private Object evalExpression(Map<Parameter, Object> parameterMapping,
			String expr) {
		try {
			for (Parameter parameter : parameterMapping.keySet()) {
				scriptEngine.put(parameter.getName(), parameterMapping
						.get(parameter));
			}

			return scriptEngine.eval(expr);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void redoChanges(RuleApplication ruleApplication) {
		ModelChange modelChange = ruleApplication.getModelChange();

		for (EObject createdObject : modelChange.getCreatedObjects()) {
			emfGraph.addEObject(createdObject);
		}

		for (EObject deletedObject : modelChange.getDeletedObjects()) {
			emfGraph.removeEObject(deletedObject);
		}

		modelChange.redoChanges();
	}

	@Override
	public void undoChanges(RuleApplication ruleApplication) {
		ModelChange modelChange = ruleApplication.getModelChange();

		modelChange.undoChanges();

		for (EObject deletedObject : modelChange.getDeletedObjects()) {
			emfGraph.addEObject(deletedObject);
		}

		for (EObject createdObject : modelChange.getCreatedObjects()) {
			emfGraph.removeEObject(createdObject);
		}
	}
}