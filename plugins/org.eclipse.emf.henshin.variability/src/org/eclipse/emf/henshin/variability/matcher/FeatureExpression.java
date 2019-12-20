package org.eclipse.emf.henshin.variability.matcher;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.variability.util.Logic;
import org.eclipse.emf.henshin.variability.util.SatChecker;
import org.eclipse.emf.henshin.variability.util.XorEncoderUtil;

import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.ComplexSentence;
import aima.core.logic.propositional.parsing.ast.Connective;
import aima.core.logic.propositional.parsing.ast.Sentence;

/**
 * This class serves as a cache for SAT evaluation results, helping to avoid
 * performing the same computations repeatedly.
 * 
 * @author Daniel Strüber
 *
 */
public class FeatureExpression {
	private static PropositionalParser parser = new PropositionalParser();

	public static final Sentence TRUE = parser.parse(Logic.TRUE);

	static Map<Sentence, Map<Sentence, Boolean>> implies = new HashMap<Sentence, Map<Sentence, Boolean>>();
	static Map<Sentence, Map<Sentence, Boolean>> contradicts = new HashMap<Sentence, Map<Sentence, Boolean>>();
	static Map<Sentence, Map<Sentence, Sentence>> and = new HashMap<Sentence, Map<Sentence, Sentence>>();
	static Map<Sentence, Map<Sentence, Sentence>> andNot = new HashMap<Sentence, Map<Sentence, Sentence>>();

	/**
	 * Does expression 1 imply expression 2?
	 * 
	 * @param expr1
	 * @param expr2
	 * @return
	 */
	public static boolean implies(Sentence expr1, Sentence expr2) {
		if (implies.containsKey(expr1)) {
			if (implies.get(expr1).containsKey(expr2)) {
				return implies.get(expr1).get(expr2);
			} else {
				boolean val = new SatChecker().isContradiction(andNot(expr1, expr2).toString());
				implies.get(expr1).put(expr2, val);
				return val;
			}
		} else {
			implies.put(expr1, new HashMap<Sentence, Boolean>());
			return implies(expr1, expr2);
		}
	}

	public static Sentence and(Sentence expr1, Sentence expr2) {
		if (and.containsKey(expr1)) {
			if (and.get(expr1).containsKey(expr2)) {
				return and.get(expr1).get(expr2);
			} else {
				Sentence val = Sentence.newConjunction(expr1, expr2);
				and.get(expr1).put(expr2, val);
				return val;
			}
		} else {
			and.put(expr1, new HashMap<Sentence, Sentence>());
			return and(expr1, expr2);
		}
	}

	public static Sentence andNot(Sentence expr1, Sentence expr2) {
		if (andNot.containsKey(expr1)) {
			if (andNot.get(expr1).containsKey(expr2)) {
				return andNot.get(expr1).get(expr2);
			} else {
				Sentence val = Sentence.newConjunction(expr1, new ComplexSentence(Connective.NOT, expr2));
				andNot.get(expr1).put(expr2, val);
				return val;
			}
		} else {
			andNot.put(expr1, new HashMap<Sentence, Sentence>());
			return andNot(expr1, expr2);
		}
	}

	public static Sentence getExpr(String condition) {
		condition = XorEncoderUtil.encodeXor(condition);
		return parser.parse(condition);
	}

	/**
	 * Does expression 1 contradict expression 2?
	 * 
	 * @param expr1
	 * @param expr2
	 * @return
	 */
	public static boolean contradicts(Sentence expr1, Sentence expr2) {
		if (contradicts.containsKey(expr1)) {
			if (contradicts.get(expr1).containsKey(expr2)) {
				return contradicts.get(expr1).get(expr2);
			} else {
				boolean val = new SatChecker().isContradiction(Sentence.newConjunction(expr1, expr2));
				contradicts.get(expr1).put(expr2, val);
				return val;
			}
		} else {
			contradicts.put(expr1, new HashMap<Sentence, Boolean>());
			return contradicts(expr1, expr2);
		}
	}

	/**
	 * 
	 * @author speldszus
	 */
	private static class PropositionalParser extends PLParser {

		private static final String NOT = "~";
		private static final String AND = "&";
		private static final String OR = "|";

		private static HashMap<String, Sentence> exprToSentence;

		/**
		 * Creates a new parser and initializes a map with already parsed expressions
		 */
		public PropositionalParser() {
			Sentence trueSentence = super.parse(Logic.TRUE);
			exprToSentence = new HashMap<>();
			exprToSentence.put(Logic.TRUE, trueSentence);
			exprToSentence.put("", trueSentence);
		}

		@Override
		public Sentence parse(String input) {
			String trimmed = input.trim();
			if (exprToSentence.containsKey(trimmed)) {
				return exprToSentence.get(trimmed);
			}
			String resolved = resolveSynonyms(trimmed);
			Sentence sentence = super.parse(resolved);
			exprToSentence.put(trimmed, sentence);
			exprToSentence.put(resolved, sentence);
			return sentence;
		}

		private static String resolveSynonyms(String expression) {
			expression = expression.replaceAll("\\)", " ) ");
			expression = expression.replaceAll("\\(", " ( ");
			expression = expression.replaceAll(" (XOR) ", "xor");
			expression = expression.replaceAll(" (or|OR|\\|\\|)", ' ' + OR + ' ');
			expression = expression.replaceAll(" (and|AND|\\&\\&) ", ' ' + AND + ' ');
			expression = expression.replaceAll("!", ' ' + NOT + ' ');
			expression = expression.replaceAll(" (not|NOT) ", ' ' + NOT + ' ');
			return expression;
		}
	}

}
