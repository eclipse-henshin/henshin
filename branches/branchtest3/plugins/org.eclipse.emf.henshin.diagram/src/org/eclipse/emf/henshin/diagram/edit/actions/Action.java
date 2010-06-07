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
package org.eclipse.emf.henshin.diagram.edit.actions;

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
 * @generated NOT
 */
public class Action {

	/**
	 * Separator of the action type information
	 */
	private static final String SEPARATOR_TYPE = ":";

	/**
	 * Separator of action type arguments
	 */
	private static final String SEPARATOR_ARGS = ",";

	private static final Pattern PATTERN_TYPE = Pattern.compile(SEPARATOR_TYPE);
	private static final Pattern PATTERN_ARGS = Pattern.compile(SEPARATOR_ARGS);
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
		 * TODO A specification of the action elements syntax need to be
		 * created. In particular, the input syntax (what the user inserts)
		 * should be equal to displayed syntax. Or at least, the displayed
		 * syntax should be validly used as input as well as a shorten form.<br>
		 * Additionally, a default action type might be reasonable e.g.
		 * ActionType.NONE.
		 */

		/*
		 * Check for a colon, as the string before a colon is suggested to be an
		 * action type informations.
		 */
		String[] aTypeAndArgs = PATTERN_TYPE.split(value, 2);
		ActionType type = ActionType.parse(aTypeAndArgs[0]);

		/*
		 * Check for further arguments, which occur after the colon and which
		 * have to be separated by a comma.
		 */
		String[] aArgs;
		if (aTypeAndArgs.length == 2) {
			aArgs = PATTERN_ARGS.split(aTypeAndArgs[1]);
		} else {
			aArgs = EMPTY_STRING_ARRAY;
		}// if else

		// Create and return the new action:
		return new Action(type, aArgs);

	}// parse

	// Action type.
	private ActionType type;

	// Optional arguments.
	private String[] arguments;

	/**
	 * Default constructor.
	 * 
	 * @param type
	 *            Action type. Must not be <code>null</code>!
	 * @param arguments
	 *            Optional arguments.
	 */
	public Action(ActionType type, String... arguments) {
		if (type == null)
			throw new IllegalArgumentException(
					"Parameter type must not be null.");
		this.type = type;
		this.arguments = (arguments == null) ? EMPTY_STRING_ARRAY : arguments;
	}// constructor

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object instanceof Action) {
			Action action = (Action) object;
			return (type == action.getType() && Arrays.equals(arguments, action
					.getArguments()));
		}
		return false;
	}

	/**
	 * Returns arguments this Action contains. If no arguments are specified,
	 * this method returns an empty string array.
	 * 
	 * @return
	 */
	public String[] getArguments() {
		return arguments;
	}

	/**
	 * Returns the action type represented by this Action.
	 * 
	 * @return
	 */
	public ActionType getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = type.hashCode();
		for (String argument : arguments) {
			hash = (hash + argument.hashCode()) << 1;
		}
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		StringBuffer result = new StringBuffer();
		result.append(type.toString());

		if (arguments.length > 0) {
			result.append(SEPARATOR_TYPE);
			result.append(arguments[0]);
			for (int i = 1; i < arguments.length; i++) {
				result.append(SEPARATOR_ARGS);
				result.append(arguments[i]);
			}// for
		}// if
		return result.toString();
	}// toString

}// class
