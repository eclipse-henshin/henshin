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
package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class AmalgamationInfo {
	private AmalgamationUnit amalgamationUnit;
	private Map<Parameter, Object> parameterValues;

	private Map<Node, Node> multiNode2kernelNode;
	private EmfEngine emfEngine;

	public AmalgamationInfo(EmfEngine emfEngine,
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {
		this.emfEngine = emfEngine;
		this.amalgamationUnit = amalgamationUnit;
		this.parameterValues = parameterValues;

		this.multiNode2kernelNode = extractMappings(
				amalgamationUnit.getLhsMappings(),
				amalgamationUnit.getRhsMappings());
	}

	private Map<Node, Node> extractMappings(List<Mapping> lhsMappings,
			List<Mapping> rhsMappings) {
		HashMap<Node, Node> multiNodeMap = new HashMap<Node, Node>();

		Collection<Mapping> unsortedMappings = new ArrayList<Mapping>(
				lhsMappings);
		unsortedMappings.addAll(rhsMappings);

		for (Mapping mapping : unsortedMappings) {
			Node kernelNode = mapping.getOrigin();
			Node multiNode = mapping.getImage();

			multiNodeMap.put(multiNode, kernelNode);
		}

		return multiNodeMap;
	}

	public RuleApplication getAmalgamationRule() {
		HenshinFactory factory = HenshinFactory.eINSTANCE;

		Rule kernelRule = amalgamationUnit.getKernelRule();
		Collection<Rule> multiRules = amalgamationUnit.getMultiRules();

		Map<Node, EObject> kernelNodeMapping = ModelHelper.createPrematch(
				amalgamationUnit, parameterValues);
		Map<Node, EObject> parallelNodeMapping = new HashMap<Node, EObject>();

		Match prematch = new Match(kernelRule, parameterValues,
				kernelNodeMapping);
		RuleApplication kernelRuleApplication = new RuleApplication(emfEngine,
				kernelRule);
		kernelRuleApplication.setMatch(prematch);

		// find one match for kernel rule
		Match kernelMatch = emfEngine.findMatch(kernelRuleApplication);
		
		if (kernelMatch == null)
			return null;

		Rule parallelRule = factory.createRule();
		parallelRule.setLhs(factory.createGraph());
		parallelRule.setRhs(factory.createGraph());
		Map<Node, Node> copyMap = new HashMap<Node, Node>();

		parallelNodeMapping.putAll(addMatchContent(parallelRule, kernelMatch,
				copyMap));

		for (Rule multiRule : multiRules) {
			Map<Node, EObject> multiRuleMappings = translateMapping(multiRule,
					kernelMatch);

			prematch = new Match(multiRule, parameterValues, multiRuleMappings);
			RuleApplication multiRuleApplication = new RuleApplication(
					emfEngine, multiRule);
			multiRuleApplication.setMatch(prematch);
			List<Match> matches = multiRuleApplication.findAllMatches();

			for (Match match : matches) {
				parallelNodeMapping.putAll(addMatchContent(parallelRule, match,
						copyMap));
			}
		}

		RuleApplication parallelRuleApplication = new RuleApplication(
				emfEngine, parallelRule);

		Match parallelMatch = new Match(parallelRule,
				kernelMatch.getParameterValues(), parallelNodeMapping);
		parallelRuleApplication.setMatch(parallelMatch);

		for (Node node : parallelNodeMapping.keySet()) {
			parallelRuleApplication.addMatch(node,
					parallelNodeMapping.get(node));
		}

		return parallelRuleApplication;
	}

	private Map<Node, EObject> translateMapping(Rule multiRule,
			Match kernelMatch) {
		Map<Node, EObject> kernelNodeMapping = kernelMatch.getNodeMapping();
		Map<Node, EObject> multiNodeMapping = new HashMap<Node, EObject>();

		for (Node node : multiRule.getLhs().getNodes()) {
			Node kernelNode = multiNode2kernelNode.get(node);
			multiNodeMapping.put(node, kernelNodeMapping.get(kernelNode));
		}

		return multiNodeMapping;
	}

	/**
	 * This method will add a match to a parallel rule while respecting those
	 * objects already added by the kernel rule.
	 * 
	 * @param parallelRule
	 * @param simpleMatch
	 * @return a partial match for the parallel rule with all new nodes from
	 *         this match
	 */
	private Map<Node, EObject> addMatchContent(Rule parallelRule,
			Match simpleMatch, Map<Node, Node> copyMap) {
		HenshinFactory factory = HenshinFactory.eINSTANCE;

		Map<Node, EObject> partialMatch = new HashMap<Node, EObject>();

		// this map stores the correspondence between the original and the
		// copied nodes
		Rule rule = simpleMatch.getRule();

		// add the current lhs to the lhs of the parallel rule
		for (Node node : rule.getLhs().getNodes()) {
			// only add a node if it wasn't already part of the kernel
			if (multiNode2kernelNode.get(node) == null) {
				Node newParallelNode = (Node) EcoreUtil.copy(node);
				newParallelNode.getOutgoing().clear();
				newParallelNode.getIncoming().clear();
				newParallelNode.setGraph(parallelRule.getLhs());
				copyMap.put(node, newParallelNode);
				partialMatch.put(newParallelNode, simpleMatch.getNodeMapping()
						.get(node));
			}
		}

		// add the current rhs to the rhs of the parallel rule
		for (Node node : rule.getRhs().getNodes()) {
			if (multiNode2kernelNode.get(node) == null) {
				Node newParallelNode = (Node) EcoreUtil.copy(node);
				newParallelNode.getOutgoing().clear();
				newParallelNode.getIncoming().clear();
				newParallelNode.setGraph(parallelRule.getRhs());
				copyMap.put(node, newParallelNode);
			} else {

			}
		}

		for (Mapping mapping : rule.getMappings()) {
			Node sourceNode = mapping.getOrigin();
			Node targetNode = mapping.getImage();

			if (targetNode.getGraph() != rule.getRhs())
				continue;

			Node parallelSource = copyMap.get(sourceNode);
			Node parallelTarget = copyMap.get(targetNode);

			if (!parallelRule.containsMapping(parallelSource, parallelTarget)) {
				Mapping parallelMapping = factory.createMapping();
				parallelMapping.setOrigin(parallelSource);
				parallelMapping.setImage(parallelTarget);
				parallelRule.getMappings().add(parallelMapping);
			}
		}

		for (Edge edge : rule.getLhs().getEdges()) {
			Node sourceNode = edge.getSource();
			Node targetNode = edge.getTarget();

			Node parallelSource = copyMap.get(sourceNode);
			Node parallelTarget = copyMap.get(targetNode);

			if (parallelSource == null)
				parallelSource = copyMap.get(multiNode2kernelNode
						.get(sourceNode));
			if (parallelTarget == null)
				parallelTarget = copyMap.get(multiNode2kernelNode
						.get(targetNode));

			if (!hasEdge(edge.getType(), parallelSource, parallelTarget)) {
				Edge parallelEdge = factory.createEdge();
				parallelEdge.setSource(parallelSource);
				parallelEdge.setTarget(parallelTarget);
				parallelEdge.setType(edge.getType());
				parallelEdge.setGraph(parallelRule.getLhs());
			}
		}

		for (Edge edge : rule.getRhs().getEdges()) {
			Node sourceNode = edge.getSource();
			Node targetNode = edge.getTarget();

			Node parallelSource = copyMap.get(sourceNode);
			Node parallelTarget = copyMap.get(targetNode);

			if (parallelSource == null)
				parallelSource = copyMap.get(multiNode2kernelNode
						.get(sourceNode));
			if (parallelTarget == null)
				parallelTarget = copyMap.get(multiNode2kernelNode
						.get(targetNode));

			if (!hasEdge(edge.getType(), parallelSource, parallelTarget)) {
				Edge parallelEdge = factory.createEdge();
				parallelEdge.setSource(parallelSource);
				parallelEdge.setTarget(parallelTarget);
				parallelEdge.setType(edge.getType());
				parallelEdge.setGraph(parallelRule.getRhs());
			}
		}

		for (Parameter parameter : rule.getParameters()) {
			Parameter parallelParameter = (Parameter) EcoreUtil.copy(parameter);
			parallelParameter.setUnit(parallelRule);
			if (simpleMatch.getRule() != amalgamationUnit.getKernelRule()) {
				String newName = parameter.getName()
						+ Math.abs(new Random().nextInt());
				parameter.setName(newName);
			}
		}

		return partialMatch;
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
