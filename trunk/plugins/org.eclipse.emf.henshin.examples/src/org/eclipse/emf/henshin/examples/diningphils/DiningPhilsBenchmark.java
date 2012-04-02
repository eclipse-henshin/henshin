package org.eclipse.emf.henshin.examples.diningphils;

import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResourceFactory;

public class DiningPhilsBenchmark {

	public static void main(String[] args) {

		StateSpaceResourceFactory.registerInRuntime();
		
		// Create a resource set:
		HenshinResourceSet resourceSet = new HenshinResourceSet();
		
		// Register the dynamic package:
//		resourceSet.registerEPackages(
//				"src/org/eclipse/emf/henshin/examples/sierpinski/model/sierpinski.ecore");

		// Load the state space:
		StateSpace stateSpace = (StateSpace) resourceSet.getFirstRoot(
				"src/org/eclipse/emf/henshin/examples/diningphils/model/8-phils.statespace");
		System.out.println("loaded " + stateSpace);
	}
	
}
