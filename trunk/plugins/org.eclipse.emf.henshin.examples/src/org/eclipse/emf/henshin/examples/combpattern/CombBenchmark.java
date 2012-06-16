package org.eclipse.emf.henshin.examples.combpattern;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.interpreter.util.InterpreterUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;

public class CombBenchmark {

	/**
	 * Relative path to the example files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/combpattern";
	
	// Used resource set:
	final HenshinResourceSet resourceSet;
	
	// Used transformation engine:
	final Engine engine;
	
	/**
	 * Default constructor.
	 * @param path Path to the example files.
	 */
	public CombBenchmark(String path) {
		resourceSet = new HenshinResourceSet(path);
		engine = new EngineImpl();
	}

	/**
	 * Build a grid structure which will be stored in the argument graph.
	 * It is assumed that the graph is empty on the invocation.
	 * @param graph Target graph.
	 * @param width Width of the grid.
	 * @param height Height of the grid.
	 * @param sparse Determines whether the grid is spares (separated columns)
	 * @return Application time in milliseconds.
	 */
	public long buildGrid(EGraph graph, int width, int height, boolean sparse) {
		
		// Load the transformation system and unit:
		TransformationSystem system = resourceSet.getTransformationSystem(sparse ? "grid-sparse.henshin" : "grid-full.henshin");
		TransformationUnit unit = system.findUnitByName("buildGrid");
		
		// Apply the unit:
		UnitApplication application = new UnitApplicationImpl(engine);
		application.setUnit(unit);
		application.setEGraph(graph);
		application.setParameterValue("width", width);
		application.setParameterValue("height", height);
		
		long time = System.currentTimeMillis();
		application.execute(null);
		time = System.currentTimeMillis() - time;
		
		// Sanity check whether to make sure the grid is correct:
		int expectedNodes = height*width+1;
		int expectedEdges = sparse ? (width*height) + (width/2)*(3*height-2) :
		                             (width*height) + (height-1)*width + height*(width-1);
		if (graph.size()!=expectedNodes || InterpreterUtil.countEdges(graph)!=expectedEdges) {
			throw new AssertionError("Generated grid incorrect");
		}
		return time;
		
	}

	/**
	 * Build the comb pattern of a given width. This executes a high-order transformation
	 * whose target model is a Henshin transformation rule.
	 * @param width Width of the comb pattern.
	 * @return The generated rule.
	 */
	public Rule buildCombPattern(int width) {
		
		// Load the transformation system and unit for building the rule:
		TransformationSystem system = resourceSet.getTransformationSystem("comb.henshin");
		TransformationUnit unit = system.findUnitByName("buildCombPattern");
		
		// Create a copy of the initial rule and prepare the EGraph:
		Rule rule = EcoreUtil.copy(system.findRuleByName("combPattern"));
		EGraph graph = new EGraphImpl();
		graph.addTree(rule);
		for (EPackage epackage : system.getImports()) {
			graph.addTree(epackage);
		}
		
		// Apply the unit for building the pattern:
		UnitApplication application = new UnitApplicationImpl(engine);
		application.setUnit(unit);
		application.setEGraph(graph);
		application.setParameterValue("width", width);
		application.execute(null);
		
		// Sanity check whether the rule is correct:
		int expectedNodes = 2*width;
		int expectedEdges = 2*width-1;
		if (rule.getLhs().getNodes().size()!=expectedNodes || rule.getLhs().getEdges().size()!=expectedEdges) {
			throw new AssertionError("Generated comb pattern incorrect");
		}
		
		// Optional saving of the generated rule:
		//system = HenshinFactory.eINSTANCE.createTransformationSystem();
		//system.getRules().add(rule);
		//benchmark.resourceSet.saveObject(system, "generated-rule.henshin");
		return rule;
		
	}
	
	/**
	 * Find all matches for the comb pattern in the grid. This checks whether
	 * the number of matches is correct and returns the required time for the
	 * match finding in milliseconds.
	 */
	public long matchCombPattern(EGraph graph, int gridWidth, int gridHeight, boolean sparse, int patternWidth, Rule combPattern) {

		long time = System.currentTimeMillis();
		int foundMatches = InterpreterUtil.findAllMatches(engine, combPattern, graph, null).size();
		time = System.currentTimeMillis() - time;

		// Check whether the number of matches is correct:
		int expectedMatches = sparse ? 0 : (gridWidth-patternWidth+1) * (gridWidth-1);
		if (expectedMatches!=foundMatches) {
			throw new AssertionError("Expected " + expectedMatches + " for the comb pattern, but found " + foundMatches);
		}
		return time;

	}
	
	/**
	 * Run the complete benchmark.
	 * @param path Path to the example files.
	 */
	public static void run(String path) {
		
		System.out.println("Starting comb benchmark...");
		System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory()/1024/1024 + "M\n");

		CombBenchmark benchmark = new CombBenchmark(path);
		benchmark.engine.getOptions().put(Engine.OPTION_SORT_VARIABLES, false);
		EGraph grid = new EGraphImpl();
		
		// Benchmark for full grid:
		System.out.println("Benchmark for generating sparse grid...");
		System.out.println("Nodes\tTime");
		for (int i=10; i<=100; i+=10) {
			System.out.println((i*i) + "\t" + benchmark.buildGrid(grid, i, i, true));
			grid.clear();
		}
		
		// Benchmark for generating sparse grid:
		System.out.println("\nBenchmark for generating full grid...");
		System.out.println("Nodes\tTime");
		for (int i=10; i<=60; i+=10) {
			System.out.println((i*i) + "\t" + benchmark.buildGrid(grid, i, i, false));
			grid.clear();
		}
		
		// Benchmark for matching comb pattern:
/*		System.out.println("\nBenchmark for matching comb pattern...");
		System.out.println("Nodes\tTime");
		for (int i=10; i<=60; i+=10) {
			System.out.println((i*i) + "\t" + benchmark.buildGrid(grid, i, i, false));
			grid.clear();
		}
*/		
		// Build the comb pattern:
		//Rule rule = benchmark.buildCombPattern(10);

	}
		
	public static void main(String[] args) {
		run(PATH);
	}

}
