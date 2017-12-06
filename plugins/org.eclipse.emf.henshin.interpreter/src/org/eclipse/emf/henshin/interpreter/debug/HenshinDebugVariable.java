package org.eclipse.emf.henshin.interpreter.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class HenshinDebugVariable extends HenshinDebugElement implements IVariable {
	
	private String name;
	private HenshinDebugValue value;
	
	/**
	 * for a variable with a primitive / human readable value
	 */
	public HenshinDebugVariable(IDebugTarget target, String name, String value, String declaredType) {
		this(target, name, new HenshinDebugValue(target, null, value, declaredType));
	}

	/**
	 * for a variable whose value contains child variables
	 */
	public HenshinDebugVariable(IDebugTarget target, String name, HenshinDebugValue value) {
		super(target);
		this.name = name;
		this.value = value;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		this.value = new HenshinDebugValue(getDebugTarget(), null, expression, value.getDeclaredType());
	}

	@Override
	public void setValue(IValue value) throws DebugException {
		setValue(value.getValueString());
	}

	@Override
	public boolean supportsValueModification() {
		return false;
	}

	@Override
	public boolean verifyValue(String expression) throws DebugException {
		return false;
	}

	@Override
	public boolean verifyValue(IValue value) throws DebugException {
		return false;
	}

	@Override
	public IValue getValue() throws DebugException {
		return value;
	}

	@Override
	public String getName() throws DebugException {
		return name;
	}

	@Override
	public String getReferenceTypeName() throws DebugException {
		return value.getDeclaredType();
	}

	@Override
	public boolean hasValueChanged() throws DebugException {
		// maybe add this in the future
		return false;
	}

	@Override
	public int hashCode() {
		//return name.hashCode()+value.hashCode();
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HenshinDebugVariable other = (HenshinDebugVariable) obj;
		return name.equals(other.name) 
				&& value.equals(other.value);
	}
}
