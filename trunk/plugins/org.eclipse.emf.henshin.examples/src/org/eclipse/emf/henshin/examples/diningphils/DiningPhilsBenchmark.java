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
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResourceSet;
import org.eclipse.emf.henshin.statespace.util.StateSpaceExplorationHelper;

public class DiningPhilsBenchmark {

	public static void main(String[] args) {

		// Create a resource set with a base directory:
		StateSpaceResourceSet resourceSet = new StateSpaceResourceSet(
				"src/org/eclipse/emf/henshin/examples/diningphils/model");
		
		// Load the state space and create a state space manager:
		StateSpace stateSpace = resourceSet.getStateSpace("3-phils.statespace");
		StateSpaceManager manager = StateSpaceFactory.eINSTANCE.createStateSpaceManager(
				stateSpace, Runtime.getRuntime().availableProcessors());
		
		// Find the rule for adding a philosopher:
		Rule createPhilRule = stateSpace.getRules().get(0).
				getTransformationSystem().findRuleByName("createPhil");

		// Now do the benchmark...
		try {
			for (int phils=3; true; phils++) {
				
				// First reset the state space:
				manager.resetStateSpace();
				
				// Then explore it again:
				long time = System.currentTimeMillis();
				int expectedStates = (int) Math.pow(3, phils);
				StateSpaceExplorationHelper.doExploration(manager, expectedStates, new NullProgressMonitor());
				if (stateSpace.getStateCount()!=expectedStates || !stateSpace.getOpenStates().isEmpty()) {
					throw new StateSpaceException("Unexpected number of states");
				}
				time = (System.currentTimeMillis() - time) / 1000;
				
				System.out.println("Philosophers: " + phils);
				System.out.println("States: " + stateSpace.getStateCount());
				System.out.println("Transitions: " + stateSpace.getTransitionCount());
				System.out.println("Time: " + time + "s\n");
				
				// Add a philosopher:
				EmfGraph initialStateGraph = manager.getModel(stateSpace.getInitialStates().get(0)).getEmfGraph();
				EmfEngine engine = new EmfEngine(initialStateGraph);
				RuleApplication app = new RuleApplication(engine, createPhilRule);
				app.apply();
				
			}
			
		} catch (StateSpaceException e) {
			e.printStackTrace();
		}
		
	}
	
}
