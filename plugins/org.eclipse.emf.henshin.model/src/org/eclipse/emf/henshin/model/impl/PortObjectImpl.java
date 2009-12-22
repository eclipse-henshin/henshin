/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortObjectImpl.java,v 1.1 2009/10/28 10:38:12 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.PortObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.PortObjectImpl#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortObjectImpl extends PortImpl implements PortObject {
        /**
         * The cached value of the '{@link #getNode() <em>Node</em>}' reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see #getNode()
         * @generated
         * @ordered
         */
        protected Node node;

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        protected PortObjectImpl() {
                super();
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        protected EClass eStaticClass() {
                return HenshinPackage.Literals.PORT_OBJECT;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public Node getNode() {
                if (node != null && node.eIsProxy()) {
                        InternalEObject oldNode = (InternalEObject)node;
                        node = (Node)eResolveProxy(oldNode);
                        if (node != oldNode) {
                                if (eNotificationRequired())
                                        eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.PORT_OBJECT__NODE, oldNode, node));
                        }
                }
                return node;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public Node basicGetNode() {
                return node;
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        public void setNode(Node newNode) {
                Node oldNode = node;
                node = newNode;
                if (eNotificationRequired())
                        eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.PORT_OBJECT__NODE, oldNode, node));
        }

        /**
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
                switch (featureID) {
                        case HenshinPackage.PORT_OBJECT__NODE:
                                if (resolve) return getNode();
                                return basicGetNode();
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
                        case HenshinPackage.PORT_OBJECT__NODE:
                                setNode((Node)newValue);
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
                        case HenshinPackage.PORT_OBJECT__NODE:
                                setNode((Node)null);
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
                        case HenshinPackage.PORT_OBJECT__NODE:
                                return node != null;
                }
                return super.eIsSet(featureID);
        }

} //PortObjectImpl
