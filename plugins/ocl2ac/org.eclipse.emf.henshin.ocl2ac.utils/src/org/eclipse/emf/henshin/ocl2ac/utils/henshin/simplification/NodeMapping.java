/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification;

import org.eclipse.emf.henshin.model.Node;

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
