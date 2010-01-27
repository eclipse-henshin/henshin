package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.StateSpaceManager;

/**
 * Command that removes all derived states from a state space.
 * @author Christian Krause
 */
public class ResetStateSpaceCommand extends AbstractStateSpaceCommand {
	
	/**
	 * Default constructor.
	 */
	public ResetStateSpaceCommand(StateSpaceManager manager) {
		super("reset state space", manager);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.commands.AbstractStateSpaceCommand#doExecute()
	 */
	@Override
	public void doExecute() { 
		getStateSpaceManager().resetStateSpace();
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
