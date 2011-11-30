package org.eclipse.emf.henshin.statespace.validation;

import java.text.ParseException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Validator that checks whether a state model
 * has a single root node or not.
 * @author Christian Krause
 */
public class SingleRootStateValidator implements StateValidator {
	
	// State space index to be used:
	private StateSpaceIndex index;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.StateValidator#validate(org.eclipse.emf.henshin.statespace.State, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public ValidationResult validate(State state, IProgressMonitor monitor) throws Exception {
		Resource resource = index.getModel(state).getResource();
		monitor.done();
		if (resource.getContents().size()==1) {
			return ValidationResult.VALID;
		} else {
			return new ValidationResult(false, "State " + state.getIndex() + " has " + 
					resource.getContents().size() + " root nodes.");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#usesProperty()
	 */
	@Override
	public boolean usesProperty() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#setProperty(java.lang.String)
	 */
	@Override
	public void setProperty(String property) throws ParseException {
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#setStateSpaceIndex(org.eclipse.emf.henshin.statespace.StateSpaceIndex)
	 */
	@Override
	public void setStateSpaceIndex(StateSpaceIndex index) {
		this.index = index;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.validation.Validator#getName()
	 */
	@Override
	public String getName() {
		return "Single root";
	}

}
