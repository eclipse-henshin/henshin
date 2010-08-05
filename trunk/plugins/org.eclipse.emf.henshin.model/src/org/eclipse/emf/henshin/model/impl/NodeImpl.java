/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NodeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NodeImpl#getGraph <em>Graph</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NodeImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NodeImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.NodeImpl#getAllEdges <em>All Edges</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeImpl extends NamedElementImpl implements Node {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClass type;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> incoming;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> outgoing;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (EClass)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.NODE__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EClass newType) {
		EClass oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.NODE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentWithInverseEList<Attribute>(Attribute.class, this, HenshinPackage.NODE__ATTRIBUTES, HenshinPackage.ATTRIBUTE__NODE);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getGraph() {
		if (eContainerFeatureID() != HenshinPackage.NODE__GRAPH) return null;
		return (Graph)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGraph(Graph newGraph, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGraph, HenshinPackage.NODE__GRAPH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraph(Graph newGraph) {
		if (newGraph != eInternalContainer() || (eContainerFeatureID() != HenshinPackage.NODE__GRAPH && newGraph != null)) {
			if (EcoreUtil.isAncestor(this, newGraph))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newGraph != null)
				msgs = ((InternalEObject)newGraph).eInverseAdd(this, HenshinPackage.GRAPH__NODES, Graph.class, msgs);
			msgs = basicSetGraph(newGraph, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.NODE__GRAPH, newGraph, newGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Edge>(Edge.class, this, HenshinPackage.NODE__INCOMING, HenshinPackage.EDGE__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<Edge>(Edge.class, this, HenshinPackage.NODE__OUTGOING, HenshinPackage.EDGE__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList<Edge> getAllEdges() {
		/*
		 * This implementation may be optimized by introducing a private global
		 * variable for this return value. This variable should be set to "null"
		 * only when outgoing/incoming change.
		 */
		List<Edge> allEdges = new ArrayList<Edge>();
		allEdges.addAll(this.getIncoming());
		allEdges.addAll(this.getOutgoing());
		
		return new EcoreEList.UnmodifiableEList<Edge>(this,
				HenshinPackage.Literals.NODE__ALL_EDGES, allEdges.size(),
				allEdges.toArray());
	}// getAllEdges

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<Edge> findOutgoingEdgesByType(EReference edgeType) {
		
		List<Edge> edges = new ArrayList<Edge>();
		for (Edge edge : this.getOutgoing()) {
			if (edgeType == edge.getType())
				edges.add(edge);
		}// for

		return new BasicEList.UnmodifiableEList<Edge>(edges.size(), edges
				.toArray());		
	}// findOutgoingEdgesByType

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<Edge> findIncomingEdgesByType(EReference edgeType) {

		List<Edge> edges = new ArrayList<Edge>();
		for (Edge edge : this.getIncoming()) {
			if (edgeType == edge.getType())
				edges.add(edge);
		}// for
		
		return new BasicEList.UnmodifiableEList<Edge>(edges.size(), edges
				.toArray());
	}// findIncomingEdgesByType

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Attribute findAttributeByType(EAttribute attributeType) {

		for (Attribute attribute : this.getAttributes()) {
			if (attribute.getType() == attributeType)
				return attribute;
		}//for
		return null;
	}// findAttributeByType

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Edge findOutgoingEdgeByType(Node targetNode, EReference edgeType) {

		for (Edge edge : this.getOutgoing()) {
			if (edgeType == edge.getType() && targetNode == edge.getTarget())
				return edge;
		}// for
		return null;
	}// findOutgoingEdgeByType

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Edge findIncomingEdgeByType(Node sourceNode, EReference edgeType) {

		for (Edge edge : this.getIncoming()) {
			if (edgeType == edge.getType() && sourceNode == edge.getSource())
				return edge;
		}// for
		return null;
	}// findIncomingEdgeByType

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.NODE__ATTRIBUTES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributes()).basicAdd(otherEnd, msgs);
			case HenshinPackage.NODE__GRAPH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetGraph((Graph)otherEnd, msgs);
			case HenshinPackage.NODE__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case HenshinPackage.NODE__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
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
			case HenshinPackage.NODE__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case HenshinPackage.NODE__GRAPH:
				return basicSetGraph(null, msgs);
			case HenshinPackage.NODE__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case HenshinPackage.NODE__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case HenshinPackage.NODE__GRAPH:
				return eInternalContainer().eInverseRemove(this, HenshinPackage.GRAPH__NODES, Graph.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case HenshinPackage.NODE__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case HenshinPackage.NODE__ATTRIBUTES:
				return getAttributes();
			case HenshinPackage.NODE__GRAPH:
				return getGraph();
			case HenshinPackage.NODE__INCOMING:
				return getIncoming();
			case HenshinPackage.NODE__OUTGOING:
				return getOutgoing();
			case HenshinPackage.NODE__ALL_EDGES:
				return getAllEdges();
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
			case HenshinPackage.NODE__TYPE:
				setType((EClass)newValue);
				return;
			case HenshinPackage.NODE__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends Attribute>)newValue);
				return;
			case HenshinPackage.NODE__GRAPH:
				setGraph((Graph)newValue);
				return;
			case HenshinPackage.NODE__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends Edge>)newValue);
				return;
			case HenshinPackage.NODE__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends Edge>)newValue);
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
			case HenshinPackage.NODE__TYPE:
				setType((EClass)null);
				return;
			case HenshinPackage.NODE__ATTRIBUTES:
				getAttributes().clear();
				return;
			case HenshinPackage.NODE__GRAPH:
				setGraph((Graph)null);
				return;
			case HenshinPackage.NODE__INCOMING:
				getIncoming().clear();
				return;
			case HenshinPackage.NODE__OUTGOING:
				getOutgoing().clear();
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
			case HenshinPackage.NODE__TYPE:
				return type != null;
			case HenshinPackage.NODE__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case HenshinPackage.NODE__GRAPH:
				return getGraph() != null;
			case HenshinPackage.NODE__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case HenshinPackage.NODE__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case HenshinPackage.NODE__ALL_EDGES:
				return !getAllEdges().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NodeImpl
