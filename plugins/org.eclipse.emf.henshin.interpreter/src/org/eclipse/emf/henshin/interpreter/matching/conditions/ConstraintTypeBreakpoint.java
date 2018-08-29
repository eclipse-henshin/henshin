package org.eclipse.emf.henshin.interpreter.matching.conditions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition.ConstraintType;

public class ConstraintTypeBreakpoint extends HenshinBreakpoint {

	/**
	 * Retrieve the brekpoint's type. (type of the value we set the breakpoint for)
	 * @return
	 */
	public ConstraintType getType() {
		return ConstraintType.valueOf(getAttribute("Type", ConstraintType.NONE.toString()));
	}

	/**
	 * Set the type string (type of value) of the breakpoint to be represented in the debugger ui.
	 * @param type
	 */
	public void setType(ConstraintType type) {
		try {
			setAttribute("Type", type.name());
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
		setMessage("Constraint type breakpoint on '" + getType() + "'");
	}
	
}
