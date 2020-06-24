package org.eclipse.emf.henshin.tests.compact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CRuleTests {
	
	static EClass account;
	static String path;
	static EPackage pack;
	CModule mod;
	CRule rule;
	
	@BeforeAll
	public static void globalSetUp() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		HenshinResourceSet hrs = new HenshinResourceSet(path);
		pack = hrs.registerDynamicEPackages("bank.ecore").get(0);
		account = (EClass) pack.getEClassifier("Account");
	}
	
	@BeforeEach
	public void localSetUp() {
		mod = new CModule("module");
		mod.addImport(pack);
		rule = mod.createRule("rule");
	}

	@Test
	void createNodeTest() {
		CNode node = rule.createNode(account, new Action(Action.Type.PRESERVE));
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	void createNodeTestClassStringTest() {
		CNode node = rule.createNode("Account", new Action(Action.Type.PRESERVE));
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	void createNodeTestActionStringTest() {
		CNode node = rule.createNode(account, "preserve");
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	void createNodeAllStrings() {
		CNode node = rule.createNode("Account", "preserve");
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	void createNodeDefaultActionTest() {
		CNode node = rule.createNode(account);
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	void createNodeDefaultActionClassStringTest() {
		CNode node = rule.createNode("Account");
		assertTrue(!rule.getUnit().getLhs().getNodes().isEmpty());
		assertEquals(node.getNode().getType(),account);
		assertEquals(node.getNode().getAction().getType(),Action.Type.PRESERVE);
		assertEquals(node.getNode().getGraph(), rule.getUnit().getLhs());
	}
	
	@Test
	void createNodeWrongActionStringTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			rule.createNode(account, "cheesecake");
			});
		assertEquals(e.getMessage(), "cheesecake is not a valid Action");
		e = assertThrows(RuntimeException.class,() -> {
			rule.createNode("Account", "cheesecake");
			});
		assertEquals(e.getMessage(), "cheesecake is not a valid Action");
	}
	
	@Test
	void createNodeInMultiRuleTest() {
		rule.createParameter("in", "param", EcorePackage.Literals.EINT);
		
		CNode node = rule.createNode(account, "delete*/myMultiRule");
		assertEquals(node.getNode().getAction().getPath()[0], "myMultiRule");
		//Assert that ParameterKinds get fixed when MultiRule is used!
		Rule multi = rule.getUnit().getMultiRule("myMultiRule");
		assertEquals(rule.getUnit().getParameter("param").getKind(), multi.getParameter("param").getKind());
	}
	
	@Test
	void createNodeInNAC() {
		CNode node = rule.createNode(account, "forbid#myNAC");
		assertTrue(rule.getUnit().getLhs().getNAC("myNAC").getConclusion().getNodes().contains(node.getNode()));
	}
	
	@Test
	void createNodeInPAC() {
		CNode node = rule.createNode(account, "require#myPAC");
		assertTrue(rule.getUnit().getLhs().getPAC("myPAC").getConclusion().getNodes().contains(node.getNode()));
	}
	
	@Test
	void extractNACTest() {
		rule.createNode(account, "forbid#myNAC");
		Not n = rule.getNAC("myNAC");
		assertEquals(n, ((Not)rule.getUnit().getLhs().getFormula()));
	}
	
	@Test
	void extractPACTest() {
		rule.createNode(account, "require#myPAC");
		NestedCondition n = rule.getPAC("myPAC");
		assertEquals(n, rule.getUnit().getLhs().getFormula());
	}
	
	@Test
	void extractNACNonExistentTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			rule.getNAC("myNAC");
			});
		assertEquals(e.getMessage(), "Could not find any Condition named: myNAC");
	}
	
	@Test
	void extractPACNonExistentTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			rule.getPAC("myPAC");
			});
		assertEquals(e.getMessage(), "Could not find any Condition named: myPAC");
	}
	
	@Test
	void setFormulaTest() {
		Or or = HenshinFactory.eINSTANCE.createOr();
		rule.setPreConditionFormula(or);
		assertEquals(rule.getUnit().getLhs().getFormula(), or);
	}
	
	@Test
	void createAttributeConditionTest() {
		rule.createParameter("in", "param", EcorePackage.Literals.EINT);
		rule.createAttributeCondition("cond", "param=100");
		AttributeCondition cond = rule.getUnit().getAttributeConditions().get(0);
		assertEquals(cond.getConditionText(), "param=100");
		assertEquals(cond.getName(), "cond");
	}
	
	@Test
	void setUnitTest() {
		CRule newRule = mod.createRule("newRule");
		newRule.setUnit(rule.getUnit());
		assertEquals(newRule.getUnit(), rule.getUnit());
	}
	
	@Test
	void setUnitTestNotARule() {
		CUnit unit = mod.createLoop(rule);
		Exception e = assertThrows(RuntimeException.class,() -> {
			rule.setUnit(unit.getUnit());
			});
		assertEquals(e.getMessage(), "Given Unit is not a Rule!");
	}
}
