/**
 */
package nestedcondition;

import graph.Edge;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link nestedcondition.EdgeMapping#getOrigin <em>Origin</em>}</li>
 *   <li>{@link nestedcondition.EdgeMapping#getImage <em>Image</em>}</li>
 * </ul>
 *
 * @see nestedcondition.NestedconditionPackage#getEdgeMapping()
 * @model
 * @generated
 */
public interface EdgeMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Origin</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin</em>' reference.
	 * @see #setOrigin(Edge)
	 * @see nestedcondition.NestedconditionPackage#getEdgeMapping_Origin()
	 * @model required="true"
	 * @generated
	 */
	Edge getOrigin();

	/**
	 * Sets the value of the '{@link nestedcondition.EdgeMapping#getOrigin <em>Origin</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin</em>' reference.
	 * @see #getOrigin()
	 * @generated
	 */
	void setOrigin(Edge value);

	/**
	 * Returns the value of the '<em><b>Image</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Image</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Image</em>' reference.
	 * @see #setImage(Edge)
	 * @see nestedcondition.NestedconditionPackage#getEdgeMapping_Image()
	 * @model required="true"
	 * @generated
	 */
	Edge getImage();

	/**
	 * Sets the value of the '{@link nestedcondition.EdgeMapping#getImage <em>Image</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Image</em>' reference.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(Edge value);

} // EdgeMapping
