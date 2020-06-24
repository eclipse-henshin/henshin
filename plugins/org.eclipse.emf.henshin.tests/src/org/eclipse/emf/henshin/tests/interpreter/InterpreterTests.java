package org.eclipse.emf.henshin.tests.interpreter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.Interpreter;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class InterpreterTests {

	
	static String path;
	
	HenshinResourceSet res;
	CModule mod;
	Interpreter interpreter;
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
		
		interpreter = new Interpreter(path);
		expectedResult = new EGraphImpl(res.getResource("example-result.xmi"));
	}

	@Test
	void executeUnitTest()
	{
		//Using https://www.eclipse.org/henshin/examples.php?example=bank for Testing
		//Creating a new account for Alice...
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5);
		//Transferring some Money...
		graph = interpreter.executeUnit(graph, mod.getModule(), "transferMoney", "Alice", 1, 2, 50.0d);
		//Deleting all accounts of Charles...
		graph = interpreter.executeUnit(graph, mod.getModule(), "deleteAllAccounts", "Charles");
		//Save the result:
		interpreter.saveGraph(graph, "test-result.xmi");
		String graphString, expectedString;
		graphString = graph.toString().split("\\(")[1];
		expectedString = expectedResult.toString().split("\\(")[1];
		//Can only assert same Structure. Same Content needs to be checked.
		assertEquals(graphString, expectedString);
	}
	
	@Test
	void executeUnitStringGraphTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = interpreter.executeUnit("example-bank.xmi", mod.getModule(), "createAccount", "Alice", 5);
			});
		assertEquals(e.getMessage(), "Failed to apply transformation");
	}
	
	@Test
	void executeUnitStringModuleTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = interpreter.executeUnit(graph, "bank.henshin", "createAccount", "Alice", 5);
			});
		assertEquals(e.getMessage(), "Failed to apply transformation");
	}
	
	@Test
	void executeUnitAllStringsTest() {
		//Using https://www.eclipse.org/henshin/examples.php?example=bank for Testing
				//Creating a new account for Alice...
				graph = interpreter.executeUnit("example-bank.xmi", "bank.henshin", "createAccount", "Alice", 5);
				//Transferring some Money...
				graph = interpreter.executeUnit(graph, "bank.henshin", "transferMoney", "Alice", 1, 2, 50.0d);
				//Deleting all accounts of Charles...
				graph = interpreter.executeUnit(graph, "bank.henshin", "deleteAllAccounts", "Charles");
				//Save the result:
				interpreter.saveGraph(graph, "test-result.xmi");
				String graphString, expectedString;
				graphString = graph.toString().split("\\(")[1];
				expectedString = expectedResult.toString().split("\\(")[1];
				//Can only assert same Structure. Same Content needs to be checked.
				assertEquals(graphString, expectedString);
	}
	
	@Test
	void executeUnitWrongUnitTest() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = interpreter.executeUnit(graph, mod.getModule(), "createCheesecake", "Alice", 5);
			});
		assertEquals(e.getMessage(), "Unit: createCheesecake doesn't exist in this Module");
	}
	
	@Test
	void executeUnitParamListTooLong() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5, "cheesecake");
			});
		assertEquals(e.getMessage(), "Too much ParameterValues were specified!");
	}
	
	@Test
	void executeUnitParamListTooShort() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice");
			});
		assertEquals(e.getMessage(), "Too less ParameterValues were specified!");
	}
	
	@Test
	void executeUnitParamTypeNotMatching() {
		Exception e = assertThrows(RuntimeException.class,() -> {
			graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", "5");
			});
		assertEquals(e.getMessage(), "Parameter Value for accountId does not have the correct Type");
	}
	
	@Test
	void getResultParameterValueTest() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5);
		assertEquals(interpreter.getResultParameterValue("accountId"), 5);
	}
	
	@Test
	void getResultParameterValueWrongParamName() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5);
		assertThrows(RuntimeException.class,() -> {
			interpreter.getResultParameterValue("cheesecake");
			});
	}
	
	@Test
	void getterSetterCoverage() {
		Engine engine = new EngineImpl();
		interpreter.setEngine(engine);
		assertEquals(interpreter.getEngine(), engine);
		ApplicationMonitor appMon = new ApplicationMonitor() {
			
			@Override
			public void notifyUndo(UnitApplication arg0, boolean arg1) {
				
			}
			
			@Override
			public void notifyRedo(UnitApplication arg0, boolean arg1) {
				
			}
			
			@Override
			public void notifyExecute(UnitApplication arg0, boolean arg1) {
				
			}
			
			@Override
			public boolean isUndo() {
				return false;
			}
			
			@Override
			public boolean isCanceled() {
				return false;
			}
			
			@Override
			public void cancelAndUndo() {
				
			}
			
			@Override
			public void cancel() {
				
			}
		};
		
		interpreter.setApplicationMonitor(appMon);
		assertEquals(interpreter.getApplicationMonitor(),appMon);
	}
	
}
