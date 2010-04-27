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
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.CountedUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Port;
import org.eclipse.emf.henshin.model.PortMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Counted Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getPortMappings <em>Port Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getSubUnit <em>Sub Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.impl.CountedUnitImpl#getCount <em>Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CountedUnitImpl extends DescribedElementImpl implements CountedUnit {
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
	 * The cached value of the '{@link #getSubUnit() <em>Sub Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubUnit()
	 * @generated
	 * @ordered
	 */
	protected TransformationUnit subUnit;

	/**
	 * The default value of the '{@link #getCount() <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected static final int COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCount() <em>Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCount()
	 * @generated
	 * @ordered
	 */
	protected int count = COUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CountedUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HenshinPackage.Literals.COUNTED_UNIT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__ACTIVATED, oldActivated, activated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, HenshinPackage.COUNTED_UNIT__PORTS, HenshinPackage.PORT__UNIT);
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
			portMappings = new EObjectContainmentEList<PortMapping>(PortMapping.class, this, HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS);
		}
		return portMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit getSubUnit() {
		if (subUnit != null && subUnit.eIsProxy()) {
			InternalEObject oldSubUnit = (InternalEObject)subUnit;
			subUnit = (TransformationUnit)eResolveProxy(oldSubUnit);
			if (subUnit != oldSubUnit) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, HenshinPackage.COUNTED_UNIT__SUB_UNIT, oldSubUnit, subUnit));
			}
		}
		return subUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransformationUnit basicGetSubUnit() {
		return subUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubUnit(TransformationUnit newSubUnit) {
		TransformationUnit oldSubUnit = subUnit;
		subUnit = newSubUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__SUB_UNIT, oldSubUnit, subUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCount() {
		return count;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCount(int newCount) {
		int oldCount = count;
		count = newCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, HenshinPackage.COUNTED_UNIT__COUNT, oldCount, count));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<TransformationUnit> getAllSubUnits() {

		return new BasicEList<TransformationUnit>(subUnit.getAllSubUnits());
	}// getAllSubUnits

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HenshinPackage.COUNTED_UNIT__PORTS:
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
			case HenshinPackage.COUNTED_UNIT__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS:
				return ((InternalEList<?>)getPortMappings()).basicRemove(otherEnd, msgs);
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
			case HenshinPackage.COUNTED_UNIT__NAME:
				return getName();
			case HenshinPackage.COUNTED_UNIT__ACTIVATED:
				return isActivated();
			case HenshinPackage.COUNTED_UNIT__PORTS:
				return getPorts();
			case HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS:
				return getPortMappings();
			case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
				if (resolve) return getSubUnit();
				return basicGetSubUnit();
			case HenshinPackage.COUNTED_UNIT__COUNT:
				return getCount();
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
			case HenshinPackage.COUNTED_UNIT__NAME:
				setName((String)newValue);
				return;
			case HenshinPackage.COUNTED_UNIT__ACTIVATED:
				setActivated((Boolean)newValue);
				return;
			case HenshinPackage.COUNTED_UNIT__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS:
				getPortMappings().clear();
				getPortMappings().addAll((Collection<? extends PortMapping>)newValue);
				return;
			case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
				setSubUnit((TransformationUnit)newValue);
				return;
			case HenshinPackage.COUNTED_UNIT__COUNT:
				setCount((Integer)newValue);
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
			case HenshinPackage.COUNTED_UNIT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case HenshinPackage.COUNTED_UNIT__ACTIVATED:
				setActivated(ACTIVATED_EDEFAULT);
				return;
			case HenshinPackage.COUNTED_UNIT__PORTS:
				getPorts().clear();
				return;
			case HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS:
				getPortMappings().clear();
				return;
			case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
				setSubUnit((TransformationUnit)null);
				return;
			case HenshinPackage.COUNTED_UNIT__COUNT:
				setCount(COUNT_EDEFAULT);
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
			case HenshinPackage.COUNTED_UNIT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case HenshinPackage.COUNTED_UNIT__ACTIVATED:
				return activated != ACTIVATED_EDEFAULT;
			case HenshinPackage.COUNTED_UNIT__PORTS:
				return ports != null && !ports.isEmpty();
			case HenshinPackage.COUNTED_UNIT__PORT_MAPPINGS:
				return portMappings != null && !portMappings.isEmpty();
			case HenshinPackage.COUNTED_UNIT__SUB_UNIT:
				return subUnit != null;
			case HenshinPackage.COUNTED_UNIT__COUNT:
				return count != COUNT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case HenshinPackage.COUNTED_UNIT__NAME: return HenshinPackage.NAMED_ELEMENT__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case HenshinPackage.NAMED_ELEMENT__NAME: return HenshinPackage.COUNTED_UNIT__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", activated: ");
		result.append(activated);
		result.append(", count: ");
		result.append(count);
		result.append(')');
		return result.toString();
	}

} //CountedUnitImpl
