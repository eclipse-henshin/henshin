package org.eclipse.emf.henshin.variability.mergein.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.mergein.clone.CloneGroup;

import mergeSuggestion.MergeNAC;
import mergeSuggestion.MergePAC;
import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionFactory;
import mergeSuggestion.impl.MergeSuggestionFactoryImpl;

public class BasicMergeSuggestionBuilder {
	static BasicMergeSuggestionBuilder instance = new BasicMergeSuggestionBuilder();

	public static BasicMergeSuggestionBuilder getInstance() {
		return instance;
	}

	public MergeSuggestion createFromBasisClones(Collection<CloneGroup> basisClones) {
		MergeSuggestion result = MergeSuggestionFactoryImpl.eINSTANCE.createMergeSuggestion();
		for (CloneGroup cg : basisClones) {
			MergeRule rule = createFromBasisCloneGroup(cg);
			result.getMergeClusters().add(rule);
		}
		return result;
	}

	public MergeRule createFromBasisCloneGroup(CloneGroup cg) {
		MergeRule result = MergeSuggestionFactoryImpl.eINSTANCE.createMergeRule();
		Rule masterRule = cg.getRules().iterator().next(); // arbitrary rule
		result.setMasterRule(masterRule);

		addGraphElementsToResult(getAllLhs(cg.getRules()), result, cg);
		addGraphElementsToResult(getAllRhs(cg.getRules()), result, cg);

		addACsToResult(cg.getRules(), result, cg, true);
		addACsToResult(cg.getRules(), result, cg, false);

		result.getRules().addAll(cg.getRules());
		return result;
	}

	private void addACsToResult(List<Rule> rules, MergeRule result, CloneGroup cg, boolean nacs) {
		Set<NestedCondition> considered = new HashSet<NestedCondition>();

		for (Rule rule : rules) {
			EList<NestedCondition> acs = nacs ? rule.getLhs().getNACs() : rule.getLhs().getPACs();
			for (NestedCondition ac1 : acs) {
				if (!considered.contains(ac1)) {
					addAcToResult(ac1, rule, cg, rules, result, considered, nacs);
				}
			}
		}
	}

	private void addAcToResult(NestedCondition ac1, Rule rule1, CloneGroup cg, List<Rule> allRules, MergeRule result,
			Set<NestedCondition> considered, boolean nacs) {
		Set<NestedCondition> corresponding = new HashSet<NestedCondition>();
		corresponding.add(ac1);
		c: for (Rule rule2 : allRules) {
			EList<NestedCondition> acs = nacs ? rule2.getLhs().getNACs() : rule2.getLhs().getPACs();
			for (NestedCondition ac2 : acs) {
				if (rule1 != rule2 && ac1 != ac2 && !considered.contains(ac2) && isFullClone(ac1, ac2, cg)) {
					corresponding.add(ac2);
					continue c;
				}
			}
		}

		if (corresponding.size() < allRules.size()) {
			addAcsToMergeResult(result, corresponding, true, nacs);
		} else if (corresponding.size() == allRules.size()) {
			addAcsToMergeResult(result, corresponding, false, nacs);
		} else
			throw new RuntimeException("Error during adding of NACs/PACs.");
		considered.addAll(corresponding);
	}

	private void addAcsToMergeResult(MergeRule result, Set<NestedCondition> corresponding, boolean isolated,
			boolean nacs) {
		if (nacs && isolated) { // then one MergeNAC per NAC
			for (NestedCondition nac : corresponding) {
				MergeNAC el = MergeSuggestionFactoryImpl.eINSTANCE.createMergeNAC();
				el.getReferenceNACs().add(nac.getConclusion());
				result.addMergeNAC(el);
			}
		} else if (nacs && !isolated) { // then one MergeNAC for all NACs
			MergeNAC el = MergeSuggestionFactoryImpl.eINSTANCE.createMergeNAC();
			for (NestedCondition nac : corresponding) {
				el.getReferenceNACs().add(nac.getConclusion());
			}
			result.addMergeNAC(el);
		} else if (!nacs && isolated) { // then one MergePAC per PAC
			for (NestedCondition nac : corresponding) {
				MergePAC el = MergeSuggestionFactoryImpl.eINSTANCE.createMergePAC();
				el.getReferencePACs().add(nac.getConclusion());
				result.addMergePAC(el);
			}
		} else if (!nacs && !isolated) { // then one MergePAC per PAC
			MergePAC el = MergeSuggestionFactoryImpl.eINSTANCE.createMergePAC();
			for (NestedCondition nac : corresponding) {
				el.getReferencePACs().add(nac.getConclusion());
			}
			result.addMergePAC(el);
		}
	}

	/**
	 * Checks if the NAC/PAC specific part of the input NAC or PAC graph fully
	 * appears in a clone group
	 * 
	 * @param acGraph
	 * @param cg
	 * @return
	 */
	private boolean isFullClone(NestedCondition ac1, NestedCondition ac2, CloneGroup cg) {
		return isFullyContained(ac1.getConclusion(), ac2.getConclusion(), cg)
				&& isFullyContained(ac2.getConclusion(), ac1.getConclusion(), cg);
	}

	private boolean isFullyContained(Graph acGraph, Graph acGraph2, CloneGroup cg) {
		for (Node node : acGraph.getNodes()) {
			if (isSpecificForApplicationCondition(node)) {
				if (!nodeHasCounterpart(node, acGraph2, cg))
					return false;

			}

			for (Attribute attribute : node.getAttributes()) {
				if (isSpecificForApplicationCondition(attribute)) {
						if (!attributeHasCounterpart(attribute, acGraph2, cg))
						return false;
				}
			}

		}

		for (Edge edge : acGraph.getEdges()) {
			if (isSpecificForApplicationCondition(edge)) {
				if (!edgeHasCounterpart(edge, acGraph2, cg) || !nodeHasCounterpart(edge.getSource(), acGraph2, cg)
						|| !nodeHasCounterpart(edge.getTarget(), acGraph2, cg)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean nodeHasCounterpart(Node node, Graph acGraph2, CloneGroup cg) {
		Map<Rule, Node> mapping = cg.getNodeMappings().get(node.getActionNode());
		return mapping != null && mapping.get(acGraph2.getRule()) != null;
	}

	private boolean attributeHasCounterpart(Attribute attribute, Graph acGraph2, CloneGroup cg) {
		Map<Rule, Attribute> mapping = cg.getAttributeMappings().get(attribute.getActionAttribute());
		return mapping != null && mapping.get(acGraph2.getRule()) != null;
	}

	private boolean edgeHasCounterpart(Edge edge, Graph acGraph2, CloneGroup cg) {
		Map<Rule, Edge> mapping = cg.getEdgeMappings().get(edge.getActionEdge());
		return mapping != null && mapping.get(acGraph2.getRule()) != null;
	}

	/**
	 * 
	 * @param
	 * @return
	 */
	private boolean isSpecificForApplicationCondition(GraphElement graphElement) {
		return (graphElement.getAction() != null) && (graphElement.getAction().getType() == Type.FORBID
				|| (graphElement.getAction().getType() == Type.REQUIRE));
	}

	protected void addGraphElementsToResult(List<Graph> graphs, MergeRule result, CloneGroup cloneGroup) {
		Set<GraphElement> addedElements = new HashSet<GraphElement>();
		for (Graph graph : graphs) {
			for (GraphElement ge : getGraphElements(graph)) {
				if (!graph.isNestedCondition())
					addGraphElementToResult(ge, graphs, result, cloneGroup, addedElements);
			}
		}
	}

	protected void addApplicationConditionToResult(List<Graph> graphs, MergeRule result, CloneGroup cloneGroup) {
		throw new UnsupportedOperationException();
	}

	protected void addGraphElementToResult(GraphElement ge, List<Graph> correspondingGraphs, MergeRule result,
			CloneGroup cloneGroup, Set<GraphElement> addedElements) {
		if (!addedElements.contains(ge)) {
			if (isCloneElement(ge, cloneGroup)) {
				addCloneMergeElementToResult(ge, correspondingGraphs, result, addedElements, cloneGroup);
			} else {
				addSingleElementToResult(result, ge);
				addedElements.add(ge);
			}
		}
	}

	protected List<Graph> getAllLhs(List<Rule> list) {
		List<Graph> result = new ArrayList<>();
		for (Rule rule : list)
			result.add(rule.getLhs());
		return result;
	}

	protected List<Graph> getAllRhs(List<Rule> list) {
		List<Graph> result = new ArrayList<>();
		for (Rule rule : list)
			result.add(rule.getRhs());
		return result;
	}

	protected List<Graph> getAllMultiLhs(List<Rule> list) {
		List<Graph> result = new ArrayList<>();
		for (Rule rule : list)
			for (Rule multiRule : rule.getMultiRules())
				result.add(multiRule.getLhs());
		return result;
	}

	protected List<Graph> getAllMultiRhs(List<Rule> list) {
		List<Graph> result = new ArrayList<>();
		for (Rule rule : list)
			for (Rule multiRule : rule.getMultiRules())
				result.add(multiRule.getRhs());
		return result;
	}

	protected List<Graph> getPacs(List<Rule> list) {
		List<Graph> result = new ArrayList<>();
		for (Rule rule : list)
			for (NestedCondition nac : rule.getLhs().getPACs()) {
				result.add(nac.getConclusion());
			}
		return result;
	}

	protected boolean isCloneElement(GraphElement element, CloneGroup cg) {
		GraphElement actionElement = getActionElement(element);
		if (actionElement instanceof Edge)
			return cg.getEdgeMappings().keySet().contains(actionElement);
		if (actionElement instanceof Attribute)
			return cg.getAttributeMappings().keySet().contains(actionElement);
		if (actionElement instanceof Node) {
			return cg.getNodeMappings().keySet().contains(actionElement);
		}
		return false;
	}

	protected List<GraphElement> getAllGraphElements(Rule r) {
		List<GraphElement> result = new ArrayList<>();
		TreeIterator<EObject> it = r.eAllContents();
		while (it.hasNext()) {
			EObject e = it.next();
			if (e instanceof GraphElement) {
				result.add((GraphElement) e);
			}
		}
		return result;
	}

	protected GraphElement getActionElement(GraphElement element) {
		if (element instanceof Node)
			return ((Node) element).getActionNode();
		if (element instanceof Attribute)
			return ((Attribute) element).getActionAttribute();
		if (element instanceof Edge)
			return ((Edge) element).getActionEdge();
		return null;
	}

	protected void addCloneMergeElementToResult(GraphElement graphElement, List<Graph> graphs, MergeRule result,
			Set<GraphElement> addedElements, CloneGroup cloneGroup) {
		MergeRuleElement merged = addSingleElementToResult(result, graphElement);
		addedElements.add(graphElement);
		for (Graph graph : graphs) {
			GraphElement clone = findClone(graphElement, graph, cloneGroup);
			if (clone != null) {
				merged.getReferenceElements().add(clone);
				addedElements.add(clone);
			}
		}
	}

	protected GraphElement findClone(GraphElement ge, Graph graph, CloneGroup cloneGroup) {
		Map outerMap = null;
		if (ge instanceof Node)
			outerMap = cloneGroup.getNodeMappings();
		else if (ge instanceof Edge)
			outerMap = cloneGroup.getEdgeMappings();
		else if (ge instanceof Attribute)
			outerMap = cloneGroup.getAttributeMappings();
		Rule rule = getRule(graph);
		GraphElement actionElement = getActionElement(ge);
		if (outerMap != null && rule != null && actionElement != null) {
			Map innerMap = (Map) outerMap.get(actionElement);
			GraphElement actionElement2 = (GraphElement) innerMap.get(rule);
			if (actionElement2 != null) {
				for (GraphElement ge2 : getGraphElements(graph)) {
					if (getActionElement(ge2) == actionElement2)
						return ge2;
				}
			}
		}

		return null;
	}

	protected Rule getRule(Graph graph) {
		EObject container = graph.eContainer();
		while (container.eContainer() != null) {
			if (container instanceof Rule)
				return (Rule) container;
			else
				container = container.eContainer();
		}
		return null;
	}

	protected MergeRuleElement addSingleElementToResult(MergeRule result, GraphElement ge) {
		MergeRuleElement el = MergeSuggestionFactory.eINSTANCE.createMergeRuleElement();
		el.getReferenceElements().add(ge);
		result.addMergeRuleElement(el);
		return el;
	}

	private List<GraphElement> getGraphElements(Graph graph) {
		List<GraphElement> result = new ArrayList<>();
		result.addAll(graph.getNodes());
		result.addAll(graph.getEdges());
		for (Node n : graph.getNodes()) {
			result.addAll(n.getAttributes());
		}
		return result;
	}

}
