/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenRule;

import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenRuleImpl#isCheckDangling <em>Check Dangling</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenRuleImpl#isCheckInverseDangling <em>Check Inverse Dangling</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenRuleImpl extends GenUnitImpl implements GenRule {
	/**
	 * The default value of the '{@link #isCheckDangling() <em>Check Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckDangling()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHECK_DANGLING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCheckDangling() <em>Check Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckDangling()
	 * @generated
	 * @ordered
	 */
	protected boolean checkDangling = CHECK_DANGLING_EDEFAULT;

	/**
	 * The default value of the '{@link #isCheckInverseDangling() <em>Check Inverse Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckInverseDangling()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CHECK_INVERSE_DANGLING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCheckInverseDangling() <em>Check Inverse Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCheckInverseDangling()
	 * @generated
	 * @ordered
	 */
	protected boolean checkInverseDangling = CHECK_INVERSE_DANGLING_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenHenshinPackage.Literals.GEN_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCheckDangling() {
		return checkDangling;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckDangling(boolean newCheckDangling) {
		boolean oldCheckDangling = checkDangling;
		checkDangling = newCheckDangling;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_RULE__CHECK_DANGLING, oldCheckDangling, checkDangling));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCheckInverseDangling() {
		return checkInverseDangling;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckInverseDangling(boolean newCheckInverseDangling) {
		boolean oldCheckInverseDangling = checkInverseDangling;
		checkInverseDangling = newCheckInverseDangling;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_RULE__CHECK_INVERSE_DANGLING, oldCheckInverseDangling, checkInverseDangling));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule getRule() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenHenshinPackage.GEN_RULE__CHECK_DANGLING:
				return isCheckDangling();
			case GenHenshinPackage.GEN_RULE__CHECK_INVERSE_DANGLING:
				return isCheckInverseDangling();
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
			case GenHenshinPackage.GEN_RULE__CHECK_DANGLING:
				setCheckDangling((Boolean)newValue);
				return;
			case GenHenshinPackage.GEN_RULE__CHECK_INVERSE_DANGLING:
				setCheckInverseDangling((Boolean)newValue);
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
			case GenHenshinPackage.GEN_RULE__CHECK_DANGLING:
				setCheckDangling(CHECK_DANGLING_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_RULE__CHECK_INVERSE_DANGLING:
				setCheckInverseDangling(CHECK_INVERSE_DANGLING_EDEFAULT);
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
			case GenHenshinPackage.GEN_RULE__CHECK_DANGLING:
				return checkDangling != CHECK_DANGLING_EDEFAULT;
			case GenHenshinPackage.GEN_RULE__CHECK_INVERSE_DANGLING:
				return checkInverseDangling != CHECK_INVERSE_DANGLING_EDEFAULT;
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
		result.append(" (checkDangling: ");
		result.append(checkDangling);
		result.append(", checkInverseDangling: ");
		result.append(checkInverseDangling);
		result.append(')');
		return result.toString();
	}

} //GenRuleImpl
