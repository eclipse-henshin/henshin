package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptEngine;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.common.util.ModelHelper;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.util.RuleMatch;
import org.eclipse.emf.henshin.model.AmalgamatedUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Variable;

public class AmalgamationWrapper {
	private List<Rule> kernelRules;
	private List<Rule> multiRules;
	private Map<Rule, List<Mapping>> rule2mappings;

	EmfGraph emfGraph;
	ScriptEngine scriptEngine;

	Map<Rule, Map<Node, Node>> subruleEmbedding;

	EmfEngine engine;

	public AmalgamationWrapper(EmfEngine engine,
			AmalgamatedUnit amalgamatedUnit, EmfGraph emfGraph,
			ScriptEngine scriptEngine) {
		kernelRules = new ArrayList<Rule>();
		kernelRules.add(amalgamatedUnit.getKernelRule());
		multiRules = amalgamatedUnit.getMultiRules();
		this.rule2mappings = sortInteractionScheme(amalgamatedUnit
				.getLhsMappings(), amalgamatedUnit.getRhsMappings());
		this.engine = engine;
		this.scriptEngine = scriptEngine;

		this.emfGraph = emfGraph;

		this.subruleEmbedding = new HashMap<Rule, Map<Node, Node>>();
	}

	public RuleMatch getAmalgamatedRule() {
		List<RuleMatch> kernelMatches = new ArrayList<RuleMatch>();
		for (Rule kernelRule : kernelRules) {
			RuleMatch kernelMatch = engine.findMatch(kernelRule);
			if (kernelMatch != null) {
				kernelMatches.add(kernelMatch);
			} else {
				return null;
			}
		}

		ArrayList<RuleMatch> multiMatches = new ArrayList<RuleMatch>();
		for (Rule multiRule : multiRules) {
			RuleWrapper wrapper = new RuleWrapper(multiRule, emfGraph,
					scriptEngine);
			wrapper.setMatchObjects(translateMapping(multiRule, kernelMatches));
			multiMatches.addAll((wrapper.getAllMatches()));
		}

		kernelMatches.addAll(multiMatches);

		return createParallelRule(kernelMatches);
	}

	private HashMap<Node, EObject> translateMapping(Rule multiRule,
			List<RuleMatch> kernelMatches) {
		HashMap<Node, EObject> myNodeMapping = new HashMap<Node, EObject>();
		List<Mapping> interactionScheme = this.rule2mappings.get(multiRule);

		for (RuleMatch kernelMatch : kernelMatches) {
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

	private RuleMatch createParallelRule(List<RuleMatch> matches) {
		HenshinFactory factory = HenshinFactory.eINSTANCE;

		Rule parallelRule = factory.createRule();
		Map<Node, EObject> parallelNodeMapping = new HashMap<Node, EObject>();

		Graph parallelLhs = factory.createGraph();
		parallelRule.setLhs(parallelLhs);

		Graph parallelRhs = factory.createGraph();
		parallelRule.setRhs(parallelRhs);

		for (RuleMatch match : matches) {
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

			for (Variable variable : singleRule.getVariables()) {
				Variable parallelVariable = (Variable) EcoreUtil.copy(variable);
				parallelVariable.setRule(parallelRule);
				String newName = variable.getName() + Math.abs(new Random().nextInt());
				variable.setName(newName);
			}
		}

		return new RuleMatch(parallelRule, null, parallelNodeMapping);
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
	public static boolean hasEdge(EReference type, Node source, Node target) {
		return source.findOutgoingEdgeByType(target, type) != null;
	}// hasEdge
    
}
