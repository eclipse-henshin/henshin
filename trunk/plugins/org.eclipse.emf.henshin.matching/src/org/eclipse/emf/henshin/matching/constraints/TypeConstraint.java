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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.EmfGraph;

/**
 * This constraint checks whether an node has a specific value.
 */
public class TypeConstraint implements UnaryConstraint {
	EClass type;
	boolean strictTyping;
	
	public TypeConstraint(EClass type, boolean strictTyping) {
		this.type = type;
		this.strictTyping = strictTyping;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.internal.constraints.UnaryConstraint#check(org
	 * .eclipse.emf.henshin.internal.matching.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot slot) {		
		return !slot.locked || isValid(slot);
	}
	
	protected boolean isValid(DomainSlot slot) {
		return strictTyping ? type == slot.value.eClass() : type.isSuperTypeOf(slot.value.eClass());
	}
	
	/**
	 * @param slot
	 * @param graph
	 * @return
	 */
	public boolean initDomain(DomainSlot slot, EmfGraph graph) {
		
		if (slot.domain == null) {
			slot.domain = graph.getDomainForType(type, strictTyping);
			return !slot.domain.isEmpty();
		}
		
		// slot.domain != null
		//
		if (slot.domain.isEmpty())
			return false;
		
		List<EObject> graphDomain = graph.getDomainForType(type, strictTyping);
		
		// swap for retainAll efficiency
		//
		if (slot.domain.size() > graphDomain.size()) {
			List<EObject> slotDomain = slot.domain;
			slot.domain = graphDomain;
			graphDomain = slotDomain;
		}
		slot.domain.retainAll(graphDomain);
		return !slot.domain.isEmpty();
	}
	
	public boolean isStrictTyping() {
		return strictTyping;
	}

	public boolean instantiationPossible(DomainSlot slot, EmfGraph graph) {
		return slot.locked?isValid(slot):!graph.isDomainEmpty(type, strictTyping);		
	}
		
}
