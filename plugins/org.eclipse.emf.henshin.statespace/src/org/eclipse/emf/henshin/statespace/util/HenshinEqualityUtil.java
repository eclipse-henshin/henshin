package org.eclipse.emf.henshin.statespace.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author Christian Krause
 * @generated NOT
 */
public class HenshinEqualityUtil {
	
	/**
	 * Check whether two eObjects are Henshin equal.
	 * @param eObject1 First object.
	 * @param eObject2 Second object.
	 * @return <code>true</code> if they are Henshin equal.
	 */
	public static boolean equals(EObject eObject1, EObject eObject2) {
		EqualityHelper helper = new EqualityHelper();
		return helper.equals(eObject1, eObject2);
	}

	/**
	 * Check whether two resources are Henshin equal.
	 * @param resource1 First resource.
	 * @param resource2 Second resource.
	 * @return <code>true</code> if they are Henshin equal.
	 */
	public static boolean equals(Resource resource1, Resource resource2) {
		EqualityHelper helper = new EqualityHelper();
		return helper.equals(resource1.getContents(), resource2.getContents());
	}
	
	/**
	 * Compute the hash code of a given resource.
	 */
	public static int hashCode(Resource resource) {
		
		// Sum up the hash code of all objects.
		int hash = 0;
		for (EObject object : resource.getContents()) {
			hash += hashCode(object);
		}
		return hash;
		
	}
	
	
	/**
	 * Compute the hash code of an eObject.
	 * @param object eObject.
	 * @return its hash code.
	 */
	private static int hashCode(EObject object) {
		
		// Get the class and package:
		EClass eclass = object.eClass();
		EPackage epackage = eclass.getEPackage();
		
		// Initialize hash based on class ID and package nsURI:
		int hash = eclass.getClassifierID() + 23;
		if (epackage!=null && epackage.getNsURI()!=null) {
			hash = hash * epackage.getNsURI().hashCode() + 47;
		}
		
		// Add the feature hash codes:
		for (EStructuralFeature feature : eclass.getEAllStructuralFeatures()) {
			
			// Compute the hash code of the values:
			int valueHash = 0;
			if (feature.isMany()) {
				EList<?> list = (EList<?>) object.eGet(feature);
				for (Object item : list) {
					valueHash += hashCode(item, feature);
				}
			} else {
				valueHash += hashCode(object.eGet(feature), feature);
			}
			
			// Include feature ID:
			hash += valueHash * (feature.getFeatureID() + 17);
			
		}
		
		return hash;
	}
	
	
	/*
	 * Private helper.
	 */
	private static int hashCode(Object value, EStructuralFeature feature) {
		
		// Value null?
		if (value==null) return 0;
		
		// Attributes:
		if (feature instanceof EAttribute) {
			return value.hashCode();
		}
		
		// References:
		if (feature instanceof EReference) {
			
			// Containment reference?
			if (((EReference) feature).isContainment()) {
				return hashCode((EObject) value);
			} else {
				return 67;
			}
			
		}
		
		// Should not get here.
		throw new IllegalArgumentException("Unsupported feature type: " + feature.getClass());
		
	}
	
	
	/**
	 * Henshin equality helper class.
	 * @see EcoreUtil.EqualityHelper
	 */
	public static class EqualityHelper extends EcoreUtil.EqualityHelper {
		
		// Default serial ID.
		private static final long serialVersionUID = 1L;
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#equals(java.util.List, java.util.List)
		 */
		@Override
	    public boolean equals(List<EObject> list1, List<EObject> list2) {
			
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
