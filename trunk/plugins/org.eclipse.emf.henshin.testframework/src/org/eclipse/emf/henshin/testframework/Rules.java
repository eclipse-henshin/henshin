/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.testframework;

import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Assertions for Rules. Contains statistical assertions for matches (i.e.
 * hasMatch, hasNoMatch, etc.). For assertions on specific objects matched, see
 * {@link Matches}.
 * 
 * @see Matches
 * @see TransformationUnits
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 * 
 */
public class Rules {
	// test number 1
	/**
	 * Asserts that a rule produces a match
	 * 
	 * @param r
	 *            {@link Rule}
	 * @throws AssertionError
	 */
	public static void assertRuleHasMatch(Rule r, EmfEngine engine) throws AssertionError {
		RuleApplication ra = new RuleApplication(engine, r);
		assertRuleHasMatch(ra);
	}
	
	/**
	 * Assert that a rule produces a match
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EmfGraph} the rule should be applied to
	 * @throws AssertionError
	 */
	public static void assertRuleHasMatch(Rule r, EmfGraph graph) throws AssertionError {
		RuleApplication ra = new RuleApplication(new EmfEngine(graph), r);
		assertRuleHasMatch(ra);
	}
	
	/**
	 * Assert that a {@link RuleApplication} produces a match
	 * 
	 * @param ra
	 *            {@link RuleApplication}
	 * @throws AssertionError
	 */
	public static void assertRuleHasMatch(RuleApplication ra) throws AssertionError {
		if (ra.findAllMatches().size() == 0) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " has matches, but has none");
		}
	}
	
	// test number 2
	/**
	 * Assert that a {@link Rule} produces no match
	 * 
	 * @param r
	 *            {@link Rule}
	 * @parem engine {@link EmfEngine}
	 * @throws AssertionError
	 */
	public static void assertRuleHasNoMatch(Rule r, EmfEngine engine) throws AssertionError {
		RuleApplication ra = new RuleApplication(engine, r);
		assertRuleHasNoMatch(ra);
	}
	
	/**
	 * Assert that a {@link Rule} produces no match
	 * 
	 * @param r
	 *            Rule
	 * @param graph
	 *            {@link EmfGraph} the {@link Rule} should be applied to
	 * @throws AssertionError
	 */
	public static void assertRuleHasNoMatch(Rule r, EmfGraph graph) throws AssertionError {
		RuleApplication ra = new RuleApplication(new EmfEngine(graph), r);
		assertRuleHasNoMatch(ra);
	}
	
	/**
	 * Assert that a {@link RuleApplication} produces no match
	 * 
	 * @param ra
	 *            {@link RuleApplication}
	 * @throws AssertionError
	 */
	public static void assertRuleHasNoMatch(RuleApplication ra) throws AssertionError {
		if (ra.findAllMatches().size() != 0) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " has no matches, but has " + ra.findAllMatches().size());
		}
	}
	
	// test number 3
	/**
	 * Assert that a {@link Rule} produces exactly n matches
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link EmfEngine}
	 * @param n
	 *            Number of expected matches
	 * @throws AssertionError
	 */
	public static void assertRuleHasNMatches(Rule r, EmfEngine engine, int n) throws AssertionError {
		RuleApplication ra = new RuleApplication(engine, r);
		assertRuleHasNMatches(ra, n);
	}
	
	/**
	 * Assert that a {@link Rule} produces exactly n matches
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EmfGraph} the rule should be applied to
	 * @param n
	 *            Number of expected matches
	 * @throws AssertionError
	 */
	public static void assertRuleHasNMatches(Rule r, EmfGraph graph, int n) throws AssertionError {
		RuleApplication ra = new RuleApplication(new EmfEngine(graph), r);
		assertRuleHasNMatches(ra, n);
	}
	
	/**
	 * Assert that a {@link RuleApplication} produces exactly n matches
	 * 
	 * @param ra
	 *            {@link RuleApplication}
	 * @param n
	 *            Number of expected matches
	 * @throws AssertionError
	 */
	public static void assertRuleHasNMatches(RuleApplication ra, int n) throws AssertionError {
		if (ra.findAllMatches().size() != n) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName() + " has " + n
					+ " matches, but has " + ra.findAllMatches().size());
		}
	}
	
	// test number 4
	
	/**
	 * Assert that a {@link Rule} can be applied multiple times
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link EmfEngine}
	 * @throws AssertionError
	 */
	public static void assertRuleCanBeAppliedMultipleTimes(Rule r, EmfEngine engine)
			throws AssertionError {
		RuleApplication ra = new RuleApplication(engine, r);
		assertRuleCanBeAppliedMultipleTimes(ra);
	}
	
	/**
	 * Assert that a {@link Rule} can be applied multiple times
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EmfGraph} the rule should be applied to
	 * @throws AssertionError
	 */
	public static void assertRuleCanBeAppliedMultipleTimes(Rule r, EmfGraph graph)
			throws AssertionError {
		RuleApplication ra = new RuleApplication(new EmfEngine(graph), r);
		assertRuleCanBeAppliedMultipleTimes(ra);
	}
	
	/**
	 * Assert that a {@link RuleApplication} can be applied multiple times
	 * 
	 * @param ra
	 *            {@link RuleApplication}
	 * @throws AssertionError
	 */
	public static void assertRuleCanBeAppliedMultipleTimes(RuleApplication ra)
			throws AssertionError {
		// TODO: maybe use createTUFromRule for this, as TUs can be run multiple
		// times without problems
		
		// i. apply the rule
		// ii. check if matches can still be found -> yes: rule can be applied
		// multiple times; no -> throw assertion
		Rule raRule = ra.getRule();
		ra.apply();
		RuleApplication ra2 = new RuleApplication(ra.getInterpreterEngine(), raRule);
		if (ra2.findAllMatches().size() == 0) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " can be applied multiple times.");
		}
		
	}
	
	// test number 5
	
	/**
	 * Assert that a {@link Rule} can be applied at least n times
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param engine
	 *            {@link EmfEngine}
	 * @param n
	 *            Number of applications
	 * @throws AssertionError
	 * @throws Exception
	 *             if n < 0
	 */
	public static void assertRuleCanBeAppliedNTimes(Rule r, EmfEngine engine, int n)
			throws AssertionError, Exception {
		RuleApplication ra = new RuleApplication(engine, r);
		assertRuleCanBeAppliedNTimes(ra, n);
	}
	
	/**
	 * Assert that a {@link Rule} can be applied at least n times
	 * 
	 * @param r
	 *            {@link Rule}
	 * @param graph
	 *            {@link EmfGraph} the rule should be applied to
	 * @param n
	 *            Number of applications
	 * @throws AssertionError
	 * @throws Exception
	 *             if n < 0
	 */
	public static void assertRuleCanBeAppliedNTimes(Rule r, EmfGraph graph, int n)
			throws AssertionError, Exception {
		RuleApplication ra = new RuleApplication(new EmfEngine(graph), r);
		assertRuleCanBeAppliedNTimes(ra, n);
	}
	
	/**
	 * Assert that a {@link RuleApplication} can be applied at least n times
	 * 
	 * @param ra
	 *            {@link RuleApplication}
	 * @param n
	 *            Number of applications
	 * @throws AssertionError
	 * @throws Exception
	 *             if n < 0
	 */
	public static void assertRuleCanBeAppliedNTimes(RuleApplication ra, int n)
			throws AssertionError, Exception {
		// i. apply the rule (n-1) times
		// ii. check if matches can still be found -> yes: rule can be applied
		// at least n times; no -> throw assertion
		if (n < 0) {
			throw new Exception("n needs to be positive.");
		}
		
		Rule raRule = ra.getRule();
		ra.apply();
		
		for (int i = 0; i < (n - 1); i++) {
			RuleApplication ra2 = new RuleApplication(ra.getInterpreterEngine(), raRule);
			ra2.apply();
		}
		
		RuleApplication ra2 = new RuleApplication(ra.getInterpreterEngine(), raRule);
		if (ra2.findAllMatches().size() == 0) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " can be applied at least " + n + " times");
		}
	}
	
	/**
	 * Assert that the specified {@link Rule} is executed by the specified
	 * {@link UnitApplication}
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @param r
	 *            {@link Rule} to be executed
	 * @throws AssertionError
	 */
	public static void assertRuleExecutedByUnitApplication(UnitApplication ua, Rule r)
			throws AssertionError {
		/*
		 * if (!(ua.getAppliedRules().contains(r))) { throw new
		 * AssertionError("expected: Rule " + r.getName() +
		 * " executed by UnitApplication " +
		 * ua.getTransformationUnit().getName() +", but wasn't."); }
		 */
		for (RuleApplication ra : ua.getAppliedRules()) {
			if (ra.getRule().equals(r)) {
				return;
			}
		}
		
		throw new AssertionError("expected: Rule " + r.getName() + " executed by UnitApplication "
				+ ua.getTransformationUnit().getName() + ", but wasn't.");
		
	}
	
	/**
	 * Asserts that a {@link Rule} was executed n times by the
	 * {@link UnitApplication}
	 * 
	 * @param ua
	 *            {@link UnitApplication}
	 * @param r
	 *            {@link Rule}
	 * @param n
	 *            number of times the {@link Rule} should have been executed
	 * @throws AssertionError
	 */
	public static void assertRuleExecutedByUnitApplicationNTimes(UnitApplication ua, Rule r, int n)
			throws AssertionError {
		int ctr = n;
		for (RuleApplication ra : ua.getAppliedRules()) {
			if (ra.getRule().equals(r)) {
				ctr--;
			}
		}
		
		if (ctr == 0) {
			return;
		} else {
			throw new AssertionError("expected: Rule " + r.getName() + " executed " + n
					+ " times by UnitApplication, but was executed " + (n - ctr) + " times.");
		}
	}
	
	public static void assertRuleCanBeApplied(Rule r, EmfEngine engine) throws AssertionError {
		assertRuleCanBeApplied(new RuleApplication(engine, r));
	}
	
	public static void assertRuleCanBeApplied(Rule r, EmfGraph graph) throws AssertionError {
		assertRuleCanBeApplied(new RuleApplication(new EmfEngine(graph), r));
	}
	
	public static void assertRuleCanBeApplied(RuleApplication ra) throws AssertionError {
		boolean result = ra.apply();
		if (!result) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " can be applied.");
		}
	}
}
