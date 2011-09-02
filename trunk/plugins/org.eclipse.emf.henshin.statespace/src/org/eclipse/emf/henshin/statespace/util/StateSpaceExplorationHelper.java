package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;

/**
 * An exploration helper class.
 * @author Christian Krause
 */
public class StateSpaceExplorationHelper {

	// State space manager:
	private final StateSpaceManager manager;

	// Expected duration of an exploration step in milliseconds:
	private int expectedDuration = 1000;
	
	// Duration of the last exploration step:
	private int lastDuration = expectedDuration;
	
	// Number of states to be explored at once:
	private int blockSize = 10;
	
	// Next states to be explored:
	private List<State> nextStates;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public StateSpaceExplorationHelper(StateSpaceManager manager) {
		this.manager = manager;
		this.nextStates = new ArrayList<State>();
	}
	
	/**
	 * Do an exploration step.
	 * @throws StateSpaceException On state space errors.
	 */
	public void doExplorationStep(boolean generateLocations) throws StateSpaceException {
		
		// Measure how long it takes:
		long startTime = System.currentTimeMillis();
		
		// Adjust the number of states to be explored in one step:
		double speedChange = (double) lastDuration / (double) expectedDuration;
		if (speedChange<0.5) {
			speedChange = 0.5;
		} else if (speedChange>1.5) {
			speedChange = 1.5;
		}
		blockSize = (int) ((double) blockSize * speedChange);
		if (blockSize<1) {
			blockSize = 1;
		}
		
		/* Update the list of next states to be explored. */
		
		// First, remove closed states:
		for (int i=0; i<nextStates.size(); i++) {
			if (!nextStates.get(i).isOpen()) {
				nextStates.remove(i);
			}
		}
		
		// Check if we have enough states in the list:
		if (nextStates.size()<blockSize) {
			for (State open : manager.getStateSpace().getOpenStates()) {
				if (!nextStates.contains(open)) {
					nextStates.add(open);
				}
				if (nextStates.size()>=2*blockSize) {
					break;
				}
			}
		}
		
		// States to be explored right now:
		List<State> exploreNow = (nextStates.size()<=blockSize) ? 
				nextStates : nextStates.subList(0, blockSize);
		
		// Explore the next states:
		List<State> result = manager.exploreStates(exploreNow, generateLocations);
		
		// We want to use the new states in the next step:
		nextStates.addAll(0, result);
		
		// Update the last duration value:
		lastDuration = (int) (System.currentTimeMillis() - startTime);
		if (lastDuration==0) {
			lastDuration = 1;
		}
		
	}
	
	public void setStepDuration(int stepDuration) {
		expectedDuration = stepDuration;
	}
	
	public double getCurrentSpeed() {
		return (double) blockSize / (double) lastDuration;
	}
	
}
