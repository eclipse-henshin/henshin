/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.examples.bank;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Module;
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
		
		// Load the module:
		Module module = resourceSet.getModule("bank.henshin", false);

		// Load the example model into an EGraph:
		EGraph graph = new EGraphImpl(resourceSet.getResource("example-bank.xmi"));
		
		// Create an engine and a rule application:
		Engine engine = new EngineImpl();
		UnitApplication createAccountApp = new UnitApplicationImpl(engine);
		createAccountApp.setEGraph(graph);
		
		// Creating a new account for Alice...
		createAccountApp.setUnit(module.getUnit("createAccount"));
		createAccountApp.setParameterValue("client", "Alice");
		createAccountApp.setParameterValue("accountId", 5);
		if (!createAccountApp.execute(null)) {
			throw new RuntimeException("Error creating account for Alice");
		}

		UnitApplication transferMoneyApp = new UnitApplicationImpl(engine);
		transferMoneyApp.setEGraph(graph);
		// Transferring some money:
		transferMoneyApp.setUnit(module.getUnit("transferMoney"));
		transferMoneyApp.setParameterValue("client", "Alice");
		transferMoneyApp.setParameterValue("fromId", 1);
		transferMoneyApp.setParameterValue("toId", 2);
		transferMoneyApp.setParameterValue("amount", 50.0d); // double
		if (!transferMoneyApp.execute(null)) { // parameters x and y will be matched by the engine
			throw new RuntimeException("Error transferring money");
		}
		
		// Deleting all accounts of Charles:
		UnitApplication deleteAccountsApp = new UnitApplicationImpl(engine);
		deleteAccountsApp.setEGraph(graph);
		deleteAccountsApp.setUnit(module.getUnit("deleteAllAccounts"));
		deleteAccountsApp.setParameterValue("client", "Charles");
		if (!deleteAccountsApp.execute(null)) {
			throw new RuntimeException("Error deleting Charles' accounts");
		}
		
		// Saving the result:
		if (saveResult) {
			resourceSet.saveEObject(graph.getRoots().get(0), "example-result.xmi");
		}
	}
	
	public static void main(String[] args) {
		run(PATH, true); // we assume the working directory is the root of the examples plug-in
	}
	
}
