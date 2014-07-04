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

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class AttributeConstraint implements UnaryConstraint {
	EAttribute attribute;
	Object attributeValue;
	
	public AttributeConstraint(final EAttribute attribute, final Object value) {
		this.attribute = attribute;
		attributeValue = value;
	}
	
	@Override
	public boolean check(final DomainSlot slot) {
		
		if (slot.locked)
			return attributeValue == null ? slot.value.eGet(attribute) == null : slot.value
					.eGet(attribute) != null && slot.value.eGet(attribute).equals(attributeValue);
		
		List<EObject> domain = slot.domain;
		for (int i = domain.size() - 1; i >= 0; i--) {
			EObject domainObject = domain.get(i);
			
			if (attributeValue == null) {
				if (domainObject.eGet(attribute) != null) {
					domain.remove(i);
				}
			} else {
				if (!attributeValue.equals(domainObject.eGet(attribute))) {
					domain.remove(i);
				}
			}
		}
		return !domain.isEmpty();
	}
}
