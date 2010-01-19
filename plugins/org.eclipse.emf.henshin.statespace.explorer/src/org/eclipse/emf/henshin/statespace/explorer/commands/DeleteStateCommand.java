package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.gef.commands.Command;

/**
 * Command for deleting states.
 * @author Christian Krause
 */
public class DeleteStateCommand extends Command {
	
	// State space manager.
	private final StateSpaceManager manager;

	// State to be deleted.
	private State state;
	
	/**
	 * Default constructor.
	 * @param state State to be deleted.
	 * @param stateSpace State space.
	 */
	public DeleteStateCommand(State state, StateSpaceManager manager) {
		this.state = state;
		this.manager = manager;
		setLabel("deleting state");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return state!=null && manager!=null && manager.getStateSpace().getStates().contains(state);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		manager.removeState(state);
	}
	
}