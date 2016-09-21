/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.junit.internal.runners.statements.Fail;

/**
 * Assertions for everything related to matches, e.g. matches produced by {@link RuleApplication}s,
 * {@link UnitApplication}s, (partial) matches on element groups, etc.
 * 
 * @see Rules
 * @see Units
 * @see ElementGroups
 * @see Graphs
 * @see GraphTransformations
 * @author Felix Rieger
 * @author Stefan Jurack
 * @author Christian Krause
 * 
 */
public class Matches {

	/**
	 * Assert that an object is contained in all of a {@link Rule}'s matches.
	 * 
	 * @param r {@link Rule}
	 * @param graph {@link EGraph} the Rule will be applied to
	 * @param object {@link EObject}
	 * @throws AssertionError
	 */
	public static void assertObjectContainedInAllMatches(Rule r, EGraph graph, Match partialMatch, Engine engine,
			EObject object) throws AssertionError {

		boolean foundMatch = false;
		for (Match match : engine.findMatches(r, graph, partialMatch)) {
			foundMatch = true;
			if (!match.getNodeTargets().contains(object)) {
				throw new AssertionError("expected: Object contained in every match, but it's not");
			}

		}
		if (!foundMatch) {
			throw new AssertionError("expected: Object contained in every match, but Rule produces no matches");
		}

	}

	/**
	 * Assert that an object is contained in none of a {@link Rule}'s matches
	 * 
	 * @param r {@link Rule}
	 * @param graph {@link EGraph} the {@link Rule} will be applied to
	 * @param object {@link EObject}
	 * @throws AssertionError
	 */
	public static void assertObjectContainedInNoMatch(Rule r, EGraph graph, Match partialMatch, Engine engine,
			EObject object) throws AssertionError {

		for (Match match : engine.findMatches(r, graph, partialMatch)) {
			if (match.getNodeTargets().contains(object)) {
				throw new AssertionError("expected: Object contained in no match, but contained in at least one");
			}

		}
	}

	/**
	 * Assert that an object is contained in exactly n of a {@link Rule}'s matches
	 * 
	 * @param r {@link Rule}
	 * @param graph {@link EGraph} the {@link Rule} will be applied to
	 * @param object {@link EObject}
	 * @param n Number of expected matches
	 * @throws AssertionError
	 */
	public static void assertObjectContainedInNMatches(Rule r, EGraph graph, Match partialMatch, Engine engine,
			EObject object, int n) throws AssertionError {

		int num = 0;
		for (Match match : engine.findMatches(r, graph, partialMatch)) {
			if (match.getNodeTargets().contains(object)) {
				num++;
			}
		}
		if (num != n) {
			throw new AssertionError("expected: Object contained in " + n + " matches, but contained in " + num);
		}
	}

	/**
	 * Assert that an object is contained in at least one of a {@link Rule}'s matches
	 * 
	 * @param r {@link Rule}
	 * @param engine {@link Engine} by which the {@link Rule} will be executed
	 * @param object {@link EObject}
	 * @throws AssertionError
	 */
	public static void assertObjectContainedInAtLeastOneMatch(Rule r, EGraph graph, Match partialMatch, Engine engine,
			EObject object) throws AssertionError {
		for (Match m : engine.findMatches(r, graph, partialMatch)) {
			if (m.getNodeTargets().contains(object)) {
				return;
			}
		}
		throw new AssertionError("expected: Object is contained in at least one match of " + r.getName()
				+ ", but is contained in none");
	}

	/**
	 * Assert that no element contained in the specified {@link Collection} is contained in any {@link Match} produced
	 * by executing the specified {@link Rule} on the {@link Engine}
	 * 
	 * @param r {@link Rule}
	 * @param engine {@link Engine} by which the {@link Rule} will be executed
	 * @param group {@link Collection} of {@link EObject}s
	 * @throws AssertionError
	 */
	public static void assertNoObjectFromGroupContainedInAnyMatch(Rule r, EGraph graph, Match partialMatch,
			Engine engine, Collection<? extends EObject> group) throws AssertionError {

		for (Match m : engine.findMatches(r, graph, partialMatch)) {
			for (EObject eo : group) {
				if (m.getNodeTargets().contains(eo)) {
					throw new AssertionError("expected: No object from group is contained in any match, but at least "
							+ eo + " is contained in at least one");
				}
			}
		}
	}

	/**
	 * Assert that the whole group is not contained in any {@link Match} produced by executing the {@link Rule} on the
	 * {@link Engine}
	 * 
	 * @param r {@link Rule}
	 * @param engine {@link Engine} by which the {@link Rule} will be executed
	 * @param group {@link Collection} of {@link EObject}s
	 * @throws AssertionError
	 */
	public static void assertGroupContainedInNoMatch(Rule r, EGraph graph, Match partialMatch, Engine engine,
			Collection<? extends EObject> group) throws AssertionError {

		for (Match m : engine.findMatches(r, graph, partialMatch)) {
			if (m.getNodeTargets().containsAll(group)) {
				throw new AssertionError("expected: Group is contained in no match, but is contained in at least one");
			}
		}

	}

	/**
	 * Assert that the whole group is contained in at least one {@link Match} produced by applying the specified
	 * {@link Rule} to the {@link EGraph}
	 * 
	 * @param r {@link Rule}
	 * @param graph {@link EGraph} the {@link Rule} will be applied to
	 * @param group {@link Collection} of {@link EObject}s
	 * @throws AssertionError
	 */
	public static void assertGroupContainedInAtLeastOneMatch(Rule r, EGraph graph, Match partialMatch, Engine engine,
			Collection<? extends EObject> group) throws AssertionError {

		for (Match m : engine.findMatches(r, graph, partialMatch)) {
			if (m.getNodeTargets().containsAll(group)) {
				return;
			}
		}

		throw new AssertionError("expected: Group is contained in at least one match of " + r.getName()
				+ ", but is contained in none");
	}

	/**
	 * Assert that at least one object from the group is contained in at least on {@link Match} produced by applying the
	 * specified {@link Rule} on the {@link EGraph}
	 * 
	 * @param r {@link Rule}
	 * @param graph {@link EGraph} the {@link Rule} will be applied to
	 * @param group {@link Collection} of {@link EObject}s
	 */
	public static void assertAnyObjectFromGroupContainedInAtLeastOneMatch(Rule r, EGraph graph, Match partialMatch,
			Engine engine, Collection<? extends EObject> group) throws AssertionError {

		for (Match m : engine.findMatches(r, graph, partialMatch)) {
			for (EObject eo : group) {
				if (m.getNodeTargets().contains(eo)) {
					return;
				}
			}
		}

		throw new AssertionError(
				"expected: At least one object from group is contained in at least one match, but isn't.");
	}

	/**
	 * Asserts that a RuleApplication matches all elements from the group (not neccessarily in one match; if this is
	 * desired, use {@link assertGroupContainedInAtLeastOneMatch})
	 * 
	 * @param ra {@link RuleApplication}
	 * @param group {@link Collection} of {@link EObject}s
	 * @throws AssertionError
	 */
	public static void assertGroupIsMatched(Rule rule, EGraph graph, Match partialMatch, Engine engine,
			Collection<? extends EObject> group) throws AssertionError {
		HashMap<EObject, Boolean> matchContained = new HashMap<EObject, Boolean>();
		for (EObject eo : group) {
			matchContained.put(eo, false);
		}

		for (Match m : engine.findMatches(rule, graph, partialMatch)) {
			for (EObject eo2 : m.getNodeTargets()) {
				matchContained.put(eo2, true);
			}
		}

		if (matchContained.containsValue(false)) {
			System.out.println("~~~~~~");
			for (Entry<EObject, Boolean> s : matchContained.entrySet()) {
				if (s.getValue() == false) {
					System.out.println(s.getKey() + " -> " + s.getValue());
				}
			}
			throw new AssertionError("expected: group is matched, but some elements aren't.");
		}
	}

	/**
	 * Asserts that a {@link RuleApplication} matches all elements from the group (not neccessarily in one match) and
	 * the match doesn't contain any objects not in the group.
	 * 
	 * @param ra
	 * @param group
	 * @throws AssertionError
	 */
	public static void assertOnlyGroupIsMatched(Rule rule, EGraph graph, Match partialMatch, Engine engine,
			Collection<? extends EObject> group) throws AssertionError {

		// Use injective matching?
		boolean injective = rule.isInjectiveMatching();
		Boolean inj = (Boolean) engine.getOptions().get(Engine.OPTION_INJECTIVE_MATCHING);
		if (inj != null) {
			injective = inj;
		}

		if (!injective) {
			// non-injective mode:
			HashMap<EObject, Integer> matchContents = new HashMap<EObject, Integer>();
			for (EObject eo : group) {
				if (matchContents.containsKey(eo)) {
					matchContents.put(eo, matchContents.get(eo) + 1);
				} else {
					matchContents.put(eo, 1);
				}
			}

			for (Match m : engine.findMatches(rule, graph, partialMatch)) {
				for (EObject eo : m.getNodeTargets()) {
					if (group.contains(eo)) {
						matchContents.put(eo, matchContents.get(eo) - 1);
					} else {
						throw new AssertionError(
								"expected: entire group + only elements from group are matched, but match contains elements not in group");
					}
				}
			}

			for (Integer i : matchContents.values()) {
				if (i != 0) {
					throw new AssertionError("expected: group is matched, but some elements aren't.");
				}
			}

		} else {
			// injective mode:
			HashMap<EObject, Boolean> matchContents = new HashMap<EObject, Boolean>();
			for (EObject eo : group) {
				matchContents.put(eo, false);
			}

			for (Match m : engine.findMatches(rule, graph, partialMatch)) {
				for (EObject eo : m.getNodeTargets()) {
					if (group.contains(eo)) {
						matchContents.put(eo, true);
					} else {
						throw new AssertionError(
								"expected: entire group + only elements from group are matched, but match contains elements not in group");
					}
				}
			}

			if (matchContents.containsValue(false)) {
				throw new AssertionError("expected: group is matched, but some elements aren't.");
			}
		}
	}

	/**
	 * Assert that a {@link Match} contains only elements from the group.
	 * 
	 * @param match
	 * @param group
	 * @throws AssertionError
	 */
	public static void assertMatchIsGroup(Match match, Collection<? extends EObject> group) throws AssertionError {
		HashMap<EObject, Integer> matchContents = new HashMap<EObject, Integer>();

		for (EObject eo : group) {
			if (matchContents.containsKey(eo)) {
				matchContents.put(eo, matchContents.get(eo) + 1);
			} else {
				matchContents.put(eo, 1);
			}
		}

		for (EObject eo : match.getNodeTargets()) {
			if (group.contains(eo)) {
				matchContents.put(eo, matchContents.get(eo) - 1);
			} else {
				throw new AssertionError("match contains elements not in group");
			}
		}

		for (Integer i : matchContents.values()) {
			if (i != 0) {
				throw new AssertionError(
						"expected: match is group, but some elements in the group are not in the match");
			}
		}
	}
	
	public static void assertOverlappingMultiMatchesRemoved(Rule rule, EGraph graph, Match partialMatch, Engine engine, int numExpected) throws AssertionError {
		List<Match> matches = new ArrayList<Match>();
		int count = 0;
		for(Rule r : rule.getAllMultiRules()){
			if(r.isMultiRule()){
				for (Match m : engine.findMatches(rule, graph, null)) {
					matches = InterpreterUtil.removeOverlappingMultiMatches(m, r);
					for (Match m2 : matches){
						count = count + 1;
					}
				}
			}
		}
		if(count != numExpected){
			throw new AssertionError(
					"expected: number of matches equals " + numExpected + " but instead was " + count);
		}
	}

}
