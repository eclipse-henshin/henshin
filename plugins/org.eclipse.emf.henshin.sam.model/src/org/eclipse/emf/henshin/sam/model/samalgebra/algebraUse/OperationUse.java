/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.sam.model.samalgebra.Operation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation Use</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getTheOperation <em>The Operation</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getOperands <em>Operands</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage#getOperationUse()
 * @model
 * @generated
 */
public interface OperationUse extends OperationParameter {
	/**
	 * Returns the value of the '<em><b>The Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>The Operation</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>The Operation</em>' reference.
	 * @see #setTheOperation(Operation)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage#getOperationUse_TheOperation()
	 * @model required="true"
	 * @generated
	 */
	Operation getTheOperation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse#getTheOperation <em>The Operation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>The Operation</em>' reference.
	 * @see #getTheOperation()
	 * @generated
	 */
	void setTheOperation(Operation value);

	/**
	 * Returns the value of the '<em><b>Operands</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operands</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operands</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage#getOperationUse_Operands()
	 * @model containment="true"
	 * @generated
	 */
	EList<OperationParameter> getOperands();

} // OperationUse
