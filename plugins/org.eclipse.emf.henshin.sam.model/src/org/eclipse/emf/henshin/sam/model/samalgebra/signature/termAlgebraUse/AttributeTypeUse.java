/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse;

import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Type Use</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse#getTheAttributeType <em>The Attribute Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getAttributeTypeUse()
 * @model
 * @generated
 */
public interface AttributeTypeUse extends OperationTermParameter {
	/**
	 * Returns the value of the '<em><b>The Attribute Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>The Attribute Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>The Attribute Type</em>' reference.
	 * @see #setTheAttributeType(AttributeType)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getAttributeTypeUse_TheAttributeType()
	 * @model required="true"
	 * @generated
	 */
	AttributeType getTheAttributeType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.AttributeTypeUse#getTheAttributeType <em>The Attribute Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>The Attribute Type</em>' reference.
	 * @see #getTheAttributeType()
	 * @generated
	 */
	void setTheAttributeType(AttributeType value);

} // AttributeTypeUse
