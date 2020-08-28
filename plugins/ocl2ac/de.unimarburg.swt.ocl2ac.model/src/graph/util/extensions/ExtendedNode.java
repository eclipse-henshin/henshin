/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package graph.util.extensions;

import java.util.ArrayList;
import java.util.List;

import graph.Node;

public class ExtendedNode {

	private Node node = null;
	private ExtendedNode predecessor = null;
	private List<ExtendedNode> successors;

	public ExtendedNode(Node n) {
		node = n;
		successors = new ArrayList<ExtendedNode>();
	}

	public ExtendedNode getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(ExtendedNode predecessor) {
		this.predecessor = predecessor;
	}

	public List<ExtendedNode> getSuccessors() {
		return successors;
	}

	public void addSuccessor(ExtendedNode successor) {
		Node successorNode = successor.getNode();
		boolean exists = false;
		for (ExtendedNode extendedNode : successors) {
			if (extendedNode.getNode() == successorNode) {
				exists = true;
				break;
			}
		}
		if (!exists)
			this.successors.add(successor);
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

}
