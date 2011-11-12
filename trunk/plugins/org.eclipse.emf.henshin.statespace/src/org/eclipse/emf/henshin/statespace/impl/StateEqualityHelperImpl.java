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
		return new StateSpaceHashCodeHelper(useGraphEquality, 
				useObjectIdentities, useObjectAttributes).hashCode(model);
	}

	/**
	 * @generated NOT
	 */
	public boolean equals(Model model1, Model model2) {
		
		// Graph equality?
		if (useGraphEquality) {
			
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
				checker1 = new GraphIsomorphyChecker(model1.getEmfGraph(), useObjectAttributes);
				isomorphyCheckerCache.put(model1, checker1);
			}
			
			// Get the EmfGraph for the second model:
			EmfGraph graph2 = model2.getEmfGraph();
			
			// Do we need to compute a match for the node IDs?
			Map<EObject,EObject> match = null;
			if (useObjectIdentities) {
				
				// Index the second model:
				EObject[] indexedModel2 = new EObject[graph2.geteObjects().size()];
				for (Map.Entry<EObject, Integer> entry2 : model2.getObjectIdentitiesMap().entrySet()) {
					indexedModel2[entry2.getValue()] = entry2.getKey();
				}
				
				// Compute the match:
				match = new HashMap<EObject,EObject>();
				for (Map.Entry<EObject, Integer> entry1 : model1.getObjectIdentitiesMap().entrySet()) {
					match.put(entry1.getKey(), indexedModel2[entry1.getValue()]);
				}
				
			}
			
			// Now we can invoke the checker:
			return checker1.isIsomorphicTo(model2.getEmfGraph(), match);
		
		} else {
			
			// Use standard Ecore equality checker:
			return new EcoreEqualityHelper(
					useObjectIdentities, 
					useObjectAttributes).equals(model1, model2);
			
		}
		
	}
	
	/**
	 * @generated NOT
	 */
	public void setUseGraphEquality(boolean useGraphEquality) {
		setUseGraphEqualityGen(useGraphEquality);
		isomorphyCheckerCache.clear();
	}

	/**
	 * @generated NOT
	 */
	public void setUseObjectAttributes(boolean useObjectAttributes) {
		setUseObjectAttributesGen(useObjectAttributes);
		isomorphyCheckerCache.clear();
	}

	/**
	 * @generated NOT
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
	 * The default value of the '{@link #isUseGraphEquality() <em>Use Graph Equality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_GRAPH_EQUALITY_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseGraphEquality() <em>Use Graph Equality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseGraphEquality()
	 * @generated
	 * @ordered
	 */
	protected boolean useGraphEquality = USE_GRAPH_EQUALITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseObjectIdentities() <em>Use Object Identities</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseObjectIdentities()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_OBJECT_IDENTITIES_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseObjectIdentities() <em>Use Object Identities</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseObjectIdentities()
	 * @generated
	 * @ordered
	 */
	protected boolean useObjectIdentities = USE_OBJECT_IDENTITIES_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseObjectAttributes() <em>Use Object Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseObjectAttributes()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_OBJECT_ATTRIBUTES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseObjectAttributes() <em>Use Object Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseObjectAttributes()
	 * @generated
	 * @ordered
	 */
	protected boolean useObjectAttributes = USE_OBJECT_ATTRIBUTES_EDEFAULT;

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
	public boolean isUseGraphEquality() {
		return useGraphEquality;
	}

	/**
	 * @generated
	 */
	public void setUseGraphEqualityGen(boolean newUseGraphEquality) {
		boolean oldUseGraphEquality = useGraphEquality;
		useGraphEquality = newUseGraphEquality;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__USE_GRAPH_EQUALITY, oldUseGraphEquality, useGraphEquality));
	}

	/**
	 * @generated
	 */
	public boolean isUseObjectIdentities() {
		return useObjectIdentities;
	}

	/**
	 * @generated
	 */
	public void setUseObjectIdentities(boolean newUseObjectIdentities) {
		boolean oldUseObjectIdentities = useObjectIdentities;
		useObjectIdentities = newUseObjectIdentities;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_IDENTITIES, oldUseObjectIdentities, useObjectIdentities));
	}

	/**
	 * @generated
	 */
	public boolean isUseObjectAttributes() {
		return useObjectAttributes;
	}

	/**
	 * @generated
	 */
	public void setUseObjectAttributesGen(boolean newUseObjectAttributes) {
		boolean oldUseObjectAttributes = useObjectAttributes;
		useObjectAttributes = newUseObjectAttributes;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES, oldUseObjectAttributes, useObjectAttributes));
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_GRAPH_EQUALITY:
				return isUseGraphEquality();
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_IDENTITIES:
				return isUseObjectIdentities();
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES:
				return isUseObjectAttributes();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_GRAPH_EQUALITY:
				setUseGraphEquality((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_IDENTITIES:
				setUseObjectIdentities((Boolean)newValue);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES:
				setUseObjectAttributes((Boolean)newValue);
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
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_GRAPH_EQUALITY:
				setUseGraphEquality(USE_GRAPH_EQUALITY_EDEFAULT);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_IDENTITIES:
				setUseObjectIdentities(USE_OBJECT_IDENTITIES_EDEFAULT);
				return;
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES:
				setUseObjectAttributes(USE_OBJECT_ATTRIBUTES_EDEFAULT);
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
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_GRAPH_EQUALITY:
				return useGraphEquality != USE_GRAPH_EQUALITY_EDEFAULT;
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_IDENTITIES:
				return useObjectIdentities != USE_OBJECT_IDENTITIES_EDEFAULT;
			case StateSpacePackage.STATE_EQUALITY_HELPER__USE_OBJECT_ATTRIBUTES:
				return useObjectAttributes != USE_OBJECT_ATTRIBUTES_EDEFAULT;
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
		result.append(" (useGraphEquality: ");
		result.append(useGraphEquality);
		result.append(", useObjectIdentities: ");
		result.append(useObjectIdentities);
		result.append(", useObjectAttributes: ");
		result.append(useObjectAttributes);
		result.append(')');
		return result.toString();
	}

}
