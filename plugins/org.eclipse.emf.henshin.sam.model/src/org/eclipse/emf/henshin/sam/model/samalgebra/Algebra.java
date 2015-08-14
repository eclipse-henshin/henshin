/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Algebra</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getConstants <em>Constants</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getOperations <em>Operations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getSignature <em>Signature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage#getAlgebra()
 * @model
 * @generated
 */
public interface Algebra extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Constants</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.Operation},
	 * and the value is of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constants</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constants</em>' map.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage#getAlgebra_Constants()
	 * @model mapType="org.eclipse.emf.henshin.sam.model.samalgebra.ConstantDataElementMap<org.eclipse.emf.henshin.sam.model.samalgebra.Operation, org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.DataElement>"
	 * @generated
	 */
	EMap<Operation, DataElement> getConstants();

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.Operation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage#getAlgebra_Operations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operation> getOperations();

	/**
	 * Returns the value of the '<em><b>Signature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature</em>' reference.
	 * @see #setSignature(AlgebraSignature)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage#getAlgebra_Signature()
	 * @model required="true"
	 * @generated
	 */
	AlgebraSignature getSignature();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.Algebra#getSignature <em>Signature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Signature</em>' reference.
	 * @see #getSignature()
	 * @generated
	 */
	void setSignature(AlgebraSignature value);

} // Algebra
