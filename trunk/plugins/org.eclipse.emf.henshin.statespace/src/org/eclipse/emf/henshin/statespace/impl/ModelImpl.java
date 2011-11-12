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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.StateSpacePackage;
import org.eclipse.emf.henshin.statespace.util.ObjectIdentityHelper;

/**
 * Transient container for state models.
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model {

	/**
	 * Constructor.
	 * @param resource Resource for this model.
	 * @generated NOT
	 */
	public ModelImpl(Resource resource) {
		this.resource = resource;
	}

	/**
	 * Constructor.
	 * @param resource Resource for this model.
	 * @param emfGraph EmfGraph for this model.
	 * @generated NOT
	 */
	public ModelImpl(Resource resource, EmfGraph emfGraph) {
		this.resource = resource;
		this.emfGraph = emfGraph;
	}

	/**
	 * @generated NOT
	 */
	public EmfGraph getEmfGraph() {
		if (emfGraph==null) {
			emfGraph = new EmfGraph();
			for (EObject root : resource.getContents()) {
				emfGraph.addRoot(root);
			}
		}
		return emfGraph;
	}

	/**
	 * Get a copy of this model.
	 * @see org.eclipse.emf.henshin.statespace.Model#getCopy(org.eclipse.emf.henshin.interpreter.util.Match)
	 * @generated NOT
	 */
	public Model getCopy(Match match) {

		// Copy the resource.
		Resource copiedResource = new ResourceImpl();
		Copier copier = new Copier();
		copiedResource.getContents()
				.addAll(copier.copyAll(resource.getContents()));
		copier.copyReferences();

		// Copy the match.
		if (match != null) {
			for (Node node : match.getRule().getLhs().getNodes()) {
				EObject newImage = copier.get(match.getNodeMapping().get(node));
				match.getNodeMapping().put(node, newImage);
			}
		}
		
		// Now create a new model.
		Model copy = new ModelImpl(copiedResource);

		// Copy the nodeIDs.
		if (objectIdentitiesMap != null) {
			TreeIterator<EObject> iterator = resource.getAllContents();
			while (iterator.hasNext()) {
				EObject object = iterator.next();
				copy.getObjectIdentitiesMap().put(copier.get(object),
						objectIdentitiesMap.get(object));
			}
		}

		// Done.
		return copy;

	}

	/**
	 * @generated NOT
	 */
	public boolean updateObjectIdentities(EClass[] objectTypes) {

		// Make sure the object identities map is not null.
		getObjectIdentitiesMap();
		
		// Remember whether there was a change:
		boolean changed = false;
		
		// Get the next free ID:
		int nextId = 0;
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			Integer identity = objectIdentitiesMap.get(object);
			if (identity!=null) {
				int id = ObjectIdentityHelper.getObjectID(identity);
				if (identity>=nextId) {
					nextId = id+1;
				}
			}
		}

		// Now set the identities for new objects:
		iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			if (objectIdentitiesMap.get(object) == null) {
				objectIdentitiesMap.put(object, nextId++);
				changed = true;
			}
		}
		
		// Done.
		return changed;
		
	}

	/**
	 * @generated NOT
	 */
	public void collectMissingRootObjects() {
		if (emfGraph!=null) {
			for (EObject root : emfGraph.getRootObjects()) {
				if (!resource.getContents().contains(root)) {
					resource.getContents().add(root);
				}
			}
		}
	}

	/**
	 * @generated NOT
	 */
	public int[] getObjectIdentities() {

		// Make sure the object identities map is not null.
		getObjectIdentitiesMap();

		// Copy the map contents to an integer array:
		List<Integer> ids = new ArrayList<Integer>(24);
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			Integer id = objectIdentitiesMap.get(object);
			if (id==null) {
				throw new RuntimeException("No object identity found for " + object);
			}
			ids.add(id.intValue());
		}
		int[] result = new int[ids.size()];
		for (int i = 0; i < ids.size(); i++) {
			result[i] = ids.get(i);
		}
		return result;

	}

	/**
	 * @generated NOT
	 */
	public void setObjectIdentities(int[] objectIdentities) {

		// Make sure the object identities map is not null and empty it.
		getObjectIdentitiesMap().clear();

		// Copy the map contents of the integer array to the map:
		TreeIterator<EObject> iterator = resource.getAllContents();
		int index = 0;
		while (iterator.hasNext()) {
			EObject object = iterator.next();
			objectIdentitiesMap.put(object, objectIdentities[index++]);
		}

	}

	/*
	 * ----------------------------------------------------------------------- *
	 * GENERATED CODE. Do not edit below this line. If you need to edit, move  *
	 * it above this line and change the '@generated'-tag to '@generated NOT'. *
	 * ----------------------------------------------------------------------- *
	 */

	/**
	 * The default value of the '{@link #getResource() <em>Resource</em>}'
	 * attribute.
	 * 
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected static final Resource RESOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}'
	 * attribute.
	 * 
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected Resource resource = RESOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEmfGraph() <em>Emf Graph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfGraph()
	 * @generated
	 * @ordered
	 */
	protected static final EmfGraph EMF_GRAPH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEmfGraph() <em>Emf Graph</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfGraph()
	 * @generated
	 * @ordered
	 */
	protected EmfGraph emfGraph = EMF_GRAPH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getObjectIdentitiesMap() <em>Object Identities Map</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getObjectIdentitiesMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, Integer> objectIdentitiesMap;

	/**
	 * The default value of the '{@link #getObjectIdentities() <em>Object Identities</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectIdentities()
	 * @generated
	 * @ordered
	 */
	protected static final int[] OBJECT_IDENTITIES_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModelImpl() {
		super();
	}

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
	public EMap<EObject, Integer> getObjectIdentitiesMap() {
		if (objectIdentitiesMap == null) {
			objectIdentitiesMap = new EcoreEMap<EObject,Integer>(StateSpacePackage.Literals.OBJECT_IDENTITY, ObjectIdentityImpl.class, this, StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP);
		}
		return objectIdentitiesMap;
	}

	/**
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP:
				return ((InternalEList<?>)getObjectIdentitiesMap()).basicRemove(otherEnd, msgs);
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
			case StateSpacePackage.MODEL__EMF_GRAPH:
				return getEmfGraph();
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP:
				if (coreType) return getObjectIdentitiesMap();
				else return getObjectIdentitiesMap().map();
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				return getObjectIdentities();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP:
				((EStructuralFeature.Setting)getObjectIdentitiesMap()).set(newValue);
				return;
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				setObjectIdentities((int[])newValue);
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
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP:
				getObjectIdentitiesMap().clear();
				return;
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				setObjectIdentities(OBJECT_IDENTITIES_EDEFAULT);
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
			case StateSpacePackage.MODEL__EMF_GRAPH:
				return EMF_GRAPH_EDEFAULT == null ? emfGraph != null : !EMF_GRAPH_EDEFAULT.equals(emfGraph);
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP:
				return objectIdentitiesMap != null && !objectIdentitiesMap.isEmpty();
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				return OBJECT_IDENTITIES_EDEFAULT == null ? getObjectIdentities() != null : !OBJECT_IDENTITIES_EDEFAULT.equals(getObjectIdentities());
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
		result.append(", emfGraph: ");
		result.append(emfGraph);
		result.append(')');
		return result.toString();
	}

} // ModelImpl
