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
package org.eclipse.emf.henshin.internal.matching;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.constraints.UnaryConstraint;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class AttributeConstraint implements UnaryConstraint {
	EAttribute attribute;
	Object attributeValue;
	
	public AttributeConstraint(EAttribute attribute, Object value) {
		this.attribute = attribute;
		this.attributeValue = value;
	}
	
	public boolean check(DomainSlot slot) {
		if (slot.locked) {
			return slot.value.eGet(attribute).equals(attributeValue);
		} else {
			List<EObject> domain = slot.domain;
			for (int i = domain.size() - 1; i >= 0; i--) {
				EObject domainObject = domain.get(i);
				
				if (!attributeValue.equals(domainObject.eGet(attribute)))
					domain.remove(i);
			}
			return !domain.isEmpty();
		}
	}
}
