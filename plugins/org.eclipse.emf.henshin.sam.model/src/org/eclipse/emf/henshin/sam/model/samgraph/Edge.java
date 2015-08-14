/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraph;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samtypegraph.EdgeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 *  An <code>Edge</code> is an <code>AttributedElement</code> and refers to as many <code>Attributes</code> with <code>AttributeType</code> as defined for the corresponding edge type (TO BE CHECKED).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getInstanceOf <em>Instance Of</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends AttributedElem, AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Node)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getEdge_Source()
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.Node#getOutgoing
	 * @model opposite="outgoing"
	 * @generated
	 */
	Node getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Node value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samgraph.Node#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Node)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getEdge_Target()
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.Node#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	Node getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Node value);

	/**
	 * Returns the value of the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Of</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Of</em>' reference.
	 * @see #setInstanceOf(EdgeType)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getEdge_InstanceOf()
	 * @model required="true"
	 * @generated
	 */
	EdgeType getInstanceOf();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getInstanceOf <em>Instance Of</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Of</em>' reference.
	 * @see #getInstanceOf()
	 * @generated
	 */
	void setInstanceOf(EdgeType value);

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
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getEdge_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Edge#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Graph graph = (Graph) this.eContainer();\nEObject container = graph.eContainer();\nif (container != null && container.eClass() == SamgraphconditionPackage.eINSTANCE.getQuantification()) {\n\tQuantification q = (Quantification) container;\n\tif (q.eContainer() != null && q.eContainer().eClass() == SamgraphconditionPackage.eINSTANCE.getNegatedCondition()) {\n\t\tif ((this.getSource().eContainer() != this.eContainer()) || this.getTarget().eContainer() != this.eContainer()) {\n\t\t\treturn true;\n\t\t} else {\n\t\t\tif (graph.getNodes().isEmpty()) {\n\t\t\t\treturn true;\n\t\t\t}\n\t\t}\n\t}\n}\nreturn false;'"
	 * @generated
	 */
	boolean partOfNacInterface();

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
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Edge result = SamgraphFactory.eINSTANCE.createEdge();\r\nresult.setInstanceOf(this.getInstanceOf());\r\nresult.setName(this.getName());\r\nreturn result;'"
	 * @generated
	 */
	Edge copy();

} // Edge
