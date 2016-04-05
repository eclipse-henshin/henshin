/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.cpa;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.henshin.cpa.CPAOptions;
import org.eclipse.emf.henshin.cpa.CPAUtility;
import org.eclipse.emf.henshin.cpa.CpaByAGG;
import org.eclipse.emf.henshin.cpa.ICriticalPairAnalysis;
import org.eclipse.emf.henshin.cpa.UnsupportedRuleException;
import org.eclipse.emf.henshin.cpa.result.CPAResult;
import org.eclipse.emf.henshin.cpa.result.Conflict;
import org.eclipse.emf.henshin.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the filtering of results which are based on the creation of duplicated references .
 * 
 * @author Kristopher Born
 *
 */
public class PreventDuplicateReferencesTest {

	final String PATH = "basic/testModelsCPA/preventDuplicateReferencesTest/";
	final String henshinFileName = "refactorings.henshin";

	Module module;
	private ICriticalPairAnalysis cpaByAgg;
	CPAOptions cpaOptions;

	@Before
	public void setUp() throws Exception {
		cpaByAgg = new CpaByAGG();
		cpaOptions = new CPAOptions();
	}

	/**
	 * Tests the situation when AGG reports critical pairs with parallel references of the same type between the same
	 * nodes. Since this is not valid for EMF models such results have to be filtered!
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void duplicateReferencesTest() throws UnsupportedRuleException {
		String firstRuleName = "Move_Attribute";
		String secondRuleName = "Move_Attribute";

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		HenshinResourceSet resourceSet = new HenshinResourceSet(PATH);
		module = resourceSet.getModule(henshinFileName, false);

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of cirtical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of cirtical pairs: " + result.getCriticalPairs().size();
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

}
