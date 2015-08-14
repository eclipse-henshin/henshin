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
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.model.samgraph.Edge;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.EdgeImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pattern Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternEdgeImpl#getSameInRule <em>Same In Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.impl.PatternEdgeImpl#getSameInProp <em>Same In Prop</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PatternEdgeImpl extends EdgeImpl implements PatternEdge {
	/**
	 * The cached value of the '{@link #getSameInRule() <em>Same In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSameInRule()
	 * @generated
	 * @ordered
	 */
	protected Edge sameInRule;

	/**
	 * The cached value of the '{@link #getSameInProp() <em>Same In Prop</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSameInProp()
	 * @generated
	 * @ordered
	 */
	protected Edge sameInProp;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PatternEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NacPackage.Literals.PATTERN_EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getSameInRule() {
		if (sameInRule != null && sameInRule.eIsProxy()) {
			InternalEObject oldSameInRule = (InternalEObject)sameInRule;
			sameInRule = (Edge)eResolveProxy(oldSameInRule);
			if (sameInRule != oldSameInRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NacPackage.PATTERN_EDGE__SAME_IN_RULE, oldSameInRule, sameInRule));
			}
		}
		return sameInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetSameInRule() {
		return sameInRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSameInRule(Edge newSameInRule) {
		Edge oldSameInRule = sameInRule;
		sameInRule = newSameInRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NacPackage.PATTERN_EDGE__SAME_IN_RULE, oldSameInRule, sameInRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge getSameInProp() {
		if (sameInProp != null && sameInProp.eIsProxy()) {
			InternalEObject oldSameInProp = (InternalEObject)sameInProp;
			sameInProp = (Edge)eResolveProxy(oldSameInProp);
			if (sameInProp != oldSameInProp) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NacPackage.PATTERN_EDGE__SAME_IN_PROP, oldSameInProp, sameInProp));
			}
		}
		return sameInProp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge basicGetSameInProp() {
		return sameInProp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSameInProp(Edge newSameInProp) {
		Edge oldSameInProp = sameInProp;
		sameInProp = newSameInProp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NacPackage.PATTERN_EDGE__SAME_IN_PROP, oldSameInProp, sameInProp));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NacPackage.PATTERN_EDGE__SAME_IN_RULE:
				if (resolve) return getSameInRule();
				return basicGetSameInRule();
			case NacPackage.PATTERN_EDGE__SAME_IN_PROP:
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
			case NacPackage.PATTERN_EDGE__SAME_IN_RULE:
				setSameInRule((Edge)newValue);
				return;
			case NacPackage.PATTERN_EDGE__SAME_IN_PROP:
				setSameInProp((Edge)newValue);
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
			case NacPackage.PATTERN_EDGE__SAME_IN_RULE:
				setSameInRule((Edge)null);
				return;
			case NacPackage.PATTERN_EDGE__SAME_IN_PROP:
				setSameInProp((Edge)null);
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
			case NacPackage.PATTERN_EDGE__SAME_IN_RULE:
				return sameInRule != null;
			case NacPackage.PATTERN_EDGE__SAME_IN_PROP:
				return sameInProp != null;
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * @generated NOT
	 */
	public String toString() {
		return this.getSource().toString() + "--" + this.getTarget().toString();
	}

} //PatternEdgeImpl
