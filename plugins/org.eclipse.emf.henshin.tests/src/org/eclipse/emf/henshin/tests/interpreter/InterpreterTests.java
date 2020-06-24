package org.eclipse.emf.henshin.tests.interpreter;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.Interpreter;
import org.eclipse.emf.henshin.model.compact.CModule;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InterpreterTests {

	static String path;

	HenshinResourceSet res;
	CModule mod;
	Interpreter interpreter;
	EGraph graph;
	EGraph expectedResult;

	@BeforeClass
	public static void globalSetUp() {
		path = "src/org/eclipse/emf/henshin/tests/interpreter/";

	}

	@Before
	public void localSetUp() {
		res = new HenshinResourceSet(path);
		mod = new CModule(res.getModule("bank.henshin"));
//		mod = CModule.loadFromFile(path+"bank.henshin");
		graph = new EGraphImpl(res.getResource("example-bank.xmi"));

		interpreter = new Interpreter(path);
		expectedResult = new EGraphImpl(res.getResource("example-result.xmi"));
	}

	@Test
	public void executeUnitTest() {
		// Using https://www.eclipse.org/henshin/examples.php?example=bank for Testing
		// Creating a new account for Alice...
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5);
		// Transferring some Money...
		graph = interpreter.executeUnit(graph, mod.getModule(), "transferMoney", "Alice", 1, 2, 50.0d);
		// Deleting all accounts of Charles...
		graph = interpreter.executeUnit(graph, mod.getModule(), "deleteAllAccounts", "Charles");
		// Save the result:
		interpreter.saveGraph(graph, "test-result.xmi");
		String graphString, expectedString;
		graphString = graph.toString().split("\\(")[1];
		expectedString = expectedResult.toString().split("\\(")[1];
		// Can only assert same Structure. Same Content needs to be checked.
		assertEquals(graphString, expectedString);
	}

	@Test(expected = RuntimeException.class)
	public void executeUnitStringGraphTest() {
		graph = interpreter.executeUnit("example-bank.xmi", mod.getModule(), "createAccount", "Alice", 5);
	}

	@Test(expected = RuntimeException.class)
	public void executeUnitStringModuleTest() {
		graph = interpreter.executeUnit(graph, "bank.henshin", "createAccount", "Alice", 5);
	}

	@Test
	public void executeUnitAllStringsTest() {
		// Using https://www.eclipse.org/henshin/examples.php?example=bank for Testing
		// Creating a new account for Alice...
		graph = interpreter.executeUnit("example-bank.xmi", "bank.henshin", "createAccount", "Alice", 5);
		// Transferring some Money...
		graph = interpreter.executeUnit(graph, "bank.henshin", "transferMoney", "Alice", 1, 2, 50.0d);
		// Deleting all accounts of Charles...
		graph = interpreter.executeUnit(graph, "bank.henshin", "deleteAllAccounts", "Charles");
		// Save the result:
		interpreter.saveGraph(graph, "test-result.xmi");
		String graphString, expectedString;
		graphString = graph.toString().split("\\(")[1];
		expectedString = expectedResult.toString().split("\\(")[1];
		// Can only assert same Structure. Same Content needs to be checked.
		assertEquals(graphString, expectedString);
	}

	@Test(expected = RuntimeException.class)
	public void executeUnitWrongUnitTest() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createCheesecake", "Alice", 5);
	}

	@Test(expected = RuntimeException.class)
	public void executeUnitParamListTooLong() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5, "cheesecake");
	}

	@Test(expected = RuntimeException.class)
	public void executeUnitParamListTooShort() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice");
	}

	@Test(expected = RuntimeException.class)
	public void executeUnitParamTypeNotMatching() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", "5");
	}

	@Test
	public void getResultParameterValueTest() {
		graph = interpreter.executeUnit(graph, mod.getModule(), "createAccount", "Alice", 5);
		assertEquals(interpreter.getResultParameterValue("accountId"), 5);
	}

	@Test(expected = RuntimeException.class)
	public void getResultParameterValueWrongParamName() {
		interpreter.getResultParameterValue("cheesecake");
	}

	@Test
	public void getterSetterCoverage() {
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
		assertEquals(interpreter.getApplicationMonitor(), appMon);
	}

}
