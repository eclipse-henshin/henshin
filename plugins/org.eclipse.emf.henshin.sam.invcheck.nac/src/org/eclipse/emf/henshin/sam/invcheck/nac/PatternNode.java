/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac;

import org.eclipse.emf.henshin.sam.model.samgraph.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pattern Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInRule <em>Same In Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInProp <em>Same In Prop</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getPatternNode()
 * @model
 * @generated
 */
public interface PatternNode extends Node {
	/**
	 * Returns the value of the '<em><b>Same In Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Same In Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Same In Rule</em>' reference.
	 * @see #setSameInRule(Node)
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getPatternNode_SameInRule()
	 * @model
	 * @generated
	 */
	Node getSameInRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInRule <em>Same In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Same In Rule</em>' reference.
	 * @see #getSameInRule()
	 * @generated
	 */
	void setSameInRule(Node value);

	/**
	 * Returns the value of the '<em><b>Same In Prop</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Same In Prop</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Same In Prop</em>' reference.
	 * @see #setSameInProp(Node)
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#getPatternNode_SameInProp()
	 * @model
	 * @generated
	 */
	Node getSameInProp();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode#getSameInProp <em>Same In Prop</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Same In Prop</em>' reference.
	 * @see #getSameInProp()
	 * @generated
	 */
	void setSameInProp(Node value);

} // PatternNode
