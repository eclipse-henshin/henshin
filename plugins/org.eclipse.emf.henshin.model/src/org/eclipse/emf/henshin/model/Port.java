/**
 * <copyright>
 * </copyright>
 *
 * $Id: Port.java,v 1.1 2009/10/28 10:38:07 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Port#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Port#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getPort()
 * @model abstract="true"
 * @generated
 */
public interface Port extends DescribedElement, NamedElement {
        /**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.henshin.model.PortKind}.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Direction</em>' attribute isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.emf.henshin.model.PortKind
	 * @see #setDirection(PortKind)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getPort_Direction()
	 * @model
	 * @generated
	 */
        PortKind getDirection();

        /**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Port#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.emf.henshin.model.PortKind
	 * @see #getDirection()
	 * @generated
	 */
        void setDirection(PortKind value);

        /**
	 * Returns the value of the '<em><b>Unit</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.TransformationUnit#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
         * <p>
         * If the meaning of the '<em>Unit</em>' container reference isn't clear,
         * there really should be more of a description here...
         * </p>
         * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' container reference.
	 * @see #setUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getPort_Unit()
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#getPorts
	 * @model opposite="ports" transient="false"
	 * @generated
	 */
        TransformationUnit getUnit();

        /**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Port#getUnit <em>Unit</em>}' container reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' container reference.
	 * @see #getUnit()
	 * @generated
	 */
        void setUnit(TransformationUnit value);

} // Port
