/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samalgebra.Algebra;
import org.eclipse.emf.henshin.sam.model.samalgebra.Operation;
import org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Algebra</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.impl.AlgebraImpl#getSignature <em>Signature</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AlgebraImpl extends EObjectImpl implements Algebra {
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
	 * The cached value of the '{@link #getConstants() <em>Constants</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstants()
	 * @generated
	 * @ordered
	 */
	protected EMap<Operation, DataElement> constants;

	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<Operation> operations;

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected AlgebraSignature signature;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlgebraImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamalgebraPackage.Literals.ALGEBRA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamalgebraPackage.ALGEBRA__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Operation, DataElement> getConstants() {
		if (constants == null) {
			constants = new EcoreEMap<Operation,DataElement>(SamalgebraPackage.Literals.CONSTANT_DATA_ELEMENT_MAP, ConstantDataElementMapImpl.class, this, SamalgebraPackage.ALGEBRA__CONSTANTS);
		}
		return constants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Operation> getOperations() {
		if (operations == null) {
			operations = new EObjectContainmentEList<Operation>(Operation.class, this, SamalgebraPackage.ALGEBRA__OPERATIONS);
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlgebraSignature getSignature() {
		if (signature != null && signature.eIsProxy()) {
			InternalEObject oldSignature = (InternalEObject)signature;
			signature = (AlgebraSignature)eResolveProxy(oldSignature);
			if (signature != oldSignature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamalgebraPackage.ALGEBRA__SIGNATURE, oldSignature, signature));
			}
		}
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlgebraSignature basicGetSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(AlgebraSignature newSignature) {
		AlgebraSignature oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamalgebraPackage.ALGEBRA__SIGNATURE, oldSignature, signature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamalgebraPackage.ALGEBRA__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamalgebraPackage.ALGEBRA__CONSTANTS:
				return ((InternalEList<?>)getConstants()).basicRemove(otherEnd, msgs);
			case SamalgebraPackage.ALGEBRA__OPERATIONS:
				return ((InternalEList<?>)getOperations()).basicRemove(otherEnd, msgs);
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
			case SamalgebraPackage.ALGEBRA__ANNOTATIONS:
				return getAnnotations();
			case SamalgebraPackage.ALGEBRA__CONSTANTS:
				if (coreType) return getConstants();
				else return getConstants().map();
			case SamalgebraPackage.ALGEBRA__OPERATIONS:
				return getOperations();
			case SamalgebraPackage.ALGEBRA__SIGNATURE:
				if (resolve) return getSignature();
				return basicGetSignature();
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
			case SamalgebraPackage.ALGEBRA__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamalgebraPackage.ALGEBRA__CONSTANTS:
				((EStructuralFeature.Setting)getConstants()).set(newValue);
				return;
			case SamalgebraPackage.ALGEBRA__OPERATIONS:
				getOperations().clear();
				getOperations().addAll((Collection<? extends Operation>)newValue);
				return;
			case SamalgebraPackage.ALGEBRA__SIGNATURE:
				setSignature((AlgebraSignature)newValue);
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
			case SamalgebraPackage.ALGEBRA__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamalgebraPackage.ALGEBRA__CONSTANTS:
				getConstants().clear();
				return;
			case SamalgebraPackage.ALGEBRA__OPERATIONS:
				getOperations().clear();
				return;
			case SamalgebraPackage.ALGEBRA__SIGNATURE:
				setSignature((AlgebraSignature)null);
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
			case SamalgebraPackage.ALGEBRA__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamalgebraPackage.ALGEBRA__CONSTANTS:
				return constants != null && !constants.isEmpty();
			case SamalgebraPackage.ALGEBRA__OPERATIONS:
				return operations != null && !operations.isEmpty();
			case SamalgebraPackage.ALGEBRA__SIGNATURE:
				return signature != null;
		}
		return super.eIsSet(featureID);
	}

} //AlgebraImpl
