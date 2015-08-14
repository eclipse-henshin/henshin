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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Attribute;
import org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.AttributeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.AttributeImpl#getInstanceOf <em>Instance Of</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.impl.AttributeImpl#getAttributedElement <em>Attributed Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeImpl extends EObjectImpl implements Attribute {
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
	 * The cached value of the '{@link #getInstanceOf() <em>Instance Of</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstanceOf()
	 * @generated
	 * @ordered
	 */
	protected AttributeType instanceOf;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamgraphPackage.Literals.ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamgraphPackage.ATTRIBUTE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType getInstanceOf() {
		if (instanceOf != null && instanceOf.eIsProxy()) {
			InternalEObject oldInstanceOf = (InternalEObject)instanceOf;
			instanceOf = (AttributeType)eResolveProxy(oldInstanceOf);
			if (instanceOf != oldInstanceOf) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamgraphPackage.ATTRIBUTE__INSTANCE_OF, oldInstanceOf, instanceOf));
			}
		}
		return instanceOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeType basicGetInstanceOf() {
		return instanceOf;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInstanceOf(AttributeType newInstanceOf) {
		AttributeType oldInstanceOf = instanceOf;
		instanceOf = newInstanceOf;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphPackage.ATTRIBUTE__INSTANCE_OF, oldInstanceOf, instanceOf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributedElem getAttributedElement() {
		if (eContainerFeatureID() != SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT) return null;
		return (AttributedElem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributedElement(AttributedElem newAttributedElement, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAttributedElement, SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributedElement(AttributedElem newAttributedElement) {
		if (newAttributedElement != eInternalContainer() || (eContainerFeatureID() != SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT && newAttributedElement != null)) {
			if (EcoreUtil.isAncestor(this, newAttributedElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAttributedElement != null)
				msgs = ((InternalEObject)newAttributedElement).eInverseAdd(this, SamgraphPackage.ATTRIBUTED_ELEM__ATTRIBUTES, AttributedElem.class, msgs);
			msgs = basicSetAttributedElement(newAttributedElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT, newAttributedElement, newAttributedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAttributedElement((AttributedElem)otherEnd, msgs);
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
			case SamgraphPackage.ATTRIBUTE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				return basicSetAttributedElement(null, msgs);
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
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				return eInternalContainer().eInverseRemove(this, SamgraphPackage.ATTRIBUTED_ELEM__ATTRIBUTES, AttributedElem.class, msgs);
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
			case SamgraphPackage.ATTRIBUTE__ANNOTATIONS:
				return getAnnotations();
			case SamgraphPackage.ATTRIBUTE__INSTANCE_OF:
				if (resolve) return getInstanceOf();
				return basicGetInstanceOf();
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				return getAttributedElement();
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
			case SamgraphPackage.ATTRIBUTE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamgraphPackage.ATTRIBUTE__INSTANCE_OF:
				setInstanceOf((AttributeType)newValue);
				return;
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				setAttributedElement((AttributedElem)newValue);
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
			case SamgraphPackage.ATTRIBUTE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamgraphPackage.ATTRIBUTE__INSTANCE_OF:
				setInstanceOf((AttributeType)null);
				return;
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				setAttributedElement((AttributedElem)null);
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
			case SamgraphPackage.ATTRIBUTE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamgraphPackage.ATTRIBUTE__INSTANCE_OF:
				return instanceOf != null;
			case SamgraphPackage.ATTRIBUTE__ATTRIBUTED_ELEMENT:
				return getAttributedElement() != null;
		}
		return super.eIsSet(featureID);
	}

} //AttributeImpl
