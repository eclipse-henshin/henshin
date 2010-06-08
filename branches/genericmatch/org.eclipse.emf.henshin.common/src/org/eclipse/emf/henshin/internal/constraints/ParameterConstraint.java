package org.eclipse.emf.henshin.internal.constraints;

import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;

public interface ParameterConstraint<T> {

	public abstract boolean check(T value,
			AttributeConditionHandler conditionHandler);

	public abstract String getParameterName();

}