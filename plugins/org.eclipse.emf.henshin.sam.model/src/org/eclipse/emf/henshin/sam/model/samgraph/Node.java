/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  A <code>Node</code> is an <code>AttributedElement</code> and refers to as many <code>Attributes</code> with <code>AttributeType</code> as defined for the corresponding node type (TO BE CHECKED).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getInstanceOf <em>Instance Of</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getIncoming <em>Incoming</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends AttributedElem, AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Of</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Of</em>' reference.
	 * @see #setInstanceOf(NodeType)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getNode_InstanceOf()
	 * @model required="true"
	 * @generated
	 */
	NodeType getInstanceOf();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getInstanceOf <em>Instance Of</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Of</em>' reference.
	 * @see #getInstanceOf()
	 * @generated
	 */
	void setInstanceOf(NodeType value);

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
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getNode_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samgraph.Edge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getNode_Outgoing()
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.Edge#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Edge> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samgraph.Edge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getNode_Incoming()
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.Edge#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<Edge> getIncoming();
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='Graph g = (Graph) this.eContainer();\nEObject container = g.eContainer();\nif (container == null) {\n\treturn null;\n} else if (SamgraphconditionPackage.eINSTANCE.getGraphCondition().isSuperTypeOf(container.eClass())) {\n\treturn (GraphCondition) container;\n} else {\n\treturn null;\n}'"
	 * @generated
	 */
	GraphCondition getCondition();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Node result = SamgraphFactory.eINSTANCE.createNode();\nresult.setInstanceOf(this.getInstanceOf());\nresult.setName(this.getName());\nreturn result;'"
	 * @generated
	 */
	Node copy();

} // Node
