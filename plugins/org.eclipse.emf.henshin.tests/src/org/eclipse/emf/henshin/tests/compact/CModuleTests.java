package org.eclipse.emf.henshin.tests.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.compact.CRule;
import org.eclipse.emf.henshin.model.compact.CUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Before;
import org.junit.Test;

public class CModuleTests {
	
	static String path;
	
	static EPackage pack;
	static HenshinResourceSet res;
	static File moduleFile, moduleFile2;
	static Rule r;
	static Unit u;
	static Module m;
	
	CModule mod;
	
	@Before
	public void globalSetup() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		res = new HenshinResourceSet(path);
		pack = res.registerDynamicEPackages("bank.ecore").get(0);
		
		moduleFile = new File("module.henshin");
		if(moduleFile.exists()) {
			moduleFile.delete();
		}
		
		moduleFile2 = new File(path+"test.henshin");
		if(moduleFile2.exists()) {
			moduleFile2.delete();
		}
		
		r = HenshinFactory.eINSTANCE.createRule("rule");
		
		m = res.getModule("bank.henshin");
		
		u = HenshinFactory.eINSTANCE.createLoopUnit();
		u.setName("unit");
	}
	
	@Before
	public void localSetup() {
		mod = new CModule("module");
	}
	

	@Test
	public void createModuleTest() {
		mod = new CModule("module");
		assertNotNull(mod);
		assertEquals(mod.getModule().getName(),"module");
	}
	
	@Test
	public void addImportTest() {
		assertTrue(mod.getModule().getImports().isEmpty());
		mod.addImport(pack);
		assertEquals(mod.getModule().getImports().get(0),pack);	
		}
	
	@Test
	public void addImportFromFile() {
		assertTrue(mod.getModule().getImports().isEmpty());
		mod.addImportsFromFile(path+"bank.ecore");
		assertTrue(!mod.getModule().getImports().isEmpty());
	}
	
	@Test
	public void reuseModuleTest() {
		mod = new CModule(m);
		assertEquals(mod.getModule(),m);
	}
	
	@Test
	public void loadModuleTest() {
		mod = CModule.loadFromFile(path+"bank.henshin");
		//.equals is not implemented for Module-Class. .toString is. Therefore the Strings are compared rather than the Modules themselves.
		assertEquals(mod.getModule().toString(),m.toString());
	}
	
	@Test
	public void saveTestWithName() {
		mod = new CModule(m);
		assertTrue(!moduleFile2.exists());
		mod.save(path+"test");
		assertTrue(moduleFile2.exists());
	}
	
	@Test
	public void saveTestWithoutName() {
		mod = new CModule(m);
		mod.getModule().setName("module");
		assertTrue(!moduleFile.exists());
		mod.save();
		assertTrue(moduleFile.exists());
	}
	
	@Test
	public void addRuleTest() {
		CRule c = mod.addRule(r);
		assertTrue(!mod.getModule().getUnits().isEmpty());
		assertNotNull(c);
		assertNotNull(mod.getModule().getUnit("rule"));
		assertNotNull(c.getUnit());
	}
	
	@Test
	public void addUnitTest() {
		CUnit cu = mod.addUnit(u);
		assertTrue(mod.getModule().getUnits().contains(u));
		assertNotNull(cu);
		assertNotNull(cu.getUnit());
		assertNotNull(mod.getModule().getUnit("unit"));
	}
	
	@Test
	public void ruleCreationTest() {
		CRule cr = mod.createRule("rule");
		assertNotNull(cr);
		assertNotNull(cr.getUnit());
		assertTrue(mod.getModule().getUnits().contains(cr.getUnit()));
	}
	
	@Test
	public void loopUnitCreationTest() {
		CRule cr = mod.addRule(r);
		mod.createLoop(cr);
		assertTrue(mod.getModule().getUnits().size()==2);
		assertNotNull(mod.getModule().getUnit("rule-Loop"));
		
	}
	
	@Test
	public void iteratedUnitCreationTest() {
		CRule cr = mod.addRule(r);
		mod.createIteration(cr,"2");
		assertTrue(mod.getModule().getUnits().size()==2);
		assertNotNull(mod.getModule().getUnit("rule-Iteration"));
		assertEquals(((IteratedUnit)mod.getModule().getUnit("rule-Iteration")).getIterations(),"2");
	}
	
	@Test
	public void conditionalUnitCreationTestWithElse() {
		CRule cr = mod.addRule(r);
		mod.createConditional(cr,cr,cr,"conditional");
		assertTrue(mod.getModule().getUnits().size()==2);
		ConditionalUnit cu = (ConditionalUnit) mod.getModule().getUnit("conditional");
		assertNotNull(cu);
		assertEquals(cu.getIf(),r);
		assertEquals(cu.getThen(),r);
		assertEquals(cu.getElse(),r);
	}
	
	@Test
	public void conditionalUnitCreationTestWithoutElse() {
		CRule cr = mod.addRule(r);
		mod.createConditional(cr,cr,null,"conditional");
		assertTrue(mod.getModule().getUnits().size()==2);
		ConditionalUnit cu = (ConditionalUnit) mod.getModule().getUnit("conditional");
		assertNotNull(cu);
		assertEquals(cu.getIf(),r);
		assertEquals(cu.getThen(),r);
		assertNull(cu.getElse());
	}
	
	@Test
	public void priorityUnitCreationTest() {
		CRule cr = mod.addRule(r);
		assertNull(mod.getModule().getUnit("priority"));
		mod.addToPriority(cr, "priority");
		PriorityUnit pu = (PriorityUnit) mod.getModule().getUnit("priority");
		assertNotNull(pu);
		assertTrue(pu.getSubUnits().contains(cr.getUnit()));
	}
	
	@Test
	public void priorityUnitAddTest() {
		CRule cr = mod.addRule(r);
		mod.addToPriority(cr, "priority");
		assertNotNull(mod.getModule().getUnit("priority"));
		assertEquals(mod.getModule().getUnit("priority").getSubUnits(false).size(),1);
		mod.addToPriority(cr, "priority");
		assertEquals(mod.getModule().getUnit("priority").getSubUnits(false).size(),2);
	}
	
	@Test
	public void sequentialUnitCreationTest() {
		CRule cr = mod.addRule(r);
		assertNull(mod.getModule().getUnit("sequence"));
		mod.addToSequence(cr, "sequence");
		SequentialUnit su = (SequentialUnit) mod.getModule().getUnit("sequence");
		assertNotNull(su);
		assertTrue(su.getSubUnits().contains(cr.getUnit()));
	}
	
	@Test
	public void sequentialUnitAddTest() {
		CRule cr = mod.addRule(r);
		mod.addToSequence(cr, "sequence");
		assertNotNull(mod.getModule().getUnit("sequence"));
		assertEquals(mod.getModule().getUnit("sequence").getSubUnits(false).size(),1);
		mod.addToSequence(cr, "sequence");
		assertEquals(mod.getModule().getUnit("sequence").getSubUnits(false).size(),2);
	}
	
	@Test
	public void independentUnitCreationTest() {
		CRule cr = mod.addRule(r);
		assertNull(mod.getModule().getUnit("independent"));
		mod.addToIndependent(cr, "independent");
		IndependentUnit iu = (IndependentUnit) mod.getModule().getUnit("independent");
		assertNotNull(iu);
		assertTrue(iu.getSubUnits().contains(cr.getUnit()));
	}
	
	@Test
	public void independentUnitAddTest() {
		CRule cr = mod.addRule(r);
		mod.addToIndependent(cr, "independent");
		assertNotNull(mod.getModule().getUnit("independent"));
		assertEquals(mod.getModule().getUnit("independent").getSubUnits(false).size(),1);
		mod.addToIndependent(cr, "independent");
		assertEquals(mod.getModule().getUnit("independent").getSubUnits(false).size(),2);
	}
	
	
	@Test
	public void getAllCUnitsTest() {
		mod = new CModule(m);
		List<CUnit> list = mod.getAllCUnits();
		assertEquals(list.size(),m.getUnits().size());
		for(CUnit cu: list)
		{
			assertTrue(m.getUnits().contains(cu.getUnit()));
		}
	}
}
