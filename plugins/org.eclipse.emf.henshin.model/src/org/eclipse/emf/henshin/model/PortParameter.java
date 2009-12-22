/**
 * <copyright>
 * </copyright>
 *
 * $Id: PortParameter.java,v 1.1 2009/10/28 10:38:06 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.PortParameter#getVariable <em>Variable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getPortParameter()
 * @model
 * @generated
 */
public interface PortParameter extends Port {
        /**
         * Returns the value of the '<em><b>Variable</b></em>' reference.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Variable</em>' reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Variable</em>' reference.
         * @see #setVariable(Variable)
         * @see org.eclipse.emf.henshin.model.HenshinPackage#getPortParameter_Variable()
         * @model
         * @generated
         */
        Variable getVariable();

        /**
         * Sets the value of the '{@link org.eclipse.emf.henshin.model.PortParameter#getVariable <em>Variable</em>}' reference.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Variable</em>' reference.
         * @see #getVariable()
         * @generated
         */
        void setVariable(Variable value);

} // PortParameter
