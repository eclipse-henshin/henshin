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
package org.eclipse.emf.henshin.internal.constraints;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class AttributeConstraint {
	EAttribute attribute;
	Object attributeValue;

	public AttributeConstraint(EAttribute attribute, Object value) {
		this.attribute = attribute;
		this.attributeValue = value;
	}

	public boolean check(EObject sourceValue) {
		return sourceValue.eGet(attribute) == attributeValue;
	}

	public void reduceDomain(List<EObject> sourceDomain) {
		
		for (int i = sourceDomain.size()-1; i >=0 ; i--) {
			EObject domainObject = sourceDomain.get(i);
			
			if (!attributeValue.equals(domainObject.eGet(attribute)))
				sourceDomain.remove(i);
		}
	}
}
