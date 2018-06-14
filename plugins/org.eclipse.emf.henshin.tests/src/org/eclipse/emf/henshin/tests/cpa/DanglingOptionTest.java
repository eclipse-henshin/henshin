/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.cpa;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.multicda.cpa.CDAOptions;
import org.eclipse.emf.henshin.multicda.cpa.CPAUtility;
import org.eclipse.emf.henshin.multicda.cpa.CpaByAGG;
import org.eclipse.emf.henshin.multicda.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.multicda.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;
import org.eclipse.emf.henshin.multicda.cpa.result.Conflict;
import org.eclipse.emf.henshin.multicda.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the support of rules with dangling option on and off as well as the 'check' for sets of rules with inconsistent
 * dangling option.
 * 
 * @author Kristopher Born
 *
 */
public class DanglingOptionTest {

	final String PATH = "basic/testModelsCPA/danglingOptionTest/";
	final String henshinFileName = "checkDangling.henshin";

	Module module;
	private ICriticalPairAnalysis cpaByAgg;
	CDAOptions cdaOptions;

	@Before
	public void setUp() throws Exception {
		cpaByAgg = new CpaByAGG();
		cdaOptions = new CDAOptions();
	}

	/**
	 * Test for a situation in which no conflict is analyzed due to the activated dangling option.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void checkDanglingOn() throws UnsupportedRuleException {
		String firstRuleName = "Rule1CheckDanglingOn";
		String secondRuleName = "Rule2CheckDanglingOn";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		HenshinResourceSet resourceSet = new HenshinResourceSet(PATH);
		module = resourceSet.getModule(henshinFileName, false);

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 0;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		}
	}

	/**
	 * Test for a situation in which a conflict is analyzed, which would not be analyzed when the dangling option is
	 * enabled.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void checkDanglingOff() throws UnsupportedRuleException {
		String firstRuleName = "Rule1CheckDanglingOff";
		String secondRuleName = "Rule2CheckDanglingOff";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		HenshinResourceSet resourceSet = new HenshinResourceSet(PATH);
		module = resourceSet.getModule(henshinFileName, false);

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Conflict) {
				String differentKindOfCP = "expected kind of critical pair: " + ConflictKind.DELETE_USE_CONFLICT;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Conflict) cp).getConflictKind().toString();
				assertTrue(differentKindOfCP, ((Conflict) cp).getConflictKind()
						.equals(ConflictKind.DELETE_USE_CONFLICT));
			} else {
				String differentTypeOfCP = "expected: CONFLICT, obtained: DEPENDENCY";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Test for the 'check'-function of the passed rule set. Check function shall deny the inconsistent rule set.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void checkDanglingIconsistent() {
		String firstRuleName = "Rule1CheckDanglingOff";
		String secondRuleName = "Rule2CheckDanglingOn";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		HenshinResourceSet resourceSet = new HenshinResourceSet(PATH);
		module = resourceSet.getModule(henshinFileName, false);

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		boolean exceptionThrown = false;

		try {
			Set<org.eclipse.emf.henshin.model.Rule> rulesToBeChecked = new HashSet<org.eclipse.emf.henshin.model.Rule>(
					firstRule);
			rulesToBeChecked.addAll(secondRule);
			cpaByAgg.check(rulesToBeChecked);
		} catch (UnsupportedRuleException e) {
			if (e.getMessage().equals(UnsupportedRuleException.inconsistentDanglingOptions))
				exceptionThrown = true;
		}
		Assert.assertTrue(exceptionThrown);
	}

}
