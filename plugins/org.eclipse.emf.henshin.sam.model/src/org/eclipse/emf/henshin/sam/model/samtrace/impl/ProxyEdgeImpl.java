/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.EdgeImpl;
import org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Proxy Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.ProxyEdgeImpl#getReferenceEdge <em>Reference Edge</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProxyEdgeImpl extends EdgeImpl implements ProxyEdge {
	/**
	 * The cached value of the '{@link #getReferenceEdge() <em>Reference Edge</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceEdge()
	 * @generated
	 * @ordered
	 */
	protected Edge referenceEdge;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProxyEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtracePackage.Literals.PROXY_EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getReferenceEdge() {
		if (referenceEdge != null && referenceEdge.eIsProxy()) {
			InternalEObject oldReferenceEdge = (InternalEObject)referenceEdge;
			referenceEdge = (Edge)eResolveProxy(oldReferenceEdge);
			if (referenceEdge != oldReferenceEdge) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamtracePackage.PROXY_EDGE__REFERENCE_EDGE, oldReferenceEdge, referenceEdge));
			}
		}
		return referenceEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetReferenceEdge() {
		return referenceEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferenceEdge(Edge newReferenceEdge) {
		Edge oldReferenceEdge = referenceEdge;
		referenceEdge = newReferenceEdge;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtracePackage.PROXY_EDGE__REFERENCE_EDGE, oldReferenceEdge, referenceEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SamtracePackage.PROXY_EDGE__REFERENCE_EDGE:
				if (resolve) return getReferenceEdge();
				return basicGetReferenceEdge();
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
			case SamtracePackage.PROXY_EDGE__REFERENCE_EDGE:
				setReferenceEdge((Edge)newValue);
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
			case SamtracePackage.PROXY_EDGE__REFERENCE_EDGE:
				setReferenceEdge((Edge)null);
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
			case SamtracePackage.PROXY_EDGE__REFERENCE_EDGE:
				return referenceEdge != null;
		}
		return super.eIsSet(featureID);
	}

} //ProxyEdgeImpl
