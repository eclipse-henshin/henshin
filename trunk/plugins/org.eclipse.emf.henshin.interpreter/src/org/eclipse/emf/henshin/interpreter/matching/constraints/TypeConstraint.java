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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;

/**
 * This constraint checks whether an node has a specific value.
 */
public class TypeConstraint implements UnaryConstraint {
	
	// Type to be matched:
	protected EClass type;
	
	// Whether to use strict typing:
	protected boolean strictTyping;
	
	public TypeConstraint(EClass type, boolean strictTyping) {
		this.type = type;
		this.strictTyping = strictTyping;
	}
	
	public EClass getType() {
		return type;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		return !slot.locked || isValid(slot);
	}
	
	protected boolean isValid(DomainSlot slot) {
		return isValid(slot.value);
	}
	
	protected boolean isValid(EObject value) {
		return strictTyping ? type == value.eClass() : type.isSuperTypeOf(value.eClass());
	}
	
	public boolean initDomain(DomainSlot slot, EGraph graph) {
		
		if (slot.domain == null) {
			slot.domain = graph.getDomain(type, strictTyping);
			return !slot.domain.isEmpty();
		}
		
		// slot.domain != null
		//
		if (slot.domain.isEmpty())
			return false;
		// TODO: Find solution to prevent referenced Objects outside the
		// EmfGraph.
		else {
			for (int i = slot.domain.size() - 1; i >= 0; i--) {
				EObject eObject = slot.domain.get(i);
				if (eObject == null || !isValid(eObject))
					slot.domain.remove(i);
			}
			return !slot.domain.isEmpty();
		}
		// List<EObject> graphDomain = graph.getDomainForType(type,
		// strictTyping);
		//
		// // swap for retainAll efficiency
		// //
		// if (slot.domain.size() > graphDomain.size()) {
		// List<EObject> slotDomain = slot.domain;
		// slot.domain = graphDomain;
		// graphDomain = slotDomain;
		// }
		// slot.domain.retainAll(graphDomain);
		// return !slot.domain.isEmpty();
	}
	
	public boolean isStrictTyping() {
		return strictTyping;
	}
	
	public boolean instantiationPossible(DomainSlot slot, EGraph graph) {
		return slot.locked ? isValid(slot) : graph.getDomainSize(type, strictTyping)>0;
	}
	
}
