/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTypeGraph <em>Type Graph</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSourceCardinality <em>Source Cardinality</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTargetCardinality <em>Target Cardinality</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType()
 * @model
 * @generated
 */
public interface EdgeType extends AttributedElem, AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(NodeType)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_Source()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getOutgoing
	 * @model opposite="outgoing"
	 * @generated
	 */
	NodeType getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(NodeType value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(NodeType)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_Target()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	NodeType getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(NodeType value);

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
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Type Graph</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getEdgeTypes <em>Edge Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Graph</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Graph</em>' container reference.
	 * @see #setTypeGraph(TypeGraph)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_TypeGraph()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph#getEdgeTypes
	 * @model opposite="edgeTypes" required="true" transient="false"
	 * @generated
	 */
	TypeGraph getTypeGraph();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTypeGraph <em>Type Graph</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Graph</em>' container reference.
	 * @see #getTypeGraph()
	 * @generated
	 */
	void setTypeGraph(TypeGraph value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The default value is <code>"UNDIRECTED"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection
	 * @see #setDirection(EdgeDirection)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_Direction()
	 * @model default="UNDIRECTED"
	 * @generated
	 */
	EdgeDirection getDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeDirection
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(EdgeDirection value);

	/**
	 * Returns the value of the '<em><b>Source Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Cardinality</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Cardinality</em>' containment reference.
	 * @see #setSourceCardinality(Cardinality)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_SourceCardinality()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Cardinality getSourceCardinality();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getSourceCardinality <em>Source Cardinality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Cardinality</em>' containment reference.
	 * @see #getSourceCardinality()
	 * @generated
	 */
	void setSourceCardinality(Cardinality value);

	/**
	 * Returns the value of the '<em><b>Target Cardinality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Cardinality</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Cardinality</em>' containment reference.
	 * @see #setTargetCardinality(Cardinality)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getEdgeType_TargetCardinality()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Cardinality getTargetCardinality();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType#getTargetCardinality <em>Target Cardinality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Cardinality</em>' containment reference.
	 * @see #getTargetCardinality()
	 * @generated
	 */
	void setTargetCardinality(Cardinality value);

} // EdgeType
