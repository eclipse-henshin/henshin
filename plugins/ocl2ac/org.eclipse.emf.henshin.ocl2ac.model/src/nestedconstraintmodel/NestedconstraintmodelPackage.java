/**
 */
package nestedconstraintmodel;

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
 * @see nestedconstraintmodel.NestedconstraintmodelFactory
 * @model kind="package"
 * @generated
 */
public interface NestedconstraintmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nestedconstraintmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/nestedconstraintmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nestedconstraintmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NestedconstraintmodelPackage eINSTANCE = nestedconstraintmodel.impl.NestedconstraintmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link nestedconstraintmodel.impl.NestedConstraintModelImpl <em>Nested Constraint Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedconstraintmodel.impl.NestedConstraintModelImpl
	 * @see nestedconstraintmodel.impl.NestedconstraintmodelPackageImpl#getNestedConstraintModel()
	 * @generated
	 */
	int NESTED_CONSTRAINT_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT_MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Nestedconstrainmodels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS = 1;

	/**
	 * The number of structural features of the '<em>Nested Constraint Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT_MODEL_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Nested Constraint Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT_MODEL_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link nestedconstraintmodel.NestedConstraintModel <em>Nested Constraint Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nested Constraint Model</em>'.
	 * @see nestedconstraintmodel.NestedConstraintModel
	 * @generated
	 */
	EClass getNestedConstraintModel();

	/**
	 * Returns the meta object for the attribute '{@link nestedconstraintmodel.NestedConstraintModel#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see nestedconstraintmodel.NestedConstraintModel#getName()
	 * @see #getNestedConstraintModel()
	 * @generated
	 */
	EAttribute getNestedConstraintModel_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link nestedconstraintmodel.NestedConstraintModel#getNestedconstrainmodels <em>Nestedconstrainmodels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nestedconstrainmodels</em>'.
	 * @see nestedconstraintmodel.NestedConstraintModel#getNestedconstrainmodels()
	 * @see #getNestedConstraintModel()
	 * @generated
	 */
	EReference getNestedConstraintModel_Nestedconstrainmodels();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NestedconstraintmodelFactory getNestedconstraintmodelFactory();

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
		 * The meta object literal for the '{@link nestedconstraintmodel.impl.NestedConstraintModelImpl <em>Nested Constraint Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedconstraintmodel.impl.NestedConstraintModelImpl
		 * @see nestedconstraintmodel.impl.NestedconstraintmodelPackageImpl#getNestedConstraintModel()
		 * @generated
		 */
		EClass NESTED_CONSTRAINT_MODEL = eINSTANCE.getNestedConstraintModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NESTED_CONSTRAINT_MODEL__NAME = eINSTANCE.getNestedConstraintModel_Name();

		/**
		 * The meta object literal for the '<em><b>Nestedconstrainmodels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS = eINSTANCE.getNestedConstraintModel_Nestedconstrainmodels();

	}

} //NestedconstraintmodelPackage
