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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Node#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Node#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Node#getGraph <em>Graph</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Node#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Node#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Node#getAllEdges <em>All Edges</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='uniqueAttributeTypes'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL uniqueAttributeTypes='attributes->forAll(attr1,attr2 : Attribute| attr1<>attr2 implies attr1.type <> attr2.type)' uniqueAttributeTypes.Msg='_Ocl_Msg_Node_uniqueAttributeTypes'"
 * @generated
 */
public interface Node extends NamedElement, GraphElement {
	
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClass)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode_Type()
	 * @model required="true"
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Node#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Attribute}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Attribute#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode_Attributes()
	 * @see org.eclipse.emf.henshin.model.Attribute#getNode
	 * @model opposite="node" containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Graph</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Graph#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graph</em>' container reference.
	 * @see #setGraph(Graph)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode_Graph()
	 * @see org.eclipse.emf.henshin.model.Graph#getNodes
	 * @model opposite="nodes" required="true" transient="false"
	 * @generated
	 */
	Graph getGraph();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Node#getGraph <em>Graph</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graph</em>' container reference.
	 * @see #getGraph()
	 * @generated
	 */
	void setGraph(Graph value);

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Edge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Edge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode_Incoming()
	 * @see org.eclipse.emf.henshin.model.Edge#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Edge> getIncoming();

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Edge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Edge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode_Outgoing()
	 * @see org.eclipse.emf.henshin.model.Edge#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Edge> getOutgoing();

	/**
	 * Returns the value of the '<em><b>All Edges</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Edge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns an <i>unmodifiable</i> list of all coinciding edges of a node.<br>
	 * Remark: This list is a snapshot of the current set of incoming and outgoing edges
	 * i.e. it is not synchronized with subsequent changes of the sets of
	 * incoming and outgoing edges. After such changes this method has to be
	 * called again to retrieve an updated list of edges.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Edges</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getNode_AllEdges()
	 * @model transient="true" changeable="false" volatile="true" derived="true" ordered="false"
	 * @generated
	 */
	EList<Edge> getAllEdges();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns an <i>unmodifiable</i> list which contains all outgoing edges
	 * this node contains, which are of the given type.<br>
	 * Remark: This list is a snapshot according to the current set of outgoing
	 * edges i.e. it is not synchronized with subsequent changes of that set.
	 * After such changes this method has to be called again to retrieve an
	 * updated list of edges.
	 * </p>
	 * <!-- end-user-doc -->
	 * @model ordered="false" typeRequired="true"
	 * @generated
	 */
	EList<Edge> getOutgoing(EReference type);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns an <i>unmodifiable</i> list which contains all incoming edges
	 * this node contains, which are of the given type.<br>
	 * Remark: This list is a snapshot according to the current set of incoming
	 * edges i.e. it is not synchronized with subsequent changes of that set.
	 * After such changes this method has to be called again to retrieve an
	 * updated list of edges.
	 * </p> 
	 * <!-- end-user-doc -->
	 * @model ordered="false" typeRequired="true"
	 * @generated
	 */
	EList<Edge> getIncoming(EReference type);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns an {@link Edge} with this node as source, the given node as
	 * target and the given type as edge type. If no such instance can be found,
	 * <code>null</code> is returned
	 * </p>
	 * <!-- end-user-doc -->
	 * @model typeRequired="true" targetRequired="true"
	 * @generated
	 */
	Edge getOutgoing(EReference type, Node target);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns an {@link Edge} with this node as target, the given node as
	 * source and the given type as edge type. If no such instance can be found,
	 * <code>null</code> is returned
	 * </p>
	 * <!-- end-user-doc -->
	 * @model typeRequired="true" sourceRequired="true"
	 * @generated
	 */
	Edge getIncoming(EReference type, Node source);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns an {@link Attribute} instance according to the give attribute
	 * type. If no such instance can be found, <code>null</code> is returned
	 * </p>
	 * <!-- end-user-doc -->
	 * @model typeRequired="true"
	 * @generated
	 */
	Attribute getAttribute(EAttribute type);

} // Node
