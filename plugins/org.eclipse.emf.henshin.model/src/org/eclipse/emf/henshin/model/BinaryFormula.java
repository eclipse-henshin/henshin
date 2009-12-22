/**
 * <copyright>
 * </copyright>
 *
 * $Id: BinaryFormula.java,v 1.1 2009/10/28 10:38:04 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary Formula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.BinaryFormula#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.BinaryFormula#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getBinaryFormula()
 * @model
 * @generated
 */
public interface BinaryFormula extends Formula {
        /**
         * Returns the value of the '<em><b>Left</b></em>' containment reference.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Left</em>' containment reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Left</em>' containment reference.
         * @see #setLeft(Formula)
         * @see org.eclipse.emf.henshin.model.HenshinPackage#getBinaryFormula_Left()
         * @model containment="true"
         * @generated
         */
        Formula getLeft();

        /**
         * Sets the value of the '{@link org.eclipse.emf.henshin.model.BinaryFormula#getLeft <em>Left</em>}' containment reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Left</em>' containment reference.
         * @see #getLeft()
         * @generated
         */
        void setLeft(Formula value);

        /**
         * Returns the value of the '<em><b>Right</b></em>' containment reference.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Right</em>' containment reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Right</em>' containment reference.
         * @see #setRight(Formula)
         * @see org.eclipse.emf.henshin.model.HenshinPackage#getBinaryFormula_Right()
         * @model containment="true"
         * @generated
         */
        Formula getRight();

        /**
         * Sets the value of the '{@link org.eclipse.emf.henshin.model.BinaryFormula#getRight <em>Right</em>}' containment reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Right</em>' containment reference.
         * @see #getRight()
         * @generated
         */
        void setRight(Formula value);

} // BinaryFormula
