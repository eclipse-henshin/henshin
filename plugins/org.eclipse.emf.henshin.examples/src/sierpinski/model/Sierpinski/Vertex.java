/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package sierpinski.model.Sierpinski;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link sierpinski.model.Sierpinski.Vertex#getLeft <em>Left</em>}</li>
 *   <li>{@link sierpinski.model.Sierpinski.Vertex#getConn <em>Conn</em>}</li>
 *   <li>{@link sierpinski.model.Sierpinski.Vertex#getRight <em>Right</em>}</li>
 * </ul>
 * </p>
 *
 * @see sierpinski.model.Sierpinski.SierpinskiPackage#getVertex()
 * @model
 * @generated
 */
public interface Vertex extends EObject {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' reference.
	 * @see #setLeft(Vertex)
	 * @see sierpinski.model.Sierpinski.SierpinskiPackage#getVertex_Left()
	 * @model
	 * @generated
	 */
	Vertex getLeft();

	/**
	 * Sets the value of the '{@link sierpinski.model.Sierpinski.Vertex#getLeft <em>Left</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' reference.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(Vertex value);

	/**
	 * Returns the value of the '<em><b>Conn</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conn</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conn</em>' reference.
	 * @see #setConn(Vertex)
	 * @see sierpinski.model.Sierpinski.SierpinskiPackage#getVertex_Conn()
	 * @model
	 * @generated
	 */
	Vertex getConn();

	/**
	 * Sets the value of the '{@link sierpinski.model.Sierpinski.Vertex#getConn <em>Conn</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conn</em>' reference.
	 * @see #getConn()
	 * @generated
	 */
	void setConn(Vertex value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' reference.
	 * @see #setRight(Vertex)
	 * @see sierpinski.model.Sierpinski.SierpinskiPackage#getVertex_Right()
	 * @model
	 * @generated
	 */
	Vertex getRight();

	/**
	 * Sets the value of the '{@link sierpinski.model.Sierpinski.Vertex#getRight <em>Right</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' reference.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(Vertex value);

} // Vertex
