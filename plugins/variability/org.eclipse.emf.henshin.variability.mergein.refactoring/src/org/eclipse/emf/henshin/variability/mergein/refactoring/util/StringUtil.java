package org.eclipse.emf.henshin.variability.mergein.refactoring.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author strueber
 *
 */
public class StringUtil {

	/**
	 * In the given input String, replaces all occurrences of the to-be-replaced
	 * with the replacement String *unless* the occurrence is directly preceded
	 * or succeeded by an alphanumeric character.
	 * 
	 * @param input
	 * @param toBeReplaced
	 * @param replacement
	 * @return
	 */
	public static String cleanReplace(String input, String toBeReplaced,
			String replacement) {
		if (toBeReplaced.equals(replacement))
			return input;
		if (input.equals(toBeReplaced))
			return replacement;
		String output = replaceAll(".*[^\\w](" + toBeReplaced + ")[^\\w].*",
				input, 1, replacement);
		output = replaceFirst("\\A(" + toBeReplaced + ")[^\\w].*", output, 1,
				replacement);
		output = replaceFirst(".*[^\\w](" + toBeReplaced + ")\\z", output, 1,
				replacement);
		return output;
	}

	private static String replaceFirst(String regex, String source,
			int groupToReplace, String replacement) {
		Matcher m = Pattern.compile(regex).matcher(source);
		if (m.find()) {
			source = new StringBuilder(source).replace(m.start(groupToReplace),
					m.end(groupToReplace), replacement).toString();
		}
		return source;
	}

	private static String replaceAll(String regex, String source,
			int groupToReplace, String replacement) {
		Matcher m = Pattern.compile(regex).matcher(source);
		while (m.find()) {
			source = new StringBuilder(source).replace(m.start(groupToReplace),
					m.end(groupToReplace), replacement).toString();
			m = Pattern.compile(regex).matcher(source);
		}
		return source;
	}

	public static void main(String[] args) {
		System.out.println(cleanReplace("auto.+a.a.auto", "a", "o"));
		System.out.println(cleanReplace("~a~", "a", "o"));
		System.out.println(cleanReplace("a", "a", "o"));
		System.out.println(cleanReplace("pushdownC", "d", "pushdownD"));
	}
}
