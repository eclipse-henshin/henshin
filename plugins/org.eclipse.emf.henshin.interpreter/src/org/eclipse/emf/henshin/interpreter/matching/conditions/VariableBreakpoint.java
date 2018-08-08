package org.eclipse.emf.henshin.interpreter.matching.conditions;

import org.eclipse.core.runtime.CoreException;

public class VariableBreakpoint extends HenshinBreakpoint {

	/**
	 * Get type name.
	 * @return
	 */
	public String getTypeName() {
		return getAttribute("TypeName", null);
	}

	/**
	 * Set breakpoints type name representing the type of the variable.
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
		try {
			setAttribute("TypeName", typeName);
			updateMessage();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * Get node path representing the variable we set a breakpoint for.
	 * @return
	 */
	public String getPath() {
		return getAttribute("Path", null);
	}
	
	public void setPath(String path) {
		try {
			setAttribute("Path", path);
			updateMessage();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	// returns true if there is a path specified
	public boolean isSpecificVariable() {
		return this.getPath() != null;
	}
	
	// returns true if breakpoint matches a specific variable type
	public boolean isSpecificType() {
		return getTypeName() != null;
	}
	
	// returns true if no specific type or variable is specified
	public boolean isGeneric() {
		return !(this.isSpecificVariable() || this.isSpecificType());
	}
	
	/**
	 * Updates the message (label of the breakpoint in the debug view).
	 */
	private void updateMessage() {
		setMessage("Variable breakpoint on '" + getTypeName() + "' (" + getPath() + ")");
	}
}
