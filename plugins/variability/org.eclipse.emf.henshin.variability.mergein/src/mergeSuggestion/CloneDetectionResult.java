/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clone Detection Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.CloneDetectionResult#getCloneGroups <em>Clone Groups</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getCloneDetectionResult()
 * @model
 * @generated
 */
public interface CloneDetectionResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Clone Groups</b></em>' containment reference list.
	 * The list contents are of type {@link mergeSuggestion.CloneGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clone Groups</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clone Groups</em>' containment reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getCloneDetectionResult_CloneGroups()
	 * @model containment="true"
	 * @generated
	 */
	EList<CloneGroup> getCloneGroups();

} // CloneDetectionResult
