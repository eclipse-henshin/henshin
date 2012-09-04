/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ModuleImpl#getSubModules <em>Sub Modules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ModuleImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ModuleImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ModuleImpl#getTransformationUnits <em>Transformation Units</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ModuleImpl#getInstances <em>Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ModuleImpl extends NamedElementImpl implements Module {
	/**
	 * The cached value of the '{@link #getSubModules() <em>Sub Modules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubModules()
	 * @generated
	 * @ordered
	 */
	protected EList<Module> subModules;


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
	 * The cached value of the '{@link #getTransformationUnits() <em>Transformation Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<TransformationUnit> transformationUnits;


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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Module> getSubModules() {
		if (subModules == null) {
			subModules = new EObjectContainmentEList<Module>(Module.class, this, HenshinPackage.MODULE__SUB_MODULES);
		}
		return subModules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<Rule>(Rule.class, this, HenshinPackage.MODULE__RULES);
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
			imports = new EObjectResolvingEList<EPackage>(EPackage.class, this, HenshinPackage.MODULE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TransformationUnit> getTransformationUnits() {
		if (transformationUnits == null) {
			transformationUnits = new EObjectContainmentEList<TransformationUnit>(TransformationUnit.class, this, HenshinPackage.MODULE__TRANSFORMATION_UNITS);
		}
		return transformationUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Graph> getInstances() {
		if (instances == null) {
			instances = new EObjectContainmentEList<Graph>(Graph.class, this, HenshinPackage.MODULE__INSTANCES);
		}
		return instances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public TransformationUnit getTransformationUnit(String unitName) {
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
	public Rule getRule(String ruleName) {
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
			case HenshinPackage.MODULE__SUB_MODULES:
				return ((InternalEList<?>)getSubModules()).basicRemove(otherEnd, msgs);
			case HenshinPackage.MODULE__RULES:
				return ((InternalEList<?>)getRules()).basicRemove(otherEnd, msgs);
			case HenshinPackage.MODULE__TRANSFORMATION_UNITS:
				return ((InternalEList<?>)getTransformationUnits()).basicRemove(otherEnd, msgs);
			case HenshinPackage.MODULE__INSTANCES:
				return ((InternalEList<?>)getInstances()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.MODULE__SUB_MODULES:
				return getSubModules();
			case HenshinPackage.MODULE__RULES:
				return getRules();
			case HenshinPackage.MODULE__IMPORTS:
				return getImports();
			case HenshinPackage.MODULE__TRANSFORMATION_UNITS:
				return getTransformationUnits();
			case HenshinPackage.MODULE__INSTANCES:
				return getInstances();
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
			case HenshinPackage.MODULE__SUB_MODULES:
				getSubModules().clear();
				getSubModules().addAll((Collection<? extends Module>)newValue);
				return;
			case HenshinPackage.MODULE__RULES:
				getRules().clear();
				getRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case HenshinPackage.MODULE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends EPackage>)newValue);
				return;
			case HenshinPackage.MODULE__TRANSFORMATION_UNITS:
				getTransformationUnits().clear();
				getTransformationUnits().addAll((Collection<? extends TransformationUnit>)newValue);
				return;
			case HenshinPackage.MODULE__INSTANCES:
				getInstances().clear();
				getInstances().addAll((Collection<? extends Graph>)newValue);
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
			case HenshinPackage.MODULE__SUB_MODULES:
				getSubModules().clear();
				return;
			case HenshinPackage.MODULE__RULES:
				getRules().clear();
				return;
			case HenshinPackage.MODULE__IMPORTS:
				getImports().clear();
				return;
			case HenshinPackage.MODULE__TRANSFORMATION_UNITS:
				getTransformationUnits().clear();
				return;
			case HenshinPackage.MODULE__INSTANCES:
				getInstances().clear();
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
			case HenshinPackage.MODULE__SUB_MODULES:
				return subModules != null && !subModules.isEmpty();
			case HenshinPackage.MODULE__RULES:
				return rules != null && !rules.isEmpty();
			case HenshinPackage.MODULE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case HenshinPackage.MODULE__TRANSFORMATION_UNITS:
				return transformationUnits != null && !transformationUnits.isEmpty();
			case HenshinPackage.MODULE__INSTANCES:
				return instances != null && !instances.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ModuleImpl
