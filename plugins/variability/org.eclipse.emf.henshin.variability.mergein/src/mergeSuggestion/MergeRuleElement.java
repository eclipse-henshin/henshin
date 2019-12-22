/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.GraphElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Merge Rule Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.MergeRuleElement#getReferenceElements <em>Reference Elements</em>}</li>
 *   <li>{@link mergeSuggestion.MergeRuleElement#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRuleElement()
 * @model
 * @generated
 */
public interface MergeRuleElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Reference Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.GraphElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Elements</em>' reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRuleElement_ReferenceElements()
	 * @model
	 * @generated
	 */
	EList<GraphElement> getReferenceElements();

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
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeRuleElement_Name()
	 * @model derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link mergeSuggestion.MergeRuleElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isNacElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isPacElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isLhsElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isRhsElement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isMultiRuleElement();

} // MergeRuleElement
