package org.eclipse.emf.henshin.examples.combpattern;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class CombBenchmarkNoMatches {
	
	/**
	 * Relative path to the example files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/combpattern";

	public static final int NUMBER_OF_ROWS = 200;
	
	public static final int NUMBER_OF_COLUMNS = 100; // each columns is a pair of two columns, see the paper for NO-Matching test case
	
	public static final int PATTERN_SIZE = 50;
	
	final static int ITERATIONS = 5;	

	public static void run(String path) {
				
		System.out.println("*******************Comb no-matches*********************");
		System.out.println("Memory allocated:" +Runtime.getRuntime().maxMemory()/1024/1024 + "Mb");
		System.out.println("NUMBER_OF_COLUMNS: " + NUMBER_OF_COLUMNS);
		System.out.println("NUMBER_OF_ROWS: " + NUMBER_OF_ROWS);
		System.out.println("PATTERN_SIZE: " + PATTERN_SIZE);
		System.out.println("ITERATIONS: " + ITERATIONS + ", first iteration is not included into evaluation");
		System.out.println("*************************************************************");
		
		// Create a resource set with a base directory:
		HenshinResourceSet resourceSet = new HenshinResourceSet(path);

		// Load the transformation system:
		TransformationSystem trasys = resourceSet.getTransformationSystem("comb.henshin");

		// Load the rules:
		Rule createGrid = trasys.findRuleByName("createGrid");
		Rule createInitialColumnNodesForNoMatching = trasys.findRuleByName("createInitialColumnNodesForNoMatching");
		Rule addNodesToColumnForNoMatching = trasys.findRuleByName("addNodesToColumnForNoMatching");	

		//create pattern for matching of Size N
		Rule combPattern = CombPatternCreator.create(PATTERN_SIZE, resourceSet);

		long sum = 0;
		// Repeat execution for obtaining mean value
		for (int k = 0; k < ITERATIONS; k++) {

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
			ruleAppl.execute(null);

			// create columns
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {

				// create Initial nodes
				ruleAppl.setRule(createInitialColumnNodesForNoMatching);
				ruleAppl.execute(null);

				// keep first node for further use, to add new column
				Object firstNode = ruleAppl.getResultParameterValue("first");

				// extend each the column with new row nodes
				ruleAppl.setRule(addNodesToColumnForNoMatching);

				for (int i = 0; i < NUMBER_OF_ROWS - 2; i++) {
					ruleAppl.setParameterValue("first", firstNode);
					ruleAppl.execute(null);
					ruleAppl.setCompleteMatch(null);
					firstNode = ruleAppl.getResultParameterValue("first");
				}
			}

			// execute comb Pattern
			int i = InterpreterUtil.findAllMatches(engine, combPattern, graph, null).size();
			
			// get finish time
			long finishTime = System.currentTimeMillis();

			if (k != 0) // don't include first ITERATION; emf classes are loaded
				sum = sum + finishTime - startTime;
			System.out.println("ITERATION: " + k + "\nMatching time:"	+ (finishTime - startTime));
			if (i == 0) {
				System.out.println("All matches found, total: " + i);
				System.out.println("############################");
			} else
				System.out.println("Found matches" + i);
			if (k == ITERATIONS - 1) // save the graph on the last iteration
				resourceSet.saveObject(graph.getRoots().get(0),	"result_nomatches.xmi");
		}
		System.out.println("DONE! Average matching time:" + (sum / (ITERATIONS - 1)));
		
	}

	public static void main(String[] args) {
		run(PATH);
	}
	
}
