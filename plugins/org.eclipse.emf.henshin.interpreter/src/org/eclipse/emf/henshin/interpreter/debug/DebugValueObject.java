package org.eclipse.emf.henshin.interpreter.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.henshin.interpreter.EGraph;

public class DebugValueObject extends HenshinDebugValue {
	
	private Object value;

	public DebugValueObject(IDebugTarget target, EGraph graph, String declaredType, Object value, int indexInDomain) {
		super(target, graph, declaredType);
		
		// needed to set value breakpoint
		this.indexInDomain = indexInDomain;
		
		valueString = value.toString();
		actualType = declaredType == null ? value.getClass().getName() : declaredType;
		childrenVariables = null;
	}

	@Override
	public IVariable[] getVariables() throws DebugException {
		return new HenshinDebugVariable[0];
	}

}
