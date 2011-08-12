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

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Table#getPlates <em>Plates</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Table#getPhilosophers <em>Philosophers</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.philosophers.Table#getForks <em>Forks</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getTable()
 * @model kind="class"
 * @generated
 */
public class Table extends MinimalEObjectImpl.Container implements EObject {
	/**
	 * The cached value of the '{@link #getPlates() <em>Plates</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlates()
	 * @generated
	 * @ordered
	 */
	protected EList<Plate> plates;

	/**
	 * The cached value of the '{@link #getPhilosophers() <em>Philosophers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhilosophers()
	 * @generated
	 * @ordered
	 */
	protected EList<Philosopher> philosophers;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Table() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PhilosophersPackage.Literals.TABLE;
	}

	/**
	 * Returns the value of the '<em><b>Plates</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.philosophers.Plate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plates</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plates</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getTable_Plates()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Plate> getPlates() {
		if (plates == null) {
			plates = new EObjectContainmentEList<Plate>(Plate.class, this, PhilosophersPackage.TABLE__PLATES);
		}
		return plates;
	}

	/**
	 * Returns the value of the '<em><b>Philosophers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.philosophers.Philosopher}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Philosophers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Philosophers</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getTable_Philosophers()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Philosopher> getPhilosophers() {
		if (philosophers == null) {
			philosophers = new EObjectContainmentEList<Philosopher>(Philosopher.class, this, PhilosophersPackage.TABLE__PHILOSOPHERS);
		}
		return philosophers;
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
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getTable_Forks()
	 * @model containment="true"
	 * @generated
	 */
	public EList<Fork> getForks() {
		if (forks == null) {
			forks = new EObjectContainmentEList<Fork>(Fork.class, this, PhilosophersPackage.TABLE__FORKS);
		}
		return forks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PhilosophersPackage.TABLE__PLATES:
				return ((InternalEList<?>)getPlates()).basicRemove(otherEnd, msgs);
			case PhilosophersPackage.TABLE__PHILOSOPHERS:
				return ((InternalEList<?>)getPhilosophers()).basicRemove(otherEnd, msgs);
			case PhilosophersPackage.TABLE__FORKS:
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
			case PhilosophersPackage.TABLE__PLATES:
				return getPlates();
			case PhilosophersPackage.TABLE__PHILOSOPHERS:
				return getPhilosophers();
			case PhilosophersPackage.TABLE__FORKS:
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
			case PhilosophersPackage.TABLE__PLATES:
				getPlates().clear();
				getPlates().addAll((Collection<? extends Plate>)newValue);
				return;
			case PhilosophersPackage.TABLE__PHILOSOPHERS:
				getPhilosophers().clear();
				getPhilosophers().addAll((Collection<? extends Philosopher>)newValue);
				return;
			case PhilosophersPackage.TABLE__FORKS:
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
			case PhilosophersPackage.TABLE__PLATES:
				getPlates().clear();
				return;
			case PhilosophersPackage.TABLE__PHILOSOPHERS:
				getPhilosophers().clear();
				return;
			case PhilosophersPackage.TABLE__FORKS:
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
			case PhilosophersPackage.TABLE__PLATES:
				return plates != null && !plates.isEmpty();
			case PhilosophersPackage.TABLE__PHILOSOPHERS:
				return philosophers != null && !philosophers.isEmpty();
			case PhilosophersPackage.TABLE__FORKS:
				return forks != null && !forks.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString() {
		return "Table@" + Integer.toHexString(hashCode());
	}

} // Table
