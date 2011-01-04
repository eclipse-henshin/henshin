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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.internal.constraints.BinaryConstraint;

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
		if (!source.locked || source.value.eGet(reference) == null)
			return false;
		
		Collection<EObject> referedObjects;
		if (reference.isMany()) {
			referedObjects = (List<EObject>) source.value.eGet(reference);
		} else {
			referedObjects = new ArrayList<EObject>();
			referedObjects.add((EObject) source.value.eGet(reference));
		}
		
		if (referedObjects.isEmpty())
			return false;
		
		if (target.locked) {
			return referedObjects.contains(target.value);
		} else {
			DomainChange change = new DomainChange(target, target.temporaryDomain);
			source.remoteChangeMap.put(this, change);
			target.temporaryDomain = new ArrayList<EObject>(referedObjects);
			
			if (change.originalValues != null)
				target.temporaryDomain.retainAll(change.originalValues);
			
			return !target.temporaryDomain.isEmpty();
		}
	}
	
	public Variable getTarget() {
		return target;
	}
}