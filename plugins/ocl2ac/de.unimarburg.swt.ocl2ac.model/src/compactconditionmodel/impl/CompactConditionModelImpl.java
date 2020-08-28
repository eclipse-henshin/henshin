/**
 */
package compactconditionmodel.impl;

import compactconditionmodel.CompactConditionModel;
import compactconditionmodel.CompactconditionmodelPackage;

import java.util.Collection;

import laxcondition.Condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compact Condition Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compactconditionmodel.impl.CompactConditionModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link compactconditionmodel.impl.CompactConditionModelImpl#getCompactconditions <em>Compactconditions</em>}</li>
 *   <li>{@link compactconditionmodel.impl.CompactConditionModelImpl#getTypeGraph <em>Type Graph</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompactConditionModelImpl extends MinimalEObjectImpl.Container implements CompactConditionModel {
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
	 * The cached value of the '{@link #getCompactconditions() <em>Compactconditions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompactconditions()
	 * @generated
	 * @ordered
	 */
	protected EList<Condition> compactconditions;

	/**
	 * The cached value of the '{@link #getTypeGraph() <em>Type Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeGraph()
	 * @generated
	 * @ordered
	 */
	protected EPackage typeGraph;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompactConditionModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompactconditionmodelPackage.Literals.COMPACT_CONDITION_MODEL;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Condition> getCompactconditions() {
		if (compactconditions == null) {
			compactconditions = new EObjectContainmentEList<Condition>(Condition.class, this, CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__COMPACTCONDITIONS);
		}
		return compactconditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getTypeGraph() {
		if (typeGraph != null && typeGraph.eIsProxy()) {
			InternalEObject oldTypeGraph = (InternalEObject)typeGraph;
			typeGraph = (EPackage)eResolveProxy(oldTypeGraph);
			if (typeGraph != oldTypeGraph) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__TYPE_GRAPH, oldTypeGraph, typeGraph));
			}
		}
		return typeGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetTypeGraph() {
		return typeGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeGraph(EPackage newTypeGraph) {
		EPackage oldTypeGraph = typeGraph;
		typeGraph = newTypeGraph;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__TYPE_GRAPH, oldTypeGraph, typeGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__COMPACTCONDITIONS:
				return ((InternalEList<?>)getCompactconditions()).basicRemove(otherEnd, msgs);
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
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__NAME:
				return getName();
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__COMPACTCONDITIONS:
				return getCompactconditions();
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__TYPE_GRAPH:
				if (resolve) return getTypeGraph();
				return basicGetTypeGraph();
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
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__NAME:
				setName((String)newValue);
				return;
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__COMPACTCONDITIONS:
				getCompactconditions().clear();
				getCompactconditions().addAll((Collection<? extends Condition>)newValue);
				return;
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__TYPE_GRAPH:
				setTypeGraph((EPackage)newValue);
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
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__COMPACTCONDITIONS:
				getCompactconditions().clear();
				return;
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__TYPE_GRAPH:
				setTypeGraph((EPackage)null);
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
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__COMPACTCONDITIONS:
				return compactconditions != null && !compactconditions.isEmpty();
			case CompactconditionmodelPackage.COMPACT_CONDITION_MODEL__TYPE_GRAPH:
				return typeGraph != null;
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

} //CompactConditionModelImpl
