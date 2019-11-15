/**
 */
package org.eclipse.emf.henshin.examples.apibasics.boxing.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.henshin.examples.apibasics.boxing.Box;
import org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage;
import org.eclipse.emf.henshin.examples.apibasics.boxing.Item;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.ItemImpl#getIsStoredBy <em>Is Stored By</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ItemImpl extends MinimalEObjectImpl.Container implements Item {
	/**
	 * The cached value of the '{@link #getIsStoredBy() <em>Is Stored By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIsStoredBy()
	 * @generated
	 * @ordered
	 */
	protected Box isStoredBy;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BoxingPackage.Literals.ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Box getIsStoredBy() {
		if (isStoredBy != null && isStoredBy.eIsProxy()) {
			InternalEObject oldIsStoredBy = (InternalEObject)isStoredBy;
			isStoredBy = (Box)eResolveProxy(oldIsStoredBy);
			if (isStoredBy != oldIsStoredBy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BoxingPackage.ITEM__IS_STORED_BY, oldIsStoredBy, isStoredBy));
			}
		}
		return isStoredBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Box basicGetIsStoredBy() {
		return isStoredBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIsStoredBy(Box newIsStoredBy, NotificationChain msgs) {
		Box oldIsStoredBy = isStoredBy;
		isStoredBy = newIsStoredBy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BoxingPackage.ITEM__IS_STORED_BY, oldIsStoredBy, newIsStoredBy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsStoredBy(Box newIsStoredBy) {
		if (newIsStoredBy != isStoredBy) {
			NotificationChain msgs = null;
			if (isStoredBy != null)
				msgs = ((InternalEObject)isStoredBy).eInverseRemove(this, BoxingPackage.BOX__STORES, Box.class, msgs);
			if (newIsStoredBy != null)
				msgs = ((InternalEObject)newIsStoredBy).eInverseAdd(this, BoxingPackage.BOX__STORES, Box.class, msgs);
			msgs = basicSetIsStoredBy(newIsStoredBy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BoxingPackage.ITEM__IS_STORED_BY, newIsStoredBy, newIsStoredBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BoxingPackage.ITEM__IS_STORED_BY:
				if (isStoredBy != null)
					msgs = ((InternalEObject)isStoredBy).eInverseRemove(this, BoxingPackage.BOX__STORES, Box.class, msgs);
				return basicSetIsStoredBy((Box)otherEnd, msgs);
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
			case BoxingPackage.ITEM__IS_STORED_BY:
				return basicSetIsStoredBy(null, msgs);
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
			case BoxingPackage.ITEM__IS_STORED_BY:
				if (resolve) return getIsStoredBy();
				return basicGetIsStoredBy();
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
			case BoxingPackage.ITEM__IS_STORED_BY:
				setIsStoredBy((Box)newValue);
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
			case BoxingPackage.ITEM__IS_STORED_BY:
				setIsStoredBy((Box)null);
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
			case BoxingPackage.ITEM__IS_STORED_BY:
				return isStoredBy != null;
		}
		return super.eIsSet(featureID);
	}

} //ItemImpl
