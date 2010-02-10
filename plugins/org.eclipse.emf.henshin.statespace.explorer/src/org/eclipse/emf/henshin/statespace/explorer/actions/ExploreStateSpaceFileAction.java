package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.henshin.statespace.explorer.jobs.ExploreStateSpaceJob;
import org.eclipse.gef.EditDomain;
import org.eclipse.jface.action.IAction;

/**
 * Action for exploring open states in a statespace file.
 * @author Christian Krause
 */
public class ExploreStateSpaceFileAction extends AbstractStateSpaceFileAction {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Run exploration job.
		ExploreStateSpaceJob job = new ExploreStateSpaceJob(getStateSpaceManager(), new EditDomain());
		job.setUser(true);
		job.setNumStatesAtOnce(20);
		job.addJobChangeListener(new StateSpaceResourceSaveAdapter());
		job.schedule();
		
	}
		
}
