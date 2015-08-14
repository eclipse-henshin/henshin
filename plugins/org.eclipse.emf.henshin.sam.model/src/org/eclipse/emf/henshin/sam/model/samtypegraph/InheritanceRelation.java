/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inheritance Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSubType <em>Sub Type</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSuperType <em>Super Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getInheritanceRelation()
 * @model
 * @generated
 */
public interface InheritanceRelation extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Sub Type</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSuperType <em>Super Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Type</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Type</em>' container reference.
	 * @see #setSubType(NodeType)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getInheritanceRelation_SubType()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSuperType
	 * @model opposite="superType" required="true" transient="false"
	 * @generated
	 */
	NodeType getSubType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSubType <em>Sub Type</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Type</em>' container reference.
	 * @see #getSubType()
	 * @generated
	 */
	void setSubType(NodeType value);

	/**
	 * Returns the value of the '<em><b>Super Type</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSubType <em>Sub Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Type</em>' reference.
	 * @see #setSuperType(NodeType)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getInheritanceRelation_SuperType()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.NodeType#getSubType
	 * @model opposite="subType" required="true"
	 * @generated
	 */
	NodeType getSuperType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.InheritanceRelation#getSuperType <em>Super Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Type</em>' reference.
	 * @see #getSuperType()
	 * @generated
	 */
	void setSuperType(NodeType value);

} // InheritanceRelation
