/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.model.samtypegraph.ContinuousAttr;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Continuous Attr</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl#getDerivation <em>Derivation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl#getLowerBound <em>Lower Bound</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.ContinuousAttrImpl#getUpperBound <em>Upper Bound</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContinuousAttrImpl extends AttributeTypeImpl implements ContinuousAttr {
	/**
	 * The default value of the '{@link #getDerivation() <em>Derivation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDerivation()
	 * @generated
	 * @ordered
	 */
	protected static final float DERIVATION_EDEFAULT = 0.0F;

	/**
	 * The cached value of the '{@link #getDerivation() <em>Derivation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDerivation()
	 * @generated
	 * @ordered
	 */
	protected float derivation = DERIVATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected static final double LOWER_BOUND_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getLowerBound() <em>Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerBound()
	 * @generated
	 * @ordered
	 */
	protected double lowerBound = LOWER_BOUND_EDEFAULT;

	/**
	 * This is true if the Lower Bound attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lowerBoundESet;

	/**
	 * The default value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected static final double UPPER_BOUND_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getUpperBound() <em>Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperBound()
	 * @generated
	 * @ordered
	 */
	protected double upperBound = UPPER_BOUND_EDEFAULT;

	/**
	 * This is true if the Upper Bound attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean upperBoundESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContinuousAttrImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtypegraphPackage.Literals.CONTINUOUS_ATTR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getDerivation() {
		return derivation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDerivation(float newDerivation) {
		float oldDerivation = derivation;
		derivation = newDerivation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.CONTINUOUS_ATTR__DERIVATION, oldDerivation, derivation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLowerBound() {
		return lowerBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerBound(double newLowerBound) {
		double oldLowerBound = lowerBound;
		lowerBound = newLowerBound;
		boolean oldLowerBoundESet = lowerBoundESet;
		lowerBoundESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.CONTINUOUS_ATTR__LOWER_BOUND, oldLowerBound, lowerBound, !oldLowerBoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLowerBound() {
		double oldLowerBound = lowerBound;
		boolean oldLowerBoundESet = lowerBoundESet;
		lowerBound = LOWER_BOUND_EDEFAULT;
		lowerBoundESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SamtypegraphPackage.CONTINUOUS_ATTR__LOWER_BOUND, oldLowerBound, LOWER_BOUND_EDEFAULT, oldLowerBoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLowerBound() {
		return lowerBoundESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getUpperBound() {
		return upperBound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperBound(double newUpperBound) {
		double oldUpperBound = upperBound;
		upperBound = newUpperBound;
		boolean oldUpperBoundESet = upperBoundESet;
		upperBoundESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.CONTINUOUS_ATTR__UPPER_BOUND, oldUpperBound, upperBound, !oldUpperBoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetUpperBound() {
		double oldUpperBound = upperBound;
		boolean oldUpperBoundESet = upperBoundESet;
		upperBound = UPPER_BOUND_EDEFAULT;
		upperBoundESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SamtypegraphPackage.CONTINUOUS_ATTR__UPPER_BOUND, oldUpperBound, UPPER_BOUND_EDEFAULT, oldUpperBoundESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetUpperBound() {
		return upperBoundESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamtypegraphPackage.CONTINUOUS_ATTR__DERIVATION:
				return getDerivation();
			case SamtypegraphPackage.CONTINUOUS_ATTR__LOWER_BOUND:
				return getLowerBound();
			case SamtypegraphPackage.CONTINUOUS_ATTR__UPPER_BOUND:
				return getUpperBound();
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
			case SamtypegraphPackage.CONTINUOUS_ATTR__DERIVATION:
				setDerivation((Float)newValue);
				return;
			case SamtypegraphPackage.CONTINUOUS_ATTR__LOWER_BOUND:
				setLowerBound((Double)newValue);
				return;
			case SamtypegraphPackage.CONTINUOUS_ATTR__UPPER_BOUND:
				setUpperBound((Double)newValue);
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
			case SamtypegraphPackage.CONTINUOUS_ATTR__DERIVATION:
				setDerivation(DERIVATION_EDEFAULT);
				return;
			case SamtypegraphPackage.CONTINUOUS_ATTR__LOWER_BOUND:
				unsetLowerBound();
				return;
			case SamtypegraphPackage.CONTINUOUS_ATTR__UPPER_BOUND:
				unsetUpperBound();
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
			case SamtypegraphPackage.CONTINUOUS_ATTR__DERIVATION:
				return derivation != DERIVATION_EDEFAULT;
			case SamtypegraphPackage.CONTINUOUS_ATTR__LOWER_BOUND:
				return isSetLowerBound();
			case SamtypegraphPackage.CONTINUOUS_ATTR__UPPER_BOUND:
				return isSetUpperBound();
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
		result.append(" (derivation: ");
		result.append(derivation);
		result.append(", lowerBound: ");
		if (lowerBoundESet) result.append(lowerBound); else result.append("<unset>");
		result.append(", upperBound: ");
		if (upperBoundESet) result.append(upperBound); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ContinuousAttrImpl
