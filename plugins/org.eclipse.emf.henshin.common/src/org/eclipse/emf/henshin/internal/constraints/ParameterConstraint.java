package org.eclipse.emf.henshin.internal.constraints;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.matching.Variable;

/**
 * This constraint checks if the attribute has the same value as the parameter.
 * If the parameter is not currently set, it will be set to the value of the
 * attribute.
 */
public class ParameterConstraint extends Constraint {

	AttributeConditionHandler conditionHandler;
	String parameterName;
	EAttribute attribute;

	public ParameterConstraint(AttributeConditionHandler conditionHandler,
			Variable creator, String parameterName, EAttribute attribute) {
		super(creator, creator);

		this.conditionHandler = conditionHandler;
		this.parameterName = parameterName;
		this.attribute = attribute;
	}

	public boolean eval() {
		EObject creatorValue = ownerVariable.getInstanceValue();
		Object attributeValue = creatorValue.eGet(attribute);
		evaluated = true;

		if (!conditionHandler.isSet(parameterName)) {
			return conditionHandler.setParameter(parameterName, attributeValue);
		} else {
			Object parameterValue = conditionHandler
					.getParameter(parameterName);
			if (parameterValue != null) {
				return parameterValue.equals(attributeValue);
			} else {
				return attributeValue == null;
			}
		}
	}

	public void undo() {
		if (evaluated)
			conditionHandler.unsetParameter(parameterName);

		super.undo();
	}
}
