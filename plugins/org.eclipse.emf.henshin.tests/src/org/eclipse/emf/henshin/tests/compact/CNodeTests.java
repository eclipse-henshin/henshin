package org.eclipse.emf.henshin.tests.compact;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.compact.CNode;
import org.eclipse.emf.henshin.model.compact.CRule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CNodeTests {

	static EClass account, bank;
	static EReference ref;
	static EAttribute att;
	static String path;
	static EPackage pack;

	CRule rule;
	CModule mod;
	CNode node1, node2;

	@BeforeClass
	public static void globalSetUp() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		HenshinResourceSet hrs = new HenshinResourceSet(path);
		pack = hrs.registerDynamicEPackages("bank.ecore").get(0);
		account = (EClass) pack.getEClassifier("Account");
		bank = (EClass) pack.getEClassifier("Bank");
		ref = bank.getEReferences().get(1);// accounts
		att = account.getEAllAttributes().get(1);// credit
	}

	@Before
	public void localSetUp() {
		mod = new CModule("module");
		mod.addImport(pack);
		rule = mod.createRule("rule");
		node1 = rule.createNode(bank);
		node2 = rule.createNode(account);
	}

	@Test
	public void canCreateEdgeTest() {
		assertEquals(node1.canCreateEdge(node2, ref),
				rule.getUnit().canCreateEdge(node1.getNode(), node2.getNode(), ref));
	}

	@Test
	public void canCreateEdgeRefStringTest() {
		assertEquals(node1.canCreateEdge(node2, "accounts"),
				rule.getUnit().canCreateEdge(node1.getNode(), node2.getNode(), ref));
	}

	@Test(expected = RuntimeException.class)
	public void canCreateEdgeWrongRefTest() {
			node1.canCreateEdge(node2, "cheesecake");
	}

	@Test
	public void createEdgeTest() {
		node1.createEdge(node2, ref, new Action(Action.Type.PRESERVE));
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}

	@Test
	public void createEdgeDefaultActionTest() {
		node1.createEdge(node2, ref);
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}

	@Test
	public void createEdgeDefaultActionStringRefTest() {
		node1.createEdge(node2, "accounts");
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}

	@Test
	public void createEdgeStringRefTest() {
		node1.createEdge(node2, "accounts", new Action(Action.Type.PRESERVE));
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}

	@Test
	public void createEdgeStringActionTest() {
		node1.createEdge(node2, ref, "preserve");
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}

	@Test
	public void createEdgeAllStringsTest() {
		node1.createEdge(node2, "accounts", "preserve");
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}

	@Test(expected = RuntimeException.class)
	public void createEdgeWrongActionTest1() {
		node1.createEdge(node2, "accounts", "cheesecake");
	}

	@Test(expected = RuntimeException.class)
	public void createEdgeWrongActionTest2() {
		node1.createEdge(node2, ref, "cheesecake");

	}

	@Test(expected = RuntimeException.class)
	public void createEdgeFailTest() {
		node1.createEdge(node2, "clients");
	}

	@Test
	public void createAttributeTest() {
		node2.createAttribute(att, "1000", new Action(Action.Type.PRESERVE));
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}

	@Test
	public void createAttributeDefaultActionTest() {
		node2.createAttribute(att, "1000");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), node2.getNode().getAction().getType());
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}

	@Test
	public void createAttributeStringAttTest() {
		node2.createAttribute("credit", "1000", new Action(Action.Type.PRESERVE));
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}

	@Test
	public void createAttributeStringActionTest() {
		node2.createAttribute(att, "1000", "preserve");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}

	@Test
	public void createAttributeDefaultActionStringRefTest() {
		node2.createAttribute("credit", "1000");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), node2.getNode().getAction().getType());
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}

	@Test
	public void createAttributeAllStrings() {
		node2.createAttribute("credit", "1000", "preserve");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}

	@Test(expected = RuntimeException.class)
	public void createAttributeWrongActionTest1() {
		node2.createAttribute("credit", "1000", "cheesecake");
	}

	@Test(expected = RuntimeException.class)
	public void createAttributeWrongActionTest2() {
		node2.createAttribute(att, "1000", "cheesecake");
	}

	@Test
	public void setAttributeResultTest() {
		node2.createAttribute(att, "1000->2000", new Action(Action.Type.PRESERVE));
		Node node = rule.getUnit().getMappings().getImage(node2.getNode(), rule.getUnit().getRhs());
		assertEquals(node2.getNode().getAttribute(att).getValue(), "1000");
		assertEquals(node.getAttribute(att).getValue(), "2000");
	}

	@Test(expected = RuntimeException.class)
	public void createAttributeWrongAttributeTest() {
		node2.createAttribute("cheesecake", "1000", new Action(Action.Type.PRESERVE));
	}

	@Test
	public void setNodeTest() {
		node2.setNode(node1.getNode());
		assertEquals(node1.getNode(), node2.getNode());
	}

	@Test
	public void setNameTest() {
		node1.setName("name");
		assertEquals(node1.getNode().getName(), "name");
	}
}
