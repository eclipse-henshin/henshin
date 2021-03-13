/**
 */
package nestedconstraintmodel.impl;

import java.util.Collection;

import nestedcondition.NestedConstraint;

import nestedconstraintmodel.NestedConstraintModel;
import nestedconstraintmodel.NestedconstraintmodelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Nested Constraint Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link nestedconstraintmodel.impl.NestedConstraintModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link nestedconstraintmodel.impl.NestedConstraintModelImpl#getNestedconstrainmodels <em>Nestedconstrainmodels</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NestedConstraintModelImpl extends MinimalEObjectImpl.Container implements NestedConstraintModel {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNestedconstrainmodels() <em>Nestedconstrainmodels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNestedconstrainmodels()
	 * @generated
	 * @ordered
	 */
	protected EList<NestedConstraint> nestedconstrainmodels;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NestedConstraintModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NestedconstraintmodelPackage.Literals.NESTED_CONSTRAINT_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NestedConstraint> getNestedconstrainmodels() {
		if (nestedconstrainmodels == null) {
			nestedconstrainmodels = new EObjectContainmentEList<NestedConstraint>(NestedConstraint.class, this, NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS);
		}
		return nestedconstrainmodels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS:
				return ((InternalEList<?>)getNestedconstrainmodels()).basicRemove(otherEnd, msgs);
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
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NAME:
				return getName();
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS:
				return getNestedconstrainmodels();
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
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NAME:
				setName((String)newValue);
				return;
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS:
				getNestedconstrainmodels().clear();
				getNestedconstrainmodels().addAll((Collection<? extends NestedConstraint>)newValue);
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
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS:
				getNestedconstrainmodels().clear();
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
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case NestedconstraintmodelPackage.NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS:
				return nestedconstrainmodels != null && !nestedconstrainmodels.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //NestedConstraintModelImpl
