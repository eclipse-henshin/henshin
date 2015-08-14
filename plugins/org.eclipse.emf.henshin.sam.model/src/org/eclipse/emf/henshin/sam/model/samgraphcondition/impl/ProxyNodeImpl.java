/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.NodeImpl;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proxy Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.ProxyNodeImpl#getReferencedNode <em>Referenced Node</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProxyNodeImpl extends NodeImpl implements ProxyNode {
	/**
	 * The cached value of the '{@link #getReferencedNode() <em>Referenced Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedNode()
	 * @generated
	 * @ordered
	 */
	protected Node referencedNode;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProxyNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamgraphconditionPackage.Literals.PROXY_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getReferencedNode() {
		if (referencedNode != null && referencedNode.eIsProxy()) {
			InternalEObject oldReferencedNode = (InternalEObject)referencedNode;
			referencedNode = (Node)eResolveProxy(oldReferencedNode);
			if (referencedNode != oldReferencedNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamgraphconditionPackage.PROXY_NODE__REFERENCED_NODE, oldReferencedNode, referencedNode));
			}
		}
		return referencedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetReferencedNode() {
		return referencedNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferencedNode(Node newReferencedNode) {
		Node oldReferencedNode = referencedNode;
		referencedNode = newReferencedNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.PROXY_NODE__REFERENCED_NODE, oldReferencedNode, referencedNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamgraphconditionPackage.PROXY_NODE__REFERENCED_NODE:
				if (resolve) return getReferencedNode();
				return basicGetReferencedNode();
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
			case SamgraphconditionPackage.PROXY_NODE__REFERENCED_NODE:
				setReferencedNode((Node)newValue);
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
			case SamgraphconditionPackage.PROXY_NODE__REFERENCED_NODE:
				setReferencedNode((Node)null);
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
			case SamgraphconditionPackage.PROXY_NODE__REFERENCED_NODE:
				return referencedNode != null;
		}
		return super.eIsSet(featureID);
	}

} //ProxyNodeImpl
