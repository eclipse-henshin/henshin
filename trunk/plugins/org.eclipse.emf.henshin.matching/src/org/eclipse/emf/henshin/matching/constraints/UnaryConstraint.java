package org.eclipse.emf.henshin.matching.constraints;


public interface UnaryConstraint extends Constraint {
	
	/**
	 * Evaluates this constraint on the given domain slot. Unary constraints may
	 * be checked at any time.
	 * 
	 * @param slot
	 *            The domain slot the constraint will be evaluated on.
	 * @return true, if the constraint is valid
	 */
	public boolean check(DomainSlot slot);
}
