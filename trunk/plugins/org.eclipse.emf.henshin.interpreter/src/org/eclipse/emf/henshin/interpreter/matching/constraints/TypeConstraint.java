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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;

/**
 * This constraint checks whether an node has a specific value.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class TypeConstraint implements UnaryConstraint {
	
	// Type to be matched:
	public final EClass type;
	
	// Whether to use strict typing:
	public final boolean strictTyping;
	
	/**
	 * Default constructor.
	 * @param type Type to be matched.
	 * @param strictTyping Whether to use strict typing.
	 */
	public TypeConstraint(EClass type, boolean strictTyping) {
		this.type = type;
		this.strictTyping = strictTyping;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		return !slot.locked || isValid(slot.value);
	}
	
	protected boolean isValid(EObject value) {
		return strictTyping ? (type==value.eClass()) : type.isSuperTypeOf(value.eClass());
	}
	
	/**
	 * Initialize a domain slot.
	 * @param slot Domain slot to be initialized.
	 * @param graph Target graph.
	 * @return <code>true</code> if it was initialized.
	 */
	public boolean initDomain(DomainSlot slot, EGraph graph) {
		
		// Already initialized:
		if (slot.domain==null) {
			slot.domain = graph.getDomain(type, strictTyping);
			return !slot.domain.isEmpty();
		}
		
		// Domain empty?
		if (slot.domain.isEmpty()) {
			return false;
		} else {
			int size = slot.domain.size();
			for (int i = size-1; i>=0; i--) {
				EObject object = slot.domain.get(i);
				if (object==null || !isValid(object)) {
					slot.domain.remove(i);
				}
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
	
	/**
	 * Check whether an instantiation could be possible.
	 * @param slot Domain slot.
	 * @param graph Target graph.
	 * @return <code>true</code> if an instantiation might be possible.
	 */
	public boolean instantiationPossible(DomainSlot slot, EGraph graph) {
		return slot.locked ? isValid(slot.value) : graph.getDomainSize(type, strictTyping)>0;
	}
	
}
