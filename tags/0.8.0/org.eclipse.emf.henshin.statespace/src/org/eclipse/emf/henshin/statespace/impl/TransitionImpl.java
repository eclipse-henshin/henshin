/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.impl;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.properties.ParametersPropertiesManager;

/**
 * @generated
 */
public class TransitionImpl extends StorageImpl implements Transition {
	
	/**
	 * Labels are not cached by the transition!
	 * Should be cached externally, if needed.
	 * @generated NOT
	 */
	public String getLabel() {
		if (rule==null) return null;
		String label = rule.getName();
		if (getParameterCount()>0) {
			label = label + "(";
			char[] prefixes = getParamPrefixes();
			int[] params = getParameterIDs();
			int count = Math.min(prefixes.length, params.length);
			for (int i=0; i<count; i++) {
				label = label + prefixes[i] + params[i];
				if (i<count-1) label = label + ",";
			}
			label = label + ")";
		}
		return label;
	}
	
	/*
	 * Private helper for computing the prefixes of parameters.
	 */
	private char[] getParamPrefixes() {
		if (getSource()==null || getSource().getStateSpace()==null) {
			return new char[0];
		}
		List<Node> nodes;
		try {
			nodes = ParametersPropertiesManager.getParameters(getSource().getStateSpace(), rule);
		} catch (StateSpaceException e) {
			throw new RuntimeException(e);
		}
		char[] prefixes = new char[nodes.size()];
		for (int i=0; i<prefixes.length; i++) {
			EClass type = nodes.get(i).getType();
			if (type!=null && type.getName()!=null) {
				prefixes[i] = type.getName().toLowerCase().charAt(0);
			} else {
				prefixes[i] = 'x';
			}
		}
		return prefixes;
	}
	
	/**
	 * @generated NOT
	 */
	public int getMatch() {
		return getData(0);
	}

	/**
	 * @generated NOT
	 */
	public void setMatch(int match) {
		setData(0, match);
	}

	/**
	 * @generated NOT
	 */
	public int getParameterCount() {
		return getData(1);
	}

	/**
	 * @generated NOT
	 */
	public void setParameterCount(int paramCount) {
		setData(1, paramCount);
	}

	/**
	 * @generated NOT
	 */
	public int[] getParameterIDs() {
		return getData(2, 2+getParameterCount());
	}

	/**
	 * @generated NOT
	 */
	public void setParameterIDs(int[] paramIDs) {
		setData(2, paramIDs);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		if (getSource()!=null && target!=null && rule!=null) {
			return getSource().getIndex() + " -- " + getLabel() + " -> " + target.getIndex();
		} else {
			return super.toString();
		}
	}


	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected State target;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' attribute.
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected Rule rule;

	/**
	 * The default value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected static final int MATCH_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getParameterCount() <em>Parameter Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterCount()
	 * @generated
	 * @ordered
	 */
	protected static final int PARAMETER_COUNT_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getParameterIDs() <em>Parameter IDs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterIDs()
	 * @generated
	 * @ordered
	 */
	protected static final int[] PARAMETER_IDS_EDEFAULT = null;

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
		return StateSpacePackage.Literals.TRANSITION;
	}

	/**
	 * @generated
	 */
	public State getSource() {
		if (eContainerFeatureID() != StateSpacePackage.TRANSITION__SOURCE) return null;
		return (State)eContainer();
	}

	/**
	 * @generated
	 */
	public NotificationChain basicSetSource(State newSource, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSource, StateSpacePackage.TRANSITION__SOURCE, msgs);
		return msgs;
	}

	/**
	 * @generated
	 */
	public void setSource(State newSource) {
		if (newSource != eInternalContainer() || (eContainerFeatureID() != StateSpacePackage.TRANSITION__SOURCE && newSource != null)) {
			if (EcoreUtil.isAncestor(this, newSource))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, StateSpacePackage.STATE__OUTGOING, State.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.TRANSITION__SOURCE, newSource, newSource));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateSpacePackage.TRANSITION__TARGET, oldTarget, target));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, StateSpacePackage.TRANSITION__TARGET, oldTarget, newTarget);
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
				msgs = ((InternalEObject)target).eInverseRemove(this, StateSpacePackage.STATE__INCOMING, State.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, StateSpacePackage.STATE__INCOMING, State.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.TRANSITION__TARGET, newTarget, newTarget));
	}

	/**
	 * @generated
	 */
	public Rule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (Rule)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateSpacePackage.TRANSITION__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(Rule newRule) {
		Rule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.TRANSITION__RULE, oldRule, rule));
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.TRANSITION__SOURCE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSource((State)otherEnd, msgs);
			case StateSpacePackage.TRANSITION__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, StateSpacePackage.STATE__INCOMING, State.class, msgs);
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
			case StateSpacePackage.TRANSITION__SOURCE:
				return basicSetSource(null, msgs);
			case StateSpacePackage.TRANSITION__TARGET:
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
			case StateSpacePackage.TRANSITION__SOURCE:
				return eInternalContainer().eInverseRemove(this, StateSpacePackage.STATE__OUTGOING, State.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.TRANSITION__SOURCE:
				return getSource();
			case StateSpacePackage.TRANSITION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case StateSpacePackage.TRANSITION__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case StateSpacePackage.TRANSITION__MATCH:
				return getMatch();
			case StateSpacePackage.TRANSITION__PARAMETER_COUNT:
				return getParameterCount();
			case StateSpacePackage.TRANSITION__PARAMETER_IDS:
				return getParameterIDs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.TRANSITION__SOURCE:
				setSource((State)newValue);
				return;
			case StateSpacePackage.TRANSITION__TARGET:
				setTarget((State)newValue);
				return;
			case StateSpacePackage.TRANSITION__RULE:
				setRule((Rule)newValue);
				return;
			case StateSpacePackage.TRANSITION__MATCH:
				setMatch((Integer)newValue);
				return;
			case StateSpacePackage.TRANSITION__PARAMETER_COUNT:
				setParameterCount((Integer)newValue);
				return;
			case StateSpacePackage.TRANSITION__PARAMETER_IDS:
				setParameterIDs((int[])newValue);
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
			case StateSpacePackage.TRANSITION__SOURCE:
				setSource((State)null);
				return;
			case StateSpacePackage.TRANSITION__TARGET:
				setTarget((State)null);
				return;
			case StateSpacePackage.TRANSITION__RULE:
				setRule((Rule)null);
				return;
			case StateSpacePackage.TRANSITION__MATCH:
				setMatch(MATCH_EDEFAULT);
				return;
			case StateSpacePackage.TRANSITION__PARAMETER_COUNT:
				setParameterCount(PARAMETER_COUNT_EDEFAULT);
				return;
			case StateSpacePackage.TRANSITION__PARAMETER_IDS:
				setParameterIDs(PARAMETER_IDS_EDEFAULT);
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
			case StateSpacePackage.TRANSITION__SOURCE:
				return getSource() != null;
			case StateSpacePackage.TRANSITION__TARGET:
				return target != null;
			case StateSpacePackage.TRANSITION__RULE:
				return rule != null;
			case StateSpacePackage.TRANSITION__MATCH:
				return getMatch() != MATCH_EDEFAULT;
			case StateSpacePackage.TRANSITION__PARAMETER_COUNT:
				return getParameterCount() != PARAMETER_COUNT_EDEFAULT;
			case StateSpacePackage.TRANSITION__PARAMETER_IDS:
				return PARAMETER_IDS_EDEFAULT == null ? getParameterIDs() != null : !PARAMETER_IDS_EDEFAULT.equals(getParameterIDs());
		}
		return super.eIsSet(featureID);
	}

} //TransitionImpl
