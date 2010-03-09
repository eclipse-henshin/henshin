/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.PortMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conditional Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getPortMappings <em>Port Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getIf <em>If</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getThen <em>Then</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl#getElse <em>Else</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConditionalUnitImpl extends EObjectImpl implements ConditionalUnit {
	/**
	 * The default value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ACTIVATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isActivated() <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isActivated()
	 * @generated
	 * @ordered
	 */
	protected boolean activated = ACTIVATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * The cached value of the '{@link #getPortMappings() <em>Port Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPortMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<PortMapping> portMappings;

	/**
	 * The cached value of the '{@link #getIf() <em>If</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIf()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit if_;

	/**
	 * The cached value of the '{@link #getThen() <em>Then</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThen()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit then;

	/**
	 * The cached value of the '{@link #getElse() <em>Else</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElse()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit else_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionalUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.CONDITIONAL_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivated(boolean newActivated) {
		boolean oldActivated = activated;
		activated = newActivated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__ACTIVATED, oldActivated, activated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, HenshinPackage.CONDITIONAL_UNIT__PORTS, HenshinPackage.PORT__UNIT);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PortMapping> getPortMappings() {
		if (portMappings == null) {
			portMappings = new EObjectContainmentEList<PortMapping>(PortMapping.class, this, HenshinPackage.CONDITIONAL_UNIT__PORT_MAPPINGS);
		}
		return portMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getIf() {
		return if_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIf(TransformationUnit newIf, NotificationChain msgs) {
		TransformationUnit oldIf = if_;
		if_ = newIf;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__IF, oldIf, newIf);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIf(TransformationUnit newIf) {
		if (newIf != if_) {
			NotificationChain msgs = null;
			if (if_ != null)
				msgs = ((InternalEObject)if_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.CONDITIONAL_UNIT__IF, null, msgs);
			if (newIf != null)
				msgs = ((InternalEObject)newIf).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.CONDITIONAL_UNIT__IF, null, msgs);
			msgs = basicSetIf(newIf, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__IF, newIf, newIf));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getThen() {
		return then;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetThen(TransformationUnit newThen, NotificationChain msgs) {
		TransformationUnit oldThen = then;
		then = newThen;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__THEN, oldThen, newThen);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setThen(TransformationUnit newThen) {
		if (newThen != then) {
			NotificationChain msgs = null;
			if (then != null)
				msgs = ((InternalEObject)then).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.CONDITIONAL_UNIT__THEN, null, msgs);
			if (newThen != null)
				msgs = ((InternalEObject)newThen).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.CONDITIONAL_UNIT__THEN, null, msgs);
			msgs = basicSetThen(newThen, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__THEN, newThen, newThen));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getElse() {
		return else_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetElse(TransformationUnit newElse, NotificationChain msgs) {
		TransformationUnit oldElse = else_;
		else_ = newElse;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__ELSE, oldElse, newElse);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElse(TransformationUnit newElse) {
		if (newElse != else_) {
			NotificationChain msgs = null;
			if (else_ != null)
				msgs = ((InternalEObject)else_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.CONDITIONAL_UNIT__ELSE, null, msgs);
			if (newElse != null)
				msgs = ((InternalEObject)newElse).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HenshinPackage.CONDITIONAL_UNIT__ELSE, null, msgs);
			msgs = basicSetElse(newElse, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.CONDITIONAL_UNIT__ELSE, newElse, newElse));
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
			case HenshinPackage.CONDITIONAL_UNIT__PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
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
			case HenshinPackage.CONDITIONAL_UNIT__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case HenshinPackage.CONDITIONAL_UNIT__PORT_MAPPINGS:
				return ((InternalEList<?>)getPortMappings()).basicRemove(otherEnd, msgs);
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				return basicSetIf(null, msgs);
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				return basicSetThen(null, msgs);
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				return basicSetElse(null, msgs);
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
			case HenshinPackage.CONDITIONAL_UNIT__ACTIVATED:
				return isActivated();
			case HenshinPackage.CONDITIONAL_UNIT__PORTS:
				return getPorts();
			case HenshinPackage.CONDITIONAL_UNIT__PORT_MAPPINGS:
				return getPortMappings();
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				return getIf();
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				return getThen();
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				return getElse();
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
			case HenshinPackage.CONDITIONAL_UNIT__ACTIVATED:
				setActivated((Boolean)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__PORT_MAPPINGS:
				getPortMappings().clear();
				getPortMappings().addAll((Collection<? extends PortMapping>)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				setIf((TransformationUnit)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				setThen((TransformationUnit)newValue);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				setElse((TransformationUnit)newValue);
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
			case HenshinPackage.CONDITIONAL_UNIT__ACTIVATED:
				setActivated(ACTIVATED_EDEFAULT);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__PORTS:
				getPorts().clear();
				return;
			case HenshinPackage.CONDITIONAL_UNIT__PORT_MAPPINGS:
				getPortMappings().clear();
				return;
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				setIf((TransformationUnit)null);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				setThen((TransformationUnit)null);
				return;
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				setElse((TransformationUnit)null);
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
			case HenshinPackage.CONDITIONAL_UNIT__ACTIVATED:
				return activated != ACTIVATED_EDEFAULT;
			case HenshinPackage.CONDITIONAL_UNIT__PORTS:
				return ports != null && !ports.isEmpty();
			case HenshinPackage.CONDITIONAL_UNIT__PORT_MAPPINGS:
				return portMappings != null && !portMappings.isEmpty();
			case HenshinPackage.CONDITIONAL_UNIT__IF:
				return if_ != null;
			case HenshinPackage.CONDITIONAL_UNIT__THEN:
				return then != null;
			case HenshinPackage.CONDITIONAL_UNIT__ELSE:
				return else_ != null;
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
		result.append(" (activated: ");
		result.append(activated);
		result.append(')');
		return result.toString();
	}

} //ConditionalUnitImpl
