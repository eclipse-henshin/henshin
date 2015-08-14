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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.NodeTypeImpl#getSubType <em>Sub Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeTypeImpl extends EObjectImpl implements NodeType {
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
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<EdgeType> outgoing;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<EdgeType> incoming;

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
	 * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperType()
	 * @generated
	 * @ordered
	 */
	protected InheritanceRelation superType;

	/**
	 * The cached value of the '{@link #getSubType() <em>Sub Type</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubType()
	 * @generated
	 * @ordered
	 */
	protected EList<InheritanceRelation> subType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtypegraphPackage.Literals.NODE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeType> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentWithInverseEList<AttributeType>(AttributeType.class, this, SamtypegraphPackage.NODE_TYPE__ATTRIBUTES, SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT);
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
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtypegraphPackage.NODE_TYPE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EdgeType> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseResolvingEList<EdgeType>(EdgeType.class, this, SamtypegraphPackage.NODE_TYPE__OUTGOING, SamtypegraphPackage.EDGE_TYPE__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EdgeType> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<EdgeType>(EdgeType.class, this, SamtypegraphPackage.NODE_TYPE__INCOMING, SamtypegraphPackage.EDGE_TYPE__TARGET);
		}
		return incoming;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.NODE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeGraph getTypeGraph() {
		if (eContainerFeatureID() != SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH) return null;
		return (TypeGraph)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTypeGraph(TypeGraph newTypeGraph, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newTypeGraph, SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTypeGraph(TypeGraph newTypeGraph) {
		if (newTypeGraph != eInternalContainer() || (eContainerFeatureID() != SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH && newTypeGraph != null)) {
			if (EcoreUtil.isAncestor(this, newTypeGraph))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTypeGraph != null)
				msgs = ((InternalEObject)newTypeGraph).eInverseAdd(this, SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES, TypeGraph.class, msgs);
			msgs = basicSetTypeGraph(newTypeGraph, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH, newTypeGraph, newTypeGraph));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceRelation getSuperType() {
		return superType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperType(InheritanceRelation newSuperType, NotificationChain msgs) {
		InheritanceRelation oldSuperType = superType;
		superType = newSuperType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.NODE_TYPE__SUPER_TYPE, oldSuperType, newSuperType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperType(InheritanceRelation newSuperType) {
		if (newSuperType != superType) {
			NotificationChain msgs = null;
			if (superType != null)
				msgs = ((InternalEObject)superType).eInverseRemove(this, SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE, InheritanceRelation.class, msgs);
			if (newSuperType != null)
				msgs = ((InternalEObject)newSuperType).eInverseAdd(this, SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE, InheritanceRelation.class, msgs);
			msgs = basicSetSuperType(newSuperType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.NODE_TYPE__SUPER_TYPE, newSuperType, newSuperType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InheritanceRelation> getSubType() {
		if (subType == null) {
			subType = new EObjectWithInverseResolvingEList<InheritanceRelation>(InheritanceRelation.class, this, SamtypegraphPackage.NODE_TYPE__SUB_TYPE, SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE);
		}
		return subType;
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
			case SamtypegraphPackage.NODE_TYPE__ATTRIBUTES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAttributes()).basicAdd(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetTypeGraph((TypeGraph)otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__SUPER_TYPE:
				if (superType != null)
					msgs = ((InternalEObject)superType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamtypegraphPackage.NODE_TYPE__SUPER_TYPE, null, msgs);
				return basicSetSuperType((InheritanceRelation)otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__SUB_TYPE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSubType()).basicAdd(otherEnd, msgs);
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
			case SamtypegraphPackage.NODE_TYPE__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				return basicSetTypeGraph(null, msgs);
			case SamtypegraphPackage.NODE_TYPE__SUPER_TYPE:
				return basicSetSuperType(null, msgs);
			case SamtypegraphPackage.NODE_TYPE__SUB_TYPE:
				return ((InternalEList<?>)getSubType()).basicRemove(otherEnd, msgs);
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
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				return eInternalContainer().eInverseRemove(this, SamtypegraphPackage.TYPE_GRAPH__NODE_TYPES, TypeGraph.class, msgs);
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
			case SamtypegraphPackage.NODE_TYPE__ATTRIBUTES:
				return getAttributes();
			case SamtypegraphPackage.NODE_TYPE__ANNOTATIONS:
				return getAnnotations();
			case SamtypegraphPackage.NODE_TYPE__OUTGOING:
				return getOutgoing();
			case SamtypegraphPackage.NODE_TYPE__INCOMING:
				return getIncoming();
			case SamtypegraphPackage.NODE_TYPE__NAME:
				return getName();
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				return getTypeGraph();
			case SamtypegraphPackage.NODE_TYPE__SUPER_TYPE:
				return getSuperType();
			case SamtypegraphPackage.NODE_TYPE__SUB_TYPE:
				return getSubType();
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
			case SamtypegraphPackage.NODE_TYPE__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends AttributeType>)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends EdgeType>)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends EdgeType>)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__NAME:
				setName((String)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				setTypeGraph((TypeGraph)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__SUPER_TYPE:
				setSuperType((InheritanceRelation)newValue);
				return;
			case SamtypegraphPackage.NODE_TYPE__SUB_TYPE:
				getSubType().clear();
				getSubType().addAll((Collection<? extends InheritanceRelation>)newValue);
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
			case SamtypegraphPackage.NODE_TYPE__ATTRIBUTES:
				getAttributes().clear();
				return;
			case SamtypegraphPackage.NODE_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtypegraphPackage.NODE_TYPE__OUTGOING:
				getOutgoing().clear();
				return;
			case SamtypegraphPackage.NODE_TYPE__INCOMING:
				getIncoming().clear();
				return;
			case SamtypegraphPackage.NODE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				setTypeGraph((TypeGraph)null);
				return;
			case SamtypegraphPackage.NODE_TYPE__SUPER_TYPE:
				setSuperType((InheritanceRelation)null);
				return;
			case SamtypegraphPackage.NODE_TYPE__SUB_TYPE:
				getSubType().clear();
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
			case SamtypegraphPackage.NODE_TYPE__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case SamtypegraphPackage.NODE_TYPE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtypegraphPackage.NODE_TYPE__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case SamtypegraphPackage.NODE_TYPE__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case SamtypegraphPackage.NODE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamtypegraphPackage.NODE_TYPE__TYPE_GRAPH:
				return getTypeGraph() != null;
			case SamtypegraphPackage.NODE_TYPE__SUPER_TYPE:
				return superType != null;
			case SamtypegraphPackage.NODE_TYPE__SUB_TYPE:
				return subType != null && !subType.isEmpty();
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
				case SamtypegraphPackage.NODE_TYPE__ANNOTATIONS: return SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;
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
				case SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS: return SamtypegraphPackage.NODE_TYPE__ANNOTATIONS;
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
		result.append(')');
		return result.toString();
	}

} //NodeTypeImpl
