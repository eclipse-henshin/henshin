/**
 */
package mergeSuggestion;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see mergeSuggestion.MergeSuggestionPackage
 * @generated
 */
public interface MergeSuggestionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MergeSuggestionFactory eINSTANCE = mergeSuggestion.impl.MergeSuggestionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Merge Suggestion</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Suggestion</em>'.
	 * @generated
	 */
	MergeSuggestion createMergeSuggestion();

	/**
	 * Returns a new object of class '<em>Merge Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Rule</em>'.
	 * @generated
	 */
	MergeRule createMergeRule();

	/**
	 * Returns a new object of class '<em>Merge Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Rule Element</em>'.
	 * @generated
	 */
	MergeRuleElement createMergeRuleElement();

	/**
	 * Returns a new object of class '<em>Merge NAC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge NAC</em>'.
	 * @generated
	 */
	MergeNAC createMergeNAC();

	/**
	 * Returns a new object of class '<em>Merge PAC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge PAC</em>'.
	 * @generated
	 */
	MergePAC createMergePAC();

	/**
	 * Returns a new object of class '<em>Merge AC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge AC</em>'.
	 * @generated
	 */
	MergeAC createMergeAC();

	/**
	 * Returns a new object of class '<em>Clone Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clone Group</em>'.
	 * @generated
	 */
	CloneGroup createCloneGroup();

	/**
	 * Returns a new object of class '<em>Clone Group Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clone Group Element</em>'.
	 * @generated
	 */
	CloneGroupElement createCloneGroupElement();

	/**
	 * Returns a new object of class '<em>Clone Detection Result</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clone Detection Result</em>'.
	 * @generated
	 */
	CloneDetectionResult createCloneDetectionResult();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MergeSuggestionPackage getMergeSuggestionPackage();

} //MergeSuggestionFactory
