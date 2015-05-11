/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.examples.aggregation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * Aggregation example for the Henshin interpreter.
 * 
 * @author Christian Krause
 */
public class AggregationExample {

	/**
	 * Relative path to the aggregation model files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/aggregation";

	/**
	 * Run the aggregation example.
	 * 
	 * @param path Relative path to the model files.
	 */
	public static void run(String path) {

		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);

		// Load the module:
		Module module = resourceSet.getModule("aggregation.henshin", false);

		// Load the example model into an EGraph:
		EGraph graph = new EGraphImpl(resourceSet.getResource("family.xmi"));

		// Get the family object:
		EObject family = graph.getRoots().get(0);

		// Create an engine and a rule application:
		Engine engine = new EngineImpl();
		UnitApplication app = new UnitApplicationImpl(engine);
		app.setEGraph(graph);

		// Calculation number of family members:
		app.setUnit(module.getUnit("calcMemberCount"));
		InterpreterUtil.executeOrDie(app);

		// We expect 4 family members:
		int memberCount = (Integer) family.eGet(family.eClass().getEStructuralFeature("memberCount"));
		System.out.println("Calculated " + memberCount + " family members");
		if (memberCount != 4) {
			throw new RuntimeException("Expected 4 family members");
		}

		// Calculation average age:
		app.setUnit(module.getUnit("calcAverageAge"));
		InterpreterUtil.executeOrDie(app);

		// We expect an average age of 20:
		double averageAge = (Double) family.eGet(family.eClass().getEStructuralFeature("averageAge"));
		System.out.println("Calculated " + averageAge + " as average age");
		if (averageAge != 20) {
			throw new RuntimeException("Expected average age of 20");
		}

	}

	public static void main(String[] args) {
		run(PATH); // we assume the working directory is the root of the examples plug-in
	}

}
