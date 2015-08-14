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
 * A representation of the model object '<em><b>Algebra Signature</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An <code>Algebra</code> is given by a number of <code>sorts</code>, describing the different type of data sets it contains.
 * The <code>operations</code> refer to an <code>OperationSignature</code> defined over these <code>sorts</code> (TO BE CHECKED).
 * 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getSorts <em>Sorts</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getOperationSymbols <em>Operation Symbols</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getExtends <em>Extends</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getAlgebraSignature()
 * @model
 * @generated
 */
public interface AlgebraSignature extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Sorts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sorts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sorts</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getAlgebraSignature_Sorts()
	 * @model containment="true"
	 * @generated
	 */
	EList<Sort> getSorts();

	/**
	 * Returns the value of the '<em><b>Operation Symbols</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.OperationSymbol}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Symbols</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Symbols</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getAlgebraSignature_OperationSymbols()
	 * @model containment="true"
	 * @generated
	 */
	EList<OperationSymbol> getOperationSymbols();

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
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getAlgebraSignature_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Extends</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extends</em>' reference.
	 * @see #setExtends(AlgebraSignature)
	 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage#getAlgebraSignature_Extends()
	 * @model
	 * @generated
	 */
	AlgebraSignature getExtends();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samalgebra.signature.AlgebraSignature#getExtends <em>Extends</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extends</em>' reference.
	 * @see #getExtends()
	 * @generated
	 */
	void setExtends(AlgebraSignature value);

} // AlgebraSignature
