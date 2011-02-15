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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Nested Condition</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl#isNegated <em>Negated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl#getConclusion <em>Conclusion</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NestedConditionImpl#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NestedConditionImpl extends FormulaImpl implements NestedCondition {
	/**
	 * The default value of the '{@link #isNegated() <em>Negated</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isNegated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEGATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNegated() <em>Negated</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isNegated()
	 * @generated
	 * @ordered
	 */
	protected boolean negated = NEGATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConclusion() <em>Conclusion</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getConclusion()
	 * @generated
	 * @ordered
	 */
	protected Graph conclusion;

	/**
	 * The cached value of the '{@link #getMappings() <em>Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> mappings;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NestedConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.NESTED_CONDITION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isNegated() {
		return negated;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegated(boolean newNegated) {
		boolean oldNegated = negated;
		negated = newNegated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.NESTED_CONDITION__NEGATED, oldNegated, negated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getConclusion() {
		return conclusion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConclusion(Graph newConclusion,
			NotificationChain msgs) {
		Graph oldConclusion = conclusion;
		conclusion = newConclusion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.NESTED_CONDITION__CONCLUSION, oldConclusion, newConclusion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setConclusion(Graph newConclusion) {
		if (newConclusion != conclusion) {
			NotificationChain msgs = null;
			if (conclusion != null)
				msgs = ((InternalEObject)conclusion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.NESTED_CONDITION__CONCLUSION, null, msgs);
			if (newConclusion != null)
				msgs = ((InternalEObject)newConclusion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.NESTED_CONDITION__CONCLUSION, null, msgs);
			msgs = basicSetConclusion(newConclusion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.NESTED_CONDITION__CONCLUSION, newConclusion, newConclusion));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, HenshinPackage.NESTED_CONDITION__MAPPINGS);
		}
		return mappings;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				return basicSetConclusion(null, msgs);
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
				return ((InternalEList<?>)getMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__NEGATED:
				return isNegated();
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				return getConclusion();
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
				return getMappings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__NEGATED:
				setNegated((Boolean)newValue);
				return;
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				setConclusion((Graph)newValue);
				return;
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
				getMappings().clear();
				getMappings().addAll((Collection<? extends Mapping>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__NEGATED:
				setNegated(NEGATED_EDEFAULT);
				return;
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				setConclusion((Graph)null);
				return;
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
				getMappings().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case HenshinPackage.NESTED_CONDITION__NEGATED:
				return negated != NEGATED_EDEFAULT;
			case HenshinPackage.NESTED_CONDITION__CONCLUSION:
				return conclusion != null;
			case HenshinPackage.NESTED_CONDITION__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (negated: ");
		result.append(negated);
		result.append(')');
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.model.Formula#updateVariableNames(java.lang.String
	 * , java.lang.String)
	 */
	public void updateVariableName(String oldVariableName,
			String newVariableName) {

		for (Node node : this.getConclusion().getNodes()) {
			for (Attribute attribute : node.getAttributes()) {
				((AttributeImpl) attribute).updateVariableName(oldVariableName,
						newVariableName);
			}// for
		}// for
	}// updateVariableName

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String stringRepresentation(boolean recursive) {
		if (!recursive || conclusion.getFormula() == null) {
			return negated ? "!" + conclusion.getName() : conclusion.getName();
		} else {
			return negated ? "!"
					+ conclusion.getFormula().stringRepresentation(recursive)
					: conclusion.getFormula().stringRepresentation(recursive);
		}
	}

} // NestedConditionImpl
