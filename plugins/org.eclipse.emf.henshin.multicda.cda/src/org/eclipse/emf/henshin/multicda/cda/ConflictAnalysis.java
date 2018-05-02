package org.eclipse.emf.henshin.multicda.cda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.computation.AtomCandidateComputation;
import org.eclipse.emf.henshin.multicda.cda.computation.ConflictReasonComputation;
import org.eclipse.emf.henshin.multicda.cda.computation.DeleteUseConflictReasonComputation;
import org.eclipse.emf.henshin.multicda.cda.computation.MinimalReasonComputation;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.CreateForbidConflictReason;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason.DeleteRequireConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Atom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ChangeAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteAttrConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.DeleteUseConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.ForbidConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.Atom.RequireConflictAtom;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalCreateForbidConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.MinimalReason.MinimalDeleteRequireConflictReason;
import org.eclipse.emf.henshin.multicda.cda.units.Reason;

public class ConflictAnalysis implements MultiGranularAnalysis {

	//WARNING: Preliminary implementation, not tested yet.
	//This is a constant to activate not tested implementation of advanced conflict and dependency analysis.
	public final static boolean COMPLETE_COMPUTATION = false;

	private Rule rule1;
	private Rule rule1INV;
	private Rule rule2NonDelete;
	private Rule rule2;
	private Set<Rule> rule2NACs;
	private Set<Rule> rule2PACs;

	private Set<ConflictAtom> atoms = new HashSet<>();
	private Set<MinimalConflictReason> mcrs = new HashSet<>();
	private Set<ConflictReason> crs = new HashSet<>();

	/**
	 * @param rule1
	 * @param rule2
	 */
	public ConflictAnalysis(Rule rule1, Rule rule2) {
		Utils.checkNull(rule1);
		Utils.checkNull(rule2);
		prepare(rule1, rule2);
		this.rule2NonDelete = Utils.nonDeleteRule(rule2);
		this.rule1INV = Utils.invertRule(rule1);
		this.rule2NACs = Utils.createNACRule(rule2);
		this.rule2PACs = Utils.createPACRule(rule2);
	}

	@Override
	public Set<ConflictAtom> computeAtoms() {
		return computeConflictAtoms();
	}

	@Override
	public ConflictAtom computeResultsBinary() {
		ConflictAtom result = hasConflicts();
		if (result == null)
			return null;
		else
			return result;
	}

	@Override
	public Set<MinimalConflictReason> computeResultsCoarse() {
		if (mcrs.isEmpty()) {
			if (!COMPLETE_COMPUTATION)
				mcrs = new MinimalReasonComputation(rule1, rule2NonDelete).computeMinimalConflictReasons();
			else {
				//WARNING: Preliminary implementation, not tested yet.
				mcrs = computeMinimalConflictReasons(rule1, rule2);
				for (Rule rNac : rule2NACs)
					for (Reason mcr : computeMinimalConflictReasons(rule1INV, rNac))
						mcrs.add(new MinimalCreateForbidConflictReason(mcr));
				for (Rule rPac : rule2PACs)
					for (Reason mcr : computeMinimalConflictReasons(rule1, rPac))
						mcrs.add(new MinimalDeleteRequireConflictReason(mcr));
			}
		}
		return mcrs;
	}

	@Override
	public Set<ConflictReason> computeResultsFine() {
		if (crs.isEmpty()) {
			if (!COMPLETE_COMPUTATION)
				crs = new HashSet<>(new ConflictReasonComputation(rule1, rule2NonDelete).computeConflictReasons());
			else {
				//WARNING: Preliminary implementation, not tested yet.
				crs = computeDeleteUseConflictReasons(rule1, rule2);
				crs.addAll(computeCreateForbidConflictReasons());
				crs.addAll(computeDeleteRequireConflictReasons());
			}
		}
		return crs;
	}

	//WARNING: Preliminary implementation, not tested yet.
	private Set<CreateForbidConflictReason> computeCreateForbidConflictReasons() {
		Set<CreateForbidConflictReason> result = new HashSet<>();
		for (Rule r2 : rule2NACs)
			for (Reason confReason : computeDeleteUseConflictReasons(rule1INV, r2))
				result.add(new CreateForbidConflictReason(confReason));
		return result;
	}

	//WARNING: Preliminary implementation, not tested yet.
	private Collection<DeleteRequireConflictReason> computeDeleteRequireConflictReasons() {
		Set<DeleteRequireConflictReason> result = new HashSet<>();
		for (Rule r2 : rule2PACs)
			for (Reason confReason : computeDeleteUseConflictReasons(rule1, r2))
				result.add(new DeleteRequireConflictReason(confReason));
		return result;
	}

	private ConflictAtom hasConflicts() {
		Set<ConflictAtom> cas = computeConflictAtoms(true);
		if (cas.isEmpty())
			return null;
		else
			return new ArrayList<>(cas).get(0);
	}

	private Set<ConflictAtom> computeConflictAtoms(boolean... earlyExit) {
		if (atoms.isEmpty()) {
			List<Atom> candidates = new AtomCandidateComputation(rule1, rule2NonDelete).computeAtomCandidates();

			//WARNING: Preliminary implementation, not tested yet.
			if (COMPLETE_COMPUTATION) {
				for (Rule rNac : rule2NACs)
					for (Atom a : new AtomCandidateComputation(rule1INV, rNac).computeAtomCandidates())
						candidates.add(new ForbidConflictAtom(a));
				for (Rule rPac : rule2PACs)
					for (Atom a : new AtomCandidateComputation(rule1, rPac).computeAtomCandidates())
						candidates.add(new RequireConflictAtom(a));
			}
			for (Atom candidate : candidates)
				if (Utils.checkAttributes(candidate)) {
					Set<MinimalConflictReason> minimalConflictReasons = new HashSet<>();
					if (candidate instanceof ForbidConflictAtom)
						for (Rule rNac : rule2NACs)
							new MinimalReasonComputation(rule1INV, rNac).computeMinimalConflictReasons(candidate,
									minimalConflictReasons);
					else if (candidate instanceof RequireConflictAtom)
						for (Rule rPac : rule2PACs)
							new MinimalReasonComputation(rule1, rPac).computeMinimalConflictReasons(candidate,
									minimalConflictReasons);
					else
						new MinimalReasonComputation(rule1, rule2NonDelete).computeMinimalConflictReasons(candidate,
								minimalConflictReasons);

					ConflictAtom a = null;
					if (!minimalConflictReasons.isEmpty()) {
						if (candidate instanceof ForbidConflictAtom)
							a = new ForbidConflictAtom(candidate, minimalConflictReasons);
						else if (candidate instanceof RequireConflictAtom)
							a = new RequireConflictAtom(candidate, minimalConflictReasons);
						else if (candidate instanceof ChangeAttrConflictAtom)
							a = new ChangeAttrConflictAtom(candidate, minimalConflictReasons);
						else if (candidate instanceof DeleteAttrConflictAtom)
							a = new DeleteAttrConflictAtom(candidate, minimalConflictReasons);
						else
							a = new DeleteUseConflictAtom(candidate, minimalConflictReasons);
						atoms.add(a);
					}
					if (!atoms.isEmpty() && earlyExit.length > 0 && earlyExit[0] == true) {
						Set<ConflictAtom> temp = atoms;
						atoms = new HashSet<>();
						return temp;
					}
				}
		}
		return atoms;
	}

	//WARNING: Preliminary implementation, not tested yet.
	private Set<MinimalConflictReason> computeMinimalConflictReasons(Rule r1, Rule r2) {
		Set<MinimalConflictReason> normalCR = new MinimalReasonComputation(r1, Utils.nonDeleteRule(r2))
				.computeMinimalConflictReasons();
		Set<MinimalConflictReason> DUCR = new HashSet<>();
		if (r1 == r2)
			DUCR = normalCR;
		else
			DUCR = new MinimalReasonComputation(r2, Utils.nonDeleteRule(r1)).computeMinimalConflictReasons();
		return new DeleteUseConflictReasonComputation<MinimalConflictReason>(r1, r2, normalCR, DUCR)
				.computeDeleteUseConflictReason();
	}

	//WARNING: Preliminary implementation, not tested yet.
	private Set<ConflictReason> computeDeleteUseConflictReasons(Rule r1, Rule r2) {
		Set<ConflictReason> normalCR = new ConflictReasonComputation(r1, Utils.nonDeleteRule(r2))
				.computeConflictReasons();
		Set<ConflictReason> DUCR = new HashSet<>();
		if (r1 == r2)
			DUCR = normalCR;
		else
			DUCR = new ConflictReasonComputation(r2, Utils.nonDeleteRule(r1)).computeConflictReasons();
		return new DeleteUseConflictReasonComputation<ConflictReason>(r1, r2, normalCR, DUCR)
				.computeDeleteUseConflictReason();
	}

	/**
	 * Renames all non named nodes of Rules, for better understandability: |1|, |2|, |3|...
	 */
	private int count = 0;

	private void prepare(Rule r1, Rule r2) {
		rule1 = r1;
		rule2 = r2;
		for (Node n : rule1.getRhs().getNodes())
			if (n.getName() == null || n.getName().isEmpty()) {
				n.setName("|" + count++ + "|");
				Node origin = rule1.getMappings().getOrigin(n);
				if (origin != null)
					origin.setName(n.getName());
			}
		for (Node n : rule1.getLhs().getNodes())
			if (n.getName() == null || n.getName().isEmpty())
				n.setName("|" + count++ + "|");
		for (Node n : rule2.getRhs().getNodes())
			if (n.getName() == null || n.getName().isEmpty()) {
				n.setName("|" + count++ + "|");
				Node origin = rule2.getMappings().getOrigin(n);
				if (origin != null)
					origin.setName(n.getName());
			}
		for (Node n : rule2.getLhs().getNodes())
			if (n.getName() == null || n.getName().isEmpty())
				n.setName("|" + count++ + "|");
		count = 0;
	}
}