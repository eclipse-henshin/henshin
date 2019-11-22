/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Merge Suggestion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.MergeSuggestion#getMergeClusters <em>Merge Clusters</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getMergeSuggestion()
 * @model
 * @generated
 */
public interface MergeSuggestion extends EObject {
	/**
	 * Returns the value of the '<em><b>Merge Clusters</b></em>' containment reference list.
	 * The list contents are of type {@link mergeSuggestion.MergeRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Merge Clusters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Merge Clusters</em>' containment reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergeSuggestion_MergeClusters()
	 * @model containment="true"
	 * @generated
	 */
	EList<MergeRule> getMergeClusters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	MergeRule findMergeRule(Rule rule);

} // MergeSuggestion
