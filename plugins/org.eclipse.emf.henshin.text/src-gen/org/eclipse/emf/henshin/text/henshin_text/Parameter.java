/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Parameter#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Parameter#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.Parameter#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getParameter()
 * @model
 * @generated
 */
public interface Parameter extends EObject
{
  /**
   * Returns the value of the '<em><b>Kind</b></em>' attribute.
   * The literals are from the enumeration {@link org.eclipse.emf.henshin.text.henshin_text.ParameterKind}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Kind</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Kind</em>' attribute.
   * @see org.eclipse.emf.henshin.text.henshin_text.ParameterKind
   * @see #setKind(ParameterKind)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getParameter_Kind()
   * @model
   * @generated
   */
  ParameterKind getKind();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Parameter#getKind <em>Kind</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Kind</em>' attribute.
   * @see org.eclipse.emf.henshin.text.henshin_text.ParameterKind
   * @see #getKind()
   * @generated
   */
  void setKind(ParameterKind value);

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
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getParameter_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Parameter#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(ParameterType)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getParameter_Type()
   * @model containment="true"
   * @generated
   */
  ParameterType getType();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.Parameter#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(ParameterType value);

} // Parameter
