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
 */
package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;

/**
 * Transient container for state models.
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model {

	/**
	 * Constructor.
	 * @generated NOT
	 */
	public ModelImpl() {
		super();
		setResource(new ResourceImpl());
	}

	/**
	 * Constructor.
	 * @param resource Resource for this model.
	 * @generated NOT
	 */
	public ModelImpl(Resource resource) {
		setResource(resource);
	}
	
	/**
	 * Get a copy of this model.
	 * @see org.eclipse.emf.henshin.statespace.Model#getCopy(org.eclipse.emf.henshin.interpreter.util.Match)
	 * @generated NOT
	 */
	public Model getCopy(Match match) {
		
		// Copy the resource.
		Model copy = new ModelImpl();
		Copier copier = new Copier();
		copy.getResource().getContents().addAll(
				copier.copyAll(resource.getContents()));
		copier.copyReferences();
		
		// Copy the match.
		if (match!=null) {
			for (Node node : match.getRule().getLhs().getNodes()) {
				EObject newImage = copier.get(match.getNodeMapping().get(node));
				match.getNodeMapping().put(node, newImage);
			}
		}
		
		// Copy the nodeIDs.
		if (nodeIDsMap!=null) {
			TreeIterator<EObject> iterator = resource.getAllContents();
			while (iterator.hasNext()) {
				EObject object = iterator.next();
				copy.getNodeIDsMap().put(copier.get(object), nodeIDsMap.get(object));
			}
		}
		
		// Done.
		return copy;
		
	}

	/**
	 * @generated NOT
	 */
	public void updateNodeIDs() {

		// Make sure the node IDs map is not null.
		getNodeIDsMap();

		// Get the next free ID:
		int nextID = 0;
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			Integer value = nodeIDsMap.get(object);
			if (value!=null && value>=nextID) {
				nextID = value + 1;
			}
		}
		
		// Now set the IDs for new objects:
		iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			if (nodeIDsMap.get(object)==null) {
				nodeIDsMap.put(object, nextID++);
			}
		}
		
	}
	
	/**
	 * @generated NOT
	 */
	public int[] getNodeIDs() {
		
		// Make sure the node IDs map is not null.
		getNodeIDsMap();
		
		// Copy the map contents to an integer array:
		List<Integer> ids = new ArrayList<Integer>(24);
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			ids.add(nodeIDsMap.get(iterator.next()));
		}
		int[] result = new int[ids.size()];
		for (int i=0; i<ids.size(); i++) {
			result[i] = ids.get(i);
		}
		return result;
		
	}


	/* ---------------------------------------------------------------- *
	 * GENERATED CODE.                                                  *
	 * Do not edit below this line. If you need to edit, move it above  *
	 * this line and change the '@generated'-tag to '@generated NOT'.   *
	 * ---------------------------------------------------------------- */
	
	/**
	 * The default value of the '{@link #getResource() <em>Resource</em>}' attribute.
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected static final Resource RESOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' attribute.
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected Resource resource = RESOURCE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNodeIDsMap() <em>Node IDs Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeIDsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, Integer> nodeIDsMap;

	/**
	 * The default value of the '{@link #getNodeIDs() <em>Node IDs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeIDs()
	 * @generated
	 * @ordered
	 */
	protected static final int[] NODE_IDS_EDEFAULT = null;

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateSpacePackage.Literals.MODEL;
	}

	/**
	 * @generated
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * @generated
	 */
	public void setResource(Resource newResource) {
		Resource oldResource = resource;
		resource = newResource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateSpacePackage.MODEL__RESOURCE, oldResource, resource));
	}

	/**
	 * @generated
	 */
	public EMap<EObject, Integer> getNodeIDsMap() {
		if (nodeIDsMap == null) {
			nodeIDsMap = new EcoreEMap<EObject,Integer>(StateSpacePackage.Literals.NODE_ID, NodeIDImpl.class, this, StateSpacePackage.MODEL__NODE_IDS_MAP);
		}
		return nodeIDsMap;
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.MODEL__NODE_IDS_MAP:
				return ((InternalEList<?>)getNodeIDsMap()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case StateSpacePackage.MODEL__RESOURCE:
				return getResource();
			case StateSpacePackage.MODEL__NODE_IDS_MAP:
				if (coreType) return getNodeIDsMap();
				else return getNodeIDsMap().map();
			case StateSpacePackage.MODEL__NODE_IDS:
				return getNodeIDs();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.MODEL__RESOURCE:
				setResource((Resource)newValue);
				return;
			case StateSpacePackage.MODEL__NODE_IDS_MAP:
				((EStructuralFeature.Setting)getNodeIDsMap()).set(newValue);
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
			case StateSpacePackage.MODEL__RESOURCE:
				setResource(RESOURCE_EDEFAULT);
				return;
			case StateSpacePackage.MODEL__NODE_IDS_MAP:
				getNodeIDsMap().clear();
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
			case StateSpacePackage.MODEL__RESOURCE:
				return RESOURCE_EDEFAULT == null ? resource != null : !RESOURCE_EDEFAULT.equals(resource);
			case StateSpacePackage.MODEL__NODE_IDS_MAP:
				return nodeIDsMap != null && !nodeIDsMap.isEmpty();
			case StateSpacePackage.MODEL__NODE_IDS:
				return NODE_IDS_EDEFAULT == null ? getNodeIDs() != null : !NODE_IDS_EDEFAULT.equals(getNodeIDs());
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
		result.append(" (resource: ");
		result.append(resource);
		result.append(')');
		return result.toString();
	}

} //ModelImpl
