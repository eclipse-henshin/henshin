package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * Abstract state space manager implementation which keeps track
 * of the distances of states from the initial states.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceManagerWithStateDistance extends AbstractStateSpaceManager {
	
	// Distances of states from the initial states:
	private int[] distances;
	
	/**
	 * Default constructor.
	 * @param stateSpace The state space.
	 */
	public AbstractStateSpaceManagerWithStateDistance(StateSpace stateSpace) {
		super(stateSpace);
		distances = new int[0];
		checkDistanceArraySize();
		updateDistances(stateSpace.getInitialStates());
	}
	
	/*
	 * Check whether the distances array is large enough and
	 * resize it if necessary.
	 */
	private void checkDistanceArraySize() {
		if (distances.length<getStateSpace().getStates().size()) {
			int newSize = (getStateSpace().getStates().size() * 2) + 4;
			int[] newDistances = Arrays.copyOf(distances, newSize);
			Arrays.fill(newDistances, distances.length, newDistances.length, -1);
			distances = newDistances;
		}
	}
	
	/*
	 * Compute the distances of a list of states
	 * and all its successor states.
	 */
	private void updateDistances(Collection<State> states) {
		
		// visited states and current:
		Set<State> visited = new HashSet<State>();
		Set<State> current = new HashSet<State>();
		for (State state : states) {
			if (state!=null) current.add(state);
		}
		
		// breadth-first search:
		while (!current.isEmpty()) {
			
			// compute the distance of the current states:
			for (State state : current) {
				int d = -1;
				int c;
				if (state.isInitial()) {
					d = 0;
				} else {
					for (Transition incoming : state.getIncoming()) {
						c = distances[incoming.getSource().getIndex()];
						if (c>=0 && (d<0 || d>c+1)) {
							d = c+1;   // +1 for the extra transition
						}
					}
				}
				distances[state.getIndex()] = d;
				visited.add(state);
			}
			
			// compute the successor states:
			Set<State> successors = new HashSet<State>();
			State target;
			for (State state : current) {
				for (Transition outgoing : state.getOutgoing()) {
					target = outgoing.getTarget();
					if (!visited.contains(target)) {
						successors.add(target);
					}
				}
			}
			current = successors;
		}
	}
	
	@Override
	protected void notifyCreateOpenState(State state) {
		checkDistanceArraySize();
		distances[state.getIndex()] = -1;
	}
	
	@Override
	public void notifyCreateInitialState(State state) {
		distances[state.getIndex()] = 0;
	}

	@Override
	public void notifyRemoveState(State state) {
		checkDistanceArraySize();
		updateDistances(getStateSpace().getInitialStates());
	}

	@Override
	public void notifyResetStateSpace() {
		checkDistanceArraySize();
		updateDistances(getStateSpace().getInitialStates());
	}

	@Override
	protected void notifyCreateTransition(Transition transition) {
		updateDistances(Collections.singleton(transition.getTarget()));
	}
	
	public int getStateDistance(State state) {
		return distances[state.getIndex()];
	}
	
}
