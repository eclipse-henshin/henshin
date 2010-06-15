/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * Helper class for deciding whether two models are graph equal.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class GraphEqualityHelper extends HashMap<EObject,EObject> {	
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Helper for handling attributes:
	private EcoreEqualityHelper attributeHelper;
	
	/**
	 * Default constructor.
	 * @param ignoreAttributes Whether to ignore attributes.
	 */
	public GraphEqualityHelper(boolean ignoreAttributes) {
		this.attributeHelper = new EcoreEqualityHelper(ignoreAttributes);
	}
	
	/**
	 * Check graph equality for two resources.
	 * @param r1 Resource 1.
	 * @param r2 Resource 2.
	 * @return <code>true</code> if they are equal.
	 */
	public boolean equals(Resource r1, Resource r2) {
		EObject[] l1 = r1.getContents().toArray(new EObject[0]);
		EObject[] l2 = r2.getContents().toArray(new EObject[0]);
		return equals(l1, l2);
	}
	
	/**
	 * Check graph equality using depth-first search.
	 * @param l1 List of nodes.
	 * @param l2 List of nodes.
	 * @return <code>true</code> if they can be matched.
	 */
	@SuppressWarnings("unchecked")
	private boolean equals(EObject[] l1, EObject[] l2) {
		
		// The lists must have the same length:
		if (l1.length!=l2.length) {
			System.out.println("Cannot match lists of different lengths");
			return false;
		}
		
		// Find the first node to match:
		int index1 = -1;
		for (int i=0; i<l1.length; i++) {
			if (l1[i]!=null) {
				index1 = i;
				break;
			}
		}
		
		// If empty, we are done.
		if (index1<0) {
			return true;
		}
		
		// Get the node and check if it is matched already:
		EObject node1 = l1[index1];
		EObject node2 = get(node1);
					
		// Already matched?
		if (node2!=null) {
			
			// Try to find the matched element in the second list:
			int index2 = -1;
			for (int i=0; i<l2.length; i++) {
				if (l2[i]==node2) {
					index2 = i;
					break;
				}
			}
			
			// Match found?
			if (index2 > 0) {
				
				// Remove the match from the lists:
				l1[index1] = null;
				l2[index2] = null;
				
				// Try to match the rest:
				if (equals(l1,l2)) {
					return true;
				}
				
				// Was not successful:
				l1[index1] = node1;
				l2[index2] = node2;
				
			}
			
			// The existing match doesn't work:
			System.out.println("Verifying match between " + node1 + " and " + node2 + " failed");
			return false;
			
		}
		
		// We have to find a valid match for the node now.
		
		// First, get the node type:
		EClass eclass = node1.eClass();

		// Try out all candidates:
		for (int index2=0; index2<l2.length; index2++) {
			
			// Must be not null and of the same type:
			node2 = l2[index2];
			if (node2==null || !eclass.equals(node2.eClass())) {
				continue;
			}
			
			// Assume it is a valid match for now.
			put(node1, node2);
			boolean valid = true;
			
			// Compare the features:
			for (EStructuralFeature feature : eclass.getEAllStructuralFeatures()) {
				
				if (feature instanceof EAttribute) {

					// Must have the same attribute values:
					if (!attributeHelper.haveEqualAttribute(node1, node2, (EAttribute) feature)) {
						valid = false;
						break;
					}
					
				} else {
					
					// Depth-first on the references:
					if (feature.isMany()) {
						
						// Try to match the two reference lists:
						EObject[] n1 = ((List<EObject>) node1.eGet(feature)).toArray(new EObject[0]);
						EObject[] n2 = ((List<EObject>) node2.eGet(feature)).toArray(new EObject[0]);
						if (!equals(n1, n2)) {
							valid = false;
						}
						
					} else {
						
						// Try to match the two singletons:
						EObject o1 = (EObject) node1.eGet(feature);
						EObject o2 = (EObject) node2.eGet(feature);
						if ((o1==null && o2!=null) || 
							(o1!=null && (o2==null || !equals(new EObject[] {o1}, new EObject[] {o2} )))) {
							valid = false;
						}						
					}
					
				}
				
				// If not valid, stop:
				if (!valid) {
					System.out.println("Cannot match feature '" + feature.getName() + "' in class '" + eclass.getName() + "'");
					break;
				}
				
			}
			
			// Was the match successful?
			if (valid) {
				
				// Remove the match from the lists:
				l1[index1] = null;
				l2[index2] = null;
				
				// Try to match the rest:
				if (equals(l1,l2)) {
					return true;
				} 
				
				// Was not successful:
				l1[index1] = node1;
				l2[index2] = node2;
				
			}
			
			// If we are here, the matching was not successful:
			remove(node1);
			
		}
		
		// None of the candidates could be matched.
		System.out.println("Cannot match " + Arrays.toString(l1) + " with " + Arrays.toString(l2));
		return false;
		
	}
	
}	
