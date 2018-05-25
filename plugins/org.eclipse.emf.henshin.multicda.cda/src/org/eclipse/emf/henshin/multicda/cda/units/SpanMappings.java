package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;

/**
 * A helper data structure for making the querying of span mappings
 * more readable and faster.
 * 
 * @author strueber
 *
 */
public class SpanMappings {
	public Span span;

	public HashMap<Node, Node> rule1ToS1;
	public HashMap<Node, Node> s1ToRule1;
	public HashMap<Node, Node> rule2ToS1;
	public HashMap<Node, Node> s1ToRule2;

	private Map<Edge, Edge> edgesS1Rule1;
	private Map<Edge, Edge> edgesS1Rule2;
	private Map<Edge, Edge> edgesRule1S1;
	private Map<Edge, Edge> edgesRule2S1;

	public SpanMappings(Span span) {
		this.span = span;
		rule1ToS1 = new HashMap<Node, Node>();
		s1ToRule1 = new HashMap<Node, Node>();
		for (Mapping mapping : span.getMappingsInRule1()) {
			s1ToRule1.put(mapping.getOrigin(), mapping.getImage());
			rule1ToS1.put(mapping.getImage(), mapping.getOrigin());
		}
		rule2ToS1 = new HashMap<Node, Node>();
		s1ToRule2 = new HashMap<Node, Node>();
		for (Mapping mapping : span.getMappingsInRule2()) {
			s1ToRule2.put(mapping.getOrigin(), mapping.getImage());
			rule2ToS1.put(mapping.getImage(), mapping.getOrigin());
		}
	}

	public Map<Edge, Edge> getEdgeMappingsS1Rule1() {
		if (edgesS1Rule1 == null)
			computeEdgeMappings();
		return edgesS1Rule1;
	}

	public Map<Edge, Edge> getEdgeMappingsS1Rule2() {
		if (edgesS1Rule2 == null)
			computeEdgeMappings();
		return edgesS1Rule2;
	}

	public Map<Edge, Edge> getEdgeMappingsRule1S1() {
		if (edgesRule1S1 == null)
			computeEdgeMappings();
		return edgesRule1S1;
	}

	public Map<Edge, Edge> getEdgeMappingsRule2S1() {
		if (edgesRule2S1 == null)
			computeEdgeMappings();
		return edgesRule2S1;
	}

	public void computeEdgeMappings() {
		edgesS1Rule1 = new HashMap<Edge, Edge>();
		edgesRule1S1 = new HashMap<Edge, Edge>();
		EList<Edge> edges = span.getGraph().getEdges();
		for (Edge edgeS1 : edges) {
			Node sourceRule1 = s1ToRule1.get(edgeS1.getSource());
			Node targetRule1 = s1ToRule1.get(edgeS1.getTarget());
			Edge counterpart = null;
			for (Edge eR1 : sourceRule1.getOutgoing(edgeS1.getType())) {
				if (eR1.getTarget() == targetRule1)
					counterpart = eR1;
			}
			if (counterpart != null) {
				edgesS1Rule1.put(edgeS1, counterpart);
				edgesRule1S1.put(counterpart, edgeS1);
			}

		}

		edgesS1Rule2 = new HashMap<Edge, Edge>();
		edgesRule2S1 = new HashMap<Edge, Edge>();
		for (Edge eSpan : edges) {
			Node sourceRule2 = s1ToRule2.get(eSpan.getSource());
			Node targetRule2 = s1ToRule2.get(eSpan.getTarget());
			Edge counterpart = null;
			for (Edge eR2 : sourceRule2.getOutgoing(eSpan.getType())) {
				if (eR2.getTarget() == targetRule2)
					counterpart = eR2;
			}
			if (counterpart != null) {
				edgesS1Rule2.put(eSpan, counterpart);
				edgesRule2S1.put(counterpart, eSpan);
			}
		}
	}
}
