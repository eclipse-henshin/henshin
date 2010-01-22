package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.TaintedStateSpaceException;

/**
 * Command for exploring a state.
 * @author Christian Krause
 */
public class ExploreStateCommand extends AbstractStateSpaceCommand {
	
	// State to be explored.
	private State state;
	
	/**
	 * Default constructor.
	 * @param state State to be explored.
	 * @param stateSpace State space.
	 */
	public ExploreStateCommand(State state, StateSpaceManager manager) {
		super("exploring state", manager);
		this.state = state;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return state!=null && getStateSpaceManager()!=null;
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
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() throws TaintedStateSpaceException {
		getStateSpaceManager().exploreState(state);
	}
	
}