/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.tests.testmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Val</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Val#getIntvl <em>Intvl</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Val#getValname <em>Valname</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Val#getIntlist <em>Intlist</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getVal()
 * @model
 * @generated
 */
public interface Val extends EObject {
	/**
	 * Returns the value of the '<em><b>Intvl</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intvl</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Intvl</em>' attribute.
	 * @see #setIntvl(int)
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getVal_Intvl()
	 * @model
	 * @generated
	 */
	int getIntvl();
	
	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.Val#getIntvl <em>Intvl</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intvl</em>' attribute.
	 * @see #getIntvl()
	 * @generated
	 */
	void setIntvl(int value);
	
	/**
	 * Returns the value of the '<em><b>Valname</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Valname</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Valname</em>' attribute.
	 * @see #setValname(String)
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getVal_Valname()
	 * @model
	 * @generated
	 */
	String getValname();
	
	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.henshin.tests.testmodel.Val#getValname
	 * <em>Valname</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Valname</em>' attribute.
	 * @see #getValname()
	 * @generated
	 */
	void setValname(String value);

	/**
	 * Returns the value of the '<em><b>Intlist</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intlist</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intlist</em>' attribute list.
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getVal_Intlist()
	 * @model
	 * @generated
	 */
	EList<Integer> getIntlist();
	
} // Val
