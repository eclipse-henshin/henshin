package org.eclipse.emf.henshin.multicda.cda;

import java.util.Set;

import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public interface MultiGranularAnalysis {

	public Reason computeResultsBinary();

	public Set<? extends MinimalReason> computeResultsCoarse();

	public Set<? extends Reason> computeResultsFine();

	public Set<? extends Atom> computeAtoms();
}
