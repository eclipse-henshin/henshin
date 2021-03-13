/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package laxcondition.util.extensions;

import graph.Node;

public class NodeMapping {

	private Node sourceNode;

	public NodeMapping(Node sourceNode, Node targetNode) {
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
	}

	private Node targetNode;

	public Node getSourceNode() {
		return sourceNode;
	}

	public Node getTargetNode() {
		return targetNode;
	}

}
