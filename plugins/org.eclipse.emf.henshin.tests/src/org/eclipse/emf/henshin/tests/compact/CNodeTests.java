package org.eclipse.emf.henshin.tests.compact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CNodeTests {

	static EClass account, bank;
	static EReference ref;
	static EAttribute att;
	static String path;
	static EPackage pack;
	
	CRule rule;
	CModule mod;
	CNode node1, node2;
	
	@BeforeAll
	public static void globalSetUp() {
		path = "src/org/eclipse/emf/henshin/tests/compact/";
		HenshinResourceSet hrs = new HenshinResourceSet(path);
		pack = hrs.registerDynamicEPackages("bank.ecore").get(0);
		account = (EClass) pack.getEClassifier("Account");
		bank = (EClass) pack.getEClassifier("Bank");
		ref = bank.getEReferences().get(1);//accounts
		att = account.getEAllAttributes().get(1);//credit
	}

	@BeforeEach
	public void localSetUp() {
		mod = new CModule("module");
		mod.addImport(pack);
		rule = mod.createRule("rule");
		node1 = rule.createNode(bank);
		node2 = rule.createNode(account);
	}
	
	@Test
	void canCreateEdgeTest() {
		assertEquals(node1.canCreateEdge(node2, ref), rule.getUnit().canCreateEdge(node1.getNode(), node2.getNode(), ref));
	}
	
	@Test
	void canCreateEdgeRefStringTest() {
		assertEquals(node1.canCreateEdge(node2, "accounts"), rule.getUnit().canCreateEdge(node1.getNode(), node2.getNode(), ref));
	}
	
	@Test
	void canCreateEdgeWrongRefTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			node1.canCreateEdge(node2,  "cheesecake");
			});
		assertEquals(e.getMessage(),"No Reference for cheesecake found.");
	}
	
	@Test
	void createEdgeTest() {
		node1.createEdge(node2, ref, new Action(Action.Type.PRESERVE));
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(),Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}
	
	@Test
	void createEdgeDefaultActionTest() {
		node1.createEdge(node2, ref);
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(),Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}
	
	@Test
	void createEdgeDefaultActionStringRefTest() {
		node1.createEdge(node2, "accounts");
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(),Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}
	
	@Test
	void createEdgeStringRefTest() {
		node1.createEdge(node2, "accounts", new Action(Action.Type.PRESERVE));
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(),Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}
	
	@Test
	void createEdgeStringActionTest() {
		node1.createEdge(node2, ref, "preserve");
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(),Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}
	
	@Test
	void createEdgeAllStringsTest() {
		node1.createEdge(node2, "accounts", "preserve");
		Edge edge = rule.getUnit().getLhs().getEdges().get(0);
		assertEquals(edge.getAction().getType(),Action.Type.PRESERVE);
		assertEquals(edge.getSource(), node1.getNode());
		assertEquals(edge.getTarget(), node2.getNode());
		assertEquals(edge.getType(), ref);
	}
	
	@Test
	void createEdgeWrongActionTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			node1.createEdge(node2, "accounts", "cheesecake");
			});
		assertEquals(e.getMessage(), "cheesecake is not a valid Action");
		
		e = assertThrows(RuntimeException.class,() -> {
			node1.createEdge(node2, ref, "cheesecake");
			});
		assertEquals(e.getMessage(), "cheesecake is not a valid Action");
		
	}
	
	@Test
	void createEdgeFailTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			node1.createEdge(node2, "clients");
			});
		assertEquals(e.getMessage(), "Failed to create Edge");
	}
	
	@Test
	void createAttributeTest() {
		node2.createAttribute(att, "1000", new Action(Action.Type.PRESERVE));
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}
	
	@Test
	void createAttributeDefaultActionTest() {
		node2.createAttribute(att, "1000");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), node2.getNode().getAction().getType());
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}
	
	@Test
	void createAttributeStringAttTest() {
		node2.createAttribute("credit", "1000", new Action(Action.Type.PRESERVE));
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}
	
	@Test
	void createAttributeStringActionTest() {
		node2.createAttribute(att, "1000", "preserve");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}
	
	@Test
	void createAttributeDefaultActionStringRefTest() {
		node2.createAttribute("credit", "1000");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), node2.getNode().getAction().getType());
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}
	
	@Test
	void createAttributeAllStrings() {
		node2.createAttribute("credit", "1000", "preserve");
		Attribute attribute = node2.getNode().getAttributes().get(0);
		assertEquals(attribute.getAction().getType(), Action.Type.PRESERVE);
		assertEquals(attribute.getNode(), node2.getNode());
		assertEquals(attribute.getType(), att);
		assertEquals(attribute.getValue(), "1000");
	}
	
	@Test
	void createAttributeWrongActionTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			node2.createAttribute("credit", "1000", "cheesecake");
			});
		assertEquals(e.getMessage(), "cheesecake is not a valid Action");
		
		e = assertThrows(RuntimeException.class,() -> {
			node2.createAttribute(att, "1000", "cheesecake");
			});
		assertEquals(e.getMessage(), "cheesecake is not a valid Action");
	}
	
	@Test
	void setAttributeResultTest() {
		node2.createAttribute(att, "1000->2000", new Action(Action.Type.PRESERVE));
		Node node = rule.getUnit().getMappings().getImage(node2.getNode(), rule.getUnit().getRhs());
		assertEquals(node2.getNode().getAttribute(att).getValue(), "1000");
		assertEquals(node.getAttribute(att).getValue(), "2000");
	}
	
	@Test
	void createAttributeWrongAttributeTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			node2.createAttribute("cheesecake", "1000", new Action(Action.Type.PRESERVE));
			});
		assertEquals(e.getMessage(), "No Attribute for cheesecake found.");
	}
	
	@Test
	void setNodeTest() {
		node2.setNode(node1.getNode());
		assertEquals(node1.getNode(), node2.getNode());
	}
	
	@Test
	void setNameTest() {
		node1.setName("name");
		assertEquals(node1.getNode().getName(), "name");
	}
}
