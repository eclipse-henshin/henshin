/**
 */
package laxcondition.impl;

import laxcondition.Condition;
import laxcondition.LaxCondition;
import laxcondition.LaxconditionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link laxcondition.impl.ConditionImpl#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link laxcondition.impl.ConditionImpl#getName <em>Name</em>}</li>
 *   <li>{@link laxcondition.impl.ConditionImpl#getLaxCondition <em>Lax Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConditionImpl extends MinimalEObjectImpl.Container implements Condition {
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
	 * The cached value of the '{@link #getLaxCondition() <em>Lax Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLaxCondition()
	 * @generated
	 * @ordered
	 */
	protected LaxCondition laxCondition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LaxconditionPackage.Literals.CONDITION;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LaxconditionPackage.CONDITION__TYPE_GRAPH, oldTypeGraph, typeGraph));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LaxconditionPackage.CONDITION__TYPE_GRAPH, oldTypeGraph, typeGraph));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LaxconditionPackage.CONDITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LaxCondition getLaxCondition() {
		return laxCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLaxCondition(LaxCondition newLaxCondition, NotificationChain msgs) {
		LaxCondition oldLaxCondition = laxCondition;
		laxCondition = newLaxCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LaxconditionPackage.CONDITION__LAX_CONDITION, oldLaxCondition, newLaxCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLaxCondition(LaxCondition newLaxCondition) {
		if (newLaxCondition != laxCondition) {
			NotificationChain msgs = null;
			if (laxCondition != null)
				msgs = ((InternalEObject)laxCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - LaxconditionPackage.CONDITION__LAX_CONDITION, null, msgs);
			if (newLaxCondition != null)
				msgs = ((InternalEObject)newLaxCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - LaxconditionPackage.CONDITION__LAX_CONDITION, null, msgs);
			msgs = basicSetLaxCondition(newLaxCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LaxconditionPackage.CONDITION__LAX_CONDITION, newLaxCondition, newLaxCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case LaxconditionPackage.CONDITION__LAX_CONDITION:
				return basicSetLaxCondition(null, msgs);
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
			case LaxconditionPackage.CONDITION__TYPE_GRAPH:
				if (resolve) return getTypeGraph();
				return basicGetTypeGraph();
			case LaxconditionPackage.CONDITION__NAME:
				return getName();
			case LaxconditionPackage.CONDITION__LAX_CONDITION:
				return getLaxCondition();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case LaxconditionPackage.CONDITION__TYPE_GRAPH:
				setTypeGraph((EPackage)newValue);
				return;
			case LaxconditionPackage.CONDITION__NAME:
				setName((String)newValue);
				return;
			case LaxconditionPackage.CONDITION__LAX_CONDITION:
				setLaxCondition((LaxCondition)newValue);
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
			case LaxconditionPackage.CONDITION__TYPE_GRAPH:
				setTypeGraph((EPackage)null);
				return;
			case LaxconditionPackage.CONDITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case LaxconditionPackage.CONDITION__LAX_CONDITION:
				setLaxCondition((LaxCondition)null);
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
			case LaxconditionPackage.CONDITION__TYPE_GRAPH:
				return typeGraph != null;
			case LaxconditionPackage.CONDITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case LaxconditionPackage.CONDITION__LAX_CONDITION:
				return laxCondition != null;
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

} //ConditionImpl
