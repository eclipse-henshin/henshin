/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class AttributeConstraint implements UnaryConstraint {
	
	// Target attribute:
	final EAttribute attribute;
	
	// Attribute value:
	final Object attributeValue;
	
	/**
	 * Default constructor.
	 * @param attribute Target attribute.
	 * @param value Attribute value.
	 */
	public AttributeConstraint(EAttribute attribute, Object value) {
		this.attribute = attribute;
		this.attributeValue = value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		
		// Slot locked already?
		if (slot.locked) {
			return (attributeValue == null) ? 
					(slot.value.eGet(attribute) == null) : 
					(slot.value.eGet(attribute) != null && slot.value.eGet(attribute).equals(attributeValue));
		}
		
		// Remove illegal objects from the slot:
		int size = slot.domain.size();
		for (int i=size-1; i>=0; i--) {
			EObject domainObject = slot.domain.get(i);
			if (attributeValue == null) {
				if (domainObject.eGet(attribute) != null) {
					slot.domain.remove(i);
				}
			} else {
				if (!attributeValue.equals(domainObject.eGet(attribute))) {
					slot.domain.remove(i);
				}
			}
		}
		
		// Slot should not be empty:
		return !slot.domain.isEmpty();
		
	}
}
