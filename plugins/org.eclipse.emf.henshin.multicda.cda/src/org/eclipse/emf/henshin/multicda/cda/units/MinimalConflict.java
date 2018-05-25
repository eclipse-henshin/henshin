package org.eclipse.emf.henshin.multicda.cda.units;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;

public class MinimalConflict extends CriticalPair {

	public MinimalConflict(Rule r1, Rule r2, Graph minimalConflictReason, AppliedAnalysis appliedAnalysis) {
		super(r1, r2, minimalConflictReason, appliedAnalysis);
	}

	public MinimalConflict(Rule firstRule, Rule originalRuleOfRule2, Span minimalConflictReason,
			List<Atom> conflictAtoms, List<Reason> conflictAtomCandidates, AppliedAnalysis appliedAnalysis) {
		this(firstRule, originalRuleOfRule2, minimalConflictReason.getGraph(), appliedAnalysis);
		this.conflictAtoms = conflictAtoms;
		this.conflictAtomCandidates = conflictAtomCandidates;
	}

	/**
	 * Kind of the conflict.
	 */
	ConflictKind conflictKind;

	List<Atom> conflictAtoms;

	List<Reason> conflictAtomCandidates;

}
