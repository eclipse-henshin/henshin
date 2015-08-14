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
 * A representation of the model object '<em><b>Node Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getIncoming <em>Incoming</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSuperType <em>Super Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSubType <em>Sub Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType()
 * @model
 * @generated
 */
public interface NodeType extends AttributedElem, AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType_Outgoing()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<EdgeType> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType_Incoming()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<EdgeType> getIncoming();

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
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type Graph</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getNodeTypes <em>Node Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Graph</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Graph</em>' container reference.
	 * @see #setTypeGraph(TypeGraph)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType_TypeGraph()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getNodeTypes
	 * @model opposite="nodeTypes" required="true" transient="false"
	 * @generated
	 */
	TypeGraph getTypeGraph();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getTypeGraph <em>Type Graph</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Graph</em>' container reference.
	 * @see #getTypeGraph()
	 * @generated
	 */
	void setTypeGraph(TypeGraph value);

	/**
	 * Returns the value of the '<em><b>Super Type</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSubType <em>Sub Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Type</em>' containment reference.
	 * @see #setSuperType(InheritanceRelation)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType_SuperType()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSubType
	 * @model opposite="subType" containment="true"
	 * @generated
	 */
	InheritanceRelation getSuperType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSuperType <em>Super Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type</em>' containment reference.
	 * @see #getSuperType()
	 * @generated
	 */
	void setSuperType(InheritanceRelation value);

	/**
	 * Returns the value of the '<em><b>Sub Type</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSuperType <em>Super Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Type</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Type</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getNodeType_SubType()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSuperType
	 * @model opposite="superType"
	 * @generated
	 */
	EList<InheritanceRelation> getSubType();

} // NodeType
