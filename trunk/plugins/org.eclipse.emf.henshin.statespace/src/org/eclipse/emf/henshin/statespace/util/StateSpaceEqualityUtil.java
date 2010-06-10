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

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Static equality and hashcode functions for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceEqualityUtil {
	
	/**
	 * Check whether two eObjects are equal.
	 * @param eObject1 First object.
	 * @param eObject2 Second object.
	 * @param graphEquality Graph-equality flag.
	 * @return <code>true</code> if they are equal.
	 */
	public static boolean equals(EObject eObject1, EObject eObject2, boolean graphEquality) {
		EqualityHelper helper = new EqualityHelper(graphEquality);
		return helper.equals(eObject1, eObject2);
	}

	/**
	 * Check whether two resources are equal.
	 * @param resource1 First resource.
	 * @param resource2 Second resource.
	 * @param graphEquality Graph-equality flag.
	 * @return <code>true</code> if they are equal.
	 */
	public static boolean equals(Resource resource1, Resource resource2, boolean graphEquality) {
		EqualityHelper helper = new EqualityHelper(graphEquality);
		return helper.equals(resource1.getContents(), resource2.getContents());
	}
	
	/**
	 * Compute the hash code of a given resource.
	 * @param resource Resource.
	 * @param graphEquality Graph-equality flag.
	 * @return The hash code.
	 */
	public static int hashCode(Resource resource, boolean graphEquality) {
		return hashCode(resource.getContents(), graphEquality);
	}
	
	/**
	 * Compute the hash code of a list of eObjects.
	 * @param eObjects eObjects.
	 * @param graphEquality Graph-equality flag.
	 * @return The hash code.
	 */
	public static int hashCode(List<?> objects, boolean graphEquality) {
		int hash = 0;
		for (Object object : objects) {
			hash += hashCode(object, graphEquality);
			if (!graphEquality) hash = hash * 2;
		}
		return hash;
	}
	
	
	/**
	 * Compute the hash code of an eObject.
	 * @param object Object or eObject.
	 * @return its hash code.
	 */
	public static int hashCode(Object object, boolean graphEquality) {
		
		// Primitive object?
		if (!(object instanceof EObject)) {
			return object.hashCode();
		}
		
		// EObject properties:
		EObject eObject = (EObject) object;
		EClass eclass = eObject.eClass();
		EPackage epackage = eclass.getEPackage();
		
		// Initialize hash based on class ID and package nsURI:
		int hash = eclass.getClassifierID() + 23;
		hash = hash * epackage.getNsURI().hashCode() + 47;
		
		// Add the feature hash codes:
		for (EStructuralFeature feature : eclass.getEAllStructuralFeatures()) {
			
			if (feature instanceof EAttribute ||
				(feature instanceof EReference && ((EReference) feature).isContainment())) {
				
				// Compute the hash code of the values:
				int valueHash = 0;
				
				// Many-feature?
				if (feature.isMany()) {
					List<?> list = (List<?>) eObject.eGet(feature);
					valueHash += hashCode(list, graphEquality);
				} else {
					Object value = eObject.eGet(feature);
					if (value!=null) {
						valueHash += hashCode(value, graphEquality);
					}
				}
				
				// Include feature ID:
				hash += valueHash * (feature.getFeatureID() + 17);
				
			}
		}
		
		return hash;
	}
		
	
	/**
	 * Equality helper class.
	 * @see EcoreUtil.EqualityHelper
	 */
	public static class EqualityHelper extends EcoreUtil.EqualityHelper {
		
		// Default serial ID.
		private static final long serialVersionUID = 1L;
		
		private boolean graphEquality;
		
		/**
		 * Constructor.
		 * @param graphEquality Graph-equality flag.
		 */
		public EqualityHelper(boolean graphEquality) {
			this.graphEquality = graphEquality;
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
		 */
		@Override
		public boolean equals(EObject eObject1, EObject eObject2) {
			
			// Decide whether they are equal.
			boolean equal = super.equals(eObject1, eObject2);
			
			// If not, remove them from the map again for consistency:
			if (!equal) {
				remove(eObject1);
				remove(eObject2);
			}
			
			// That is all.
			return equal;
			
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(java.util.List, java.util.List)
		 */
		@Override
	    public boolean equals(List<EObject> list1, List<EObject> list2) {
			
			// Use Ecore equality?
			if (!graphEquality) {
				return super.equals(list1, list2);
			}
			
			// Compare sizes:
			if (list1.size()!=list2.size()) {
				return false;
			}
			
			// Create arrays which we can modify:
			EObject[] array1 = list1.toArray(new EObject[0]);
			EObject[] array2 = list2.toArray(new EObject[0]);

			// Do an exhaustive equality check:
			return exhaustiveEquals(array1, array2);
						
		}
		
		/*
		 * Perform an exhaustive equality check. It is assumed that both lists have the same size.
		 */
		private boolean exhaustiveEquals(EObject[] list1, EObject[] list2) {
			
			// Try all possible combinations:
			for (int i=0; i<list1.length; i++) {
				
				// Get the first object:
				if (list1[i]==null) continue;
				EObject e1 = list1[i];
				
				for (int j=0; j<list2.length; j++) {
					
					// Get the second object:
					if (list2[j]==null) continue;
					EObject e2 = list2[j];
					
					// Check if they are equal:
					if (equals(e1,e2)) {
						
						// Remove the elements from the arrays:
						list1[i] = null;
						list2[j] = null;
						
						// Now compare the rest:
						if (exhaustiveEquals(list1, list2)) {
							return true;
						}
						
						// Not successful, so add the objects back to the arrays again:
						list1[i] = e1;
						list2[j] = e2;
						
					}
				}
			}
			
			// Success (both arrays are empty):
			return true;
			
		}
	}
	
}
