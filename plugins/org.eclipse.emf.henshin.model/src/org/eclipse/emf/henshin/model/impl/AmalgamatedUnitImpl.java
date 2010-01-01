/**
 * <copyright>
 * </copyright>
 *
 * $Id: AmalgamatedUnitImpl.java,v 1.1 2009/10/28 10:38:10 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.henshin.model.AmalgamatedUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Amalgamated Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl#getKernelRule <em>Kernel Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl#getMultiRules <em>Multi Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl#getLhsMappings <em>Lhs Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.AmalgamatedUnitImpl#getRhsMappings <em>Rhs Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AmalgamatedUnitImpl extends TransformationUnitImpl implements AmalgamatedUnit {
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
	 * The cached value of the '{@link #getLhsMappings() <em>Lhs Mappings</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhsMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<Mapping> lhsMappings;

	/**
	 * The cached value of the '{@link #getRhsMappings() <em>Rhs Mappings</em>}' reference list.
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
	protected AmalgamatedUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.AMALGAMATED_UNIT;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.AMALGAMATED_UNIT__KERNEL_RULE, oldKernelRule, kernelRule));
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
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.AMALGAMATED_UNIT__KERNEL_RULE, oldKernelRule, kernelRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Rule> getMultiRules() {
		if (multiRules == null) {
			multiRules = new EObjectResolvingEList<Rule>(Rule.class, this, HenshinPackage.AMALGAMATED_UNIT__MULTI_RULES);
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
			lhsMappings = new EObjectResolvingEList<Mapping>(Mapping.class, this, HenshinPackage.AMALGAMATED_UNIT__LHS_MAPPINGS);
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
			rhsMappings = new EObjectResolvingEList<Mapping>(Mapping.class, this, HenshinPackage.AMALGAMATED_UNIT__RHS_MAPPINGS);
		}
		return rhsMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case HenshinPackage.AMALGAMATED_UNIT__KERNEL_RULE:
			if (resolve) return getKernelRule();
			return basicGetKernelRule();
		case HenshinPackage.AMALGAMATED_UNIT__MULTI_RULES:
			return getMultiRules();
		case HenshinPackage.AMALGAMATED_UNIT__LHS_MAPPINGS:
			return getLhsMappings();
		case HenshinPackage.AMALGAMATED_UNIT__RHS_MAPPINGS:
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
		case HenshinPackage.AMALGAMATED_UNIT__KERNEL_RULE:
			setKernelRule((Rule)newValue);
			return;
		case HenshinPackage.AMALGAMATED_UNIT__MULTI_RULES:
			getMultiRules().clear();
			getMultiRules().addAll((Collection<? extends Rule>)newValue);
			return;
		case HenshinPackage.AMALGAMATED_UNIT__LHS_MAPPINGS:
			getLhsMappings().clear();
			getLhsMappings().addAll((Collection<? extends Mapping>)newValue);
			return;
		case HenshinPackage.AMALGAMATED_UNIT__RHS_MAPPINGS:
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
		case HenshinPackage.AMALGAMATED_UNIT__KERNEL_RULE:
			setKernelRule((Rule)null);
			return;
		case HenshinPackage.AMALGAMATED_UNIT__MULTI_RULES:
			getMultiRules().clear();
			return;
		case HenshinPackage.AMALGAMATED_UNIT__LHS_MAPPINGS:
			getLhsMappings().clear();
			return;
		case HenshinPackage.AMALGAMATED_UNIT__RHS_MAPPINGS:
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
		case HenshinPackage.AMALGAMATED_UNIT__KERNEL_RULE:
			return kernelRule != null;
		case HenshinPackage.AMALGAMATED_UNIT__MULTI_RULES:
			return multiRules != null && !multiRules.isEmpty();
		case HenshinPackage.AMALGAMATED_UNIT__LHS_MAPPINGS:
			return lhsMappings != null && !lhsMappings.isEmpty();
		case HenshinPackage.AMALGAMATED_UNIT__RHS_MAPPINGS:
			return rhsMappings != null && !rhsMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AmalgamatedUnitImpl
