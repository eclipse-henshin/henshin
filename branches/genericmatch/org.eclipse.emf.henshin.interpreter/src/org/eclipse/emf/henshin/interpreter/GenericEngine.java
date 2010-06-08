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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.henshin.common.util.GraphSkeleton;
import org.eclipse.emf.henshin.common.util.TransformationOptions;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.conditions.nested.AndFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.ApplicationCondition;
import org.eclipse.emf.henshin.internal.conditions.nested.IFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.NotFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.OrFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.TrueFormula;
import org.eclipse.emf.henshin.internal.interpreter.AmalgamationInfo;
import org.eclipse.emf.henshin.internal.interpreter.ConditionInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.VariableInfo;
import org.eclipse.emf.henshin.internal.matching.DomainSlot;
import org.eclipse.emf.henshin.internal.matching.Matchfinder;
import org.eclipse.emf.henshin.internal.matching.Solution;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.And;
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
public abstract class GenericEngine<TType, TNode> implements InterpreterEngine {
	GraphSkeleton<TType, TNode> graph;
	ScriptEngine scriptEngine;

	Map<Rule, RuleInfo<TType, TNode>> ruleInformation;
	Map<AmalgamationUnit, AmalgamationInfo> amalgamationInformation;
	TransformationOptions options;

	public GenericEngine() {
		ruleInformation = new HashMap<Rule, RuleInfo<TType, TNode>>();

		ScriptEngineManager mgr = new ScriptEngineManager();
		scriptEngine = mgr.getEngineByName("JavaScript");

		options = new TransformationOptions();
	}

	public GenericEngine(GraphSkeleton<TType, TNode> graph) {
		this();

		this.graph = graph;
	}

	private Matchfinder<TType, TNode> prepareMatchfinder(
			RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleInfo<TType, TNode> ruleInfo = ruleInformation.get(rule);
		ConditionInfo conditionInfo = ruleInfo.getConditionInfo();
		VariableInfo<TType, TNode> variableInfo = ruleInfo.getVariableInfo();

		Map<Node, TNode> prematch = ruleApplication.getMatch().getNodeMapping();
		Map<Parameter, Object> parameterValues = ruleApplication.getMatch()
				.getParameterValues();

		// evaluates attribute conditions of the rule
		AttributeConditionHandler conditionHandler = new AttributeConditionHandler(
				conditionInfo.getConditionParameters(), scriptEngine);

		// usedObjects ensures injective matching by removing already
		// matched objects from other DomainSlots
		Collection<TNode> usedObjects = new HashSet<TNode>();

		// Creates a domain map where all variables are mapped to slots.
		// Different variables may share one domain slot, if there is a mapping
		// between the nodes of the variables.
		Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap = new HashMap<Variable<TType, TNode>, DomainSlot<TType, TNode>>();
		for (Variable<TType, TNode> mainVariable : variableInfo
				.getMainVariables()) {
			Node node = variableInfo.getVariableForNode(mainVariable);

			// use injective, deterministic matching for nested conditions
			TransformationOptions options = getOptions();
			if (node.getGraph() != ruleInfo.getRule().getLhs()) {
				options = new TransformationOptions();
				options.setDeterministic(true);
				options.setInjective(true);
			}

			DomainSlot<TType, TNode> domainSlot = new DomainSlot<TType, TNode>(conditionHandler, usedObjects, graph, options);

			if (prematch.get(node) != null)
				domainSlot.fixInstanciation(prematch.get(node));

			for (Variable<TType, TNode> dependendVariable : variableInfo
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

		Map<Graph, List<Variable<TType, TNode>>> graphMap = variableInfo
				.getGraph2variables();
		Matchfinder<TType, TNode> matchfinder = new Matchfinder<TType, TNode>(
				graph, domainMap, conditionHandler);
		matchfinder.setVariables(graphMap.get(rule.getLhs()));
		matchfinder.setFormula(initFormula(rule.getLhs().getFormula(),
				domainMap, graphMap, conditionHandler));

		return matchfinder;
	}

	private IFormula initFormula(Formula formula,
			Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap,
			Map<Graph, List<Variable<TType, TNode>>> graphMap,
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

	private ApplicationCondition<TType, TNode> initApplicationCondition(
			NestedCondition nc,
			Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap,
			Map<Graph, List<Variable<TType, TNode>>> graphMap,
			AttributeConditionHandler conditionHandler) {
		ApplicationCondition<TType, TNode> ac = new ApplicationCondition<TType, TNode>(
				graph, domainMap, nc.isNegated());
		ac.setVariables(graphMap.get(nc.getConclusion()));
		ac.setFormula(initFormula(nc.getConclusion().getFormula(), domainMap,
				graphMap, conditionHandler));

		return ac;
	}

	protected abstract RuleInfo<TType, TNode> createRuleInfo(Rule rule,
			ScriptEngine scriptEngine);

	@Override
	public List<Match> findAllMatches(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();

		RuleInfo<TType, TNode> wrapper = ruleInformation.get(rule);

		if (wrapper == null) {
			wrapper = createRuleInfo(rule, scriptEngine);
			ruleInformation.put(rule, wrapper);
		}

		Matchfinder<TType, TNode> matchfinder = prepareMatchfinder(ruleApplication);

		List<Solution<TType, TNode>> solutions = matchfinder.getAllMatches();
		List<Match> matches = new ArrayList<Match>();
		for (Solution<TType, TNode> solution : solutions) {
			Match match = new Match(rule, solution, wrapper.getVariableInfo()
					.getNode2variable());
			matches.add(match);
		}

		return matches;
	}

	public Match findMatch(RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		RuleInfo<TType, TNode> wrapper = ruleInformation.get(rule);

		if (wrapper == null) {
			wrapper = createRuleInfo(rule, scriptEngine);
			ruleInformation.put(rule, wrapper);
		}

		Matchfinder<TType, TNode> matchfinder = prepareMatchfinder(ruleApplication);
		Solution<TType, TNode> solution = matchfinder.getNextMatch();

		if (solution != null) {
			Match match = new Match(rule, solution, wrapper.getVariableInfo()
					.getNode2variable());
			return match;
		} else
			return null;
	}

	public abstract RuleApplication generateAmalgamationRule(
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues);

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
	 * @return the graph
	 */
	public GraphSkeleton<TType, TNode> getGraph() {
		return graph;
	}

	public void setGraph(GraphSkeleton<TType, TNode> graph) {
		this.graph = graph;
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
	protected abstract Match executeModelChanges(RuleApplication ruleApplication);

	/**
	 * Evaluates the given Java-Expression.
	 * 
	 * @param expr
	 *            An expression string.
	 * @return The result of the computation.
	 */
	protected Object evalExpression(Map<Parameter, Object> parameterMapping,
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
}