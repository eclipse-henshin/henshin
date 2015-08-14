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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inheritance Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl#getSubType <em>Sub Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.InheritanceRelationImpl#getSuperType <em>Super Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InheritanceRelationImpl extends EObjectImpl implements InheritanceRelation {
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
	 * The cached value of the '{@link #getSuperType() <em>Super Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperType()
	 * @generated
	 * @ordered
	 */
	protected NodeType superType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InheritanceRelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtypegraphPackage.Literals.INHERITANCE_RELATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtypegraphPackage.INHERITANCE_RELATION__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType getSubType() {
		if (eContainerFeatureID() != SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE) return null;
		return (NodeType)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubType(NodeType newSubType, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSubType, SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubType(NodeType newSubType) {
		if (newSubType != eInternalContainer() || (eContainerFeatureID() != SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE && newSubType != null)) {
			if (EcoreUtil.isAncestor(this, newSubType))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSubType != null)
				msgs = ((InternalEObject)newSubType).eInverseAdd(this, SamtypegraphPackage.NODE_TYPE__SUPER_TYPE, NodeType.class, msgs);
			msgs = basicSetSubType(newSubType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE, newSubType, newSubType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType getSuperType() {
		if (superType != null && superType.eIsProxy()) {
			InternalEObject oldSuperType = (InternalEObject)superType;
			superType = (NodeType)eResolveProxy(oldSuperType);
			if (superType != oldSuperType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE, oldSuperType, superType));
			}
		}
		return superType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeType basicGetSuperType() {
		return superType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperType(NodeType newSuperType, NotificationChain msgs) {
		NodeType oldSuperType = superType;
		superType = newSuperType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE, oldSuperType, newSuperType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperType(NodeType newSuperType) {
		if (newSuperType != superType) {
			NotificationChain msgs = null;
			if (superType != null)
				msgs = ((InternalEObject)superType).eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__SUB_TYPE, NodeType.class, msgs);
			if (newSuperType != null)
				msgs = ((InternalEObject)newSuperType).eInverseAdd(this, SamtypegraphPackage.NODE_TYPE__SUB_TYPE, NodeType.class, msgs);
			msgs = basicSetSuperType(newSuperType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE, newSuperType, newSuperType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSubType((NodeType)otherEnd, msgs);
			case SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE:
				if (superType != null)
					msgs = ((InternalEObject)superType).eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__SUB_TYPE, NodeType.class, msgs);
				return basicSetSuperType((NodeType)otherEnd, msgs);
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
			case SamtypegraphPackage.INHERITANCE_RELATION__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				return basicSetSubType(null, msgs);
			case SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE:
				return basicSetSuperType(null, msgs);
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
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				return eInternalContainer().eInverseRemove(this, SamtypegraphPackage.NODE_TYPE__SUPER_TYPE, NodeType.class, msgs);
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
			case SamtypegraphPackage.INHERITANCE_RELATION__ANNOTATIONS:
				return getAnnotations();
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				return getSubType();
			case SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE:
				if (resolve) return getSuperType();
				return basicGetSuperType();
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
			case SamtypegraphPackage.INHERITANCE_RELATION__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				setSubType((NodeType)newValue);
				return;
			case SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE:
				setSuperType((NodeType)newValue);
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
			case SamtypegraphPackage.INHERITANCE_RELATION__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				setSubType((NodeType)null);
				return;
			case SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE:
				setSuperType((NodeType)null);
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
			case SamtypegraphPackage.INHERITANCE_RELATION__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtypegraphPackage.INHERITANCE_RELATION__SUB_TYPE:
				return getSubType() != null;
			case SamtypegraphPackage.INHERITANCE_RELATION__SUPER_TYPE:
				return superType != null;
		}
		return super.eIsSet(featureID);
	}

} //InheritanceRelationImpl
