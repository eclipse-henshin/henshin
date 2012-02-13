/**
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 */
package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.henshin.statespace.StateSpacePackage;

/**
 * A map entry implementation for object keys.
 * @generated
 */
public class ObjectKeyImpl extends MinimalEObjectImpl.Container implements BasicEMap.Entry<EObject,Integer> {
	
	/**
	 * The cached value of the '{@link #getTypedKey() <em>Key</em>}' reference.
	 * @see #getTypedKey()
	 * @generated
	 * @ordered
	 */
	protected EObject key;

	/**
	 * The default value of the '{@link #getTypedValue() <em>Value</em>}' attribute.
	 * @see #getTypedValue()
	 * @generated
	 * @ordered
	 */
	protected static final Integer VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTypedValue() <em>Value</em>}' attribute.
	 * @see #getTypedValue()
	 * @generated
	 * @ordered
	 */
	protected Integer value = VALUE_EDEFAULT;

	/**
	 * @generated
	 */
	protected ObjectKeyImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.OBJECT_KEY;
	}

	/**
	 * @generated
	 */
	public EObject getTypedKey() {
		if (key != null && key.eIsProxy()) {
			InternalEObject oldKey = (InternalEObject)key;
			key = eResolveProxy(oldKey);
			if (key != oldKey) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateSpacePackage.OBJECT_KEY__KEY, oldKey, key));
			}
		}
		return key;
	}

	/**
	 * @generated
	 */
	public EObject basicGetTypedKey() {
		return key;
	}

	/**
	 * @generated
	 */
	public void setTypedKey(EObject newKey) {
		EObject oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.OBJECT_KEY__KEY, oldKey, key));
	}

	/**
	 * @generated
	 */
	public Integer getTypedValue() {
		return value;
	}

	/**
	 * @generated
	 */
	public void setTypedValue(Integer newValue) {
		Integer oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.OBJECT_KEY__VALUE, oldValue, value));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.OBJECT_KEY__KEY:
				if (resolve) return getTypedKey();
				return basicGetTypedKey();
			case StateSpacePackage.OBJECT_KEY__VALUE:
				return getTypedValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.OBJECT_KEY__KEY:
				setTypedKey((EObject)newValue);
				return;
			case StateSpacePackage.OBJECT_KEY__VALUE:
				setTypedValue((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackage.OBJECT_KEY__KEY:
				setTypedKey((EObject)null);
				return;
			case StateSpacePackage.OBJECT_KEY__VALUE:
				setTypedValue(VALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackage.OBJECT_KEY__KEY:
				return key != null;
			case StateSpacePackage.OBJECT_KEY__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return getKey() + " -> " + getValue();
	}

	/**
	 * @generated
	 */
	protected int hash = -1;

	/**
	 * @generated
	 */
	public int getHash() {
		if (hash == -1) {
			Object theKey = getKey();
			hash = (theKey == null ? 0 : theKey.hashCode());
		}
		return hash;
	}

	/**
	 * @generated
	 */
	public void setHash(int hash) {
		this.hash = hash;
	}

	/**
	 * @generated
	 */
	public EObject getKey() {
		return getTypedKey();
	}

	/**
	 * @generated
	 */
	public void setKey(EObject key) {
		setTypedKey(key);
	}

	/**
	 * @generated
	 */
	public Integer getValue() {
		return getTypedValue();
	}

	/**
	 * @generated
	 */
	public Integer setValue(Integer value) {
		Integer oldValue = getValue();
		setTypedValue(value);
		return oldValue;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EMap<EObject, Integer> getEMap() {
		EObject container = eContainer();
		return container == null ? null : (EMap<EObject, Integer>)container.eGet(eContainmentFeature());
	}

} //ObjectIdentityImpl
