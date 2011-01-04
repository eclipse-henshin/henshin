package org.eclipse.emf.henshin.internal.constraints;

import org.eclipse.emf.henshin.internal.matching.DomainSlot;

public interface BinaryConstraint extends Constraint {
	/**
	 * Evaluates the constraint between the given domain slots.
	 * BinaryConstraints may only be checked *after* the source slot has been
	 * locked.
	 * 
	 * @param source
	 *            The domain slot initiating the evaluation.
	 * @param target
	 *            The domain slot which domain will be checked.
	 * @return true, if the constraint is compatible with the given slot pair.
	 */
	public boolean check(DomainSlot source, DomainSlot target);
}
