package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
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
			int[] newDistances = new int[(int) (getStateSpace().getStates().size() * 1.75) + 4];
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
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#createOpenState(org.eclipse.emf.henshin.statespace.Model, int, int[])
	 */
	@Override
	protected State createOpenState(Model model, int hash, int[] location) {
		State openState = super.createOpenState(model, hash, location);
		checkDistanceArraySize();
		distances[openState.getIndex()] = -1;
		return openState;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#createInitialState(org.eclipse.emf.henshin.statespace.Model)
	 */
	@Override
	public State createInitialState(Model model) throws StateSpaceException {
		State initialState = super.createInitialState(model);
		distances[initialState.getIndex()] = 0;
		return initialState;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#removeState(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	public List<State> removeState(State state) throws StateSpaceException {
		Set<State> successors = new HashSet<State>();
		for (Transition outgoing : state.getOutgoing()) {
			successors.add(outgoing.getTarget());
		}
		List<State> removed = super.removeState(state); // perform the removal
		successors.removeAll(removed);
		updateDistances(successors);
		return removed;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#resetStateSpace()
	 */
	@Override
	public void resetStateSpace() {
		super.resetStateSpace();
		distances = new int[64];
		Arrays.fill(distances, -1);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#createTransition(org.eclipse.emf.henshin.statespace.State, org.eclipse.emf.henshin.statespace.State, org.eclipse.emf.henshin.model.Rule, int, int[])
	 */
	@Override
	protected Transition createTransition(State source, State target, Rule rule, int match, int[] paramIDs) {
		Transition transition = super.createTransition(source, target, rule, match, paramIDs);
		updateDistances(Collections.singleton(target));
		return transition;
	}
	
	public int getStateDistance(State state) {
		return distances[state.getIndex()];
	}
	
}
