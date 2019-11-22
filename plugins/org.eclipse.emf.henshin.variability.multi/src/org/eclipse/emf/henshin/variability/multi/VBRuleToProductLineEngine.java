package org.eclipse.emf.henshin.variability.multi;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.matcher.RulePreparator;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareEngine;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareMatch;
import org.eclipse.emf.henshin.variability.util.Logic;
import org.eclipse.emf.henshin.variability.util.SatChecker;

import aima.core.logic.propositional.parsing.ast.Sentence;
import de.ovgu.featureide.fm.core.base.IFeatureModel;

public class VBRuleToProductLineEngine extends VariabilityAwareEngine {

	private IFeatureModel fmP;
	private Map<EObject, String> pcsP;
	private Lifting lifting;

	public VBRuleToProductLineEngine(Rule rule, EGraph graphP, IFeatureModel fmP, Map<EObject, String> pcsP,
			RuleToProductLineEngine varEngine) {
		super(rule, graphP);
		this.fmP = fmP;
		this.pcsP = pcsP;
		this.engine = varEngine;
		this.lifting = new Lifting(varEngine, graphP, fmP, pcsP);
	}

	public VBRuleToProductLineEngine(Rule rule, EGraph graphP, IFeatureModel fmP, Map<EObject, String> pcsP) {
		this(rule, graphP, fmP, pcsP, new RuleToProductLineEngine());
	}

	public void transform() {
		liftAndAppy(findMatches());
	}

	public void liftAndAppy(Iterable<VariabilityAwareMatch> matches) {

		for (VariabilityAwareMatch match : matches) {
			match.prepareRule();
			lifting.liftAndApplyRule(match.getMatch(), rule);
			match.undoPreparation();
		}
	}

	@Override
	public Set<VariabilityAwareMatch> findMatches() {

		Set<VariabilityAwareMatch> matches = new HashSet<>();

		// Line 1: findBasePreMatches
		Set<Match> baseMatches = findBasePreMatches();

		List<RulePreparator> preparators = null;
		List<Set<Sentence>> assumedTrue = null;

		// Line 2: iterate over all base-matches
		for (Match basePreMatch : baseMatches) {
			boolean isLiftAble = true;

			// If there is no base-match the rule is liftable in all cases
			// (optimization for too many base-matches)
			if (basePreMatch != null) {
				// Line 3: calculate Phi_apply and AND FM from Line 4
				String phiApply = ElementHelper.getFMExpressionAsCNF(this.fmP);
				for (EObject eObject : basePreMatch.getNodeTargets()) {
					if (pcsP.containsKey(eObject)) {
						phiApply = Logic.and(phiApply, pcsP.get(eObject));
					}
				}

				// Line 4: check if Phi_apply & FM is SAT
				SatChecker satChecker = new SatChecker();
				isLiftAble = satChecker.isSatisfiable(phiApply).booleanValue();
			}
			if (isLiftAble) {
				if (preparators == null) {
					preparators = new ArrayList<RulePreparator>();
					assumedTrue = new ArrayList<Set<Sentence>>();
					try {
						prepareRulePreparators(preparators, assumedTrue);
					} catch (ScriptException e) {
						throw new RuntimeException(e);
					}
				}

				for (int i = 0; i < preparators.size(); i++) {
					RulePreparator prep = preparators.get(i);
					Set<Sentence> theTrue = assumedTrue.get(i);

					// Line 8: Get and collect matches for concrete rule
					prep.doPreparation();
					Iterator<Match> classicMatches = engine.findMatches(rule, graph, basePreMatch).iterator();
					while (classicMatches.hasNext()) {
						matches.add(new VariabilityAwareMatch(classicMatches.next(), theTrue, rule, prep));
					}
					prep.undo();

				}

			}
		}
		return matches;
	}

	public void prepareRulePreparators(List<RulePreparator> preparators, List<Set<Sentence>> assumedTrue)
			throws ScriptException {

		// Calculate all possible configurations of concrete rule
		LinkedList<List<String>> trueFeatureList = new LinkedList<>();
		LinkedList<List<String>> falseFeatureList = new LinkedList<>();
		RuleUtil.calculateTrueAndFalseFeatures(rule, ruleInfo, trueFeatureList, falseFeatureList);

		// Iterate over all concrete rules and collect matches
		for (int i = 0; i < trueFeatureList.size(); i++) {
			List<String> trueFeatures = trueFeatureList.get(i);
			List<String> falseFeatures = falseFeatureList.get(i);

			// Generate concrete rule
			Set<Sentence> elementsToRemove = RuleUtil.calculateElementsToRemove(ruleInfo, trueFeatures,
					falseFeatures);
			MatchingInfo matchingInfo = new MatchingInfo(new ArrayList<Sentence>(expressions.values()), ruleInfo);
			RulePreparator preparator = new RulePreparator(rule);
			BitSet reducedRule = preparator.prepare(ruleInfo, elementsToRemove, rule.isInjectiveMatching(), false);
			if (!matchingInfo.getMatchedSubrules().contains(reducedRule)) {

				RulePreparator prep = preparator.getSnapShot();
				Set<Sentence> theTrue = matchingInfo.getAssumedTrue();
				preparators.add(prep);
				assumedTrue.add(theTrue);
				matchingInfo.getMatchedSubrules().add(reducedRule);

			}
			preparator.undo();
		}
	}

	private Set<Match> findBasePreMatches() {
		BitSet bs = rulePreparator.prepare(ruleInfo, ruleInfo.getPc2Elem().keySet(), rule.isInjectiveMatching(), true);
		Set<Match> baseMatches = new HashSet<Match>();
		Iterator<Match> it = engine.findMatches(rule, graph, null).iterator();
		while (it.hasNext()) {
			if (baseMatches.size() < THRESHOLD_MAXIMUM_BASE_MATCHES) {
				baseMatches.add(it.next());
			} else {
				baseMatches.clear();
				baseMatches.add(null);
				System.out.println("Too many base matches:" + rule);
				break;
			}
		}
		rulePreparator.undo();
		return baseMatches;
	}
}
