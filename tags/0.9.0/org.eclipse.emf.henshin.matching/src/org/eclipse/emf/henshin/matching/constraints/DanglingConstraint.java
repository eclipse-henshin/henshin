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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.henshin.matching.EmfGraph;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 */
public class DanglingConstraint implements Constraint {
	private Map<EReference, Integer> outgoingEdgeCount;
	private Map<EReference, Integer> incomingEdgeCount;
	
	public DanglingConstraint(Map<EReference, Integer> outgoingEdgeCount,
			Map<EReference, Integer> incomingEdgeCount) {
		this.outgoingEdgeCount = outgoingEdgeCount;
		this.incomingEdgeCount = incomingEdgeCount;
	}
	
		
	@SuppressWarnings("unchecked")
	public boolean check(EObject sourceValue, EmfGraph graph) {
		 Collection<Setting> settings = graph.getCrossReferenceAdapter().getInverseReferences(sourceValue);
		 
		 Map<EReference, Integer> actualIncomingEdges = createMapFromSettings(settings);
		 
		 if (incomingEdgeCount != null) {
			 for (EReference ref: actualIncomingEdges.keySet()) {
				 if (actualIncomingEdges.get(ref) > incomingEdgeCount.get(ref))					 
					 return false;
			 }
		 } else {
			 if (!actualIncomingEdges.isEmpty())
				 return false;
		 }
		 
		
		// outgoing references
		for (EReference type : sourceValue.eClass().getEReferences()) {
			if (!type.isDerived()) {
				Integer expectedCount;
				if (outgoingEdgeCount != null && outgoingEdgeCount.get(type) != null)
					expectedCount = outgoingEdgeCount.get(type);
				else
					expectedCount = 0;
				
				if (type.isMany()) {
					List<Object> outgoingEdges = (List<Object>) sourceValue.eGet(type);
					
					// TODO: test how slow this is
					outgoingEdges.retainAll(graph.geteObjects());
					
					if (expectedCount != null)
						if (expectedCount != outgoingEdges.size())
							return false;
				} else {
					if (sourceValue.eGet(type) != null && expectedCount != 1
							&& graph.geteObjects().contains(sourceValue.eGet(type)))
						return false;
				}
			}
		}		
		return true;
	}
	
	private Map<EReference, Integer> createMapFromSettings(Collection<Setting> settings) {
		Map<EReference, Integer> result = new HashMap<EReference, Integer>();
		
		for (Setting setting: settings) {
			Integer count = result.get(setting.getEStructuralFeature());
			if (count == null) {
				count = 1;
				result.put((EReference) setting.getEStructuralFeature(), count);
			} else {
				count++;
			}
		}
		
		return result;
	}
}