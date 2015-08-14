/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace.impl;

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
import org.eclipse.emf.henshin.sam.model.samannotation.Annotation;
import org.eclipse.emf.henshin.sam.model.samgraph.Graph;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Discrete Trace Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl#getState <em>State</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl#getCurrentRule <em>Current Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtrace.impl.DiscreteTraceEntryImpl#getCurrentMatch <em>Current Match</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiscreteTraceEntryImpl extends EObjectImpl implements DiscreteTraceEntry {
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
	 * The cached value of the '{@link #getState() <em>State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected Graph state;

	/**
	 * The cached value of the '{@link #getCurrentRule() <em>Current Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentRule()
	 * @generated
	 * @ordered
	 */
	protected GraphRule currentRule;

	/**
	 * The cached value of the '{@link #getCurrentMatch() <em>Current Match</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCurrentMatch()
	 * @generated
	 * @ordered
	 */
	protected Match currentMatch;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DiscreteTraceEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SamtracePackage.Literals.DISCRETE_TRACE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectContainmentEList<Annotation>(Annotation.class, this, SamtracePackage.DISCRETE_TRACE_ENTRY__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Graph getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetState(Graph newState, NotificationChain msgs) {
		Graph oldState = state;
		state = newState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtracePackage.DISCRETE_TRACE_ENTRY__STATE, oldState, newState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(Graph newState) {
		if (newState != state) {
			NotificationChain msgs = null;
			if (state != null)
				msgs = ((InternalEObject)state).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamtracePackage.DISCRETE_TRACE_ENTRY__STATE, null, msgs);
			if (newState != null)
				msgs = ((InternalEObject)newState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamtracePackage.DISCRETE_TRACE_ENTRY__STATE, null, msgs);
			msgs = basicSetState(newState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtracePackage.DISCRETE_TRACE_ENTRY__STATE, newState, newState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GraphRule getCurrentRule() {
		if (currentRule != null && currentRule.eIsProxy()) {
			InternalEObject oldCurrentRule = (InternalEObject)currentRule;
			currentRule = (GraphRule)eResolveProxy(oldCurrentRule);
			if (currentRule != oldCurrentRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_RULE, oldCurrentRule, currentRule));
			}
		}
		return currentRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GraphRule basicGetCurrentRule() {
		return currentRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentRule(GraphRule newCurrentRule) {
		GraphRule oldCurrentRule = currentRule;
		currentRule = newCurrentRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_RULE, oldCurrentRule, currentRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Match getCurrentMatch() {
		return currentMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCurrentMatch(Match newCurrentMatch, NotificationChain msgs) {
		Match oldCurrentMatch = currentMatch;
		currentMatch = newCurrentMatch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH, oldCurrentMatch, newCurrentMatch);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrentMatch(Match newCurrentMatch) {
		if (newCurrentMatch != currentMatch) {
			NotificationChain msgs = null;
			if (currentMatch != null)
				msgs = ((InternalEObject)currentMatch).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH, null, msgs);
			if (newCurrentMatch != null)
				msgs = ((InternalEObject)newCurrentMatch).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH, null, msgs);
			msgs = basicSetCurrentMatch(newCurrentMatch, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH, newCurrentMatch, newCurrentMatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SamtracePackage.DISCRETE_TRACE_ENTRY__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case SamtracePackage.DISCRETE_TRACE_ENTRY__STATE:
				return basicSetState(null, msgs);
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH:
				return basicSetCurrentMatch(null, msgs);
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
			case SamtracePackage.DISCRETE_TRACE_ENTRY__ANNOTATIONS:
				return getAnnotations();
			case SamtracePackage.DISCRETE_TRACE_ENTRY__STATE:
				return getState();
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_RULE:
				if (resolve) return getCurrentRule();
				return basicGetCurrentRule();
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH:
				return getCurrentMatch();
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
			case SamtracePackage.DISCRETE_TRACE_ENTRY__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__STATE:
				setState((Graph)newValue);
				return;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_RULE:
				setCurrentRule((GraphRule)newValue);
				return;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH:
				setCurrentMatch((Match)newValue);
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
			case SamtracePackage.DISCRETE_TRACE_ENTRY__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__STATE:
				setState((Graph)null);
				return;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_RULE:
				setCurrentRule((GraphRule)null);
				return;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH:
				setCurrentMatch((Match)null);
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
			case SamtracePackage.DISCRETE_TRACE_ENTRY__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case SamtracePackage.DISCRETE_TRACE_ENTRY__STATE:
				return state != null;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_RULE:
				return currentRule != null;
			case SamtracePackage.DISCRETE_TRACE_ENTRY__CURRENT_MATCH:
				return currentMatch != null;
		}
		return super.eIsSet(featureID);
	}

} //DiscreteTraceEntryImpl
