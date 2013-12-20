/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.examples.pacbenchmark;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * @author Christian Krause
 */
public class PACBenchmark {

	/** 
	 * Relative path to the bank model files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/pacbenchmark";
	
	public static void run(String path) {
		
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		
		// Load the module and the required units:
		Module module = resourceSet.getModule("pacbenchmark.henshin", false);
		Unit createExampleUnit = module.getUnit("createExample");
		Unit findCouplesUnit = module.getUnit("findCouples");

		// Create an engine:
		Engine engine = new EngineImpl();

		System.out.println("Couples\tNodes\tTime (ms)");
		for (int i=1; i<=10; i++) {
			int n = i*100;

			// Create an EGraph:
			EGraph graph = new EGraphImpl();
			
			// Create test example:
			UnitApplication createExample = new UnitApplicationImpl(engine, graph, createExampleUnit, null);
			createExample.setParameterValue("n", n);
			InterpreterUtil.executeOrDie(createExample);

			// Find couples:
			long millis = System.currentTimeMillis();
			UnitApplication findCouples = new UnitApplicationImpl(engine, graph, findCouplesUnit, null);
			InterpreterUtil.executeOrDie(findCouples);
			millis = System.currentTimeMillis() - millis;
			
			// Check if it is correct:
			EClass coupleClass = (EClass) module.getImports().get(0).getEClassifier("Couple");
			int foundCouples = graph.getDomainSize(coupleClass, true);
			int expected = 2*n;
			if (foundCouples != expected) {
				throw new AssertionError("Expected to find " + expected + " couples, but actually found " + foundCouples);
			}
			
			System.out.println(n + "\t" + graph.size() + "\t" + millis);
			
		}
		
	}
	
	public static void main(String[] args) {
		run(PATH); // we assume the working directory is the root of the examples plug-in
	}
	
}
