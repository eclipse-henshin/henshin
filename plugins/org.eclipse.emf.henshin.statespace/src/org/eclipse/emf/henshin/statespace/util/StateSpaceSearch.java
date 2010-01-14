package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * Abstract state space search class.
 * @generated NOT
 * @author Christian Krause
 */
public abstract class StateSpaceSearch {
	
	// Visited states.
	private Set<State> visited = new HashSet<State>();
	
	/**
	 * Visit a state. 
	 * @param state State to be visited.
	 * @return <code>true</code> if the traversal should continue.
	 */
	protected abstract boolean visit(State state);
	
	/**
	 * Perform a breadth-first search.
	 * @param states Start states.
	 * @param reverse Flag indicating if the traversal should be in reverse direction.
	 */
	public synchronized void breadthFirst(List<State> states, boolean reverse) {
		
		visited.clear();
		while (!states.isEmpty()) {
			
			// Collect the new states:
			List<State> newStates = new ArrayList<State>();
			
			for (State state : states) {
				
				// Visit the current state:
				if (visited.contains(state)) continue;					
				visited.add(state);
				if (!visit(state)) continue;					

				// Follow the transitions:
				List<Transition> transitions = reverse ? state.getIncoming() : state.getOutgoing();
				for (Transition transition : transitions) {
					State next = reverse ? transition.getSource() : transition.getTarget();
					if (!visited.contains(next)) newStates.add(next);
				}
			}
			states = newStates;
		}
		
	}
	
	/**
	 * Perform a breadth-first search, starting at the initial states.
	 * @param stateSpace State space.
	 */
	public void breadthFirst(StateSpace stateSpace, boolean reverse) {
		breadthFirst(getInitialStates(stateSpace), reverse);
	}
	
	/**
	 * Get the set of visited states during the last search.
	 * @return Visited states.
	 */
	public Set<State> getVisitedStates() {
		return visited;
	}
	
	/*
	 * Find the initial states.
	 */
	private List<State> getInitialStates(StateSpace stateSpace) {
		List<State> initial = new ArrayList<State>();
		for (State state : stateSpace.getStates()) {
			if (state.isInitial()) initial.add(state);
		}
		return initial;
	}
	
	/**
	 * Remove all unreachable states from a state space.
	 * @param stateSpace State space.
	 */
	public static void removeUnreachableStates(StateSpace stateSpace) {
		StateSpaceSearch search = new StateSpaceSearch() {
			@Override
			protected boolean visit(State state) {
				return true;
			}
		};
		search.breadthFirst(stateSpace, false);
		stateSpace.getStates().retainAll(search.getVisitedStates());
	}
	
}
