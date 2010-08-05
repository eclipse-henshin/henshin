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
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Transformation Unit</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationUnit#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationUnit#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationUnit#getParameterMappings <em>Parameter Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit()
 * @model abstract="true"
 * @generated
 */
public interface TransformationUnit extends DescribedElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Activated</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activated</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Activated</em>' attribute.
	 * @see #setActivated(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_Activated()
	 * @model
	 * @generated
	 */
	boolean isActivated();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.henshin.model.TransformationUnit#isActivated
	 * <em>Activated</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Activated</em>' attribute.
	 * @see #isActivated()
	 * @generated
	 */
	void setActivated(boolean value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Parameter}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Parameter#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_Parameters()
	 * @see org.eclipse.emf.henshin.model.Parameter#getUnit
	 * @model opposite="unit" containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.ParameterMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_ParameterMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterMapping> getParameterMappings();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Collects all transitively contained units and returns that list. If not
	 * unit is encapsulated at all, an empty list is returned. All units have to
	 * implement this method.
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" ordered="false"
	 * @generated
	 */
	EList<TransformationUnit> getAllSubUnits();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Parameter getParameterByName(String parametername);

} // TransformationUnit
