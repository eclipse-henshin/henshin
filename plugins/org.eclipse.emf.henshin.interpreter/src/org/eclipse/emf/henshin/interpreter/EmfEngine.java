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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.emf.henshin.internal.interpreter.AmalgamationWrapper;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleWrapper;
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
	TransformationOptions options;

	Map<Rule, RuleWrapper> rule2wrapper;
	Map<Rule, RuleInfo> rule2ruleInfo;

	EmfGraph emfGraph;
	ScriptEngine scriptEngine;

	public EmfEngine() {
		this.emfGraph = new EmfGraph();

		rule2wrapper = new HashMap<Rule, RuleWrapper>();
		rule2ruleInfo = new HashMap<Rule, RuleInfo>();

		ScriptEngineManager mgr = new ScriptEngineManager();
		scriptEngine = mgr.getEngineByName("JavaScript");

		options = new TransformationOptions();
	}

	public EmfEngine(EmfGraph emfGraph) {
		this.emfGraph = emfGraph;

		rule2wrapper = new HashMap<Rule, RuleWrapper>();
		rule2ruleInfo = new HashMap<Rule, RuleInfo>();

		ScriptEngineManager mgr = new ScriptEngineManager();
		scriptEngine = mgr.getEngineByName("JavaScript");

		options = new TransformationOptions();
	}

	private Map<Variable, DomainSlot> createDomainMap(RuleWrapper wrapper,
			Map<Node, EObject> prematch) {
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();
		Map<Variable, Variable> mainVariableMap = wrapper
				.getVariable2mainVariable();
		AttributeConditionHandler conditionHandler = wrapper
				.getConditionHandler();

		Set<EObject> usedObjects = new HashSet<EObject>();

		TransformationOptions defaultOptions = new TransformationOptions();

		for (Variable var : wrapper.getMainVariables()) {
			Node node = wrapper.getVariable2node().get(var);

			DomainSlot slot;
			if (node.getGraph() == wrapper.getRule().getLhs()) {
				slot = (prematch.get(node) == null) ? new DomainSlot(var,
						conditionHandler, usedObjects, options)
						: new DomainSlot(prematch.get(node), conditionHandler,
								usedObjects, options);
			} else {
				slot = (prematch.get(node) == null) ? new DomainSlot(var,
						conditionHandler, usedObjects, defaultOptions)
						: new DomainSlot(prematch.get(node), conditionHandler,
								usedObjects, defaultOptions);
			}

			domainMap.put(var, slot);
		}

		for (Variable var : mainVariableMap.keySet()) {
			Variable mainVariable = mainVariableMap.get(var);
			DomainSlot slot = domainMap.get(mainVariable);
			domainMap.put(var, slot);
		}

		return domainMap;
	}

	private Matchfinder prepareMatchfinder(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		Map<Parameter, Object> assignments = ruleApplication.getMatch()
				.getParameterValues();
		Map<Node, EObject> prematch = ruleApplication.getMatch()
				.getNodeMapping();

		RuleWrapper wrapper = rule2wrapper.get(rule);
		AttributeConditionHandler handler = wrapper.getConditionHandler();
		handler.clear();

		if (assignments != null) {
			for (Parameter parameter : assignments.keySet()) {
				wrapper.getConditionHandler().setParameter(parameter.getName(),
						assignments.get(parameter));
			}
		}

		Map<Variable, DomainSlot> domainMap = createDomainMap(wrapper, prematch);
		Map<Graph, List<Variable>> graphMap = wrapper.getGraph2variables();

		Matchfinder matchfinder = new Matchfinder(emfGraph, domainMap, handler);
		matchfinder.setVariables(graphMap.get(rule.getLhs()));
		matchfinder.setFormula(initFormula(rule.getLhs().getFormula(),
				domainMap, graphMap, handler));

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

		RuleWrapper wrapper = rule2wrapper.get(rule);

		if (wrapper == null) {
			wrapper = new RuleWrapper(rule, scriptEngine);
			rule2wrapper.put(rule, wrapper);
		}

		Matchfinder matchfinder = prepareMatchfinder(ruleApplication);

		List<Solution> solutions = matchfinder.getAllMatches();
		List<Match> matches = new ArrayList<Match>();
		for (Solution solution : solutions) {
			Match match = new Match(rule, solution, wrapper.getNode2variable());
			matches.add(match);
		}

		return matches;
	}

	public Match findMatch(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleWrapper wrapper = rule2wrapper.get(rule);

		if (wrapper == null) {
			wrapper = new RuleWrapper(rule, scriptEngine);
			rule2wrapper.put(rule, wrapper);
		}

		Matchfinder matchfinder = prepareMatchfinder(ruleApplication);
		Solution solution = matchfinder.getNextMatch();

		if (solution != null) {
			Match match = new Match(rule, solution, wrapper.getNode2variable());
			return match;
		} else
			return null;
	}

	public RuleApplication generateAmalgamationRule(
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {

		AmalgamationWrapper amalgamationWrapper = new AmalgamationWrapper(this,
				amalgamationUnit, parameterValues);

		return amalgamationWrapper.getAmalgamatedRule();
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

	public void purgeRuleCache() {
		rule2ruleInfo.clear();
		rule2wrapper.clear();
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
		Match match = ruleApplication.getMatch();

		if (!match.isComplete())
			return null;

		ModelChange modelChange = new ModelChange();

		RuleInfo ruleInfo = rule2ruleInfo.get(rule);
		if (ruleInfo == null) {
			ruleInfo = new RuleInfo(rule);
			rule2ruleInfo.put(rule, ruleInfo);
		}

		Map<Node, EObject> matchNodeMapping = match.getNodeMapping();
		Map<Node, EObject> comatchNodeMapping = new HashMap<Node, EObject>();
		Map<Parameter, Object> comatchParameterValues = new HashMap<Parameter, Object>();
		comatchParameterValues.putAll(match.getParameterValues());

		// create new EObjects with their attributes
		for (Node node : ruleInfo.getCreatedNodes()) {

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
		for (Node node : ruleInfo.getDeletedNodes()) {
			modelChange.addDeletedObject(match.getNodeMapping().get(node));
			emfGraph.removeEObject(match.getNodeMapping().get(node));
		}

		for (Node node : ruleInfo.getPreservedNodes()) {
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
		for (Edge edge : ruleInfo.getDeletedEdges()) {
			modelChange.addObjectChange(matchNodeMapping.get(edge.getSource()),
					edge.getType(), matchNodeMapping.get(edge.getTarget()));

		}

		// add new edges
		for (Edge edge : ruleInfo.getCreatedEdges()) {
			modelChange.addObjectChange(comatchNodeMapping
					.get(edge.getSource()), edge.getType(), comatchNodeMapping
					.get(edge.getTarget()));
		}

		for (Attribute attribute : ruleInfo.getAttributeChanges()) {
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