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

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
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
import org.eclipse.emf.henshin.multicda.cpa.result.Dependency;
import org.eclipse.emf.henshin.multicda.cpa.result.DependencyKind;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the support of inheritance on finding critical pairs.
 * 
 * @author Kristopher Born
 *
 */
public class BasicInheritanceTesting {

	final String PATH = "basic/testModelsCPA/inheritanceTesting/";
	final String henshinFileName = "basicRulesForInheritanceTesting.henshin";

	Module module;
	private ICriticalPairAnalysis cpaByAgg;
	CDAOptions cdaOptions;

	@Before
	public void setUp() throws Exception {
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(PATH);
		// Load the module:
		module = resourceSet.getModule(henshinFileName, false);
		cpaByAgg = new CpaByAGG();
		cdaOptions = new CDAOptions();
	}

	/**
	 * Minimal test for the delete use conflict involving the deletion of a superclass of the used class.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _11_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "deleteMotorVehicle";
		String secondRuleName = "useDigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
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
	 * Minimal test for the delete use conflict involving the deletion of a subclass of the used class.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _12_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "deleteDigger";
		String secondRuleName = "useMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
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
	 * Minimal test ensuring that two different subclasses of a common superclass do not lead to a delete use conflict.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _13_NOdeleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "deleteTruck";
		String secondRuleName = "useConstructionSiteTool";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 0;
		String message = "No conflict expected! Conflict occurred nevertheless.";
		assertTrue(message, result.getCriticalPairs().size() == expectedQuantityOfCPs);
	}

	/**
	 * Minimal test for the delete use conflict involving the deletion of an abstract superclass with the used class.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _14_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "deleteVehicle";
		String secondRuleName = "useDigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
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
	 * Minimal test for the delete use conflict involving the deletion of a subclass of the used abstracts class and the
	 * subclass inherits from than just the used superclasses.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _15_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "deleteDigger";
		String secondRuleName = "useVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
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
	 * Minimal test ensuring that the creation of a superclass do not lead to a produce forbid conflict with its
	 * subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _21_NOproduceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "produceMotorVehicle";
		String secondRuleName = "forbidDigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 0;
		String message = "No conflict expected! Conflict occurred nevertheless.";
		assertTrue(message, result.getCriticalPairs().size() == expectedQuantityOfCPs);
	}

	/**
	 * Minimal test ensuring that the creation of a subclass leads to a produce forbid conflict with its forbidden
	 * superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _22_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "produceDigger";
		String secondRuleName = "forbidMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Conflict) {
				String differentKindOfCP = "expected kind of critical pair: " + ConflictKind.PRODUCE_FORBID_CONFLICT;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Conflict) cp).getConflictKind().toString();
				assertTrue(differentKindOfCP,
						((Conflict) cp).getConflictKind().equals(ConflictKind.PRODUCE_FORBID_CONFLICT));
			} else {
				String differentTypeOfCP = "expected: CONFLICT, obtained: DEPENDENCY";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the creation of a class do not lead to a produce forbid conflict with another class
	 * when both just share a common superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _23_NOproduceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "produceTruck";
		String secondRuleName = "forbidDigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 0;
		String message = "No conflict expected! Conflict occurred nevertheless.";
		assertTrue(message, result.getCriticalPairs().size() == expectedQuantityOfCPs);
	}

	/**
	 * Minimal test ensuring that the creation of a subclass leads to a produce forbid conflict with its forbidden
	 * abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _24_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "produceDigger";
		String secondRuleName = "forbidVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Conflict) {
				String differentKindOfCP = "expected kind of critical pair: " + ConflictKind.PRODUCE_FORBID_CONFLICT;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Conflict) cp).getConflictKind().toString();
				assertTrue(differentKindOfCP,
						((Conflict) cp).getConflictKind().equals(ConflictKind.PRODUCE_FORBID_CONFLICT));
			} else {
				String differentTypeOfCP = "expected: CONFLICT, obtained: DEPENDENCY";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the deletion of a superclass leads to a delete forbid dependency with its forbidden
	 * subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _61_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "deleteMotorVehicle";
		String secondRuleName = "forbidDigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Dependency) {
				String differentKindOfCP = "expected kind of critical pair: " + DependencyKind.DELETE_FORBID_DEPENDENCY;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Dependency) cp).getDependencyKind().toString();
				assertTrue(differentKindOfCP,
						((Dependency) cp).getDependencyKind().equals(DependencyKind.DELETE_FORBID_DEPENDENCY));
			} else {
				String differentTypeOfCP = "expected: DEPENDENCY, obtained: CONFLICT";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the deletion of a subclass leads to a delete forbid dependency with its forbidden
	 * superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _62_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "deleteDigger";
		String secondRuleName = "forbidMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Dependency) {
				String differentKindOfCP = "expected kind of critical pair: " + DependencyKind.DELETE_FORBID_DEPENDENCY;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Dependency) cp).getDependencyKind().toString();
				assertTrue(differentKindOfCP,
						((Dependency) cp).getDependencyKind().equals(DependencyKind.DELETE_FORBID_DEPENDENCY));
			} else {
				String differentTypeOfCP = "expected: DEPENDENCY, obtained: CONFLICT";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the deletion of a class doens't lead to a delete forbid dependency with another class
	 * when both just share a common superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _63_NOdeleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "deleteTruck";
		String secondRuleName = "forbidConstructionSiteTool";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 0;
		String message = "No conflict expected! Conflict occurred nevertheless.";
		assertTrue(message, result.getCriticalPairs().size() == expectedQuantityOfCPs);
	}

	/**
	 * Minimal test ensuring that the deletion of an abstract superclass leads to a delete forbid dependency with its
	 * forbidden subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _64_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "deleteVehicle";
		String secondRuleName = "forbidDigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Dependency) {
				String differentKindOfCP = "expected kind of critical pair: " + DependencyKind.DELETE_FORBID_DEPENDENCY;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Dependency) cp).getDependencyKind().toString();
				assertTrue(differentKindOfCP,
						((Dependency) cp).getDependencyKind().equals(DependencyKind.DELETE_FORBID_DEPENDENCY));
			} else {
				String differentTypeOfCP = "expected: DEPENDENCY, obtained: CONFLICT";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the deletion of an subclass leads to a delete forbid dependency with its forbidden
	 * abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _65_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "deleteDigger";
		String secondRuleName = "forbidVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Dependency) {
				String differentKindOfCP = "expected kind of critical pair: " + DependencyKind.DELETE_FORBID_DEPENDENCY;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Dependency) cp).getDependencyKind().toString();
				assertTrue(differentKindOfCP,
						((Dependency) cp).getDependencyKind().equals(DependencyKind.DELETE_FORBID_DEPENDENCY));
			} else {
				String differentTypeOfCP = "expected: DEPENDENCY, obtained: CONFLICT";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the creation of a class doens't lead to a produce use dependency with another class
	 * which is a subclass of the produced class.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _71_NOproduceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "produceMotorVehicle";
		String secondRuleName = "usedigger";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runConflictAnalysis();

		int expectedQuantityOfCPs = 0;
		String message = "No conflict expected! Conflict occurred nevertheless.";
		assertTrue(message, result.getCriticalPairs().size() == expectedQuantityOfCPs);
	}

	/**
	 * Minimal test ensuring that the creation of an class leads to a produce use dependency with its used superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _72_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "produceDigger";
		String secondRuleName = "useMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Dependency) {
				String differentKindOfCP = "expected kind of critical pair: " + DependencyKind.PRODUCE_USE_DEPENDENCY;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Dependency) cp).getDependencyKind().toString();
				assertTrue(differentKindOfCP,
						((Dependency) cp).getDependencyKind().equals(DependencyKind.PRODUCE_USE_DEPENDENCY));
			} else {
				String differentTypeOfCP = "expected: DEPENDENCY, obtained: CONFLICT";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}

	/**
	 * Minimal test ensuring that the creation of a class doens't lead to a produce use dependency with another class
	 * which just share a common superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _73_NOproduceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "produceTruck";
		String secondRuleName = "useConstructionSiteTool";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 0;
		String message = "No dependency expected! Dependency occurred nevertheless.";
		assertTrue(message, result.getCriticalPairs().size() == expectedQuantityOfCPs);
	}

	/**
	 * Minimal test ensuring that the creation of an class leads to a produce use dependency with its used abstract
	 * superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _74_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "producedigger";
		String secondRuleName = "useVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical  pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical  pairs: " + result.getCriticalPairs().size();
			assertTrue(unequalNumberOfCPs, false);
		} else {
			CriticalPair cp = result.getCriticalPairs().get(0);
			if (cp instanceof Dependency) {
				String differentKindOfCP = "expected kind of critical pair: " + DependencyKind.PRODUCE_USE_DEPENDENCY;
				differentKindOfCP += ", obtained kind of critical pair: "
						+ ((Dependency) cp).getDependencyKind().toString();
				assertTrue(differentKindOfCP,
						((Dependency) cp).getDependencyKind().equals(DependencyKind.PRODUCE_USE_DEPENDENCY));
			} else {
				String differentTypeOfCP = "expected: DEPENDENCY, obtained: CONFLICT";
				assertTrue(differentTypeOfCP, false);
			}
		}
	}
}