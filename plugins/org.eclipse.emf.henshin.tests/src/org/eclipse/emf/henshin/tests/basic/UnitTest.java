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
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests execution of units.
 *
 * The scheme of the tests is documented in "UnitTest-scheme.html".
 *
 * @author Felix Rieger
 * @author Stefan Jurack
 * @author Christian Krause
 * @author Benjamin Heidelmeier
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
	/**
	 * Unit: Independent
	 * Flags: -
	 * Case: Unit empty
	 */
	public void testEmptyIndependentUnit() {
		loadEGraph("graphBefore_loopUnit");
		loadUnit("emptyIndependentUnit");
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	/**
	 * Unit: Priority
	 * Flags: -
	 * Case: Unit empty
	 */
	public void testEmptyPriorityUnit() {
		loadEGraph("graphBefore_loopUnit");
		loadUnit("emptyPriorityUnit");
		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	/**
	 * Unit: Sequential
	 * Flags: strict=true
	 * Case: Unit empty
	 */
	public void testEmptyStrictSequentialUnit() {
		loadEGraph("graphBefore_loopUnit");
		loadUnit("emptySequentialUnit");
		Units.assertUnitCanBeExecuted(htUnitApp);
	}

	@Test
	/**
	 * Unit: Sequential
	 * Flags: strict=false
	 * Case: Unit empty
	 */
	public void testEmptyNonstrictSequentialUnit() {
		loadEGraph("graphBefore_loopUnit");
		loadUnit("emptySequentialUnit");
		((SequentialUnitImpl)htUnit).setStrict(false);
		Units.assertUnitCanBeExecuted(htUnitApp);
	}
	
	@Test
	/**
	 * Execution of strict SequentialUnit should return false if not all
	 * sub-units can be applied. Without rollback, the changes of applied
	 * iterations should remain.
	 *
	 * Unit: Sequential
	 * Flags: strict=true, rollback=false
	 * Case: Unit fails
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
	 *
	 * Unit: Sequential
	 * Flags: strict=true, rollback=true
	 * Case: Unit fails
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
	 *
	 * Unit: Sequential
	 * Flags: strict=true, rollback=true
	 * Case: Unit succeeds
	 */
	public void testStrictSequentialUnitSucceeds() {
		loadEGraph("graphWithTwoNodesAndOneVal");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(true);
		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(0, nodeNumber);
		int valNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.VAL, true);
		assertEquals(0, valNumber);
	}
	
	@Test
	/**
	 * Execution of non-strict SequentialUnit should return false if no sub-unit
	 * can be applied.
	 *
	 * Unit: Sequential
	 * Flags: strict=false, rollback=true
	 * Case: Unit fails
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
	 *
	 * Unit: Sequential
	 * Flags: strict=false, rollback=true
	 * Case: Unit succeeds
	 */
	public void testNonstrictSequentialUnitSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("sequentialUnitNodeAndValDeletionTest");
		((SequentialUnitImpl)htUnit).setStrict(false);
		((SequentialUnitImpl)htUnit).setRollback(true);
		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(0, nodeNumber);
	}
	
	@Test
	/**
	 * Execution of strict IteratedUnit should return false if not all
	 * iterations can be applied. Without rollback, the changes of applied
	 * iterations should remain.
	 *
	 * Unit: Iterated
	 * Flags: strict=true, rollback=false
	 * Case: Unit fails
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
	 *
	 * Unit: Iterated
	 * Flags: strict=true, rollback=true
	 * Case: Unit fails
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
	 *
	 * Unit: Iterated
	 * Flags: strict=true, rollback=false, iterations=2
	 * Case: Unit succeeds
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
	 *
	 * Unit: Iterated
	 * Flags: strict=false, rollback=true
	 * Case: Unit fails
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
	 *
	 * Unit: Iterated
	 * Flags: strict=false, rollback=true
	 * Case: Unit succeeds
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

	@Test
	/**
	 * Execution of ConditionalUnit without 'else'-subUnit should return true
	 * if 'if'-subUnit fails.
	 *
	 * Unit: Conditional
	 * Flags: -
	 * Case: Unit empty
	 */
	public void testConditionalUnitWithoutElseIfFalseSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("conditionalUnitWithoutElse");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(2, nodeNumber);
	}

	@Test
	/**
	 * Execution of ConditionalUnit should return true if 'if'-subUnit fails
	 * and 'else'-subUnit succeeds.
	 *
	 * Unit: Conditional
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testConditionalUnitElseSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("conditionalUnitTest2");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}

	@Test
	/**
	 * Execution of ConditionalUnit should return false if 'if'-subUnit fails
	 * and 'else'-subUnit fails.
	 *
	 * Unit: Conditional
	 * Flags: -
	 * Case: Unit fails
	 */
	public void testConditionalUnitElseFails() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("conditionalUnitTest1");

		Units.assertUnitCanNotBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(2, nodeNumber);
	}

	@Test
	/**
	 * Execution of ConditionalUnit should return true if 'if'-subUnit succeeds
	 * and 'then'-subUnit succeeds.
	 *
	 * Unit: Conditional
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testConditionalUnitThenSucceeds() {
		loadEGraph("graphWithTwoNodesAndOneVal");
		loadUnit("conditionalUnitTest1");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}

	@Test
	/**
	 * Execution of ConditionalUnit should return false if 'if'-subUnit succeeds
	 * and 'then'-subUnit fails.
	 *
	 * Unit: Conditional
	 * Flags: -
	 * Case: Unit fails
	 */
	public void testConditionalUnitThenFails() {
		loadEGraph("graphWithTwoNodesAndOneVal");
		loadUnit("conditionalUnitTest2");

		Units.assertUnitCanNotBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(2, nodeNumber);
	}

	@Test
	/**
	 * Execution of PriorityUnit should return false if all subUnits fail.
	 *
	 * Unit: Priority
	 * Flags: -
	 * Case: Unit fails
	 */
	public void testPriorityUnitAllfail() {
		loadEGraph("graphWithCont");
		loadUnit("priorityUnitTest");

		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	/**
	 * Execution of PriorityUnit should return true if last subUnit succeeds.
	 *
	 * Unit: Priority
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testPriorityUnitLastSucceeds() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("priorityUnitTest");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}

	@Test
	/**
	 * Execution of PriorityUnit should return true if subUnit in the middle succeeds.
	 *
	 * Unit: Priority
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testPriorityUnitMiddleSucceeds() {
		loadEGraph("graphWithTwoNodesAndOneVal");
		loadUnit("priorityUnitTest");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(2, nodeNumber);
		int valNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.VAL, true);
		assertEquals(0, valNumber);
	}

	@Test
	/**
	 * Execution of PriorityUnit should return true if first subUnit succeeds.
	 *
	 * Unit: Priority
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testPriorityUnitFirstSucceeds() {
		loadEGraph("graphBefore_priorityUnit");
		loadUnit("priorityUnitTest");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(4, nodeNumber);
		int valNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.VAL, true);
		assertEquals(2, valNumber);
	}

	@Test
	/**
	 * Execution of IndependentUnit should return false if all subUnits fail.
	 *
	 * Unit: Independent
	 * Flags: -
	 * Case: Unit fails
	 */
	public void testIndependentUnitAllfail() {
		loadEGraph("graphWithCont");
		loadUnit("independentUnitTest");

		Units.assertUnitCanNotBeExecuted(htUnitApp);
	}

	@Test
	/**
	 * Execution of IndependentUnit should return true if one of three subUnits can succeed.
	 *
	 * Unit: Independent
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testIndependentUnitOneCanSucceed() {
		loadEGraph("graphWithTwoNodes");
		loadUnit("independentUnitTest");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}

	@Test
	/**
	 * Execution of IndependentUnit should return true if two of three subUnits can succeed.
	 *
	 * Unit: Independent
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testIndependentUnitTwoCanSucceed() {
		loadEGraph("graphWithTwoNodesAndOneVal");
		loadUnit("independentUnitTest");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		int valNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.VAL, true);
		assertTrue(nodeNumber == 2 && valNumber == 0 || nodeNumber == 1 && valNumber == 1);
	}

	@Test
	/**
	 * Execution of LoopUnit should return true if no execution of the subUnit is possible.
	 *
	 * Unit: Loop
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testLoopUnitNoLoopSucceeds() {
		loadEGraph("graphWithCont");
		loadUnit("loopUnitTest");
		Units.assertUnitCanBeExecuted(htUnitApp);
	}

	@Test
	/**
	 * Execution of LoopUnit should return true if multiple executions of the subUnit are possible.
	 *
	 * Unit: Loop
	 * Flags: -
	 * Case: Unit succeeds
	 */
	public void testLoopUnitMultipleLoopsSucceeds() {
		loadEGraph("graphBefore_loopUnit");
		loadUnit("loopUnitTest");

		Units.assertUnitCanBeExecuted(htUnitApp);
		int nodeNumber = htEGraph.getDomainSize(TestmodelPackage.Literals.NODE, true);
		assertEquals(1, nodeNumber);
	}

}