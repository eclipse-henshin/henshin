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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.Cardinality;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getSourceCardinality <em>Source Cardinality</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.EdgeTypeImpl#getTargetCardinality <em>Target Cardinality</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EdgeTypeImpl extends EObjectImpl implements EdgeType {
	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeType> attributes;

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
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected NodeType source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected NodeType target;

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
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final EdgeDirection DIRECTION_EDEFAULT = EdgeDirection.UNDIRECTED;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected EdgeDirection direction = DIRECTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSourceCardinality() <em>Source Cardinality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceCardinality()
	 * @generated
	 * @ordered
	 */
	protected Cardinality sourceCardinality;

	/**
	 * The cached value of the '{@link #getTargetCardinality() <em>Target Cardinality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetCardinality()
	 * @generated
	 * @ordered
	 */
	protected Cardinality targetCardinality;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EdgeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtypegraphPackage.Literals.EDGE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeType> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentWithInverseEList<AttributeType>(AttributeType.class, this, SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES, SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (NodeType)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamtypegraphPackage.EDGE_TYPE__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(NodeType newSource, NotificationChain msgs) {
		NodeType oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(NodeType newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__OUTGOING, NodeType.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, SamtypegraphPackage.NODE_TYPE__OUTGOING, NodeType.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (NodeType)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamtypegraphPackage.EDGE_TYPE__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(NodeType newTarget, NotificationChain msgs) {
		NodeType oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(NodeType newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__INCOMING, NodeType.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, SamtypegraphPackage.NODE_TYPE__INCOMING, NodeType.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__TARGET, newTarget, newTarget));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeGraph getTypeGraph() {
		if (eContainerFeatureID() != SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH) return null;
		return (TypeGraph)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeGraph(TypeGraph newTypeGraph, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTypeGraph, SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeGraph(TypeGraph newTypeGraph) {
		if (newTypeGraph != eInternalContainer() || (eContainerFeatureID() != SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH && newTypeGraph != null)) {
			if (EcoreUtil.isAncestor(this, newTypeGraph))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTypeGraph != null)
				msgs = ((InternalEObject)newTypeGraph).eInverseAdd(this, SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES, TypeGraph.class, msgs);
			msgs = basicSetTypeGraph(newTypeGraph, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH, newTypeGraph, newTypeGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EdgeDirection getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(EdgeDirection newDirection) {
		EdgeDirection oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cardinality getSourceCardinality() {
		return sourceCardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSourceCardinality(Cardinality newSourceCardinality, NotificationChain msgs) {
		Cardinality oldSourceCardinality = sourceCardinality;
		sourceCardinality = newSourceCardinality;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY, oldSourceCardinality, newSourceCardinality);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceCardinality(Cardinality newSourceCardinality) {
		if (newSourceCardinality != sourceCardinality) {
			NotificationChain msgs = null;
			if (sourceCardinality != null)
				msgs = ((InternalEObject)sourceCardinality).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY, null, msgs);
			if (newSourceCardinality != null)
				msgs = ((InternalEObject)newSourceCardinality).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY, null, msgs);
			msgs = basicSetSourceCardinality(newSourceCardinality, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY, newSourceCardinality, newSourceCardinality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cardinality getTargetCardinality() {
		return targetCardinality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetCardinality(Cardinality newTargetCardinality, NotificationChain msgs) {
		Cardinality oldTargetCardinality = targetCardinality;
		targetCardinality = newTargetCardinality;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY, oldTargetCardinality, newTargetCardinality);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetCardinality(Cardinality newTargetCardinality) {
		if (newTargetCardinality != targetCardinality) {
			NotificationChain msgs = null;
			if (targetCardinality != null)
				msgs = ((InternalEObject)targetCardinality).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY, null, msgs);
			if (newTargetCardinality != null)
				msgs = ((InternalEObject)newTargetCardinality).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY, null, msgs);
			msgs = basicSetTargetCardinality(newTargetCardinality, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY, newTargetCardinality, newTargetCardinality));
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
			case SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributes()).basicAdd(otherEnd, msgs);
			case SamtypegraphPackage.EDGE_TYPE__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__OUTGOING, NodeType.class, msgs);
				return basicSetSource((NodeType)otherEnd, msgs);
			case SamtypegraphPackage.EDGE_TYPE__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__INCOMING, NodeType.class, msgs);
				return basicSetTarget((NodeType)otherEnd, msgs);
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTypeGraph((TypeGraph)otherEnd, msgs);
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
			case SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.EDGE_TYPE__SOURCE:
				return basicSetSource(null, msgs);
			case SamtypegraphPackage.EDGE_TYPE__TARGET:
				return basicSetTarget(null, msgs);
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				return basicSetTypeGraph(null, msgs);
			case SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY:
				return basicSetSourceCardinality(null, msgs);
			case SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY:
				return basicSetTargetCardinality(null, msgs);
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
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				return eInternalContainer().eInverseRemove(this, SamtypegraphPackage.TYPE_GRAPH__EDGE_TYPES, TypeGraph.class, msgs);
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
			case SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES:
				return getAttributes();
			case SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS:
				return getAnnotations();
			case SamtypegraphPackage.EDGE_TYPE__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case SamtypegraphPackage.EDGE_TYPE__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case SamtypegraphPackage.EDGE_TYPE__NAME:
				return getName();
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				return getTypeGraph();
			case SamtypegraphPackage.EDGE_TYPE__DIRECTION:
				return getDirection();
			case SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY:
				return getSourceCardinality();
			case SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY:
				return getTargetCardinality();
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
			case SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends AttributeType>)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__SOURCE:
				setSource((NodeType)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__TARGET:
				setTarget((NodeType)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__NAME:
				setName((String)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				setTypeGraph((TypeGraph)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__DIRECTION:
				setDirection((EdgeDirection)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY:
				setSourceCardinality((Cardinality)newValue);
				return;
			case SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY:
				setTargetCardinality((Cardinality)newValue);
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
			case SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES:
				getAttributes().clear();
				return;
			case SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtypegraphPackage.EDGE_TYPE__SOURCE:
				setSource((NodeType)null);
				return;
			case SamtypegraphPackage.EDGE_TYPE__TARGET:
				setTarget((NodeType)null);
				return;
			case SamtypegraphPackage.EDGE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				setTypeGraph((TypeGraph)null);
				return;
			case SamtypegraphPackage.EDGE_TYPE__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY:
				setSourceCardinality((Cardinality)null);
				return;
			case SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY:
				setTargetCardinality((Cardinality)null);
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
			case SamtypegraphPackage.EDGE_TYPE__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtypegraphPackage.EDGE_TYPE__SOURCE:
				return source != null;
			case SamtypegraphPackage.EDGE_TYPE__TARGET:
				return target != null;
			case SamtypegraphPackage.EDGE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamtypegraphPackage.EDGE_TYPE__TYPE_GRAPH:
				return getTypeGraph() != null;
			case SamtypegraphPackage.EDGE_TYPE__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case SamtypegraphPackage.EDGE_TYPE__SOURCE_CARDINALITY:
				return sourceCardinality != null;
			case SamtypegraphPackage.EDGE_TYPE__TARGET_CARDINALITY:
				return targetCardinality != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == AnnotatedElem.class) {
			switch (derivedFeatureID) {
				case SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS: return SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == AnnotatedElem.class) {
			switch (baseFeatureID) {
				case SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS: return SamtypegraphPackage.EDGE_TYPE__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", direction: ");
		result.append(direction);
		result.append(')');
		return result.toString();
	}

} //EdgeTypeImpl
