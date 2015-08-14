/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getNodeTypes <em>Node Types</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getEdgeTypes <em>Edge Types</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getConditions <em>Conditions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getTypeGraph()
 * @model
 * @generated
 */
public interface TypeGraph extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Node Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Types</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getTypeGraph_NodeTypes()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getTypeGraph
	 * @model opposite="typeGraph" containment="true"
	 * @generated
	 */
	EList<NodeType> getNodeTypes();

	/**
	 * Returns the value of the '<em><b>Edge Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTypeGraph <em>Type Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edge Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge Types</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getTypeGraph_EdgeTypes()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTypeGraph
	 * @model opposite="typeGraph" containment="true"
	 * @generated
	 */
	EList<EdgeType> getEdgeTypes();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getTypeGraph_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraphCondition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getTypeGraph_Conditions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TypeGraphCondition> getConditions();

} // TypeGraph
