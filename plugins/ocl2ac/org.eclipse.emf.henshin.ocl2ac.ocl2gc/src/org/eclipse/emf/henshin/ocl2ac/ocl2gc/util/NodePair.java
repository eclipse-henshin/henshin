/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.util;

import org.eclipse.emf.ecore.EClass;

import graph.Node;

public class NodePair {

	private Node node1;
	private Node node2;
	private EClass type;

	public NodePair(Node node1, Node node2) {
		if (node1.getType() == node2.getType()) {
			this.node1 = node1;
			this.node2 = node2;
			this.type = node1.getType();
		}
	}

	public Node getNode1() {
		return node1;
	}

	public Node getNode2() {
		return node2;
	}

	public EClass getType() {
		return type;
	}

}
