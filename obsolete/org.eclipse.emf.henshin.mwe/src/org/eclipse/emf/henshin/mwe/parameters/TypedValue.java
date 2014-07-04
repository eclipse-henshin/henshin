package org.eclipse.emf.henshin.mwe.parameters;

import org.eclipse.emf.mwe.core.WorkflowContext;

public abstract class TypedValue extends Value {

	protected String value;

	public void setValue(String value) {
		this.value = value;
	}	
	
	abstract Object getValue(); 
		
	public Object getValue(WorkflowContext ctx) {	
		return getValue();
	}

}
