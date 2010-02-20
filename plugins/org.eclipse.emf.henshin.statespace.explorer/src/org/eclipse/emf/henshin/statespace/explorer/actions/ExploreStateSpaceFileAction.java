package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.henshin.statespace.explorer.jobs.ExploreStateSpaceJob;
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
		ExploreStateSpaceJob job = new ExploreStateSpaceJob(getStateSpaceManager(), new EditDomain());
		job.setNumStatesAtOnce(10);
		job.schedule();
		
		// We don't need the state space anymore.
		disposeStateSpace();
		
	}
	
}
