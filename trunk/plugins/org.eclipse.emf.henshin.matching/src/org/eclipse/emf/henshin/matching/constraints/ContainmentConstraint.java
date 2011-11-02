/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.matching.constraints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
/**
 * {@link ContainmentConstraint} checks 
 * @author Gregor Bonifer
 *
 */
public class ContainmentConstraint implements BinaryConstraint {
	
	private EReference reference;
	private Variable target;
	
	public ContainmentConstraint(EReference reference, Variable target) {
		this.reference = reference;
		this.target = target;
	}
	
	@Override
	public boolean check(DomainSlot containedSlot, DomainSlot containerSlot) {
		
		// only locked slots may be valid.
		//
		if (!containedSlot.locked)
			return false;
		
		// the source value must have a container
		//
		EObject container = containedSlot.value.eContainer();
		if (container == null)
			return false;
		
		if (containerSlot.locked) { // containerSlot already locked
		
			// containerSlot must hold the container 
			//
			if (containerSlot.value != container)
				return false;
			
			// containment must be established by the given EReference
			//
			return isContainedByRequiredEReference(containedSlot.value, container);
			
		} else { // containerSlot not locked yet
		
			// Constraint is fulfilled if the containerSlot's temporaryDomain is
			// unrestricted or contains the required container.
			//
			boolean result = containerSlot.temporaryDomain == null
					|| containerSlot.temporaryDomain.contains(container);
			
			if (result) {
				DomainChange change = new DomainChange(containerSlot, containerSlot.temporaryDomain);
				containedSlot.remoteChangeMap.put(this, change);
				containerSlot.temporaryDomain = new ArrayList<EObject>(1);
				containerSlot.temporaryDomain.add(container);
			}
			return result;
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean isContainedByRequiredEReference(EObject containedObject, EObject container) {
		if (reference.isMany())
			return ((List<EObject>) container.eGet(reference)).contains(containedObject);
		else {
			return container.eGet(reference) == containedObject;
		}
	}
	
	public Variable getTargetVariable() {
		return target;
	}
}
