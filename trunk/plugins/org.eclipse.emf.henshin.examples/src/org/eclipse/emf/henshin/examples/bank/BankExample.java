package org.eclipse.emf.henshin.examples.bank;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * Bank example for the Henshin interpreter. Shows the usage of the interpreter.
 * 
 * @author Christian Krause
 */
public class BankExample {

	/** 
	 * Relative path to the bank model files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/bank";
	
	/**
	 * Run the bank example.
	 * @param path Relative path to the model files.
	 * @param saveResult Whether the result should be saved.
	 */
	public static void run(String path, boolean saveResult) {
		
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		
		// Load the transformation system:
		TransformationSystem trasys = resourceSet.getTransformationSystem("bank.henshin");

		// Load the example model into an EGraph:
		EGraph graph = new EGraphImpl(resourceSet.getResource("example-bank.xmi"));
		
		// Create an engine and a rule application:
		Engine engine = new EngineImpl();
		RuleApplication app = new RuleApplicationImpl(engine);
		app.setEGraph(graph);
		
		// Creating a new account for Alice...
		app.setRule(trasys.getRule("createAccount"));
		app.setParameterValue("client", "Alice");
		app.setParameterValue("accountId", 5);
		if (!app.execute(null)) {
			throw new RuntimeException("Error creating account for Alice");
		}
		
		// Transferring some money:
		app.setRule(trasys.getRule("transferMoney"));
		app.setParameterValue("client", "Alice");
		app.setParameterValue("fromId", 1);
		app.setParameterValue("toId", 2);
		app.setParameterValue("amount", 50.0d); // double
		if (!app.execute(null)) { // parameters x and y will be matched by the engine
			throw new RuntimeException("Error transferring money");
		}
		
		// Deleting all accounts of Charles:
		app.setRule(trasys.getRule("deleteAllAccounts"));
		app.setParameterValue("client", "Charles");
		if (!app.execute(null)) {
			throw new RuntimeException("Error deleting Charles' accounts");
		}
		
		// Saving the result:
		if (saveResult) {
			resourceSet.saveObject(graph.getRoots().get(0), "example-result.xmi");
		}
	}
	
	public static void main(String[] args) {
		run(PATH, true); // we assume the working directory is the root of the examples plug-in
	}
	
}
