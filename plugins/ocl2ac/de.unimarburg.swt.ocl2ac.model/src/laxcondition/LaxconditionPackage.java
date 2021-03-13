/**
 */
package laxcondition;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see laxcondition.LaxconditionFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore' graph_0='graph.ecore#/'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface LaxconditionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "laxcondition";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/laxcondition";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "laxcondition";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LaxconditionPackage eINSTANCE = laxcondition.impl.LaxconditionPackageImpl.init();

	/**
	 * The meta object id for the '{@link laxcondition.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.impl.ConditionImpl
	 * @see laxcondition.impl.LaxconditionPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 0;

	/**
	 * The feature id for the '<em><b>Type Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__TYPE_GRAPH = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__NAME = 1;

	/**
	 * The feature id for the '<em><b>Lax Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__LAX_CONDITION = 2;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link laxcondition.impl.LaxConditionImpl <em>Lax Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.impl.LaxConditionImpl
	 * @see laxcondition.impl.LaxconditionPackageImpl#getLaxCondition()
	 * @generated
	 */
	int LAX_CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAX_CONDITION__FORMULA = 0;

	/**
	 * The number of structural features of the '<em>Lax Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAX_CONDITION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Lax Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LAX_CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link laxcondition.impl.QuantifiedLaxConditionImpl <em>Quantified Lax Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.impl.QuantifiedLaxConditionImpl
	 * @see laxcondition.impl.LaxconditionPackageImpl#getQuantifiedLaxCondition()
	 * @generated
	 */
	int QUANTIFIED_LAX_CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION__FORMULA = LAX_CONDITION__FORMULA;

	/**
	 * The feature id for the '<em><b>Graph</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION__GRAPH = LAX_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION__CONDITION = LAX_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Quantifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION__QUANTIFIER = LAX_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION__VARIABLES = LAX_CONDITION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Quantified Lax Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION_FEATURE_COUNT = LAX_CONDITION_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Quantified Lax Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_LAX_CONDITION_OPERATION_COUNT = LAX_CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link laxcondition.impl.TrueImpl <em>True</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.impl.TrueImpl
	 * @see laxcondition.impl.LaxconditionPackageImpl#getTrue()
	 * @generated
	 */
	int TRUE = 3;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE__FORMULA = LAX_CONDITION__FORMULA;

	/**
	 * The number of structural features of the '<em>True</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_FEATURE_COUNT = LAX_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>True</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_OPERATION_COUNT = LAX_CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link laxcondition.impl.FormulaImpl <em>Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.impl.FormulaImpl
	 * @see laxcondition.impl.LaxconditionPackageImpl#getFormula()
	 * @generated
	 */
	int FORMULA = 4;

	/**
	 * The feature id for the '<em><b>Formula</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__FORMULA = LAX_CONDITION__FORMULA;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__OP = LAX_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__ARGUMENTS = LAX_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_FEATURE_COUNT = LAX_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_OPERATION_COUNT = LAX_CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link laxcondition.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.impl.VariableImpl
	 * @see laxcondition.impl.LaxconditionPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link laxcondition.Quantifier <em>Quantifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.Quantifier
	 * @see laxcondition.impl.LaxconditionPackageImpl#getQuantifier()
	 * @generated
	 */
	int QUANTIFIER = 6;

	/**
	 * The meta object id for the '{@link laxcondition.Operator <em>Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see laxcondition.Operator
	 * @see laxcondition.impl.LaxconditionPackageImpl#getOperator()
	 * @generated
	 */
	int OPERATOR = 7;


	/**
	 * Returns the meta object for class '{@link laxcondition.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see laxcondition.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the reference '{@link laxcondition.Condition#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Graph</em>'.
	 * @see laxcondition.Condition#getTypeGraph()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_TypeGraph();

	/**
	 * Returns the meta object for the attribute '{@link laxcondition.Condition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see laxcondition.Condition#getName()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_Name();

	/**
	 * Returns the meta object for the containment reference '{@link laxcondition.Condition#getLaxCondition <em>Lax Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lax Condition</em>'.
	 * @see laxcondition.Condition#getLaxCondition()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_LaxCondition();

	/**
	 * Returns the meta object for class '{@link laxcondition.LaxCondition <em>Lax Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lax Condition</em>'.
	 * @see laxcondition.LaxCondition
	 * @generated
	 */
	EClass getLaxCondition();

	/**
	 * Returns the meta object for the container reference '{@link laxcondition.LaxCondition#getFormula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Formula</em>'.
	 * @see laxcondition.LaxCondition#getFormula()
	 * @see #getLaxCondition()
	 * @generated
	 */
	EReference getLaxCondition_Formula();

	/**
	 * Returns the meta object for class '{@link laxcondition.QuantifiedLaxCondition <em>Quantified Lax Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quantified Lax Condition</em>'.
	 * @see laxcondition.QuantifiedLaxCondition
	 * @generated
	 */
	EClass getQuantifiedLaxCondition();

	/**
	 * Returns the meta object for the containment reference '{@link laxcondition.QuantifiedLaxCondition#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Graph</em>'.
	 * @see laxcondition.QuantifiedLaxCondition#getGraph()
	 * @see #getQuantifiedLaxCondition()
	 * @generated
	 */
	EReference getQuantifiedLaxCondition_Graph();

	/**
	 * Returns the meta object for the containment reference '{@link laxcondition.QuantifiedLaxCondition#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see laxcondition.QuantifiedLaxCondition#getCondition()
	 * @see #getQuantifiedLaxCondition()
	 * @generated
	 */
	EReference getQuantifiedLaxCondition_Condition();

	/**
	 * Returns the meta object for the attribute '{@link laxcondition.QuantifiedLaxCondition#getQuantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Quantifier</em>'.
	 * @see laxcondition.QuantifiedLaxCondition#getQuantifier()
	 * @see #getQuantifiedLaxCondition()
	 * @generated
	 */
	EAttribute getQuantifiedLaxCondition_Quantifier();

	/**
	 * Returns the meta object for the containment reference list '{@link laxcondition.QuantifiedLaxCondition#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see laxcondition.QuantifiedLaxCondition#getVariables()
	 * @see #getQuantifiedLaxCondition()
	 * @generated
	 */
	EReference getQuantifiedLaxCondition_Variables();

	/**
	 * Returns the meta object for class '{@link laxcondition.True <em>True</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>True</em>'.
	 * @see laxcondition.True
	 * @generated
	 */
	EClass getTrue();

	/**
	 * Returns the meta object for class '{@link laxcondition.Formula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Formula</em>'.
	 * @see laxcondition.Formula
	 * @generated
	 */
	EClass getFormula();

	/**
	 * Returns the meta object for the attribute '{@link laxcondition.Formula#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see laxcondition.Formula#getOp()
	 * @see #getFormula()
	 * @generated
	 */
	EAttribute getFormula_Op();

	/**
	 * Returns the meta object for the containment reference list '{@link laxcondition.Formula#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see laxcondition.Formula#getArguments()
	 * @see #getFormula()
	 * @generated
	 */
	EReference getFormula_Arguments();

	/**
	 * Returns the meta object for class '{@link laxcondition.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see laxcondition.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link laxcondition.Variable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see laxcondition.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for enum '{@link laxcondition.Quantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Quantifier</em>'.
	 * @see laxcondition.Quantifier
	 * @generated
	 */
	EEnum getQuantifier();

	/**
	 * Returns the meta object for enum '{@link laxcondition.Operator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator</em>'.
	 * @see laxcondition.Operator
	 * @generated
	 */
	EEnum getOperator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LaxconditionFactory getLaxconditionFactory();

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
		 * The meta object literal for the '{@link laxcondition.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.impl.ConditionImpl
		 * @see laxcondition.impl.LaxconditionPackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '<em><b>Type Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__TYPE_GRAPH = eINSTANCE.getCondition_TypeGraph();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__NAME = eINSTANCE.getCondition_Name();

		/**
		 * The meta object literal for the '<em><b>Lax Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__LAX_CONDITION = eINSTANCE.getCondition_LaxCondition();

		/**
		 * The meta object literal for the '{@link laxcondition.impl.LaxConditionImpl <em>Lax Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.impl.LaxConditionImpl
		 * @see laxcondition.impl.LaxconditionPackageImpl#getLaxCondition()
		 * @generated
		 */
		EClass LAX_CONDITION = eINSTANCE.getLaxCondition();

		/**
		 * The meta object literal for the '<em><b>Formula</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LAX_CONDITION__FORMULA = eINSTANCE.getLaxCondition_Formula();

		/**
		 * The meta object literal for the '{@link laxcondition.impl.QuantifiedLaxConditionImpl <em>Quantified Lax Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.impl.QuantifiedLaxConditionImpl
		 * @see laxcondition.impl.LaxconditionPackageImpl#getQuantifiedLaxCondition()
		 * @generated
		 */
		EClass QUANTIFIED_LAX_CONDITION = eINSTANCE.getQuantifiedLaxCondition();

		/**
		 * The meta object literal for the '<em><b>Graph</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_LAX_CONDITION__GRAPH = eINSTANCE.getQuantifiedLaxCondition_Graph();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_LAX_CONDITION__CONDITION = eINSTANCE.getQuantifiedLaxCondition_Condition();

		/**
		 * The meta object literal for the '<em><b>Quantifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUANTIFIED_LAX_CONDITION__QUANTIFIER = eINSTANCE.getQuantifiedLaxCondition_Quantifier();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_LAX_CONDITION__VARIABLES = eINSTANCE.getQuantifiedLaxCondition_Variables();

		/**
		 * The meta object literal for the '{@link laxcondition.impl.TrueImpl <em>True</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.impl.TrueImpl
		 * @see laxcondition.impl.LaxconditionPackageImpl#getTrue()
		 * @generated
		 */
		EClass TRUE = eINSTANCE.getTrue();

		/**
		 * The meta object literal for the '{@link laxcondition.impl.FormulaImpl <em>Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.impl.FormulaImpl
		 * @see laxcondition.impl.LaxconditionPackageImpl#getFormula()
		 * @generated
		 */
		EClass FORMULA = eINSTANCE.getFormula();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMULA__OP = eINSTANCE.getFormula_Op();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMULA__ARGUMENTS = eINSTANCE.getFormula_Arguments();

		/**
		 * The meta object literal for the '{@link laxcondition.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.impl.VariableImpl
		 * @see laxcondition.impl.LaxconditionPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__NAME = eINSTANCE.getVariable_Name();

		/**
		 * The meta object literal for the '{@link laxcondition.Quantifier <em>Quantifier</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.Quantifier
		 * @see laxcondition.impl.LaxconditionPackageImpl#getQuantifier()
		 * @generated
		 */
		EEnum QUANTIFIER = eINSTANCE.getQuantifier();

		/**
		 * The meta object literal for the '{@link laxcondition.Operator <em>Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see laxcondition.Operator
		 * @see laxcondition.impl.LaxconditionPackageImpl#getOperator()
		 * @generated
		 */
		EEnum OPERATOR = eINSTANCE.getOperator();

	}

} //LaxconditionPackage
