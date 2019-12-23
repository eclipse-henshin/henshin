/**
 */
package mergeSuggestion.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import mergeSuggestion.CloneDetectionResult;
import mergeSuggestion.CloneGroup;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Clone Detection Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mergeSuggestion.impl.CloneDetectionResultImpl#getCloneGroups <em>Clone Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CloneDetectionResultImpl extends MinimalEObjectImpl.Container implements CloneDetectionResult {
	/**
	 * The cached value of the '{@link #getCloneGroups() <em>Clone Groups</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCloneGroups()
	 * @generated
	 * @ordered
	 */
	protected EList<CloneGroup> cloneGroups;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CloneDetectionResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MergeSuggestionPackage.Literals.CLONE_DETECTION_RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CloneGroup> getCloneGroups() {
		if (cloneGroups == null) {
			cloneGroups = new EObjectContainmentEList<CloneGroup>(CloneGroup.class, this, MergeSuggestionPackage.CLONE_DETECTION_RESULT__CLONE_GROUPS);
		}
		return cloneGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT__CLONE_GROUPS:
				return ((InternalEList<?>)getCloneGroups()).basicRemove(otherEnd, msgs);
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
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT__CLONE_GROUPS:
				return getCloneGroups();
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
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT__CLONE_GROUPS:
				getCloneGroups().clear();
				getCloneGroups().addAll((Collection<? extends CloneGroup>)newValue);
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
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT__CLONE_GROUPS:
				getCloneGroups().clear();
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
			case MergeSuggestionPackage.CLONE_DETECTION_RESULT__CLONE_GROUPS:
				return cloneGroups != null && !cloneGroups.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CloneDetectionResultImpl
