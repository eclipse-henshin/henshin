/**
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
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
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.equality.EcoreEqualityHelper;
import org.eclipse.emf.henshin.statespace.equality.GraphEqualityHelper;
import org.eclipse.emf.henshin.statespace.equality.HashCodeTree;
import org.eclipse.emf.henshin.statespace.equality.StateSpaceHashCodeHelper;

/**
 * Default implementation of {@link StateEqualityHelper}.
 * @generated
 */
public class StateEqualityHelperImpl extends MinimalEObjectImpl.Container implements StateEqualityHelper {
	
	/**
	 * Default constructor.
	 * @generated
	 */
	public StateEqualityHelperImpl() {
		super();
	}

	/**
	 * @generated NOT
	 */
	public int hashCode(Model model, HashCodeTree tree) {
		return new StateSpaceHashCodeHelper(graphEquality, ignoreNodeIDs, ignoreAttributes).hashCode(model, tree);
	}

	/**
	 * @generated NOT
	 */
	public boolean equals(Model model1, HashCodeTree tree1, Model model2, HashCodeTree tree2) {
		if (graphEquality) {
			return new GraphEqualityHelper(ignoreNodeIDs,
					ignoreAttributes).equals(model1, tree1, model2, tree2);
		} else {
			return new EcoreEqualityHelper(ignoreNodeIDs,
					ignoreAttributes).equals(model1, model2);
		}
	}

	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The default value of the '{@link #isGraphEquality() <em>Graph Equality</em>}' attribute.
	 * @see #isGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GRAPH_EQUALITY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGraphEquality() <em>Graph Equality</em>}' attribute.
	 * @see #isGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected boolean graphEquality = GRAPH_EQUALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreNodeIDs() <em>Ignore Node IDs</em>}' attribute.
	 * @see #isIgnoreNodeIDs()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_NODE_IDS_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIgnoreNodeIDs() <em>Ignore Node IDs</em>}' attribute.
	 * @see #isIgnoreNodeIDs()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreNodeIDs = IGNORE_NODE_IDS_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreAttributes() <em>Ignore Attributes</em>}' attribute.
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_ATTRIBUTES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreAttributes() <em>Ignore Attributes</em>}' attribute.
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreAttributes = IGNORE_ATTRIBUTES_EDEFAULT;

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.STATE_EQUALITY_HELPER;
	}

	/**
	 * @generated
	 */
	public boolean isGraphEquality() {
		return graphEquality;
	}

	/**
	 * @generated
	 */
	public void setGraphEquality(boolean newGraphEquality) {
		boolean oldGraphEquality = graphEquality;
		graphEquality = newGraphEquality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY, oldGraphEquality, graphEquality));
	}

	/**
	 * @generated
	 */
	public boolean isIgnoreNodeIDs() {
		return ignoreNodeIDs;
	}

	/**
	 * @generated
	 */
	public void setIgnoreNodeIDs(boolean newIgnoreNodeIDs) {
		boolean oldIgnoreNodeIDs = ignoreNodeIDs;
		ignoreNodeIDs = newIgnoreNodeIDs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_NODE_IDS, oldIgnoreNodeIDs, ignoreNodeIDs));
	}

	/**
	 * @generated
	 */
	public boolean isIgnoreAttributes() {
		return ignoreAttributes;
	}

	/**
	 * @generated
	 */
	public void setIgnoreAttributes(boolean newIgnoreAttributes) {
		boolean oldIgnoreAttributes = ignoreAttributes;
		ignoreAttributes = newIgnoreAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES, oldIgnoreAttributes, ignoreAttributes));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				return isGraphEquality();
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_NODE_IDS:
				return isIgnoreNodeIDs();
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				return isIgnoreAttributes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				setGraphEquality((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_NODE_IDS:
				setIgnoreNodeIDs((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				setIgnoreAttributes((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				setGraphEquality(GRAPH_EQUALITY_EDEFAULT);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_NODE_IDS:
				setIgnoreNodeIDs(IGNORE_NODE_IDS_EDEFAULT);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				setIgnoreAttributes(IGNORE_ATTRIBUTES_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__GRAPH_EQUALITY:
				return graphEquality != GRAPH_EQUALITY_EDEFAULT;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_NODE_IDS:
				return ignoreNodeIDs != IGNORE_NODE_IDS_EDEFAULT;
			case StateSpacePackage.STATE_EQUALITY_HELPER__IGNORE_ATTRIBUTES:
				return ignoreAttributes != IGNORE_ATTRIBUTES_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (graphEquality: ");
		result.append(graphEquality);
		result.append(", ignoreNodeIDs: ");
		result.append(ignoreNodeIDs);
		result.append(", ignoreAttributes: ");
		result.append(ignoreAttributes);
		result.append(')');
		return result.toString();
	}

}
