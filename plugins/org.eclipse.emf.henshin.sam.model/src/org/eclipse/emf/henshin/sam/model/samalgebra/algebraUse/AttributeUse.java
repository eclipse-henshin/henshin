/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse;

import org.eclipse.emf.henshin.sam.model.samgraph.Attribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Use</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse#getTheAttribute <em>The Attribute</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage#getAttributeUse()
 * @model
 * @generated
 */
public interface AttributeUse extends OperationParameter {
	/**
	 * Returns the value of the '<em><b>The Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>The Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>The Attribute</em>' reference.
	 * @see #setTheAttribute(Attribute)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage#getAttributeUse_TheAttribute()
	 * @model
	 * @generated
	 */
	Attribute getTheAttribute();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AttributeUse#getTheAttribute <em>The Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>The Attribute</em>' reference.
	 * @see #getTheAttribute()
	 * @generated
	 */
	void setTheAttribute(Attribute value);

} // AttributeUse
