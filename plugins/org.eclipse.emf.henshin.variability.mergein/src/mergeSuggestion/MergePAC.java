/**
 */
package mergeSuggestion;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Merge PAC</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.MergePAC#getName <em>Name</em>}</li>
 *   <li>{@link mergeSuggestion.MergePAC#getReferencePACs <em>Reference PA Cs</em>}</li>
 * </ul>
 *
 * @see mergeSuggestion.MergeSuggestionPackage#getMergePAC()
 * @model
 * @generated
 */
public interface MergePAC extends MergeAC {
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
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergePAC_Name()
	 * @model derived="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link mergeSuggestion.MergePAC#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Reference PA Cs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Graph}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference PA Cs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference PA Cs</em>' reference list.
	 * @see mergeSuggestion.MergeSuggestionPackage#getMergePAC_ReferencePACs()
	 * @model
	 * @generated
	 */
	EList<Graph> getReferencePACs();

} // MergePAC
