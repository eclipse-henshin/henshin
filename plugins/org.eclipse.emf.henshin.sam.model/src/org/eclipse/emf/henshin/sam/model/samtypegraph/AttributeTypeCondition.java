/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Type Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition#getOperation <em>Operation</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributeTypeCondition()
 * @model
 * @generated
 */
public interface AttributeTypeCondition extends TypeGraphCondition {
	/**
	 * Returns the value of the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' containment reference.
	 * @see #setOperation(OperationSymbolUse)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributeTypeCondition_Operation()
	 * @model containment="true" required="true"
	 * @generated
	 */
	OperationSymbolUse getOperation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeTypeCondition#getOperation <em>Operation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation</em>' containment reference.
	 * @see #getOperation()
	 * @generated
	 */
	void setOperation(OperationSymbolUse value);

} // AttributeTypeCondition
