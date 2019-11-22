/**
 */
package metrics;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * @see metrics.MetricsFactory
 * @model kind="package"
 * @generated
 */
public interface MetricsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "metrics";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://metrics";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "metrics";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MetricsPackage eINSTANCE = metrics.impl.MetricsPackageImpl.init();

	/**
	 * The meta object id for the '{@link metrics.impl.RuleSetMetricsImpl <em>Rule Set Metrics</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see metrics.impl.RuleSetMetricsImpl
	 * @see metrics.impl.MetricsPackageImpl#getRuleSetMetrics()
	 * @generated
	 */
	int RULE_SET_METRICS = 0;

	/**
	 * The feature id for the '<em><b>Rule Metrics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__RULE_METRICS = 0;

	/**
	 * The feature id for the '<em><b>Number Of Rules</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__NUMBER_OF_RULES = 1;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__RULE_SET = 2;

	/**
	 * The feature id for the '<em><b>Total Number Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES = 3;

	/**
	 * The feature id for the '<em><b>Total Number Of Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES = 4;

	/**
	 * The feature id for the '<em><b>Total Number Of Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES = 5;

	/**
	 * The feature id for the '<em><b>Total Number Of Lhs Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES = 6;

	/**
	 * The feature id for the '<em><b>Total Number Of Lhs Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES = 7;

	/**
	 * The feature id for the '<em><b>Total Number Of Lhs Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES = 8;

	/**
	 * The feature id for the '<em><b>Total Number Of Annotated Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES = 9;

	/**
	 * The feature id for the '<em><b>Total Number Of Annotated Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES = 10;

	/**
	 * The feature id for the '<em><b>Total Number Of Annotated Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES = 11;

	/**
	 * The feature id for the '<em><b>Total Number Of NA Cs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS = 12;

	/**
	 * The feature id for the '<em><b>Total Number Of PA Cs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS = 13;

	/**
	 * The number of structural features of the '<em>Rule Set Metrics</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS_FEATURE_COUNT = 14;

	/**
	 * The operation id for the '<em>Find Rule Metrics</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS___FIND_RULE_METRICS__RULE = 0;

	/**
	 * The operation id for the '<em>Create Presentation String</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS___CREATE_PRESENTATION_STRING = 1;

	/**
	 * The number of operations of the '<em>Rule Set Metrics</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SET_METRICS_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link metrics.impl.RuleMetricsImpl <em>Rule Metrics</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see metrics.impl.RuleMetricsImpl
	 * @see metrics.impl.MetricsPackageImpl#getRuleMetrics()
	 * @generated
	 */
	int RULE_METRICS = 1;

	/**
	 * The feature id for the '<em><b>Number Of Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_NODES = 0;

	/**
	 * The feature id for the '<em><b>Number Of Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_EDGES = 1;

	/**
	 * The feature id for the '<em><b>Number Of Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_ATTRIBUTES = 2;

	/**
	 * The feature id for the '<em><b>Number Of Lhs Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_LHS_NODES = 3;

	/**
	 * The feature id for the '<em><b>Number Of Lhs Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_LHS_EDGES = 4;

	/**
	 * The feature id for the '<em><b>Number Of Lhs Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES = 5;

	/**
	 * The feature id for the '<em><b>Number Of Annotated Nodes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_ANNOTATED_NODES = 6;

	/**
	 * The feature id for the '<em><b>Number Of Annotated Edges</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES = 7;

	/**
	 * The feature id for the '<em><b>Number Of Annotated Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES = 8;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__RULE = 9;

	/**
	 * The feature id for the '<em><b>Number Of NA Cs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_NA_CS = 10;

	/**
	 * The feature id for the '<em><b>Number Of PA Cs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS__NUMBER_OF_PA_CS = 11;

	/**
	 * The number of structural features of the '<em>Rule Metrics</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS_FEATURE_COUNT = 12;

	/**
	 * The number of operations of the '<em>Rule Metrics</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_METRICS_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link metrics.RuleSetMetrics <em>Rule Set Metrics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Set Metrics</em>'.
	 * @see metrics.RuleSetMetrics
	 * @generated
	 */
	EClass getRuleSetMetrics();

	/**
	 * Returns the meta object for the containment reference list '{@link metrics.RuleSetMetrics#getRuleMetrics <em>Rule Metrics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rule Metrics</em>'.
	 * @see metrics.RuleSetMetrics#getRuleMetrics()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EReference getRuleSetMetrics_RuleMetrics();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getNumberOfRules <em>Number Of Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Rules</em>'.
	 * @see metrics.RuleSetMetrics#getNumberOfRules()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_NumberOfRules();

	/**
	 * Returns the meta object for the reference list '{@link metrics.RuleSetMetrics#getRuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rule Set</em>'.
	 * @see metrics.RuleSetMetrics#getRuleSet()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EReference getRuleSetMetrics_RuleSet();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfNodes <em>Total Number Of Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Nodes</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfNodes()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfNodes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfEdges <em>Total Number Of Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Edges</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfEdges()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfEdges();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfAttributes <em>Total Number Of Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Attributes</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfAttributes()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfAttributes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfLhsNodes <em>Total Number Of Lhs Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Lhs Nodes</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfLhsNodes()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfLhsNodes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfLhsEdges <em>Total Number Of Lhs Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Lhs Edges</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfLhsEdges()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfLhsEdges();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfLhsAttributes <em>Total Number Of Lhs Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Lhs Attributes</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfLhsAttributes()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfLhsAttributes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedNodes <em>Total Number Of Annotated Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Annotated Nodes</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfAnnotatedNodes()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfAnnotatedNodes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedEdges <em>Total Number Of Annotated Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Annotated Edges</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfAnnotatedEdges()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfAnnotatedEdges();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfAnnotatedAttributes <em>Total Number Of Annotated Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of Annotated Attributes</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfAnnotatedAttributes()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfAnnotatedAttributes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfNACs <em>Total Number Of NA Cs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of NA Cs</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfNACs()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfNACs();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleSetMetrics#getTotalNumberOfPACs <em>Total Number Of PA Cs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Number Of PA Cs</em>'.
	 * @see metrics.RuleSetMetrics#getTotalNumberOfPACs()
	 * @see #getRuleSetMetrics()
	 * @generated
	 */
	EAttribute getRuleSetMetrics_TotalNumberOfPACs();

	/**
	 * Returns the meta object for the '{@link metrics.RuleSetMetrics#findRuleMetrics(org.eclipse.emf.henshin.model.Rule) <em>Find Rule Metrics</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Find Rule Metrics</em>' operation.
	 * @see metrics.RuleSetMetrics#findRuleMetrics(org.eclipse.emf.henshin.model.Rule)
	 * @generated
	 */
	EOperation getRuleSetMetrics__FindRuleMetrics__Rule();

	/**
	 * Returns the meta object for the '{@link metrics.RuleSetMetrics#createPresentationString() <em>Create Presentation String</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Create Presentation String</em>' operation.
	 * @see metrics.RuleSetMetrics#createPresentationString()
	 * @generated
	 */
	EOperation getRuleSetMetrics__CreatePresentationString();

	/**
	 * Returns the meta object for class '{@link metrics.RuleMetrics <em>Rule Metrics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Metrics</em>'.
	 * @see metrics.RuleMetrics
	 * @generated
	 */
	EClass getRuleMetrics();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfNodes <em>Number Of Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Nodes</em>'.
	 * @see metrics.RuleMetrics#getNumberOfNodes()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfNodes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfEdges <em>Number Of Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Edges</em>'.
	 * @see metrics.RuleMetrics#getNumberOfEdges()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfEdges();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfAttributes <em>Number Of Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Attributes</em>'.
	 * @see metrics.RuleMetrics#getNumberOfAttributes()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfAttributes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfLhsNodes <em>Number Of Lhs Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Lhs Nodes</em>'.
	 * @see metrics.RuleMetrics#getNumberOfLhsNodes()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfLhsNodes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfLhsEdges <em>Number Of Lhs Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Lhs Edges</em>'.
	 * @see metrics.RuleMetrics#getNumberOfLhsEdges()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfLhsEdges();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfLhsAttributes <em>Number Of Lhs Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Lhs Attributes</em>'.
	 * @see metrics.RuleMetrics#getNumberOfLhsAttributes()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfLhsAttributes();

	/**
	 * Returns the meta object for the reference '{@link metrics.RuleMetrics#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see metrics.RuleMetrics#getRule()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EReference getRuleMetrics_Rule();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfNACs <em>Number Of NA Cs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of NA Cs</em>'.
	 * @see metrics.RuleMetrics#getNumberOfNACs()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfNACs();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfPACs <em>Number Of PA Cs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of PA Cs</em>'.
	 * @see metrics.RuleMetrics#getNumberOfPACs()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfPACs();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfAnnotatedNodes <em>Number Of Annotated Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Annotated Nodes</em>'.
	 * @see metrics.RuleMetrics#getNumberOfAnnotatedNodes()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfAnnotatedNodes();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfAnnotatedEdges <em>Number Of Annotated Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Annotated Edges</em>'.
	 * @see metrics.RuleMetrics#getNumberOfAnnotatedEdges()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfAnnotatedEdges();

	/**
	 * Returns the meta object for the attribute '{@link metrics.RuleMetrics#getNumberOfAnnotatedAttributes <em>Number Of Annotated Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Annotated Attributes</em>'.
	 * @see metrics.RuleMetrics#getNumberOfAnnotatedAttributes()
	 * @see #getRuleMetrics()
	 * @generated
	 */
	EAttribute getRuleMetrics_NumberOfAnnotatedAttributes();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MetricsFactory getMetricsFactory();

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
		 * The meta object literal for the '{@link metrics.impl.RuleSetMetricsImpl <em>Rule Set Metrics</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see metrics.impl.RuleSetMetricsImpl
		 * @see metrics.impl.MetricsPackageImpl#getRuleSetMetrics()
		 * @generated
		 */
		EClass RULE_SET_METRICS = eINSTANCE.getRuleSetMetrics();

		/**
		 * The meta object literal for the '<em><b>Rule Metrics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_SET_METRICS__RULE_METRICS = eINSTANCE.getRuleSetMetrics_RuleMetrics();

		/**
		 * The meta object literal for the '<em><b>Number Of Rules</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__NUMBER_OF_RULES = eINSTANCE.getRuleSetMetrics_NumberOfRules();

		/**
		 * The meta object literal for the '<em><b>Rule Set</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_SET_METRICS__RULE_SET = eINSTANCE.getRuleSetMetrics_RuleSet();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES = eINSTANCE.getRuleSetMetrics_TotalNumberOfNodes();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Edges</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES = eINSTANCE.getRuleSetMetrics_TotalNumberOfEdges();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES = eINSTANCE.getRuleSetMetrics_TotalNumberOfAttributes();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Lhs Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES = eINSTANCE.getRuleSetMetrics_TotalNumberOfLhsNodes();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Lhs Edges</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES = eINSTANCE.getRuleSetMetrics_TotalNumberOfLhsEdges();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Lhs Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES = eINSTANCE.getRuleSetMetrics_TotalNumberOfLhsAttributes();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Annotated Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES = eINSTANCE.getRuleSetMetrics_TotalNumberOfAnnotatedNodes();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Annotated Edges</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES = eINSTANCE.getRuleSetMetrics_TotalNumberOfAnnotatedEdges();

		/**
		 * The meta object literal for the '<em><b>Total Number Of Annotated Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES = eINSTANCE.getRuleSetMetrics_TotalNumberOfAnnotatedAttributes();

		/**
		 * The meta object literal for the '<em><b>Total Number Of NA Cs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS = eINSTANCE.getRuleSetMetrics_TotalNumberOfNACs();

		/**
		 * The meta object literal for the '<em><b>Total Number Of PA Cs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS = eINSTANCE.getRuleSetMetrics_TotalNumberOfPACs();

		/**
		 * The meta object literal for the '<em><b>Find Rule Metrics</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RULE_SET_METRICS___FIND_RULE_METRICS__RULE = eINSTANCE.getRuleSetMetrics__FindRuleMetrics__Rule();

		/**
		 * The meta object literal for the '<em><b>Create Presentation String</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RULE_SET_METRICS___CREATE_PRESENTATION_STRING = eINSTANCE.getRuleSetMetrics__CreatePresentationString();

		/**
		 * The meta object literal for the '{@link metrics.impl.RuleMetricsImpl <em>Rule Metrics</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see metrics.impl.RuleMetricsImpl
		 * @see metrics.impl.MetricsPackageImpl#getRuleMetrics()
		 * @generated
		 */
		EClass RULE_METRICS = eINSTANCE.getRuleMetrics();

		/**
		 * The meta object literal for the '<em><b>Number Of Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_NODES = eINSTANCE.getRuleMetrics_NumberOfNodes();

		/**
		 * The meta object literal for the '<em><b>Number Of Edges</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_EDGES = eINSTANCE.getRuleMetrics_NumberOfEdges();

		/**
		 * The meta object literal for the '<em><b>Number Of Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_ATTRIBUTES = eINSTANCE.getRuleMetrics_NumberOfAttributes();

		/**
		 * The meta object literal for the '<em><b>Number Of Lhs Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_LHS_NODES = eINSTANCE.getRuleMetrics_NumberOfLhsNodes();

		/**
		 * The meta object literal for the '<em><b>Number Of Lhs Edges</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_LHS_EDGES = eINSTANCE.getRuleMetrics_NumberOfLhsEdges();

		/**
		 * The meta object literal for the '<em><b>Number Of Lhs Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES = eINSTANCE.getRuleMetrics_NumberOfLhsAttributes();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_METRICS__RULE = eINSTANCE.getRuleMetrics_Rule();

		/**
		 * The meta object literal for the '<em><b>Number Of NA Cs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_NA_CS = eINSTANCE.getRuleMetrics_NumberOfNACs();

		/**
		 * The meta object literal for the '<em><b>Number Of PA Cs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_PA_CS = eINSTANCE.getRuleMetrics_NumberOfPACs();

		/**
		 * The meta object literal for the '<em><b>Number Of Annotated Nodes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_ANNOTATED_NODES = eINSTANCE.getRuleMetrics_NumberOfAnnotatedNodes();

		/**
		 * The meta object literal for the '<em><b>Number Of Annotated Edges</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES = eINSTANCE.getRuleMetrics_NumberOfAnnotatedEdges();

		/**
		 * The meta object literal for the '<em><b>Number Of Annotated Attributes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES = eINSTANCE.getRuleMetrics_NumberOfAnnotatedAttributes();

	}

} //MetricsPackage
