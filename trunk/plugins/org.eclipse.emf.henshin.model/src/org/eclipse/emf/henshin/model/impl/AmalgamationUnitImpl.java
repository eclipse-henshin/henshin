/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Amalgamation Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamationUnitImpl#getKernelRule <em>Kernel Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamationUnitImpl#getMultiRules <em>Multi Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamationUnitImpl#getLhsMappings <em>Lhs Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamationUnitImpl#getRhsMappings <em>Rhs Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AmalgamationUnitImpl extends TransformationUnitImpl implements AmalgamationUnit {
	/**
	 * The cached value of the '{@link #getKernelRule() <em>Kernel Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKernelRule()
	 * @generated
	 * @ordered
	 */
	protected Rule kernelRule;

	/**
	 * The cached value of the '{@link #getMultiRules() <em>Multi Rules</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiRules()
	 * @generated
	 * @ordered
	 */
	protected EList<Rule> multiRules;

	/**
	 * The cached value of the '{@link #getLhsMappings() <em>Lhs Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> lhsMappings;

	/**
	 * The cached value of the '{@link #getRhsMappings() <em>Rhs Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhsMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> rhsMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AmalgamationUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.AMALGAMATION_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getKernelRule() {
		if (kernelRule != null && kernelRule.eIsProxy()) {
			InternalEObject oldKernelRule = (InternalEObject)kernelRule;
			kernelRule = (Rule)eResolveProxy(oldKernelRule);
			if (kernelRule != oldKernelRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE, oldKernelRule, kernelRule));
			}
		}
		return kernelRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetKernelRule() {
		return kernelRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKernelRule(Rule newKernelRule) {
		Rule oldKernelRule = kernelRule;
		kernelRule = newKernelRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE, oldKernelRule, kernelRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getMultiRules() {
		if (multiRules == null) {
			multiRules = new EObjectResolvingEList<Rule>(Rule.class, this, HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES);
		}
		return multiRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getLhsMappings() {
		if (lhsMappings == null) {
			lhsMappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS);
		}
		return lhsMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Mapping> getRhsMappings() {
		if (rhsMappings == null) {
			rhsMappings = new EObjectContainmentEList<Mapping>(Mapping.class, this, HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS);
		}
		return rhsMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS:
				return ((InternalEList<?>)getLhsMappings()).basicRemove(otherEnd, msgs);
			case HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS:
				return ((InternalEList<?>)getRhsMappings()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE:
				if (resolve) return getKernelRule();
				return basicGetKernelRule();
			case HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES:
				return getMultiRules();
			case HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS:
				return getLhsMappings();
			case HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS:
				return getRhsMappings();
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
			case HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE:
				setKernelRule((Rule)newValue);
				return;
			case HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES:
				getMultiRules().clear();
				getMultiRules().addAll((Collection<? extends Rule>)newValue);
				return;
			case HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS:
				getLhsMappings().clear();
				getLhsMappings().addAll((Collection<? extends Mapping>)newValue);
				return;
			case HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS:
				getRhsMappings().clear();
				getRhsMappings().addAll((Collection<? extends Mapping>)newValue);
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
			case HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE:
				setKernelRule((Rule)null);
				return;
			case HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES:
				getMultiRules().clear();
				return;
			case HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS:
				getLhsMappings().clear();
				return;
			case HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS:
				getRhsMappings().clear();
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
			case HenshinPackage.AMALGAMATION_UNIT__KERNEL_RULE:
				return kernelRule != null;
			case HenshinPackage.AMALGAMATION_UNIT__MULTI_RULES:
				return multiRules != null && !multiRules.isEmpty();
			case HenshinPackage.AMALGAMATION_UNIT__LHS_MAPPINGS:
				return lhsMappings != null && !lhsMappings.isEmpty();
			case HenshinPackage.AMALGAMATION_UNIT__RHS_MAPPINGS:
				return rhsMappings != null && !rhsMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TransformationUnit> getSubUnits(boolean deep) {
		List<TransformationUnit> allunits = new ArrayList<TransformationUnit>();

		// use getters for lazy initialization and to resolve proxies.
		//
		allunits.add(getKernelRule());
		allunits.addAll(getMultiRules());
		
		return new BasicEList<TransformationUnit>(allunits);
	}// getSubUnits

} //AmalgamationUnitImpl
