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
import org.eclipse.emf.henshin.statespace.util.ObjectKeyHelper;

/**
 * Transient container for state models.
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model {
	
	/*
	 * Default implementation of an object key map.
	 * This implementation returns 0 for all objects
	 * that do not have a key.
	 */
	private static class ObjectKeyMap extends EcoreEMap<EObject,Integer> {

		// Static zero integer object:
		private static final Integer ZERO = new Integer(0);

		// Serial Id:
		private static final long serialVersionUID = 1L;

		public ObjectKeyMap(ModelImpl model) {
			super(StateSpacePackage.Literals.OBJECT_IDENTITY, 
				ObjectKeyImpl.class, model, 
				StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP);
		}

		@Override
		public Integer get(Object object) {
			Integer key = super.get(object);
			return (key!=null) ? key : ZERO;
		}
	};
	
	
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
	public EMap<EObject, Integer> getObjectKeysMap() {
		if (objectKeysMap == null) {
			objectKeysMap = new ObjectKeyMap(this);
		}
		return objectKeysMap;
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
		ModelImpl copy = new ModelImpl(copiedResource);

		// Copy the object keys.
		if (objectKeysMap != null) {
			TreeIterator<EObject> iterator = resource.getAllContents();
			while (iterator.hasNext()) {
				EObject object = iterator.next();
				copy.getObjectKeysMap().put(copier.get(object),
						objectKeysMap.get(object));
			}
		}

		// Done.
		return copy;

	}

	/**
	 * @generated NOT
	 */
	public boolean updateObjectKeys(EClass[] supportedTypes) {

		// Make sure the object keys map is not null.
		getObjectKeysMap();
		
		// Remember whether there was a change:
		boolean changed = false;

		// Get the next free object Id:
		int nextFreeId = getNextFreeId();
		
		// Now set the keys for new objects:
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			EObject object = iterator.next();
				
			// Check if the current object type is supported:
			EClass type = object.eClass();
			boolean isSupported = false;
			for (int i=0; i<supportedTypes.length; i++) {
				if (supportedTypes[i]==type) {
					isSupported = true;
					break;
				}
			}
			
			// Get the current Id:
			int currentId = ObjectKeyHelper.getObjectID(objectKeysMap.get(object));
			
			// Check if we need to create or change the key:
			if (currentId==0 && isSupported) {
				//System.out.println("Creating object id " + nextFreeId + " for object of type " + object.eClass().getName());
				int objectKey = ObjectKeyHelper.createObjectKey(object.eClass(), nextFreeId++, supportedTypes);
				objectKeysMap.put(object, objectKey);
				changed = true;
			}
			else if (currentId!=0 && !isSupported) {
				//System.out.println("Removing illegal object id for object of type " + object.eClass().getName());
				objectKeysMap.remove(object);
				changed = true;
			}
			
		}
		
		// Done.
		return changed;
		
	}
	
	/*
	 * Get the next free object Id.
	 */
	private int getNextFreeId() {
		
		// Make sure the keys map is not null.
		getObjectKeysMap();
		
		// Compute the next free object Id.
		int nextFreeId = 1;
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			int id = ObjectKeyHelper.getObjectID(objectKeysMap.get(iterator.next()));
			if (id>=nextFreeId) {
				nextFreeId = id+1;
			}
		}
		
		// Done.
		return nextFreeId;
		
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
	public int[] getObjectKeys() {

		// Make sure the object keys map is not null.
		getObjectKeysMap();

		// Copy the map contents to an integer array:
		List<Integer> objectKeys = new ArrayList<Integer>(24);
		TreeIterator<EObject> iterator = resource.getAllContents();
		while (iterator.hasNext()) {
			objectKeys.add(objectKeysMap.get(iterator.next()));
		}
		int[] result = new int[objectKeys.size()];
		for (int i = 0; i < objectKeys.size(); i++) {
			result[i] = objectKeys.get(i);
		}
		return result;

	}

	/**
	 * @generated NOT
	 */
	public void setObjectKeys(int[] objectKeys) {

		// Make sure the object keys map is not null and empty it.
		getObjectKeysMap().clear();
		
		// Copy the map contents of the integer array to the map:
		TreeIterator<EObject> iterator = resource.getAllContents();
		int index = 0;
		while (iterator.hasNext() && index<objectKeys.length) {
			EObject object = iterator.next();
			int key = objectKeys[index++];
			//System.out.println("Setting object id " + ObjectKeyHelper.getObjectID(key) + " for object of type " + object.eClass().getName());
			objectKeysMap.put(object, key);
		}

	}

	/**
	 * @generated NOT
	 */
	public int getObjectCount() {
		if (emfGraph==null) {
			getEmfGraph();
		}
		return emfGraph.geteObjects().size();
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
	 * The cached value of the '{@link #getObjectKeysMap() <em>Object Identities Map</em>}' map.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getObjectKeysMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<EObject, Integer> objectKeysMap;

	/**
	 * The default value of the '{@link #getObjectKeys() <em>Object Identities</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectKeys()
	 * @generated
	 * @ordered
	 */
	protected static final int[] OBJECT_IDENTITIES_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getObjectCount() <em>Object Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectCount()
	 * @generated
	 * @ordered
	 */
	protected static final int OBJECT_COUNT_EDEFAULT = 0;

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
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES_MAP:
				return ((InternalEList<?>)getObjectKeysMap()).basicRemove(otherEnd, msgs);
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
				if (coreType) return getObjectKeysMap();
				else return getObjectKeysMap().map();
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				return getObjectKeys();
			case StateSpacePackage.MODEL__OBJECT_COUNT:
				return getObjectCount();
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
				((EStructuralFeature.Setting)getObjectKeysMap()).set(newValue);
				return;
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				setObjectKeys((int[])newValue);
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
				getObjectKeysMap().clear();
				return;
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				setObjectKeys(OBJECT_IDENTITIES_EDEFAULT);
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
				return objectKeysMap != null && !objectKeysMap.isEmpty();
			case StateSpacePackage.MODEL__OBJECT_IDENTITIES:
				return OBJECT_IDENTITIES_EDEFAULT == null ? getObjectKeys() != null : !OBJECT_IDENTITIES_EDEFAULT.equals(getObjectKeys());
			case StateSpacePackage.MODEL__OBJECT_COUNT:
				return getObjectCount() != OBJECT_COUNT_EDEFAULT;
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
