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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.ConditionalUnit#getIf <em>If</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.ConditionalUnit#getThen <em>Then</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.ConditionalUnit#getElse <em>Else</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit()
 * @model
 * @generated
 */
public interface ConditionalUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>If</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>If</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>If</em>' reference.
	 * @see #setIf(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit_If()
	 * @model required="true"
	 * @generated
	 */
	TransformationUnit getIf();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getIf <em>If</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>If</em>' reference.
	 * @see #getIf()
	 * @generated
	 */
	void setIf(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Then</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Then</em>' reference.
	 * @see #setThen(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit_Then()
	 * @model required="true"
	 * @generated
	 */
	TransformationUnit getThen();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getThen <em>Then</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Then</em>' reference.
	 * @see #getThen()
	 * @generated
	 */
	void setThen(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Else</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else</em>' reference.
	 * @see #setElse(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit_Else()
	 * @model
	 * @generated
	 */
	TransformationUnit getElse();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getElse <em>Else</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Else</em>' reference.
	 * @see #getElse()
	 * @generated
	 */
	void setElse(TransformationUnit value);

} // ConditionalUnit
