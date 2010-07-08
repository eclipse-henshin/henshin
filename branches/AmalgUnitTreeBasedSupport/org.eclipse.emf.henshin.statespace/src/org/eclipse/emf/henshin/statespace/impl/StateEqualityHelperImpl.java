/**
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.util.EcoreEqualityHelper;
import org.eclipse.emf.henshin.statespace.util.GraphEqualityHelper;
import org.eclipse.emf.henshin.statespace.util.StateSpaceHashCodeHelper;

/**
 * Default implementation of {@link StateEqualityHelper}.
 * @generated
 */
public class StateEqualityHelperImpl extends MinimalEObjectImpl.Container implements StateEqualityHelper {
	
	// Helper for computing hash codes:
	private StateSpaceHashCodeHelper hashCodeHelper = new StateSpaceHashCodeHelper(GRAPH_EQUALITY_EDEFAULT, IGNORE_ATTRIBUTES_EDEFAULT);
	
	/**
	 * @generated NOT
	 */
	public int hashCode(Resource model) {
		return hashCodeHelper.hashCode(model);
	}

	/**
	 * @generated NOT
	 */
	public boolean equals(Resource model1, Resource model2) {
		if (graphEquality) {
			return new GraphEqualityHelper(ignoreAttributes).equals(model1, model2);
		} else {
			return new EcoreEqualityHelper(ignoreAttributes).equals(model1, model2);
		}
	}
	
	/**
	 * @generated NOT
	 */
	public void setGraphEquality(boolean graphEquality) {
		setGraphEqualityGen(graphEquality);
		hashCodeHelper = new StateSpaceHashCodeHelper(graphEquality, ignoreAttributes);
	}

	/**
	 * @generated NOT
	 */
	public void setIgnoreAttributes(boolean ignoreAttributes) {
		setIgnoreAttributesGen(ignoreAttributes);
		hashCodeHelper = new StateSpaceHashCodeHelper(graphEquality, ignoreAttributes);
	}


	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The default value of the '{@link #isGraphEquality() <em>Graph Equality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GRAPH_EQUALITY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGraphEquality() <em>Graph Equality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected boolean graphEquality = GRAPH_EQUALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreAttributes() <em>Ignore Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_ATTRIBUTES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreAttributes() <em>Ignore Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreAttributes = IGNORE_ATTRIBUTES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateEqualityHelperImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.STATE_EQUALITY_HELPER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGraphEquality() {
		return graphEquality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraphEqualityGen(boolean newGraphEquality) {
		boolean oldGraphEquality = graphEquality;
		graphEquality = newGraphEquality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY, oldGraphEquality, graphEquality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIgnoreAttributes() {
		return ignoreAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIgnoreAttributesGen(boolean newIgnoreAttributes) {
		boolean oldIgnoreAttributes = ignoreAttributes;
		ignoreAttributes = newIgnoreAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES, oldIgnoreAttributes, ignoreAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				return isGraphEquality();
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				return isIgnoreAttributes();
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
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				setGraphEquality((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				setIgnoreAttributes((Boolean)newValue);
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
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				setGraphEquality(GRAPH_EQUALITY_EDEFAULT);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				setIgnoreAttributes(IGNORE_ATTRIBUTES_EDEFAULT);
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
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				return graphEquality != GRAPH_EQUALITY_EDEFAULT;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				return ignoreAttributes != IGNORE_ATTRIBUTES_EDEFAULT;
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
		result.append(" (graphEquality: ");
		result.append(graphEquality);
		result.append(", ignoreAttributes: ");
		result.append(ignoreAttributes);
		result.append(')');
		return result.toString();
	}

}
