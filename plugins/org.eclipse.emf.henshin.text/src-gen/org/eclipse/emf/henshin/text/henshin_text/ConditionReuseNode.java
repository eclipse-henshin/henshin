/**
 * generated by Xtext 2.16.0
 */
package org.eclipse.emf.henshin.text.henshin_text;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition Reuse Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.ConditionReuseNode#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.text.henshin_text.ConditionReuseNode#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getConditionReuseNode()
 * @model
 * @generated
 */
public interface ConditionReuseNode extends ConditionGraphElements
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' reference.
   * @see #setName(ConditionNodeTypes)
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getConditionReuseNode_Name()
   * @model
   * @generated
   */
  ConditionNodeTypes getName();

  /**
   * Sets the value of the '{@link org.eclipse.emf.henshin.text.henshin_text.ConditionReuseNode#getName <em>Name</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' reference.
   * @see #getName()
   * @generated
   */
  void setName(ConditionNodeTypes value);

  /**
   * Returns the value of the '<em><b>Attribute</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.emf.henshin.text.henshin_text.Match}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attribute</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attribute</em>' containment reference list.
   * @see org.eclipse.emf.henshin.text.henshin_text.Henshin_textPackage#getConditionReuseNode_Attribute()
   * @model containment="true"
   * @generated
   */
  EList<Match> getAttribute();

} // ConditionReuseNode
