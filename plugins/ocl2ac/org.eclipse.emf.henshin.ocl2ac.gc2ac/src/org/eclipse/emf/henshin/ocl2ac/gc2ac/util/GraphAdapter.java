/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.util;

import java.util.HashMap;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;

import graph.Attribute;
import graph.Edge;
import graph.GraphFactory;
import graph.Node;
import graph.util.extensions.Constants;

public class GraphAdapter {

	private static final String VAR = "var";
	private org.eclipse.emf.henshin.model.Graph henshinGraph;
	private graph.Graph graph;
	private EPackage typeModel;
	private HashMap<org.eclipse.emf.henshin.model.Node, Node> nodeMappingsFromHenshin;
	private HashMap<Node, org.eclipse.emf.henshin.model.Node> nodeMappingsToHenshin;
	private final GraphFactory factory = GraphFactory.eINSTANCE;
	private final HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

	public HashMap<org.eclipse.emf.henshin.model.Parameter, graph.Attribute> henVarMappingsToNcAttribute;

	public GraphAdapter(Graph henshinGraph, EPackage typeModel) {
		super();
		this.henshinGraph = henshinGraph;
		this.graph = factory.createGraph();
		this.typeModel = typeModel;
		this.nodeMappingsFromHenshin = new HashMap<org.eclipse.emf.henshin.model.Node, Node>();
		this.henVarMappingsToNcAttribute = new HashMap<org.eclipse.emf.henshin.model.Parameter, Attribute>();
	}

	/**
	 * 
	 * @param graph
	 *            AdaptToHenshin
	 */
	public GraphAdapter(graph.Graph graph) {
		super();
		this.henshinGraph = henshinFactory.createGraph();
		;
		this.graph = graph;
		this.nodeMappingsToHenshin = new HashMap<Node, org.eclipse.emf.henshin.model.Node>();
		this.henVarMappingsToNcAttribute = new HashMap<org.eclipse.emf.henshin.model.Parameter, Attribute>();
	}

	/**
	 * For each element of our graph, we create a Henshin element
	 */
	public void adaptToHenshin() {
		adaptNodesWithAttributesToHenshin();
		adaptEdgesToHenshin();
	}

	/**
	 * For each edge of our graph we create a Henshin edge
	 */
	private void adaptEdgesToHenshin() {
		for (Edge edge : graph.getEdges()) {
			org.eclipse.emf.henshin.model.Edge henshinEdge = henshinFactory.createEdge();
			henshinEdge.setType(edge.getType());
			henshinEdge.setSource(nodeMappingsToHenshin.get(edge.getSource()));
			henshinEdge.setTarget(nodeMappingsToHenshin.get(edge.getTarget()));
			this.henshinGraph.getEdges().add(henshinEdge);
		}
	}

	/**
	 * For each node of nc graph, we create a Henshin node Supporting graph
	 * attributes Supporting ncAttribute parameters
	 */
	private void adaptNodesWithAttributesToHenshin() {
		for (Node ncNode : graph.getNodes()) {
			org.eclipse.emf.henshin.model.Node henshinNode = henshinFactory.createNode();
			henshinNode.setType(ncNode.getType());
			henshinNode.setName(ncNode.getName());

			if (ncNode.getAttributes().size() > 0) {
				for (Attribute ncAttribute : ncNode.getAttributes()) {
					org.eclipse.emf.henshin.model.Attribute henshinAttribute = henshinFactory.createAttribute();
					henshinAttribute.setType(ncAttribute.getType());

					if (ncAttribute.getOp().isEmpty() || ncAttribute.getOp().contentEquals(Constants.EQUALS)) {
						henshinAttribute.setValue(ncAttribute.getValue());
					} else if (!ncAttribute.getOp().isEmpty()) {
						Parameter henVar = henshinFactory.createParameter();

						// TODO the followings have to be checked again
						String nodeName = ncNode.getName();
						if (nodeName.contains("=")) {
							nodeName = nodeName.substring(0, nodeName.indexOf("="));
						}
						henVar.setName(VAR + nodeName.toUpperCase() + ncAttribute.getType().getName());

						henshinAttribute.setValue(henVar.getName());

						if (!this.henVarMappingsToNcAttribute.containsKey(henVar))
							this.henVarMappingsToNcAttribute.put(henVar, ncAttribute);
					}

					henshinNode.getAttributes().add(henshinAttribute);
				}
			}
			nodeMappingsToHenshin.put(ncNode, henshinNode);
			this.henshinGraph.getNodes().add(henshinNode);
		}
	}

	/**
	 * For each Henshin element, we create an element of our graph
	 */
	public void adaptFromHenshin() {
		this.graph.setTypegraph(this.typeModel);
		adaptNodesWithAttributesFromHenshin();
		adaptEdgesFromHenshin();
	}

	/**
	 * For each Henshin edge, we create an edge of our graph
	 */
	private void adaptEdgesFromHenshin() {
		for (org.eclipse.emf.henshin.model.Edge henshinEdge : henshinGraph.getEdges()) {
			Edge edge = factory.createEdge();
			edge.setType(henshinEdge.getType());
			edge.setSource(nodeMappingsFromHenshin.get(henshinEdge.getSource()));
			edge.setTarget(nodeMappingsFromHenshin.get(henshinEdge.getTarget()));
			this.graph.getEdges().add(edge);
		}
	}

	/**
	 * For each Henshin node, we create a node of our graph
	 */
	private void adaptNodesWithAttributesFromHenshin() {
		for (org.eclipse.emf.henshin.model.Node henshinNode : henshinGraph.getNodes()) {
			Node node = factory.createNode();
			node.setType(henshinNode.getType());
			node.setName(henshinNode.getName());
			nodeMappingsFromHenshin.put(henshinNode, node);
			// TODO improve and complete it
			for (org.eclipse.emf.henshin.model.Attribute henAttribute : henshinNode.getAttributes()) {
				Attribute ncAttribute = factory.createAttribute();
				ncAttribute.setType(henAttribute.getType());
				ncAttribute.setValue(henAttribute.getValue());
				// TODO improve and complete it
				ncAttribute.setOp(Constants.EQUALS);
				node.getAttributes().add(ncAttribute);
			}
			this.graph.getNodes().add(node);
		}
	}

	public graph.Graph getGraph() {
		return graph;
	}

	public Graph getHenshinGraph() {
		return henshinGraph;
	}
}
