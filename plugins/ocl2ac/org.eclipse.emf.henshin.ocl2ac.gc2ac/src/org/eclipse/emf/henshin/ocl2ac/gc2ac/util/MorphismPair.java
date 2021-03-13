/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.gc2ac.util;

import nestedcondition.Morphism;

public class MorphismPair {

	private Morphism aPrime;
	private Morphism bPrime;

	public Morphism getAPrime() {
		return aPrime;
	}

	public Morphism getBPrime() {
		return bPrime;
	}

	public void setMorphismAPrime(Morphism aPrime) {
		this.aPrime = aPrime;
	}

	public void setMorphismBPrime(Morphism bPrime) {
		this.bPrime = bPrime;
	}

}
