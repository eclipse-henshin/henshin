package org.eclipse.emf.henshin.multicda.cda;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class DependencyAnalysis implements MultiGranularAnalysis {

	private Rule rule1INV;
	private Rule rule2;
	private ConflictAnalysis ca;

	private Set<DependencyAtom> atoms = new HashSet<>();
	private Set<MinimalDependencyReason> mdrs = new HashSet<>();
	private Set<DependencyReason> drs = new HashSet<>();

	// Constructor
	public DependencyAnalysis(Rule rule1, Rule rule2) {
		this.rule2 = rule2;
		this.rule1INV = Utils.invertRule(rule1);
		this.ca = new ConflictAnalysis(rule1INV, rule2);
	}

	@Override
	public Set<DependencyAtom> computeAtoms() {
		return computeDependencyAtoms();
	}

	@Override
	public Reason computeResultsBinary() {
		return hasDependencies();
	}

	@Override
	public Set<MinimalDependencyReason> computeResultsCoarse() {
		return computeMinimalDependencyReasons();
	}

	@Override
	public Set<DependencyReason> computeResultsFine() {
		return computeDependencyReasons();
	}

	private DependencyAtom hasDependencies() {
		ConflictAtom conflictAtom = ca.computeResultsBinary();
		if (conflictAtom != null) {
			return ReasonFactory.eINSTANCE.createDependencyAtom(conflictAtom);
		} else
			return null;
	}

	private Set<DependencyAtom> computeDependencyAtoms() {
		if (atoms.isEmpty())
			for (ConflictAtom atom : ca.computeAtoms())
				atoms.add(ReasonFactory.eINSTANCE.createDependencyAtom(atom));
		return atoms;
	}

	private Set<MinimalDependencyReason> computeMinimalDependencyReasons() {
		if (mdrs.isEmpty())
			for (MinimalConflictReason cr : ca.computeResultsCoarse())
				mdrs.add(ReasonFactory.eINSTANCE.createMinimalDependencyReason(cr));
		return mdrs;
	}

	private Set<DependencyReason> computeDependencyReasons() {
		if (drs.isEmpty())
			for (ConflictReason cr : ca.computeResultsFine())
				drs.add(ReasonFactory.eINSTANCE.createDependencyReason(cr));
		return drs;
	}

}
