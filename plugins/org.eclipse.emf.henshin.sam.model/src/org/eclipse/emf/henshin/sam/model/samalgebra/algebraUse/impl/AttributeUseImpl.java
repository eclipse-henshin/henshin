/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse;
import org.eclipse.emf.henshin.sam.model.samgraph.Attribute;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Use</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AttributeUseImpl#getTheAttribute <em>The Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeUseImpl extends OperationParameterImpl implements AttributeUse {
	/**
	 * The cached value of the '{@link #getTheAttribute() <em>The Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTheAttribute()
	 * @generated
	 * @ordered
	 */
	protected Attribute theAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeUseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlgebraUsePackage.Literals.ATTRIBUTE_USE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute getTheAttribute() {
		if (theAttribute != null && theAttribute.eIsProxy()) {
			InternalEObject oldTheAttribute = (InternalEObject)theAttribute;
			theAttribute = (Attribute)eResolveProxy(oldTheAttribute);
			if (theAttribute != oldTheAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AlgebraUsePackage.ATTRIBUTE_USE__THE_ATTRIBUTE, oldTheAttribute, theAttribute));
			}
		}
		return theAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute basicGetTheAttribute() {
		return theAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTheAttribute(Attribute newTheAttribute) {
		Attribute oldTheAttribute = theAttribute;
		theAttribute = newTheAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlgebraUsePackage.ATTRIBUTE_USE__THE_ATTRIBUTE, oldTheAttribute, theAttribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AlgebraUsePackage.ATTRIBUTE_USE__THE_ATTRIBUTE:
				if (resolve) return getTheAttribute();
				return basicGetTheAttribute();
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
			case AlgebraUsePackage.ATTRIBUTE_USE__THE_ATTRIBUTE:
				setTheAttribute((Attribute)newValue);
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
			case AlgebraUsePackage.ATTRIBUTE_USE__THE_ATTRIBUTE:
				setTheAttribute((Attribute)null);
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
			case AlgebraUsePackage.ATTRIBUTE_USE__THE_ATTRIBUTE:
				return theAttribute != null;
		}
		return super.eIsSet(featureID);
	}

} //AttributeUseImpl
