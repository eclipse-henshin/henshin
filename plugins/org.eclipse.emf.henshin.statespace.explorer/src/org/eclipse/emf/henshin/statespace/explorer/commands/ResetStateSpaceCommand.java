package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.gef.commands.Command;

/**
 * Command that removes all derived states from a state space.
 * @author Christian Krause
 */
public class ResetStateSpaceCommand extends Command {
	
	// State space manager:
	private StateSpaceManager manager;
	
	/**
	 * Default constructor.
	 */
	public ResetStateSpaceCommand(StateSpaceManager manager) {
		this.manager = manager;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() { 
		manager.resetStateSpace();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return false;
	}

}
