package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;

/**
 * Job for exploring states.
 * @author Christian Krause
 */
public class ExploreStatesJob extends Job {
	
	// State space manager to be used for exploration.
	private StateSpaceManager manager;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public ExploreStatesJob(StateSpaceManager manager) {
		super("Exploring states");
		this.manager = manager;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
