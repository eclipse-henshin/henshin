/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Element</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getGraphElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GraphElement extends EObject {
	
	/**
	 * <!-- begin-user-doc -->
	 * Returns the graph this graph element is contained in.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Graph getGraph();

} // GraphElement
