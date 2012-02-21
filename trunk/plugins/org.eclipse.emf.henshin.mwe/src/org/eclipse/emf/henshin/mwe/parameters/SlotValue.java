package org.eclipse.emf.henshin.mwe.parameters;

import org.eclipse.emf.mwe.core.WorkflowContext;

public class SlotValue extends Value {

	protected String slot;
	
	
	public void setSlot(String slot) {
		this.slot = slot;
	}
	
	public Object getValue(WorkflowContext ctx) {
		return ctx.get(this.slot);
	}

}
