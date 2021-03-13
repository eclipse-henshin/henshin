/**
 */
package nestedcondition;

import laxcondition.Operator;

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
 *   <li>{@link nestedcondition.Formula#getOperator <em>Operator</em>}</li>
 *   <li>{@link nestedcondition.Formula#getArguments <em>Arguments</em>}</li>
 * </ul>
 *
 * @see nestedcondition.NestedconditionPackage#getFormula()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ArgumentsDomainConsistency OneArgumentForNOT AtLeastTwoArgumentForANDOR TwoArgumentForIMPLEQUALXOR'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ArgumentsDomainConsistency='self.arguments -> forAll(cond|cond.domain = self.domain)' OneArgumentForNOT='(self.operator = laxcondition::Operator::NOT) implies (self.arguments -> size() = 1)' AtLeastTwoArgumentForANDOR='((self.operator = laxcondition::Operator::AND) or (self.operator = laxcondition::Operator::OR)) implies (self.arguments -> size() > 1)' TwoArgumentForIMPLEQUALXOR='((self.operator = laxcondition::Operator::IMPLIES) or (self.operator = laxcondition::Operator::EQUIVALENT) or (self.operator = laxcondition::Operator::XOR)) implies (self.arguments -> size() = 2)'"
 * @generated
 */
public interface Formula extends NestedCondition {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link laxcondition.Operator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see laxcondition.Operator
	 * @see #setOperator(Operator)
	 * @see nestedcondition.NestedconditionPackage#getFormula_Operator()
	 * @model
	 * @generated
	 */
	Operator getOperator();

	/**
	 * Sets the value of the '{@link nestedcondition.Formula#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see laxcondition.Operator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(Operator value);

	/**
	 * Returns the value of the '<em><b>Arguments</b></em>' containment reference list.
	 * The list contents are of type {@link nestedcondition.NestedCondition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arguments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arguments</em>' containment reference list.
	 * @see nestedcondition.NestedconditionPackage#getFormula_Arguments()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<NestedCondition> getArguments();

} // Formula
