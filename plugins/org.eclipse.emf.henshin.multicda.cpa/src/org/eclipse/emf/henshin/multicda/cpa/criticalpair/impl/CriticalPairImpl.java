/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair;
import org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalpairPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Critical Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl#getFirstRule <em>First Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl#getSecondRule <em>Second Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl#getMinimalModel <em>Minimal Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CriticalPairImpl extends MinimalEObjectImpl.Container implements CriticalPair {
	/**
	 * The cached value of the '{@link #getFirstRule() <em>First Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstRule()
	 * @generated
	 * @ordered
	 */
	protected Rule firstRule;

	/**
	 * The cached value of the '{@link #getSecondRule() <em>Second Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondRule()
	 * @generated
	 * @ordered
	 */
	protected Rule secondRule;

	/**
	 * The cached value of the '{@link #getMinimalModel() <em>Minimal Model</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinimalModel()
	 * @generated
	 * @ordered
	 */
	protected EPackage minimalModel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CriticalPairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CriticalpairPackage.Literals.CRITICAL_PAIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getFirstRule() {
		return firstRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFirstRule(Rule newFirstRule, NotificationChain msgs) {
		Rule oldFirstRule = firstRule;
		firstRule = newFirstRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE, oldFirstRule, newFirstRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstRule(Rule newFirstRule) {
		if (newFirstRule != firstRule) {
			NotificationChain msgs = null;
			if (firstRule != null)
				msgs = ((InternalEObject)firstRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE, null, msgs);
			if (newFirstRule != null)
				msgs = ((InternalEObject)newFirstRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE, null, msgs);
			msgs = basicSetFirstRule(newFirstRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE, newFirstRule, newFirstRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getSecondRule() {
		return secondRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecondRule(Rule newSecondRule, NotificationChain msgs) {
		Rule oldSecondRule = secondRule;
		secondRule = newSecondRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE, oldSecondRule, newSecondRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondRule(Rule newSecondRule) {
		if (newSecondRule != secondRule) {
			NotificationChain msgs = null;
			if (secondRule != null)
				msgs = ((InternalEObject)secondRule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE, null, msgs);
			if (newSecondRule != null)
				msgs = ((InternalEObject)newSecondRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE, null, msgs);
			msgs = basicSetSecondRule(newSecondRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE, newSecondRule, newSecondRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getMinimalModel() {
		return minimalModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMinimalModel(EPackage newMinimalModel, NotificationChain msgs) {
		EPackage oldMinimalModel = minimalModel;
		minimalModel = newMinimalModel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL, oldMinimalModel, newMinimalModel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinimalModel(EPackage newMinimalModel) {
		if (newMinimalModel != minimalModel) {
			NotificationChain msgs = null;
			if (minimalModel != null)
				msgs = ((InternalEObject)minimalModel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL, null, msgs);
			if (newMinimalModel != null)
				msgs = ((InternalEObject)newMinimalModel).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL, null, msgs);
			msgs = basicSetMinimalModel(newMinimalModel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL, newMinimalModel, newMinimalModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE:
				return basicSetFirstRule(null, msgs);
			case CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE:
				return basicSetSecondRule(null, msgs);
			case CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL:
				return basicSetMinimalModel(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE:
				return getFirstRule();
			case CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE:
				return getSecondRule();
			case CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL:
				return getMinimalModel();
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
			case CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE:
				setFirstRule((Rule)newValue);
				return;
			case CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE:
				setSecondRule((Rule)newValue);
				return;
			case CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL:
				setMinimalModel((EPackage)newValue);
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
			case CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE:
				setFirstRule((Rule)null);
				return;
			case CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE:
				setSecondRule((Rule)null);
				return;
			case CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL:
				setMinimalModel((EPackage)null);
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
			case CriticalpairPackage.CRITICAL_PAIR__FIRST_RULE:
				return firstRule != null;
			case CriticalpairPackage.CRITICAL_PAIR__SECOND_RULE:
				return secondRule != null;
			case CriticalpairPackage.CRITICAL_PAIR__MINIMAL_MODEL:
				return minimalModel != null;
		}
		return super.eIsSet(featureID);
	}

} //CriticalPairImpl
