package org.eclipse.emf.henshin.statespace.validation;

import java.text.ParseException;

import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Interface for Henshin state space model checkers.
 * @author Christian Krause
 */
public interface ModelChecker {
	
	/**
	 * Set the state space to be model checked.
	 * @param stateSpace State space.
	 */
	void setStateSpace(StateSpace stateSpace);

	/**
	 * Parse a formula. This is used only for verifying if a formula
	 * is syntactically correct according to this model checker.
	 * @param formula Formula to be parsed.
	 * @throws ParseException On parse errors.
	 */
	void parseFormula(String formula) throws ParseException;
	
	/**
	 * Model check a formula based on the currently set state space.
	 * @param formula Formula to be checked.
	 * @return <code>true</code> if the formula is satisfied.
	 * @throws ParseException On parse errors.
	 */
	boolean checkFormula(String formula) throws ParseException;
	
}
