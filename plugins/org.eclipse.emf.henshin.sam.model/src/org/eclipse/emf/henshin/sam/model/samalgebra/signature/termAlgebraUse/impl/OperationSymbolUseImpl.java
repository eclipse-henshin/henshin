/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Symbol Use</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationSymbolUseImpl#getTheOperationSymbol <em>The Operation Symbol</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationSymbolUseImpl#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationSymbolUseImpl extends OperationTermParameterImpl implements OperationSymbolUse {
	/**
	 * The cached value of the '{@link #getTheOperationSymbol() <em>The Operation Symbol</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTheOperationSymbol()
	 * @generated
	 * @ordered
	 */
	protected OperationSymbol theOperationSymbol;

	/**
	 * The cached value of the '{@link #getOperands() <em>Operands</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperands()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationTermParameter> operands;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationSymbolUseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TermAlgebraUsePackage.Literals.OPERATION_SYMBOL_USE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSymbol getTheOperationSymbol() {
		if (theOperationSymbol != null && theOperationSymbol.eIsProxy()) {
			InternalEObject oldTheOperationSymbol = (InternalEObject)theOperationSymbol;
			theOperationSymbol = (OperationSymbol)eResolveProxy(oldTheOperationSymbol);
			if (theOperationSymbol != oldTheOperationSymbol) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TermAlgebraUsePackage.OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL, oldTheOperationSymbol, theOperationSymbol));
			}
		}
		return theOperationSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSymbol basicGetTheOperationSymbol() {
		return theOperationSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTheOperationSymbol(OperationSymbol newTheOperationSymbol) {
		OperationSymbol oldTheOperationSymbol = theOperationSymbol;
		theOperationSymbol = newTheOperationSymbol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TermAlgebraUsePackage.OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL, oldTheOperationSymbol, theOperationSymbol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperationTermParameter> getOperands() {
		if (operands == null) {
			operands = new EObjectResolvingEList<OperationTermParameter>(OperationTermParameter.class, this, TermAlgebraUsePackage.OPERATION_SYMBOL_USE__OPERANDS);
		}
		return operands;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL:
				if (resolve) return getTheOperationSymbol();
				return basicGetTheOperationSymbol();
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__OPERANDS:
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
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL:
				setTheOperationSymbol((OperationSymbol)newValue);
				return;
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__OPERANDS:
				getOperands().clear();
				getOperands().addAll((Collection<? extends OperationTermParameter>)newValue);
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
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL:
				setTheOperationSymbol((OperationSymbol)null);
				return;
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__OPERANDS:
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
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL:
				return theOperationSymbol != null;
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE__OPERANDS:
				return operands != null && !operands.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OperationSymbolUseImpl
