/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.examples.sierpinski;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;

/**
 * A benchmark constructing multiple levels of a sierpinski triangle.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Sierpinski_triangle">Sierpinski
 *      Triangle</a>
 */
public class SierpinskiBenchmark {
	public static void main(String[] args) {

		HenshinPackageImpl.init();

		ModelHelper.registerFileExtension("henshin");
		ModelHelper.registerFileExtension("sierpinski");
		ModelHelper.registerFileExtension("ecore");
		
		EPackage sierpinskiPackage = (EPackage) ModelHelper
				.loadFile("src/org/eclipse/emf/henshin/examples/sierpinski/model/sierpinski.ecore");
		EPackage.Registry.INSTANCE.put(sierpinskiPackage.getNsURI(), sierpinskiPackage);

		// load the transformation rules
		TransformationSystem ts = (TransformationSystem) ModelHelper
				.loadFile("src/org/eclipse/emf/henshin/examples/sierpinski/model/sierpinski.henshin");

		// load a minimal first level sierpinski triangle
		// VertexContainer container = (VertexContainer) ModelHelper
		// .loadFile("src/sierpinski/instances/start.sierpinski");
		EObject container = ModelHelper
				.loadFile("src/org/eclipse/emf/henshin/examples/sierpinski/model/start.sierpinski");

		// initialize the henshin interpreter
		EmfGraph graph = new EmfGraph();
		graph.addRoot(container);
		graph.removeEObject(container);
		EmfEngine engine = new EmfEngine(graph);

		// load a rule
		Rule addTriangleRule = ts.findRuleByName("AddTriangle");

		// compute different sierpinski levels
		int i = 0;
		do {
			i++;
			long startTime = System.nanoTime();
			RuleApplication addTriangle = new RuleApplication(engine,
					addTriangleRule);
			List<Match> matches = addTriangle.findAllMatches();
			long matchingTime = (System.nanoTime() - startTime) / 1000000;
			System.out.println("Level: " + i);
			System.out.println("Rule applications:" + matches.size());
			System.out.println("Matching: " + matchingTime + "ms");
			startTime = System.nanoTime();
			for (Match match : matches) {
				addTriangle = new RuleApplication(engine, addTriangleRule);
				addTriangle.setMatch(match);
				addTriangle.apply();
			}
			long runtime = (System.nanoTime() - startTime) / 1000000;
			// System.out.println("Rule applications: "+matches.size());

			System.out.println("Application: " + runtime + "ms");
			System.out.println("Total: " + (matchingTime + runtime) + "ms");

			System.out.println("Nodes: " + graph.geteObjects().size());
			System.out.println();
		} while (true);
	}
}
