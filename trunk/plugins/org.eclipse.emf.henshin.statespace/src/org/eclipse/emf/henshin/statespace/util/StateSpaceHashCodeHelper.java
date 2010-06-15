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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class StateSpaceHashCodeHelper {

	// The ten first prime numbers.
	private static int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

	// Whether to use graph equality.
	private boolean graphEquality;
	
	// Whether to ignore attributes.
	private boolean ignoreAttributes;
	
	// Currently used EPackage (cached).
	private EPackage ePackage;
	
	// Hash code of EPackage's nsURI.
	private int nsURIHashCode;
	
	/**
	 * Default constructor.
	 * @param graphEquality Graph equality?
	 * @param ignoreAttributes Ignore attributes?
	 */
	public StateSpaceHashCodeHelper(boolean graphEquality, boolean ignoreAttributes) {
		this.graphEquality = graphEquality;
		this.ignoreAttributes = ignoreAttributes;
	}
	
	/**
	 * Compute the hash code for a given model.
	 * @generated NOT
	 */
	public int hashCode(Resource resource) {
		return hashCode(resource.getContents(), 0);
	}
	
	/*
	 * Compute the hash code of a list of EObjects.
	 * This delegates to #hashCode() for a single EObject.
	 * Depending on the equality type, the list is treated
	 * as a sequence or as a set.
	 */
	private int hashCode(EList<EObject> objects, int depth) {
		int hash = 0;
		for (EObject object : objects) {
			if (!graphEquality) {
				hash *= PRIMES[depth % PRIMES.length];
			}
			hash += hashCode(object, depth);
		}
		return hash;
	}
	
	/*
	 * Compute the hash code of a single EObject. This computes a local 
	 * hash code for the object and merges it with the ones from the 
	 * contents of the object.
	 */
	@SuppressWarnings("unchecked")
	private int hashCode(EObject object, int depth) {
		int hash = nodeHashCode(object);
		for (EReference reference : object.eClass().getEAllContainments()) {
			int value;
			if (reference.isMany()) {
				EList<EObject> list = (EList<EObject>) object.eGet(reference);
				value = hashCode(list, depth+1);
			} else {
				EObject child = (EObject) object.eGet(reference);
				value = (child==null) ? 0 : hashCode(child, depth+1);
			}
			hash = (hash * 31) + value;
		}
		return hash;
	}
	
	/*
	 * Compute hash code for a node.
	 */
	private int nodeHashCode(EObject node) {

		// Class and its features:
		EClass eclass = node.eClass();
		EList<EStructuralFeature> features = eclass.getEAllStructuralFeatures();
		
		// Use classifier ID:
		int hashCode = elementHashCode(0, eclass.getClassifierID());

		// Use features:
		for (int i=0; i<features.size(); i++) {				
			EStructuralFeature feature = features.get(i);
			int value = 0;
			if (feature.isMany()) {
				List<?> list = (List<?>) node.eGet(feature);
				if (feature instanceof EReference) {
					value = list.size();
				} else if (feature instanceof EAttribute) {
					value = ignoreAttributes ? 0 : list.hashCode();
				}
			} else {
				Object object = node.eGet(feature);
				if (object==null) {
					value = 0;
				} else if (feature instanceof EReference) {
					value = 1;
				} else if (feature instanceof EAttribute) {
					value = ignoreAttributes ? 0 : object.hashCode();
				}
			}
			hashCode *= elementHashCode(i+1, value);
		}
		
		// Update cached ePackage:
		if (ePackage!=eclass.getEPackage()) {
			ePackage = eclass.getEPackage();
			nsURIHashCode = (ePackage.getNsURI()!=null) ? ePackage.getNsURI().hashCode() : 0;
		}
		
		// Add the hash code of the EPackage's nsURI:
		return hashCode + nsURIHashCode;

	}

	
	private int elementHashCode(int index, int value) {
		
		int exponent = ((value<0) ? -value : value) % 8;
		int base = PRIMES[index % PRIMES.length];
		
		int result = base;
		for (int i=1; i<exponent; i++) {
			result *= base;
		}
		
		return result;
		
	}

}
