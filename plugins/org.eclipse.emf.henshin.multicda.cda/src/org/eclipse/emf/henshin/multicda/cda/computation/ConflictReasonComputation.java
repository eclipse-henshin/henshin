package org.eclipse.emf.henshin.multicda.cda.computation;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cda.ReasonFactory;
import org.eclipse.emf.henshin.multicda.cda.Utils;
import org.eclipse.emf.henshin.multicda.cda.conflict.ConflictReason;

public class ConflictReasonComputation {

	static Action deleteAction = new Action(Action.Type.DELETE);
	static Action preserveAction = new Action(Action.Type.PRESERVE);
	static HenshinFactory henshinFactory = HenshinFactory.eINSTANCE;

	protected Rule rule1;
	protected Rule rule2;

	public ConflictReasonComputation(Rule rule1, Rule rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
	}

	public Set<ConflictReason> computeConflictReasons() {
		Set<ConflictReason> minimalReasons = new MinimalReasonComputation(rule1, rule2).computeMinimalConflictReasons();
		return computeConflictReasons(minimalReasons);
	}

	public Set<ConflictReason> computeConflictReasons(Set<ConflictReason> minimalConflictReasons) {
		Set<ConflictReason> result = new HashSet<>();
		for (ConflictReason mcr : minimalConflictReasons) {
			Set<ConflictReason> remainingMCR = new HashSet<>(minimalConflictReasons);
			remainingMCR.remove(mcr);

			result.addAll(Utils.computeMinimalConflictReasons(mcr, remainingMCR));

			result.add(ReasonFactory.eINSTANCE.createDRReason(mcr));
		}
		return result;
	}

}
