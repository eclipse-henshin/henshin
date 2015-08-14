/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.Operation;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Use</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationUseImpl#getTheOperation <em>The Operation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationUseImpl#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationUseImpl extends OperationParameterImpl implements OperationUse {
	/**
	 * The cached value of the '{@link #getTheOperation() <em>The Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTheOperation()
	 * @generated
	 * @ordered
	 */
	protected Operation theOperation;

	/**
	 * The cached value of the '{@link #getOperands() <em>Operands</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperands()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationParameter> operands;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationUseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlgebraUsePackage.Literals.OPERATION_USE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation getTheOperation() {
		if (theOperation != null && theOperation.eIsProxy()) {
			InternalEObject oldTheOperation = (InternalEObject)theOperation;
			theOperation = (Operation)eResolveProxy(oldTheOperation);
			if (theOperation != oldTheOperation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AlgebraUsePackage.OPERATION_USE__THE_OPERATION, oldTheOperation, theOperation));
			}
		}
		return theOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation basicGetTheOperation() {
		return theOperation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTheOperation(Operation newTheOperation) {
		Operation oldTheOperation = theOperation;
		theOperation = newTheOperation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlgebraUsePackage.OPERATION_USE__THE_OPERATION, oldTheOperation, theOperation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperationParameter> getOperands() {
		if (operands == null) {
			operands = new EObjectContainmentEList<OperationParameter>(OperationParameter.class, this, AlgebraUsePackage.OPERATION_USE__OPERANDS);
		}
		return operands;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlgebraUsePackage.OPERATION_USE__OPERANDS:
				return ((InternalEList<?>)getOperands()).basicRemove(otherEnd, msgs);
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
			case AlgebraUsePackage.OPERATION_USE__THE_OPERATION:
				if (resolve) return getTheOperation();
				return basicGetTheOperation();
			case AlgebraUsePackage.OPERATION_USE__OPERANDS:
				return getOperands();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AlgebraUsePackage.OPERATION_USE__THE_OPERATION:
				setTheOperation((Operation)newValue);
				return;
			case AlgebraUsePackage.OPERATION_USE__OPERANDS:
				getOperands().clear();
				getOperands().addAll((Collection<? extends OperationParameter>)newValue);
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
			case AlgebraUsePackage.OPERATION_USE__THE_OPERATION:
				setTheOperation((Operation)null);
				return;
			case AlgebraUsePackage.OPERATION_USE__OPERANDS:
				getOperands().clear();
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
			case AlgebraUsePackage.OPERATION_USE__THE_OPERATION:
				return theOperation != null;
			case AlgebraUsePackage.OPERATION_USE__OPERANDS:
				return operands != null && !operands.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OperationUseImpl
