/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attributed Elem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributedElem#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributedElem()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface AttributedElem extends EObject {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getAttributedElement <em>Attributed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getAttributedElem_Attributes()
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.AttributeType#getAttributedElement
	 * @model opposite="attributedElement" containment="true"
	 * @generated
	 */
	EList<AttributeType> getAttributes();

} // AttributedElem
