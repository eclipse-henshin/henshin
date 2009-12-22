/**
 * <copyright>
 * </copyright>
 *
 * $Id: Variable.java,v 1.1 2009/10/28 10:38:08 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Variable#getRule <em>Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends DescribedElement, NamedElement {
        /**
         * Returns the value of the '<em><b>Rule</b></em>' container reference.
         * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Rule#getVariables <em>Variables</em>}'.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Rule</em>' container reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Rule</em>' container reference.
         * @see #setRule(Rule)
         * @see org.eclipse.emf.henshin.model.HenshinPackage#getVariable_Rule()
         * @see org.eclipse.emf.henshin.model.Rule#getVariables
         * @model opposite="variables" transient="false"
         * @generated
         */
        Rule getRule();

        /**
         * Sets the value of the '{@link org.eclipse.emf.henshin.model.Variable#getRule <em>Rule</em>}' container reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Rule</em>' container reference.
         * @see #getRule()
         * @generated
         */
        void setRule(Rule value);

} // Variable
