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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

/**
 * A benchmark constructing multiple levels of a Sierpinski triangle.
 * @see <a href="http://en.wikipedia.org/wiki/Sierpinski_triangle">Sierpinski Triangle</a>
 */
public class SierpinskiBenchmark {
	
	/** 
	 * Relative path to the Sierpinski model files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/sierpinski";
	
	/**
	 * Run the Sierpinski benchmark.
	 * @param path Relative path to the model files.
	 * @param iterations Number of iterations.
	 */
	public static void run(String path, int iterations) {
		
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);
		
		// Load the transformation system and find the rule:
		TransformationSystem trasys = resourceSet.getTransformationSystem("sierpinski.henshin");
		Rule rule = trasys.findRuleByName("AddTriangle");

		// Load the first level of the Sierpinski triangle:
		Resource resource = resourceSet.getResource("sierpinski-start.xmi");
		EObject container = resource.getContents().get(0);
		
		// Create the graph representation:
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		graph.addTree(container);
		graph.remove(container);
		
		// Create an engine and a rule application:
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		RuleApplication application = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		application.setRule(rule);
		application.setEGraph(graph);
		
		// Check how much memory is available:
		System.out.println(Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB available memory\n");
		
		// Iteratively compute the Sierpinski triangle:
		List<Match> matches = new ArrayList<Match>();
		for (int i=0; i<iterations; i++) {

			// Clear the matches:
			matches.clear();
			System.gc();

			// Find all matches:
			long startTime = System.currentTimeMillis();
			for (Match match : engine.findMatches(rule, graph, null)) {
				matches.add(match);
			}
			long matchingTime = (System.currentTimeMillis() - startTime);
			
			System.out.println("Level: " + (i+1));
			System.out.println("Matches:" + matches.size());
			System.out.println("Matching time: " + matchingTime + "ms");

			// Apply rule with all found matches:
			ApplicationMonitor monitor = InterpreterFactory.INSTANCE.createApplicationMonitor();
			System.gc();

			startTime = System.currentTimeMillis();
			for (Match match : matches) {
				application.setCompleteMatch(match);
				if (!application.execute(monitor)) {
					throw new RuntimeException("Error transforming Sierpinski model");
				}
			}
			long runtime = (System.currentTimeMillis() - startTime);

			System.out.println("Application time: " + runtime + "ms");
			System.out.println("Total time: " + (matchingTime + runtime) + "ms");
			System.out.println("Nodes: " + graph.size());
			System.out.println();
			
		}
		
	}
	
	public static void main(String[] args) {
		run(PATH, 15); // we assume the working direcory is the root of the examples plug-in
	}
	
}
