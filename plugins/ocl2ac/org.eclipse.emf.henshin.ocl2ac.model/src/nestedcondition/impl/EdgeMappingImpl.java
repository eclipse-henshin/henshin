/**
 */
package nestedcondition.impl;

import graph.Edge;

import nestedcondition.EdgeMapping;
import nestedcondition.NestedconditionPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.impl.EdgeMappingImpl#getOrigin <em>Origin</em>}</li>
 *   <li>{@link nestedcondition.impl.EdgeMappingImpl#getImage <em>Image</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeMappingImpl extends MinimalEObjectImpl.Container implements EdgeMapping {
	/**
	 * The cached value of the '{@link #getOrigin() <em>Origin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrigin()
	 * @generated
	 * @ordered
	 */
	protected Edge origin;

	/**
	 * The cached value of the '{@link #getImage() <em>Image</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImage()
	 * @generated
	 * @ordered
	 */
	protected Edge image;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NestedconditionPackage.Literals.EDGE_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getOrigin() {
		if (origin != null && origin.eIsProxy()) {
			InternalEObject oldOrigin = (InternalEObject)origin;
			origin = (Edge)eResolveProxy(oldOrigin);
			if (origin != oldOrigin) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NestedconditionPackage.EDGE_MAPPING__ORIGIN, oldOrigin, origin));
			}
		}
		return origin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetOrigin() {
		return origin;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrigin(Edge newOrigin) {
		Edge oldOrigin = origin;
		origin = newOrigin;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.EDGE_MAPPING__ORIGIN, oldOrigin, origin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getImage() {
		if (image != null && image.eIsProxy()) {
			InternalEObject oldImage = (InternalEObject)image;
			image = (Edge)eResolveProxy(oldImage);
			if (image != oldImage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NestedconditionPackage.EDGE_MAPPING__IMAGE, oldImage, image));
			}
		}
		return image;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetImage() {
		return image;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(Edge newImage) {
		Edge oldImage = image;
		image = newImage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.EDGE_MAPPING__IMAGE, oldImage, image));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NestedconditionPackage.EDGE_MAPPING__ORIGIN:
				if (resolve) return getOrigin();
				return basicGetOrigin();
			case NestedconditionPackage.EDGE_MAPPING__IMAGE:
				if (resolve) return getImage();
				return basicGetImage();
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
			case NestedconditionPackage.EDGE_MAPPING__ORIGIN:
				setOrigin((Edge)newValue);
				return;
			case NestedconditionPackage.EDGE_MAPPING__IMAGE:
				setImage((Edge)newValue);
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
			case NestedconditionPackage.EDGE_MAPPING__ORIGIN:
				setOrigin((Edge)null);
				return;
			case NestedconditionPackage.EDGE_MAPPING__IMAGE:
				setImage((Edge)null);
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
			case NestedconditionPackage.EDGE_MAPPING__ORIGIN:
				return origin != null;
			case NestedconditionPackage.EDGE_MAPPING__IMAGE:
				return image != null;
		}
		return super.eIsSet(featureID);
	}

} //EdgeMappingImpl
