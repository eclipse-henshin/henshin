/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samgraph.Node;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.NodeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pattern Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternNodeImpl#getSameInRule <em>Same In Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternNodeImpl#getSameInProp <em>Same In Prop</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatternNodeImpl extends NodeImpl implements PatternNode {
	/**
	 * The cached value of the '{@link #getSameInRule() <em>Same In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSameInRule()
	 * @generated
	 * @ordered
	 */
	protected Node sameInRule;

	/**
	 * The cached value of the '{@link #getSameInProp() <em>Same In Prop</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSameInProp()
	 * @generated
	 * @ordered
	 */
	protected Node sameInProp;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NacPackage.Literals.PATTERN_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSameInRule() {
		if (sameInRule != null && sameInRule.eIsProxy()) {
			InternalEObject oldSameInRule = (InternalEObject)sameInRule;
			sameInRule = (Node)eResolveProxy(oldSameInRule);
			if (sameInRule != oldSameInRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NacPackage.PATTERN_NODE__SAME_IN_RULE, oldSameInRule, sameInRule));
			}
		}
		return sameInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSameInRule() {
		return sameInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSameInRule(Node newSameInRule) {
		Node oldSameInRule = sameInRule;
		sameInRule = newSameInRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NacPackage.PATTERN_NODE__SAME_IN_RULE, oldSameInRule, sameInRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node getSameInProp() {
		if (sameInProp != null && sameInProp.eIsProxy()) {
			InternalEObject oldSameInProp = (InternalEObject)sameInProp;
			sameInProp = (Node)eResolveProxy(oldSameInProp);
			if (sameInProp != oldSameInProp) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NacPackage.PATTERN_NODE__SAME_IN_PROP, oldSameInProp, sameInProp));
			}
		}
		return sameInProp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSameInProp() {
		return sameInProp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSameInProp(Node newSameInProp) {
		Node oldSameInProp = sameInProp;
		sameInProp = newSameInProp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NacPackage.PATTERN_NODE__SAME_IN_PROP, oldSameInProp, sameInProp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NacPackage.PATTERN_NODE__SAME_IN_RULE:
				if (resolve) return getSameInRule();
				return basicGetSameInRule();
			case NacPackage.PATTERN_NODE__SAME_IN_PROP:
				if (resolve) return getSameInProp();
				return basicGetSameInProp();
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
			case NacPackage.PATTERN_NODE__SAME_IN_RULE:
				setSameInRule((Node)newValue);
				return;
			case NacPackage.PATTERN_NODE__SAME_IN_PROP:
				setSameInProp((Node)newValue);
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
			case NacPackage.PATTERN_NODE__SAME_IN_RULE:
				setSameInRule((Node)null);
				return;
			case NacPackage.PATTERN_NODE__SAME_IN_PROP:
				setSameInProp((Node)null);
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
			case NacPackage.PATTERN_NODE__SAME_IN_RULE:
				return sameInRule != null;
			case NacPackage.PATTERN_NODE__SAME_IN_PROP:
				return sameInProp != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	public String toString() {
		return this.name + ":" + this.getInstanceOf().getName();
	}

} //PatternNodeImpl
