/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.jobs;

import java.io.IOException;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract state space job implementation.
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceJob extends Job {
	
	// State space manager:
	private StateSpaceManager manager;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public AbstractStateSpaceJob(String name, StateSpaceManager manager) {
		super(name);
		this.manager = manager;
	}

	/**
	 * Save the state space resource.
	 */
	protected void saveStateSpace() {
		if (manager!=null) {
			// Perform saving:
			Resource resource = manager.getStateSpace().eResource();
			try {
				resource.save(null);
			} catch (IOException e) {
				StateSpaceExplorerPlugin.getInstance().logError("Error saving state space", e);
				Shell shell = Display.getDefault().getActiveShell();
				MessageDialog.openError(shell, "Save State Space", "Error saving state space. See the error log for more information.");
			}	
		}
	}
		
	/**
	 * Get the loaded state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
	/**
	 * Set the state space manager to be used.
	 * @param manager State space manager.
	 */
	public void setStateSpaceManager(StateSpaceManager manager) {
		this.manager = manager;
	}
}
