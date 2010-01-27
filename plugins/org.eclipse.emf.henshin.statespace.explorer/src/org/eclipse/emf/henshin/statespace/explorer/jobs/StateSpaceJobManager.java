package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.IrreversibleCommand;
import org.eclipse.gef.EditDomain;
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
	
	// Job for exploring the state space:
	private ExploreStateSpaceJob exploreJob;
	
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
		this.reloadJob = new ReloadStateSpaceJob(stateSpaceManager);
		this.layoutJob = new LayoutStateSpaceJob(stateSpaceManager.getStateSpace(), Display.getCurrent());
		this.exploreJob = new ExploreStateSpaceJob(stateSpaceManager, editDomain);
	}
	
	/**
	 * Start the background spring layouter job.
	 */
	public LayoutStateSpaceJob startLayoutJob() {
		if (isTerminated(layoutJob)) {
			editDomain.getCommandStack().execute(new IrreversibleCommand("start layouter"));
			layoutJob.schedule();			
		}
		return layoutJob;
	}
	
	/**
	 * Start the reload job.
	 */
	public ReloadStateSpaceJob startReloadJob() {
		if (isTerminated(reloadJob)) reloadJob.schedule();
		return reloadJob;
	}

	/**
	 * Start the explore job.
	 */
	public ExploreStateSpaceJob startExploreJob() {
		if (isTerminated(exploreJob)) exploreJob.schedule();
		return exploreJob;
	}

	/**
	 * Stop the spring layouter job.
	 */
	public void stopLayoutJob() {
		stop(layoutJob);
	}

	/**
	 * Stop the reload job.
	 */
	public void stopReloadJob() {
		stop(reloadJob);
	}

	/**
	 * Stop the explore job.
	 */
	public void stopExploreJob() {
		stop(exploreJob);
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
		return job.getState()==Job.NONE;
	}
	
	/**
	 * Stop all background jobs.
	 */
	public void stopAllJobs() {
		stopLayoutJob();
	}
	
	/**
	 * Get the layout job.
	 * @return layout job.
	 */
	public LayoutStateSpaceJob getLayoutJob() {
		return layoutJob;
	}
	
	/**
	 * Get the reload job.
	 * @return reload job.
	 */
	public ReloadStateSpaceJob getReloadJob() {
		return reloadJob;
	}

	/**
	 * Get the explore job.
	 * @return explore job.
	 */
	public Job getExploreJob() {
		return exploreJob;
	}

	public StateSpaceManager getStateSpaceManager() {
		return stateSpaceManager;
	}

	
}
