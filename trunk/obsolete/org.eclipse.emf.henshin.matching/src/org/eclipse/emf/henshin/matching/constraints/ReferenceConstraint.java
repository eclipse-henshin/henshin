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
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class ReferenceConstraint implements BinaryConstraint {
	private EReference reference;
	private Variable target;
	
	public ReferenceConstraint(Variable target, EReference eReference) {
		this.target = target;
		this.reference = eReference;
	}
	
	@SuppressWarnings("unchecked")
	public boolean check(DomainSlot source, DomainSlot target) {
		if (!source.locked || source.value.eGet(reference) == null) return false;
		
		Collection<EObject> referredObjects;
		if (reference.isMany()) {
			referredObjects = (List<EObject>) source.value.eGet(reference);
			if (referredObjects.isEmpty()) return false;
		} else {
			EObject v = (EObject) source.value.eGet(reference);
			if (v == null) return false;
			referredObjects = new ArrayList<EObject>(1);
			referredObjects.add(v);
		}
		
		if (target.locked) {
			return referredObjects.contains(target.value);
		} else {
			DomainChange change = new DomainChange(target, target.temporaryDomain);
			source.remoteChangeMap.put(this, change);
			target.temporaryDomain = new ArrayList<EObject>(referredObjects);
			
			if (change.originalValues != null)
				target.temporaryDomain.retainAll(change.originalValues);
			
			return !target.temporaryDomain.isEmpty();
		}
	}
	
	public Variable getTarget() {
		return target;
	}
}