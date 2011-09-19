package org.eclipse.emf.henshin.matching.constraints;


public interface BinaryConstraint extends Constraint {
	/**
	 * Evaluates the constraint between the given domain slots.
	 * BinaryConstraints may only be checked <i>after</i> the source slot has
	 * been locked.
	 * 
	 * @param source
	 *            The (locked) domain slot initiating the evaluation.
	 * @param target
	 *            The domain slot which domain will be checked.
	 * @return true, if the constraint is compatible with the given slot pair.
	 */
	public boolean check(DomainSlot source, DomainSlot target);
}
