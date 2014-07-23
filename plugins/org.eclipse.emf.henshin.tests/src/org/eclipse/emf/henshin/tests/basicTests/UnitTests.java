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
package org.eclipse.emf.henshin.tests.basicTests;

import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.framework.Units;
import org.junit.Before;
import org.junit.Test;

/**
 * tests execution of transformationUnits
 * @author Felix Rieger
 * @author Stefan Jurack (sjurack)
 *
 */
public class UnitTests extends HenshinTest {

	@Before
	public void setUp() throws Exception {
		init("basicTestRules/unitTests.henshin");
		setModelGraphProperties("basicTestModels/unitTestsModels/", "testmodel");
	}
	
	
	@Test
	public void testAmalgamationUnitAttributeInKernelRuleNac() {
		loadGraph("graphBefore_amu_attributeInKernelNac");
		//loadRule("amu2_krl");
		//Tools.printMatches(htRuleApp.findAllMatches());
		loadTu("loop_amu_attributeInKernelNac");
		Units.assertTuCanBeExecuted(htUnitApp);
		//Tools.persist(Tools.getGraphRoot(htEmfGraph), "gt_amu2.testmodel");
	}
	
	@Test
	public void testEmptyIndependentUnit() {
		loadGraph("graphBefore_countedUnit");
		loadTu("emptyIndependentUnit");
		Units.assertTuCanNotBeExecuted(htUnitApp);
		//htUnitApp.undo(null);
		//GraphTransformations.assertGraphIsNotChanged(htUnitApp, 0.9);
	}
	
	@Test
	public void testEmptyPriorityUnit() {
		loadGraph("graphBefore_countedUnit");
		loadTu("emptyPriorityUnit");
		Units.assertTuCanNotBeExecuted(htUnitApp);
		//htUnitApp.undo(null);
		//GraphTransformations.assertGraphIsNotChanged(htUnitApp, 0.9);
	}
	

	
}