/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraph;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;
import org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Attribute#getInstanceOf <em>Instance Of</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samgraph.Attribute#getAttributedElement <em>Attributed Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Instance Of</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance Of</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance Of</em>' reference.
	 * @see #setInstanceOf(AttributeType)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getAttribute_InstanceOf()
	 * @model required="true"
	 * @generated
	 */
	AttributeType getInstanceOf();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Attribute#getInstanceOf <em>Instance Of</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance Of</em>' reference.
	 * @see #getInstanceOf()
	 * @generated
	 */
	void setInstanceOf(AttributeType value);

	/**
	 * Returns the value of the '<em><b>Attributed Element</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributed Element</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributed Element</em>' container reference.
	 * @see #setAttributedElement(AttributedElem)
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage#getAttribute_AttributedElement()
	 * @see org.eclipse.emf.henshin.sam.model.samgraph.AttributedElem#getAttributes
	 * @model opposite="attributes" required="true" transient="false"
	 * @generated
	 */
	AttributedElem getAttributedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samgraph.Attribute#getAttributedElement <em>Attributed Element</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributed Element</em>' container reference.
	 * @see #getAttributedElement()
	 * @generated
	 */
	void setAttributedElement(AttributedElem value);

} // Attribute
