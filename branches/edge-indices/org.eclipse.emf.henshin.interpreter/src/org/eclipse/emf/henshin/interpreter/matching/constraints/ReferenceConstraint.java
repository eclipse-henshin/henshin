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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class ReferenceConstraint implements BinaryConstraint {
	
	// Target variable:
	final Variable targetVariable;
	
	// Reference:
	final EReference reference;
	
	// Index:
	final Integer index;

	/**
	 * Default constructor.
	 * @param target Target variable.
	 * @param reference Reference.
	 */
	public ReferenceConstraint(Variable target, EReference reference, Integer index) {
		this.targetVariable = target;
		this.reference = reference;
		this.index = index;
	}

	/**
	 * Convenience constructor.
	 * @param target Target variable.
	 * @param reference Reference.
	 */
	public ReferenceConstraint(Variable target, EReference reference) {
		this(target, reference, null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot, org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean check(DomainSlot source, DomainSlot target) {
		
		// Source must be locked:
		if (!source.locked) {
			return false;
		}
		
		// Get the target objects:
		List<EObject> targetObjects;
		if (reference.isMany()) {
			targetObjects = (List<EObject>) source.value.eGet(reference);
			if (targetObjects.isEmpty()) {
				return false;
			}
		} else {
			EObject obj = (EObject) source.value.eGet(reference);
			if (obj==null) {
				return false;
			}
			targetObjects = Collections.singletonList(obj);
		}
		
		// Calculation for negative indices:
		Integer calculatedIndex = index;
		if (index!=null && index<0) {
			calculatedIndex = targetObjects.size() + index;
		}
		
		// Target domain slot locked?
		if (target.locked) {
			if (calculatedIndex!=null) {
				return targetObjects.indexOf(target.value)==calculatedIndex;
			} else {
				return targetObjects.contains(target.value);
			}
		} else {
			// Create a domain change to restrict the target domain:
			DomainChange change = new DomainChange(target, target.temporaryDomain);
			source.remoteChangeMap.put(this, change);
			if (calculatedIndex!=null) {
				if (calculatedIndex>=0 && calculatedIndex<targetObjects.size()) {
					target.temporaryDomain = Collections.singletonList(targetObjects.get(calculatedIndex));
				} else {
					target.temporaryDomain = Collections.emptyList();
				}
			} else {
				target.temporaryDomain = new ArrayList<EObject>(targetObjects);
			}
			if (change.originalValues!=null) {
				target.temporaryDomain.retainAll(change.originalValues);
			}
			return !target.temporaryDomain.isEmpty();
		}
		
	}
	
}