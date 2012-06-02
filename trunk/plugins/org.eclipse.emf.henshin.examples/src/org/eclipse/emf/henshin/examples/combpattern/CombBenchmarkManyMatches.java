package org.eclipse.emf.henshin.examples.combpattern;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.LoggingApplicationMonitorImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class CombBenchmarkManyMatches {
	
	/**
	 * Relative path to the example files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/combpattern";

	public static final int GRID_SIZE = 5; // Number of nodes is - 	GRID_SIZE^2
	
	public static final int PATTERN_SIZE = 5;
	
	final static int ITERATIONS = 2;	

	
	public static void run(String path) {

		System.out.println("*******************Comb multiple matches*********************");
		System.out.println("Memory allocated:" +Runtime.getRuntime().maxMemory()/1024/1024 + "M");
		System.out.println("GRID_SIZE: " + GRID_SIZE);
		System.out.println("PATTERN_SIZE: " + PATTERN_SIZE);
		System.out.println("ITERATIONS: " + ITERATIONS + ", first iteration is not included into evaluation");
		System.out.println("*************************************************************");
		
		int numberOfMatches = (GRID_SIZE - PATTERN_SIZE+1) * (GRID_SIZE - 1);
		
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);

		// Load the transformation system:
		TransformationSystem trasys = resourceSet.getTransformationSystem("comb.henshin");
		
		LoggingApplicationMonitorImpl monitor = new LoggingApplicationMonitorImpl();
		
		// Load the rules:
		Rule createGrid = trasys.findRuleByName("createGrid");
		Rule createInitialNodes = trasys.findRuleByName("createInitialNodes");
		Rule addNodeToColumn = trasys.findRuleByName("addNodeToColumn");	
		Rule addnextColumnFirstElement = trasys.findRuleByName("addnextColumnFirstElement");
		TransformationUnit addAllNodesToColumn = trasys.findUnitByName("addAllNodesToColumn");		

		//create pattern for matching of Size N
		Rule combPattern = CombPatternCreator.create(PATTERN_SIZE, resourceSet);

		long sum = 0;
		// Repeat execution for obtaining mean value
		for (int j = 0; j < ITERATIONS; j++) {
			// Initialize the Henshin interpreter:
			EGraph graph = InterpreterFactory.INSTANCE.createEGraph();
			Engine engine = InterpreterFactory.INSTANCE.createEngine();
			RuleApplication ruleAppl = InterpreterFactory.INSTANCE
					.createRuleApplication(engine);
			ruleAppl.setEGraph(graph);
			UnitApplication unitAppl = InterpreterFactory.INSTANCE
					.createUnitApplication(engine);
			unitAppl.setEGraph(graph);

			long startTime = System.currentTimeMillis();
			// create Grid node
			ruleAppl.setRule(createGrid);
			ruleAppl.execute(monitor);

			// create Initial nodes
			ruleAppl.setRule(createInitialNodes);
			ruleAppl.execute(monitor);

			// keep very top left node for further use, to add new columns
			Object firstNode = ruleAppl.getResultParameterValue("first");

			// create first column
			ruleAppl.setRule(addNodeToColumn);
			for (int i = 0; i < GRID_SIZE - 2; i++) {
				ruleAppl.execute(monitor);
				ruleAppl.setCompleteMatch(null);
			}

			// create the rest of the grid
			ruleAppl.setRule(addnextColumnFirstElement);
			unitAppl.setUnit(addAllNodesToColumn);

			for (int i = 0; i < GRID_SIZE - 1; i++) {
				// create first element for the next column on the top of the
				// grid,
				// next to first node, reassign first node to this
				ruleAppl.setParameterValue("first", firstNode);
				ruleAppl.execute(null);
				firstNode = ruleAppl.getResultParameterValue("first");
				ruleAppl.setCompleteMatch(null);
				// populate the new column with the rest of the nodes.
				unitAppl.execute(monitor);
			}

			// execute comb Pattern
			int i = 0;
			for (Match match : engine.findMatches(combPattern, graph, null)) {
				// System.out.println(match);
				i++;
			}
			//get finish time
			long finishTime = System.currentTimeMillis();
			
			if(j!=0) //don't include first ITERATION; emf classes are loaded
				sum = sum + finishTime - startTime;
			System.out.println("ITERATION: " + j + "\nMatching time:"	+ (finishTime - startTime));
			if (i == numberOfMatches){
				System.out.println ("All matches found, total: " + i);
			System.out.println ("############################");
			}
			else
				System.out.println("Number of Matches: " + numberOfMatches	+ ", found matches" + i);
			if(j == ITERATIONS - 1 ) // save the graph on the last iteration
				resourceSet.saveObject(graph.getRoots().get(0),	"result_manymatches.xmi");
		}
		System.out.println("DONE! Average matching time:" + (sum/(ITERATIONS-1)));
	}
	
	public static void main(String[] args) {
		run(PATH);
	}

}
