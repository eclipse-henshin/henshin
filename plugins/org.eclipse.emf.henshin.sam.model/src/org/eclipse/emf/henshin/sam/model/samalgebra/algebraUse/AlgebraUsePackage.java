/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse;

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
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUseFactory
 * @model kind="package"
 * @generated
 */
public interface AlgebraUsePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "algebraUse";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samalgebra/use";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samalgebraUse";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AlgebraUsePackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationParameterImpl <em>Operation Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationParameterImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getOperationParameter()
	 * @generated
	 */
	int OPERATION_PARAMETER = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PARAMETER__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PARAMETER__REFERENCES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Operation Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_PARAMETER_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.DataElementImpl <em>Data Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.DataElementImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getDataElement()
	 * @generated
	 */
	int DATA_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ELEMENT__ANNOTATIONS = OPERATION_PARAMETER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ELEMENT__REFERENCES = OPERATION_PARAMETER__REFERENCES;

	/**
	 * The feature id for the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ELEMENT__SORT = OPERATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ELEMENT__VALUE = OPERATION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Data Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_ELEMENT_FEATURE_COUNT = OPERATION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AttributeUseImpl <em>Attribute Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AttributeUseImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getAttributeUse()
	 * @generated
	 */
	int ATTRIBUTE_USE = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_USE__ANNOTATIONS = OPERATION_PARAMETER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_USE__REFERENCES = OPERATION_PARAMETER__REFERENCES;

	/**
	 * The feature id for the '<em><b>The Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_USE__THE_ATTRIBUTE = OPERATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_USE_FEATURE_COUNT = OPERATION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationUseImpl <em>Operation Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationUseImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getOperationUse()
	 * @generated
	 */
	int OPERATION_USE = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_USE__ANNOTATIONS = OPERATION_PARAMETER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_USE__REFERENCES = OPERATION_PARAMETER__REFERENCES;

	/**
	 * The feature id for the '<em><b>The Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_USE__THE_OPERATION = OPERATION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operands</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_USE__OPERANDS = OPERATION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Operation Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_USE_FEATURE_COUNT = OPERATION_PARAMETER_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter <em>Operation Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Parameter</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter
	 * @generated
	 */
	EClass getOperationParameter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>References</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter#getReferences()
	 * @see #getOperationParameter()
	 * @generated
	 */
	EReference getOperationParameter_References();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement <em>Data Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Element</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement
	 * @generated
	 */
	EClass getDataElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement#getSort <em>Sort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sort</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement#getSort()
	 * @see #getDataElement()
	 * @generated
	 */
	EReference getDataElement_Sort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement#getValue()
	 * @see #getDataElement()
	 * @generated
	 */
	EAttribute getDataElement_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse <em>Attribute Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Use</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse
	 * @generated
	 */
	EClass getAttributeUse();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse#getTheAttribute <em>The Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>The Attribute</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse#getTheAttribute()
	 * @see #getAttributeUse()
	 * @generated
	 */
	EReference getAttributeUse_TheAttribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse <em>Operation Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Use</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse
	 * @generated
	 */
	EClass getOperationUse();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getTheOperation <em>The Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>The Operation</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getTheOperation()
	 * @see #getOperationUse()
	 * @generated
	 */
	EReference getOperationUse_TheOperation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getOperands <em>Operands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operands</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getOperands()
	 * @see #getOperationUse()
	 * @generated
	 */
	EReference getOperationUse_Operands();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AlgebraUseFactory getAlgebraUseFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationParameterImpl <em>Operation Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationParameterImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getOperationParameter()
		 * @generated
		 */
		EClass OPERATION_PARAMETER = eINSTANCE.getOperationParameter();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_PARAMETER__REFERENCES = eINSTANCE.getOperationParameter_References();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.DataElementImpl <em>Data Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.DataElementImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getDataElement()
		 * @generated
		 */
		EClass DATA_ELEMENT = eINSTANCE.getDataElement();

		/**
		 * The meta object literal for the '<em><b>Sort</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_ELEMENT__SORT = eINSTANCE.getDataElement_Sort();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_ELEMENT__VALUE = eINSTANCE.getDataElement_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AttributeUseImpl <em>Attribute Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AttributeUseImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getAttributeUse()
		 * @generated
		 */
		EClass ATTRIBUTE_USE = eINSTANCE.getAttributeUse();

		/**
		 * The meta object literal for the '<em><b>The Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_USE__THE_ATTRIBUTE = eINSTANCE.getAttributeUse_TheAttribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationUseImpl <em>Operation Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.OperationUseImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl#getOperationUse()
		 * @generated
		 */
		EClass OPERATION_USE = eINSTANCE.getOperationUse();

		/**
		 * The meta object literal for the '<em><b>The Operation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_USE__THE_OPERATION = eINSTANCE.getOperationUse_TheOperation();

		/**
		 * The meta object literal for the '<em><b>Operands</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_USE__OPERANDS = eINSTANCE.getOperationUse_Operands();

	}

} //AlgebraUsePackage
