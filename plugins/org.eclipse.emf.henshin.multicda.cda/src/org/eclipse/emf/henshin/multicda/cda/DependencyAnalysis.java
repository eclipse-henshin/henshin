package org.eclipse.emf.henshin.multicda.cda;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.dependency.DependencyReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DependencyAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class DependencyAnalysis implements MultiGranularAnalysis {

	private Rule rule1INV;
	private ConflictAnalysis ca;

	private Set<DependencyAtom> atoms = new HashSet<>();
	private Set<Reason> mdrs = new HashSet<>();
	private Set<Reason> drs = new HashSet<>();

	public DependencyAnalysis(Rule rule1, Rule rule2) {
		this.rule1INV = Utils.invertRule(rule1);
		this.ca = new ConflictAnalysis(rule1INV, rule2);
	}

	@Override
	public Set<DependencyAtom> computeAtoms() {
		return computeDependencyAtoms();
	}

	@Override
	public DependencyAtom computeResultsBinary() {
		ConflictAtom conflictAtom = ca.computeResultsBinary();
		if (conflictAtom != null) {
			return ReasonFactory.eINSTANCE.createDependencyAtom(conflictAtom);
		} else
			return null;
	}

	@Override
	public Set<Reason> computeResultsCoarse() {
		if (mdrs.isEmpty())
			for (Reason cr : ca.computeResultsCoarse()) {
				Reason r = ReasonFactory.eINSTANCE.createMinimalDependencyReason(cr);
				if (r != null)
					mdrs.add(r);
			}
		return mdrs;
	}

	@Override
	public Set<Reason> computeResultsFine() {
		if (drs.isEmpty())
			for (Reason cr : ca.computeResultsFine()) {
				Reason r = ReasonFactory.eINSTANCE.createDependencyReason(cr);
				if (r != null)
					drs.add(r);
			}
		return drs;
	}

	private Set<DependencyAtom> computeDependencyAtoms() {
		if (atoms.isEmpty())
			for (ConflictAtom atom : ca.computeAtoms())
				atoms.add(ReasonFactory.eINSTANCE.createDependencyAtom(atom));
		return atoms;
	}

}
