/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SignatureFactoryImpl extends EFactoryImpl implements SignatureFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SignatureFactory init() {
		try {
			SignatureFactory theSignatureFactory = (SignatureFactory)EPackage.Registry.INSTANCE.getEFactory(SignaturePackage.eNS_URI);
			if (theSignatureFactory != null) {
				return theSignatureFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SignatureFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SignatureFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SignaturePackage.SORT: return createSort();
			case SignaturePackage.OPERATION_SYMBOL: return createOperationSymbol();
			case SignaturePackage.OPERAND_SYMBOL: return createOperandSymbol();
			case SignaturePackage.ALGEBRA_SIGNATURE: return createAlgebraSignature();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sort createSort() {
		SortImpl sort = new SortImpl();
		return sort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSymbol createOperationSymbol() {
		OperationSymbolImpl operationSymbol = new OperationSymbolImpl();
		return operationSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperandSymbol createOperandSymbol() {
		OperandSymbolImpl operandSymbol = new OperandSymbolImpl();
		return operandSymbol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlgebraSignature createAlgebraSignature() {
		AlgebraSignatureImpl algebraSignature = new AlgebraSignatureImpl();
		return algebraSignature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SignaturePackage getSignaturePackage() {
		return (SignaturePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SignaturePackage getPackage() {
		return SignaturePackage.eINSTANCE;
	}

} //SignatureFactoryImpl
