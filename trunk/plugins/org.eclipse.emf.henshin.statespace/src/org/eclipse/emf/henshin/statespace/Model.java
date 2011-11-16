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
package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.matching.EmfGraph;

/**
 * Container class for state models.
 * These state models are transient. 
 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject {
	
	/**
	 * Get the resource that contains the actual model elements.
	 * @return the value of the '<em>Resource</em>' attribute.
	 * @see #setResource(Resource)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel_Resource()
	 * @model transient="true"
	 * @generated
	 */
	Resource getResource();
	
	/**
	 * Get the associated {@link EmfGraph} instance for this model.
	 * @return the value of the '<em>Emf Graph</em>' attribute.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel_EmfGraph()
	 * @model dataType="org.eclipse.emf.henshin.statespace.EmfGraph" transient="true" changeable="false"
	 * @generated
	 */
	EmfGraph getEmfGraph();

	/**
	 * Get the object identities map for this state model.
	 * @return the value of the '<em>Object Identities</em>' map.
	 * @model mapType="org.eclipse.emf.henshin.statespace.ObjectIdentity<org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EIntegerObject>"
	 * @generated
	 */
	EMap<EObject, Integer> getObjectIdentitiesMap();

	/**
	 * Get the object identities of this state model as an integer array.
	 * This is derived from {@link #getObjectIdentitiesMap()}.
	 * @return the value of the '<em>Object Identities</em>' attribute.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel_NodeIDs()
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int[] getObjectIdentities();

	/**
	 * Set the object identities of this state model as an integer array.
	 * This forwards to {@link #getObjectIdentitiesMap()}.
	 * @param objectIdentities the new value of the '<em>Object Identities</em>' attribute.
	 * @see #getObjectIdentities()
	 * @generated
	 */
	void setObjectIdentities(int[] value);

	/**
	 * Get the number of objects in this model.
	 * This is derived from {@link #getEmfGraph()}.
	 * @return the value of the '<em>Object Count</em>' attribute.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel_ObjectCount()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int getObjectCount();

	/**
	 * Get a copy of this model.
	 * @param Optional match.
	 * @model matchDataType="org.eclipse.emf.henshin.statespace.Match"
	 * @generated
	 */
	Model getCopy(Match match);

	/**
	 * @model
	 * @generated
	 */
	boolean updateObjectIdentities(EClass[] objectTypes);

	/**
	 * Collect missing root objects from the {@link EmfGraph} of this model.
	 * New root objects will be added to this objects resource.
	 * @model
	 * @generated
	 */
	void collectMissingRootObjects();

} // Model
