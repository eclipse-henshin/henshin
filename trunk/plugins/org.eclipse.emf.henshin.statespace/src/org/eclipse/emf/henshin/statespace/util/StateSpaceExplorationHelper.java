package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceDebug;

/**
 * An exploration helper class.
 * 
 * @author Christian Krause
 */
public class StateSpaceExplorationHelper {
	
	// Minimum amount of free memory that should be available: 15%.
	private static final double MIN_FREE_MEMORY = 0.15;
	
	// State space manager:
	private final StateSpaceManager manager;

	// Expected duration of an exploration step in milliseconds:
	private int expectedDuration = 2000;
	
	// Duration of the last exploration step:
	private int lastDuration = expectedDuration;
	
	// Number of states to be explored at once:
	private int blockSize, minBlockSize, maxBlockSize;
	
	// Next states to be explored:
	private Set<State> nextStates;
	
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
		this.nextStates = new LinkedHashSet<State>();
		this.lastSpeeds = new double[10];
		this.steps = 0;
		this.blockSize = 10;
		this.minBlockSize = manager.getNumThreads() * 4;
		this.maxBlockSize = 2000;
	}
	
	/**
	 * Do an exploration step.
	 * @throws StateSpaceException On state space errors.
	 */
	public boolean doExplorationStep() throws StateSpaceException {
		
		// Measure how long it takes:
		long startTime = System.currentTimeMillis();
		
		// Adjust the number of states to be explored in one step:
		double speedChange = rangeCheck((double) expectedDuration / (double) lastDuration, 0.5, 2);
		if (!StateSpaceDebug.ENFORCE_DETERMINISM) {
			blockSize = rangeCheck((int) ((double) blockSize * speedChange), minBlockSize, maxBlockSize);
		}
		
		/* Update the list of next states to be explored. */
		int maxStateDistance = manager.getStateSpace().getMaxStateDistance();
		
		// Add new states to be explored:
		for (State open : manager.getStateSpace().getOpenStates()) {
			if (nextStates.size()>=blockSize) {
				break;
			}
			if (maxStateDistance<0 || maxStateDistance<=manager.getStateDistance(open)) {
				nextStates.add(open);
			}
		}
		
		// Nothing left to do?
		if (nextStates.isEmpty()) {
			return false;
		}
		
		// How many state we can really explore:
		int realSize = Math.min(blockSize, nextStates.size());	
		
		// States to be explored right now:
		List<State> exploreNow = new ArrayList<State>(realSize);
		Iterator<State> it = nextStates.iterator();
		for (int i=0; i<realSize; i++) {
			exploreNow.add(it.next());
			it.remove();
		}
		
		// Explore the next states:
		List<State> result = manager.exploreStates(exploreNow, generateLocations);
		
		// We want to use the new states in the next step:
		Set<State> oldNextStates = nextStates;
		nextStates = new LinkedHashSet<State>();
		nextStates.addAll(result);
		nextStates.addAll(oldNextStates);
		
		// Update the last duration value:
		lastDuration = rangeCheck((int) (System.currentTimeMillis() - startTime), 1, 10*expectedDuration);
		
		// Record the speed:
		lastSpeeds[steps % lastSpeeds.length] = (1000.0d * (double) realSize) / (double) lastDuration;

		// Increase steps count:
		steps++;
		
		// Regularly check whether we need to clear the caches:
		if (steps % 15 == 0) {
			System.gc();
			double freeMem = ((double) Runtime.getRuntime().freeMemory()) / 
							 ((double) Runtime.getRuntime().maxMemory());
			//System.out.println("Free memory: " + freeMem + "%");
			if (freeMem < MIN_FREE_MEMORY) {
				System.out.println("Clearing cache...");
				manager.clearCache();
				System.gc();
				freeMem = ((double) Runtime.getRuntime().freeMemory()) / 
								 ((double) Runtime.getRuntime().maxMemory());
			}
			
		}
		
		// Done for this cycle.
		return true;
		
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
	
	/**
	 * Automatically do a state space exploration.
	 * @param manager State space manager.
	 * @param maxStates Maximum number of states.
	 * @param monitor Progress monitor.
	 * @throws StateSpaceException On errors.
	 */
	public static void doExploration(
			StateSpaceManager manager, int maxStates, IProgressMonitor monitor) throws StateSpaceException {
		
		// Create an exploration helper instance:
		StateSpaceExplorationHelper helper = new StateSpaceExplorationHelper(manager);
		monitor.beginTask("Exploring state space...", maxStates);
		
		// Number of explored states:
		int explored = manager.getStateSpace().getStateCount();
		boolean changed = true;
		monitor.worked(explored);
		
		// Explore until finished, canceled or maximum state count is reached:
		while (changed &&
				!monitor.isCanceled() && 
				manager.getStateSpace().getStateCount()<=maxStates) {
			
			// Do an exp0loration step:
			changed = helper.doExplorationStep();
			monitor.worked(manager.getStateSpace().getStateCount() - explored);
			explored = manager.getStateSpace().getStateCount();
		}
		
		// Done.
		monitor.done();
		
	}
	
}
