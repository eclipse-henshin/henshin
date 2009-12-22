/**
 * <copyright>
 * </copyright>
 *
 * $Id: UnaryFormula.java,v 1.1 2009/10/28 10:38:06 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Formula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.UnaryFormula#getChild <em>Child</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getUnaryFormula()
 * @model
 * @generated
 */
public interface UnaryFormula extends Formula {
        /**
         * Returns the value of the '<em><b>Child</b></em>' containment reference.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Child</em>' containment reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Child</em>' containment reference.
         * @see #setChild(Formula)
         * @see org.eclipse.emf.henshin.model.HenshinPackage#getUnaryFormula_Child()
         * @model containment="true"
         * @generated
         */
        Formula getChild();

        /**
         * Sets the value of the '{@link org.eclipse.emf.henshin.model.UnaryFormula#getChild <em>Child</em>}' containment reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Child</em>' containment reference.
         * @see #getChild()
         * @generated
         */
        void setChild(Formula value);

} // UnaryFormula
