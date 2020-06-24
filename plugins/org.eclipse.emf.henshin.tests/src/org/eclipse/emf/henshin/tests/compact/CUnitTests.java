package org.eclipse.emf.henshin.tests.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.compact.CRule;
import org.eclipse.emf.henshin.model.compact.CUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CUnitTests {

	static EClassifier account;
	static String path;
	static EPackage pack;
	CModule mod, bank;
	CUnit unit;
	CRule rule;

	@BeforeClass
	public static void globalSetup() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		HenshinResourceSet hrs = new HenshinResourceSet(path);
		pack = hrs.registerDynamicEPackages("bank.ecore").get(0);
		account = pack.getEClassifier("Account");

	}

	@Before
	public void localSetup() {
		mod = new CModule("module");
		rule = mod.createRule("rule");
		unit = mod.createLoop(rule);
	}

	@Test
	public void createParameterTest() {
		unit.createParameter(ParameterKind.IN, "param", EcorePackage.Literals.EINT);
		Parameter par = unit.getUnit().getParameter("param");
		assertNotNull(par);
		assertEquals(par.getKind(), ParameterKind.IN);
		assertEquals(par.getName(), "param");
		assertEquals(par.getType(), EcorePackage.Literals.EINT);
	}

	@Test
	public void createParameterWithStringKind() {
		unit.createParameter("in", "param", EcorePackage.Literals.EINT);
		Parameter par = unit.getUnit().getParameter("param");
		assertNotNull(par);
		assertEquals(par.getKind(), ParameterKind.IN);
		assertEquals(par.getName(), "param");
		assertEquals(par.getType(), EcorePackage.Literals.EINT);
	}

	@Test(expected = RuntimeException.class)
	public void createParameterWithWrongStringKind() {
		unit.createParameter("cheesecake", "param", EcorePackage.Literals.EINT);
	}

	@Test
	public void createParameterWithClassifierString() {
		mod.addImport(pack);
		rule.createParameter(ParameterKind.IN, "param", "Account");
		Parameter par = rule.getUnit().getParameter("param");
		assertEquals(par.getType(), account);
	}

	@Test(expected = RuntimeException.class)
	public void createParameterWithWrongClassifierString() {
		unit.createParameter(ParameterKind.IN, "param", "cheesecake");
	}

	@Test
	public void createParameterAllStrings() {
		mod.addImport(pack);
		rule.createParameter("inout", "param", "Account");
		Parameter par = rule.getUnit().getParameter("param");
		assertNotNull(par);
		assertEquals(par.getKind(), ParameterKind.INOUT);
		assertEquals(par.getType(), account);
		assertEquals(par.getName(), "param");
	}

	@Test
	public void mapParameterTest() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.EINT);
		assertTrue(unit.getUnit().getParameterMappings().isEmpty());
		unit.mapParameterToSubunit("param1", "rule", "param2");
		assertTrue(!unit.getUnit().getParameterMappings().isEmpty());
		EList<ParameterMapping> list = unit.getUnit().getParameterMappings();
		ParameterMapping map = list.get(0);
		assertEquals(map.getSource(), unit.getUnit().getParameter("param1"));
		assertEquals(map.getTarget(), rule.getUnit().getParameter("param2"));
	}

	@Test(expected = RuntimeException.class)
	public void mapParameterWrongUnitTest() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.EINT);
		unit.mapParameterToSubunit("param1", "cheesecake", "param2");
	}

	@Test(expected = RuntimeException.class)
	public void mapParameterWrongParamTest1() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.EINT);
		unit.mapParameterToSubunit("cheesecake", "rule", "param2");
		;
	}

	@Test(expected = RuntimeException.class)
	public void mapParameterWrongParamTest2() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.EINT);
		unit.mapParameterToSubunit("param1", "rule", "cheesecake");
	}

	@Test(expected = RuntimeException.class)
	public void mapParameterTypesNotEqualTest() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.ESTRING);
		unit.mapParameterToSubunit("param1", "rule", "param2");
	}

	@Test
	public void setUnitTest() {
		unit.setUnit(rule.getUnit());
		assertEquals(unit.getUnit(), rule.getUnit());
	}
}
