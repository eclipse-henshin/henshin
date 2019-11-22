package org.eclipse.emf.henshin.variability.multi;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.script.ScriptException;

import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.matcher.RulePreparator;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareEngine.MatchingInfo;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareEngine.RuleInfo;
import org.eclipse.emf.henshin.variability.util.Logic;
import org.eclipse.emf.henshin.variability.util.SatChecker;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareMatch;
import org.eclipse.emf.henshin.variability.wrapper.VariabilityFactory;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import aima.core.logic.propositional.kb.data.Clause;
import aima.core.logic.propositional.kb.data.Literal;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;

public class RuleUtil {

	public static Pattern pattern = Pattern.compile("(def|definedEx)\\((\\S+)\\)");

	public static Iterable<VariabilityAwareMatch> flattenRuleAndMatch(EGraphImpl graphP, Rule rule) throws ScriptException {
		// Calculate allowed rule configurations
		LinkedList<List<String>> trueFeatureList = new LinkedList<List<String>>();
		LinkedList<List<String>> falseFeatureList = new LinkedList<List<String>>();

		RuleInfo ruleInfo = new RuleInfo(rule);
		calculateTrueAndFalseFeatures(rule, ruleInfo, trueFeatureList, falseFeatureList);

		LinkedList<VariabilityAwareMatch> matches = new LinkedList<>();
		RuleToProductLineEngine engine = new RuleToProductLineEngine();

		while (0 < trueFeatureList.size()) {
			List<String> trueFeatures = trueFeatureList.remove(0);
			List<String> falseFeatures = falseFeatureList.remove(0);

			Set<Sentence> elementsToRemove = calculateElementsToRemove(ruleInfo, trueFeatures, falseFeatures);

			// Flatten rule
			RulePreparator preparator = new RulePreparator(rule);
			MatchingInfo matchingInfo = new MatchingInfo(new ArrayList<Sentence>(ruleInfo.getExpressions().values()),
					ruleInfo);
			BitSet reducedRule = preparator.prepare(ruleInfo, elementsToRemove, rule.isInjectiveMatching(), false);

			if (!matchingInfo.getMatchedSubrules().contains(reducedRule)) {

				// Find matches for flattened rule
				Iterator<Match> classicMatches = engine.findMatches(rule, graphP, null).iterator();

				while (classicMatches.hasNext()) {
					Match match = classicMatches.next();
					RulePreparator prep = preparator.getSnapShot();
					matches.add(new VariabilityAwareMatch(classicMatches.next(), matchingInfo.getAssumedTrue(), rule,
							prep));
				}
				matchingInfo.getMatchedSubrules().add(reducedRule);

			}
			preparator.undo();
		}
		return matches;
	}

	public static Status calculateTrueAndFalseFeatures(Rule rule, RuleInfo ruleInfo,
			List<List<String>> trueFeatureList, List<List<String>> falseFeatureList) {
		// Line 5: calculate Phi_rule
		List<String> features = VariabilityFactory.createVariabilityRule(rule).getFeatures().stream()
				.map(f -> pattern.matcher(f).replaceAll("definedEx($2)")).collect(Collectors.toList());

		
		// Line 6: get all Solutions for Phi_rule
		Sentence phiRule = ruleInfo.getFeatureModel();
		String expr = phiRule.toString();
		if (expr.equals("1")) {
			expr = Logic.TRUE;
		}
//		expr = pattern.matcher(expr).replaceAll("$2");

		// Remove contained features
		int numFeatures = features.size();
		ArrayList<String> unusedFeatures = new ArrayList<>(numFeatures);
		for (String next : features) {
			if (!expr.contains(next)) {
				unusedFeatures.add(pattern.matcher(next).replaceAll("$2"));
//			} else {
//				features.remove(next);
			}
		}

		expr = pattern.matcher(expr).replaceAll("$2");
		Map<Integer, String> symbolsToIndices = new HashMap<>();

		ISolver solver = SatChecker.createModelIterator(expr, symbolsToIndices);

		// Line 6: iterate over all Solutions of Phi_rule
		try {
			while (solver.isSatisfiable()) {
				int[] model = solver.model();
				List<String> tmpTrueFeatures = new LinkedList<>();
				List<String> tmpFalseFeatures = new LinkedList<>();
				for (int selection : model) {
					int abs = Math.abs(selection);
					if (selection > 0) {
						tmpTrueFeatures.add(symbolsToIndices.get(abs));
					} else {
						tmpFalseFeatures.add(symbolsToIndices.get(abs));
					}
				}
				if (unusedFeatures.isEmpty()) {
					trueFeatureList.add(tmpTrueFeatures);
					falseFeatureList.add(tmpFalseFeatures);
				} else {
					int bitVector = (int) Math.pow(2, features.size() - 1);
					while (bitVector >= 0) {
						LinkedList<String> trueFeatures = new LinkedList<>(tmpTrueFeatures);
						LinkedList<String> falseFeatures = new LinkedList<>(tmpFalseFeatures);
						for (int i = 0; i < features.size(); i++) {
							if (((1 << features.size() - i - 1 & bitVector) != 0)) {
								trueFeatures.add(unusedFeatures.get(i));
							} else {
								falseFeatures.add(unusedFeatures.get(i));
							}
						}
						trueFeatureList.add(trueFeatures);
						falseFeatureList.add(falseFeatures);
						bitVector--;
					}
				}
				
			}
		} catch (TimeoutException e1) {
			return Status.SATTimeout;
		}
		return Status.OK;
	}

	public static Set<Sentence> calculateElementsToRemove(RuleInfo ruleInfo, List<String> trueFeatures,
			List<String> falseFeatures) throws ScriptException {
		// Line 7: get all Features set to false and remove false
		// Line 7: remove all pcs of rule evaluating to false
		Set<Sentence> elementsToRemove = new HashSet<>();
		for (Entry<Sentence, Set<GraphElement>> pcElementPair : ruleInfo.getPc2Elem().entrySet()) {
			String pc = RuleUtil.pattern.matcher(pcElementPair.getKey().toString()).replaceAll("$2");
			// pcElementPair.getKey().toString().replaceAll(pattern,
			// "$2");
			Boolean reduced;
			if (pc.trim().equalsIgnoreCase(Logic.TRUE.trim())) {
				reduced = Boolean.TRUE;
			} else {
				reduced = Logic.reduce(pc, trueFeatures, falseFeatures);
			}
			if (!reduced.booleanValue()) {
				elementsToRemove.add(pcElementPair.getKey());
			}
		}
		return elementsToRemove;
	}

	private static Map<PropositionSymbol, Integer> getSymbol2IndexMap(Set<PropositionSymbol> symbols) {
		Map<PropositionSymbol, Integer> list2Index = new HashMap<PropositionSymbol, Integer>(symbols.size());
		int counter = 1;
		for (PropositionSymbol symbol : symbols) {
			list2Index.put(symbol, Integer.valueOf(counter));
			counter++;
		}
		return list2Index;
	}

	private static int[] convertToArray(Clause clause, Map<PropositionSymbol, Integer> indices) {
		Set<Literal> literals = clause.getLiterals();
		int[] result = new int[literals.size()];
		int counter = 0;
		for (Literal literal : literals) {
			int sign = literal.isPositiveLiteral() ? 1 : -1;
			PropositionSymbol symbol = literal.getAtomicSentence();
			int index = indices.get(symbol).intValue();
			result[counter] = sign * index;
			counter++;
		}
		return result;
	}
}
