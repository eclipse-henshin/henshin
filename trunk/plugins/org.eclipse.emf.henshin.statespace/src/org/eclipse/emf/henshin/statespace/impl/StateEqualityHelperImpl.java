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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.util.GraphIsomorphyChecker;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateEqualityHelper;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl.Cache;
import org.eclipse.emf.henshin.statespace.util.EcoreEqualityHelper;
import org.eclipse.emf.henshin.statespace.util.StateSpaceHashCodeHelper;

/**
 * Default implementation of {@link StateEqualityHelper}.
 * 
 * @generated
 */
public class StateEqualityHelperImpl extends MinimalEObjectImpl.Container
		implements StateEqualityHelper {

	/* 
	 * Graph isomorphy-checker cache:
	 */
	private final Map<Model,GraphIsomorphyChecker> isomorphyCheckerCache = 
		Collections.synchronizedMap(new Cache<Model,GraphIsomorphyChecker>());

	/**
	 * @generated NOT
	 */
	public int hashCode(Model model) {
		return new StateSpaceHashCodeHelper(graphEquality, ignoreNodeIDs,
				ignoreAttributes).hashCode(model);
	}

	/**
	 * @generated NOT
	 */
	public boolean equals(Model model1, Model model2) {
		
		// Graph equality?
		if (graphEquality) {
			
			// Get the isomorphy checker for the first model:
			GraphIsomorphyChecker checker1 = isomorphyCheckerCache.get(model1);
			
			// Switch if there is only checker for model2:
			if (checker1==null) {
				GraphIsomorphyChecker checker2 = isomorphyCheckerCache.get(model2);
				if (checker2!=null) {
					checker1 = checker2;
					Model dummy = model2;
					model2 = model1;
					model1 = dummy;
				}
			}
			
			// Create a new isomorphy checker if required:
			if (checker1==null) {
				checker1 = new GraphIsomorphyChecker(model1.getEmfGraph(), ignoreAttributes);
				isomorphyCheckerCache.put(model1, checker1);
			}
			
			// Get the EmfGraph for the second model:
			EmfGraph graph2 = model2.getEmfGraph();
			
			// Do we need to compute a match for the node IDs?
			Map<EObject,EObject> match = null;
			if (!ignoreNodeIDs) {
				
				// Index the second model:
				EObject[] indexedModel2 = new EObject[graph2.geteObjects().size()];
				for (Map.Entry<EObject, Integer> entry2 : model2.getNodeIDsMap().entrySet()) {
					indexedModel2[entry2.getValue()] = entry2.getKey();
				}
				
				// Compute the match:
				match = new HashMap<EObject,EObject>();
				for (Map.Entry<EObject, Integer> entry1 : model1.getNodeIDsMap().entrySet()) {
					match.put(entry1.getKey(), indexedModel2[entry1.getValue()]);
				}
				
			}
			
			// Now we can invoke the checker:
			return checker1.isIsomorphicTo(model2.getEmfGraph(), match);
		
		} else {
			
			// Use standard Ecore equality checker:
			EcoreEqualityHelper helper = 
					new EcoreEqualityHelper(ignoreNodeIDs, ignoreAttributes);
			return helper.equals(model1, model2);
			
		}
		
	}
	
	/**
	 * @generated NOT
	 */
	public void setGraphEquality(boolean graphEquality) {
		setGraphEqualityGen(graphEquality);
		isomorphyCheckerCache.clear();
	}

	/**
	 * @generated NOT
	 */
	public void setIgnoreAttributes(boolean ignoreAttributes) {
		setIgnoreAttributesGen(ignoreAttributes);
		isomorphyCheckerCache.clear();
	}

	/*
	 * Clear cache.
	 */
	public void clearCache() {
		isomorphyCheckerCache.clear();
	}


	/*
	 * ----------------------------------------------------------------------- *
	 * GENERATED CODE. Do not edit below this line. If you need to edit, move
	 * it above this line and change the '@generated'-tag to '@generated NOT'.
	 * ----------------------------------------------------------------------- *
	 */

	/**
	 * The default value of the '{@link #isGraphEquality()
	 * <em>Graph Equality</em>}' attribute.
	 * 
	 * @see #isGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GRAPH_EQUALITY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isGraphEquality()
	 * <em>Graph Equality</em>}' attribute.
	 * 
	 * @see #isGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected boolean graphEquality = GRAPH_EQUALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreNodeIDs()
	 * <em>Ignore Node IDs</em>}' attribute.
	 * 
	 * @see #isIgnoreNodeIDs()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_NODE_IDS_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIgnoreNodeIDs()
	 * <em>Ignore Node IDs</em>}' attribute.
	 * 
	 * @see #isIgnoreNodeIDs()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreNodeIDs = IGNORE_NODE_IDS_EDEFAULT;

	/**
	 * The default value of the '{@link #isIgnoreAttributes()
	 * <em>Ignore Attributes</em>}' attribute.
	 * 
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IGNORE_ATTRIBUTES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIgnoreAttributes()
	 * <em>Ignore Attributes</em>}' attribute.
	 * 
	 * @see #isIgnoreAttributes()
	 * @generated
	 * @ordered
	 */
	protected boolean ignoreAttributes = IGNORE_ATTRIBUTES_EDEFAULT;

	/**
	 * Default constructor.
	 * @generated
	 */
	protected StateEqualityHelperImpl() {
		super();
	}

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
	public void setGraphEqualityGen(boolean newGraphEquality) {
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
	public void setIgnoreAttributesGen(boolean newIgnoreAttributes) {
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
