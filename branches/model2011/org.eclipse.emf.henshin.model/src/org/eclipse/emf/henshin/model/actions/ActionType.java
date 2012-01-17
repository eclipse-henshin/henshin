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

/**
 * An enum for action types.
 * @author Christian Krause
 */
public enum ActionType {
		
	PRESERVE, CREATE, DELETE, FORBID, REQUIRE;
	
	/**
	 * Parse an element action type.
	 * @param value String representation.
	 * @return The parsed action type.
	 * @throws ParseException On parse errors.
	 */
	public static ActionType parse(String value) throws ParseException {
		value = value.trim();
		for (ActionType type : values()) {
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
