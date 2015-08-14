/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samtypegraph.TypeGraph;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>Graph</code> is typed over a <code>TypeGraph</code>.
 * It contains <code>nodes</code> with types occurring in the <code>TypeGraph</code> (TO BE CHECKED).
 * It contains <code>edges</code> with types occurring in the <code>TypeGraph</code> such that source and target nodes are typed as described by the source and target type of the corresponding edge type (TO BE CHECKED). 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Graph#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Graph#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Graph#getTypedOver <em>Typed Over</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Graph#getAttributeCondition <em>Attribute Condition</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getGraph()
 * @model
 * @generated
 */
public interface Graph extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samgraph.Node}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getGraph_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Node> getNodes();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samgraph.Edge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getGraph_Edges()
	 * @model containment="true"
	 * @generated
	 */
	EList<Edge> getEdges();

	/**
	 * Returns the value of the '<em><b>Typed Over</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Typed Over</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typed Over</em>' reference.
	 * @see #setTypedOver(TypeGraph)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getGraph_TypedOver()
	 * @model
	 * @generated
	 */
	TypeGraph getTypedOver();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Graph#getTypedOver <em>Typed Over</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typed Over</em>' reference.
	 * @see #getTypedOver()
	 * @generated
	 */
	void setTypedOver(TypeGraph value);

	/**
	 * Returns the value of the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An <code>attributeCondition</code> refers to an <code>Operation</code> constraining attribute values of nodes and edges in the corresponding <code>Graph</code>.  
	 * <code>AttributeUse</code> is an <code>OperationParameter</code> and needs to refer to an <code>Attribute</code> belonging to a node or edge of the corresponding <code>Graph</code> (TO BE CHECKED).
	 * 
	 * In general, the <code>attributeCondition</code> does not constrain each attribute to one concrete value, but to a set of values. Therefore, <code>Graph</code> may describe a set of graphs, where the node and edge structure is identical, but the attributes may have different valid values. 
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Attribute Condition</em>' containment reference.
	 * @see #setAttributeCondition(OperationUse)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getGraph_AttributeCondition()
	 * @model containment="true"
	 * @generated
	 */
	OperationUse getAttributeCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Graph#getAttributeCondition <em>Attribute Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Condition</em>' containment reference.
	 * @see #getAttributeCondition()
	 * @generated
	 */
	void setAttributeCondition(OperationUse value);

} // Graph
