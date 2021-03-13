/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package laxcondition.util.extensions;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class Intersection {

	public Graph getSourceGraph() {
		return (Graph) nodeMappings.get(0).getSourceNode().eContainer();
	}

	private EList<NodeMapping> nodeMappings;

	public EList<NodeMapping> getNodeMappings() {
		return nodeMappings;
	}

	public EList<EdgeMapping> getEdgeMappings() {
		return edgeMappings;
	}

	private EList<EdgeMapping> edgeMappings;

	public Intersection() {
		nodeMappings = new BasicEList<NodeMapping>();
		edgeMappings = new BasicEList<EdgeMapping>();
	}

	public void addNodeMapping(NodeMapping nodeMapping) {
		nodeMappings.add(nodeMapping);
	}

	public void addEdgeMapping(EdgeMapping edgeMapping) {
		edgeMappings.add(edgeMapping);
	}

	public boolean containsTarget(Node node) {
		for (NodeMapping mapping : nodeMappings) {
			if (mapping.getTargetNode() == node) {
				return true;
			}
		}
		return false;
	}

	public boolean containsTarget(Edge edge) {
		for (EdgeMapping mapping : edgeMappings) {
			if (mapping.getTargetEdge() == edge) {
				return true;
			}
		}
		return false;
	}

	public Node getSourceNode(Node node) {
		for (NodeMapping mapping : nodeMappings) {
			if (mapping.getTargetNode() == node) {
				return mapping.getSourceNode();
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("Node Mappings:");
		for (NodeMapping nodeMapping : nodeMappings) {
			result.append("\n- from ");
			result.append(nodeMapping.getSourceNode().toString());
			result.append(" to ");
			result.append(nodeMapping.getTargetNode().toString());
			result.append("\n- graphs: " + nodeMapping.getSourceNode().eContainer() + " and "
					+ nodeMapping.getTargetNode().eContainer());
		}
		result.append("\nEdge Mappings: ");
		for (EdgeMapping edgeMapping : edgeMappings) {
			result.append("\n- between ");
			result.append(edgeMapping.getSourceEdge().toString());
			result.append(" and ");
			result.append(edgeMapping.getTargetEdge().toString());
		}
		return result.toString();
	}

	public boolean containsSource(Node node) {
		for (NodeMapping mapping : nodeMappings) {
			Node sourceNode = mapping.getSourceNode();
			if (sourceNode.getName().equals(node.getName()) && sourceNode.getType() == node.getType()) {
				return true;
			}
		}
		return false;
	}

	public Node getSourceNode1(Node node) {
		for (NodeMapping mapping : nodeMappings) {
			Node targetNode = mapping.getTargetNode();
			if (targetNode.getName().equals(node.getName()) && targetNode.getType() == node.getType()) {
				return mapping.getSourceNode();
			}
		}
		return null;
	}
}
