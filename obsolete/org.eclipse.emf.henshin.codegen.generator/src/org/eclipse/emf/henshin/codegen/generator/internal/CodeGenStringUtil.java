package org.eclipse.emf.henshin.codegen.generator.internal;

/**
 * Static helper methods for strings.
 * @author Christian Krause
 */
public class CodeGenStringUtil {

	/**
	 * Capitalize a string.
	 */
	public static String capitalize(String string) {
		return string = string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	/**
	 * Turn a camel-case string into an upper-case string with underscores.
	 */
	public static String camelCaseToUpperCase(String string) {
		String result = "";
		boolean lastLower = false;
		for (int i=0; i<string.length(); i++) {
			char ch = string.charAt(i);
			if (Character.isUpperCase(ch) && lastLower) {
				result = result + "_";
			}
			result = result + Character.toUpperCase(ch);
			lastLower = Character.isLowerCase(ch);
		}
		return result;
	}

	/**
	 * Escape a string.
	 */
	public static String escapeString(String value) {
		return "\"" + value.replaceAll("\"", "\\\\\"")+ "\"";
	}

}
