package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;


/**
 * Job manager for state explorers.
 * @author Christian Krause
 */
public class StateSpaceJobManager {
	
	// Job for reloading the state space:
	private ReloadStateSpaceJob reloadJob;
	
	// Job for running the spring layouter:
	private LayoutStateSpaceJob layoutJob;
	
	// State space manager:
	private StateSpaceManager stateSpaceManager;
	
	// Edit domain for running commands:
	private EditDomain editDomain;
	
	/**
	 * Default constructor.
	 * @param stateSpaceManager State space manager.
	 */
	public StateSpaceJobManager(StateSpaceManager stateSpaceManager, EditDomain editDomain) {
		this.stateSpaceManager = stateSpaceManager;
		this.editDomain = editDomain;
	}
	
	/**
	 * Start the background spring layouter job.
	 */
	public LayoutStateSpaceJob startLayoutJob() {
		if (!isTerminated(layoutJob)) return layoutJob;
		layoutJob = new LayoutStateSpaceJob(stateSpaceManager.getStateSpace(), Display.getCurrent());
		editDomain.getCommandStack().execute(new IrreversibleCommand("start layouter"));
		layoutJob.schedule();
		return layoutJob;
	}
	
	/**
	 * Start the reload job.
	 */
	public ReloadStateSpaceJob startReloadJob() {
		if (!isTerminated(reloadJob)) return reloadJob;
		reloadJob = new ReloadStateSpaceJob(stateSpaceManager);
		reloadJob.schedule();
		return reloadJob;
	}
	
	/**
	 * Stop the background spring layouter job.
	 */
	public void stopLayoutJob() {
		stop(layoutJob);
		layoutJob = null;
	}
	
	/*
	 * Stop a given job.
	 */
	private void stop(Job job) {
		while (!isTerminated(job)) {
			try {
				job.cancel();
				job.join();
			} catch (InterruptedException e) {}
		}
	}
	
	/*
	 * Check if a job is terminated.
	 */
	private boolean isTerminated(Job job) {
		return job==null || job.getState()==Job.NONE;
	}
	
	/**
	 * Stop all background jobs.
	 */
	public void stopAllJobs() {
		stopLayoutJob();
	}

	/*
	 * Irreversible helper command.
	 */
	class IrreversibleCommand extends Command {
		
		IrreversibleCommand(String name) {
			super(name);
		}
		
		@Override
		public boolean canUndo() {
			return false;
		}
		
	}
	
	/**
	 * Get the layout job.
	 * @return layout job or <code>null</code>.
	 */
	public LayoutStateSpaceJob getLayoutJob() {
		return layoutJob;
	}
	
	/**
	 * Get the reload job.
	 * @return reload job or <code>null</code>.
	 */
	public ReloadStateSpaceJob getReloadJob() {
		return reloadJob;
	}

	public StateSpaceManager getStateSpaceManager() {
		return stateSpaceManager;
	}
	
}
