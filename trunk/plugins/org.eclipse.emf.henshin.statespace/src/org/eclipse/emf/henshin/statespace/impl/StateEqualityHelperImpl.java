/**
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace.impl;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;

/**
 * Default implementation of {@link StateEqualityHelper}.
 * @generated
 */
public class StateEqualityHelperImpl extends MinimalEObjectImpl implements StateEqualityHelper {
	
	// The ten first prime numbers: used for computing hash codes.
	private static int[] PRIMES = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

	/**
	 * Compute the hash code for a state model.
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
			if (equalityType==ECORE_EQUALITY) {
				hash = PRIMES[depth % PRIMES.length] * hash;
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

		// Initialize the exponents array:
		int[] exponents = new int[features.size()+1];

		// Index 0 reserved for classifier ID.
		exponents[0] = asExponent(eclass.getClassifierID());

		// The rest is use for the actual features.
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
			exponents[i+1] = asExponent(value);
		}

		// Now we compute the hash code using the exponents and prime numbers.
		int hashCode = 1;
		for (int i=0; i<exponents.length; i++) {
			hashCode = hashCode * intPow(PRIMES[i % PRIMES.length], exponents[i]);
		}
		if (eclass.getEPackage().getNsURI()!=null) {
			hashCode += eclass.getEPackage().getNsURI().hashCode();
		}
		return hashCode;

	}

	/*
	 * Raise a integer to a given exponent. Unlike {@link java.lang.Math#pow(double, double)} 
	 * this implementation is base on integers, and hence, can produce overflows.
	 */
	private int intPow(int base, int exponent) {
		if (exponent==0) return 1;
		if (exponent==1) return base;
		if (exponent>1) {
			int result = intPow(base,exponent/2) * intPow(base,exponent/2);
			if (exponent % 2==1) result = result * base;
			return result;
		} else {
			throw new IllegalArgumentException("Negative exponent");
		}
	}

	/*
	 * Turn a value into a suitable exponent. The returned
	 * value range between 0..8, so that the exponents are
	 * always non-negative and small numbers.
	 */
	private int asExponent(int value) {
		return Math.abs(value) % 8;
	}		
	
	/**
	 * @generated NOT
	 */
	public boolean equals(Resource model1, Resource model2) {
		return new EqualityHelper().equals(model1, model2);
	}

	/**
	 * Equality helper class.
	 * @see EcoreUtil.EqualityHelper
	 */
	public class EqualityHelper extends EcoreUtil.EqualityHelper {	
		
		private static final long serialVersionUID = 1L;
		
		/*
		 * Check equality for two state models.
		 */
		public boolean equals(Resource model1, Resource model2) {
			switch (equalityType) {
			
			case ECORE_EQUALITY: 
				return super.equals(model1.getContents(), model2.getContents());
				
			case GRAPH_EQUALITY:
				
				// Make (shallow) copies first:
				EObject[] l1 = model1.getContents().toArray(new EObject[0]);
				EObject[] l2 = model2.getContents().toArray(new EObject[0]);
				
				// Match using depth-first:
				return graphMatchLists(l1,l2);
			
			default:
				throw new RuntimeException("Unknown equality type: " + equalityType);
			}
		}
		
		/*
		 * Match two lists using depth-first search.
		 */
		@SuppressWarnings("unchecked")
		private boolean graphMatchLists(EObject[] l1, EObject[] l2) {
			
			// The lists must have the same length:
			if (l1.length!=l2.length) return false;
			
			// Find the first node to match:
			int index1 = -1;
			for (int i=0; i<l1.length; i++) {
				if (l1[i]!=null) {
					index1 = i;
					break;
				}
			}
			
			// If empty, we are done.
			if (index1<0) return true;
			
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
					if (graphMatchLists(l1,l2)) {
						return true;
					}
					
					// Was not successful:
					l1[index1] = node1;
					l2[index2] = node2;
					
				}
				
				// The existing match doesn't work:
				return false;
				
			}
			
			// We have to find a valid match for the node now.
			
			// First, get the node type:
			EClass eclass = node1.eClass();

			// Try out all candidates:
			for (int index2=0; index2<l2.length; index2++) {
				
				// Must be not null:
				node2 = l2[index2];
				if (node2==null) continue;
				
				// Must be from the same class:
				if (!eclass.equals(node2.eClass())) continue;
				
				// Assume it is a valid match for now.
				put(node1, node2);
				boolean valid = true;
				
				// Compare the features:
				for (EStructuralFeature feature : eclass.getEAllStructuralFeatures()) {
					
					if (feature instanceof EAttribute) {

						// Must have the same attribute values:
						if (!haveEqualAttribute(node1, node2, (EAttribute) feature)) {
							valid = false;
							break;
						}
						
					} else {
						
						// Depth-first on the references:
						if (feature.isMany()) {
							
							// Try to match the two reference lists:
							EObject[] n1 = ((List<EObject>) node1.eGet(feature)).toArray(new EObject[0]);
							EObject[] n2 = ((List<EObject>) node2.eGet(feature)).toArray(new EObject[0]);
							if (!graphMatchLists(n1, n2)) {
								valid = false;
								break;
							}
							
						} else {
							
							// Try to match the two singletons:
							EObject o1 = (EObject) node1.eGet(feature);
							EObject o2 = (EObject) node2.eGet(feature);
							if ((o1==null && o2!=null) || 
								(o1!=null && (o2==null || !graphMatchLists(new EObject[] {o1}, new EObject[] {o2} )))) {
								valid = false;
								break;
							}
							
						}
					}
				}
				
				// Was the match successful?
				if (valid) {
					
					// Remove the match from the lists:
					l1[index1] = null;
					l2[index2] = null;
					
					// Try to match the rest:
					if (graphMatchLists(l1,l2)) {
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
			return false;
			
		}
		
		/*
		 * (non-Javadoc)
		 * @see org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper#haveEqualAttribute(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
		 */
		@Override
	    protected boolean haveEqualAttribute(EObject o1, EObject o2, EAttribute attribute) {
	    	return ignoreAttributes || super.haveEqualAttribute(o1, o2, attribute);
	    }
		
	}	
	
	/**
	 * @generated NOT
	 */
	public void setEqualityType(int type) {
		if (type==ECORE_EQUALITY || type==GRAPH_EQUALITY) {
			setEqualityTypeGen(type);
		} else {
			throw new IllegalArgumentException("Illegal equality type: " + type);
		}
	}
	
	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The default value of the '{@link #getEqualityType() <em>Equality Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEqualityType()
	 * @generated
	 * @ordered
	 */
	protected static final int EQUALITY_TYPE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEqualityType() <em>Equality Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEqualityType()
	 * @generated
	 * @ordered
	 */
	protected int equalityType = EQUALITY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreAttributes() <em>Ignore Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_ATTRIBUTES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreAttributes() <em>Ignore Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreAttributes = IGNORE_ATTRIBUTES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateEqualityHelperImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.STATE_EQUALITY_HELPER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getEqualityType() {
		return equalityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEqualityTypeGen(int newEqualityType) {
		int oldEqualityType = equalityType;
		equalityType = newEqualityType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__EQUALITY_TYPE, oldEqualityType, equalityType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIgnoreAttributes() {
		return ignoreAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIgnoreAttributes(boolean newIgnoreAttributes) {
		boolean oldIgnoreAttributes = ignoreAttributes;
		ignoreAttributes = newIgnoreAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES, oldIgnoreAttributes, ignoreAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__EQUALITY_TYPE:
				return getEqualityType();
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				return isIgnoreAttributes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__EQUALITY_TYPE:
				setEqualityType((Integer)newValue);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				setIgnoreAttributes((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__EQUALITY_TYPE:
				setEqualityType(EQUALITY_TYPE_EDEFAULT);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				setIgnoreAttributes(IGNORE_ATTRIBUTES_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__EQUALITY_TYPE:
				return equalityType != EQUALITY_TYPE_EDEFAULT;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				return ignoreAttributes != IGNORE_ATTRIBUTES_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (equalityType: ");
		result.append(equalityType);
		result.append(", ignoreAttributes: ");
		result.append(ignoreAttributes);
		result.append(')');
		return result.toString();
	}

}
