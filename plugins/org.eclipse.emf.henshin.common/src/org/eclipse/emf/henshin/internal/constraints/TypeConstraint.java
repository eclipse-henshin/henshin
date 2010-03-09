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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class TypeConstraint {
	EClass type;

	public TypeConstraint(EClass type) {
		this.type = type;
	}

	public boolean check(EObject sourceValue) {
		return type.isSuperTypeOf(sourceValue.eClass());
	}

	public List<EObject> reduceDomain(List<EObject> sourceDomain, EmfGraph graph) {
		if (sourceDomain == null) {
			return new ArrayList<EObject>(graph.getDomainForType(type));
		} else {
			if (sourceDomain != null && sourceDomain.size() > 0)
				for (int i = 0; i < sourceDomain.size(); i++) {
					EObject eObject = sourceDomain.get(i);
					
					if (eObject != null  && !type.isSuperTypeOf(eObject.eClass()))
						sourceDomain.remove(i);
				}
		}

		return sourceDomain;
	}
}
