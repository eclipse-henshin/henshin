/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraFactory
 * @model kind="package"
 * @generated
 */
public interface SamalgebraPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "samalgebra";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samalgebra";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samalgebra";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamalgebraPackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl <em>Algebra</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl#getAlgebra()
	 * @generated
	 */
	int ALGEBRA = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA__CONSTANTS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA__OPERATIONS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA__SIGNATURE = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Algebra</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.ConstantDataElementMapImpl <em>Constant Data Element Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.ConstantDataElementMapImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl#getConstantDataElementMap()
	 * @generated
	 */
	int CONSTANT_DATA_ELEMENT_MAP = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_DATA_ELEMENT_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_DATA_ELEMENT_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Constant Data Element Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_DATA_ELEMENT_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.OperationImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl#getOperation()
	 * @generated
	 */
	int OPERATION = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__SIGNATURE = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra <em>Algebra</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Algebra</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Algebra
	 * @generated
	 */
	EClass getAlgebra();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Constants</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getConstants()
	 * @see #getAlgebra()
	 * @generated
	 */
	EReference getAlgebra_Constants();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getOperations()
	 * @see #getAlgebra()
	 * @generated
	 */
	EReference getAlgebra_Operations();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signature</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getSignature()
	 * @see #getAlgebra()
	 * @generated
	 */
	EReference getAlgebra_Signature();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Constant Data Element Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant Data Element Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.henshin.sam.model.samalgebra.Operation"
	 *        valueType="org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement"
	 * @generated
	 */
	EClass getConstantDataElementMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getConstantDataElementMap()
	 * @generated
	 */
	EReference getConstantDataElementMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getConstantDataElementMap()
	 * @generated
	 */
	EReference getConstantDataElementMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Operation
	 * @generated
	 */
	EClass getOperation();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Operation#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Signature</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Operation#getSignature()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_Signature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Operation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.Operation#getName()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SamalgebraFactory getSamalgebraFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl <em>Algebra</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl#getAlgebra()
		 * @generated
		 */
		EClass ALGEBRA = eINSTANCE.getAlgebra();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGEBRA__CONSTANTS = eINSTANCE.getAlgebra_Constants();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGEBRA__OPERATIONS = eINSTANCE.getAlgebra_Operations();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGEBRA__SIGNATURE = eINSTANCE.getAlgebra_Signature();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.ConstantDataElementMapImpl <em>Constant Data Element Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.ConstantDataElementMapImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl#getConstantDataElementMap()
		 * @generated
		 */
		EClass CONSTANT_DATA_ELEMENT_MAP = eINSTANCE.getConstantDataElementMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTANT_DATA_ELEMENT_MAP__KEY = eINSTANCE.getConstantDataElementMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTANT_DATA_ELEMENT_MAP__VALUE = eINSTANCE.getConstantDataElementMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.OperationImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl#getOperation()
		 * @generated
		 */
		EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__SIGNATURE = eINSTANCE.getOperation_Signature();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__NAME = eINSTANCE.getOperation_Name();

	}

} //SamalgebraPackage
