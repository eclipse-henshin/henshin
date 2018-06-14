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
 * Tests the support of inheritance focusing on references to find critical pairs.
 * 
 * @author Kristopher Born
 *
 */
public class InheritedReferenceTesting {

	final String PATH = "basic/testModelsCPA/inheritedReferenceTesting/";
	final String henshinFileName = "rulesForInheritedReferenceTesting.henshin";

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
	 * Test the delete use conflicts involving the deletion of a containment reference and the usage of the same
	 * containment reference to a subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _11_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "removeVehicleFromFleet";
		String secondRuleName = "getMotorVehicleFromFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for delete use conflicts involving the deletion of a containment reference and the usage of the same
	 * containment reference to a superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _12_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "removeMotorVehicleFromFleet";
		String secondRuleName = "getVehicleFromFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the delete use conflict involving the deletion of a reference and the usage of the same reference to a
	 * subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _13_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "removeDriverFromVehicle";
		String secondRuleName = "getDriverOfMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the delete use conflict involving the deletion of a reference and the usage of the same reference to a
	 * superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _14_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "removeDriverFromMotorVehicle";
		String secondRuleName = "getDriverOfVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the delete use conflict involving the deletion of a reference and the usage of the eOpposite reference
	 * to a subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _15_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "removeOwnerOfVehicle";
		String secondRuleName = "getMotorVehicleInOwnershipOfPerson";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the delete use conflict involving the deletion of a reference and the usage of the eOpposite reference
	 * to a superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _16_deleteUseConflict() throws UnsupportedRuleException {
		String firstRuleName = "removeOwnerOfMotorVehicle";
		String secondRuleName = "getVehicleInOwnershipOfPerson";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the produce forbid conflict involving the creation of a containment reference and the not allowed
	 * (forbidden) existence of the same containment reference to a superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _21_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "addMotorVehicleToFleet";
		String secondRuleName = "noVehicleInFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * // * Test for the produce forbid conflict involving the creation of a containment reference and the not allowed
	 * (forbidden) existence of the same containment reference to a subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _22_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "addVehicleToFleet";
		String secondRuleName = "noMotorVehicleInFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the produce forbid conflict involving the creation of a reference and the not allowed (forbidden)
	 * existence of the same reference to a subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _23_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "assignDriverToVehicle";
		String secondRuleName = "noDriverOfVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the produce forbid conflict involving the creation of a reference and the not allowed (forbidden)
	 * existence of the same reference to an abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _24_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "assignDriverToMotorVehicle";
		String secondRuleName = "noDriverOfVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the produce forbid conflict involving the creation of a reference and the not allowed (forbidden)
	 * existence of the eOpposite reference to an subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _25_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "assignVehicleOwnershipToPerson";
		String secondRuleName = "noOwnerOfMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the produce forbid conflict involving the creation of a reference and the usage of the same reference to
	 * an abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _26_produceForbidConflict() throws UnsupportedRuleException {
		String firstRuleName = "assignMotorVehicleOwnershipToPerson";
		String secondRuleName = "noOwnerOfVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

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
	 * Test for the delete forbid dependency involving the deletion of a containment reference and the not allowed
	 * (forbidden) existence of the same reference to an subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _61_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "removeVehicleFromFleet";
		String secondRuleName = "noMotorVehicleInFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the delete forbid dependency involving the deletion of a containment reference and the not allowed
	 * (forbidden) existence of the same reference to an abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _62_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "removeMotorVehicleFromFleet";
		String secondRuleName = "noVehicleInFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the delete forbid dependency involving the deletion of a reference and the not allowed (forbidden)
	 * existence of the same reference to an subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _63_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "removeDriverFromVehicle";
		String secondRuleName = "noDriverOfMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the delete forbid dependency involving the deletion of a reference and the not allowed (forbidden)
	 * existence of the same reference to an abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _64_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "removeDriverFromMotorVehicle";
		String secondRuleName = "noDriverOfVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the delete forbid dependency involving the deletion of a reference and the not allowed (forbidden)
	 * existence of the eOpposite reference to an subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _65_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "removeOwnerOfVehicle";
		String secondRuleName = "PersonOwnsNoMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the delete forbid dependency involving the deletion of a reference and the not allowed (forbidden)
	 * existence of the eOpposite reference to an abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _66_deleteForbidDependency() throws UnsupportedRuleException {
		String firstRuleName = "removeOwnerOfMotorVehicle";
		String secondRuleName = "PersonOwnsNoVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the produce use dependency involving the creation of a containment reference and the usage of the
	 * containment reference to an subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _71_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "addVehicleToFleet";
		String secondRuleName = "getMotorVehicleFromFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the produce use dependency involving the creation of a containment reference and the usage of the
	 * containment reference to an superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _72_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "addMotorVehicleToFleet";
		String secondRuleName = "getVehicleFromFleet";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the produce use dependency involving the creation of a reference and the usage of the reference to an
	 * subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _73_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "assignDriverToVehicle";
		String secondRuleName = "getDriverOfMotorVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the produce use dependency involving the creation of a reference and the usage of the reference to an
	 * superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _74_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "assignDriverToMotorVehicle";
		String secondRuleName = "getDriverOfVehicle";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the produce use dependency involving the creation of a reference and the usage of the reference to an
	 * subclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _75_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "assignVehicleOwnershipToPerson";
		String secondRuleName = "getMotorVehicleInOwnershipOfPerson";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
	 * Test for the produce use dependency involving the creation of a reference and the usage of the reference to an
	 * abstract superclass.
	 * 
	 * @throws UnsupportedRuleException when attempting to analyze rules which are not supported
	 */
	@Test
	public void _76_produceUseDependency() throws UnsupportedRuleException {
		String firstRuleName = "assignMotorVehicleOwnershipToPerson";
		String secondRuleName = "getVehicleInOwnershipOfPerson";

		Set<Rule> firstRule = new HashSet<Rule>();
		Set<Rule> secondRule = new HashSet<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cdaOptions);
		CPAResult result = cpaByAgg.runDependencyAnalysis();

		int expectedQuantityOfCPs = 1;
		if (result.getCriticalPairs().size() != expectedQuantityOfCPs) {
			String unequalNumberOfCPs = "expected quantity of critical pairs: " + expectedQuantityOfCPs;
			unequalNumberOfCPs += ", obtained quantity of critical pairs: " + result.getCriticalPairs().size();
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
