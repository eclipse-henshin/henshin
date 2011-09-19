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
 * <em><b>Sequential Unit</b></em>'.<br>
 * <p>
 * Sequential units apply contained transformation units (including rules) in a
 * sequential manner. The application order is given by the position in its list
 * <code>subUnits</code>.
 * </p>
 * <p>
 * The default behavior is as follows: Each unit is applied one after the other.
 * If a unit cannot be applied, the sequential units aborts, rolls back all
 * changes of this sequential unit and returns <code>false</code> (indicates
 * non-successful application). Otherwise <code>true</code> is returned.
 * </p>
 * <p>
 * This behavior can be altered slightly using attributes <code>strict</code>
 * and <code>rollback</code>. The defaults are <code>strict=true</code> and
 * <code>rollback=true</code> with the semantics described above.
 * 
 * If <code>strict=false</code>, even inapplicable rules/units will not break
 * the sequential application order, i.e., all containees are tried to be
 * applied one after the other. Consequently, <i>unstrict</i> sequential units
 * are always successful.
 * 
 * If <code>rollback=false</code> no rollback is performed on abortion.
 * </p>
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.henshin.model.SequentialUnit#getSubUnits <em>Sub
 * Units</em>}</li>
 * <li>{@link org.eclipse.emf.henshin.model.SequentialUnit#isStrict <em>Strict
 * </em>}</li>
 * <li>{@link org.eclipse.emf.henshin.model.SequentialUnit#isRollback <em>
 * Rollback</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSequentialUnit()
 * @model
 * @generated
 */
public interface SequentialUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>Sub Units</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.eclipse.emf.henshin.model.TransformationUnit}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Units</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Sub Units</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSequentialUnit_SubUnits()
	 * @model
	 * @generated
	 */
	EList<TransformationUnit> getSubUnits();
	
	/**
	 * Returns the value of the '<em><b>Strict</b></em>' attribute. The default
	 * value is <code>"true"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strict</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Strict</em>' attribute.
	 * @see #setStrict(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSequentialUnit_Strict()
	 * @model default="true"
	 * @generated
	 */
	boolean isStrict();
	
	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.henshin.model.SequentialUnit#isStrict
	 * <em>Strict</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Strict</em>' attribute.
	 * @see #isStrict()
	 * @generated
	 */
	void setStrict(boolean value);
	
	/**
	 * Returns the value of the '<em><b>Rollback</b></em>' attribute. The
	 * default value is <code>"true"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rollback</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Rollback</em>' attribute.
	 * @see #setRollback(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getSequentialUnit_Rollback()
	 * @model default="true"
	 * @generated
	 */
	boolean isRollback();
	
	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.henshin.model.SequentialUnit#isRollback
	 * <em>Rollback</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Rollback</em>' attribute.
	 * @see #isRollback()
	 * @generated
	 */
	void setRollback(boolean value);
	
} // SequentialUnit
