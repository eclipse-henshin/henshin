package org.eclipse.emf.henshin.tests.compact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.compact.CNode;
import org.eclipse.emf.henshin.model.compact.CRule;
import org.eclipse.emf.henshin.model.compact.CUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CRuleTests {
	
	static EClass account;
	static String path;
	static EPackage pack;
	CModule mod;
	CRule rule;
	
	@BeforeClass
	public static void globalSetUp() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		HenshinResourceSet hrs = new HenshinResourceSet(path);
		pack = hrs.registerDynamicEPackages("bank.ecore").get(0);
		account = (EClass) pack.getEClassifier("Account");
	}
	
	@Before
	public void localSetUp() {
		mod = new CModule("module");
		mod.addImport(pack);
		rule = mod.createRule("rule");
	}

	@Test
	public void createNodeTest() {
		CNode node = rule.createNode(account, new Action(Action.Type.PRESERVE));
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	public void createNodeTestClassStringTest() {
		CNode node = rule.createNode("Account", new Action(Action.Type.PRESERVE));
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	public void createNodeTestActionStringTest() {
		CNode node = rule.createNode(account, "preserve");
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	public void createNodeAllStrings() {
		CNode node = rule.createNode("Account", "preserve");
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	public void createNodeDefaultActionTest() {
		CNode node = rule.createNode(account);
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	public void createNodeDefaultActionClassStringTest() {
		CNode node = rule.createNode("Account");
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test(expected = RuntimeException.class)
	public void createNodeWrongActionStringTest1() {
			rule.createNode(account, "cheesecake");
	}
	

	@Test(expected = RuntimeException.class)
	public void createNodeWrongActionStringTest2() {
			rule.createNode("Account", "cheesecake");
	}
	
	
	@Test
	public void createNodeInMultiRuleTest() {
		rule.createParameter("in", "param", EcorePackage.Literals.EINT);
		
		CNode node = rule.createNode(account, "delete*/myMultiRule");
		assertEquals(node.getNode().getAction().getPath()[0], "myMultiRule");
		//Assert that ParameterKinds get fixed when MultiRule is used!
		Rule multi = rule.getUnit().getMultiRule("myMultiRule");
		assertEquals(rule.getUnit().getParameter("param").getKind(), multi.getParameter("param").getKind());
	}
	
	@Test
	public void createNodeInNAC() {
		CNode node = rule.createNode(account, "forbid#myNAC");
		assertTrue(rule.getUnit().getLhs().getNAC("myNAC").getConclusion().getNodes().contains(node.getNode()));
	}
	
	@Test
	public void createNodeInPAC() {
		CNode node = rule.createNode(account, "require#myPAC");
		assertTrue(rule.getUnit().getLhs().getPAC("myPAC").getConclusion().getNodes().contains(node.getNode()));
	}
	
	@Test
	public void extractNACTest() {
		rule.createNode(account, "forbid#myNAC");
		Not n = rule.getNAC("myNAC");
		assertEquals(n, ((Not)rule.getUnit().getLhs().getFormula()));
	}
	
	@Test
	public void extractPACTest() {
		rule.createNode(account, "require#myPAC");
		NestedCondition n = rule.getPAC("myPAC");
		assertEquals(n, rule.getUnit().getLhs().getFormula());
	}
	
	@Test(expected = RuntimeException.class)
	public void extractNACNonExistentTest() {
			rule.getNAC("myNAC");
	}
	
	@Test(expected = RuntimeException.class)
	public void extractPACNonExistentTest() {
			rule.getPAC("myPAC");
	}
	
	@Test
	public void setFormulaTest() {
		Or or = HenshinFactory.eINSTANCE.createOr();
		rule.setPreConditionFormula(or);
		assertEquals(rule.getUnit().getLhs().getFormula(), or);
	}
	
	@Test
	public void createAttributeConditionTest() {
		rule.createParameter("in", "param", EcorePackage.Literals.EINT);
		rule.createAttributeCondition("cond", "param=100");
		AttributeCondition cond = rule.getUnit().getAttributeConditions().get(0);
		assertEquals(cond.getConditionText(), "param=100");
		assertEquals(cond.getName(), "cond");
	}
	
	@Test
	public void setUnitTest() {
		CRule newRule = mod.createRule("newRule");
		newRule.setUnit(rule.getUnit());
		assertEquals(newRule.getUnit(), rule.getUnit());
	}
	
	@Test(expected = RuntimeException.class)
	public void setUnitTestNotARule() {
		CUnit unit = mod.createLoop(rule);
			rule.setUnit(unit.getUnit());
	}
}
