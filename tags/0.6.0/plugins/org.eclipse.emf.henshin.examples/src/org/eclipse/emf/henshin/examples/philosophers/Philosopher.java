/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.examples.philosophers;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Philosopher</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getPlate <em>Plate</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getRight <em>Right</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher()
 * @model kind="class"
 * @generated
 */
public class Philosopher extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The cached value of the '{@link #getPlate() <em>Plate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlate()
	 * @generated
	 * @ordered
	 */
	protected Plate plate;

	/**
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected Fork left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected Fork right;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Philosopher() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PhilosophersPackage.Literals.PHILOSOPHER;
	}

	/**
	 * Returns the value of the '<em><b>Plate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plate</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plate</em>' reference.
	 * @see #setPlate(Plate)
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher_Plate()
	 * @model
	 * @generated
	 */
	public Plate getPlate() {
		if (plate != null && plate.eIsProxy()) {
			InternalEObject oldPlate = (InternalEObject)plate;
			plate = (Plate)eResolveProxy(oldPlate);
			if (plate != oldPlate) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PhilosophersPackage.PHILOSOPHER__PLATE, oldPlate, plate));
			}
		}
		return plate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plate basicGetPlate() {
		return plate;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getPlate <em>Plate</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plate</em>' reference.
	 * @see #getPlate()
	 * @generated
	 */
	public void setPlate(Plate newPlate) {
		Plate oldPlate = plate;
		plate = newPlate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhilosophersPackage.PHILOSOPHER__PLATE, oldPlate, plate));
	}

	/**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(Fork)
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher_Left()
	 * @model containment="true"
	 * @generated
	 */
	public Fork getLeft() {
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeft(Fork newLeft, NotificationChain msgs) {
		Fork oldLeft = left;
		left = newLeft;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PhilosophersPackage.PHILOSOPHER__LEFT, oldLeft, newLeft);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
	public void setLeft(Fork newLeft) {
		if (newLeft != left) {
			NotificationChain msgs = null;
			if (left != null)
				msgs = ((InternalEObject)left).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PhilosophersPackage.PHILOSOPHER__LEFT, null, msgs);
			if (newLeft != null)
				msgs = ((InternalEObject)newLeft).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PhilosophersPackage.PHILOSOPHER__LEFT, null, msgs);
			msgs = basicSetLeft(newLeft, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhilosophersPackage.PHILOSOPHER__LEFT, newLeft, newLeft));
	}

	/**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(Fork)
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher_Right()
	 * @model containment="true"
	 * @generated
	 */
	public Fork getRight() {
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRight(Fork newRight, NotificationChain msgs) {
		Fork oldRight = right;
		right = newRight;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PhilosophersPackage.PHILOSOPHER__RIGHT, oldRight, newRight);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
	public void setRight(Fork newRight) {
		if (newRight != right) {
			NotificationChain msgs = null;
			if (right != null)
				msgs = ((InternalEObject)right).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PhilosophersPackage.PHILOSOPHER__RIGHT, null, msgs);
			if (newRight != null)
				msgs = ((InternalEObject)newRight).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PhilosophersPackage.PHILOSOPHER__RIGHT, null, msgs);
			msgs = basicSetRight(newRight, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhilosophersPackage.PHILOSOPHER__RIGHT, newRight, newRight));
	}

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher_Id()
	 * @model
	 * @generated
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhilosophersPackage.PHILOSOPHER__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PhilosophersPackage.PHILOSOPHER__LEFT:
				return basicSetLeft(null, msgs);
			case PhilosophersPackage.PHILOSOPHER__RIGHT:
				return basicSetRight(null, msgs);
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
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				if (resolve) return getPlate();
				return basicGetPlate();
			case PhilosophersPackage.PHILOSOPHER__LEFT:
				return getLeft();
			case PhilosophersPackage.PHILOSOPHER__RIGHT:
				return getRight();
			case PhilosophersPackage.PHILOSOPHER__ID:
				return getId();
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
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				setPlate((Plate)newValue);
				return;
			case PhilosophersPackage.PHILOSOPHER__LEFT:
				setLeft((Fork)newValue);
				return;
			case PhilosophersPackage.PHILOSOPHER__RIGHT:
				setRight((Fork)newValue);
				return;
			case PhilosophersPackage.PHILOSOPHER__ID:
				setId((Integer)newValue);
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
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				setPlate((Plate)null);
				return;
			case PhilosophersPackage.PHILOSOPHER__LEFT:
				setLeft((Fork)null);
				return;
			case PhilosophersPackage.PHILOSOPHER__RIGHT:
				setRight((Fork)null);
				return;
			case PhilosophersPackage.PHILOSOPHER__ID:
				setId(ID_EDEFAULT);
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
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				return plate != null;
			case PhilosophersPackage.PHILOSOPHER__LEFT:
				return left != null;
			case PhilosophersPackage.PHILOSOPHER__RIGHT:
				return right != null;
			case PhilosophersPackage.PHILOSOPHER__ID:
				return id != ID_EDEFAULT;
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
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} // Philosopher
