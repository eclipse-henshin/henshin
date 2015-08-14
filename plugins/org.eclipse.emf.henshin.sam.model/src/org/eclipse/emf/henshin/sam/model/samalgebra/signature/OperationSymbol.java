/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Symbol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getOperandSymbols <em>Operand Symbols</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getResultSymbol <em>Result Symbol</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getOperationSymbol()
 * @model
 * @generated
 */
public interface OperationSymbol extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Operand Symbols</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperandSymbol}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operand Symbols</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand Symbols</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getOperationSymbol_OperandSymbols()
	 * @model containment="true"
	 * @generated
	 */
	EList<OperandSymbol> getOperandSymbols();

	/**
	 * Returns the value of the '<em><b>Result Symbol</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result Symbol</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Symbol</em>' reference.
	 * @see #setResultSymbol(Sort)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getOperationSymbol_ResultSymbol()
	 * @model required="true"
	 * @generated
	 */
	Sort getResultSymbol();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getResultSymbol <em>Result Symbol</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Symbol</em>' reference.
	 * @see #getResultSymbol()
	 * @generated
	 */
	void setResultSymbol(Sort value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getOperationSymbol_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // OperationSymbol
