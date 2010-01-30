package org.eclipse.emf.henshin.statespace.validation.impl;

import java.text.ParseException;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.validation.StateValidator;
import org.eclipse.emf.henshin.statespace.validation.ValidationResult;

/**
 * State validator delegate.
 * @author Christian Krause
 */
public abstract class StateValidatorDelegate extends AbstractStateValidator {
	
	// The wrapped state validator.
	private StateValidator validator;
	
	/**
	 * Create a new state validator instance based on a given ID.
	 * @param id Validator ID.
	 * @return The new validator instance;
	 */
	protected abstract StateValidator createStateValidator(String id);
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#setProperty(java.lang.String)
	 */
	public void setProperty(String property) throws ParseException {
		
		// Parse the wrapped property:
		Parser parser = new Parser(property);
		parser.parse();
		
		// Create and initialize the actual validator:
		validator = createStateValidator(parser.validatorId);
		validator.setProperty(parser.body.trim());
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#validate(org.eclipse.emf.henshin.statespace.State)
	 */
	public ValidationResult validate(State state) {
		return validator.validate(state);
	}
	
	/*
	 * Internal helper class for parsing wrapped validation properties.
	 */
	static class Parser {
		
		public static final String BODY_START = "{{";
		public static final String BODY_END = "}}";
		
		protected char[] unparsed;
		protected int index = 0;
		
		String validatorId, body;

		Parser(String property) {
			unparsed = property.toCharArray();
		}
		
		void parse() throws ParseException {
			whitespace();
			validatorId = ident();
			whitespace();
			body = body();
		}
		
		protected String body() throws ParseException {
			for (int i=0; i<BODY_START.length(); i++) {
				if (index>unparsed.length || unparsed[index++]!=BODY_START.charAt(i))
					throw new ParseException("Expected '" + BODY_START + "'", index);
			}
			String body = ""; int matched = 0;
			while (matched<BODY_END.length()) {
				char next = next();
				if (next==BODY_END.charAt(matched)) matched++;
				else {
					body = body + ((matched>0) ? BODY_END.substring(0,matched) : next);
					matched = 0;
				}
			}
			return body;
		}
		
		protected String ident() throws ParseException {
			if (!Character.isJavaIdentifierStart(next())) {
				throw new ParseException("Expected identifier", index);
			}
			int last = index + 1;
			while (last<unparsed.length && Character.isJavaIdentifierPart(unparsed[last])) last++;
			String ident = new String(unparsed,index,last-index);
			index = last;
			return ident;
		}
		
		protected void whitespace() {
			while (index<unparsed.length && Character.isWhitespace(unparsed[index])) index++;
		}
		
		char next() throws ParseException {
			if (index>=unparsed.length) throw new ParseException("Unexpected EOF", index);
			return unparsed[index++];
		}
	}
	
}
