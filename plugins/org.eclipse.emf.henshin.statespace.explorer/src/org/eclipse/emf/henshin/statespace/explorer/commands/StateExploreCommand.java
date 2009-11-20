package org.eclipse.emf.henshin.statespace.explorer.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.impl.StateAttributes;
import org.eclipse.gef.commands.Command;

/**
 * 
 * @author Christian Krause
 */
public class StateExploreCommand extends Command {
	
	// State to be explored.
	private State state;
	
	// New states:
	private List<State> states;
	
	// New transitions:
	private List<Transition> transitions;
	
	/**
	 * Default constructor.
	 * @param state State to be explored.
	 * @param stateSpace State space.
	 */
	public StateExploreCommand(State state) {
		this.state = state;
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
		return state!=null && state.getStateSpace()!=null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		
		State newState = StateSpaceFactory.INSTANCE.createState();
		newState.setName("s" + state.getStateSpace().getStates().size());
		StateAttributes.setLocation(newState,StateAttributes.getMovedLocation(state,0,100));
		
		Transition newTransition = StateSpaceFactory.INSTANCE.createTransition();
		newTransition.setTarget(newState);
		
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
		
		// Mark the state as explored:
		StateAttributes.setExplored(state,true);
		
		// Add the states to the state space:
		for (State current : states) {
			state.getStateSpace().getStates().add(current);			
		}
		
		// Add the transitions:
		for (int i=0; i<transitions.size(); i++) {
			transitions.get(i).setSource(state);
			transitions.get(i).setTarget(states.get(i));
		}
		
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {

		StateAttributes.setExplored(state,false);

		// Remove the states from the state space:
		for (State current : states) {
			state.getStateSpace().getStates().remove(current);			
		}
		
		// Remove the transitions:
		for (int i=0; i<transitions.size(); i++) {
			transitions.get(i).setSource(null);
			transitions.get(i).setTarget(null);			
		}
		
	}
	
}