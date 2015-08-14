/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation Symbol</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl#getOperandSymbols <em>Operand Symbols</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl#getResultSymbol <em>Result Symbol</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationSymbolImpl extends EObjectImpl implements OperationSymbol {
	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The cached value of the '{@link #getOperandSymbols() <em>Operand Symbols</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperandSymbols()
	 * @generated
	 * @ordered
	 */
	protected EList<OperandSymbol> operandSymbols;

	/**
	 * The cached value of the '{@link #getResultSymbol() <em>Result Symbol</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResultSymbol()
	 * @generated
	 * @ordered
	 */
	protected Sort resultSymbol;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationSymbolImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SignaturePackage.Literals.OPERATION_SYMBOL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SignaturePackage.OPERATION_SYMBOL__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperandSymbol> getOperandSymbols() {
		if (operandSymbols == null) {
			operandSymbols = new EObjectContainmentEList<OperandSymbol>(OperandSymbol.class, this, SignaturePackage.OPERATION_SYMBOL__OPERAND_SYMBOLS);
		}
		return operandSymbols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sort getResultSymbol() {
		if (resultSymbol != null && resultSymbol.eIsProxy()) {
			InternalEObject oldResultSymbol = (InternalEObject)resultSymbol;
			resultSymbol = (Sort)eResolveProxy(oldResultSymbol);
			if (resultSymbol != oldResultSymbol) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SignaturePackage.OPERATION_SYMBOL__RESULT_SYMBOL, oldResultSymbol, resultSymbol));
			}
		}
		return resultSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sort basicGetResultSymbol() {
		return resultSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResultSymbol(Sort newResultSymbol) {
		Sort oldResultSymbol = resultSymbol;
		resultSymbol = newResultSymbol;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SignaturePackage.OPERATION_SYMBOL__RESULT_SYMBOL, oldResultSymbol, resultSymbol));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SignaturePackage.OPERATION_SYMBOL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SignaturePackage.OPERATION_SYMBOL__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SignaturePackage.OPERATION_SYMBOL__OPERAND_SYMBOLS:
				return ((InternalEList<?>)getOperandSymbols()).basicRemove(otherEnd, msgs);
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
			case SignaturePackage.OPERATION_SYMBOL__ANNOTATIONS:
				return getAnnotations();
			case SignaturePackage.OPERATION_SYMBOL__OPERAND_SYMBOLS:
				return getOperandSymbols();
			case SignaturePackage.OPERATION_SYMBOL__RESULT_SYMBOL:
				if (resolve) return getResultSymbol();
				return basicGetResultSymbol();
			case SignaturePackage.OPERATION_SYMBOL__NAME:
				return getName();
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
			case SignaturePackage.OPERATION_SYMBOL__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SignaturePackage.OPERATION_SYMBOL__OPERAND_SYMBOLS:
				getOperandSymbols().clear();
				getOperandSymbols().addAll((Collection<? extends OperandSymbol>)newValue);
				return;
			case SignaturePackage.OPERATION_SYMBOL__RESULT_SYMBOL:
				setResultSymbol((Sort)newValue);
				return;
			case SignaturePackage.OPERATION_SYMBOL__NAME:
				setName((String)newValue);
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
			case SignaturePackage.OPERATION_SYMBOL__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SignaturePackage.OPERATION_SYMBOL__OPERAND_SYMBOLS:
				getOperandSymbols().clear();
				return;
			case SignaturePackage.OPERATION_SYMBOL__RESULT_SYMBOL:
				setResultSymbol((Sort)null);
				return;
			case SignaturePackage.OPERATION_SYMBOL__NAME:
				setName(NAME_EDEFAULT);
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
			case SignaturePackage.OPERATION_SYMBOL__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SignaturePackage.OPERATION_SYMBOL__OPERAND_SYMBOLS:
				return operandSymbols != null && !operandSymbols.isEmpty();
			case SignaturePackage.OPERATION_SYMBOL__RESULT_SYMBOL:
				return resultSymbol != null;
			case SignaturePackage.OPERATION_SYMBOL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
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

} //OperationSymbolImpl
