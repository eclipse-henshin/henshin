package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Wrapper around state space that manages exclusive access to it.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceAccessorImpl extends AdapterImpl {

	// Thread that is allowed to change the state space:
	private Thread accessingThread;
	
	// State space:
	private StateSpace stateSpace;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public StateSpaceAccessorImpl(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
		stateSpace.eAdapters().add(this);
	}
	
	/*
	 * Request an access to the state space.
	 */
	protected synchronized boolean access() {
		if (accessingThread==null) {
			accessingThread = Thread.currentThread();
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Release access again.
	 */
	protected synchronized boolean release() {
		if (accessingThread==Thread.currentThread()) {
			accessingThread = null;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Get the state space,
	 * @return State space.
	 */
	protected StateSpace getStateSpace() {
		return stateSpace;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification event) {
		if (event.getNotifier()==stateSpace && accessingThread!=Thread.currentThread()) {
			throw new RuntimeException("Illegal state space access");
		}
	}
	
}
