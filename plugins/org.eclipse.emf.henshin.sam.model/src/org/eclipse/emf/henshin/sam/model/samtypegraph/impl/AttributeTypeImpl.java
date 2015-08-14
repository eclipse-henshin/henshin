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
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.sorts.TypeSingleton;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl#getSort <em>Sort</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.impl.AttributeTypeImpl#getAttributedElement <em>Attributed Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeTypeImpl extends EObjectImpl implements AttributeType {
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
	 * The cached value of the '{@link #getSort() <em>Sort</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSort()
	 * @generated
	 * @ordered
	 */
	protected Sort sort;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtypegraphPackage.Literals.ATTRIBUTE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtypegraphPackage.ATTRIBUTE_TYPE__ANNOTATIONS);
		}
		return annotations;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.ATTRIBUTE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sort getSort() {
		if (sort != null && sort.eIsProxy()) {
			InternalEObject oldSort = (InternalEObject)sort;
			sort = (Sort)eResolveProxy(oldSort);
			if (sort != oldSort) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamtypegraphPackage.ATTRIBUTE_TYPE__SORT, oldSort, sort));
			}
		}
		return sort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Sort basicGetSort() {
		return sort;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSort(Sort newSort) {
		Sort oldSort = sort;
		sort = newSort;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.ATTRIBUTE_TYPE__SORT, oldSort, sort));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributedElem getAttributedElement() {
		if (eContainerFeatureID() != SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT) return null;
		return (AttributedElem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributedElement(AttributedElem newAttributedElement, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAttributedElement, SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributedElement(AttributedElem newAttributedElement) {
		if (newAttributedElement != eInternalContainer() || (eContainerFeatureID() != SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT && newAttributedElement != null)) {
			if (EcoreUtil.isAncestor(this, newAttributedElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAttributedElement != null)
				msgs = ((InternalEObject)newAttributedElement).eInverseAdd(this, SamtypegraphPackage.ATTRIBUTED_ELEM__ATTRIBUTES, AttributedElem.class, msgs);
			msgs = basicSetAttributedElement(newAttributedElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT, newAttributedElement, newAttributedElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Sort lookUpSort(EDataType dataType) {
		TypeSingleton typeSingleton = TypeSingleton.getInstance();
		return typeSingleton.getSort(dataType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EDataType lookUpEDataType(Sort sort) {
		TypeSingleton typeSingleton = TypeSingleton.getInstance();
		return typeSingleton.getEDataType(sort);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Sort lookUpSort(String name) {
		TypeSingleton typeSingleton = TypeSingleton.getInstance();
		return typeSingleton.getSort(name);
	}

	/**
	 * @generated NOT
	 */
	public static Sort lookUpSortStatic(EDataType dataType) {
		TypeSingleton typeSingleton = TypeSingleton.getInstance();
		return typeSingleton.getSort(dataType);
	}
	
	/**
	 * @generated NOT
	 */
	public static Sort lookUpSortStatic(String name) {
		TypeSingleton typeSingleton = TypeSingleton.getInstance();
		return typeSingleton.getSort(name);
	}
	
	/**
	 * @generated NOT
	 */
	public static EDataType lookUpEDataTypeStatic(Sort sort) {
		TypeSingleton typeSingleton = TypeSingleton.getInstance();
		return typeSingleton.getEDataType(sort);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
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
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
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
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
				return eInternalContainer().eInverseRemove(this, SamtypegraphPackage.ATTRIBUTED_ELEM__ATTRIBUTES, AttributedElem.class, msgs);
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
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ANNOTATIONS:
				return getAnnotations();
			case SamtypegraphPackage.ATTRIBUTE_TYPE__NAME:
				return getName();
			case SamtypegraphPackage.ATTRIBUTE_TYPE__SORT:
				if (resolve) return getSort();
				return basicGetSort();
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
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
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__NAME:
				setName((String)newValue);
				return;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__SORT:
				setSort((Sort)newValue);
				return;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
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
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__SORT:
				setSort((Sort)null);
				return;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
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
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtypegraphPackage.ATTRIBUTE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamtypegraphPackage.ATTRIBUTE_TYPE__SORT:
				return sort != null;
			case SamtypegraphPackage.ATTRIBUTE_TYPE__ATTRIBUTED_ELEMENT:
				return getAttributedElement() != null;
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

} //AttributeTypeImpl
