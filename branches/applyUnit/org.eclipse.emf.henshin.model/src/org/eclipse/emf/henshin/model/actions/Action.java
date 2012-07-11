/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.actions;

import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This class represents an Action of a graph element. Actions consist of an
 * {@link ActionType} and a number of string arguments. Actions can be printed,
 * parsed and compared using equals().
 * 
 * @author Christian Krause
 * @author Stefan Jurack
 */
public class Action {

	/**
	 * Separator of the action type information
	 */
	private static final String SEPARATOR_TYPE = ":";
	private static final Pattern PATTERN_TYPE = Pattern.compile(SEPARATOR_TYPE);

	/**
	 * Separator of action type arguments
	 */
	private static final String SEPARATOR_ARGS = ",";
	private static final Pattern PATTERN_ARGS = Pattern.compile(SEPARATOR_ARGS);

	/**
	 * Amalgamation marker
	 */
	private static final String MARKER_AMALGAMATION = "*";

	/**
	 * Empty string array
	 */
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * Parses an action string for graph elements. Such string may have the form ".*:[.*[,.*]*]?".
	 * 
	 * @param value
	 *            String representation of the action.
	 * @return The parsed element action.
	 * @throws ParseException
	 *             On parse errors.
	 */
	public static Action parse(String value) throws ParseException {
		
		/*
		 * Check for a colon, as the string before a colon is suggested to be an
		 * action type informations.
		 */
		String[] typeAndArgs = PATTERN_TYPE.split(value, 2);
		
		boolean amalgamated = false;
		String trimmedType = typeAndArgs[0].trim();
		if (trimmedType.endsWith(MARKER_AMALGAMATION)) {
			amalgamated = true;
			trimmedType = trimmedType.substring(0, trimmedType.length()-1);
		}
		ActionType type = ActionType.parse(trimmedType);

		/*
		 * Check for further arguments, which occur after the colon and which
		 * have to be separated by a comma.
		 */
		String[] args;
		if (typeAndArgs.length == 2) {
			args = PATTERN_ARGS.split(typeAndArgs[1]);
		} else {
			args = EMPTY_STRING_ARRAY;
		}

		// Create and return the new action:
		return new Action(type, amalgamated, args);

	}

	// Action type.
	private ActionType type;

	// Amalgamated flag.
	private boolean amalgamated;

	// Optional arguments.
	private String[] arguments;


	/**
	 * Default constructor.
	 * @param type Action type. Must not be <code>null</code>!
	 * @param amalgamated Amalgamation flag.
	 * @param arguments Optional arguments.
	 */
	public Action(ActionType type, boolean amalgamated, String... arguments) {
		if (type == null)
			throw new IllegalArgumentException(
					"Parameter type must not be null.");
		this.type = type;
		this.amalgamated = amalgamated;
		this.arguments = (arguments == null) ? EMPTY_STRING_ARRAY : arguments;
	}

	/*
	 * Alternative constructor.
	 */
	public Action(ActionType type, String... arguments) {
		this(type, false, arguments);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object instanceof Action) {
			Action action = (Action) object;
			return (type == action.getType() &&
					amalgamated == action.isAmalgamated() &&
					Arrays.equals(arguments, action.getArguments()));
		}
		return false;
	}

	/**
	 * Get the amalgamation flag. 
	 * @return Amalgamation flag.
	 */
	public boolean isAmalgamated() {
		return amalgamated;
	}

	/**
	 * Returns arguments this Action contains. If no arguments are specified,
	 * this method returns an empty string array.
	 * @return Arguments.
	 */
	public String[] getArguments() {
		return arguments;
	}

	/**
	 * Returns the action type represented by this Action.
	 * @return Action type.
	 */
	public ActionType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = type.hashCode();
		for (String argument : arguments) {
			hash = (hash + argument.hashCode()) << 1;
		}
		if (amalgamated) {
			hash++;
		}
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuffer result = new StringBuffer();
		result.append(type.toString());
		if (amalgamated){
			result.append(MARKER_AMALGAMATION);
		}
		
		if (arguments.length > 0) {
			result.append(SEPARATOR_TYPE);
			result.append(arguments[0]);
			for (int i = 1; i < arguments.length; i++) {
				result.append(SEPARATOR_ARGS);
				result.append(arguments[i]);
			}
		}
		return result.toString();
	}

}
