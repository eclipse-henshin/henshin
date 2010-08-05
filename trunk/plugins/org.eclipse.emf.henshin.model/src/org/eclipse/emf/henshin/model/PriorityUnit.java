/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Priority Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.PriorityUnit#getSubUnits <em>Sub Units</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getPriorityUnit()
 * @model
 * @generated
 */
public interface PriorityUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>Sub Units</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.TransformationUnit}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Units</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Units</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getPriorityUnit_SubUnits()
	 * @model
	 * @generated
	 */
	EList<TransformationUnit> getSubUnits();

} // PriorityUnit
