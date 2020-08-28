/**
 */
package compactconditionmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see compactconditionmodel.CompactconditionmodelFactory
 * @model kind="package"
 * @generated
 */
public interface CompactconditionmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "compactconditionmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/CompactConditionModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "compactconditionmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CompactconditionmodelPackage eINSTANCE = compactconditionmodel.impl.CompactconditionmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link compactconditionmodel.impl.CompactConditionModelImpl <em>Compact Condition Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see compactconditionmodel.impl.CompactConditionModelImpl
	 * @see compactconditionmodel.impl.CompactconditionmodelPackageImpl#getCompactConditionModel()
	 * @generated
	 */
	int COMPACT_CONDITION_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPACT_CONDITION_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Compactconditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPACT_CONDITION_MODEL__COMPACTCONDITIONS = 1;

	/**
	 * The feature id for the '<em><b>Type Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPACT_CONDITION_MODEL__TYPE_GRAPH = 2;

	/**
	 * The number of structural features of the '<em>Compact Condition Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPACT_CONDITION_MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Compact Condition Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPACT_CONDITION_MODEL_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link compactconditionmodel.CompactConditionModel <em>Compact Condition Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compact Condition Model</em>'.
	 * @see compactconditionmodel.CompactConditionModel
	 * @generated
	 */
	EClass getCompactConditionModel();

	/**
	 * Returns the meta object for the attribute '{@link compactconditionmodel.CompactConditionModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see compactconditionmodel.CompactConditionModel#getName()
	 * @see #getCompactConditionModel()
	 * @generated
	 */
	EAttribute getCompactConditionModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link compactconditionmodel.CompactConditionModel#getCompactconditions <em>Compactconditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compactconditions</em>'.
	 * @see compactconditionmodel.CompactConditionModel#getCompactconditions()
	 * @see #getCompactConditionModel()
	 * @generated
	 */
	EReference getCompactConditionModel_Compactconditions();

	/**
	 * Returns the meta object for the reference '{@link compactconditionmodel.CompactConditionModel#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Graph</em>'.
	 * @see compactconditionmodel.CompactConditionModel#getTypeGraph()
	 * @see #getCompactConditionModel()
	 * @generated
	 */
	EReference getCompactConditionModel_TypeGraph();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CompactconditionmodelFactory getCompactconditionmodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link compactconditionmodel.impl.CompactConditionModelImpl <em>Compact Condition Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see compactconditionmodel.impl.CompactConditionModelImpl
		 * @see compactconditionmodel.impl.CompactconditionmodelPackageImpl#getCompactConditionModel()
		 * @generated
		 */
		EClass COMPACT_CONDITION_MODEL = eINSTANCE.getCompactConditionModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPACT_CONDITION_MODEL__NAME = eINSTANCE.getCompactConditionModel_Name();

		/**
		 * The meta object literal for the '<em><b>Compactconditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPACT_CONDITION_MODEL__COMPACTCONDITIONS = eINSTANCE.getCompactConditionModel_Compactconditions();

		/**
		 * The meta object literal for the '<em><b>Type Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPACT_CONDITION_MODEL__TYPE_GRAPH = eINSTANCE.getCompactConditionModel_TypeGraph();

	}

} //CompactconditionmodelPackage
