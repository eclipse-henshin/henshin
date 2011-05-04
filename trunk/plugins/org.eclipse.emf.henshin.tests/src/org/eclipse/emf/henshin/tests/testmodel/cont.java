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
 * <em><b>cont</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.henshin.tests.testmodel.cont#getContainsNode <em>
 * Contains Node</em>}</li>
 * <li>{@link org.eclipse.emf.henshin.tests.testmodel.cont#getContainsVal <em>
 * Contains Val</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getcont()
 * @model
 * @generated
 */
public interface cont extends EObject {
	/**
	 * Returns the value of the '<em><b>Contains Node</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.emf.henshin.tests.testmodel.Node}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Contains Node</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contains Node</em>' containment reference
	 *         list.
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getcont_ContainsNode()
	 * @model containment="true"
	 * @generated
	 */
	EList<Node> getContainsNode();
	
	/**
	 * Returns the value of the '<em><b>Contains Val</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.emf.henshin.tests.testmodel.Val}. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Contains Val</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contains Val</em>' containment reference
	 *         list.
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getcont_ContainsVal()
	 * @model containment="true"
	 * @generated
	 */
	EList<Val> getContainsVal();
	
} // cont
