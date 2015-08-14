/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Symbol Use</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getTheOperationSymbol <em>The Operation Symbol</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getOperationSymbolUse()
 * @model
 * @generated
 */
public interface OperationSymbolUse extends OperationTermParameter {
	/**
	 * Returns the value of the '<em><b>The Operation Symbol</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>The Operation Symbol</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>The Operation Symbol</em>' reference.
	 * @see #setTheOperationSymbol(OperationSymbol)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getOperationSymbolUse_TheOperationSymbol()
	 * @model required="true"
	 * @generated
	 */
	OperationSymbol getTheOperationSymbol();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationSymbolUse#getTheOperationSymbol <em>The Operation Symbol</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>The Operation Symbol</em>' reference.
	 * @see #getTheOperationSymbol()
	 * @generated
	 */
	void setTheOperationSymbol(OperationSymbol value);

	/**
	 * Returns the value of the '<em><b>Operands</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.OperationTermParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operands</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operands</em>' reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage#getOperationSymbolUse_Operands()
	 * @model
	 * @generated
	 */
	EList<OperationTermParameter> getOperands();

} // OperationSymbolUse
