/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Concrete implementation of the {@link State} interface.
 * @generated
 */
public class StateSpaceImpl extends MinimalEObjectImpl implements StateSpace {
	
	/**
	 * Get the list of states in this state space.
	 * @generated NOT
	 */
	public EList<State> getStates() {
		if (states == null) {
			states = new StateSpaceStatesEList(this);
		}
		return states;
	}
	
	/**
	 * Get the integer array for the explored states in this state space.
	 * @generated NOT
	 */
	public int[] getExplored() {
		// Make sure it is big enough:
		if (explored==null) {
			explored = new int[getMinimumExploredSize()];
		}
		else if (explored.length<getStates().size()) {
			explored = Arrays.copyOf(explored, getMinimumExploredSize());
		}
		return explored;
	}
	
	/*
	 * Minimum size of the explored-integer-array.
	 */
	private int getMinimumExploredSize() {
		return (int) (2 * getStates().size() / Integer.SIZE) + 4;
	}
	
	/**
	 * Set the number of explored states. For internal use only!
	 * @generated NOT
	 */
	public void internalSetExploredCount(int newExploredCount) {
		int oldExploredCount = exploredCount;
		exploredCount = newExploredCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE_SPACE__EXPLORED_COUNT, oldExploredCount, exploredCount));
	}
	
	/**
	 * Set the total number of transitions. For internal use only!
	 * @generated NOT
	 */
	public void internalSetTransitionCount(int newTransitionCount) {
		int oldTransitionCount = transitionCount;
		transitionCount = newTransitionCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE_SPACE__TRANSITION_COUNT, oldTransitionCount, transitionCount));
	}

	
	
	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The default value of the '{@link #getExplored() <em>Explored</em>}' attribute.
	 * @see #getExplored()
	 * @generated
	 * @ordered
	 */
	protected static final int[] EXPLORED_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getExplored() <em>Explored</em>}' attribute.
	 * @see #getExplored()
	 * @generated
	 * @ordered
	 */
	protected int[] explored = EXPLORED_EDEFAULT;

	/**
	 * The default value of the '{@link #getExploredCount() <em>Explored Count</em>}' attribute.
	 * @see #getExploredCount()
	 * @generated
	 * @ordered
	 */
	protected static final int EXPLORED_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getExploredCount() <em>Explored Count</em>}' attribute.
	 * @see #getExploredCount()
	 * @generated
	 * @ordered
	 */
	protected int exploredCount = EXPLORED_COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getTransitionCount() <em>Transition Count</em>}' attribute.
	 * @see #getTransitionCount()
	 * @generated
	 * @ordered
	 */
	protected static final int TRANSITION_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTransitionCount() <em>Transition Count</em>}' attribute.
	 * @see #getTransitionCount()
	 * @generated
	 * @ordered
	 */
	protected int transitionCount = TRANSITION_COUNT_EDEFAULT;

	/**
	 * @generated
	 */
	protected StateSpaceImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackageImpl.Literals.STATE_SPACE;
	}

	/**
	 * @generated
	 */
	public int getExploredCount() {
		return exploredCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTransitionCount() {
		return transitionCount;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE_SPACE__STATES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getStates()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE_SPACE__STATES:
				return ((InternalEList<?>)getStates()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE_SPACE__STATES:
				return getStates();
			case StateSpacePackageImpl.STATE_SPACE__EXPLORED:
				return getExplored();
			case StateSpacePackageImpl.STATE_SPACE__EXPLORED_COUNT:
				return getExploredCount();
			case StateSpacePackageImpl.STATE_SPACE__TRANSITION_COUNT:
				return getTransitionCount();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE_SPACE__STATES:
				getStates().clear();
				getStates().addAll((Collection<? extends State>)newValue);
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
			case StateSpacePackageImpl.STATE_SPACE__STATES:
				getStates().clear();
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
			case StateSpacePackageImpl.STATE_SPACE__STATES:
				return states != null && !states.isEmpty();
			case StateSpacePackageImpl.STATE_SPACE__EXPLORED:
				return EXPLORED_EDEFAULT == null ? explored != null : !EXPLORED_EDEFAULT.equals(explored);
			case StateSpacePackageImpl.STATE_SPACE__EXPLORED_COUNT:
				return exploredCount != EXPLORED_COUNT_EDEFAULT;
			case StateSpacePackageImpl.STATE_SPACE__TRANSITION_COUNT:
				return transitionCount != TRANSITION_COUNT_EDEFAULT;
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
		result.append(" (explored: ");
		result.append(explored);
		result.append(", exploredCount: ");
		result.append(exploredCount);
		result.append(", transitionCount: ");
		result.append(transitionCount);
		result.append(')');
		return result.toString();
	}

} //StateSpaceImpl
