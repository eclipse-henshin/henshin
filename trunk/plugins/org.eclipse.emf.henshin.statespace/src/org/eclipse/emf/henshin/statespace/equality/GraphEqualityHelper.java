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

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateSpacePlugin;

/**
 * Helper class for deciding whether two models are graph equal.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public class GraphEqualityHelper extends LinkedHashMap<EObject,EObject> {	
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	// Helper for handling attributes:
	private EcoreEqualityHelper attributeHelper;

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
		if (map1==null || map2==null) {
			map1 = HashCodeMap.ECLASS_HASH_CODE_MAP;
			map2 = HashCodeMap.ECLASS_HASH_CODE_MAP;
			StateSpacePlugin.INSTANCE.logWarning("Using EClass-based node comparison (very slow!)");
		}

		// Get the array representations of the models:
		EObject[] objects1 = model1.asArray();
		EObject[] objects2 = model2.asArray();
		
		// They must have the same length:
		if (objects1.length!=objects2.length) {
			return false;
		}
		int size = objects1.length;

		// Number of different object types:
		int types = 0;

		// Generate the two patterns based on the local hash codes:
		int[] pattern1 = new int[size];
		int[] pattern2 = new int[size];
		
		// We associate hash codes with indizes:
		Map<Integer,Integer> indizes = new HashMap<Integer, Integer>();
		for (int i=0; i<size; i++) {
			int hash = map1.getHashCode(objects1[i]);
			Integer index = indizes.get(hash);
			if (index==null) {
				index = types++;
				indizes.put(hash, index);
			}
			pattern1[i] = index;
		}
		for (int i=0; i<size; i++) {
			int hash = map2.getHashCode(objects2[i]);
			Integer index = indizes.get(hash);
			if (index==null) {
				return false;
			}
			pattern2[i] = index;
		}
		
		// Now generate the matches:
		MatchIterator matches = new MatchIterator(pattern1, pattern2, types);
		while (matches.hasNext()) {
			
			// Get the next match:
			int[] match = matches.next();
			//System.out.println("Match " + Arrays.toString(match));
			
			// Check whether the match is valid:
			boolean valid = true;
			for (int i=0; i<size; i++) {
				
				// Get the matched objects:
				EObject o1 = objects1[i];
				EObject o2 = objects2[match[i]];
				
				// The type must be the same:
				EClass type = o1.eClass();
				if (!o2.eClass().equals(type)) {
					valid = false;
					break;
				}
				
				// The node IDs must be the same:
				if (!ignoreNodeIDs && getNodeID(o1,model1)!=getNodeID(o2,model2)) {
					valid = false;
					break;
				}

				// Check the structural features:
				for (EStructuralFeature feature : type.getEAllStructuralFeatures()) {
					
					if (feature instanceof EAttribute) {
						if (!attributeHelper.haveEqualAttribute(o1, o2, (EAttribute) feature)) {
							valid = false;
							break;
						}
					} else {
						EList<EObject> list1 = getReferenceAsList(o1, (EReference) feature);
						EList<EObject> list2 = getReferenceAsList(o2, (EReference) feature);
						int references = list1.size();
						if (list2.size()!=references) {
							valid = false;
							break;
						}
						for (int j=0; j<references; j++) {
							EObject r1 = list1.get(j);
							
							// TODO compare lists
						}
						
					}
					
				}
				if (!valid) {
					break;
				}
			}
			
			// Check if the match was valid, then we are done:
			if (valid) {
				return true;
			}

		}
		
		// No (valid) match found:
		return false;
		
	}
	
	/*
	 * Get a reference of an eObject as a list.
	 */
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

	/*
	 * Get the ID of a node.
	 */
	private int getNodeID(EObject object, Model model) {
		Integer result = model.getNodeIDsMap().get(object);
		if (result==null) {
			throw new RuntimeException("No node ID found for " + object);			
		}
		return result;
	}
	
}	
