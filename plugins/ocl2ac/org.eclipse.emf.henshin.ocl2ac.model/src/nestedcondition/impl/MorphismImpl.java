/**
 */
package nestedcondition.impl;

import graph.Graph;

import java.util.Collection;

import nestedcondition.EdgeMapping;
import nestedcondition.Morphism;
import nestedcondition.NestedconditionPackage;
import nestedcondition.NodeMapping;

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
 * An implementation of the model object '<em><b>Morphism</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.impl.MorphismImpl#getNodeMappings <em>Node Mappings</em>}</li>
 *   <li>{@link nestedcondition.impl.MorphismImpl#getFrom <em>From</em>}</li>
 *   <li>{@link nestedcondition.impl.MorphismImpl#getTo <em>To</em>}</li>
 *   <li>{@link nestedcondition.impl.MorphismImpl#getEdgeMappings <em>Edge Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MorphismImpl extends MinimalEObjectImpl.Container implements Morphism {
	/**
	 * The cached value of the '{@link #getNodeMappings() <em>Node Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeMapping> nodeMappings;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Graph from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Graph to;

	/**
	 * The cached value of the '{@link #getEdgeMappings() <em>Edge Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgeMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<EdgeMapping> edgeMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MorphismImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NestedconditionPackage.Literals.MORPHISM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NodeMapping> getNodeMappings() {
		if (nodeMappings == null) {
			nodeMappings = new EObjectContainmentEList<NodeMapping>(NodeMapping.class, this, NestedconditionPackage.MORPHISM__NODE_MAPPINGS);
		}
		return nodeMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject)from;
			from = (Graph)eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NestedconditionPackage.MORPHISM__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(Graph newFrom) {
		Graph oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.MORPHISM__FROM, oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject)to;
			to = (Graph)eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NestedconditionPackage.MORPHISM__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(Graph newTo) {
		Graph oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NestedconditionPackage.MORPHISM__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EdgeMapping> getEdgeMappings() {
		if (edgeMappings == null) {
			edgeMappings = new EObjectContainmentEList<EdgeMapping>(EdgeMapping.class, this, NestedconditionPackage.MORPHISM__EDGE_MAPPINGS);
		}
		return edgeMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NestedconditionPackage.MORPHISM__NODE_MAPPINGS:
				return ((InternalEList<?>)getNodeMappings()).basicRemove(otherEnd, msgs);
			case NestedconditionPackage.MORPHISM__EDGE_MAPPINGS:
				return ((InternalEList<?>)getEdgeMappings()).basicRemove(otherEnd, msgs);
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
			case NestedconditionPackage.MORPHISM__NODE_MAPPINGS:
				return getNodeMappings();
			case NestedconditionPackage.MORPHISM__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
			case NestedconditionPackage.MORPHISM__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case NestedconditionPackage.MORPHISM__EDGE_MAPPINGS:
				return getEdgeMappings();
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
			case NestedconditionPackage.MORPHISM__NODE_MAPPINGS:
				getNodeMappings().clear();
				getNodeMappings().addAll((Collection<? extends NodeMapping>)newValue);
				return;
			case NestedconditionPackage.MORPHISM__FROM:
				setFrom((Graph)newValue);
				return;
			case NestedconditionPackage.MORPHISM__TO:
				setTo((Graph)newValue);
				return;
			case NestedconditionPackage.MORPHISM__EDGE_MAPPINGS:
				getEdgeMappings().clear();
				getEdgeMappings().addAll((Collection<? extends EdgeMapping>)newValue);
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
			case NestedconditionPackage.MORPHISM__NODE_MAPPINGS:
				getNodeMappings().clear();
				return;
			case NestedconditionPackage.MORPHISM__FROM:
				setFrom((Graph)null);
				return;
			case NestedconditionPackage.MORPHISM__TO:
				setTo((Graph)null);
				return;
			case NestedconditionPackage.MORPHISM__EDGE_MAPPINGS:
				getEdgeMappings().clear();
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
			case NestedconditionPackage.MORPHISM__NODE_MAPPINGS:
				return nodeMappings != null && !nodeMappings.isEmpty();
			case NestedconditionPackage.MORPHISM__FROM:
				return from != null;
			case NestedconditionPackage.MORPHISM__TO:
				return to != null;
			case NestedconditionPackage.MORPHISM__EDGE_MAPPINGS:
				return edgeMappings != null && !edgeMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MorphismImpl
