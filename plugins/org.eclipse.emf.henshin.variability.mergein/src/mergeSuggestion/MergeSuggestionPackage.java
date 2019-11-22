/**
 */
package mergeSuggestion;

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
 * @see mergeSuggestion.MergeSuggestionFactory
 * @model kind="package"
 * @generated
 */
public interface MergeSuggestionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mergeSuggestion";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://mergeSuggestion";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "mergeSuggestion";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MergeSuggestionPackage eINSTANCE = mergeSuggestion.impl.MergeSuggestionPackageImpl.init();

	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.MergeSuggestionImpl <em>Merge Suggestion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.MergeSuggestionImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeSuggestion()
	 * @generated
	 */
	int MERGE_SUGGESTION = 0;

	/**
	 * The feature id for the '<em><b>Merge Clusters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SUGGESTION__MERGE_CLUSTERS = 0;

	/**
	 * The number of structural features of the '<em>Merge Suggestion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SUGGESTION_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Find Merge Rule</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SUGGESTION___FIND_MERGE_RULE__RULE = 0;

	/**
	 * The number of operations of the '<em>Merge Suggestion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_SUGGESTION_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.MergeRuleImpl <em>Merge Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.MergeRuleImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeRule()
	 * @generated
	 */
	int MERGE_RULE = 1;

	/**
	 * The feature id for the '<em><b>Master Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE__MASTER_RULE = 0;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE__RULES = 1;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE__ELEMENTS = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE__NAME = 3;

	/**
	 * The feature id for the '<em><b>Merge Nacs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE__MERGE_NACS = 4;

	/**
	 * The feature id for the '<em><b>Merge Pacs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE__MERGE_PACS = 5;

	/**
	 * The number of structural features of the '<em>Merge Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_FEATURE_COUNT = 6;

	/**
	 * The operation id for the '<em>Find Merge Rule Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE___FIND_MERGE_RULE_ELEMENT__GRAPHELEMENT = 0;

	/**
	 * The operation id for the '<em>Add Merge Rule Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE___ADD_MERGE_RULE_ELEMENT__MERGERULEELEMENT = 1;

	/**
	 * The operation id for the '<em>Add Merge NAC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE___ADD_MERGE_NAC__MERGENAC = 2;

	/**
	 * The operation id for the '<em>Add Merge PAC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE___ADD_MERGE_PAC__MERGEPAC = 3;

	/**
	 * The operation id for the '<em>Find Merge NAC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE___FIND_MERGE_NAC__GRAPH = 4;

	/**
	 * The operation id for the '<em>Find Merge PAC</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE___FIND_MERGE_PAC__GRAPH = 5;

	/**
	 * The number of operations of the '<em>Merge Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_OPERATION_COUNT = 6;

	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.MergeRuleElementImpl <em>Merge Rule Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.MergeRuleElementImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeRuleElement()
	 * @generated
	 */
	int MERGE_RULE_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Reference Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT__NAME = 1;

	/**
	 * The number of structural features of the '<em>Merge Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Is Nac Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT___IS_NAC_ELEMENT = 0;

	/**
	 * The operation id for the '<em>Is Pac Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT___IS_PAC_ELEMENT = 1;

	/**
	 * The operation id for the '<em>Is Lhs Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT___IS_LHS_ELEMENT = 2;

	/**
	 * The operation id for the '<em>Is Rhs Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT___IS_RHS_ELEMENT = 3;

	/**
	 * The operation id for the '<em>Is Multi Rule Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT___IS_MULTI_RULE_ELEMENT = 4;

	/**
	 * The number of operations of the '<em>Merge Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_RULE_ELEMENT_OPERATION_COUNT = 5;


	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.MergeACImpl <em>Merge AC</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.MergeACImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeAC()
	 * @generated
	 */
	int MERGE_AC = 5;

	/**
	 * The number of structural features of the '<em>Merge AC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_AC_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Merge AC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_AC_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.MergeNACImpl <em>Merge NAC</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.MergeNACImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeNAC()
	 * @generated
	 */
	int MERGE_NAC = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NAC__NAME = MERGE_AC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reference NA Cs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NAC__REFERENCE_NA_CS = MERGE_AC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Merge NAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NAC_FEATURE_COUNT = MERGE_AC_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Merge NAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NAC_OPERATION_COUNT = MERGE_AC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.MergePACImpl <em>Merge PAC</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.MergePACImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergePAC()
	 * @generated
	 */
	int MERGE_PAC = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_PAC__NAME = MERGE_AC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reference PA Cs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_PAC__REFERENCE_PA_CS = MERGE_AC_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Merge PAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_PAC_FEATURE_COUNT = MERGE_AC_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Merge PAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_PAC_OPERATION_COUNT = MERGE_AC_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.CloneGroupImpl <em>Clone Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.CloneGroupImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getCloneGroup()
	 * @generated
	 */
	int CLONE_GROUP = 6;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP__RULES = 0;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP__ELEMENTS = 1;

	/**
	 * The number of structural features of the '<em>Clone Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Clone Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.CloneGroupElementImpl <em>Clone Group Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.CloneGroupElementImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getCloneGroupElement()
	 * @generated
	 */
	int CLONE_GROUP_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP_ELEMENT__ELEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Clone Group Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Clone Group Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_GROUP_ELEMENT_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link mergeSuggestion.impl.CloneDetectionResultImpl <em>Clone Detection Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mergeSuggestion.impl.CloneDetectionResultImpl
	 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getCloneDetectionResult()
	 * @generated
	 */
	int CLONE_DETECTION_RESULT = 8;

	/**
	 * The feature id for the '<em><b>Clone Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_DETECTION_RESULT__CLONE_GROUPS = 0;

	/**
	 * The number of structural features of the '<em>Clone Detection Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_DETECTION_RESULT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Clone Detection Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLONE_DETECTION_RESULT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link mergeSuggestion.MergeSuggestion <em>Merge Suggestion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Suggestion</em>'.
	 * @see mergeSuggestion.MergeSuggestion
	 * @generated
	 */
	EClass getMergeSuggestion();

	/**
	 * Returns the meta object for the containment reference list '{@link mergeSuggestion.MergeSuggestion#getMergeClusters <em>Merge Clusters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Merge Clusters</em>'.
	 * @see mergeSuggestion.MergeSuggestion#getMergeClusters()
	 * @see #getMergeSuggestion()
	 * @generated
	 */
	EReference getMergeSuggestion_MergeClusters();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeSuggestion#findMergeRule(org.eclipse.emf.henshin.model.Rule) <em>Find Merge Rule</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Find Merge Rule</em>' operation.
	 * @see mergeSuggestion.MergeSuggestion#findMergeRule(org.eclipse.emf.henshin.model.Rule)
	 * @generated
	 */
	EOperation getMergeSuggestion__FindMergeRule__Rule();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.MergeRule <em>Merge Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Rule</em>'.
	 * @see mergeSuggestion.MergeRule
	 * @generated
	 */
	EClass getMergeRule();

	/**
	 * Returns the meta object for the reference '{@link mergeSuggestion.MergeRule#getMasterRule <em>Master Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Master Rule</em>'.
	 * @see mergeSuggestion.MergeRule#getMasterRule()
	 * @see #getMergeRule()
	 * @generated
	 */
	EReference getMergeRule_MasterRule();

	/**
	 * Returns the meta object for the reference list '{@link mergeSuggestion.MergeRule#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rules</em>'.
	 * @see mergeSuggestion.MergeRule#getRules()
	 * @see #getMergeRule()
	 * @generated
	 */
	EReference getMergeRule_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link mergeSuggestion.MergeRule#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see mergeSuggestion.MergeRule#getElements()
	 * @see #getMergeRule()
	 * @generated
	 */
	EReference getMergeRule_Elements();

	/**
	 * Returns the meta object for the attribute '{@link mergeSuggestion.MergeRule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see mergeSuggestion.MergeRule#getName()
	 * @see #getMergeRule()
	 * @generated
	 */
	EAttribute getMergeRule_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link mergeSuggestion.MergeRule#getMergeNacs <em>Merge Nacs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Merge Nacs</em>'.
	 * @see mergeSuggestion.MergeRule#getMergeNacs()
	 * @see #getMergeRule()
	 * @generated
	 */
	EReference getMergeRule_MergeNacs();

	/**
	 * Returns the meta object for the containment reference list '{@link mergeSuggestion.MergeRule#getMergePacs <em>Merge Pacs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Merge Pacs</em>'.
	 * @see mergeSuggestion.MergeRule#getMergePacs()
	 * @see #getMergeRule()
	 * @generated
	 */
	EReference getMergeRule_MergePacs();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRule#findMergeRuleElement(org.eclipse.emf.henshin.model.GraphElement) <em>Find Merge Rule Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Find Merge Rule Element</em>' operation.
	 * @see mergeSuggestion.MergeRule#findMergeRuleElement(org.eclipse.emf.henshin.model.GraphElement)
	 * @generated
	 */
	EOperation getMergeRule__FindMergeRuleElement__GraphElement();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRule#addMergeRuleElement(mergeSuggestion.MergeRuleElement) <em>Add Merge Rule Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Merge Rule Element</em>' operation.
	 * @see mergeSuggestion.MergeRule#addMergeRuleElement(mergeSuggestion.MergeRuleElement)
	 * @generated
	 */
	EOperation getMergeRule__AddMergeRuleElement__MergeRuleElement();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRule#addMergeNAC(mergeSuggestion.MergeNAC) <em>Add Merge NAC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Merge NAC</em>' operation.
	 * @see mergeSuggestion.MergeRule#addMergeNAC(mergeSuggestion.MergeNAC)
	 * @generated
	 */
	EOperation getMergeRule__AddMergeNAC__MergeNAC();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRule#addMergePAC(mergeSuggestion.MergePAC) <em>Add Merge PAC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Add Merge PAC</em>' operation.
	 * @see mergeSuggestion.MergeRule#addMergePAC(mergeSuggestion.MergePAC)
	 * @generated
	 */
	EOperation getMergeRule__AddMergePAC__MergePAC();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRule#findMergeNAC(org.eclipse.emf.henshin.model.Graph) <em>Find Merge NAC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Find Merge NAC</em>' operation.
	 * @see mergeSuggestion.MergeRule#findMergeNAC(org.eclipse.emf.henshin.model.Graph)
	 * @generated
	 */
	EOperation getMergeRule__FindMergeNAC__Graph();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRule#findMergePAC(org.eclipse.emf.henshin.model.Graph) <em>Find Merge PAC</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Find Merge PAC</em>' operation.
	 * @see mergeSuggestion.MergeRule#findMergePAC(org.eclipse.emf.henshin.model.Graph)
	 * @generated
	 */
	EOperation getMergeRule__FindMergePAC__Graph();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.MergeRuleElement <em>Merge Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Rule Element</em>'.
	 * @see mergeSuggestion.MergeRuleElement
	 * @generated
	 */
	EClass getMergeRuleElement();

	/**
	 * Returns the meta object for the reference list '{@link mergeSuggestion.MergeRuleElement#getReferenceElements <em>Reference Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reference Elements</em>'.
	 * @see mergeSuggestion.MergeRuleElement#getReferenceElements()
	 * @see #getMergeRuleElement()
	 * @generated
	 */
	EReference getMergeRuleElement_ReferenceElements();

	/**
	 * Returns the meta object for the attribute '{@link mergeSuggestion.MergeRuleElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see mergeSuggestion.MergeRuleElement#getName()
	 * @see #getMergeRuleElement()
	 * @generated
	 */
	EAttribute getMergeRuleElement_Name();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRuleElement#isNacElement() <em>Is Nac Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Nac Element</em>' operation.
	 * @see mergeSuggestion.MergeRuleElement#isNacElement()
	 * @generated
	 */
	EOperation getMergeRuleElement__IsNacElement();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRuleElement#isPacElement() <em>Is Pac Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Pac Element</em>' operation.
	 * @see mergeSuggestion.MergeRuleElement#isPacElement()
	 * @generated
	 */
	EOperation getMergeRuleElement__IsPacElement();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRuleElement#isLhsElement() <em>Is Lhs Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Lhs Element</em>' operation.
	 * @see mergeSuggestion.MergeRuleElement#isLhsElement()
	 * @generated
	 */
	EOperation getMergeRuleElement__IsLhsElement();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRuleElement#isRhsElement() <em>Is Rhs Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Rhs Element</em>' operation.
	 * @see mergeSuggestion.MergeRuleElement#isRhsElement()
	 * @generated
	 */
	EOperation getMergeRuleElement__IsRhsElement();

	/**
	 * Returns the meta object for the '{@link mergeSuggestion.MergeRuleElement#isMultiRuleElement() <em>Is Multi Rule Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Is Multi Rule Element</em>' operation.
	 * @see mergeSuggestion.MergeRuleElement#isMultiRuleElement()
	 * @generated
	 */
	EOperation getMergeRuleElement__IsMultiRuleElement();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.MergeNAC <em>Merge NAC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge NAC</em>'.
	 * @see mergeSuggestion.MergeNAC
	 * @generated
	 */
	EClass getMergeNAC();

	/**
	 * Returns the meta object for the attribute '{@link mergeSuggestion.MergeNAC#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see mergeSuggestion.MergeNAC#getName()
	 * @see #getMergeNAC()
	 * @generated
	 */
	EAttribute getMergeNAC_Name();

	/**
	 * Returns the meta object for the reference list '{@link mergeSuggestion.MergeNAC#getReferenceNACs <em>Reference NA Cs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reference NA Cs</em>'.
	 * @see mergeSuggestion.MergeNAC#getReferenceNACs()
	 * @see #getMergeNAC()
	 * @generated
	 */
	EReference getMergeNAC_ReferenceNACs();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.MergePAC <em>Merge PAC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge PAC</em>'.
	 * @see mergeSuggestion.MergePAC
	 * @generated
	 */
	EClass getMergePAC();

	/**
	 * Returns the meta object for the attribute '{@link mergeSuggestion.MergePAC#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see mergeSuggestion.MergePAC#getName()
	 * @see #getMergePAC()
	 * @generated
	 */
	EAttribute getMergePAC_Name();

	/**
	 * Returns the meta object for the reference list '{@link mergeSuggestion.MergePAC#getReferencePACs <em>Reference PA Cs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reference PA Cs</em>'.
	 * @see mergeSuggestion.MergePAC#getReferencePACs()
	 * @see #getMergePAC()
	 * @generated
	 */
	EReference getMergePAC_ReferencePACs();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.MergeAC <em>Merge AC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge AC</em>'.
	 * @see mergeSuggestion.MergeAC
	 * @generated
	 */
	EClass getMergeAC();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.CloneGroup <em>Clone Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clone Group</em>'.
	 * @see mergeSuggestion.CloneGroup
	 * @generated
	 */
	EClass getCloneGroup();

	/**
	 * Returns the meta object for the reference list '{@link mergeSuggestion.CloneGroup#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rules</em>'.
	 * @see mergeSuggestion.CloneGroup#getRules()
	 * @see #getCloneGroup()
	 * @generated
	 */
	EReference getCloneGroup_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link mergeSuggestion.CloneGroup#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see mergeSuggestion.CloneGroup#getElements()
	 * @see #getCloneGroup()
	 * @generated
	 */
	EReference getCloneGroup_Elements();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.CloneGroupElement <em>Clone Group Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clone Group Element</em>'.
	 * @see mergeSuggestion.CloneGroupElement
	 * @generated
	 */
	EClass getCloneGroupElement();

	/**
	 * Returns the meta object for the reference list '{@link mergeSuggestion.CloneGroupElement#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Elements</em>'.
	 * @see mergeSuggestion.CloneGroupElement#getElements()
	 * @see #getCloneGroupElement()
	 * @generated
	 */
	EReference getCloneGroupElement_Elements();

	/**
	 * Returns the meta object for class '{@link mergeSuggestion.CloneDetectionResult <em>Clone Detection Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clone Detection Result</em>'.
	 * @see mergeSuggestion.CloneDetectionResult
	 * @generated
	 */
	EClass getCloneDetectionResult();

	/**
	 * Returns the meta object for the containment reference list '{@link mergeSuggestion.CloneDetectionResult#getCloneGroups <em>Clone Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Clone Groups</em>'.
	 * @see mergeSuggestion.CloneDetectionResult#getCloneGroups()
	 * @see #getCloneDetectionResult()
	 * @generated
	 */
	EReference getCloneDetectionResult_CloneGroups();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MergeSuggestionFactory getMergeSuggestionFactory();

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
		 * The meta object literal for the '{@link mergeSuggestion.impl.MergeSuggestionImpl <em>Merge Suggestion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.MergeSuggestionImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeSuggestion()
		 * @generated
		 */
		EClass MERGE_SUGGESTION = eINSTANCE.getMergeSuggestion();

		/**
		 * The meta object literal for the '<em><b>Merge Clusters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_SUGGESTION__MERGE_CLUSTERS = eINSTANCE.getMergeSuggestion_MergeClusters();

		/**
		 * The meta object literal for the '<em><b>Find Merge Rule</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_SUGGESTION___FIND_MERGE_RULE__RULE = eINSTANCE.getMergeSuggestion__FindMergeRule__Rule();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.MergeRuleImpl <em>Merge Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.MergeRuleImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeRule()
		 * @generated
		 */
		EClass MERGE_RULE = eINSTANCE.getMergeRule();

		/**
		 * The meta object literal for the '<em><b>Master Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_RULE__MASTER_RULE = eINSTANCE.getMergeRule_MasterRule();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_RULE__RULES = eINSTANCE.getMergeRule_Rules();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_RULE__ELEMENTS = eINSTANCE.getMergeRule_Elements();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGE_RULE__NAME = eINSTANCE.getMergeRule_Name();

		/**
		 * The meta object literal for the '<em><b>Merge Nacs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_RULE__MERGE_NACS = eINSTANCE.getMergeRule_MergeNacs();

		/**
		 * The meta object literal for the '<em><b>Merge Pacs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_RULE__MERGE_PACS = eINSTANCE.getMergeRule_MergePacs();

		/**
		 * The meta object literal for the '<em><b>Find Merge Rule Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE___FIND_MERGE_RULE_ELEMENT__GRAPHELEMENT = eINSTANCE.getMergeRule__FindMergeRuleElement__GraphElement();

		/**
		 * The meta object literal for the '<em><b>Add Merge Rule Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE___ADD_MERGE_RULE_ELEMENT__MERGERULEELEMENT = eINSTANCE.getMergeRule__AddMergeRuleElement__MergeRuleElement();

		/**
		 * The meta object literal for the '<em><b>Add Merge NAC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE___ADD_MERGE_NAC__MERGENAC = eINSTANCE.getMergeRule__AddMergeNAC__MergeNAC();

		/**
		 * The meta object literal for the '<em><b>Add Merge PAC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE___ADD_MERGE_PAC__MERGEPAC = eINSTANCE.getMergeRule__AddMergePAC__MergePAC();

		/**
		 * The meta object literal for the '<em><b>Find Merge NAC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE___FIND_MERGE_NAC__GRAPH = eINSTANCE.getMergeRule__FindMergeNAC__Graph();

		/**
		 * The meta object literal for the '<em><b>Find Merge PAC</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE___FIND_MERGE_PAC__GRAPH = eINSTANCE.getMergeRule__FindMergePAC__Graph();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.MergeRuleElementImpl <em>Merge Rule Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.MergeRuleElementImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeRuleElement()
		 * @generated
		 */
		EClass MERGE_RULE_ELEMENT = eINSTANCE.getMergeRuleElement();

		/**
		 * The meta object literal for the '<em><b>Reference Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS = eINSTANCE.getMergeRuleElement_ReferenceElements();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGE_RULE_ELEMENT__NAME = eINSTANCE.getMergeRuleElement_Name();

		/**
		 * The meta object literal for the '<em><b>Is Nac Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE_ELEMENT___IS_NAC_ELEMENT = eINSTANCE.getMergeRuleElement__IsNacElement();

		/**
		 * The meta object literal for the '<em><b>Is Pac Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE_ELEMENT___IS_PAC_ELEMENT = eINSTANCE.getMergeRuleElement__IsPacElement();

		/**
		 * The meta object literal for the '<em><b>Is Lhs Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE_ELEMENT___IS_LHS_ELEMENT = eINSTANCE.getMergeRuleElement__IsLhsElement();

		/**
		 * The meta object literal for the '<em><b>Is Rhs Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE_ELEMENT___IS_RHS_ELEMENT = eINSTANCE.getMergeRuleElement__IsRhsElement();

		/**
		 * The meta object literal for the '<em><b>Is Multi Rule Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation MERGE_RULE_ELEMENT___IS_MULTI_RULE_ELEMENT = eINSTANCE.getMergeRuleElement__IsMultiRuleElement();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.MergeNACImpl <em>Merge NAC</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.MergeNACImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeNAC()
		 * @generated
		 */
		EClass MERGE_NAC = eINSTANCE.getMergeNAC();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGE_NAC__NAME = eINSTANCE.getMergeNAC_Name();

		/**
		 * The meta object literal for the '<em><b>Reference NA Cs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_NAC__REFERENCE_NA_CS = eINSTANCE.getMergeNAC_ReferenceNACs();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.MergePACImpl <em>Merge PAC</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.MergePACImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergePAC()
		 * @generated
		 */
		EClass MERGE_PAC = eINSTANCE.getMergePAC();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MERGE_PAC__NAME = eINSTANCE.getMergePAC_Name();

		/**
		 * The meta object literal for the '<em><b>Reference PA Cs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MERGE_PAC__REFERENCE_PA_CS = eINSTANCE.getMergePAC_ReferencePACs();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.MergeACImpl <em>Merge AC</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.MergeACImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getMergeAC()
		 * @generated
		 */
		EClass MERGE_AC = eINSTANCE.getMergeAC();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.CloneGroupImpl <em>Clone Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.CloneGroupImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getCloneGroup()
		 * @generated
		 */
		EClass CLONE_GROUP = eINSTANCE.getCloneGroup();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLONE_GROUP__RULES = eINSTANCE.getCloneGroup_Rules();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLONE_GROUP__ELEMENTS = eINSTANCE.getCloneGroup_Elements();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.CloneGroupElementImpl <em>Clone Group Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.CloneGroupElementImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getCloneGroupElement()
		 * @generated
		 */
		EClass CLONE_GROUP_ELEMENT = eINSTANCE.getCloneGroupElement();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLONE_GROUP_ELEMENT__ELEMENTS = eINSTANCE.getCloneGroupElement_Elements();

		/**
		 * The meta object literal for the '{@link mergeSuggestion.impl.CloneDetectionResultImpl <em>Clone Detection Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mergeSuggestion.impl.CloneDetectionResultImpl
		 * @see mergeSuggestion.impl.MergeSuggestionPackageImpl#getCloneDetectionResult()
		 * @generated
		 */
		EClass CLONE_DETECTION_RESULT = eINSTANCE.getCloneDetectionResult();

		/**
		 * The meta object literal for the '<em><b>Clone Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLONE_DETECTION_RESULT__CLONE_GROUPS = eINSTANCE.getCloneDetectionResult_CloneGroups();

	}

} //MergeSuggestionPackage
