package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.StateSpaceValidationResult;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;

/**
 * Job for running a state space validator.
 * @author Christian Krause
 */
public class ValidateStateSpaceJob extends AbstractStateSpaceJob {
	
	// State space validator:
	private StateSpaceValidator validator;
	
	// Property to be checked:
	private String property;
	
	// Validation result:
	private StateSpaceValidationResult result;
	
	/**
	 * Default constructor.
	 * @param validator State space validator.
	 * @param manager State space manager.
	 */
	public ValidateStateSpaceJob(StateSpaceManager manager) {
		super("Validating state space", manager);
		setUser(true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			validator.setStateSpaceIndex(getStateSpaceManager());
			validator.setProperty(property);
			result = validator.validate(monitor);
		} catch (Exception e) {
			return new Status(IStatus.ERROR, StateSpacePlugin.PLUGIN_ID, 0, "Error validating property", e);
		}
		return Status.OK_STATUS;
	}
	
	public StateSpaceValidationResult getValidationResult() {
		return result;
	}

	public void setValidator(StateSpaceValidator validator) {
		this.validator = validator;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
