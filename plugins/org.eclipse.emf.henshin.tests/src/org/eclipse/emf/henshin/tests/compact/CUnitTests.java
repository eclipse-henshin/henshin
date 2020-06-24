package org.eclipse.emf.henshin.tests.compact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CUnitTests {
	
	static EClassifier account;
	static String path;
	static EPackage pack;
	CModule mod,bank;
	CUnit unit;
	CRule rule;

	@BeforeAll
	public static void globalSetup() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		HenshinResourceSet hrs = new HenshinResourceSet(path);
		pack = hrs.registerDynamicEPackages("bank.ecore").get(0);
		account = pack.getEClassifier("Account");
		
	}
	
	@BeforeEach
	public void localSetup() {
		mod = new CModule("module");
		rule = mod.createRule("rule");
		unit = mod.createLoop(rule);
	}

	@Test
	void createParameterTest() {
		unit.createParameter(ParameterKind.IN, "param", EcorePackage.Literals.EINT);
		Parameter par = unit.getUnit().getParameter("param");
		assertNotNull(par);
		assertEquals(par.getKind(),ParameterKind.IN);
		assertEquals(par.getName(),"param");
		assertEquals(par.getType(), EcorePackage.Literals.EINT);
	}
	
	@Test
	void createParameterWithStringKind() {
		unit.createParameter("in", "param", EcorePackage.Literals.EINT);
		Parameter par = unit.getUnit().getParameter("param");
		assertNotNull(par);
		assertEquals(par.getKind(),ParameterKind.IN);
		assertEquals(par.getName(),"param");
		assertEquals(par.getType(), EcorePackage.Literals.EINT);
	}
	
	@Test
	void createParameterWithWrongStringKind() {
		assertThrows(RuntimeException.class,() -> {
			unit.createParameter("cheesecake", "param", EcorePackage.Literals.EINT);
			});
	}
	
	@Test
	void createParameterWithClassifierString() {
		mod.addImport(pack);
		rule.createParameter(ParameterKind.IN, "param", "Account");
		Parameter par = rule.getUnit().getParameter("param");
		assertEquals(par.getType(),account);
	}
	
	@Test
	void createParameterWithWrongClassifierString() {
		assertThrows(RuntimeException.class,() -> {
			unit.createParameter(ParameterKind.IN, "param", "cheesecake");
			});
	}
	
	@Test
	void createParameterAllStrings() {
		mod.addImport(pack);
		rule.createParameter("inout", "param", "Account");
		Parameter par = rule.getUnit().getParameter("param");
		assertNotNull(par);
		assertEquals(par.getKind(), ParameterKind.INOUT);
		assertEquals(par.getType(), account);
		assertEquals(par.getName(), "param");
	}
	
	@Test
	void mapParameterTest() {
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
	
	@Test
	void mapParameterWrongUnitTest() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.EINT);
		Exception e = assertThrows(RuntimeException.class,() -> {
			unit.mapParameterToSubunit("param1", "cheesecake", "param2");
			});
		assertEquals("Unit: cheesecake not found", e.getMessage());
	}
	
	@Test
	void mapParameterWrongParamTest() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.EINT);
		Exception e = assertThrows(RuntimeException.class,() -> {
			unit.mapParameterToSubunit("cheesecake", "rule", "param2");
			});
		assertEquals(e.getMessage(), "Parameter: cheesecake not found");
		e = assertThrows(RuntimeException.class,() -> {
			unit.mapParameterToSubunit("param1", "rule", "cheesecake");
			});
		assertEquals(e.getMessage(), "Parameter: cheesecake in rule not found");
	}
	
	@Test
	void mapParameterTypesNotEqualTest() {
		unit.createParameter("out", "param1", EcorePackage.Literals.EINT);
		rule.createParameter("in", "param2", EcorePackage.Literals.ESTRING);
		Exception e = assertThrows(RuntimeException.class,() -> {
			unit.mapParameterToSubunit("param1", "rule", "param2");
			});
		assertEquals(e.getMessage(), "Parameters do not have equal Types");
	}
	
	@Test
	void setUnitTest() {
		unit.setUnit(rule.getUnit());
		assertEquals(unit.getUnit(), rule.getUnit());
	}
}
