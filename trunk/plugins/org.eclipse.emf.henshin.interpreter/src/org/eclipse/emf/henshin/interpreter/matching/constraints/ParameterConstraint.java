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
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import org.eclipse.emf.ecore.EAttribute;

/**
 * This constraint checks if the attribute has the same value as the parameter.
 * If the parameter is not currently set, it will be set to the value of the
 * attribute.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class ParameterConstraint implements UnaryConstraint {
	
	// Name of the parameter to be checked:
	final String parameterName;
	
	// Target attribute
	final EAttribute attribute;
	
	/**
	 * Default constructor.
	 * @param parameterName Parameter name.
	 * @param attribute Target attribute.
	 */
	public ParameterConstraint(String parameterName, EAttribute attribute) {
		this.parameterName = parameterName;
		this.attribute = attribute;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		
		// If the slot is not locked ignore the parameter constraint. 
		if (!slot.locked) {
			return true;
		}
		
		// Get the attribute value:
		Object attributeValue = slot.value.eGet(attribute);
		
		// Parameter not set yet?
		if (!slot.conditionHandler.isSet(parameterName)) {
			
			// Try to initialize the parameter with attributeValue. Might fail due to attribute conditions.
			boolean ok = slot.conditionHandler.setParameter(parameterName, attributeValue);
			if (!ok) {
				slot.conditionHandler.unsetParameter(parameterName);
			}
			return ok;
			
		} else {
			
			// Get the parameter value
			Object parameterValue = slot.conditionHandler.getParameter(parameterName);
			
			// Treat a set parameter like a constant:
			if (parameterValue != null) {
				return parameterValue.equals(attributeValue);
			} else {
				return (attributeValue == null);
			}
		}
		
	}
	
}
