/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.NodeImpl;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Preserved Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.impl.PreservedNodeImpl#getRefInRule <em>Ref In Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PreservedNodeImpl extends NodeImpl implements PreservedNode {
	/**
	 * The cached value of the '{@link #getRefInRule() <em>Ref In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefInRule()
	 * @generated
	 * @ordered
	 */
	protected PreservedNode refInRule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreservedNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamrulesPackage.Literals.PRESERVED_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreservedNode getRefInRule() {
		if (refInRule != null && refInRule.eIsProxy()) {
			InternalEObject oldRefInRule = (InternalEObject)refInRule;
			refInRule = (PreservedNode)eResolveProxy(oldRefInRule);
			if (refInRule != oldRefInRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamrulesPackage.PRESERVED_NODE__REF_IN_RULE, oldRefInRule, refInRule));
			}
		}
		return refInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreservedNode basicGetRefInRule() {
		return refInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRefInRule(PreservedNode newRefInRule, NotificationChain msgs) {
		PreservedNode oldRefInRule = refInRule;
		refInRule = newRefInRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamrulesPackage.PRESERVED_NODE__REF_IN_RULE, oldRefInRule, newRefInRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRefInRule(PreservedNode newRefInRule) {
		if (newRefInRule != refInRule) {
			NotificationChain msgs = null;
			if (refInRule != null)
				msgs = ((InternalEObject)refInRule).eInverseRemove(this, SamrulesPackage.PRESERVED_NODE__REF_IN_RULE, PreservedNode.class, msgs);
			if (newRefInRule != null)
				msgs = ((InternalEObject)newRefInRule).eInverseAdd(this, SamrulesPackage.PRESERVED_NODE__REF_IN_RULE, PreservedNode.class, msgs);
			msgs = basicSetRefInRule(newRefInRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamrulesPackage.PRESERVED_NODE__REF_IN_RULE, newRefInRule, newRefInRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamrulesPackage.PRESERVED_NODE__REF_IN_RULE:
				if (refInRule != null)
					msgs = ((InternalEObject)refInRule).eInverseRemove(this, SamrulesPackage.PRESERVED_NODE__REF_IN_RULE, PreservedNode.class, msgs);
				return basicSetRefInRule((PreservedNode)otherEnd, msgs);
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
			case SamrulesPackage.PRESERVED_NODE__REF_IN_RULE:
				return basicSetRefInRule(null, msgs);
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
			case SamrulesPackage.PRESERVED_NODE__REF_IN_RULE:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SamrulesPackage.PRESERVED_NODE__REF_IN_RULE:
				setRefInRule((PreservedNode)newValue);
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
			case SamrulesPackage.PRESERVED_NODE__REF_IN_RULE:
				setRefInRule((PreservedNode)null);
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
			case SamrulesPackage.PRESERVED_NODE__REF_IN_RULE:
				return refInRule != null;
		}
		return super.eIsSet(featureID);
	}

} //PreservedNodeImpl
