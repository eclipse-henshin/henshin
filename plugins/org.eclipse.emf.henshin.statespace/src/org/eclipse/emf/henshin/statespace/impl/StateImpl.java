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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.resources.StateSpaceResource;

/**
 * Concrete implementation of the {@link State} interface.
 * @generated
 */
public class StateImpl extends StorageImpl implements State {
	
	/**
	 * Check whether this state is an initial one.
	 * @generated NOT
	 */
	public boolean isInitial() {
		return model!=null && model.getURI()!=null;
	}
	
	/**
	 * Check whether this state is terminal.
	 * @generated NOT
	 */
	public boolean isTerminal() {
		return !isOpen() && getOutgoing().isEmpty();
	}
	
	/**
	 * @generated NOT
	 */
	public boolean isOpen() {
		return (getData(1)!=0);
	}

	/**
	 * @generated NOT
	 */
	public void setOpen(boolean open) {
		setData(1, open ? 1 : 0);
	}
	
	/**
	 * @generated NOT
	 */
	public int getHashCode() {
		return getData(0);
	}

	/**
	 * @generated NOT
	 */
	public void setHashCode(int hashCode) {
		setData(0,hashCode);
	}
	
	/**
	 * @generated NOT
	 */
	public int[] getLocation() {
		return getData(2, 4);
	}

	/**
	 * @generated NOT
	 */
	public void setLocation(int... newLocation) {
		setData(2, newLocation);
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
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final int[] LOCATION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isOpen() <em>Open</em>}' attribute.
	 * @see #isOpen()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPEN_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getHashCode() <em>Hash Code</em>}' attribute.
	 * @see #getHashCode()
	 * @generated
	 * @ordered
	 */
	protected static final int HASH_CODE_EDEFAULT = 0;

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
		return StateSpacePackage.Literals.STATE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE__NAME, oldName, name));
	}

	/**
	 * Get the list of incoming transitions of this state.
	 * @generated
	 */
	public EList<Transition> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Transition>(Transition.class, this, StateSpacePackage.STATE__INCOMING, StateSpacePackage.TRANSITION__TARGET);
		}
		return incoming;
	}
	
	/**
	 * Get the list of outgoing transitions of this state.
	 * @generated
	 */
	public EList<Transition> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectContainmentWithInverseEList<Transition>(Transition.class, this, StateSpacePackage.STATE__OUTGOING, StateSpacePackage.TRANSITION__SOURCE);
		}
		return outgoing;
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
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE__MODEL, oldModel, model));
	}

	/**
	 * @generated
	 */
	public StateSpace getStateSpace() {
		if (eContainerFeatureID() != StateSpacePackage.STATE__STATE_SPACE) return null;
		return (StateSpace)eContainer();
	}
	
	/**
	 * @generated
	 */
	public NotificationChain basicSetStateSpace(StateSpace newStateSpace, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newStateSpace, StateSpacePackage.STATE__STATE_SPACE, msgs);
		return msgs;
	}
	
	/**
	 * @generated
	 */
	public void setStateSpace(StateSpace newStateSpace) {
		if (newStateSpace != eInternalContainer() || (eContainerFeatureID() != StateSpacePackage.STATE__STATE_SPACE && newStateSpace != null)) {
			if (EcoreUtil.isAncestor(this, newStateSpace))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newStateSpace != null)
				msgs = ((InternalEObject)newStateSpace).eInverseAdd(this, StateSpacePackage.STATE_SPACE__STATES, StateSpace.class, msgs);
			msgs = basicSetStateSpace(newStateSpace, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE__STATE_SPACE, newStateSpace, newStateSpace));
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.STATE__INCOMING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncoming()).basicAdd(otherEnd, msgs);
			case StateSpacePackage.STATE__OUTGOING:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoing()).basicAdd(otherEnd, msgs);
			case StateSpacePackage.STATE__STATE_SPACE:
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
			case StateSpacePackage.STATE__INCOMING:
				return ((InternalEList<?>)getIncoming()).basicRemove(otherEnd, msgs);
			case StateSpacePackage.STATE__OUTGOING:
				return ((InternalEList<?>)getOutgoing()).basicRemove(otherEnd, msgs);
			case StateSpacePackage.STATE__STATE_SPACE:
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
			case StateSpacePackage.STATE__STATE_SPACE:
				return eInternalContainer().eInverseRemove(this, StateSpacePackage.STATE_SPACE__STATES, StateSpace.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE__NAME:
				return getName();
			case StateSpacePackage.STATE__INCOMING:
				return getIncoming();
			case StateSpacePackage.STATE__OUTGOING:
				return getOutgoing();
			case StateSpacePackage.STATE__MODEL:
				return getModel();
			case StateSpacePackage.STATE__STATE_SPACE:
				return getStateSpace();
			case StateSpacePackage.STATE__LOCATION:
				return getLocation();
			case StateSpacePackage.STATE__OPEN:
				return isOpen();
			case StateSpacePackage.STATE__HASH_CODE:
				return getHashCode();
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
			case StateSpacePackage.STATE__NAME:
				setName((String)newValue);
				return;
			case StateSpacePackage.STATE__INCOMING:
				getIncoming().clear();
				getIncoming().addAll((Collection<? extends Transition>)newValue);
				return;
			case StateSpacePackage.STATE__OUTGOING:
				getOutgoing().clear();
				getOutgoing().addAll((Collection<? extends Transition>)newValue);
				return;
			case StateSpacePackage.STATE__MODEL:
				setModel((Resource)newValue);
				return;
			case StateSpacePackage.STATE__STATE_SPACE:
				setStateSpace((StateSpace)newValue);
				return;
			case StateSpacePackage.STATE__LOCATION:
				setLocation((int[])newValue);
				return;
			case StateSpacePackage.STATE__OPEN:
				setOpen((Boolean)newValue);
				return;
			case StateSpacePackage.STATE__HASH_CODE:
				setHashCode((Integer)newValue);
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
			case StateSpacePackage.STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case StateSpacePackage.STATE__INCOMING:
				getIncoming().clear();
				return;
			case StateSpacePackage.STATE__OUTGOING:
				getOutgoing().clear();
				return;
			case StateSpacePackage.STATE__MODEL:
				setModel(MODEL_EDEFAULT);
				return;
			case StateSpacePackage.STATE__STATE_SPACE:
				setStateSpace((StateSpace)null);
				return;
			case StateSpacePackage.STATE__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case StateSpacePackage.STATE__OPEN:
				setOpen(OPEN_EDEFAULT);
				return;
			case StateSpacePackage.STATE__HASH_CODE:
				setHashCode(HASH_CODE_EDEFAULT);
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
			case StateSpacePackage.STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case StateSpacePackage.STATE__INCOMING:
				return incoming != null && !incoming.isEmpty();
			case StateSpacePackage.STATE__OUTGOING:
				return outgoing != null && !outgoing.isEmpty();
			case StateSpacePackage.STATE__MODEL:
				return MODEL_EDEFAULT == null ? model != null : !MODEL_EDEFAULT.equals(model);
			case StateSpacePackage.STATE__STATE_SPACE:
				return getStateSpace() != null;
			case StateSpacePackage.STATE__LOCATION:
				return LOCATION_EDEFAULT == null ? getLocation() != null : !LOCATION_EDEFAULT.equals(getLocation());
			case StateSpacePackage.STATE__OPEN:
				return isOpen() != OPEN_EDEFAULT;
			case StateSpacePackage.STATE__HASH_CODE:
				return getHashCode() != HASH_CODE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} //StateImpl
