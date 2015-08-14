/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl#getNodeTypes <em>Node Types</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl#getEdgeTypes <em>Edge Types</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.TypeGraphImpl#getConditions <em>Conditions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TypeGraphImpl extends EObjectImpl implements TypeGraph {
	/**
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The cached value of the '{@link #getNodeTypes() <em>Node Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<NodeType> nodeTypes;

	/**
	 * The cached value of the '{@link #getEdgeTypes() <em>Edge Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgeTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<EdgeType> edgeTypes;

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
	 * The cached value of the '{@link #getConditions() <em>Conditions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditions()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeGraphCondition> conditions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeGraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtypegraphPackage.Literals.TYPE_GRAPH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtypegraphPackage.TYPE_GRAPH__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<NodeType> getNodeTypes() {
		if (nodeTypes == null) {
			nodeTypes = new EObjectContainmentWithInverseEList<NodeType>(NodeType.class, this, SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES, SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH);
		}
		return nodeTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EdgeType> getEdgeTypes() {
		if (edgeTypes == null) {
			edgeTypes = new EObjectContainmentWithInverseEList<EdgeType>(EdgeType.class, this, SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES, SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH);
		}
		return edgeTypes;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.TYPE_GRAPH__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TypeGraphCondition> getConditions() {
		if (conditions == null) {
			conditions = new EObjectContainmentEList<TypeGraphCondition>(TypeGraphCondition.class, this, SamtypegraphPackage.TYPE_GRAPH__CONDITIONS);
		}
		return conditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getNodeTypes()).basicAdd(otherEnd, msgs);
			case SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEdgeTypes()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamtypegraphPackage.TYPE_GRAPH__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES:
				return ((InternalEList<?>)getNodeTypes()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES:
				return ((InternalEList<?>)getEdgeTypes()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.TYPE_GRAPH__CONDITIONS:
				return ((InternalEList<?>)getConditions()).basicRemove(otherEnd, msgs);
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
			case SamtypegraphPackage.TYPE_GRAPH__ANNOTATIONS:
				return getAnnotations();
			case SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES:
				return getNodeTypes();
			case SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES:
				return getEdgeTypes();
			case SamtypegraphPackage.TYPE_GRAPH__NAME:
				return getName();
			case SamtypegraphPackage.TYPE_GRAPH__CONDITIONS:
				return getConditions();
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
			case SamtypegraphPackage.TYPE_GRAPH__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES:
				getNodeTypes().clear();
				getNodeTypes().addAll((Collection<? extends NodeType>)newValue);
				return;
			case SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES:
				getEdgeTypes().clear();
				getEdgeTypes().addAll((Collection<? extends EdgeType>)newValue);
				return;
			case SamtypegraphPackage.TYPE_GRAPH__NAME:
				setName((String)newValue);
				return;
			case SamtypegraphPackage.TYPE_GRAPH__CONDITIONS:
				getConditions().clear();
				getConditions().addAll((Collection<? extends TypeGraphCondition>)newValue);
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
			case SamtypegraphPackage.TYPE_GRAPH__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES:
				getNodeTypes().clear();
				return;
			case SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES:
				getEdgeTypes().clear();
				return;
			case SamtypegraphPackage.TYPE_GRAPH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamtypegraphPackage.TYPE_GRAPH__CONDITIONS:
				getConditions().clear();
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
			case SamtypegraphPackage.TYPE_GRAPH__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES:
				return nodeTypes != null && !nodeTypes.isEmpty();
			case SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES:
				return edgeTypes != null && !edgeTypes.isEmpty();
			case SamtypegraphPackage.TYPE_GRAPH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamtypegraphPackage.TYPE_GRAPH__CONDITIONS:
				return conditions != null && !conditions.isEmpty();
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

} //TypeGraphImpl
