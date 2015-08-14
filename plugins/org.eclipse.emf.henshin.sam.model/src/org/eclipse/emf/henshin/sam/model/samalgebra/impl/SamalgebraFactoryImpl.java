/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.impl;

import java.util.Map.Entry;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.henshin.sam.model.samalgebra.*;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamalgebraFactoryImpl extends EFactoryImpl implements SamalgebraFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SamalgebraFactory init() {
		try {
			SamalgebraFactory theSamalgebraFactory = (SamalgebraFactory)EPackage.Registry.INSTANCE.getEFactory(SamalgebraPackage.eNS_URI);
			if (theSamalgebraFactory != null) {
				return theSamalgebraFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SamalgebraFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamalgebraFactoryImpl() {
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
			case SamalgebraPackage.ALGEBRA: return createAlgebra();
			case SamalgebraPackage.CONSTANT_DATA_ELEMENT_MAP: return (EObject)createConstantDataElementMap();
			case SamalgebraPackage.OPERATION: return createOperation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Algebra createAlgebra() {
		AlgebraImpl algebra = new AlgebraImpl();
		return algebra;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entry<Operation, DataElement> createConstantDataElementMap() {
		ConstantDataElementMapImpl constantDataElementMap = new ConstantDataElementMapImpl();
		return constantDataElementMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operation createOperation() {
		OperationImpl operation = new OperationImpl();
		return operation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamalgebraPackage getSamalgebraPackage() {
		return (SamalgebraPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SamalgebraPackage getPackage() {
		return SamalgebraPackage.eINSTANCE;
	}

} //SamalgebraFactoryImpl
