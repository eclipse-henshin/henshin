package org.eclipse.emf.henshin.statespace.validation.impl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationContext;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;

/**
 * Validator that checks whether a rule is enabled in a given state.
 * @author Christian Krause
 */
public class EnabledRuleValidator implements StateSpaceValidator {
	
	// The rule that should be enabled.
	private Rule rule;
	
	/**
	 * Default constructor.
	 * @param rule Rule that should be enabled.
	 */
	public EnabledRuleValidator(Rule rule) {
		this.rule = rule;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#validate(org.eclipse.emf.henshin.statespace.State, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public StateSpaceValidationResult validate(State state, IProgressMonitor monitor) {
		
		// Any rule ok?
		if (rule==null && !state.getOutgoing().isEmpty()) {
			monitor.done();
			return StateSpaceValidationResultImpl.VALID;
		}
		
		// Try to find a matching transition:
		for (Transition transition : state.getOutgoing()) {
			if (transition.getRule()==rule) {
				monitor.done();
				return StateSpaceValidationResultImpl.VALID;
			}
		}
		
		// Rule is not enabled in this state.
		monitor.done();
		return StateSpaceValidationResultImpl.INVALID;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setContext(org.eclipse.emf.henshin.statespace.validation.StateSpaceValidationContext)
	 */
	public void setContext(StateSpaceValidationContext context) {
		// We don't need the context.
	}
	
}
