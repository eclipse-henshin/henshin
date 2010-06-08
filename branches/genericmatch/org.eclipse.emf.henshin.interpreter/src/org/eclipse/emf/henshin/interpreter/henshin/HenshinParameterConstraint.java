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
package org.eclipse.emf.henshin.interpreter.henshin;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;

/**
 * This constraint checks if the attribute has the same value as the parameter.
 * If the parameter is not currently set, it will be set to the value of the
 * attribute.
 */
public class HenshinParameterConstraint implements ParameterConstraint<Node> {
	private String parameterName;
	private Attribute attribute;

	public HenshinParameterConstraint(String parameterName, Attribute attribute) {
		this.parameterName = parameterName;
		this.attribute = attribute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.internal.constraints.IParameterConstraint#check
	 * (org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.henshin.internal.conditions
	 * .attribute.AttributeConditionHandler)
	 */
	public boolean check(Node value, AttributeConditionHandler conditionHandler) {
		Object attributeValue = getValue(value, attribute.getType());

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
	
	private Object getValue(Node node, EAttribute type) {
		for (Attribute attribute: node.getAttributes()) {
			if (attribute.getType() == type)
				return attribute;
		}
		
		return null;
	}
}
