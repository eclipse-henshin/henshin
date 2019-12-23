/**
 */
package mergeSuggestion.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import mergeSuggestion.CloneDetectionResult;
import mergeSuggestion.CloneGroup;
import mergeSuggestion.CloneGroupElement;
import mergeSuggestion.MergeAC;
import mergeSuggestion.MergeNAC;
import mergeSuggestion.MergePAC;
import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionFactory;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MergeSuggestionFactoryImpl extends EFactoryImpl implements MergeSuggestionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MergeSuggestionFactory init() {
		try {
			MergeSuggestionFactory theMergeSuggestionFactory = (MergeSuggestionFactory)EPackage.Registry.INSTANCE.getEFactory(MergeSuggestionPackage.eNS_URI);
			if (theMergeSuggestionFactory != null) {
				return theMergeSuggestionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MergeSuggestionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSuggestionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MergeSuggestionPackage.MERGE_SUGGESTION: return createMergeSuggestion();
			case MergeSuggestionPackage.MERGE_RULE: return createMergeRule();
			case MergeSuggestionPackage.MERGE_RULE_ELEMENT: return createMergeRuleElement();
			case MergeSuggestionPackage.MERGE_NAC: return createMergeNAC();
			case MergeSuggestionPackage.MERGE_PAC: return createMergePAC();
			case MergeSuggestionPackage.MERGE_AC: return createMergeAC();
			case MergeSuggestionPackage.CLONE_GROUP: return createCloneGroup();
			case MergeSuggestionPackage.CLONE_GROUP_ELEMENT: return createCloneGroupElement();
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT: return createCloneDetectionResult();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSuggestion createMergeSuggestion() {
		MergeSuggestionImpl mergeSuggestion = new MergeSuggestionImpl();
		return mergeSuggestion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeRule createMergeRule() {
		MergeRuleImpl mergeRule = new MergeRuleImpl();
		return mergeRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeRuleElement createMergeRuleElement() {
		MergeRuleElementImpl mergeRuleElement = new MergeRuleElementImpl();
		return mergeRuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeNAC createMergeNAC() {
		MergeNACImpl mergeNAC = new MergeNACImpl();
		return mergeNAC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergePAC createMergePAC() {
		MergePACImpl mergePAC = new MergePACImpl();
		return mergePAC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeAC createMergeAC() {
		MergeACImpl mergeAC = new MergeACImpl();
		return mergeAC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloneGroup createCloneGroup() {
		CloneGroupImpl cloneGroup = new CloneGroupImpl();
		return cloneGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloneGroupElement createCloneGroupElement() {
		CloneGroupElementImpl cloneGroupElement = new CloneGroupElementImpl();
		return cloneGroupElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloneDetectionResult createCloneDetectionResult() {
		CloneDetectionResultImpl cloneDetectionResult = new CloneDetectionResultImpl();
		return cloneDetectionResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSuggestionPackage getMergeSuggestionPackage() {
		return (MergeSuggestionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MergeSuggestionPackage getPackage() {
		return MergeSuggestionPackage.eINSTANCE;
	}

} //MergeSuggestionFactoryImpl
