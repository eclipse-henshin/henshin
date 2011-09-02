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
	private int expectedDuration = 500;
	
	// Duration of the last exploration step:
	private int lastDuration = expectedDuration;
	
	// Number of states to be explored at once:
	private int blockSize = 10;
	
	// Next states to be explored:
	private List<State> nextStates;
	
	// Whether to generate locations:
	private boolean generateLocations;
	
	// Count the number of steps performed:
	private int steps;
	
	// Record how fast we were during the last steps:
	private double[] lastSpeeds;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public StateSpaceExplorationHelper(StateSpaceManager manager) {
		this.manager = manager;
		this.generateLocations = false;
		this.nextStates = new ArrayList<State>();
		this.lastSpeeds = new double[10];
		this.steps = 0;
	}
	
	/**
	 * Do an exploration step.
	 * @throws StateSpaceException On state space errors.
	 */
	public boolean doExplorationStep() throws StateSpaceException {
		
		// Measure how long it takes:
		long startTime = System.currentTimeMillis();
		
		// Adjust the number of states to be explored in one step:
		double speedChange = rangeCheck((double) expectedDuration / (double) lastDuration, 0.1, 10);
		blockSize = rangeCheck((int) ((double) blockSize * speedChange), 3, 1000);
		
		/* Update the list of next states to be explored. */
		
		// Filter out irrelevant states:
		filterNextStates();
		
		// Check if we have enough states in the list:
		if (nextStates.size()<blockSize) {
			for (State open : manager.getStateSpace().getOpenStates()) {
				if (!nextStates.contains(open)) {
					nextStates.add(open);
				}
				if (nextStates.size()>=2*blockSize) {
					filterNextStates();
					if (nextStates.size()>=blockSize) {
						break;
					}
				}
			}
		}
		
		// Filter out distant states:
		filterNextStates();
		
		// Nothing left to do?
		if (nextStates.isEmpty()) {
			return false;
		}
		
		// States to be explored right now:
		List<State> exploreNow = (nextStates.size()<=blockSize) ? 
				nextStates : nextStates.subList(0, blockSize);
		
		// Explore the next states:
		List<State> result = manager.exploreStates(exploreNow, generateLocations);
		
		// We want to use the new states in the next step:
		nextStates.addAll(0, result);
		
		// Update the last duration value:
		lastDuration = rangeCheck((int) (System.currentTimeMillis() - startTime), 1, 10*expectedDuration);
		
		// Record the speed:
		lastSpeeds[steps % lastSpeeds.length] = (1000.0d * (double) blockSize) / (double) lastDuration;
		
		// Increase steps count:
		steps++;
		
		// Done for this cycle.
		return true;
		
	}
	
	private void filterNextStates() {
		int maxStateDistance = manager.getStateSpace().getMaxStateDistance();
		for (int i=0; i<nextStates.size(); i++) {

			// Not open?
			if (!nextStates.get(i).isOpen()) {
				nextStates.remove(i--);
				continue;
			}

			// To distant?
			if (maxStateDistance>=0 && maxStateDistance<=manager.getStateDistance(nextStates.get(i))) {
				nextStates.remove(i--);
				continue;
			}

		}
	}
	
	private int rangeCheck(int value, int min, int max) {
		return (value<min) ? min : ((value>max) ? max : value);
	}

	private double rangeCheck(double value, double min, double max) {
		return (value<min) ? min : ((value>max) ? max : value);
	}

	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
	public void setStepDuration(int stepDuration) {
		expectedDuration = stepDuration;
	}
	
	public void setGenerateLocations(boolean generateLocations) {
		this.generateLocations = generateLocations;
	}
	
	public double getCurrentSpeed() {
		if (steps<=0) {
			return 0d;
		} else {
			double speed = 0d;
			int count = Math.min(steps, lastSpeeds.length);
			for (int i=0; i<count; i++) {
				speed += lastSpeeds[i];
			}
			return speed / (double) count;
		}
	}
	
}
