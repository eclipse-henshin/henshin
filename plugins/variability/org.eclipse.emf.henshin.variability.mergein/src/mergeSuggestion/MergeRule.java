/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Merge Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.MergeRule#getMasterRule <em>Master Rule</em>}</li>
 *   <li>{@link mergeSuggestion.MergeRule#getRules <em>Rules</em>}</li>
 *   <li>{@link mergeSuggestion.MergeRule#getElements <em>Elements</em>}</li>
 *   <li>{@link mergeSuggestion.MergeRule#getName <em>Name</em>}</li>
 *   <li>{@link mergeSuggestion.MergeRule#getMergeNacs <em>Merge Nacs</em>}</li>
 *   <li>{@link mergeSuggestion.MergeRule#getMergePacs <em>Merge Pacs</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule()
 * @model
 * @generated
 */
public interface MergeRule extends EObject {
	/**
	 * Returns the value of the '<em><b>Master Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master Rule</em>' reference.
	 * @see #setMasterRule(Rule)
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule_MasterRule()
	 * @model
	 * @generated
	 */
	Rule getMasterRule();

	/**
	 * Sets the value of the '{@link mergeSuggestion.MergeRule#getMasterRule <em>Master Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master Rule</em>' reference.
	 * @see #getMasterRule()
	 * @generated
	 */
	void setMasterRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule_Rules()
	 * @model
	 * @generated
	 */
	EList<Rule> getRules();

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link mergeSuggestion.MergeRuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<MergeRuleElement> getElements();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule_Name()
	 * @model derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link mergeSuggestion.MergeRule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Merge Nacs</b></em>' containment reference list.
	 * The list contents are of type {@link mergeSuggestion.MergeNAC}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merge Nacs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Merge Nacs</em>' containment reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule_MergeNacs()
	 * @model containment="true"
	 * @generated
	 */
	EList<MergeNAC> getMergeNacs();

	/**
	 * Returns the value of the '<em><b>Merge Pacs</b></em>' containment reference list.
	 * The list contents are of type {@link mergeSuggestion.MergePAC}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merge Pacs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Merge Pacs</em>' containment reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRule_MergePacs()
	 * @model containment="true"
	 * @generated
	 */
	EList<MergePAC> getMergePacs();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	MergeRuleElement findMergeRuleElement(GraphElement graphElement);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addMergeRuleElement(MergeRuleElement mergeRuleElement);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addMergeNAC(MergeNAC mergeRuleElement);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addMergePAC(MergePAC mergeRuleElement);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	MergeNAC findMergeNAC(Graph graph);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	MergePAC findMergePAC(Graph graph);

} // MergeRule
