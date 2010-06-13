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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.util.NodeKey;

/**
 * Default implementation of {@link StateEqualityHelper}.
 * @generated
 */
public class StateEqualityHelperImpl extends MinimalEObjectImpl implements StateEqualityHelper {
	
	// Cache for node keys.
	private NodeKey.Cache nodeKeyCache = new NodeKey.Cache(IGNORE_ATTRIBUTES_EDEFAULT);
	
	/**
	 * @generated NOT
	 */
	public int hashCode(Resource resource) {
		return hashCode(resource.getContents());
	}
	
	/*
	 * Compute the hash code of a list of EObjects.
	 * This delegates to #hashCode() for a single EObject.
	 * Depending on the equality type, the list is treated
	 * as a sequence or as a set.
	 */
	private int hashCode(EList<EObject> objects) {
		int hash = 0;
		for (EObject object : objects) {
			if (equalityType==ECORE_EQUALITY) {
				hash = 31 * hash;
			}
			hash += hashCode(object);
		}
		return hash;
	}
	
	/*
	 * Compute the hash code of a single EObject.
	 * This creates or NodeKey for the object and
	 * merges the hash code with the ones from
	 * the contents of the object.
	 */
	@SuppressWarnings("unchecked")
	private int hashCode(EObject object) {
		int hash = nodeKeyCache.get(object).hashCode();
		for (EReference reference : object.eClass().getEAllContainments()) {
			int value;
			if (reference.isMany()) {
				EList<EObject> list = (EList<EObject>) object.eGet(reference);
				value = hashCode(list);
			} else {
				EObject child = (EObject) object.eGet(reference);
				value = (child==null) ? 0 : hashCode(child);
			}
			hash = (hash * 31) + value;
		}
		return hash;
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
		
		// Default serial ID.
		private static final long serialVersionUID = 1L;
		
		/*
		 * Check equality for two state models.
		 */
		public boolean equals(Resource model1, Resource model2) {
			//if (equalityType==ECORE_EQUALITY) {
				return super.equals(model1.getContents(), model2.getContents());
			/*}
			else if (equalityType==GRAPH_EQUALITY) {
				return graphEquals(model1, model2);
			}
			else {
				throw new RuntimeException("Unknown equality type: " + equalityType);
			}
			*/
		}
		
		/*
		 * Check graph equality. This method tries to build a
		 * graph isomorphism (one-to-one mapping between the nodes).
		 * If it succeeds, true is returned, otherwise false.
		 */
		private boolean graphEquals(Resource model1, Resource model2) {
			
			// TODO
			
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

	/**
	 * @generated NOT
	 */
	public void setIgnoreAttributes(boolean ignoreAttributes) {
		setIgnoreAttributesGen(ignoreAttributes);
		nodeKeyCache = new NodeKey.Cache(ignoreAttributes);
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
	public void setIgnoreAttributesGen(boolean newIgnoreAttributes) {
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
