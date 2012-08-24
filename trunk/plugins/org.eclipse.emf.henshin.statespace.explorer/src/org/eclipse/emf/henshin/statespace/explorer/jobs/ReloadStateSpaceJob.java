/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;

/**
 * Job for loading a state space manager.
 * @author Christian Krause
 */
public class ReloadStateSpaceJob extends Job {
	
	// State space manager.
	private StateSpaceManager manager;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public ReloadStateSpaceJob(StateSpaceManager manager) {
		super("Reload state space");
		this.manager = manager;
		setPriority(LONG);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			manager.reload(monitor);
		} catch (Throwable e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error reloading state space", e);
		}
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
	}
	
	/**
	 * Get the loaded state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
}
