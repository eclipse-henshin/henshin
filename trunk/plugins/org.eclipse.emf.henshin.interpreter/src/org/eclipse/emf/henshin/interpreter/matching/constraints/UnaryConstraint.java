package org.eclipse.emf.henshin.interpreter.matching.constraints;

/**
 * Interface for unary constraints.
 * 
 * @author Enrico Biermann Christian Krause
 */
public interface UnaryConstraint extends Constraint {
	
	/**
	 * Evaluates this constraint on the given domain slot. Unary constraints may
	 * be checked at any time.
	 * @param slot The domain slot the constraint will be evaluated on.
	 * @return <code>true</code>, if the constraint is valid.
	 */
	boolean check(DomainSlot slot);
	
}
