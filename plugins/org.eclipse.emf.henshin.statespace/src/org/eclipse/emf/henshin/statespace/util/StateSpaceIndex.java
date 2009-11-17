package org.eclipse.emf.henshin.statespace.util;

import java.util.Arrays;

import org.eclipse.emf.henshin.statespace.State;

/**
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceIndex {
	
	// Initial minor size:
	private static int MINOR_SIZE = 4;
	
	// The actual index:
	protected State[][] states;
	
	/**
	 * Default constructor.
	 * @param size Initial size of the index.
	 */
	public StateSpaceIndex(int size) {
		this.states = new State[size][];
	}
	
	public State[] get(int hash) {
		return states[hash % states.length];
	}
	
	public void add(int hash, State state) {
		
		int index = hash % states.length;
		
		// Need to create a new array?
		if (states[index]==null) {
			states[index] = new State[MINOR_SIZE];
		}
		
		// Find the next free minor index:
		int minor = states[index].length;
		for (int i=0; i<states[index].length; i++) {
			if (states[i]==null) {
				minor = i;
				break;
			}
		}
		
		// Check if the array needs to be expanded:
		if (minor>=states[index].length) {
			states[index] = Arrays.copyOf(states[index], states[index].length*2);
		}
		
		// Add the entry:
		states[index][minor] = state;
		
	}
	
}
