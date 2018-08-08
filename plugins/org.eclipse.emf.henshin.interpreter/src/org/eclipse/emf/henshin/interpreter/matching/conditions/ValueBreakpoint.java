package org.eclipse.emf.henshin.interpreter.matching.conditions;

import org.eclipse.core.runtime.CoreException;

public class ValueBreakpoint extends HenshinBreakpoint {
	
	/**
	 * Retrieve the brekpoint's type. (type of the value we set the breakpoint for)
	 * @return
	 */
	public String getType() {
		return getAttribute("Type", "");
	}

	/**
	 * Set the type string (type of value) of the breakpoint to be represented in the debugger ui.
	 * @param type
	 */
	public void setType(String type) {
		try {
			setAttribute("Type", type);
			updateMessage();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setValueString(String valueString) {
		try {
			setAttribute("ValueString", valueString);
			updateMessage();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String getValueStringAttr() {
		return getAttribute("ValueString", "");
	}
	
	/**
	 * Retrieve index.
	 * @return
	 */
	public int getIndex() {
		return getIntAttribute("Index", -1);
	}

	/**
	 * Set an index which represents the index of the value within the domain.
	 * @param index
	 */
	public void setIndex(int index) {
		try {
			setAttribute("Index", index);
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
		setMessage("Value breakpoint on type '" + getType() + "' with value string '" + getValueStringAttr() + "' at [" + getIndex() + "]");
	}
}
