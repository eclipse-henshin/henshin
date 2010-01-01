/**
 * <copyright>
 * </copyright>
 *
 * $Id: AttributeCondition.java,v 1.1 2009/10/28 10:38:04 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.AttributeCondition#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AttributeCondition#getConditionText <em>Condition Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAttributeCondition()
 * @model
 * @generated
 */
public interface AttributeCondition extends DescribedElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Rule</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Rule#getAttributeConditions <em>Attribute Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' container reference.
	 * @see #setRule(Rule)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAttributeCondition_Rule()
	 * @see org.eclipse.emf.henshin.model.Rule#getAttributeConditions
	 * @model opposite="attributeConditions" transient="false"
	 * @generated
	 */
	Rule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.AttributeCondition#getRule <em>Rule</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' container reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Condition Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition Text</em>' attribute.
	 * @see #setConditionText(String)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAttributeCondition_ConditionText()
	 * @model
	 * @generated
	 */
	String getConditionText();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.AttributeCondition#getConditionText <em>Condition Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition Text</em>' attribute.
	 * @see #getConditionText()
	 * @generated
	 */
	void setConditionText(String value);

} // AttributeCondition
