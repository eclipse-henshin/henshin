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

import org.eclipse.emf.ecore.EObject;

/**
 * {@link ContainmentConstraint} checks
 * 
 * @author Gregor Bonifer
 * 
 */
public class ContainmentConstraint implements BinaryConstraint {
		
	private Variable target;
	
	public ContainmentConstraint(Variable target) {	
		this.target = target;
	}
	
	@Override
	public boolean check(DomainSlot containedSlot, DomainSlot containerSlot) {
		
		// only locked slots may be valid.
		//
		if (!containedSlot.locked)
			return false;
		
		// containedSlot.value must be an element of the temporaryDomain
		// specified by the containment reference.
		//
		if (containerSlot.locked)
			return true;
		
		// the source value must have a container
		//
		EObject container = containedSlot.value.eContainer();
		if (container == null)
			return false;
		
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
		// }
	}
		
	public Variable getTargetVariable() {
		return target;
	}
}
