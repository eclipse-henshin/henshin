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
package org.eclipse.emf.henshin.matching.constraints;

import org.eclipse.emf.ecore.EAttribute;

/**
 * This constraint checks if the attribute has the same value as the parameter.
 * If the parameter is not currently set, it will be set to the value of the
 * attribute.
 */
public class ParameterConstraint implements UnaryConstraint {
	String parameterName;
	EAttribute attribute;
	
	public ParameterConstraint(String parameterName, EAttribute attribute) {
		this.parameterName = parameterName;
		this.attribute = attribute;
	}
	
	public boolean check(DomainSlot slot) {
		// if the slot is not locked ignore the parameter constraint 
		if (!slot.locked)
			return true;
		
		Object attributeValue = slot.value.eGet(attribute);
		
		if (!slot.conditionHandler.isSet(parameterName)) {
			// Try to initialize the parameter with attributeValue. Might fail
			// due to attribute conditions.
			boolean ok = slot.conditionHandler.setParameter(parameterName, attributeValue);
			if (!ok)
				slot.conditionHandler.unsetParameter(parameterName);
			return ok;
		} else {
			Object parameterValue = slot.conditionHandler.getParameter(parameterName);
			
			// treat a set parameter like a constant
			if (parameterValue != null) {
				return parameterValue.equals(attributeValue);
			} else {
				return attributeValue == null;
			}
		}
	}
}
