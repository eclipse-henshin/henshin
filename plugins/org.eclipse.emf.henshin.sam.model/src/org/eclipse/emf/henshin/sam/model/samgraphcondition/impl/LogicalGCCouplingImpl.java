/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition.impl;

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
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Logical GC Coupling</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.LogicalGCCouplingImpl#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LogicalGCCouplingImpl extends EObjectImpl implements LogicalGCCoupling {
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
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final LogicalOperator OPERATOR_EDEFAULT = LogicalOperator.DISJUNCTION;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected LogicalOperator operator = OPERATOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOperands() <em>Operands</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperands()
	 * @generated
	 * @ordered
	 */
	protected EList<GraphCondition> operands;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LogicalGCCouplingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamgraphconditionPackage.Literals.LOGICAL_GC_COUPLING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS);
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
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.LOGICAL_GC_COUPLING__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogicalOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(LogicalOperator newOperator) {
		LogicalOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GraphCondition> getOperands() {
		if (operands == null) {
			operands = new EObjectContainmentEList<GraphCondition>(GraphCondition.class, this, SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERANDS);
		}
		return operands;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERANDS:
				return ((InternalEList<?>)getOperands()).basicRemove(otherEnd, msgs);
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
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS:
				return getAnnotations();
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__NAME:
				return getName();
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERATOR:
				return getOperator();
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERANDS:
				return getOperands();
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
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__NAME:
				setName((String)newValue);
				return;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERATOR:
				setOperator((LogicalOperator)newValue);
				return;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERANDS:
				getOperands().clear();
				getOperands().addAll((Collection<? extends GraphCondition>)newValue);
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
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERATOR:
				setOperator(OPERATOR_EDEFAULT);
				return;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERANDS:
				getOperands().clear();
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
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERATOR:
				return operator != OPERATOR_EDEFAULT;
			case SamgraphconditionPackage.LOGICAL_GC_COUPLING__OPERANDS:
				return operands != null && !operands.isEmpty();
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
				case SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS: return SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS;
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
				case SamannotationPackage.ANNOTATED_ELEM__ANNOTATIONS: return SamgraphconditionPackage.LOGICAL_GC_COUPLING__ANNOTATIONS;
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
		result.append(", operator: ");
		result.append(operator);
		result.append(')');
		return result.toString();
	}

} //LogicalGCCouplingImpl
