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
package org.eclipse.emf.henshin.interpreter.henshin;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.GraphSkeleton;
import org.eclipse.emf.henshin.internal.constraints.TypeConstraint;
import org.eclipse.emf.henshin.model.Node;

/**
 * This constraint checks whether an attribute has a specific value.
 */
public class HenshinTypeConstraint implements TypeConstraint<EClass, Node> {
	EClass type;

	public HenshinTypeConstraint(EClass type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.internal.constraints.ITypeConstraint#check(org
	 * .eclipse.emf.ecore.EObject)
	 */
	public boolean check(Node sourceValue) {
		return type.isSuperTypeOf(sourceValue.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.internal.constraints.ITypeConstraint#reduceDomain
	 * (java.util.List, org.eclipse.emf.henshin.common.util.EmfGraph)
	 */
	public List<Node> reduceDomain(List<Node> sourceDomain,
			GraphSkeleton<EClass, Node> graph) {
		if (sourceDomain == null) {
			return new ArrayList<Node>(graph.getDomainForType(getType()));
		} else {
			if (sourceDomain != null && sourceDomain.size() > 0)
				for (int i = sourceDomain.size() - 1; i >= 0; i--) {
					EObject eObject = sourceDomain.get(i);

					if (eObject != null
							&& !type.isSuperTypeOf(eObject.eClass()))
						sourceDomain.remove(i);
				}
		}

		return sourceDomain;
	}

	@Override
	public EClass getType() {
		return type;
	}
}
