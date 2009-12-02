package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.gef.commands.Command;

/**
 * @author Christian Krause
 */
public class StateExploreCommand extends Command {
	
	// State to be explored.
	private State state;
	
	// State space manager.
	private StateSpaceManager manager;
	
	/**
	 * Default constructor.
	 * @param state State to be explored.
	 * @param stateSpace State space.
	 */
	public StateExploreCommand(State state, StateSpaceManager manager) {
		this.state = state;
		this.manager = manager;
		setLabel("exploring state");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return state!=null && manager!=null;
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
		manager.exploreState(state);
	}
	
}