package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.TaintedStateSpaceException;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.gef.commands.Command;

/**
 * Abstract state space command.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceCommand extends Command {
	
	// State space manager to be used.
	private StateSpaceManager manager;
	
	// Exception:
	private TaintedStateSpaceException exception;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public AbstractStateSpaceCommand(String label, StateSpaceManager manager) {
		super(label);
		this.manager = manager;
	}
	
	/**
	 * Get the state space manager.
	 * @return State space manager.
	 */
	protected StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
	/**
	 * Execute the command.
	 * @throws TaintedStateSpaceException If the state space is tainted.
	 */
	public void doExecute() throws TaintedStateSpaceException {
		doRedo();
	}

	/**
	 * Undo the command.
	 * @throws TaintedStateSpaceException If the state space is tainted.
	 */
	public void doUndo() throws TaintedStateSpaceException {
		// Do nothing.
	}

	/**
	 * Redo the command.
	 * @throws TaintedStateSpaceException If the state space is tainted.
	 */
	public void doRedo() throws TaintedStateSpaceException {
		// Do nothing.
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public final void execute() {
		try {
			doExecute();
		} catch (TaintedStateSpaceException e) {
			StateSpaceExplorerPlugin.getInstance().logError("Tainted state space", e);
			this.exception = e;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public final void undo() {
		try {
			doUndo();
		} catch (TaintedStateSpaceException e) {
			StateSpaceExplorerPlugin.getInstance().logError("Tainted state space", e);
			this.exception = e;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public final void redo() {
		try {
			doRedo();
		} catch (TaintedStateSpaceException e) {
			StateSpaceExplorerPlugin.getInstance().logError("Tainted state space", e);
			this.exception = e;
		}
	}
	
	/**
	 * Get the exception of this command.
	 * @return Exception.
	 */
	public TaintedStateSpaceException getException() {
		return exception;
	}
	
}
