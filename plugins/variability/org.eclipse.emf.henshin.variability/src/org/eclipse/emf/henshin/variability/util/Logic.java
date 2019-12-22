package org.eclipse.emf.henshin.variability.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 * @author Daniel Strüber
 * @author Sven Peldszus 
 *
 */
public class Logic {
	public static final String TRUE = "true";
	private static final String TRUE_space = " true ";
	public static final String FALSE = "false";
	private static final String FALSE_space = " false ";
	private static String NOT = " not ";
	private static String AND = " and ";
	private static String OR = " or ";
	private static String LB = " ( ";
	private static String RB = " ) ";
	private static String limitForCombinations = "0123456789";

	protected static synchronized String choice(final List<String> expressions, final int min, final int max,
			final boolean forceFalse) {
		final List<String> combinationsIndexes = new ArrayList<String>();
		if (expressions.size() > Logic.limitForCombinations.length()) {
			return TRUE_space;
		}
		final String limit = Logic.limitForCombinations.substring(0, expressions.size());
		combinations(min, max, "", limit, combinationsIndexes);
		final List<String> orInput = new ArrayList<String>();
		if (forceFalse) {
			for (int i = 0; i < combinationsIndexes.size(); ++i) {
				final String comb = combinationsIndexes.get(i);
				final List<String> andInput = new ArrayList<String>();
				for (int j = 0; j < expressions.size(); ++j) {
					String andElement = "";
					if (comb.lastIndexOf(String.valueOf(j)) == -1) {
						andElement = negate(expressions.get(j));
					} else {
						andElement = expressions.get(j);
					}
					andInput.add(andElement);
				}
				orInput.add(and(andInput));
			}
		} else {
			for (int i = 0; i < combinationsIndexes.size(); ++i) {
				final String comb = combinationsIndexes.get(i);
				final List<String> andInput = new ArrayList<String>();
				for (int j = 0; j < comb.length(); ++j) {
					andInput.add(expressions.get(Integer.parseInt(String.valueOf(comb.charAt(j)))));
				}
				orInput.add(and(andInput));
			}
		}
		return or(orInput);
	}

	protected static synchronized void combinations(final int min, final int max, final String prefix,
			final String elements, final List<String> combinationsIndexes) {
		if (prefix.length() >= min) {
			combinationsIndexes.add(prefix);
		}
		for (int i = 0; i < elements.length(); ++i) {
			if (prefix.length() < max) {
				combinations(min, max, String.valueOf(prefix) + elements.charAt(i), elements.substring(i + 1),
						combinationsIndexes);
			}
		}
	}

	protected static synchronized String claused(final String expression) {
		if (expression == null || expression.length() == 0) {
			return "";
		}
		if (isClaused(expression)) {
			return expression;
		}
		return String.valueOf(Logic.LB) + expression + Logic.RB;
	}

	private static boolean isClaused(String expression) {
		if (!expression.startsWith("(") || !expression.endsWith(")")) {
			return false;
		}
		Map<Integer, Integer> openClose = new HashMap<>();
		Stack<Integer> open = new Stack<>();
		int index = 0;
		while (index < expression.length()) {
			char c = expression.charAt(index);
			if (c == '(') {
				open.push(Integer.valueOf(index));
			} else if (c == ')') {
				openClose.put(open.pop(), Integer.valueOf(index));
			}
			index++;
		}
		return openClose.get(Integer.valueOf(0)).intValue() == expression.length() - 1;
	}

	protected static synchronized String implies(final String lhs, final String rhs) {
		if (lhs == null || lhs.length() == 0 || rhs == null || rhs.length() == 0) {
			throw new RuntimeException();
		}
		String lhs_trimmed = lhs.trim();
		String rhs_trimmed = rhs.trim();
		if (FALSE.equals(lhs_trimmed) || TRUE.equals(rhs_trimmed)
				|| lhs_trimmed.equals(rhs_trimmed)) {
			return TRUE_space;
		}
		if (FALSE.equals(rhs_trimmed)) {
			return negate(lhs);
		}
		if (TRUE.equals(lhs_trimmed)) {
			return rhs;
		}
		return or(negate(lhs), rhs);
	}

	public static synchronized String negate(String expression) {
		String trimmed = expression.trim();
		if (trimmed.contentEquals(TRUE)) {
			return FALSE_space;
		} else if (trimmed.equals(FALSE)) {
			return TRUE_space;
		}
		return Logic.NOT + claused(expression);
	}

	private static synchronized String op(final List<String> expressions, final String op) {
		if (expressions == null || expressions.size() == 0) {
			return "";
		}
		if (expressions.size() == 1) {
			return expressions.get(0);
		}
		String expression = "";
		String part = null;
		boolean first = true;
		for (int i = 0; i < expressions.size(); ++i) {
			part = expressions.get(i);
			if (part != null && part.length() > 0) {
				if (first) {
					expression = String.valueOf(expression) + claused(part);
					first = false;
				} else {
					expression = String.valueOf(expression) + op + claused(part);
				}
			}
		}
		return expression;
	}

	protected static synchronized String or(final List<String> expressions) {
		List<String> reduced = new ArrayList<>(expressions.size());
		for (String expr : expressions) {
			String trimmedExpr = expr.trim();
			if (TRUE.equals(trimmedExpr)) {
				return TRUE_space;
			} else if (FALSE.equals(trimmedExpr)) {
				// Skip "| false" as id doesn't change the expression
				continue;
			} else {
				reduced.add(expr);
			}
		}
		if (reduced.size() == 0) {
			return Logic.FALSE_space; // All expressions are FALSE
		}
		if (reduced.size() == 1) {
			return reduced.get(0);
		}
		String v1 = reduced.get(0);
		for (int i = 1; i < reduced.size(); i++) {
			v1 = or(v1, reduced.get(i));
		}
		return v1;
	}

	protected static synchronized String or(final String s1, final String s2) {
		String s1_trimmed = s1.trim();
		String s2_trimmed = s2.trim();
		if (TRUE.equals(s1_trimmed) || TRUE.equals(s2_trimmed)) {
			return Logic.TRUE_space;
		}
		if (Logic.negate(s1_trimmed).trim().equals(s2_trimmed)
				|| Logic.negate(s2_trimmed).trim().equals(s1_trimmed)) {
			return Logic.TRUE_space;
		}
		if (s1_trimmed.equals(s2_trimmed)) {
			return s1;
		}
		if (FALSE.equals(s1_trimmed)) {
			if (FALSE.equals(s2_trimmed)) {
				return Logic.FALSE_space;
			}
			return s2;
		}
		if (FALSE.contentEquals(s2_trimmed)) {
			return s1;
		}
		return claused(s1) + Logic.OR + claused(s2);
	}

	protected static synchronized String and(final List<String> expressions) {
		List<String> reduced = new ArrayList<>(expressions.size());
		for (String expr : expressions) {
			String trimmed = expr.trim();
			if (FALSE.equals(trimmed)) {
				return FALSE_space;
			} else if (TRUE.equals(trimmed)) {
				// Skip "& true" as id doesn't change the expression
				continue;
			} else {
				reduced.add(expr);
			}
		}
		if (reduced.size() == 0) {
			return Logic.TRUE_space; // all elements are TRUE
		}
		if (reduced.size() == 1) {
			return reduced.get(0);
		}
		String v1 = reduced.get(0);
		for (int i = 1; i < reduced.size(); i++) {
			v1 = and(v1, reduced.get(i));
		}
		return v1;
	}

	public static synchronized String and(final String s1, final String s2) {
		String s1_trimmed = s1.trim();
		String s2_trimmed = s2.trim();
		if (FALSE.equals(s1_trimmed) || FALSE.equals(s2_trimmed)) {
			return Logic.FALSE_space;
		}
		if (Logic.negate(s1_trimmed).trim().equals(s2_trimmed)
				|| Logic.negate(s2_trimmed).trim().equals(s1_trimmed)) {
			return Logic.FALSE_space;
		}
		final List<String> list = new ArrayList<String>();
		if (!"".equals(s1_trimmed) && !TRUE.equals(s1_trimmed)) {
			list.add(s1);
		}
		if (!"".equals(s2_trimmed) && !TRUE.equals(s2_trimmed) && !s1_trimmed.equals(s2_trimmed)) {
			list.add(s2);
		}
		if (list.size() == 0) {
			return Logic.TRUE_space;
		}
		if (list.size() == 1) {
			return list.get(0);
		}
		return op(list, Logic.AND);
	}

	protected static synchronized List<String> andMerge(final List<String> memberEnds,
			final HashMap<String, String> memberEndPCs, final HashMap<String, String> memberEndsTypesPCs) {
		final List<String> mergedList = new ArrayList<String>();
		for (int i = 0; i < memberEnds.size(); ++i) {
			mergedList.add(and(memberEndPCs.get(memberEnds.get(i)), memberEndsTypesPCs.get(memberEnds.get(i))));
		}
		return mergedList;
	}

	protected static String simplifyDNF(final String dnf, final List<String> variables) {
		String expression = dnf;
		int i = 0;
		boolean delete = false;
		String newExpression = "";
		while (expression.lastIndexOf(" | ") != -1) {
			i = expression.lastIndexOf(" | ") + " | ".length();
			String clause = expression.substring(i, expression.length());
			delete = false;
			for (final String variable : variables) {
				if (((clause.contains(String.valueOf(Logic.AND) + variable) || clause.startsWith(variable))
						&& clause.contains(String.valueOf(Logic.NOT) + variable)) || clause.contains(Logic.FALSE)) {
					delete = true;
					break;
				}
			}
			expression = expression.substring(0, expression.lastIndexOf(" | "));
			if (!delete && newExpression.indexOf(String.valueOf(Logic.LB) + clause + Logic.RB) == -1
					&& clause.indexOf(Logic.FALSE) == -1) {
				for (final String variable : variables) {
					if (clause.indexOf(variable) != clause.lastIndexOf(variable)) {
						clause = clause.replaceAll(Logic.AND + Logic.TRUE + Logic.AND, Logic.AND)
								.replaceAll(String.valueOf(Logic.AND) + Logic.TRUE, "")
								.replaceAll(Logic.TRUE + Logic.AND, "");
						final String part1 = clause.substring(0, clause.indexOf(variable) + variable.length());
						String part2 = clause.substring(clause.indexOf(variable) + variable.length(),
								clause.length());
						part2 = part2.replaceAll(String.valueOf(Logic.AND) + Logic.NOT + variable, "");
						part2 = part2.replaceAll(String.valueOf(Logic.AND) + variable, "");
						clause = String.valueOf(part1) + part2;
					}
				}
				if (clause.replaceAll(" ", "").length() <= 0
						|| newExpression.contains(String.valueOf(Logic.LB) + clause + Logic.RB)
						|| clause.contains(Logic.FALSE_space)) {
					continue;
				}
				newExpression = String.valueOf(newExpression) + Logic.LB + clause + Logic.RB + Logic.OR;
			}
		}
		newExpression = newExpression.substring(0, newExpression.lastIndexOf(Logic.OR));
		return newExpression;
	}

	public static Boolean reduce(String pc, List<String> trueFeatures, List<String> falseFeatures) throws ScriptException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		for(String trueFeature : trueFeatures) {
			engine.put(trueFeature, Boolean.TRUE);
		}
		for(String falseFeature : falseFeatures) {
			engine.put(falseFeature, Boolean.FALSE);
		}
		
		return (Boolean) engine.eval(pc);
	}
}