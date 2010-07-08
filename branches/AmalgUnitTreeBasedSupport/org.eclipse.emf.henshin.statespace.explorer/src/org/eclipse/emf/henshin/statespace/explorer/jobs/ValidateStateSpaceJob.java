package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;
import org.eclipse.emf.henshin.statespace.StateValidator;
import org.eclipse.emf.henshin.statespace.ValidationResult;
import org.eclipse.emf.henshin.statespace.StateSpaceValidator;
import org.eclipse.emf.henshin.statespace.Validator;
import org.eclipse.emf.henshin.statespace.impl.InvariantStateSpaceValidator;

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
	private ValidationResult result;
	
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
			result = validator.validate(getStateSpaceManager().getStateSpace(), monitor);
		} catch (Exception e) {
			return new Status(IStatus.ERROR, StateSpacePlugin.PLUGIN_ID, 0, "Error validating property", e);
		}
		return Status.OK_STATUS;
	}
	
	public ValidationResult getValidationResult() {
		return result;
	}

	public void setValidator(Validator validator) {
		if (validator instanceof StateSpaceValidator) {
			this.validator = (StateSpaceValidator) validator;
		} 
		else if (validator instanceof StateValidator) {
			this.validator = new InvariantStateSpaceValidator((StateValidator) validator);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}
