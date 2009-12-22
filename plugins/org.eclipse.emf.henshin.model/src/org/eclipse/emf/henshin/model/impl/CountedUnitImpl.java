/**
 * <copyright>
 * </copyright>
 *
 * $Id: CountedUnitImpl.java,v 1.1 2009/10/28 10:38:12 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Counted Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getSubUnit <em>Sub Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getCount <em>Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CountedUnitImpl extends TransformationUnitImpl implements CountedUnit {
        /**
         * The cached value of the '{@link #getSubUnit() <em>Sub Unit</em>}' containment reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getSubUnit()
         * @generated
         * @ordered
         */
        protected TransformationUnit subUnit;

        /**
         * The default value of the '{@link #getCount() <em>Count</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getCount()
         * @generated
         * @ordered
         */
        protected static final int COUNT_EDEFAULT = 0;

        /**
         * The cached value of the '{@link #getCount() <em>Count</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getCount()
         * @generated
         * @ordered
         */
        protected int count = COUNT_EDEFAULT;

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        protected CountedUnitImpl() {
                super();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        protected EClass eStaticClass() {
                return HenshinPackage.Literals.COUNTED_UNIT;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public TransformationUnit getSubUnit() {
                return subUnit;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public NotificationChain basicSetSubUnit(TransformationUnit newSubUnit, NotificationChain msgs) {
                TransformationUnit oldSubUnit = subUnit;
                subUnit = newSubUnit;
                if (eNotificationRequired()) {
                        ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__SUB_UNIT, oldSubUnit, newSubUnit);
                        if (msgs == null) msgs = notification; else msgs.add(notification);
                }
                return msgs;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public void setSubUnit(TransformationUnit newSubUnit) {
                if (newSubUnit != subUnit) {
                        NotificationChain msgs = null;
                        if (subUnit != null)
                                msgs = ((InternalEObject)subUnit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.COUNTED_UNIT__SUB_UNIT, null, msgs);
                        if (newSubUnit != null)
                                msgs = ((InternalEObject)newSubUnit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.COUNTED_UNIT__SUB_UNIT, null, msgs);
                        msgs = basicSetSubUnit(newSubUnit, msgs);
                        if (msgs != null) msgs.dispatch();
                }
                else if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__SUB_UNIT, newSubUnit, newSubUnit));
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public int getCount() {
                return count;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public void setCount(int newCount) {
                int oldCount = count;
                count = newCount;
                if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__COUNT, oldCount, count));
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
                switch (featureID) {
                        case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
                                return basicSetSubUnit(null, msgs);
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
                        case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
                                return getSubUnit();
                        case HenshinPackage.COUNTED_UNIT__COUNT:
                                return getCount();
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
                        case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
                                setSubUnit((TransformationUnit)newValue);
                                return;
                        case HenshinPackage.COUNTED_UNIT__COUNT:
                                setCount((Integer)newValue);
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
                        case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
                                setSubUnit((TransformationUnit)null);
                                return;
                        case HenshinPackage.COUNTED_UNIT__COUNT:
                                setCount(COUNT_EDEFAULT);
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
                        case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
                                return subUnit != null;
                        case HenshinPackage.COUNTED_UNIT__COUNT:
                                return count != COUNT_EDEFAULT;
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
                result.append(" (count: ");
                result.append(count);
                result.append(')');
                return result.toString();
        }

} //CountedUnitImpl
