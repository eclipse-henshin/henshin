/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Negated Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>NegatedCondition</code> is false whenever the included <code>GraphCondition</code> is false and the other way round.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition#getOperand <em>Operand</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getNegatedCondition()
 * @model
 * @generated
 */
public interface NegatedCondition extends GraphCondition {
	/**
	 * Returns the value of the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operand</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand</em>' containment reference.
	 * @see #setOperand(GraphCondition)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getNegatedCondition_Operand()
	 * @model containment="true"
	 * @generated
	 */
	GraphCondition getOperand();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition#getOperand <em>Operand</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand</em>' containment reference.
	 * @see #getOperand()
	 * @generated
	 */
	void setOperand(GraphCondition value);

} // NegatedCondition
