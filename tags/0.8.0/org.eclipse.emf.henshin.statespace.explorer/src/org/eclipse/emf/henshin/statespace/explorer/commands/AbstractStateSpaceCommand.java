/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.commands;

import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
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
	private Throwable exception;
	
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
	 * @throws StateSpaceException If the state space is tainted.
	 */
	public void doExecute() throws StateSpaceException {
		doRedo();
	}

	/**
	 * Undo the command.
	 * @throws StateSpaceException If the state space is tainted.
	 */
	public void doUndo() throws StateSpaceException {
		// Do nothing.
	}

	/**
	 * Redo the command.
	 * @throws StateSpaceException If the state space is tainted.
	 */
	public void doRedo() throws StateSpaceException {
		// Do nothing.
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public final void execute() {
		try {
			doExecute();
		} catch (Throwable e) {
			StateSpaceExplorerPlugin.getInstance().logError("Error while trying to " + getLabel(), e);
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
		} catch (Throwable e) {
			StateSpaceExplorerPlugin.getInstance().logError("Error while trying to " + getLabel(), e);
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
		} catch (Throwable e) {
			StateSpaceExplorerPlugin.getInstance().logError("Error while trying to " + getLabel(), e);
			this.exception = e;
		}
	}
	
	/**
	 * Get the exception of this command.
	 * @return Exception.
	 */
	public Throwable getException() {
		return exception;
	}
	
}
