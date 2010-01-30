package org.eclipse.emf.henshin.statespace.validation.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;


public abstract class StateSpaceValidatorDelegate extends AbstractStateSpaceValidator {

	/**
	 * Create a new state space validator instance based on a given ID.
	 * @param id Validator ID.
	 * @return The new validator instance;
	 */
	protected abstract StateSpaceValidator createStateSpaceValidator(String id);

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#setProperty(java.lang.String)
	 */
	public void setProperty(String property) throws ParseException {
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator#validate(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ValidationResult validate(IProgressMonitor monitor) {
		return null;
	}
	
	/*
	 * Internal helper class for parsing wrapped validation properties.
	 */
	static class Parser extends StateValidatorDelegate.Parser {
		
		public static final String WHERE = "where";
		public static final char EQUAL = '=';
		public static final char END = ';';
		
		List<String> stateValidatorIds = new ArrayList<String>();
		List<String> stateValidatorbodies = new ArrayList<String>();
		
		Parser(String property) {
			super(property);
		}
		
		void parse() throws ParseException {
			super.parse();
			whitespace();
			if (index<unparsed.length) {
				if (!WHERE.equals(ident())) throw new ParseException("Expected '" + WHERE + "'", index);
			}
		}
	}
}
