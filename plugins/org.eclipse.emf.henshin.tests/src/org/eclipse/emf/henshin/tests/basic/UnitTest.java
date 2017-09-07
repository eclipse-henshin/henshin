/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.*;

import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.henshin.model.impl.IteratedUnitImpl;
import org.eclipse.emf.henshin.model.impl.SequentialUnitImpl;
import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Units;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelFactory;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests execution of units.
 * 
 * @author Felix Rieger
 * @author Stefan Jurack
 * @author Christian Krause
 *
 */
public class UnitTest extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		TestmodelPackage.eINSTANCE.eClass();
		initFactory("testmodel", new XMIResourceFactoryImpl());
		init("basic/rules/unitTests.henshin");		
		setEGraphPath("basic/models/unitTestsModels/", "testmodel");
	}

	@Test
	public void testAmalgamationUnitAttributeInKernelRuleNac() {
		loadEGraph("graphBefore_amu_attributeInKernelNac");
		loadUnit("loop_amu_attributeInKernelNac");
		Units.assertUnitCanBeExecuted(htUnitApp);
	}

	@Test
	public void testEmptyIndependentUnit() {
		loadEGraph("graphBefore_countedUnit");
		loadUnit("emptyIndependentUnit");
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	public void testEmptyPriorityUnit() {
		loadEGraph("graphBefore_countedUnit");
		loadUnit("emptyPriorityUnit");
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}
	
	@Test
	/**
	 * Execution of strict SequentialUnit should return false if not all
	 * sub-units can be applied. Without rollback, the changes of applied
	 * iterations should remain.
	 */
	public void testStrictSequentialUnitWithoutRollbackFails() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(true);
		((SequentialUnitImpl)htUnit).setRollback(false);
		Units.assertUnitCanNotBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}
	
	@Test
	/**
	 * Execution of strict SequentialUnit should return false if not all
	 * sub-units can be applied. With rollback, the changes of applied
	 * sub-units should be reverted.
	 */
	public void testStrictSequentialUnitWithRollbackFails() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(true);
		((SequentialUnitImpl)htUnit).setRollback(true);
		Units.assertUnitCanNotBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(2, nodeNumber);
	}
	
	@Test
	/**
	 * Execution of strict SequentialUnit should return true if all
	 * sub-units can be applied.
	 */
	public void testStrictSequentialUnitSucceeds() {
		loadEGraph("graphWithTwoNodesAndOneVal");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(true);
		Units.assertUnitCanBeExecuted(htUnitApp);
	}
	
	@Test
	/**
	 * Execution of non-strict SequentialUnit should return false if no sub-unit
	 * can be applied.
	 */
	public void testNonstrictSequentialUnitFails() {
		loadEGraph("graphWithCont");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(false);
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}
	
	@Test
	/**
	 * Execution of non-strict SequentialUnit should return true if at least
	 * one sub-unit can be applied, even if not all sub-units can be applied.
	 * Using rollback should not have an effect on applied iterations.
	 */
	public void testNonstrictSequentialUnitSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(false);
		((SequentialUnitImpl)htUnit).setRollback(true);
		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}
	
	@Test
	/**
	 * Execution of strict IteratedUnit should return false if not all
	 * iterations can be applied. Without rollback, the changes of applied
	 * iterations should remain.
	 */
	public void testStrictIteratedUnitWithoutRollbackFails() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("iteratedUnitNodeDeletionTest");
		((IteratedUnitImpl)htUnit).setStrict(true);
		((IteratedUnitImpl)htUnit).setRollback(false);
		Units.assertUnitCanNotBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(0, nodeNumber);
	}
	
	@Test
	/**
	 * Execution of strict IteratedUnit should return false if not all
	 * iterations can be applied. With rollback, the changes of applied
	 * iterations should be reverted.
	 */
	public void testStrictIteratedUnitWithRollbackFails() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("iteratedUnitNodeDeletionTest");
		((IteratedUnitImpl)htUnit).setStrict(true);
		((IteratedUnitImpl)htUnit).setRollback(true);
		Units.assertUnitCanNotBeExecuted(htUnitApp);		
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(2, nodeNumber);
	}
	
	@Test
	/**
	 * Execution of strict IteratedUnit should return true if all iterations
	 * can be applied.
	 */
	public void testStrictIteratedUnitSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("iteratedUnitNodeDeletionTest");
		((IteratedUnitImpl)htUnit).setIterations("2");
		((IteratedUnitImpl)htUnit).setStrict(true);
		((IteratedUnitImpl)htUnit).setRollback(false);
		Units.assertUnitCanBeExecuted(htUnitApp);
	}
	
	@Test
	/**
	 * Execution of non-strict IteratedUnit should return false if no iteration
	 * can be applied.
	 */
	public void testNonstrictIteratedUnitFails() {
		loadEGraph("graphWithCont");
		loadUnit("iteratedUnitNodeDeletionTest");
		((IteratedUnitImpl)htUnit).setStrict(false);
		Units.assertUnitCanNotBeExecuted(htUnitApp);		
	}
	
	@Test
	/**
	 * Execution of non-strict IteratedUnit should return true if at least
	 * one iteration can be applied, even if not all iterations can be applied.
	 * Using rollback should not have an effect on applied iterations.
	 */
	public void testNonstrictIteratedUnitSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("iteratedUnitNodeDeletionTest");
		((IteratedUnitImpl)htUnit).setStrict(false);
		((IteratedUnitImpl)htUnit).setRollback(true);
		Units.assertUnitCanBeExecuted(htUnitApp);	
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(0, nodeNumber);
	}
	
	

}