/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
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

	public String getParameterName() {
		return parameterName;
	}
}
