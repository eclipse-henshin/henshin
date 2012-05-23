package org.eclipse.emf.henshin.examples.diningphils;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.InterpreterFactory;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpaceProperties;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResourceSet;
import org.eclipse.emf.henshin.statespace.util.StateSpaceExplorationHelper;

public class DiningPhilsBenchmark {

	/**
	 * Relative path to the example files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/diningphils";
	
	/**
	 * Perform the benchmark.
	 * @param path Relative path to the model files.
	 * @param maxPhils Maximum number of philosophers.
	 * @param numThreads Number of threads to use.
	 */
	public static void doBenchmark(String path, int maxPhils, int numThreads) {

		System.out.println("Starting benchmark...");
		System.out.println("NumThreads: " + numThreads);
		System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB\n");

		// Create a resource set with a base directory:
		StateSpaceResourceSet resourceSet = new StateSpaceResourceSet(path);
		
		// Load the state space and create a state space manager:
		StateSpace stateSpace = resourceSet.getStateSpace("3-phils.statespace");
		StateSpaceManager manager = StateSpaceFactory.eINSTANCE.createStateSpaceManager(stateSpace, numThreads);
		
		// To improve the performance, we omit the identity types:
		stateSpace.getProperties().remove(StateSpaceProperties.IDENTITY_TYPES);
		
		// Find the rule for adding a philosopher:
		Rule createPhilRule = stateSpace.getRules().get(0).
				getTransformationSystem().findRuleByName("createPhil");

		// Now do the benchmark...
		try {
			System.out.println("Phils\tStates\tTrans\tTime");

			for (int phils=3; phils<=maxPhils; phils++) {
				
				// First reset the state space:
				manager.resetStateSpace();
				for (int i=0; i<10; i++) {
					System.gc();
				}
				
				// Then explore it again:
				StateSpaceExplorationHelper helper = new StateSpaceExplorationHelper(manager);
				long time = System.currentTimeMillis();
				helper.doExploration(-1, new NullProgressMonitor());
				time = (System.currentTimeMillis() - time);

				int expectedStates = (int) Math.pow(3, phils);
				if (stateSpace.getStateCount()!=expectedStates || !stateSpace.getOpenStates().isEmpty()) {
					throw new StateSpaceException("Unexpected number of states: " +
							stateSpace.getStateCount() + "(" +
							stateSpace.getOpenStates().size() + ")");
				}
				
				System.out.println(phils + "\t" + 
								stateSpace.getStateCount() + "\t" + 
								stateSpace.getTransitionCount() + "\t" + 
								time);
				
				// Add a philosopher:
				EGraph initialStateGraph = manager.getModel(stateSpace.getInitialStates().get(0)).getEGraph();
				Engine engine = InterpreterFactory.INSTANCE.createEngine();
				RuleApplication app = InterpreterFactory.INSTANCE.createRuleApplication(engine);
				ApplicationMonitor monitor = InterpreterFactory.INSTANCE.createApplicationMonitor();
				app.setEGraph(initialStateGraph);
				app.setRule(createPhilRule);
				if (!app.execute(monitor)) {
					throw new RuntimeException("Error adding philosopher!");
				}
				
			}	
		}
		catch (StateSpaceException e) {
			e.printStackTrace();
		}
		finally {
			manager.shutdown();
			manager = null;
			for (int i=0; i<10; i++) {
				System.gc();
			}
		}
		System.out.println();
		
	}
	
	public static void main(String[] args) {
		
		int threads = Runtime.getRuntime().availableProcessors();
		
		System.out.println("\n******* WARMUP PHASE ********\n");
		doBenchmark(PATH, 8, threads);
		
		System.out.println("\n******* BENCHMARK ********\n");
		doBenchmark(PATH, 13, threads);
		
	}

}
