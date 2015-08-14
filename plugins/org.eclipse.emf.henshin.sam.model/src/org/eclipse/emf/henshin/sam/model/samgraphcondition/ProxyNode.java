/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;

import org.eclipse.emf.henshin.sam.model.samgraph.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Proxy Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>ProxyNode</code> refers to a node that is implicitly part of the <code>context</code> of a <code>Quantification</code>. In terms of inclusion morphisms <code>ProxyNode</code>s refer to quantified nodes of the domain. Conversely, quantified nodes belonging to the codomain, but not belonging to the domain of the inclusion morphism are added explicitly to the <code>context</>.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode#getReferencedNode <em>Referenced Node</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getProxyNode()
 * @model
 * @generated
 */
public interface ProxyNode extends Node {
	/**
	 * Returns the value of the '<em><b>Referenced Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Node</em>' reference.
	 * @see #setReferencedNode(Node)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getProxyNode_ReferencedNode()
	 * @model required="true"
	 * @generated
	 */
	Node getReferencedNode();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode#getReferencedNode <em>Referenced Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Referenced Node</em>' reference.
	 * @see #getReferencedNode()
	 * @generated
	 */
	void setReferencedNode(Node value);

} // ProxyNode
