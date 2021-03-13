/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification;

import org.eclipse.emf.henshin.model.Edge;

public class EdgeMapping {

	private Edge sourceEdge;

	public EdgeMapping(Edge sourceEdge, Edge targetEdge) {
		this.sourceEdge = sourceEdge;
		this.targetEdge = targetEdge;
	}

	private Edge targetEdge;

	public Edge getSourceEdge() {
		return sourceEdge;
	}

	public Edge getTargetEdge() {
		return targetEdge;
	}

}
