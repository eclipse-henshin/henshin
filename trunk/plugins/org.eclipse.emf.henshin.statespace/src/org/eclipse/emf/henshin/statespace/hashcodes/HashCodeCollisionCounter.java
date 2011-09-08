package org.eclipse.emf.henshin.statespace.hashcodes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Utility class for counting the number of hashcode
 * collisions in a state space.
 * @author Christian Krause
 */
public class HashCodeCollisionCounter {

	public static double getNumStatesPerHash(StateSpace stateSpace) {
		
		// Count the number of states per hash code:
		Map<Integer,Integer> numStatesPerHash = new HashMap<Integer,Integer>();
		for (State state : stateSpace.getStates()) {
			int hashcode = state.getHashCode();
			Integer numStates = numStatesPerHash.get(hashcode);
			if (numStates==null) {
				numStatesPerHash.put(hashcode, 1);
			} else {
				numStatesPerHash.put(hashcode, numStates+1);
			}
		}
		
		// Calculate the average:
		int sum = 0;
		for (Integer count : numStatesPerHash.values()) {
			sum += count;
		}
		double average = (double) sum / numStatesPerHash.values().size();
		
		// Done.
		return average;
		
	}
	
}
