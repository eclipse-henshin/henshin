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

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public class DomainChange {
	DomainSlot slot;
	Collection<EObject> originalValues;
	
	public DomainChange(DomainSlot slot, Collection<EObject> originalValues) {
		this.slot = slot;
		this.originalValues = originalValues;
	}
}
