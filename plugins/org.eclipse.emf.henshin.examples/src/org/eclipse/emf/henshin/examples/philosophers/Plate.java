/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.examples.philosophers;

import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Plate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Plate#getForks <em>Forks</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPlate()
 * @model kind="class"
 * @generated
 */
public class Plate extends Identifiable {
	/**
	 * The cached value of the '{@link #getForks() <em>Forks</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForks()
	 * @generated
	 * @ordered
	 */
	protected EList<Fork> forks;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Plate() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PhilosophersPackage.Literals.PLATE;
	}

	/**
	 * Returns the value of the '<em><b>Forks</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.philosophers.Fork}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Forks</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Forks</em>' reference list.
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPlate_Forks()
	 * @model
	 * @generated
	 */
	public EList<Fork> getForks() {
		if (forks == null) {
			forks = new EObjectResolvingEList<Fork>(Fork.class, this, PhilosophersPackage.PLATE__FORKS);
		}
		return forks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PhilosophersPackage.PLATE__FORKS:
				return getForks();
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
			case PhilosophersPackage.PLATE__FORKS:
				getForks().clear();
				getForks().addAll((Collection<? extends Fork>)newValue);
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
			case PhilosophersPackage.PLATE__FORKS:
				getForks().clear();
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
			case PhilosophersPackage.PLATE__FORKS:
				return forks != null && !forks.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // Plate
