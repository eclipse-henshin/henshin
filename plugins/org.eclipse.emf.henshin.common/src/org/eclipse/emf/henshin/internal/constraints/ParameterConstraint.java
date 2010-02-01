package org.eclipse.emf.henshin.internal.constraints;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;

/**
 * This constraint checks if the attribute has the same value as the parameter.
 * If the parameter is not currently set, it will be set to the value of the
 * attribute. 
 */
public class ParameterConstraint {
	private String parameterName;
	private EAttribute attribute;

	public ParameterConstraint(String parameterName, EAttribute attribute) {
		this.parameterName = parameterName;
		this.attribute = attribute;
	}

	public boolean check(EObject value,
			AttributeConditionHandler conditionHandler) {
		Object attributeValue = value.eGet(attribute);

		if (!conditionHandler.isSet(parameterName)) {
			boolean ok = conditionHandler.setParameter(parameterName,
					attributeValue);
			if (!ok)
				conditionHandler.unsetParameter(parameterName);

			return ok;
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
}
