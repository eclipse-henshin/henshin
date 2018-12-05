package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.multicda.cda.conflict.EssentialConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;

public class ReasonComputation {

	public Set<EssentialConflictReason> computeConflictReasons(List<ConflictAtom> conflictAtoms,
			Set<Reason> initialReasons) {
		Set<EssentialConflictReason> conflictReasonsDerivedFromInitialReason = new HashSet<EssentialConflictReason>();
		Set<Reason> originMCRs = new HashSet<>();
		for (Reason initialReason : initialReasons) {
			originMCRs.addAll(initialReason.getOriginMCRs());
			Set<Atom> byInitialReasonCoveredEdgeConflictAtoms = initialReason.getCoveredEdgeConflictAtoms();
			Set<ConflictAtom> allEdgeConflictAtoms = extractEdgeConflictAtoms(conflictAtoms);
			allEdgeConflictAtoms.removeAll(byInitialReasonCoveredEdgeConflictAtoms);
			Set<ConflictAtom> byInitialReasonUncoveredConflictAtoms = allEdgeConflictAtoms;
			Set<EssentialConflictReason> allDerivedConflictReasons = initialReason
					.getAllDerivedConflictReasons(byInitialReasonUncoveredConflictAtoms);
			conflictReasonsDerivedFromInitialReason.addAll(allDerivedConflictReasons);
		}
		return conflictReasonsDerivedFromInitialReason;
	}

	public Set<ConflictAtom> extractEdgeConflictAtoms(List<ConflictAtom> computedConflictAtoms) {
		Set<ConflictAtom> edgeConflictAtoms = new HashSet<ConflictAtom>();
		for (ConflictAtom ca : computedConflictAtoms) {
			if (ca.isDeleteEdgeConflictAtom())
				edgeConflictAtoms.add(ca);
		}
		return edgeConflictAtoms;
	}
}
