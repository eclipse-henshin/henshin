/**
 */
package org.eclipse.emf.henshin.tests.EGraphTestmodel;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Recursive Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode#getContainedChildren <em>Contained Children</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage#getRecursiveNode()
 * @model kind="class"
 * @generated
 */
public class RecursiveNode extends EObjectImpl implements EObject {
	/**
	 * The cached value of the '{@link #getContainedChildren() <em>Contained Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainedChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<RecursiveNode> containedChildren;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RecursiveNode() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EGraphTestmodelPackage.Literals.RECURSIVE_NODE;
	}

	/**
	 * Returns the value of the '<em><b>Contained Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained Children</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage#getRecursiveNode_ContainedChildren()
	 * @model containment="true"
	 * @generated
	 */
	public EList<RecursiveNode> getContainedChildren() {
		if (containedChildren == null) {
			containedChildren = new EObjectContainmentEList<RecursiveNode>(RecursiveNode.class, this, EGraphTestmodelPackage.RECURSIVE_NODE__CONTAINED_CHILDREN);
		}
		return containedChildren;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EGraphTestmodelPackage.RECURSIVE_NODE__CONTAINED_CHILDREN:
				return ((InternalEList<?>)getContainedChildren()).basicRemove(otherEnd, msgs);
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
			case EGraphTestmodelPackage.RECURSIVE_NODE__CONTAINED_CHILDREN:
				return getContainedChildren();
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
			case EGraphTestmodelPackage.RECURSIVE_NODE__CONTAINED_CHILDREN:
				getContainedChildren().clear();
				getContainedChildren().addAll((Collection<? extends RecursiveNode>)newValue);
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
			case EGraphTestmodelPackage.RECURSIVE_NODE__CONTAINED_CHILDREN:
				getContainedChildren().clear();
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
			case EGraphTestmodelPackage.RECURSIVE_NODE__CONTAINED_CHILDREN:
				return containedChildren != null && !containedChildren.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // RecursiveNode
