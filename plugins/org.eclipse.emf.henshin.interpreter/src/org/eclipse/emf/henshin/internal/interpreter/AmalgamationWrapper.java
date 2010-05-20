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
import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class AmalgamationWrapper {
	private AmalgamationUnit amalgamationUnit;
	private Map<Parameter, Object> parameterValues;

	private List<Rule> kernelRules;
	private List<Rule> multiRules;
	private Map<Rule, Map<Node, Node>> subruleEmbedding;
	private Map<Rule, List<Mapping>> rule2mappings;
	private InterpreterEngine engine;

	public AmalgamationWrapper(EmfEngine engine,
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {
		this.amalgamationUnit = amalgamationUnit;
		this.parameterValues = parameterValues;

		kernelRules = new ArrayList<Rule>();
		kernelRules.add(amalgamationUnit.getKernelRule());
		multiRules = amalgamationUnit.getMultiRules();

		this.engine = engine;
		this.rule2mappings = sortInteractionScheme(amalgamationUnit
				.getLhsMappings(), amalgamationUnit.getRhsMappings());
		this.subruleEmbedding = new HashMap<Rule, Map<Node, Node>>();
	}

	public RuleApplication getAmalgamatedRule() {
		List<Match> kernelMatches = new ArrayList<Match>();

		for (Rule kernelRule : kernelRules) {
			Match kernelMatch = engine.findMatch(kernelRule, ModelHelper
					.createPrematch(amalgamationUnit, parameterValues), parameterValues);
			if (kernelMatch != null) {
				kernelMatches.add(kernelMatch);
			} else {
				return null;
			}
		}

		List<Match> multiMatches = new ArrayList<Match>();
		for (Rule multiRule : multiRules) {
			RuleApplication ruleApplication = new RuleApplication(engine,
					multiRule);

			Map<Node, EObject> mappings = translateMapping(multiRule,
					kernelMatches);
			for (Node node : mappings.keySet()) {
				ruleApplication.addMatch(node, mappings.get(node));
			}

			Map<Node, EObject> parameterMappings = ModelHelper.createPrematch(
					amalgamationUnit, parameterValues);
			for (Node node : parameterMappings.keySet()) {
				ruleApplication.addMatch(node, parameterMappings.get(node));
			}

			ruleApplication.setAssignments(parameterValues);

			multiMatches.addAll(ruleApplication.findAllMatches());
		}

		kernelMatches.addAll(multiMatches);

		return createParallelRule(kernelMatches);
	}

	private Map<Node, EObject> translateMapping(Rule multiRule,
			List<Match> kernelMatches) {
		HashMap<Node, EObject> myNodeMapping = new HashMap<Node, EObject>();
		List<Mapping> interactionScheme = this.rule2mappings.get(multiRule);

		for (Match kernelMatch : kernelMatches) {
			Map<Node, EObject> kernelNodeMapping = kernelMatch.getNodeMapping();
			List<Mapping> usedMappings = new ArrayList<Mapping>(
					this.rule2mappings.get(kernelMatch.getRule()));
			usedMappings.retainAll(interactionScheme);

			for (Mapping mapping : usedMappings) {
				if (multiRule.getLhs().getNodes().contains(mapping.getImage())) {
					myNodeMapping.put(mapping.getImage(), kernelNodeMapping
							.get(mapping.getOrigin()));
				}
			}
		}

		return myNodeMapping;
	}

	private Map<Rule, List<Mapping>> sortInteractionScheme(
			List<Mapping> unsortedLhsMappings, List<Mapping> unsortedRhsMappings) {
		Map<Rule, List<Mapping>> result = new HashMap<Rule, List<Mapping>>();
		List<Mapping> unsortedMappings = new ArrayList<Mapping>(
				unsortedLhsMappings);
		unsortedMappings.addAll(unsortedRhsMappings);

		for (Mapping mapping : unsortedMappings) {
			Node sourceNode = mapping.getOrigin();
			Node targetNode = mapping.getImage();

			Rule sourceRule = (Rule) sourceNode.getGraph().eContainer();
			Rule targetRule = (Rule) targetNode.getGraph().eContainer();

			List<Mapping> mappings = result.get(sourceRule);
			if (mappings == null) {
				mappings = new ArrayList<Mapping>();
				result.put(sourceRule, mappings);
			}
			mappings.add(mapping);

			mappings = result.get(targetRule);
			if (mappings == null) {
				mappings = new ArrayList<Mapping>();
				result.put(targetRule, mappings);
			}
			mappings.add(mapping);
		}

		return result;
	}

	private RuleApplication createParallelRule(List<Match> matches) {
		HenshinFactory factory = HenshinFactory.eINSTANCE;

		Rule parallelRule = factory.createRule();
		Graph parallelLhs = parallelRule.getLhs();
		Graph parallelRhs = parallelRule.getRhs();

		Map<Node, EObject> parallelNodeMapping = new HashMap<Node, EObject>();

		for (Match match : matches) {
			Rule singleRule = match.getRule();
			Map<Node, EObject> singleNodeMapping = match.getNodeMapping();
			List<Mapping> involvedMappings = rule2mappings.get(singleRule);

			Map<Node, Node> embedding = new HashMap<Node, Node>();
			subruleEmbedding.put(singleRule, embedding);

			Graph singleLhs = singleRule.getLhs();
			Graph singleRhs = singleRule.getRhs();

			// add the current lhs to the lhs of the parallel rule
			for (Node singleNode : singleLhs.getNodes()) {
				List<Node> sourceNodes = ModelHelper.getSourceNodes(
						involvedMappings, singleNode);

				if (sourceNodes.size() > 0) {
					Node kernelNode = sourceNodes.get(0);
					Rule kernelRule = (Rule) kernelNode.getGraph().eContainer();
					Node parallelNode = subruleEmbedding.get(kernelRule).get(
							kernelNode);
					embedding.put(singleNode, parallelNode);
				} else {
					Node newParallelNode = (Node) EcoreUtil.copy(singleNode);
					newParallelNode.getOutgoing().clear();
					newParallelNode.getIncoming().clear();
					newParallelNode.setGraph(parallelLhs);
					embedding.put(singleNode, newParallelNode);
					parallelNodeMapping.put(newParallelNode, singleNodeMapping
							.get(singleNode));
				}
			}

			// add the current rhs to the rhs of the parallel rule
			for (Node singleNode : singleRhs.getNodes()) {
				List<Node> sourceNodes = ModelHelper.getSourceNodes(
						involvedMappings, singleNode);

				if (sourceNodes.size() > 0) {
					Node kernelNode = sourceNodes.get(0);
					Rule kernelRule = (Rule) kernelNode.getGraph().eContainer();
					Node parallelNode = subruleEmbedding.get(kernelRule).get(
							kernelNode);
					embedding.put(singleNode, parallelNode);
				} else {
					Node newParallelNode = (Node) EcoreUtil.copy(singleNode);
					newParallelNode.getOutgoing().clear();
					newParallelNode.getIncoming().clear();
					newParallelNode.setGraph(parallelRhs);
					embedding.put(singleNode, newParallelNode);
				}
			}

			for (Mapping mapping : singleRule.getMappings()) {
				Node singleSourceNode = mapping.getOrigin();
				Node singleTargetNode = mapping.getImage();

				if (singleTargetNode.getGraph() != singleRhs)
					continue;

				Node parallelSourceNode = embedding.get(singleSourceNode);
				Node parallelTargetNode = embedding.get(singleTargetNode);

				if (!ModelHelper.containsMapping(parallelRule.getMappings(),
						parallelSourceNode, parallelTargetNode)) {
					Mapping parallelMapping = factory.createMapping();
					parallelMapping.setOrigin(parallelSourceNode);
					parallelMapping.setImage(parallelTargetNode);
					parallelRule.getMappings().add(parallelMapping);
				}
			}

			for (Edge singleEdge : singleLhs.getEdges()) {
				Node singleSource = singleEdge.getSource();
				Node singleTarget = singleEdge.getTarget();

				Node parallelSource = embedding.get(singleSource);
				Node parallelTarget = embedding.get(singleTarget);

				if (!hasEdge(singleEdge.getType(), parallelSource,
						parallelTarget)) {
					Edge parallelEdge = factory.createEdge();
					parallelEdge.setSource(parallelSource);
					parallelEdge.setTarget(parallelTarget);
					parallelEdge.setType(singleEdge.getType());
					parallelEdge.setGraph(parallelLhs);
				}
			}

			for (Edge singleEdge : singleRhs.getEdges()) {
				Node singleSource = singleEdge.getSource();
				Node singleTarget = singleEdge.getTarget();

				Node parallelSource = embedding.get(singleSource);
				Node parallelTarget = embedding.get(singleTarget);

				if (!hasEdge(singleEdge.getType(), parallelSource,
						parallelTarget)) {
					Edge parallelEdge = factory.createEdge();
					parallelEdge.setSource(parallelSource);
					parallelEdge.setTarget(parallelTarget);
					parallelEdge.setType(singleEdge.getType());
					parallelEdge.setGraph(parallelRhs);
				}
			}

			for (Parameter parameter : singleRule.getParameters()) {
				Parameter parallelParameter = (Parameter) EcoreUtil.copy(parameter);
				parallelParameter.setUnit(parallelRule);
				String newName = parameter.getName()
						+ Math.abs(new Random().nextInt());
				parameter.setName(newName);
			}
		}

		RuleApplication parallelRuleApplication = new RuleApplication(engine,
				parallelRule);
		for (Node node : parallelNodeMapping.keySet()) {
			parallelRuleApplication.addMatch(node, parallelNodeMapping
					.get(node));
		}

		return parallelRuleApplication;
	}

	/**
	 * Checks if there is an {@link Edge} of type <code>type</code> with
	 * {@link Node}s <code>source</code> and <code>target</code>.
	 * 
	 * @param type
	 * @param source
	 * @param target
	 * @return
	 */
	private boolean hasEdge(EReference type, Node source, Node target) {
		return source.findOutgoingEdgeByType(target, type) != null;
	}// hasEdge

}
