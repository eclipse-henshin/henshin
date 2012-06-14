package org.eclipse.emf.henshin.examples.combpattern;

import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class CombBenchmarkManyMatches {

	/**
	 * Relative path to the example files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/combpattern";

	public static void run(String path, int gridSize, int patternSize) {

		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);

		// Load the transformation system:
		TransformationSystem trasys = resourceSet.getTransformationSystem("comb.henshin");

		// Load the rules:
		Rule createGrid = trasys.findRuleByName("createGrid");
		Rule createInitialNodes = trasys.findRuleByName("createInitialNodes");
		Rule addNodeToColumn = trasys.findRuleByName("addNodeToColumn");	
		Rule addnextColumnFirstElement = trasys.findRuleByName("addnextColumnFirstElement");
		TransformationUnit addAllNodesToColumn = trasys.findUnitByName("addAllNodesToColumn");

		// Create comb pattern:
		Rule combPattern = CombPatternCreator.createPattern(patternSize, resourceSet);

		// Initialize the Henshin interpreter:
		EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
		Engine engine = InterpreterFactory.INSTANCE.createEngine();
		
		RuleApplication ruleAppl = InterpreterFactory.INSTANCE.createRuleApplication(engine);
		ruleAppl.setEGraph(graph);
		
		UnitApplication unitAppl = InterpreterFactory.INSTANCE.createUnitApplication(engine);
		unitAppl.setEGraph(graph);
		
		ApplicationMonitor monitor = null; // new ProfilingApplicationMonitorImpl();

		long startTime = System.currentTimeMillis();
		
		// Create grid node:
		ruleAppl.setRule(createGrid);
		ruleAppl.execute(monitor);

		// Create initial nodes:
		ruleAppl.setRule(createInitialNodes);
		ruleAppl.execute(monitor);

		// Keep very top left node for further use, to add new columns:
		Object firstNode = ruleAppl.getResultParameterValue("first");

		// Create first column:
		ruleAppl.setRule(addNodeToColumn);
		for (int i=0; i < gridSize-2; i++) {
			ruleAppl.execute(monitor);
		}

		// Create the rest of the grid:
		ruleAppl.setRule(addnextColumnFirstElement);
		unitAppl.setUnit(addAllNodesToColumn);

		for (int i=0; i<gridSize-1; i++) {			
			// Create first element for the next column on the top of the grid,
			// next to first node, reassign first node to this:
			ruleAppl.setParameterValue("first", firstNode);
			ruleAppl.execute(monitor);
			firstNode = ruleAppl.getResultParameterValue("first");
			// Populate the new column with the rest of the nodes:
			unitAppl.execute(monitor);
		}

		// Get the finish time:
		long endTime1 = System.currentTimeMillis();

		// Find all matches for the comb pattern in the grid:
		int foundMatches = InterpreterUtil.findAllMatches(engine, combPattern, graph, null).size();

		// Get the finish time:
		long endTime2 = System.currentTimeMillis();
		
		// Check whether the number of matches is correct:
		int expectedMatches = (gridSize-patternSize+1) * (gridSize-1);
		if (expectedMatches!=foundMatches) {
			throw new AssertionError("Expected " + expectedMatches + " for the comb pattern, but found " + foundMatches);
		}
		System.out.println(gridSize + "\t" + patternSize + "\t" + foundMatches + 
				"\t" + (endTime1 - startTime) + "\t" + (endTime2 - endTime1) + "\t" + (endTime2 - startTime));
		
		//((ProfilingApplicationMonitorImpl) monitor).printStats();
		
	}
	
	public static void runDefault(String path) {
		System.out.println("Starting comb pattern benchmark for multiple matches...");
		System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory()/1024/1024 + "M\n");
		System.out.println("Grid\tPattern\tMatches\tConTime\tMatTime\tTotTime");
		for (int g=10; g<=30; g=g+10) {
			for (int p=10; p<=g; p=p+10) {
				run(path, g, p);			
			}
		}
	}

	public static void main(String[] args) {
		runDefault(PATH);
	}

}
