/**
 */
package nestedcondition;

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
 * @see nestedcondition.NestedconditionFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore#/' graph='graph.ecore#/' laxcondition='laxcondition.ecore#/'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface NestedconditionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nestedcondition";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/nestedcondition";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nestedcondition";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NestedconditionPackage eINSTANCE = nestedcondition.impl.NestedconditionPackageImpl.init();

	/**
	 * The meta object id for the '{@link nestedcondition.impl.NestedConstraintImpl <em>Nested Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.NestedConstraintImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getNestedConstraint()
	 * @generated
	 */
	int NESTED_CONSTRAINT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type Graph</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT__TYPE_GRAPH = 1;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT__CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT__DOMAIN = 3;

	/**
	 * The number of structural features of the '<em>Nested Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Nested Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.NestedConditionImpl <em>Nested Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.NestedConditionImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getNestedCondition()
	 * @generated
	 */
	int NESTED_CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__VARIABLES = 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION__DOMAIN = 1;

	/**
	 * The number of structural features of the '<em>Nested Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Nested Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NESTED_CONDITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.VariableImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 2;

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
	 * The meta object id for the '{@link nestedcondition.impl.QuantifiedConditionImpl <em>Quantified Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.QuantifiedConditionImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getQuantifiedCondition()
	 * @generated
	 */
	int QUANTIFIED_CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION__VARIABLES = NESTED_CONDITION__VARIABLES;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION__DOMAIN = NESTED_CONDITION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Quantifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION__QUANTIFIER = NESTED_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION__CONDITION = NESTED_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Morphism</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION__MORPHISM = NESTED_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Codomain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION__CODOMAIN = NESTED_CONDITION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Quantified Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION_FEATURE_COUNT = NESTED_CONDITION_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Quantified Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIED_CONDITION_OPERATION_COUNT = NESTED_CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.TrueImpl <em>True</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.TrueImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getTrue()
	 * @generated
	 */
	int TRUE = 4;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE__VARIABLES = NESTED_CONDITION__VARIABLES;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE__DOMAIN = NESTED_CONDITION__DOMAIN;

	/**
	 * The number of structural features of the '<em>True</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_FEATURE_COUNT = NESTED_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>True</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUE_OPERATION_COUNT = NESTED_CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.FormulaImpl <em>Formula</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.FormulaImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getFormula()
	 * @generated
	 */
	int FORMULA = 5;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__VARIABLES = NESTED_CONDITION__VARIABLES;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__DOMAIN = NESTED_CONDITION__DOMAIN;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__OPERATOR = NESTED_CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arguments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA__ARGUMENTS = NESTED_CONDITION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_FEATURE_COUNT = NESTED_CONDITION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Formula</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORMULA_OPERATION_COUNT = NESTED_CONDITION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.MorphismImpl <em>Morphism</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.MorphismImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getMorphism()
	 * @generated
	 */
	int MORPHISM = 6;

	/**
	 * The feature id for the '<em><b>Node Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORPHISM__NODE_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORPHISM__FROM = 1;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORPHISM__TO = 2;

	/**
	 * The feature id for the '<em><b>Edge Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORPHISM__EDGE_MAPPINGS = 3;

	/**
	 * The number of structural features of the '<em>Morphism</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORPHISM_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Morphism</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MORPHISM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.NodeMappingImpl <em>Node Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.NodeMappingImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getNodeMapping()
	 * @generated
	 */
	int NODE_MAPPING = 7;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING__ORIGIN = 0;

	/**
	 * The feature id for the '<em><b>Image</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING__IMAGE = 1;

	/**
	 * The number of structural features of the '<em>Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link nestedcondition.impl.EdgeMappingImpl <em>Edge Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see nestedcondition.impl.EdgeMappingImpl
	 * @see nestedcondition.impl.NestedconditionPackageImpl#getEdgeMapping()
	 * @generated
	 */
	int EDGE_MAPPING = 8;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_MAPPING__ORIGIN = 0;

	/**
	 * The feature id for the '<em><b>Image</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_MAPPING__IMAGE = 1;

	/**
	 * The number of structural features of the '<em>Edge Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Edge Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_MAPPING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link nestedcondition.NestedConstraint <em>Nested Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nested Constraint</em>'.
	 * @see nestedcondition.NestedConstraint
	 * @generated
	 */
	EClass getNestedConstraint();

	/**
	 * Returns the meta object for the attribute '{@link nestedcondition.NestedConstraint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see nestedcondition.NestedConstraint#getName()
	 * @see #getNestedConstraint()
	 * @generated
	 */
	EAttribute getNestedConstraint_Name();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.NestedConstraint#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type Graph</em>'.
	 * @see nestedcondition.NestedConstraint#getTypeGraph()
	 * @see #getNestedConstraint()
	 * @generated
	 */
	EReference getNestedConstraint_TypeGraph();

	/**
	 * Returns the meta object for the containment reference '{@link nestedcondition.NestedConstraint#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see nestedcondition.NestedConstraint#getCondition()
	 * @see #getNestedConstraint()
	 * @generated
	 */
	EReference getNestedConstraint_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link nestedcondition.NestedConstraint#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain</em>'.
	 * @see nestedcondition.NestedConstraint#getDomain()
	 * @see #getNestedConstraint()
	 * @generated
	 */
	EReference getNestedConstraint_Domain();

	/**
	 * Returns the meta object for class '{@link nestedcondition.NestedCondition <em>Nested Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nested Condition</em>'.
	 * @see nestedcondition.NestedCondition
	 * @generated
	 */
	EClass getNestedCondition();

	/**
	 * Returns the meta object for the containment reference list '{@link nestedcondition.NestedCondition#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see nestedcondition.NestedCondition#getVariables()
	 * @see #getNestedCondition()
	 * @generated
	 */
	EReference getNestedCondition_Variables();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.NestedCondition#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain</em>'.
	 * @see nestedcondition.NestedCondition#getDomain()
	 * @see #getNestedCondition()
	 * @generated
	 */
	EReference getNestedCondition_Domain();

	/**
	 * Returns the meta object for class '{@link nestedcondition.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see nestedcondition.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link nestedcondition.Variable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see nestedcondition.Variable#getName()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Name();

	/**
	 * Returns the meta object for class '{@link nestedcondition.QuantifiedCondition <em>Quantified Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quantified Condition</em>'.
	 * @see nestedcondition.QuantifiedCondition
	 * @generated
	 */
	EClass getQuantifiedCondition();

	/**
	 * Returns the meta object for the attribute '{@link nestedcondition.QuantifiedCondition#getQuantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Quantifier</em>'.
	 * @see nestedcondition.QuantifiedCondition#getQuantifier()
	 * @see #getQuantifiedCondition()
	 * @generated
	 */
	EAttribute getQuantifiedCondition_Quantifier();

	/**
	 * Returns the meta object for the containment reference '{@link nestedcondition.QuantifiedCondition#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see nestedcondition.QuantifiedCondition#getCondition()
	 * @see #getQuantifiedCondition()
	 * @generated
	 */
	EReference getQuantifiedCondition_Condition();

	/**
	 * Returns the meta object for the containment reference '{@link nestedcondition.QuantifiedCondition#getMorphism <em>Morphism</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Morphism</em>'.
	 * @see nestedcondition.QuantifiedCondition#getMorphism()
	 * @see #getQuantifiedCondition()
	 * @generated
	 */
	EReference getQuantifiedCondition_Morphism();

	/**
	 * Returns the meta object for the containment reference '{@link nestedcondition.QuantifiedCondition#getCodomain <em>Codomain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Codomain</em>'.
	 * @see nestedcondition.QuantifiedCondition#getCodomain()
	 * @see #getQuantifiedCondition()
	 * @generated
	 */
	EReference getQuantifiedCondition_Codomain();

	/**
	 * Returns the meta object for class '{@link nestedcondition.True <em>True</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>True</em>'.
	 * @see nestedcondition.True
	 * @generated
	 */
	EClass getTrue();

	/**
	 * Returns the meta object for class '{@link nestedcondition.Formula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Formula</em>'.
	 * @see nestedcondition.Formula
	 * @generated
	 */
	EClass getFormula();

	/**
	 * Returns the meta object for the attribute '{@link nestedcondition.Formula#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see nestedcondition.Formula#getOperator()
	 * @see #getFormula()
	 * @generated
	 */
	EAttribute getFormula_Operator();

	/**
	 * Returns the meta object for the containment reference list '{@link nestedcondition.Formula#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arguments</em>'.
	 * @see nestedcondition.Formula#getArguments()
	 * @see #getFormula()
	 * @generated
	 */
	EReference getFormula_Arguments();

	/**
	 * Returns the meta object for class '{@link nestedcondition.Morphism <em>Morphism</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Morphism</em>'.
	 * @see nestedcondition.Morphism
	 * @generated
	 */
	EClass getMorphism();

	/**
	 * Returns the meta object for the containment reference list '{@link nestedcondition.Morphism#getNodeMappings <em>Node Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Node Mappings</em>'.
	 * @see nestedcondition.Morphism#getNodeMappings()
	 * @see #getMorphism()
	 * @generated
	 */
	EReference getMorphism_NodeMappings();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.Morphism#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see nestedcondition.Morphism#getFrom()
	 * @see #getMorphism()
	 * @generated
	 */
	EReference getMorphism_From();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.Morphism#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see nestedcondition.Morphism#getTo()
	 * @see #getMorphism()
	 * @generated
	 */
	EReference getMorphism_To();

	/**
	 * Returns the meta object for the containment reference list '{@link nestedcondition.Morphism#getEdgeMappings <em>Edge Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edge Mappings</em>'.
	 * @see nestedcondition.Morphism#getEdgeMappings()
	 * @see #getMorphism()
	 * @generated
	 */
	EReference getMorphism_EdgeMappings();

	/**
	 * Returns the meta object for class '{@link nestedcondition.NodeMapping <em>Node Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Mapping</em>'.
	 * @see nestedcondition.NodeMapping
	 * @generated
	 */
	EClass getNodeMapping();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.NodeMapping#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin</em>'.
	 * @see nestedcondition.NodeMapping#getOrigin()
	 * @see #getNodeMapping()
	 * @generated
	 */
	EReference getNodeMapping_Origin();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.NodeMapping#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Image</em>'.
	 * @see nestedcondition.NodeMapping#getImage()
	 * @see #getNodeMapping()
	 * @generated
	 */
	EReference getNodeMapping_Image();

	/**
	 * Returns the meta object for class '{@link nestedcondition.EdgeMapping <em>Edge Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge Mapping</em>'.
	 * @see nestedcondition.EdgeMapping
	 * @generated
	 */
	EClass getEdgeMapping();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.EdgeMapping#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin</em>'.
	 * @see nestedcondition.EdgeMapping#getOrigin()
	 * @see #getEdgeMapping()
	 * @generated
	 */
	EReference getEdgeMapping_Origin();

	/**
	 * Returns the meta object for the reference '{@link nestedcondition.EdgeMapping#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Image</em>'.
	 * @see nestedcondition.EdgeMapping#getImage()
	 * @see #getEdgeMapping()
	 * @generated
	 */
	EReference getEdgeMapping_Image();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NestedconditionFactory getNestedconditionFactory();

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
		 * The meta object literal for the '{@link nestedcondition.impl.NestedConstraintImpl <em>Nested Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.NestedConstraintImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getNestedConstraint()
		 * @generated
		 */
		EClass NESTED_CONSTRAINT = eINSTANCE.getNestedConstraint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NESTED_CONSTRAINT__NAME = eINSTANCE.getNestedConstraint_Name();

		/**
		 * The meta object literal for the '<em><b>Type Graph</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONSTRAINT__TYPE_GRAPH = eINSTANCE.getNestedConstraint_TypeGraph();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONSTRAINT__CONDITION = eINSTANCE.getNestedConstraint_Condition();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONSTRAINT__DOMAIN = eINSTANCE.getNestedConstraint_Domain();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.NestedConditionImpl <em>Nested Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.NestedConditionImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getNestedCondition()
		 * @generated
		 */
		EClass NESTED_CONDITION = eINSTANCE.getNestedCondition();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONDITION__VARIABLES = eINSTANCE.getNestedCondition_Variables();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NESTED_CONDITION__DOMAIN = eINSTANCE.getNestedCondition_Domain();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.VariableImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getVariable()
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
		 * The meta object literal for the '{@link nestedcondition.impl.QuantifiedConditionImpl <em>Quantified Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.QuantifiedConditionImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getQuantifiedCondition()
		 * @generated
		 */
		EClass QUANTIFIED_CONDITION = eINSTANCE.getQuantifiedCondition();

		/**
		 * The meta object literal for the '<em><b>Quantifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUANTIFIED_CONDITION__QUANTIFIER = eINSTANCE.getQuantifiedCondition_Quantifier();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_CONDITION__CONDITION = eINSTANCE.getQuantifiedCondition_Condition();

		/**
		 * The meta object literal for the '<em><b>Morphism</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_CONDITION__MORPHISM = eINSTANCE.getQuantifiedCondition_Morphism();

		/**
		 * The meta object literal for the '<em><b>Codomain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIED_CONDITION__CODOMAIN = eINSTANCE.getQuantifiedCondition_Codomain();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.TrueImpl <em>True</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.TrueImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getTrue()
		 * @generated
		 */
		EClass TRUE = eINSTANCE.getTrue();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.FormulaImpl <em>Formula</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.FormulaImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getFormula()
		 * @generated
		 */
		EClass FORMULA = eINSTANCE.getFormula();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FORMULA__OPERATOR = eINSTANCE.getFormula_Operator();

		/**
		 * The meta object literal for the '<em><b>Arguments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FORMULA__ARGUMENTS = eINSTANCE.getFormula_Arguments();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.MorphismImpl <em>Morphism</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.MorphismImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getMorphism()
		 * @generated
		 */
		EClass MORPHISM = eINSTANCE.getMorphism();

		/**
		 * The meta object literal for the '<em><b>Node Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MORPHISM__NODE_MAPPINGS = eINSTANCE.getMorphism_NodeMappings();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MORPHISM__FROM = eINSTANCE.getMorphism_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MORPHISM__TO = eINSTANCE.getMorphism_To();

		/**
		 * The meta object literal for the '<em><b>Edge Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MORPHISM__EDGE_MAPPINGS = eINSTANCE.getMorphism_EdgeMappings();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.NodeMappingImpl <em>Node Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.NodeMappingImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getNodeMapping()
		 * @generated
		 */
		EClass NODE_MAPPING = eINSTANCE.getNodeMapping();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_MAPPING__ORIGIN = eINSTANCE.getNodeMapping_Origin();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_MAPPING__IMAGE = eINSTANCE.getNodeMapping_Image();

		/**
		 * The meta object literal for the '{@link nestedcondition.impl.EdgeMappingImpl <em>Edge Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see nestedcondition.impl.EdgeMappingImpl
		 * @see nestedcondition.impl.NestedconditionPackageImpl#getEdgeMapping()
		 * @generated
		 */
		EClass EDGE_MAPPING = eINSTANCE.getEdgeMapping();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_MAPPING__ORIGIN = eINSTANCE.getEdgeMapping_Origin();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE_MAPPING__IMAGE = eINSTANCE.getEdgeMapping_Image();

	}

} //NestedconditionPackage
