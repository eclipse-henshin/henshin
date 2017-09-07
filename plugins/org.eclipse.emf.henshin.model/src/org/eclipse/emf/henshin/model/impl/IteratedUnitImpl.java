/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IteratedUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Iterated Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl#getIterations <em>Iterations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl#isStrict <em>Strict</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.IteratedUnitImpl#isRollback <em>Rollback</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IteratedUnitImpl extends UnaryUnitImpl implements IteratedUnit {
	
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
	 * The default value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRICT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected boolean strict = STRICT_EDEFAULT;

	/**
	 * The default value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRollback()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ROLLBACK_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRollback()
	 * @generated
	 * @ordered
	 */
	protected boolean rollback = ROLLBACK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IteratedUnitImpl() {
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
	public boolean isStrict() {
		return strict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrict(boolean newStrict) {
		boolean oldStrict = strict;
		strict = newStrict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.ITERATED_UNIT__STRICT, oldStrict, strict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRollback() {
		return rollback;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRollback(boolean newRollback) {
		boolean oldRollback = rollback;
		rollback = newRollback;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.ITERATED_UNIT__ROLLBACK, oldRollback, rollback));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				return getIterations();
			case HenshinPackage.ITERATED_UNIT__STRICT:
				return isStrict();
			case HenshinPackage.ITERATED_UNIT__ROLLBACK:
				return isRollback();
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
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				setIterations((String)newValue);
				return;
			case HenshinPackage.ITERATED_UNIT__STRICT:
				setStrict((Boolean)newValue);
				return;
			case HenshinPackage.ITERATED_UNIT__ROLLBACK:
				setRollback((Boolean)newValue);
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
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				setIterations(ITERATIONS_EDEFAULT);
				return;
			case HenshinPackage.ITERATED_UNIT__STRICT:
				setStrict(STRICT_EDEFAULT);
				return;
			case HenshinPackage.ITERATED_UNIT__ROLLBACK:
				setRollback(ROLLBACK_EDEFAULT);
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
			case HenshinPackage.ITERATED_UNIT__ITERATIONS:
				return ITERATIONS_EDEFAULT == null ? iterations != null : !ITERATIONS_EDEFAULT.equals(iterations);
			case HenshinPackage.ITERATED_UNIT__STRICT:
				return strict != STRICT_EDEFAULT;
			case HenshinPackage.ITERATED_UNIT__ROLLBACK:
				return rollback != ROLLBACK_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		String result = super.toString();
		if (iterations==null || iterations.trim().length()==0) {
			return result + " [?]";
		} else {
			return result + " [" + iterations + "]";			
		}
	}

} //IteratedUnitImpl
