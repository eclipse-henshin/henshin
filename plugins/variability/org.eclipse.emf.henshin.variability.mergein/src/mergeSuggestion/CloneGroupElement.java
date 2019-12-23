/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.GraphElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clone Group Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.CloneGroupElement#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getCloneGroupElement()
 * @model
 * @generated
 */
public interface CloneGroupElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.GraphElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getCloneGroupElement_Elements()
	 * @model
	 * @generated
	 */
	EList<GraphElement> getElements();

} // CloneGroupElement
