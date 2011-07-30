/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model.impl;

import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.codegen.model.GenHenshin;
import org.eclipse.emf.henshin.codegen.model.GenHenshinPackage;
import org.eclipse.emf.henshin.codegen.model.GenTransformation;
import org.eclipse.emf.henshin.codegen.model.GenUnit;

import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Transformation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getTransformation <em>Transformation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getTransformationClass <em>Transformation Class</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getGenPackages <em>Gen Packages</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getGenUnits <em>Gen Units</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.impl.GenTransformationImpl#getGenHenshin <em>Gen Henshin</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenTransformationImpl extends EObjectImpl implements GenTransformation {
	/**
	 * The cached value of the '{@link #getTransformation() <em>Transformation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformation()
	 * @generated
	 * @ordered
	 */
	protected TransformationSystem transformation;

	/**
	 * The default value of the '{@link #getTransformationClass() <em>Transformation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSFORMATION_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransformationClass() <em>Transformation Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationClass()
	 * @generated
	 * @ordered
	 */
	protected String transformationClass = TRANSFORMATION_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGenPackages() <em>Gen Packages</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<GenPackage> genPackages;

	/**
	 * The cached value of the '{@link #getGenUnits() <em>Gen Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<GenUnit> genUnits;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenTransformationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenHenshinPackage.Literals.GEN_TRANSFORMATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem getTransformation() {
		if (transformation != null && transformation.eIsProxy()) {
			InternalEObject oldTransformation = (InternalEObject)transformation;
			transformation = (TransformationSystem)eResolveProxy(oldTransformation);
			if (transformation != oldTransformation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION, oldTransformation, transformation));
			}
		}
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationSystem basicGetTransformation() {
		return transformation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformation(TransformationSystem newTransformation) {
		TransformationSystem oldTransformation = transformation;
		transformation = newTransformation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION, oldTransformation, transformation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTransformationClass() {
		return transformationClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransformationClass(String newTransformationClass) {
		String oldTransformationClass = transformationClass;
		transformationClass = newTransformationClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS, oldTransformationClass, transformationClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenPackage> getGenPackages() {
		if (genPackages == null) {
			genPackages = new EObjectResolvingEList<GenPackage>(GenPackage.class, this, GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES);
		}
		return genPackages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenUnit> getGenUnits() {
		if (genUnits == null) {
			genUnits = new EObjectContainmentEList<GenUnit>(GenUnit.class, this, GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS);
		}
		return genUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenHenshin getGenHenshin() {
		if (eContainerFeatureID() != GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN) return null;
		return (GenHenshin)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGenHenshin(GenHenshin newGenHenshin, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newGenHenshin, GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenHenshin(GenHenshin newGenHenshin) {
		if (newGenHenshin != eInternalContainer() || (eContainerFeatureID() != GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN && newGenHenshin != null)) {
			if (EcoreUtil.isAncestor(this, newGenHenshin))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newGenHenshin != null)
				msgs = ((InternalEObject)newGenHenshin).eInverseAdd(this, GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS, GenHenshin.class, msgs);
			msgs = basicSetGenHenshin(newGenHenshin, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN, newGenHenshin, newGenHenshin));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenPackage getGenPackage(EPackage ePackage) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTransformationClassFormatted() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetGenHenshin((GenHenshin)otherEnd, msgs);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return ((InternalEList<?>)getGenUnits()).basicRemove(otherEnd, msgs);
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return basicSetGenHenshin(null, msgs);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return eInternalContainer().eInverseRemove(this, GenHenshinPackage.GEN_HENSHIN__GEN_TRANSFORMATIONS, GenHenshin.class, msgs);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				if (resolve) return getTransformation();
				return basicGetTransformation();
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				return getTransformationClass();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				return getGenPackages();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return getGenUnits();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return getGenHenshin();
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				setTransformation((TransformationSystem)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				setTransformationClass((String)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				getGenPackages().clear();
				getGenPackages().addAll((Collection<? extends GenPackage>)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				getGenUnits().clear();
				getGenUnits().addAll((Collection<? extends GenUnit>)newValue);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				setGenHenshin((GenHenshin)newValue);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				setTransformation((TransformationSystem)null);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				setTransformationClass(TRANSFORMATION_CLASS_EDEFAULT);
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				getGenPackages().clear();
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				getGenUnits().clear();
				return;
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				setGenHenshin((GenHenshin)null);
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
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION:
				return transformation != null;
			case GenHenshinPackage.GEN_TRANSFORMATION__TRANSFORMATION_CLASS:
				return TRANSFORMATION_CLASS_EDEFAULT == null ? transformationClass != null : !TRANSFORMATION_CLASS_EDEFAULT.equals(transformationClass);
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_PACKAGES:
				return genPackages != null && !genPackages.isEmpty();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_UNITS:
				return genUnits != null && !genUnits.isEmpty();
			case GenHenshinPackage.GEN_TRANSFORMATION__GEN_HENSHIN:
				return getGenHenshin() != null;
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
		result.append(" (transformationClass: ");
		result.append(transformationClass);
		result.append(')');
		return result.toString();
	}

} //GenTransformationImpl
