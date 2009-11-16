package org.eclipse.emf.henshin.statespace.explorer.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.gef.commands.Command;

/**
 * 
 * @author Christian Krause
 */
public class StateExploreCommand extends Command {
	
	// State to be explored.
	private State state;
	
	// State space to be added to.
	private final StateSpace stateSpace;
	
	// New states:
	private List<State> states;
	
	// New transitions:
	private List<Transition> transitions;
	
	/**
	 * Default constructor.
	 * @param state State to be expored.
	 * @param stateSpace State space.
	 */
	public StateExploreCommand(State state, StateSpace stateSpace) {
		this.state = state;
		this.stateSpace = stateSpace;
		this.states = new ArrayList<State>();
		this.transitions = new ArrayList<Transition>();
		setLabel("exploring state");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return state!=null && stateSpace!=null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		State newState = new State("s" + stateSpace.getStates().size());
		Transition newTransition = new Transition("test");
		
		states.add(newState);
		transitions.add(newTransition);
		
		redo();
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		
		// Add the states to the state space:
		for (State current : states) {
			stateSpace.getStates().add(current);			
		}
		
		// Add the transitions:
		for (int i=0; i<transitions.size(); i++) {
			transitions.get(i).reconnect(state, states.get(i));
		}
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		
		// Remove the states from the state space:
		for (State current : states) {
			stateSpace.getStates().remove(current);			
		}
		
		// Remove the transitions:
		for (int i=0; i<transitions.size(); i++) {
			transitions.get(i).disconnect();
		}
		
	}
	
}