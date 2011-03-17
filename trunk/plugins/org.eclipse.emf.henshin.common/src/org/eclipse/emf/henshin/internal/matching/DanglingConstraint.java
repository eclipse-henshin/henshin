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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.constraints.Constraint;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class DanglingConstraint implements Constraint {
	private Map<EReference, Integer> outgoingEdgeCount;
	//private Map<EReference, Integer> incomingEdgeCount;

	public DanglingConstraint(Map<EReference, Integer> outgoingEdgeCount,
			Map<EReference, Integer> incomingEdgeCount) {
		this.outgoingEdgeCount = outgoingEdgeCount;
		//this.incomingEdgeCount = incomingEdgeCount;
	}

	@SuppressWarnings("unchecked")
	public boolean check(EObject sourceValue, EmfGraph graph) {
		// TODO: implement incoming edges
		// incoming references
		// Collection<Setting> incomingEdges = crossReferences.get(sourceValue);
		// if (incomingEdges != null) { for (EReference ref :
		// sourceValue.eClass().getEReferences()) { // compare number of cross
		// references to number of incoming // edges of the rule Map<EReference,
		// Integer> objectIncomingEdges = createCountMap(incomingEdges); if
		// (incomingEdgeCount == null && objectIncomingEdges.keySet().size() >
		// 0) return false;
		//		 
		// for (EReference type : objectIncomingEdges.keySet()) { Integer
		// expectedCount = incomingEdgeCount.get(type); Integer actualCount =
		// objectIncomingEdges.get(type); if
		// (!actualCount.equals(expectedCount)) { return false; } } } }

		// outgoing references
		for (EReference type : sourceValue.eClass().getEReferences()) {
			if (!type.isDerived()) {
				Integer expectedCount;
				if (outgoingEdgeCount != null && outgoingEdgeCount.get(type) != null)
					expectedCount = outgoingEdgeCount.get(type);
				else
					expectedCount = 0;

				if (type.isMany()) {
					List<Object> outgoingEdges = (List<Object>) sourceValue
							.eGet(type);
					
					//TODO: test how slow this is
					outgoingEdges.retainAll(graph.geteObjects());
					
					if (expectedCount != null)
						if (expectedCount != outgoingEdges.size())
							return false;
				} else {
					if (sourceValue.eGet(type) != null && expectedCount != 1)
						return false;
				}
			}
		}

		return true;
	}
}