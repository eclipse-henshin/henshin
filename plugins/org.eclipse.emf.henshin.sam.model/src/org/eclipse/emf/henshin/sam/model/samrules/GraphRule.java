/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules;

import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.OperationUse;
import org.eclipse.emf.henshin.sam.model.samannotation.AnnotatedElem;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A <code>GraphRule</code> consists of a <code>left</code> and <code>right</code> <code>RuleGraph</code>, describing the left-hand side and right-hand side of the rule, respectively.
 * It may have an <code>attributeCondition</code> referring to an <code>Operation</code>, constraining the attribute values before and after applying the rule.
 * If the <code>OperationParameters</code> consists of some <code>AttributeUse</code> elements, then the corresponding <code>Attributes</code> should belong to nodes or edges in one of the <code>RuleGraph</code>s (TO BE CHECKED).
 * Rules may have a <code>priority</code>, applying rules with a higher priority first.
 * Rules may be <code>urgent</code> such that their application is triggered as soon as the rule becomes applicable.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getRight <em>Right</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getAttributeCondition <em>Attribute Condition</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#isUrgent <em>Urgent</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule()
 * @model
 * @generated
 */
public interface GraphRule extends AnnotatedElem {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(RuleGraph)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule_Left()
	 * @model containment="true"
	 * @generated
	 */
	RuleGraph getLeft();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(RuleGraph value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(RuleGraph)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule_Right()
	 * @model containment="true"
	 * @generated
	 */
	RuleGraph getRight();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(RuleGraph value);

	/**
	 * Returns the value of the '<em><b>Attribute Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Condition</em>' containment reference.
	 * @see #setAttributeCondition(OperationUse)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule_AttributeCondition()
	 * @model containment="true"
	 * @generated
	 */
	OperationUse getAttributeCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getAttributeCondition <em>Attribute Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Condition</em>' containment reference.
	 * @see #getAttributeCondition()
	 * @generated
	 */
	void setAttributeCondition(OperationUse value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(int)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule_Priority()
	 * @model
	 * @generated
	 */
	int getPriority();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(int value);

	/**
	 * Returns the value of the '<em><b>Urgent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Urgent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Urgent</em>' attribute.
	 * @see #setUrgent(boolean)
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule_Urgent()
	 * @model
	 * @generated
	 */
	boolean isUrgent();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#isUrgent <em>Urgent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Urgent</em>' attribute.
	 * @see #isUrgent()
	 * @generated
	 */
	void setUrgent(boolean value);

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
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#getGraphRule_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samrules.GraphRule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // GraphRule
