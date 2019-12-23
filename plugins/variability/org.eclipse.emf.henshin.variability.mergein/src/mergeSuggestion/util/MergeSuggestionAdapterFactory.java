/**
 */
package mergeSuggestion.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see mergeSuggestion.MergeSuggestionPackage
 * @generated
 */
public class MergeSuggestionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MergeSuggestionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSuggestionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MergeSuggestionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MergeSuggestionSwitch<Adapter> modelSwitch =
		new MergeSuggestionSwitch<Adapter>() {
			@Override
			public Adapter caseMergeSuggestion(MergeSuggestion object) {
				return createMergeSuggestionAdapter();
			}
			@Override
			public Adapter caseMergeRule(MergeRule object) {
				return createMergeRuleAdapter();
			}
			@Override
			public Adapter caseMergeRuleElement(MergeRuleElement object) {
				return createMergeRuleElementAdapter();
			}
			@Override
			public Adapter caseMergeNAC(MergeNAC object) {
				return createMergeNACAdapter();
			}
			@Override
			public Adapter caseMergePAC(MergePAC object) {
				return createMergePACAdapter();
			}
			@Override
			public Adapter caseMergeAC(MergeAC object) {
				return createMergeACAdapter();
			}
			@Override
			public Adapter caseCloneGroup(CloneGroup object) {
				return createCloneGroupAdapter();
			}
			@Override
			public Adapter caseCloneGroupElement(CloneGroupElement object) {
				return createCloneGroupElementAdapter();
			}
			@Override
			public Adapter caseCloneDetectionResult(CloneDetectionResult object) {
				return createCloneDetectionResultAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.MergeSuggestion <em>Merge Suggestion</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.MergeSuggestion
	 * @generated
	 */
	public Adapter createMergeSuggestionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.MergeRule <em>Merge Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.MergeRule
	 * @generated
	 */
	public Adapter createMergeRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.MergeRuleElement <em>Merge Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.MergeRuleElement
	 * @generated
	 */
	public Adapter createMergeRuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.MergeNAC <em>Merge NAC</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.MergeNAC
	 * @generated
	 */
	public Adapter createMergeNACAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.MergePAC <em>Merge PAC</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.MergePAC
	 * @generated
	 */
	public Adapter createMergePACAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.MergeAC <em>Merge AC</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.MergeAC
	 * @generated
	 */
	public Adapter createMergeACAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.CloneGroup <em>Clone Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.CloneGroup
	 * @generated
	 */
	public Adapter createCloneGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.CloneGroupElement <em>Clone Group Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.CloneGroupElement
	 * @generated
	 */
	public Adapter createCloneGroupElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link mergeSuggestion.CloneDetectionResult <em>Clone Detection Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see mergeSuggestion.CloneDetectionResult
	 * @generated
	 */
	public Adapter createCloneDetectionResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //MergeSuggestionAdapterFactory
