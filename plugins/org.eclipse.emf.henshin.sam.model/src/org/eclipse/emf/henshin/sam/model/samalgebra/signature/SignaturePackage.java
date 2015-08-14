/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature;

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
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignatureFactory
 * @model kind="package"
 * @generated
 */
public interface SignaturePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "signature";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2015/Henshin/sam/samalgebra/signature";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "signature";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SignaturePackage eINSTANCE = org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SortImpl <em>Sort</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SortImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getSort()
	 * @generated
	 */
	int SORT = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SORT__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SORT__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sort</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SORT_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl <em>Operation Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getOperationSymbol()
	 * @generated
	 */
	int OPERATION_SYMBOL = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Operand Symbols</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL__OPERAND_SYMBOLS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Result Symbol</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL__RESULT_SYMBOL = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Operation Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_SYMBOL_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperandSymbolImpl <em>Operand Symbol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperandSymbolImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getOperandSymbol()
	 * @generated
	 */
	int OPERAND_SYMBOL = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERAND_SYMBOL__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERAND_SYMBOL__SORT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERAND_SYMBOL__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Operand Symbol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERAND_SYMBOL_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl <em>Algebra Signature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getAlgebraSignature()
	 * @generated
	 */
	int ALGEBRA_SIGNATURE = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_SIGNATURE__ANNOTATIONS = SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Sorts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_SIGNATURE__SORTS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operation Symbols</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_SIGNATURE__OPERATION_SYMBOLS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_SIGNATURE__NAME = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_SIGNATURE__EXTENDS = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Algebra Signature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGEBRA_SIGNATURE_FEATURE_COUNT = SamannotationPackage.ANNOTATED_ELEM_FEATURE_COUNT + 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort <em>Sort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sort</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort
	 * @generated
	 */
	EClass getSort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort#getName()
	 * @see #getSort()
	 * @generated
	 */
	EAttribute getSort_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol <em>Operation Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation Symbol</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol
	 * @generated
	 */
	EClass getOperationSymbol();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getOperandSymbols <em>Operand Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operand Symbols</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getOperandSymbols()
	 * @see #getOperationSymbol()
	 * @generated
	 */
	EReference getOperationSymbol_OperandSymbols();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getResultSymbol <em>Result Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Result Symbol</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getResultSymbol()
	 * @see #getOperationSymbol()
	 * @generated
	 */
	EReference getOperationSymbol_ResultSymbol();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getName()
	 * @see #getOperationSymbol()
	 * @generated
	 */
	EAttribute getOperationSymbol_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol <em>Operand Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operand Symbol</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol
	 * @generated
	 */
	EClass getOperandSymbol();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol#getSort <em>Sort</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Sort</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol#getSort()
	 * @see #getOperandSymbol()
	 * @generated
	 */
	EReference getOperandSymbol_Sort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol#getName()
	 * @see #getOperandSymbol()
	 * @generated
	 */
	EAttribute getOperandSymbol_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature <em>Algebra Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Algebra Signature</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature
	 * @generated
	 */
	EClass getAlgebraSignature();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getSorts <em>Sorts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sorts</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getSorts()
	 * @see #getAlgebraSignature()
	 * @generated
	 */
	EReference getAlgebraSignature_Sorts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getOperationSymbols <em>Operation Symbols</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operation Symbols</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getOperationSymbols()
	 * @see #getAlgebraSignature()
	 * @generated
	 */
	EReference getAlgebraSignature_OperationSymbols();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getName()
	 * @see #getAlgebraSignature()
	 * @generated
	 */
	EAttribute getAlgebraSignature_Name();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getExtends <em>Extends</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Extends</em>'.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getExtends()
	 * @see #getAlgebraSignature()
	 * @generated
	 */
	EReference getAlgebraSignature_Extends();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SignatureFactory getSignatureFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SortImpl <em>Sort</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SortImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getSort()
		 * @generated
		 */
		EClass SORT = eINSTANCE.getSort();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SORT__NAME = eINSTANCE.getSort_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl <em>Operation Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperationSymbolImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getOperationSymbol()
		 * @generated
		 */
		EClass OPERATION_SYMBOL = eINSTANCE.getOperationSymbol();

		/**
		 * The meta object literal for the '<em><b>Operand Symbols</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SYMBOL__OPERAND_SYMBOLS = eINSTANCE.getOperationSymbol_OperandSymbols();

		/**
		 * The meta object literal for the '<em><b>Result Symbol</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION_SYMBOL__RESULT_SYMBOL = eINSTANCE.getOperationSymbol_ResultSymbol();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION_SYMBOL__NAME = eINSTANCE.getOperationSymbol_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperandSymbolImpl <em>Operand Symbol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.OperandSymbolImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getOperandSymbol()
		 * @generated
		 */
		EClass OPERAND_SYMBOL = eINSTANCE.getOperandSymbol();

		/**
		 * The meta object literal for the '<em><b>Sort</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERAND_SYMBOL__SORT = eINSTANCE.getOperandSymbol_Sort();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERAND_SYMBOL__NAME = eINSTANCE.getOperandSymbol_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl <em>Algebra Signature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl
		 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl#getAlgebraSignature()
		 * @generated
		 */
		EClass ALGEBRA_SIGNATURE = eINSTANCE.getAlgebraSignature();

		/**
		 * The meta object literal for the '<em><b>Sorts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGEBRA_SIGNATURE__SORTS = eINSTANCE.getAlgebraSignature_Sorts();

		/**
		 * The meta object literal for the '<em><b>Operation Symbols</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGEBRA_SIGNATURE__OPERATION_SYMBOLS = eINSTANCE.getAlgebraSignature_OperationSymbols();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ALGEBRA_SIGNATURE__NAME = eINSTANCE.getAlgebraSignature_Name();

		/**
		 * The meta object literal for the '<em><b>Extends</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALGEBRA_SIGNATURE__EXTENDS = eINSTANCE.getAlgebraSignature_Extends();

	}

} //SignaturePackage
