/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.resources.StateSpaceResource;

/**
 * Concrete implementation of the {@link State} interface.
 * @generated
 */
public class StateImpl extends AttributeHolderImpl implements State {
	
	/**
	 * Check whether this state is an initial one.
	 * @generated NOT
	 */
	public boolean isInitial() {
		return model!=null && model.getURI()!=null;
	}
	
	/**
	 * Get the list of outgoing transitions of this state.
	 * @generated NOT
	 */
	public EList<Transition> getOutgoing() {
		if (outgoing == null) {
			outgoing = new StateOutgoingTransitionsEList(this);
		}
		return outgoing;
		
	}
	
	/**
	 * Set the state space that contains this state.
	 * @generated NOT
	 */
	public NotificationChain basicSetStateSpace(StateSpace newStateSpace, NotificationChain msgs) {
		
		// Is this state explored?
		boolean explored = StateAttributes.isExplored(this);
		
		if (getStateSpace()!=null) {
			// Decrease explored count in old state space:
			if (explored) {
				((StateSpaceImpl) getStateSpace()).internalSetExploredCount(getStateSpace().getExploredCount() - 1);
			}
			// Update the number of transitions:
			((StateSpaceImpl) getStateSpace()).internalSetTransitionCount(getStateSpace().getTransitionCount() - getOutgoing().size());
		}
		
		// Set the new state space:
		msgs = basicSetStateSpaceGen(newStateSpace, msgs);
		
		if (getStateSpace()!=null) {
			// Increase the explored count in the new state space:
			if (explored) {
				((StateSpaceImpl) newStateSpace).internalSetExploredCount(newStateSpace.getExploredCount() + 1);
			}
			// Update the number of transitions:
			((StateSpaceImpl) getStateSpace()).internalSetTransitionCount(getStateSpace().getTransitionCount() + getOutgoing().size());
		}
		
		return msgs;
		
	}
	
	/**
	 * Pretty-print this state.
	 * @generated NOT
	 */
	@Override
	public String toString() {
		return StateSpaceResource.printState(this);
	}
	
	
	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> incoming;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' containment reference list.
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> outgoing;

	/**
	 * The default value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected static final Resource MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected Resource model = MODEL_EDEFAULT;

	/**
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackageImpl.Literals.STATE;
	}

	/**
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE__NAME, oldName, name));
	}

	/**
	 * @generated
	 */
	public EList<Transition> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Transition>(Transition.class, this, StateSpacePackageImpl.STATE__INCOMING, StateSpacePackageImpl.TRANSITION__TARGET);
		}
		return incoming;
	}
	
	/**
	 * @generated
	 */
	public Resource getModel() {
		return model;
	}

	/**
	 * @generated
	 */
	public void setModel(Resource newModel) {
		Resource oldModel = model;
		model = newModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE__MODEL, oldModel, model));
	}

	/**
	 * @generated
	 */
	public StateSpace getStateSpace() {
		if (eContainerFeatureID() != StateSpacePackageImpl.STATE__STATE_SPACE) return null;
		return (StateSpace)eContainer();
	}
	
	/**
	 * @generated
	 */
	public NotificationChain basicSetStateSpaceGen(StateSpace newStateSpace, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newStateSpace, StateSpacePackageImpl.STATE__STATE_SPACE, msgs);
		return msgs;
	}
	
	/**
	 * @generated
	 */
	public void setStateSpace(StateSpace newStateSpace) {
		if (newStateSpace != eInternalContainer() || (eContainerFeatureID() != StateSpacePackageImpl.STATE__STATE_SPACE && newStateSpace != null)) {
			if (EcoreUtil.isAncestor(this, newStateSpace))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newStateSpace != null)
				msgs = ((InternalEObject)newStateSpace).eInverseAdd(this, StateSpacePackageImpl.STATE_SPACE__STATES, StateSpace.class, msgs);
			msgs = basicSetStateSpace(newStateSpace, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE__STATE_SPACE, newStateSpace, newStateSpace));
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case StateSpacePackageImpl.STATE__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetStateSpace((StateSpace)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case StateSpacePackageImpl.STATE__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				return basicSetStateSpace(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				return eInternalContainer().eInverseRemove(this, StateSpacePackageImpl.STATE_SPACE__STATES, StateSpace.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackageImpl.STATE__NAME:
				return getName();
			case StateSpacePackageImpl.STATE__INCOMING:
				return getIncoming();
			case StateSpacePackageImpl.STATE__OUTGOING:
				return getOutgoing();
			case StateSpacePackageImpl.STATE__MODEL:
				return getModel();
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				return getStateSpace();
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
			case StateSpacePackageImpl.STATE__NAME:
				setName((String)newValue);
				return;
			case StateSpacePackageImpl.STATE__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends Transition>)newValue);
				return;
			case StateSpacePackageImpl.STATE__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends Transition>)newValue);
				return;
			case StateSpacePackageImpl.STATE__MODEL:
				setModel((Resource)newValue);
				return;
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				setStateSpace((StateSpace)newValue);
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
			case StateSpacePackageImpl.STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StateSpacePackageImpl.STATE__INCOMING:
				getIncoming().clear();
				return;
			case StateSpacePackageImpl.STATE__OUTGOING:
				getOutgoing().clear();
				return;
			case StateSpacePackageImpl.STATE__MODEL:
				setModel(MODEL_EDEFAULT);
				return;
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				setStateSpace((StateSpace)null);
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
			case StateSpacePackageImpl.STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StateSpacePackageImpl.STATE__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case StateSpacePackageImpl.STATE__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case StateSpacePackageImpl.STATE__MODEL:
				return MODEL_EDEFAULT == null ? model != null : !MODEL_EDEFAULT.equals(model);
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				return getStateSpace() != null;
		}
		return super.eIsSet(featureID);
	}

} //StateImpl
