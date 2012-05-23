package org.eclipse.emf.henshin.tests.actionTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.HenshinActionHelper;
import org.eclipse.emf.henshin.testframework.HenshinTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for setting actions.
 * @author Christian Krause
 */
public class SetAction extends HenshinTest {
	
	/**
	 * Test actions (string representations).
	 */
	private static final String[] TEST_ACTIONS = new String[] { 

		// Basic actions:
		"preserve",		"create",		"delete", 
		
		// Forbid actions:
		"forbid",		"forbid:nac1",	"forbid:nac1", 
		
		// Require action:
		"require",		"require:pac1",	"require:pac2",
		
		// Amalgamated actions:
		"preserve*",	"preserve*:multi1",	"preserve*:multi2",	
		"create*",		"create*:multi1",	"create*:multi2",	
		"delete*",		"delete*:multi1",	"delete*:multi2",	
		
	};
	
	// The test actions:
	private List<Action> actions;
	
	// Rules to test:
	private List<Rule> rules;
	
	@Before
	public void loadRules() {
		
		// Load rules:
		List<File> files = findHenshinFiles(new File("basicTestRules"));
		assertFalse("No Henshin files found", files.isEmpty());
		rules = new ArrayList<Rule>();
		for (File file : files) {
			TransformationSystem system = (TransformationSystem) 
					ModelHelper.loadFile(file.getAbsolutePath());
			rules.addAll(system.getRules());
			if (rules.size()>10) {
				break;
			}
		}
		
		// Parse the actions:
		actions = new ArrayList<Action>();
		for (String action : TEST_ACTIONS) {
			try {
				actions.add(Action.parse(action));
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	
	@Test
	public void setNodeActions() {
		doSetActionsTest(HenshinPackage.eINSTANCE.getNode());
	}

	@Test
	public void setEdgeActions() {
		doSetActionsTest(HenshinPackage.eINSTANCE.getEdge());
	}

	@Test
	public void setAttributeActions() {
		doSetActionsTest(HenshinPackage.eINSTANCE.getAttribute());
	}

	private void doSetActionsTest(EClass elementType) {
		
		// Check all rules:
		for (Rule rule : rules) {
			System.out.println("Testing " + elementType.getName().toLowerCase() + 
							   " actions for rule " + rule.getName() + "...");
			
			// Set node actions for all action elements:
			for (Object element : getActionElements(rule, elementType)) {
				element = copyRule(element);
				for (Action action1 : actions) {
					setAction(element, action1);
					for (Action action2 : actions) {
						EObject copiedElement = (EObject) copyRule(element);
						setAction(copiedElement, action2);
						setAction(copiedElement, action1);						
					}
				}
			}
		}
	}
	
	private Rule getRule(EObject object) {
		while (object!=null && !(object instanceof Rule)) {
			object = object.eContainer();
		}
		return (Rule) object;
	}
	
	/*
	 * Get the action elements in a rule.
	 */
	private List<?> getActionElements(Rule rule, EClass elementType) {
		if (elementType==HenshinPackage.eINSTANCE.getNode()) {
			return HenshinActionHelper.getActionNodes(rule, null);
		}
		if (elementType==HenshinPackage.eINSTANCE.getEdge()) {
			return HenshinActionHelper.getActionEdges(rule, null);
		}
		if (elementType==HenshinPackage.eINSTANCE.getAttribute()) {
			List<Node> nodes = HenshinActionHelper.getActionNodes(rule, null);
			List<Attribute> attributes = new ArrayList<Attribute>();
			for (Node node : nodes) {
				attributes.addAll(HenshinActionHelper.getActionAttributes(node, null));
			}
			return attributes;
		}
		return null;
	}

	/*
	 * Set an element action and check if it went well.
	 */
	private void setAction(Object element, Action action) {
		if (action.isAmalgamated() && 
			(element instanceof Attribute || element instanceof Edge)) {
			return;
		}
		Rule rule = getRule((EObject) element);
		//System.out.println("Setting action " + action + " for " + ((EObject) element).eClass().getName() + " in rule " + ruleName(rule));
		if (element instanceof Node) {
			HenshinActionHelper.setAction((Node) element, action);
			Action action2 = HenshinActionHelper.getAction((Node) element); 
			assertTrue("Error setting node action " + action + " in rule " + rule.getName() + " (got action " + action2 + ")", 
				action.equals(action2));
		}
		else if (element instanceof Edge) {
			HenshinActionHelper.setAction((Edge) element, action);
			Action action2 = HenshinActionHelper.getAction((Edge) element); 
			assertTrue("Error setting edge action " + action + " in rule " + rule.getName() + " (got action " + action2 + ")", 
				action.equals(action2));
		}
		else if (element instanceof Attribute) {
			HenshinActionHelper.setAction((Attribute) element, action);
			Action action2 = HenshinActionHelper.getAction((Attribute) element); 
			assertTrue("Error setting attribute action " + action + " in rule " + rule.getName() + " (got action " + action2 + ")", 
				action.equals(action2));
		}
	}
	
	/*
	 * Copy a rule and return the copy of an element in it.
	 */
	@SuppressWarnings("unchecked")
	private <E> E copyRule(E element) {
		Copier copier = new Copier();
		copier.copy(getRule((EObject) element));
		copier.copyReferences();
		return (E) copier.get(element);
	}
	
}

