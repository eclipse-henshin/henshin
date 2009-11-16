package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author Christian Krause
 */
public class ResourceHelper {

	/*
	public static boolean equals(Resource r1, Resource r2) {
		
		// Compare the sizes.
		if (r1.getContents().size()!=r2.getContents().size()) return false;

		// Compare the objects:
		for (int i=0; i<size(); i++) {
			boolean found = false;
			for (int j=0; j<copy.size(); j++) {
				if (EcoreUtil.equals(get(i), copy.get(j))) {
					copy.remove(j);
					found = true;
					break;
				}
			}
			if (!found) return false;
		}
		return true;
	}
	*/
	
	/**
	 * Compute the hashcode of a given resource.
	 */
	public static int hashCode(Resource resource) {
		
		// Sum up the hash code of all objects.
		int hash = 0;
		for (EObject object : resource.getContents()) {
			hash += hashCode(object);
		}
		return hash;
		
	}
	
	
	/*
	 * Compute the hash code of an object.
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
				return value.hashCode();
			}
			
		}
		
		// Should not get here.
		throw new IllegalArgumentException("Unknow feature type: " + feature);
		
	}
	
}
