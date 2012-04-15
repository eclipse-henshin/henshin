package org.eclipse.emf.henshin.examples.diningphils;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.interpreter.EmfEngine;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpaceProperties;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResourceSet;
import org.eclipse.emf.henshin.statespace.util.StateSpaceExplorationHelper;

public class DiningPhilsBenchmark {

	public static void doBenchmark(int maxPhils, int numThreads) {

		System.out.println("Starting benchmark...");
		System.out.println("NumThreads: " + numThreads);
		System.out.println("MaxMemory: " + Runtime.getRuntime().maxMemory() / (1024 * 1024) + "MB\n");

		// Create a resource set with a base directory:
		StateSpaceResourceSet resourceSet = new StateSpaceResourceSet(
				"src/org/eclipse/emf/henshin/examples/diningphils/model");
		
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
				int expectedStates = (int) Math.pow(3, phils);

				long time = System.currentTimeMillis();
				StateSpaceExplorationHelper.doExploration(manager, expectedStates, new NullProgressMonitor());
				time = (System.currentTimeMillis() - time);

				if (stateSpace.getStateCount()!=expectedStates || !stateSpace.getOpenStates().isEmpty()) {
					throw new StateSpaceException("Unexpected number of states");
				}
				
				System.out.println(phils + "\t" + 
								stateSpace.getStateCount() + "\t" + 
								stateSpace.getTransitionCount() + "\t" + 
								time);
				
				// Add a philosopher:
				EmfGraph initialStateGraph = manager.getModel(stateSpace.getInitialStates().get(0)).getEmfGraph();
				EmfEngine engine = new EmfEngine(initialStateGraph);
				RuleApplication app = new RuleApplication(engine, createPhilRule);
				app.apply();
				
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
		doBenchmark(8, threads);
		
		System.out.println("\n******* BENCHMARK ********\n");
		doBenchmark(13, threads);
		
	}

}
