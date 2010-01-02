package org.eclipse.emf.henshin.diagram.actions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class ElementAction {
	
	// Action type.
	private ElementActionType type;
	
	// Optional arguments.
	private String[] arguments;
	
	/**
	 * Default constructor.
	 * @param type Action type.
	 * @param arguments Optional arguments.
	 */
	public ElementAction(ElementActionType type, String... arguments) {
		this.type = type;
		this.arguments = arguments;
	}
	
	public ElementActionType getType() {
		return type;
	}
	
	public String[] getArguments() {
		return arguments;
	}
	
	/**
	 * Parse an element action.
	 * @param value String representation of the action.
	 * @return The parsed element action.
	 * @throws ParseException On parse errors.
	 */
	public static ElementAction parse(String value) throws ParseException {
		
		// Check if there is a colon:
		int colon = value.indexOf(':');
		ElementActionType type = ElementActionType.parse(colon < 0 ? value : value.substring(0,colon));
		
		// Parse the arguments:
		List<String> arguments = new ArrayList<String>();
		if (colon>=0) {
			String args = value.substring(colon);
			while (args!=null) {
				int comma = args.indexOf(',');
				String current;
				if (comma>=0) {
					current = args.substring(0,comma);
					args = args.substring(comma);
				} else {
					current = args;
					args = null;
				}
				current = current.trim();
				if (current.length()!=0) {
					arguments.add(current);
				}
			}
		}
		
		// Create and return the new action:
		return new ElementAction(type, arguments.toArray(new String[0]));
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object==this) return true;
		if (object instanceof ElementAction) {
			ElementAction action = (ElementAction) object;
			return (type==action.getType() && Arrays.equals(arguments, action.getArguments()));
		}
		return false;
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
		return hash;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = type.toString();
		for (String argument : arguments) {
			result = result + ',' + argument;
		}
		return result;
	}
	
}
