package org.eclipse.emf.henshin.interpreter.debug;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;

public class HenshinDebugVariable extends HenshinDebugElement implements IVariable {
	
	private String name;
	private HenshinDebugValue value;
	private Variable variable;
	private final boolean isConstraintType;
	private final boolean isConstraint;
	
	/**
	 * for a variable with a primitive / human readable value
	 */
	public HenshinDebugVariable(IDebugTarget target, String name, String value, Variable variable) {
		this(target, name, value, variable.typeConstraint.type.getName());
		this.variable = variable;
	}
	
	public HenshinDebugVariable(IDebugTarget target, String name, String value, String declaredType) {
		this(target, name, new DebugValueObject(target, null, declaredType, value, -1));
	}

	/**
	 * for a variable whose value contains child variables
	 */
	public HenshinDebugVariable(IDebugTarget target, String name, HenshinDebugValue value) {
		super(target);
		this.name = name;
		this.value = value;
		isConstraintType = "Constraint Type".equals(name);
		isConstraint = "Constraint".equals(name);
	}
	
	public boolean isConstraint() {
		return isConstraint;
	}
	
	public boolean isConstraintType() {
		return isConstraintType;
	}
	
	public Variable getVariable() {
		return variable;
	}

	@Override
	public void setValue(String expression) throws DebugException {
		this.value = new DebugValueEObject(getDebugTarget(), null, expression, (EObject) value, -1);
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
		// TODO: Return correct string
		return value.toString();
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
