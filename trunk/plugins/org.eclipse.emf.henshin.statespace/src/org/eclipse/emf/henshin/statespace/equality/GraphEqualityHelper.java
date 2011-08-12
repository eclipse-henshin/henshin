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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
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
	
	// Stack of lists to compare:
	private EObject[][] s1, s2;
	
	// Optional hash code maps:
	private HashCodeMap p1, p2;
	
	// Cached models:
	private Model m1, m2;

	// Whether node IDs should be ignored:
	private boolean ignoreNodeIDs;
	
	
	/**
	 * Default constructor.
	 * @param ignoreAttributes Whether to ignore attributes.
	 */
	public GraphEqualityHelper(boolean ignoreNodeIDs, boolean ignoreAttributes) {
		this.ignoreNodeIDs = ignoreNodeIDs;
		this.attributeHelper = new EcoreEqualityHelper(ignoreNodeIDs, ignoreAttributes);
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
		s1 = new EObject[][] { m1.getResource().getContents().toArray(new EObject[0]) };
		s2 = new EObject[][] { m2.getResource().getContents().toArray(new EObject[0]) };
		p1 = map1;
		p2 = map2;
		
		// Perform depth-first search:
		debug("\n=== CHECKING GRAPH EQUALITY ===\n", 0);
		boolean equals = depthFirst(0);
		if (equals) {
			debug("\n=== MATCH FOUND === ", 0);
		} else {
			debug("\n=== NO MATCH FOUND === ", 0);
		}

		// Release variables:
		m1 = null;
		m2 = null;
		s1 = null;
		s2 = null;
		p1 = null;
		p2 = null;
		
		// Done.
		//return equals;
		
		return true;
		
	}
	
	/**
	 * Check graph equality using depth-first search.
	 * @return <code>true</code> if a match was found.
	 */
	@SuppressWarnings("unchecked")
	private boolean depthFirst(int current) {
		
		// If we have a negative index, we cannot backtrack anymore:
		if (current<0) {
			return false;
		}
		
		// Get the lists to be matched now:
		EObject[] l1 = s1[current];
		EObject[] l2 = s2[current];
		
		// The lists must have the same length:
		if (l1.length!=l2.length) {
			debug("Current lists have different lengths, aborting...", current);
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

		// If the list is empty, we are done with this branch:
		if (index1<0) {
			
			// Now check whether there is more work on the stack left:
			if (current==0) {
				return true;				  // everything done already
			} else {
				return depthFirst(current-1); // otherwise continue with parent
			}
			
		}
		
		// Get the first node and check if it is matched already:
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
			if (index2 >= 0) {
				
				// Remove the matched elements from the lists:
				l1[index1] = null;
				l2[index2] = null;
				
				// Try to match the rest of the current lists:
				if (depthFirst(current)) {
					debug("Matching " + node1 + " with " + node2 + " works", current);
					return true;
				}
				
				// Was not successful:
				l1[index1] = node1;
				l2[index2] = node2;
				
			}
			
			// The existing match doesn't work:
			debug("Failed verifying match for " + node1 + "\n", current);
			return false;
			
		}
		
		
		/* We have to find a valid match for the node now. */
		
		// First, get the node type and the node hash code:
		EClass eclass = node1.eClass();
		int hash1 = p1.getHashCode(node1);
		
		// Try out all candidates:
		int attempt = 0;
		for (int index2=0; index2<l2.length; index2++) {
			
			// This is the candidate node:
			node2 = l2[index2];
			
			// Must be not null and of the same type, with the same hash code:
			if (node2==null || 
				!eclass.equals(node2.eClass()) ||
				hash1!=p2.getHashCode(node2)) {
				continue;
			}
			
			// Compare the IDs if necessary:
			if (!ignoreNodeIDs) {
				if (getNodeID(node1,m1)!=getNodeID(node2,m2)) continue;
			}

			// Assume it is a valid match for now.
			put(node1, node2);
			boolean valid = true;
			debug("Attempt " + (++attempt) + " for matching " + node1 + "...", current);
			debug("Current match: " + this + "\n", current);
			
			// Compare the features:
			for (EStructuralFeature feature : eclass.getEAllStructuralFeatures()) {
				
				if (feature instanceof EAttribute) {

					// Must have the same attribute values:
					if (!attributeHelper.haveEqualAttribute(node1, node2, (EAttribute) feature)) {
						valid = false;
					}
					
				} else {
					
					EObject[] n1, n2;
					
					// Depth-first on the references:
					if (feature.isMany()) {
						n1 = ((List<EObject>) node1.eGet(feature)).toArray(new EObject[0]);
						n2 = ((List<EObject>) node2.eGet(feature)).toArray(new EObject[0]);
						if (n1.length!=n2.length) {
							valid = false;
						}
					} else {
						n1 = new EObject[] { (EObject) node1.eGet(feature) };
						n2 = new EObject[] { (EObject) node2.eGet(feature) };
						if ((n1[0]==null && n2[0]!=null) || (n1[0]!=null && n2[0]==null)) {
							valid = false;
						}
					}
					
					// Try depth-first traversal:
					if (valid) {
						
						// We need some more space on the stack first:
						s1 = grow(s1, current+2);
						s2 = grow(s2, current+2);
						
						// Append the new branch:
						s1[current+1] = n1;
						s2[current+1] = n2;
						
						// Go depth-first:
						if (!depthFirst(current+1)) {
							valid = false;
						}
						
					}

				}
				
				// If the assumed match doesn't work, stop checking the features:
				if (!valid) {
					debug("Cannot match feature '" + feature.getName() + "' in " + node1, current);
					break;
				}
				
			}
			
			if (valid && depthFirst(current)) {
				return true;
			}
			
			// If we are here, the matching was not successful:
			debug("Attempt " + attempt + " for matching " + node1 + " FAILED!!!\n", current);
			remove(node1);
				
		}
		
		// None of the candidates could be matched.
		debug("Giving up matching " + node1 + " after " + attempt + " attempts\n", current);
		return false;
		
	}
	
	private static void debug(String message, int depth) {
		//for (int i=0; i<depth; i++) {
		//	System.out.print("    ");
		//}
		//System.out.println(message);
	}
	
	/*
	 * Get the ID of a node.
	 */
	private static int getNodeID(EObject object, Model model) {
		Integer result = model.getNodeIDsMap().get(object);
		if (result==null) {
			throw new RuntimeException("No node ID found for " + object);			
		}
		return result;
	}
	
	/*
	 * Grow an array.
	 */
	private static EObject[][] grow(EObject[][] array, int size) {
		if (size<=array.length) return array;
		EObject[][] result = new EObject[(size * 2) +1][];
		for (int i=0; i<array.length; i++) {
			result[i] = array[i];
		}
		return result;
	}
	
}	
