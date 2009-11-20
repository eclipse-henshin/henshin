/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.henshin.statespace.AttributeHolder;

/**
 * @generated
 */
public class AttributeHolderImpl extends MinimalEObjectImpl implements AttributeHolder {
	
	/**
	 * @generated NOT
	 */
	public static int[] EMPTY = new int[0];
	
	/**
	 * @generated NOT
	 */
	public int[] getAttributes() {
		return attributes!=null ? attributes : EMPTY;
	}
	
	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The default value of the '{@link #getAttributes() <em>Attributes</em>}' attribute.
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final int[] ATTRIBUTES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' attribute.
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected int[] attributes = ATTRIBUTES_EDEFAULT;

	/**
	 * @generated
	 */
	protected AttributeHolderImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackageImpl.Literals.ATTRIBUTE_HOLDER;
	}

	/**
	 * @generated
	 */
	public void setAttributes(int[] newAttributes) {
		int[] oldAttributes = attributes;
		attributes = newAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.ATTRIBUTE_HOLDER__ATTRIBUTES, oldAttributes, attributes));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackageImpl.ATTRIBUTE_HOLDER__ATTRIBUTES:
				return getAttributes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackageImpl.ATTRIBUTE_HOLDER__ATTRIBUTES:
				setAttributes((int[])newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackageImpl.ATTRIBUTE_HOLDER__ATTRIBUTES:
				setAttributes(ATTRIBUTES_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackageImpl.ATTRIBUTE_HOLDER__ATTRIBUTES:
				return ATTRIBUTES_EDEFAULT == null ? attributes != null : !ATTRIBUTES_EDEFAULT.equals(attributes);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (attributes: ");
		result.append(attributes);
		result.append(')');
		return result.toString();
	}

} //AttributeHolderImpl
