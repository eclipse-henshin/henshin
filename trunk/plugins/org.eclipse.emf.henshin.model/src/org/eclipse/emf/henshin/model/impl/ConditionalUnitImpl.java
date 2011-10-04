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
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getIf <em>If</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getThen <em>Then</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getElse <em>Else</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConditionalUnitImpl extends TransformationUnitImpl implements ConditionalUnit {
	/**
	 * The cached value of the '{@link #getIf() <em>If</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIf()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit if_;

	/**
	 * The cached value of the '{@link #getThen() <em>Then</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThen()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit then;

	/**
	 * The cached value of the '{@link #getElse() <em>Else</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElse()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit else_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.CONDITIONAL_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getIf() {
		if (if_ != null && if_.eIsProxy()) {
			InternalEObject oldIf = (InternalEObject)if_;
			if_ = (TransformationUnit)eResolveProxy(oldIf);
			if (if_ != oldIf) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.CONDITIONAL_UNIT__IF, oldIf, if_));
			}
		}
		return if_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetIf() {
		return if_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIf(TransformationUnit newIf) {
		TransformationUnit oldIf = if_;
		if_ = newIf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__IF, oldIf, if_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getThen() {
		if (then != null && then.eIsProxy()) {
			InternalEObject oldThen = (InternalEObject)then;
			then = (TransformationUnit)eResolveProxy(oldThen);
			if (then != oldThen) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.CONDITIONAL_UNIT__THEN, oldThen, then));
			}
		}
		return then;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetThen() {
		return then;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThen(TransformationUnit newThen) {
		TransformationUnit oldThen = then;
		then = newThen;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__THEN, oldThen, then));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getElse() {
		if (else_ != null && else_.eIsProxy()) {
			InternalEObject oldElse = (InternalEObject)else_;
			else_ = (TransformationUnit)eResolveProxy(oldElse);
			if (else_ != oldElse) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.CONDITIONAL_UNIT__ELSE, oldElse, else_));
			}
		}
		return else_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetElse() {
		return else_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElse(TransformationUnit newElse) {
		TransformationUnit oldElse = else_;
		else_ = newElse;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__ELSE, oldElse, else_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TransformationUnit> getSubUnits(boolean deep) {
		List<TransformationUnit> allunits = new ArrayList<TransformationUnit>();
		
		allunits.add(if_);
		allunits.add(then);
		allunits.add(else_);
		if (deep) {
			if ((if_ != null) && (if_ != this)) {		// do not recursively add recursive units
					allunits.addAll(if_.getSubUnits(deep));
			}
			if ((then != null) && (then != this)) {		// do not recursively add recursive units
				allunits.addAll(then.getSubUnits(deep));
			}
			if ((else_ != null) && (else_ != this)) {	// do not recursively add recursive units 
				allunits.addAll(else_.getSubUnits(deep));
			}
		}// if
		
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
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				if (resolve) return getIf();
				return basicGetIf();
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				if (resolve) return getThen();
				return basicGetThen();
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				if (resolve) return getElse();
				return basicGetElse();
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
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				setIf((TransformationUnit)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				setThen((TransformationUnit)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				setElse((TransformationUnit)newValue);
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
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				setIf((TransformationUnit)null);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				setThen((TransformationUnit)null);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				setElse((TransformationUnit)null);
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
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				return if_ != null;
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				return then != null;
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				return else_ != null;
		}
		return super.eIsSet(featureID);
	}

} //ConditionalUnitImpl
