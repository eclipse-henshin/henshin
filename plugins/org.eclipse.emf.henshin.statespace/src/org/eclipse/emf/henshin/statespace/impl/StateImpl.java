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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.statespace.impl.StateImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.statespace.impl.StateImpl#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.statespace.impl.StateImpl#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.statespace.impl.StateImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.statespace.impl.StateImpl#getModel <em>Model</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.statespace.impl.StateImpl#getStateSpace <em>State Space</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends MinimalEObjectImpl implements State {
	
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncoming() <em>Incoming</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncoming()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> incoming;

	/**
	 * The cached value of the '{@link #getOutgoing() <em>Outgoing</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoing()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> outgoing;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final int[] LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected int[] location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected static final Resource MODEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModel() <em>Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModel()
	 * @generated
	 * @ordered
	 */
	protected Resource model = MODEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackageImpl.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getIncoming() {
		if (incoming == null) {
			incoming = new EObjectWithInverseResolvingEList<Transition>(Transition.class, this, StateSpacePackageImpl.STATE__INCOMING, StateSpacePackageImpl.TRANSITION__TARGET);
		}
		return incoming;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getOutgoing() {
		if (outgoing == null) {
			outgoing = new EObjectWithInverseEList<Transition>(Transition.class, this, StateSpacePackageImpl.STATE__OUTGOING, StateSpacePackageImpl.TRANSITION__SOURCE);
		}
		return outgoing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int[] getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setLocation(int... newLocation) {
		int[] oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE__LOCATION, oldLocation, location));
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource getModel() {
		return model;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModel(Resource newModel) {
		Resource oldModel = model;
		model = newModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackageImpl.STATE__MODEL, oldModel, model));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateSpace getStateSpace() {
		if (eContainerFeatureID() != StateSpacePackageImpl.STATE__STATE_SPACE) return null;
		return (StateSpace)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStateSpace(StateSpace newStateSpace, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newStateSpace, StateSpacePackageImpl.STATE__STATE_SPACE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getX() {
		return (location!=null && location.length>0) ? location[0] : 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getY() {
		return (location!=null && location.length>1) ? location[1] : 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isInitial() {
		return model!=null && model.getURI()!=null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
			case StateSpacePackageImpl.STATE__LOCATION:
				return getLocation();
			case StateSpacePackageImpl.STATE__MODEL:
				return getModel();
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				return getStateSpace();
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
			case StateSpacePackageImpl.STATE__LOCATION:
				setLocation((int[])newValue);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
			case StateSpacePackageImpl.STATE__LOCATION:
				setLocation(LOCATION_EDEFAULT);
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
			case StateSpacePackageImpl.STATE__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case StateSpacePackageImpl.STATE__MODEL:
				return MODEL_EDEFAULT == null ? model != null : !MODEL_EDEFAULT.equals(model);
			case StateSpacePackageImpl.STATE__STATE_SPACE:
				return getStateSpace() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String toString() {
		
		StringBuffer result = new StringBuffer();
		result.append(name + "[x=" + getX() + ",y=" + getY() + "]");
		
		// Outgoing transitions:
		if (!getOutgoing().isEmpty()) {
			result.append(" --");
			for (Transition transition : getOutgoing()) {
				result.append(" (" + transition.getRule() + "," + transition.getTarget().getName() + ")");
			}
		}
		result.append(";");
		return result.toString();
		
	}

} //StateImpl
