/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.equality;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.statespace.Model;

/**
 * Helper class for deciding whether two models are graph equal.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class GraphEqualityHelper extends LinkedHashMap<EObject,EObject> {	
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Empty hash code map:
	private static final HashCodeMap EMPTY_MAP = new HashCodeMap();
	
	// Helper for handling attributes:
	private EcoreEqualityHelper attributeHelper;

	// Cached models:
	private Model m1, m2;

	// Optional hash code maps:
	private HashCodeMap p1, p2;

	// Whether node IDs should be ignored:
	private boolean ignoreNodeIDs;

	
	/**
	 * Default constructor.
	 * @param ignoreAttributes Whether to ignore attributes.
	 */
	public GraphEqualityHelper(boolean ignoreNodeIDs, boolean ignoreAttributes) {
		this.ignoreNodeIDs = ignoreNodeIDs;
		this.attributeHelper = ignoreAttributes ? null : new EcoreEqualityHelper(ignoreNodeIDs, false);
	}
	
	/**
	 * Check graph equality for two models.
	 * @param model1 Model 1.
	 * @param model2 Model 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Model model1, HashCodeMap map1, 
						  Model model2, HashCodeMap map2) {
		
		// Make sure we have both trees
		//if (map1==null || map2==null) {
			map1 = EMPTY_MAP;
			map2 = EMPTY_MAP;
		//}
		
		// Initialize variables:
		m1 = model1;
		m2 = model2;
		p1 = map1;
		p2 = map2;
		
		// Perform depth-first search:
		boolean equals = equals(m1.getResource().getContents(), m2.getResource().getContents(), 0);

		// Release variables:
		m1 = null;
		m2 = null;
		p1 = null;
		p2 = null;
		
		// Done.
		return true; //equals;
		
	}
	
	
	/**
	 * Check graph equality using depth-first search.
	 * @return <code>true</code> if a match was found.
	 */
	private boolean equals(EList<EObject> list1, EList<EObject> list2,  int start) {
		
		// System.out.println("Current match: " + this);
		
		// Lists must have the same size:
		int size = list1.size();
		if (size!=list2.size()) {
			return false;
		}
		
		// Find the first object that is not matched yet:
		EObject obj1 = null;
		for (int i=start; i<size; i++) {
			EObject current = list1.get(i);
			EObject matched = get(current);
			
			// If it is matched already, it must be also in the list:
			if (matched!=null) {
				if (!list2.contains(matched)) {
					return false;
				}
			} else {
				obj1 = current;
				break;
			}
		}
		
		// If all objects are matched already, we are done:
		if (obj1==null) {
			return true;
		}
		
		// Get some details of the first objects:
		EClass type = obj1.eClass();
		int hashcode = p1.getHashCode(obj1);
		int nodeId = getNodeID(obj1, m1);
		
		// Now we try to match it:
		for (EObject obj2 : list2) {
			
			// The type must be the same:
			if (!type.equals(obj2.eClass())) {
				continue;
			}
			
			// The hash code must be the same:
			if (hashcode!=p2.getHashCode(obj2)) {
				continue;
			}
			
			// The node IDs must be the same:
			if (!ignoreNodeIDs && nodeId!=getNodeID(obj2,m2)) {
				continue;
			}
			
			// The object must not be matched yet:
			if (containsKey(obj2)) {
				continue;
			}
			
			// Must have the same attributes:
			if (attributeHelper!=null) {
				boolean equalAttributes = true;
				for (EAttribute attribute : type.getEAllAttributes()) {
					if (!attributeHelper.haveEqualAttribute(obj1, obj2, attribute)) {
						equalAttributes = false;
						break;
					}
				}
				if (!equalAttributes) {
					continue;
				}
			}
			
			// Assume it is a valid match:
			put(obj1, obj2);
			put(obj2, obj1);
			boolean matchWorks = true;

			// Now check the references:
			for (EReference reference : type.getEAllReferences()) {
				EList<EObject> refs1 = getReferenceAsList(obj1, reference);
				EList<EObject> refs2 = getReferenceAsList(obj2, reference);
				
				// Depth-first:
				if (!equals(refs1, refs2, 0)) {
					matchWorks = false;
					break;
				}
			}
			
			// Check if the match worked:
			if (matchWorks) {
				
				// Now we just need to match the rest of the original lists:
				if (equals(list1, list2, start+1)) {
					return true;
				}
				
			}
			
			// If we get to here the match didn't work:
			remove(obj1);
			remove(obj2);
			
		}
		
		// No match found:
		return false;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private static EList<EObject> getReferenceAsList(EObject obj, EReference reference) {
		if (reference.isMany()) {
			return (EList<EObject>) obj.eGet(reference);
		} else {
			EList<EObject> list = new BasicEList<EObject>();
			EObject target = (EObject) obj.eGet(reference);
			if (target!=null) {
				list.add(target);
			}
			return list;
		}
	}
	
	/**
	 * Get the ID of a node.
	 */
	private int getNodeID(EObject object, Model model) {
		if (ignoreNodeIDs) {
			return 0;
		}
		Integer result = model.getNodeIDsMap().get(object);
		if (result==null) {
			throw new RuntimeException("No node ID found for " + object);			
		}
		return result;
	}
	
}	
