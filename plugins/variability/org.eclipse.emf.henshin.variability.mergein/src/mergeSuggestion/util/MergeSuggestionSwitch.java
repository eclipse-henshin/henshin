/**
 */
package mergeSuggestion.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import mergeSuggestion.CloneDetectionResult;
import mergeSuggestion.CloneGroup;
import mergeSuggestion.CloneGroupElement;
import mergeSuggestion.MergeAC;
import mergeSuggestion.MergeNAC;
import mergeSuggestion.MergePAC;
import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see mergeSuggestion.MergeSuggestionPackage
 * @generated
 */
public class MergeSuggestionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MergeSuggestionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSuggestionSwitch() {
		if (modelPackage == null) {
			modelPackage = MergeSuggestionPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION: {
				MergeSuggestion mergeSuggestion = (MergeSuggestion)theEObject;
				T result = caseMergeSuggestion(mergeSuggestion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.MERGE_RULE: {
				MergeRule mergeRule = (MergeRule)theEObject;
				T result = caseMergeRule(mergeRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT: {
				MergeRuleElement mergeRuleElement = (MergeRuleElement)theEObject;
				T result = caseMergeRuleElement(mergeRuleElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.MERGE_NAC: {
				MergeNAC mergeNAC = (MergeNAC)theEObject;
				T result = caseMergeNAC(mergeNAC);
				if (result == null) result = caseMergeAC(mergeNAC);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.MERGE_PAC: {
				MergePAC mergePAC = (MergePAC)theEObject;
				T result = caseMergePAC(mergePAC);
				if (result == null) result = caseMergeAC(mergePAC);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.MERGE_AC: {
				MergeAC mergeAC = (MergeAC)theEObject;
				T result = caseMergeAC(mergeAC);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.CLONE_GROUP: {
				CloneGroup cloneGroup = (CloneGroup)theEObject;
				T result = caseCloneGroup(cloneGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.CLONE_GROUP_ELEMENT: {
				CloneGroupElement cloneGroupElement = (CloneGroupElement)theEObject;
				T result = caseCloneGroupElement(cloneGroupElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT: {
				CloneDetectionResult cloneDetectionResult = (CloneDetectionResult)theEObject;
				T result = caseCloneDetectionResult(cloneDetectionResult);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge Suggestion</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge Suggestion</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeSuggestion(MergeSuggestion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeRule(MergeRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge Rule Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeRuleElement(MergeRuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge NAC</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge NAC</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeNAC(MergeNAC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge PAC</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge PAC</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergePAC(MergePAC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Merge AC</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Merge AC</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMergeAC(MergeAC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Clone Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Clone Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCloneGroup(CloneGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Clone Group Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Clone Group Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCloneGroupElement(CloneGroupElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Clone Detection Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Clone Detection Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCloneDetectionResult(CloneDetectionResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //MergeSuggestionSwitch
