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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transformation System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationSystemImpl#getTransformationUnits <em>Transformation Units</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransformationSystemImpl extends DescribedElementImpl implements TransformationSystem {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> rules;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> imports;

	/**
	 * The cached value of the '{@link #getInstances() <em>Instances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Graph> instances;

	/**
	 * The cached value of the '{@link #getTransformationUnits() <em>Transformation Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<TransformationUnit> transformationUnits;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformationSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.TRANSFORMATION_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION_SYSTEM__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<Rule>(Rule.class, this, HenshinPackage.TRANSFORMATION_SYSTEM__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getImports() {
		if (imports == null) {
			imports = new EObjectResolvingEList<EPackage>(EPackage.class, this, HenshinPackage.TRANSFORMATION_SYSTEM__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Graph> getInstances() {
		if (instances == null) {
			instances = new EObjectContainmentEList<Graph>(Graph.class, this, HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TransformationUnit> getTransformationUnits() {
		if (transformationUnits == null) {
			transformationUnits = new EObjectContainmentEList<TransformationUnit>(TransformationUnit.class, this, HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS);
		}
		return transformationUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TransformationUnit findUnitByName(String unitName) {
		for (TransformationUnit unit : getTransformationUnits()) {
			if (unitName.equals(unit.getName())) {
				return unit;
			}
		}
		for (Rule rule : getRules()) {
			if (unitName.equals(rule.getName())) {
				return rule;
			}
		}
		return null;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Rule findRuleByName(String ruleName) {
		for (Rule rule : getRules()) {
			if (ruleName.equals(rule.getName())) {
				return rule;
			}
		}
		for (TransformationUnit unit : getTransformationUnits()) {
			if (unit instanceof Rule && ruleName.equals(unit.getName())) {
				return (Rule) unit;
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.TRANSFORMATION_SYSTEM__RULES:
				return ((InternalEList<?>)getRules()).basicRemove(otherEnd, msgs);
			case HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
			case HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS:
				return ((InternalEList<?>)getTransformationUnits()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.TRANSFORMATION_SYSTEM__NAME:
				return getName();
			case HenshinPackage.TRANSFORMATION_SYSTEM__RULES:
				return getRules();
			case HenshinPackage.TRANSFORMATION_SYSTEM__IMPORTS:
				return getImports();
			case HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES:
				return getInstances();
			case HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS:
				return getTransformationUnits();
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
			case HenshinPackage.TRANSFORMATION_SYSTEM__NAME:
				setName((String)newValue);
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends EPackage>)newValue);
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends Graph>)newValue);
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS:
				getTransformationUnits().clear();
				getTransformationUnits().addAll((Collection<? extends TransformationUnit>)newValue);
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
			case HenshinPackage.TRANSFORMATION_SYSTEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__RULES:
				getRules().clear();
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__IMPORTS:
				getImports().clear();
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES:
				getInstances().clear();
				return;
			case HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS:
				getTransformationUnits().clear();
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
			case HenshinPackage.TRANSFORMATION_SYSTEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case HenshinPackage.TRANSFORMATION_SYSTEM__RULES:
				return rules != null && !rules.isEmpty();
			case HenshinPackage.TRANSFORMATION_SYSTEM__IMPORTS:
				return imports != null && !imports.isEmpty();
			case HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES:
				return instances != null && !instances.isEmpty();
			case HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS:
				return transformationUnits != null && !transformationUnits.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case HenshinPackage.TRANSFORMATION_SYSTEM__NAME: return HenshinPackage.NAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case HenshinPackage.NAMED_ELEMENT__NAME: return HenshinPackage.TRANSFORMATION_SYSTEM__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TransformationSystemImpl