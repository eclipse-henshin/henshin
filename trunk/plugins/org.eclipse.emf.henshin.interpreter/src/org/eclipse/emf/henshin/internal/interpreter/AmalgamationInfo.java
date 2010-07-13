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
import java.util.Collection;
import java.util.HashMap;
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
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class AmalgamationInfo {
	private AmalgamationUnit amalgamationUnit;
	private Map<Parameter, Object> parameterValues;

	private Map<Rule, Collection<Mapping>> rule2mappings;
	private EmfEngine emfEngine;

	public AmalgamationInfo(EmfEngine emfEngine,
			AmalgamationUnit amalgamationUnit,
			Map<Parameter, Object> parameterValues) {
		this.emfEngine = emfEngine;

		this.amalgamationUnit = amalgamationUnit;
		this.parameterValues = parameterValues;

		this.rule2mappings = sortInteractionScheme(amalgamationUnit
				.getLhsMappings(), amalgamationUnit.getRhsMappings());
	}

	public RuleApplication getAmalgamationRule() {
		Rule kernelRule = amalgamationUnit.getKernelRule();
		Collection<Rule> multiRules = amalgamationUnit.getMultiRules();
		
		Map<Node, EObject> nodeMapping = ModelHelper.createPrematch(amalgamationUnit,
				parameterValues);

		Match prematch = new Match(kernelRule, parameterValues, nodeMapping);
		RuleApplication kernelRuleApplication = new RuleApplication(emfEngine,
				kernelRule);
		kernelRuleApplication.setMatch(prematch);

		// find one match for kernel rule 
		Match kernelMatch = emfEngine.findMatch(kernelRuleApplication);

		// collect all possible matches for the multi rules that use the kernel match as a prematch 
		Collection<Match> multiRuleMatches = new ArrayList<Match>();
		for (Rule multiRule : multiRules) {
			Map<Node, EObject> multiRuleMappings = translateMapping(multiRule,
					kernelMatch);

			prematch = new Match(multiRule, parameterValues, multiRuleMappings);
			RuleApplication multiRuleApplication = new RuleApplication(emfEngine,
					multiRule);
			multiRuleApplication.setMatch(prematch);
			
			multiRuleMatches.addAll(multiRuleApplication.findAllMatches());
		}

		return createParallelRuleApplication(multiRuleMatches);
	}
	
	private Map<Node, EObject> translateMapping(Rule multiRule,
			Match kernelMatch) {
		Map<Node, EObject> multiNodeMapping = new HashMap<Node, EObject>();
		Collection<Mapping> interactionScheme = this.rule2mappings
				.get(multiRule);

		Map<Node, EObject> kernelNodeMapping = kernelMatch.getNodeMapping();
		Collection<Mapping> usedMappings = new ArrayList<Mapping>(
				this.rule2mappings.get(kernelMatch.getRule()));
		usedMappings.retainAll(interactionScheme);

		for (Mapping mapping : usedMappings) {
			if (multiRule.getLhs().getNodes().contains(mapping.getImage())) {
				multiNodeMapping.put(mapping.getImage(), kernelNodeMapping
						.get(mapping.getOrigin()));
			}
		}

		return multiNodeMapping;
	}

	private Map<Rule, Collection<Mapping>> sortInteractionScheme(
			Collection<Mapping> unsortedLhsMappings,
			Collection<Mapping> unsortedRhsMappings) {
		Map<Rule, Collection<Mapping>> result = new HashMap<Rule, Collection<Mapping>>();
		Collection<Mapping> unsortedMappings = new ArrayList<Mapping>(
				unsortedLhsMappings);
		unsortedMappings.addAll(unsortedRhsMappings);

		for (Mapping mapping : unsortedMappings) {
			Node sourceNode = mapping.getOrigin();
			Node targetNode = mapping.getImage();

			Rule sourceRule = (Rule) sourceNode.getGraph().eContainer();
			Rule targetRule = (Rule) targetNode.getGraph().eContainer();

			Collection<Mapping> mappings = result.get(sourceRule);
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

	private RuleApplication createParallelRuleApplication(Collection<Match> matches) {
		HenshinFactory factory = HenshinFactory.eINSTANCE;

		Rule parallelRule = factory.createRule();
		Graph parallelLhs = parallelRule.getLhs();
		Graph parallelRhs = parallelRule.getRhs();

		Map<Node, EObject> parallelNodeMapping = new HashMap<Node, EObject>();

		for (Match match : matches) {
			Rule singleRule = match.getRule();
			Map<Node, EObject> singleNodeMapping = match.getNodeMapping();

			Map<Node, Node> embedding = new HashMap<Node, Node>();

			Graph singleLhs = singleRule.getLhs();
			Graph singleRhs = singleRule.getRhs();

			// add the current lhs to the lhs of the parallel rule
			for (Node singleNode : singleLhs.getNodes()) {
				Node newParallelNode = (Node) EcoreUtil.copy(singleNode);
				newParallelNode.getOutgoing().clear();
				newParallelNode.getIncoming().clear();
				newParallelNode.setGraph(parallelLhs);
				embedding.put(singleNode, newParallelNode);
				parallelNodeMapping.put(newParallelNode, singleNodeMapping
						.get(singleNode));
			}

			// add the current rhs to the rhs of the parallel rule
			for (Node singleNode : singleRhs.getNodes()) {
				Node newParallelNode = (Node) EcoreUtil.copy(singleNode);
				newParallelNode.getOutgoing().clear();
				newParallelNode.getIncoming().clear();
				newParallelNode.setGraph(parallelRhs);
				embedding.put(singleNode, newParallelNode);
			}

			for (Mapping mapping : singleRule.getMappings()) {
				Node singleSourceNode = mapping.getOrigin();
				Node singleTargetNode = mapping.getImage();

				if (singleTargetNode.getGraph() != singleRhs)
					continue;

				Node parallelSourceNode = embedding.get(singleSourceNode);
				Node parallelTargetNode = embedding.get(singleTargetNode);

				if (!parallelRule.containsMapping(parallelSourceNode,
						parallelTargetNode)) {
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
				Parameter parallelParameter = (Parameter) EcoreUtil
						.copy(parameter);
				parallelParameter.setUnit(parallelRule);
				String newName = parameter.getName()
						+ Math.abs(new Random().nextInt());
				parameter.setName(newName);
			}
		}

		RuleApplication parallelRuleApplication = new RuleApplication(
				emfEngine, parallelRule);
		
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
