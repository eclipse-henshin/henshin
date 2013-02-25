package org.eclipse.emf.henshin.mwe.parameters;

import org.eclipse.emf.mwe.core.WorkflowContext;

public abstract class Value {	
	public abstract Object getValue(WorkflowContext ctx);
}
