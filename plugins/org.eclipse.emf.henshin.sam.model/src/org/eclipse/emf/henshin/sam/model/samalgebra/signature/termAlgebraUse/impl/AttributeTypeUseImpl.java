/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Type Use</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.AttributeTypeUseImpl#getTheAttributeType <em>The Attribute Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeTypeUseImpl extends OperationTermParameterImpl implements AttributeTypeUse {
	/**
	 * The cached value of the '{@link #getTheAttributeType() <em>The Attribute Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTheAttributeType()
	 * @generated
	 * @ordered
	 */
	protected AttributeType theAttributeType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeTypeUseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TermAlgebraUsePackage.Literals.ATTRIBUTE_TYPE_USE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType getTheAttributeType() {
		if (theAttributeType != null && theAttributeType.eIsProxy()) {
			InternalEObject oldTheAttributeType = (InternalEObject)theAttributeType;
			theAttributeType = (AttributeType)eResolveProxy(oldTheAttributeType);
			if (theAttributeType != oldTheAttributeType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE, oldTheAttributeType, theAttributeType));
			}
		}
		return theAttributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType basicGetTheAttributeType() {
		return theAttributeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTheAttributeType(AttributeType newTheAttributeType) {
		AttributeType oldTheAttributeType = theAttributeType;
		theAttributeType = newTheAttributeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE, oldTheAttributeType, theAttributeType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE:
				if (resolve) return getTheAttributeType();
				return basicGetTheAttributeType();
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
			case TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE:
				setTheAttributeType((AttributeType)newValue);
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
			case TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE:
				setTheAttributeType((AttributeType)null);
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
			case TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE:
				return theAttributeType != null;
		}
		return super.eIsSet(featureID);
	}

} //AttributeTypeUseImpl
