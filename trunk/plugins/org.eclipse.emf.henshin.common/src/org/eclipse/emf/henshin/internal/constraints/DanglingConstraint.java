/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.constraints;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class DanglingConstraint {
	private Map<EReference, Integer> outgoingEdgeCount;
	private Map<EReference, Integer> incomingEdgeCount;

	public DanglingConstraint(Map<EReference, Integer> outgoingEdgeCount,
			Map<EReference, Integer> incomingEdgeCount) {
		this.outgoingEdgeCount = outgoingEdgeCount;
		this.incomingEdgeCount = incomingEdgeCount;
	}

	public boolean check(EObject sourceValue,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		
		// incoming references
		Collection<EStructuralFeature.Setting> sourceCrossReferences = crossReferences
				.get(sourceValue);
				
		return true;
	}
}