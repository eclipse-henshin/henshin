/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.examples.probbroadcast.timed;

import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.external.prism.MDPStateSpaceValidator;
import org.eclipse.emf.henshin.statespace.impl.ParallelStateSpaceManager;
import org.eclipse.emf.henshin.statespace.ocl.OCLStateValidator;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResourceSet;
import org.eclipse.emf.henshin.statespace.util.StateSpaceXYPlot;

/**
 * Timed version of the probabilistic broadcast example for wireless sensor networks.
 * 
 * @author Christian Krause
 */
public class TimedProbBroadcast {
	
	/**
	 * Relative path to the example files.
	 */
	public static final String PATH = "src/org/eclipse/emf/henshin/examples/probbroadcast/timed";
	
	// State space manager used to generate state spaces:
	public final StateSpaceManager manager;
	
	// State space resource set for loading files:
	public final StateSpaceResourceSet resourceSet;
	
	/**
	 * Default constructor.
	 */
	public TimedProbBroadcast(String path, String stateSpaceFile) {
		resourceSet = new StateSpaceResourceSet(path);
		StateSpace stateSpace = resourceSet.getStateSpace(stateSpaceFile); // load the state space
		manager = new ParallelStateSpaceManager(stateSpace); // create a state space manager
		System.out.println(" - Loaded " + stateSpace.getStateCount() + " states from " + stateSpaceFile);
	}
	
		
	/**
	 * Compute the message reception probabilities for a set of nodes and a fixed send probability. 
	 * @param nodes The nodes for which the reception probabilities shall be computed. 
	 * @param probSend The fixed send probability.
	 */
	public void fixedSendProb(int[] nodes, int LO, int UP, int CF, double probSend) throws Exception {
		
		System.out.println("\n - Constants: LO=" + LO + ", UP=" + UP + ", CF=" + CF + ", probSend="  + probSend);
		System.out.println(" - Computing reception probabilities...");
		System.out.println("   Node\tPmin\tPmax");
		
		// Build the properties to check:
		String prop = "";
		for (int i=0; i<nodes.length; i++) {
			prop = prop + "label \"T" + i + "\" = <<<OCL not self.nodes->at(" + nodes[i] + ").active >>>;\n" 
						+ " Pmin=?[F \"T" + i + "\"]  Pmax=?[F \"T" + i + "\"]\n";
		}
		
		// Create the state space validator instance and set the property:
		MDPStateSpaceValidator validator = new MDPStateSpaceValidator(manager);
		validator.setProperty(prop);
		
		// Set the time constants:
		manager.getStateSpace().getProperties().put("constants", "int LO=" + LO + ",int UP=" + UP + ",int CF=" + CF + "");
		
		// Set the send probabilities:
		manager.getStateSpace().getProperties().put("probSend1", probSend+"");
		manager.getStateSpace().getProperties().put("probSend2", "1-probSend1");
		
		// Run the validator:
		StateSpaceXYPlot plot = (StateSpaceXYPlot) validator.validate(manager.getStateSpace(), null).getResult();
		
		// Print the results:
		for (int i=0; i<nodes.length; i++) {
			System.out.println("   " + nodes[i] + "\t" + plot.getY(0, 2*i) + "\t" + plot.getY(0, 2*i+1));
		}
		
	}

	/**
	 * Compute the results for the probabilistic timed broadcast example.
	 * @param path Relative path to the model files.
	 */
	public static void run(String path) {
		TimedProbBroadcast main = new TimedProbBroadcast(path, "grid3x3.henshin_statespace");
		try {
			OCLStateValidator.register(); // we need the OCL validator
			
			//main.fixedSendProb(new int[] { 1,2,3,4 }, 5, 8, 2, 0.8);			// 2x2 grid
			//main.fixedSendProb(new int[] { 1,2,3,4 }, 5, 18, 2, 0.8);			// 2x2 grid
			
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 6, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 7, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 8, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 9, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 10, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 11, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 12, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 13, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 14, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 15, 2, 0.8);	// 3x3 grid
			main.fixedSendProb(new int[] { 2,4,3,5,7,6,8,9 }, 5, 16, 2, 0.8);	// 3x3 grid
			
		} catch (Throwable t){
			t.printStackTrace();
		} finally {
			main.manager.shutdown();  // shut down the state space manager
		}
	}
	
	public static void main(String[] args) {
		run(PATH);
	}
	
}
