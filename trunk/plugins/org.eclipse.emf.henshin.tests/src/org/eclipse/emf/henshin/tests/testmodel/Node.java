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
 * <em><b>Node</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Node#getHasVals <em>Has Vals</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Node#getParentNode <em>Parent Node</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Node#getChildNodes <em>Child Nodes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.tests.testmodel.Node#getNodename <em>Nodename</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends EObject {
	/**
	 * Returns the value of the '<em><b>Has Vals</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.tests.testmodel.Val}.
	 * <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Has Vals</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Vals</em>' reference list.
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getNode_HasVals()
	 * @model
	 * @generated
	 */
	EList<Val> getHasVals();
	
	/**
	 * Returns the value of the '<em><b>Parent Node</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getChildNodes <em>Child Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Node</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Node</em>' reference.
	 * @see #setParentNode(Node)
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getNode_ParentNode()
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node#getChildNodes
	 * @model opposite="childNodes"
	 * @generated
	 */
	Node getParentNode();
	
	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getParentNode <em>Parent Node</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Parent Node</em>' reference.
	 * @see #getParentNode()
	 * @generated
	 */
	void setParentNode(Node value);
	
	/**
	 * Returns the value of the '<em><b>Child Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.tests.testmodel.Node}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getParentNode <em>Parent Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Child Nodes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Child Nodes</em>' reference list.
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getNode_ChildNodes()
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node#getParentNode
	 * @model opposite="parentNode"
	 * @generated
	 */
	EList<Node> getChildNodes();
	
	/**
	 * Returns the value of the '<em><b>Nodename</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodename</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Nodename</em>' attribute.
	 * @see #setNodename(String)
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#getNode_Nodename()
	 * @model
	 * @generated
	 */
	String getNodename();
	
	/**
	 * Sets the value of the '
	 * {@link org.eclipse.emf.henshin.tests.testmodel.Node#getNodename
	 * <em>Nodename</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Nodename</em>' attribute.
	 * @see #getNodename()
	 * @generated
	 */
	void setNodename(String value);
	
} // Node
