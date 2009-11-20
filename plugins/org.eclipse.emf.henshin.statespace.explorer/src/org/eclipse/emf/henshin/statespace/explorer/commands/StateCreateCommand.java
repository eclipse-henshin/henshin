package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.impl.StateAttributes;
import org.eclipse.gef.commands.Command;

/**
 * Command for adding a state to a state space.
 * @author Christian Krause
 */
public class StateCreateCommand extends Command {
	
	// State to be added.
	private State state;
	
	// State space to be added to.
	private final StateSpace stateSpace;
	
	// State coordinates:
	private int[] location;
	
	// Name of the state:
	private String name;

	
	/**
	 * Default constructor.
	 * @param state State to be added.
	 * @param stateSpace State space.
	 */
	public StateCreateCommand(State state, StateSpace stateSpace) {
		this.state = state;
		this.stateSpace = stateSpace;
		this.name = "s" + stateSpace.getStates().size();
		setLabel("adding state");
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
		state.setName(name);
		StateAttributes.setLocation(state,location);
		redo();
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		stateSpace.getStates().add(state);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		stateSpace.getStates().remove(state);
	}
	
	/**
	 * Set the state coordinates.
	 * @param x X-coordinate.
	 * @param y Y-coordinate.
	 */
	public void setLocation(int... location) {
		this.location = location;
	}
	
}