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
package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.jobs.ExploreStateSpaceJob;
import org.eclipse.emf.henshin.statespace.impl.MultiThreadedStateSpaceManager;
import org.eclipse.gef.EditDomain;
import org.eclipse.jface.action.IAction;

/**
 * Action for exploring open states in a state space file.
 * @author Christian Krause
 */
public class ExploreStateSpaceFileAction extends AbstractStateSpaceFileAction {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Run exploration job.
		StateSpaceManager manager = getStateSpaceManager();
		ExploreStateSpaceJob job = new ExploreStateSpaceJob(manager, new EditDomain());
		
		// Adjust the number of states to be explored at one for multi-threaded state space managers:
		if (manager instanceof MultiThreadedStateSpaceManager) {
			job.setNumStatesAtOnce(10 * MultiThreadedStateSpaceManager.CPU_COUNT);
		}
		
		// Schedule the job:
		job.schedule();
		
		// We don't need the state space anymore.
		setStateSpaceManager(null);
		
	}
	
}
