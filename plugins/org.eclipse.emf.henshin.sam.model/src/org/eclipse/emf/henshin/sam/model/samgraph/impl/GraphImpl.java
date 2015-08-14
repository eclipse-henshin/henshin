/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.GraphImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.GraphImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.GraphImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.GraphImpl#getTypedOver <em>Typed Over</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.GraphImpl#getAttributeCondition <em>Attribute Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GraphImpl extends EObjectImpl implements Graph {
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
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> nodes;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> edges;

	/**
	 * The cached value of the '{@link #getTypedOver() <em>Typed Over</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTypedOver()
	 * @generated
	 * @ordered
	 */
	protected TypeGraph typedOver;

	/**
	 * The cached value of the '{@link #getAttributeCondition() <em>Attribute Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeCondition()
	 * @generated
	 * @ordered
	 */
	protected OperationUse attributeCondition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamgraphPackage.Literals.GRAPH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamgraphPackage.GRAPH__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<Node>(Node.class, this, SamgraphPackage.GRAPH__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getEdges() {
		if (edges == null) {
			edges = new EObjectContainmentEList<Edge>(Edge.class, this, SamgraphPackage.GRAPH__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeGraph getTypedOver() {
		if (typedOver != null && typedOver.eIsProxy()) {
			InternalEObject oldTypedOver = (InternalEObject)typedOver;
			typedOver = (TypeGraph)eResolveProxy(oldTypedOver);
			if (typedOver != oldTypedOver) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamgraphPackage.GRAPH__TYPED_OVER, oldTypedOver, typedOver));
			}
		}
		return typedOver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeGraph basicGetTypedOver() {
		return typedOver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypedOver(TypeGraph newTypedOver) {
		TypeGraph oldTypedOver = typedOver;
		typedOver = newTypedOver;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphPackage.GRAPH__TYPED_OVER, oldTypedOver, typedOver));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationUse getAttributeCondition() {
		return attributeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeCondition(OperationUse newAttributeCondition, NotificationChain msgs) {
		OperationUse oldAttributeCondition = attributeCondition;
		attributeCondition = newAttributeCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION, oldAttributeCondition, newAttributeCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributeCondition(OperationUse newAttributeCondition) {
		if (newAttributeCondition != attributeCondition) {
			NotificationChain msgs = null;
			if (attributeCondition != null)
				msgs = ((InternalEObject)attributeCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION, null, msgs);
			if (newAttributeCondition != null)
				msgs = ((InternalEObject)newAttributeCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION, null, msgs);
			msgs = basicSetAttributeCondition(newAttributeCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION, newAttributeCondition, newAttributeCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamgraphPackage.GRAPH__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamgraphPackage.GRAPH__NODES:
				return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
			case SamgraphPackage.GRAPH__EDGES:
				return ((InternalEList<?>)getEdges()).basicRemove(otherEnd, msgs);
			case SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION:
				return basicSetAttributeCondition(null, msgs);
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
			case SamgraphPackage.GRAPH__ANNOTATIONS:
				return getAnnotations();
			case SamgraphPackage.GRAPH__NODES:
				return getNodes();
			case SamgraphPackage.GRAPH__EDGES:
				return getEdges();
			case SamgraphPackage.GRAPH__TYPED_OVER:
				if (resolve) return getTypedOver();
				return basicGetTypedOver();
			case SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION:
				return getAttributeCondition();
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
			case SamgraphPackage.GRAPH__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamgraphPackage.GRAPH__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends Node>)newValue);
				return;
			case SamgraphPackage.GRAPH__EDGES:
				getEdges().clear();
				getEdges().addAll((Collection<? extends Edge>)newValue);
				return;
			case SamgraphPackage.GRAPH__TYPED_OVER:
				setTypedOver((TypeGraph)newValue);
				return;
			case SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION:
				setAttributeCondition((OperationUse)newValue);
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
			case SamgraphPackage.GRAPH__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamgraphPackage.GRAPH__NODES:
				getNodes().clear();
				return;
			case SamgraphPackage.GRAPH__EDGES:
				getEdges().clear();
				return;
			case SamgraphPackage.GRAPH__TYPED_OVER:
				setTypedOver((TypeGraph)null);
				return;
			case SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION:
				setAttributeCondition((OperationUse)null);
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
			case SamgraphPackage.GRAPH__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamgraphPackage.GRAPH__NODES:
				return nodes != null && !nodes.isEmpty();
			case SamgraphPackage.GRAPH__EDGES:
				return edges != null && !edges.isEmpty();
			case SamgraphPackage.GRAPH__TYPED_OVER:
				return typedOver != null;
			case SamgraphPackage.GRAPH__ATTRIBUTE_CONDITION:
				return attributeCondition != null;
		}
		return super.eIsSet(featureID);
	}

} //GraphImpl
