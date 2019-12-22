package org.eclipse.emf.henshin.variability.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 
 * @author Daniel Strüber, Stefan Schulz
 *
 */
public class XorEncoderUtil {

	/**
	 * Encodes any occurrence of a xor(A,B) pattern into ((A and !B) or (A and
	 * !B)). Works for an arbitrary number of arguments. A and B are allowed to
	 * contain futher xor patterns, as long as parentheses are nested correctly.
	 * 
	 * @param presenceCondition
	 * @return
	 */
	public static String encodeXor(String expression) {
		if (!isWellFormed(expression)) {
			return expression;
		}
		
		expression = expression.replace("XOR(", "xor(");
		while (expression.contains("xor(")) {
			expression = eliminateFirstXor(expression);
		}
		return expression;
	}
	
	public static boolean isWellFormed(String expression) {		
		Stack<Character> stack = new Stack<Character>();		
		for(Character c : expression.toCharArray()) {
			if (c.equals('(')) {
				stack.push(c);
			} else if (c.equals(')')) {
				if (stack.empty()) {
					return false;
				} else {
					stack.pop();
				}
			}
		}
		return stack.isEmpty();
	}

	private static String eliminateFirstXor(String expression) {
		List<String> arguments = new ArrayList<String>();
		int startIndex = expression.indexOf("xor(") + 4;
		int endIndex = -1;

		int index = expression.indexOf("xor(") + 4;
		int offsetCurrentArgument = expression.indexOf("xor(") + 4;
		int bracketDepth = 0;
		while (endIndex == -1 && index < expression.length()) {
			int nextRelevantPos = findNextRelevantChar(expression, index);
			if (nextRelevantPos > -1) {
				char nextRelevant = expression.charAt(nextRelevantPos);
				if (nextRelevant == '(') {
					bracketDepth++;
				} else if (nextRelevant == ')' && bracketDepth > 0) {
					bracketDepth--;
				} else if (nextRelevant == ')' && bracketDepth == 0) { // i.e.: done
					String argument = expression.substring(
							offsetCurrentArgument, nextRelevantPos)
							.trim();
					arguments.add(argument);
					endIndex = nextRelevantPos;
				} else if (nextRelevant == ',' && bracketDepth == 0) {
					String argument = expression.substring(
							offsetCurrentArgument, nextRelevantPos)
							.trim();
					arguments.add(argument);
					offsetCurrentArgument = nextRelevantPos + 1;
				}
				index = nextRelevantPos + 1;
			} else {
				throw new RuntimeException("Error in expression " + expression);
			}
		}
		String prefix = expression.substring(0, startIndex - 4);
		String suffix = expression.substring(endIndex + 1, expression.length());
		return prefix + createEncoding(arguments) + suffix;
	}

	/**
	 * Finds next occurrence of a '(', ')' or ','
	 * 
	 * @param expression
	 * @param offset
	 * @return
	 */
	private static int findNextRelevantChar(String expression, int offset) {
		String substring = expression.substring(offset);

		int indexOpenBracket = substring.indexOf('(');
		int indexClosedBracket = substring.indexOf(')');
		int indexComma = substring.indexOf(',');

		// Now find out which one of these three ints is lowest without being -1.
		// Any suggestions on a more readable way to do this is are welcome.
		int result = -1;
		if (indexOpenBracket >= 0) {
			if (indexClosedBracket >= 0) {
				result = Math.min(indexOpenBracket, indexClosedBracket);
				if (indexComma >= 0) {
					result = Math.min(indexComma, result);
				}
			} else if (indexComma >= 0) {
				result = Math.min(indexComma, indexOpenBracket);
			}
		} else {
			if (indexClosedBracket >= 0) {
				if (indexComma >= 0) {
					result = Math.min(indexComma, indexClosedBracket);
				} else
					result = indexClosedBracket;
			} else
				result = indexComma;
		}

		if (result > -1)
			result += offset;
		return result;
	}

	private static String createEncoding(List<String> literals) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < literals.size(); i++) {
			sb.append('(');
			for (int j = 0; j < literals.size(); j++) {
				if (i == j) {
					sb.append(literals.get(i));
				} else {
					sb.append("!(");
					sb.append(literals.get(j));
					sb.append(")");
				}
				if (j + 1 < literals.size())
					sb.append(" and ");
			}
			sb.append(')');
			if (i + 1 < literals.size())
				sb.append(" or ");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(encodeXor("xor(A, B, C)"));
		System.out.println(encodeXor("xor(A, !B, D)"));
		System.out.println(encodeXor("xor(A, xor(B,C))"));
		System.out
				.println(encodeXor("xor(def(tr_e_04_argument),def(tr_e_04_source))"));
	}
}
