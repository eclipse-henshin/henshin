/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules;

import org.eclipse.emf.henshin.sam.model.samgraph.Node;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Preserved Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>PreservedNode</code> is preserved by the rule. It is contained in the left as well as in the right <code>RuleGraph</code> (TO BE CHECKED) and refers to its equivalent in the left or right <code>RuleGraph</code>, respectively (TO BE CHECKED).
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedNode#getRefInRule <em>Ref In Rule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getPreservedNode()
 * @model
 * @generated
 */
public interface PreservedNode extends Node {
	/**
	 * Returns the value of the '<em><b>Ref In Rule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedNode#getRefInRule <em>Ref In Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ref In Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ref In Rule</em>' reference.
	 * @see #setRefInRule(PreservedNode)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getPreservedNode_RefInRule()
	 * @see org.eclipse.emf.henshin.sam.model.samrules.PreservedNode#getRefInRule
	 * @model opposite="refInRule" required="true"
	 * @generated
	 */
	PreservedNode getRefInRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.PreservedNode#getRefInRule <em>Ref In Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ref In Rule</em>' reference.
	 * @see #getRefInRule()
	 * @generated
	 */
	void setRefInRule(PreservedNode value);

} // PreservedNode
