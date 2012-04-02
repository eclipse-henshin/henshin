/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.examples.sierpinski;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * A benchmark constructing multiple levels of a Sierpinski triangle.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Sierpinski_triangle">Sierpinski Triangle</a>
 */
public class SierpinskiBenchmark {
	
	public static void main(String[] args) {

		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(
				"src/org/eclipse/emf/henshin/examples/sierpinski/model");
		
		// Load the transformation system:
		TransformationSystem trasys = resourceSet.getTransformationSystem("sierpinski.henshin");

		// Load the first level of the Sierpinski triangle:
		EObject container = resourceSet.getFirstRoot("sierpinski-start.xmi");
		
		// Initialize the Henshin interpreter:
		EmfGraph graph = new EmfGraph(container);
		graph.removeEObject(container);
		EmfEngine engine = new EmfEngine(graph);

		// Load the rule:
		Rule addTriangleRule = trasys.findRuleByName("AddTriangle");

		System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB available memory\n");
		
		// Iteratively compute the Sierpinski triangle:
		int i = 1;
		while (true) {
			
			// Find all matches:
			long startTime = System.nanoTime();
			RuleApplication addTriangle = new RuleApplication(engine,
					addTriangleRule);
			List<Match> matches = addTriangle.findAllMatches();
			long matchingTime = (System.nanoTime() - startTime) / 1000000;
			
			System.out.println("Level: " + i);
			System.out.println("Rule applications:" + matches.size());
			System.out.println("Matching: " + matchingTime + "ms");

			// Apply rule with all matches:
			startTime = System.nanoTime();
			for (Match match : matches) {
				addTriangle = new RuleApplication(engine, addTriangleRule);
				addTriangle.setMatch(match);
				addTriangle.apply();
			}
			long runtime = (System.nanoTime() - startTime) / 1000000;

			System.out.println("Application: " + runtime + "ms");
			System.out.println("Total: " + (matchingTime + runtime) + "ms");
			System.out.println("Nodes: " + graph.geteObjects().size());
			System.out.println();
			i++;
			
		}
		
	}
	
}
