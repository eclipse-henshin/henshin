/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samannotation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotationDetails;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl#getDetails <em>Details</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samannotation.impl.AnnotationImpl#getRefInRule <em>Ref In Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationImpl extends EObjectImpl implements Annotation {
	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected String source = SOURCE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EObject target;

	/**
	 * The cached value of the '{@link #getDetails() <em>Details</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDetails()
	 * @generated
	 * @ordered
	 */
	protected EList<AnnotationDetails> details;

	/**
	 * The cached value of the '{@link #getRefInRule() <em>Ref In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefInRule()
	 * @generated
	 * @ordered
	 */
	protected Annotation refInRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamannotationPackage.Literals.ANNOTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(String newSource) {
		String oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamannotationPackage.ANNOTATION__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamannotationPackage.ANNOTATION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(EObject newTarget) {
		EObject oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamannotationPackage.ANNOTATION__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotationDetails> getDetails() {
		if (details == null) {
			details = new EObjectContainmentEList<AnnotationDetails>(AnnotationDetails.class, this, SamannotationPackage.ANNOTATION__DETAILS);
		}
		return details;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Annotation getRefInRule() {
		if (refInRule != null && refInRule.eIsProxy()) {
			InternalEObject oldRefInRule = (InternalEObject)refInRule;
			refInRule = (Annotation)eResolveProxy(oldRefInRule);
			if (refInRule != oldRefInRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamannotationPackage.ANNOTATION__REF_IN_RULE, oldRefInRule, refInRule));
			}
		}
		return refInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Annotation basicGetRefInRule() {
		return refInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefInRule(Annotation newRefInRule) {
		Annotation oldRefInRule = refInRule;
		refInRule = newRefInRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamannotationPackage.ANNOTATION__REF_IN_RULE, oldRefInRule, refInRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamannotationPackage.ANNOTATION__DETAILS:
				return ((InternalEList<?>)getDetails()).basicRemove(otherEnd, msgs);
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
			case SamannotationPackage.ANNOTATION__SOURCE:
				return getSource();
			case SamannotationPackage.ANNOTATION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case SamannotationPackage.ANNOTATION__DETAILS:
				return getDetails();
			case SamannotationPackage.ANNOTATION__REF_IN_RULE:
				if (resolve) return getRefInRule();
				return basicGetRefInRule();
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
			case SamannotationPackage.ANNOTATION__SOURCE:
				setSource((String)newValue);
				return;
			case SamannotationPackage.ANNOTATION__TARGET:
				setTarget((EObject)newValue);
				return;
			case SamannotationPackage.ANNOTATION__DETAILS:
				getDetails().clear();
				getDetails().addAll((Collection<? extends AnnotationDetails>)newValue);
				return;
			case SamannotationPackage.ANNOTATION__REF_IN_RULE:
				setRefInRule((Annotation)newValue);
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
			case SamannotationPackage.ANNOTATION__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case SamannotationPackage.ANNOTATION__TARGET:
				setTarget((EObject)null);
				return;
			case SamannotationPackage.ANNOTATION__DETAILS:
				getDetails().clear();
				return;
			case SamannotationPackage.ANNOTATION__REF_IN_RULE:
				setRefInRule((Annotation)null);
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
			case SamannotationPackage.ANNOTATION__SOURCE:
				return SOURCE_EDEFAULT == null ? source != null : !SOURCE_EDEFAULT.equals(source);
			case SamannotationPackage.ANNOTATION__TARGET:
				return target != null;
			case SamannotationPackage.ANNOTATION__DETAILS:
				return details != null && !details.isEmpty();
			case SamannotationPackage.ANNOTATION__REF_IN_RULE:
				return refInRule != null;
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
		result.append(" (source: ");
		result.append(source);
		result.append(')');
		return result.toString();
	}

} //AnnotationImpl
