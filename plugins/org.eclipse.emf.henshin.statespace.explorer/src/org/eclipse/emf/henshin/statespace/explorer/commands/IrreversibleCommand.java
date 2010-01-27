package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.gef.commands.Command;

/**
 * A command that cannot be undone.
 * @author Christian Krause.
 */
public class IrreversibleCommand extends Command {
	
	/**
	 * Constructor.
	 * @param name Name of the command.
	 */
	public IrreversibleCommand(String name) {
		super(name);
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
