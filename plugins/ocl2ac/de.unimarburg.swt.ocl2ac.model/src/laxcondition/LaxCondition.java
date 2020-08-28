/**
 */
package laxcondition;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Lax Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link laxcondition.LaxCondition#getFormula <em>Formula</em>}</li>
 * </ul>
 *
 * @see laxcondition.LaxconditionPackage#getLaxCondition()
 * @model abstract="true"
 * @generated
 */
public interface LaxCondition extends EObject {
	/**
	 * Returns the value of the '<em><b>Formula</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link laxcondition.Formula#getArguments <em>Arguments</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Formula</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Formula</em>' container reference.
	 * @see #setFormula(Formula)
	 * @see laxcondition.LaxconditionPackage#getLaxCondition_Formula()
	 * @see laxcondition.Formula#getArguments
	 * @model opposite="arguments" transient="false"
	 * @generated
	 */
	Formula getFormula();

	/**
	 * Sets the value of the '{@link laxcondition.LaxCondition#getFormula <em>Formula</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Formula</em>' container reference.
	 * @see #getFormula()
	 * @generated
	 */
	void setFormula(Formula value);

} // LaxCondition
