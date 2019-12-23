/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clone Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.CloneGroup#getRules <em>Rules</em>}</li>
 *   <li>{@link mergeSuggestion.CloneGroup#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getCloneGroup()
 * @model
 * @generated
 */
public interface CloneGroup extends EObject {
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
	 * @see mergeSuggestion.MergeSuggestionPackage#getCloneGroup_Rules()
	 * @model
	 * @generated
	 */
	EList<Rule> getRules();

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link mergeSuggestion.CloneGroupElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getCloneGroup_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<CloneGroupElement> getElements();

} // CloneGroup
