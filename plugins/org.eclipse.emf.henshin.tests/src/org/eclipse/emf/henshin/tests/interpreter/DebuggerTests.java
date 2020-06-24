package org.eclipse.emf.henshin.tests.interpreter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.Debugger;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DebuggerTests {


	
	static String path;
	
	HenshinResourceSet res;
	EPackage pack;
	EClass account;
	EAttribute credit, iD;
	CModule mod;
	Debugger debugger;
	EGraph graph;
	EGraph expectedResult;
	
	@BeforeAll
	public static void globalSetUp() {
		path = "src/org/eclipse/emf/henshin/tests/interpreter/";
		
	}
	
	@BeforeEach
	public void localSetUp() {
		res = new HenshinResourceSet(path);
		mod = new CModule(res.getModule("bank.henshin"));
//		mod = CModule.loadFromFile(path+"bank.henshin");
		graph = new EGraphImpl(res.getResource("example-bank.xmi"));
		
		debugger = new Debugger(path);
		expectedResult = new EGraphImpl(res.getResource("example-result.xmi"));
		
		pack = res.registerDynamicEPackages("bank.ecore").get(0);
		account = (EClass) pack.getEClassifier("Account");
		credit = account.getEAllAttributes().get(1);
		iD = account.getEAllAttributes().get(0);
	}

	@Test
	void undoRedoTest() {
		//Using a modified version of https://www.eclipse.org/henshin/examples.php?example=bank for Testing
				//Creating a new account for Alice...
				String expectedAfterUndo;
				expectedAfterUndo = graph.toString().split("\\(")[1];
				
				graph = debugger.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5);
				//Transferring some Money...
				graph = debugger.executeUnit(graph, mod.getModule(), "transferMoney", "Alice", 1, 2, 50.0d);
				//Deleting all accounts of Charles...
				graph = debugger.executeUnit(graph, mod.getModule(), "deleteAllAccounts", "Charles");

				String graphString, expectedString;
				graphString = graph.toString().split("\\(")[1];
				expectedString = expectedResult.toString().split("\\(")[1];
				//Can only assert same Structure. Same Content needs to be checked.
				assertEquals(graphString, expectedString);
				
				graph = debugger.undo();
				graph = debugger.undo();
				graph = debugger.undo();
				graphString = graph.toString().split("\\(")[1];
				assertEquals(graphString, expectedAfterUndo);
				
				graph = debugger.redo();
				graph = debugger.redo();
				graph = debugger.redo();

				graphString = graph.toString().split("\\(")[1];
				assertEquals(graphString, expectedString);
	}
	
	@Test
	void undoRedoFailTest() {
		
		Exception e = assertThrows(RuntimeException.class,() -> {
			debugger.undo();
			});
		assertEquals(e.getMessage(), "No undoable Units exist");
		e = assertThrows(RuntimeException.class,() -> {
			debugger.redo();
			});
		assertEquals(e.getMessage(), "No redoable Units exist");
	}
	
	@Test
	void executeRuleOnCompleteMatchTest() {
		List<EObject> list = graph.getDomain(account,true);
		EObject eNode = null;
		for(EObject e: list)
		{
			 int iDVal = (int)e.eGet(iD);
			 if(iDVal == 2)
			 {
				 eNode = e;//This should be Account 2
				 break;
			 }
		}
		
		
		Rule r = (Rule) mod.getModule().getUnit("addCreditToRandomAccount2");
		Match match = InterpreterFactory.INSTANCE.createMatch(r, false);
		Node n = r.getLhs().getNodes().get(0); //The Rule only has this node.
		
		match.setNodeTarget(n, eNode);
		
		EGraph transformedGraph = debugger.executeRuleOnMatch(graph, mod.getModule(), "addCreditToRandomAccount2", match, 400.0);
		debugger.saveGraph(graph, "debuggerTest-result.xmi");
		
		list = transformedGraph.getDomain(account,true);
		eNode = null;
		for(EObject e: list)
		{
			 double value = (double)e.eGet(credit);

			 int iDVal = (int) e.eGet(iD);
			 if(iDVal == 2)
			 {
				 assertEquals(value,400.0);
			 }
		}
	}
	
	@Test
	void executeRuleOnPartialMatchTest() {
		List<EObject> list = graph.getDomain(account,true);
		EObject eNode = null;
		for(EObject e: list)
		{
			 int iDVal = (int)e.eGet(iD);
			 if(iDVal == 2)
			 {
				 eNode = e;//This should be Account 2
				 break;
			 }
		}
		
		
		Rule r = (Rule) mod.getModule().getUnit("addCreditToRandomAccount");
		Match match = InterpreterFactory.INSTANCE.createMatch(r, false);
		Node n = r.getLhs().getNodes().get(0); //The Rule only has this node.
		
		match.setNodeTarget(n, eNode);
		
		EGraph transformedGraph = debugger.executeRuleOnMatch(graph, mod.getModule(), "addCreditToRandomAccount", match, 400.0);
		debugger.saveGraph(graph, "debuggerTest-result.xmi");
		
		list = transformedGraph.getDomain(account,true);
		eNode = null;
		for(EObject e: list)
		{
			 double value = (double)e.eGet(credit);

			 int iDVal = (int) e.eGet(iD);
			 if(iDVal == 2)
			 {
				 assertEquals(value,400.0);
			 }
		}
	}
	
	@Test
	void executeRuleOnMatchStringGraphTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = debugger.executeRuleOnMatch("example-bank.xmi", mod.getModule(), "addCreditToRandomAccount", null, 400.0);
			});
		assertEquals(e.getMessage(), "Failed to apply transformation");
	}
	
	@Test
	void executeRuleOnMatchStringModuleTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = debugger.executeRuleOnMatch(graph, "bank.henshin", "createAccount", null, "Alice", 5);
			});
		assertEquals(e.getMessage(), "Failed to apply transformation");
	}
	
	@Test
	void executeRuleOnMatchAllStringsTest() {
		assertDoesNotThrow(() -> {
			graph = debugger.executeRuleOnMatch("example-bank.xmi", "bank.henshin", "createAccount", null, "Alice", 5);
			});
	}
	
	@Test
	void executeRuleOnMatchNoRuleTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = debugger.executeRuleOnMatch("example-bank.xmi", "bank.henshin", "cheesecake", null, "Alice", 5);
			});
		assertEquals(e.getMessage(), "Specified Rule does not exist");
	}
	
	@Test
	void executeRuleOnMatchNotARuleTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = debugger.executeRuleOnMatch("example-bank.xmi", "bank.henshin", "independent", null, "Alice", 5);
			});
		assertEquals(e.getMessage(), "Specified Unit is not a Rule");
	}
}
