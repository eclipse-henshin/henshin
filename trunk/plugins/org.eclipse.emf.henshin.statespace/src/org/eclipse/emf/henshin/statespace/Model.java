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

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.util.Match;

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
	 * Set the resource that contains the actual model elements.
	 * @param value the new value of the '<em>Resource</em>' attribute.
	 * @see #getResource()
	 * @generated
	 */
	void setResource(Resource value);

	/**
	 * Get the node IDs map for this state model.
	 * @return the value of the '<em>Node IDs</em>' map.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel_NodeIDs()
	 * @model mapType="org.eclipse.emf.henshin.statespace.NodeID<org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EIntegerObject>"
	 * @generated
	 */
	EMap<EObject, Integer> getNodeIDsMap();

	/**
	 * Get the node IDs of this state model as an integer array.
	 * This is derived from {@link #getNodeIDsMap()}.
	 * @return the value of the '<em>Node IDs</em>' attribute.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getModel_NodeIDs()
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray" transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	int[] getNodeIDs();

	/**
	 * Get a copy of this model.
	 * @param Optional match.
	 * @model matchDataType="org.eclipse.emf.henshin.statespace.Match"
	 * @generated
	 */
	Model getCopy(Match match);

	/**
	 * Update the node IDs for this model.
	 * This generates new unique IDs for new 
	 * elements in this model.
	 * @model
	 * @generated
	 */
	void updateNodeIDs();

} // Model
