/**
 */
package laxcondition.impl;

import laxcondition.Formula;
import laxcondition.LaxCondition;
import laxcondition.LaxconditionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lax Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link laxcondition.impl.LaxConditionImpl#getFormula <em>Formula</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class LaxConditionImpl extends MinimalEObjectImpl.Container implements LaxCondition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LaxConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LaxconditionPackage.Literals.LAX_CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Formula getFormula() {
		if (eContainerFeatureID() != LaxconditionPackage.LAX_CONDITION__FORMULA) return null;
		return (Formula)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFormula(Formula newFormula, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFormula, LaxconditionPackage.LAX_CONDITION__FORMULA, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormula(Formula newFormula) {
		if (newFormula != eInternalContainer() || (eContainerFeatureID() != LaxconditionPackage.LAX_CONDITION__FORMULA && newFormula != null)) {
			if (EcoreUtil.isAncestor(this, newFormula))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFormula != null)
				msgs = ((InternalEObject)newFormula).eInverseAdd(this, LaxconditionPackage.FORMULA__ARGUMENTS, Formula.class, msgs);
			msgs = basicSetFormula(newFormula, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LaxconditionPackage.LAX_CONDITION__FORMULA, newFormula, newFormula));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFormula((Formula)otherEnd, msgs);
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
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				return basicSetFormula(null, msgs);
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
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				return eInternalContainer().eInverseRemove(this, LaxconditionPackage.FORMULA__ARGUMENTS, Formula.class, msgs);
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
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				return getFormula();
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
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				setFormula((Formula)newValue);
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
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				setFormula((Formula)null);
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
			case LaxconditionPackage.LAX_CONDITION__FORMULA:
				return getFormula() != null;
		}
		return super.eIsSet(featureID);
	}

} //LaxConditionImpl
