package org.eclipse.emf.henshin.tests.basic;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.henshin.tests.framework.HenshinTest;
import org.eclipse.emf.henshin.tests.testmodel.SpecialVal;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.eclipse.emf.henshin.tests.testmodel.Val;
import org.eclipse.emf.henshin.tests.testmodel.cont;
import org.junit.Before;
import org.junit.Test;

public class AttributeRestrictionsTest extends HenshinTest {

	@Before
	public void setUp() {
		TestmodelPackage.eINSTANCE.eClass();
		init("basic/rules/basicTests.henshin");
		setEGraphPath("basic/models/attributeTestsModels/", "testmodel");
		loadEGraph("restrictedAttributes");
	}
	
	@Test
	public void testSetStandardAttribute() {
		loadRule("setStandardAttributeValue");
		htRuleApp.execute(null);
		cont root = (cont)htEGraph.getRoots().get(0);
		Val val = root.getContainsVal().get(0);
		assertEquals(99,val.getIntvl());		
	}
	
	@Test(expected=IllegalStateException.class)
	public void testSetDerivedAttribute() {
		loadRule("setDerivedAttributeValue");
		htRuleApp.execute(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testSetUnchangeableAttribute() {
		loadRule("setUnchangeableAttributeValue");
		htRuleApp.execute(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testUnsetStandardAttribute() {
		loadRule("unsetStandardAttribute");
		htRuleApp.execute(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testUnsetUnsetableUnchangeableAttribute() {
		loadRule("unsetUnsetableUnchangeableAttribute");
		htRuleApp.execute(null);
	}
	
	@Test
	public void testUnsetUnsetableAttribute() {
		loadRule("unsetUnsetableAttribute");
		htRuleApp.execute(null);
		cont root = (cont)htEGraph.getRoots().get(0);
		SpecialVal val = (SpecialVal) root.getContainsVal().get(0);
		assertEquals(42, val.getUnsetablevl());
	}
}
