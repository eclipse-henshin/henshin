/**
 */
package laxcondition;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Formula</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link laxcondition.Formula#getOp <em>Op</em>}</li>
 *   <li>{@link laxcondition.Formula#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see laxcondition.LaxconditionPackage#getFormula()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='OneArgumentForNOT AtLeastTwoArgumentForANDOR TwoArgumentForIMPLEQUALXOR'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot OneArgumentForNOT='(self.op = Operator::NOT) implies (self.arguments -> size() = 1)' AtLeastTwoArgumentForANDOR='((self.op = Operator::AND) or (self.op = Operator::OR)) implies (self.arguments -> size() > 1)' TwoArgumentForIMPLEQUALXOR='((self.op = Operator::IMPLIES) or (self.op = Operator::EQUIVALENT) or (self.op = Operator::XOR)) implies (self.arguments -> size() = 2)'"
 * @generated
 */
public interface Formula extends LaxCondition {
	/**
	 * Returns the value of the '<em><b>Op</b></em>' attribute.
	 * The default value is <code>"NOT"</code>.
	 * The literals are from the enumeration {@link laxcondition.Operator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Op</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Op</em>' attribute.
	 * @see laxcondition.Operator
	 * @see #setOp(Operator)
	 * @see laxcondition.LaxconditionPackage#getFormula_Op()
	 * @model default="NOT" required="true"
	 * @generated
	 */
	Operator getOp();

	/**
	 * Sets the value of the '{@link laxcondition.Formula#getOp <em>Op</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Op</em>' attribute.
	 * @see laxcondition.Operator
	 * @see #getOp()
	 * @generated
	 */
	void setOp(Operator value);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
	 * The list contents are of type {@link laxcondition.LaxCondition}.
	 * It is bidirectional and its opposite is '{@link laxcondition.LaxCondition#getFormula <em>Formula</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see laxcondition.LaxconditionPackage#getFormula_Arguments()
	 * @see laxcondition.LaxCondition#getFormula
	 * @model opposite="formula" containment="true" required="true"
	 * @generated
	 */
	EList<LaxCondition> getArguments();

} // Formula
