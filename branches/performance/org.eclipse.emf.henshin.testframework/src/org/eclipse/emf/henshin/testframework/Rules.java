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

import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.interpreter.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.impl.EmfEngine;
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
		RuleApplicationImpl ra = new RuleApplicationImpl(engine, r);
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
		RuleApplicationImpl ra = new RuleApplicationImpl(new EmfEngine(graph), r);
		assertRuleHasMatch(ra);
	}
	
	/**
	 * Assert that a {@link RuleApplicationImpl} produces a match
	 * 
	 * @param ra
	 *            {@link RuleApplicationImpl}
	 * @throws AssertionError
	 */
	public static void assertRuleHasMatch(RuleApplicationImpl ra) throws AssertionError {
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
		RuleApplicationImpl ra = new RuleApplicationImpl(engine, r);
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
		RuleApplicationImpl ra = new RuleApplicationImpl(new EmfEngine(graph), r);
		assertRuleHasNoMatch(ra);
	}
	
	/**
	 * Assert that a {@link RuleApplicationImpl} produces no match
	 * 
	 * @param ra
	 *            {@link RuleApplicationImpl}
	 * @throws AssertionError
	 */
	public static void assertRuleHasNoMatch(RuleApplicationImpl ra) throws AssertionError {
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
		RuleApplicationImpl ra = new RuleApplicationImpl(engine, r);
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
		RuleApplicationImpl ra = new RuleApplicationImpl(new EmfEngine(graph), r);
		assertRuleHasNMatches(ra, n);
	}
	
	/**
	 * Assert that a {@link RuleApplicationImpl} produces exactly n matches
	 * 
	 * @param ra
	 *            {@link RuleApplicationImpl}
	 * @param n
	 *            Number of expected matches
	 * @throws AssertionError
	 */
	public static void assertRuleHasNMatches(RuleApplicationImpl ra, int n) throws AssertionError {
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
		RuleApplicationImpl ra = new RuleApplicationImpl(engine, r);
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
		RuleApplicationImpl ra = new RuleApplicationImpl(new EmfEngine(graph), r);
		assertRuleCanBeAppliedMultipleTimes(ra);
	}
	
	/**
	 * Assert that a {@link RuleApplicationImpl} can be applied multiple times
	 * 
	 * @param ra
	 *            {@link RuleApplicationImpl}
	 * @throws AssertionError
	 */
	public static void assertRuleCanBeAppliedMultipleTimes(RuleApplicationImpl ra)
			throws AssertionError {
		// TODO: maybe use createTUFromRule for this, as TUs can be run multiple
		// times without problems
		
		// i. apply the rule
		// ii. check if matches can still be found -> yes: rule can be applied
		// multiple times; no -> throw assertion
		Rule raRule = ra.getRule();
		ra.apply();
		RuleApplicationImpl ra2 = new RuleApplicationImpl(ra.getInterpreterEngine(), raRule);
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
		RuleApplicationImpl ra = new RuleApplicationImpl(engine, r);
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
		RuleApplicationImpl ra = new RuleApplicationImpl(new EmfEngine(graph), r);
		assertRuleCanBeAppliedNTimes(ra, n);
	}
	
	/**
	 * Assert that a {@link RuleApplicationImpl} can be applied at least n times
	 * 
	 * @param ra
	 *            {@link RuleApplicationImpl}
	 * @param n
	 *            Number of applications
	 * @throws AssertionError
	 * @throws Exception
	 *             if n < 0
	 */
	public static void assertRuleCanBeAppliedNTimes(RuleApplicationImpl ra, int n)
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
			RuleApplicationImpl ra2 = new RuleApplicationImpl(ra.getInterpreterEngine(), raRule);
			ra2.apply();
		}
		
		RuleApplicationImpl ra2 = new RuleApplicationImpl(ra.getInterpreterEngine(), raRule);
		if (ra2.findAllMatches().size() == 0) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " can be applied at least " + n + " times");
		}
	}
	
	/**
	 * Assert that the specified {@link Rule} is executed by the specified
	 * {@link UnitApplicationImpl}
	 * 
	 * @param ua
	 *            {@link UnitApplicationImpl}
	 * @param r
	 *            {@link Rule} to be executed
	 * @throws AssertionError
	 */
	public static void assertRuleExecutedByUnitApplication(UnitApplicationImpl ua, Rule r)
			throws AssertionError {
		/*
		 * if (!(ua.getAppliedRules().contains(r))) { throw new
		 * AssertionError("expected: Rule " + r.getName() +
		 * " executed by UnitApplication " +
		 * ua.getTransformationUnit().getName() +", but wasn't."); }
		 */
		for (RuleApplicationImpl ra : ua.getAppliedRules()) {
			if (ra.getRule().equals(r)) {
				return;
			}
		}
		
		throw new AssertionError("expected: Rule " + r.getName() + " executed by UnitApplication "
				+ ua.getTransformationUnit().getName() + ", but wasn't.");
		
	}
	
	/**
	 * Asserts that a {@link Rule} was executed n times by the
	 * {@link UnitApplicationImpl}
	 * 
	 * @param ua
	 *            {@link UnitApplicationImpl}
	 * @param r
	 *            {@link Rule}
	 * @param n
	 *            number of times the {@link Rule} should have been executed
	 * @throws AssertionError
	 */
	public static void assertRuleExecutedByUnitApplicationNTimes(UnitApplicationImpl ua, Rule r, int n)
			throws AssertionError {
		int ctr = n;
		for (RuleApplicationImpl ra : ua.getAppliedRules()) {
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
		assertRuleCanBeApplied(new RuleApplicationImpl(engine, r));
	}
	
	public static void assertRuleCanBeApplied(Rule r, EmfGraph graph) throws AssertionError {
		assertRuleCanBeApplied(new RuleApplicationImpl(new EmfEngine(graph), r));
	}
	
	public static void assertRuleCanBeApplied(RuleApplicationImpl ra) throws AssertionError {
		boolean result = ra.apply();
		if (!result) {
			throw new AssertionError("expected: Rule " + ra.getRule().getName()
					+ " can be applied.");
		}
	}
}
