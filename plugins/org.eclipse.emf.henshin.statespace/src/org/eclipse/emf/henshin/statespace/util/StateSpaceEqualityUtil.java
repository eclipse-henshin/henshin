package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
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
					valueHash += hashCode(eObject.eGet(feature), graphEquality);
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
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(java.util.List, java.util.List)
		 */
		@Override
	    public boolean equals(List<EObject> list1, List<EObject> list2) {
			
			// Use Ecore equality?
			if (!graphEquality) {
				return super.equals(list1, list2);
			}
			
			// Compare sizes:
			if (list1.size() != list2.size()) {
				return false;
			}
			
			// Copy the second list:
			list2 = new ArrayList<EObject>(list2);

			// Match objects:
			for (int i=0; i<list1.size(); i++) {
				boolean found = false;
				for (int j=0; j<list2.size(); j++) {
					if (equals(list1.get(i), list2.get(j))) {
						list2.remove(j);
						found = true;
						break;
					}
				}
				if (!found) return false;
			}
			
			// Success:
			return true;
			
		}
	}
	
}
