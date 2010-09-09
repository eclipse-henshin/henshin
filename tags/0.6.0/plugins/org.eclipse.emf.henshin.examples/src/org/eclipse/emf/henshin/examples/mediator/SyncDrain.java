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
 * A representation of the model object '<em><b>Sync Drain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource1 <em>Source1</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource2 <em>Source2</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSyncDrain()
 * @model kind="class"
 * @generated
 */
public class SyncDrain extends Channel {
	/**
	 * The cached value of the '{@link #getSource1() <em>Source1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource1()
	 * @generated
	 * @ordered
	 */
	protected Node source1;

	/**
	 * The cached value of the '{@link #getSource2() <em>Source2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource2()
	 * @generated
	 * @ordered
	 */
	protected Node source2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SyncDrain() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MediatorPackage.Literals.SYNC_DRAIN;
	}

	/**
	 * Returns the value of the '<em><b>Source1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source1</em>' reference.
	 * @see #setSource1(Node)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSyncDrain_Source1()
	 * @model
	 * @generated
	 */
	public Node getSource1() {
		if (source1 != null && source1.eIsProxy()) {
			InternalEObject oldSource1 = (InternalEObject)source1;
			source1 = (Node)eResolveProxy(oldSource1);
			if (source1 != oldSource1) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediatorPackage.SYNC_DRAIN__SOURCE1, oldSource1, source1));
			}
		}
		return source1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSource1() {
		return source1;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource1 <em>Source1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source1</em>' reference.
	 * @see #getSource1()
	 * @generated
	 */
	public void setSource1(Node newSource1) {
		Node oldSource1 = source1;
		source1 = newSource1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.SYNC_DRAIN__SOURCE1, oldSource1, source1));
	}

	/**
	 * Returns the value of the '<em><b>Source2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source2</em>' reference.
	 * @see #setSource2(Node)
	 * @see org.eclipse.emf.henshin.examples.mediator.MediatorPackage#getSyncDrain_Source2()
	 * @model
	 * @generated
	 */
	public Node getSource2() {
		if (source2 != null && source2.eIsProxy()) {
			InternalEObject oldSource2 = (InternalEObject)source2;
			source2 = (Node)eResolveProxy(oldSource2);
			if (source2 != oldSource2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MediatorPackage.SYNC_DRAIN__SOURCE2, oldSource2, source2));
			}
		}
		return source2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node basicGetSource2() {
		return source2;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.mediator.SyncDrain#getSource2 <em>Source2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source2</em>' reference.
	 * @see #getSource2()
	 * @generated
	 */
	public void setSource2(Node newSource2) {
		Node oldSource2 = source2;
		source2 = newSource2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MediatorPackage.SYNC_DRAIN__SOURCE2, oldSource2, source2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MediatorPackage.SYNC_DRAIN__SOURCE1:
				if (resolve) return getSource1();
				return basicGetSource1();
			case MediatorPackage.SYNC_DRAIN__SOURCE2:
				if (resolve) return getSource2();
				return basicGetSource2();
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
			case MediatorPackage.SYNC_DRAIN__SOURCE1:
				setSource1((Node)newValue);
				return;
			case MediatorPackage.SYNC_DRAIN__SOURCE2:
				setSource2((Node)newValue);
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
			case MediatorPackage.SYNC_DRAIN__SOURCE1:
				setSource1((Node)null);
				return;
			case MediatorPackage.SYNC_DRAIN__SOURCE2:
				setSource2((Node)null);
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
			case MediatorPackage.SYNC_DRAIN__SOURCE1:
				return source1 != null;
			case MediatorPackage.SYNC_DRAIN__SOURCE2:
				return source2 != null;
		}
		return super.eIsSet(featureID);
	}

} // SyncDrain
