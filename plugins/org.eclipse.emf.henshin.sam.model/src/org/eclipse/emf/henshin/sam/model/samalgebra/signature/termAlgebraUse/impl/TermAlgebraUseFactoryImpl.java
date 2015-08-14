/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TermAlgebraUseFactoryImpl extends EFactoryImpl implements TermAlgebraUseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TermAlgebraUseFactory init() {
		try {
			TermAlgebraUseFactory theTermAlgebraUseFactory = (TermAlgebraUseFactory)EPackage.Registry.INSTANCE.getEFactory(TermAlgebraUsePackage.eNS_URI);
			if (theTermAlgebraUseFactory != null) {
				return theTermAlgebraUseFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TermAlgebraUseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermAlgebraUseFactoryImpl() {
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
			case TermAlgebraUsePackage.ATTRIBUTE_TYPE_USE: return createAttributeTypeUse();
			case TermAlgebraUsePackage.OPERATION_TERM_PARAMETER: return createOperationTermParameter();
			case TermAlgebraUsePackage.OPERATION_SYMBOL_USE: return createOperationSymbolUse();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeTypeUse createAttributeTypeUse() {
		AttributeTypeUseImpl attributeTypeUse = new AttributeTypeUseImpl();
		return attributeTypeUse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationTermParameter createOperationTermParameter() {
		OperationTermParameterImpl operationTermParameter = new OperationTermParameterImpl();
		return operationTermParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationSymbolUse createOperationSymbolUse() {
		OperationSymbolUseImpl operationSymbolUse = new OperationSymbolUseImpl();
		return operationSymbolUse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermAlgebraUsePackage getTermAlgebraUsePackage() {
		return (TermAlgebraUsePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TermAlgebraUsePackage getPackage() {
		return TermAlgebraUsePackage.eINSTANCE;
	}

} //TermAlgebraUseFactoryImpl
