/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.validation.impl;

import java.text.ParseException;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * Validator that checks whether a rule is enabled in a given state.
 * @author Christian Krause
 */
public class EnabledRuleValidator extends AbstractStateValidator {
	
	// The rule that should be enabled.
	private Rule rule;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#validate(org.eclipse.emf.henshin.statespace.State)
	 */
	public ValidationResult validate(State state) {
		
		// Any rule ok?
		if (rule==null && !state.getOutgoing().isEmpty()) {
			return ValidationResult.VALID;
		}
		
		// Try to find a matching transition:
		for (Transition transition : state.getOutgoing()) {
			if (transition.getRule()==rule) {
				return ValidationResult.VALID;
			}
		}
		
		// Rule is not enabled in this state.
		return ValidationResult.INVALID;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#setProperty(java.lang.String)
	 */
	public void setProperty(String ruleName) throws ParseException {
		
		// Rule name cannot be empty.
		if (ruleName==null) {
			throw new IllegalArgumentException("Rule name cannot be null");
		}
		
		// Find the corresponding rule:
		for (Rule rule : getStateSpaceIndex().getStateSpace().getRules()) {
			if (ruleName.equals(rule.getName())) {
				this.rule = rule;
				return;
			}
		}
		
		// Not found:
		throw new ParseException("Unknown rule: " + ruleName, 0); 
	}
	
}
