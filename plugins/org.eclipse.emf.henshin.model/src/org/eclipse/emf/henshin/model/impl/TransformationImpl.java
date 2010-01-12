/**
 * <copyright>
 * </copyright>
 *
 * $Id: TransformationImpl.java,v 1.1 2009/10/28 10:38:15 enrico Exp $
 */
package org.eclipse.emf.henshin.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Transformation;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationImpl#getMainUnit <em>Main Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.TransformationImpl#getTransformationSystem <em>Transformation System</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransformationImpl extends DescribedElementImpl implements Transformation {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMainUnit() <em>Main Unit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMainUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit mainUnit;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getMainUnit() {
		return mainUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMainUnit(TransformationUnit newMainUnit, NotificationChain msgs) {
		TransformationUnit oldMainUnit = mainUnit;
		mainUnit = newMainUnit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION__MAIN_UNIT, oldMainUnit, newMainUnit);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMainUnit(TransformationUnit newMainUnit) {
		if (newMainUnit != mainUnit) {
			NotificationChain msgs = null;
			if (mainUnit != null)
				msgs = ((InternalEObject)mainUnit).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.TRANSFORMATION__MAIN_UNIT, null, msgs);
			if (newMainUnit != null)
				msgs = ((InternalEObject)newMainUnit).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.TRANSFORMATION__MAIN_UNIT, null, msgs);
			msgs = basicSetMainUnit(newMainUnit, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION__MAIN_UNIT, newMainUnit, newMainUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem getTransformationSystem() {
		if (eContainerFeatureID() != HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM) return null;
		return (TransformationSystem)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransformationSystem(TransformationSystem newTransformationSystem, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTransformationSystem, HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformationSystem(TransformationSystem newTransformationSystem) {
		if (newTransformationSystem != eInternalContainer() || (eContainerFeatureID() != HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM && newTransformationSystem != null)) {
			if (EcoreUtil.isAncestor(this, newTransformationSystem))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTransformationSystem != null)
				msgs = ((InternalEObject)newTransformationSystem).eInverseAdd(this, HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATIONS, TransformationSystem.class, msgs);
			msgs = basicSetTransformationSystem(newTransformationSystem, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM, newTransformationSystem, newTransformationSystem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTransformationSystem((TransformationSystem)otherEnd, msgs);
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
			case HenshinPackage.TRANSFORMATION__MAIN_UNIT:
				return basicSetMainUnit(null, msgs);
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				return basicSetTransformationSystem(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATIONS, TransformationSystem.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.TRANSFORMATION__NAME:
				return getName();
			case HenshinPackage.TRANSFORMATION__MAIN_UNIT:
				return getMainUnit();
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				return getTransformationSystem();
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
			case HenshinPackage.TRANSFORMATION__NAME:
				setName((String)newValue);
				return;
			case HenshinPackage.TRANSFORMATION__MAIN_UNIT:
				setMainUnit((TransformationUnit)newValue);
				return;
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				setTransformationSystem((TransformationSystem)newValue);
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
			case HenshinPackage.TRANSFORMATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case HenshinPackage.TRANSFORMATION__MAIN_UNIT:
				setMainUnit((TransformationUnit)null);
				return;
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				setTransformationSystem((TransformationSystem)null);
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
			case HenshinPackage.TRANSFORMATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case HenshinPackage.TRANSFORMATION__MAIN_UNIT:
				return mainUnit != null;
			case HenshinPackage.TRANSFORMATION__TRANSFORMATION_SYSTEM:
				return getTransformationSystem() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case HenshinPackage.TRANSFORMATION__NAME: return HenshinPackage.NAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case HenshinPackage.NAMED_ELEMENT__NAME: return HenshinPackage.TRANSFORMATION__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TransformationImpl
