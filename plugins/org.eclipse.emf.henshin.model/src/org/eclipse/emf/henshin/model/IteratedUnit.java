/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterated Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.IteratedUnit#getIterations <em>Iterations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.IteratedUnit#isStrict <em>Strict</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.IteratedUnit#isRollback <em>Rollback</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='iterationsNotEmpty iterationsValidJavaScript iterationsAllParametersAreDeclared'"
 * @generated
 */
public interface IteratedUnit extends UnaryUnit {
	
	/**
	 * Returns the value of the '<em><b>Iterations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterations</em>' attribute.
	 * @see #setIterations(String)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit_Iterations()
	 * @model
	 * @generated
	 */
	String getIterations();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.IteratedUnit#getIterations <em>Iterations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterations</em>' attribute.
	 * @see #getIterations()
	 * @generated
	 */
	void setIterations(String value);

	/**
	 * Returns the value of the '<em><b>Strict</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strict</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strict</em>' attribute.
	 * @see #setStrict(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit_Strict()
	 * @model default="false"
	 * @generated
	 */
	boolean isStrict();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.IteratedUnit#isStrict <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strict</em>' attribute.
	 * @see #isStrict()
	 * @generated
	 */
	void setStrict(boolean value);

	/**
	 * Returns the value of the '<em><b>Rollback</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rollback</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rollback</em>' attribute.
	 * @see #setRollback(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit_Rollback()
	 * @model default="true"
	 * @generated
	 */
	boolean isRollback();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.IteratedUnit#isRollback <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rollback</em>' attribute.
	 * @see #isRollback()
	 * @generated
	 */
	void setRollback(boolean value);

} // IteratedUnit
