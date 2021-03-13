/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.ocl2gc.util;

import graph.Graph;
import nestedcondition.Morphism;

public class MorphismPair {

	private Graph P = null;
	private Graph C = null;
	private Graph resultGraph = null;
	private Morphism morphismP = null;
	private Morphism morphismC = null;

	public MorphismPair(Graph p, Graph c) {
		super();
		P = p;
		C = c;
	}

	public Graph getResultGraph() {
		return resultGraph;
	}

	public void setResultGraph(Graph resultGraph) {
		this.resultGraph = resultGraph;
	}

	public Morphism getMorphismP() {
		return morphismP;
	}

	public void setMorphismP(Morphism morphismP) {
		this.morphismP = morphismP;
	}

	public Morphism getMorphismC() {
		return morphismC;
	}

	public void setMorphismC(Morphism morphismC) {
		this.morphismC = morphismC;
	}

	public Graph getP() {
		return P;
	}

	public Graph getC() {
		return C;
	}
}
