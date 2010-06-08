/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.constraints.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;

/**
 * This constraint checks if the attribute has the same value as the parameter.
 * If the parameter is not currently set, it will be set to the value of the
 * attribute. 
 */
public class EmfParameterConstraint implements ParameterConstraint<EObject> {
	private String parameterName;
	private EAttribute attribute;

	public EmfParameterConstraint(String parameterName, EAttribute attribute) {
		this.parameterName = parameterName;
		this.attribute = attribute;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.internal.constraints.IParameterConstraint#check(org.eclipse.emf.ecore.EObject, org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler)
	 */
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

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.internal.constraints.IParameterConstraint#getParameterName()
	 */
	public String getParameterName() {
		return parameterName;
	}
}
