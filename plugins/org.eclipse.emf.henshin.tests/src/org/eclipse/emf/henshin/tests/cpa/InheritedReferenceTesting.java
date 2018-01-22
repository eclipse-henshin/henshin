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
import org.eclipse.emf.henshin.cpa.result.Dependency;
import org.eclipse.emf.henshin.cpa.result.DependencyKind;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
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
	CPAOptions cpaOptions;

	@Before
	public void setUp() throws Exception {
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(PATH);
		// Load the module:
		module = resourceSet.getModule(henshinFileName, false);
		cpaByAgg = new CpaByAGG();
		cpaOptions = new CPAOptions();
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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

		List<Rule> firstRule = new LinkedList<Rule>();
		List<Rule> secondRule = new LinkedList<Rule>();

		CPAUtility.extractSingleRules(module, firstRule, firstRuleName, secondRule, secondRuleName);

		cpaByAgg.init(firstRule, secondRule, cpaOptions);
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
