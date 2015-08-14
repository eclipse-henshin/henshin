/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl;

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
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Algebra Signature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl#getSorts <em>Sorts</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl#getOperationSymbols <em>Operation Symbols</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.AlgebraSignatureImpl#getExtends <em>Extends</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AlgebraSignatureImpl extends EObjectImpl implements AlgebraSignature {
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
	 * The cached value of the '{@link #getSorts() <em>Sorts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Sort> sorts;

	/**
	 * The cached value of the '{@link #getOperationSymbols() <em>Operation Symbols</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationSymbols()
	 * @generated
	 * @ordered
	 */
	protected EList<OperationSymbol> operationSymbols;

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
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected AlgebraSignature extends_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlgebraSignatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SignaturePackage.Literals.ALGEBRA_SIGNATURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SignaturePackage.ALGEBRA_SIGNATURE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Sort> getSorts() {
		if (sorts == null) {
			sorts = new EObjectContainmentEList<Sort>(Sort.class, this, SignaturePackage.ALGEBRA_SIGNATURE__SORTS);
		}
		return sorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OperationSymbol> getOperationSymbols() {
		if (operationSymbols == null) {
			operationSymbols = new EObjectContainmentEList<OperationSymbol>(OperationSymbol.class, this, SignaturePackage.ALGEBRA_SIGNATURE__OPERATION_SYMBOLS);
		}
		return operationSymbols;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SignaturePackage.ALGEBRA_SIGNATURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlgebraSignature getExtends() {
		if (extends_ != null && extends_.eIsProxy()) {
			InternalEObject oldExtends = (InternalEObject)extends_;
			extends_ = (AlgebraSignature)eResolveProxy(oldExtends);
			if (extends_ != oldExtends) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SignaturePackage.ALGEBRA_SIGNATURE__EXTENDS, oldExtends, extends_));
			}
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlgebraSignature basicGetExtends() {
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtends(AlgebraSignature newExtends) {
		AlgebraSignature oldExtends = extends_;
		extends_ = newExtends;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SignaturePackage.ALGEBRA_SIGNATURE__EXTENDS, oldExtends, extends_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SignaturePackage.ALGEBRA_SIGNATURE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SignaturePackage.ALGEBRA_SIGNATURE__SORTS:
				return ((InternalEList<?>)getSorts()).basicRemove(otherEnd, msgs);
			case SignaturePackage.ALGEBRA_SIGNATURE__OPERATION_SYMBOLS:
				return ((InternalEList<?>)getOperationSymbols()).basicRemove(otherEnd, msgs);
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
			case SignaturePackage.ALGEBRA_SIGNATURE__ANNOTATIONS:
				return getAnnotations();
			case SignaturePackage.ALGEBRA_SIGNATURE__SORTS:
				return getSorts();
			case SignaturePackage.ALGEBRA_SIGNATURE__OPERATION_SYMBOLS:
				return getOperationSymbols();
			case SignaturePackage.ALGEBRA_SIGNATURE__NAME:
				return getName();
			case SignaturePackage.ALGEBRA_SIGNATURE__EXTENDS:
				if (resolve) return getExtends();
				return basicGetExtends();
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
			case SignaturePackage.ALGEBRA_SIGNATURE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__SORTS:
				getSorts().clear();
				getSorts().addAll((Collection<? extends Sort>)newValue);
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__OPERATION_SYMBOLS:
				getOperationSymbols().clear();
				getOperationSymbols().addAll((Collection<? extends OperationSymbol>)newValue);
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__NAME:
				setName((String)newValue);
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__EXTENDS:
				setExtends((AlgebraSignature)newValue);
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
			case SignaturePackage.ALGEBRA_SIGNATURE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__SORTS:
				getSorts().clear();
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__OPERATION_SYMBOLS:
				getOperationSymbols().clear();
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SignaturePackage.ALGEBRA_SIGNATURE__EXTENDS:
				setExtends((AlgebraSignature)null);
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
			case SignaturePackage.ALGEBRA_SIGNATURE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SignaturePackage.ALGEBRA_SIGNATURE__SORTS:
				return sorts != null && !sorts.isEmpty();
			case SignaturePackage.ALGEBRA_SIGNATURE__OPERATION_SYMBOLS:
				return operationSymbols != null && !operationSymbols.isEmpty();
			case SignaturePackage.ALGEBRA_SIGNATURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SignaturePackage.ALGEBRA_SIGNATURE__EXTENDS:
				return extends_ != null;
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

} //AlgebraSignatureImpl
