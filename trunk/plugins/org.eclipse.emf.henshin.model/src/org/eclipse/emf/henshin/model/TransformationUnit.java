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
 *        annotation="http://www.eclipse.org/emf/2002/Ecore constraints='uniqueParameterNames parameterMappingsPointToDirectSubUnit'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL uniqueParameterNames='parameters->forAll( param1, param2 : Parameter | param1 <> param2 implies param1.name <> param2.name)' uniqueParameterNames.Msg='_Ocl_Msg_TransformationUnit_uniqueParameterNames'"
 * @generated
 */
public interface TransformationUnit extends NamedElement {
	
	/**
	 * Returns the value of the '<em><b>Activated</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activated</em>' attribute.
	 * @see #setActivated(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_Activated()
	 * @model default="true"
	 * @generated
	 */
	boolean isActivated();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.TransformationUnit#isActivated <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc-->
	 * @param value the new value of the '<em>Activated</em>' attribute.
	 * @see #isActivated()
	 * @generated
	 */
	void setActivated(boolean value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Parameter}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Parameter#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_Parameters()
	 * @see org.eclipse.emf.henshin.model.Parameter#getUnit
	 * @model opposite="unit" containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Parameter getParameter(String parameter);

	/**
	 * Returns the value of the '<em><b>Parameter Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.ParameterMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_ParameterMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ParameterMapping> getParameterMappings();
	
	/**
	 * <!-- begin-user-doc --> <br>
	 * Finds all direct or transitive subunits of this unit and return them in a
	 * unique list.<br>
	 * Remark: In some units <code>getSubUnits(false)</code> has a result
	 * equivalent to method getSubUnits(), if available. However, the list
	 * return by this latter corresponds to the feature while the list return by
	 * the former method is created new with every call.<br>
	 * Note furthermore, that rules are {@link TransformationUnit}s and are
	 * therefore collected as well.
	 * 
	 * @param deep
	 *            If <code>true</code> all subunits are accumulated
	 *            transitively/recursively, otherwise only direct subunits are
	 *            collected.
	 * @return A list of subunits<br>
	 *         <!-- end-user-doc -->
	 * @model ordered="false"
	 * @generated
	 */
	EList<TransformationUnit> getSubUnits(boolean deep);

} // TransformationUnit
