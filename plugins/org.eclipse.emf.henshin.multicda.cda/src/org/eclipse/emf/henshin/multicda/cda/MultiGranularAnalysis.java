package org.eclipse.emf.henshin.multicda.cda;

import java.util.Set;

import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public interface MultiGranularAnalysis {

	/**
	 * @return first atom or null if there are no reasons
	 */
	public Atom computeResultsBinary();

	/**
	 * @return set of minimal reasons
	 */
	public Set<? extends Reason> computeResultsCoarse();

	/**
	 * @return set of reasons
	 */
	public Set<? extends Reason> computeResultsFine();

	/**
	 * @return set of atoms
	 */
	public Set<? extends Atom> computeAtoms();
}
