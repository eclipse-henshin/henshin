package org.eclipse.emf.henshin.diagram.actions;

import java.text.ParseException;

/**
 * An enum for action types.
 * @generated NOT
 */
public enum ElementActionType {
	
	NONE, CREATE, DELETE, FORBID;
	
	/**
	 * Parse an element action type.
	 * @param value String representation.
	 * @return The parsed action type.
	 * @throws ParseException On parse errors.
	 */
	public static ElementActionType parse(String value) throws ParseException {
		value = value.trim();
		for (ElementActionType type : values()) {
			if (type.name().equalsIgnoreCase(value)) return type;
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
