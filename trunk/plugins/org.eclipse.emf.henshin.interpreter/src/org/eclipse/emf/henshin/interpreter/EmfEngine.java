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
import javax.script.ScriptException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.internal.interpreter.ChangeInfo;
import org.eclipse.emf.henshin.internal.interpreter.ConditionInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.VariableInfo;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelChange;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.matching.conditions.nested.AndFormula;
import org.eclipse.emf.henshin.matching.conditions.nested.ApplicationCondition;
import org.eclipse.emf.henshin.matching.conditions.nested.IFormula;
import org.eclipse.emf.henshin.matching.conditions.nested.NotFormula;
import org.eclipse.emf.henshin.matching.conditions.nested.OrFormula;
import org.eclipse.emf.henshin.matching.conditions.nested.TrueFormula;
import org.eclipse.emf.henshin.matching.conditions.nested.XorFormula;
import org.eclipse.emf.henshin.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.matching.constraints.Matchfinder;
import org.eclipse.emf.henshin.matching.constraints.Solution;
import org.eclipse.emf.henshin.matching.constraints.Variable;
import org.eclipse.emf.henshin.matching.util.TransformationOptions;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.Xor;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;

/**
 * The default implementation of an interpreter engine.
 */
public class EmfEngine implements InterpreterEngine {
	// the graph the engine will operate on
	EmfGraph emfGraph;
	
	// a script engine used to compute java expressions in attributes
	ScriptEngine scriptEngine;
	
	// information lookup for each rule
	Map<Rule, RuleInfo> ruleInformation;
	
	// options that alter the matching strategy of the engine
	TransformationOptions options;
	
	/**
	 * Creates a new EmfEngine instance.
	 */
	public EmfEngine() {
		ruleInformation = new HashMap<Rule, RuleInfo>();
		
		ScriptEngineManager mgr = new ScriptEngineManager();
		scriptEngine = mgr.getEngineByName("JavaScript");
		
		options = new TransformationOptions();
	}
	
	/**
	 * Creates a new EmfEngine instance.
	 * 
	 * @param emfGraph
	 *            The graph this engine will operate on.
	 */
	public EmfEngine(final EmfGraph emfGraph) {
		this();
		this.emfGraph = emfGraph;
	}
	
	// private Matchfinder prepareMatchfinder(final RuleApplication
	// ruleApplication) {
	// return prepareMatchfinder(ruleApplication.getRule(),
	// ruleApplication.getMatch()
	// .getParameterValues(), ruleApplication.getMatch().getNodeMapping());
	// }
	
	// private Matchfinder prepareMatchfinder(Rule rule, Map<Parameter, Object>
	// parameterValues,
	// Map<Node, EObject> rulePrematch) {
	// return prepareMatchfinder(rule, parameterValues, rulePrematch, new
	// HashSet<EObject>());
	// }
	
	private Matchfinder prepareMatchfinder(Rule rule, Map<Parameter, Object> parameterValues,
			Map<Node, EObject> rulePrematch, Collection<EObject> usedObjects) {
		
		RuleInfo ruleInfo = getRuleInformation(rule);
		ConditionInfo conditionInfo = ruleInfo.getConditionInfo();
		VariableInfo variableInfo = ruleInfo.getVariableInfo();
		Map<Node, EObject> prematch = createPrematch(rule, parameterValues);
		
		// for (Node node : rulePrematch.keySet()) {
		// prematch.put(node, rulePrematch.get(node));
		// }
		prematch.putAll(rulePrematch);
		
		// evaluates attribute conditions of the rule
		AttributeConditionHandler conditionHandler = new AttributeConditionHandler(
				conditionInfo.getConditionParameters(), scriptEngine);
		
		// usedObjects ensures injective matching by removing already
		// matched objects from other DomainSlots
		// Collection<EObject> usedObjects = new HashSet<EObject>();
		
		// Creates a domain map where all variables are mapped to slots.
		// Different variables may share one domain slot, if there is a mapping
		// between the nodes of the variables.
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();
		for (Variable mainVariable : variableInfo.getMainVariables()) {
			Node node = variableInfo.getVariableForNode(mainVariable);
			
			// The matchfinder gets a new set of transformation options in case
			// it has to be modified
			TransformationOptions options = getOptions().copy();
			if (options.getOption(TransformationOptions.INJECTIVE) == null)
				options.setInjective(ruleInfo.getRule().isInjectiveMatching());
			if (options.getOption(TransformationOptions.DANGLING) == null)
				options.setDangling(ruleInfo.getRule().isCheckDangling());
			
			// use injective, deterministic matching for nested conditions
			if (node.getGraph() != ruleInfo.getRule().getLhs()) {
				options.setDeterministic(true);
				options.setInjective(true);
				options.setDangling(false);
			}
			
			DomainSlot domainSlot = new DomainSlot(conditionHandler, usedObjects, options);
			
			if (prematch.get(node) != null) {
				domainSlot.fixInstantiation(prematch.get(node));
			}
			
			for (Variable dependendVariable : variableInfo.getDependendVariables(mainVariable)) {
				domainMap.put(dependendVariable, domainSlot);
			}
		}
		
		boolean conditionFailed = false;
		if (parameterValues != null) {
			for (Parameter parameter : parameterValues.keySet()) {
				conditionFailed |= !conditionHandler.setParameter(parameter.getName(),
						parameterValues.get(parameter));
			}
		}
		
		if (conditionFailed)
			return null;
		
		Map<Graph, List<Variable>> graphMap = variableInfo.getGraph2variables();
		Matchfinder matchfinder = new Matchfinder(emfGraph, domainMap, conditionHandler);
		matchfinder.setVariables(graphMap.get(rule.getLhs()));
		matchfinder.setFormula(initFormula(rule.getLhs().getFormula(), domainMap, graphMap,
				conditionHandler));
		
		return matchfinder;
	}
	
	private IFormula initFormula(final Formula formula, final Map<Variable, DomainSlot> domainMap,
			final Map<Graph, List<Variable>> graphMap,
			final AttributeConditionHandler conditionHandler) {
		if (formula instanceof And) {
			And and = (And) formula;
			IFormula left = initFormula(and.getLeft(), domainMap, graphMap, conditionHandler);
			IFormula right = initFormula(and.getRight(), domainMap, graphMap, conditionHandler);
			AndFormula andFormula = new AndFormula(left, right);
			
			return andFormula;
		} else if (formula instanceof Or) {
			Or or = (Or) formula;
			IFormula left = initFormula(or.getLeft(), domainMap, graphMap, conditionHandler);
			IFormula right = initFormula(or.getRight(), domainMap, graphMap, conditionHandler);
			OrFormula orFormula = new OrFormula(left, right);
			
			return orFormula;
		} else if (formula instanceof Xor) {
			Xor xor = (Xor) formula;
			IFormula left = initFormula(xor.getLeft(), domainMap, graphMap, conditionHandler);
			IFormula right = initFormula(xor.getRight(), domainMap, graphMap, conditionHandler);
			XorFormula xorFormula = new XorFormula(left, right);
			
			return xorFormula;
		} else if (formula instanceof Not) {
			Not not = (Not) formula;
			IFormula child = initFormula(not.getChild(), domainMap, graphMap, conditionHandler);
			NotFormula notFormula = new NotFormula(child);
			
			return notFormula;
		} else if (formula instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) formula;
			IFormula ac = initApplicationCondition(nc, domainMap, graphMap, conditionHandler);
			
			return ac;
		}
		
		return new TrueFormula();
	}
	
	private ApplicationCondition initApplicationCondition(final NestedCondition nc,
			final Map<Variable, DomainSlot> domainMap, final Map<Graph, List<Variable>> graphMap,
			final AttributeConditionHandler conditionHandler) {
		// ApplicationCondition ac = new ApplicationCondition(emfGraph,
		// domainMap, nc.isNegated());
		ApplicationCondition ac = new ApplicationCondition(emfGraph, domainMap, false);
		ac.setVariables(graphMap.get(nc.getConclusion()));
		ac.setFormula(initFormula(nc.getConclusion().getFormula(), domainMap, graphMap,
				conditionHandler));
		
		return ac;
	}
	
	private RuleInfo getRuleInformation(final Rule rule) {
		RuleInfo ruleInfo = ruleInformation.get(rule);
		if (ruleInfo == null) {
			ruleInfo = new RuleInfo(rule, scriptEngine);
			ruleInformation.put(rule, ruleInfo);
		}
		return ruleInfo;
	}
	
	@Override
	public List<Match> findAllMatches(final RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		Collection<EObject> usedObjects = new HashSet<EObject>();
		Map<Node, EObject> prematch = ruleApplication.getMatch().getNodeMapping();
		Map<Parameter, Object> parameterValues = ruleApplication.getMatch().getParameterValues();
		return findAllMultiMatches(rule, usedObjects, parameterValues, prematch);
	}
	
	@Override
	public Match findMatch(final RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		Collection<EObject> usedObjects = new HashSet<EObject>();
		Map<Node, EObject> prematch = ruleApplication.getMatch().getNodeMapping();
		Map<Parameter, Object> parameterValues = ruleApplication.getMatch().getParameterValues();
		return findMatch(rule, usedObjects, parameterValues, prematch);
	}
	
	private Match findMatch(Rule rule, Collection<EObject> usedObjects,
			Map<Parameter, Object> parameterValues, Map<Node, EObject> prematch) {
		if (emfGraph == null)
			throw new IllegalArgumentException("no target graph was specified for the engine");
		
		Matchfinder matchfinder = prepareMatchfinder(rule, parameterValues, prematch, usedObjects);
		
		// workaround for empty rules with attribute conditions
		if (matchfinder == null)
			return null;
		
		Solution solution = matchfinder.getNextMatch();
		if (solution == null)
			return null;
		
		Match match = new Match(rule, solution, getRuleInformation(rule).getVariableInfo()
				.getNode2variable());
		for (Rule mRule : rule.getMultiRules()) {
			Collection<EObject> usedKernelObjects = new HashSet<EObject>(usedObjects);
			usedKernelObjects.addAll(match.getNodeMapping().values());
			Map<Node, EObject> multiPrematch = new HashMap<Node, EObject>();
			for (Mapping multiMapping : mRule.getMultiMappings()) {
				multiPrematch.put(multiMapping.getImage(),
						match.getNodeMapping().get(multiMapping.getOrigin()));
			}
			
			List<Match> multiMatches = findAllMultiMatches(mRule, usedKernelObjects,
					parameterValues, multiPrematch);
			match.getNestedMatchesFor(mRule).addAll(multiMatches);
		}
		return match;
	}
	
	private List<Match> findAllMultiMatches(Rule rule, Collection<EObject> usedObjects,
			Map<Parameter, Object> parameterValues, Map<Node, EObject> prematch) {
		if (emfGraph == null)
			throw new IllegalArgumentException("no target graph was specified for the engine");
		
		Matchfinder matchfinder = prepareMatchfinder(rule, parameterValues, prematch, usedObjects);
		
		// workaround for empty rules with attribute conditions
		if (matchfinder == null)
			return new ArrayList<Match>();
		
		List<Solution> solutions = matchfinder.getAllMatches();
		
		List<Match> matches = new ArrayList<Match>();
		for (Solution solution : solutions) {
			Match match = new Match(rule, solution, getRuleInformation(rule).getVariableInfo()
					.getNode2variable());
			for (Rule mRule : rule.getMultiRules()) {
				Collection<EObject> usedKernelObjects = new HashSet<EObject>(usedObjects);
				usedKernelObjects.addAll(match.getNodeMapping().values());
				Map<Node, EObject> multiPrematch = new HashMap<Node, EObject>();
				for (Mapping multiMapping : mRule.getMultiMappings())
					multiPrematch.put(multiMapping.getImage(),
							match.getNodeMapping().get(multiMapping.getOrigin()));
				List<Match> multiMatches = findAllMultiMatches(mRule, usedKernelObjects,
						parameterValues, multiPrematch);
				match.getNestedMatchesFor(mRule).addAll(multiMatches);
			}
			matches.add(match);
		}
		
		return matches;
	}
	
	@Override
	public Match applyRule(final RuleApplication ruleApplication) {
		Match match = findMatch(ruleApplication);
		if (match != null) {
			ruleApplication.setMatch(match);
			Match comatch = executeModelChanges(ruleApplication);
			ruleApplication.setComatch(comatch);
			
			return comatch;
		}
		
		return null;
	}
	
	public void purgeCache() {
		ruleInformation.clear();
	}
	
	/**
	 * Computes all necessary model changes resulting from the current match.
	 * Note that this method doesn't actually change the model.
	 * 
	 * @return the comatch from the RHS into the instance
	 */
	private Match executeModelChanges(final RuleApplication ruleApplication) {
		Rule rule = ruleApplication.getRule();
		Match match = ruleApplication.getMatch();
		
		if (!match.isComplete())
			return null;
		ModelChange modelChange = new ModelChange();
		Map<Node, EObject> comatchNodeMapping = new HashMap<Node, EObject>();
		Match comatch = accumulateModelChange(rule, match, modelChange, comatchNodeMapping);
		modelChange.applyChanges();
		ruleApplication.setModelChange(modelChange);
		return comatch;
	}
	
	private Match accumulateModelChange(Rule rule, Match match, ModelChange modelChange,
			Map<Node, EObject> comatchNodeMapping) {
		Match comatch = createModelChange(rule, match, modelChange, comatchNodeMapping);
		for (Rule mRule : rule.getMultiRules()) {
			for (Match multiMatch : match.getNestedMatchesFor(mRule)) {
				Map<Node, EObject> multiComatchNodeMapping = new HashMap<Node, EObject>();
				
				for (Mapping comatchMapping : mRule.getMultiMappings()) {
					if (comatchMapping.getImage().getGraph().isRhs()) {
						multiComatchNodeMapping.put(comatchMapping.getImage(), comatch
								.getNodeMapping().get(comatchMapping.getOrigin()));
					}
				}
				Match multiComatch = accumulateModelChange(mRule, multiMatch, modelChange,
						multiComatchNodeMapping);
				comatch.addNestedMatch(mRule, multiComatch);
			}
		}
		return comatch;
	}
	
	protected Match createModelChange(Rule rule, Match match, ModelChange modelChange,
			Map<Node, EObject> comatchNodeMapping) {
		
		ChangeInfo changeInfo = getRuleInformation(rule).getChangeInfo();
		
		if (!match.isComplete())
			return null;
		
		// ModelChange modelChange = new ModelChange();
		
		Map<Node, EObject> matchNodeMapping = match.getNodeMapping();
		// Map<Node, EObject> comatchNodeMapping = new HashMap<Node, EObject>();
		Map<Parameter, Object> comatchParameterValues = new HashMap<Parameter, Object>();
		comatchParameterValues.putAll(match.getParameterValues());
		for (Parameter parameter : match.getParameterValues().keySet()) {
			scriptEngine.put(parameter.getName(), match.getParameterValues().get(parameter));
		}
		
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
			EObject removedNode = match.getNodeMapping().get(node);
			
			emfGraph.removeEObject(removedNode);
			
			if (!rule.isCheckDangling()) {
				Collection<Setting> removedEdges = emfGraph.getCrossReferenceAdapter()
						.getInverseReferences(removedNode);
				for (Setting edge : removedEdges) {
					modelChange.addReferenceChange(edge.getEObject(),
							(EReference) edge.getEStructuralFeature(), removedNode, true);
				}
			}
		}
		
		for (Node node : changeInfo.getPreservedNodes()) {
			Node lhsNode = HenshinMappingUtil.getRemoteNode(rule.getMappings(), node);
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
			modelChange.addReferenceChange(matchNodeMapping.get(edge.getSource()), edge.getType(),
					matchNodeMapping.get(edge.getTarget()), true);
			// modelChange.addObjectChange(matchNodeMapping.get(edge.getSource()),
			// edge.getType(),
			// matchNodeMapping.get(edge.getTarget()), true);
		}
		
		// add new edges
		for (Edge edge : changeInfo.getCreatedEdges()) {
			modelChange.addReferenceChange(comatchNodeMapping.get(edge.getSource()),
					edge.getType(), comatchNodeMapping.get(edge.getTarget()), false);
			// modelChange.addObjectChange(comatchNodeMapping.get(edge.getSource()),
			// edge.getType(),
			// comatchNodeMapping.get(edge.getTarget()), false);
		}
		
		for (Attribute attribute : changeInfo.getAttributeChanges()) {
			EObject targetObject = comatchNodeMapping.get(attribute.getNode());
			Object value = evalAttributeExpression(attribute);
			
			String valueString = null;
			// workaround for Double conversion:
			// javascript evaluated numbers are always shown in floating point
			// notation. This leads to a NumberFormatException if the EAttribute
			// has EDataType EInt or ELong.
			//
			if (value != null) {
				valueString = value.toString();
				// removal of trailing ".0" is fatal in case of string
				// attributes!
				if (valueString.endsWith(".0")
						&& attribute.getType().getEAttributeType().getClassifierID() != EcorePackage.ESTRING) {
					valueString = valueString.substring(0, valueString.length() - 2);
				}
			}
			
			modelChange.addAttributeChange(targetObject, attribute.getType(), valueString, false);
			
			value = EcoreUtil
					.createFromString(attribute.getType().getEAttributeType(), valueString);
			
			// modelChange.addObjectChange(targetObject, attribute.getType(),
			// value, false);
		}
		return new Match(rule, comatchParameterValues, comatchNodeMapping);
	}
	
	/**
	 * Evaluates the given Java-Expression.
	 * 
	 * @param expr
	 *            An expression string.
	 * @return The result of the computation.
	 */
	private Object evalAttributeExpression(final Attribute attribute) {
		
		/*
		 * If the attribute's type is an Enumeration, its value shall be rather
		 * checked against the ecore model than against the javascript machine.
		 */
		if ((attribute.getType() != null) && (attribute.getType().getEType() instanceof EEnum)) {
			EEnum eenum = (EEnum) attribute.getType().getEType();
			EEnumLiteral eelit = eenum.getEEnumLiteral(attribute.getValue());
			if (eelit != null)
				return eelit;
		}// if
		
		try {
			return scriptEngine.eval(attribute.getValue());
		} catch (ScriptException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public void redoChanges(final RuleApplication ruleApplication) {
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
	public void undoChanges(final RuleApplication ruleApplication) {
		ModelChange modelChange = ruleApplication.getModelChange();
		
		modelChange.undoChanges();
		
		for (EObject deletedObject : modelChange.getDeletedObjects()) {
			emfGraph.addEObject(deletedObject);
		}
		
		for (EObject createdObject : modelChange.getCreatedObjects()) {
			emfGraph.removeEObject(createdObject);
		}
	}
	
	/**
	 * @return the emfGraph
	 */
	public EmfGraph getEmfGraph() {
		return emfGraph;
	}
	
	/**
	 * @param emfGraph
	 *            the emfGraph to set
	 */
	public void setEmfGraph(final EmfGraph emfGraph) {
		this.emfGraph = emfGraph;
	}
	
	/**
	 * @return the options
	 */
	public TransformationOptions getOptions() {
		return options;
	}
	
	/**
	 * @param options
	 *            the options to set
	 */
	@Override
	public void setOptions(final TransformationOptions options) {
		this.options = options;
	}
	
	public static Map<Node, EObject> createPrematch(TransformationUnit unit,
			Map<Parameter, Object> parameterValues) {
		
		Map<Node, EObject> prematch = new HashMap<Node, EObject>();
		
		Rule rule = null;
		if (unit instanceof Rule)
			rule = (Rule) unit;
		
		if (rule != null) {
			for (Parameter parameter : unit.getParameters()) {
				Node node = rule.getNodeByName(parameter.getName(), true);
				if (node != null)
					prematch.put(node, (EObject) parameterValues.get(parameter));
			}
		}
		return prematch;
	}
	
}