/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iterated Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl#getSubUnit <em>Sub Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl#getIterations <em>Iterations</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IteratedUnitImpl extends TransformationUnitImpl implements IteratedUnit {
	
	/**
	 * The cached value of the '{@link #getSubUnit() <em>Sub Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit subUnit;

	/**
	 * The default value of the '{@link #getIterations() <em>Iterations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterations()
	 * @generated
	 * @ordered
	 */
	protected static final String ITERATIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIterations() <em>Iterations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterations()
	 * @generated
	 * @ordered
	 */
	protected String iterations = ITERATIONS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IteratedUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.ITERATED_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getSubUnit() {
		if (subUnit != null && subUnit.eIsProxy()) {
			InternalEObject oldSubUnit = (InternalEObject)subUnit;
			subUnit = (TransformationUnit)eResolveProxy(oldSubUnit);
			if (subUnit != oldSubUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.ITERATED_UNIT__SUB_UNIT, oldSubUnit, subUnit));
			}
		}
		return subUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetSubUnit() {
		return subUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubUnit(TransformationUnit newSubUnit) {
		TransformationUnit oldSubUnit = subUnit;
		subUnit = newSubUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.ITERATED_UNIT__SUB_UNIT, oldSubUnit, subUnit));
	}

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TransformationUnit> getSubUnits() {
		if (subUnit!=null) {
			EList<TransformationUnit> list = new BasicEList<TransformationUnit>();
			list.add(subUnit);
			return list;
		} else {
			return ECollections.emptyEList();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIterations() {
		return iterations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIterations(String newIterations) {
		String oldIterations = iterations;
		iterations = newIterations;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.ITERATED_UNIT__ITERATIONS, oldIterations, iterations));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.ITERATED_UNIT__SUB_UNIT:
				if (resolve) return getSubUnit();
				return basicGetSubUnit();
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				return getIterations();
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
			case HenshinPackage.ITERATED_UNIT__SUB_UNIT:
				setSubUnit((TransformationUnit)newValue);
				return;
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				setIterations((String)newValue);
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
			case HenshinPackage.ITERATED_UNIT__SUB_UNIT:
				setSubUnit((TransformationUnit)null);
				return;
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				setIterations(ITERATIONS_EDEFAULT);
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
			case HenshinPackage.ITERATED_UNIT__SUB_UNIT:
				return subUnit != null;
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				return ITERATIONS_EDEFAULT == null ? iterations != null : !ITERATIONS_EDEFAULT.equals(iterations);
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
		result.append(" (iterations: ");
		result.append(iterations);
		result.append(')');
		return result.toString();
	}

} //IteratedUnitImpl
