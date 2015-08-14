/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Logical GC Coupling</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>GraphCondition<code> may be a disjunction or conjunction (<code>LogicalOperator</code>) of <code>GraphCondition</code>s.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getLogicalGCCoupling()
 * @model
 * @generated
 */
public interface LogicalGCCoupling extends GraphCondition {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator
	 * @see #setOperator(LogicalOperator)
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getLogicalGCCoupling_Operator()
	 * @model
	 * @generated
	 */
	LogicalOperator getOperator();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(LogicalOperator value);

	/**
	 * Returns the value of the '<em><b>Operands</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operands</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operands</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#getLogicalGCCoupling_Operands()
	 * @model containment="true" lower="2"
	 * @generated
	 */
	EList<GraphCondition> getOperands();

} // LogicalGCCoupling
