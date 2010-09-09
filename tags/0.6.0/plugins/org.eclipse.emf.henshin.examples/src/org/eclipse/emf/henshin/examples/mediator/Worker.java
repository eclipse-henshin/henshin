/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.examples.mediator;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Worker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.Worker#getStart <em>Start</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.Worker#getStop <em>Stop</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.Worker#isActive <em>Active</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getWorker()
 * @model kind="class"
 * @generated
 */
public class Worker extends Channel {
	/**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
	protected Node start;

	/**
	 * The cached value of the '{@link #getStop() <em>Stop</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStop()
	 * @generated
	 * @ordered
	 */
	protected Node stop;

	/**
	 * The default value of the '{@link #isActive() <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isActive() <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActive()
	 * @generated
	 * @ordered
	 */
	protected boolean active = ACTIVE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Worker() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MediatorPackage.Literals.WORKER;
	}

	/**
	 * Returns the value of the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' reference.
	 * @see #setStart(Node)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getWorker_Start()
	 * @model
	 * @generated
	 */
	public Node getStart() {
		if (start != null && start.eIsProxy()) {
			InternalEObject oldStart = (InternalEObject)start;
			start = (Node)eResolveProxy(oldStart);
			if (start != oldStart) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediatorPackage.WORKER__START, oldStart, start));
			}
		}
		return start;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetStart() {
		return start;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.Worker#getStart <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' reference.
	 * @see #getStart()
	 * @generated
	 */
	public void setStart(Node newStart) {
		Node oldStart = start;
		start = newStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.WORKER__START, oldStart, start));
	}

	/**
	 * Returns the value of the '<em><b>Stop</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stop</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stop</em>' reference.
	 * @see #setStop(Node)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getWorker_Stop()
	 * @model
	 * @generated
	 */
	public Node getStop() {
		if (stop != null && stop.eIsProxy()) {
			InternalEObject oldStop = (InternalEObject)stop;
			stop = (Node)eResolveProxy(oldStop);
			if (stop != oldStop) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediatorPackage.WORKER__STOP, oldStop, stop));
			}
		}
		return stop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetStop() {
		return stop;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.Worker#getStop <em>Stop</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stop</em>' reference.
	 * @see #getStop()
	 * @generated
	 */
	public void setStop(Node newStop) {
		Node oldStop = stop;
		stop = newStop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.WORKER__STOP, oldStop, stop));
	}

	/**
	 * Returns the value of the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active</em>' attribute.
	 * @see #setActive(boolean)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getWorker_Active()
	 * @model
	 * @generated
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.Worker#isActive <em>Active</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Active</em>' attribute.
	 * @see #isActive()
	 * @generated
	 */
	public void setActive(boolean newActive) {
		boolean oldActive = active;
		active = newActive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.WORKER__ACTIVE, oldActive, active));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MediatorPackage.WORKER__START:
				if (resolve) return getStart();
				return basicGetStart();
			case MediatorPackage.WORKER__STOP:
				if (resolve) return getStop();
				return basicGetStop();
			case MediatorPackage.WORKER__ACTIVE:
				return isActive();
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
			case MediatorPackage.WORKER__START:
				setStart((Node)newValue);
				return;
			case MediatorPackage.WORKER__STOP:
				setStop((Node)newValue);
				return;
			case MediatorPackage.WORKER__ACTIVE:
				setActive((Boolean)newValue);
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
			case MediatorPackage.WORKER__START:
				setStart((Node)null);
				return;
			case MediatorPackage.WORKER__STOP:
				setStop((Node)null);
				return;
			case MediatorPackage.WORKER__ACTIVE:
				setActive(ACTIVE_EDEFAULT);
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
			case MediatorPackage.WORKER__START:
				return start != null;
			case MediatorPackage.WORKER__STOP:
				return stop != null;
			case MediatorPackage.WORKER__ACTIVE:
				return active != ACTIVE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (active: ");
		result.append(active);
		result.append(')');
		return result.toString();
	}

} // Worker
