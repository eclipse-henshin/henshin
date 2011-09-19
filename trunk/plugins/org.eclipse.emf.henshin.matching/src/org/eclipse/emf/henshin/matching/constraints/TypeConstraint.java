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

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.EmfGraph;

/**
 * This constraint checks whether an node has a specific value.
 */
public class TypeConstraint implements UnaryConstraint {
	EClass type;
	
	public TypeConstraint(EClass type) {
		this.type = type;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.internal.constraints.UnaryConstraint#check(org
	 * .eclipse.emf.henshin.internal.matching.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		if (slot.locked) return type.isSuperTypeOf(slot.value.eClass());
		
		return true;
	}
	
	/**
	 * @param slot
	 * @param graph
	 * @return
	 */
	public boolean initDomain(DomainSlot slot, EmfGraph graph) {
		if (slot.domain == null) {
			slot.domain = new ArrayList<EObject>(graph.getDomainForType(type));
		} else if (!slot.domain.isEmpty()) {
			for (int i = slot.domain.size() - 1; i >= 0; i--) {
				EObject eObject = slot.domain.get(i);
				
				if (eObject != null && !type.isSuperTypeOf(eObject.eClass()))
					slot.domain.remove(i);
			}
		}
		
		return !slot.domain.isEmpty();
	}
}
