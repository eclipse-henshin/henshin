/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse;

import org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Term Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter#getReferences <em>References</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getOperationTermParameter()
 * @model
 * @generated
 */
public interface OperationTermParameter extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>References</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>References</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>References</em>' reference.
	 * @see #setReferences(OperandSymbol)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getOperationTermParameter_References()
	 * @model
	 * @generated
	 */
	OperandSymbol getReferences();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter#getReferences <em>References</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>References</em>' reference.
	 * @see #getReferences()
	 * @generated
	 */
	void setReferences(OperandSymbol value);

} // OperationTermParameter
