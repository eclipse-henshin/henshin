/**
 */
package mergeSuggestion.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Rule;

import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Merge Suggestion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.impl.MergeSuggestionImpl#getMergeClusters <em>Merge Clusters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MergeSuggestionImpl extends MinimalEObjectImpl.Container implements MergeSuggestion {
	/**
	 * The cached value of the '{@link #getMergeClusters() <em>Merge Clusters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMergeClusters()
	 * @generated
	 * @ordered
	 */
	protected EList<MergeRule> mergeClusters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MergeSuggestionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MergeSuggestionPackage.Literals.MERGE_SUGGESTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MergeRule> getMergeClusters() {
		if (mergeClusters == null) {
			mergeClusters = new EObjectContainmentEList<MergeRule>(MergeRule.class, this, MergeSuggestionPackage.MERGE_SUGGESTION__MERGE_CLUSTERS);
		}
		return mergeClusters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public MergeRule findMergeRule(Rule rule) {
		for (MergeRule mergeRule : getMergeClusters()) {
			if (mergeRule.getMasterRule() == rule ||
					mergeRule.getRules().contains(rule))
				return mergeRule;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION__MERGE_CLUSTERS:
				return ((InternalEList<?>)getMergeClusters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION__MERGE_CLUSTERS:
				return getMergeClusters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION__MERGE_CLUSTERS:
				getMergeClusters().clear();
				getMergeClusters().addAll((Collection<? extends MergeRule>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION__MERGE_CLUSTERS:
				getMergeClusters().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION__MERGE_CLUSTERS:
				return mergeClusters != null && !mergeClusters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case MergeSuggestionPackage.MERGE_SUGGESTION___FIND_MERGE_RULE__RULE:
				return findMergeRule((Rule)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //MergeSuggestionImpl
