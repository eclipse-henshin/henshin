/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model;

import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This class represents an Action of a graph element. Actions consist of an
 * {@link Type} and a number of string arguments. Actions can be printed,
 * parsed and compared using equals().
 * 
 * @author Christian Krause
 * @author Stefan Jurack
 */
public final class Action {

	/**
	 * An enum for action types.
	 * @author Christian Krause
	 */
	public static enum Type {
			
		PRESERVE, CREATE, DELETE, FORBID, REQUIRE;
		
		/**
		 * Parse an element action type.
		 * @param value String representation.
		 * @return The parsed action type.
		 * @throws ParseException On parse errors.
		 */
		public static Type parse(String value) throws ParseException {
			value = value.trim();
			for (Type type : values()) {
				if (type.name().equalsIgnoreCase(value)) return type;
			}
			// Some convenience...
			if ("remove".equalsIgnoreCase(value)) {
				return DELETE;
			}
			if ("new".equalsIgnoreCase(value)) {
				return CREATE;
			}
			if ("none".equalsIgnoreCase(value)) {
				return PRESERVE;
			}
			throw new ParseException("Unknown action type: " + value, 0);
		}
			
		/*
		 * (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}
		
	}
	
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
	 * Multi-marker
	 */
	private static final String MULTI_MARKER = "*";

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
		
		boolean isMulti = false;
		String trimmedType = typeAndArgs[0].trim();
		if (trimmedType.endsWith(MULTI_MARKER)) {
			isMulti = true;
			trimmedType = trimmedType.substring(0, trimmedType.length()-1);
		}
		Type type = Type.parse(trimmedType);

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
		return new Action(type, isMulti, args);

	}

	// Action type.
	private Type type;

	// Multi-flag.
	private boolean isMulti;

	// Optional arguments.
	private String[] arguments;

	/**
	 * Default constructor.
	 * @param type Action type. Must not be <code>null</code>!
	 * @param isMulti Multi-flag.
	 * @param arguments Optional arguments.
	 */
	public Action(Type type, boolean isMulti, String... arguments) {
		if (type == null)
			throw new IllegalArgumentException(
					"Parameter type must not be null.");
		this.type = type;
		this.isMulti = isMulti;
		this.arguments = (arguments == null) ? EMPTY_STRING_ARRAY : arguments;
	}

	/*
	 * Alternative constructor.
	 */
	public Action(Type type, String... arguments) {
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
					isMulti == action.isMulti() &&
					Arrays.equals(arguments, action.getArguments()));
		}
		return false;
	}

	/**
	 * Get the multi-flag. 
	 * @return Multi-flag.
	 */
	public boolean isMulti() {
		return isMulti;
	}

	/**
	 * Returns arguments this Action contains. If no arguments are specified,
	 * this method returns an empty string array.
	 * @return Arguments.
	 */
	public String[] getArguments() {
		return Arrays.copyOf(arguments , arguments.length);
	}

	/**
	 * Returns the action type represented by this Action.
	 * @return Action type.
	 */
	public Type getType() {
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
		if (isMulti) {
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
		if (isMulti){
			result.append(MULTI_MARKER);
		}
		if (arguments.length > 0) {
			result.append(SEPARATOR_TYPE);
			result.append(argumentToString(arguments[0]));
			for (int i = 1; i < arguments.length; i++) {
				result.append(SEPARATOR_ARGS);
				result.append(argumentToString(arguments[i]));
			}
		}
		return result.toString();
	}
	
	private static String argumentToString(String arg) {
		if (arg==null || arg.trim().length()==0) return "?";
		return arg;
	}
	
}
