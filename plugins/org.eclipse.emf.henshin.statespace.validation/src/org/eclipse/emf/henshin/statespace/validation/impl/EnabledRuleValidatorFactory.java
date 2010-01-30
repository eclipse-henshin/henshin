package org.eclipse.emf.henshin.statespace.validation.impl;

import java.text.ParseException;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidatorFactory;

/**
 * Factory for EnabledRuleValidators.
 * @author Christian Krause
 */
public class EnabledRuleValidatorFactory implements StateSpaceValidatorFactory {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidatorFactory#createValidator(java.lang.String, org.eclipse.emf.henshin.statespace.StateSpace)
	 */
	public StateSpaceValidator createValidator(String ruleName, StateSpace stateSpace) throws ParseException {
		
		// Rule name cannot be empty.
		if (ruleName==null) {
			throw new IllegalArgumentException("Rule name cannot be null");
		}
		
		// Find the corresponding rule:
		for (Rule rule : stateSpace.getRules()) {
			if (ruleName.equals(rule.getName())) {
				return new EnabledRuleValidator(rule);
			}
		}
		
		// Not found:
		throw new ParseException("Unknown rule: " + ruleName, 0); 
		
	}
	
}
