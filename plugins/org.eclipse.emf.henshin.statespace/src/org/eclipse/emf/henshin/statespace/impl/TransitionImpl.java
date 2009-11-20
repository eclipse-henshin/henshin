/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * @generated
 */
public class TransitionImpl extends AttributeHolderImpl implements Transition {
	
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected State target;

	/**
	 * The default value of the '{@link #getRule() <em>Rule</em>}' attribute.
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' attribute.
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected String rule = RULE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected static final int MATCH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected int match = MATCH_EDEFAULT;

	/**
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackageImpl.Literals.TRANSITION;
	}

	/**
	 * @generated
	 */
	public State getSource() {
		if (eContainerFeatureID() != StateSpacePackageImpl.TRANSITION__SOURCE) return null;
		return (State)eContainer();
	}

	/**
	 * @generated
	 */
	public NotificationChain basicSetSource(State newSource, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSource, StateSpacePackageImpl.TRANSITION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * @generated
	 */
	public void setSource(State newSource) {
		if (newSource != eInternalContainer() || (eContainerFeatureID() != StateSpacePackageImpl.TRANSITION__SOURCE && newSource != null)) {
			if (EcoreUtil.isAncestor(this, newSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, StateSpacePackageImpl.STATE__OUTGOING, State.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.TRANSITION__SOURCE, newSource, newSource));
	}

	/**
	 * @generated
	 */
	public State getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (State)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateSpacePackageImpl.TRANSITION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * @generated
	 */
	public State basicGetTarget() {
		return target;
	}

	/**
	 * @generated
	 */
	public NotificationChain basicSetTarget(State newTarget, NotificationChain msgs) {
		State oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.TRANSITION__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * @generated
	 */
	public void setTarget(State newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, StateSpacePackageImpl.STATE__INCOMING, State.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, StateSpacePackageImpl.STATE__INCOMING, State.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.TRANSITION__TARGET, newTarget, newTarget));
	}

	/**
	 * @generated
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @generated
	 */
	public void setRule(String newRule) {
		String oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.TRANSITION__RULE, oldRule, rule));
	}

	/**
	 * @generated
	 */
	public int getMatch() {
		return match;
	}

	/**
	 * @generated
	 */
	public void setMatch(int newMatch) {
		int oldMatch = match;
		match = newMatch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.TRANSITION__MATCH, oldMatch, match));
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSource((State)otherEnd, msgs);
			case StateSpacePackageImpl.TRANSITION__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, StateSpacePackageImpl.STATE__INCOMING, State.class, msgs);
				return basicSetTarget((State)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				return basicSetSource(null, msgs);
			case StateSpacePackageImpl.TRANSITION__TARGET:
				return basicSetTarget(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				return eInternalContainer().eInverseRemove(this, StateSpacePackageImpl.STATE__OUTGOING, State.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				return getSource();
			case StateSpacePackageImpl.TRANSITION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case StateSpacePackageImpl.TRANSITION__RULE:
				return getRule();
			case StateSpacePackageImpl.TRANSITION__MATCH:
				return getMatch();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				setSource((State)newValue);
				return;
			case StateSpacePackageImpl.TRANSITION__TARGET:
				setTarget((State)newValue);
				return;
			case StateSpacePackageImpl.TRANSITION__RULE:
				setRule((String)newValue);
				return;
			case StateSpacePackageImpl.TRANSITION__MATCH:
				setMatch((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				setSource((State)null);
				return;
			case StateSpacePackageImpl.TRANSITION__TARGET:
				setTarget((State)null);
				return;
			case StateSpacePackageImpl.TRANSITION__RULE:
				setRule(RULE_EDEFAULT);
				return;
			case StateSpacePackageImpl.TRANSITION__MATCH:
				setMatch(MATCH_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackageImpl.TRANSITION__SOURCE:
				return getSource() != null;
			case StateSpacePackageImpl.TRANSITION__TARGET:
				return target != null;
			case StateSpacePackageImpl.TRANSITION__RULE:
				return RULE_EDEFAULT == null ? rule != null : !RULE_EDEFAULT.equals(rule);
			case StateSpacePackageImpl.TRANSITION__MATCH:
				return match != MATCH_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (rule: ");
		result.append(rule);
		result.append(", match: ");
		result.append(match);
		result.append(')');
		return result.toString();
	}

} //TransitionImpl
