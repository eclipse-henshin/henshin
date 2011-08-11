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
public class GraphEqualityHelper extends HashMap<EObject,EObject> {	
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Helper for handling attributes:
	private EcoreEqualityHelper attributeHelper;
	
	// Stack of lists to compare:
	private EObject[][] s1, s2;
	
	// Optional hash code trees:
	private HashCodeTree t1, t2;
	
	// Current position in the stack:
	private int current;
	
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
	public boolean equals(Model model1, HashCodeTree tree1, Model model2, HashCodeTree tree2) {
		
		// We always need both trees:
		if (tree1==null || tree2==null) {
			tree1 = null;
			tree2 = null;
		}
		
		// Initialize variables:
		m1 = model1;
		m2 = model2;
		s1 = new EObject[][] { m1.getResource().getContents().toArray(new EObject[0]) };
		s2 = new EObject[][] { m2.getResource().getContents().toArray(new EObject[0]) };
		t1 = tree1;
		t2 = tree2;
		current = 0;
		
		// Reset the trees:
		if (t1!=null) {
			t1.goToRoot();
			t1.goDown();
			t2.goToRoot();
			t2.goDown();
		}
		
		// Perform depth-first search:
		//System.out.println("\n=== CHECKING GRAPH EQUALITY ===\n");
		boolean equals = depthFirst();
		//if (equals) System.out.println("\n=== MATCH FOUND === ");

		// Reset the trees:
		if (t1!=null) {
			t1.goToRoot();
			t2.goToRoot();
		}

		// Release variables:
		m1 = null;
		m2 = null;
		s1 = null;
		s2 = null;
		t1 = null;
		t2 = null;
		
		// Done.
		return equals;
		
	}
	
	/**
	 * Check graph equality using depth-first search.
	 * @return <code>true</code> if a match was found.
	 */
	@SuppressWarnings("unchecked")
	private boolean depthFirst() {
		
		// If we have a negative index, we cannot backtrack anymore:
		if (current<0) {
			return false;
		}
		
		// Get the lists to be matched now:
		EObject[] l1 = s1[current];
		EObject[] l2 = s1[current];
		
		// The lists must have the same length:
		if (l1.length!=l2.length) {
			return false;
		}
		
		// Find the first node to match:
		int index1 = -1;
		for (int i=0; i<l1.length; i++) {
			if (l1[i]!=null) {
				index1 = i;
				break;
			}
			if (t1!=null) {
				t1.goRight();
			}
		}
		
		// If the list is empty, we are done with this branch:
		if (index1<0) {
			
			// Now check whether there is more work on the stack left:
			if (current==0) {
				return true;	// everything done already
			} else {
				current--;		// otherwise continue with parent
				return depthFirst();
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
				if (depthFirst()) {
					return true;
				}
				
				// Was not successful:
				l1[index1] = node1;
				l2[index2] = node2;
				
			}
			
			// The existing match doesn't work:
			// System.out.println("Failed verifying match between for " + node1);
			return false;
			
		}
		
		
		/* We have to find a valid match for the node now. */
		
		// First, get the node type:
		EClass eclass = node1.eClass();

		// Try out all candidates:
		for (int index2=0; index2<l2.length; index2++) {
			
			// Must be not null and of the same type:
			node2 = l2[index2];
			if (node2==null || !eclass.equals(node2.eClass())) {
				continue;
			}
			
			// Compare the IDs if necessary:
			if (!ignoreNodeIDs) {
				if (getNodeID(node1)!=getNodeID(node2)) continue;
			}

			// Assume it is a valid match for now.
			put(node1, node2);
			boolean valid = true;
			// System.out.println("Attempt " + index2 + " of matching " + node1);
			// System.out.println("Current match: " + this + "\n");
						
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
						current++;
						valid = depthFirst();
						
					}

				}
				
				// If the assumed match doesn't work, stop checking the features:
				if (!valid) {
					// System.out.println("Cannot match feature '" + feature.getName() + "' in class '" + eclass.getName() + "'");
					break;
				}
				
			}
			
			
			// Was the match successful?
			if (valid) {
				
				// Remove the match from the lists:
				l1[index1] = null;
				l2[index2] = null;
				
				// Try to match the current rest:
				if (depthFirst()) {
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
		// System.out.println("Giving up matching " + node1);
		return false;
		
	}
	
	/*
	 * Get the ID of a node.
	 */
	private int getNodeID(EObject object) {
		Integer result = m1.getNodeIDsMap().get(object);
		if (result==null) {
			result = m2.getNodeIDsMap().get(object);			
		}
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
