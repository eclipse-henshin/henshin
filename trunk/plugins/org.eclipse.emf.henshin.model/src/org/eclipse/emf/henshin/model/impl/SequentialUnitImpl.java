/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sequential Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SequentialUnitImpl#getSubUnits <em>Sub Units</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SequentialUnitImpl#isStrict <em>Strict</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SequentialUnitImpl#isRollback <em>Rollback</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SequentialUnitImpl extends TransformationUnitImpl implements SequentialUnit {
	/**
	 * The cached value of the '{@link #getSubUnits() <em>Sub Units</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<TransformationUnit> subUnits;

	/**
	 * The default value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRICT_EDEFAULT = true;
	/**
	 * The cached value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected boolean strict = STRICT_EDEFAULT;
	/**
	 * The default value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRollback()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ROLLBACK_EDEFAULT = true;
	/**
	 * The cached value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRollback()
	 * @generated
	 * @ordered
	 */
	protected boolean rollback = ROLLBACK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SequentialUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.SEQUENTIAL_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TransformationUnit> getSubUnits() {
		if (subUnits == null) {
			subUnits = new EObjectResolvingEList<TransformationUnit>(TransformationUnit.class, this, HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS) {
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isUnique() {
					// We don't want uniqueness. See also bug #89325.
					return false;
				}
			};
		}
		return subUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStrict() {
		return strict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrict(boolean newStrict) {
		boolean oldStrict = strict;
		strict = newStrict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.SEQUENTIAL_UNIT__STRICT, oldStrict, strict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRollback() {
		return rollback;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRollback(boolean newRollback) {
		boolean oldRollback = rollback;
		rollback = newRollback;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.SEQUENTIAL_UNIT__ROLLBACK, oldRollback, rollback));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TransformationUnit> getSubUnits(boolean deep) {
		List<TransformationUnit> allunits = new ArrayList<TransformationUnit>();
		for (TransformationUnit unit : this.getSubUnits()) {
			allunits.add(unit);
			if (deep && (unit != this)) {	// do not recursively add recursive units 
				allunits.addAll(unit.getSubUnits(deep));
			}
		}// for
		return new BasicEList<TransformationUnit>(allunits);
	}// getSubUnits

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS:
				return getSubUnits();
			case HenshinPackage.SEQUENTIAL_UNIT__STRICT:
				return isStrict();
			case HenshinPackage.SEQUENTIAL_UNIT__ROLLBACK:
				return isRollback();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS:
				getSubUnits().clear();
				getSubUnits().addAll((Collection<? extends TransformationUnit>)newValue);
				return;
			case HenshinPackage.SEQUENTIAL_UNIT__STRICT:
				setStrict((Boolean)newValue);
				return;
			case HenshinPackage.SEQUENTIAL_UNIT__ROLLBACK:
				setRollback((Boolean)newValue);
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
			case HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS:
				getSubUnits().clear();
				return;
			case HenshinPackage.SEQUENTIAL_UNIT__STRICT:
				setStrict(STRICT_EDEFAULT);
				return;
			case HenshinPackage.SEQUENTIAL_UNIT__ROLLBACK:
				setRollback(ROLLBACK_EDEFAULT);
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
			case HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS:
				return subUnits != null && !subUnits.isEmpty();
			case HenshinPackage.SEQUENTIAL_UNIT__STRICT:
				return strict != STRICT_EDEFAULT;
			case HenshinPackage.SEQUENTIAL_UNIT__ROLLBACK:
				return rollback != ROLLBACK_EDEFAULT;
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
		result.append(" (strict: ");
		result.append(strict);
		result.append(", rollback: ");
		result.append(rollback);
		result.append(')');
		return result.toString();
	}

} //SequentialUnitImpl
