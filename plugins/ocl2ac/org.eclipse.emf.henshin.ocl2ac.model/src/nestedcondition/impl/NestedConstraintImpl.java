/**
 */
package nestedcondition.impl;

import graph.Graph;

import nestedcondition.NestedCondition;
import nestedcondition.NestedConstraint;
import nestedcondition.NestedconditionPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Nested Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.impl.NestedConstraintImpl#getName <em>Name</em>}</li>
 *   <li>{@link nestedcondition.impl.NestedConstraintImpl#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link nestedcondition.impl.NestedConstraintImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link nestedcondition.impl.NestedConstraintImpl#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NestedConstraintImpl extends MinimalEObjectImpl.Container implements NestedConstraint {
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
	 * The cached value of the '{@link #getTypeGraph() <em>Type Graph</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypeGraph()
	 * @generated
	 * @ordered
	 */
	protected EPackage typeGraph;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected NestedCondition condition;

	/**
	 * The cached value of the '{@link #getDomain() <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomain()
	 * @generated
	 * @ordered
	 */
	protected Graph domain;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NestedConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NestedconditionPackage.Literals.NESTED_CONSTRAINT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.NESTED_CONSTRAINT__NAME, oldName, name));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NestedconditionPackage.NESTED_CONSTRAINT__TYPE_GRAPH, oldTypeGraph, typeGraph));
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
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.NESTED_CONSTRAINT__TYPE_GRAPH, oldTypeGraph, typeGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedCondition getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(NestedCondition newCondition, NotificationChain msgs) {
		NestedCondition oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NestedconditionPackage.NESTED_CONSTRAINT__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(NestedCondition newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NestedconditionPackage.NESTED_CONSTRAINT__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NestedconditionPackage.NESTED_CONSTRAINT__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.NESTED_CONSTRAINT__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getDomain() {
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomain(Graph newDomain, NotificationChain msgs) {
		Graph oldDomain = domain;
		domain = newDomain;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN, oldDomain, newDomain);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(Graph newDomain) {
		if (newDomain != domain) {
			NotificationChain msgs = null;
			if (domain != null)
				msgs = ((InternalEObject)domain).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN, null, msgs);
			if (newDomain != null)
				msgs = ((InternalEObject)newDomain).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN, null, msgs);
			msgs = basicSetDomain(newDomain, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN, newDomain, newDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NestedconditionPackage.NESTED_CONSTRAINT__CONDITION:
				return basicSetCondition(null, msgs);
			case NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN:
				return basicSetDomain(null, msgs);
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
			case NestedconditionPackage.NESTED_CONSTRAINT__NAME:
				return getName();
			case NestedconditionPackage.NESTED_CONSTRAINT__TYPE_GRAPH:
				if (resolve) return getTypeGraph();
				return basicGetTypeGraph();
			case NestedconditionPackage.NESTED_CONSTRAINT__CONDITION:
				return getCondition();
			case NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN:
				return getDomain();
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
			case NestedconditionPackage.NESTED_CONSTRAINT__NAME:
				setName((String)newValue);
				return;
			case NestedconditionPackage.NESTED_CONSTRAINT__TYPE_GRAPH:
				setTypeGraph((EPackage)newValue);
				return;
			case NestedconditionPackage.NESTED_CONSTRAINT__CONDITION:
				setCondition((NestedCondition)newValue);
				return;
			case NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN:
				setDomain((Graph)newValue);
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
			case NestedconditionPackage.NESTED_CONSTRAINT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case NestedconditionPackage.NESTED_CONSTRAINT__TYPE_GRAPH:
				setTypeGraph((EPackage)null);
				return;
			case NestedconditionPackage.NESTED_CONSTRAINT__CONDITION:
				setCondition((NestedCondition)null);
				return;
			case NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN:
				setDomain((Graph)null);
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
			case NestedconditionPackage.NESTED_CONSTRAINT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case NestedconditionPackage.NESTED_CONSTRAINT__TYPE_GRAPH:
				return typeGraph != null;
			case NestedconditionPackage.NESTED_CONSTRAINT__CONDITION:
				return condition != null;
			case NestedconditionPackage.NESTED_CONSTRAINT__DOMAIN:
				return domain != null;
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

} //NestedConstraintImpl
