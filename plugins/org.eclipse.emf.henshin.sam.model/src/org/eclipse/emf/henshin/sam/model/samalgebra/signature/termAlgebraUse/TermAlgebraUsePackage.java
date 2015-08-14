/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse;

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
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUseFactory
 * @model kind="package"
 * @generated
 */
public interface TermAlgebraUsePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "termAlgebraUse";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samalgebra/signature/term";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "samtermAlgebraUse";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TermAlgebraUsePackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationTermParameterImpl <em>Operation Term Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationTermParameterImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl#getOperationTermParameter()
	 * @generated
	 */
	int OPERATION_TERM_PARAMETER = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_TERM_PARAMETER__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_TERM_PARAMETER__REFERENCES = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Operation Term Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_TERM_PARAMETER_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.AttributeTypeUseImpl <em>Attribute Type Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.AttributeTypeUseImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl#getAttributeTypeUse()
	 * @generated
	 */
	int ATTRIBUTE_TYPE_USE = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_USE__ANNOTATIONS = OPERATION_TERM_PARAMETER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_USE__REFERENCES = OPERATION_TERM_PARAMETER__REFERENCES;

	/**
	 * The feature id for the '<em><b>The Attribute Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE = OPERATION_TERM_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute Type Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_USE_FEATURE_COUNT = OPERATION_TERM_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationSymbolUseImpl <em>Operation Symbol Use</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationSymbolUseImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl#getOperationSymbolUse()
	 * @generated
	 */
	int OPERATION_SYMBOL_USE = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL_USE__ANNOTATIONS = OPERATION_TERM_PARAMETER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL_USE__REFERENCES = OPERATION_TERM_PARAMETER__REFERENCES;

	/**
	 * The feature id for the '<em><b>The Operation Symbol</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL = OPERATION_TERM_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operands</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL_USE__OPERANDS = OPERATION_TERM_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Operation Symbol Use</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL_USE_FEATURE_COUNT = OPERATION_TERM_PARAMETER_FEATURE_COUNT + 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse <em>Attribute Type Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Type Use</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse
	 * @generated
	 */
	EClass getAttributeTypeUse();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse#getTheAttributeType <em>The Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>The Attribute Type</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse#getTheAttributeType()
	 * @see #getAttributeTypeUse()
	 * @generated
	 */
	EReference getAttributeTypeUse_TheAttributeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter <em>Operation Term Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Term Parameter</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter
	 * @generated
	 */
	EClass getOperationTermParameter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>References</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter#getReferences()
	 * @see #getOperationTermParameter()
	 * @generated
	 */
	EReference getOperationTermParameter_References();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse <em>Operation Symbol Use</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Symbol Use</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse
	 * @generated
	 */
	EClass getOperationSymbolUse();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getTheOperationSymbol <em>The Operation Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>The Operation Symbol</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getTheOperationSymbol()
	 * @see #getOperationSymbolUse()
	 * @generated
	 */
	EReference getOperationSymbolUse_TheOperationSymbol();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getOperands <em>Operands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Operands</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getOperands()
	 * @see #getOperationSymbolUse()
	 * @generated
	 */
	EReference getOperationSymbolUse_Operands();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TermAlgebraUseFactory getTermAlgebraUseFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.AttributeTypeUseImpl <em>Attribute Type Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.AttributeTypeUseImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl#getAttributeTypeUse()
		 * @generated
		 */
		EClass ATTRIBUTE_TYPE_USE = eINSTANCE.getAttributeTypeUse();

		/**
		 * The meta object literal for the '<em><b>The Attribute Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_TYPE_USE__THE_ATTRIBUTE_TYPE = eINSTANCE.getAttributeTypeUse_TheAttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationTermParameterImpl <em>Operation Term Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationTermParameterImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl#getOperationTermParameter()
		 * @generated
		 */
		EClass OPERATION_TERM_PARAMETER = eINSTANCE.getOperationTermParameter();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_TERM_PARAMETER__REFERENCES = eINSTANCE.getOperationTermParameter_References();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationSymbolUseImpl <em>Operation Symbol Use</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.OperationSymbolUseImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl#getOperationSymbolUse()
		 * @generated
		 */
		EClass OPERATION_SYMBOL_USE = eINSTANCE.getOperationSymbolUse();

		/**
		 * The meta object literal for the '<em><b>The Operation Symbol</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SYMBOL_USE__THE_OPERATION_SYMBOL = eINSTANCE.getOperationSymbolUse_TheOperationSymbol();

		/**
		 * The meta object literal for the '<em><b>Operands</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SYMBOL_USE__OPERANDS = eINSTANCE.getOperationSymbolUse_Operands();

	}

} //TermAlgebraUsePackage
