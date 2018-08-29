package org.eclipse.emf.henshin.interpreter.matching.conditions;

import org.eclipse.core.runtime.CoreException;

public class ConstraintInstanceBreakpoint extends HenshinBreakpoint {

	/**
	 * Retrieve the brekpoint's type. (type of the value we set the breakpoint for)
	 * @return
	 */
	public String getConstraintInstance() {
		return getAttribute("ConstraintInstance", "");
	}

	public void setConstraintInstance(String constraintInstance) {
		try {
			setAttribute("ConstraintInstance", constraintInstance);
			updateMessage();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	/**
	 * Updates the message (label of the breakpoint in the debug view).
	 */
	private void updateMessage() {
		setMessage("Constraint instance breakpoint on '" + getConstraintInstance() + "'");
	}
	
}
