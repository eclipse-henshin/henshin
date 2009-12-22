/**
 * <copyright>
 * </copyright>
 *
 * $Id: SingleUnitImpl.java,v 1.1 2009/10/28 10:38:10 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SingleUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Single Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.SingleUnitImpl#getRule <em>Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SingleUnitImpl extends TransformationUnitImpl implements SingleUnit {
        /**
         * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getRule()
         * @generated
         * @ordered
         */
        protected Rule rule;

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        protected SingleUnitImpl() {
                super();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        protected EClass eStaticClass() {
                return HenshinPackage.Literals.SINGLE_UNIT;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public Rule getRule() {
                if (rule != null && rule.eIsProxy()) {
                        InternalEObject oldRule = (InternalEObject)rule;
                        rule = (Rule)eResolveProxy(oldRule);
                        if (rule != oldRule) {
                                if (eNotificationRequired())
                                        eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.SINGLE_UNIT__RULE, oldRule, rule));
                        }
                }
                return rule;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public Rule basicGetRule() {
                return rule;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public void setRule(Rule newRule) {
                Rule oldRule = rule;
                rule = newRule;
                if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.SINGLE_UNIT__RULE, oldRule, rule));
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
                switch (featureID) {
                        case HenshinPackage.SINGLE_UNIT__RULE:
                                if (resolve) return getRule();
                                return basicGetRule();
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
                        case HenshinPackage.SINGLE_UNIT__RULE:
                                setRule((Rule)newValue);
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
                        case HenshinPackage.SINGLE_UNIT__RULE:
                                setRule((Rule)null);
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
                        case HenshinPackage.SINGLE_UNIT__RULE:
                                return rule != null;
                }
                return super.eIsSet(featureID);
        }

} //SingleUnitImpl
