/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.examples.philosophers;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Philosopher</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getForks <em>Forks</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getPlate <em>Plate</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher()
 * @model kind="class"
 * @generated
 */
public class Philosopher extends Identifiable {
	/**
	 * The cached value of the '{@link #getForks() <em>Forks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForks()
	 * @generated
	 * @ordered
	 */
	protected EList<Fork> forks;

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
	 * Returns the value of the '<em><b>Forks</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.philosophers.Fork}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Forks</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Forks</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher_Forks()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Fork> getForks() {
		if (forks == null) {
			forks = new EObjectContainmentEList<Fork>(Fork.class, this, PhilosophersPackage.PHILOSOPHER__FORKS);
		}
		return forks;
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PhilosophersPackage.PHILOSOPHER__FORKS:
				return ((InternalEList<?>)getForks()).basicRemove(otherEnd, msgs);
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
			case PhilosophersPackage.PHILOSOPHER__FORKS:
				return getForks();
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				if (resolve) return getPlate();
				return basicGetPlate();
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
			case PhilosophersPackage.PHILOSOPHER__FORKS:
				getForks().clear();
				getForks().addAll((Collection<? extends Fork>)newValue);
				return;
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				setPlate((Plate)newValue);
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
			case PhilosophersPackage.PHILOSOPHER__FORKS:
				getForks().clear();
				return;
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				setPlate((Plate)null);
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
			case PhilosophersPackage.PHILOSOPHER__FORKS:
				return forks != null && !forks.isEmpty();
			case PhilosophersPackage.PHILOSOPHER__PLATE:
				return plate != null;
		}
		return super.eIsSet(featureID);
	}

} // Philosopher
