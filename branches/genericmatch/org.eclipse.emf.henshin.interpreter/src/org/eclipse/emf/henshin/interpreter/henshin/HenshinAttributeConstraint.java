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

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class HenshinAttributeConstraint implements AttributeConstraint<Node> {
	Attribute attribute;
	Object attributeValue;

	public HenshinAttributeConstraint(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.attributeValue = value;
	}

	public boolean check(Node sourceValue) {
		return attributeValue.equals(getValue(sourceValue, attribute.getType()));
	}

	public void reduceDomain(List<Node> sourceDomain) {
		for (int i = sourceDomain.size()-1; i >=0 ; i--) {
			Node domainObject = sourceDomain.get(i);
			
			if (!attributeValue.equals(getValue(domainObject, attribute.getType())))
				sourceDomain.remove(i);
		}
	}
	
	private Object getValue(Node node, EAttribute type) {
		for (Attribute attribute: node.getAttributes()) {
			if (attribute.getType() == type)
				return attribute;
		}
		
		return null;
	}
}
