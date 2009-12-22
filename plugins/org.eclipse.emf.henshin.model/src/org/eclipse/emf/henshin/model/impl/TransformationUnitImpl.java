/**
 * <copyright>
 * </copyright>
 *
 * $Id: TransformationUnitImpl.java,v 1.1 2009/10/28 10:38:15 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transformation Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationUnitImpl#getPorts <em>Ports</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TransformationUnitImpl extends EObjectImpl implements TransformationUnit {
        /**
         * The default value of the '{@link #isActivated() <em>Activated</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #isActivated()
         * @generated
         * @ordered
         */
        protected static final boolean ACTIVATED_EDEFAULT = false;

        /**
         * The cached value of the '{@link #isActivated() <em>Activated</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #isActivated()
         * @generated
         * @ordered
         */
        protected boolean activated = ACTIVATED_EDEFAULT;

        /**
         * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getPorts()
         * @generated
         * @ordered
         */
        protected EList<Port> ports;

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        protected TransformationUnitImpl() {
                super();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        protected EClass eStaticClass() {
                return HenshinPackage.Literals.TRANSFORMATION_UNIT;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public boolean isActivated() {
                return activated;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public void setActivated(boolean newActivated) {
                boolean oldActivated = activated;
                activated = newActivated;
                if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED, oldActivated, activated));
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public EList<Port> getPorts() {
                if (ports == null) {
                        ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, HenshinPackage.TRANSFORMATION_UNIT__PORTS, HenshinPackage.PORT__UNIT);
                }
                return ports;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @SuppressWarnings("unchecked")
        @Override
        public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
                switch (featureID) {
                        case HenshinPackage.TRANSFORMATION_UNIT__PORTS:
                                return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
                }
                return super.eInverseAdd(otherEnd, featureID, msgs);
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
                switch (featureID) {
                        case HenshinPackage.TRANSFORMATION_UNIT__PORTS:
                                return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
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
                        case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
                                return isActivated();
                        case HenshinPackage.TRANSFORMATION_UNIT__PORTS:
                                return getPorts();
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
                        case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
                                setActivated((Boolean)newValue);
                                return;
                        case HenshinPackage.TRANSFORMATION_UNIT__PORTS:
                                getPorts().clear();
                                getPorts().addAll((Collection<? extends Port>)newValue);
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
                        case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
                                setActivated(ACTIVATED_EDEFAULT);
                                return;
                        case HenshinPackage.TRANSFORMATION_UNIT__PORTS:
                                getPorts().clear();
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
                        case HenshinPackage.TRANSFORMATION_UNIT__ACTIVATED:
                                return activated != ACTIVATED_EDEFAULT;
                        case HenshinPackage.TRANSFORMATION_UNIT__PORTS:
                                return ports != null && !ports.isEmpty();
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
                result.append(" (activated: ");
                result.append(activated);
                result.append(')');
                return result.toString();
        }

} //TransformationUnitImpl
