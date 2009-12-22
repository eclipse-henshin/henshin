/**
 * <copyright>
 * </copyright>
 *
 * $Id: DescribedElement.java,v 1.1 2009/10/28 10:38:07 enrico Exp $
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Described Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.DescribedElement#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getDescribedElement()
 * @model abstract="true"
 * @generated
 */
public interface DescribedElement extends EObject {
        /**
         * Returns the value of the '<em><b>Description</b></em>' attribute.
         * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Description</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
         * @return the value of the '<em>Description</em>' attribute.
         * @see #setDescription(String)
         * @see org.eclipse.emf.henshin.model.HenshinPackage#getDescribedElement_Description()
         * @model
         * @generated
         */
        String getDescription();

        /**
         * Sets the value of the '{@link org.eclipse.emf.henshin.model.DescribedElement#getDescription <em>Description</em>}' attribute.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @param value the new value of the '<em>Description</em>' attribute.
         * @see #getDescription()
         * @generated
         */
        void setDescription(String value);

} // DescribedElement
