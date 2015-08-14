/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getSort <em>Sort</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getAttributedElement <em>Attributed Element</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributeType()
 * @model
 * @generated
 */
public interface AttributeType extends AnnotatedElem {
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
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributeType_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sort</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sort</em>' reference.
	 * @see #setSort(Sort)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributeType_Sort()
	 * @model required="true"
	 * @generated
	 */
	Sort getSort();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getSort <em>Sort</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' reference.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(Sort value);

	/**
	 * Returns the value of the '<em><b>Attributed Element</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributed Element</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributed Element</em>' container reference.
	 * @see #setAttributedElement(AttributedElem)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributeType_AttributedElement()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem#getAttributes
	 * @model opposite="attributes" required="true" transient="false"
	 * @generated
	 */
	AttributedElem getAttributedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getAttributedElement <em>Attributed Element</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attributed Element</em>' container reference.
	 * @see #getAttributedElement()
	 * @generated
	 */
	void setAttributedElement(AttributedElem value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Sort lookUpSort(EDataType dataType);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	EDataType lookUpEDataType(Sort sort);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Sort lookUpSort(String name);
} // AttributeType
