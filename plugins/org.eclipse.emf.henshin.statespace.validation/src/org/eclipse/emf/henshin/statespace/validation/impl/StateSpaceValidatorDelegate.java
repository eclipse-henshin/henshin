package org.eclipse.emf.henshin.statespace.validation.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.validation.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * State space validator delegate.
 * @author Christian Krause
 */
public class StateSpaceValidatorDelegate extends AbstractStateSpaceValidator {

	// Validator types.
	private Map<String,Class<StateSpaceValidator>> validatorTypes = new HashMap<String,Class<StateSpaceValidator>>();
	
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
	
	/**
	 * Get the supported state space validator types.
	 * @return State space validator types.
	 */
	public Map<String, Class<StateSpaceValidator>> getValidatorTypes() {
		return validatorTypes;
	}

	/**
	 * Create a new state space validator instance based on a given ID.
	 * @param id Validator ID.
	 * @return The new validator instance;
	 */
	protected StateSpaceValidator createStateValidator(String id) {
		Class<StateSpaceValidator> type = validatorTypes.get(id);
		if (id==null) throw new RuntimeException("Unknown validator type id: '" + id + "'");
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error loading validator '" + id + "'", e);
		}
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
