package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayDeque;
import java.util.Deque;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;

public class ValidateScheduler {

	private final DiagramEditPart diagramEditPart;

	private final Deque<ValidateJob> scheduledJobs;

	private ValidateJob activeJob;
	
	private long delay;

	public ValidateScheduler(DiagramEditPart diagramEditPart, long delay) {
		this.diagramEditPart = diagramEditPart;
		this.scheduledJobs = new ArrayDeque<ValidateJob>();
		this.delay = delay;
	}

	public void scheduleValidation() {
		synchronized (this) {
			// Cancel active job:
			if (activeJob != null) {
				activeJob.cancel();
			}
			// Add new job:
			scheduledJobs.push(new ValidateJob());
		}
		// Process job queue:
		processQueue();
	}

	private void processQueue() {
		synchronized (this) {
			if (!scheduledJobs.isEmpty()) {
				activeJob = scheduledJobs.pop();
				activeJob.addJobChangeListener(jobListener);
				activeJob.schedule(delay);
			}
		}
	}

	private class ValidateJob extends Job {

		public ValidateJob() {
			super("Henshin Validation");
			setPriority(Job.BUILD);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			ValidateAction.runValidation(diagramEditPart, diagramEditPart.getDiagramView());
			return Status.OK_STATUS;
		}
	}

	private final JobChangeAdapter jobListener = new JobChangeAdapter() {
		@Override
		public void done(IJobChangeEvent event) {
			if (event.getJob() == activeJob) {
				processQueue();
			}
		}
	};
}
