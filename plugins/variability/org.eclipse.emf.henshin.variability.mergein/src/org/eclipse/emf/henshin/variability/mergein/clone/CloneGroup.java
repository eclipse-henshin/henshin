package org.eclipse.emf.henshin.variability.mergein.clone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class CloneGroup {
	private List<Rule> rules;
	private Map<Edge, Map<Rule, Edge>> edgeMappings;
	private Map<Attribute, Map<Rule, Attribute>> attributeMappings;
	private Map<Node, Map<Rule, Node>> nodeMappings;

	public CloneGroup() {
		this.rules = new ArrayList<Rule>();
		this.edgeMappings = new LinkedHashMap<Edge, Map<Rule, Edge>>();
		this.attributeMappings = new LinkedHashMap<Attribute, Map<Rule, Attribute>>();
	}

	public CloneGroup(List<Rule> rules, Map<Edge, Map<Rule, Edge>> edgeMappings,
			Map<Attribute, Map<Rule, Attribute>> attributeMappings) {
		this.rules = rules;
		this.edgeMappings = edgeMappings;
		this.attributeMappings = attributeMappings;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public Map<Edge, Map<Rule, Edge>> getEdgeMappings() {
		return edgeMappings;
	}

	public Map<Node, Map<Rule, Node>> getNodeMappings() {
		if (nodeMappings == null)
			updateNodeMappings();
		return nodeMappings;
	}

	public Map<Attribute, Map<Rule, Attribute>> getAttributeMappings() {
		return attributeMappings;
	}

	public int getNumberOfCommonEdges() {
		return edgeMappings.keySet().size() / rules.size();
	}

	public void removeRule(Rule rule) {
		Set<Edge> deleteEdges = new HashSet<Edge>();
		Set<Attribute> deleteAttributes = new HashSet<Attribute>();
		for (Edge edge : edgeMappings.keySet()) {
			Edge correspondingEdge = edgeMappings.get(edge).get(rule);
			if (correspondingEdge != null)
				deleteEdges.add(correspondingEdge);
			edgeMappings.get(edge).remove(rule);
		}
		for (Attribute attribute : attributeMappings.keySet()) {
			Attribute correspondingAttribute = attributeMappings.get(attribute)
					.get(rule);
			if (correspondingAttribute != null)
				deleteAttributes.add(correspondingAttribute);
			attributeMappings.get(attribute).remove(rule);
		}
		for (Edge edge : deleteEdges) {
			edgeMappings.remove(edge);
		}
		for (Attribute attribute : deleteAttributes) {
			edgeMappings.remove(attribute);
		}
		rules.remove(rule);
		updateNodeMappings();
	}

	public void removeRules(Collection<Rule> ruleSet) {
		for (Rule r : ruleSet) {
			removeRule(r);
		}
	}

	protected void updateNodeMappings() {
		Set<Map<Rule, Node>> innerMaps = createInnerMapsForNodeMappings();
		HashMap<Node, Map<Rule, Node>> outerMap = createOuterMapsForNodeMappings(innerMaps);
		nodeMappings = outerMap;
	}

	private HashMap<Node, Map<Rule, Node>> createOuterMapsForNodeMappings(
			Set<Map<Rule, Node>> innerMaps) {
		HashMap<Node, Map<Rule, Node>> outerMap = new LinkedHashMap<Node, Map<Rule, Node>>();
		for (Map<Rule, Node> innerMap : innerMaps) {
			for (Rule rule : innerMap.keySet()) {
				outerMap.put(innerMap.get(rule), innerMap);
			}
		}
		return outerMap;
	}

	private Set<Map<Rule, Node>> createInnerMapsForNodeMappings() {
		Set<Map<Rule, Node>> innerMaps = new HashSet<Map<Rule, Node>>();
		for (Edge edge : edgeMappings.keySet()) {
			Map<Rule, Edge> edgeInnerMap = edgeMappings.get(edge);
			Map<Rule, Node> innerMapForSource = createOrFindInnerMapForNodeMappings(
					edgeInnerMap.values(), true, innerMaps);
			for (Rule rule : edgeInnerMap.keySet()) {
				Edge innerEdge = edgeInnerMap.get(rule);
				addToInnerMapForNodeMappings(innerEdge.getSource(), rule,
						innerMapForSource);
			}
		}

		for (Edge edge : edgeMappings.keySet()) {
			Map<Rule, Edge> edgeInnerMap = edgeMappings.get(edge);
			Map<Rule, Node> innerMapForTarget = createOrFindInnerMapForNodeMappings(
					edgeInnerMap.values(), false, innerMaps);
			for (Rule rule : edgeInnerMap.keySet()) {
				Edge innerEdge = edgeInnerMap.get(rule);
				addToInnerMapForNodeMappings(innerEdge.getTarget(), rule,
						innerMapForTarget);
			}

		}
		return innerMaps;
	}

	/**
	 * Determines if already an inner map was created for one of the sources
	 * (targets) of the input edges. If this is the case, then the existing
	 * inner map is returned. Otherwise, a new one is created and returned.
	 * 
	 * @param edgeSet
	 * @param source
	 *            "true" for analyzing the edges sources, "false" for analyzing
	 *            the edge targets
	 * @param innerMaps
	 * @return
	 */
	private Map<Rule, Node> createOrFindInnerMapForNodeMappings(
			Collection<Edge> edgeSet, boolean source,
			Set<Map<Rule, Node>> innerMaps) {
		for (Edge edge : edgeSet) {
			Node nodeToAnalyse = source ? edge.getSource() : edge.getTarget();
			Map<Rule, Node> potentialResult = createOrFindInnerMapForNodeMappings(
					nodeToAnalyse, innerMaps);
			if (potentialResult != null)
				return potentialResult;
		}
		HashMap<Rule, Node> newInnerMap = new LinkedHashMap<Rule, Node>();
		innerMaps.add(newInnerMap);
		return newInnerMap;
	}

	private Map<Rule, Node> createOrFindInnerMapForNodeMappings(Node node,
			Set<Map<Rule, Node>> innerMaps) {
		for (Map<Rule, Node> innerMap : innerMaps) {
			for (Rule entry : innerMap.keySet())
				if (innerMap.get(entry) == node)
					return innerMap;
		}
		return null;
	}

	private void addToInnerMapForNodeMappings(Node node, Rule hostRule,
			Map<Rule, Node> innerMap) {
		Node actionNode = node.getActionNode();
		innerMap.put(hostRule, actionNode);
	}

	public int getSize() {
		return (getEdgeMappings().keySet().size()) / rules.size();
//		return (getNodeMappings().keySet().size()
//				+ getEdgeMappings().keySet().size() + getAttributeMappings()
//				.keySet().size()) / rules.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" [Size ");
		sb.append(getSize());
		sb.append(", ");
		sb.append(getRules().size());
		sb.append(" Rules: ");
		Iterator<Rule> it = getRules().iterator();
		while (it.hasNext()) {
			Rule r = it.next();
			sb.append(r.getName());
			if (it.hasNext())
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	public double getNumberOfCommonLhsEdges() {
		int size = edgeMappings.keySet().stream().filter(e -> e.getAction().getType() == Type.DELETE || e.getAction().getType() == Type.PRESERVE).collect(Collectors.toList()).size();
		return size / rules.size();
	}
	

}
