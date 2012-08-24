/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.UnaryUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unary Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.UnaryUnitImpl#getSubUnit <em>Sub Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class UnaryUnitImpl extends TransformationUnitImpl implements UnaryUnit {
	
	/**
	 * The cached value of the '{@link #getSubUnit() <em>Sub Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit subUnit;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UnaryUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.UNARY_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getSubUnit() {
		if (subUnit != null && subUnit.eIsProxy()) {
			InternalEObject oldSubUnit = (InternalEObject)subUnit;
			subUnit = (TransformationUnit)eResolveProxy(oldSubUnit);
			if (subUnit != oldSubUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.UNARY_UNIT__SUB_UNIT, oldSubUnit, subUnit));
			}
		}
		return subUnit;
	}

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public final EList<TransformationUnit> getSubUnits() {
		if (subUnit!=null) {
			EList<TransformationUnit> list = new BasicEList<TransformationUnit>();
			list.add(subUnit);
			return list;
		} else {
			return ECollections.emptyEList();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetSubUnit() {
		return subUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubUnit(TransformationUnit newSubUnit) {
		TransformationUnit oldSubUnit = subUnit;
		subUnit = newSubUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.UNARY_UNIT__SUB_UNIT, oldSubUnit, subUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.UNARY_UNIT__SUB_UNIT:
				if (resolve) return getSubUnit();
				return basicGetSubUnit();
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
			case HenshinPackage.UNARY_UNIT__SUB_UNIT:
				setSubUnit((TransformationUnit)newValue);
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
			case HenshinPackage.UNARY_UNIT__SUB_UNIT:
				setSubUnit((TransformationUnit)null);
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
			case HenshinPackage.UNARY_UNIT__SUB_UNIT:
				return subUnit != null;
		}
		return super.eIsSet(featureID);
	}

} //UnaryUnitImpl
